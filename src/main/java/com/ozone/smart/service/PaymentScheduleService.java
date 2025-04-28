package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class PaymentScheduleService {
	
	@Autowired
	private CustomerVehicleRepo customerVehicleRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;

	public ResponseEntity<InputStreamResource> printSchedule(String custId, String agreeId) {
		
		String strAgreement = "";
		String strRegno = "";
		String strPrintDate = "";
		String strCustid = "";
		String strAgreementno = "";
		String strProposal = "";
		String strCustomername = "";
		String strVehicle = "";
		
		String strScheduleno = "";
		String strSchedule = "";
		String strPaymentdate = "";
		String strAmount = ""; //installment
		String strPrincipal = "";
		String strInterest = "";
		String strRunningbal = "";
		
		int AgreementSerial = 0;
		
		String strPdffilename = "";
		
		String strProposalno = "";
		String strVehicledet = "";
		String strBrand = "";
		String strModel = "";
		
		String strReleasedate = "";
		
		String strVeharray [] = null;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File newfile = null ;
		
		strCustid = custId;
		strAgreement = agreeId;
		
		strAgreement = strAgreement.trim();
		strCustid = strCustid.trim();
		ByteArrayOutputStream baos = null;
		
		Response<String> response = new Response<>();
		
		formatDigits fd = new formatDigits();
							
		if (strCustid == null || strCustid.length() == 0) {			
			response.setData("Please select customer");		
		} else if (strAgreement == null || strAgreement.length() == 0) {
			response.setData("Please select agreement");
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			strPrintDate = gts.newfmtTimeStamp();
							
//			String[] arrOfStr = strAgreement.split("::"); 
//			strAgreement = arrOfStr[0];
			
			strAgreementno = strAgreement;
			strAgreement = strAgreement.replace("AN", "");
			AgreementSerial = Integer.parseInt(strAgreement);
						
			try {
				List<CustomerVehicle> custveh = customerVehicleRepo.findCustomerVehiclesByAgreeIdAndCustId(AgreementSerial, strCustid);
								
				for (CustomerVehicle cv:custveh) {
					strProposal = cv.getProposalno();
					strRegno = cv.getVehicleregno();
					strProposalno = cv.getProposalno();
					strReleasedate = cv.getDisbdatetime();
					
					strReleasedate = strReleasedate.substring(0, 10);
				}
				
			}catch (Exception e) {}	
			
			try {
				CustomerDetails custdet =custRepo.findByotherid(strCustid);
					strCustomername = custdet.getSurname() + " " + custdet.getFirstname();
				
			}catch (Exception e) {}	
			
			try {
				Proposal proposal = propRepo.findByproposalno(strProposalno);
				
					strVehicledet = proposal.getVehicleregno();
				
				strVeharray = strVehicledet.split(" | ");
				if (strVeharray.length == 7) {
					strBrand = strVeharray[0];
					strModel = strVeharray[2];
				} else if (strVeharray.length == 1) {
					strBrand = strVeharray[0];
					strModel = strVeharray[0];
				} else {
					strBrand = "";
					strModel = "";
				}
				strVehicle =  strBrand + " " + strModel;
				
			} catch (Exception e) {
				System.out.println("Error retrieving proposal details for : " + strProposalno);
			}
			
			try {
				
				baos = new ByteArrayOutputStream();
	            Document document = new Document();
//				strPdffilename = "src/main/resources/Documents/Schedule" + strAgreementno + ".pdf";
//				Document document = new Document();
//				PdfWriter.getInstance(document, new FileOutputStream(strPdffilename));
	            PdfWriter.getInstance(document, baos);
				document.open();
				Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
				Font tblfont = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
				Chunk chunk = new Chunk("Customer Name: " + strCustomername, font);
				document.add(chunk);
				document.add(new Paragraph(""));
				Chunk chunk01 = new Chunk("Proposal No: " + strProposal, font);
				document.add(chunk01);
				document.add(new Paragraph(""));
				Chunk chunk02 = new Chunk("Agreement No: " + strAgreementno, font);
				document.add(chunk02);
				document.add(new Paragraph(""));
				Chunk chunk03 = new Chunk("Bike Brand: " + strVehicle, font);
				document.add(chunk03);
				document.add(new Paragraph(""));
				Chunk chunk04 = new Chunk("Bike Registration No: " + strRegno, font);
				document.add(chunk04);
				document.add(new Paragraph(""));
				Chunk chunk05 = new Chunk("Release Date: " + strReleasedate, font);
				document.add(chunk05);
				document.add(new Paragraph("\n\n"));	
					
				PdfPTable table = new PdfPTable(6);
				table.setWidthPercentage(90);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidths(new int[]{17,15,13,15,15,25});
				table.setTotalWidth(100);
				addTableHeader(table);
				//add table rows				
				strAgreementno += '%';
				
				try {
					
					List<PaymentSchedule> payschedule = paymentScheduleRepo.findPaymentSchedulesByLoanIdAndScheduleNo(strProposalno, strAgreementno);
					
					if (payschedule.size() > 0 ) {
						for (PaymentSchedule paysch:payschedule) {
							strScheduleno = paysch.getScheduleno();
							strPaymentdate = paysch.getPaymentdate();
							strAmount = paysch.getInstallment();
							strPrincipal = paysch.getPrincipal();
							strInterest = paysch.getInterestamount();
							strRunningbal = paysch.getRunningamount();
							
							strSchedule = strScheduleno.substring(8);
														
							if(!strAmount.contains(",")) {strAmount = fd.digit(strAmount);}
							if(!strPrincipal.contains(",")) {strPrincipal = fd.digit(strPrincipal);}
							if(!strInterest.contains(",")) {strInterest = fd.digit(strInterest);}
							if(!strRunningbal.contains(",")) {strRunningbal = fd.digit(strRunningbal);}
							
							PdfPCell cellSchedule = new PdfPCell(new Phrase(strSchedule, tblfont));
							table.addCell(cellSchedule);
							PdfPCell cellPaymentdate = new PdfPCell(new Phrase(strPaymentdate, tblfont));
							table.addCell(cellPaymentdate);
							PdfPCell cellAmount = new PdfPCell(new Phrase(strAmount, tblfont));
							cellAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
				            table.addCell(cellAmount);
							//table.addCell(strAmount);
				            PdfPCell cellPrincipal = new PdfPCell(new Phrase(strPrincipal, tblfont));
							cellPrincipal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				            table.addCell(cellPrincipal);
							//table.addCell(strPrincipal);
				            PdfPCell cellInterest = new PdfPCell(new Phrase(strInterest, tblfont));
				            cellInterest.setHorizontalAlignment(Element.ALIGN_RIGHT);
				            table.addCell(cellInterest);
							//table.addCell(strInterest);
				            PdfPCell cellRunningbal = new PdfPCell(new Phrase(strRunningbal, tblfont));
				            cellRunningbal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				            table.addCell(cellRunningbal);
							//table.addCell(strRunningbal);
							
						}
						document.add(table);
						response.setData("Payment schedule printed successfully");
					} else {
						response.setData("Loan has not been disbursed.");
					}
				} catch (Exception e) {}
				
				document.close();
			
				//document.open();
//				newfile = new File(strPdffilename);
			    
//			    if (newfile.exists()) {
//			    	FileInputStream inputStream = new FileInputStream(newfile);
//		            resource1 = new InputStreamResource(inputStream);

		            // Set headers
		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Schedule" + strAgreementno + ".pdf");
		            headers.setContentType(MediaType.APPLICATION_PDF);
		            resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));   
		        		    
//			    }			    
				
			} catch (Exception e) {
				System.out.println("Error printing payment schedule");
			}
			
		}				
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(baos.size())
                .body(resource1);
	}
	
	private void addTableHeader(PdfPTable table) {
		Stream.of("Schedule No", "Due Date","    Amount","      Principal","       Interest","      Running Balance")
			.forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				//header.setBorderWidth(1);
				header.setPhrase(new Phrase(columnTitle));
				table.addCell(header);
		});
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

}
