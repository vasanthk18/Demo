package com.ozone.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CoCamDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class CamService {
	
	@Autowired
	private GuarantorRepo guaranRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ProposalRepo proposalRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private RiderRepo riderRepo;

	public Response<String> updateCam(CoCamDto coCamDto) {
		
		String strQuery = "";
		String strMsg="";
		String strCocam = "";
		String strCocamremarks = "";
		String strActivity = "";
		String strCocamsr = "";
		String strId = "";
		String strQuerypart = "";
		String strCustomerid = "";
		String strProposalid = "";
		
		String strCAMRequestdatetime = "";
		String strLoginuser = "";
		
		int intUser = 0;
		
		strId = coCamDto.getId();
		strActivity = coCamDto.getActivity() ;
		strCocam = coCamDto.getCocam();
		strCocamremarks = coCamDto.getCocamremarks();
		strCocamsr = coCamDto.getCocamsr();
		strLoginuser = coCamDto.getLoginUser();
		strCustomerid = coCamDto.getCustomerId();
		strProposalid = coCamDto.getProposalId();
		
		System.out.println(strId);
		System.out.println(strActivity);
		System.out.println(strCocam);
		System.out.println("COCAM REAMRKS ::> "+strCocamremarks);
		System.out.println(strCocamsr);
		System.out.println(strLoginuser);
		System.out.println(strCustomerid);
		System.out.println("strProposalid"+strProposalid);
		
		strId = strId.trim();
		strProposalid = strProposalid.trim();
		System.out.println("Checking proposal id for reverting :: "+strProposalid);
		
		Response<String> response = new Response<>();
				
//		if (strActivity.equals("CD")) {
////			strActivity = "CustomerDetails";
////			strQuerypart = "otherid = '" + strId + "'";
//			strQuerypart = "cd.otherid = '" + strId + "'";
//		} else if (strActivity.equals("G")) {
//			strActivity = "Guarantor";
//			strQuerypart = "id = " + strId + "";
//		} else if (strActivity.equals("DP")) {
//			strActivity = "Proposal";
//			strQuerypart = "proposalno = '" + strId + "'";
//		} else if (strActivity.equals("LN")) {
//			strActivity = "Loan";
//			strQuerypart = "proposalno = '" + strId + "'";
//		} 
//		
//		strQuery = "UPDATE " + strActivity + " set coapproval = '" + strCocam + "', coremarks = '" + strCocamremarks + "', camuser = '" + strLoginuser + "', camdatetime = '" + strCAMRequestdatetime + "'  where " + strQuerypart + "";
		
		TimeStampUtil gts = new TimeStampUtil();		
		strCAMRequestdatetime = gts.TimeStamp();
				
//		Session em = (Session)request.getAttribute("sh");
//		easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
//		
		
		
		if (strId.length() > 0) {
			if (strActivity.equals("CD")) {
				intUser = custRepo.updateCustomerDetails(strCocam, strCocamremarks, strLoginuser, strCAMRequestdatetime, strId);
			} else if (strActivity.equals("G")) {
				intUser = guaranRepo.updateGuarantor(strCocam, strCocamremarks, strLoginuser, strCAMRequestdatetime, strId);
			} else if (strActivity.equals("DP")) {
				intUser = proposalRepo.updateProposalForCam(strCocam, strCocamremarks, strLoginuser, strCAMRequestdatetime, strId);
			} else if (strActivity.equals("LN")) {
				intUser = loanRepo.updateLoanForCam(strCocam, strCocamremarks, strLoginuser, strCAMRequestdatetime, strId);
			} 
			else if (strActivity.equals("RD")) {
				System.out.println("inside RD");
				intUser = riderRepo.updateCamRiderDetails(strCocam, strCocamremarks, strLoginuser, strCAMRequestdatetime, strId);
			} 
			
			if (intUser == 1) {
				strMsg = strActivity + " updated successfully";
			} else {
				strMsg = strActivity + " failed to update";
			}
			
		} else {
			strMsg = strActivity + " id is empty";
		}
		
		
		//Carry out stage reversal "CQC","TV","FI"
		strQuery = "";
		if (strCocamsr != "" && strCocam.equals("Not Recommended")) {
			if (strCocamsr.equals("CQC")) {
//				strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				if (strActivity.equals("CD")) {
					intUser = custRepo.updateCustomerForCQC(strCocamremarks, strId);
//					strActivity = "Guarantor";
//					strQuerypart = "custid = '" + strId + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForCQC(strCocamremarks, strId);
					
					intUser = riderRepo.updateRiderForCQC(strCocamremarks, strProposalid);
					
					
				} else if (strActivity.equals("G")) {
//					strQuerypart = "custid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForCQC(strCocamremarks, strCustomerid);
					strActivity = "CustomerDetails";
					strQuerypart = "otherid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = custRepo.updateCustomerForCQC(strCocamremarks, strCustomerid);
					
					intUser = riderRepo.updateRiderForCQC(strCocamremarks, strProposalid);
				} else if (strActivity.equals("RD")) {
					
					intUser = riderRepo.updateRiderForCQC(strCocamremarks, strProposalid);
//					strQuerypart = "custid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForCQC(strCocamremarks, strCustomerid);
					strActivity = "CustomerDetails";
					strQuerypart = "otherid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = custRepo.updateCustomerForCQC(strCocamremarks, strCustomerid);
					
				} 
				
			} else if (strCocamsr.equals("TV")) {
				strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				if (strActivity.equals("CD")) {
					intUser =custRepo.updateCustomerForTV(strCocamremarks, strId);
//					strActivity = "Guarantor";
//					strQuerypart = "custid = '" + strId + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForTV(strCocamremarks, strId);
					
					intUser = riderRepo.updateRiderForTV(strCocamremarks, strProposalid);
				} else if (strActivity.equals("G")) {
//					strQuerypart = "custid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForTV(strCocamremarks, strCustomerid);
//					strActivity = "CustomerDetails";
//					strQuerypart = "otherid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = custRepo.updateCustomerForTV(strCocamremarks, strCustomerid);
					
					intUser = riderRepo.updateRiderForTV(strCocamremarks, strProposalid);

				}else if (strActivity.equals("RD")) {
					intUser = riderRepo.updateRiderForTV(strCocamremarks, strProposalid);
//					strQuerypart = "custid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForTV(strCocamremarks, strCustomerid);
//					strActivity = "CustomerDetails";
//					strQuerypart = "otherid = '" + strCustomerid + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', tvverified = false, tvremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = custRepo.updateCustomerForTV(strCocamremarks, strCustomerid);
					

				}
			} else if (strCocamsr.equals("FI")) {
//				strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', fiverified = false, firemarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				if (strActivity.equals("CD")) {
					intUser = custRepo.updateCustomerForFI(strCocamremarks, strId);
//					strActivity = "Guarantor";
//					strQuerypart = "custid = '" + strId + "'";
//					strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', fiverified = false, firemarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
					intUser = guaranRepo.updateGuarantorForFI(strCocamremarks, strId);
					
					intUser = riderRepo.updateRiderForFI(strCocamremarks, strProposalid);

				} else if (strActivity.equals("G")) {
					intUser = guaranRepo.updateGuarantorForFI(strCocamremarks, strCustomerid);
					
					intUser = custRepo.updateCustomerForFI(strCocamremarks, strCustomerid);
					
					intUser = riderRepo.updateRiderForFI(strCocamremarks, strProposalid);

				} else if (strActivity.equals("RD")) {
					intUser = riderRepo.updateRiderForFI(strCocamremarks, strProposalid);
					
					intUser = guaranRepo.updateGuarantorForFI(strCocamremarks, strCustomerid);
					
					intUser = custRepo.updateCustomerForFI(strCocamremarks, strCustomerid);
					

				}
			}
			
			//UPDATE Loan set coapproval = '', coremarks = '', tvverified = false, fiverified = false, cqc = false, cqcremarks = '', srremarks = 'wrong rate' where proposalno = 'VBYB100161'
			if (strActivity.equals("DP")) {
//				strQuery = "UPDATE Proposal set coapproval = '', coremarks = '', cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				intUser = proposalRepo.updateProposals(strCocamremarks, strId);
//				strActivity = "Loan";
//				strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				intUser = loanRepo.updateLoans(strCocamremarks, strId);
			} else if (strActivity.equals("LN")) {
//				strQuery = "UPDATE Loan set coapproval = '', coremarks = '', cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				intUser = loanRepo.updateLoans(strCocamremarks, strId);
//				strActivity = "Proposal";
//				strQuery = "UPDATE " + strActivity + " set coapproval = '', coremarks = '', cqc = false, cqcremarks = '', srremarks = '" + strCocamremarks + "' where " + strQuerypart + "";
				intUser = proposalRepo.updateProposals(strCocamremarks, strId);
			} 
		}
		response.setData(strMsg);
		
		return response;
	}

}
