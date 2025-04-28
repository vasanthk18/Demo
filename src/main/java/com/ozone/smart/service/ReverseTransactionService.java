package com.ozone.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.ReverseTransactionDto;
import com.ozone.smart.entity.DownPayment;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.repository.DownPaymentRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class ReverseTransactionService {
	
	@Autowired
	private DownPaymentRepo dpRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;

	public Response<String> fetchReceiptDetails(String receipt) {
		
		String strReceiptno = "";
		String strTrantype = "";
		String strProposalno = "";
		String strAmount = "";
		String strVehicle = "";
		String strPaymode = "";
		String strTransref = "";
		String strTransdate = "";
		String strRevflag = "";
		
		boolean blnRevflag = false;
				
		String strMsg = "";
		String strQuery = "";
		
		strReceiptno = receipt;
		
		Response<String> response = new Response<>();
		
		if (strReceiptno == null || strReceiptno.length() == 0) {			
			strMsg =  "Please select enter receipt no";
		} else {
			
			if (strReceiptno.substring(0, 2).equals("DP")) {
				/*strQuery = "From DownPayment where receiptno = '" + strReceiptno + "' and " + 
						"to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') = CURRENT_DATE";
				*/
//				strQuery = "From DownPayment where receiptno = '" + strReceiptno + "'";
				
				try {
					List<DownPayment> downp = dpRepo.findBydownreceipt(strReceiptno);
					if (downp.size() > 0) {
						for (DownPayment dp:downp) {
							strTrantype = dp.getTransactiontype();
							strProposalno = dp.getProposalno();
							strAmount = dp.getAmount();
							strPaymode = dp.getPaymentmode();
							strVehicle = "";
							strTransref = dp.getTransactionref();
							strTransdate = dp.getPaymentdate();
							blnRevflag = dp.getRevflag();
						}
						strTransdate = strTransdate.substring(0, 2) + "/" + strTransdate.substring(2, 4) + "/" + strTransdate.substring(4, 8);
						if (blnRevflag == true) {
							strRevflag = "REVERSED";
						} else {
							strRevflag = "";
						}
					}
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			} else if (strReceiptno.substring(0, 2).equals("IN")) {
				/*strQuery = "From WeeklyInstallment WHERE receiptno = '" + strReceiptno + "' and " +
						"to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') = CURRENT_DATE";
				*/
//				strQuery = "From WeeklyInstallment WHERE receiptno = '" + strReceiptno + "'";
				
				try {
					List<WeeklyInstallment> weekly = weeklyInstRepo.findByweekreceipt(strReceiptno);
					
					if (weekly.size() > 0) {
						for (WeeklyInstallment wi:weekly) {
							strTrantype = wi.getTransactiontype();
							strProposalno = wi.getProposalno();
							strAmount = wi.getAmount();
							strPaymode = wi.getPaymentmode();
							strVehicle = wi.getVehicleregno();
							strTransref = wi.getTransactionref();
							strTransdate = wi.getPaymentdate();
							blnRevflag = wi.getRevflag();
						}
						strTransdate = strTransdate.substring(0, 2) + "/" + strTransdate.substring(2, 4) + "/" + strTransdate.substring(4, 8);
						if (blnRevflag == true) {
							strRevflag = "REVERSED";
						} else {
							strRevflag = "";
						}
					}
					
				} catch (Exception e) {	System.out.println(e.getLocalizedMessage());}
			}
			
			response.setData(strTrantype + "|" + strProposalno + "|" + strAmount + "|" + strPaymode + "|" + strVehicle + "|" + strTransref + "|" + strTransdate + "|" + strRevflag);
		}
			return response;
	}

	public Response<String> updateTnx(ReverseTransactionDto reverseTransDto) {
		
		String strRequestdatetime = "";
		
		String strQuery = "";
		String strReceiptno = "";
		String strProposalno = "";
		String strTrantype = "";
		String strVehicle = "";
		String strPaymode = "";
		String strAmount = "";
		String strTransref = "";
		String strLoginuser = "";
		
		int intUser = 0;
		
		strReceiptno =  reverseTransDto.getReceiptno();
		strProposalno = reverseTransDto.getProposal();
		strAmount =  reverseTransDto.getAmount();
		strTrantype =  reverseTransDto.getTtype();
		strVehicle =  reverseTransDto.getVehicle();
		strPaymode =  reverseTransDto.getMode();
		strTransref =  reverseTransDto.getRef();
		strLoginuser = reverseTransDto.getUsername();
					
		Response<String> response = new Response<>();
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
									
			try {
				if (strTrantype.equals("EWI")) {
//					strQuery = "UPDATE WeeklyInstallment set revflag = true, revuser = '" + strLoginuser + "', revdatetime ='" + strRequestdatetime + "' " +
//							"WHERE receiptno = '" + strReceiptno + "' and proposalno = '" + strProposalno + "' and vehicleregno = '" + strVehicle + "' and " +
//							"transactionref = '" + strTransref + "' and amount = '" + strAmount + "' and paymentmode = '" + strPaymode + "'";
//					
					intUser = weeklyInstRepo.updateWeeklyInstallment(strLoginuser, strRequestdatetime, strReceiptno, strProposalno, strVehicle, strTransref, strAmount, strPaymode) ;
					if (intUser == 1) {response.setData(strReceiptno + " reversed successfully");}
				} else if (strTrantype.equals("DP")) {
//					strQuery = "UPDATE DownPayment set revflag = true, revuser = '" + strLoginuser + "', revdatetime ='" + strRequestdatetime + "' " +
//							"WHERE receiptno = '" + strReceiptno + "' and proposalno = '" + strProposalno + "' and transactionref = '" + strTransref + "' and " +
//							"amount = '" + strAmount + "' and paymentmode = '" + strPaymode + "'";
//					
					intUser = dpRepo.updateDownPayment(strLoginuser, strRequestdatetime, strReceiptno, strProposalno, strTransref, strAmount, strPaymode);
					if (intUser == 1) {response.setData(strReceiptno + " reversed successfully");}
				}
			} catch (Exception e) {
				Throwable th = e.getCause();
			    System.out.println("THROWABLE INFO: " + th.getCause().toString());			         
				response.setErrorMsg(th.getCause().toString());					
			}
		return response;
	}

}
