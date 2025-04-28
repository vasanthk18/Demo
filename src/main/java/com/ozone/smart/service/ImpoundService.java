package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ozone.smart.dto.CategoryDto;
import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.LoanStatusDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.LoanStatus;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanStatusRepo;
import com.ozone.smart.util.FileUploadUtil;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class ImpoundService {
	
	
	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private ImpoundedStockRepo impStockRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private LoanStatusRepo loanStatusRepo;

	public Response<String> viewImpound(String agreeNo) {
		String strMsg = "";
		String strQuery = "";
		
		String strAgreementno = "";
		String strTrcagreement = "";
		String strProposalno = "";
		String strVehicle = "";
				
		strAgreementno = agreeNo;
		
		Response<String> response = new Response<>();
		
		if (strAgreementno == null || strAgreementno.length() == 0) {			
			strMsg = "Please select agreement no";
		} else {
			
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { e.printStackTrace(); }
			 */
			
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
			
			strTrcagreement = strAgreementno.substring(2, 8);
			int agreementserial = Integer.parseInt(strTrcagreement);
			
//			strQuery = "From CustomerVehicle where agreementserial = '" + strTrcagreement + "'";
			
			try {
				List<CustomerVehicle> custveh = custVehRepo.findByagreementserial(agreementserial);
				
				for (CustomerVehicle cv:custveh) {
					strProposalno = cv.getProposalno();
					strVehicle = cv.getVehicleregno();
				}				
				
				strMsg = strProposalno + "|" +  strVehicle;
				
			} catch (Exception e) {
				e.printStackTrace();
				strMsg = "Error retreiving details for agreement no: " + strAgreementno;
			}			
		}
		response.setData(strMsg);
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(strMsg);
		return response;
	}

	public Response<String> saveImpound(ImpStockDto impStockDto) {
		
		String strRequestdatetime;
		String strMsg="";
		String strAgreementno = "";
		String strProposal = "";
		String strVehicle = "";
		String strRemarks = "";
		String strImpounddate = "";
		String strLoginuser = "";
		
		Response<String> response = new Response<>();
		
		strAgreementno = impStockDto.getAgreementno();
		strProposal = impStockDto.getProposalno();
		strVehicle = impStockDto.getVehicleregno();
		strRemarks = impStockDto.getRemarks();
		strLoginuser = impStockDto.getCaptureuser();
						
		if (strAgreementno == null || strAgreementno.length() == 0) {				
			strMsg = "Please select agreement";				
		} else if (strProposal == null || strProposal.length() == 0) {					
			strMsg = "Please select agreement";				
		} else if (strVehicle == null || strVehicle.length() == 0) {			
			strMsg = "Please select agreement";	
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			strRequestdatetime = gts.TimeStamp();
			strRequestdatetime = strRequestdatetime.trim();
			strImpounddate = gts.standardDate();
										
			/*
			 * try { InitialContext ic = new InitialContext(); libraryBean =
			 * (easybodaPersistentBeanRemote)ic.lookup(
			 * "java:module/easybodaPersistentBean!com.ozone.stateless.easybodaPersistentBeanRemote"
			 * ); } catch (NamingException e) { System.out.println(e.getLocalizedMessage());
			 * }
			 */	
//			Session em = (Session)request.getAttribute("sh");
//			easybodaPersistentBeanRemote libraryBean = new easybodaPersistentBean(em);
				
			Impoundedstock is = new Impoundedstock();
			is.setAgreementno(strAgreementno);
			is.setProposalno(strProposal);
			is.setVehicleregno(strVehicle);
			is.setImpounddate(strImpounddate);
			is.setRemarks(strRemarks);
			is.setCaptureuser(strLoginuser);
			is.setCapturedatetime(strRequestdatetime);
			is.setStatus("");
			
			try {
				impStockRepo.save(is);
				strMsg = "Vehicle regno " + strVehicle + " impounded successfully";
				
//				strQuery = "Update Loan set impounded = true where proposalno = '" + strProposal + "'";
				int intUser = loanRepo.updateLoanForImpound(strProposal);
				if (intUser > 0) {
					System.out.println("Vehicle " + strVehicle + " impounded successfully");
				}
			} catch (Exception e) {
				Throwable th = e.getCause();
//			    System.out.println("THROWABLE INFO: " + th.getCause().toString());			         
				response.setErrorMsg(th.getCause().toString());					
//			    System.out.println("THROWABLE INFO: " + th.getCause().toString());			         
				response.setErrorMsg(th.getCause().toString());					
			}			
		}
		response.setData(strMsg);
		return response;
	}

	public Response<String> savePhoto(String username, String agreeNo ,MultipartFile multipartFile) {
		String UPLOAD_DIRECTORY = "src/main/resources/Documents/";
		String strUploadfilename = "";
		String strMsg="";
		Response<String> response = new Response<>();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			try {
				
//				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				strUploadfilename = agreeNo + filename;	
						try {
//							strQuery = "From Impoundedstock where agreementno = '" + agreeNo + "'";
							Optional<Impoundedstock> impound = impStockRepo.findById(agreeNo) ;							
							if (impound.isPresent()) {
//								item.write(new File(UPLOAD_DIRECTORY + strUploadfilename));
								FileUploadUtil.saveFile(strUploadfilename, multipartFile, UPLOAD_DIRECTORY + strUploadfilename);
								//update impoundedstock with filename								
//								strQuery = "UPDATE Impoundedstock set vehphotofilename = '" + strUploadfilename + "' where agreementno = '" + strAgreement + "'";
								int intUser = impStockRepo.updateImpound(strUploadfilename, agreeNo);
								if (intUser == 1) {
									strMsg = "Impounded vehicle photo file : " + strUploadfilename + " uploaded successfully";
								} else {
									strMsg = "Error updating impound stock for " + agreeNo;
								}
								
							} else {
								strMsg = "Upload aborted, ensure impounded vehicle details are saved in db";
							}
						} catch (Exception e) {
							strMsg = "File upload failed due to" + e;
						}

			} catch (Exception ex) {
				strMsg = "File upload failed due to" + ex;
			}
		} else {
			strMsg = "Sorry this servlet only handles file upload request";
		}
		response.setData(strMsg);
		return response;
	}

	public Response<List<LoanStatusDto>> viewImpounds() {
		
		Response<List<LoanStatusDto>> response = new Response<>();
		
		List<LoanStatus> loanStatus = loanStatusRepo.findloanStatus() ;
		
		List<LoanStatusDto> categoryDtoList = new ArrayList<>();
		
		for(LoanStatus ls : loanStatus) {
			LoanStatusDto lsDto = new LoanStatusDto();
			lsDto.setAgreementno(ls.getAgreementno());
			categoryDtoList.add(lsDto);		
		}
		response.setData(categoryDtoList);
		return response;
	}

}
