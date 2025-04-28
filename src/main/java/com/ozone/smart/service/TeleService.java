package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ozone.smart.component.TotalDue;
import com.ozone.smart.component.buckets;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.LoanStatus;
import com.ozone.smart.entity.Ptp;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.LoanStatusRepo;
import com.ozone.smart.repository.PtpRepo;
import com.ozone.smart.util.formatDigits;
import java.util.Date;
import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service
public class TeleService {
	
	@Autowired
	private LoanStatusRepo loanStatusRepo;

	@Autowired
	private PtpRepo ptpRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private buckets bucks;
	
	@Autowired
	private TotalDue td;
	
	@Value("${s3.bucket.name}")
    private String bucketName;
	
	
	public ResponseEntity<String> generateTele(String datetime) {
		
		
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
					
		String strAgreement = "";
		String strCustomername = "";
		String strMobileno = "";
		String strInstallment = "";
		String strOverdue = "";
		String strPtpdate = "";
		String strPtpremarks = "";			
		String strProposalno = "";
					
		String[] bucketsArray;
		String[] buckArray1;
		String[] buckArray2;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		int i = 0;
		int intCounter = 0;
		
		Date dtNulldate = null;
		Date dtPtpdate = null;
					
		final WritableCellFormat DATE_CELL_FRMT;
		
		Response<String> response = new Response<>();
		
		strDate = datetime;
							
		if (strDate == null || strDate.length() == 0) {
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
			
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "telerpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Bucket 3", 0);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            
            formatDigits fd = new formatDigits();
            
//            buckets bucks = new buckets();
            
            try {
				dtNulldate =  formatter.parse("01/01/1900");
			} catch (ParseException e1) {System.out.println(e1.getLocalizedMessage());}
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Agreement No ");
	            excelSheet.addCell(label);
	            		            
	            label = new Label(2, 0, "Customer Name ");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Mobile No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Installment ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Overdue ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "PTP Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "PTP Remarks ");
	            excelSheet.addCell(label);
	            		            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
	            //Bucket I
				try {				
					bucketsArray = bucks.Bucket("3");	
					
					for (i=0; bucketsArray.length > i; i++) {	
						strAgreement = bucketsArray[i];
//						strQuery = "From LoanStatus where agreementno = " + "'" + strAgreement + "'";
						List<LoanStatus> loanstatus = loanStatusRepo.findByagreementno(strAgreement);
						
						for (LoanStatus ls:loanstatus) {
							strProposalno = ls.getLoanid();
							strInstallment = ls.getInstallment();
						}	
						
//						strQuery = "From CustomerDetails where otherid in (Select customerid from Proposal where proposalno = " + "'" + strProposalno + "')";
						List<CustomerDetails> custdetails = custRepo.findByProposalNo(strProposalno);
						
						for (CustomerDetails custdet:custdetails) {
							strCustomername = custdet.getSurname() + " " + custdet.getFirstname();
							strMobileno = custdet.getMobileno();					
						}	
						
//						TotalDue td = new TotalDue();
						strOverdue = td.getTotalDue(strAgreement);
						if(!strOverdue.contains(",")) {strOverdue = fd.digit(strOverdue);}
						
						//reset ptp values
						strPtpdate = "";
						strPtpremarks = "";
						
//						strQuery = "From Ptp where agreementno = " + "'" + strAgreement + "'";
						List<Ptp> ptps = ptpRepo.findByagreementno(strAgreement);
						
						for (Ptp ptp:ptps) {
							strPtpdate = ptp.getPtpdate();
							strPtpremarks = ptp.getPtpremarks();
						}	
						
						if (strPtpdate == null || strPtpdate.length() == 0) {
							dtPtpdate = dtNulldate;
						} else {
							dtPtpdate = formatter.parse(strPtpdate);
						}
													
						intCounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);				            

			            label = new Label(1, intCounter, strAgreement);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomername);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strMobileno);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strInstallment);
			            excelSheet.addCell(label);				            
			            
			            label = new Label(5, intCounter, strOverdue);
			            excelSheet.addCell(label);
			            
			            if (dtPtpdate != dtNulldate) {
			            	DateTime datet = new DateTime(6, intCounter, dtPtpdate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
			            }
			            
			            label = new Label(7, intCounter, strPtpremarks);
			            excelSheet.addCell(label);
													
					}
					
				} catch (Exception e) {
					e.printStackTrace();
//					System.out.println(e.getLocalizedMessage());
				}
				
				//myDatadump.write();
				
				//Bucket II
				intCounter = 0;
	            WritableSheet excelSheet1 = myDatadump.createSheet("Bucket 2", 0);
	         
			    label = new Label(0, 0, "Sr No."); //first cell - first col, first row
		        excelSheet1.addCell(label);
		            
		        label = new Label(1, 0, "Agreement No ");
		        excelSheet1.addCell(label);
		            		            
		        label = new Label(2, 0, "Customer Name ");
		        excelSheet1.addCell(label);
		            
		        label = new Label(3, 0, "Mobile No ");
		        excelSheet1.addCell(label);
		            
		        label = new Label(4, 0, "Installment ");
		        excelSheet1.addCell(label);
		            
		        label = new Label(5, 0, "Overdue ");
		        excelSheet1.addCell(label);
		            
		        label = new Label(6, 0, "PTP Date ");
		        excelSheet1.addCell(label);
		            
		        label = new Label(7, 0, "PTP Remarks ");
		        excelSheet1.addCell(label);
		            
				try {
					buckArray1 = bucks.Bucket("2");	
					
					for (i=0; buckArray1.length > i; i++) {	
						strAgreement = buckArray1[i];
						
//						strQuery = "From LoanStatus where agreementno = " + "'" + strAgreement + "'";
						List<LoanStatus> loanstatus = loanStatusRepo.findByagreementno(strAgreement);
						
						for (LoanStatus ls:loanstatus) {
							strProposalno = ls.getLoanid();
							strInstallment = ls.getInstallment();
						}	
						
//						strQuery = "From CustomerDetails where otherid in (Select customerid from Proposal where proposalno = " + "'" + strProposalno + "')";
						List<CustomerDetails> custdetails = custRepo.findByProposalNo(strProposalno);
						
						for (CustomerDetails custdet:custdetails) {
							strCustomername = custdet.getSurname() + " " + custdet.getFirstname();
							strMobileno = custdet.getMobileno();					
						}	
						
//						TotalDue td = new TotalDue();
						strOverdue = td.getTotalDue(strAgreement);
						if(!strOverdue.contains(",")) {strOverdue = fd.digit(strOverdue);}
						
						//reset ptp values
						strPtpdate = "";
						strPtpremarks = "";
						
//						strQuery = "From Ptp where agreementno = " + "'" + strAgreement + "'";
						List<Ptp> ptps = ptpRepo.findByagreementno(strAgreement);
						
						for (Ptp ptp:ptps) {
							strPtpdate = ptp.getPtpdate();
							strPtpremarks = ptp.getPtpremarks();
						}	
						
						if (strPtpdate == null || strPtpdate.length() == 0) {
							dtPtpdate = dtNulldate;
						} else {
							dtPtpdate = formatter.parse(strPtpdate);
						}
													
						intCounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet1.addCell(number);				            

			            label = new Label(1, intCounter, strAgreement);
			            excelSheet1.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomername);
			            excelSheet1.addCell(label);
			            
			            label = new Label(3, intCounter, strMobileno);
			            excelSheet1.addCell(label);
			            
			            label = new Label(4, intCounter, strInstallment);
			            excelSheet1.addCell(label);				            
			            
			            label = new Label(5, intCounter, strOverdue);
			            excelSheet1.addCell(label);
			            
			            if (dtPtpdate != dtNulldate) {
			            	DateTime datet = new DateTime(6, intCounter, dtPtpdate, DATE_CELL_FRMT);
				            excelSheet1.addCell(datet);
			            }
			            
			            label = new Label(7, intCounter, strPtpremarks);
			            excelSheet1.addCell(label);
													
					}
					
				} catch (Exception e) {
					e.printStackTrace();
//					System.out.println(e.getLocalizedMessage());
				}
				
				//myDatadump.write();
				
				//Bucket III
				intCounter = 0;
				WritableSheet excelSheet2 = myDatadump.createSheet("Bucket 1", 0);
				
				label = new Label(0, 0, "Sr No."); //first cell - first col, first row
		        excelSheet2.addCell(label);
		            
		        label = new Label(1, 0, "Agreement No ");
		        excelSheet2.addCell(label);
		            		            
		        label = new Label(2, 0, "Customer Name ");
		        excelSheet2.addCell(label);
		            
		        label = new Label(3, 0, "Mobile No ");
		        excelSheet2.addCell(label);
		            
		        label = new Label(4, 0, "Installment ");
		        excelSheet2.addCell(label);
		            
		        label = new Label(5, 0, "Overdue ");
		        excelSheet2.addCell(label);
		            
		        label = new Label(6, 0, "PTP Date ");
		        excelSheet2.addCell(label);
		            
		        label = new Label(7, 0, "PTP Remarks ");
		        excelSheet2.addCell(label);
		        
				try {
					buckArray2 = bucks.Bucket("1");	
					
					for (i=0; buckArray2.length > i; i++) {	
						strAgreement = buckArray2[i];
						
//						strQuery = "From LoanStatus where agreementno = " + "'" + strAgreement + "'";
						List<LoanStatus> loanstatus = loanStatusRepo.findByagreementno(strAgreement);
						
						for (LoanStatus ls:loanstatus) {
							strProposalno = ls.getLoanid();
							strInstallment = ls.getInstallment();
						}	
						
						strQuery = "From CustomerDetails where otherid in (Select customerid from Proposal where proposalno = " + "'" + strProposalno + "')";
						List<CustomerDetails> custdetails = custRepo.findByProposalNo(strProposalno);
						
						for (CustomerDetails custdet:custdetails) {
							strCustomername = custdet.getSurname() + " " + custdet.getFirstname();
							strMobileno = custdet.getMobileno();					
						}	
						
//						TotalDue td = new TotalDue();
						strOverdue = td.getTotalDue(strAgreement);
						if(!strOverdue.contains(",")) {strOverdue = fd.digit(strOverdue);}
						
						//reset ptp values
						strPtpdate = "";
						strPtpremarks = "";
						
//						strQuery = "From Ptp where agreementno = " + "'" + strAgreement + "'";
						List<Ptp> ptps = ptpRepo.findByagreementno(strAgreement);
						
						for (Ptp ptp:ptps) {
							strPtpdate = ptp.getPtpdate();
							strPtpremarks = ptp.getPtpremarks();
						}	
						
						if (strPtpdate == null || strPtpdate.length() == 0) {
							dtPtpdate = dtNulldate;
						} else {
							dtPtpdate = formatter.parse(strPtpdate);
						}
													
						intCounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet2.addCell(number);				            

			            label = new Label(1, intCounter, strAgreement);
			            excelSheet2.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomername);
			            excelSheet2.addCell(label);
			            
			            label = new Label(3, intCounter, strMobileno);
			            excelSheet2.addCell(label);
			            
			            label = new Label(4, intCounter, strInstallment);
			            excelSheet2.addCell(label);				            
			            
			            label = new Label(5, intCounter, strOverdue);
			            excelSheet2.addCell(label);
			            
			            if (dtPtpdate != dtNulldate) {
			            	DateTime datet = new DateTime(6, intCounter, dtPtpdate, DATE_CELL_FRMT);
				            excelSheet2.addCell(datet);
			            }
			            
			            label = new Label(7, intCounter, strPtpremarks);
			            excelSheet2.addCell(label);
													
					}
					
				} catch (Exception e) {
					e.printStackTrace();
//					System.out.println(e.getLocalizedMessage());
				}
									
				myDatadump.write();
			
            } catch (IOException e) {System.out.println(e.getLocalizedMessage());
            } catch (WriteException e) {System.out.println(e.getLocalizedMessage());
            } finally {
                if (myDatadump != null) {
                    try {
                    	myDatadump.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                }
            }
							
			try {
				//Open xls file
				
				// Convert ByteArrayOutputStream to InputStream
	            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

	            // Upload to S3 bucket
	            AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
	            String keyName = strDate+"filename.xls"; 
	            
	      
	            metadata.setContentLength(outputStream.size());
	            metadata.setContentType("application/vnd.ms-excel");

	            s3client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, metadata));
			    
	            
	            java.util.Date expiration = new java.util.Date();
	            long expTimeMillis = expiration.getTime();
	            expTimeMillis += 1000 * 60 * 60; // 1 hour expiration time
	            expiration.setTime(expTimeMillis);

	            GeneratePresignedUrlRequest generatePresignedUrlRequest = 
	                    new GeneratePresignedUrlRequest(bucketName, keyName)
	                    .withMethod(HttpMethod.GET)
	                    .withExpiration(expiration);
	            
	            url = s3client.generatePresignedUrl(generatePresignedUrlRequest); 
		        
			} catch (Exception e) {
			    e.printStackTrace();
			}
		
		}
		return ResponseEntity.ok(url.toString());
//		return null;
	}

}
