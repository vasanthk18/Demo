package com.ozone.smart.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.LoanStatusDto;
import com.ozone.smart.dto.ReposessionDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.entity.LoanStatus;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Reposession;
import com.ozone.smart.entity.Vehicle;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.ReposessionRepo;
import com.ozone.smart.repository.VehicleRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class ReposessionService {
	@Autowired
	private ReposessionRepo reposessionRepo;
	
	@Autowired
	private ProposalRepo proposalRepo;
	
	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private ImpoundedStockRepo impStockRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentSchRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private LoanParamRepo loanParamRepo;
	
	@Autowired
	private LoanEntryRepo loanEntryRepo;

	public Response<List<ReposessionDto>> FetchAggrementNo(String id) {
		List<Reposession> repoList = reposessionRepo.findInitiatedReposessionsByCustomerId(id);
		Response<List<ReposessionDto>> response = new Response<>();
		List<ReposessionDto> repoDtoList = new ArrayList<>() ;
		for (Reposession repo:repoList) {
			ReposessionDto repoDto = new ReposessionDto();
			repoDto.setAgreementno(repo.getAgreementno());
			repoDto.setVehicleregno(repo.getVehicleregno());
			repoDtoList.add(repoDto);
		}
		response.setData(repoDtoList);
		return response;
	}

	public Response<ReposessionDto> viewByAggrementNo(String agreement) {
		
		Response<ReposessionDto> response = new Response<>();
		ReposessionDto resDto = new ReposessionDto();
		String AgreementNo[];
		String ProposalNo = null;
		String FuturePrincipal = null;
		String Vehicle = null;
		if (agreement == null || agreement.length() == 0) {			
			response.setErrorMsg("Select agreement");
		} else {
			
			AgreementNo = agreement.split(" | ");
			agreement = AgreementNo[0];	
			try {
				Optional<Reposession> reposession = reposessionRepo.findById(agreement);	
				if(reposession.isPresent()) {
					Reposession repo = reposession.get();
					ProposalNo=repo.getProposalno();
					resDto.setFutureprincipal(repo.getFutureprincipal());
				}
				try {
					Proposal proposal = proposalRepo.findByproposalno(ProposalNo);			
						resDto.setVm(proposal.getVehicleregno());		
				} catch (Exception e) { System.out.println(e.getLocalizedMessage());}
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error retrieving details for: " + agreement);
			}
			System.out.println(resDto.getFutureprincipal());
			System.out.println(resDto.getVm());
			response.setData(resDto);
		}
		return response;
	}
	
	@Autowired
	private VehicleRepo vehicleRepo;

	public Response<String> viewByProposalNo(String proposal) {
		
		Response<String> response = new Response<>();
		String strMsg = "";
		String strQuery = "";
		
		String strProposalno = "";
		String strRegno = "";
		String strDealer = "";
		String strEngineno = "";		
		String strChassisno = "";
		String strTrackingno = "";
		String strSimcardno = "";
		
		strProposalno = proposal;
		
		if (strProposalno == null || strProposalno.length() == 0) {			
			strMsg = "Select Proposalno";
		} else {
//			strQuery = "From Reposession where newproposalid = " + "'" + strProposalno + "'";
			
			try {
				List<Reposession> reposession = reposessionRepo.findByproposalno(strProposalno);
				if (reposession.size() > 0) {
					for (Reposession rep:reposession) {
						strRegno = rep.getVehicleregno();
					}
					
					strRegno = strRegno.trim();
//					strQuery = "From Vehicle where id = " + "'" + strRegno + "'";
					
					try {
						Optional<Vehicle> vehicle = vehicleRepo.findById(strRegno);				
							if(vehicle.isPresent()){
								Vehicle vh = vehicle.get();
							strDealer = vh.getDealer();
							strEngineno = vh.getEngineno();
							strChassisno = vh.getChassisno();
							strTrackingno = vh.getTrackingno();
							strSimcardno = vh.getSimcardno();
						}
						
						strMsg = strRegno + "|" + strDealer + "|" + strEngineno + "|" + strChassisno + "|" + strTrackingno + "|" + strSimcardno;
						
					} catch (Exception e) {
						System.out.println(e.getLocalizedMessage());
						strMsg = "";
					}
				} else {
					strMsg = "";
				}
				
			} catch (Exception e) {
				
			}
		}			
		response.setData(strMsg);
		return response;
	}

	public Response<String> viewRepo(String agreement) {
		
		
		String strMsg = "";
		String strQuery = "";
		
		String strAgreementno = "";
		String strAgreementqry = "";
		String strImpounddate = "";
		String strNewimpounddate = "";
		String strProposalno = "";
		String strVehicle = "";
		
		String strCustid = "";
		String strCustomername = "";
		String strImpound = "";
		
		String strInst = "";
		
		String strPrincipal = "";
		String strInt = "";
		
		String strPenalty = "";
		
		int intNoofinst = 0;
		int intOverdue = 0;
		int intImpound = 0;
		
		int intPennoofinst = 0;
		int intTotalpen = 0;
		int intTotaltopay = 0;
		
		int intfuturenoofinst = 0;
		int intfuturedue = 0;
		double dblfutureprincipal = 0;
		double dblfutureint = 0;
		double dblassetcost = 0;
		
		String strOverdue = "";
		String strTotalpen = "";
		String strTotaltopay = "";
		String strFuturedue = "";
		String strFutureprincipal = "";
		String strFutureint = "";
		String strAssetcost = "";
		
		strAgreementno = agreement;
		strAgreementqry = strAgreementno + "%";
		
		Response<String> response = new Response<>();
		
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else {
			
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { e.printStackTrace(); }
			 */
			
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
			
			formatDigits fd = new formatDigits();
			
//			strQuery = "From Impoundedstock where agreementno = '" + strAgreementno + "'";
			
			try {
				Optional<Impoundedstock> impound = impStockRepo.findById(strAgreementno);
				
				if(impound.isPresent()){
					Impoundedstock ims = impound.get();
					strProposalno = ims.getProposalno();
					strVehicle = ims.getVehicleregno();
					strImpounddate = ims.getImpounddate();
				}	
												
//				strQuery = "From CustomerDetails where otherid in (select customerid " +
//						"from Proposal where proposalno = '" + strProposalno + "')";
				
				try {
					List<CustomerDetails> custdet = custRepo.findByProposalNo(strProposalno);
					
					for (CustomerDetails cd:custdet) {
						strCustid = cd.getOtherid();
						strCustomername = cd.getSurname() + " " + cd.getFirstname();
					}
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				//Payment schedule -- 20/12/2019
//				strNewimpounddate = strImpounddate.substring(6, 10) + "-" + strImpounddate.substring(3, 5) + "-" +strImpounddate.substring(0, 2);

				LocalDate localDate = LocalDate.parse(strImpounddate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				java.sql.Date date = java.sql.Date.valueOf(localDate);
				
				//				strQuery = "From PaymentSchedule " +
//						"where scheduleno like '" + strAgreementqry + "' and to_date(paymentdate, 'dd/mm/yyyy') <= '" + strNewimpounddate + "'" +
//						" and status != 'PAID'";
			
				try {
					List<PaymentSchedule> paysch = paymentSchRepo.findNonPaidSchedules(strAgreementqry, date);
					
					for (PaymentSchedule ps:paysch) {
						strInst = ps.getInstallment();						
						intNoofinst++;
						intOverdue+= Integer.parseInt(strInst);
					}
					
					strOverdue = Integer.toString(intOverdue);
					strOverdue = fd.digit(strOverdue);
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				//Future due intfuturenoofinst, intfuturedue;
//				strNewimpounddate = strImpounddate.substring(6, 10) + "-" + strImpounddate.substring(3, 5) + "-" +strImpounddate.substring(0, 2);
//				strQuery = "From PaymentSchedule " +
//						"where scheduleno like '" + strAgreementqry + "' and to_date(paymentdate, 'dd/mm/yyyy') > '" + strNewimpounddate + "'";
			
				try {
					List<PaymentSchedule> paysch = paymentSchRepo.findSchedules(strAgreementqry, date);
					
					for (PaymentSchedule ps:paysch) {
						strInst = ps.getInstallment();
						strPrincipal = ps.getPrincipal();
						strInt = ps.getInterestamount();
						
						intfuturenoofinst++;
						intfuturedue+= Integer.parseInt(strInst);
						dblfutureprincipal+= Double.parseDouble(strPrincipal);
						dblfutureint+= Double.parseDouble(strInt);				
					}
				
					dblfutureprincipal = Math.round(dblfutureprincipal * 100) / 100D;
					dblfutureint = Math.round(dblfutureint * 100) / 100D;
					
					strFuturedue = Integer.toString(intfuturedue);
					strFuturedue = fd.digit(strFuturedue);
					strFutureprincipal = Double.toString(dblfutureprincipal);
					strFutureprincipal = fd.digit(strFutureprincipal);
					strFutureint = Double.toString(dblfutureint);
					strFutureint = fd.digit(strFutureint);
					
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				
				//Penalty
				
//				strQuery = "From Penalty where loanscheduleno like '" + strAgreementqry + "' and (status = '' or status is null)";
			
				try {
					List<Penalty> penalty = penaltyRepo.findByLoanSchdlNoIdAndStatusIsNull(strAgreementqry);
					
					for (Penalty pen:penalty) {
						strPenalty = pen.getPenalty();				
						intPennoofinst++;
						intTotalpen+= Integer.parseInt(strPenalty);
					}
					
					strTotalpen = Integer.toString(intTotalpen);
					strTotalpen = fd.digit(strTotalpen);
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
//				strQuery = "From LoanParam";
				
				try {
					List<LoanParam> loan = loanParamRepo.findAll();
					
					for (LoanParam ln:loan) {
						strImpound = ln.getImpound();
					}
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			
				intImpound = Integer.parseInt(strImpound);
				intTotaltopay = intOverdue + intTotalpen + intImpound;
				
				strTotaltopay = Integer.toString(intTotaltopay);
				strTotaltopay = fd.digit(strTotaltopay);
				strInst = fd.digit(strInst);
				
				dblassetcost = dblfutureprincipal + intTotaltopay;
				strAssetcost = Double.toString(dblassetcost);
				
				if (strAssetcost == "") {strAssetcost = "0";}
				strAssetcost = fd.digit(strAssetcost);	
				if (strImpound == "") {strImpound = "0";}
				strImpound = fd.digit(strImpound);
				if (strPenalty == "") {strPenalty = "0";}
				strPenalty = fd.digit(strPenalty);
				
				strMsg = strProposalno + "|" +  strVehicle + "|" +  strImpounddate + "|" +  strCustid + "|" +  strCustomername + "|" +  intNoofinst
						+ "|" +  strInst + "|" +  strOverdue + "|" +  intPennoofinst + "|" +  strPenalty + "|" +  strTotalpen + "|" +  strTotaltopay
						+ "|" +  strImpound + "|" + intfuturenoofinst + "|" +  strFuturedue + "|" + strFutureprincipal + "|" +  strFutureint + "|" +  strAssetcost;
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retreiving details for agreement no: " + strAgreementno;
			}			
		}
				
		response.setData(strMsg);
		
		return response;
	}

	public Response<String> saveRepo(ReposessionDto reposessionDto) {
		
		
		String strMsg ="";
		String strAgreementno = "";
		String strCustid = "";
		String strProposalno = "";
		String strVehicle = "";
		String strImpdate = "";
		String strInstdue = "";			
		String strInstallment = "";
		String strPeninstdue = "";
		String strPenalty = "";			
		String strAmnttoclear = "";			
		String strImpchgs = "";
		String strFutureinst = "";
		String strTotfuturedue = "";
		String strFutureprincipal = "";
		String strFutureint = "";
	
		String strStatus = "INITIATED";
		
		String strRequestdatetime = "";
		String strLoginuser = "";
		
		Response<String> response = new Response<>();
		
		strAgreementno = reposessionDto.getAgreementno();
		strCustid = reposessionDto.getCustomerid();
		strProposalno = reposessionDto.getProposalno();
		strVehicle = reposessionDto.getVehicleregno();
		strImpdate = reposessionDto.getImpounddate();
		strInstdue = reposessionDto.getInstdue();		
		strInstallment = reposessionDto.getInstallment();
		strPeninstdue = reposessionDto.getPenalinst();
		strPenalty = reposessionDto.getPenalty();			
		strAmnttoclear = reposessionDto.getAmounttoclear();			
		strImpchgs = reposessionDto.getImpcharges();
		strFutureinst = reposessionDto.getFutureinst();
		strTotfuturedue = reposessionDto.getTotalfuturedue();
		strFutureprincipal = reposessionDto.getFutureprincipal();
		strFutureint = reposessionDto.getFutureinterest();
		strLoginuser = reposessionDto.getReposessuser();
					
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strCustid == null || strCustid.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strProposalno == null || strProposalno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else { 
			
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { e.printStackTrace(); }
			 */
			
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			
			Reposession rep = new Reposession();
			rep.setAgreementno(strAgreementno);
			rep.setCustomerid(strCustid);
			rep.setProposalno(strProposalno);
			rep.setVehicleregno(strVehicle);
			rep.setImpounddate(strImpdate);
			rep.setInstdue(strInstdue);
			rep.setInstallment(strInstallment);
			rep.setPenalinst(strPeninstdue);
			rep.setPenalty(strPenalty);
			rep.setImpcharges(strImpchgs);
			rep.setAmounttoclear(strAmnttoclear);
			rep.setFutureinst(strFutureinst);
			rep.setFutureprincipal(strFutureprincipal);
			rep.setFutureinterest(strFutureint);
			rep.setTotalfuturedue(strTotfuturedue);
			rep.setStatus(strStatus);
			rep.setReposessdatetime(strRequestdatetime);
			rep.setReposessuser(strLoginuser);
			
			try {					
				reposessionRepo.save(rep);
				//Get customer id and add in the message
				
				strMsg = "Rescheduling initiated successfully, \n " + 
						"Create new proposal and link to agreementno " + strAgreementno;
				
//				strQuery = "Update Loan set reposessed = true where proposalno = '" + strProposalno + "'";
				int intUser = loanEntryRepo.updateLoanForRepo(strProposalno);
				if (intUser > 0) {
					System.out.println("Loan " + strProposalno + " reposessed successfully");
				}							
						
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retreiving details for agreement no: " + strAgreementno;
			}			
		}
		response.setData(strMsg);
		return response;
	}

	public Response<List<ImpStockDto>> viewAgree() {
		
		Response<List<ImpStockDto>> response = new Response<>();
		
		List<Impoundedstock> loanStatus = impStockRepo.getAgree() ;
		
		List<ImpStockDto> categoryDtoList = new ArrayList<>();
		
		for(Impoundedstock ls : loanStatus) {
			ImpStockDto lsDto = new ImpStockDto();
			lsDto.setAgreementno(ls.getAgreementno());
			categoryDtoList.add(lsDto);		
		}
		response.setData(categoryDtoList);
		return response;
	}

}
