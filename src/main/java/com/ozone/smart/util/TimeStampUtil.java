package com.ozone.smart.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtil {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final DateFormat newdateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final DateFormat thiday = new SimpleDateFormat("dd");
	private static final DateFormat thismoth = new SimpleDateFormat("MMM");
	private static final DateFormat thisyear = new SimpleDateFormat("YYYY");
	private static final DateFormat toDay = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat newtoDay = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public String TimeStamp() {
		
		String newDateTimeStamp = dateFormat.format(new Date());		
		return newDateTimeStamp;	
	}
	
	public String newfmtTimeStamp() {
		
		String newfmtDateTimeStamp = newdateFormat.format(new Date());
		return newfmtDateTimeStamp;
	}
	
	public String newfmtDay() {
		
		String newfmtday = thiday.format(new Date());
		return newfmtday;
	}

	public String newfmtMonth() {
		
		String newfmtmonth = thismoth.format(new Date());
		return newfmtmonth;
	}
	
	public String newfmtYear() {
		
		String newfmtYear = thisyear.format(new Date());
		return newfmtYear;
	}
	
	public String standardDate() {
		
		String standarddate = toDay.format(new Date());
		return standarddate;
	}
	
	public String newstandardDate() {
		
		String standarddate = newtoDay.format(new Date());
		return standarddate;
	}

}
