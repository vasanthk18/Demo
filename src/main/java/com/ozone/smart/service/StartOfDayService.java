package com.ozone.smart.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.TotalDue;
import com.ozone.smart.component.processPenalty;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.entity.LoanPayments;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Sodrun;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.LoanPaymentRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.SodrunRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class StartOfDayService {
	
	@Autowired
	private SodrunRepo sodrunRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;
	
	@Autowired
	private LoanPaymentRepo loanPaymentRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;
	
	@Autowired
	private LoanParamRepo loanParamRepo;
	
	@Autowired
	private LoanEntryRepo loanEntryRepo;
	
	@Autowired
	private processPenalty pp;
	
	@Autowired
	private TotalDue totalDue;
	
//	 Scheduling method to run every day at midnight (UTC+3)
    @Scheduled(cron = "0 0 0 * * *", zone = "Africa/Kampala")
    public void scheduledStartOfDayRun() {
        // Get the current date in the required format
//        String todate = LocalDate.now(ZoneId.of("Africa/Kampala"))
//                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String username = "System"; // You can set this as appropriate

        // Call the existing method
        Response<String> response = startOfDayRun(username);
        System.out.println(response.getData());
    }
	
	public Response<String> startOfDayRun( String username) {
		String strMsg ="";
		String strCurrentdate = "";
		String strnToday = "";
		String strnewToday = "";
		String strLoginuser = "";
		
		String strProposalno = "";
		String strInstallment = "";
		String strQuery = "";
		String strRequestdatetime = "";
				
		String strScheduleno = "";
		String strAgreementno = "";
		String strInstallmentpaydate = "";
		//String strPenalty = "10000";
		String strPenalty = "0";
		String strPaymenttype = "INSTALLLMENT";
		
		String strNoofewi = "";
		String strNoofloans = "";
		String strStatus = "";
		String strPaymode = "";
		String strOverdue = "";
		
		Object weeklyinsts[];
		Object loanpayments[];
		
		double dblinstallment = 0;
		double dblavailableamount = 0;
		double dbltotalpaid = 0;
		double dbltotalcleared = 0;
		double dblOverdue = 0;
		
		int intUser = 0;
		int intScehdule = 0;
		int intNoofewi = 0;
		int intNoofloans = 0;
		
		Date dtnToday = null;
		Date sqlToday = null;
							
//		strCurrentdate = todate;
		strLoginuser = username;
//		strCurrentdate = strCurrentdate.trim();
		
		
		Response<String> response = new Response<>();
		
		TimeStampUtil gts = new TimeStampUtil();
		strnToday = gts.standardDate();
		strnewToday = gts.newstandardDate();
		Date date =null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
             date = sdf.parse(strnewToday);
            String formattedDate = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
		
        List<LoanPayments> allLoanPayments = new ArrayList<>();
		
//		Date newToday = null;
//		try {
//			newToday = new SimpleDateFormat("dd/MM/yyyy").parse(formattedDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		new GetFutureDate();
		
		try {
			dtnToday = new SimpleDateFormat("dd/MM/yyyy").parse(strnToday);
			sqlToday = new SimpleDateFormat("dd/MM/yyyy").parse(strnToday);
		} catch (ParseException e1) {
			System.out.println(e1.getLocalizedMessage());
		}
		
//		TotalDue td = new TotalDue();
		
		try {
//			strQuery = "From Sodrun where rundate = '" + strnewToday + "'";
//			System.out.println(newToday);
			Optional<Sodrun> sodrun =  sodrunRepo.findById(date);			
			if (sodrun.isEmpty()) {
				strQuery = "From PaymentSchedule where to_date(paymentdate, 'dd/mm/yyyy') < '" + strnewToday + "' " +
						"and (status = '' or status is null) " +
						"and loanid in (select proposalno from Loan where impounded = false and reposessed = false and auctioned = false and preclosed = false and normalclosure = false) " +
						"order by loanid, schedule";					
				System.out.println("Before findBY date"+date);
				List<PaymentSchedule> paysch = paymentScheduleRepo.findPaymentSchedules(date);
				if (paysch.size() > 0) {
					
					for (PaymentSchedule ps:paysch) {
						strInstallment = ps.getInstallment();
						strScheduleno = ps.getScheduleno();
						strInstallmentpaydate = ps.getPaymentdate();
						strProposalno = ps.getLoanid();
						intScehdule = ps.getSchedule();
						dblinstallment = Integer.parseInt(strInstallment);
						if(strProposalno.compareTo("VBYB101298") >= 0 || strProposalno.equals("VBYB100880") || strProposalno.equals("VBYB100907")){
							System.out.println("I have a Proposalno "+ strProposalno);
							strAgreementno = strScheduleno.substring(0, 8);  //AN1000051
							//process penalties
//							processPenalty pp = new processPenalty();
							List<LoanPayments> penaltyPayments = pp.CheckPenalty(strProposalno);
							if (penaltyPayments != null && !penaltyPayments.isEmpty()) {
							    allLoanPayments.addAll(penaltyPayments);
							}
							System.out.println("came outside Penalty Check");
							//get penalty amount
//							strQuery = "From Loan where proposalno = '" + strProposalno + "'";
							List<Loan> loan = loanEntryRepo.findByproposalno(strProposalno);
							if (loan.size() > 0) {
								for (Loan ln:loan) {
									strPaymode = ln.getPaymentmode();
									System.out.println("getting paymode from loan "+strPaymode);
								}
//								strQuery = "From LoanParam";
								List<LoanParam> loanparam = loanParamRepo.findAll();
								if (loanparam.size() > 0) { //!= 0) {
									for (LoanParam lp:loanparam) {
										if (strPaymode.equalsIgnoreCase("Weekly")) {
											System.out.println("Inside a LoanParam weekly");
											SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											Date paydate = dateFormat.parse(strInstallmentpaydate);
//					                        Date paymentDate = ; // Assuming Loan has getPaymentdate() method
					                        Date upcomingMonday = getUpcomingMonday(paydate);
					                        Date today = new Date();
					                        System.out.println("Today Date "+ today);
					                        System.out.println("upcoming Monday "+ upcomingMonday);
					                        // Check if today is the upcoming Monday or after
					                        if (!today.before(upcomingMonday)) {  // today >= upcomingMonday
					                            strPenalty = lp.getLatepayment(); // Apply weekly penalty
					                            System.out.println("Updated penalty amount "+ strPenalty);
					                        } else {
					                            strPenalty = "0"; // No penalty before Monday
					                        }
					                        
					                    }  else if (strPaymode.equalsIgnoreCase("Monthly")) {
					                    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											Date paydate = dateFormat.parse(strInstallmentpaydate);
											Date today = new Date();
					                        // Check if today is after the installment date
					                        if (!today.before(paydate)) {  // today >= paydate
					                            strPenalty = lp.getLatepaymentmonthly(); // Apply monthly penalty
					                        } else {
					                            strPenalty = "0"; // No penalty before the installment date
					                        }
					                       
					                    }
									}									
								}
							} else {strPenalty = "0"; }
							
							 
							//Process payment schedules						
//							strQuery = "select sum(cast(amount as double)) as totalpaid From " +
//									"WeeklyInstallment where proposalno = '" + strProposalno + "' and revflag = false group by proposalno";
							Double weeklyinst = weeklyInstRepo.getTotalPaidAmountByProposal(strProposalno);
							System.out.println("After Weekly query "+ weeklyinst);
							if (weeklyinst != null) {
								System.out.println("Inside weeklyinst loop");
								dbltotalpaid = weeklyinst;
//								strQuery = "select sum(cast(amount as double)) as totalcleared From " +
//										"LoanPayments where loanid = '" + strProposalno + "'";
								Double loanpayment = loanPaymentRepo.getTotalClearedAmountByProposal(strProposalno);
								if (loanpayment != null && loanpayment > 0) {
//									loanpayments = loanpayment.toArray();
										dbltotalcleared =  loanpayment;
										System.out.println("Total cleared amount "+ dbltotalcleared);
								}
								
								dblavailableamount = dbltotalpaid - dbltotalcleared;
								System.out.println("AvaibleAmount for loanPayment "+ dblavailableamount);
								if ((dblavailableamount > dblinstallment) || (dblavailableamount == dblinstallment)) {
									//dtnToday = new SimpleDateFormat("dd/MM/yyyy").parse(strnToday);
									System.out.println("Inside avaiableamount greater then installment amount");
									LoanPayments lp = new LoanPayments();
									lp.setSchedule(intScehdule);
									lp.setScheduleno(strScheduleno);
									lp.setLoanid(strProposalno);
									lp.setAmount(strInstallment);
									lp.setRundate(dtnToday);
									lp.setPaymenttype(strPaymenttype);
									
									try {
										allLoanPayments.add(lp);
										for (LoanPayments loanPayment : allLoanPayments) {
											  System.out.println("Main Logic loan payment"+loanPayment.getSchedule() + loanPayment.getScheduleno() + loanPayment.getAmount() + loanPayment.getLoanid());
										}
										// Save all LoanPayments in a single transaction
										if (!allLoanPayments.isEmpty()) {
										    loanPaymentRepo.saveAll(allLoanPayments);  // Persist all the loan payments
										    loanPaymentRepo.flush();  // Optional: flush to ensure data is written immediately
										    System.out.println("All loan payments saved successfully.");
										}
										//Flag payment schedule as paid		
//										strQuery = "UPDATE PaymentSchedule set status = 'PAID' where scheduleno = '" + strScheduleno + "'" +
//												" and loanid = '" + strProposalno + "' and paymentdate = '" + strInstallmentpaydate + "'";
										try {
											intUser = paymentScheduleRepo.updatePaymentSchedule(strScheduleno, strProposalno, strInstallmentpaydate);
										}catch(Exception e) {
											e.printStackTrace();
										}
										
										if (intUser != 0) {
											System.out.println("Payment schedule processed " + strScheduleno + " " + strProposalno + " " + strInstallmentpaydate);
										}
									} catch (Exception e) {
										System.out.println(e.getLocalizedMessage());
									}
										
								} else if (dblavailableamount < dblinstallment) { // book penalty -- available amount cannot cover installment
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
									Date paydate = dateFormat.parse(strInstallmentpaydate);
//			                        Date paymentDate = ; // Assuming Loan has getPaymentdate() method
			                        Date upcomingMonday = getUpcomingMonday(paydate);
			                        Date today = new Date();
									//get overdue amount	
									System.out.println("Inside avaiable amount lesser then installment");
									strOverdue = "";
									dblOverdue = 0;
									String aapenalty = null;
									strOverdue = totalDue.getTotalDue(strAgreementno);
									System.out.println("Overdue amount "+ strOverdue);
									dblOverdue = Double.parseDouble(strOverdue);
									
									dblOverdue = dblOverdue / 2;
									 if (strPaymode.equalsIgnoreCase("Monthly")) {
										 aapenalty = "40000"; 
									 }else if (strPaymode.equalsIgnoreCase("Weekly")) {
										 aapenalty = "10000";
									 }
									/* Compare overdue amount with available amount -- 06.09.2020
									 * If overdue amount is greater than 50% of available amount apply penalty
									 */
									
									if ((dblavailableamount < dblOverdue) && (!today.before(upcomingMonday)) ) {
										System.out.println("Inside avaiable amount lesser then Overdue");
										//Check that schedule does not exist in penalty table
//										strQuery = "From Penalty where loanscheduleno = '" + strScheduleno + "'";
										System.out.println("ScheduleNo "+ strScheduleno);
										List<Penalty> pencheck = penaltyRepo.findByloanscheduleno(strScheduleno);
										if (pencheck.size() == 0) {
											Penalty penalty = new Penalty();
											penalty.setLoanid(strProposalno);
											penalty.setLoanscheduleno(strScheduleno);
											penalty.setLoaninstallment(strInstallment);
											penalty.setLoanpaymentdate(strInstallmentpaydate);
											penalty.setPenalty(aapenalty);		
											System.out.println("Penalty values " + strProposalno + strScheduleno + strInstallment + strInstallmentpaydate + aapenalty);
											try {
												penaltyRepo.save(penalty);		
												System.out.println("Penalty Saved In SODR");
											} catch (Exception e) {
												System.out.println(e.getLocalizedMessage());
											}
										}
									}
								}
								intNoofewi++;
							} else { //book penalty -- no installments paid
//								strQuery = "From Penalty where loanscheduleno = '" + strScheduleno + "'";
								System.out.println("book penalty -- no installments paid");
								List<Penalty> pencheck = null;
								try {
								pencheck =  penaltyRepo.findByloanscheduleno(strScheduleno);
								}catch(Exception e) {
									e.printStackTrace();
								}
								System.out.println("pencheck value "+ pencheck.size());
								if (pencheck.size() == 0) {
									if(strPenalty != "0") {
										System.out.println("inside penalty not equal to zero conditon");
										Penalty penalty = new Penalty();
										penalty.setLoanid(strProposalno);
										penalty.setLoanscheduleno(strScheduleno);
										penalty.setLoaninstallment(strInstallment);
										penalty.setLoanpaymentdate(strInstallmentpaydate);
										penalty.setPenalty(strPenalty);							
										try {
											penaltyRepo.save(penalty);					
										} catch (Exception e) {
											System.out.println(e.getLocalizedMessage());
										}
									}
								}
							}	
							
							intNoofloans++;
						}
					
					}					
				} else {
					strMsg = "There are no schedules for processing";
				}				
				
				strNoofloans = Integer.toString(intNoofloans);
				strNoofewi = Integer.toString(intNoofewi);
				strStatus = "PROCESSED";
				strRequestdatetime = gts.TimeStamp();

				
				Sodrun sod = new Sodrun();
				sod.setNofofewi(strNoofewi);
				sod.setNoofloans(strNoofloans);
				sod.setRundate(sqlToday);
				sod.setRundatetime(strRequestdatetime);
				sod.setRunuser(strLoginuser);
				sod.setStatus(strStatus);
				try {
					sodrunRepo.save(sod);
					System.out.println("successfully runned");
					strMsg = "Start of day for  " + strnToday + " processed successfully";
				}catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			} else {
				strMsg = "Start of day for  " + strnToday + " is already processed";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(strMsg);
		return response;
	}
	
    private static Date getUpcomingMonday(Date paymentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(paymentDate);

        // Calculate how many days until the next Monday
        int daysUntilMonday = (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK) + 7) % 7;
//        if (daysUntilMonday == 0) {
//            daysUntilMonday = 7; // If it's already Monday, set it to the next Monday
//        }

        // Add the days until the next Monday
        cal.add(Calendar.DAY_OF_MONTH, daysUntilMonday);

        return cal.getTime();
    }

	public String checkIfDateExists() {
			String strmsg ;
			String strnToday = "";
//		 	LocalDateTime currentDate = LocalDateTime.now();
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	        String formattedDate = currentDate.format(formatter);
			Date sqlToday = null;
			TimeStampUtil gts = new TimeStampUtil();
			strnToday = gts.standardDate();
			try {
				sqlToday = new SimpleDateFormat("dd/MM/yyyy").parse(strnToday);
			} catch (ParseException e1) {
				System.out.println(e1.getLocalizedMessage());
			}
	        if( sodrunRepo.existsByDateField(sqlToday)) {
	        	strmsg = "Start of Day ran successfully for "+ sqlToday;
	        }else {
	        	strmsg = "Error: Start of Day has not run for "+ sqlToday;
	        }
	        return strmsg;
	}

}
