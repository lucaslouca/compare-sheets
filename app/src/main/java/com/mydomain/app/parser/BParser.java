package com.mydomain.app.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import com.domain.app.model.Month;
import com.domain.app.model.Person;

public class BParser extends AbstractSheetParser {
	private final String NAME_COLUMN_REFERENCE = "B";
	private final String VACATION_COLUMN_REFERENCE = "I";
	private final String NAME_ROW_START_INDICATOR = "Last Name, First Name";

	/**
	 * Given a row: <br>
	 * <br>
	 * "some_values...some_values... - V - V - ... - - some_values..." <br>
	 * <br>
	 * We search the row for the column where the vacation entries start (the
	 * first '-' in the example above). When we find the start of the vacation
	 * entries we add each cell entry (till the end of the row) as an item in a
	 * <code>List</code>. Note: the <code>List</code> will contain also entries
	 * that are not Vacation relevant. In the example above the
	 * <code>List</code> will contain all entries after the first '-'
	 * (inclusive):
	 * 
	 * <br>
	 * <br>
	 * 
	 * "- V - V - ... - - some_values..." <br>
	 * <br>
	 * Once we have the <code>List</code> we then iterate over all the months in
	 * a year and create an array <code>Boolean[DAYS_IN_MONTH_i]</code> for each
	 * month <code>i</code> that holds <code>true</code> at index <code>d</code>
	 * if <code>List.get(offset
	 * + d)</code> has a value and <code>false</code> if
	 * <code>List.get(offset + d)</code> is <code>NO_VACATION</code>.
	 * <code>offset</code> is the total amount of days of the previous months
	 * <code>(0..i-1)</code>.
	 * 
	 * @param row
	 *            the row
	 * @param cols
	 *            number of columns in the row
	 * @return a <code>Map</code> containing the month name (<code>String</code>
	 *         ) as the key to a <code>Boolean[]</code> of vacations
	 */
	@Override
	protected Map<String, Boolean[]> getMonthToDays(XSSFRow row, int cols) {
		Map<String, Boolean[]> result = new HashMap<String, Boolean[]>();

		List<String> rowValues = new ArrayList<String>();

		int vacationStartIndex = 0;
		for (int c = 0; c < cols; c++) {
			XSSFCell cell = row.getCell((short) c);
			if (vacationStartIndex == 0 && cell != null && cell.getReference().startsWith(VACATION_COLUMN_REFERENCE)) {
				// Found column index where vacations start
				vacationStartIndex = c;
			}

			if (vacationStartIndex > 0 && c >= vacationStartIndex) {
				if (XSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
					rowValues.add(VACATION);
				} else if (XSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
					rowValues.add(NO_VACATION);
				}
			}
		}

		int offset = 0;
		for (Month month : Month.values()) {
			int daysInMonth = getDaysInMonth(month.getNumber(), 2015);
			Boolean[] monthVacation = new Boolean[daysInMonth];
			int count = 0;
			for (int i = offset; i < (offset + daysInMonth); i++) {
				if (NO_VACATION.equals(rowValues.get(i))) {
					monthVacation[count] = false;
				} else {
					monthVacation[count] = true;
				}
				count++;
			}
			result.put(month.name(), monthVacation);
			offset = offset + daysInMonth;
		}

		return result;
	}

	@Override
	protected String getNameColumnReference() {
		return NAME_COLUMN_REFERENCE;
	}

	@Override
	protected String getNameRowStartIndicator() {
		return NAME_ROW_START_INDICATOR;
	}

	@Override
	protected Person getPerson(XSSFRow row) {
		XSSFCell cell = row.getCell(CellReference.convertColStringToIndex(getNameColumnReference()));
		String personName = cell.getStringCellValue();
		String[] nameSplitted = personName.split(",");
		if (nameSplitted.length < 2) {
			nameSplitted = personName.split(" ");
		}
		return new Person(nameSplitted[0].trim(), nameSplitted[1].trim());
	}
}
