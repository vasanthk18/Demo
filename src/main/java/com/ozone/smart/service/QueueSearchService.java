package com.ozone.smart.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Auctionsale;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.DocSubmission;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.Preclosure;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Reposession;
import com.ozone.smart.repository.AuctionSaleRepo;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.DocSubmitRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.PreClosureRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.ReposessionRepo;

@Service
public class QueueSearchService {
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private DocSubmitRepo docRepo;
	
	@Autowired
	private GuarantorRepo guaranRepo;
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private CustomerVehicleRepo custVehicleRepo;
	
	@Autowired
	private ImpoundedStockRepo impoundStockRepo;
	
	@Autowired
	private ReposessionRepo reposessRepo;
	
	@Autowired
	private AuctionSaleRepo auctionRepo;
	
	@Autowired
	private PreClosureRepo preclosureRepo;

	public Response<String> viewByCustId(String id, String flag) {
		String strCustid = "";
		String strMsg[];
		String strMsgs = "";
		String strQuery = "";
		String strQuery1 = "";
		String strQuery2 = "";
		
		String strFirstname = "";
		String strSurname = "";
		String strGuarantype = "";
		boolean blnTvverified = false;
		boolean blnFiverified = false;
		boolean blnCqc = false;
		boolean blnDcsubmit = false;
		boolean blnDcupload = false;
		String strCoapproval = "";
		String strCooapproval = "";
		String strGfirstname = "";
		String strGsecondname = "";
		String strGname = "";
		boolean blnFirstguarantor = false;
		boolean blnSecondguarantor = false;
		boolean blnGcqc = false;
		boolean blnGtvverified = false;
		boolean blnGfiverified = false;
		String strGcoapproval = "";
		String strGcooapproval = "";
		
		String strTvdate = "";
		String strFidate = "";
		String strCqcdate = "";
		String strDcsubmitdate = "";
		String strDcuploaddate = "";
		String strGtvdate = "";
		String strGfidate = "";
		String strGCqcdate = "";
		
		Response<String> response = new Response<>();
		
		strCustid = id;
		
		if (strCustid == null || strCustid.length() == 0) {			
			strMsgs = "";
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

			//strQuery = "From vwCustGuarStatus where custid = '" + strCustid + "'";
//			strQuery = "From CustomerDetails where otherid = '" + strCustid + "'";
			strQuery1  = "From Guarantor where custid = '" + strCustid + "' order by firstguarantor desc";
//			strQuery2 = "FROM docsubmission where custid = '" + strCustid + "'";
			
			
			try {
				System.out.println("customerId "+strCustid);
				CustomerDetails customerstatus = custRepo.findByotherid(strCustid);
				
//				for (CustomerDetails custstat:customerstatus) {
						
					strFirstname = customerstatus.getFirstname();
					strSurname = customerstatus.getSurname();
					blnTvverified = customerstatus.getTvverified();
					blnFiverified = customerstatus.getFiverified();
					blnCqc = customerstatus.getCqc();
					blnDcsubmit = customerstatus.getDcsubmit();
					strCoapproval = customerstatus.getCamdatetime();
					if (strCoapproval == null) {strCoapproval = "";}
					strCooapproval = customerstatus.getCamadatetime();
					if (strCooapproval == null) {strCooapproval = "";}
					strTvdate = customerstatus.getTvdatetime();
					if (strTvdate == null) {strTvdate = "";}
					strFidate = customerstatus.getFidatetime();
					if (strFidate == null) {strFidate = "";}
					strCqcdate = customerstatus.getCqcdatetime();
					if (strCqcdate == null) {strCqcdate = "";}
//				}
				try {
					DocSubmission docsubmission = docRepo.getDocByCustId(strCustid);
//					for (DocSubmission docsub:docsubmission) {
					if(docsubmission != null) {
						strDcsubmitdate = docsubmission.getSubdatetime();
						if (strDcsubmitdate == null) {strDcsubmitdate = "";}
						strDcuploaddate = docsubmission.getUpdatetime();
						if (strDcuploaddate == null) {strDcuploaddate = "";}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
								
				List<Guarantor> guaranstatus = guaranRepo.findByCustidOrderByFirstguarantorDesc(strCustid);
				strMsg = new String[guaranstatus.size()];
				
				int i = 0;		
				
				if (guaranstatus.size() > 0) {
					for (Guarantor gstat:guaranstatus) {
						strGfirstname = gstat.getFirstname();
						strGsecondname = gstat.getSurname();
						blnFirstguarantor = gstat.getFirstguarantor();
						blnSecondguarantor = gstat.getSecondguarantor();
						blnGcqc = gstat.getCqc();
						blnGtvverified = gstat.getTvverified();
						blnGfiverified = gstat.getFiverified();
						strGcoapproval = gstat.getCamdatetime();
						if (strGcoapproval == null) {strGcoapproval = "";}
						strGcooapproval = gstat.getCamadatetime();
						if (strGcooapproval == null) {strGcooapproval = "";}
						strGtvdate = gstat.getTvdatetime();
						if (strGtvdate == null) {strGtvdate = "";}
						strGfidate = gstat.getFidatetime();
						if (strGfidate == null) {strGfidate = "";}
						strGCqcdate = gstat.getCqcdatetime();
						if (strGCqcdate == null) {strGCqcdate = "";}
						
						strGname = strGsecondname + " " + strGfirstname;
						if (blnFirstguarantor == true) {
							strGuarantype = "1st Guarantor";
						} else if (blnSecondguarantor == true) {
							strGuarantype = "2nd Guarantor";
						}
						
						strMsg[i]= strCustid + "|" + strFirstname + "|" + strSurname + "|" + strDcsubmitdate + "|" + strDcuploaddate + "|" + strCqcdate + "|" + 
								strTvdate + "|" + strFidate + "|" + strCoapproval + "|" + strCooapproval + "|" + strGname + "|" +
								strGuarantype + "|" + strGCqcdate + "|" + strGtvdate + "|" + strGfidate + "|" +
								strGcoapproval + "|" + strGcooapproval;
						
						//strMsg[i]= strCustid + "|" + strFirstname + "|" + strSurname + "|" + blnDcsubmit + "|" + blnDcupload + "|" + blnCqc + "|" + 
						//		blnTvverified + "|" + blnFiverified + "|" + strCoapproval + "|" + strCooapproval + "|" + strGname + "|" +
						//		strGuarantype + "|" + blnGcqc + "|" + blnGtvverified + "|" + blnGfiverified + "|" +
						//		strGcoapproval + "|" + strGcooapproval;
						
						i++;
					}
				} else {
					strMsg = new String[1];
					
					strMsg[i]= strCustid + "|" + strFirstname + "|" + strSurname + "|" + blnDcsubmit + "|" + blnDcupload + "|" + strCqcdate + "|" + 
							strTvdate + "|" + strFidate + "|" + strCoapproval + "|" + strCooapproval + "|" + strGname + "|" +
							strGuarantype + "|" + strGname + "|" + strGname + "|" + strGname + "|" + 
							strGcoapproval + "|" + strGcooapproval;
				}
				
				strMsgs = Arrays.toString(strMsg);
								
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsgs = "Error retreiving details for cust id: " + strCustid;
			}			
		}
		response.setData(strMsgs);
		return response;
	}

	public Response<String> viewProposalByCustId(String id, String flag) {
		String strCustid = "";
		String strMsg[];
		String strMsgs = "";
		String strQuery = "";
		String strQuery1 = "";
		String strQuery2 = "";
		String strQuery3 = "";
		
		String strLnno = "";
		String strPropno = "";
		String strCoapproval = "";
		String strCooapproval = "";
		String strRevremarks = "";
	
		String strLncam = "";
		String strLncama = "";
		String strLnrevremarks = "";
		String strVehregno = "";
		String strAgup = "";		
		
		String strCqcdate = "";
		String strLncqcdate = "";
		String strDisbdate = "";
		
		String strAgreementno = "";
		String strImp = "";
		String strRepo = "";
		String strAuc = "";
		String strPreclosed = "";
		String strClosed = "";
		
		boolean blnCqc = false;
		boolean blnLncqc = false;	
		boolean blnImp = false;
		boolean blnRepo = false;
		boolean blnAuc = false;
		boolean blnPreclosed = false;
		boolean blnClosed = false;
		boolean blnDisb = false;
		
		int intScheduled = 0;	
		int intAgserial = 0;
		
		strCustid = id;
		
		Response<String> response = new Response<>();
		
		if (strCustid == null || strCustid.length() == 0) {			
			strMsgs = "";
		} else {
			
//			strQuery = "From Proposal where customerid = '" + strCustid + "' order by proposalno";
			
			try {
				List<Proposal> propstat = propRepo.getProposalByCustId(strCustid);
				strMsg = new String[propstat.size()];
				
				int i = 0;	
				if (propstat.size() > 0) {
					for (Proposal props:propstat) {
						strPropno = props.getProposalno();
						blnCqc = props.getCqc();
						strCqcdate = props.getCqcdatetime();
						if (strCqcdate == null) {strCqcdate = "";}
						strCoapproval = props.getCamdatetime();
						if (strCoapproval == null) {strCoapproval = "";}
						strCooapproval = props.getCamadatetime();
						if (strCooapproval == null) {strCooapproval = "";}
						strRevremarks = props.getRevremarks();
						
//						strQuery1 = "From Loan where proposalno = '" + strPropno + "'";
						List<Loan> loanstat = loanRepo.findByproposalno(strPropno);
						
						if (loanstat.size() > 0) {
							for (Loan lnstat:loanstat) {
								strLnno = lnstat.getProposalno();
								blnLncqc = lnstat.getCqc();
								strLncqcdate  =lnstat.getCqcdatetime();
								if (strLncqcdate == null) {strLncqcdate = "";}
								strLncam = lnstat.getCamdatetime();
								if (strLncam == null) {strLncam = "";}
								strLncama = lnstat.getCamadatetime();
								if (strLncama == null) {strLncama = "";}
								intScheduled = lnstat.getScheduled();
								strLnrevremarks = lnstat.getRevremarks();
								blnImp = lnstat.getImpounded();
								blnRepo = lnstat.getReposessed();
								blnAuc = lnstat.getAuctioned();
								blnPreclosed= lnstat.getPreclosed();
								strClosed = lnstat.getClosuredatetime();
								if (strClosed == null) {strClosed = "";}
								
//								strQuery2 = "From CustomerVehicle where proposalno = '" + strPropno + "'";
								CustomerVehicle custvehstat = custVehicleRepo.findByproposalno(strPropno);
								
								if (custvehstat != null) {
//									for (CustomerVehicle cvstat:custvehstat) {
										intAgserial = custvehstat.getAgreementserial();
										strVehregno = custvehstat.getVehicleregno();
										blnDisb = custvehstat.getDisbursed();
										strDisbdate = custvehstat.getDisbdatetime();
										if (strDisbdate == null) {strDisbdate = "";}
										strAgup = custvehstat.getAgreementfilename();
//									}
									
									strAgreementno = "AN" + intAgserial;
									
									//*****************Start of collectiong imp, repo, auc, preclosed, closed datetime **************
									if (blnImp == true) {
//										strQuery3 = "From Impoundedstock where agreementno = '" + strAgreementno + "'";
										List<Impoundedstock> impstock =impoundStockRepo.getByAgreeNo(strAgreementno);
										
										if (impstock.size() > 0) {
											for (Impoundedstock is:impstock) {
												strImp = is.getCapturedatetime();
											}
										}
									} else {strImp = "";}
									
									if (blnRepo == true) {
//										strQuery3 = "From Reposession where agreementno = '" + strAgreementno + "'";
										List<Reposession> reposess = reposessRepo.findByAgree(strAgreementno) ;
										
										if (reposess.size() > 0) {
											for (Reposession repo:reposess) {
												strRepo = repo.getReposessdatetime();
											}
										}
									} else {strRepo = "";}

									if (blnAuc == true) {
										strQuery3 = "From Auctionsale where agreementno = '" + strAgreementno + "'";
										List<Auctionsale> aucsale = auctionRepo.findByAgree(strAgreementno);
										
										if (aucsale.size() > 0) {
											for (Auctionsale as:aucsale) {
												strAuc = as.getAucdatetime();
											}
										}
									} else {strAuc = "";}
									
									if (blnPreclosed == true) {
										strQuery3 = "From Preclosure where agreementno = '" + strAgreementno + "'";
										List<Preclosure> preclosure = preclosureRepo.findByAgree(strAgreementno);
										
										if (preclosure.size() > 0) {
											for (Preclosure prec:preclosure) {
												strPreclosed = prec.getPreclosuredatetime();
											}
										}
									} else {strPreclosed = "";}
									//*****************End of collectiong imp, repo, auc, preclosed, closed datetime **************
									
								} else {
									intAgserial = 0;
									strVehregno = "";
									blnDisb = false;
									strAgup = "";
								}
							}
							
							if (intAgserial == 0) {
								//strMsg[i]= strPropno + "|" + blnCqc + "|" + strCoapproval + "|" + strCooapproval + "|" + strRevremarks + "|" + blnLncqc + "|" + 
								//		strLncam + "|" + strLncama + "|" + intScheduled + "|" + "" + "|" + blnImp + "|" + blnRepo + "|" + 
								//		blnAuc + "|" + blnPreclosed + "|" + blnClosed + "|" + "" + "|" + "" + "|" + strLnrevremarks;
								strMsg[i]= strPropno + "|" + strCqcdate + "|" + strCoapproval + "|" + strCooapproval + "|" + strRevremarks + "|" + strLncqcdate + "|" + 
										strLncam + "|" + strLncama + "|" + strDisbdate + "|" + "" + "|" + strImp + "|" + strRepo + "|" + 
										strAuc + "|" + strPreclosed + "|" + strClosed + "|" + "" + "|" + "" + "|" + strLnrevremarks;
							} else {
								strMsg[i]= strPropno + "|" + strCqcdate + "|" + strCoapproval + "|" + strCooapproval + "|" + strRevremarks + "|" + strLncqcdate + "|" + 
										strLncam + "|" + strLncama + "|" + strDisbdate + "|" + strDisbdate + "|" + strImp + "|" + strRepo + "|" + 
										strAuc + "|" + strPreclosed + "|" + strClosed + "|" + intAgserial + "|" + strVehregno + "|" + strLnrevremarks;
							}
														
						} else {
							strMsg[i]= strPropno + "|" + strCqcdate + "|" + strCoapproval + "|" + strCooapproval + "|" + strRevremarks + "|" + "" + "|" + 
									"" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + 
									"" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "";
						}
						i++;
					}
				} else {
					strMsg = new String[1];
					strMsg[i]= strPropno + "|" + strPropno + "|" + strCoapproval + "|" + strCooapproval + "|" + strRevremarks + "|" + strPropno + "|" + 
							strLncam + "|" + strLncama + "|" + strDisbdate + "|" + strLnrevremarks + "|" + strPropno + "|" + strPropno + "|" + 
							strPropno + "|" + strPropno + "|" + strPropno + "|" + intAgserial + "|" + strVehregno + "|" + strPropno;
				}
				
				strMsgs = Arrays.toString(strMsg);
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsgs = "Error retreiving details for cust id: " + strCustid;
			}			
		}
		response.setData(strMsgs);
		return response;
	}

}
