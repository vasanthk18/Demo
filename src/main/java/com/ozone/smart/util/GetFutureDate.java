package com.ozone.smart.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetFutureDate {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
//	public static void main(String[] args) {
//		
//	}
	
	public String newDate(int intYr, int intMnth, int intDate, int intHr, int intMin, int intSec) {
		Date currentDate = new Date();
		
		//convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		
		c.add(Calendar.YEAR, intYr);
		c.add(Calendar.MONTH, intMnth);
		c.add(Calendar.DATE, intDate);
		c.add(Calendar.HOUR, intHr);
		c.add(Calendar.MINUTE, intMin);
		c.add(Calendar.SECOND, intSec);
		
		Date currentDatePlus = c.getTime();
		
		String newDate = dateFormat.format(currentDatePlus);
		return newDate;
	}
}
