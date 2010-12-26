package com.oas.common.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.wirecard.common.utils.DateFormatDef;
import com.wirecard.common.utils.DateToolsException;

/**
 * DateTools a class that handles all date manipulation it uses Calendar from
 * Java.
 * 
 * @author Wire Card AG (Herry John)
 * @version 1.0
 * @since 2003-January-21
 */
public class DateTools {
	// Number of days for each month
	private static byte numberDaysIndex[] = { 31, // January
			28, // February
			31, // March
			30, // April
			31, // May
			30, // June
			31, // July
			31, // August
			30, // September
			31, // October
			30, // November
			31 // December
	};

	private static String dayName[] = { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", };

	private static final String FIRST = "st";
	private static final String SECOND = "nd";
	private static final String THIRD = "rd";
	private static final String REST = "th";

	public final static int SEMI_MONTH = 1001;

	private static final int[][] fields = { { Calendar.MILLISECOND }, { Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR }, { Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM
			/*
			 * Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK,
			 * Calendar.DAY_OF_WEEK_IN_MONTH
			 */
			}, { Calendar.MONTH, SEMI_MONTH }, { Calendar.YEAR }, { Calendar.ERA } };

	/**
	 * To get instance of DateTools, we use this way so we can control the
	 * creation of this instance without changing the client.
	 * 
	 * @return the new instance of DateTools
	 */
	public synchronized static DateTools getInstance() {
		return new DateTools();
	}

	private Calendar calendar;

	/**
	 * private constructor, user cannot instantiate it directly
	 */
	private DateTools() {
		calendar = Calendar.getInstance(DateFormatDef.STD_LOCALE);
	}

	/**
	 * To convert date into String format
	 * 
	 * @param date
	 *            date input in Date format
	 * @return the date in String format
	 */
	public String toString(Date date) {
		return toString(date, DateFormatDef.DATE_PATTERN);
	}

	/**
	 * To convert date into String format with specific pattern
	 * 
	 * @param date
	 *            Date input in Date format
	 * @param pattern
	 *            The date pattern
	 * @return the date in String format
	 */
	public String toString(Date date, String pattern) {
		if (date != null && pattern != null) {
			calendar.setTime(date);

			// Set format patterns and assign calendar
			SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance();
			dateFormat.applyPattern(pattern);
			dateFormat.setCalendar(calendar);

			// Return date/time in appropriate pattern
			String returnDate = dateFormat.format(date).toString();

			return returnDate;
		} else {
			return null;
		}
	}

	/**
	 * To convert date into String format with specific pattern
	 * 
	 * @param date
	 *            Date input in Date format
	 * @param pattern
	 *            The date pattern
	 * @return the date in String format
	 */
	public String toString(Calendar cal, String pattern) {
		if (cal != null) {
			calendar.setTime(cal.getTime());

			// Set format patterns and assign calendar
			SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance();
			dateFormat.applyPattern(pattern);
			dateFormat.setCalendar(calendar);

			// Return date/time in appropriate pattern
			String returnDate = dateFormat.format(cal).toString();

			return returnDate;
		} else {
			return null;
		}
	}
	
	
	/**
	 * To convert date in String format into Date format
	 * 
	 * @param dateStr
	 *            Date in String format
	 * @return the date in Date class format
	 */
	public Date toDate(String dateStr) {
		if (dateStr != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(DateFormatDef.DATE_PATTERN);

			// Parse the previous string back into a Date.
			ParsePosition pos = new ParsePosition(0);
			Date temp = formatter.parse(dateStr, pos);

			return temp;
		} else {
			return null;
		}
	}

	/**
	 * To roll the "date" by some "numbers"
	 * 
	 * @param date
	 *            The date that we want to roll
	 * @param numbers
	 *            The number of days that we want to roll, can be minus
	 * @return The rolled date
	 */
	public Date rollDate(Date date, int numbers) {
		calendar.setTime(date);

		calendar.add(Calendar.DAY_OF_MONTH, numbers);

		return calendar.getTime();
	}

	public Date rollMonth(Date date, int numbers) {
		calendar.setTime(date);

		calendar.add(Calendar.MONTH, numbers);

		return calendar.getTime();
	}

	/**
	 * To get the day from the date
	 * 
	 * @param date
	 *            The input date
	 * @return the output day
	 */
	public int getDay(Date date) {
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public int getDayOfMonth(Date date) {
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth(Date date) {
		calendar.setTime(date);

		return calendar.get(Calendar.MONTH);
	}

	public int getYear(Date date) {
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}

	/**
	 * To get the day from the date + numbers
	 * 
	 * @param date
	 *            The input date
	 * @return the output day
	 */
	public int getDay(Date date, int numbers) {
		// roll the date
		date = rollDate(date, numbers);

		// get the calendar
		calendar.setTime(date);

		// return the day
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * To get the day after adding with numbers
	 * 
	 * @param day
	 * @param numbers
	 * @return
	 */
	public int getTheDayAfter(int day, int numbers) {
		calendar.roll(Calendar.DAY_OF_WEEK, numbers);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * To get number of days in specific month & year
	 * 
	 * @param month
	 * @param year
	 * @return number of days in specific month & year
	 * @throws com.wirecard.tools.DateToolsException
	 */
	public int getNumberDaysInMonth(int month, int year) throws DateToolsException {
		// PeriodCheck the month value
		if (month < 1 || month > 12) {
			throw new DateToolsException("Month is not valid, should be between 1 until 12 !");
		}

		// PeriodCheck the year value
		if (year < 1000 || year > 2500) {
			throw new DateToolsException("Year is not valid, should be at least 4 digit !");
		}

		int numberDays = numberDaysIndex[month - 1];

		// PeriodCheck additional day for February
		if (year % 4 == 0 && month == 2) {
			numberDays++;
		}

		return numberDays;
	}

	public Date setDayOfMonth(Date date, int dayOfMonth) {
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		return calendar.getTime();
	}

	public int getNumberDaysInMonth(Date date, int offsetMonth) throws DateToolsException {
		calendar.setTime(date);

		calendar.roll(Calendar.MONTH, offsetMonth);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		return getNumberDaysInMonth(month, year);
	}

	public int getNumberDaysInMonth(Date date) throws DateToolsException {
		// get the calendar
		calendar.setTime(date);

		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		return getNumberDaysInMonth(month, year);
	}

	public Date rollMonthsAndSetDate(Date source, int months, int targetDate) {
		calendar.setTime(source);
		calendar.add(Calendar.MONTH, -1 * months);
		int rolledMonth = calendar.get(Calendar.MONTH) + 1;
		int rolledYear = calendar.get(Calendar.YEAR);
		calendar.set(rolledYear, rolledMonth - 1, targetDate);

		return calendar.getTime();
	}

	/**
	 * To get day with additional info like 1 become 1st
	 * 
	 * @param number
	 * @return The number with postfix in String
	 */
	public String getDayWithPostfix(int number) {
		String numberInStr = String.valueOf(number);

		char endCharacter = numberInStr.charAt(numberInStr.length() - 1);

		if (endCharacter == '1') {
			return number + FIRST;
		} else if (endCharacter == '2') {
			return number + SECOND;
		} else if (endCharacter == '3') {
			return number + THIRD;
		} else {
			return number + REST;
		}
	}

	/**
	 * To get day as String
	 * 
	 * @param day
	 * @return the string format of day
	 */
	public String getDayAsString(int day) {
		return dayName[day];
	}

	/**
	 * This method has responsibility to check if the different between
	 * startDate and endDate are more than one month!
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean checkIfDifferenceMoreThanOneMonth(Date startDate, Date endDate) throws DateToolsException {
		long startTimeDays = getRunDays(startDate);
		long endTimeDays = getRunDays(endDate);

		int numberDays = getNumberDaysInMonth(startDate);

		if (endTimeDays - startTimeDays >= numberDays) {
			return true;
		}

		return false;
	}

	/**
	 * This method has responsibility to check if the different between
	 * startDate and endDate are more then one year!
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean checkIfDifferenceMoreThanOneYear(Date startDate, Date endDate) {
		long startTimeDays = getRunDays(startDate);
		long endTimeDays = getRunDays(endDate);

		if (endTimeDays - startTimeDays >= 365) {
			return true;
		}

		return false;
	}

	public long getRunDays(Date input) {
		return input.getTime() / (1000 * 3600 * 24);
	}

	/**
	 * To add zero in the number if the length of the number is only 1, and
	 * automatically convert it to String
	 * 
	 * @param number
	 *            The number
	 * @return The converted number
	 */
	public static String addZero(int number) {
		String result = number + "";
		if (result.length() == 1)
			result = "0" + result;

		return result;
	}

	public static String getDiffTimeAsString(long startTs, long endTs) {
		if (startTs >= endTs) {
			return "00:00:00.000";
		}

		if (startTs < 0 || endTs < 0) {
			return "00:00:00.000";
		}

		long diff = endTs - startTs;

		int days = (int) (diff / (24 * 60 * 60 * 1000));
		diff = (int) (diff % (24 * 60 * 60 * 1000));

		int hours = (int) (diff / (60 * 60 * 1000));
		diff = (int) (diff % (60 * 60 * 1000));

		int min = (int) (diff / (60 * 1000));
		diff = (int) (diff % (60 * 1000));

		int secs = (int) (diff / (1000));
		int ms = (int) (diff % (1000));

		StringBuffer sb = new StringBuffer();
		if (days > 0) {
			sb.append(days).append(days == 1 ? " day " : " days ");
		}
		sb.append(hours < 10 ? "0" : "").append(hours).append(":");
		sb.append(min < 10 ? "0" : "").append(min).append(":");
		sb.append(secs < 10 ? "0" : "").append(secs).append(".");
		if (ms < 10) {
			sb.append("00").append(ms);
		} else {
			sb.append(ms < 100 ? "0" : "").append(ms);
		}

		return sb.toString();
	}

	/**
	 * <p>
	 * Truncate this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, false);
		return gval.getTime();
	}

	/**
	 * <p>
	 * Internal calculation method.
	 * </p>
	 * 
	 * @param val
	 *            the calendar
	 * @param field
	 *            the field constant
	 * @param round
	 *            true to round, false to truncate
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	private static void modify(Calendar val, int field, boolean round) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException("Calendar value too large for accurate calculations");
		}

		boolean roundUp = false;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] == field) {
					// This is our field... we stop looping
					if (round && roundUp) {
						if (field == SEMI_MONTH) {
							// This is a special case that's hard to generalize
							// If the date is 1, we round up to 16, otherwise
							// we subtract 15 days and add 1 month
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
						} else {
							// We need at add one to this field since the
							// last number causes us to round up
							val.add(fields[i][0], 1);
						}
					}
					return;
				}
			}
			// We have various fields that are not easy roundings
			int offset = 0;
			boolean offsetSet = false;
			// These are special categoryTypes of fields that require different rounding
			// rules
			switch (field) {
			case SEMI_MONTH:
				if (fields[i][0] == Calendar.DATE) {
					// If we're going to drop the DATE field's value,
					// we want to do this our own way.
					// We need to subtrace 1 since the date has a minimum of 1
					offset = val.get(Calendar.DATE) - 1;
					// If we're above 15 days adjustment, that means we're in
					// the
					// bottom half of the month and should stay accordingly.
					if (offset >= 15) {
						offset -= 15;
					}
					// Record whether we're in the top or bottom half of that
					// range
					roundUp = offset > 7;
					offsetSet = true;
				}
				break;
			case Calendar.AM_PM:
				if (fields[i][0] == Calendar.HOUR_OF_DAY) {
					// If we're going to drop the HOUR field's value,
					// we want to do this our own way.
					offset = val.get(Calendar.HOUR_OF_DAY);
					if (offset >= 12) {
						offset -= 12;
					}
					roundUp = offset > 6;
					offsetSet = true;
				}
				break;
			}
			if (!offsetSet) {
				int min = val.getActualMinimum(fields[i][0]);
				int max = val.getActualMaximum(fields[i][0]);
				// Calculate the offset from the minimum allowed value
				offset = val.get(fields[i][0]) - min;
				// Set roundUp if this is more than half way between the minimum
				// and maximum
				roundUp = offset > ((max - min) / 2);
			}
			// We need to remove this field
			val.set(fields[i][0], val.get(fields[i][0]) - offset);
		}
		throw new IllegalArgumentException("The field " + field + " is not supported");

	}
}

