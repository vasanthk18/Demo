package com.ozone.smart.component;

import org.springframework.stereotype.Component;

@Component
public class InsuranceFeeComponent {
	
//	public static void main(String[] args) {
//		
//	}
	
	public String getFee(String assetCost, String depreciation, String rate, 
			String firstValue, String secondValue, String thirdValue, String tenure, String paymode) {
			
		String fee = "";
		double insAmntfirstYr = 0;
		double insAmntsecondYr = 0;
		double insAmntthirdYr = 0;
		double totalInsAmnt = 0;
		double amntIns2ndYr = 0;
		double amntIns3ndYr = 0;
		int intTenure = 0;
		double intAssetCost = 0;
		double intDepreciation = 0;
		double intRate = 0;
		int intFirstValue = 0;
		double intSecondValue = 0;
		int intThirdValue = 0;
		


		intAssetCost = Integer.parseInt(assetCost.trim());
		intDepreciation = Integer.parseInt(depreciation.trim());
		intRate = Integer.parseInt(rate.trim());
		intFirstValue = Integer.parseInt(firstValue.trim());
		intSecondValue = Integer.parseInt(secondValue.trim());
		intThirdValue = Integer.parseInt(thirdValue.trim());
		intTenure = Integer.parseInt(tenure.trim());	
		
		intRate = intRate / 10000000;
		intSecondValue = intSecondValue / 100;
		intDepreciation = intDepreciation / 100;
		
		insAmntfirstYr = (((intAssetCost * intRate) + intFirstValue) * intSecondValue) + intThirdValue;
				
		if (paymode.equals("Weekly")) {
			if (intTenure > 52) {
//				amntIns2ndYr = intAssetCost * intDepreciation;
				amntIns2ndYr = intAssetCost;
//				amntIns2ndYr = intAssetCost * intDepreciation;
				amntIns2ndYr = intAssetCost;
			} else {
				amntIns2ndYr = 0;
			}
			
			if (intTenure > 104) {
//				amntIns3ndYr = amntIns2ndYr * intDepreciation;
				amntIns3ndYr = amntIns2ndYr;
//				amntIns3ndYr = amntIns2ndYr * intDepreciation;
				amntIns3ndYr = amntIns2ndYr;
			} else {
				amntIns3ndYr = 0;
			}
		} else if (paymode.equals("Monthly")) {
			if (intTenure > 12) {
//				amntIns2ndYr = intAssetCost * intDepreciation;
				amntIns2ndYr = intAssetCost;
//				amntIns2ndYr = intAssetCost * intDepreciation;
				amntIns2ndYr = intAssetCost;
			} else {
				amntIns2ndYr = 0;
			}
			
			if (intTenure > 24) {
//				amntIns3ndYr = amntIns2ndYr * intDepreciation;
				amntIns3ndYr = amntIns2ndYr;
//				amntIns3ndYr = amntIns2ndYr * intDepreciation;
				amntIns3ndYr = amntIns2ndYr;
			} else {
				amntIns3ndYr = 0;
			}
		}
		
		if (amntIns2ndYr > 0) {
			insAmntsecondYr = (((amntIns2ndYr * intRate) + intFirstValue) * intSecondValue) + intThirdValue;
		}
		
		if (amntIns3ndYr > 0) {
			insAmntthirdYr = (((amntIns3ndYr * intRate) + intFirstValue) * intSecondValue) + intThirdValue;
		}
		
		totalInsAmnt = insAmntfirstYr + insAmntsecondYr + insAmntthirdYr;
		totalInsAmnt = Math.round(totalInsAmnt);
		
		/*
		long lngAmt = Math.round(totalInsAmnt);		
		fee = Long.toString(lngAmt);
		*/
		
		fee = Double.toString(totalInsAmnt);
		
		return fee;
	}

}