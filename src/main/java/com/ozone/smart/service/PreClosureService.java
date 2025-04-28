package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.LoanEntryDto;
import com.ozone.smart.dto.PreclosureDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Preclosure;
import com.ozone.smart.entity.pdfParams;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PdfParamRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.PreClosureRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class PreClosureService {
	
	@Autowired
	private CustomerVehicleRepo custVehicleRepo;
	
	@Autowired
	private ImpoundedStockRepo impRepo;
	
	@Autowired
	private LoanParamRepo lpRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private PreClosureRepo preClosureRepo;

	@Autowired
	private LoanEntryRepo LoanRepo;
	
	
	
	public Response<String> viewDetails(String agreeNo, String flag) {
		String strMsg = "";
		String strQuery = "";
		
		String strAgreementno = "";
		String strAgreementserial = "";
		String strAgreementqry = "";
		String strImpounddate = "";
		String strNewimpounddate = "";
		String strDate = "";
		String strProposalno = "";
		String strVehicle = "";
		
		String strCustid = "";
		String strCustomername = "";
		String strImpound = "0";
		String strFlag = "";
		
		String strInst = "0";
		
		String strPrincipal = "0";
		String strInt = "0";
		
		String strPenalty = "0";
		
		int intNoofinst = 0;
		int intOverdue = 0;
		int intImpound = 0;
		int intAgreement = 0;
		
		int intPennoofinst = 0;
		int intTotalpen = 0;
		int intTotaltopay = 0;
		
		int intfuturenoofinst = 0;
		int intfuturedue = 0;
		double dblfutureprincipal = 0;
		double dblfutureint = 0;
		double dblassetcost = 0;
		
		String strOverdue = "0";
		String strTotalpen = "0";
		String strTotaltopay = "0";
		String strFuturedue = "0";
		String strFutureprincipal = "0";
		String strFutureint = "0";
		String strAssetcost = "0";
		
		boolean blnPass = false;
		
		strAgreementno = agreeNo;
		strFlag = flag;
		strAgreementqry = strAgreementno + "%";
		strAgreementserial = strAgreementno.replace("AN", "");
		
		int Agreementserial = Integer.parseInt(strAgreementserial);
		
		Response<String> response = new Response<>();
		
		/*
		 * try { InitialContext ic = new InitialContext(); libraryBean =
		 * (easybodaPersistentBeanRemote)ic.lookup(
		 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
		 * ); } catch (NamingException e) { e.printStackTrace(); }
		 */
		
//		Session em = (Session)request.getAttribute("sh");
//		easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
		
		if (strFlag.equals("Noobjection")) {
//			strQuery = "From CustomerVehicle where agreementserial = " + strAgreementserial + " and proposalno in " + 
//					"(select proposalno from Loan where auctioned = true or preclosed = true or " +
//					"normalclosure = true)";
			List<CustomerVehicle> custveh = custVehicleRepo.findByAgreeSerialAndLoanStatus(strAgreementserial);
			if (custveh.size() > 0) {
				blnPass = true;
			} else {
				blnPass = false;
			}			
		
		} else if (strFlag.equals("Preclosure")) {
			blnPass = true;
		} else if (strFlag.equals("Normal")) {
			blnPass = true;
		} else {
			blnPass = false;
		}
				
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please enter agreement no";
		} else if (blnPass == false) {			
				strMsg = "Please enter valid agreement no";
		} else {
			
			formatDigits fd = new formatDigits();
			
			TimeStampUtil gts = new TimeStampUtil();
			strDate = gts.standardDate();
			
//			strQuery = "From CustomerVehicle where agreementserial = '" + strAgreementserial + "')";
				
			try {
				List<CustomerVehicle> custveh = custVehicleRepo.findByagreementserial(Agreementserial) ;
				for (CustomerVehicle cv:custveh) {
					strProposalno = cv.getProposalno();
					strVehicle = cv.getVehicleregno();
				}
			} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			
//			strQuery = "From Impoundedstock where agreementno = '" + strAgreementno + "'";
			
			try {
				Optional<Impoundedstock> impound = impRepo.findById(strAgreementno);
				
				if (impound.isPresent()) {
					
					Impoundedstock ims = impound.get();
					
						strImpounddate = ims.getImpounddate();
					
					
//					strQuery = "From LoanParam";					
					try {
						List<LoanParam> loan = lpRepo.findAll() ;
						
						for (LoanParam ln:loan) {
							strImpound = ln.getImpound();
						}
					} catch (Exception e) {System.out.println(e.getLocalizedMessage());}					
					
				} else {
					strImpounddate = strDate;
				}
								
//				strQuery = "From CustomerDetails where otherid in (select customerid " +
//						"from Proposal where proposalno = '" + strProposalno + "')";
				
				try {
					List<CustomerDetails> custdet = custRepo.findByProposalNo(strProposalno) ;
					
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
					List<PaymentSchedule> paysch = paymentScheduleRepo.findNonPaidSchedules(strAgreementqry, date);
					
					for (PaymentSchedule ps:paysch) {
						strInst = ps.getInstallment();						
						intNoofinst++;
						intOverdue+= Integer.parseInt(strInst);
					}
					
					strOverdue = Integer.toString(intOverdue);
					strOverdue = fd.digit(strOverdue);
					
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());}
				
				//Future due intfuturenoofinst, intfuturedue;
//				strNewimpounddate = strImpounddate.substring(6, 10) + "-" + strImpounddate.substring(3, 5) + "-" +strImpounddate.substring(0, 2);
//				strQuery = "From PaymentSchedule " +
//						"where scheduleno like '" + strAgreementqry + "' and to_date(paymentdate, 'dd/mm/yyyy') > '" + strNewimpounddate + "'";
//			
				try {
					List<PaymentSchedule> paysch = paymentScheduleRepo.findSchedules(strAgreementqry, date);
					
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
					
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());}
				
				
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
							
				intImpound = Integer.parseInt(strImpound);
				intTotaltopay = intOverdue + intTotalpen + intImpound;
				
				strTotaltopay = Integer.toString(intTotaltopay);
				strTotaltopay = fd.digit(strTotaltopay);
				strInst = fd.digit(strInst);
				
				dblassetcost = dblfutureprincipal + intTotaltopay;
				strAssetcost = Double.toString(dblassetcost);
				
				strAssetcost = fd.digit(strAssetcost);	
				strImpound = fd.digit(strImpound);
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

	public Response<String> savePreClosure(PreclosureDto preclosureDto) {
		
		
		String strMsg = "";
		String strAgreementno = "";
		String strOverdue = "";
		String strProposalno = "";
		String strVehicle = "";
		String strCustid = "";			
		String strOverdueinst = "";
		String strInstallment = "";
		String strPenalty = "";		
		String strImpchgs = "";
		String strDueinst = "";
		String strPreclosurevalue = "";
		String strPrincipalout = "";
		String strPercentpout = "";
		String strLoginuser = "";
		
		String strStatus = "PRECLOSED";			
		String strRequestdatetime = "";
		
		Response<String> response = new Response<>();
		
		strAgreementno = preclosureDto.getAgreementno();
		strCustid = preclosureDto.getCustid();
		strProposalno = preclosureDto.getProposalno();
		strVehicle = preclosureDto.getVehicle();
		strOverdueinst = preclosureDto.getOverdueinst();
		strInstallment = preclosureDto.getInstallment();
		strOverdue = preclosureDto.getOverdue();
		strPenalty = preclosureDto.getPenalty();
		strImpchgs = preclosureDto.getImpoundcharges();	
		strDueinst = preclosureDto.getDueinst();
		strPrincipalout = preclosureDto.getPrincipalout();
		strPreclosurevalue = preclosureDto.getPreclosurevalue();
		strPercentpout = preclosureDto.getPercentonpout();
		strLoginuser = preclosureDto.getPreclosureuser();
					
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strVehicle == null || strVehicle.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strPrincipalout == null || strPrincipalout.length() == 0) {
			strMsg = "Please enter sale amount";
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
			
			Preclosure preclosure = new Preclosure();
			preclosure.setAgreementno(strAgreementno);
			preclosure.setCustid(strCustid);
			preclosure.setProposalno(strProposalno);
			preclosure.setVehicle(strVehicle);
			preclosure.setOverdueinst(strOverdueinst);
			preclosure.setInstallment(strInstallment);
			preclosure.setOverdue(strOverdue);
			preclosure.setPenalty(strPenalty);
			preclosure.setImpoundcharges(strImpchgs);
			preclosure.setDueinst(strDueinst);
			preclosure.setPrincipalout(strPrincipalout);
			preclosure.setPreclosurevalue(strPreclosurevalue);
			preclosure.setPercentonpout(strPercentpout);
			preclosure.setPreclosureuser(strLoginuser);
			preclosure.setPreclosuredatetime(strRequestdatetime);
			
			try {					
				preClosureRepo.save(preclosure);
				strMsg = "Preclosure for " + strAgreementno + " saved successfully" ;
				
//				strQuery = "Update Loan set preclosed = true where proposalno = '" + strProposalno + "'";
				
				int intUser = LoanRepo.updateLoan(strProposalno);
				if (intUser > 0) {
					System.out.println("Loan " + strProposalno + " preclosed successfully");
				}							
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error while saving preclosure for : " + strAgreementno);
				response.setErrorMsg("Error while saving preclosure for : " + strAgreementno);
			}			
		}
				
		response.setData(strMsg);
		
		
		return response;
	}

	public Response<String> getValue(String principal, String value, String percent) {
		
		String strPrincipal = "";
		String strValue = "";
		String strPercent = "";
		String strPreclosure = "";
		
		double dblPrincipal = 0;
		double dblValue = 0;
		double dblPercent = 0;
		double dblPreclosure = 0;
				
		String strMsg = "";
		
		strPrincipal = principal;
		strValue = value;
		strPercent = percent;
		
		Response<String> response = new Response<>();
		
		if (strPrincipal == null || strPrincipal.length() == 0) {			
			strMsg =  "Please select agreement";		
		} else if (strValue == null || strValue.length() == 0) {			
			strMsg = "Please select agreement";			
		} else if (strPercent == null || strPercent.length() == 0) {			
			strMsg = "Please enter percentage";		
		} else {
			
			strPrincipal = strPrincipal.replace(",", "");
			strValue = strValue.replace(",", "");
			strPercent = strPercent.replace(",", "");
			
			dblPrincipal = Double.parseDouble(strPrincipal);
			dblValue = Double.parseDouble(strValue);
			dblPercent = Double.parseDouble(strPercent);
			
			dblPreclosure = ((dblPercent / 100) * dblPrincipal) + dblValue;
			strPreclosure = Double.toString(dblPreclosure);
			
			formatDigits fd = new formatDigits();
			strPreclosure = fd.digit(strPreclosure);
			
			strMsg = strPreclosure;
		}

	response.setData(strMsg);
		
		
		
		return response;
	}

	@Autowired
	private PdfParamRepo pdfRepo;
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	public ResponseEntity<InputStreamResource> getClosureLetter(String agreement, String value, String percent) {
		
		
		Response<String> response = new Response<>();
		
		String strQuery = "";
		String strAddress = "";
		String strAddress2 = "";
		String strCustomername = "";
		String strVehicle = "";
		String strRef = "";
		String strAgreement = "";
		String strAgreementserial = "";
		String strDate = "";
		String strPreclosurevalue = "";
		String strPercent = "";
		
		String strVillage = "";
		String strParish = "";
		String strSubcounty = "";
		String strCounty = "";
		String strDistrict = "";
		
		int datex = 0;
		int datey = 0;
		int date1x = 0;
		int date1y = 0;
		int custnamex = 0;
		int custnamey = 0;
		int agreementx = 0;
		int agreementy = 0;
		int addressx = 0;
		int addressy = 0;
		int address2x = 0;
		int address2y = 0;
		int refx = 0;
		int refy = 0;
		int value1x = 0;
		int value1y = 0;
		int value2x = 0;
		int value2y = 0;
		int percentx = 0;
		int percenty = 0;
		
		String pdfFile = "";
		String newpdfFile = "";
		String strXYField = "";
		String strX = "";
		String strY = "";
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		strAgreement =agreement;
		strPreclosurevalue = value;
		strPercent = percent;
		int intAgreementserial = 0;
		
		strAgreement = strAgreement.trim();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
							
		if (strAgreement == null || strAgreement.length() == 0) {
		} else if (strPercent == null || strAgreement.length() == 0) {
		} else if (strPreclosurevalue == null || strPreclosurevalue.length() == 0) {
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
				
			TimeStampUtil gts = new TimeStampUtil();		
			strDate = gts.standardDate();
			
			formatDigits fd = new formatDigits();
			
			try {
				strQuery = "From pdfParams"; 
				List<pdfParams> pparam = pdfRepo.findAll();
								
				for (pdfParams pp:pparam) {
					strXYField = pp.getField();
					strX = pp.getXaxis();
					strY = pp.getYaxis();
					
					if (strXYField.equals("pcdate")) {
						datex = Integer.parseInt(strX);
						datey = Integer.parseInt(strY);
					} else if (strXYField.equals("pcdate1")) {
						date1x = Integer.parseInt(strX);
						date1y = Integer.parseInt(strY);
					} else if (strXYField.equals("pccustname")) {
						custnamex = Integer.parseInt(strX);
						custnamey = Integer.parseInt(strY);
					} else if (strXYField.equals("pcagreement")) {
						agreementx = Integer.parseInt(strX);
						agreementy = Integer.parseInt(strY);
					} else if (strXYField.equals("pcaddress")) {
						addressx = Integer.parseInt(strX);
						addressy = Integer.parseInt(strY);
					} else if (strXYField.equals("pcaddress2")) {
						address2x = Integer.parseInt(strX);
						address2y = Integer.parseInt(strY);
					} else if (strXYField.equals("pcref")) {
						refx = Integer.parseInt(strX);
						refy = Integer.parseInt(strY);
					} else if (strXYField.equals("pcvalue1")) {
						value1x = Integer.parseInt(strX);
						value1y = Integer.parseInt(strY);
					} else if (strXYField.equals("pcvalue2")) {
						value2x = Integer.parseInt(strX);
						value2y = Integer.parseInt(strY);
					} else if (strXYField.equals("pcpercent")) {
						percentx = Integer.parseInt(strX);
						percenty = Integer.parseInt(strY);
					}			
				}				
			}catch (Exception e) {}			
			
			try {
				strAgreementserial = strAgreement.replace("AN", "");
				intAgreementserial = Integer.parseInt(strAgreementserial);
//				strQuery = "From CustomerDetails where otherid in " +
//							"(select customerid from Proposal where proposalno in " +
//							"(select proposalno from CustomerVehicle where agreementserial = '" + strAgreementserial + "'))";
				
				List<CustomerDetails> custdet = custRepo.findByAgreementSerial(strAgreementserial);
								
				for (CustomerDetails cd:custdet) {
					strCustomername = cd.getSurname() + " " + cd.getFirstname();
					strVillage = cd.getVillage();
					strParish = cd.getParish();
					strSubcounty = cd.getSubcounty();
					strCounty = cd.getCounty();
					strDistrict = cd.getDistrict();
				}
				
				strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
				if (strAddress.length() > 60) {
					int len1 = strVillage.trim().length();
					int len2 = strParish.trim().length();
					int len3  = strSubcounty.trim().length();
					int len4 = strCounty.trim().length();
					
					if ((len1 + len2 + len3 + len4) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim();
						strAddress2 = strDistrict.trim();
					} else if ((len1 + len2 + len3) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim();
						strAddress2 = strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1 + len2) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim();
						strAddress2 = strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1) < 60) {
						strAddress = strVillage.trim();
						strAddress2 = strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					}
					
				}
				
			} catch (Exception e) {System.out.println(e.getLocalizedMessage());}	
			
			try {
				//				strQuery = "From CustomerVehicle where agreementserial = '" + strAgreementserial + "'";  
				List<CustomerVehicle> custveh = custVehicleRepo.findByagreementserial(intAgreementserial);
								
				for (CustomerVehicle cv:custveh) {
					strVehicle = cv.getVehicleregno();
				}
				
				strRef = strVehicle;
				
			} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			
			try {
				//Create PdfReader instance.					
//				pdfFile = request.getRealPath("/docs/VBY_Preclosure_letter.pdf");
				
				Resource resource = resourceLoader.getResource("classpath:docs/VBY_Preclosure_letter.pdf");
				
//				File Pdffiles = null;
//				try {
//					Pdffiles = resource.getFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			
			    PdfReader pdfReader = 
					new PdfReader(resource.getInputStream());	
	 
			    //Create PdfStamper instance.pdfFile
//			    newpdfFile = "src/main/resources/Documents/" + strAgreement  + "VBY_Preclosure_letter.pdf";
//			    PdfStamper pdfStamper = new PdfStamper(pdfReader,
//				new FileOutputStream(newpdfFile));
			    
			    
			    PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
	 
			    //Create BaseFont instance.
			    BaseFont baseFont = BaseFont.createFont(
		                BaseFont.COURIER_BOLD, 
		                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	 
			    //Get the number of pages in pdf.
			    int pages = pdfReader.getNumberOfPages(); 
	 
			    //Iterate the pdf through pages.
			    for(int i=1; i<=pages; i++) { 
					//Contain the pdf data.
					PdfContentByte pageContentByte = 
							pdfStamper.getOverContent(i);
		 
					pageContentByte.beginText();
					//Set text font and size.
					pageContentByte.setFontAndSize(baseFont, 12);
					
					if (i == 1) {						 
						pageContentByte.setTextMatrix(datex, datey);
			 			pageContentByte.showText(strDate);
			 			
						pageContentByte.setTextMatrix(custnamex, custnamey);
						pageContentByte.showText(strCustomername);
						pageContentByte.setTextMatrix(agreementx , agreementy);
						pageContentByte.showText(strAgreement);							
						pageContentByte.setTextMatrix(addressx, addressy);
						pageContentByte.showText(strAddress);							
						pageContentByte.setTextMatrix(address2x, address2y);
						pageContentByte.showText(strAddress2);
						
						pageContentByte.setTextMatrix(refx, refy);
						pageContentByte.showText(strRef);
						
						pageContentByte.setTextMatrix(date1x, date1y);
			 			pageContentByte.showText(strDate);
						pageContentByte.setTextMatrix(value1x, value1y);
						pageContentByte.showText(strPreclosurevalue);							
						pageContentByte.setTextMatrix(value2x, value2y);
						pageContentByte.showText(strPreclosurevalue);
						
						pageContentByte.setTextMatrix(percentx, percenty);
						pageContentByte.showText(strPercent);
					}
					
					pageContentByte.endText();
			    }
	 
			    //Close the pdfStamper.
			    pdfStamper.close();	
			   
//			    file = new File(newpdfFile);
//		        FileOutputStream fos = null;
			    
		   
			 resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

	            // Set headers
		    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + strAgreement  + "VBY_Preclosure_letter.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);	    
			    
		        
			} catch (Exception e) {
			    e.printStackTrace();
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}				
		//response.setContentType("text/plain");//response.setCharacterEncoding("UTF-8");//response.getWriter().write(strMsg);
//		session.setAttribute("preletterid", "");
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(baos.size())
                .body(resource1);
	}
	
	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
	
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
		        // File is too large
		}
		byte[] bytes = new byte[(int)length];
	
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
		   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		   offset += numRead;
		}
	
		if (offset < bytes.length) {
		   throw new IOException("Could not completely read file " + file.getName());
		}
	
		is.close();
		return bytes;
	}

	public ResponseEntity<InputStreamResource> getObjLetter(String agreement) {
		
		String strQuery = "";
		String strAddress = "";
		String strAddress2 = "";
		String strCustomername = "";
		String strVehicle = "";
		String strRef = "";
		String strAgreement = "";
		String strAgreementserial = "";
		String strDate = "";
		
		String strVillage = "";
		String strParish = "";
		String strSubcounty = "";
		String strCounty = "";
		String strDistrict = "";
		
		int datex = 0;
		int datey = 0;
		int custnamex = 0;
		int custnamey = 0;
		int agreementx = 0;
		int agreementy = 0;
		int addressx = 0;
		int addressy = 0;
		int address2x = 0;
		int address2y = 0;
		int refx = 0;
		int refy = 0;
		
		String pdfFile = "";
//		String newpdfFile = "";
		String strXYField = "";
		String strX = "";
		String strY = "";
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		strAgreement = agreement;
		strAgreement = strAgreement.trim();
		int intAgreementserial = 0;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Response<String> response = new Response<>();
							
		if (strAgreement == null || strAgreement.length() == 0) {
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
				
			TimeStampUtil gts = new TimeStampUtil();		
			strDate = gts.standardDate();
			
			formatDigits fd = new formatDigits();
			
			try {
				strQuery = "From pdfParams"; 
				List<pdfParams> pparam = pdfRepo.findAll();
								
				for (pdfParams pp:pparam) {
					strXYField = pp.getField();
					strX = pp.getXaxis();
					strY = pp.getYaxis();
					
					if (strXYField.equals("obdate")) {
						datex = Integer.parseInt(strX);
						datey = Integer.parseInt(strY);
					} else if (strXYField.equals("obcustname")) {
						custnamex = Integer.parseInt(strX);
						custnamey = Integer.parseInt(strY);
					} else if (strXYField.equals("obagreement")) {
						agreementx = Integer.parseInt(strX);
						agreementy = Integer.parseInt(strY);
					} else if (strXYField.equals("obaddress")) {
						addressx = Integer.parseInt(strX);
						addressy = Integer.parseInt(strY);
					} else if (strXYField.equals("obaddress2")) {
						address2x = Integer.parseInt(strX);
						address2y = Integer.parseInt(strY);
					} else if (strXYField.equals("obref")) {
						refx = Integer.parseInt(strX);
						refy = Integer.parseInt(strY);
					}	
				}				
			}catch (Exception e) {}			
			
			try {
				strAgreementserial = strAgreement.replace("AN", "");
				intAgreementserial = Integer.parseInt(strAgreementserial);
//				strQuery = "From CustomerDetails where otherid in " +
//							"(select customerid from Proposal where proposalno in " +
//							"(select proposalno from CustomerVehicle where agreementserial = '" + strAgreementserial + "'))";
//				
				List<CustomerDetails> custdet = custRepo.findByAgreementSerial(strAgreementserial);
								
				for (CustomerDetails cd:custdet) {
					strCustomername = cd.getSurname() + " " + cd.getFirstname();
					strVillage = cd.getVillage();
					strParish = cd.getParish();
					strSubcounty = cd.getSubcounty();
					strCounty = cd.getCounty();
					strDistrict = cd.getDistrict();
				}
				
				strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
				if (strAddress.length() > 60) {
					int len1 = strVillage.trim().length();
					int len2 = strParish.trim().length();
					int len3  = strSubcounty.trim().length();
					int len4 = strCounty.trim().length();
					
					if ((len1 + len2 + len3 + len4) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim();
						strAddress2 = strDistrict.trim();
					} else if ((len1 + len2 + len3) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim();
						strAddress2 = strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1 + len2) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim();
						strAddress2 = strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1) < 60) {
						strAddress = strVillage.trim();
						strAddress2 = strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					}
					
				}
				
			} catch (Exception e) {System.out.println(e.getLocalizedMessage());}	
			
			try {
//				strQuery = "From CustomerVehicle where agreementserial = '" + strAgreementserial + "'";  
				List<CustomerVehicle> custveh = custVehicleRepo.findByagreementserial(intAgreementserial);
								
				for (CustomerVehicle cv:custveh) {
					strVehicle = cv.getVehicleregno();
				}
				
				strRef = strAgreement + " / " + strVehicle;
				
			} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			
			try {			
				
				Resource resource = resourceLoader.getResource("classpath:docs/VB_No_Objection_letter.pdf");
				
//				File Pdffiles = null;
//				try {
//					Pdffiles = resource.getFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
			
			    PdfReader pdfReader = 
					new PdfReader(resource.getInputStream());					
	 
			    //Create PdfStamper instance.pdfFile
//			    newpdfFile = "src/main/resources/Documents/" + strAgreement  + "VB_No_Objection_letter.pdf";
//			    PdfStamper pdfStamper = new PdfStamper(pdfReader,
//				new FileOutputStream(newpdfFile));
	 
			 // Create a ByteArrayOutputStream to hold the modified PDF
	            
	            PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
			    
			    
			    //Create BaseFont instance.
			    BaseFont baseFont = BaseFont.createFont(
		                BaseFont.COURIER_BOLD, 
		                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	 
			    //Get the number of pages in pdf.
			    int pages = pdfReader.getNumberOfPages(); 
	 
			    //Iterate the pdf through pages.
			    for(int i=1; i<=pages; i++) { 
					//Contain the pdf data.
					PdfContentByte pageContentByte = 
							pdfStamper.getOverContent(i);
		 
					pageContentByte.beginText();
					//Set text font and size.
					pageContentByte.setFontAndSize(baseFont, 12);
					
					if (i == 1) {						 
						pageContentByte.setTextMatrix(datex, datey);
			 			pageContentByte.showText(strDate);
			 			
						pageContentByte.setTextMatrix(custnamex, custnamey);
						pageContentByte.showText(strCustomername);
						pageContentByte.setTextMatrix(agreementx , agreementy);
						pageContentByte.showText(strAgreement);							
						pageContentByte.setTextMatrix(addressx, addressy);
						pageContentByte.showText(strAddress);							
						pageContentByte.setTextMatrix(address2x, address2y);
						pageContentByte.showText(strAddress2);
						
						pageContentByte.setTextMatrix(refx, refy);
						pageContentByte.showText(strRef);
					}
					
					pageContentByte.endText();
			    }
	 
			    //Close the pdfStamper.
			    pdfStamper.close();	
			   
//		        	FileInputStream inputStream = new FileInputStream(file);
//		            resource1 = new InputStreamResource(inputStream);
			    resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

		            // Set headers
			    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=VB_No_Objection_letter.pdf");
	            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}				
		//response.setContentType("text/plain");//response.setCharacterEncoding("UTF-8");//response.getWriter().write(strMsg);
//		session.setAttribute("objletterid", "");
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(baos.size())
                .body(resource1);
	}

	public Response<List<String>> getAgreeNo() {
		Response<List<String>> response = new Response<>();
		
		List<String> loanStatus = LoanRepo.getAgreementNo() ;
		
		List<String> categoryDtoList = new ArrayList<>();
		
		for(String ls : loanStatus) {
//			LoanEntryDto lsDto = new LoanEntryDto();
//			lsDto.set;
			categoryDtoList.add(ls);		
		}
		response.setData(categoryDtoList);
		return response;
	}

}
