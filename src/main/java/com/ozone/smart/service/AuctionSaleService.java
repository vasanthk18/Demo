package com.ozone.smart.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.AuctionDto;
import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Auctionsale;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.repository.AuctionSaleRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class AuctionSaleService {
	@Autowired
	private AuctionSaleRepo auctionSaleRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private ImpoundedStockRepo impStockRepo;

	public Response<String> saveAuction(AuctionDto auctionDto) {
	
		String strAgreementno = "";
		String strOverdue = "";
		String strProposalno = "";
		String strVehicle = "";
		String strImpdate = "";
		String strBuyer = "";			
		String strMobileno = "";
		String strTin = "";
		String strPenalty = "";			
		String strAddress = "";			
		String strImpchgs = "";
		String strSaleamount = "";
		String strAssetcost = "";
		String strPrincipalout = "";
		String strProfit = "";
		String strVatonsale = "";
		String strMsg ="";
		String strStatus = "AUCTIONED";
		
		String strRequestdatetime = "";
		String strLoginuser = "";
		
		Response<String> response = new Response<>();
		
		strAgreementno = auctionDto.getAgreementno();
		strProposalno = auctionDto.getProposalno();
		strVehicle = auctionDto.getVehicle();
		strImpdate = auctionDto.getImpounddate();
		strOverdue = auctionDto.getOverdue();
		strPenalty = auctionDto.getPenalty();
		strImpchgs = auctionDto.getImpoundcharges();		
		strPrincipalout = auctionDto.getPrincipalout();
		strAssetcost = auctionDto.getAssetcost();
		strBuyer = auctionDto.getBuyername();			
		strMobileno = auctionDto.getMobileno();			
		strTin = auctionDto.getTin();
		strAddress = auctionDto.getAddress();
		strSaleamount = auctionDto.getSaleamount();
		strProfit = auctionDto.getProfit();
		strVatonsale = auctionDto.getVatonsale();
		strLoginuser = auctionDto.getAucuser();
					
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strVehicle == null || strVehicle.length() == 0) {			
			strMsg = "Please select agreement no";
		} else if (strSaleamount == null || strSaleamount.length() == 0) {			
			strMsg = "Please enter sale amount";
		} else if (strProfit == null || strProfit.length() == 0) {			
			strMsg = "Please enter sale amount";
		} else if (strVatonsale == null || strVatonsale.length() == 0) {			
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
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			
			Auctionsale aucsale = new Auctionsale();
			aucsale.setAgreementno(strAgreementno);
			aucsale.setVehicle(strVehicle);
			aucsale.setImpounddate(strImpdate);
			aucsale.setOverdue(strOverdue);
			aucsale.setPenalty(strPenalty);
			aucsale.setImpoundcharges(strImpchgs);
			aucsale.setPrincipalout(strPrincipalout);
			aucsale.setAssetcost(strAssetcost);
			aucsale.setBuyername(strBuyer);
			aucsale.setMobileno(strMobileno);
			aucsale.setTin(strTin);
			aucsale.setAddress(strAddress);
			aucsale.setSaleamount(strSaleamount);
			aucsale.setProfit(strProfit);
			aucsale.setVatonsale(strVatonsale);
			aucsale.setAucuser(strLoginuser);
			aucsale.setAucdatetime(strRequestdatetime);
			
			try {					
				auctionSaleRepo.save(aucsale);
				strMsg = "Auction sale for " + strAgreementno + " saved successfully" ;
				
//				strQuery = "Update Loan set auctioned = true where proposalno = '" + strProposalno + "'";
				int intUser = loanRepo.updateLoanForAuction(strProposalno);
				if (intUser > 0) {
					System.out.println("Loan " + strProposalno + " auctioned successfully");
				}							
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				strMsg = "Error while saving auction sale for : " + strAgreementno;
			}			
		}
		
		response.setData(strMsg);
		return response;
	}

	public Response<String> getCal(String salecost, String salevalue, String pout) {
		
		String strSalecost = "";
		String strSalevalue = "";
		String strPout = "";
		String strProfit = "";
		String strVat = "";
		
		double dblSalecost = 0;
		double dblSalevalue = 0;
		double dblPout = 0;
		double dblProfit = 0;
		double dblVat = 0;
				
		String strMsg = "";
		
		Response<String> response = new Response<>();
		
		strSalecost = salecost;
		strSalevalue = salevalue;	
		strPout = pout;	
		
		if (strSalecost == null || strSalecost.length() == 0) {			
			strMsg =  "Please select agreement";		
		} else if (strSalevalue == null || strSalevalue.length() == 0) {			
			strMsg = "Please enter sale value";
		} else if (strPout == null || strPout.length() == 0) {			
			strMsg = "Please select agreement";
		} else {
			
			strSalecost = strSalecost.replace(",", "");
			strSalevalue = strSalevalue.replace(",", "");
			strPout = strPout.replace(",", "");
			
			dblSalecost = Double.parseDouble(strSalecost);
			dblSalevalue = Double.parseDouble(strSalevalue);
			dblPout = Double.parseDouble(strPout);
						
			dblProfit = dblSalevalue - dblSalecost;
			strProfit = Double.toString(dblProfit);
			
			dblVat = ((dblProfit + dblPout) * 18)/118;
			dblVat = Math.round(dblVat * 100) / 100D;
			strVat = Double.toString(dblVat);
			
			formatDigits fd = new formatDigits();
			strProfit = fd.digit(strProfit);
			strVat = fd.digit(strVat);
			
			strMsg = strProfit + "|" + strVat;
		}

		response.setData(strMsg);
		
		
		return response;
	}

	public ResponseEntity<InputStreamResource> printAgree(String agreement) {
		
		String strQuery = "";
		String strAgreement = "";
		String strAgreementfilename = "";
		String strAgreementno = "";
		int AgreementSerial = 0;
		String strPdffilename = "";
		String strMsg = "";
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		strAgreement = agreement;			
		strAgreement = strAgreement.trim();
		
		Response<String> response = new Response<>();
							
		if (strAgreement == null || strAgreement.length() == 0) {			
			strMsg = "Please select agreement no";
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
				
			strAgreementno = strAgreement;
			strAgreementno = strAgreementno.replace("AN", "");
			AgreementSerial = Integer.parseInt(strAgreementno);
						
			try {
//				strQuery = "From CustomerVehicle where agreementserial = " + AgreementSerial;
				List<CustomerVehicle> custveh = custVehRepo.findByagreementserial(AgreementSerial);
								
				for (CustomerVehicle cv:custveh) {
					strAgreementfilename = cv.getAgreementfilename();
				}
				
			}catch (Exception e) {}	
			
							
			try {
				strPdffilename = "c:/Uploads/" + strAgreementfilename;
								
				//document.open();
				file = new File(strPdffilename);
			    
			    if (file.exists()) {
			    	FileInputStream inputStream = new FileInputStream(file);
		            resource1 = new InputStreamResource(inputStream);

		            // Set headers
		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);		    
			    
		        	
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
			    }			    
				
			} catch (Exception e) {
				System.out.println("Error printing payment schedule");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			headers.add("x-message", strMsg);	
		}				
		//response.setContentType("text/plain");
		//response.setCharacterEncoding("UTF-8");
		//response.getWriter().write(strMsg);

//		session.setAttribute("pagreid", "");
		
		
		return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource1);
	}

	public Response<List<ImpStockDto>> getAgreeNo() {
		Response<List<ImpStockDto>> response = new Response<>();
		
		List<Impoundedstock> loanStatus = impStockRepo.getAgreeNo() ;
		
		List<ImpStockDto> categoryDtoList = new ArrayList<>();
		
		for(Impoundedstock ls : loanStatus) {
			ImpStockDto lsDto = new ImpStockDto();
			lsDto.setAgreementno(ls.getAgreementno());
			categoryDtoList.add(lsDto);		
		}
		response.setData(categoryDtoList);
		return response;
	}	
	

}
