package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class CustProfileService {

	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private GuarantorRepo guarantorRepo;
	
	public Response<String> viewStatement(String vehicle, String flag) {
		
		String strMsg = "";
		String strFlag = "";
		String strQuery = "";
		
		String strVehicle = "";
		String strCustid = "";
		String strProposalno = "";
		String strCustomer = "";
		String strAgreementno = "";
		
		int intAgreement = 0;
		
		strVehicle = vehicle;
		strFlag = flag;
		
		Response<String> response = new Response<>();
		
		if (strVehicle == null || strVehicle.length() == 0) {			
			strMsg = "";
		} else {
			if (strFlag.equals("all")) {
				strFlag = " and disbursed = true";
			} 					
			
//			strQuery = "From CustomerVehicle where vehicleregno = '" + strVehicle + "'";
			
			try {
				List<CustomerVehicle> custveh = custVehRepo.findByvehicleregno(strVehicle);
				
				if (custveh.size() > 0) {
					for (CustomerVehicle cv:custveh) {
						strProposalno = cv.getProposalno();
						intAgreement = cv.getAgreementserial();
					}
					
					strAgreementno = "AN" + Integer.toString(intAgreement);
					
//					strQuery = "From CustomerDetails where otherid in (select customerid from Proposal where proposalno = '" + strProposalno + "')";
					
					List<CustomerDetails> custdet = custRepo.findByProposalNo(strProposalno);
					if (custdet.size() > 0) {
						for (CustomerDetails cd:custdet) {
							strCustid = cd.getOtherid();
							strCustomer = cd.getFirstname() + " " + cd.getSurname();
						}
					}
					
					strMsg = strCustid + "|" +  strCustomer + "|" +  strProposalno + "|" +  strAgreementno; 
					
				} else {
					response.setErrorMsg("Please enter correct vehicle regno, " + strVehicle + " does not exist.");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setErrorMsg("Error retrieving agreements: " + strCustomer);
			}			
		}
				
		response.setData(strMsg);
		return response;
	}

	public ResponseEntity<InputStreamResource> printProfile(String customer, String vehicle) {
		
		String strMsg = "";
		String strVehicle = "";
		String strCustid = "";
		String strCustomername = "";
		String strPrintDate = "";
		String strPdffilename="";
								
		String strAddress = "";
		String strAddress2 = "";
		String strVillage = "";
		String strParish = "";
		String strSubcounty = "";
		String strCounty = "";
		String strDistrict = "";
		String strMobile = "";
		
		String strDate = "";
		
		String strDob = "";
		String strFirstname = "";
		String strOthername = "";
		String strSsurname = "";
		String strMaritalstatus = "";
		String strSex = "";
		String strMobileno = "";
		String strStage = "";
		String strCurrentbikeregno = "";
		String strNewbikeuse = "";
		
		String strYearsinvillage = "";
		String strNationalid = "";
		String strOtherid = "";
		boolean blnTvverified = false;
		boolean blnfiverified = false;
		String strTvremarks = "";
		String strFiremarks = "";
		boolean blndcverified = false;
		String strDcremarks = "";
		String strNextofkin = "";
		String strNokmobileno = "";
		String strNokrelationship = "";
		boolean blnNokagreeing = false;
		String strDrivingpermit = "";
		String strNationality = "";
		String strNoofdependants = "";
		String strOwnhouserented = "";
		String strLandlordname = "";
		String strLandlordmobileno = "";
		String strRentpm = "";
		String strOtherincomesource = "";
		String strDownpaymentsource = "";
		String strPermanentaddress = "";
		String strFathersname = "";
		String strMothersname = "";
		String strNearbypolicestation = "";
		String strCapturedatetime = "";
		String strRemarks = "";
		String strLc = "";
		String strLcmobileno = "";
		
		//Guarantor I
		String strG1surname = "";
		String strG1firstname = "";
		String strG1othername = "";
		boolean blnG1firstguarantor = false;
		boolean blnG1secondguarantor = false;
		String strG1custid = "";
		String strG1nationalid = "";
		String strG1mobileno = "";
		String strG1address = "";
		String strG1permanentaddress = "";
		String strG1yrsinaddress = "";
		String strG1ownhouserented = "";
		String strG1rentpm = "";
		String strG1nextofkin = "";
		String strG1nokmobileno = "";
		String strG1nokrelationship = "";
		boolean blnG1nokagreeing = false;
		String strG1bikeregno = "";
		String strG1bikeowner = "";
		boolean blnG1salaried = false;
		String strG1employername = "";
		String strG1monthlyincome = "";
		String strG1ois = "";
		String strG1otherincome = "";
		String strG1stagename = "";
		String strG1stageaddress = "";
		boolean blnG1stagechairconf = false;
		String strG1lcname = "";
		String strG1remarks = "";
		String strG1relationship = "";
		String strG1stage = "";
		String strG1vehicleregno = "";
		String strG1lcmobile = "";
		String strG1stagechairname = "";
		String strG1stagechairmobile = "";
		

		File file = null ;
		
		//Guarantor II
		String strG2surname = "";
		String strG2firstname = "";
		String strG2othername = "";
		boolean blnG2firstguarantor = false;
		boolean blnG2secondguarantor = false;
		String strG2custid = "";
		String strG2nationalid = "";
		String strG2mobileno = "";
		String strG2address = "";
		String strG2permanentaddress = "";
		String strG2yrsinaddress = "";
		String strG2ownhouserented = "";
		String strG2rentpm = "";
		String strG2nextofkin = "";
		String strG2nokmobileno = "";
		String strG2nokrelationship = "";
		boolean blnG2nokagreeing = false;
		String strG2bikeregno = "";
		String strG2bikeowner = "";
		boolean blnG2salaried = false;
		String strG2employername = "";
		String strG2monthlyincome = "";
		String strG2ois = "";
		String strG2otherincome = "";
		String strG2stagename = "";
		String strG2stageaddress = "";
		boolean blnG2stagechairconf = false;
		String strG2lcname = "";
		String strG2remarks = "";
		String strG2relationship = "";
		String strG2stage = "";
		String strG2vehicleregno = "";
		String strG2lcmobile = "";
		String strG2stagechairname = "";
		String strG2stagechairmobile = "";	
		
		String strG1stagechairconf = "";	
		String strG2stagechairconf = "";	
		
		String strG1salaried = "";
		String strG2salaried = "";
		
		String strCqcremarks = "";
		String strTvcremarks = "";
		String strFicremarks = "";
		String strCoapproval = "";
		String StrCoremarks = "";
		String strCooapproval = "";
		String StrCooremarks = "";
		
		String strG1cqcremarks = "";
		String strG1tvcremarks = "";
		String strG1ficremarks = "";
		String strG1coapproval = "";
		String StrG1coremarks = "";
		String strG1cooapproval = "";
		String StrG1cooremarks = "";
		
		String strG2cqcremarks = "";
		String strG2tvcremarks = "";
		String strG2ficremarks = "";
		String strG2coapproval = "";
		String StrG2coremarks = "";
		String strG2cooapproval = "";
		String StrG2cooremarks = "";
		
		boolean blnFirstguarantor = false;
		boolean blnSecondguarantor = false;
		
		int intCount = 0;
					
		strCustid = customer;	
		strVehicle = vehicle;	
		strCustid = strCustid.trim();
		strVehicle = strVehicle.trim();
		
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		
		Document document = new Document();
		
		HttpHeaders headers = new HttpHeaders();
		
		InputStreamResource resource1 = null ;
		
		Response<String> response = new Response<>();
		
		formatDigits fd = new formatDigits();
							
		if (strCustid == null || strCustid.length() == 0) {			
			strMsg = "Please select customer";		
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
			strPrintDate = gts.TimeStamp();
			strDate = gts.standardDate();					
							
			try {
//				strQuery = "From CustomerDetails where otherid = '" + strCustid + "')";
				
				CustomerDetails cd = custRepo.findByotherid(strCustid);
							
					strCustomername = cd.getSurname() + " " + cd.getFirstname();
					strVillage = cd.getVillage();
					strParish = cd.getParish();
					strSubcounty = cd.getSubcounty();
					strCounty = cd.getCounty();
					strDistrict = cd.getDistrict();
					strMobile = cd.getMobileno();
					
					strDob = cd.getDob();
					strFirstname = cd.getFirstname();
					strOthername = cd.getOthername();
					strSsurname = cd.getSurname();
					strMaritalstatus = cd.getMaritalstatus();
					strSex = cd.getSex();
					strMobileno = cd.getMobileno();
					strStage = cd.getStage();
					strCurrentbikeregno = cd.getCurrentbikeregno();
					strNewbikeuse = cd.getNewbikeuse();
					strYearsinvillage = cd.getYearsinvillage();
					strNationalid = cd.getNationalid();
					strNationalid = strNationalid.trim();
					blnTvverified = cd.getTvverified();
					blnfiverified = cd.getFiverified();
					
					strNextofkin = cd.getNextofkin();
					strNokmobileno = cd.getNokmobileno();
					strNokrelationship = cd.getNokrelationship();
					blnNokagreeing = cd.getNokagreeing();
					strDrivingpermit = cd.getDrivingpermit();
					strNationality = cd.getNationality();
					strNoofdependants = cd.getNoofdependants();
					strOwnhouserented = cd.getOwnhouserented();
					strLandlordname = cd.getLandlordname();
					strLandlordmobileno = cd.getLandlordmobileno();
					strRentpm = cd.getRentpm();
					strOtherincomesource = cd.getOtherincomesource();
					strDownpaymentsource = cd.getDownpaymentsource();
					strPermanentaddress = cd.getPermanentaddress();
					strFathersname = cd.getFathersname();
					strMothersname = cd.getMothersname();
					strNearbypolicestation = cd.getNearbypolicestation();
					strRemarks = cd.getRemarks();
					strLc = cd.getLc();
					strLcmobileno = cd.getLcmobileno();
					
					strCqcremarks = cd.getCqcremarks();
					strTvcremarks = cd.getTvremarks();
					strFicremarks = cd.getFiremarks();
					strCoapproval = cd.getCoapproval();
					StrCoremarks = cd.getCoremarks();
					strCooapproval = cd.getCooapproval();
					StrCooremarks = cd.getcooremarks();
				
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
				
			}catch (Exception e) {}
			
//			strQuery = "From Guarantor where custid = '" + strCustid + "'";
			
			try {
				List<Guarantor> guarantor = guarantorRepo.findBycustid(strCustid);
				
				for (Guarantor gr:guarantor) {
					blnFirstguarantor = gr.getFirstguarantor();
					blnSecondguarantor = gr.getSecondguarantor();
					if (blnFirstguarantor == true) {
						strG1surname = gr.getSurname();
						strG1firstname = gr.getFirstname();
						strG1othername = gr.getOtherincome();		
						strG1nationalid = gr.getNationalid();
						strG1mobileno = gr.getMobileno();
						strG1address = gr.getAddress();
						strG1permanentaddress = gr.getPermanentaddress();
						strG1yrsinaddress = gr.getYrsinaddress();
						strG1ownhouserented = gr.getOwnhouserented();
						strG1rentpm = gr.getRentpm();
						strG1nextofkin = gr.getNextofkin();
						strG1nokmobileno = gr.getNokmobileno();
						strG1nokrelationship = gr.getNokrelationship();
						blnG1nokagreeing = gr.getNokagreeing();
						strG1bikeregno = gr.getBikeregno();
						strG1bikeowner = gr.getBikeowner();
						blnG1salaried = gr.getSalaried();
						if (blnG1salaried == true) {strG1salaried = "YES";} else {strG1salaried = "NO";}
						strG1employername = gr.getEmployername();
						strG1monthlyincome = gr.getMonthlyincome();
						strG1ois = gr.getOis();
						strG1otherincome = gr.getOtherincome();
						strG1stagename = gr.getStagename();
						strG1stageaddress = gr.getStageaddress();
						blnG1stagechairconf = gr.getStagechairconf();
						if (blnG1stagechairconf == true) {strG1stagechairconf = "YES";} else {strG1stagechairconf = "NO";}
						strG1lcname = gr.getLcname();
						strG1relationship = gr.getRelationship();
						strG1vehicleregno = gr.getBikeregno();
						strG1lcmobile = gr.getLcmobile();
						strG1stagechairname = gr.getStagechairname();
						strG1stagechairmobile = gr.getStagechairmobile();
						strG1remarks = gr.getRemarks();
						
						strG1cqcremarks = gr.getCqcremarks();
						strG1tvcremarks = gr.getTvremarks();
						strG1ficremarks = gr.getFiremarks();
						strG1coapproval = gr.getCoapproval();
						StrG1coremarks = gr.getCoremarks();
						strG1cooapproval = gr.getCooapproval();
						StrG1cooremarks = gr.getcooremarks();
						
					} else if (blnSecondguarantor == true) {
						intCount++;
						if (intCount == 1) {
							strG2surname = gr.getSurname();
							strG2firstname = gr.getFirstname();
							strG2othername = gr.getOtherincome();		
							strG2nationalid = gr.getNationalid();
							strG2mobileno = gr.getMobileno();
							strG2address = gr.getAddress();
							strG2permanentaddress = gr.getPermanentaddress();
							strG2yrsinaddress = gr.getYrsinaddress();
							strG2ownhouserented = gr.getOwnhouserented();
							strG2rentpm = gr.getRentpm();
							strG2nextofkin = gr.getNextofkin();
							strG2nokmobileno = gr.getNokmobileno();
							strG2nokrelationship = gr.getNokrelationship();
							blnG2nokagreeing = gr.getNokagreeing();
							strG2bikeregno = gr.getBikeregno();
							strG2bikeowner = gr.getBikeowner();
							blnG2salaried = gr.getSalaried();
							if (blnG2salaried == true) {strG2salaried = "YES";} else {strG2salaried = "NO";}
							strG2employername = gr.getEmployername();
							strG2monthlyincome = gr.getMonthlyincome();
							strG2ois = gr.getOis();
							strG2otherincome = gr.getOtherincome();
							strG2stagename = gr.getStagename();
							strG2stageaddress = gr.getStageaddress();
							blnG2stagechairconf = gr.getStagechairconf();
							if (blnG2stagechairconf == true) {strG2stagechairconf = "YES";} else {strG2stagechairconf = "NO";}
							strG2lcname = gr.getLcname();
							strG2relationship = gr.getRelationship();
							strG2vehicleregno = gr.getBikeregno();
							strG2lcmobile = gr.getLcmobile();
							strG2stagechairname = gr.getStagechairname();
							strG2stagechairmobile = gr.getStagechairmobile();
							strG2remarks = gr.getRemarks();
							
							strG2cqcremarks = gr.getCqcremarks();
							strG2tvcremarks = gr.getTvremarks();
							strG2ficremarks = gr.getFiremarks();
							strG2coapproval = gr.getCoapproval();
							StrG2coremarks = gr.getCoremarks();
							strG2cooapproval = gr.getCooapproval();
							StrG2cooremarks = gr.getcooremarks();
						}
					}

				}					
				
			} catch (Exception e) {
				System.out.println("Error retrieving guarantor details for : " + strCustid);
			}
			
			try {
//				strPdffilename = "src/main/resources/Documents/CustomerProfile" + strCustid + ".pdf";
//				Document document = new Document();
				
				
				PdfWriter.getInstance(document, baos);
//				PdfWriter.getInstance(document, new FileOutputStream(strPdffilename));
				document.open();
				Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
				Font bldFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
									
				//section 0					
				PdfPTable hdrtbl = new PdfPTable(2);
				hdrtbl.setWidthPercentage(100);
				hdrtbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				hdrtbl.setWidths(new int[]{50,50});
				hdrtbl.setTotalWidth(100);
				
				PdfPCell lblvehicle = new PdfPCell(new Phrase("Vehicle Regno:  " + strVehicle, font));
				lblvehicle.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lblvehicle);
				PdfPCell lbldate = new PdfPCell(new Phrase("Date:  " + strPrintDate, font));
				lbldate.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lbldate.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lbldate);
				
				PdfPCell lblblnk1 = new PdfPCell(new Phrase("\n"));
				lblblnk1.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lblblnk1);
				PdfPCell lblblnk2 = new PdfPCell(new Phrase("\n"));
				lblblnk2.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lblblnk2);
						
				PdfPCell lblblnk3 = new PdfPCell(new Phrase("CUSTOMER DETAILS:", bldFont));
				lblblnk3.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lblblnk3);
				PdfPCell lblblnk4 = new PdfPCell(new Phrase(""));
				lblblnk4.setBorderColor(BaseColor.WHITE);
				hdrtbl.addCell(lblblnk4);
				
				document.add(hdrtbl);
				
				document.add(new Paragraph(""));
				
				//section I					
				PdfPTable tbl = new PdfPTable(4);
				tbl.setWidthPercentage(100);
				tbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbl.setWidths(new int[]{25,25,25,25});
				tbl.setTotalWidth(100);
				
				PdfPCell lblcustid = new PdfPCell(new Phrase("Customer Id: ",font));
				lblcustid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcustid);
				PdfPCell custid = new PdfPCell(new Phrase(strCustid,font));
				custid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(custid);
				PdfPCell lblAsset = new PdfPCell(new Phrase("National Id: ",font));
				lblAsset.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblAsset);
				PdfPCell assetAmnt = new PdfPCell(new Phrase(strNationalid,font));
				assetAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(assetAmnt);
				
				PdfPCell lbldisbdate = new PdfPCell(new Phrase("Customer Name: ",font));
				lbldisbdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldisbdate);
				PdfPCell disbdate = new PdfPCell(new Phrase(strCustomername,font));
				disbdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(disbdate);
				PdfPCell lbldp = new PdfPCell(new Phrase("Date of Birth: ",font));
				lbldp.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldp);					
				PdfPCell dpAmnt = new PdfPCell(new Phrase(strDob,font));
				dpAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(dpAmnt);
				
				PdfPCell lblinststartdate = new PdfPCell(new Phrase("Sex: ",font));
				lblinststartdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblinststartdate);
				PdfPCell inststartdate = new PdfPCell(new Phrase(strSex,font));
				inststartdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(inststartdate);					
				PdfPCell lblamtdisb = new PdfPCell(new Phrase("Years in village: ",font));
				lblamtdisb.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblamtdisb);
				PdfPCell amtdisb = new PdfPCell(new Phrase(strYearsinvillage,font));
				amtdisb.setBorderColor(BaseColor.WHITE);
				tbl.addCell(amtdisb);					
				
				PdfPCell lblinstenddate = new PdfPCell(new Phrase("Marital Status: ",font));
				lblinstenddate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblinstenddate);
				PdfPCell instenddate = new PdfPCell(new Phrase(strMaritalstatus,font));
				instenddate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(instenddate);
				PdfPCell lblewi = new PdfPCell(new Phrase("Next of Kin: ",font));
				lblewi.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblewi);
				PdfPCell ewiAmnt = new PdfPCell(new Phrase(strNextofkin,font));
				ewiAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(ewiAmnt);
				
				PdfPCell lblintrate = new PdfPCell(new Phrase("Mobile No: ",font));
				lblintrate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblintrate);
				PdfPCell intrate = new PdfPCell(new Phrase(strMobileno,font));
				intrate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(intrate);
				PdfPCell lblTenure = new PdfPCell(new Phrase("Next of Kin Mobile: ",font));
				lblTenure.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblTenure);
				PdfPCell Tenure = new PdfPCell(new Phrase(strNokmobileno,font));
				Tenure.setBorderColor(BaseColor.WHITE);
				tbl.addCell(Tenure);					
									
				PdfPCell lblblk1 = new PdfPCell(new Phrase("Stage Name: ",font));
				lblblk1.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk1);
				PdfPCell blk1 = new PdfPCell(new Phrase(strStage,font));
				blk1.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk1);
				PdfPCell lblEmifreq = new PdfPCell(new Phrase("Next of Kin Relationship: ",font));
				lblEmifreq.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblEmifreq);
				PdfPCell emifreq = new PdfPCell(new Phrase(strNokrelationship,font));
				emifreq.setBorderColor(BaseColor.WHITE);
				tbl.addCell(emifreq);					
				
				PdfPCell lblblk2 = new PdfPCell(new Phrase("Current Bike Regno: ",font));
				lblblk2.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk2);
				PdfPCell blk2 = new PdfPCell(new Phrase(strCurrentbikeregno,font));
				blk2.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk2);
				PdfPCell lblprinpaid = new PdfPCell(new Phrase("Driving Permit: ",font));
				lblprinpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblprinpaid);
				PdfPCell prinpaid = new PdfPCell(new Phrase(strDrivingpermit,font));
				prinpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(prinpaid);					
				
				PdfPCell lblblk3 = new PdfPCell(new Phrase("New Bike Use: ",font));
				lblblk3.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk3);
				PdfPCell blk3 = new PdfPCell(new Phrase(strNewbikeuse,font));
				blk3.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk3);
				PdfPCell lblintpaid = new PdfPCell(new Phrase("Nationality: ",font));
				lblintpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblintpaid);
				PdfPCell intpaid = new PdfPCell(new Phrase(strNationality,font));
				intpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(intpaid);				
									
				PdfPCell lblblk4 = new PdfPCell(new Phrase("No of Dependants: ",font));
				lblblk4.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk4);
				PdfPCell blk4 = new PdfPCell(new Phrase(strNoofdependants,font));
				blk4.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk4);
				PdfPCell lblor = new PdfPCell(new Phrase("Own House or Rented: ",font));
				lblor.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblor);
				PdfPCell txtor = new PdfPCell(new Phrase(strOwnhouserented,font));
				txtor.setBorderColor(BaseColor.WHITE);
				tbl.addCell(txtor);
									
				PdfPCell lblblk5 = new PdfPCell(new Phrase("Landlord Name: ",font));
				lblblk5.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk5);
				PdfPCell blk5 = new PdfPCell(new Phrase(strLandlordname,font));
				blk5.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk5);
				PdfPCell lblllmobile = new PdfPCell(new Phrase("Landlord Mobile No: ",font));
				lblllmobile.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblllmobile);
				PdfPCell llmobile = new PdfPCell(new Phrase(strLandlordmobileno,font));
				llmobile.setBorderColor(BaseColor.WHITE);
				tbl.addCell(llmobile);
				
				PdfPCell lblrpm = new PdfPCell(new Phrase("Rent Per Month: ",font));
				lblrpm.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblrpm);
				PdfPCell rpm = new PdfPCell(new Phrase(strRentpm,font));
				rpm.setBorderColor(BaseColor.WHITE);
				tbl.addCell(rpm);
				PdfPCell lblois = new PdfPCell(new Phrase("Other Income Source: ",font));
				lblois.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblois);
				PdfPCell ois = new PdfPCell(new Phrase(strOtherincomesource,font));
				ois.setBorderColor(BaseColor.WHITE);
				tbl.addCell(ois);
				
				PdfPCell lbldps = new PdfPCell(new Phrase("Down Payment Source: ",font));
				lbldps.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldps);
				PdfPCell dps = new PdfPCell(new Phrase(strDownpaymentsource,font));
				dps.setBorderColor(BaseColor.WHITE);
				tbl.addCell(dps);
				PdfPCell lblpadd = new PdfPCell(new Phrase("Permanent Address: ",font));
				lblpadd.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblpadd);
				PdfPCell padd = new PdfPCell(new Phrase(strPermanentaddress,font));
				padd.setBorderColor(BaseColor.WHITE);
				tbl.addCell(padd);
				
				PdfPCell lblfather = new PdfPCell(new Phrase("Father's Name: ",font));
				lblfather.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblfather);
				PdfPCell father = new PdfPCell(new Phrase(strFathersname,font));
				father.setBorderColor(BaseColor.WHITE);
				tbl.addCell(father);
				PdfPCell lblmother = new PdfPCell(new Phrase("Mother's Name: ",font));
				lblmother.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblmother);
				PdfPCell mother = new PdfPCell(new Phrase(strMothersname,font));
				mother.setBorderColor(BaseColor.WHITE);
				tbl.addCell(mother);
				
				PdfPCell lblchair = new PdfPCell(new Phrase("Local Chairman: ",font));
				lblchair.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblchair);
				PdfPCell lc = new PdfPCell(new Phrase(strLc,font));
				lc.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lc);
				PdfPCell lbllcmobile = new PdfPCell(new Phrase("LC Mobile no: ",font));
				lbllcmobile.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbllcmobile);
				PdfPCell lcmobile = new PdfPCell(new Phrase(strLcmobileno,font));
				lcmobile.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lcmobile);
				
				PdfPCell lblpolice = new PdfPCell(new Phrase("Nearby Police Station: ",font));
				lblpolice.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblpolice);
				PdfPCell police = new PdfPCell(new Phrase(strNearbypolicestation,font));
				police.setBorderColor(BaseColor.WHITE);
				tbl.addCell(police);
				PdfPCell lbldistrict = new PdfPCell(new Phrase("District: ",font));
				lbldistrict.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldistrict);
				PdfPCell district = new PdfPCell(new Phrase(strDistrict,font));
				district.setBorderColor(BaseColor.WHITE);
				tbl.addCell(district);
				
				PdfPCell lblcounty = new PdfPCell(new Phrase("County: ",font));
				lblcounty.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcounty);
				PdfPCell county = new PdfPCell(new Phrase(strCounty,font));
				county.setBorderColor(BaseColor.WHITE);
				tbl.addCell(county);
				PdfPCell lblsubcounty = new PdfPCell(new Phrase("Sub County: ",font));
				lblsubcounty.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblsubcounty);
				PdfPCell subcounty = new PdfPCell(new Phrase(strSubcounty,font));
				subcounty.setBorderColor(BaseColor.WHITE);
				tbl.addCell(subcounty);
				
				PdfPCell lblparish = new PdfPCell(new Phrase("Parish: ",font));
				lblparish.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblparish);
				PdfPCell parish = new PdfPCell(new Phrase(strParish,font));
				parish.setBorderColor(BaseColor.WHITE);
				tbl.addCell(parish);
				PdfPCell lblVillage = new PdfPCell(new Phrase("Village: ",font));
				lblVillage.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblVillage);
				PdfPCell village = new PdfPCell(new Phrase(strVillage,font));
				village.setBorderColor(BaseColor.WHITE);
				tbl.addCell(village);
				
				PdfPCell lbltv = new PdfPCell(new Phrase("TV Remarks: ",font));
				lbltv.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbltv);
				PdfPCell tvremarks = new PdfPCell(new Phrase(strTvcremarks,font));
				tvremarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(tvremarks);
				PdfPCell lblfi = new PdfPCell(new Phrase("FI Remaks: ",font));
				lblfi.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblfi);
				PdfPCell firemarks = new PdfPCell(new Phrase(strFicremarks,font));
				firemarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(firemarks);
				
				PdfPCell lblcam = new PdfPCell(new Phrase("CAM: ",font));
				lblcam.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcam);
				PdfPCell cam = new PdfPCell(new Phrase(strCoapproval,font));
				cam.setBorderColor(BaseColor.WHITE);
				tbl.addCell(cam);
				PdfPCell lblcamremarks = new PdfPCell(new Phrase("CAM Remaks: ",font));
				lblcamremarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcamremarks);
				PdfPCell camremarks = new PdfPCell(new Phrase(StrCoremarks,font));
				camremarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(camremarks);
				
				PdfPCell lblcamapp = new PdfPCell(new Phrase("CAM Approval: ",font));
				lblcamapp.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcamapp);
				PdfPCell camapp = new PdfPCell(new Phrase(strCoapproval,font));
				camapp.setBorderColor(BaseColor.WHITE);
				tbl.addCell(camapp);
				PdfPCell lblcamappremarks = new PdfPCell(new Phrase("CAM Approval Remaks: ",font));
				lblcamappremarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblcamappremarks);
				PdfPCell camappremarks = new PdfPCell(new Phrase(StrCoremarks,font));
				camappremarks.setBorderColor(BaseColor.WHITE);
				tbl.addCell(camappremarks);
				
				document.add(tbl);
				
				document.add(new Paragraph("\n"));
				
				//section 0					
				PdfPTable mdltbl = new PdfPTable(2);
				mdltbl.setWidthPercentage(100);
				mdltbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				mdltbl.setWidths(new int[]{50,50});
				mdltbl.setTotalWidth(100);
										
				PdfPCell lblblnk5 = new PdfPCell(new Phrase("GUARANTOR DETAILS:", bldFont));
				lblblnk5.setBorderColor(BaseColor.WHITE);
				mdltbl.addCell(lblblnk5);
				PdfPCell lblblnk6 = new PdfPCell(new Phrase(""));
				lblblnk6.setBorderColor(BaseColor.WHITE);
				mdltbl.addCell(lblblnk6);
				
				document.add(mdltbl);
				document.add(new Paragraph(""));
				
				//section II Guarantors	
				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidths(new int[]{40,30,30});
				table.setTotalWidth(100);
				addTableHeader(table);
				//add table rows
				
				PdfPCell lblsurname = new PdfPCell(new Phrase("Surname", font));
				table.addCell(lblsurname);					
				PdfPCell g1surname = new PdfPCell(new Phrase(strG1surname, font));
	            table.addCell(g1surname);					
	            PdfPCell g2surname = new PdfPCell(new Phrase(strG2surname, font));
	            table.addCell(g2surname);
	           
	            PdfPCell lblfirstname = new PdfPCell(new Phrase("Firstname", font));
				table.addCell(lblfirstname);					
				PdfPCell g1firstname = new PdfPCell(new Phrase(strG1firstname, font));
	            table.addCell(g1firstname);					
	            PdfPCell g2firstname = new PdfPCell(new Phrase(strG2firstname, font));
	            table.addCell(g2firstname);
	            
	            PdfPCell lblothername = new PdfPCell(new Phrase("Othername", font));
				table.addCell(lblothername);					
				PdfPCell g1othername = new PdfPCell(new Phrase(strG1othername, font));
	            table.addCell(g1othername);					
	            PdfPCell g2othername = new PdfPCell(new Phrase(strG2othername, font));
	            table.addCell(g2othername);
	            
	            PdfPCell lblnatid = new PdfPCell(new Phrase("National Id", font));
				table.addCell(lblnatid);					
				PdfPCell g1natid = new PdfPCell(new Phrase(strG1nationalid, font));
	            table.addCell(g1natid);					
	            PdfPCell g2natid = new PdfPCell(new Phrase(strG2nationalid, font));
	            table.addCell(g2natid);
	            
	            PdfPCell lblgmobileno = new PdfPCell(new Phrase("Mobile No", font));
				table.addCell(lblgmobileno);					
				PdfPCell g1gmobile = new PdfPCell(new Phrase(strG1mobileno, font));
	            table.addCell(g1gmobile);					
	            PdfPCell g2gmobile = new PdfPCell(new Phrase(strG2mobileno, font));
	            table.addCell(g2gmobile);
	            
	            PdfPCell lblgadd = new PdfPCell(new Phrase("Address", font));
				table.addCell(lblgadd);					
				PdfPCell g1gadd = new PdfPCell(new Phrase(strG1address, font));
	            table.addCell(g1gadd);					
	            PdfPCell g2gadd = new PdfPCell(new Phrase(strG2address, font));
	            table.addCell(g2gadd);
	            
	            PdfPCell lblgpadd = new PdfPCell(new Phrase("Permanent Address", font));
				table.addCell(lblgpadd);					
				PdfPCell g1gpadd = new PdfPCell(new Phrase(strG1permanentaddress, font));
	            table.addCell(g1gpadd);					
	            PdfPCell g2gpadd = new PdfPCell(new Phrase(strG2permanentaddress, font));
	            table.addCell(g2gpadd);
	            
	            PdfPCell lblgyrsinadd = new PdfPCell(new Phrase("Years in Address", font));
				table.addCell(lblgyrsinadd);					
				PdfPCell g1gyrsinadd = new PdfPCell(new Phrase(strG1yrsinaddress, font));
	            table.addCell(g1gyrsinadd);					
	            PdfPCell g2gyrsinadd = new PdfPCell(new Phrase(strG2yrsinaddress, font));
	            table.addCell(g2gyrsinadd);
	            
	            PdfPCell lblgor = new PdfPCell(new Phrase("Own House or Rented", font));
				table.addCell(lblgor);					
				PdfPCell g1gor = new PdfPCell(new Phrase(strG1ownhouserented, font));
	            table.addCell(g1gor);					
	            PdfPCell g2gor = new PdfPCell(new Phrase(strG2ownhouserented, font));
	            table.addCell(g2gor);
	            
	            PdfPCell lblgrpm = new PdfPCell(new Phrase("Rent per Month", font));
				table.addCell(lblgrpm);					
				PdfPCell g1grpm = new PdfPCell(new Phrase(strG1rentpm, font));
	            table.addCell(g1grpm);					
	            PdfPCell g2grpm = new PdfPCell(new Phrase(strG2rentpm, font));
	            table.addCell(g2grpm);
	            
	            PdfPCell lblgnok = new PdfPCell(new Phrase("Next of Kin", font));
				table.addCell(lblgnok);					
				PdfPCell g1gnok = new PdfPCell(new Phrase(strG1nextofkin, font));
	            table.addCell(g1gnok);					
	            PdfPCell g2gnok = new PdfPCell(new Phrase(strG2nextofkin, font));
	            table.addCell(g2gnok);
	            
	            PdfPCell lblgnokmobile = new PdfPCell(new Phrase("Nexk of Kin Mobile", font));
				table.addCell(lblgnokmobile);					
				PdfPCell g1gnokmobile = new PdfPCell(new Phrase(strG1nokmobileno, font));
	            table.addCell(g1gnokmobile);					
	            PdfPCell g2gnokmobile = new PdfPCell(new Phrase(strG2nokmobileno, font));
	            table.addCell(g2gnokmobile);
	            
	            PdfPCell lblgnokr = new PdfPCell(new Phrase("Next of Kin Relationship", font));
				table.addCell(lblgnokr);					
				PdfPCell g1gnokr = new PdfPCell(new Phrase(strG1nokrelationship, font));
	            table.addCell(g1gnokr);					
	            PdfPCell g2gnokr = new PdfPCell(new Phrase(strG2nokrelationship, font));
	            table.addCell(g2gnokr);

	            PdfPCell lblgbikeregno = new PdfPCell(new Phrase("Bike Regno", font));
				table.addCell(lblgbikeregno);					
				PdfPCell g1gbikeregno = new PdfPCell(new Phrase(strG1bikeregno, font));
	            table.addCell(g1gbikeregno);					
	            PdfPCell g2gbikeregno = new PdfPCell(new Phrase(strG2bikeregno, font));
	            table.addCell(g2gbikeregno);
	            
	            PdfPCell lblgbikeowner = new PdfPCell(new Phrase("Bike Owner", font));
				table.addCell(lblgbikeowner);					
				PdfPCell g1gbikeowner = new PdfPCell(new Phrase(strG1surname, font));
	            table.addCell(g1gbikeowner);					
	            PdfPCell g2gbikeowner = new PdfPCell(new Phrase(strG2surname, font));
	            table.addCell(g2gbikeowner);
	            
	            PdfPCell lblgsalaried = new PdfPCell(new Phrase("Salaried", font));
				table.addCell(lblsurname);					
				PdfPCell g1gsalaried = new PdfPCell(new Phrase(strG1salaried, font));
	            table.addCell(g1gsalaried);					
	            PdfPCell g2gsalaried = new PdfPCell(new Phrase(strG2salaried, font));
	            table.addCell(g2gsalaried);
	            
	            PdfPCell lblgempname = new PdfPCell(new Phrase("Employer Name", font));
				table.addCell(lblgempname);					
				PdfPCell g1gempname = new PdfPCell(new Phrase(strG1employername, font));
	            table.addCell(g1gempname);					
	            PdfPCell g2gempname = new PdfPCell(new Phrase(strG2employername, font));
	            table.addCell(g2gempname);
	            
	            PdfPCell lblgmincome = new PdfPCell(new Phrase("Monthly Income", font));
				table.addCell(lblgmincome);					
				PdfPCell g1gmincome = new PdfPCell(new Phrase(strG1monthlyincome, font));
	            table.addCell(g1gmincome);					
	            PdfPCell g2gmincome = new PdfPCell(new Phrase(strG2monthlyincome, font));
	            table.addCell(g2gmincome);
	            
	            PdfPCell lblgois = new PdfPCell(new Phrase("Other Income Source", font));
				table.addCell(lblgois);					
				PdfPCell g1gois = new PdfPCell(new Phrase(strG1surname, font));
	            table.addCell(g1gois);					
	            PdfPCell g2gois = new PdfPCell(new Phrase(strG2surname, font));
	            table.addCell(g2gois);
	            
	            PdfPCell lblgoi = new PdfPCell(new Phrase("Other Income", font));
				table.addCell(lblgoi);					
				PdfPCell g1goi = new PdfPCell(new Phrase(strG1otherincome, font));
	            table.addCell(g1goi);					
	            PdfPCell g2goi = new PdfPCell(new Phrase(strG2otherincome, font));
	            table.addCell(g2goi);
				
	            PdfPCell lblgstage = new PdfPCell(new Phrase("Stage Name", font));
				table.addCell(lblgstage);					
				PdfPCell g1gstage = new PdfPCell(new Phrase(strG1stagename, font));
	            table.addCell(g1gstage);					
	            PdfPCell g2gstage = new PdfPCell(new Phrase(strG2stagename, font));
	            table.addCell(g2gstage);
	            
	            PdfPCell lblgstageadd = new PdfPCell(new Phrase("Stage Address", font));
				table.addCell(lblgstageadd);					
				PdfPCell g1gstageadd = new PdfPCell(new Phrase(strG1stageaddress, font));
	            table.addCell(g1gstageadd);					
	            PdfPCell g2gstageadd = new PdfPCell(new Phrase(strG2stageaddress, font));
	            table.addCell(g2gstageadd);
	            
	            PdfPCell lblgstgchairconf = new PdfPCell(new Phrase("Stage Chairman Confirmation", font));
				table.addCell(lblgstgchairconf);					
				PdfPCell g1gstgchairconf = new PdfPCell(new Phrase(strG1stagechairconf, font));
	            table.addCell(g1gstgchairconf);					
	            PdfPCell g2gstgchairconf = new PdfPCell(new Phrase(strG2stagechairconf, font));
	            table.addCell(g2gstgchairconf);
	            
	            PdfPCell lblgchair = new PdfPCell(new Phrase("Local Chairman", font));
				table.addCell(lblgchair);					
				PdfPCell g1gchair = new PdfPCell(new Phrase(strG1lcname, font));
	            table.addCell(g1gchair);					
	            PdfPCell g2gchair = new PdfPCell(new Phrase(strG2lcname, font));
	            table.addCell(g2gchair);
	            
	            PdfPCell lblglcmobile = new PdfPCell(new Phrase("LC Mobile no", font));
				table.addCell(lblglcmobile);					
				PdfPCell g1glcmobile = new PdfPCell(new Phrase(strG1lcmobile, font));
	            table.addCell(g1glcmobile);					
	            PdfPCell g2glcmobile = new PdfPCell(new Phrase(strG2lcmobile, font));
	            table.addCell(g2glcmobile);
	            
	            PdfPCell lblgstgchairman = new PdfPCell(new Phrase("Stage Chairman", font));
				table.addCell(lblgstgchairman);					
				PdfPCell g1gstgchairman = new PdfPCell(new Phrase(strG1stagechairname, font));
	            table.addCell(g1gstgchairman);					
	            PdfPCell g2gstgchairman = new PdfPCell(new Phrase(strG2stagechairname, font));
	            table.addCell(g2gstgchairman);
	            
	            PdfPCell lblgstgchairmanmobile = new PdfPCell(new Phrase("Stage Chairman Mobile", font));
				table.addCell(lblgstgchairmanmobile);					
				PdfPCell g1gstgchairmanmobile = new PdfPCell(new Phrase(strG1stagechairmobile, font));
	            table.addCell(g1gstgchairmanmobile);					
	            PdfPCell g2gstgchairmanmobile = new PdfPCell(new Phrase(strG2stagechairmobile, font));
	            table.addCell(g2gstgchairmanmobile);
	            
	            PdfPCell lblgtvremarks = new PdfPCell(new Phrase("TV Remarks", font));
				table.addCell(lblgtvremarks);					
				PdfPCell g1gtvremarks = new PdfPCell(new Phrase(strG1tvcremarks, font));
	            table.addCell(g1gtvremarks);					
	            PdfPCell g2gtvremarks = new PdfPCell(new Phrase(strG2tvcremarks, font));
	            table.addCell(g2gtvremarks);
	            
	            PdfPCell lblgfiremarks = new PdfPCell(new Phrase("FI Remarks", font));
				table.addCell(lblgfiremarks);					
				PdfPCell g1gfiremarks = new PdfPCell(new Phrase(strG1ficremarks, font));
	            table.addCell(g1gfiremarks);					
	            PdfPCell g2gfiremarks = new PdfPCell(new Phrase(strG2ficremarks, font));
	            table.addCell(g2gfiremarks);
	            
	            PdfPCell lblgcam = new PdfPCell(new Phrase("CAM", font));
				table.addCell(lblgcam);					
				PdfPCell g1gcam = new PdfPCell(new Phrase(strG1coapproval, font));
	            table.addCell(g1gcam);					
	            PdfPCell g2gcam = new PdfPCell(new Phrase(strG2coapproval, font));
	            table.addCell(g2gcam);
	            
	            PdfPCell lblgcamremarks = new PdfPCell(new Phrase("CAM Remarks", font));
				table.addCell(lblgcamremarks);					
				PdfPCell g1gcamremarks = new PdfPCell(new Phrase(StrG1coremarks, font));
	            table.addCell(g1gcamremarks);					
	            PdfPCell g2gcamremarks = new PdfPCell(new Phrase(StrG2coremarks, font));
	            table.addCell(g2gcamremarks);
	            
	            PdfPCell lblgcamapp = new PdfPCell(new Phrase("CAM Approval", font));
				table.addCell(lblgcamapp);					
				PdfPCell g1gcamapp = new PdfPCell(new Phrase(strG1cooapproval, font));
	            table.addCell(g1gcamapp);					
	            PdfPCell g2gcamapp = new PdfPCell(new Phrase(strG2cooapproval, font));
	            table.addCell(g2gcamapp);
	            
	            PdfPCell lblgcamappremarks = new PdfPCell(new Phrase("CAM Approval Remarks", font));
				table.addCell(lblgcamappremarks);					
				PdfPCell g1gcamappremarks = new PdfPCell(new Phrase(StrG1cooremarks, font));
	            table.addCell(g1gcamappremarks);					
	            PdfPCell g2gcamappremarks = new PdfPCell(new Phrase(StrG2cooremarks, font));
	            table.addCell(g2gcamappremarks);
	            		            
	            document.add(table);
	            
				document.close();
				
				 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CustomerProfile" + strCustid + ".pdf");
		         headers.setContentType(MediaType.APPLICATION_PDF);
		         resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));       
			
				//document.open();
//				file = new File(strPdffilename);
			    
//			    if (file.exists()) {

//			    	FileInputStream inputStream = new FileInputStream(file);
//		            resource1 = new InputStreamResource(inputStream);
//
//		            // Set headers
//		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);		    
			    
		        	
//		        	response.reset();
//		        	response.addHeader("Pragma", "public");
//		        	response.addHeader("Cache-Control", "max-age=0");
//		        	response.setHeader("Content-disposition", "attachment;filename=\"%s\"" + file.getName());
//		        	response.setContentType("application/pdf");
//		        	
//		        	response.setContentLength(encodedBytes.length);
//		        	ServletOutputStream servletOutputStream = response.getOutputStream();
//		        	try {
//		        		servletOutputStream.write(encodedBytes);
//		        	} catch (IOException ioExObj) {
//		        		System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
//		        	} finally {
//		        		servletOutputStream.flush();
//		                servletOutputStream.close();			                
//		        	}		    
//			    }			    
				
			} catch (Exception e) {
				System.out.println("Error printing payment schedule");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(baos.size())
                .body(resource1);
	}
	
	private void addTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
		
		Stream.of("Particulars","Guarantor I Details","Guarantor II Details")
			.forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setPhrase(new Phrase(columnTitle, hdrfont));
				table.addCell(header);
		});
	}
	
}
