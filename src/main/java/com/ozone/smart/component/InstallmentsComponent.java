package com.ozone.smart.component;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class InstallmentsComponent {
	
//	private PaymentSchedule ps = new PaymentSchedule();
//	private String strMsg = "";	

	double dblLoanamnt = 0;
	double dblIntrate = 0;
	int Noinst = 0;
	double dblDownpmnt = 0;
	double dblInsfee = 0;
	double dblTrckfee = 0;
	
	double dblInstallment = 0;	
	double Pv = 0;
	double rt = 0;
	double n = 0;
	double k = 0;
	double Tn = 0;
	double Tncnt = 0;
	double dblIntamount = 0;
	double dblPrincipal = 0;
	double dblRunningamnt = 0;
	double dbl1rt = 0;
	double intDays = 7;
	double intNumDays = 0;
	double chk1 = 0;
	double chk2 = 0;
	
	int intInstallment = 0;
	String strInstallment = "";
	
	public String ewi(String rate, String strLoanamnt, String strNoinst, 
			String strDownpmnt, String strInsfee, String strTrckfee,
			String strPaymode) {
		
			
		dblLoanamnt	= Double.parseDouble(strLoanamnt);
		dblIntrate = Double.parseDouble(rate);
		Noinst = Integer.parseInt(strNoinst);
		Noinst = Integer.parseInt(strNoinst);
		dblDownpmnt = Double.parseDouble(strDownpmnt);
		dblInsfee = Double.parseDouble(strInsfee);
		dblTrckfee = Double.parseDouble(strTrckfee);
	
		Pv = -(dblLoanamnt + dblInsfee - dblDownpmnt);
		
//		pv * rate / (1 - Math.pow(1 + rate, -nper))
		
		Tn = Noinst;
		
		Pv = -(dblLoanamnt + dblInsfee - dblDownpmnt);
		
//		pv * rate / (1 - Math.pow(1 + rate, -nper))
		
		Tn = Noinst;
		
		if (strPaymode.equals("Weekly")) {
			rt = dblIntrate / (52 * 100);
			rt = dblIntrate / (52 * 100);
		} else if (strPaymode.equals("Monthly")) {
			rt = dblIntrate / (12 * 100);
			rt = dblIntrate / (12 * 100);
		}
		
		dblInstallment =  Math.abs(Pv * rt / (1 - Math.pow(1 + rt, -Tn)));
		 long roundedValue = (long) Math.ceil(dblInstallment);
		
		//Correct new value by rounding up
//		BigDecimal bd = new BigDecimal(dblInstallment);
//		bd = bd.setScale(-3, BigDecimal.ROUND_UP);
//		dblInstallment = bd.doubleValue();
		
//		double stampDuty = dblLoanamnt *0.01;
//		
//		double sd = (stampDuty + dblTrckfee)/ Noinst;
//
//		double result = sd+dblInstallment;

//		intInstallment = (int) dblInstallment;	

//		BigDecimal bbd = new BigDecimal(intInstallment);
//		bbd = bbd.setScale(-3, BigDecimal.ROUND_UP);
//		double ewi = bbd.doubleValue();
		strInstallment = Double.toString(roundedValue);
//		BigDecimal bd = new BigDecimal(dblInstallment);
//		bd = bd.setScale(-3, BigDecimal.ROUND_UP);
//		dblInstallment = bd.doubleValue();
		
//		double stampDuty = dblLoanamnt *0.01;
//		
//		double sd = (stampDuty + dblTrckfee)/ Noinst;
//
//		double result = sd+dblInstallment;

//		intInstallment = (int) dblInstallment;	

//		BigDecimal bbd = new BigDecimal(intInstallment);
//		bbd = bbd.setScale(-3, BigDecimal.ROUND_UP);
//		double ewi = bbd.doubleValue();
		strInstallment = Double.toString(roundedValue);
		return strInstallment;	
	}	
}