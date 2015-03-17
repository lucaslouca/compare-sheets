package com.mydomain.app;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.domain.app.model.Month;
import com.domain.app.model.Person;
import com.domain.app.util.HolidayUtil;
import com.mydomain.app.parser.AParser;
import com.mydomain.app.parser.AbstractSheetParser;
import com.mydomain.app.parser.BParser;

public class App {
	private static Logger logger = Logger.getLogger(App.class.getName());

	private static final int YEAR = 2015;
	private static HolidayUtil holidayUtil;

	/**
	 * Returns the String representation of a Boolean[] array with days starting
	 * from 1...31 or 1...28, etc
	 * 
	 * @param array
	 * @return String representation of a <code>Boolean[]</code> array
	 */
	public static String getStringRepresentation(Boolean[] array) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			if (array[i]) {
				result.append("   D" + (i + 1) + " v ");
			} else {
				result.append("   D" + (i + 1) + " - ");
			}
		}

		return result.toString();
	}

	/**
	 * Compares <code>Boolean[]</code> arrays representing vacation days (
	 * <code>true</code> for vacation, <code>false</code> for non vacation) for
	 * a given <code>Month</code> and a given <code>Person</code>. If at least
	 * one pair of working days to be compared is not consistent (one day is
	 * noted as vacation and the other not) we have an inconsistency.
	 * 
	 * 
	 * @param person
	 * @param month
	 * @param vacationA
	 * @param vacationB
	 * 
	 * @return <code>true</code> if the two arrays are consistent.
	 *         <code>false</code> otherwise.
	 */
	private static boolean isVacationsOfPersonForMonthConsistent(Person person, Month month, Boolean[] vacationA, Boolean[] vacationB) {
		logger.info("Checking person: " + person);
		logger.info("Month: " + month.name());
		logger.info("A vacation entry: " + getStringRepresentation(vacationA));
		logger.info("B vacation entry: " + getStringRepresentation(vacationB));

		if (vacationB.length != vacationA.length) {
			throw new IllegalArgumentException("Arrays have different length!");
		}

		boolean result = true;
		StringBuilder inconsistentOutput = new StringBuilder();
		for (int day = 0; day < vacationA.length; day++) {
			if (!holidayUtil.isHoliday((day + 1), month.getNumber(), YEAR) && (vacationA[day] != vacationB[day])) {
				inconsistentOutput.append("[Day: " + (day + 1) + "  A:" + vacationA[day] + " vs B:" + vacationB[day] + "] ");
				result = false;
			}
		}

		logger.info(inconsistentOutput);
		logger.info("------------------------------------------------------------------------------------------------------------------------------------");

		return result;
	}

	/**
	 * Returns the <code>Set</code> of <code>Person</code> that have
	 * inconsistent entries between the two sheets.
	 * 
	 * 
	 * @param month
	 * @param nameToMonthToVacationA
	 * @param nameToMonthToVacationB
	 * @return <code>Set</code> of <code>Person</code> that have inconsistent
	 *         entries between the two sheets.
	 */
	private static Set<Person> findInconsistentPersons(Month month, Map<Person, Map<String, Boolean[]>> nameToMonthToVacationA,
			Map<Person, Map<String, Boolean[]>> nameToMonthToVacationB) {
		Set<Person> result = new HashSet<Person>();

		for (Person aPerson : nameToMonthToVacationA.keySet()) {
			B_LOOP: for (Person bPerson : nameToMonthToVacationB.keySet()) {
				Boolean[] bVacation = nameToMonthToVacationB.get(bPerson).get(month.name());
				if (bPerson.equals(aPerson)) {

					Boolean[] aVacation = nameToMonthToVacationA.get(aPerson).get(month.name());
					if (!isVacationsOfPersonForMonthConsistent(aPerson, month, aVacation, bVacation)) {
						result.add(aPerson);
					}

					break B_LOOP;
				}

			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("No enough sheet paths provided! Please provide 2 sheets to compare.");
		}

		String aSheetPath = args[0];
		String bSheetPath = args[1];

		final Month MONTH = Month.MARCH;
		holidayUtil = new HolidayUtil();

		AbstractSheetParser aParser = new AParser(MONTH);
		Map<Person, Map<String, Boolean[]>> nameToMonthToVacationA = aParser.parse(aSheetPath);

		AbstractSheetParser bParser = new BParser();
		Map<Person, Map<String, Boolean[]>> nameToMonthToVacationB = bParser.parse(bSheetPath);

		Set<Person> inconsistentPersons = findInconsistentPersons(MONTH, nameToMonthToVacationA, nameToMonthToVacationB);

		logger.info("Inconsistent persons:");
		for (Person person : inconsistentPersons) {
			logger.info(person);
		}
	}
}
