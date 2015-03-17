package com.domain.app.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HolidayUtil {
	private static Logger logger = Logger.getLogger(HolidayUtil.class.getName());

	private static List<Calendar> holidays;

	public HolidayUtil() {
		readHolidays();
	}

	/**
	 * Reads dates from the holidays.xml file and adds them to holidays
	 * <code>List</code>
	 * 
	 */
	private void readHolidays() {
		logger.debug("Reading holidays from file...");
		holidays = new ArrayList<Calendar>();

		ClassLoader classLoader = getClass().getClassLoader();
		File xmlFile = new File(classLoader.getResource("holidays.xml").getFile());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("date");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node dateNode = nList.item(temp);
				if (dateNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) dateNode;
					int day = Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
					int month = Integer.parseInt(eElement.getElementsByTagName("month").item(0).getTextContent());
					int year = Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
					holidays.add(new GregorianCalendar(year, (month - 1), day));
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns <code>true</code> if day/month/year is a weekend or holiday.
	 * <code>false</code> otherwise.
	 * 
	 * @param day
	 * @param month
	 *            zero-based month
	 * @param year
	 * @return Returns <code>true</code> if day/month/year is a weekend or
	 *         holiday. <code>false</code> otherwise.
	 */
	public boolean isHoliday(int day, int month, int year) {
		boolean result = false;

		Calendar mycal = new GregorianCalendar(year, month, day);
		if (mycal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			result = true;
		} else if (mycal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			result = true;
		} else if (holidays.contains(mycal)) {
			result = true;
		}

		return result;
	}
}
