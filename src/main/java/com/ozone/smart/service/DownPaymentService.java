package com.ozone.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.DownPayment;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.repository.DownPaymentRepo;

@Service
public class DownPaymentService {
	
	@Autowired
	private DownPaymentRepo dpRepo;

	public Response<String> viewDp(String propNo, String flag) {
		
		Response<String> response = new Response<>();
		
		String strProposalno = "";	
		String strMsg = "";
		String strFlag = "";
		String strVehicle = "";
		String strAmount = "";
		String strDownamount = "";
		String strDpamountpaid = "";
		String strCqcremarks = "";
		String strCqc = "";
		String strCoappremarks = "";
		String strCoapp = "";
		String strsrremarks="";
		
		boolean blnCqc = false;
		
		String strDPStatus = "";
		
		double dblDPamount = 0;
		double dblPropamount = 0;
		double dblTotaldp = 0;
		double dblAmountdiff = 0;
					
		strProposalno = propNo;
		strFlag = flag;
		strProposalno = strProposalno.trim();		
		strFlag = strFlag.trim();
		
		if (strProposalno == null || strProposalno.length() == 0) {			
			response.setData("Select proposal no");
		} else {
			try {
				List<Proposal> proposal = dpRepo.findByProposalNoWithFlag(strProposalno, strFlag);
				
				for (Proposal prop:proposal) {
					strVehicle = prop.getVehicleregno();
					System.out.println("Vehicle registernumber "+strVehicle);
					strVehicle = strVehicle.replace("|", " :: ");
					strAmount = prop.getAmount();
					strDownamount = prop.getDownamount();					
					strCqcremarks = prop.getCqcremarks();
					blnCqc = prop.getCqc();
					strCoappremarks = prop.getCoremarks();
					strCoapp = prop.getCoapproval();
					strsrremarks=prop.getSrremarks();				}
				
				if (blnCqc == true) {strCqc = "Recommended";} else {strCqc = "Not Recommended";}
				
				//Get dp on downpayment and compare with prop amount
				
				 //revflag added on 23.05.2020
				
				try {
					List<DownPayment> downpayment = dpRepo.findByProposalNoAndRevFlagFalse(strProposalno);
					
					for (DownPayment dp:downpayment) {
						strDpamountpaid = dp.getAmount();
						dblDPamount = Double.parseDouble(strDpamountpaid);
						dblTotaldp += dblDPamount;
					}

					if (strDownamount.contains(",")) {strDownamount = strDownamount.replace(",", "");}
					dblPropamount = Double.parseDouble(strDownamount);
					
					if (dblTotaldp < dblPropamount) {
						dblAmountdiff = dblPropamount - dblDPamount ;
						strDPStatus = "Down Payment Status: Pending by UGX " + dblAmountdiff;
					} else if (dblTotaldp == dblPropamount) {
						strDPStatus = "Down Payment Status: Complete";
					} else if (dblTotaldp > dblPropamount) {
						dblAmountdiff = dblDPamount - dblPropamount;
						strDPStatus = "Down Payment Status: Overpaid by UGX " + dblAmountdiff;
					} else {
						strDPStatus = "";
					}
					
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
					response.setData("Error retrieving customer id: " + strProposalno);
				}
				
				response.setData(strVehicle + "|" + strAmount + "|" + strDownamount + "|" + strDPStatus + "|" + strCqc + "|"
						+ strCqcremarks + "|" + strCoapp + "|" + strCoappremarks + "|" + strsrremarks);
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setData("Error retrieving customer id: " + strProposalno);
			}
		}	
		return response;
	}

}
