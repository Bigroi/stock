package com.bigroi.stock.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static final long MILS_IN_DAY = 1000l * 60l * 60l * 24l;

	public static boolean isBeforToday(Date date) {
		return dateWithZeroHours(new Date()).after(dateWithZeroHours(date));		
	}

	private static Calendar dateWithZeroHours(Date date) {
		Calendar c =  GregorianCalendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND,0);
		return c;
	}
	
	public static Date shiftMonths(Date date, int monthsToSubtract) {
		LocalDate localDate = LocalDate.ofEpochDay(date.getTime() / MILS_IN_DAY).plusMonths(monthsToSubtract);
		return new Date(localDate.toEpochDay() * MILS_IN_DAY);		
	}

	private DateUtil(){}
}
