package com.ozone.smart.component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.entity.vwProposal;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.VmProposalRepo;
import com.ozone.smart.repository.WeeklyInstRepo;

@Component
public class TotalDue {
	
	@Autowired
	private VmProposalRepo vmProposalRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;
	
	
	@Autowired
	private PaymentScheduleRepo psRepo;
	
	private Proposal prop = new Proposal();
	private PaymentSchedule ps = new PaymentSchedule();
	private String[] strMsgArray;
	private String strMsg = "";

	int intPenalty = 0;
	int intTotalPenalty = 0;
	
	int intCounter = 0;
	int intAgreementserial = 0;
	
	double dblWeeks = 0;
	double dbldiff = 0;
	double dblOverdue = 0;
	double dblEwi = 0;
	
	int intTotalduewks = 0;
	int intAmount = 0;
	int intEwi = 0;
	int intTotaldue = 0;
	int intTotalrecv = 0;
	int intOverdue = 0;
	
	int intNoofinst = 0;
	int intLoanamnt = 0;
	
	int intGtotaldue = 0;
	int intGtotaloverdue = 0;
	
	double dblGtotaldue = 0;
	double dblGtotaloverdue = 0;
	double dblPercentage = 0;
	
	String strQuery = "";
	String strQueryp = "";
	String strQuerys = "";
	String strBucketno = "";
	
	String strAgreement = "";
	String strPenalty = "";
	String strCustid = "";
	String strProposalno = "";
	String strVehicle = ""; 
	String strReleasedate = ""; 
	String strPaymentdate = "";
	String strLoanamnt = ""; 
	String strEwi = ""; 
	String strNoofinst = ""; 
	String strPaymode = "";
	String strCustomername = ""; 
	String strProfile = ""; 
	String strWeeks = "";
	String strAmount = "";
	String strTotaldue = "";
	String strTotalrecv = "";
	String strOverdue = "";
	String strLnamount = "";
	
	String strnPaymentdate = "";
	
	LocalDate paymentdate;
	Date firstDate;
	Date secondDate;
	
	public String getTotalDue(String strAgree) {
	
		
		/*
		 * try { InitialContext ic = new InitialContext(); libraryBean =
		 * (easybodaPersistentBeanRemote)ic.lookup(
		 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
		 * ); } catch (NamingException e) { e.printStackTrace(); }
		 */
		
		 			
		
		/*
		 * Session em = (Session)request.getAttribute("sh");
		 * easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
		 */
		
		LocalDate today = LocalDate.now();
		
//		strQuery = "From vwProposal where agreementno = '" + strAgreement + "'";		
		List<vwProposal> vwproposal = vmProposalRepo.findByagreementno(strAgree) ;
						
		for (vwProposal vwprop:vwproposal) {
			strCustid = vwprop.getCustomerid();
			strProposalno = vwprop.getProposalno();
			strAgreement = vwprop.getAgreementno();
			strVehicle = vwprop.getVehicleregno();
			strReleasedate = vwprop.getDateofrelease();
			strLoanamnt = vwprop.getLoanamount();
			strEwi = vwprop.getEwi();
			strNoofinst = vwprop.getNoofinstallments();
			strPaymode = vwprop.getPaymentmode();
			strTotalrecv = "";						
			strOverdue = "";
			strTotaldue = "";
			strWeeks = "";							
			strPenalty = "";
			
			intPenalty = 0;
			intTotalPenalty = 0;
										
			if (strReleasedate == null || strReleasedate.length() == 0) {
				//some stuff axed
			} else {
				
				strLnamount = strLoanamnt.replace(",", "");								
				intNoofinst = Integer.parseInt(strNoofinst);
				intLoanamnt = Integer.parseInt(strLnamount);
				
				//Check todays date against last date of loan
				//SELECT * FROM public.tblpaymentschedule where loanid = 'VBYB100002' and schedule in
				//		(select max(schedule) from public.tblpaymentschedule where loanid = 'VBYB100002');
//				strQuerys = "From PaymentSchedule where loanid = '" + strProposalno + "' and schedule in " + 
//						"(select max(schedule) From PaymentSchedule where loanid = '" + strProposalno + "')";
				List<PaymentSchedule> paysch = psRepo.findLatestPaymentScheduleByLoanId(strProposalno);
				
				for (PaymentSchedule ps:paysch) {
					strPaymentdate = ps.getPaymentdate(); //23/10/2020 >> 2020-07-04
				}
				strnPaymentdate = strPaymentdate.substring(6, 10) + "-" + strPaymentdate.substring(3, 5) + "-" + strPaymentdate.substring(0, 2);				
				paymentdate = LocalDate.parse(strnPaymentdate);
				
				if (paymentdate.compareTo(today) < 0) {
					secondDate = Date.valueOf(paymentdate);	
				} else {
					secondDate = Date.valueOf(today);
				}
				
				firstDate = Date.valueOf(strReleasedate);
				//Date secondDate = Date.valueOf(today);				
				
				long timediff = secondDate.getTime() - firstDate.getTime();
				long diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
				dbldiff = (double) diff;
				
				if (strPaymode.equals("Weekly") || strPaymode.equals("weekly")) {								
					dblWeeks = dbldiff / 7;
					dblWeeks = Math.round(dblWeeks * 100) / 100D;
				} else {
					dblWeeks = dbldiff / 30;
					dblWeeks = Math.round(dblWeeks * 100) / 100D;
				}
				
				intTotalduewks = (int) Math.floor(dblWeeks);
				
				double dblEwi = Double.parseDouble(strEwi);
				
				intEwi = (int) dblEwi;
				intTotaldue = intEwi * intTotalduewks;
				
				//total due must include penalties incurred
				intPenalty = 0;
				intTotalPenalty = 0;
				
//				strQueryp = "From Penalty where loanid = '" + strProposalno + "'";
				List<Penalty> penalty = penaltyRepo.findByloanid(strProposalno) ;
				
				for (Penalty penal:penalty) {
					strPenalty = penal.getPenalty();
					intPenalty = Integer.parseInt(strPenalty);
					intTotalPenalty += intPenalty;
				}
				
				intTotaldue += intTotalPenalty;
				//***************************************************											
				strTotaldue = Integer.toString(intTotaldue);
																	
				try {
//					strQuery = "From WeeklyInstallment where proposalno = '" + strProposalno + "' and revflag = false";								
					List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findByRevFlagAndProposalNo(strProposalno);
					
					intTotalrecv = 0;
					intAmount = 0;
					
					for (WeeklyInstallment winst:weeklyinst) {
						strAmount = winst.getAmount();
						intAmount = Integer.parseInt(strAmount);
						intTotalrecv += intAmount;
					}								
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				strTotalrecv = Integer.toString(intTotalrecv);
				intOverdue = intTotaldue - intTotalrecv;							
				strOverdue = Integer.toString(intOverdue);
				
				intGtotaldue += intTotaldue;
				intGtotaloverdue += intOverdue;
				
				dblOverdue = Integer.valueOf(intOverdue);
				dblEwi = Integer.valueOf(intEwi);
				
				dblWeeks = dblOverdue / dblEwi;
				dblWeeks = Math.round(dblWeeks * 100) / 100D;
				strWeeks = Double.toString(dblWeeks);
			}
			
			strMsg = strOverdue;
		}
		
		return strMsg;	
	}	
}