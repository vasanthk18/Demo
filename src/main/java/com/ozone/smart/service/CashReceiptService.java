package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ozone.smart.entity.DownPayment;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.repository.CollectionRepo;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.DownPaymentRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.fieldValidation;
import com.ozone.smart.util.formatDigits;
import com.ozone3.smart.pojo.conversionTool;
//import org.apache.commons.io.IOUtils;

@Service
public class CashReceiptService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;
	
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;
	
	@Autowired
	private DownPaymentRepo dpRepo;
	
	@Autowired
	private CollectionRepo collectionRepo;
	
	@Autowired
    private ResourceLoader resourceLoader;

	
	public ResponseEntity<InputStreamResource> saveCashReceipt(CashReceiptDto cashReceiptDto) {
		
		Response<String> response = new Response<>();
		String strMsg ="";
		String strCashcustid = "";
		String strCashprop = "";
		String strCashpaytype = "";
		String strCashvehicle = "";
		String strCashamount = "";
		String strCashnarr = "";
		String strRequestdatetime = "";
		//String strTruncreqdatetime = "";
		String strTranref = "";
		String strDate = "";
		String strPaydate = "";
		String strnPaydate = "";
		String strPaymode = "";
		String strGroupedamount = "";
		String strAmtinwords = "";
		String strEwi = "";
		String strInst = "";
		String strAmtdue = "";
		String strInvoice = "";
		String strCustomer = "";
		String strPenamount = "";
		String strAmount = "";
		String strnewToday = "";
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
			
		int dblAmt = 0;
		int intEwi = 0;
		int intInst = 0;
		int intAmtdue = 0;
		int intCashamount = 0;
		
		String strPaid = "0";
		String strLoginuser = "";	

		int intPaid = 0;
		int intPenamount = 0;
		
		String strBalance = "";
		int intBalance = 0;
		
		boolean blnDatevalid = false;
		

		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		
		ByteArrayOutputStream baos = null;
		
		
//		String strImgfilename = logo.png;
//		InputStream in = getClass()
//			      .getResourceAsStream("/src/main/resources/static/logo.png");	
//		
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("static/logo.png");
		
		Image Imagefiles = null;
		try {
			try {
				 Imagefiles = Image.getInstance(IOUtils.toByteArray(inputStream));
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		strCashcustid = cashReceiptDto.getCustomer();
		strCashprop = cashReceiptDto.getProposal();
		strCashpaytype = cashReceiptDto.getPaytype();
		strCashvehicle = cashReceiptDto.getVehregno();
		strCashamount = cashReceiptDto.getAmount();
		strCashnarr = cashReceiptDto.getNarrative();
		strPaydate = cashReceiptDto.getPaydate();
		strPaymode = cashReceiptDto.getPaymode();
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
		} else if ((strCashvehicle == null || strCashvehicle.length() == 0) && (strCashpaytype.equals("EWI"))) {				
			response.setData("Please select a vehicle for installment payment");								
		} else if (strPaydate == null || strPaydate.length() == 0) {					
			response.setData("Please ensure posting date format is dd/mm/yyyy");
		} else if (blnDatevalid == false) {				
			response.setData("Please ensure posting date format is dd/mm/yyyy");
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			strRequestdatetime = strRequestdatetime.replace("/", "");
			strRequestdatetime = strRequestdatetime.replace(":", "");
			strRequestdatetime = strRequestdatetime.replace(" ", "");
			strDate = gts.standardDate();
//			strDate = "16082024347688";
			System.out.println("strDate standardate = "+strDate);
			
			strDate = strDate.replace("/", "");
			strPaydate = strPaydate.replace("/", "");
			
			strnewToday = gts.newstandardDate();
//			strnewToday = "2024-08-16";
		  System.out.println("strnewToday newstandardate = "+strnewToday);
//			strnewToday = "09/08/2024";

			LocalDate localDate = LocalDate.parse(strnewToday);
			java.sql.Date date = java.sql.Date.valueOf(localDate);
			System.out.println("date = "+date);
			
//			String newToday = date.toString();
			
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
			List<CustomerDetails> custdet = customerRepo.findByProposalNo(strCashprop);
			if (custdet.size() != 0) {
				for (CustomerDetails cd:custdet) {
					strCustomer = cd.getFirstname() + " " + cd.getSurname();
				}
			}
			
			String maxWeekreceipt = weeklyInstRepo.findMaxWeekreceipt();
			int nextWeekReceiptNumber;

			// Check if there are any existing partreceipt numbers
			if (maxWeekreceipt != null && !maxWeekreceipt.isEmpty()) {
			    // Extract the numeric part and increment it
			    int maxWeekReceiptNumber = Integer.parseInt(maxWeekreceipt.substring(2));
			    nextWeekReceiptNumber = maxWeekReceiptNumber + 1;
			} else {
			    // If no existing partreceipt numbers, start from 10000010
				nextWeekReceiptNumber = 10007374;
			}

			// Generate the partreceipt number
			String weekReceipt = "IN" + nextWeekReceiptNumber;
			
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
				col.setReceiptid(weekReceipt);
				
				try {
					collectionRepo.save(col);
				}catch(Exception e) {
					e.printStackTrace();
				}
				}
			
			if (strCashpaytype.equals("EWI")) {
				WeeklyInstallment weekly = new WeeklyInstallment();
				weekly.setProposalno(strCashprop);
				weekly.setVehicleregno(strCashvehicle);
				weekly.setTransactiontype(strCashpaytype);
				weekly.setTransactionref(strTranref);
				weekly.setAmount(strCashamount);
				weekly.setPaymentmode(strPaymode);
				weekly.setPaymentdate(strRequestdatetime);
				weekly.setCaptureuser(strLoginuser);
				weekly.setWeekreceipt(weekReceipt);
				
				try {
					weeklyInstRepo.save(weekly);						
					
					//fetch invoice number
					List<WeeklyInstallment> weeklyinst =  weeklyInstRepo.findByParameters(strCashprop, strTranref, strCashamount, strCashvehicle);
					if (weeklyinst.size() != 0) {
						for (WeeklyInstallment wi:weeklyinst) {
							strInvoice = wi.getWeekreceipt();
						}
					}										
					//fetch schedules due as at today						
					
					List<PaymentSchedule> paysch = paymentScheduleRepo.findByPaymentDateAndLoanId(date, strCashprop);
					if (paysch.size() != 0) {							
						for (PaymentSchedule ps:paysch) {
							strEwi = ps.getInstallment();
							intInst = ps.getSchedule();
						}
								
						intEwi = Integer.parseInt(strEwi);
						intAmtdue = intEwi * intInst;
						
						strInst = Integer.toString(intInst);
					}	
					
					//fetch penalty due
					List<Penalty> penalty = penaltyRepo.findByloanid(strCashprop) ;
					if (penalty.size() > 0) {
						for (Penalty pen:penalty) {
							strPenamount = pen.getPenalty();
							intPenamount += Integer.parseInt(strPenamount);
						}							
					} else {
						intPenamount = intCashamount;
					}
					
					intAmtdue += intPenamount;
					strAmtdue = Integer.toString(intAmtdue);
					
					
					//fetch paid till date
					List<WeeklyInstallment> wkinstallment =  weeklyInstRepo.findByRevFlagAndProposalNo(strCashprop);
					if (wkinstallment.size() > 0) {
						for (WeeklyInstallment wkinst:wkinstallment) {
							strAmount = wkinst.getAmount();
							intPaid += Integer.parseInt(strAmount);
						}							
					} else {
						intPaid = intCashamount;
					}					
											
					strPaid = Integer.toString(intPaid);
					
					//get Balance
					intBalance = intAmtdue - intPaid;
					strBalance = Integer.toString(intBalance);
					
					strAmtdue = fd.digit(strAmtdue);
					strPaid = fd.digit(strPaid);
					strBalance = fd.digit(strBalance);
					
					response.setData("Installment for : " + strCashvehicle + " saved successfully." + "|" + strTranref + "|" + strDate + 
							"|" + strGroupedamount + "|" + strAmtinwords + "|" + strEwi + "|" + strInst + "|" + strAmtdue +
							"|" + strPaid + "|" + strBalance + "|" + strInvoice + "|" + strCustomer);
				} catch (Exception e) {
					e.printStackTrace();
					Throwable th = e.getCause();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
					strMsg = th.getCause().toString();
					if (strMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1") || 
							strMsg.contains("The database returned no natively generated identity value") ||
							strMsg.contains("ConstraintViolationException")) {					
						response.setData("Installment for : " + strCashvehicle + " saved successfully." + "|" + strTranref + "|" + strDate + 
								"|" + strGroupedamount + "|" + strAmtinwords + "|" + strEwi + "|" + strInst + "|" + strAmtdue +
								"|" + strPaid + "|" + strBalance + "|" + strInvoice + "|" + strCustomer);
					}
				}
				
			} else if (strCashpaytype.equals("DP")) {
				
				String maxDownreceipt = dpRepo.findMaxDownreceipt();
				int nextDownReceiptNumber;

				if (maxDownreceipt != null && !maxDownreceipt.isEmpty()) {
				    int maxDownReceiptNumber = Integer.parseInt(maxDownreceipt.substring(2));
				    nextDownReceiptNumber = maxDownReceiptNumber + 1;
				} else {
					nextDownReceiptNumber = 10001368;
				}

				String downReceipt = "DP" + nextDownReceiptNumber;
				
				
				DownPayment dp = new DownPayment();
				dp.setProposalno(strCashprop);
				dp.setTransactiontype(strCashpaytype);
				dp.setTransactionref(strTranref);
				dp.setAmount(strCashamount);
				dp.setPaymentmode(strPaymode);
				dp.setPaymentdate(strRequestdatetime);
				dp.setCaptureuser(strLoginuser);
				dp.setDownreceipt(downReceipt);
				
				try {
					dpRepo.save(dp);
					
					//fetch invoice number
					List<DownPayment> downpay = dpRepo.findByProposalNoAndTransactionRefAndAmount(strCashprop, strTranref, strCashamount);
					if (downpay.size() != 0) {
						for (DownPayment downp:downpay) {
							strInvoice = downp.getDownreceipt();
						}
					}		
					
					response.setData("Down Payment for : " + strCashprop + " saved successfully." + "|" + strTranref + "|" + strDate + 
							"|" + strGroupedamount + "|" + strAmtinwords + "|" + strInvoice + "|" + strCustomer);
				} catch (Exception e) {
					Throwable th = e.getCause();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
					strMsg = th.getCause().toString();
					if (strMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1") || 
							strMsg.contains("The database returned no natively generated identity value") ||
							strMsg.contains("ConstraintViolationException")) {					
						strMsg = "Down Payment for : " + strCashprop + " saved successfully." + "|" + strTranref + "|" + strDate + "|" +
							strGroupedamount + "|" + strAmtinwords + "|" + strInvoice + "|" + strCustomer;
					}
				}
			} 
			
			//Write pdf receipt
			try {					
//				strPdffilename = "src/main/resources/Documents/Receipt" + strInvoice + ".pdf";
				baos = new ByteArrayOutputStream();
				Document document = new Document();
				PdfWriter.getInstance(document, baos);
//				PdfWriter.getInstance(document, new FileOutputStream(strPdffilename));
				document.open();
				Font boldfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
				Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
				Font underlinefont = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
				
				//section I
//				Image image = Image.getInstance(Imagefiles);
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
									
				if (strCashpaytype.equals("EWI")) {
					PdfPCell lblvehicle = new PdfPCell(new Phrase("Installment for Vehicle Reg No. :" + strCashvehicle,font));
					lblvehicle.setBorderColor(BaseColor.WHITE);
					table.addCell(lblvehicle);
				} else {
					PdfPCell lblproposal = new PdfPCell(new Phrase("Down Payment for " + strCashprop,font));
					lblproposal.setBorderColor(BaseColor.WHITE);
					table.addCell(lblproposal);
				}
				
				PdfPCell lblpaid = new PdfPCell(new Phrase("UGX " + strGroupedamount,font));
				lblpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblpaid.setBorderColor(BaseColor.WHITE);
				table.addCell(lblpaid);
				
				if (strCashpaytype.equals("EWI")) {
					table.addCell(lblamtinwords);						
					table.addCell(lblblank1);
				}
				
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
									
				table.addCell(lblblank1);
				table.addCell(lblblank1);
				
				if (strCashpaytype.equals("EWI")) {
					PdfPCell lblamtdue = new PdfPCell(new Phrase("Actual Amount Due (" + strEwi + " * " + strInst + ") + Penalty",font));
					lblamtdue.setBorderColor(BaseColor.WHITE);
					table.addCell(lblamtdue);
					PdfPCell amtdue = new PdfPCell(new Phrase("UGX " + strAmtdue,font));
					amtdue.setHorizontalAlignment(Element.ALIGN_RIGHT);
					amtdue.setBorderColor(BaseColor.WHITE);
					table.addCell(amtdue);
					
					PdfPCell lblpaidtilldate = new PdfPCell(new Phrase("Paid till date ",font));
					lblpaidtilldate.setBorderColor(BaseColor.WHITE);
					table.addCell(lblpaidtilldate);
					PdfPCell paidtilldate = new PdfPCell(new Phrase("UGX " + strPaid,font));
					paidtilldate.setHorizontalAlignment(Element.ALIGN_RIGHT);
					paidtilldate.setBorderColor(BaseColor.WHITE);
					table.addCell(paidtilldate);
					
					PdfPCell lblbal = new PdfPCell(new Phrase("Overdue ",font));
					lblbal.setBorderColor(BaseColor.WHITE);
					table.addCell(lblbal);
					PdfPCell bal = new PdfPCell(new Phrase("UGX " + strBalance,underlinefont));
					bal.setHorizontalAlignment(Element.ALIGN_RIGHT);
					bal.setBorderColor(BaseColor.WHITE);
					table.addCell(bal);
				} else {
					table.addCell(lblamtinwords);							
					PdfPCell lbldpamt = new PdfPCell(new Phrase("UGX " + strGroupedamount,underlinefont));
					lbldpamt.setHorizontalAlignment(Element.ALIGN_RIGHT);
					lbldpamt.setBorderColor(BaseColor.WHITE);
					table.addCell(lbldpamt);						
				}
				
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
				

		         // Set headers
		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Receipt" + strInvoice + ".pdf");
		            headers.setContentType(MediaType.APPLICATION_PDF);
		            resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));       
//			    }			    
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error printing payment receipt");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}	
//		if (!strMsg.equals("") && (strMsg.contains("Please"))) {	
//			 return response.setData(strMsg);
//		}
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(baos.size())
                .body(resource1);

	}

}
