package com.ozone.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.InstallmentsComponent;
import com.ozone.smart.component.InsuranceFeeComponent;
import com.ozone.smart.dto.CqcDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Insurance;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.VehicleMaster;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.InsuranceRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.repository.VehicleMasterRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class CqcService {
	
	@Autowired
	private CustomerRepo custRepo;
	@Autowired
	private GuarantorRepo guaranRepo;
	@Autowired
	private ProposalRepo propRepo;
	@Autowired
	private LoanEntryRepo loanRepo;
	@Autowired
	private RiderRepo riderRepo;
	@Autowired
	private VehicleMasterRepo vmRepo;
	
	@Autowired
	private InsuranceRepo insRepo;
	
	@Autowired
	private InstallmentsComponent instComp;
	
	@Autowired
	private InsuranceFeeComponent insComp;
	

	public Response<String> updateCqc(CqcDto cqcDto) {
		
		Response<String> response = new Response<>();
		String strQuery = "";
		String strMsg = "";
		
		boolean blnCqc = false;
		String strCqc = "";
		String strCqcremarks = "";
		String strActivity = "";
		String strId = "";
		String strQuerypart = "";
		String strVehicle = "";
		String strLnamount = "";
		String strNoinst = "";
		String strDp = "";
		String strTrckFee = "";
		String strIrr = "";
		String strInsfee = "";
		String strEwi = "";
		String strPaymode = "";
		
		String strTenure = "";
		String strTrckfee = "";
		String strRate = "";
		String strPmode = "";
		
		String strDepreciation = "";
		String strOrate = "";
		String strFirstvalue = "";
		String strSecondvalue = "";
		String strThirdvalue = "";
		
		String insFee = "";
		String strTrackerfee = "";
		String strDiscount = "";
		
		int intTrackerfee = 0;
		int intTenure = 0;
		
		String strCQCRequestdatetime = "";
		String strLoginuser = "";
		
		int intUser = 0;
		
		strId = cqcDto.getId();
		strActivity = cqcDto.getActivity();
		strCqc = cqcDto.getCqc();
		strCqcremarks = cqcDto.getRemarks();
		strVehicle = cqcDto.getVehicle();
		strLnamount = cqcDto.getLoan(); 
		strNoinst = cqcDto.getInst();
		strDp = cqcDto.getDp(); 
		strTrckFee = cqcDto.getTrkfee(); 
		strIrr = cqcDto.getIrr(); 
		strInsfee = cqcDto.getIns(); 
		strEwi = cqcDto.getEwi(); 
		strPaymode = cqcDto.getPaymode();
		strLoginuser = cqcDto.getUsername();
		
		strId = strId.trim();
		strActivity = strActivity.trim();
		System.out.println(strActivity);
		strCqc = strCqc.trim();
		strCqcremarks = strCqcremarks.trim();
		if(strVehicle != null) {strVehicle = strVehicle.trim();}
		if(strLnamount != null) {strLnamount = strLnamount.trim();}
		if(strNoinst != null) {strNoinst = strNoinst.trim();}
		if(strDp != null) {strDp = strDp.trim();}		
		if(strTrckFee != null) {strTrckFee = strTrckFee.trim();}
		if(strIrr != null) {strIrr = strIrr.trim();}
		if(strInsfee != null) {strInsfee = strInsfee.trim();}
		if(strEwi != null) {strEwi = strEwi.trim();}
		if(strPaymode != null) {strPaymode = strPaymode.trim();}
		strLoginuser = strLoginuser.trim();
				
		if (strCqc.equals("Yes")) {
			blnCqc = true;
		} else {
			blnCqc = false;
		}	
		
		TimeStampUtil gts = new TimeStampUtil();		
		strCQCRequestdatetime = gts.TimeStamp();
		
		
		switch (strActivity) {
        case "CD":
            strQuerypart = strId;
            intUser = custRepo.updateCqcDetails(blnCqc, strCqcremarks, strLoginuser, strCQCRequestdatetime, strQuerypart);
            break;
        case "G":
            strQuerypart = strId;
            intUser = guaranRepo.updateCqcDetails(blnCqc, strCqcremarks, strLoginuser, strCQCRequestdatetime, strQuerypart);
            break;
            
        case "RR":
            strQuerypart = strId;
            intUser = riderRepo.updateCqcDetails(blnCqc, strCqcremarks, strLoginuser, strCQCRequestdatetime, strQuerypart);
            break;
        case "DP":
            strQuerypart = strId;
            intUser = propRepo.updateCqcDetails(blnCqc, strCqcremarks, strLoginuser, strCQCRequestdatetime, strQuerypart);
            
            strVehicle = strVehicle.replace("::", "|");
//			strQuery = "From VehicleMaster where brand || ' | ' || model  || ' | ' || cc  || ' | ' || color = '" + strVehicle + "'";
			
			List<VehicleMaster> vehmaster = vmRepo.findByBrandModelCcAndColor(strVehicle);
			
			for (VehicleMaster vehm:vehmaster) {
				strDiscount = vehm.getDiscount();
			}
			
			intUser =propRepo.updateProposal(strLnamount, strDp, strVehicle, strDiscount, strLoginuser, strCQCRequestdatetime, strId);
			if (intUser > 0) { // recalculate 
//				strQuery = "From Loan where proposalno = '" + strId + "'";
				
				
				List<Loan> loan = loanRepo.findByproposalno(strId);	
				
				if (loan.size() > 0) {
					for (Loan ln:loan) {
						strTenure = ln.getNoofinstallments();
						strTrckfee = ln.getTrackerfee();
						strRate = ln.getInterestrate();
						strPmode = ln.getPaymentmode();
					}
					intTenure = Integer.parseInt(strTenure);
				
					List<Insurance> insurance = insRepo.findAll() ;	
							
					for (Insurance insure:insurance) {
						strDepreciation = insure.getAssetdepreciation();
						strOrate = insure.getRate();
						strFirstvalue = insure.getFirstvalue();
						strSecondvalue = insure.getSecondvalue();
						strThirdvalue = insure.getThirdvalue();
					}
					
					strLnamount = strLnamount.replace(",", "");			
					strDp = strDp.replace(",", "");
					
					//recalculate insurance
					insFee = insComp.getFee(strLnamount, strDepreciation, strOrate, strFirstvalue, 
							strSecondvalue, strThirdvalue, strTenure, strPmode);
					
					//recalculate installment
					strEwi = instComp.ewi(strRate, strLnamount, strTenure, strDp, insFee, 
							strTrckfee, strPmode);
					double dblStampduty = Double.parseDouble(strLnamount) *0.01;
					double othercost = (dblStampduty + intTrackerfee)/ Double.parseDouble(strTenure);
					double dbloldintstallment= Double.parseDouble(strEwi);//old installment amount
					 
					double dblnewInstallmentamt = Math.round((dbloldintstallment + othercost) / 1000) * 1000;//new installment amount
					strEwi = Double.toString(dblnewInstallmentamt);
					
					
					 intUser =  loanRepo.updateLoan(strLnamount, strDp, insFee, strEwi, strLoginuser, strCQCRequestdatetime, strId);					
					if (intUser > 0) 
					{ 
						System.out.println("DP update, Loan updated successfully");
					}
				}			
			}
            break;
        case "LN":
            strQuerypart = strId ;
//            intUser =loanRepo.updateCqcDetails(blnCqc, strCqcremarks, strLoginuser, strCQCRequestdatetime, strQuerypart);
            try {
//            	intUser = loanRepo.updateLoanwithinst(strLnamount, strNoinst, strDp, strTrckfee, strIrr, strPaymode, strInsfee, strEwi, strLoginuser, strCQCRequestdatetime, strId);
            	intUser = loanRepo.updateLoanDetails(blnCqc, strCqcremarks, strLnamount, strNoinst,
                        strDp, strTrckFee, strIrr, strPaymode, strInsfee, strEwi,
                        strLoginuser, strCQCRequestdatetime,strQuerypart);
            }
            catch(Exception e) {
            	intUser=0;
            	e.printStackTrace();
            }
            break;
        default:
            // Handle unsupported activity
            break;
    }
		if (intUser == 1) {
			System.out.println(strActivity);
			if(strActivity.equals("G")) {
				response.setData ("Guarantor updated successfully");
			}else if(strActivity.equals("DP")) {
				response.setData ("Proposal updated successfully");
			}else if(strActivity.equals("CD")){
				response.setData ("Customer updated successfully");
			}else if(strActivity.equals("RR")){
				response.setData ("Rider updated successfully");
			}else {
				response.setData("Loan updated successfully");
			}
		} else {
			response.setData(strActivity + " failed to update");
		} 
		return response;
	}

}
