package com.ozone.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CamApprovalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class CamApprovalService {
	
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private GuarantorRepo guanRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;

	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private RiderRepo riderRepo;
	
	public Response<String> updateCoCamApp(CamApprovalDto camApproval) {
		
		String strMsg ="";
		String strCocam = "";
		String strCocamaremarks = "";
		String strActivity = "";
		String strId = "";
		String strG1id = "";
		String strG2id = "";
		String strProp = "";
		//String strCocamasr = "";
		String strRevremarks = "";
		
		String strCAMARequestdatetime = "";
		String strLoginuser = "";
		
		int intUser = 0;
		
		strId = camApproval.getId();
		strG1id = camApproval.getG1id();
		strG2id = camApproval.getG2id();
		strProp = camApproval.getProp();
		strActivity = camApproval.getActivity();
		strCocam = camApproval.getCocamverdict();
		strCocamaremarks = camApproval.getCocamremark();
		//strCocamasr = cocamasr");
		strRevremarks = camApproval.getRevremarks();
		strLoginuser = camApproval.getUsername();
		
		try {
			strId = strId.trim();
			strProp = strProp.trim();
			strG1id = strG1id.trim();
			strG2id = strG2id.trim();
		} catch (Exception e) {}		
		
		TimeStampUtil gts = new TimeStampUtil();		
		strCAMARequestdatetime = gts.TimeStamp();
		
		Response<String> response = new Response<>();
						
		/*
		 * InitialContext ic = new InitialContext(); libraryBean =
		 * (easybodaPersistentBeanRemote)ic.lookup(
		 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
		 * );
		 */
//		Session em = (Session)request.getAttribute("sh");
//		easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
		
		if (strActivity.equals("ALL")) {
			if (strCocam.equals("Approve")) {
				
//				strQuerypart = "otherid = '" + strId + "'";
//				strQuery = "UPDATE CustomerDetails set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";				
				intUser = custRepo.updateCustomerForCoo(strCocam,strCocamaremarks, strLoginuser, strCAMARequestdatetime, strId); 
				
				if (!strG1id.equals("")) {
//					strQuerypart = "id = '" + strG1id + "'";
//					strQuery = "UPDATE Guarantor set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "'  where " + strQuerypart + "";				
					intUser = guanRepo.updateGuarantorForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strG1id);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
				}
				
				if (!strG2id.equals("")) {
//					strQuerypart = "id = '" + strG2id + "'";
//					strQuery = "UPDATE Guarantor set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "'  where " + strQuerypart + "";				
					intUser = guanRepo.updateGuarantorForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strG2id);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
				}
				
				if (!strProp.equals("")) {
//					strQuerypart = "proposalno = '" + strProp + "'";
//					strQuery = "UPDATE Loan set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser +"', camadatetime = '" + strCAMARequestdatetime + "'  where " + strQuerypart + "";				
					intUser = loanRepo.updateLoanForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strProp);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}					
//					strQuery = "UPDATE Proposal set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";				
					intUser = propRepo.updatePropForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strProp); 
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
					
					intUser = riderRepo.updateRiderForCoo(strCocam,strCocamaremarks, strLoginuser, strCAMARequestdatetime, strProp); 
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
					
				}
				//strQuerypart = "proposalno = '" + strProp + "'";				
				//strQuery = "UPDATE " + strActivity + " set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamremarks + "'  where " + strQuerypart + "";				
				//intUser = libraryBean.updateProposal(strQuery);
			
			} else if (strCocam.equals("Reverse")) {				
				
				strRevremarks  = strRevremarks.trim();
				if (strRevremarks.length() > 0) {
					strCocamaremarks = strRevremarks + "#" + strCocamaremarks;
				}
				
//				strQuerypart = "otherid = '" + strId + "'";
//				strQuery = "UPDATE CustomerDetails set coapproval = '', cooapproval = '', revremarks = '" + strCocamaremarks + "'  where " + strQuerypart + "";				
				intUser = custRepo.updateCustomerForReverse(strCocamaremarks, strId); 
				
				
				if (!strG1id.equals("")) {
//					strQuerypart = "id = '" + strG1id + "'";
//					strQuery = "UPDATE Guarantor set coapproval = '', cooapproval = '', revremarks = '" + strCocamaremarks + "'  where " + strQuerypart + "";				
					intUser = guanRepo.updateGuaranForReverse(strCocamaremarks, strG1id);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
				}
				
				if (!strG2id.equals("")) {
//					strQuerypart = "id = '" + strG2id + "'";
//					strQuery = "UPDATE Guarantor set coapproval = '', cooapproval = '', revremarks = '" + strCocamaremarks + "'  where " + strQuerypart + "";				
					intUser = guanRepo.updateGuaranForReverse(strCocamaremarks, strG2id);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
				}
				
				if (!strProp.equals("")) {
//					strQuerypart = "proposalno = '" + strProp + "'";
//					strQuery = "UPDATE Loan set coapproval = '', cooapproval = '', revremarks = '" + strCocamaremarks + "'  where " + strQuerypart + "";				
					intUser = loanRepo.updateLoanForReverse(strCocamaremarks, strProp);
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
					
//					strQuery = "UPDATE Proposal set coapproval = '', cooapproval = '', revremarks = '" + strCocamaremarks + "'  where " + strQuerypart + "";				
					intUser = propRepo.updateProposalForReverse(strCocamaremarks, strProp); 
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
					
					intUser = riderRepo.updateRiderForReverse(strCocamaremarks, strProp); 
					if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
				}
			}
		} else if (strActivity.equals("DP")) {				
//			strQuerypart = "proposalno = '" + strId + "'";
						
//			strQuery = "UPDATE Proposal set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";				
			intUser = propRepo.updatePropForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strId); 
			if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
			
//			strQuery = "UPDATE Loan set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";				
			intUser = loanRepo.updateLoanForCoo(strCocam, strCocamaremarks, strLoginuser, strCAMARequestdatetime, strId); 
			if (intUser == 1) {	strMsg = strActivity + " updated successfully";	}
		}
		response.setData(strMsg);
		return response;
	}

}
