package com.ozone.smart.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ozone.smart.component.loanSchedule;
import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.UserDto;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.User;
import com.ozone.smart.entity.pdfParams;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.PdfParamRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.VehicleRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;
import com.ozone3.smart.pojo.conversionTool;

import jakarta.servlet.ServletOutputStream;

@Service
public class VehicleAllocationService {
	
	@Autowired
	private CustomerVehicleRepo cvRepo;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private CustomerVehicleRepo custVehRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private PdfParamRepo pdfParamRepo;
	
	@Autowired
	private LoanEntryRepo loanEntryRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ProposalRepo proposalRepo;
	
	@Autowired
	private LoanParamRepo loanParamRepo;
	
	@Autowired
	private loanSchedule loanSch;

	public Response<String> updateCustVehicle(String proposal, String regno, String username, String customer) {
			String strVMsg="";	
			String strMsg="";	
			String newstrMsg = "";
			String strProposal, strRegno, strCapturedatetime;	
			String strQuery = "";
			int updateCount = 0;
			String strLoginuser = "";
			System.out.println("Inside add allocation service");
			int intAgreementserial = 0;
			String strAgreementno = "";
			String genAgreementno = "";
			String strCustomer = customer;
			String strLoanamt = "";
			String strIntrate = "";
			String strNoinst = "";
			String strDownpmnt = "";
			String strTrffee = "";
			String strInsfee = "";
			String strTrckfee = "";
			String strPaymode = "";
//			String strDISBRequestdatetime = "";		
			strProposal = proposal;
			strRegno = regno;
			strLoginuser =username;
			
//			TimeStampUtil gts = new TimeStampUtil();
//			strDISBRequestdatetime = gts.TimeStamp();
			
			Response<String> response = new Response<>();
								
			if (strProposal == null || strProposal.length() == 0) {			
				strVMsg = "Please select proposal";
				
			} else if (strRegno == null || strRegno.length() == 0) {				
				strVMsg = "Please select regno";
			
			} else {
				
				
				TimeStampUtil gts = new TimeStampUtil();		
				strCapturedatetime = gts.TimeStamp();
				
				strProposal = strProposal.trim();
				strRegno = strRegno.trim();
				
				String maxAgreeNo = custVehRepo.findMaxagreementno();
				int nextAgreeNo;

				// Check if there are any existing partreceipt numbers
				if (maxAgreeNo != null && !maxAgreeNo.isEmpty()) {
				    // Extract the numeric part and increment it
				    int maxWeekReceiptNumber = Integer.parseInt(maxAgreeNo);
				    nextAgreeNo = maxWeekReceiptNumber + 1;
				} else {
				    // If no existing partreceipt numbers, start from 10000010
					nextAgreeNo = 101048;
				}
				genAgreementno = String.valueOf(nextAgreeNo);
				CustomerVehicle cv = new CustomerVehicle();
				cv.setProposalno(strProposal);
				cv.setVehicleregno(strRegno);
				cv.setAgreementno(genAgreementno);
				cv.setCapturedatetime(strCapturedatetime);
				cv.setCvuser(strLoginuser);
				
				try {
					cvRepo.save(cv);
					
					try {
//						strQuery = "Update Vehicle set allocated = true where regno =  " + "'" + strRegno + "'"; 
						updateCount = vehicleRepo.updateAllocatedStatus(strRegno);	
						
						if (updateCount < 1) {
							strVMsg = "Failed to update allocated vehicle: " + strRegno;
						} else {
							strVMsg = "Vehicle: " + strRegno + " for proposal: " + strProposal + " allocated successfully";
						}
						
					} catch (Exception e) {
//						System.out.println(e.getLocalizedMessage());
						e.printStackTrace();
						strVMsg = "Error updating allocated vehicle : " + strRegno;
					}
					
				} catch (Exception e) {
					Throwable th = e.getCause();
					e.printStackTrace();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
			        strVMsg = th.getCause().toString();
					if (strVMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1") || 
							strVMsg.contains("The database returned no natively generated identity value") ||
							strVMsg.contains("ConstraintViolationException")) {
						
						strVMsg = "Vehicle allocation for : " + strProposal + " already exists.";					
						
					} else {
						strVMsg = "Error creating vehicle allocation for : " + strProposal;
					}		
				}
				List<CustomerVehicle> custvehicle=  custVehRepo.findByProposalAndDisb(strProposal);
				try {
					if (custvehicle.size() != 0) {
						for (CustomerVehicle cvv:custvehicle) {
							System.out.println("Agreement number "+cvv.getAgreementno());
							intAgreementserial = Integer.parseInt(cvv.getAgreementno());
							if (intAgreementserial != 0) {
								
								strAgreementno = "AN" + intAgreementserial;					
								
//								strQuery = "From Loan where custid = '" + strCustomer + "'  and proposalno = '" + strProposal + "' and scheduled = 0 and cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve'";
								
								List<Loan> loan = loanRepo.findByParameters(strCustomer, strProposal) ;
								
								if (loan.size() > 0) {
									for (Loan ln:loan) {
										strIntrate = ln.getInterestrate();
										strLoanamt = ln.getLoanamount();
										strNoinst = ln.getNoofinstallments();
										strDownpmnt = ln.getDownpayment();
										strTrffee = "0";
										strInsfee = ln.getInsurancefee();
										strTrckfee = ln.getTrackerfee();
										strPaymode = ln.getPaymentmode();
										
										if (strLoanamt.contains(",")) {strLoanamt = strLoanamt.replace(",", "");}
										if (strDownpmnt.contains(",")) {strDownpmnt = strDownpmnt.replace(",", "");}
										if (strInsfee.contains(",")) {strInsfee = strInsfee.replace(",", "");}
										if (strTrckfee.contains(",")) {strTrckfee = strTrckfee.replace(",", "");}
										
//										loanSchedule ls = new loanSchedule();
										newstrMsg = loanSch.schedule(strAgreementno, strProposal, strIntrate, strLoanamt, strNoinst, strDownpmnt, 
												strTrffee, strInsfee, strTrckfee, strPaymode);
										
										strMsg = newstrMsg;
										
										if (strMsg.contains("Payment schedule")) {
//											strQuery = "UPDATE Loan set scheduled = 1 where proposalno = '" + strProposal + "'"; 
											try {
												loanRepo.updateLoanByProposal(strProposal);
											}catch(Exception e) {
												e.printStackTrace();
											}
											
//											if (intLoan > 0) {									
////												strQuery = "UPDATE CustomerVehicle set disbursed = true, disbuser = '" + strLoginuser + "' , disbdatetime = '" + strDISBRequestdatetime + "' where proposalno = '" + strProposal + "'";
//												int intCV = custVehRepo.updateCustomerVehicle(strLoginuser, strCapturedatetime, strProposal);
//												if (intCV == 0) {
//													strMsg = "Loan " + strProposal + " has been scheduled but CV not flagged";
//												}
//											} else {
//												strMsg = "Loan " + strProposal + " has been scheduled but not flagged";
//											}
										}
									}	
								} else {
									strMsg = "Failed, Check Loan approvals!";
								}
								
							} else {
								strMsg = "Vehicle allocation is mandatory!";
							}	
						}
						
					} else {
						strMsg = "Loan for " + strAgreementno + " is already disbursed";
						//strMsg = "Payment schedule : " + strProposal + " saved successfully.";
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}				
			response.setData(strVMsg+ " " +strMsg+ "|" +strAgreementno);
		return response;
	}

	public ResponseEntity<Resource> generateAgree(String proposal, String regno, String chassis, String engine) {
		
		Response<String> response = new Response<>();
		  System.out.println("Inside generate Agree");
		String strProposal, strRegno, strChassis, strEngine, strDay, strMonth, strYear;	
		String strQuery = "";
		String strCustid = "";
		String strAgreementno = "";
		String strOutputfilename = "";
		String strMsg="";
		
		int dayx = 0;
		int dayy = 0;
		int monthx = 0;
		int monthy = 0;
		int yearx = 0;
		int yeary = 0;
		int dpx = 0;
		int dpy = 0;
		int dpaIwx = 0;

		int dpaIwy = 0;
		int instx = 0;
		int insty = 0;
		int tenurex = 0;
		int tenurey = 0;
		int natidx = 0;
		int natidy = 0;
		
		int namex = 0;
		int namey = 0;
		int addressx = 0;
		int addressy = 0;
		int address2x = 0;
		int address2y = 0;
		int mobilex = 0;
		int mobiley = 0;
		
		int Abrandx = 0;	
		int Amodelx = 0;
		int AColorx = 0;	
		int Aregnox = 0;	
		int Aenginex = 0;	
		int Achassisx = 0;	
		int Aassetx = 0;	
		int Adpx = 0;		
		int Agpsx = 0;		
		int Airrx = 0;		
		int Aewix = 0;		
		int Atenurex = 0;	
		int Ainsurancex = 0;	
		int Ahppricex = 0;	
		int Alppenaltyx = 0;	
		int Apimpoundx = 0;
		int Acashpricex = 0;
		
		int Abrandy = 0;	
		int Amodely = 0;
		int AColory = 0;	
		int Aregnoy = 0;	
		int Aenginey = 0;	
		int Achassisy = 0;	
		int Aassety = 0;	
		int Adpy = 0;		
		int Agpsy = 0;		
		int Airry = 0;		
		int Aewiy = 0;		
		int Atenurey = 0;	
		int Ainsurancey = 0;	
		int Ahppricey = 0;	
		int Alppenaltyy = 0;	
		int Apimpoundy = 0;
		int Acashpricey = 0;
		
		int updateCount = 0;
		
		int dblDP = 0;
		
		Resource res = null;
		
		String pdfFile = "";
		String newpdfFile = "";
		String strXYField = "";
		String strX = "";
		String strY = "";
		
		String strDownPayment = "";
		String strDownPaymentfmt = "";
		String strInstallment = "";
		String strFmtInstallment = "";
		String strTenure = "";
		String strLoanamnt = "";
		String strTrackerfee = "";
		String strInsurance = "";
		String strIrr = "";
		String strLatepayemnt = "";
		String strImpound = "";
		
		String strDPinwords = "";
		
		String strFirstname = "";
		String strSurname = "";
		String strCustname = "";
		String strAddress = "";
		String strAddress2 = "";
		String strAddressmanip = "";
		String strVillage = "";
		String strParish = "";
		String strSubcounty = "";
		String strCounty = "";
		String strDistrict = "";
		String strMobile = "";
		String strNatid = "";
			
		String strbrand = "";	
		String strmodel = "";
		String strColor = "";
		String strhpprice = "";
		
		String strVehicle = "";
		String strPaymode = "";
		String strCusttype = "";
		
		double dbltenure = 0;
		double dblewi = 0;
		double dblhpprice = 0;
		double dblInsfee = 0;
		double dblAmtfinanced = 0;
		double dblTracker = 0;
		double dblDownpayment = 0;
		double dblIntamount = 0;
		double dblLnrate = 0;
		double dblLoanamount = 0;
		
		int inthpprice = 0;
		int intInsfee = 0;
		int intLnrate = 0;
		
		strProposal =proposal;
		strRegno = regno;
		strChassis = chassis;
		strEngine = engine;
		
		strProposal = strProposal.trim();
		strRegno = strRegno.trim();
		strChassis = strChassis.trim();
		strEngine = strEngine.trim();
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = null;
//		Resource resource = null;
		byte[] encodedBytes = null;
							
		if (strProposal == null || strProposal.length() == 0) {			
			strMsg = "Please select proposal";	
		} else if (strRegno == null || strRegno.length() == 0) {			
			strMsg = "Please select vehicle";
		} else if (strChassis == null || strChassis.length() == 0) {			
			strMsg = "Please ensure chassis is available";
		} else if (strEngine == null || strEngine.length() == 0) {			
			strMsg = "Please ensure engine no is available";
		} else {
			TimeStampUtil gts = new TimeStampUtil();		
			strDay = gts.newfmtDay();
			strMonth = gts.newfmtMonth();
			strYear = gts.newfmtYear();
			
			formatDigits fd = new formatDigits();
			
			try {
//				strQuery = "From pdfParams"; 
				List<pdfParams> pparam = pdfParamRepo.findAll();
								
				for (pdfParams pp:pparam) {
					strXYField = pp.getField();
					strX = pp.getXaxis();
					strY = pp.getYaxis();
					
					if (strXYField.equals("Day")) {
						dayx = Integer.parseInt(strX);
						dayy = Integer.parseInt(strY);
					} else if (strXYField.equals("Month")) {
						monthx = Integer.parseInt(strX);
						monthy = Integer.parseInt(strY);
					} else if (strXYField.equals("Dp")) {
						dpx = Integer.parseInt(strX);
						dpy = Integer.parseInt(strY);
					} else if (strXYField.equals("DpaIw")) {
						dpaIwx = Integer.parseInt(strX);
						dpaIwy = Integer.parseInt(strY);
					} else if (strXYField.equals("Inst")) {
						instx = Integer.parseInt(strX);
						insty = Integer.parseInt(strY);
					} else if (strXYField.equals("Tenure")) {
						tenurex = Integer.parseInt(strX);
						tenurey = Integer.parseInt(strY);
					} else if (strXYField.equals("Custname")) {
						namex = Integer.parseInt(strX);
						namey = Integer.parseInt(strY);
					} else if (strXYField.equals("Address")) {
						addressx = Integer.parseInt(strX);
						addressy = Integer.parseInt(strY);
					} else if (strXYField.equals("Mobile")) {
						mobilex = Integer.parseInt(strX);
						mobiley = Integer.parseInt(strY);
					} else if (strXYField.equals("Natid")) {
						natidx = Integer.parseInt(strX);
						natidy = Integer.parseInt(strY);
					} else if (strXYField.equals("Address2")) {
						address2x = Integer.parseInt(strX);
						address2y = Integer.parseInt(strY);
					} else if (strXYField.equals("Year")) {
						yearx = Integer.parseInt(strX);
						yeary = Integer.parseInt(strY);
					} else if (strXYField.equals("Abrand")) {
						Abrandx = Integer.parseInt(strX);
						Abrandy = Integer.parseInt(strY);
					} else if (strXYField.equals("Amodel")) {
						Amodelx = Integer.parseInt(strX);
						Amodely = Integer.parseInt(strY);
					} else if (strXYField.equals("AColor")) {
						AColorx = Integer.parseInt(strX);
						AColory = Integer.parseInt(strY);
					} else if (strXYField.equals("Aregno")) {
						Aregnox = Integer.parseInt(strX);
						Aregnoy = Integer.parseInt(strY);
					} else if (strXYField.equals("Aengine")) {
						Aenginex = Integer.parseInt(strX);
						Aenginey = Integer.parseInt(strY);
					} else if (strXYField.equals("Achassis")) {
						Achassisx = Integer.parseInt(strX);
						Achassisy = Integer.parseInt(strY);
					} else if (strXYField.equals("Aasset")) {
						Aassetx = Integer.parseInt(strX);
						Aassety = Integer.parseInt(strY);
					} else if (strXYField.equals("Adp")) {
						Adpx = Integer.parseInt(strX);
						Adpy = Integer.parseInt(strY);
					} else if (strXYField.equals("Agps")) {
						Agpsx = Integer.parseInt(strX);
						Agpsy = Integer.parseInt(strY);
					} else if (strXYField.equals("Airr")) {
						Airrx = Integer.parseInt(strX);
						Airry = Integer.parseInt(strY);
					} else if (strXYField.equals("Aewi")) {
						Aewix = Integer.parseInt(strX);
						Aewiy = Integer.parseInt(strY);
					} else if (strXYField.equals("Atenure")) {
						Atenurex = Integer.parseInt(strX);
						Atenurey = Integer.parseInt(strY);
					} else if (strXYField.equals("Ainsurance")) {
						Ainsurancex = Integer.parseInt(strX);
						Ainsurancey = Integer.parseInt(strY);
					} else if (strXYField.equals("Ahpprice")) {
						Ahppricex = Integer.parseInt(strX);
						Ahppricey = Integer.parseInt(strY);
					} else if (strXYField.equals("Alppenalty")) {
						Alppenaltyx = Integer.parseInt(strX);
						Alppenaltyy = Integer.parseInt(strY);
					} else if (strXYField.equals("Apimpound")) {
						Apimpoundx = Integer.parseInt(strX);
						Apimpoundy = Integer.parseInt(strY);
					} else if (strXYField.equals("Acashprice")) {
						Acashpricex = Integer.parseInt(strX);
						Acashpricey = Integer.parseInt(strY);
					}	
				}				
			}catch (Exception e) {
				e.printStackTrace();
			}			
			
			try {
//				strQuery = "From Loan where proposalno = '" + strProposal + "'"; 
				List<Loan> loan = loanEntryRepo.findByproposalno(strProposal);
								
				for (Loan ln:loan) {
					strDownPayment = ln.getDownpayment();
					strInstallment = ln.getEwi();
					strTenure = ln.getNoofinstallments();
					strCustid = ln.getCustid();
					strLoanamnt = ln.getLoanamount();
					strTrackerfee = ln.getTrackerfee();
					strInsurance = ln.getInsurancefee();
					strPaymode = ln.getPaymentmode();
				}
								
				strFmtInstallment = fd.digit(strInstallment);
				if(strLoanamnt.contains(",")) {strLoanamnt = strLoanamnt.replace(",", "");}
				dblLoanamount = Double.parseDouble(strLoanamnt);
				if(strDownPayment.contains(",")) {strDownPayment = strDownPayment.replace(",", "");}
				dblDownpayment = Double.parseDouble(strDownPayment);
				if(strTrackerfee.contains(",")) {strTrackerfee = strTrackerfee.replace(",", "");}
				dblTracker = Double.parseDouble(strTrackerfee);
				
				if (strDownPayment.contains(",")) {
					strDownPaymentfmt = strDownPayment.replace(",", "");
				} else {
					strDownPaymentfmt = strDownPayment;
					strDownPayment = fd.digit(strDownPayment);
				}
				dblDP = Integer.parseInt(strDownPaymentfmt);
				conversionTool ct = new conversionTool();
				strDPinwords = ct.convert(dblDP);
				strDPinwords = "Ushs " + strDPinwords;				
				
				//calculate hp price
				dbltenure = Integer.parseInt(strTenure);
				dblewi =  (int)Double.parseDouble(strInstallment);
				dblhpprice = dbltenure * dblewi;
				
				BigDecimal hp= new BigDecimal(dblhpprice);
				hp = hp.setScale(0, BigDecimal.ROUND_UP);
				dblhpprice = hp.doubleValue();
				inthpprice = (int) dblhpprice;
				strhpprice = Integer.toString(inthpprice);
				
				dblInsfee = Double.parseDouble(strInsurance);
				
				BigDecimal ins = new BigDecimal(dblInsfee);
				ins = ins.setScale(0, BigDecimal.ROUND_UP);
				dblInsfee = ins.doubleValue();
				intInsfee = (int) dblInsfee;
				strInsurance = Integer.toString(intInsfee);
				
				if(!strLoanamnt.contains(",")) {strLoanamnt = fd.digit(strLoanamnt);}
				if(!strTrackerfee.contains(",")) {strTrackerfee = fd.digit(strTrackerfee);}
				if(!strInsurance.contains(",")) {strInsurance = fd.digit(strInsurance);}
				if(!strhpprice.contains(",")) {strhpprice = fd.digit(strhpprice);}
				
				dblAmtfinanced = dblLoanamount - dblDownpayment + dblInsfee + dblTracker;
				dblIntamount = (dbltenure * dblewi) - dblAmtfinanced;
				
				if (strPaymode.equals("Weekly")) {
					dblLnrate = (((dblIntamount / dbltenure) * 52) / dblAmtfinanced) * 100;
				} else if (strPaymode.equals("Monthly")) {
					dblLnrate = (((dblIntamount / dbltenure) * 12) / dblAmtfinanced) * 100;
				}
				
				BigDecimal lr= new BigDecimal(dblLnrate);
				lr = lr.setScale(2, BigDecimal.ROUND_UP);
				dblLnrate = lr.doubleValue();
				//intLnrate = (int) dblLnrate;				
				strIrr = Double.toString(dblLnrate);
				
				strIrr += "%";
				
			}catch (Exception e) {
				e.printStackTrace();
			}	
			
			try {
//				strQuery = "From CustomerDetails where otherid = '" + strCustid + "'"; 
				CustomerDetails cd = custRepo.findByotherid(strCustid);
								
				
					//strCustname = cd.getSurname() + " " + cd.getFirstname();
					strFirstname = cd.getFirstname();
					strSurname = cd.getSurname();
					strVillage = cd.getVillage();
					strParish = cd.getParish();
					strSubcounty = cd.getSubcounty();
					strCounty = cd.getCounty();
					strDistrict = cd.getDistrict();					
					strMobile = cd.getMobileno();
					strNatid = cd.getNationalid();
					strCusttype = cd.getCusttype();
				System.out.println(strCusttype);
				if (strFirstname.trim().equals(strSurname.trim())) {
					strCustname = strSurname;
				} else {
					strCustname = strSurname + " " + strFirstname;
				}
				
				
				if (!(strCusttype == null) && strCusttype.length() > 0) {
					strCusttype = strCusttype.trim();
				}
				
				//For cases where customer name is very long
				strVillage = strCustname + " c/o " + strVillage;
				
				strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
				if (strAddress.length() > 44) {
					int len1 = strVillage.trim().length();
					int len2 = strParish.trim().length();
					int len3  = strSubcounty.trim().length();
					int len4 = strCounty.trim().length();
					
					if ((len1 + len2 + len3 + len4) < 44) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim();
						strAddress2 = strDistrict.trim();
					} else if ((len1 + len2 + len3) < 44) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim();
						strAddress2 = strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1 + len2) < 44) {
						strAddress = strVillage.trim() + " " + strParish.trim();
						strAddress2 = strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1) < 44) {
						strAddress = strVillage.trim();
						strAddress2 = strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					}
					
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
//				strQuery = "From Proposal where proposalno = '" + strProposal + "'"; 
				Proposal prop = proposalRepo.findByproposalno(strProposal);
					strVehicle = prop.getVehicleregno();	
					
				//Bajaj | Pulsar | 125cc | Yellow
				String[] strVehicles = strVehicle.split(" | ");
				
				strbrand = strVehicles[0] + " " + strVehicles[2];
				strmodel = strVehicles[4];
				strColor = strVehicles[6];	
				
			}catch (Exception e) {
				e.printStackTrace();
			}	
			
			
			try {
				strQuery = "From LoanParam"; 
				List<LoanParam> loanparam = loanParamRepo.findAll();
				
				for (LoanParam lp:loanparam) {
					strLatepayemnt = lp.getLatepayment();
					strImpound = lp.getImpound();
				}
				
				if(!strLatepayemnt.contains(",")) {strLatepayemnt = fd.digit(strLatepayemnt);}
				if(!strImpound.contains(",")) {strImpound = fd.digit(strImpound);}
				
			}catch (Exception e) {
				e.printStackTrace();
			}	
				
			/* Customer Types
			 * "Boda rider","Personal","Corporate","Fleet"
			 */
			
			try {
				//Create PdfReader instance.
				if (strCusttype.equals("Boda rider")) {
					inputStream = classLoader.getResourceAsStream("docs/Hire_Purchase_Agreement.pdf");
//					resource = resourceLoader.getResource("classpath:docs/Hire_Purchase_Agreement.pdf");
//					File file = resource.getFile();
//					pdfFile = file.getAbsolutePath();
				} else if (strCusttype.equals("Corporate") || strCusttype.equals("Personal")) {
					//pdfFile = request.getRealPath("/docs/Hire_Purchase_Agreement_M.pdf"); New agreement file on 19.08.2020
					inputStream = classLoader.getResourceAsStream("docs/Hire_Purchase_Agreement_Corporate.pdf");
//					resource = resourceLoader.getResource("classpath:docs/Hire_Purchase_Agreement_Corporate.pdf");
//					File file = resource.getFile();
//					pdfFile = file.getAbsolutePath();
				} else if (strCusttype.equals("Fleet")) {
					inputStream = classLoader.getResourceAsStream("docs/Hire_Purchase_Agreement_Fleet.pdf");
//					resource = resourceLoader.getResource("classpath:docs/Hire_Purchase_Agreement_Fleet.pdf");
//					File file = resource.getFile();
//					pdfFile = file.getAbsolutePath();
				}
			    PdfReader pdfReader = 
					new PdfReader(inputStream);	
	 
			    //Create PdfStamper instance.pdfFile
//			    newpdfFile = "src/main/resources/Documents/" + strProposal  + ".pdf";
//			    newpdfFile = "src/main/resources/Agreement2/" + strProposal +".pdf";
			    PdfStamper pdfStamper = new PdfStamper(pdfReader,baos);
	 
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
					
					if ((i == 1) && !(strCusttype.equals("Corporate") || strCusttype.equals("Personal") || strCusttype.equals("Fleet"))) {						 
						pageContentByte.setTextMatrix(dayx, dayy);
			 			pageContentByte.showText(strDay);
						pageContentByte.setTextMatrix(monthx, monthy);
						pageContentByte.showText(strMonth);
						pageContentByte.setTextMatrix(yearx , yeary);
						pageContentByte.showText(strYear);
						
						pageContentByte.setTextMatrix(namex, namey);
						//pageContentByte.showText(strCustname);
						pageContentByte.showText(strAddress);
						//pageContentByte.setTextMatrix(addressx, addressy);
						//pageContentByte.showText(strAddress);
						pageContentByte.setTextMatrix(address2x, address2y);
						pageContentByte.showText(strAddress2);
						
						pageContentByte.setTextMatrix(natidx, natidy);
						pageContentByte.showText(strNatid);
						pageContentByte.setTextMatrix(mobilex, mobiley);
						pageContentByte.showText(strMobile);
					} else if ((i == 1) && (strCusttype.equals("Corporate") || strCusttype.equals("Personal") || strCusttype.equals("Fleet"))) {						 
						pageContentByte.setTextMatrix(dayx, dayy);
			 			pageContentByte.showText(strDay);
						pageContentByte.setTextMatrix(monthx, monthy);
						pageContentByte.showText(strMonth);
						pageContentByte.setTextMatrix(yearx , yeary);
						pageContentByte.showText(strYear);
						
						pageContentByte.setTextMatrix(namex, namey);
						//pageContentByte.showText(strCustname);
						pageContentByte.showText(strAddress);
						//pageContentByte.setTextMatrix(addressx, addressy);
						//pageContentByte.showText(strAddress);
						pageContentByte.setTextMatrix(address2x, address2y);
						pageContentByte.showText(strAddress2);
						
						//pageContentByte.setTextMatrix(natidx, natidy);
						//pageContentByte.showText(strNatid);
						pageContentByte.setTextMatrix(mobilex, mobiley);
						pageContentByte.showText(strMobile);						
					} else if ((i == 3) && !(strCusttype.equals("Corporate") || strCusttype.equals("Personal") || strCusttype.equals("Fleet"))) {
						pageContentByte.setTextMatrix(dpx, dpy);
						pageContentByte.showText(strDownPayment);
						
						pageContentByte.setFontAndSize(baseFont, 10);
						pageContentByte.setTextMatrix(dpaIwx, dpaIwy);
						pageContentByte.showText(strDPinwords);						
						
						pageContentByte.setFontAndSize(baseFont, 12);
						
						pageContentByte.setTextMatrix(instx, insty);
						pageContentByte.showText(strFmtInstallment);			
						
						pageContentByte.setTextMatrix(tenurex, tenurey);
						pageContentByte.showText(strTenure);
					} else if (i == 7) {
						//strRegno, strChassis, strEngine, strbrand, strmodel, strColor, strLoanamnt, strDownPayment, 
						//strTrackerfee, strIrr, strFmtInstallment, strTenure, strInsurance, strhpprice, strLatepayemnt, strImpound
						
						pageContentByte.setTextMatrix(Abrandx, Abrandy);
						pageContentByte.showText(strbrand);
						
						pageContentByte.setTextMatrix(Amodelx, Amodely);
						pageContentByte.showText(strmodel);	
						
						pageContentByte.setTextMatrix(AColorx, AColory);
						pageContentByte.showText(strColor);			
						
						pageContentByte.setTextMatrix(Aregnox, Aregnoy);
						pageContentByte.showText(strRegno);
						
						pageContentByte.setTextMatrix(Aenginex, Aenginey);
						pageContentByte.showText(strEngine);
						
						pageContentByte.setTextMatrix(Achassisx, Achassisy);
						pageContentByte.showText(strChassis);	
						
						pageContentByte.setTextMatrix(Aassetx, Aassety);
						pageContentByte.showText(strLoanamnt);			
						
						pageContentByte.setTextMatrix(Acashpricex, Acashpricey);
						pageContentByte.showText(strLoanamnt);
						
						pageContentByte.setTextMatrix(Adpx, Adpy);
						pageContentByte.showText(strDownPayment);
						
						pageContentByte.setTextMatrix(Agpsx, Agpsy);
						pageContentByte.showText(strTrackerfee);	
						
						pageContentByte.setTextMatrix(Airrx, Airry);
						pageContentByte.showText(strIrr);			
						
						pageContentByte.setTextMatrix(Aewix, Aewiy);
						pageContentByte.showText(strFmtInstallment);
						
						pageContentByte.setTextMatrix(Atenurex, Atenurey);
						pageContentByte.showText(strTenure);
						
						pageContentByte.setTextMatrix(Ainsurancex, Ainsurancey);
						pageContentByte.showText(strInsurance);	
						
						pageContentByte.setTextMatrix(Ahppricex, Ahppricey);
						pageContentByte.showText(strhpprice);			
						
						pageContentByte.setTextMatrix(Alppenaltyx, Alppenaltyy);
						pageContentByte.showText(strLatepayemnt);
						
						pageContentByte.setTextMatrix(Apimpoundx, Apimpoundy);
						pageContentByte.showText(strImpound);
					}
					pageContentByte.endText();
			    }
			    pdfStamper.close();	

			    resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

	            // Set headers
		    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=VB_Agreement_letter.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);            
//		        }
		        
			} catch (Exception e) {
			    e.printStackTrace();
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}				
		   //return new ResponseEntity<>(encodedBytes, headers, HttpStatus.OK);
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

	public Response<List<CustomerDetailsDto>> getVehAllocation() {
		List<CustomerDetails> vehList = custRepo.findEligibleCustomers();
		Response<List<CustomerDetailsDto>> response = new Response<>();
		List<CustomerDetailsDto> PropDtoList = new ArrayList<>();
		
		for(CustomerDetails prop : vehList) {
			CustomerDetailsDto propDto = new CustomerDetailsDto();
			propDto.setOtherid(prop.getOtherid());
			propDto.setSurname(prop.getSurname());
			PropDtoList.add(propDto);
			response.setData(PropDtoList);
		}	
		return response;
	}

//	public Response<String> scheduleGen(String customer, String proposal, String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
