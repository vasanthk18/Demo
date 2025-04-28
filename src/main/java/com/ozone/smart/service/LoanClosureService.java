package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.AgreementCountDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class LoanClosureService {
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private PaymentScheduleRepo payRepo;

	public Response<String> getLoanClosure(String agreeNo, String propNo, String userName) {
		
		String strMsg = "";
		String strAgreementno = "";	
		String strProposalno = "";
		String strLoginuser = "";
			
		String strRequestdatetime = "";
		
		strAgreementno = agreeNo;
		strProposalno = propNo;
		strLoginuser = userName;
		
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
//			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();				
			
			try {
//				strQuery = "Update Loan set normalclosure = true, closureuser = '" + strLoginuser + "', closuredatetime = '" + strRequestdatetime + "' where proposalno = '" + strProposalno + "'";
				int intUser = loanRepo.updateLoanForNormalClosure(strLoginuser, strRequestdatetime, strProposalno);
				if (intUser > 0) {
					strMsg = "Loan " + strProposalno + " closed successfully";
				}							
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error while saving Loan Closure for : " + strAgreementno);
				response.setErrorMsg("Error while saving Loan Closure for : " + strAgreementno);
			}			
		}
	
		response.setData(strMsg);
		return response;
	}

//	public Response<String> getAgreement() {
//		List<String> vehList = propRepo.getCustForDisburse();
//		Response<List<ProposalDto>> response = new Response<>();
//		List<ProposalDto> PropDtoList = new ArrayList<>();
//		
//		for(String prop : vehList) {
//			ProposalDto propDto = new ProposalDto();
//			propDto.setCustomerid(prop);
//			PropDtoList.add(propDto);
//			response.setData(PropDtoList);
//		}	
//		return response;
//	}

	public List<AgreementCountDto> findAgreementCount() {
		return payRepo.findAgreementCount();
	}

}
