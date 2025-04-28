package com.ozone.smart.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class formatDigits {
	
	private static final DecimalFormat df = new DecimalFormat();
	private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	private static double dblNumber = 0;
	
//	public static void main(String[] args) {
//		
//	}
	
	public String digit(String value) {		
		df.setDecimalFormatSymbols(symbols);
		df.setGroupingSize(3);
		df.setMaximumFractionDigits(2);
		
		//dblNumber = Integer.parseInt(value);
		dblNumber = Double.parseDouble(value);
		return df.format(dblNumber);		
	}
}
