package com.ozone.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.InstallmentsComponent;
import com.ozone.smart.component.InsuranceFeeComponent;
import com.ozone.smart.dto.LoanEntryDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Insurance;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.repository.InsuranceRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class LoanEntryService {
	
	@Autowired
	private LoanEntryRepo loanEntryRepo;
	
	@Autowired
	private ProposalRepo proposalRepo;
	
	@Autowired
	private InsuranceRepo insuranceRepo;
	
	@Autowired
	private InstallmentsComponent instComponent;
	
	@Autowired
	private InsuranceFeeComponent insuComponent;

	public Response<String> addLoan(LoanEntryDto loanEntryDto) {
		Response<String> response = new Response<>();
		
		String strPropno, strCustid, strLnamt, strDownpaymnt, strIrr, strPaymode, strTckfee,
		strInstmnts, strInsurance, strEwi;
		String strQuery = "";
		String strRequestdatetime = "";
		String strLoginuser = "";
	
		int intUser = 0;
				
		strPropno = loanEntryDto.getProposalno(); 
		strCustid = loanEntryDto.getCustid();
		strLnamt = loanEntryDto.getLoanamount();
		strDownpaymnt = loanEntryDto.getDownpayment();
		strIrr = loanEntryDto.getInterestrate();
		strPaymode = loanEntryDto.getPaymentmode();
		strTckfee = loanEntryDto.getTrackerfee();
		strInstmnts = loanEntryDto.getNoofinstallments();
		strInsurance = loanEntryDto.getInsurancefee();
		strEwi = loanEntryDto.getEwi();
		strLoginuser = loanEntryDto.getCaptureuser();
		
		TimeStampUtil gts = new TimeStampUtil();		
		strRequestdatetime = gts.TimeStamp();
					
		Loan ln = new Loan();
		ln.setProposalno(strPropno);
		ln.setCustid(strCustid);
		ln.setFirstguarantorid("");
		ln.setSecondguarantorid("");
		ln.setLoanamount(strLnamt);
		ln.setInterestrate(strIrr);
		ln.setPaymentmode(strPaymode);
		ln.setEwi(strEwi);
		ln.setNoofinstallments(strInstmnts);
		ln.setDownpayment(strDownpaymnt);
		ln.setInsurancefee(strInsurance);
		ln.setTrackerfee(strTckfee);
		ln.setCaptureuser(strLoginuser);
		ln.setCapturedatetime(strRequestdatetime);
		System.out.println("strloan entry:: "+strEwi);
		try {
			loanEntryRepo.save(ln);
			
			//update Proposal for dp amount
			strQuery = "UPDATE Proposal set downamount = '" + strDownpaymnt + "' where proposalno = '" + strPropno + "'";
			Proposal prop =  proposalRepo.findByproposalno(strPropno);
			prop.setDownamount(strDownpaymnt);
							
			response.setData( "Loan for proposal : " + strPropno + " saved successfully.");
		} catch (Exception e) {
			Throwable th = e.getCause();
	        System.out.println("THROWABLE INFO: " + th.getCause().toString());
	         
			String Msg = th.getCause().toString();
			if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
				response.setData( "Loan : " + strPropno + " saved successfully / Already exists");
			} else if (Msg.contains("The database returned no natively generated identity value")) {
				response.setData( "Guarantor : " + strPropno + " saved successfully / Already exists");
			} else if (Msg.contains("ConstraintViolationException")) {
				response.setData( "Loan : " + strPropno + " saved successfully / Already exists");
			} else {
				response.setData( "General error");
			}
		}				
		return response;
	}

	public Response<String> calInsurance(LoanEntryDto loanEntryDto) {
		
		Response<String> response = new Response<>();
		
		String strQuery = "";
		String strDepreciation = "";
		String strRate = "";
		String strFirstvalue = "";
		String strSecondvalue = "";
		String strThirdvalue = "";
		String insFee = "";
		String strEwi = "";
		String strTenure = "";
		String strAssetcost = "";
		String strDownpayment = "";
		String strTrackerfee = "";
		String lnRate = "";
		String strPaymode = "";
		
		int intTrackerfee = 0;
		int intTenure = 0;
		
		String strMsg = "";
		
		strAssetcost = loanEntryDto.getLoanamount();
		strTenure = loanEntryDto.getNoofinstallments();
		strDownpayment = loanEntryDto.getDownpayment();
		lnRate = loanEntryDto.getInterestrate();
		strPaymode = loanEntryDto.getPaymentmode();
		
		if (strAssetcost == null || strAssetcost.length() == 0) {
			
			response.setData("Please select proposal");
		
		} else if (strDownpayment == null || strDownpayment.length() == 0) {
			
			response.setData("Please select proposal");
			
		} else if (strPaymode == null || strPaymode.length() == 0) {
			
			response.setData("Please select payment mode");
		
		} else if (strTenure == null || strTenure.length() == 0) {
			
			response.setData("Please enter tenure");
		
		} else {
		
			intTenure = Integer.parseInt(strTenure);
				
			List<Insurance> insurance = insuranceRepo.findAll();
					
			for (Insurance insure:insurance) {
				strDepreciation = insure.getAssetdepreciation();
				strRate = insure.getRate();
				strFirstvalue = insure.getFirstvalue();
				strSecondvalue = insure.getSecondvalue();
				strThirdvalue = insure.getThirdvalue();
			}
			
			strAssetcost = strAssetcost.replace(",", "");			
			strDownpayment = strDownpayment.replace(",", "");
			
			insFee = insuComponent.getFee(strAssetcost, strDepreciation, strRate, strFirstvalue, 
					strSecondvalue, strThirdvalue, strTenure, strPaymode);
	
			if (strPaymode.equals("Weekly")) {
				intTrackerfee = 27000* (intTenure * 7) / 30;
			} else if (strPaymode.equals("Monthly")) {
				intTrackerfee = 27000 * intTenure;
			}
			
			strTrackerfee = Integer.toString(intTrackerfee);
			
			
			strEwi = instComponent.ewi(lnRate, strAssetcost, strTenure, strDownpayment, insFee, 
					strTrackerfee, strPaymode);
			
			double dblStampduty = Double.parseDouble(strAssetcost) *0.01;
			double othercost = (dblStampduty + intTrackerfee)/ Double.parseDouble(strTenure);
			double dbloldintstallment= Double.parseDouble(strEwi);//old installment amount
			 
			double dblnewInstallmentamt = Math.round((dbloldintstallment + othercost) / 1000) * 1000;//new installment amount
			
			//request.setAttribute("insurancefee", insFee);	
			response.setData(insFee + "|" + dblnewInstallmentamt + "|" + lnRate + "|" + strTrackerfee);
		}
		return response;
	}

//	(load proposal)
	public Response<String> viewLoanByPropNo(String propno, String flag) {
		
		Response<String> response = new Response<>();
		
		String strProposalno = "";	
		String strMsg = "";
		String strFlag = "";
		String strLoanamount = "";
		String strIrr = "";
		String strPaymode = "";
		String strTenure = "";
		String strDownpayment = "";
		String strInsfee = "";
		String strTrackerfee = "";		
		String strCqcremarks = "";
		String strCqc = "";
		String strCoappremarks = "";
		String strCoapp = "";
		String strEwi = "";
		String strsrremarks="";
		
		boolean blnCqc = false;
				
		strProposalno = propno;
		strFlag = flag;
		strProposalno = strProposalno.trim();
		
		if (strProposalno == null || strProposalno.length() == 0) {			
			response.setData("Select proposal no");
		} else {
			try {
				List<Loan> loan = loanEntryRepo.findByProposalNoWithFlag(strProposalno, strFlag);
				for (Loan ln:loan) {
					strLoanamount = ln.getLoanamount();
					strIrr = ln.getInterestrate();
					strPaymode = ln.getPaymentmode();
					strTenure = ln.getNoofinstallments();
					strDownpayment = ln.getDownpayment();
					strInsfee = ln.getInsurancefee();
					strTrackerfee = ln.getTrackerfee();	
					strCqcremarks = ln.getCqcremarks();
					blnCqc = ln.getCqc();
					strCoapp = ln.getCoapproval();
					strCoappremarks = ln.getCoremarks();	
					strEwi = ln.getEwi();
					strsrremarks=ln.getSrremarks();
				}
				if (blnCqc == true) {strCqc = "Recommended";} else {strCqc = "Not Recommended";}				
				response.setData(strLoanamount + "|" + strIrr + "|" + strPaymode + "|" + strTenure + "|"
						+ strDownpayment + "|" + strInsfee + "|" + strTrackerfee + "|" + strCqc + "|"
						+ strCqcremarks + "|" + strCoapp + "|" + strCoappremarks + "|" + strEwi + "|" + strsrremarks); 		
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error retrieving loan for proposal id: " + strProposalno);
				response.setErrorMsg("Error retrieving loan for proposal id: " + strProposalno);
			}
		}
		return response;
	}
	
	public Response<LoanEntryDto> getProposalsLoanDetails(String custId) {

		String strCustomer = "";

		strCustomer = custId;
		Loan loan = loanEntryRepo.getLatestLoanByCustId(strCustomer);

		Response<LoanEntryDto> response = new Response<>();

		if (loan != null) {
			LoanEntryDto loanEntryDto = new LoanEntryDto();

			loanEntryDto.setEwi(loan.getEwi());
			loanEntryDto.setNoofinstallments(loan.getNoofinstallments());

			response.setData(loanEntryDto);
			response.setResponseCode(200); // HTTP OK
			response.setErrorMsg(null); // No error

		} else {
			response.setData(null);
			response.setResponseCode(404); // HTTP Not Found
			response.setErrorMsg("No proposal found for the given customer ID");
		}

		return response;
	}

}
