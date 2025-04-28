package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.loanSchedule;
import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Vehicle;
import com.ozone.smart.entity.VehicleMaster;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.VehicleMasterRepo;
import com.ozone.smart.repository.VehicleRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class DisbursementService {
	
	@Autowired
	private VehicleMasterRepo vmRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private loanSchedule loanSch;
	

	public Response<String> viewParameters(String customer, String proposal, String flag) {
		
		String strCustomer = "";
		String strProposalno = "";	
		String strQuery  ="";
		String strMsg = "";
		String strFlag = "";
		String strFlag1 = "";
		
		String strCustname = "";
		String strNatid = "";
		String strMobile = "";
		String strStage = "";
		
		String strBrand = "";
		String strModel = "";
		String strRegno = "";
		String strEngineno = "";
		String strChassis = "";
		
		String strVehicle = "";
		String strVehicledet = "";
		String strAssetamt = "";
		String strDiscount = "";
		String strCommission = "";
		String strNetpay = "";
		
		String strLnasset = "";
		String strDpa = "";
		String strInsfee = "";
		String strTracker = "";
		String strAmtfncd = "";
		String strEwi = "";
		String strInst = "";
		
		String strNewLnasset = "";
		String strNewDpa = "";
		String strNewInsfee = "";
		String strNewTracker = "";
		String strNewEwi = "";
		String cooRemarks = "";
		String strVeharray [] = null;
		
		int intAssetamt = 0;
		int intDiscount = 0;
		int intNetpay = 0;
		
		int intLnasset = 0;
		int intDpa = 0;
		double dblInsfee = 0;
		int intInsfee = 0;
		int intTracker = 0;
		int intAmtfncd = 0;
		double dblEwi = 0;
					
		strCustomer = customer;
		strProposalno = proposal;
		strFlag = flag;
		strProposalno = strProposalno.trim();
		
		Response<String> response = new Response<>();
		
		if (strCustomer == null || strCustomer.length() == 0) {			
			strMsg = "Select customer";
		} else if (strProposalno == null || strProposalno.length() == 0) {			
			strMsg = "Select proposal no";
		} else {
			
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { System.out.println(e.getLocalizedMessage());
			 * }
			 */	
			
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
			
//			if (strFlag.equals("db")) {
//				strFlag = " and tvverified = true and fiverified = true and cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve'";
//				strFlag1 = " and cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve'";
//			} else if (strFlag.equals("")) {
//				strFlag = "";
//			}
			
			//"customer","brand","asset","natid","model","disc","mobile","engine","commission","stage","chassis","net",lnasset,dpa,lninsfee,trckfee,amtfncd
			//get customer details
//			strQuery = "From CustomerDetails where otherid = '" + strCustomer + "'" + strFlag +"";
			
			
			try {
				List<CustomerDetails> cust = custRepo.findByCustomerIdAndFlag(strCustomer, strFlag);
				
				for (CustomerDetails cd:cust) {
					strCustname = cd.getSurname() + " " + cd.getFirstname();
					strNatid = cd.getNationalid();
					strMobile = cd.getMobileno();
					strStage = cd.getStage();
					cooRemarks = cd.getcooremarks();
				}
				strMsg = strCustname + "|" + strNatid + "|" + strMobile + "|" + strStage + "|" + cooRemarks + "|";
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving customer id: " + strProposalno;
			}			
			
			//get proposal details
			
//			strQuery = "From Proposal where proposalno = '" + strProposalno + "'" + strFlag1 +"";
			
			try {
				List<Proposal> props = propRepo.findByProposalWithFlag(strProposalno, strFlag);
				
				for (Proposal prop:props) {
					strVehicle = prop.getVehicleregno();
					strVehicledet = prop.getVehicleregno();
//					strVehicle = strVehicle.replace("|", "");
//					strVehicle = strVehicle.replace(" |", "");
				}
				
				/*strVeharray = strVehicledet.split(" | ");
				if (strVeharray.length > 6) {
					strBrand = strVeharray[0];
					strModel = strVeharray[2];
				} else if (strVeharray.length == 1) {
					strBrand = strVeharray[0];
					strModel = strVeharray[0];
				} else {
					strBrand = "";
					strModel = "";
				}
				strMsg +=  strBrand + "|" + strModel + "|";
				*/
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving proposal details for : " + strProposalno;
			}
			
			
			//get amounts
			formatDigits fd = new formatDigits();
			
//			strQuery = "From VehicleMaster where brand || model || cc || color = '" + strVehicle + "'";
			
			try {
				List<VehicleMaster> vehiclem = vmRepo.findByBrandModelCcAndColor(strVehicle) ;
				
				if (vehiclem.size() != 0) {
					for (VehicleMaster vm:vehiclem) {
						strBrand = vm.getBrand();
						strModel = vm.getModel();
						strAssetamt = vm.getPrice();
						strDiscount = vm.getDiscount();
					}	
					
					strAssetamt = strAssetamt.replace(",", ""); 
					strDiscount = strDiscount.replace(",", "");
					
					intAssetamt = Integer.parseInt(strAssetamt);
					intDiscount = Integer.parseInt(strDiscount);
					intNetpay = intAssetamt - intDiscount;
					
					strAssetamt = Integer.toString(intAssetamt);
					strDiscount = Integer.toString(intDiscount);
					strCommission = "0";
					strNetpay = Integer.toString(intNetpay);
				} else {
					strAssetamt = "";
					strDiscount = "";
					strCommission = "";
					strNetpay = "";
				}
				
				if (strAssetamt.contains(",")!=true) {strAssetamt = fd.digit(strAssetamt);}
				if (strDiscount.contains(",")!=true) {strDiscount = fd.digit(strDiscount);}
				if (strCommission.contains(",")!=true) {strCommission = fd.digit(strCommission);}
				if (strNetpay.contains(",")!=true) {strNetpay = fd.digit(strNetpay);}
				
				strMsg +=  strBrand + "|" + strModel + "|" + strAssetamt + "|" + strDiscount + "|" + strCommission + "|" + strNetpay + "|";
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving allocated vehicle for: " + strProposalno;
			}
			
			//get vehicle details
			
//			strQuery = "From CustomerVehicle where proposalno  = '" + strProposalno + "'";
			
			try {
				CustomerVehicle cv =custVehRepo.findByproposalno(strProposalno);
				
				strRegno = cv.getVehicleregno();
				strQuery = "From Vehicle where regno  = '" + strRegno + "'";
				try {
					Optional<Vehicle> vehicle = vehicleRepo.findById(strRegno);
					if(vehicle.isPresent()) {
						Vehicle veh = vehicle.get();
						strEngineno = veh.getEngineno();
						strChassis = veh.getChassisno();
					}	
					strMsg +=  strEngineno + "|" + strChassis + "|";
					
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
					strMsg = "Error retrieving vehicle for: " + strProposalno;
				}
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving customer id: " + strProposalno;
			}
			
			//get Loan details
			
//			strQuery = "From Loan where proposalno = '" + strProposalno + "'";
			
			try {
				List<Loan> loan = loanRepo.findByproposalno(strProposalno);
				
				for (Loan ln:loan) {
					strLnasset = ln.getLoanamount();
					strDpa = ln.getDownpayment();
					strInsfee = ln.getInsurancefee();
					strTracker = ln.getTrackerfee();
					strEwi = ln.getEwi();
					strInst = ln.getNoofinstallments();
				}	
				
				strNewLnasset = strLnasset.replace(",", "");
				strNewDpa = strDpa.replace(",", "");
				strNewInsfee = strInsfee.replace(",", "");
				strNewTracker = strTracker.replace(",", "");
				strNewEwi = strEwi.replace(",", "");
				
				intLnasset = Integer.parseInt(strNewLnasset);
				intDpa = Integer.parseInt(strNewDpa);
				dblInsfee = Double.parseDouble(strNewInsfee);
				intTracker = Integer.parseInt(strNewTracker);
				intInsfee = (int) dblInsfee;
				int intInsfee2 = (int) Math.round(dblInsfee);
				intAmtfncd = intLnasset + intInsfee - intDpa;
				double dblStampduty = intLnasset*0.01;
//				double othercost = (dblStampduty + intTracker)/ Double.parseDouble(strInst);
				intAmtfncd = intAmtfncd + intTracker + (int) dblStampduty ;
//				double dbloldintstallment= Double.parseDouble(strNewEwi);//old installment amount
//				 
//				double dblnewInstallmentamt = Math.round((dbloldintstallment + othercost) / 1000) * 1000;//new installment amount
//				int intNewinstallment =(int) dblnewInstallmentamt;
				dblEwi = Double.parseDouble(strNewEwi);
				
				strAmtfncd = Integer.toString(intAmtfncd);
				strLnasset = Integer.toString(intLnasset);
				strDpa = Integer.toString(intDpa);
				strInsfee = Integer.toString(intInsfee);
				strTracker = Integer.toString(intTracker);	
				strEwi = Double.toString(dblEwi);
				
				if (strLnasset.contains(",")!=true) {strLnasset = fd.digit(strLnasset);}
				if (strDpa.contains(",")!=true) {strDpa = fd.digit(strDpa);}
				if (strInsfee.contains(",")!=true) {strInsfee = fd.digit(strInsfee);}
				if (strTracker.contains(",")!=true) {strTracker = fd.digit(strTracker);}
				if (strAmtfncd.contains(",")!=true) {strAmtfncd = fd.digit(strAmtfncd);}
				if (strEwi.contains(",")!=true) {strEwi = fd.digit(strEwi);}
				
				strMsg +=  strLnasset + "|" + strDpa + "|" + strInsfee + "|" + strTracker + "|" + strAmtfncd + "|" + strRegno + "|" + strEwi + "|" + strInst;
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving loan details for: " + strProposalno;
			}
			
		}
		response.setData(strMsg);
		return response;
	}


	public Response<String> scheduleGen(String customer, String proposal, String username) {
		
		String strMsg = "";
		String newstrMsg = "";
		int intAgreementserial = 0;
		String strAgreementno = "";
		
		String strProposal = "";
		String strCustomer = "";
		String strLoanamt = "";
		String strIntrate = "";
		String strNoinst = "";
		String strDownpmnt = "";
		String strTrffee = "";
		String strInsfee = "";
		String strTrckfee = "";
		String strQuery = "";
		String strPaymode = "";
		
		String strDISBRequestdatetime = "";
		String strLoginuser = "";
		
		Response<String> response = new Response<>();
						
		strCustomer = customer;
		strProposal = proposal;
		strLoginuser = username;
		
		strCustomer = strCustomer.trim();
		strProposal = strProposal.trim();
		
		TimeStampUtil gts = new TimeStampUtil();
		strDISBRequestdatetime = gts.TimeStamp();
		
		if (strCustomer == null || strCustomer.length() == 0) {			
			strMsg = "Select customer";
		} else if (strProposal == null || strProposal.length() == 0) {			
			strMsg = "Select proposal";
		} else {
			
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
			
			try {
				
//				strQuery = "From CustomerVehicle where proposalno = '" + strProposal + "' and disbursed = false";
				
				List<CustomerVehicle> custvehicle=  custVehRepo.findByProposalAndDisb(strProposal);
				
				if (custvehicle.size() != 0) {
					for (CustomerVehicle cv:custvehicle) {
						intAgreementserial = cv.getAgreementserial();
						if (intAgreementserial != 0) {
							
							strAgreementno = "AN" + intAgreementserial;					
							
//							strQuery = "From Loan where custid = '" + strCustomer + "'  and proposalno = '" + strProposal + "' and scheduled = 0 and cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve'";
							
							List<Loan> loan = loanRepo.findByParameters(strCustomer, strProposal) ;
							
							if (loan.size() > 0) {
								for (Loan ln:loan) {
//									strIntrate = ln.getInterestrate();
//									strLoanamt = ln.getLoanamount();
//									strNoinst = ln.getNoofinstallments();
//									strDownpmnt = ln.getDownpayment();
//									strTrffee = "0";
//									strInsfee = ln.getInsurancefee();
//									strTrckfee = ln.getTrackerfee();
//									strPaymode = ln.getPaymentmode();
//									
//									if (strLoanamt.contains(",")) {strLoanamt = strLoanamt.replace(",", "");}
//									if (strDownpmnt.contains(",")) {strDownpmnt = strDownpmnt.replace(",", "");}
//									if (strInsfee.contains(",")) {strInsfee = strInsfee.replace(",", "");}
//									if (strTrckfee.contains(",")) {strTrckfee = strTrckfee.replace(",", "");}
//									
////									loanSchedule ls = new loanSchedule();
//									newstrMsg = loanSch.schedule(strAgreementno, strProposal, strIntrate, strLoanamt, strNoinst, strDownpmnt, 
//											strTrffee, strInsfee, strTrckfee, strPaymode);
//									
//									strMsg = newstrMsg;
									
//									if (strMsg.contains("Payment schedule")) {
//										strQuery = "UPDATE Loan set scheduled = 1 where proposalno = '" + strProposal + "'"; 
										int intLoan = loanRepo.countLoansByProposal(strProposal);
										if (intLoan > 0) {									
//											strQuery = "UPDATE CustomerVehicle set disbursed = true, disbuser = '" + strLoginuser + "' , disbdatetime = '" + strDISBRequestdatetime + "' where proposalno = '" + strProposal + "'";
											int intCV = custVehRepo.updateCustomerVehicle(strLoginuser, strDISBRequestdatetime, strProposal);
											if (intCV == 0) {
												strMsg = "Loan " + strAgreementno + " has been scheduled but CV not flagged";
											}else {
												strMsg = "Loan for " + strAgreementno + " is disbursed Successfully";
											}
										} else {
											strMsg = "Loan " + strAgreementno + " has been scheduled but not flagged";
										}
//									}
								}	
							} else {
								strMsg = "Failed, Check Loan approvals!";
							}
							
						} else {
							strMsg = "Vehicle allocation is mandatory!";
						}	
					}
					
				} else {
					strMsg = "Loan for " + strAgreementno + " is already disbursed";
					//strMsg = "Payment schedule : " + strProposal + " saved successfully.";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
		}				
		response.setData(strMsg);
		return response;
	}
	
public Response<List<CustomerDetailsDto>> viewCustId() {
		
		List<CustomerDetails> vehList = custRepo.getCustForDisburse();
		Response<List<CustomerDetailsDto>> response = new Response<>();
		List<CustomerDetailsDto> PropDtoList = new ArrayList<>();
		
		for(CustomerDetails prop : vehList) {
			CustomerDetailsDto propDto = new CustomerDetailsDto();
			propDto.setOtherid(prop.getOtherid());
			propDto.setSurname(prop.getSurname());
			PropDtoList.add(propDto);
			response.setData(PropDtoList);
		}	
		return response;
	}

}
