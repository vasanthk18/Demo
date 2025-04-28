package com.ozone.smart.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.LoanPayments;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.repository.LoanPaymentRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.util.TimeStampUtil;

import jakarta.persistence.EntityManager;

@Component
public class processPenalty {
		
	String strnToday = "";
	String strnewToday = "";	
	String strQuery = "";
	String strMsg = "";
	
	String strProposalno = "";
	String strPaymenttype = "PENALTY";	
	String strPenalloanid = "";
	String strPenalloanscheduleno = "";
	String strPenalloaninstallment = "";
	String strPenalloanpaymentdate = "";
	String strPenalamount = "";
	String strPenaltranref = "";
	String strPenalpaymentdate = "";
	String strPenalstatus = "";
	
	Object wklyinsts[];
	Object lnpayments[];
	
	double dblpenamount = 0;
	double dblinstallment = 0;
	double dblavailableamount = 0;
	double dbltotalpaid = 0;
	double dbltotalcleared = 0;
	double dblamount = 0;
	double dblcumamount = 0;
	double dblremamount = 0;
	
	int intUser = 0;
	int intScehdule = 0;
	
	String strTotalpaid = "";
	String strAvailableamount = "";
	String strTotalcleared = "";
	
	Date dtnToday;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;
	
	@Autowired
	private LoanPaymentRepo loanPaymentRepo;
		
	public List<LoanPayments> CheckPenalty(String Proposalno) {
				
		strProposalno = Proposalno;		
		TimeStampUtil gts = new TimeStampUtil();
		strnToday = gts.standardDate();
		
		//Process  penalties
//		strQuery = "From Penalty where (status = '' or status is null) and loanid = '" + strProposalno + "'" +
//				" order by loanscheduleno";
		List<Penalty> penal = penaltyRepo.orderByLoanSchedule(strProposalno);
		 List<LoanPayments> loanPaymentsBatch = new ArrayList<>();
		 
		if (penal.size() !=0) {
			try {
				for (Penalty pen:penal) {
					strPenalloanid = pen.getLoanid();
					strPenalloanscheduleno = pen.getLoanscheduleno();
					strPenalloaninstallment = pen.getLoaninstallment();
					strPenalloanpaymentdate = pen.getLoanpaymentdate();
					strPenalamount = pen.getPenalty();
					strPenalloanscheduleno = strPenalloanscheduleno.trim();
					System.out.println("Inside check penalty "+ strPenalloanscheduleno);
					intScehdule = Integer.parseInt(strPenalloanscheduleno.substring(8));
					dblpenamount = Double.parseDouble(strPenalamount);
				
//					strQuery = "select sum(cast(amount as double)) as totalpaid From " +
//							"WeeklyInstallment where proposalno = '" + strProposalno + "' and revflag = false group by proposalno";
					Double wklyinst =  weeklyInstRepo.getTotalPaidAmountByProposal(Proposalno);
					System.out.println("Total Weekly installment "+ wklyinst);
					if (wklyinst != null && wklyinst != 0) {
						System.out.println("Inside Check Penalty WeeklyInstallment");
//						wklyinsts = wklyinst.toArray();
							dbltotalpaid = wklyinst;
						System.out.println("Total paid amount "+ dbltotalpaid);
//						strQuery = "select sum(cast(amount as double)) as totalcleared From " +
//								"LoanPayments where loanid = '" + strProposalno + "'";
//						List<LoanPayments> lnpayment =  loanPaymentRepo.getTotalClearedAmountByProposal(Proposalno);
						Double loanpayment = loanPaymentRepo.getTotalClearedAmountByProposal(strProposalno);
						if (loanpayment != null && loanpayment > 0) {
						    dbltotalcleared = loanpayment;
						}
						System.out.println("TotalPaid"+dbltotalpaid);
						System.out.println("TotalCleared"+dbltotalcleared);
						
						dblavailableamount = dbltotalpaid - dbltotalcleared;
						System.out.println("Check penalty Availableamount"+dblavailableamount);					
						if ((dblavailableamount > dblpenamount) || (dblavailableamount == dblpenamount)) {
							
							System.out.println("Inside available amount greater then penamount");
							try {
								dtnToday = new SimpleDateFormat("dd/MM/yyyy").parse(strnToday);
								System.out.println("RunDate"+dtnToday);
							} catch (ParseException e1) {
								System.out.println(e1.getLocalizedMessage());
							}
							
							LoanPayments loanp = new LoanPayments();
							loanp.setSchedule(intScehdule);
							loanp.setScheduleno(strPenalloanscheduleno);
							loanp.setLoanid(strPenalloanid);
							loanp.setAmount(strPenalamount);
							loanp.setRundate(dtnToday);
							loanp.setPaymenttype(strPaymenttype);
							
							try {
								try {
									loanPaymentsBatch.add(loanp);
									for (LoanPayments loanPayment : loanPaymentsBatch) {
									    System.out.println("Process Penalty loan payment"+loanPayment.getSchedule() + loanPayment.getScheduleno() + loanPayment.getAmount() + loanPayment.getLoanid());
									}
//									loanPaymentRepo.save(loanp);
//									entityManager.flush();
//									entityManager.clear();
								}catch(Exception e) {
									e.printStackTrace();
								}
									
								System.out.println("Saved loan");
								//Flag payment schedule as paid	
								strPenalstatus = "PAID";
								strPenalpaymentdate = strnToday;
								strPenaltranref = strPenalloanscheduleno;
//								strQuery = "UPDATE Penalty set status = '" + strPenalstatus + "', penaltypaymentdate = '" + strPenalpaymentdate + "'," +
//										" penaltytranref = '" + strPenaltranref + "' where loanscheduleno = '" + strPenalloanscheduleno + "'" +
//										" and loanid = '" + strPenalloanid + "' and loanpaymentdate = '" + strPenalloanpaymentdate + "'";
//								intUser = libraryBean.updatePaymentSchedule(strQuery);
								
								intUser = penaltyRepo.updatePenalty(strPenalstatus, strPenalpaymentdate, strPenaltranref, strPenalloanscheduleno, strPenalloanid, strPenalloanpaymentdate);
								if (intUser != 0) {
									System.out.println("Penalty processed: " + strPenalloanscheduleno + strPenalloanpaymentdate + strPenalloanid);
								}
							} catch (Exception e) {
								System.out.println(e.getLocalizedMessage());
							}						
						} else if (dblavailableamount < dblpenamount) {} //Available amount cannot cover penalty											
					} 
					else {
						System.out.println("No Installment Paid");
					} //No installments paid
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
					
					
		} else {
			System.out.println("There are not panelties for " + strProposalno);
		}
		return loanPaymentsBatch;	
	}	
}