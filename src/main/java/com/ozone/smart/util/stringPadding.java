package com.ozone.smart.util;

public class stringPadding {
	public static String rightPad(String inputString, int requiredLength) {
		return String.format("%1$-" + requiredLength + "s", inputString);
	}
	
	public static String leftPad(String inputString, int requiredLength) {		
		return String.format("%1$" + requiredLength + "s", inputString);	
	}
	
	public static String leftPadZeroes(String inputString, int requiredLength) {		
		return String.format("%1$" + requiredLength + "s", inputString).replace(' ', '0');	
	}


}