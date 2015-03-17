package com.domain.app.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import com.domain.app.model.Month;
import com.domain.app.model.Person;

public class AParser extends AbstractSheetParser {
	private final String NAME_COLUMN_REFERENCE = "I";
	private final String VACATION_COLUMN_REFERENCE = "V";
	private final String LAST_NAME_COLUMN_REFERENCE = "J";
	private final String NAME_ROW_START_INDICATOR = "Name";
	private final Month month;

	public AParser(Month month) {
		this.month = month;
	}

	/**
	 * Given a row: <br>
	 * <br>
	 * "some_values...some_values... - V - V - ... - - some_values..." <br>
	 * <br>
	 * We search the row for the column where the vacation entries start (the
	 * first '-' in the example above). When we find the start of the vacation
	 * entries we add each cell entry (till the end of the row) as an item in a
	 * <code>List</code>. Note: the list will contain also entries that are not
	 * vacation relevant. In the example above the <code>List</code> will
	 * contain all entries after the first '-' (inclusive):
	 * 
	 * <br>
	 * <br>
	 * 
	 * "- V - V - ... - - some_values..." <br>
	 * <br>
	 * Once we have this <code>List</code> we create an
	 * <code>Boolean[DAYS_IN_MONTH]</code> and iterate over the days of the
	 * month. Setting index <code>d</code> to <code>true</code> if
	 * <code>List.get(d) != BLANK</code> and false otherwise
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
				if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					double hours = cell.getNumericCellValue();
					if (hours > 0) {
						rowValues.add(NO_VACATION);
					} else {
						rowValues.add(VACATION);
					}
				} else if (XSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
					rowValues.add(VACATION);
				}
			}
		}

		int daysInMonth = getDaysInMonth(month.getNumber(), 2015);
		Boolean[] monthVacation = new Boolean[daysInMonth];
		for (int day = 0; day < daysInMonth; day++) {
			if (NO_VACATION.equals(rowValues.get(day))) {
				monthVacation[day] = false;
			} else {
				monthVacation[day] = true;
			}
		}
		result.put(month.name(), monthVacation);

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
		String personFirstName = cell.getStringCellValue();

		cell = row.getCell(CellReference.convertColStringToIndex(LAST_NAME_COLUMN_REFERENCE));
		String personLastName = cell.getStringCellValue();

		return new Person(personFirstName.trim(), personLastName.trim());
	}
}
