package com.ozone.smart.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.TotalDue;
import com.ozone.smart.component.buckets;
import com.ozone.smart.dto.PtpDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.LoanStatus;
import com.ozone.smart.entity.Ptp;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.LoanStatusRepo;
import com.ozone.smart.repository.PtpRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.fieldValidation;
import com.ozone.smart.util.formatDigits;

@Service
public class BucketService {
	
	@Autowired
	private PtpRepo ptpRepo;
	
	@Autowired
	private LoanStatusRepo loanStatusRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private buckets buck;
	
	@Autowired
	private TotalDue totalDue;

	public Response<String> saveBucket(PtpDto ptpDto) {
		
		String strRequestdatetime, strDate;
		String strMsg="";
		String strQuery = "";
		String strAgreementno = "";
		String strCustomer = "";
		String strMobile = "";
		String strInstallment = "";
		String strBucket = "";
		String strPtpdate = "";
		String strPtpremark = "";
		String strLoginuser = "";
		
		boolean blnDatevalid = false;
		
		Response<String> response = new Response<>();
		
		strAgreementno = ptpDto.getAgreementno();
		strCustomer = ptpDto.getCustomername();
		strMobile = ptpDto.getMobile();
		strInstallment = ptpDto.getInstallment();
		strBucket = ptpDto.getBucket();
		strPtpdate = ptpDto.getPtpdate();
		strPtpremark = ptpDto.getPtpremarks();
		strLoginuser = ptpDto.getCaptureuser();
		
		fieldValidation fv = new fieldValidation();
		
		if (fv.validateDate(strPtpdate) == "success") {
			blnDatevalid = true;
		} else {blnDatevalid = false;}
			
		if (strAgreementno == null || strAgreementno.length() == 0) {				
			strMsg = "Please select agreement";				
		} else if (strCustomer == null || strCustomer.length() == 0) {					
			strMsg = "Please select agreement";				
		} else if (blnDatevalid == false) {
			strMsg = "Please ensure date format is dd/mm/yyyy";
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			strDate = gts.standardDate();
										
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { System.out.println(e.getLocalizedMessage());
			 * }
			 */	
			
			
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
				
			Ptp	ptp = new Ptp();
			ptp.setAgreementno(strAgreementno);
			ptp.setCustomername(strCustomer);
			ptp.setMobile(strMobile);
			ptp.setInstallment(strInstallment);
			ptp.setBucket(strBucket);
			ptp.setPtpdate(strPtpdate);
			ptp.setPtpremarks(strPtpremark);
			ptp.setCaptureuser(strLoginuser);
			ptp.setCapturedatetime(strRequestdatetime);
			ptp.setCapturedate(strDate);
			
			try {
				ptpRepo.save(ptp);	
				strMsg = "Bucket for " + strAgreementno + " saved successfully";
			} catch (Exception e) {
				Throwable th = e.getCause();
			    System.out.println("THROWABLE INFO: " + th.getCause().toString());			         
				strMsg = th.getCause().toString();					
			}			
		}
		response.setData(strMsg);
		return response;
		
	}

	public Response<String> viewBucket(String agreeno) {
		
		String strMsg = "";
		String strQuery = "";
		
		String strAgreementno = "";
		String strProposalno = "";
		String strCustomername = "";
		String strMobileno = "";
		String strEwi = "";
		
		BigInteger intOverdueinst = null;
		String strOverdue = "";
		
		double dblEwi = 0;
		double dblInst = 0;
		double dblOverdue = 0;
		
		strAgreementno = agreeno;
		
		formatDigits fd = new formatDigits();
		
		
		Response<String> response = new Response<>();
		
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else {
			
//			strQuery = "From LoanStatus where agreementno = " + "'" + strAgreementno + "'";
			
			try {
				List<LoanStatus> loanstatus = loanStatusRepo.findByagreementno(strAgreementno);
				
				for (LoanStatus ls:loanstatus) {
					strProposalno = ls.getLoanid();
					strEwi = ls.getInstallment();
					intOverdueinst = ls.getOverdueinst();
				}	
				System.out.println("Proposal nUmber "+strProposalno);
//				strQuery = "From CustomerDetails where otherid in (Select customerid from Proposal where proposalno = " + "'" + strProposalno + "')";
				List<CustomerDetails> custdetails = custRepo.findByProposalNo(strProposalno);
				
				for (CustomerDetails custdet:custdetails) {
					strCustomername = custdet.getSurname() + " " + custdet.getFirstname();
					strMobileno = custdet.getMobileno();					
				}
				System.out.println("CustomerName "+ strCustomername + strMobileno);
				/*dblEwi = Double.parseDouble(strEwi);
				dblInst = intOverdueinst.doubleValue();
				dblOverdue = dblInst * dblEwi;
				
				strOverdue = Double.toString(dblOverdue);
				strOverdue = "(" + intOverdueinst + " * " + strEwi + ") : " + strOverdue;*/
				
//				TotalDue td = new TotalDue();
				strOverdue = totalDue.getTotalDue(strAgreementno);
				System.out.println(strOverdue);
				if(!strOverdue.contains(",")) {strOverdue = fd.digit(strOverdue);}
				
				strMsg = strCustomername + "|" +  strMobileno + "|" +  strEwi + "|" + strOverdue;
				System.out.println("Bucket Deetails "+strMsg);
			} catch (Exception e) {
				e.printStackTrace();
				strMsg = "Error details for agreement no: " + strAgreementno;
			}			
		}
		
		response.setData(strMsg);
		
		return response;
	}

	public String[] viewBuckets(String flag) {
//		buckets buck = new buckets();
		System.out.println("API hitted");
		return buck.Bucket(flag);
	}

}
