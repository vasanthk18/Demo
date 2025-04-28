package com.ozone.smart.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Dealer;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.ReleaseLetter;
import com.ozone.smart.entity.Vehicle;
import com.ozone.smart.entity.pdfParams;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.DealerRepo;
import com.ozone.smart.repository.PdfParamRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.ReleaseLetterRepo;
import com.ozone.smart.repository.VehicleRepo;
import com.ozone.smart.util.TimeStampUtil;

import jakarta.servlet.ServletOutputStream;

@Service
public class ReleaseLetterService {
	
	@Autowired
	private PdfParamRepo pdfRepo;
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private DealerRepo dealerRepo;
	
	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ReleaseLetterRepo releaseRepo;
	
	@Autowired
	private CustomerVehicleRepo custVehicleRepo;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	

	public ResponseEntity<InputStreamResource> generateRL(String proposal, String custId, String username) {
		
		String strMsg = "";
		String strQuery = "";
		int intUser = 0;
		String strProposal = "";
		String strRegno = "";
		String strDay = "";
		String strMonth = "";
		String strYear = "";
		String strDate = "";
		String strCustomer = "";		
		String strEngine = "";
		String strChassis = "";
		String strCc = "";
		String strAgreement = "";
		String strYrinvt = "";
		
		String strDealer = "";
		String strPhyaddress = "";
		String strPostaddress  = "";
		String strTelephone1  = "";
		String strTelephone2  = "";
		String strEmail  = "";	
		
		String strSubject = "";
		String strReference = "";
		
		int intAgreement = 0;
		int dealerx = 0;
		int dealery = 0;
		int dphyaddx = 0;
		int dphyaddy = 0;
		int dpostaddx = 0;
		int dpostaddy = 0;
		int dtel1x = 0;
		int dtel1y = 0;
		int dtel2x = 0;
		int dtel2y = 0;
		int demailx = 0;
		int demaily = 0;
		int refx = 0;
		int refy = 0;
		
		int custnamex = 0;
		int custnamey = 0;
		int propx = 0;
		int propy = 0;
		int vregnox = 0;
		int vregnoy = 0;
		
		int enginex = 0;
		int enginey = 0;
		int chassisx = 0;
		int chassisy = 0;
		int natx = 0;
		int naty = 0;
		int datex = 0;
		int datey = 0;
		int brandx = 0;
		int brandy = 0;
		int modelx = 0;
		int modely = 0;
		
		String pdfFile = "";
		String newpdfFile = "";
		String strXYField = "";
		String strX = "";
		String strY = "";
		
		String strDealeradd = "";
		String strVehicle = "";
		String strBrand = "";
		String strModel = "";
				
		String strCustname = "";
		String strNatid = "";
		
		String reluser = "";
		String reldatetime = "";
		
		strProposal = proposal;
		strCustomer = custId;
		reluser = username;
		
		strProposal = strProposal.trim();
		strCustomer = strCustomer.trim();
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File newfile = null ;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
		if (strProposal == null || strProposal.length() == 0) {			
			strMsg = "Please select proposal";
		} else if (strCustomer == null || strCustomer.length() == 0) {			
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
			strDay = gts.newfmtDay();
			strMonth = gts.newfmtMonth();
			strYear = gts.newfmtYear();
			strDate = strDay + "/" + strMonth + "/" + strYear;
			
			reldatetime = gts.TimeStamp();
			
			strYrinvt = strYear.substring(2, 4) + "-" + strYear.substring(0, 2);
			
			try {
//				strQuery = "From pdfParams"; 
				List<pdfParams> pparam = pdfRepo.findAll();
								
				for (pdfParams pp:pparam) {
					strXYField = pp.getField();
					strX = pp.getXaxis();
					strY = pp.getYaxis();
					
					if (strXYField.equals("Rdealer")) {
						dealerx = Integer.parseInt(strX);
						dealery = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdealerphyadd")) {
						dphyaddx = Integer.parseInt(strX);
						dphyaddy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdealerpostadd")) {
						dpostaddx = Integer.parseInt(strX);
						dpostaddy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdealertel1")) {
						dtel1x = Integer.parseInt(strX);
						dtel1y = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdealertel2")) {
						dtel2x = Integer.parseInt(strX);
						dtel2y = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdealeremail")) {
						demailx = Integer.parseInt(strX);
						demaily = Integer.parseInt(strY);
					} else if (strXYField.equals("Rcustname")) {
						custnamex = Integer.parseInt(strX);
						custnamey = Integer.parseInt(strY);
					} else if (strXYField.equals("Rpropno")) {
						propx = Integer.parseInt(strX);
						propy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rvregno")) {
						vregnox = Integer.parseInt(strX);
						vregnoy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rengineno")) {
						enginex = Integer.parseInt(strX);
						enginey = Integer.parseInt(strY);
					} else if (strXYField.equals("Rchassisno")) {
						chassisx = Integer.parseInt(strX);
						chassisy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rnatid")) {
						natx = Integer.parseInt(strX);
						naty = Integer.parseInt(strY);
					} else if (strXYField.equals("Rdate")) {
						datex = Integer.parseInt(strX);
						datey = Integer.parseInt(strY);
					} else if (strXYField.equals("Rbrand")) {
						brandx = Integer.parseInt(strX);
						brandy = Integer.parseInt(strY);
					} else if (strXYField.equals("Rmodel")) {
						modelx = Integer.parseInt(strX);
						modely = Integer.parseInt(strY);
					} else if (strXYField.equals("Rreference")) {
						refx = Integer.parseInt(strX);
						refy = Integer.parseInt(strY);
					}								
				}
				
			} catch (Exception e) {}
			
			try {
				strQuery = "From CustomerVehicle where proposalno = '" + strProposal + "'"; 
				CustomerVehicle cv = custVehicleRepo.findByproposalno(strProposal);
								
					strRegno = cv.getVehicleregno();
					intAgreement = cv.getAgreementserial();
				
				strAgreement = "AN" + Integer.toString(intAgreement);
				strReference = "VBY/" + strYrinvt + "/" + strAgreement;
				
			} catch (Exception e) {}
			
			try {
//				strQuery = "From Vehicle where regno = '" + strRegno + "'"; 
				Optional<Vehicle> vehicle = vehicleRepo.findById(strRegno);
								
				if(vehicle.isPresent()) {
					Vehicle veh = vehicle.get();
					
					strDealer = veh.getDealer();
					strEngine = veh.getEngineno();
					strChassis = veh.getChassisno();
				}
				
			} catch (Exception e) {}
			
			
			try {
				strQuery = "From Dealer where dealer = '" + strDealer + "'"; 
				Optional<Dealer> dealer = dealerRepo.findById(strDealer);
								
				if(dealer.isPresent()){
					
					Dealer dl = dealer.get();
					
					strPhyaddress = dl.getPhysicaladdress();
					strPostaddress  = dl.getPostaladdress();
					strTelephone1  = dl.getTelephone1();
					strTelephone2  = dl.getTelephone2();
					strEmail  = dl.getEmail();
				}
				
			} catch (Exception e) {}	
			
			try {
//				strQuery = "From CustomerDetails where otherid = '" + strCustomer + "'"; 
				CustomerDetails cd = custRepo.findByotherid(strCustomer);
								
					strCustname = cd.getSurname() + " " + cd.getFirstname();
					strNatid = cd.getNationalid();
			}catch (Exception e) {}
			
			try {
//				strQuery = "From Proposal where proposalno = '" + strProposal + "'"; 
				Proposal prop = propRepo.findByproposalno(strProposal);
								
					strVehicle = prop.getVehicleregno();
				
				String[] strVehicles = strVehicle.split(" | ");
				
				strBrand = strVehicles[0];
				strModel = strVehicles[2];
				strCc = strVehicles[4];
				
				strSubject = "Release of the Vehicle-(" + strBrand + " " + strModel + " " + strCc + ")";
				
			}catch (Exception e) {}
			
			try {
				//Create PdfReader instance.
//				pdfFile = request.getRealPath("/docs/VBY_Release_letter.pdf");
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream("docs/VBY_Release_letter.pdf");
				
//				Resource resource = resourceLoader.getResource("classpath:docs/VBY_Release_letter.pdf");
//				File file = resource.getFile();
//				pdfFile = file.getAbsolutePath();
				
			    PdfReader pdfReader = 
					new PdfReader(inputStream);
	 
			    //Create PdfStamper instance.pdfFile
			    
//			    newpdfFile = "src/main/resources/Documents/Release" + strProposal  + ".pdf";
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
						pageContentByte.setTextMatrix(dealerx, dealery);
			 			pageContentByte.showText(strDealer);
			 			
						pageContentByte.setTextMatrix(dphyaddx, dphyaddy);
						pageContentByte.showText(strPhyaddress);
												
						pageContentByte.setTextMatrix(dpostaddx, dpostaddy);
						pageContentByte.showText(strPostaddress);
						
						pageContentByte.setTextMatrix(dtel1x, dtel1y);
						pageContentByte.showText(strTelephone1);
						
						pageContentByte.setTextMatrix(dtel2x, dtel2y);
						pageContentByte.showText(strTelephone2);
						
						pageContentByte.setTextMatrix(demailx, demaily);
						pageContentByte.showText(strEmail);
												
						pageContentByte.setTextMatrix(datex, datey);
						pageContentByte.showText(strDate);
						
						pageContentByte.setTextMatrix(custnamex, custnamey);
						pageContentByte.showText(strCustname);
						
						pageContentByte.setTextMatrix(propx, propy);
						pageContentByte.showText(strProposal);
					
						pageContentByte.setTextMatrix(vregnox, vregnoy);
						pageContentByte.showText(strRegno);
						
						pageContentByte.setTextMatrix(enginex, enginey);
						pageContentByte.showText(strEngine);
						
						pageContentByte.setTextMatrix(chassisx, chassisy);
						pageContentByte.showText(strChassis);
						
						pageContentByte.setTextMatrix(natx, naty);
						pageContentByte.showText(strNatid);
						
						pageContentByte.setTextMatrix(brandx, brandy);
						pageContentByte.showText(strSubject);
						
						pageContentByte.setTextMatrix(refx, refy);
						pageContentByte.showText(strReference);
					}
					pageContentByte.endText();
			    }
	 
			    //Close the pdfStamper.
			    pdfStamper.close();	
			    /*
			    if ((new File(newpdfFile)).exists()) {
					Process p = Runtime
					   .getRuntime()
					   .exec("rundll32 url.dll,FileProtocolHandler " + newpdfFile);
					p.waitFor();						
				} else {
					System.out.println("File is not exists");
				}	 
			    System.out.println("PDF modified successfully.");
			    strMsg = "Release letter for " + strProposal + " generated successfully";*/
			    
			    resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

	            // Set headers
			    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=VBY_Release_letter.pdf");
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
//			    }	
			    
			    //Update releaseletter table
			    ReleaseLetter rl = new ReleaseLetter();
				rl.setProposalno(strProposal);
				rl.setCustomerid(strCustomer);
				rl.setAgreementno(strAgreement);
				rl.setPrintuser(reluser);
				rl.setPrintdatetime(reldatetime);
				
				try {
					releaseRepo.save(rl);
				} catch (Exception e) {
					Throwable th = e.getCause();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
					strMsg = th.getCause().toString();
					if (strMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1") || 
							strMsg.contains("The database returned no natively generated identity value") ||
							strMsg.contains("ConstraintViolationException")) {
						
						strMsg = "Release letter : " + newpdfFile + " already exists.";					
						
					} else {
						strMsg = "Error updating release letter";
					}
					headers.setContentType(MediaType.TEXT_PLAIN);
			        headers.set(HttpHeaders.CONTENT_ENCODING, "UTF-8");
			        headers.add("X-Message", strMsg);
//					response.setData(strMsg);
				}				    
			    
			} catch (Exception e) {
			    e.printStackTrace();
			}
						
		}
//		session.setAttribute("relletterId", "");
		
        
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

	public Response<List<CustomerDetailsDto>> viewCustId() {
		
		List<CustomerDetails> vehList = custRepo.getCustIdForRelease();
		Response<List<CustomerDetailsDto>> response = new Response<>();
		List<CustomerDetailsDto> PropDtoList = new ArrayList<>();
		
		for(CustomerDetails prop : vehList) {
			CustomerDetailsDto propDto = new CustomerDetailsDto();
			propDto.setOtherid(prop.getOtherid());;
			propDto.setSurname(prop.getSurname());
			PropDtoList.add(propDto);
			response.setData(PropDtoList);
		}	
		return response;
	}

}
