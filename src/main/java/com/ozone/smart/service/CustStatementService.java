package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
import com.ozone.smart.entity.CustDataRecord;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Vehicle;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.VehicleRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;
import java.text.DateFormat;

@Service
public class CustStatementService {
	@Autowired
	private CustomerRepo custRepo;

	@Autowired
	private CustomerVehicleRepo custVehRepo;

	@Autowired
	private LoanEntryRepo loanRepo;

	@Autowired
	private PaymentScheduleRepo paymentScheduleRepo;

	@Autowired
	private PenaltyRepo penaltyRepo;

	@Autowired
	private ProposalRepo propRepo;

	@Autowired
	private VehicleRepo vehicleRepo;

	@Autowired
	private WeeklyInstRepo weeklyInstRepo;

	public ResponseEntity<InputStreamResource> printStatement(String customer, String agreement) {

		String strAgreement = "";
		String strRegno = "";
		String strPrintDate = "";
		String strMonth = "";
		String strQuery = "";
		String strCustid = "";
		String strAgreementno = "";
		String strOutputfilename = "";
		String strProposal = "";
		String strCustomername = "";
		String strVehicle = "";

		String strScheduleno = "";
		String strSchedule = "";
		String strPaymentdate = "";
		String strEwipaydate = "";
		String strPaymentmode = "";
		String strAmount = ""; // installment from weeklyinstallment
		String strPaymentamt = "";// installment from payment schedule
		String strBalance = "0";// balance amt
		String strpenpayment = "0";// penalty+paymentamount
		String strPenAmount = "";
		String strPrincipal = "";
		String strInterest = "";
		String strRunningbal = "";

		int AgreementSerial = 0;

		String strPdffilename = "";

		String strProposalno = "";

		String strAddress = "";
		String strAddress2 = "";
		String strVillage = "";
		String strParish = "";
		String strSubcounty = "";
		String strCounty = "";
		String strDistrict = "";
		String strMobile = "";

		String strDate = "";
		String strNewdate = "";

		String strDisbdate = "";
		String strCompanyname = "VUGABODAYO LTD";
		String strAssetCost = "";
		String strEwi = "";
		String strTenure = "";
		String strFrequency = "";
		String strPrincipalout = "";
		String strPrincipalintout = "";
		String strOverdueinst = "";
		String strPenalty = "";
		String strTotaloverdue = "";
		String strLoanstatus = "";

		String strVehicleregno = "";
		String strVehicledet = "";
		String strBrand = "";
		String strModel = "";

		String strVeharray[] = null;

		int intSchedule = 0;
		String strStartdate = "";
		String strEnddate = "";
		String strInttype = "Cumulative";

		int intNoofinst = 0;
		int intOverdue = 0;
		int intImpound = 0;

		int intPennoofinst = 0;
		int intTotalpen = 0;
		int intTotaltopay = 0;

		int intfuturenoofinst = 0;
		int intfuturedue = 0;
		double dblfutureprincipal = 0;
		double dblfutureint = 0;
		double dblassetcost = 0;

		String strInt = "";
		String strInst = "";
		String strOverdue = "";
		String strTotalpen = "";
		String strTotaltopay = "";
		String strFuturedue = "";
		String strFutureprincipal = "";
		String strFutureint = "";
		String strAssetcost = "";
		String strTotalpaid = "";

		String strDp = "";
		String strAmtdisb = "";
		String strInsfee = "";
		String strTrackerfee = "";

		String strEmifrequency = "";
		String strPaidprincipal = "";
		String strPaidint = "";

		String strChassis = "";
		String strEngine = "";

		double dblInsfee = 0;
		double dblTrackerfee = 0;
		double dblAmtdisb = 0;
		double oldOpeningbalance = 0;
		double dblStampduty = 0;

		double dblPaidprincipal = 0;
		double dblPaidint = 0;

		int intScheduleno = 0;
		int intTotalpaid = 0;
		int intAssetCost = 0;
		int intDp = 0;

		String strReceiptno = "";
		String strReceiptamount = "";
		String strMsg = "";
		boolean blnImpounded = false;
		boolean blnReposessed = false;
		boolean blnAuctioned = false;
		boolean blnPreclosed = false;
		boolean blnNormalclosure = false;
		String strweekResult = "";
		String strpenaltyamt = "";
		String strpenaltypaydate="";
		String strpenaltyresult ="";
		String strtransactiontype="";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document document = new Document();

		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null;
		File file = null;

		strCustid = customer;
		strAgreement = agreement;

		strAgreement = strAgreement.trim();
		strCustid = strCustid.trim();

		formatDigits fd = new formatDigits();

		Response<String> response = new Response<>();

		if (strCustid == null || strCustid.length() == 0) {
			strMsg = "Please select customer";
		} else if (strAgreement == null || strAgreement.length() == 0) {
			strMsg = "Please select agreement";
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
			strPrintDate = gts.newfmtTimeStamp();
			strDate = gts.standardDate();

			String[] arrOfStr = strAgreement.split("::");
			strAgreement = arrOfStr[1];

			strAgreementno = strAgreement;
			strAgreement = strAgreement.replace("AN", "");
			AgreementSerial = Integer.parseInt(strAgreement);

			try {
//				strQuery = "From CustomerVehicle where agreementserial = " + AgreementSerial + " and proposalno in (select proposalno from Loan where custid = '" + strCustid + "')";
				List<CustomerVehicle> custveh = custVehRepo.findByAgreementSerialAndCustid(AgreementSerial, strCustid);

				for (CustomerVehicle cv : custveh) {
					strProposalno = cv.getProposalno();
					strDisbdate = cv.getDisbdatetime();
					strRegno = cv.getVehicleregno();
				}

			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}

			try {
//				strQuery = "From Vehicle where regno = '" + strRegno + "'";
				Optional<Vehicle> vehicle = vehicleRepo.findById(strRegno);

				if (vehicle.isPresent()) {
					Vehicle veh = vehicle.get();
					strChassis = veh.getChassisno();
					strEngine = veh.getEngineno();
				}

			} catch (Exception e) {
				System.out.println("inside Vehicle");
			}

			strQuery = "From Proposal where proposalno = '" + strProposalno + "'";

			try {
				Proposal prop = propRepo.findByproposalno(strProposalno);

				strVehicledet = prop.getVehicleregno();

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
				strVehicle = strBrand + " " + strModel;

			} catch (Exception e) {
				System.out.println("Error retrieving proposal details for : " + strProposalno);
			}

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

				strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " "
						+ strCounty.trim() + " " + strDistrict.trim();
				if (strAddress.length() > 60) {
					int len1 = strVillage.trim().length();
					int len2 = strParish.trim().length();
					int len3 = strSubcounty.trim().length();
					int len4 = strCounty.trim().length();

					if ((len1 + len2 + len3 + len4) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim() + " "
								+ strCounty.trim();
						strAddress2 = strDistrict.trim();
					} else if ((len1 + len2 + len3) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim() + " " + strSubcounty.trim();
						strAddress2 = strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1 + len2) < 60) {
						strAddress = strVillage.trim() + " " + strParish.trim();
						strAddress2 = strSubcounty.trim() + " " + strCounty.trim() + " " + strDistrict.trim();
					} else if ((len1) < 60) {
						strAddress = strVillage.trim();
						strAddress2 = strParish.trim() + " " + strSubcounty.trim() + " " + strCounty.trim() + " "
								+ strDistrict.trim();
					}

				}

			} catch (Exception e) {
				System.out.println("Inside CustomerDetails");
			}

			strQuery = "From Loan where proposalno = '" + strProposalno + "'";
			// Pv = dblLoanamnt - dblDownpmnt + dblTrffee + dblInsfee + dblTrckfee;

			try {
				List<Loan> loan = loanRepo.findByproposalno(strProposalno);
				for (Loan ln : loan) {
					strAssetCost = ln.getLoanamount();
					strEwi = ln.getEwi();
					strTenure = ln.getNoofinstallments();
					strFrequency = ln.getPaymentmode();
					strDp = ln.getDownpayment();
					strInsfee = ln.getInsurancefee();
					strTrackerfee = ln.getTrackerfee();
					strEmifrequency = ln.getPaymentmode();

					blnImpounded = ln.getImpounded();
					blnReposessed = ln.getReposessed();
					blnAuctioned = ln.getAuctioned();
					blnPreclosed = ln.getPreclosed();
					blnNormalclosure = ln.getNormalclosure();
				}
				strDisbdate = strDisbdate.substring(0, 10);
				strEwi = fd.digit(strEwi);

				if (strAssetCost.contains(",")) {
					strAssetCost = strAssetCost.replace(",", "");
				}
				if (strDp.contains(",")) {
					strDp = strDp.replace(",", "");
				}

				intAssetCost = Integer.parseInt(strAssetCost);

				dblStampduty = intAssetCost * 0.01;// calculating stampduty
				System.out.println("Stampduty = " + dblStampduty);

				intDp = Integer.parseInt(strDp);
				dblInsfee = Double.parseDouble(strInsfee);
				dblTrackerfee = Double.parseDouble(strTrackerfee);
				System.out.println("check asset cost = " + intAssetCost);
				System.out.println("check dp amt = " + intDp);
				System.out.println("check insfee = " + dblInsfee);
				System.out.println("check tracker fee = " + dblTrackerfee);
//				dblAmtdisb = intAssetCost - intDp + dblInsfee;				
				oldOpeningbalance = intAssetCost - intDp + dblInsfee;
				System.out.println("oldOpeningbalance = " + oldOpeningbalance);

				dblAmtdisb = oldOpeningbalance + dblTrackerfee + dblStampduty;

//				dblAmtdisb = Math.round(dblAmtdisb * 100) / 100D;
				strAmtdisb = Double.toString(dblAmtdisb);

				strAssetCost = fd.digit(strAssetCost);
				strDp = fd.digit(strDp);
				strAmtdisb = fd.digit(strAmtdisb);

				if (blnAuctioned == true) {
					strLoanstatus = "Auctioned";
				} else if (blnReposessed == true) {
					strLoanstatus = "Reposessed";
				} else if (blnImpounded == true) {
					strLoanstatus = "Impounded";
				} else if (blnPreclosed == true) {
					strLoanstatus = "Pre Closed";
				} else if (blnNormalclosure == true) {
					strLoanstatus = "Closed";
				} else {
					strLoanstatus = "Normal";
				}

			} catch (Exception e) {
				System.out.println("Error retrieving proposal details for : " + strProposalno);
			}

//			strQuery = "FROM PaymentSchedule where loanid = '" + strProposalno + "' order by schedule";

			try {
				List<PaymentSchedule> paysch = paymentScheduleRepo.findByLoanId(strProposalno);
				for (PaymentSchedule ps : paysch) {
					intSchedule = ps.getSchedule();
					if (intSchedule == 1) {
						strStartdate = ps.getPaymentdate();
					}
					strEnddate = ps.getPaymentdate();
				}

				// strDisbdate = strDisbdate.substring(1, 10);
			} catch (Exception e) {
				System.out.println("Error retrieving proposal details for : " + strProposalno);
			}

			// Payment schedule -- 20/12/2019
			strNewdate = strDate.substring(6, 10) + "-" + strDate.substring(3, 5) + "-" + strDate.substring(0, 2);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date sqlDate = null;
			try {
				java.util.Date parsedDate = sdf.parse(strNewdate);
				sqlDate = new java.sql.Date(parsedDate.getTime());
			} catch (ParseException e) {
				System.out.println("Date parsing error: " + e.getMessage());
			}

//			strQuery = "From PaymentSchedule " +
//					"where loanid = '" + strProposalno + "' and to_date(paymentdate, 'dd/mm/yyyy') <= '" + strNewdate + "'" +
//					" and status != 'PAID'";

			try {
				List<PaymentSchedule> paysch = paymentScheduleRepo.findPaymentScheduleByLoanId(strProposalno, sqlDate);

				for (PaymentSchedule ps : paysch) {
					strInst = ps.getInstallment();
					System.out.println("Overdue Amount "+strInst);
					intNoofinst++;
					intOverdue += Integer.parseInt(strInst);
				}

				strOverdue = Integer.toString(intOverdue);
				strOverdue = fd.digit(strOverdue);

			} catch (Exception e) {
				System.out.println("inside paymentSchedule" + e.getLocalizedMessage());
			}

			// Get total paid
//			strQuery = "From PaymentSchedule " +
//					"where loanid = '" + strProposalno + "' and to_date(paymentdate, 'dd/mm/yyyy') <= '" + strNewdate + "'" +
//					" and status = 'PAID'";
			intNoofinst = 0;
			strInst = "";
			try {
				List<PaymentSchedule> paysch = paymentScheduleRepo.findPaymentScheduleByLoanId(strProposalno, sqlDate);
				for (PaymentSchedule ps : paysch) {
					strInst = ps.getInstallment();
					strPrincipal = ps.getPrincipal();
					strInterest = ps.getInterestamount();

					intNoofinst++;
					intTotalpaid += Integer.parseInt(strInst);
					dblPaidprincipal += Double.parseDouble(strPrincipal);
					dblPaidint += Double.parseDouble(strInterest);
				}

				dblPaidprincipal = Math.round(dblPaidprincipal * 100) / 100D;
				dblPaidint = Math.round(dblPaidint * 100) / 100D;

				strPaidprincipal = Double.toString(dblPaidprincipal);
				strPaidint = Double.toString(dblPaidint);

				strTotalpaid = Integer.toString(intTotalpaid);
				strTotalpaid = fd.digit(strTotalpaid);

			} catch (Exception e) {
				System.out.println("Inside PaymentSchedule" + e.getLocalizedMessage());
			}

			/*
			 * //Future due intfuturenoofinst, intfuturedue; strQuery =
			 * "From PaymentSchedule " + "where loanid = '" + strProposalno +
			 * "' and to_date(paymentdate, 'dd/mm/yyyy') > '" + strNewdate + "'";
			 * 
			 * try { List<PaymentSchedule> paysch =
			 * libraryBean.getPaymentSchedule(strQuery);
			 * 
			 * for (PaymentSchedule ps:paysch) { strInst = ps.getInstallment(); strPrincipal
			 * = ps.getPrincipal(); strInt = ps.getInterestamount();
			 * 
			 * intfuturenoofinst++; intfuturedue+= Integer.parseInt(strInst);
			 * dblfutureprincipal+= Double.parseDouble(strPrincipal); dblfutureint+=
			 * Double.parseDouble(strInt); }
			 * 
			 * dblfutureprincipal = Math.round(dblfutureprincipal * 100) / 100D;
			 * dblfutureint = Math.round(dblfutureint * 100) / 100D;
			 * 
			 * strFuturedue = Integer.toString(intfuturedue); strFuturedue =
			 * fd.digit(strFuturedue); strFutureprincipal =
			 * Double.toString(dblfutureprincipal); strFutureprincipal =
			 * fd.digit(strFutureprincipal); strFutureint = Double.toString(dblfutureint);
			 * strFutureint = fd.digit(strFutureint);
			 * 
			 * 
			 * } catch (Exception e) {System.out.println(e.getLocalizedMessage());}
			 */

			// Penalty

//			strQuery = "From Penalty where loanid = '" + strProposalno + "' and (status = '' or status is null)";

			try {
				List<Penalty> penalty = penaltyRepo.findByLoanIdAndStatusIsNull(strProposalno);

				for (Penalty pen : penalty) {
					strPenalty = pen.getPenalty();
					intPennoofinst++;
					intTotalpen += Integer.parseInt(strPenalty);
				}

				strTotalpen = Integer.toString(intTotalpen);
				strTotalpen = fd.digit(strTotalpen);

			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}

			intTotaltopay = intTotalpen + intOverdue;
			strTotaloverdue = Integer.toString(intTotaltopay);
			strTotaloverdue = fd.digit(strTotaloverdue);

			try {
//				strPdffilename = "src/main/resources/Documents/Statement" + strAgreementno + ".pdf";

				PdfWriter.getInstance(document, baos);
//				PdfWriter.getInstance(document, new FileOutputStream(strPdffilename));
				document.open();
				Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
				Chunk chunk = new Chunk("Name:    " + strCustomername, font);
				document.add(chunk);
				document.add(new Paragraph(""));
				Chunk chunk01 = new Chunk("Address: " + strAddress, font);
				document.add(chunk01);
				document.add(new Paragraph(""));
				Chunk chunk02 = new Chunk("         " + strAddress2, font);
				document.add(chunk02);

				document.add(new Paragraph("\n"));

				Chunk chunk03 = new Chunk("Mobile No: " + strMobile
						+ "                                                     Statement Issue Date: " + strDate,
						font);
				document.add(chunk03);
				document.add(new Paragraph(""));
				Chunk chunk04 = new Chunk("Statement Period: " + strDisbdate + " to " + strDate, font);
				document.add(chunk04);
				document.add(new Paragraph(""));
				Chunk chunk05 = new Chunk("Name of Financier: " + strCompanyname, font);
				document.add(chunk05);
				document.add(new Paragraph("\n"));

				// section II
				PdfPTable tbl = new PdfPTable(4);
				tbl.setWidthPercentage(100);
				tbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbl.setWidths(new int[] { 25, 18, 42, 15 });
				tbl.setTotalWidth(100);

				PdfPCell lblproduct = new PdfPCell(new Phrase("Product: ", font));
				lblproduct.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblproduct);
				PdfPCell lblvehicle = new PdfPCell(new Phrase(strVehicle, font));
				lblvehicle.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblvehicle);
				PdfPCell lblAsset = new PdfPCell(new Phrase("Asset Cost: ", font));
				lblAsset.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblAsset.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblAsset);
				PdfPCell assetAmnt = new PdfPCell(new Phrase(strAssetCost, font));
				assetAmnt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				assetAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(assetAmnt);

				PdfPCell lbldisbdate = new PdfPCell(new Phrase("Disbursal Date: ", font));
				lbldisbdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldisbdate);
				PdfPCell disbdate = new PdfPCell(new Phrase(strDisbdate, font));
				disbdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(disbdate);
				PdfPCell lbldp = new PdfPCell(new Phrase("Down Payment: ", font));
				lbldp.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lbldp.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lbldp);
				PdfPCell dpAmnt = new PdfPCell(new Phrase(strDp, font));
				dpAmnt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dpAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(dpAmnt);

				PdfPCell lblinststartdate = new PdfPCell(new Phrase("Installment Start Date: ", font));
				lblinststartdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblinststartdate);
				PdfPCell inststartdate = new PdfPCell(new Phrase(strStartdate, font));
				inststartdate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(inststartdate);
				PdfPCell lblamtdisb = new PdfPCell(new Phrase("Amount Disbursed: ", font));
				lblamtdisb.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblamtdisb.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblamtdisb);
				PdfPCell amtdisb = new PdfPCell(new Phrase(strAmtdisb, font));
				amtdisb.setHorizontalAlignment(Element.ALIGN_RIGHT);
				amtdisb.setBorderColor(BaseColor.WHITE);
				tbl.addCell(amtdisb);

				PdfPCell lblinstenddate = new PdfPCell(new Phrase("Installment End Date: ", font));
				lblinstenddate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblinstenddate);
				PdfPCell instenddate = new PdfPCell(new Phrase(strEnddate, font));
				instenddate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(instenddate);
				PdfPCell lblewi = new PdfPCell(new Phrase("EMI Amount: ", font));
				lblewi.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblewi.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblewi);
				PdfPCell ewiAmnt = new PdfPCell(new Phrase(strEwi, font));
				ewiAmnt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				ewiAmnt.setBorderColor(BaseColor.WHITE);
				tbl.addCell(ewiAmnt);

				PdfPCell lblintrate = new PdfPCell(new Phrase("Interest Rate Type: ", font));
				lblintrate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblintrate);
				PdfPCell intrate = new PdfPCell(new Phrase(strInttype, font));
				intrate.setBorderColor(BaseColor.WHITE);
				tbl.addCell(intrate);
				PdfPCell lblTenure = new PdfPCell(new Phrase("Total Tenure: ", font));
				lblTenure.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblTenure.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblTenure);
				PdfPCell Tenure = new PdfPCell(new Phrase(strTenure, font));
				Tenure.setHorizontalAlignment(Element.ALIGN_RIGHT);
				Tenure.setBorderColor(BaseColor.WHITE);
				tbl.addCell(Tenure);

				PdfPCell lblblk1 = new PdfPCell(new Phrase("Chassis No", font));
				lblblk1.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk1);
				PdfPCell blk1 = new PdfPCell(new Phrase(strChassis, font));
				blk1.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk1);
				PdfPCell lblEmifreq = new PdfPCell(new Phrase("Frequency of EMI: ", font));
				lblEmifreq.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblEmifreq.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblEmifreq);
				PdfPCell emifreq = new PdfPCell(new Phrase(strEmifrequency, font));
				emifreq.setHorizontalAlignment(Element.ALIGN_RIGHT);
				emifreq.setBorderColor(BaseColor.WHITE);
				tbl.addCell(emifreq);

				PdfPCell lblblk2 = new PdfPCell(new Phrase("Engine No", font));
				lblblk2.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk2);
				PdfPCell blk2 = new PdfPCell(new Phrase(strEngine, font));
				blk2.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk2);
				PdfPCell lblprinpaid = new PdfPCell(new Phrase("Principal paid during statement period: ", font));
				lblprinpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblprinpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblprinpaid);
				PdfPCell prinpaid = new PdfPCell(new Phrase(strPaidprincipal, font));
				prinpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				prinpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(prinpaid);

				PdfPCell lblblk3 = new PdfPCell(new Phrase("Regn. No", font));
				lblblk3.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblblk3);
				PdfPCell blk3 = new PdfPCell(new Phrase(strRegno, font));
				blk3.setBorderColor(BaseColor.WHITE);
				tbl.addCell(blk3);
				PdfPCell lblintpaid = new PdfPCell(new Phrase("Interest paid during statement period: ", font));
				lblintpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lblintpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(lblintpaid);
				PdfPCell intpaid = new PdfPCell(new Phrase(strPaidint, font));
				intpaid.setHorizontalAlignment(Element.ALIGN_RIGHT);
				intpaid.setBorderColor(BaseColor.WHITE);
				tbl.addCell(intpaid);
				document.add(tbl);

				document.add(new Paragraph("\n"));

				// section III
				PdfPTable ptbl = new PdfPTable(4);
				ptbl.setWidthPercentage(100);
				ptbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				ptbl.setWidths(new int[] { 40, 10, 40, 10 });
				ptbl.setTotalWidth(100);

				PdfPCell lbltotalosdues = new PdfPCell(new Phrase("Total O/S dues as on : " + strDate, font));
				lbltotalosdues.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lbltotalosdues);
				PdfPCell totalosdues = new PdfPCell(new Phrase("", font));
				totalosdues.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(totalosdues);
				PdfPCell lblblank = new PdfPCell(new Phrase("", font));
				lblblank.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank);
				PdfPCell lblblank1 = new PdfPCell(new Phrase("", font));
				lblblank1.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank1);

				PdfPCell lbltotalinstoverdue = new PdfPCell(new Phrase("Total installments overdue: ", font));
				lbltotalinstoverdue.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lbltotalinstoverdue);
				PdfPCell totalinstoverdue = new PdfPCell(new Phrase(strOverdue, font));
				totalinstoverdue.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(totalinstoverdue);
				PdfPCell lbllnstatus = new PdfPCell(new Phrase("Loan status: ", font));
				lbllnstatus.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lbllnstatus.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lbllnstatus);
				PdfPCell lnstatus = new PdfPCell(new Phrase(strLoanstatus, font));
				lnstatus.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lnstatus.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lnstatus);

				PdfPCell lbltotaloschgs = new PdfPCell(new Phrase("Total O/S charges: ", font));
				lbltotaloschgs.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lbltotaloschgs);
				PdfPCell totaloschgs = new PdfPCell(new Phrase(strTotalpen, font));
				totaloschgs.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(totaloschgs);
				PdfPCell lblblank2 = new PdfPCell(new Phrase("", font));
				lblblank2.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank2);
				PdfPCell lblblank3 = new PdfPCell(new Phrase("", font));
				lblblank3.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank3);

				PdfPCell lbltotaloverdue = new PdfPCell(new Phrase("Total payment overdue: ", font));
				lbltotaloverdue.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lbltotaloverdue);
				PdfPCell totaloverdue = new PdfPCell(new Phrase(strTotaloverdue, font));
				totaloverdue.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(totaloverdue);
				PdfPCell lblblank4 = new PdfPCell(new Phrase("", font));
				lblblank4.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank4);
				PdfPCell lblblank5 = new PdfPCell(new Phrase("", font));
				lblblank5.setBorderColor(BaseColor.WHITE);
				ptbl.addCell(lblblank5);
				document.add(ptbl);

				document.add(new Paragraph("\n"));

				// section IV
				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(100);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidths(new int[] { 15, 25, 20, 20, 20 });
				table.setTotalWidth(100);
				addTableHeader(table);

				// add table rows

				strAgreementno += '%';

				try {

					List<PaymentSchedule> payschedule = null;
					List<String> weeklyAmounts = null;
					List<String> penaltyamounts = null;
					System.out.println("CHECK 1 #");
					try {
						System.out.println("strProposalno == " + strProposalno);
						System.out.println("strAgreementno == " + strAgreementno);
						System.out.println("sqlDate == " + sqlDate);
						payschedule = paymentScheduleRepo.findByScheduleNoAndLoanId(strProposalno, strAgreementno,
								sqlDate);
						weeklyAmounts = weeklyInstRepo.findAmountsByProposalNo(strProposalno);
						System.out.println("weeklyAmounts = "+weeklyAmounts);
						penaltyamounts = penaltyRepo.findPenaltybyProposalno(strProposalno);
						System.out.println("penaltyamounts = "+penaltyamounts);
						System.out.println("CHECK 2 #");

					} catch (Exception e) {
						e.printStackTrace();
					}

					if (payschedule != null && !payschedule.isEmpty()) {
						int index = 0;
//					if (payschedule.size() > 0 ) {
						boolean isOpeningBalanceAdded = false;
						String strAmtdisb1 = strAmtdisb;
						int decimalIndex = strAmtdisb1.indexOf('.');
						System.out.println("CHECK 3 #");

						// If there's a decimal point, get the substring before it
						if (decimalIndex != -1) {
							strAmtdisb1 = strAmtdisb1.substring(0, decimalIndex);
						}
//						 double strAmtdisb2=0;
						List<CustDataRecord> combinedRecords = new ArrayList<>();
						
						// Process weekly installments
						for (String weeklyAmount : weeklyAmounts) {
						    String[] weeklyArray = weeklyAmount.split(",");
						    String weeklyDate = weeklyArray[1].substring(0, 2) + "/" + weeklyArray[1].substring(2, 4) + "/" + weeklyArray[1].substring(4, 8);
						    String amount = weeklyArray[0];
						    String transactionType = weeklyArray[2];
						    
						    combinedRecords.add(new CustDataRecord(weeklyDate, amount, transactionType, false)); // False since it's not a penalty
						}
						System.out.println("CHECK 4 #");
						// Process penalties
						for (String penaltyAmount : penaltyamounts) {
						    String[] penaltyArray = penaltyAmount.split(",");
						    String penaltyDate = penaltyArray[1];
						    String penaltyAmt = penaltyArray[0];
						    
						    combinedRecords.add(new CustDataRecord(penaltyDate, penaltyAmt, "PEN", true)); // True because it's a penalty
						}
						System.out.println("CHECK 5 #");
						Collections.sort(combinedRecords, new Comparator<CustDataRecord>() {
						    public int compare(CustDataRecord r1, CustDataRecord r2) {
						        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						        try {
						            return df.parse(r1.getDate()).compareTo(df.parse(r2.getDate()));
						        } catch (ParseException e) {
						            e.printStackTrace();
						        }
						        return 0;
						    }
						});
						System.out.println("CHECK 6 # "+ strAmtdisb1);
						double balanceAmount = 0;
						try {
							balanceAmount = Double.parseDouble(strAmtdisb1.replaceAll(",", ""));
							System.out.println("CHECK 7 # "+ balanceAmount);
						}catch(Exception e) {
							e.printStackTrace();
						}
						
						if (!isOpeningBalanceAdded) {
							System.out.println("CHECK 8 #");

//							PdfPCell paymentdate = new PdfPCell(new Phrase(strPaymentdate, font));
//							table.addCell(paymentdate);// Date for opening balance
							
							PdfPCell emptyCell = new PdfPCell();
							table.addCell(emptyCell);

							PdfPCell schedule = new PdfPCell(new Phrase("Opening Balance", font));
							table.addCell(schedule);
							isOpeningBalanceAdded = true;

							PdfPCell dr = new PdfPCell();
							dr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(dr);

							PdfPCell cr = new PdfPCell(new Phrase(" ", font));
							cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cr);

							PdfPCell cellRunningbal = new PdfPCell(new Phrase(strAmtdisb1 + " DR", font));
							cellRunningbal.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cellRunningbal);
						}
						for (CustDataRecord record : combinedRecords) {
						    String date = record.getDate();
						    String amount = record.getAmount();
						    String transactionType = record.getTransactionType();
						    
						    // Convert amount to double for calculations
						    double recordAmount = Double.parseDouble(amount);
						    System.out.println("CHECK 9 # "+recordAmount);
						    if (record.isPenalty()) {
						    	
						        // Update balance by adding penalty
						        balanceAmount += recordAmount;
						        System.out.println("CHECK 10 # "+balanceAmount);
						        String strbalance = Double.toString(balanceAmount);
						        System.out.println("Inside a penalty balance "+ strbalance);
						        // Handle penalty-related display logic
						        PdfPCell penaltyDateCell = new PdfPCell(new Phrase(date, font));
						        table.addCell(penaltyDateCell);
						        PdfPCell penaltyCell = new PdfPCell(new Phrase("Penalty", font));
						        table.addCell(penaltyCell);
						        PdfPCell penaltyAmountCell = new PdfPCell(new Phrase(amount, font));
						        penaltyAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						        table.addCell(penaltyAmountCell);
						        PdfPCell emptyCell2 = new PdfPCell();
								table.addCell(emptyCell2);

								PdfPCell dr2 = new PdfPCell(new Phrase(strbalance, font));
								System.out.println("strpenpayment = " + balanceAmount);
								dr2.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(dr2);
						    } else {

						        // Update balance by subtracting weekly installment
						        balanceAmount -= recordAmount;
						        String strbalance = Double.toString(balanceAmount);
						        System.out.println("Inside a penalty balance "+ strbalance);
						        
						    	// Handle weekly installment-related display logic
						        PdfPCell weekDateCell = new PdfPCell(new Phrase(date, font));
						        table.addCell(weekDateCell);
						        
						        // Check transaction type for weekly installments
						        if (transactionType.equals("EWI")) {
						            PdfPCell weekPaymentCell = new PdfPCell(new Phrase("EWI Payment Received", font));
						            table.addCell(weekPaymentCell);
						        } else {
						            PdfPCell weekPaymentCell = new PdfPCell(new Phrase("Penalty Payment Received", font));
						            table.addCell(weekPaymentCell);
						        }
						        PdfPCell emptyCell2 = new PdfPCell();
								table.addCell(emptyCell2);
								
						        PdfPCell weekAmountCell = new PdfPCell(new Phrase(amount + " CR", font));
						        weekAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						        table.addCell(weekAmountCell);
						        
						        PdfPCell dr2 = new PdfPCell(new Phrase(strbalance, font));
								System.out.println("strpenpayment = " + balanceAmount);
								dr2.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(dr2);
						    }
						}


						// document.add(table);
						strMsg = "Payment schedule printed successfully";
					} else {
						strMsg = "Loan has not been disbursed.";
					}
					document.add(table);
				} catch (Exception e) {
				}

				document.add(new Paragraph(""));

				Chunk chunk06 = new Chunk("List of payments received: ", font);
				document.add(chunk06);

				document.add(new Paragraph("\n"));

				// section V
				PdfPTable wtable = new PdfPTable(5);
				wtable.setWidthPercentage(100);
				wtable.setHorizontalAlignment(Element.ALIGN_LEFT);
				wtable.setWidths(new int[] { 20, 20, 20, 20, 20 });
				wtable.setTotalWidth(100);
				addwTableHeader(wtable);

				try {
//					strQuery = "From WeeklyInstallment where proposalno = '" + strProposalno + "' order by receiptid"; 
//					List<WeeklyInstallment> weeklyinst = libraryBean.getWeeklyInstallment(strQuery);
					List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findall(strProposalno);
					if (weeklyinst.size() > 0) {
						for (WeeklyInstallment winst : weeklyinst) {
							strReceiptno = winst.getWeekreceipt();
							strReceiptamount = winst.getAmount();
							strEwipaydate = winst.getPaymentdate();
							strPaymentmode = winst.getPaymentmode();

							strEwipaydate = strEwipaydate.substring(0, 2) + "/" + strEwipaydate.substring(2, 4) + "/"
									+ strEwipaydate.substring(4, 8);

							if (!strReceiptamount.contains(",")) {
								strReceiptamount = fd.digit(strReceiptamount);
							}

							PdfPCell receiptno = new PdfPCell(new Phrase(strReceiptno, font));
							wtable.addCell(receiptno);

							PdfPCell city = new PdfPCell(new Phrase("Kampala ", font));
							wtable.addCell(city);

							PdfPCell paymode = new PdfPCell(new Phrase(strPaymentmode, font));
							wtable.addCell(paymode);

							PdfPCell paydate = new PdfPCell(new Phrase(strEwipaydate, font));
							wtable.addCell(paydate);

							PdfPCell receiptamt = new PdfPCell(new Phrase(strReceiptamount, font));
							receiptamt.setHorizontalAlignment(Element.ALIGN_RIGHT);
							wtable.addCell(receiptamt);
						}
						document.add(wtable);
						strMsg = "Payment schedule printed successfully";
					} else {
						strMsg = "Loan has not been disbursed.";
					}
				} catch (Exception e) {
				}

				document.close();

				// document.open();
//				file = new File(strPdffilename);

//			    if (file.exists()) {
//			    	FileInputStream inputStream = new FileInputStream(file);
//		            resource1 = new InputStreamResource(inputStream);

				// Set headers
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=Statement" + strAgreementno + ".pdf");
				headers.setContentType(MediaType.APPLICATION_PDF);
				resource1 = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));
//			    }			    

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		return ResponseEntity.ok().headers(headers).contentLength(baos.size()).body(resource1);
	}

	private void addTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
//		Stream.of("PaymentDate", "Description","           Credit","          Debit","         Amount")
		Stream.of("Date", "Particulars", "           Debit", "          Credit", "         Balance")
				.forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setPhrase(new Phrase(columnTitle, hdrfont));
					table.addCell(header);
				});
	}

	private void addwTableHeader(PdfPTable table) {
		Font whdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);

		Stream.of("S.No", "City", "Mode of Payment", "Payment Date", "          Amount").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase(columnTitle, whdrfont));
			table.addCell(header);
		});
	}

}
