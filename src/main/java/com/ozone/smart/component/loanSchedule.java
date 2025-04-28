package com.ozone.smart.component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
//import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.util.GetFutureDate;


@Component
public class loanSchedule {
	
	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;
	
	private PaymentSchedule ps = new PaymentSchedule();
	private String strMsg = "";	

	double dblLoanamnt = 0;
	double dblIntrate = 0;
	double dblNoinst = 0;
	double dblDownpmnt = 0;
	double dblTrffee = 0;
	double dblInsfee = 0;
	double dblTrckfee = 0;
	double dblStampduty =0;
	
	double dbloldintstallment = 0;	
	double Pv = 0;
	double Mv = 0;
	double rt = 0;
	double n = 0;
	double k = 0;
	double Tn = 0;
	double Tncnt = 0;
	double dblIntamount = 0;
	double dbloldPrincipal = 0;
	double dblnewPrincipal = 0;
	double dblRunningamnt = 0;
	double dbl1rt = 0;
	double intDays = 7;
	double intNumDays = 0;
	double chk1 = 0;
	double chk2 = 0;
	double othercost = 0;
	double dblnewInstallmentamt = 0;
	
//	int intInstallment = 0;
	int intNewinstallment = 0;
	int intDate = 0;
	int kn = 0;
	
	String strBalance = "";
	String strInstallment = "";
	String strInterestamnt = "";
	String strPaymentdate = "";
	String strPrincipal = "";
	String strRunningamount = "";
	String strScheduleno = "";
	String strLoankey = "";
	String strNewInstallment="";
	
	public String schedule(String strAgreementno, String strPropno, String rate, String strLoanamnt, String strNoinst, 
			String strDownpmnt, String strTrffee, String strInsfee, String strTrckfee, String strPaymode) {
		/*
		 * EMI formula
		 * Pv*rt*(Pow(1+rt,n)) / (Pow(1+rt,n) - 1)
		 * 
		 * IPMT formula
		 * Pv*rt*(Pow(rt+1,Tn+1)-Pow(rt+1,n)) / ((rt+1)*(Pow(rt+1,Tn)-1))
		 */
			
		dblLoanamnt	= Double.parseDouble(strLoanamnt);
		dblIntrate = Double.parseDouble(rate);
		dblNoinst = Double.parseDouble(strNoinst);
		dblDownpmnt = Double.parseDouble(strDownpmnt);
		dblTrffee = Double.parseDouble(strTrffee);
		dblInsfee = Double.parseDouble(strInsfee);
		dblTrckfee = Double.parseDouble(strTrckfee);
		
		dblStampduty =dblLoanamnt*0.01;
		
		othercost = (dblStampduty + dblTrckfee)/dblNoinst;//calculating other cost
		System.out.println("OTHER COST = "+othercost);
		
		/*
		 * try { InitialContext ic = new InitialContext(); libraryBean =
		 * (easybodaPersistentBeanRemote)ic.lookup(
		 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
		 * ); } catch (NamingException e) { e.printStackTrace(); }
		 */
//		  Session em = (Session)request.getAttribute("sh");
//		  easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
		 
		
		GetFutureDate gfd = new GetFutureDate();
		System.out.println("loan amount ::> " +dblLoanamnt);
//		Pv = dblLoanamnt - dblDownpmnt + dblTrffee + dblInsfee + dblTrckfee;
//		Mv = dblLoanamnt - dblDownpmnt + dblStampduty + dblInsfee;
		
		
		Mv = dblLoanamnt - dblDownpmnt + dblInsfee;//AMT FINANCED (B2-B3+B5)(ASSESTCOST-MARGINMONEY+INSURANCE)
		//Mv is Amt financed
		
		System.out.println("dblLoanamnt == "+dblLoanamnt);
		System.out.println("dblDownpmnt == "+dblDownpmnt);
		System.out.println("dblTrffee == "+dblTrffee);
		System.out.println("dblInsfee == "+dblInsfee);
		System.out.println("dblTrckfee == "+dblTrckfee);
		System.out.println("dblStampduty == "+dblStampduty);
		System.out.println("Mv(Amt financed)= "+Mv);
		

		
		 InstallmentsComponent component = new InstallmentsComponent();
		 strInstallment= component.ewi(rate, strLoanamnt, strNoinst, strDownpmnt, strInsfee, strTrckfee, strPaymode);
		System.out.println("check strInstallment :: "+strInstallment);
		if (strPaymode.equals("Weekly")) {
			rt = (dblIntrate / 100) / 52;
		} else if (strPaymode.equals("Monthly")) {
			rt = (dblIntrate / 100) / 12;
		}
		
		dbl1rt = 1 + rt;
		Tn = dblNoinst;
		Tncnt = Tn + 1;
//				
//
//	dblInstallment = Mv * rt * (Math.pow(dbl1rt,Tn)) / (Math.pow(dbl1rt,Tn) - 1);
//		
//		//Correct new value by rounding up
//		BigDecimal bd = new BigDecimal(dblInstallment);
//		bd = bd.setScale(-3, BigDecimal.ROUND_UP);
//		dblInstallment = bd.doubleValue();
//		intInstallment = (int) dblInstallment;
//		
		dbloldintstallment= Double.parseDouble(strInstallment);//old installment amount
		 
		 dblnewInstallmentamt = Math.round((dbloldintstallment + othercost) / 1000) * 1000;//new installment amount
		 intNewinstallment =(int) dblnewInstallmentamt;

		dblRunningamnt = Mv + dblTrckfee + dblStampduty ; //RUNNING AMOUNT =H15+F12+B12+B4(AMOUNTFINANCED+TRACKERCOST+STAMPDUTY)
		System.out.println("dblRunningamnt ()RUNNING AMT = "+dblRunningamnt);
		LocalDate today = LocalDate.now();
        LocalDate nextWednesday;

        if (strPaymode.equals("Weekly")) {
            if (today.getDayOfWeek() == DayOfWeek.FRIDAY ||
                today.getDayOfWeek() == DayOfWeek.SATURDAY || 
                today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                nextWednesday = today.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).plusWeeks(1);
            }
            else if(today.getDayOfWeek() == DayOfWeek.MONDAY || today.getDayOfWeek() == DayOfWeek.TUESDAY) {
                nextWednesday = today.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).plusWeeks(1);
            }
            else {
                nextWednesday = today.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
            }
        } else {
            nextWednesday = today.plusMonths(1).withDayOfMonth(1); // Placeholder for monthly payment logic
        }

		for (n = 1; n < Tncnt; n++) {

			dblIntamount = Mv * rt * (Math.pow(dbl1rt,Tn+1) - Math.pow(dbl1rt,n)) / (dbl1rt * (Math.pow(dbl1rt,Tn) - 1));
			
			//Correct new value by rounding up
			dblIntamount = Math.round(dblIntamount * 100) / 100D;
			
			
			System.out.println("OLD INSTALLMENT(dbloldintstallment) = "+dbloldintstallment);

			
			dbloldPrincipal = dbloldintstallment - dblIntamount;//calculating old principal amount
			System.out.println("OLD PRINCIPAL(dbloldPrincipal) = "+dbloldPrincipal);

			
			
			System.out.println("NEW PRINCIPAL(dblnewPrincipal) = "+dblnewPrincipal);

			if (dblnewInstallmentamt < dblRunningamnt) {
				dblnewPrincipal = dbloldPrincipal + othercost;//calculating new principal amount
//				dblPrincipal = dblInstallment - dblIntamount;
			}	else {
				dblnewPrincipal = dblRunningamnt;
			}
			
			dblRunningamnt = dblRunningamnt - dblnewPrincipal;
			
			dblRunningamnt = Math.round(dblRunningamnt * 100) / 100D;
			dblnewPrincipal = Math.round(dblnewPrincipal * 100) / 100D;
			kn = (int) n;
			
			strBalance = Double.toString(dblRunningamnt);
			strNewInstallment = Integer.toString(intNewinstallment);
			strInterestamnt = Double.toString(dblIntamount);
			strPaymentdate = Double.toString(dblRunningamnt);
			strPrincipal = Double.toString(dblnewPrincipal);
			strRunningamount = Double.toString(dblRunningamnt);
			strScheduleno = Integer.toString(kn);
			
			//k = n-1;
			k = n;
			kn = (int) n;
			intNumDays = k * intDays;
			intDate = (int) (intNumDays);
			
			if (strPaymode.equals("Weekly")) { 
				strPaymentdate = nextWednesday.plusWeeks((int) k - 1).toString();
				System.out.println(strPaymentdate);
//				strPaymentdate = gfd.newDate(0, 0, intDate, 0, 0, 0);	
			} else if (strPaymode.equals("Monthly")) {
				strPaymentdate = gfd.newDate(0, kn, 0, 0, 0, 0);
				System.out.println(strPaymentdate);
			}
			// Parse the original date string to a Date object
//            Date date = originalFormat.parse(strPaymentDate);
            
            // Define the new date format
			  SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
	            
	            // Parse the string into a Date object
	            Date date = null;
				try {
					date = originalFormat.parse(strPaymentdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            // Define the new date format
	            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
	            
	            // Format the Date object to the new format
	            String formattedDate = newFormat.format(date);
            
			strLoankey = strAgreementno + strScheduleno;
			ps.setScheduleno(strLoankey);
			ps.setLoanid(strPropno);
			ps.setPaymentdate(formattedDate);
			ps.setRunningamount(strRunningamount);
			ps.setInstallment(strNewInstallment);
			ps.setInterestamount(strInterestamnt);
			ps.setPrincipal(strPrincipal);
			ps.setBalance(strBalance);
			ps.setStatus("");
			ps.setSchedule(kn);
			
			try {
				paymentScheduleRepo.save(ps);
				strMsg = "Payment schedule : " + strAgreementno + " saved successfully.";
			} catch (Exception e) {
				e.printStackTrace();
				strMsg = "Payment schedule : " + strAgreementno + " Failed.";
			}	

		}			
		
		return strMsg;	
	}	
}