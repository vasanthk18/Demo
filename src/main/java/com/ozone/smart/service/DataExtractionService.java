package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ozone.smart.component.TotalDue;
import com.ozone.smart.component.buckets;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Collection;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.DownPayment;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.entity.Impoundedstock;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.LoanStatus;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.Ptp;
import com.ozone.smart.entity.Reposession;
import com.ozone.smart.entity.VehicleMaster;
import com.ozone.smart.entity.Waiver;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.entity.vwCustomer;
import com.ozone.smart.entity.vwGuarantors;
import com.ozone.smart.entity.vwProposal;
import com.ozone.smart.entity.vwRejcustomers;
import com.ozone.smart.repository.CollectionRepo;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.DownPaymentRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.ImpoundedStockRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.LoanStatusRepo;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.ProposalRepo;
import com.ozone.smart.repository.PtpRepo;
import com.ozone.smart.repository.ReposessionRepo;
import com.ozone.smart.repository.VWGuaranRepo;
import com.ozone.smart.repository.VehicleMasterRepo;
import com.ozone.smart.repository.VmProposalRepo;
import com.ozone.smart.repository.WaiverRepo;
import com.ozone.smart.repository.WeeklyInstRepo;
import com.ozone.smart.repository.vwCustRepo;
import com.ozone.smart.repository.vwRejCustRepo;
import com.ozone.smart.util.fieldValidation;
import com.ozone.smart.util.formatDigits;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service
public class DataExtractionService {
	
	@Autowired
	private VmProposalRepo vmProposalRepo;
	
	@Autowired
	private DownPaymentRepo downPaymentRepo;
	
	@Autowired
	private LoanStatusRepo loanStatusRepo;
	
	@Autowired
	private ReposessionRepo reposessRepo;
	
	
	@Autowired
	private PtpRepo ptpRepo;
	
	@Autowired
	private PenaltyRepo penaltyRepo;
	
	@Autowired
	private WeeklyInstRepo weeklyInstRepo;
	
	@Autowired
	private VWGuaranRepo vwGuaranRepo;
	
	@Autowired
	private vwCustRepo vwcustRepo;
	
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private LoanEntryRepo loanRepo;
	
	@Autowired
	private vwRejCustRepo vwRejRepo;
	
	@Autowired
	private GuarantorRepo guaranRepo;
	
	@Autowired
	private VehicleMasterRepo vmRepo;
	
	@Autowired
	private CollectionRepo collRepo;
	
	@Autowired
	private WaiverRepo waiverRepo;
	
	@Autowired
	private ImpoundedStockRepo impRepo;
	
	@Value("${s3.bucket.name}")
    private String bucketName;

	public ResponseEntity<String> generateDataDump(String datetime) {
		
		String strDate = "";
		String strQuery = "";
		String StrQueryp = "";
		String strNewxlfile = "";
		String strFilename = "";
		
		String strCustid = "";
		String strProposalno = "";
		String strAgreement = ""; 
		String strVehicle = ""; 
		String strVehiclebrand = ""; 
		String strDealer = ""; 
		String strReleasedate = ""; 
		String strNewReleasedate = "";
		String strPaymentday = ""; 
		String strSimcardno = ""; 
		String strTrackingno = "";
		String strTrackerfee = ""; 
		String strLoanamnt = ""; 
		String strDownpayment = ""; 
		String strRate = ""; 
		String strEwi = ""; 
		String strNoofinst = ""; 
		String strPaymode = "";
		String strCapturedate = "";
		String strMonthyear = "";
		String strCustomername = ""; 
		String strProfile = ""; 
		String strOtherid = ""; 
		String strStage = ""; 
		String strChairman = ""; 
		String strChairmanmobile = "";
		String strAddress = ""; 
		String strMobileno = ""; 
		String strFirstguaran = ""; 
		String strSecondguaran = "";
		
		String strStatus = "";
		String strWeeks = "";
		String strTotalduewks = "";
		String strAmount = "";
		String strTotaldue = "";
		String strTotalrecv = "";
		String strOverdue = "";
		String strInsamtyr2 = "";
		String strVatreceived = "";
		String strCostofpurchase = "";
		String strDiscount = "";
		String strDealerpayment = "";
		String strInspre1 = "";
		String strInspre2 = "";
		String strTotalpremium = "";
		
		String strLnamount = "";
		String strDownp = "";
		
		String strCosttocust = "";
		String strTotintincome = "";
		String strDefvatonint = "";
		String strVatoutondp = "";
		String strDefintincome = "";
		String strDefvatonrev = "";
		String strSales = "";
		String strImpoundDate ="";
		String strRepoDate="";
		
		String strLiveOrClosed = "";
		
		String strPenalty = "";
		int intPenalty = 0;
		int intTotalPenalty = 0;
		
		int intCounter = 0;
		
		double dblWeeks = 0;
		double dbldiff = 0;
		double dblVatreceived = 0;
		double dblCostofpurchase = 0;
		double dblLoanamount = 0;
		double dblInspre1 = 0;
		double dblInspre2 = 0;
		double dblTotalpremium = 0;
		double dblTracker = 0;
		
		int intTotalduewks = 0;
		int intAmount = 0;
		int intAmountrecv = 0;
		int intEwi = 0;
		int intTotaldue = 0;
		int intTotalrecv = 0;
		int intOverdue = 0;
		
		int intNoofinst = 0;
		int intLoanamnt = 0;
		double dblInsPrem2_75 = 0;
		double dblInsTraLevy = 0.0;
		double dblStickerFee = 0.0;
		double dblInsVatPurpose = 0.0;
		double dblVat18 = 0.0;
		double dblStampDuty = 0.0;
		double dblTotalPayToInsCo = 0.0;
		
		int intInsamtyr2 = 0;
		int intDiscount = 0;
		int intDealerpayment = 0;
		int intDp = 0;
		
		int intCosttocust = 0;
		double dblTotintincome = 0;
		double dblDefvatonint = 0;
		double dblVatoutondp = 0;
		double dblDefintincome = 0;
		double dblDefvatonrev = 0;
		double dblSales = 0;
		
		int intwaiver = 0;
		String strWaiverReason = "";
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		java.util.Date dtMonthyear = null;
		Date dtReleasedate = null;
		java.util.Date dtNewreleasedate = null;
		final WritableCellFormat DATE_CELL_FRMT;
		
		int intTrackerfee = 0;
		int intDownpayment = 0;
		double dblRate = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Response<String> response = new Response<>(); 
		
		strDate = datetime;
							
		if (strDate == null || strDate.length() == 0) {
		} else {
			
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "ddump.xls";
			
			LocalDate today = LocalDate.now();
							
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("MASTER", 0);
            
            NumberFormat dp3 = new NumberFormat("###,###");				
			NumberFormat dp4 = new NumberFormat("##.##%");

			WritableCellFormat dp3cell = new WritableCellFormat(dp3);				
			WritableCellFormat dp4cell = new WritableCellFormat(dp4);
			
			Number number = new Number(0,0,0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Month/Year");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Customer Name.");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Profile ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Customer ID ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Proposal ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Agreement No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "Veh. Regn No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(8, 0, "Insurance No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "Stage Name ");
	            excelSheet.addCell(label);
	            
	            label = new Label(10, 0, "Stage chairman Name ");
	            excelSheet.addCell(label);
	            
	            label = new Label(11, 0, "Stage Chairman Mobileno ");
	            excelSheet.addCell(label);
	            
	            label = new Label(12, 0, "Current Address ");
	            excelSheet.addCell(label);
	            
	            label = new Label(13, 0, "Mobile No. ");
	            excelSheet.addCell(label);
	            
	            label = new Label(14, 0, "Guarantor1 ");
	            excelSheet.addCell(label);
	            
	            label = new Label(15, 0, "Guarantor2 ");
	            excelSheet.addCell(label);
	            
	            label = new Label(16, 0, "Vehicle Brand ");
	            excelSheet.addCell(label);
	            
	            label = new Label(17, 0, "Sourced By ");
	            excelSheet.addCell(label);
	            
	            label = new Label(18, 0, "Source Type ");
	            excelSheet.addCell(label);
	            
	            label = new Label(19, 0, "Dealer Name ");
	            excelSheet.addCell(label);
	            
	            label = new Label(20, 0, "Status ");
	            excelSheet.addCell(label);
	            
	            label = new Label(21, 0, "Date of Release ");
	            excelSheet.addCell(label);
	            
	            label = new Label(22, 0, "Payment Day ");
	            excelSheet.addCell(label);
	            
	            label = new Label(23, 0, "Sim Card No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(24, 0, "Tracker No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(25, 0, "Tracker Cost Customer ");
	            excelSheet.addCell(label);
	            
	            label = new Label(26, 0, "Asset cost ");
	            excelSheet.addCell(label);
	            
	            label = new Label(27, 0, "Down Payment ");
	            excelSheet.addCell(label);

	            label = new Label(28, 0, "IR ");
	            excelSheet.addCell(label);
	            
	            label = new Label(29, 0, "EWI ");
	            excelSheet.addCell(label);
	            
	            label = new Label(30, 0, "Tenure ");
	            excelSheet.addCell(label);
	            
	            label = new Label(31, 0, "Repayment Type ");
	            excelSheet.addCell(label);
	            
	            label = new Label(32, 0, "Weeks ");
	            excelSheet.addCell(label);
	            
	            label = new Label(33, 0, "Total Due Wks ");
	            excelSheet.addCell(label);
	            
	            label = new Label(34, 0, "Total Due ");
	            excelSheet.addCell(label);
	            
	            label = new Label(35, 0, "Total Received ");
	            excelSheet.addCell(label);
	            
	            label = new Label(36, 0, "Overdue ");
	            excelSheet.addCell(label);
	            
	            label = new Label(37, 0, "Insured Amount ");
	            excelSheet.addCell(label);
	            
	            label = new Label(38, 0, "Insured Amount- 2nd year ");
	            excelSheet.addCell(label);
	            
	            label = new Label(39, 0, "VAT Received ");
	            excelSheet.addCell(label);
	            
	            label = new Label(40, 0, "Cost of Purchase ");
	            excelSheet.addCell(label);
	            
	            label = new Label(41, 0, "Discount ");
	            excelSheet.addCell(label);
	            
	            label = new Label(42, 0, "Dealer Payment ");
	            excelSheet.addCell(label);
	            
	            label = new Label(43, 0, "Live / CLosed");
	            excelSheet.addCell(label);
	            
	            label = new Label(44, 0, "Dealer Invoice number ");
	            excelSheet.addCell(label);
	            
	            label = new Label(45, 0, "Dealer DO No. ");
	            excelSheet.addCell(label);
	            
	            label = new Label(46, 0, "Dealer Payment made ");
	            excelSheet.addCell(label);
	            
	            label = new Label(47, 0, "Insurance Premium ");
	            excelSheet.addCell(label);
	            
	            label = new Label(48, 0, "Ins Prem - 2nd Yr ");
	            excelSheet.addCell(label);
	            
	            label = new Label(49, 0, "Total Premium ");
	            excelSheet.addCell(label);
	            
	            label = new Label(50, 0, "Ins Prem 2.75% ");
	            excelSheet.addCell(label);
	            
	            label = new Label(51, 0, "Ins-Training Levy ");
	            excelSheet.addCell(label);
	            
	            label = new Label(52, 0, "Ins-Sticker Fee ");
	            excelSheet.addCell(label);
	            
	            label = new Label(53, 0, "Ins For VAT Purpose ");
	            excelSheet.addCell(label);
	            
	            label = new Label(54, 0, "VAT@18 ");
	            excelSheet.addCell(label);
	            
	            label = new Label(55, 0, "Stamp Duty ");
	            excelSheet.addCell(label);
	            
	            label = new Label(56, 0, "Total Payable to Insco ");
	            excelSheet.addCell(label);
	            
	            label = new Label(57, 0, "Total Cost to Customer");
	            excelSheet.addCell(label);
	            
	            label = new Label(58, 0, "Total Interest Income ");
	            excelSheet.addCell(label);
	            
	            label = new Label(59, 0, "Deferred VAT on Interest ");
	            excelSheet.addCell(label);
	            
	            label = new Label(60, 0, "VAT Output on Down Payment ");
	            excelSheet.addCell(label);
	            
	            label = new Label(61, 0, "Deferred Interest Income ");
	            excelSheet.addCell(label);
	            
	            label = new Label(62, 0, "Deferred VAT on Revenue ");
	            excelSheet.addCell(label);
	            
	            label = new Label(63, 0, "Sales ");
	            excelSheet.addCell(label);
	            
	            label = new Label(64, 0, "Waiver ");
	            excelSheet.addCell(label);
	            
	            label = new Label(65, 0, "Reason for Waiver ");
	            excelSheet.addCell(label);
	            
	            label = new Label(66, 0, "Impounded Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(67, 0, "Impound release Date ");
	            excelSheet.addCell(label);
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
				try {
					strQuery = "From vwProposal where dateofrelease is not null";
					
					List<vwProposal> vwproposal = vmProposalRepo.findByDateRelease() ;
									
					for (vwProposal vwprop:vwproposal) {
						
						strCustid = vwprop.getCustomerid();
						strProposalno = vwprop.getProposalno();
						strAgreement = vwprop.getAgreementno();
						strVehicle = vwprop.getVehicleregno();
						strVehiclebrand = vwprop.getBrand();
						strDealer = vwprop.getDealer();
						strReleasedate = vwprop.getDateofrelease();
						strPaymentday = vwprop.getPaymentday();
						strMonthyear = vwprop.getMonthyear();
						strSimcardno = vwprop.getSimcardno();
						strTrackingno = vwprop.getTrackingno();
						strTrackerfee = vwprop.getTrackerfee();
						strLoanamnt = vwprop.getLoanamount();
						strDownpayment = vwprop.getDownpayment();
						strRate = vwprop.getInterestrate();
						strEwi = vwprop.getEwi();
						strNoofinst = vwprop.getNoofinstallments();
						strPaymode = vwprop.getPaymentmode();
						strDiscount = vwprop.getDiscount();						
						if (strCustid == null) {strCustid = "";}
						if (strProposalno == null) {strProposalno = "";}
						if (strAgreement == null) {strAgreement = "";}
						if (strVehicle == null) {strVehicle = "";}
						if (strVehiclebrand == null) {strVehiclebrand = "";}
						if (strDealer == null) {strDealer = "";}
						if (strPaymentday == null) {strPaymentday = "";}
						if (strMonthyear == null) {strMonthyear = "";}
						if (strSimcardno == null) {strSimcardno = "";}
						if (strTrackingno == null) {strTrackingno = "";}
						if (strTrackerfee == null) {strTrackerfee = "";}
						if (strLoanamnt == null) {strLoanamnt = "";}
						if (strDownpayment == null) {strDownpayment = "";}
						if (strRate == null) {strRate = "";}
						if (strEwi == null) {strEwi = "";}
						if (strNoofinst == null) {strNoofinst = "";}
						if (strPaymode == null) {strPaymode = "";}
						if ((strDiscount == null) || (strDiscount.equals(""))) {
							strDiscount = "0";
						}
						
						List<VehicleMaster> vm = vmRepo.findByBrandModelCcAndColor(strVehiclebrand);
						
						for(VehicleMaster Vm : vm) {
							if(Vm.getVat() != null) {
								dblVatreceived= Double.parseDouble(Vm.getVat().replace(",", ""));
							}
						}
						
						strTotalrecv = "";						
						strOverdue = "";
						strTotalduewks = "";
						strTotaldue = "";
						strWeeks = "";
						strPenalty = "";
						
						intPenalty = 0;
						intTotalPenalty = 0;
																				
						if (strReleasedate == null || strReleasedate.length() == 0) {
							strStatus = "";
							strWeeks = "";								
							strTotalduewks = "";								
							strTotaldue = "";								
							strInsamtyr2 = "";
							strVatreceived = "";
							strCostofpurchase = "";
							strDealerpayment = "";
							strInspre1 = "";
							strInspre2 = "";						
							
							strCosttocust = "";
							strTotintincome = "";
							strDefvatonint = "";
							strVatoutondp = "";
							strDefintincome = "";
							strDefvatonrev = "";
							strSales = "";
							
							strTotalrecv = "";							
							strOverdue = "";	
							strTotalpremium = "";
							
							//dtReleasedate = java.sql.Date.valueOf("1900-01-01");
							dtNewreleasedate = formatter.parse("01/01/1900");
							intTrackerfee = 0;
							intLoanamnt = 0;
							intDp = 0;
							dblRate = 0.0;
							intEwi = 0;
							intNoofinst = 0;
							dblWeeks = 0.0;
							intTotalduewks = 0;
							intTotaldue = 0;
							intTotalrecv = 0;
							intOverdue = 0;
							intLoanamnt = 0;
							intInsamtyr2 = 0;
							dblVatreceived = 0.0;
							dblCostofpurchase = 0.0;
							intDiscount = 0;
							intDealerpayment = 0;
							dblInspre1 = 0.0;
							dblInspre2 = 0.0;
							dblTotalpremium = 0.0;
							intCosttocust = 0;
							dblTotintincome = 0.0;
							dblDefvatonint = 0.0;
							dblVatoutondp = 0.0;
							dblDefintincome = 0.0;
							dblDefvatonrev = 0.0;
							dblSales = 0.0;
							dblInsPrem2_75 =0;
							dblVat18 =0.0;
						} else {
							
							dtReleasedate = java.sql.Date.valueOf(strReleasedate);
							
							strNewReleasedate = strReleasedate.substring(8, 10) + "/" + strReleasedate.substring(5, 7) + "/" + strReleasedate.substring(0, 4);								
							dtNewreleasedate = formatter.parse(strNewReleasedate);

							intTrackerfee = Integer.parseInt(strTrackerfee);
							dblRate = Double.parseDouble(strRate);
							dblRate = dblRate/100;
							
							strStatus = "RELEASED";
							
							List<Waiver> wai = waiverRepo.findByproposalNo(strProposalno);
							for (Waiver waiver : wai) {
								if(waiver != null) {
									intwaiver = waiver.getPenaltyAmt();
									strWaiverReason = waiver.getReason();
								}	
							}
							
							List<Impoundedstock> imp = impRepo.findByproposalno(strProposalno);
							
							for (Impoundedstock imps : imp) {
								if(imps != null) {
									strImpoundDate = imps.getImpounddate();
								}else {
									strImpoundDate = "";
								}
							}
							
							List<Reposession> rep = reposessRepo.findByproposalno(strProposalno);
							
							for (Reposession repo : rep) {
								if(repo != null) {
									strRepoDate = repo.getReposessdatetime();
								}else {
									strRepoDate = "";
								}
							}

							strLnamount = strLoanamnt.replace(",", "");
							strDownp = strDownpayment.replace(",", "");
							
							intNoofinst = Integer.parseInt(strNoofinst);
							intLoanamnt = Integer.parseInt(strLnamount);
							dblLoanamount = Double.parseDouble(strLnamount);
							dblTracker = Double.parseDouble(strTrackerfee);
							
							Date firstDate = Date.valueOf(strReleasedate);
							Date secondDate = Date.valueOf(today);
							
							long timediff = secondDate.getTime() - firstDate.getTime();
							long diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
							dbldiff = (double) diff;
							
							if (strPaymode.equals("Weekly") || strPaymode.equals("weekly")) {								
								dblWeeks = dbldiff / 7;
								dblWeeks = Math.round(dblWeeks * 100) / 100D;
								strWeeks = Double.toString(dblWeeks);
								
								if (intNoofinst > 52) {
									intInsamtyr2 = (int) (intLoanamnt * 0.5);
								} else {
									intInsamtyr2 = 0;
								}
							} else {
								dblWeeks = dbldiff / 30;
								dblWeeks = Math.round(dblWeeks * 100) / 100D;
								strWeeks = Double.toString(dblWeeks);
								
								if (intNoofinst > 12) {
									intInsamtyr2 = (int) (intLoanamnt * 0.5);
								} else {
									intInsamtyr2 = 0;
								}
							}
							
							intTotalduewks = (int) Math.floor(dblWeeks);
							strTotalduewks = Integer.toString(intTotalduewks);
							
							double doubleEwi = Double.parseDouble(strEwi); // Parse as double first
							intEwi = (int) doubleEwi; // Cast to int if necessary
							intTotaldue = intEwi * intTotalduewks;
							
							//total due must include penalties incurred
							intPenalty = 0;
							intTotalPenalty = 0;
							
//							StrQueryp = "From Penalty where loanid = '" + strProposalno + "'";
							List<Penalty> penalty = penaltyRepo.findByloanid(strProposalno);
							
							for (Penalty penal:penalty) {
								strPenalty = penal.getPenalty();
								intPenalty = Integer.parseInt(strPenalty);
								intTotalPenalty += intPenalty;
							}
							
							intTotaldue += intTotalPenalty;
							//***************************************************
							
							strTotaldue = Integer.toString(intTotaldue);
							
							strInsamtyr2 = Integer.toString(intInsamtyr2);
							intDiscount = Integer.parseInt(strDiscount.replace(",", ""));
							
							
							/*
							 * This formulae to be removed and value picked from db. For existing vehicles a populate customervehicle table 
							 */
//							if (strDealer.contains("Verma")) { 
//								dblVatreceived = 597355.93;
//							} else {//(assetcost-discount)/118 * 18
//								dblVatreceived = ((intLoanamnt - intDiscount) /118) * 18;
//								dblVatreceived = Math.round(dblVatreceived * 100) / 100D;
//								//dblVatreceived = 671033.90;
//							}
							
							strVatreceived = Double.toString(dblVatreceived);
							
							dblCostofpurchase = dblLoanamount - dblVatreceived;
							strCostofpurchase = Double.toString(dblCostofpurchase);
							
							//intDiscount = Integer.parseInt(strDiscount);
							intDealerpayment = intLoanamnt - intDiscount;
							strDealerpayment = Integer.toString(intDealerpayment);
							
							//Calculate insurance premiums //=(((AM6*2.76375%)+5000)*1.18)+35000 //=IF(AN6>0,(((AN6*2.76375%)+5000)*1.18)+35000,0)
							dblInspre1 = (((dblLoanamount * 0.0276375) + 5000) * 1.18) + 35000;
							dblInspre1 = Math.round(dblInspre1 * 100) / 100D;
							
							if (intInsamtyr2 > 0)  {
								dblInspre2 = (((intInsamtyr2 * 0.0276375) + 5000) * 1.18) + 35000;
								dblInspre2 = Math.round(dblInspre2 * 100) / 100D;
							} else {
								dblInspre2 = 0;
							}
							
							dblTotalpremium = dblInspre1 + dblInspre2;								
							
							strInspre1 = Double.toString(dblInspre1);
							strInspre2 = Double.toString(dblInspre2);
							strTotalpremium = Double.toString(dblTotalpremium);
							dblInsPrem2_75 = dblTotalpremium * (2.75 / 100);
							dblInsTraLevy = dblInsPrem2_75 * (0.5 / 100);
							dblStickerFee = 5000;
							dblInsVatPurpose = dblInsPrem2_75 + dblInsTraLevy + dblStickerFee;
							dblVat18 = dblInsVatPurpose * (18 / 100);
							dblStampDuty = dblLoanamount*0.01;
							dblTotalPayToInsCo = dblInsVatPurpose + dblVat18 + dblStampDuty;
							intDp = Integer.parseInt(strDownp);
							intCosttocust = intNoofinst * intEwi;
							dblTotintincome = intCosttocust -((intLoanamnt - intDp) + dblInspre1 + dblTracker);
							dblTotintincome = Math.round(dblTotintincome * 100) / 100D;
							dblDefvatonint = (dblTotintincome / 118) * 18;
							dblDefvatonint = Math.round(dblDefvatonint * 100) / 100D;
							dblVatoutondp = (intDp / 118) * 18;
							dblVatoutondp = Math.round(dblVatoutondp * 100) / 100D;
							dblDefintincome = dblTotintincome - dblDefvatonint;
							dblDefintincome = Math.round(dblDefintincome * 100) / 100D;
							dblDefvatonrev = dblVatreceived - dblVatoutondp;
							dblDefvatonrev = Math.round(dblDefvatonrev * 100) / 100D;
							
							//Sales =(((AE3*AF3)+AC3+AA3)-(AV3+(AA3*2)+BL3+BM3+BN3+BO3))
							dblSales = (((intCosttocust) + intDp + dblTracker)-(dblInspre1 + (dblTracker * 2) + dblDefvatonint + dblVatoutondp + dblDefintincome + dblDefvatonrev));
							dblSales = Math.round(dblSales * 100) / 100D;
							
							strCosttocust = Integer.toString(intCosttocust);
							strTotintincome = Double.toString(dblTotintincome);
							strDefvatonint = Double.toString(dblDefvatonint);
							strVatoutondp = Double.toString(dblVatoutondp);
							strDefintincome = Double.toString(dblDefintincome);
							strDefvatonrev = Double.toString(dblDefvatonrev);
							strSales = Double.toString(dblSales);
												
							try {
								strQuery = "From WeeklyInstallment where proposalno = '" + strProposalno + "' and revflag = false";								
								List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findByRevFlagAndProposalNo(strProposalno);
								
								intTotalrecv = 0;
								intAmount = 0;
								
								for (WeeklyInstallment winst:weeklyinst) {
									strAmount = winst.getAmount();
									intAmount = Integer.parseInt(strAmount);
									intTotalrecv += intAmount;
								}
								
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println(e.getLocalizedMessage());}
							
							strTotalrecv = Integer.toString(intTotalrecv);
							intOverdue = intTotaldue - intTotalrecv;							
							strOverdue = Integer.toString(intOverdue);
						}
						
						if(intOverdue > 0) {
							strLiveOrClosed = "Live";
						}else {
							strLiveOrClosed = "Closed";
						}
						System.out.println(strLiveOrClosed);
						intCounter++;
						
						try {
							
//							strQuery = "From vwCustomer where otherid = '" + strCustid + "'";
							
							List<vwCustomer> vwcustomer = vwcustRepo.findByotherid(strCustid);
											
							for (vwCustomer vwcust:vwcustomer) {
								strCapturedate = vwcust.getCapturedate();
								//strMonthyear = vwcust.getMonthYear();
								strCustomername = vwcust.getCustomername();
								strProfile = vwcust.getProfile();
								strOtherid = vwcust.getOtherid();
								strStage = vwcust.getStage();
								strChairman = vwcust.getChairman();
								strChairmanmobile = vwcust.getChairmanmobileno();
								strAddress = vwcust.getAddress();
								strMobileno = vwcust.getMobileno();
																	
								try {
//									strQuery = "From vwGuarantors where custid = '" + strCustid + "'";
									
									List<vwGuarantors> vwguarantor = vwGuaranRepo.findBycustid(strCustid);
													
									for (vwGuarantors vwguaran:vwguarantor) {
										strFirstguaran = vwguaran.getFirstguaran();
										strSecondguaran = vwguaran.getSecondguaran();											
									}
									
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println(e.getLocalizedMessage());}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.getLocalizedMessage());}
						
						/* NumberFormat dp3 = new NumberFormat("###,###.##");
						 * WritableCellFormat dp3cell = new WritableCellFormat(dp3);
						 * n = new Number(1,3,3.1415926535,dp3cell);
						 */
						System.out.println(dblVat18);
						//Populate excel rows
			            number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            label = new Label(1, intCounter, strMonthyear);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomername);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strProfile);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strCustid);
			            excelSheet.addCell(label);
			            
			            label = new Label(5, intCounter, strProposalno);
			            excelSheet.addCell(label);
			            
			            label = new Label(6, intCounter, strAgreement);
			            excelSheet.addCell(label);
			            
			            label = new Label(7, intCounter, strVehicle);
			            excelSheet.addCell(label);
			            
			            label = new Label(8, intCounter, ""); //insurance no
			            excelSheet.addCell(label);
			            
			            label = new Label(9, intCounter, strStage);
			            excelSheet.addCell(label);
			            
			            label = new Label(10, intCounter, strChairman);
			            excelSheet.addCell(label);
			            
			            label = new Label(11, intCounter, strChairmanmobile);
			            excelSheet.addCell(label);
			            
			            label = new Label(12, intCounter, strAddress);
			            excelSheet.addCell(label);
			            
			            label = new Label(13, intCounter, strMobileno);
			            excelSheet.addCell(label);
			            
			            label = new Label(14, intCounter, strFirstguaran);
			            excelSheet.addCell(label);
			            
			            label = new Label(15, intCounter, strSecondguaran);
			            excelSheet.addCell(label);
			            
			            label = new Label(16, intCounter, strVehiclebrand);
			            excelSheet.addCell(label);
			            
			            label = new Label(17, intCounter, ""); //sourced by
			            excelSheet.addCell(label);
			            
			            label = new Label(18, intCounter, ""); //Source type
			            excelSheet.addCell(label);
			            
			            label = new Label(19, intCounter, strDealer);
			            excelSheet.addCell(label);
			            
			            label = new Label(20, intCounter, strStatus);
			            excelSheet.addCell(label);
			            
			            //DateTime datetime = new DateTime(21, intCounter, dtReleasedate);
			            DateTime date = new DateTime(21, intCounter, dtNewreleasedate, DATE_CELL_FRMT);
			            excelSheet.addCell(date);
			            
			            label = new Label(22, intCounter, strPaymentday);
			            excelSheet.addCell(label);
			            
			            label = new Label(23, intCounter, strSimcardno);
			            excelSheet.addCell(label);
			            
			            label = new Label(24, intCounter, strTrackingno);
			            excelSheet.addCell(label);				            
						
						number = new Number(25, intCounter, intTrackerfee, dp3cell);
						excelSheet.addCell(number);
						
						number = new Number(26, intCounter, intLoanamnt, dp3cell);
			            excelSheet.addCell(number);
			            
			            number = new Number(27, intCounter, intDp, dp3cell);
			            excelSheet.addCell(number);
		
			            number = new Number(28, intCounter, dblRate, dp4cell);
			            excelSheet.addCell(number);
			            
			            number = new Number(29, intCounter, intEwi, dp3cell);
			            excelSheet.addCell(number);
			            
			            number = new Number(30, intCounter, intNoofinst);
			            excelSheet.addCell(number);
			            
			            label = new Label(31, intCounter, strPaymode);
			            excelSheet.addCell(label);
			            
			            number = new Number(32, intCounter, dblWeeks);
			            excelSheet.addCell(number);
			            
			            number = new Number(33, intCounter, intTotalduewks);
			            excelSheet.addCell(number);
			            
			            number = new Number(34, intCounter, intTotaldue);
			            excelSheet.addCell(number);
			            
			            number = new Number(35, intCounter, intTotalrecv);
			            excelSheet.addCell(number);
			            
			            number = new Number(36, intCounter, intOverdue);
			            excelSheet.addCell(number);
			            
			            number = new Number(37, intCounter, intLoanamnt);
			            excelSheet.addCell(number);
			            
			            number = new Number(38, intCounter, intInsamtyr2);
			            excelSheet.addCell(number);
			            
			            number = new Number(39, intCounter, dblVatreceived);
			            excelSheet.addCell(number);
			            
			            number = new Number(40, intCounter, dblCostofpurchase);
			            excelSheet.addCell(number);
			            
			            number = new Number(41, intCounter, intDiscount);
			            excelSheet.addCell(number);
			            
			            number = new Number(42, intCounter, intDealerpayment);
			            excelSheet.addCell(number);
			            
			            label = new Label(43, intCounter, strLiveOrClosed); //Live /closed
			            excelSheet.addCell(label);
			            
			            label = new Label(44, intCounter, ""); //Dealer DO No. 
			            excelSheet.addCell(label);
			            
			            label = new Label(45, intCounter, ""); //Dealer DO No. 
			            excelSheet.addCell(label);
			            
			            label = new Label(46, intCounter, ""); //Dealer Payment made 
			            excelSheet.addCell(label);
			            
			            number = new Number(47, intCounter, dblInspre1);
			            excelSheet.addCell(number);
			            
			            number = new Number(48, intCounter, dblInspre2);
			            excelSheet.addCell(number);
			            
			            number = new Number(49, intCounter, dblTotalpremium);
			            excelSheet.addCell(number);
			            
			            number = new Number(50, intCounter, dblInsPrem2_75);
			            excelSheet.addCell(number);
			            
			            number = new Number(51, intCounter, dblInsTraLevy);
			            excelSheet.addCell(number);
			            
			            number = new Number(52, intCounter, dblStickerFee);
			            excelSheet.addCell(number);
			            
			            number = new Number(53, intCounter, dblInsVatPurpose);
			            excelSheet.addCell(number);
			            
			            number = new Number(54, intCounter, dblVat18);
			            excelSheet.addCell(number);
			            
			            number = new Number(55, intCounter, dblStampDuty);
			            excelSheet.addCell(number);
			            
			            number = new Number(56, intCounter, dblTotalPayToInsCo);
			            excelSheet.addCell(number);
			            
			            number = new Number(57, intCounter, intCosttocust);
			            excelSheet.addCell(number);
			            
			            number = new Number(58, intCounter, dblTotintincome);
			            excelSheet.addCell(number);
			            
			            number = new Number(59, intCounter, dblDefvatonint);
			            excelSheet.addCell(number);
			            
			            number = new Number(60, intCounter, dblVatoutondp);
			            excelSheet.addCell(number);
			            
			            number = new Number(61, intCounter, dblDefintincome);
			            excelSheet.addCell(number);
			            
			            number = new Number(62, intCounter, dblDefvatonrev);
			            excelSheet.addCell(number);
						
			            number = new Number(63, intCounter, dblSales);
			            excelSheet.addCell(number);
			            
			            number = new Number(64, intCounter, intwaiver);
			            excelSheet.addCell(number);
			            
			            label = new Label(65, intCounter, strWaiverReason);
			            excelSheet.addCell(label);
			            
			            label = new Label(66, intCounter, strImpoundDate);
			            excelSheet.addCell(label);
			            
			            label = new Label(67, intCounter, strRepoDate);
			            excelSheet.addCell(label);
			            
			            intwaiver = 0;
			            strWaiverReason = "";
			            strImpoundDate ="";
			            strRepoDate="";
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());}
				
				myDatadump.write();
			
            } catch (IOException e) {
            	e.printStackTrace();
            	System.out.println(e.getLocalizedMessage());
            } catch (WriteException e) {
            	e.printStackTrace();
            	System.out.println(e.getLocalizedMessage());
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
	            String keyName = "filename.xls"; 
	            
	      
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
	            // Download the file from S3
//	            S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
//	            InputStream s3InputStream = s3Object.getObjectContent();
	            
//		        	FileInputStream inputStream = new FileInputStream(file);
//		            resource1 = new InputStreamResource(inputStream);

	         // Set headers for downloading the file
//	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + keyName);
//	            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

	            // Return InputStreamResource as ResponseEntity
//	            resource1 = new InputStreamResource(s3InputStream);
//	            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
			} catch (Exception e) {
			    e.printStackTrace();
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}				
//		session.setAttribute("datadumpid", "");
				return ResponseEntity.ok(url.toString());
	}

	public ResponseEntity<String> generateCollectionReport(String datetime) {
		
		String strDate = "";
		String strQuery = "";
		String strQueryp = "";
		String strNewxlfile = "";
		String strFilename = "";
		
		String strCustid = "";
		String strProposalno = "";
		String strVehicle = ""; 
		String strReleasedate = ""; 
		String strLoanamnt = ""; 
		String strEwi = ""; 
		String strNoofinst = ""; 
		String strPaymode = "";
		String strCustomername = ""; 
		String strProfile = ""; 
		String strWeeks = "";
		String strAmount = "";
		String strTotaldue = "";
		String strTotalrecv = "";
		String strOverdue = "";
		String strLnamount = "";
		String strPercentage = "";
		String strAgreement = "";			
		String strPenalty = "";
		String strCustmobile = "";
		
		int intPenalty = 0;
		int intTotalPenalty = 0;
		
		int intCounter = 0;
		int intAgreementserial = 0;
		
		double dblWeeks = 0;
		double dbldiff = 0;
		double dblOverdue = 0;
		double dblEwi = 0;
		
		int intTotalduewks = 0;
		int intAmount = 0;
		int intEwi = 0;
		int intTotaldue = 0;
		int intTotalrecv = 0;
		int intOverdue = 0;
		
		int intNoofinst = 0;
		int intLoanamnt = 0;
		
		int intGtotaldue = 0;
		int intGtotaloverdue = 0;
		
		double dblGtotaldue = 0;
		double dblGtotaloverdue = 0;
		double dblPercentage = 0;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		strDate = datetime;
		
		Response<String> response = new Response<>();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
							
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
				
			/*GetTimeStamp gts = new GetTimeStamp();		
			strDate = gts.TimeStamp();*/
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "collectionrpt.xls";
			
			LocalDate today = LocalDate.now();
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("MASTER", 0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Customer Name.");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Profile ");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Mobile No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Agreement No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Veh. Regn No ");
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(6, 0, "Asset cost ");
	            excelSheet.addCell(label);	            
	            
	            label = new Label(7, 0, "EWI ");
	            excelSheet.addCell(label);
	            
	            label = new Label(8, 0, "Tenure ");
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "Total Due ");
	            excelSheet.addCell(label);
	            
	            label = new Label(10, 0, "Total Received ");
	            excelSheet.addCell(label);
	            
	            label = new Label(11, 0, "Overdue ");
	            excelSheet.addCell(label);
	            
	            label = new Label(12, 0, "Bucket ");
	            excelSheet.addCell(label);
	            
				try {
					strQuery = "From vwProposal where dateofrelease is not null";
					
					List<vwProposal> vwproposal = vmProposalRepo.findByDateRelease();
									
					for (vwProposal vwprop:vwproposal) {
						strCustid = vwprop.getCustomerid();
						strProposalno = vwprop.getProposalno();
						strAgreement = vwprop.getAgreementno();
						strVehicle = vwprop.getVehicleregno();
						strReleasedate = vwprop.getDateofrelease();
						strLoanamnt = vwprop.getLoanamount();
						strEwi = vwprop.getEwi();
						System.out.println();
						strNoofinst = vwprop.getNoofinstallments();
						strPaymode = vwprop.getPaymentmode();
						
						strTotalrecv = "";						
						strOverdue = "";
						strTotaldue = "";
						strWeeks = "";							
						strPenalty = "";
						
						intPenalty = 0;
						intTotalPenalty = 0;
													
						if (strReleasedate == null || strReleasedate.length() == 0) {
							strWeeks = "";										
							strTotaldue = "";												
							strTotalrecv = "";							
							strOverdue = "";
							
							if (strLoanamnt == null) {
								intLoanamnt = 0;
							} else {
								if (strLoanamnt.length() > 0) {
									strLnamount = strLoanamnt.replace(",", "");
									intLoanamnt = Integer.parseInt(strLnamount);
								} else {
									intLoanamnt = 0;
								}
							}
							
							if (strLoanamnt == null) {
								intNoofinst = 0;
							} else {
								if (strNoofinst.length() > 0) {
									intNoofinst = Integer.parseInt(strNoofinst);
								} else {
									intNoofinst = 0;
								}
							}
							
							if (strEwi == null) {
								intEwi = 0;
								intTotalduewks = 0;
								intTotaldue = 0;
								strTotaldue = "";
							} else {
								if (strEwi.length() > 0) {
									intEwi = Integer.parseInt(strEwi);
									intTotalduewks = 0;
									intTotaldue = intEwi * intTotalduewks;
									strTotaldue = Integer.toString(intTotaldue);
								} else {
									intEwi = 0;
									intTotalduewks = 0;
									intTotaldue = 0;
									strTotaldue = "";
								}
							}
								
							intTotalrecv = 0;
							intOverdue = 0;
							dblWeeks = 0;
						} else {
							
							strLnamount = strLoanamnt.replace(",", "");								
							intNoofinst = Integer.parseInt(strNoofinst);
							intLoanamnt = Integer.parseInt(strLnamount);
							
							Date firstDate = Date.valueOf(strReleasedate);
							Date secondDate = Date.valueOf(today);
							
							long timediff = secondDate.getTime() - firstDate.getTime();
							long diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
							dbldiff = (double) diff;
							
							if (strPaymode.equals("Weekly") || strPaymode.equals("weekly")) {								
								dblWeeks = dbldiff / 7;
								dblWeeks = Math.round(dblWeeks * 100) / 100D;
							} else {
								dblWeeks = dbldiff / 30;
								dblWeeks = Math.round(dblWeeks * 100) / 100D;
							}
							
							intTotalduewks = (int) Math.floor(dblWeeks);  //Weeks/Month example 199
							
							intEwi = Integer.parseInt(strEwi);
							intTotaldue = intEwi * intTotalduewks; //199*97000
							
							//total due must include penalties incurred
							intPenalty = 0;
							intTotalPenalty = 0;
							
							strQueryp = "From Penalty where loanid = '" + strProposalno + "'";
							List<Penalty> penalty = penaltyRepo.findByloanid(strProposalno);
							
							for (Penalty penal:penalty) {
								strPenalty = penal.getPenalty();
								intPenalty = Integer.parseInt(strPenalty);
								intTotalPenalty += intPenalty;
							}
							
							intTotaldue += intTotalPenalty;
							//***************************************************								
															
							strTotaldue = Integer.toString(intTotaldue);
																				
							try {
								strQuery = "From WeeklyInstallment where proposalno = '" + strProposalno + "' and revflag = false";								
								List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findByRevFlagAndProposalNo(strProposalno);
								
								intTotalrecv = 0;
								intAmount = 0;
								
								for (WeeklyInstallment winst:weeklyinst) {
									strAmount = winst.getAmount();
									intAmount = Integer.parseInt(strAmount);
									intTotalrecv += intAmount;                 //TotalRecovered
								}								
							} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
							
							strTotalrecv = Integer.toString(intTotalrecv);
							intOverdue = intTotaldue - intTotalrecv;			//Overdue				
							strOverdue = Integer.toString(intOverdue);
							
							intGtotaldue += intTotaldue;
							intGtotaloverdue += intOverdue;
							
							dblOverdue = Integer.valueOf(intOverdue);
							dblEwi = Integer.valueOf(intEwi);
							
							dblWeeks = dblOverdue / dblEwi;
							dblWeeks = Math.round(dblWeeks * 100) / 100D;
							strWeeks = Double.toString(dblWeeks);
						}
						
						intCounter++;
						
						try {
							
							strQuery = "From vwCustomer where otherid = '" + strCustid + "'";
							
							List<vwCustomer> vwcustomer = vwcustRepo.findByotherid(strCustid);
											
							for (vwCustomer vwcust:vwcustomer) {
								strCustomername = vwcust.getCustomername();
								strProfile = vwcust.getProfile();	
								strCustmobile = vwcust.getMobileno();
							}
							
						} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            label = new Label(1, intCounter, strCustomername);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strProfile);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strCustmobile);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strAgreement);
			            excelSheet.addCell(label);
			            
			            label = new Label(5, intCounter, strVehicle);
			            excelSheet.addCell(label);
			            
			            number = new Number(6, intCounter, intLoanamnt);
			            excelSheet.addCell(number);
			            
			            number = new Number(7, intCounter, intEwi);
			            excelSheet.addCell(number);
			            
			            number = new Number(8, intCounter, intNoofinst);
			            excelSheet.addCell(number);
			            
			            number = new Number(9, intCounter, intTotaldue);
			            excelSheet.addCell(number);
			            
			            number = new Number(10, intCounter, intTotalrecv);
			            excelSheet.addCell(number);
			            
			            number = new Number(11, intCounter, intOverdue);
			            excelSheet.addCell(number);
			            
			            number = new Number(12, intCounter, dblWeeks);
			            excelSheet.addCell(number);
			            				            
					}
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}
				
				dblGtotaldue = Integer.valueOf(intGtotaldue);
				dblGtotaloverdue = Integer.valueOf(intGtotaloverdue);
				dblPercentage = (dblGtotaloverdue / dblGtotaldue) * 100;
				dblPercentage = Math.round(dblPercentage * 100) / 100D;
				strPercentage = Double.toString(dblPercentage) + "%";
				
				label = new Label(13, 0, strPercentage);
	            excelSheet.addCell(label);
				
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
				
//			    file = new File(strFilename);
//		        if (file.exists()) {
//		        	FileInputStream inputStream = new FileInputStream(file);
//		            resource1 = new InputStreamResource(inputStream);
//
//		            // Set headers
//		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);		    
		        	
//		        	response.reset();
//		        	response.addHeader("Pragma", "public");
//		        	response.addHeader("Cache-Control", "max-age=0");
//		        	response.setHeader("Content-disposition", "attachment;filename=\"%s\"" + file.getName());
//		        	response.setContentType("application/vnd.ms-excel");
//		        			        	
//		        	// avoid "byte shaving" by specifying precise length of transferred data
//		        	response.setContentLength(encodedBytes.length);
//
//		        	// send to output stream
//		        	ServletOutputStream servletOutputStream = response.getOutputStream();
//		            
//		            try {		            	 
//		                			            	
//		            	servletOutputStream.write(encodedBytes);			            	
//		                
//		            } catch(IOException ioExObj) {
//		                System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
//		            } finally {  
//		                servletOutputStream.flush();
//		                servletOutputStream.close();
//		            }
//		        }
		        
			} catch (Exception e) {
			    e.printStackTrace();
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}				
		//response.setContentType("text/plain");
		//response.setCharacterEncoding("UTF-8");
		//response.getWriter().write(strMsg);
//		session.setAttribute("collectionid", "");
		return ResponseEntity.ok(url.toString());
	}
	
	@Autowired
	private CustomerVehicleRepo custvehRepo;
	
	@Autowired
	private PaymentScheduleRepo paymentSchRepo;

	public ResponseEntity<String> generateDue(String datetime, String fromDate, String toDate) {
		
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
		
		String strMsg = "";
		
		String strVehicle = "";
		String strProposalno = "";
		String strInstallment = "";
		String strPrincipal = "";
		String strInterest = "";
		String strPaymentdate = "";
		String strFromdate = "";
		String strTodate = "";
		
		int intInstallment = 0;
		double dblPrincipal = 0;
		double dblInterest = 0;
		double dblVatonprincipal = 0;
		double dblVatoninterest = 0;
		
		int intInsttotal = 0;
		double dblPrincipaltotal = 0;
		double dblInteresttotal = 0;
		double dblVatonprincipaltotal = 0;
		double dblVatoninteresttotal = 0;
		
		boolean blnDatevalid = false;
		boolean blnDate1valid = false;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		int intCounter = 0;
		
		java.util.Date dtDuedate = null;
		
		final WritableCellFormat DATE_CELL_FRMT;
		
		Response<String> response = new Response<>();
		
		strDate = datetime;
		strFromdate = fromDate;
		strTodate = toDate;
			
		fieldValidation fv = new fieldValidation();
		
		if (fv.validateQDate(strFromdate) == "success") {
			blnDatevalid = true;
		} else {blnDatevalid = false;}
		
		if (fv.validateQDate(strTodate) == "success") {
			blnDate1valid = true;
		} else {blnDate1valid = false;}
		
		if (strDate == null || strDate.length() == 0) {
			
		} else if (blnDatevalid == false) {
			strMsg = "Please ensure from date format is yyyy-mm-dd";
		} else if (blnDate1valid == false) {
			strMsg = "Please ensure to date format is yyyy-mm-dd";
		} else {
			
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "duerpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Installments", 0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Payment Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Proposalno.");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Vehicle Reg No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Installment ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Principal ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Interest ");
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(7, 0, "VAT on principal ");
	            excelSheet.addCell(label);
	            
	            label = new Label(8, 0, "VAT on interest ");
	            excelSheet.addCell(label);
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
				try {
					
					 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				        // Parse the string to LocalDate
				        LocalDate fromDateLocal = LocalDate.parse(strFromdate, formatter);
				        LocalDate toDateLocal = LocalDate.parse(strTodate, formatter);

				        // Convert LocalDate to java.sql.Date
				        Date sqlFromDate = Date.valueOf(fromDateLocal);
				        Date sqlToDate = Date.valueOf(toDateLocal);
					
					List<PaymentSchedule> paysch = paymentSchRepo.findBetweenDates(sqlFromDate, sqlToDate);
									
					//Proposalno, Installment, Principal, Interest, VAT on Principal, VAT on Interest
					for (PaymentSchedule ps:paysch) {							
						strProposalno = ps.getLoanid();
						strInstallment = ps.getInstallment();
						strPrincipal = ps.getPrincipal();
						strInterest = ps.getInterestamount();
						strPaymentdate = ps.getPaymentdate();
													
						CustomerVehicle custveh = custvehRepo.findByproposalno(strProposalno);											
						
						strVehicle = custveh.getVehicleregno();
						
						SimpleDateFormat sdformatter = new SimpleDateFormat("dd/MM/yyyy");
						dtDuedate = sdformatter.parse(strPaymentdate);
													
						intInstallment = Integer.parseInt(strInstallment);
						dblPrincipal = Double.parseDouble(strPrincipal);
						dblInterest = Double.parseDouble(strInterest);
						
						dblVatonprincipal = (dblPrincipal * 18) / 118;
						dblVatoninterest = (dblInterest * 18) / 118;
						
						dblVatonprincipal = Math.round(dblVatonprincipal * 100) / 100D;
						dblVatoninterest = Math.round(dblVatoninterest * 100) / 100D;
						
						intCounter++;					
						
						intInsttotal += intInstallment;
						dblPrincipaltotal += dblPrincipal;
						dblInteresttotal += dblInterest;
						dblVatonprincipaltotal += dblVatonprincipal;
						dblVatoninteresttotal += dblVatoninterest;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            DateTime datet = new DateTime(1, intCounter, dtDuedate, DATE_CELL_FRMT);
			            excelSheet.addCell(datet);
			            
			            label = new Label(2, intCounter, strProposalno);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strVehicle);
			            excelSheet.addCell(label);
			            
			            number = new Number(4, intCounter, intInstallment);
			            excelSheet.addCell(number);
			            
			            number = new Number(5, intCounter, dblPrincipal);
			            excelSheet.addCell(number);
			            
			            number = new Number(6, intCounter, dblInterest);
			            excelSheet.addCell(number);
			            
			            number = new Number(7, intCounter, dblVatonprincipal);
			            excelSheet.addCell(number);	
			            
			            number = new Number(8, intCounter, dblVatoninterest);
			            excelSheet.addCell(number); 
			            
			            
					}
					
				} catch (Exception e) {System.out.println("trycatch"+e.getLocalizedMessage());}
				
				//Adding totals to due report
				intCounter++;
				
	            label = new Label(0, intCounter, "");
	            excelSheet.addCell(label);
	            
	            label = new Label(1, intCounter, "");
	            excelSheet.addCell(label);
	            		            
	            label = new Label(2, intCounter, "");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, intCounter, "Totals: ");
	            excelSheet.addCell(label);
	            
	            Number number = new Number(4, intCounter, intInsttotal);
	            excelSheet.addCell(number);
	            
	            number = new Number(5, intCounter, dblPrincipaltotal);
	            excelSheet.addCell(number);
	            
	            number = new Number(6, intCounter, dblInteresttotal);
	            excelSheet.addCell(number);
	            
	            number = new Number(7, intCounter, dblVatonprincipaltotal);
	            excelSheet.addCell(number);	
	            
	            number = new Number(8, intCounter, dblVatoninteresttotal);
	            excelSheet.addCell(number); 
									
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
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}
		
		if (!strMsg.equals("")) {
			headers.add("X-message", strMsg);
		}		
		return ResponseEntity.ok(url.toString());
	}


	
	public ResponseEntity<String> generateRejCust(String datetime) {
		
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
		
		String strCustid = "";
		String strFirstname = "";
		String strSurname = "";
		String strMobileno = "";
		String strStage = "";
		String strNationalid = "";
		String strCapturedate = "";
		String strRejectstage = "";
		String strRejectreason = "";
		
		int intInstallment = 0;
		double dblPrincipal = 0;
		double dblInterest = 0;
		double dblVatonprincipal = 0;
		double dblVatoninterest = 0;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		int intCounter = 0;
		
		java.util.Date dtDuedate = null;
		
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
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "rejcustrpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Rejected customers", 0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Customer Id ");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Surname.");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Firstname ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Mobile No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Stage ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "National Id ");
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(7, 0, "Capture Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(8, 0, "Reject Stage");
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "Reject Reason");
	            excelSheet.addCell(label);
	            
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
				try {
					strQuery = "From vwRejcustomers";							
					List<vwRejcustomers> rejcustomers = vwRejRepo.findAll();										
					
					for (vwRejcustomers rejcust:rejcustomers) {												
						strCustid = rejcust.getOtherid();
						strFirstname = rejcust.getFirstname();
						strSurname = rejcust.getSurname();
						strMobileno = rejcust.getMobileno();
						strStage = rejcust.getStage();
						strNationalid = rejcust.getNationalid();
						strCapturedate = rejcust.getCapturedatetime();
						strRejectstage = rejcust.getRejectreason();
						strRejectreason = rejcust.getRejectremarks();	
													
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						dtDuedate = formatter.parse(strCapturedate);
						
						intCounter++;	
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            label = new Label(1, intCounter, strCustid);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strSurname);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strFirstname);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strMobileno);
			            excelSheet.addCell(label);
			            
			            label = new Label(5, intCounter, strStage);
			            excelSheet.addCell(label);
			            
			            label = new Label(6, intCounter, strNationalid);
			            excelSheet.addCell(label);

			            DateTime datet = new DateTime(7, intCounter, dtDuedate, DATE_CELL_FRMT);
			            excelSheet.addCell(datet);
			            
			            label = new Label(8, intCounter, strRejectstage);
			            excelSheet.addCell(label); 
			            
			            label = new Label(9, intCounter, strRejectreason);
			            excelSheet.addCell(label); 
			            
					}
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}					
									
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
				
				 InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		            // Upload to S3 bucket
		            AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
		            String keyName = strDate+"rejcust.xls"; 
		            
		      
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
	}

	public ResponseEntity<String> generateBikereldisb(String datetime) {
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
		
		String strAgreement = "";
		String strProposalno = "";
		String strVehicleregno = "";
		String strAllocationdate = "";
		String strDisbdate = "";
		String strDisbursed = "";
		boolean blnDisbursed = false;
		
		int intAgreementserial = 0;
		
		int intCounter = 0;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		java.util.Date dtAllocDate = null;
		java.util.Date dtDisbDate = null;
		
		final WritableCellFormat DATE_CELL_FRMT;
		
		strDate = datetime;
		
		Response<String> response = new Response<>();
							
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
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "bikereldisbrpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Disbursed Yes No", 0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Agreement No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Proposal No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Vehicle Reg No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Vehicle Allocation Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Disbursed? ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Disbursement Date ");
	            excelSheet.addCell(label);
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
				try {
					strQuery = "From CustomerVehicle";							
					List<CustomerVehicle> custveh = custvehRepo.findAll();									
					
					for (CustomerVehicle cv:custveh) {												
						intAgreementserial = cv.getAgreementserial();
						strProposalno = cv.getProposalno();
						strVehicleregno = cv.getVehicleregno();
						strAllocationdate = cv.getCapturedatetime();
						strDisbdate = cv.getDisbdatetime();
						blnDisbursed = cv.getDisbursed();
							
						strAgreement = "AN" + intAgreementserial;
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						dtAllocDate = formatter.parse(strAllocationdate);
						if (strDisbdate == null || strDisbdate.length() == 0) {
							dtDisbDate = formatter.parse("01/01/1900");
						} else {
							dtDisbDate = formatter.parse(strDisbdate);
						}
						
						if (blnDisbursed == true) {
							strDisbursed = "YES";
						} else {
							strDisbursed = "NO";
						}
						
						intCounter++;	
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            label = new Label(1, intCounter, strAgreement);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strProposalno);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strVehicleregno);
			            excelSheet.addCell(label);
			            
			            DateTime datet = new DateTime(4, intCounter, dtAllocDate, DATE_CELL_FRMT);
			            excelSheet.addCell(datet);
			            
			            label = new Label(5, intCounter, strDisbursed);
			            excelSheet.addCell(label);
			            
			            datet = new DateTime(6, intCounter, dtDisbDate, DATE_CELL_FRMT);
			            excelSheet.addCell(datet);
			            
					}
					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}					
									
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
	            String keyName = strDate+"bikereldisbrpt.xls";
	            
	      
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
	}
	

	

	public ResponseEntity<String> generateTat(String datetime) {
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
					
		String strCustomerid = "";
		String strGuaranid ;
		String strSurname = "";
		String strFirstname = "";
		String strCapturedatetime = "";
		String strCqcdatetime = "";
		String strTvdatetime = "";
		String strFidatetime = "";
		String strCamdatetime = "";
		String strCamadatetime = "";
		String strProposalno = "";
		
		String strGuarantype = "";
		
		boolean bln1stguarantype = false;
		boolean bln2ndguarantype = false;
					
		int intCounter = 0;
		int intCustcounter = 0;
		
		java.sql.Date dtCapturedate = null;
		java.sql.Date dtCqcdate = null;
		java.sql.Date dtFidate = null;
		java.sql.Date dtTvdate = null;
		java.sql.Date dtCamdate = null;
		java.sql.Date dtCamadate = null;
		
		java.sql.Date dtNulldate = null;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		int intCqc = 0;
		int intFi = 0;
		int intTv = 0;
		int intCam = 0;
		int intCama = 0;
		
		long timediff = 0;
		long diff = 0;
		
		final WritableCellFormat DATE_CELL_FRMT;
		final WritableCellFormat NUM_CELL_FMT;
		
		strDate = datetime;
		
		Response<String> response = new Response<>();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
							
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
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "tatrpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("TAT Report", 0);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdformatter = new SimpleDateFormat("dd/MM/yyyy");
            
            try {
//				dtNulldate = formatter.parse("01/01/1900");
				  java.util.Date utilDate = formatter.parse("01/01/1900"); // Parsing to java.util.Date  
		          // Convert java.util.Date to java.sql.Date
				  dtNulldate = new java.sql.Date(utilDate.getTime());
			} catch (ParseException e1) {System.out.println(e1.getLocalizedMessage());}
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "");
	            excelSheet.addCell(label);
	            		            
	            label = new Label(2, 0, "Id ");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Surname");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Firstname ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Capture datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "CQC datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "CQC Tat ");
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(8, 0, "FI datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "FI Tat ");
	            excelSheet.addCell(label);
	            
	            label = new Label(10, 0, "TV datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(11, 0, "TV Tat ");
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(12, 0, "Credit Appraisal Memo datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(13, 0, "Credit Appraisal Memo Tat ");
	            excelSheet.addCell(label);
	            
	            label = new Label(14, 0, "Credit Appraisal Memo Approval datetime ");
	            excelSheet.addCell(label);
	            
	            label = new Label(15, 0, "Credit Appraisal Memo Approval Tat ");
	            excelSheet.addCell(label);
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
	            /*NumberFormat numfmt = new NumberFormat("0; -0;"); //0;-0; new NumberFormat("#,##0;(#,##0)a"); 0;-0;;@
	            NUM_CELL_FMT = new WritableCellFormat(numfmt);*/
	            
				try {
//					strQuery = "From CustomerDetails order by capturedatetime desc";
					
					List<CustomerDetails> custdet = custRepo.findAllOrderByCapturedatetimeDesc();
									
					//Proposalno, Installment, Principal, Interest, VAT on Principal, VAT on Interest
					for (CustomerDetails cd:custdet) {	
						strCustomerid = cd.getOtherid();
						strSurname = cd.getSurname();
						strFirstname = cd.getFirstname();
						strCapturedatetime = cd.getCapturedatetime();
						strCqcdatetime = cd.getCqcdatetime();
						strTvdatetime = cd.getTvdatetime();
						strFidatetime = cd.getFidatetime();
						strCamdatetime = cd.getCamdatetime();
						strCamadatetime = cd.getCamadatetime();
						
													
						if (strCapturedatetime == null || strCapturedatetime.length() == 0) {
							dtCapturedate = (Date) dtNulldate;
						} else {
							
							
							java.util.Date utilDate = sdformatter.parse(strCapturedatetime);
							dtCapturedate = new java.sql.Date(utilDate.getTime());
//							dtCapturedate = (Date) formatter.parse(strCapturedatetime);
							
							if (strCqcdatetime == null || strCqcdatetime.length() == 0) {
								dtCqcdate = (Date) dtNulldate;
								intCqc = 0;
							} else {
//								SimpleDateFormat sdformatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
								java.util.Date cqcutilDate = sdformatter.parse(strCqcdatetime);
								dtCqcdate = new java.sql.Date(cqcutilDate.getTime());
								
//								dtCqcdate = (Date) formatter.parse(strCqcdatetime);
								timediff = dtCqcdate.getTime() - dtCapturedate.getTime();
								diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
								intCqc = (int) diff;
							}
							
							if (strFidatetime == null || strFidatetime.length() == 0) {
								dtFidate = (Date) dtNulldate;
								intFi = 0;
							} else {
								java.util.Date fiutilDate = sdformatter.parse(strFidatetime);
								dtFidate = new java.sql.Date(fiutilDate.getTime());
//								dtFidate = (Date) formatter.parse(strFidatetime);
								timediff = dtFidate.getTime() - dtCapturedate.getTime();
								diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
								intFi = (int) diff;
							}
							
							if (strTvdatetime == null || strTvdatetime.length() == 0) {
								dtTvdate = (Date) dtNulldate;
								intTv = 0;
							} else {
								java.util.Date tvutilDate = sdformatter.parse(strTvdatetime);
								dtTvdate = new java.sql.Date(tvutilDate.getTime());
//								dtTvdate = (Date) formatter.parse(strTvdatetime);
								timediff = dtTvdate.getTime() - dtCapturedate.getTime();
								diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
								intTv = (int) diff;
							}
							
							if (strCamdatetime == null || strCamdatetime.length() == 0) {
								dtCamdate = (Date) dtNulldate;
								intCam = 0;
							} else {
								java.util.Date camutilDate = sdformatter.parse(strCamdatetime);
								dtCamdate = new java.sql.Date(camutilDate.getTime());
//								dtCamdate = (Date) formatter.parse(strCamdatetime);	
								timediff = dtCamdate.getTime() - dtCapturedate.getTime();
								diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
								intCam = (int) diff;
							}
							
							if (strCamadatetime == null || strCamadatetime.length() == 0) {
								dtCamadate = dtNulldate;
								intCama = 0;
							} else {
								java.util.Date camutilDate = sdformatter.parse(strCamadatetime);
								dtCamadate = new java.sql.Date(camutilDate.getTime());
//								dtCamadate = formatter.parse(strCamadatetime);						
								timediff = dtCamadate.getTime() - dtCapturedate.getTime();
								diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
								intCama = (int) diff;
							}
						}
						
						intCounter++;
						intCustcounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCustcounter); 
			            excelSheet.addCell(number);				            

			            label = new Label(1, intCounter, "Customer");
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomerid);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strSurname);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strFirstname);
			            excelSheet.addCell(label);
			            
			            if (dtCapturedate != dtNulldate) {
				            DateTime datet = new DateTime(5, intCounter, dtCapturedate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
			            }
			            
			            if (dtCqcdate != dtNulldate) {
			            	DateTime datet = new DateTime(6, intCounter, dtCqcdate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
				         
				            number = new Number(7, intCounter, intCqc);
				            excelSheet.addCell(number);
			            }
			             
			            if (dtFidate != dtNulldate) {				            
			            	DateTime datet = new DateTime(8, intCounter, dtFidate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
				            				            
				            number = new Number(9, intCounter, intFi);
				            excelSheet.addCell(number);
			            }
			            
			            if (dtTvdate != dtNulldate) {		
			            	DateTime datet = new DateTime(10, intCounter, dtTvdate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
				            				            
				            number = new Number(11, intCounter, intTv);
				            excelSheet.addCell(number);
			            }
			            
			            if (dtCamdate != dtNulldate) {	
			            	DateTime datet = new DateTime(12, intCounter, dtCamdate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
				            				            
				            number = new Number(13, intCounter, intCam);
				            excelSheet.addCell(number);
			            }
			            
			            if (dtCamadate != dtNulldate) {	
			            	DateTime datet = new DateTime(14, intCounter, dtCamadate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
				            				            
				            number = new Number(15, intCounter, intCama);
				            excelSheet.addCell(number);
			            }
						
			            //Guarantors
//						strQuery = "From Guarantor where custid = '" + strCustomerid + "' order by firstguarantor desc";							
						List<Guarantor> guaran = guaranRepo.findByCustidOrderByFirstguarantorDesc(strCustomerid);											
						for (Guarantor g:guaran) {
							strGuaranid = Integer.toString(g.getId());
							strSurname = g.getSurname();
							strFirstname = g.getFirstname();
							bln1stguarantype = g.getFirstguarantor();
							bln2ndguarantype = g.getSecondguarantor();
							strCapturedatetime = g.getCapturedatetime();
							strCqcdatetime = g.getCqcdatetime();
							strTvdatetime = g.getTvdatetime();
							strFidatetime = g.getFidatetime();
							strCamdatetime = g.getCamdatetime();
							strCamadatetime = g.getCamadatetime();
							
							if (bln1stguarantype == true) {
								strGuarantype = "Guarantor I";
							} else if (bln2ndguarantype == true) {
								strGuarantype = "Guarantor II";
							}
							
							//Populate excel rows
							if (strCapturedatetime == null || strCapturedatetime.length() == 0) {
								dtCapturedate = (Date) dtNulldate;
							} else {
								java.util.Date utilDate = sdformatter.parse(strCapturedatetime);
								dtCapturedate = new java.sql.Date(utilDate.getTime());
//								dtCapturedate = (Date) formatter.parse(strCapturedatetime);
								
								if (strCqcdatetime == null || strCqcdatetime.length() == 0) {
									dtCqcdate = (Date) dtNulldate;
									intCqc = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strCqcdatetime);
									dtCqcdate = new java.sql.Date(cqcutilDate.getTime());
//									dtCqcdate = (Date) formatter.parse(strCqcdatetime);
									timediff = dtCqcdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCqc = (int) diff;
								}
								
								if (strFidatetime == null || strFidatetime.length() == 0) {
									dtFidate = (Date) dtNulldate;
									intFi = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strFidatetime);
									dtFidate = new java.sql.Date(cqcutilDate.getTime());
//									dtFidate = (Date) formatter.parse(strFidatetime);
									timediff = dtFidate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intFi = (int) diff;
								}
								
								if (strTvdatetime == null || strTvdatetime.length() == 0) {
									dtTvdate = (Date) dtNulldate;
									intTv = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strTvdatetime);
									dtTvdate = new java.sql.Date(cqcutilDate.getTime());
//									dtTvdate = (Date) formatter.parse(strTvdatetime);
									timediff = dtTvdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intTv = (int) diff;
								}
								
								if (strCamdatetime == null || strCamdatetime.length() == 0) {
									dtCamdate = (Date) dtNulldate;
									intCam = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strCamdatetime);
									dtCamdate = new java.sql.Date(cqcutilDate.getTime());
//									dtCamdate = (Date) formatter.parse(strCamdatetime);	
									timediff = dtCamdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCam = (int) diff;
								}
								
								if (strCamadatetime == null || strCamadatetime.length() == 0) {
									dtCamadate = dtNulldate;
									intCama = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strCamadatetime);
									dtCamadate = new java.sql.Date(cqcutilDate.getTime());
//									dtCamadate = formatter.parse(strCamadatetime);						
									timediff = dtCamadate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCama = (int) diff;
								}
							}
							
							intCounter++;
							
							//Populate excel rows
				            /*number = new Number(0, intCounter, intCounter);  Guarantors are not numbered
				            excelSheet.addCell(number);		*/
							
							label = new Label(1, intCounter, strGuarantype);
				            excelSheet.addCell(label);

				            label = new Label(2, intCounter, strGuaranid);
				            excelSheet.addCell(label);
				            
				            label = new Label(3, intCounter, strSurname);
				            excelSheet.addCell(label);
				            
				            label = new Label(4, intCounter, strFirstname);
				            excelSheet.addCell(label);
				            
				            if (dtCapturedate != dtNulldate) {
					            DateTime datet = new DateTime(5, intCounter, dtCapturedate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
				            }
				            
				            if (dtCqcdate != dtNulldate) {
				            	DateTime datet = new DateTime(6, intCounter, dtCqcdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(7, intCounter, intCqc);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtFidate != dtNulldate) {				            
				            	DateTime datet = new DateTime(8, intCounter, dtFidate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(9, intCounter, intFi);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtTvdate != dtNulldate) {		
				            	DateTime datet = new DateTime(10, intCounter, dtTvdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(11, intCounter, intTv);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtCamdate != dtNulldate) {	
				            	DateTime datet = new DateTime(12, intCounter, dtCamdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(13, intCounter, intCam);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtCamadate != dtNulldate) {	
				            	DateTime datet = new DateTime(14, intCounter, dtCamadate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(15, intCounter, intCama);
					            excelSheet.addCell(number);
				            }
						}
						
						//Proposals & Loans
//						strQuery = "From Proposal where customerid = '" + strCustomerid + "'";							
						List<Proposal> proposal = propRepo.findBycustomerid(strCustomerid);											
						for (Proposal prop:proposal) {
							strProposalno = prop.getProposalno();
							strCapturedatetime = prop.getCapturedatetime();
							strCqcdatetime = prop.getCqcdatetime();
							strTvdatetime = prop.getTvdatetime();
							strFidatetime = prop.getFidatetime();
							strCamdatetime = prop.getCamdatetime();
							strCamadatetime = prop.getCamadatetime();
							
							//Populate excel rows
							if (strCapturedatetime == null || strCapturedatetime.length() == 0) {
								dtCapturedate = (Date) dtNulldate;
							} else {
								java.util.Date utilDate = sdformatter.parse(strCapturedatetime);
								dtCapturedate = new java.sql.Date(utilDate.getTime());
//								dtCapturedate = (Date) formatter.parse(strCapturedatetime);
								
								if (strCqcdatetime == null || strCqcdatetime.length() == 0) {
									dtCqcdate = (Date) dtNulldate;
									intCqc = 0;
								} else {
									java.util.Date cqcutilDate = sdformatter.parse(strCqcdatetime);
									dtCqcdate = new java.sql.Date(cqcutilDate.getTime());
//									dtCqcdate = (Date) formatter.parse(strCqcdatetime);
									timediff = dtCqcdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCqc = (int) diff;
								}
								
								if (strFidatetime == null || strFidatetime.length() == 0) {
									dtFidate = dtNulldate;
									intFi = 0;
								} else {
									java.util.Date fiutilDate = sdformatter.parse(strFidatetime);
									dtFidate = new java.sql.Date(fiutilDate.getTime());
//									dtFidate = (Date) formatter.parse(strFidatetime);
									timediff = dtFidate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intFi = (int) diff;
								}
								
								if (strTvdatetime == null || strTvdatetime.length() == 0) {
									dtTvdate = dtNulldate;
									intTv = 0;
								} else {
									java.util.Date tvutilDate = sdformatter.parse(strTvdatetime);
									dtTvdate = new java.sql.Date(tvutilDate.getTime());
//									dtTvdate = (Date) formatter.parse(strTvdatetime);
									timediff = dtTvdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intTv = (int) diff;
								}
								
								if (strCamdatetime == null || strCamdatetime.length() == 0) {
									dtCamdate = dtNulldate;
									intCam = 0;
								} else {
									java.util.Date tvutilDate = sdformatter.parse(strCamdatetime);
									dtCamdate = new java.sql.Date(tvutilDate.getTime());
//									dtCamdate = (Date) formatter.parse(strCamdatetime);	
									timediff = dtCamdate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCam = (int) diff;
								}
								
								if (strCamadatetime == null || strCamadatetime.length() == 0) {
									dtCamadate = dtNulldate;
									intCama = 0;
								} else {
									java.util.Date tvutilDate = sdformatter.parse(strCamadatetime);
									dtCamadate = new java.sql.Date(tvutilDate.getTime());
//									dtCamadate = formatter.parse(strCamadatetime);						
									timediff = dtCamadate.getTime() - dtCapturedate.getTime();
									diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
									intCama = (int) diff;
								}
							}
																							
							intCounter++;
							
							//Populate excel rows
				            /*number = new Number(0, intCounter, intCounter);  Guarantors are not numbered
				            excelSheet.addCell(number);		*/		            

							label = new Label(1, intCounter, "Proposal");
				            excelSheet.addCell(label);
							
				            label = new Label(2, intCounter, strProposalno);
				            excelSheet.addCell(label);
				            
				            /*label = new Label(3, intCounter, strSurname);
				            excelSheet.addCell(label);
				            
				            label = new Label(4, intCounter, strFirstname);
				            excelSheet.addCell(label);*/
				            if (dtCqcdate != dtNulldate) {
					            DateTime datet = new DateTime(5, intCounter, dtCapturedate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
				            }
				            
				            if (dtCqcdate != dtNulldate) {
				            	DateTime datet = new DateTime(6, intCounter, dtCqcdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(7, intCounter, intCqc);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtFidate != dtNulldate) {				            
				            	DateTime datet = new DateTime(8, intCounter, dtFidate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(9, intCounter, intFi);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtTvdate != dtNulldate) {		
				            	DateTime datet = new DateTime(10, intCounter, dtTvdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(11, intCounter, intTv);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtCamdate != dtNulldate) {	
				            	DateTime datet = new DateTime(12, intCounter, dtCamdate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(13, intCounter, intCam);
					            excelSheet.addCell(number);
				            }
				            
				            if (dtCamadate != dtNulldate) {	
				            	DateTime datet = new DateTime(14, intCounter, dtCamadate, DATE_CELL_FRMT);
					            excelSheet.addCell(datet);
					            				            
					            number = new Number(15, intCounter, intCama);
					            excelSheet.addCell(number);
				            }
				            
				            //**************************Loans**************************
//				            strQuery = "From Loan where proposalno = '" + strProposalno + "'";							
							List<Loan> loan = loanRepo.findByproposalno(strProposalno);											
							for (Loan ln:loan) {
								strCapturedatetime = ln.getCapturedatetime();
								strCqcdatetime = ln.getCqcdatetime();
								strTvdatetime = ln.getTvdatetime();
								strFidatetime = ln.getFidatetime();
								strCamdatetime = ln.getCamdatetime();
								strCamadatetime = ln.getCamadatetime();
								
								//Populate excel rows									
								if (strCapturedatetime == null || strCapturedatetime.length() == 0) {
									dtCapturedate = dtNulldate;
								} else {
									java.util.Date utilDate = sdformatter.parse(strCapturedatetime);
									dtCapturedate = new java.sql.Date(utilDate.getTime());
//									dtCapturedate = (Date) formatter.parse(strCapturedatetime);
									
									if (strCqcdatetime == null || strCqcdatetime.length() == 0) {
										dtCqcdate = dtNulldate;
										intCqc = 0;
									} else {
										java.util.Date cqcutilDate = sdformatter.parse(strCqcdatetime);
										dtCqcdate = new java.sql.Date(cqcutilDate.getTime());
//										dtCqcdate = (Date) formatter.parse(strCqcdatetime);
										timediff = dtCqcdate.getTime() - dtCapturedate.getTime();
										diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
										intCqc = (int) diff;
									}
									
									if (strFidatetime == null || strFidatetime.length() == 0) {
										dtFidate = dtNulldate;
										intFi = 0;
									} else {
										java.util.Date cqcutilDate = sdformatter.parse(strFidatetime);
										dtFidate = new java.sql.Date(cqcutilDate.getTime());
//										dtFidate = (Date) formatter.parse(strFidatetime);
										timediff = dtFidate.getTime() - dtCapturedate.getTime();
										diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
										intFi = (int) diff;
									}
									
									if (strTvdatetime == null || strTvdatetime.length() == 0) {
										dtTvdate = dtNulldate;
										intTv = 0;
									} else {
										java.util.Date cqcutilDate = sdformatter.parse(strTvdatetime);
										dtTvdate = new java.sql.Date(cqcutilDate.getTime());
//										dtTvdate = (Date) formatter.parse(strTvdatetime);
										timediff = dtTvdate.getTime() - dtCapturedate.getTime();
										diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
										intTv = (int) diff;
									}
									
									if (strCamdatetime == null || strCamdatetime.length() == 0) {
										dtCamdate = dtNulldate;
										intCam = 0;
									} else {
										java.util.Date cqcutilDate = sdformatter.parse(strCamdatetime);
										dtCamdate = new java.sql.Date(cqcutilDate.getTime());
//										dtCamdate = (Date) formatter.parse(strCamdatetime);	
										timediff = dtCamdate.getTime() - dtCapturedate.getTime();
										diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
										intCam = (int) diff;
									}
									
									if (strCamadatetime == null || strCamadatetime.length() == 0) {
										dtCamadate = dtNulldate;
										intCama = 0;
									} else {
										java.util.Date cqcutilDate = sdformatter.parse(strCamadatetime);
										dtCamadate = new java.sql.Date(cqcutilDate.getTime());
//										dtCamadate = formatter.parse(strCamadatetime);						
										timediff = dtCamadate.getTime() - dtCapturedate.getTime();
										diff = TimeUnit.DAYS.convert(timediff, TimeUnit.MILLISECONDS);
										intCama = (int) diff;
									}
								}									
																	
								intCounter++;
								
								//Populate excel rows
					            /*number = new Number(0, intCounter, intCounter);  Guarantors are not numbered
					            excelSheet.addCell(number);		*/	
								
								label = new Label(1, intCounter, "Loan");
					            excelSheet.addCell(label);

					            label = new Label(2, intCounter, strProposalno);
					            excelSheet.addCell(label);
					            
					            /*label = new Label(3, intCounter, strSurname);
					            excelSheet.addCell(label);
					            
					            label = new Label(4, intCounter, strFirstname);
					            excelSheet.addCell(label);*/
					            
					            if (dtCapturedate != dtNulldate) {
						            DateTime datet = new DateTime(5, intCounter, dtCapturedate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
					            }
					            
					            if (dtCqcdate != dtNulldate) {
					            	DateTime datet = new DateTime(6, intCounter, dtCqcdate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
						            				            
						            number = new Number(7, intCounter, intCqc);
						            excelSheet.addCell(number);
					            }
					            
					            if (dtFidate != dtNulldate) {				            
					            	DateTime datet = new DateTime(8, intCounter, dtFidate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
						            				            
						            number = new Number(9, intCounter, intFi);
						            excelSheet.addCell(number);
					            }
					            
					            if (dtTvdate != dtNulldate) {		
					            	DateTime datet = new DateTime(10, intCounter, dtTvdate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
						            				            
						            number = new Number(11, intCounter, intTv);
						            excelSheet.addCell(number);
					            }
					            
					            if (dtCamdate != dtNulldate) {	
					            	DateTime datet = new DateTime(12, intCounter, dtCamdate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
						            				            
						            number = new Number(13, intCounter, intCam);
						            excelSheet.addCell(number);
					            }
					            
					            if (dtCamadate != dtNulldate) {	
					            	DateTime datet = new DateTime(14, intCounter, dtCamadate, DATE_CELL_FRMT);
						            excelSheet.addCell(datet);
						            				            
						            number = new Number(15, intCounter, intCama);
						            excelSheet.addCell(number);
					            }
							}
				            //*****************Loans***********************
						}
						//================= End of Proposals & Loans ============================
					}
					
				} catch (Exception e) {e.printStackTrace();}
									
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
	            String keyName = "filename.xls"; 
	            
	      
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
	}

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
		
		java.util.Date dtNulldate = null;
		java.util.Date dtPtpdate = null;
					
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
            
            buckets bucks = new buckets();
            
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
						
						TotalDue td = new TotalDue();
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
						
						TotalDue td = new TotalDue();
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
						
						TotalDue td = new TotalDue();
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
	}

	public ResponseEntity<String> generateReporpt(String datetime) {
		String strDate = "";
		String strQuery = "";
		String strQuery1 = "";
		String strFilename = "";
		
		String strCustomer = "";
		String strProposalno = "";
		String strAgreement = "";
		String strVehregno = "";
		String strImpdate = "";
		String strInstdue = "";
		String strInstallment = "";
		String strPeninst = "";
		String strPenalty = "";
		String strAmounttoclear = "";
		String strAmountcleared = "";
		String strFutureinst = "";
		String strFutureprincipal = "";
		String strFutureinterest = "";
		String strTotalfuturedue = "";
		String strImpcharges = "";
		String strStatus = "";
		String strReposessuser = "";
		String strReposessdate = "";
		String strNewproposalid = "";			
		
		String strCustomername = "";
		
		double dblInstdue = 0;
		double dblInstallment = 0;
		double dblPeninst = 0;
		double dblPenalty = 0;
		double dblAmounttoclear = 0;
		double dblFutureinst = 0;
		double dblFutureprincipal = 0;
		double dblFutureinterest = 0;
		double dblTotalfuturedue = 0;
		double dblImpcharges = 0;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
								
		int i = 0;
		int intCounter = 0;
		
		java.util.Date dtNulldate = null;
		java.util.Date dtImpdate = null;
		java.util.Date dtRepodate = null;
					
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
//			
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "reposessionrpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Reposession", 0);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            
            formatDigits fd = new formatDigits();
            
            try {
				dtNulldate = formatter.parse("01/01/1900");
			} catch (ParseException e1) {System.out.println(e1.getLocalizedMessage());}
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Customer Id ");
	            excelSheet.addCell(label);
	            		            
	            label = new Label(2, 0, "Customer Name ");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Proposal No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Agreement No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Vehicle Reg No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Impound Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "Installments Due ");
	            excelSheet.addCell(label);
	            
	            label = new Label(8, 0, "Installment Amount ");
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "Penalties Due ");
	            excelSheet.addCell(label);
	            
	            label = new Label(10, 0, "Penalty Amount ");
	            excelSheet.addCell(label);
	            
	            label = new Label(11, 0, "Total Amount To Clear ");
	            excelSheet.addCell(label);
	            
	            label = new Label(12, 0, "Future Installments ");
	            excelSheet.addCell(label);
	            
	            label = new Label(13, 0, "Future Principal ");
	            excelSheet.addCell(label);
	            
	            label = new Label(14, 0, "Future Interest ");
	            excelSheet.addCell(label);
	            		            
	            label = new Label(15, 0, "Future Due ");
	            excelSheet.addCell(label);
	            
	            label = new Label(16, 0, "Impound Charges ");
	            excelSheet.addCell(label);
	            
	            label = new Label(17, 0, "User Id ");
	            excelSheet.addCell(label);
	            
	            label = new Label(18, 0, "Reposess Date ");
	            excelSheet.addCell(label);
	            
	            label = new Label(19, 0, "New Proposal No ");
	            excelSheet.addCell(label);
	            
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
	            //Load reposession to excel file
				try {
//					strQuery = "From Reposession"; // where otherid = '" + strCustomer + "'";
					List<Reposession> repodet = reposessRepo.findAll();
					
					for (Reposession rp:repodet) {
						strCustomer = rp.getCustomerid();
						strProposalno = rp.getProposalno();
						strAgreement = rp.getAgreementno();
						strVehregno = rp.getVehicleregno();
						strImpdate = rp.getImpounddate();
						strInstdue = rp.getInstdue();
						strInstallment = rp.getInstallment();
						strPeninst = rp.getPenalinst();
						strPenalty = rp.getPenalty();
						strAmounttoclear = rp.getAmounttoclear();
						strAmountcleared = rp.getAmountcleared();
						strFutureinst = rp.getFutureinst();
						strFutureprincipal = rp.getFutureprincipal();
						strFutureinterest = rp.getFutureinterest();
						strTotalfuturedue = rp.getTotalfuturedue();
						strImpcharges = rp.getImpcharges();
						strStatus = rp.getStatus();
						strReposessuser = rp.getReposessuser();
						strReposessdate = rp.getReposessdatetime();
						strNewproposalid = rp.getNewproposalid();		
						
						//get customer full name
//						strQuery1 = "From CustomerDetails where otherid = '" + strCustomer + "'";
						CustomerDetails cd = custRepo.findByotherid(strCustomer);
						
							strCustomername = cd.getSurname() + " " + cd.getFirstname();
						
						
						if (strImpdate == null || strImpdate.length() == 0) {
							dtImpdate = dtNulldate;
						} else {
							dtImpdate = formatter.parse(strImpdate);
						}
						
						if (strReposessdate == null || strReposessdate.length() == 0) {
							dtRepodate = dtNulldate;
						} else {
							dtRepodate = formatter.parse(strReposessdate);
						}
						
						//Convert all amounts to double
						if (strInstdue == null) {
							dblInstdue = 0;
						} else {
							if (strInstdue.length() > 0) {
								strInstdue = strInstdue.replace(",", "");
								dblInstdue = Double.parseDouble(strInstdue);
							} else {dblInstdue = 0;	}
						}
						
						if (strInstallment == null) {
							dblInstallment = 0;
						} else {
							if (strInstallment.length() > 0) {
								strInstallment = strInstallment.replace(",", "");
								dblInstallment = Double.parseDouble(strInstallment);
							} else {dblInstallment = 0;	}
						}
						
						if (strPeninst == null) {
							dblPeninst = 0;
						} else {
							if (strPeninst.length() > 0) {
								strPeninst = strPeninst.replace(",", "");
								dblPeninst = Double.parseDouble(strPeninst);
							} else {dblPeninst = 0;	}
						}
						
						if (strPenalty == null) {
							dblPenalty = 0;
						} else {
							if (strPenalty.length() > 0) {
								strPenalty = strPenalty.replace(",", "");
								dblPenalty = Double.parseDouble(strPenalty);
							} else {dblPenalty = 0;	}
						}
						
						if (strAmounttoclear == null) {
							dblAmounttoclear = 0;
						} else {
							if (strAmounttoclear.length() > 0) {
								strAmounttoclear = strAmounttoclear.replace(",", "");
								dblAmounttoclear = Double.parseDouble(strAmounttoclear);
							} else {dblAmounttoclear = 0;	}
						}
						
						if (strFutureinst == null) {
							dblFutureinst = 0;
						} else {
							if (strFutureinst.length() > 0) {
								strFutureinst = strFutureinst.replace(",", "");
								dblFutureinst = Double.parseDouble(strFutureinst);
							} else {dblFutureinst = 0;	}
						}
						
						if (strFutureprincipal == null) {
							dblFutureprincipal = 0;
						} else {
							if (strFutureprincipal.length() > 0) {
								strFutureprincipal = strFutureprincipal.replace(",", "");
								dblFutureprincipal = Double.parseDouble(strFutureprincipal);
							} else {dblFutureprincipal = 0;	}
						}
						
						if (strFutureinterest == null) {
							dblFutureinterest = 0;
						} else {
							if (strFutureinterest.length() > 0) {
								strFutureinterest = strFutureinterest.replace(",", "");
								dblFutureinterest = Double.parseDouble(strFutureinterest);
							} else {dblFutureinterest = 0;	}
						}
						
						if (strTotalfuturedue == null) {
							dblTotalfuturedue = 0;
						} else {
							if (strTotalfuturedue.length() > 0) {
								strTotalfuturedue = strTotalfuturedue.replace(",", "");
								dblTotalfuturedue = Double.parseDouble(strTotalfuturedue);
							} else {dblTotalfuturedue = 0;	}
						}
						
						if (strImpcharges == null) {
							dblImpcharges = 0;
						} else {
							if (strImpcharges.length() > 0) {
								strImpcharges = strImpcharges.replace(",", "");
								dblImpcharges = Double.parseDouble(strImpcharges);
							} else {dblImpcharges = 0;	}
						}
													
						intCounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);				            

			            label = new Label(1, intCounter, strCustomer);
			            excelSheet.addCell(label);
			            
			            label = new Label(2, intCounter, strCustomername);
			            excelSheet.addCell(label);
			            
			            label = new Label(3, intCounter, strProposalno);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strAgreement);
			            excelSheet.addCell(label);				            
			            
			            label = new Label(5, intCounter, strVehregno);
			            excelSheet.addCell(label);
			            
			            if (dtImpdate != dtNulldate) {
			            	DateTime datet = new DateTime(6, intCounter, dtImpdate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
			            }
			            				            
			            number = new Number(7, intCounter, dblInstdue);
			            excelSheet.addCell(number);
			            
			            number = new Number(8, intCounter, dblInstallment);
			            excelSheet.addCell(number);
			            
			            number = new Number(9, intCounter, dblPeninst);
			            excelSheet.addCell(number);
			            
			            number = new Number(10, intCounter, dblPenalty);
			            excelSheet.addCell(number);
			            
			            number = new Number(11, intCounter, dblAmounttoclear);
			            excelSheet.addCell(number);
			            
			            number = new Number(12, intCounter, dblFutureinst);
			            excelSheet.addCell(number);
			            
			            number = new Number(13, intCounter, dblFutureprincipal);
			            excelSheet.addCell(number);
			            
			            number = new Number(14, intCounter, dblFutureinterest);
			            excelSheet.addCell(number);
			            
			            number = new Number(15, intCounter, dblTotalfuturedue);
			            excelSheet.addCell(number);
			            
			            number = new Number(16, intCounter, dblImpcharges);
			            excelSheet.addCell(number);
			            
			            label = new Label(17, intCounter, strReposessuser);
			            excelSheet.addCell(label);
			            
			            if (dtRepodate != dtNulldate) {
			            	DateTime datet = new DateTime(18, intCounter, dtRepodate, DATE_CELL_FRMT);
				            excelSheet.addCell(datet);
			            }
			            
			            label = new Label(19, intCounter, strNewproposalid);
			            excelSheet.addCell(label);
					}
					
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
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
	            String keyName = "filename.xls"; 
	            
	      
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
	}

	public ResponseEntity<String> generateDp(String datetime, String fromDate, String toDate) {
		
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
		
		String strMsg = "";
					
		String strProposalno = "";
		String strAmount = "";
		String strTransactionref = "";
		String strPaymentmode = "";
		String strPaymentdate = "";
		String strReceiptno = "";
		String strPaydate = "";
		String strFromdate = "";
		String strTodate = "";
		
		String strRevflag = "";
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		File file = null ;
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		
		boolean blnDatevalid = false;
		boolean blnDate1valid = false;
		boolean blnRevflag = false;
		
		int intAmount = 0;			
		int intCounter = 0;
		
		java.util.Date dtPaydate = null;
		
		final WritableCellFormat DATE_CELL_FRMT;
		
		Response<String> response = new Response<>();
		
		strDate = datetime;
		strFromdate = fromDate;
		strTodate = toDate;
							
		fieldValidation fv = new fieldValidation();
		
		if (fv.validateQDate(strFromdate) == "success") {
			blnDatevalid = true;
		} else {blnDatevalid = false;}
		
		if (fv.validateQDate(strTodate) == "success") {
			blnDate1valid = true;
		} else {blnDate1valid = false;}
		
		if (strDate == null || strDate.length() == 0) {
			
		} else if (blnDatevalid == false) {
			strMsg = "Please ensure from date format is yyyy-mm-dd";
		} else if (blnDate1valid == false) {
			strMsg = "Please ensure to date format is yyyy-mm-dd";
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
//			
			strDate = strDate.replace(":", "");
			strDate = strDate.replace(" ", "");	
			strDate = strDate.replace("-", "");
			
			strFilename = "src/main/resources/Documents/" + strDate  + "dprpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Installments", 0);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No."); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Proposal No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Payment Date");
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Transaction Ref ");
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Payment Mode ");
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Amount ");
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Receipt No ");
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "Reversal Flag ");
	            excelSheet.addCell(label);
	            
	            		            		            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
				try {
				
//					strQuery = "From DownPayment " +
//					"WHERE to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') >= '" + strFromdate + "' " +
//					"AND " +
//					"to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') <= '" + strTodate + "' " +
//					"order by receiptid";	
					
					String startPeriod = null;
					String endPeriod = null;
			        
					 DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            // Parse the input date string to a LocalDate object
			            LocalDate fdate = LocalDate.parse(strFromdate, inputFormatter);
			            LocalDate tdate = LocalDate.parse(strTodate, inputFormatter);
			            
			            // Extract month and year
			            int fmonth = fdate.getMonthValue();
			            int fyear = fdate.getYear();
			            
			            int tmonth = tdate.getMonthValue();
			            int tyear = tdate.getYear();
			            
			            int fromday = fdate.getDayOfMonth();
			            int today = tdate.getDayOfMonth();
			            // Format the month and year into the desired output string
			           String startingperiod= String.format("%02d%d", fmonth, fyear);
			           String endingperiod= String.format("%02d%d", tmonth, tyear);
			           String Fromdate= String.format("%02d%02d%d",fromday, tmonth, tyear);
			           String Todate= String.format("%02d%02d%d",today, tmonth, tyear);
			           Fromdate += "000000";
			           Todate += "235959";
			           System.out.println("startingperiod:: "+startingperiod);
			           System.out.println("endingperiod:: "+endingperiod);
			           System.out.println("Fromdate:: "+Fromdate+"000000");
			           System.out.println("Todate:: "+Todate+"000000");
//					strFromdate.substring(, intCounter)
			           
			           
					
					List<DownPayment> downp = downPaymentRepo.findByDateRange(startingperiod,endingperiod,Fromdate, Todate);
			        
			        
//					List<DownPayment> downp = downPaymentRepo.findByDateRange(fromdate, Todate);
									
					//Proposalno, date, ref, mode, amount, receiptno
					for (DownPayment dp:downp) {							
						strProposalno = dp.getProposalno();
						strAmount = dp.getAmount();
						strTransactionref = dp.getTransactionref();
						strPaymentmode = dp.getPaymentmode();							
						strReceiptno = dp.getDownreceipt();
						strPaymentdate = dp.getPaymentdate();
						blnRevflag = dp.getRevflag();
						
						if (blnRevflag == true) {
							strRevflag = "REV";
						} else {
							strRevflag = "";
						}
									
						strPaydate = strPaymentdate.substring(0, 2) + "/" + strPaymentdate.substring(2, 4) + "/" + strPaymentdate.substring(4, 8);
						SimpleDateFormat spformatter = new SimpleDateFormat("dd/MM/yyyy");
						dtPaydate = spformatter.parse(strPaydate);
													
						intAmount = Integer.parseInt(strAmount);							
						intCounter++;
						
						//Populate excel rows
			            Number number = new Number(0, intCounter, intCounter); 
			            excelSheet.addCell(number);
			            
			            label = new Label(1, intCounter, strProposalno);
			            excelSheet.addCell(label);
			            
			            DateTime datet = new DateTime(2, intCounter, dtPaydate, DATE_CELL_FRMT);
			            excelSheet.addCell(datet);
			            
			            label = new Label(3, intCounter, strTransactionref);
			            excelSheet.addCell(label);
			            
			            label = new Label(4, intCounter, strPaymentmode);
			            excelSheet.addCell(label);
			            
			            number = new Number(5, intCounter, intAmount);
			            excelSheet.addCell(number);
			            
			            label = new Label(6, intCounter, strReceiptno);
			            excelSheet.addCell(label);
			            
			            label = new Label(7, intCounter, strRevflag);
			            excelSheet.addCell(label);
			            
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());}					
									
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
		
		if (!strMsg.equals("")) {
			headers.add("X-message", strMsg);	
		}
//		session.setAttribute("dpid", "");
		return ResponseEntity.ok(url.toString());
	}

	public ResponseEntity<String> generateWeeklyinst(String datetime, String fromDate, String toDate) {
		String strDate = "";
		String strQuery = "";
		String strFilename = "";
		
		String strMsg = "";
		
		String strPaymentdate = "";
		String strPaydate = "";
		String strProposalno = "";
		String strTrantype = ""; 
		String strTranrefno = ""; 
		String strAmount = ""; 
		String strPaymentmode = ""; 
		String strReceiptno = "";
		String strVehicle = "";
		String strFromdate = "";
		String strTodate = "";
		
		String strRevflag = "";
		String strAgreement = "";
		
		boolean blnDatevalid = false;
		boolean blnDate1valid = false;
		boolean blnRevflag = false;
		
		int fiftythousand =0;
		int twentythousand =0;
		int tenthousand=0;
		int fivethousand=0;
		int twothousand=0;
		int thousand=0;
		int fivehundred=0;
		int twohundred=0;
		int hundred = 0;
		int coins = 0;
		
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectMetadata metadata = new ObjectMetadata();
	    URL url = null;
		File file = null ;
		
		int intCounter = 0;
		int intAmount = 0;
		int intAgreementserial = 0;
		
		java.util.Date dtPaydate = null;
		
		final WritableCellFormat DATE_CELL_FRMT;
		
		Response<String> response = new Response<>();
		
		strDate = datetime;								
		strFromdate = fromDate;
		strTodate =toDate;
							
		fieldValidation fv = new fieldValidation();
		
		if (fv.validateQDate(strFromdate) == "success") {
			blnDatevalid = true;
		} else {blnDatevalid = false;}
		
		if (fv.validateQDate(strTodate) == "success") {
			blnDate1valid = true;
		} else {blnDate1valid = false;}
		
		if (strDate == null || strDate.length() == 0) {
			
		} else if (blnDatevalid == false) {
			strMsg = "Please ensure from date format is yyyy-mm-dd";
		} else if (blnDate1valid == false) {
			strMsg = "Please ensure to date format is yyyy-mm-dd";
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
			
//			strFilename = "src/main/resources/Documents/" + strDate  + "weeklyinstrpt.xls";
			
			//1. Create an Excel file
	        WritableWorkbook myDatadump = null;		        
	        try {
				myDatadump = Workbook.createWorkbook(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // create an Excel sheet
            WritableSheet excelSheet = myDatadump.createSheet("Installments", 0);
            
         // Create a bold font
            WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

            // Create a cell format with the bold font
            WritableCellFormat boldFormat = new WritableCellFormat(boldFont);
            
            try {
	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Sr No.", boldFormat); //first cell - first col, first row
	            excelSheet.addCell(label);
	            
	            label = new Label(1, 0, "Payment Date ",boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(2, 0, "Proposalno.", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(3, 0, "Agreement No", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(4, 0, "Tranaction type ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(5, 0, "Transaction ref no ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(6, 0, "Amount ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(7, 0, "Payment mode ", boldFormat);
	            excelSheet.addCell(label);
	            		            		            
	            label = new Label(8, 0, "Receiptno ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(9, 0, "Vehicle Reg No ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(10, 0, "Reversal Flag ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(11, 0, "50000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(12, 0, "20000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(13, 0, "10000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(14, 0, "5000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(15, 0, "2000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(16, 0, "1000 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(17, 0, "500 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(18, 0, "200 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(19, 0, "100 ", boldFormat);
	            excelSheet.addCell(label);
	            
	            label = new Label(20, 0, "coins ", boldFormat);
	            excelSheet.addCell(label);
	            
	            DateFormat df = new DateFormat("dd/MM/yyyy");
	            df.getDateFormat().setTimeZone(TimeZone.getTimeZone("GMT"));
	            DATE_CELL_FRMT = new WritableCellFormat(df);
	            
	            int totalFiftyThousand = 0;
	            int totalTwentyThousand = 0;
	            int totalTenThousand = 0;
	            int totalFiveThousand = 0;
	            int totalTwoThousand = 0;
	            int totalThousand = 0;
	            int totalFiveHundred = 0;
	            int totalTwoHundred = 0;
	            int totalHundred = 0;
	            int totalCoins = 0;
	            
				try {
//					strQuery = "From WeeklyInstallment " +
//					"WHERE to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') >= '" + strFromdate + "' " +
//					"AND " +
//					"to_date(substring(paymentdate,1,2) || '/' || substring(paymentdate,3,2) || '/' || substring(paymentdate,5,4), 'dd/MM/yyyy') <= '" + strTodate + "' " +
//					"order by receiptid";
					List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findByPaymentDateBetween(strFromdate, strTodate) ;
									
					for (WeeklyInstallment winst:weeklyinst) {
						
						strProposalno = winst.getProposalno();
						strTrantype = winst.getTransactiontype();
						strTranrefno = winst.getTransactionref();
						strAmount = winst.getAmount();
						strPaymentmode = winst.getPaymentmode();
						strReceiptno = winst.getWeekreceipt();
						strPaymentdate = winst.getPaymentdate();
						blnRevflag = winst.getRevflag();
						
						Collection col = collRepo.findByreceiptid(strReceiptno);
						if(col != null) {
						fiftythousand = Integer.parseInt(col.getFiftythousand());
						twentythousand = Integer.parseInt(col.getTwentythousand());
						tenthousand = Integer.parseInt(col.getTenthousand());
						fivethousand = Integer.parseInt(col.getFivethousand());
						twothousand = Integer.parseInt(col.getTwothousand());
						thousand = Integer.parseInt(col.getThousand());
						fivehundred = Integer.parseInt(col.getFivehundred());
						twohundred = Integer.parseInt(col.getTwohundred());
						hundred = Integer.parseInt(col.getHundred());
						coins = Integer.parseInt(col.getCoins());
						
						 totalFiftyThousand += fiftythousand;
			             totalTwentyThousand += twentythousand;
			                totalTenThousand += tenthousand;
			                totalFiveThousand += fivethousand;
			                totalTwoThousand += twothousand;
			                totalThousand += thousand;
			                totalFiveHundred += fivehundred;
			                totalTwoHundred += twohundred;
			                totalHundred +=hundred;
			                totalCoins += coins;
			                
						}
						if (blnRevflag == true) {
							strRevflag = "REV";
						} else {
							strRevflag = "";
						}
						
						strAgreement = "";
						
//						strQuery = "From CustomerVehicle where proposalno = '" + strProposalno + "'";							
						CustomerVehicle cv = custvehRepo.findByproposalno(strProposalno);											
							strVehicle = cv.getVehicleregno();
							intAgreementserial = cv.getAgreementserial();
							strAgreement = "AN" + intAgreementserial;
													
						strPaydate = strPaymentdate.substring(0, 2) + "/" + strPaymentdate.substring(2, 4) + "/" + strPaymentdate.substring(4, 8);
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						dtPaydate = formatter.parse(strPaydate);
						
						intAmount = Integer.parseInt(strAmount);
						
						intCounter++;						
						
//						int lastRowIndex = intCounter + 1;
						
						//Populate excel rows
						// Populate excel rows
						Number number = new Number(0, intCounter, intCounter); 
						excelSheet.addCell(number);

						DateTime datet = new DateTime(1, intCounter, dtPaydate, DATE_CELL_FRMT);
						excelSheet.addCell(datet);

						label = new Label(2, intCounter, strProposalno);
						excelSheet.addCell(label);

						label = new Label(3, intCounter, strAgreement);
						excelSheet.addCell(label);

						label = new Label(4, intCounter, strTrantype);
						excelSheet.addCell(label);

						label = new Label(5, intCounter, strTranrefno);
						excelSheet.addCell(label);

						number = new Number(6, intCounter, intAmount);
						excelSheet.addCell(number);

						label = new Label(7, intCounter, strPaymentmode);
						excelSheet.addCell(label);

						label = new Label(8, intCounter, strReceiptno);
						excelSheet.addCell(label);

						label = new Label(9, intCounter, strVehicle);
						excelSheet.addCell(label);

						label = new Label(10, intCounter, strRevflag);
						excelSheet.addCell(label);

						number = new Number(11, intCounter, fiftythousand);
						excelSheet.addCell(number);  // This should add the 'number' instead of 'label'

						number = new Number(12, intCounter, twentythousand);
						excelSheet.addCell(number);

						number = new Number(13, intCounter, tenthousand);
						excelSheet.addCell(number);

						number = new Number(14, intCounter, fivethousand);
						excelSheet.addCell(number);

						number = new Number(15, intCounter, twothousand);
						excelSheet.addCell(number);

						number = new Number(16, intCounter, thousand);
						excelSheet.addCell(number);

						number = new Number(17, intCounter, fivehundred);
						excelSheet.addCell(number);

						number = new Number(18, intCounter, twohundred);
						excelSheet.addCell(number);

						number = new Number(19, intCounter, hundred);
						excelSheet.addCell(number);

						number = new Number(20, intCounter, coins);
						excelSheet.addCell(number);  // This should add the 'number' instead of 'label'
					}
						// Add total denomination row
						int lastRowIndex = intCounter + 1;
						label = new Label(10, lastRowIndex, "Total Denomination:", boldFormat);
						excelSheet.addCell(label);

						Number number = new Number(11, lastRowIndex, totalFiftyThousand);
						excelSheet.addCell(number);

						number = new Number(12, lastRowIndex, totalTwentyThousand);
						excelSheet.addCell(number);

						number = new Number(13, lastRowIndex, totalTenThousand);
						excelSheet.addCell(number);

						number = new Number(14, lastRowIndex, totalFiveThousand);
						excelSheet.addCell(number);

						number = new Number(15, lastRowIndex, totalTwoThousand);
						excelSheet.addCell(number);

						number = new Number(16, lastRowIndex, totalThousand);
						excelSheet.addCell(number);

						number = new Number(17, lastRowIndex, totalFiveHundred);
						excelSheet.addCell(number);

						number = new Number(18, lastRowIndex, totalTwoHundred);
						excelSheet.addCell(number);

						number = new Number(19, lastRowIndex, totalHundred);
						excelSheet.addCell(number);

						number = new Number(20, lastRowIndex, totalCoins);
						excelSheet.addCell(number);

						// Calculate total value and cash amount
						int totalValueFiftyThousand = totalFiftyThousand * 50000;
						int totalValueTwentyThousand = totalTwentyThousand * 20000;
						int totalValueTenThousand = totalTenThousand * 10000;
						int totalValueFiveThousand = totalFiveThousand * 5000;
						int totalValueTwoThousand = totalTwoThousand * 2000;
						int totalValueThousand = totalThousand * 1000;
						int totalValueFiveHundred = totalFiveHundred * 500;
						int totalValueTwoHundred = totalTwoHundred * 200;
						int totalValueHundred = totalHundred * 100;
						int totalValueCoins = coins;

						int totalCashAmount = totalValueFiftyThousand + totalValueTwentyThousand + totalValueTenThousand + totalValueFiveThousand +
						                      totalValueTwoThousand + totalValueThousand + totalValueFiveHundred + totalValueTwoHundred + totalValueHundred + totalValueCoins;

						// Add total value row
						int RowIndex = intCounter + 2;
						label = new Label(10, RowIndex, "Total Value:", boldFormat);
						excelSheet.addCell(label);

						number = new Number(11, RowIndex, totalValueFiftyThousand);
						excelSheet.addCell(number);

						number = new Number(12, RowIndex, totalValueTwentyThousand);
						excelSheet.addCell(number);

						number = new Number(13, RowIndex, totalValueTenThousand);
						excelSheet.addCell(number);

						number = new Number(14, RowIndex, totalValueFiveThousand);
						excelSheet.addCell(number);

						number = new Number(15, RowIndex, totalValueTwoThousand);
						excelSheet.addCell(number);

						number = new Number(16, RowIndex, totalValueThousand);
						excelSheet.addCell(number);

						number = new Number(17, RowIndex, totalValueFiveHundred);
						excelSheet.addCell(number);

						number = new Number(18, RowIndex, totalValueTwoHundred);
						excelSheet.addCell(number);

						number = new Number(19, RowIndex, totalValueHundred);
						excelSheet.addCell(number);

						number = new Number(20, RowIndex, totalCoins);
						excelSheet.addCell(number);

						// Add total cash amount row
						int TotalValueRow = intCounter + 4;
						label = new Label(10, TotalValueRow, "Total Cash Amount", boldFormat);
						excelSheet.addCell(label);

						number = new Number(11, TotalValueRow, totalCashAmount);
						excelSheet.addCell(number);

					
				} catch (Exception e) {System.out.println(e.getLocalizedMessage());}					
									
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
		
		if (!strMsg.equals("")) {
			headers.add("X-message", strMsg);
		}
		
		return ResponseEntity.ok(url.toString());
	}

}
