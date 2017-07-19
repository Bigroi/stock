package com.bigroi.stock.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static boolean beforToday(Date date) {
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

}
