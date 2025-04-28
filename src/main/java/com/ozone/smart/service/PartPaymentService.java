package com.ozone.smart.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ozone.smart.dto.CashReceiptDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Collection;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.entity.PartPayment;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.repository.CollectionRepo;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.PartPaymentRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.fieldValidation;
import com.ozone.smart.util.formatDigits;
import com.ozone3.smart.pojo.conversionTool;

//import jakarta.annotation.Resource;
//import jakarta.servlet.ServletOutputStream;

@Service
public class PartPaymentService {
//	private String strMsg;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private PartPaymentRepo ppRepo;
	
	@Autowired
	private ImpoundedStockRepo impStockRepo;
	
	
	@Autowired
	private PaymentScheduleRepo psRepo;
	
	
	@Autowired
	private LoanParamRepo loanParamRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private CollectionRepo collectionRepo;
	
//	private

	public ResponseEntity<InputStreamResource> getPayment(CashReceiptDto cashReceiptDto) {
	
		String strMsg = "";
		String strCashcustid = "";
		String strCashprop = "";
		String strCashpaytype = "";
		String strCashvehicle = "";
		String strCashamount = "";
		String strCashnarr = "";
		String strRequestdatetime = "";
		String strPenalty = "";
		String strTranref = "";
		String strDate = "";
		String strPaydate = "";
		String strnPaydate = "";
		String strPaymode = "";
		String strGroupedamount = "";
		String strAmtinwords = "";
		String strQuery = "";
		String strLoginuser = "";
		String strfiftythousand = "";
		String strtwentythousand = "";
		String strtenthousand = "";
		String strfivethousand = "";
		String strtwothousand = "";
		String strthousand = "";
		String strfivehundred ="";
		String strtwohundred = "";
		String strhundred ="";
		Boolean transferstatus = false;
		String strcoins ="";
		String strInvoice = "";
		String strCustomer = "";
			
		int dblAmt = 0;
		
		boolean blnDatevalid = false;

		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    Document document = new Document();
		    InputStreamResource resource;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;


		 
//		String strImgfilename = request.getRealPath("/logo.png");
		
		Response<String> response = new Response<>();
		
		strCashcustid = cashReceiptDto.getCustomer();
		strCashprop = cashReceiptDto.getProposal();
		strCashpaytype = cashReceiptDto.getPaytype();
		strCashvehicle = cashReceiptDto.getVehregno();
		strCashamount = cashReceiptDto.getAmount();
		strCashnarr = cashReceiptDto.getNarrative();
		strPaydate = cashReceiptDto.getPaydate();
		strPaymode = cashReceiptDto.getPaymode();
		strPenalty = cashReceiptDto.getPenalty();
		strLoginuser = cashReceiptDto.getUsername();
		strfiftythousand = cashReceiptDto.getFiftythousand();
		if(strfiftythousand == null) {
			strfiftythousand = "0";
		}
		strtwentythousand = cashReceiptDto.getTwentythousand();
		if(strtwentythousand == null) {
			strtwentythousand = "0";
		}
		strtenthousand = cashReceiptDto.getTenthousand();
		if(strtenthousand ==null) {
			strtenthousand = "0";
		}
		strfivethousand = cashReceiptDto.getFivethousand();
		if(strfivethousand == null) {
			strfivethousand = "0";
		}
		strtwothousand = cashReceiptDto.getTwothousand();
		if(strtwothousand == null) {
			strtwothousand = "0";
		}
		strthousand = cashReceiptDto.getThousand();
		if(strthousand==null) {
			strthousand = "0";
		}
		strfivehundred = cashReceiptDto.getFivehundred();
		if(strfivehundred == null) {
			strfivehundred = "0";
		}
		strtwohundred = cashReceiptDto.getTwohundred();
		if(strtwohundred == null) {
			strtwohundred = "0";
		}
		strhundred = cashReceiptDto.getHundred();
		if(strhundred ==null) {
			strhundred = "0";
		}
		strcoins = cashReceiptDto.getCoins();
		if(strcoins==null) {
			strcoins = "0";
		}
		strnPaydate = strPaydate;
		
		strCashprop = strCashprop.trim();
		strCashvehicle = strCashvehicle.replace("Vehicle Reg No. :", "");
		strPenalty = strPenalty.replace("Impound charges to clear :", "");
		strCashpaytype = "PART";
		
		fieldValidation fv = new fieldValidation();
		
		if (fv.validateDate(strPaydate) == "success") {
			blnDatevalid = true;
		} else {blnDatevalid = false;}
					
		if (strCashcustid == null || strCashcustid.length() == 0) {				
			response.setData("Please select customer id");			
		} else if (strCashprop == null || strCashprop.length() == 0) {					
			response.setData("Please select proposal id");
		} else if (strCashamount == null || strCashamount.length() == 0) {				
			response.setData("Please enter recieved amount");
		} else if (strCashvehicle == null || strCashvehicle.length() == 0) {				
			response.setData("Please select a vehicle for part payment");
		} else if (strPenalty == null || strPenalty.length() == 0) {				
			response.setData("Please enter amount for part payment");
		} else if (strPaydate == null || strPaydate.length() == 0) {				
			response.setData("Please ensure posting date format is dd/mm/yyyy");
		} else if (blnDatevalid == false) {				
			response.setData("Please ensure posting date format is dd/mm/yyyy");
		} 
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			strRequestdatetime = strRequestdatetime.replace("/", "");
			strRequestdatetime = strRequestdatetime.replace(":", "");
			strRequestdatetime = strRequestdatetime.replace(" ", "");
			strDate = gts.standardDate();
			
			strDate = strDate.replace("/", "");
			strPaydate = strPaydate.replace("/", "");
			
			strRequestdatetime = strRequestdatetime.replaceFirst(strDate, strPaydate);
			strDate = strnPaydate;
			
			strTranref = strCashprop + strRequestdatetime.substring(0, 12);
			formatDigits fd = new formatDigits();
			strGroupedamount = fd.digit(strCashamount);

			dblAmt = Integer.parseInt(strCashamount);
			conversionTool ct = new conversionTool();
			strAmtinwords = ct.convert(dblAmt);
			strAmtinwords = "Ushs " + strAmtinwords + " only";			
									
			//fetch customer name
//			strQuery = "From CustomerDetails where otherid in (select customerid from Proposal where proposalno = '" + strCashprop + "')";
			List<CustomerDetails> custdet = custRepo.findByProposalNo(strCashprop);
			if (custdet.size() != 0) {
				for (CustomerDetails cd:custdet) {
					strCustomer = cd.getFirstname() + " " + cd.getSurname();
				}
			}
			
			String maxPartReceipt = ppRepo.findMaxPartReceipt();
			int nextPartReceiptNumber;

			// Check if there are any existing partreceipt numbers
			if (maxPartReceipt != null && !maxPartReceipt.isEmpty()) {
			    // Extract the numeric part and increment it
			    int maxPartReceiptNumber = Integer.parseInt(maxPartReceipt.substring(2));
			    nextPartReceiptNumber = maxPartReceiptNumber + 1;
			} else {
			    // If no existing partreceipt numbers, start from 10000010
			    nextPartReceiptNumber = 10000019;
			    nextPartReceiptNumber = 10000019;
			}
			
			
			
			try {
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream("static/logo.png");
				
				String partReceipt = "IN" + nextPartReceiptNumber;
				
				if(strPaymode.equalsIgnoreCase("CASH")) {
					Collection col = new Collection();
					col.setAmount(strCashamount);
					col.setCoins(strcoins);
					col.setCollectionpersonname(strLoginuser);
					col.setCustid(strCashcustid);
					col.setDate(strnPaydate);
					col.setFiftythousand(strfiftythousand);
					col.setFivehundred(strfivehundred);
					col.setFivethousand(strfivethousand);
					col.setHundred(strhundred);
					col.setTenthousand(strtenthousand);
					col.setThousand(strthousand);
					col.setTransferstatus(transferstatus);
					col.setTwentythousand(strtwentythousand);
					col.setTwohundred(strtwohundred);
					col.setTwothousand(strtwothousand);
					col.setVehicleregno(strCashvehicle);
					col.setReceiptid(partReceipt);
					
					try {
						collectionRepo.save(col);
					}catch(Exception e) {
						e.printStackTrace();
					}
					}
				
				PartPayment pp = new PartPayment();
				pp.setProposalno(strCashprop);
				pp.setVehicleregno(strCashvehicle);
				pp.setTransactiontype(strCashpaytype);
				pp.setTransactionref(strTranref);
				pp.setAmount(strCashamount);
				pp.setPaymentmode(strPaymode);
				pp.setPaymentdate(strRequestdatetime);
				pp.setCaptureuser(strLoginuser);
				pp.setPartreceipt(partReceipt);
				
				try {
					pp = ppRepo.save(pp);
					
					
					ppRepo.flush();
					//fetch invoice number
//					strQuery = "From PartPayment where proposalno = '" + strCashprop + "' and transactionref = '" + strTranref + "' and amount = '" + strCashamount + "'";
					List<PartPayment> partpay =  ppRepo.findByParameters(strCashprop, strTranref, strCashamount);
					if (partpay.size() != 0) {
						for (PartPayment downp:partpay) {
							String transref = downp.getTransactionref();
							strInvoice = downp.getPartreceipt();
						}
					}		
					
					strMsg = "Part Payment for : " + strCashprop + " saved successfully." + "|" + strTranref + "|" + strDate + 
							"|" + strGroupedamount + "|" + strAmtinwords + "|" + strInvoice + "|" + strCustomer;
				} catch (Exception e) {
					Throwable th = e.getCause();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
					strMsg = th.getCause().toString();
					if (strMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1") || 
							strMsg.contains("The database returned no natively generated identity value") ||
							strMsg.contains("ConstraintViolationException")) {					
						strMsg = "Part Payment for : " + strCashprop + " saved successfully." + "|" + strTranref + "|" + strDate + "|" +
							strGroupedamount + "|" + strAmtinwords + "|" + strInvoice + "|" + strCustomer;
					}
				}
				
				try {
					List<Impoundedstock> impound = impStockRepo.findByproposalno(strCashprop);
					impound.forEach(imp -> imp.setStatus("PAID"));
					impStockRepo.saveAll(impound);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				Image Imagefiles = null;
				 
				Imagefiles = Image.getInstance(IOUtils.toByteArray(inputStream));
				
//				strPdffilename = "src/main/resources/Documents/PartReceipt" + strInvoice + ".pdf";
//				ByteArrayOutputStream baos  = new ByteArrayOutputStream();
//				Document document = new Document();
				PdfWriter.getInstance(document, baos);
				
//				PdfWriter.getInstance(document, new FileOutputStream(strPdffilename));
				document.open();
				Font boldfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
				Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
				Font underlinefont = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
				
				//section I
//				Image image = Image.getInstance(Imagefiles.getAbsolutePath());
				document.add(Imagefiles);
				
				document.add(new Paragraph(""));
				Chunk chunk = new Chunk("Date: " + strDate, font);
				document.add(chunk);
				document.add(new Paragraph(""));
				Chunk chunk01 = new Chunk("Receipt No: " + strInvoice, font);
				document.add(chunk01);
				document.add(new Paragraph(""));
				Chunk chunk02 = new Chunk("Customer Name: " + strCustomer, font);
				document.add(chunk02);
									
				document.add(new Paragraph("\n"));
				
				//section II					
				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(100);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidths(new int[]{75,25});
				table.setTotalWidth(100);
				
				//blank
				PdfPCell lblblank1 = new PdfPCell(new Phrase(" ",font));
				lblblank1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblblank1.setBorderColor(BaseColor.WHITE);
				//amt in words
				PdfPCell lblamtinwords = new PdfPCell(new Phrase("Amount(in words): " + strAmtinwords,font));
				lblamtinwords.setBorderColor(BaseColor.WHITE);
				
				PdfPCell lblhdr1 = new PdfPCell(new Phrase("Particulars",boldfont));
				lblhdr1.setBorderColor(BaseColor.WHITE);
				table.addCell(lblhdr1);
				PdfPCell lblhdr2 = new PdfPCell(new Phrase("Amount",boldfont));
				lblhdr2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblhdr2.setBorderColor(BaseColor.WHITE);
				table.addCell(lblhdr2);
									
				
				PdfPCell lblvehicle = new PdfPCell(new Phrase("Part Payment for :" + strCashprop,font));
				lblvehicle.setBorderColor(BaseColor.WHITE);
				table.addCell(lblvehicle);												
				PdfPCell lblpaid = new PdfPCell(new Phrase("UGX " + strGroupedamount,font));
				lblpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblpaid.setBorderColor(BaseColor.WHITE);
				table.addCell(lblpaid);
				
				PdfPCell lblpaymode = new PdfPCell(new Phrase("Payment mode: " + strPaymode,font));
				lblpaymode.setBorderColor(BaseColor.WHITE);
				table.addCell(lblpaymode);
				table.addCell(lblblank1);
									
				table.addCell(lblblank1);
				table.addCell(lblblank1);
				
				PdfPCell lblnarr = new PdfPCell(new Phrase(strCashnarr,font));
				lblnarr.setBorderColor(BaseColor.WHITE);
				table.addCell(lblnarr);
				table.addCell(lblblank1);											

				table.addCell(lblamtinwords);						
				PdfPCell lblpaidamt = new PdfPCell(new Phrase("UGX " + strGroupedamount,underlinefont));
				lblpaidamt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblpaidamt.setBorderColor(BaseColor.WHITE);
				table.addCell(lblpaidamt);
										
				document.add(table);	
				
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));
				
				//SECOND RECEIPT					
				//section I					
				document.add(Imagefiles);
				
				document.add(new Paragraph(""));
				document.add(chunk);
				document.add(new Paragraph(""));
				document.add(chunk01);
				document.add(new Paragraph(""));
				document.add(chunk02);
									
				document.add(new Paragraph("\n"));
				
				//section II
				document.add(table);
									
				document.close();
				
//				file = new File(strPdffilename);
			    
//			    if (file.exists()) {

				 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Receipt" + strInvoice + ".pdf");
		            headers.setContentType(MediaType.APPLICATION_PDF);
		            resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));     	    		    
		            
		            
		    		if (!strMsg.equals("") && (strMsg.contains("Please"))) {
		    			headers.setContentType(MediaType.TEXT_PLAIN);
		    	        headers.set(HttpHeaders.CONTENT_ENCODING, "UTF-8");
		    	        headers.add("X-Message", strMsg);
		    		}
		            
		            
		            return ResponseEntity.ok()
		                    .headers(headers)
		                    .contentLength(baos.size())
		                    .body(resource1);
				
			}catch(Exception e) {
				System.out.println("Error printing payment receipt");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		
	}

private void addTableHeader(PdfPTable table) {
	Font whdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
	
	Stream.of("Particulars", "          Amount")
		.forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase(columnTitle, whdrfont));
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

public Response<String> getTotal(String propNo) {

	
	String strAgreementno = "";
	String strAgreementqry = "";
	String strImpounddate = "";
	String strNewimpounddate = "";
	String strProposalno = "";
	String strVehicle = "";
	String strImpound = "";
	
	String strInst = "";
	
	String strPenalty = "";
	
	int intOverdue = 0;
	int intImpound = 0;
			
	int intTotalpen = 0;
	int intTotaltopay = 0;
	
	String strOverdue = "";
	String strTotalpen = "";
	String strTotaltopay = "";		
	
	strProposalno = propNo;
	strProposalno = strProposalno.trim();
	
	
	Response<String> response = new Response<>();
			
	if (strProposalno == null || strProposalno.length() == 0) {			
		response.setData("Please select proposal no");
	} else {
		formatDigits fd = new formatDigits();
		
		try {
			List<Impoundedstock> impound = impStockRepo.findByproposalno(strProposalno) ;
			if (impound.size() > 0) {
				for (Impoundedstock ims:impound) {
					strAgreementno = ims.getAgreementno();
					strVehicle = ims.getVehicleregno();
					strImpounddate = ims.getImpounddate();
				}	
				
				strAgreementqry = strAgreementno + "%";
				
				//Payment schedule -- 20/12/2019
				strNewimpounddate = strImpounddate.substring(6, 10) + "-" + strImpounddate.substring(3, 5) + "-" +strImpounddate.substring(0, 2);
//				strQuery = "From PaymentSchedule " +
//						"where scheduleno like '" + strAgreementqry + "' and to_date(paymentdate, 'dd/mm/yyyy') <= '" + strNewimpounddate + "'" +
//						" and status != 'PAID'";
			
				try {
					List<PaymentSchedule> paysch = psRepo.findByScheduleDateStatus(strAgreementqry, strNewimpounddate);
					
					for (PaymentSchedule ps:paysch) {
						strInst = ps.getInstallment();
						intOverdue+= Integer.parseInt(strInst);
					}
					
					strOverdue = Integer.toString(intOverdue);
					strOverdue = fd.digit(strOverdue);
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				//Penalty
				
//				strQuery = "From Penalty where loanscheduleno like '" + strAgreementqry + "' and (status = '' or status is null)";
			
				try {
					List<Penalty> penalty = penaltyRepo.findByLoanSchdlNoIdAndStatusIsNull(strAgreementqry);
					
					for (Penalty pen:penalty) {
						strPenalty = pen.getPenalty();	
						intTotalpen+= Integer.parseInt(strPenalty);
					}
					
					strTotalpen = Integer.toString(intTotalpen);
					strTotalpen = fd.digit(strTotalpen);
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
//				strQuery = "From LoanParam";
				
				try {
					List<LoanParam> loan = loanParamRepo.findAll() ;
					
					for (LoanParam ln:loan) {
						strImpound = ln.getImpound();
					}
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			
				intImpound = Integer.parseInt(strImpound);
				intTotaltopay = intOverdue + intTotalpen + intImpound;
				
				strTotaltopay = Integer.toString(intTotaltopay);
				strTotaltopay = fd.digit(strTotaltopay);
			}
							
			response.setData(strVehicle + "|" +  strTotaltopay);
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			response.setData("Error retreiving details for agreement no: " + strAgreementno);
		}
	}
	return response;
}
}
