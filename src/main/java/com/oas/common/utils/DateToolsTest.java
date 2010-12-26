package com.oas.common.utils;

import java.util.Date;

import com.wirecard.common.utils.DateToolsException;

public class DateToolsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateTools dateTools = DateTools.getInstance();
		Date startDate = dateTools.toDate("2009-05-01");
		Date endDate = dateTools.toDate("2009-05-04");
		try {
			boolean val = dateTools.checkIfDifferenceMoreThanOneMonth(startDate, endDate);
			System.out.println(val);
		} catch (DateToolsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
