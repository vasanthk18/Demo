package com.ozone3.smart.pojo;

public class conversionTool {
	
	public static final String[] ones = { 
			  "", " one", " two", " three", " four", 
			  " five", " six", " seven", " eight", 
			  " nine", " ten", " eleven", " twelve", 
			  " thirteen", " fourteen", " fifteen", 
			  " sixteen", " seventeen", " eighteen", 
			  " nineteen"
			};
			 
	public static final String[] tens = {
			  "",          // 0
			  " ten",          // 1
			  " twenty",    // 2
			  " thirty",    // 3
			  " forty",     // 4
			  " fifty",     // 5
			  " sixty",     // 6
			  " seventy",   // 7
			  " eighty",    // 8
			  " ninety"     // 9
			};
	
	public static final String[] special = {
			"",
			" thousand",
			" million",
			" billion",
			" trillion",
			" quadrillion",
			" quintillion"
	};
		
	public static String convertNumbers(int number) {
		String current;
	
		if (number % 100 < 20) {
			current = ones[number % 100];
			number /= 100;
		} else {
			current = ones[number % 10];
			number /= 10;
			
			current = tens[number % 10] + current;
			number /= 10;
		}
		
		if (number == 0) return current;
		return ones[number] + " hundred" + current;
		
	}	

	public String convert(int number) {
		if(number ==0) {return "zero";}
		
		String prefix = "";
		
		if (number < 0) {
			number = -number;
			prefix ="negative";
		}
		
		String current = "";
		int place = 0;
		
		do {
			int n = number % 1000;
			if (n !=0) {
				String s = convertNumbers(n);
				current = s + special[place] + current;
			}
			place++;
			number/= 1000;
		} while (number > 0);
		
		return (prefix + current).trim();
		
	}


}
