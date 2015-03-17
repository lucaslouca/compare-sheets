package com.mydomain.app.parser;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.domain.app.model.Person;

public abstract class AbstractSheetParser {
	private static Logger logger = Logger.getLogger(AbstractSheetParser.class.getName());

	protected final String VACATION = "V";
	protected final String NO_VACATION = "X";

	/**
	 * Returns the number of days in a month for a given year.
	 * 
	 * @param month zero-base month
	 * @param year
	 * @return the number of days in month
	 */
	protected int getDaysInMonth(int month, int year) {
		int iYear = year;
		int iMonth = month;
		int iDay = 1;

		Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return daysInMonth;
	}

	/**
	 * Merge two vacation arrays of the same month. Since we are merging boolean
	 * arrays the merge will be an | operation.
	 *
	 * 
	 * @param monthToVacation1
	 * @param monthToVacation2
	 * @return <code>Map</code> containing the month name as the key and the
	 *         merged vacation array as the value.
	 */
	private Map<String, Boolean[]> mergeVacations(Map<String, Boolean[]> monthToVacation1, Map<String, Boolean[]> monthToVacation2) {
		Map<String, Boolean[]> result = new HashMap<String, Boolean[]>();

		for (String month : monthToVacation1.keySet()) {
			Boolean[] vacation = monthToVacation1.get(month);
			if (monthToVacation2.containsKey(month)) {
				Boolean[] vacation2 = monthToVacation2.get(month);
				for (int d = 0; d < vacation.length; d++) {
					vacation[d] = vacation[d] | vacation2[d];
				}
			}

			result.put(month, vacation);
		}

		return result;
	}

	/**
	 * Read the excel sheet, parse it accordingly and return a <code>Map</code>:
	 * <code>Person</code> -> <code>Map</code>[<code>Month name (String)</code>
	 * -> <code>Boolean[]</code> ]
	 * 
	 * @param fileName
	 *            the full path of the .xlsx file
	 * @return returns <code>Map</code>: <code>Person</code> -> [
	 *         <code>String</code> -> <code>Boolean[]</code>]
	 */
	public Map<Person, Map<String, Boolean[]>> parse(String fileName) {
		Map<Person, Map<String, Boolean[]>> result = new HashMap<Person, Map<String, Boolean[]>>();

		String NAME_COLUMN_REFERENCE = getNameColumnReference();
		String NAME_ROW_START_INDICATOR = getNameRowStartIndicator();

		try {
			XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(new File(fileName));
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			int numberOfColunms = 0;
			int tmpNumberOfColumns = 0;

			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows
			for (int i = 0; i < 10 || i < numberOfRows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmpNumberOfColumns = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmpNumberOfColumns > numberOfColunms)
						numberOfColunms = tmpNumberOfColumns;
				}
			}

			// Search row where the names start
			int nameStartIndex = 0;
			for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
				row = sheet.getRow(rowIndex);
				if (row != null) {
					cell = row.getCell(CellReference.convertColStringToIndex(NAME_COLUMN_REFERENCE));

					if (cell != null && XSSFCell.CELL_TYPE_STRING == cell.getCellType() && NAME_ROW_START_INDICATOR.equals(cell.getStringCellValue())) {
						nameStartIndex = rowIndex + 1;
						continue;
					}

					if (nameStartIndex > 0 && rowIndex >= nameStartIndex) {
						if (XSSFCell.CELL_TYPE_STRING == cell.getCellType() && !cell.getStringCellValue().isEmpty()) {
							Map<String, Boolean[]> vacation = getMonthToDays(row, numberOfColunms);

							Person person = getPerson(row);

							if (result.containsKey(person)) {
								logger.debug("Merging " + person);
								Map<String, Boolean[]> existingVacations = result.get(person);
								vacation = mergeVacations(vacation, existingVacations);
							}

							result.put(person, vacation);
						}
					}

				}
			}

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

		return result;
	}

	/**
	 * Return the <code>String</code> cell reference representation of the
	 * column that contains the person names
	 * 
	 * @return <code>String</code> cell reference representation of the column
	 *         that contains the person names (e.g.: "I")
	 */
	protected abstract String getNameColumnReference();

	/**
	 * Return the <code>String</code> value the cell representing the header for
	 * the names has.<br>
	 * <br>
	 * 
	 * Example (one colunm):<br>
	 * ...<br>
	 * ...<br>
	 * Last Name, First Name<br>
	 * Appleseed, John<br>
	 * John, Doe<br>
	 * ...<br>
	 * <br>
	 * will return "Last Name, First Name"
	 * 
	 * @return <code>String</code> value the cell representing the header for
	 *         the names has
	 */
	protected abstract String getNameRowStartIndicator();

	protected abstract Map<String, Boolean[]> getMonthToDays(XSSFRow row, int cols);

	/**
	 * Return a <code>Person</code> that is contained in this row (first name,
	 * last name, etc).
	 * 
	 * @param row
	 * @return <code>Person</code> that is contained in row
	 */
	protected abstract Person getPerson(XSSFRow row);

}
