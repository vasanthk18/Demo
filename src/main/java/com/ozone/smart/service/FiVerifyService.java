package com.ozone.smart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ozone.smart.dto.FiVerificationDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.FiVerification;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.entity.RiderDetails;
import com.ozone.smart.entity.TvVerification;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.FiVerifyRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

@Service
public class FiVerifyService {
	
	@Autowired
	private FiVerifyRepo fiVerifyRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private GuarantorRepo guarantorRepo;
	
	@Autowired
	private RiderRepo riderRepo;
	
	@Value("${s3.bucket.name}")
    private String bucketName;
	

	public Response<String> addCustFi(FiVerificationDto fiVerificationDto) {
		Response<String> response = new Response<>();
		
		String strCustid, strRemarks, strFiverdict;
		
		String strMsg = "";
		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnFiverdict = false;
		
		String strFIRequestdatetime = "";
//		String strLoginuser ;
		
		strCustid = fiVerificationDto.getFiid();
//		int Custid = Integer.parseInt(strCustid);
		strFiverdict = fiVerificationDto.getStrfiverdict();
		strRemarks =  fiVerificationDto.getFiremarks();		
		
		String strSurname = fiVerificationDto.getSurname();
		String strSurnameremark = fiVerificationDto.getSurnameremark();
		String strSurnameflag = fiVerificationDto.getSurnameflag(); 
		String strFname = fiVerificationDto.getFirstname();
		String strfnameremark = fiVerificationDto.getFirstnameremark();
		String strfnameflag = fiVerificationDto.getFirstnameflag();
		String stroname = fiVerificationDto.getOthername();
		String stronameremark = fiVerificationDto.getOthernameremark();
		String stronameflag = fiVerificationDto.getOthernameflag();
		String strms = fiVerificationDto.getMaritalstatus();
		String strmsremark = fiVerificationDto.getMaritalstatusremark();
		String strmsflag = fiVerificationDto.getMaritalstatusflag();
		String strsex = fiVerificationDto.getSex();
		String strsexremark = fiVerificationDto.getSexremark();
		String strsexflag = fiVerificationDto.getSexflag();
		String strmobno = fiVerificationDto.getMobileno();
		String strmobnoremark = fiVerificationDto.getMobilenoremark();
		String strmobnoflag = fiVerificationDto.getMobilenoflag();
		String strstgnm = fiVerificationDto.getStage();
		String strstgnmremark = fiVerificationDto.getStageremark();
		String strstgnmflag = fiVerificationDto.getStageflag();
		String strstgchmnname= fiVerificationDto.getStagechmnname();
		String strstgchmnnameremark= fiVerificationDto.getStagechmnnameremarks();
		String strstgchmnnameflag= fiVerificationDto.getStagechmnnameflag();
		String strstgchmnno= fiVerificationDto.getStagechmnno();
		String strstgchmnnoremarks= fiVerificationDto.getStagechmnnoremarks();
		String strstgchmnnoflag= fiVerificationDto.getStagechmnnoflag();
		String strdist = fiVerificationDto.getDistrict();
		String strdistremark = fiVerificationDto.getDistrictremark();
		String strdistflag = fiVerificationDto.getDistrictflag();
		String strlcnm = fiVerificationDto.getLc();
		String strlcnmremark =fiVerificationDto.getLcremark();
		String strlcnmflag = fiVerificationDto.getLcflag();
		String strpar = fiVerificationDto.getParish();
		String strparremark = fiVerificationDto.getParishremark();
		String strparflag = fiVerificationDto.getParishflag();
		String strnid =  fiVerificationDto.getNationalid();
		String strnidremark = fiVerificationDto.getNationalidremark();
		String strnidflag = fiVerificationDto.getNationalidflag();
		String strbregno = fiVerificationDto.getBikeregno();
		String strbregnoremark = fiVerificationDto.getBikeregnoremark();
		String strbregnoflag =  fiVerificationDto.getBikeregnoflag();
		String strbuse =  fiVerificationDto.getBikeuse();
		String strbuseremark = fiVerificationDto.getBikeuseremark();
		String strbuseflag = fiVerificationDto.getBikeuseflag();
		String strdob = fiVerificationDto.getDob();
		String strdobremark =  fiVerificationDto.getDobremark();
		String strdobflag = fiVerificationDto.getDobflag();
		String strcntd = fiVerificationDto.getCounty();
		String strcntremark = fiVerificationDto.getCountyremark();
		String strcntflag = fiVerificationDto.getCountyflag();
		String strscnt = fiVerificationDto.getSubcounty();
		String strscntremark = fiVerificationDto.getSubcountyremark();
		String strscntflag =  fiVerificationDto.getSubcountyflag();
		String strvil = fiVerificationDto.getVillage();
		String strvilremark =  fiVerificationDto.getVillageremark();
		String strvilflag =  fiVerificationDto.getVillageflag();
		String stryrvil =  fiVerificationDto.getYearsinvillage();
		String stryrvilremark = fiVerificationDto.getYearsinvillageremark();
		String stryrvilflag =  fiVerificationDto.getYearsinvillageflag();
		String strnok = fiVerificationDto.getNextofkin();
		String strnokremark = fiVerificationDto.getNextofkinremark();
		String strnokflag =  fiVerificationDto.getNextofkinflag();
		String strnokm =  fiVerificationDto.getNokmobileno();
		String strnokmremark = fiVerificationDto.getNokmobilenoremark();
		String strnokmflag = fiVerificationDto.getNokmobilenoflag();
		String strnokr = fiVerificationDto.getNokrelationship();
		String strnokrremark = fiVerificationDto.getNokrelationshipremark();
		String strnokrflag =  fiVerificationDto.getNokrelationshipflag();
		String strnoka =  fiVerificationDto.getNokagreeing();
		String strnokaremark = fiVerificationDto.getNokagreeingremark();
		String strnokaflag =  fiVerificationDto.getNokagreeingflag();
		String strdl = fiVerificationDto.getDrivingpermit();
		String strdlremark = fiVerificationDto.getDrivingpermitremark();
		String strdlflag = fiVerificationDto.getDrivingpermitflag();
		String strnat =  fiVerificationDto.getNationality();
		String strnatremark =fiVerificationDto.getNationalityremark();
		String strnatflag = fiVerificationDto.getNationalityflag();	
		String strnod =  fiVerificationDto.getNoofdependants();
		String strnodremark = fiVerificationDto.getNoofdependantsremark();
		String strnodflag = fiVerificationDto.getNoofdependantsflag();
		String stror = fiVerificationDto.getOwnhouserented();
		String strorremark =fiVerificationDto.getOwnhouserentedremark();
		String strorflag = fiVerificationDto.getOwnhouserentedflag();
		String strll = fiVerificationDto.getLandlordname();
		String strllremark = fiVerificationDto.getLandlordnameremark();
		String strllflag = fiVerificationDto.getLandlordnameflag();
		String strllmob =  fiVerificationDto.getLandlordmobileno();
		String strllmobremark = fiVerificationDto.getLandlordmobilenoremark();
		String strllmobflag = fiVerificationDto.getLandlordmobilenoflag();
		String strrpm = fiVerificationDto.getRentpm();
		String strrpmremark = fiVerificationDto.getRentpmremark();
		String strrpmflag = fiVerificationDto.getRentpmflag();
		String strois = fiVerificationDto.getOtherincomesource();
		String stroisremark = fiVerificationDto.getOtherincomesourceremark();
		String stroisflag = fiVerificationDto.getOtherincomesourceflag();
		String strdps = fiVerificationDto.getDownpaymentsource();
		String strdpsremark = fiVerificationDto.getDownpaymentsourceremark();
		String strdpsflg = fiVerificationDto.getDownpaymentsourceflag();
		String strpadd = fiVerificationDto.getPermanentaddress();
		String strpaddremark = fiVerificationDto.getPermanentaddressremark();
		String strpaddflag = fiVerificationDto.getPermanentaddressflag();
		String strfath = fiVerificationDto.getFathersname();
		String strfathremark =fiVerificationDto.getFathersnameremark();
		String strfathflag = fiVerificationDto.getFathersnameflag();
		String strmoth = fiVerificationDto.getMothersname();
		String strmothremark =fiVerificationDto.getMothersnameremark();
		String strmothflag = fiVerificationDto.getMothersnameflag();
		String strnps = fiVerificationDto.getNearbypolicestation();
		String strnpsremark = fiVerificationDto.getNearbypolicestationremark();
		String strnpsflag = fiVerificationDto.getNearbypolicestationflag();
		String strlcnmobno =fiVerificationDto.getLcmobileno();
		String strlcnmobnoremark = fiVerificationDto.getLcmobilenoremark();
		String strlcnmobnoflag = fiVerificationDto.getLcmobilenoflag();
		String strcusttype =fiVerificationDto.getCusttype();
		String strcusttyperemark = fiVerificationDto.getCusttyperemark();
		String strcusttypeflag = fiVerificationDto.getCusttypeflag();
		
		System.out.println("strSurnameflag = " + strSurnameflag);
		System.out.println("strfnameflag = " + strfnameflag);
		System.out.println("stronameflag = " + stronameflag);
		System.out.println("strmsflag = " + strmsflag);
		System.out.println("strsexflag = " + strsexflag);
		System.out.println("strmobnoflag = " + strmobnoflag);
		System.out.println("strstgnmflag = " + strstgnmflag);
		System.out.println("strstgchmnnameflag = " + strstgchmnnameflag);
		System.out.println("strstgchmnnoflag = " + strstgchmnnoflag);
		System.out.println("strdistflag = " + strdistflag);
		System.out.println("strlcnmflag = " + strlcnmflag);
		System.out.println("strparflag = " + strparflag);
		System.out.println("strnidflag = " + strnidflag);
		System.out.println("strbregnoflag = " + strbregnoflag);
		System.out.println("strbuseflag = " + strbuseflag);
		System.out.println("strdobflag = " + strdobflag);
		System.out.println("strcntflag = " + strcntflag);
		System.out.println("strscntflag = " + strscntflag);
		System.out.println("strvilflag = " + strvilflag);
		System.out.println("stryrvilflag = " + stryrvilflag);
		System.out.println("strnokflag = " + strnokflag);
		System.out.println("strnokmflag = " + strnokmflag);
		System.out.println("strnokrflag = " + strnokrflag);
		System.out.println("strnokaflag = " + strnokaflag);
		System.out.println("strdlflag = " + strdlflag);
		System.out.println("strnatflag = " + strnatflag);
		System.out.println("strnodflag = " + strnodflag);
		System.out.println("strorflag = " + strorflag);
		System.out.println("strllflag = " + strllflag);
		System.out.println("strllmobflag = " + strllmobflag);
		System.out.println("strrpmflag = " + strrpmflag);
		System.out.println("stroisflag = " + stroisflag);
		System.out.println("strdpsflg = " + strdpsflg);
		System.out.println("strpaddflag = " + strpaddflag);
		System.out.println("strfathflag = " + strfathflag);
		System.out.println("strmothflag = " + strmothflag);
		System.out.println("strnpsflag = " + strnpsflag);
		System.out.println("strlcnmobnoflag = " + strlcnmobnoflag);
		System.out.println("strcusttypeflag = " + strcusttypeflag);

		
		String strllrentfeedback = fiVerificationDto.getLlrentfeedback();
		String strllrentfeedbackremark = fiVerificationDto.getLlrentfeedbackremarks();
		String strllrentfeedbackflag = fiVerificationDto.getLlrentfeedbackflag();

		String strnoyrsinarea = fiVerificationDto.getNoyrsinarea();
		String strnoyrsinarearemark = fiVerificationDto.getNoyrsinarearemarks();
		String strnoyrsinareaflag = fiVerificationDto.getNoyrsinareaflag();

		String strlc1chmnrecfeed = fiVerificationDto.getLc1chmnrecfeed();
		String strlc1chmnrecfeedremark = fiVerificationDto.getLc1chmnrecfeedremarks();
		String strlc1chmnrecfeedflag = fiVerificationDto.getLc1chmnrecfeedflag();

		String strnearlmarkresi = fiVerificationDto.getNearlmarkresi();
		String strnearlmarkresiremark = fiVerificationDto.getNearlmarkresiremarks();
		String strnearlmarkresiflag = fiVerificationDto.getNearlmarkresiflag();

		String stremptype = fiVerificationDto.getEmptype();
		String stremptyremark = fiVerificationDto.getEmptyperemarks();
		String stremptypeflag = fiVerificationDto.getEmptypeflag();

		String strstgorwrkadrssnearlmark = fiVerificationDto.getStgorwrkadrssnearlmark();
		String strstgorwrkadrssnearlmarkremark = fiVerificationDto.getStgorwrkadrssnearlmarkremarks();
		String strstgorwrkadrssnearlmarkflag = fiVerificationDto.getStgorwrkadrssnearlmarkflag();

		String strstgoremprecm = fiVerificationDto.getStgoremprecm();
		String strstgoremprecmremark = fiVerificationDto.getStgoremprecmremarks();
		String strstgoremprecmflag = fiVerificationDto.getStgoremprecmflag();

		String strnoofyrsinstgorbusi = fiVerificationDto.getNoofyrsinstgorbusi();
		String strnoofyrsinstgorbusiremark = fiVerificationDto.getNoofyrsinstgorbusiremarks();
		String strnoofyrsinstgorbuisflag = fiVerificationDto.getNoofyrsinstgorbuisflag();

		String strstgnoofvehi = fiVerificationDto.getStgnoofvehi();
		String strstgnoofvehiremark = fiVerificationDto.getStgnoofvehiremarks();
		String strstgnoofvehiflag = fiVerificationDto.getStgnoofvehiflag();

		String strbikeowner = fiVerificationDto.getOwnerofbike();
		String strbikeownerremark = fiVerificationDto.getOwnerofbikeremarks();
		String strbikeownerflag = fiVerificationDto.getOwnerofbikeflag();

		String strnetincome = fiVerificationDto.getNetincome();
		String strnetincomeremark = fiVerificationDto.getNetincomeremarks();
		String strnetincomeflag = fiVerificationDto.getNetincomeflag();

		String strbikeusearea = fiVerificationDto.getBikeusearea();
		String strbikeusearearemark = fiVerificationDto.getBikeusearearemarks();
		String strbikeuseareaflag = fiVerificationDto.getBikeuseareaflag();

		String strspousename = fiVerificationDto.getSpousename();
		String strspousenameremark = fiVerificationDto.getSpousenameremarks();
		String strspousenameflag = fiVerificationDto.getSpousenameflag();

		String strspouseno = fiVerificationDto.getSpouseno();
		String strspousenoremark = fiVerificationDto.getSpousenoremarks();
		String strspousenoflag = fiVerificationDto.getSpousenoflag();

		String strspouseconfirm = fiVerificationDto.getSpouseconfirm();
		String strspouseconfirmremark = fiVerificationDto.getSpouseconfirmremarks();
		String strspouseconfirmflag = fiVerificationDto.getSpouseconfirmflag();

		String stroffcdistance = fiVerificationDto.getOffcdistance();
		String stroffcdistanceremark = fiVerificationDto.getOffcdistanceremarks();
		String stroffcdistanceflag = fiVerificationDto.getOffcdistanceflag();

		String strrelawithapplicant = fiVerificationDto.getRelawithapplicant();
		String strrelawithapplicantremark = fiVerificationDto.getRelawithapplicantremarks();
		String strrelawithapplicantflag = fiVerificationDto.getRelawithapplicantflag();

		String strpaymentbyrider = fiVerificationDto.getPaymentbyrider();
		String strpaymentbyriderremark = fiVerificationDto.getPaymentbyriderremarks();
		String strpaymentbyriderflag = fiVerificationDto.getPaymentbyriderflag();

		String stryakanum = fiVerificationDto.getYakanum();
		String stryakanumremark = fiVerificationDto.getYakanumremarks();
		String stryakanumflag = fiVerificationDto.getYakanumflag();

		String stryakanumname = fiVerificationDto.getYakanumname();
		String stryakanumnameremark = fiVerificationDto.getYakanumnameremarks();
		String stryakanumnamflag = fiVerificationDto.getYakanumnamflag();

		String strpaymtdetailstovby = fiVerificationDto.getPaymtdetailstovby();
		String strpaymtdetailstovbyremark = fiVerificationDto.getPaymtdetailstovbyremarks();
		String strpaymtdetailstovbyflag = fiVerificationDto.getPaymtdetailstovbyflag();

		String strcashpaymntworeceipt = fiVerificationDto.getCashpaymntworeceipt();
		String strcashpaymntworeceiptremark = fiVerificationDto.getCashpaymntworeceiptremarks();
		String strcashpaymntworeceiptflag = fiVerificationDto.getCashpaymntworeceiptflag();

		String strapplicantknowvby = fiVerificationDto.getApplicantknowvby();
		String strapplicantknowvbyremark = fiVerificationDto.getApplicantknowvbyremarks();
		String strapplicantknowvbyflag = fiVerificationDto.getApplicantknowvbyflag();

		String strrelawithguarantors = fiVerificationDto.getRelawithguarantors();
		String strrelawithguarantorsremark = fiVerificationDto.getRelawithguarantorsremarks();
		String strrelawithguarantorsflag = fiVerificationDto.getRelawithguarantorsflag();

		String strbikeapplied = fiVerificationDto.getBikeapplied();
		String strbikeappliedremarks = fiVerificationDto.getBikeappliedremarks();
		String strbikeappliedflag = fiVerificationDto.getBikeappliedflag();

		String strdownpayment = fiVerificationDto.getDownpayment();
		String strdownpaymentremarks = fiVerificationDto.getDownpaymentremarks();
		String strdownpaymentflag = fiVerificationDto.getDownpaymentflag();

		String strtenure = fiVerificationDto.getTenure();
		String strtenureremarks = fiVerificationDto.getTenureremarks();
		String strtenureflag = fiVerificationDto.getTenureflag();

		String strewioremi = fiVerificationDto.getEwioremi();
		String strewioremiremarks = fiVerificationDto.getEwioremiremarks();
		String strewioremiflag = fiVerificationDto.getEwioremiflag();
		

		String strarrangebtwnrider = fiVerificationDto.getArrangebtwnrider();
		String strarrangebtwnriderRemarks = fiVerificationDto.getArrangebtwnriderremarks();
		String strarrangebtwnriderchkbx = fiVerificationDto.getArrangebtwnriderflag();
		
		String strresiadrss = fiVerificationDto.getResiadrss();
		String strresiadrssRemarks = fiVerificationDto.getResiadrssremarks();
		String strresiadrsschkbx = fiVerificationDto.getResiadrssflag();
		
		String strnoofyrsinaddrs = fiVerificationDto.getNoofyrinaddrss();
		String strnoofyrsinaddrsRemarks = fiVerificationDto.getNoofyrinaddrssremarks();
		String strnoofyrsinaddrschkbx = fiVerificationDto.getNoofyrinaddrssflag();
		
		String strmbnumnotinname = fiVerificationDto.getMbnonotinname();
		String strmbnumnotinnameRemarks = fiVerificationDto.getMbnonotinnameremarks();
		String strmbnumnotinnamechkbx = fiVerificationDto.getMbnonotinnameflag();
		
		String strfiid = fiVerificationDto.getCfiid();
		String strfiid2 = fiVerificationDto.getCfiid2();
	
		 String strLoginuser = fiVerificationDto.getUsername();
		
		if (strCustid == null || strCustid.length() == 0) {
			
			strMsg =  "Please select customer id";
		
		} else {
			
			if (strFiverdict.equals(strPosverdict)) {
				blnFiverdict = true;
			} else if (strFiverdict.equals(strNegverdict)) {
				blnFiverdict = false;
			}
			
			TimeStampUtil gts = new TimeStampUtil();		
			strFIRequestdatetime = gts.TimeStamp();
			
			if (strfiid.length() > 0 && strfiid2.length() > 0) {
				Optional<CustomerDetails> opCd = Optional.of(customerRepo.findByotherid(strCustid));
				if(opCd.isPresent()) {
					CustomerDetails cd = opCd.get();
					cd.setFiremarks(strRemarks);
					cd.setFiverified(blnFiverdict);
					cd.setFiuser(strLoginuser);
					cd.setFidatetime(strFIRequestdatetime);
					customerRepo.save(cd);
				}
//				strQuery = "UPDATE CustomerDetails set fiverified = " + blnTvverdict + ", firemarks = " + "'" + strRemarks + "', fiuser = " + "'" + strLoginuser + "', fidatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			} else {
				blnFiverdict = false;
				Optional<CustomerDetails> opCd = Optional.of(customerRepo.findByotherid(strCustid));
				if(opCd.isPresent()) {
					CustomerDetails cd = opCd.get();
//					CustomerDetails cd = opCd.get();
					cd.setFiremarks(strRemarks);
					cd.setFiverified(blnFiverdict);
					cd.setFiuser(strLoginuser);
					cd.setFidatetime(strFIRequestdatetime);
					customerRepo.save(cd);
				}
//				strQuery = "UPDATE CustomerDetails set fiverified = " + blnTvverdict + ", firemarks = " + "'" + strRemarks + "', fiuser = " + "'" + strLoginuser + "', fidatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			}
			
			FiVerification fiverify = new FiVerification();
			fiverify.setFiid(strCustid);			
			fiverify.setSurname(strSurname);
			fiverify.setSurnameremark(strSurnameremark); 
			fiverify.setSurnameflag(strSurnameflag);
			fiverify.setFirstname(strFname);
			fiverify.setFirstnameremark(strfnameremark);
			fiverify.setFirstnameflag(strfnameflag);
			fiverify.setOthername(stroname);
			fiverify.setOthernameremark(stronameremark);
			fiverify.setOthernameflag(stronameflag);
			fiverify.setMaritalstatus(strms);
			fiverify.setMaritalstatusremark(strmsremark);
			fiverify.setMaritalstatusflag(strmsflag);
			fiverify.setSex(strsex);
			fiverify.setSexremark(strsexremark);
			fiverify.setSexflag(strsexflag);
			fiverify.setMobileno(strmobno);
			fiverify.setMobilenoremark(strmobnoremark);	
			fiverify.setMobilenoflag(strmobnoflag);
			fiverify.setStage(strstgnm);
			fiverify.setStageremark(strstgnmremark);
			fiverify.setStageflag(strstgnmflag);
			fiverify.setStagechmnname(strstgchmnname);
			fiverify.setStagechmnnameremaks(strstgchmnnameremark);
			fiverify.setStagechmnnameflag(strstgchmnnameflag);
			fiverify.setStagechmnno(strstgchmnno);
			fiverify.setStagechmnnoremarks(strstgchmnnoremarks);
			fiverify.setStagechmnnoflag(strstgchmnnoflag);
			fiverify.setDistrict(strdist);
			fiverify.setDistrictremark(strdistremark);
			fiverify.setDistrictflag(strdistflag);
			fiverify.setLc(strlcnm);
			fiverify.setLcremark(strlcnmremark);
			fiverify.setLcflag(strlcnmflag);
			fiverify.setParish(strpar);
			fiverify.setParishremark(strparremark);
			fiverify.setParishflag(strparflag);
			fiverify.setNationalid(strnid);
			fiverify.setNationalidremark(strnidremark);
			fiverify.setNationalidflag(strnidflag);
			fiverify.setBikeregno(strbregno);
			fiverify.setBikeregnoremark(strbregnoremark);
			fiverify.setBikeregnoflag(strbregnoflag);
			fiverify.setBikeuse(strbuse);
			fiverify.setBikeuseremark(strbuseremark);
			fiverify.setBikeuseflag(strbuseflag);
			fiverify.setDob(strdob);
			fiverify.setDobremark(strdobremark);
			fiverify.setDobflag(strdobflag);
			fiverify.setCounty(strcntd);
			fiverify.setCountyremark(strcntremark);
			fiverify.setCountyflag(strcntflag);
			fiverify.setSubcounty(strscnt);
			fiverify.setSubcountyremark(strscntremark);
			fiverify.setSubcountyflag(strscntflag);
			fiverify.setVillage(strvil);
			fiverify.setVillageremark(strvilremark);
			fiverify.setVillageflag(strvilflag);
			fiverify.setYearsinvillage(stryrvil);
			fiverify.setYearsinvillageremark(stryrvilremark);
			fiverify.setYearsinvillageflag(stryrvilflag);
			fiverify.setNextofkin(strnok);
			fiverify.setNextofkinremark(strnokremark);
			fiverify.setNextofkinflag(strnokflag);
			fiverify.setNokmobileno(strnokm);
			fiverify.setNokmobilenoremark(strnokmremark);
			fiverify.setNokmobilenoflag(strnokmflag);
			fiverify.setNokrelationship(strnokr);
			fiverify.setNokrelationshipremark(strnokrremark);
			fiverify.setNokrelationshipflag(strnokrflag);
			fiverify.setNokagreeing(strnoka);
			fiverify.setNokagreeingremark(strnokaremark);
			fiverify.setNokagreeingflag(strnokaflag);
			fiverify.setDrivingpermit(strdl);
			fiverify.setDrivingpermitremark(strdlremark);
			fiverify.setDrivingpermitflag(strdlflag);
			fiverify.setNationality(strnat);
			fiverify.setNationalityremark(strnatremark);
			fiverify.setNationalityflag(strnatflag);
			fiverify.setNoofdependants(strnod);
			fiverify.setNoofdependantsremark(strnodremark);
			fiverify.setNoofdependantsflag(strnodflag);
			fiverify.setOwnhouserented(stror);
			fiverify.setOwnhouserentedremark(strorremark);
			fiverify.setOwnhouserentedflag(strorflag);
			fiverify.setLandlordname(strll);
			fiverify.setLandlordnameremark(strllremark);
			fiverify.setLandlordnameflag(strllflag);
			fiverify.setLandlordmobileno(strllmob);
			fiverify.setLandlordmobilenoremark(strllmobremark);
			fiverify.setLandlordmobilenoflag(strllmobflag);
			fiverify.setRentpm(strrpm);
			fiverify.setRentpmremark(strrpmremark);
			fiverify.setRentpmflag(strrpmflag);
			fiverify.setOtherincomesource(strois);
			fiverify.setOtherincomesourceremark(stroisremark);
			fiverify.setOtherincomesourceflag(stroisflag);
			fiverify.setDownpaymentsource(strdps);
			fiverify.setDownpaymentsourceremark(strdpsremark);
			fiverify.setDownpaymentsourceflag(strdpsflg);
			fiverify.setPermanentaddress(strpadd);
			fiverify.setPermanentaddressremark(strpaddremark);
			fiverify.setPermanentaddressflag(strpaddflag);
			fiverify.setFathersname(strfath);
			fiverify.setFathersnameremark(strfathremark);
			fiverify.setFathersnameflag(strfathflag);
			fiverify.setMothersname(strmoth);
			fiverify.setMothersnameremark(strmothremark);
			fiverify.setMothersnameflag(strmothflag);
			fiverify.setNearbypolicestation(strnps);
			fiverify.setNearbypolicestationremark(strnpsremark);
			fiverify.setNearbypolicestationflag(strnpsflag);
			fiverify.setLcmobileno(strlcnmobno);
			fiverify.setLcmobilenoremark(strlcnmobnoremark);
			fiverify.setLcmobilenoflag(strlcnmobnoflag);
			fiverify.setCusttype(strcusttype);
			fiverify.setCusttyperemark(strcusttyperemark);
			fiverify.setCusttypeflag(strcusttypeflag);	
			
			fiverify.setLlrentfeedback(strllrentfeedback);
			fiverify.setLlrentfeedbackremarks(strllrentfeedbackremark);
			fiverify.setLlrentfeedbackflag(strllrentfeedbackflag);

			fiverify.setNoyrsinarea(strnoyrsinarea);
			fiverify.setNoyrsinarearemarks(strnoyrsinarearemark);
			fiverify.setNoyrsinareaflag(strnoyrsinareaflag);

			fiverify.setLc1chmnrecfeed(strlc1chmnrecfeed);
			fiverify.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
			fiverify.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);

			fiverify.setNearlmarkresi(strnearlmarkresi);
			fiverify.setNearlmarkresiremarks(strnearlmarkresiremark);
			fiverify.setNearlmarkresiflag(strnearlmarkresiflag);

			fiverify.setEmptype(stremptype);
			fiverify.setEmptyperemarks(stremptyremark);
			fiverify.setEmptypeflag(stremptypeflag);

			fiverify.setStgorwrkadrssnearlmark(strstgorwrkadrssnearlmark);
			fiverify.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
			fiverify.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);

			fiverify.setStgoremprecm(strstgoremprecm);
			fiverify.setStgoremprecmremarks(strstgoremprecmremark);
			fiverify.setStgoremprecmflag(strstgoremprecmflag);

			fiverify.setNoofyrsinstgorbusi(strnoofyrsinstgorbusi);
			fiverify.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
			fiverify.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);

			fiverify.setStgnoofvehi(strstgnoofvehi);
			fiverify.setStgnoofvehiremarks(strstgnoofvehiremark);
			fiverify.setStgnoofvehiflag(strstgnoofvehiflag);

			fiverify.setOwnerofbike(strbikeowner);
			fiverify.setOwnerofbikeremarks(strbikeownerremark);
			fiverify.setOwnerofbikeflag(strbikeownerflag);

			fiverify.setNetincome(strnetincome);
			fiverify.setNetincomeremarks(strnetincomeremark);
			fiverify.setNetincomeflag(strnetincomeflag);

			fiverify.setBikeusearea(strbikeusearea);
			fiverify.setBikeusearearemarks(strbikeusearearemark);
			fiverify.setBikeuseareaflag(strbikeuseareaflag);

			fiverify.setSpousename(strspousename);
			fiverify.setSpousenameremarks(strspousenameremark);
			fiverify.setSpousenameflag(strspousenameflag);

			fiverify.setSpouseno(strspouseno);
			fiverify.setSpousenoremarks(strspousenoremark);
			fiverify.setSpousenoflag(strspousenoflag);

			fiverify.setSpouseconfirm(strspouseconfirm);
			fiverify.setSpouseconfirmremarks(strspouseconfirmremark);
			fiverify.setSpouseconfirmflag(strspouseconfirmflag);

			fiverify.setOffcdistance(stroffcdistance);
			fiverify.setOffcdistanceremarks(stroffcdistanceremark);
			fiverify.setOffcdistanceflag(stroffcdistanceflag);

			fiverify.setRelawithapplicant(strrelawithapplicant);
			fiverify.setRelawithapplicantremarks(strrelawithapplicantremark);
			fiverify.setRelawithapplicantflag(strrelawithapplicantflag);

			fiverify.setPaymentbyrider(strpaymentbyrider);
			fiverify.setPaymentbyriderremarks(strpaymentbyriderremark);
			fiverify.setPaymentbyriderflag(strpaymentbyriderflag);

			fiverify.setYakanum(stryakanum);
			fiverify.setYakanumremarks(stryakanumremark);
			fiverify.setYakanumflag(stryakanumflag);

			fiverify.setYakanumname(stryakanumname);
			fiverify.setYakanumnameremarks(stryakanumnameremark);
			fiverify.setYakanumnamflag(stryakanumnamflag);

			fiverify.setPaymtdetailstovby(strpaymtdetailstovby);
			fiverify.setPaymtdetailstovbyremarks(strpaymtdetailstovbyremark);
			fiverify.setPaymtdetailstovbyflag(strpaymtdetailstovbyflag);

			fiverify.setCashpaymntworeceipt(strcashpaymntworeceipt);
			fiverify.setCashpaymntworeceiptremarks(strcashpaymntworeceiptremark);
			fiverify.setCashpaymntworeceiptflag(strcashpaymntworeceiptflag);

			fiverify.setApplicantknowvby(strapplicantknowvby);
			fiverify.setApplicantknowvbyremarks(strapplicantknowvbyremark);
			fiverify.setApplicantknowvbyflag(strapplicantknowvbyflag);

			fiverify.setRelawithguarantors(strrelawithguarantors);
			fiverify.setRelawithguarantorsremarks(strrelawithguarantorsremark);
			fiverify.setRelawithguarantorsflag(strrelawithguarantorsflag);
			
			fiverify.setBikeapplied(strbikeapplied);
			fiverify.setBikeappliedremarks(strbikeappliedremarks);
			fiverify.setBikeappliedflag(strbikeappliedflag);
			
			fiverify.setDownpayment(strdownpayment);
			fiverify.setDownpaymentremarks(strdownpaymentremarks);
			fiverify.setDownpaymentflag(strdownpaymentflag);
			
			fiverify.setTenure(strtenure);
			fiverify.setTenureremarks(strtenureremarks);
			fiverify.setTenureflag(strtenureflag);
			
			fiverify.setEwioremi(strewioremi);
			fiverify.setEwioremiremarks(strewioremiremarks);
			fiverify.setEwioremiflag(strewioremiflag);
			
			fiverify.setArrangebtwnrider(strarrangebtwnrider);
			fiverify.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
			fiverify.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
			
			fiverify.setResiadrss(strresiadrss);
			fiverify.setResiadrssremarks(strresiadrssRemarks);
			fiverify.setResiadrssflag(strresiadrsschkbx);
			
			fiverify.setNoofyrinaddrss(strnoofyrsinaddrs);
			fiverify.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
			fiverify.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);
					
			fiverify.setMbnonotinname(strmbnumnotinname);
			fiverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			fiverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

			fiverify.setFirstguarantor(false);
			fiverify.setSecondguarantor(false);
			fiverify.setFiremarks(strRemarks);
			fiverify.setFiverdict(blnFiverdict);
			
			
			try {
				fiVerifyRepo.save(fiverify);
				response.setData("FI Verification for : " + strCustid + " (Customer) saved successfully.");				
				
			} catch (Exception  e) {	
				e.printStackTrace();
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		         
				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {
					
					Optional<FiVerification> opfi = fiVerifyRepo.findById(strCustid);
					
					if(opfi.isPresent()) {
						FiVerification fi = opfi.get();
						fi.setSurnameremark(strSurnameremark); 
						fi.setSurnameflag(strSurnameflag);
						fi.setFirstnameremark(strfnameremark);
						fi.setFirstnameflag(strfnameflag);
						fi.setOthernameremark(stronameremark);
						fi.setOthernameflag(stronameflag);
						fi.setMaritalstatusremark(strmsremark);
						fi.setMaritalstatusflag(strmsflag);
						fi.setSexremark(strsexremark);
						fi.setSexflag(strsexflag);
						fi.setMobilenoremark(strmobnoremark);	
						fi.setMobilenoflag(strmobnoflag);
						fi.setStageremark(strstgnmremark);
						fi.setStageflag(strstgnmflag);
						fi.setStagechmnnameremaks(strstgchmnnameremark);
						fi.setStagechmnnameflag(strstgchmnnameflag);
						fi.setStagechmnnoremarks(strstgchmnnoremarks);
						fi.setStagechmnnoflag(strstgchmnnoflag);
						fi.setDistrictremark(strdistremark);
						fi.setDistrictflag(strdistflag);
						fi.setLcremark(strlcnmremark);
						fi.setLcflag(strlcnmflag);
						fi.setParishremark(strparremark);
						fi.setParishflag(strparflag);
						fi.setNationalidremark(strnidremark);
						fi.setNationalidflag(strnidflag);
						fi.setBikeregnoremark(strbregnoremark);
						fi.setBikeregnoflag(strbregnoflag);
						fi.setBikeuseremark(strbuseremark);
						fi.setBikeuseflag(strbuseflag);
						fi.setDobremark(strdobremark);
						fi.setDobflag(strdobflag);
						fi.setCountyremark(strcntremark);
						fi.setCountyflag(strcntflag);
						fi.setSubcountyremark(strscntremark);
						fi.setSubcountyflag(strscntflag);
						fi.setVillageremark(strvilremark);
						fi.setVillageflag(strvilflag);
						fi.setYearsinvillageremark(stryrvilremark);
						fi.setYearsinvillageflag(stryrvilflag);
						fi.setNextofkinremark(strnokremark);
						fi.setNextofkinflag(strnokflag);
						fi.setNokmobilenoremark(strnokmremark);
						fi.setNokmobilenoflag(strnokmflag);
						fi.setNokrelationshipremark(strnokrremark);
						fi.setNokrelationshipflag(strnokrflag);
						fi.setNokagreeingremark(strnokaremark);
						fi.setNokagreeingflag(strnokaflag);
						fi.setDrivingpermitremark(strdlremark);
						fi.setDrivingpermitflag(strdlflag);
						fi.setNationalityremark(strnatremark);
						fi.setNationalityflag(strnatflag);
						fi.setNoofdependantsremark(strnodremark);
						fi.setNoofdependantsflag(strnodflag);
						fi.setOwnhouserentedremark(strorremark);
						fi.setOwnhouserentedflag(strorflag);
						fi.setLandlordnameremark(strllremark);
						fi.setLandlordnameflag(strllflag);
						fi.setLandlordmobilenoremark(strllmobremark);
						fi.setLandlordmobilenoflag(strllmobflag);
						fi.setRentpmremark(strrpmremark);
						fi.setRentpmflag(strrpmflag);
						fi.setOtherincomesourceremark(stroisremark);
						fi.setOtherincomesourceflag(stroisflag);
						fi.setDownpaymentsourceremark(strdpsremark);
						fi.setDownpaymentsourceflag(strdpsflg);
						fi.setPermanentaddressremark(strpaddremark);
						fi.setPermanentaddressflag(strpaddflag);
						fi.setFathersnameremark(strfathremark);
						fi.setFathersnameflag(strfathflag);
						fi.setMothersnameremark(strmothremark);
						fi.setMothersnameflag(strmothflag);
						fi.setNearbypolicestationremark(strnpsremark);
						fi.setNearbypolicestationflag(strnpsflag);
						fi.setLcmobilenoremark(strlcnmobnoremark);
						fi.setLcmobilenoflag(strlcnmobnoflag);
						fi.setCusttyperemark(strcusttyperemark);
						fi.setCusttypeflag(strcusttypeflag);
						
						fi.setLlrentfeedbackremarks(strllrentfeedbackremark);
						fi.setLlrentfeedbackflag(strllrentfeedbackflag);
						fi.setNoyrsinarearemarks(strnoyrsinarearemark);
						fi.setNoyrsinareaflag(strnoyrsinareaflag);
						fi.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
						fi.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);
						fi.setNearlmarkresiremarks(strnearlmarkresiremark);
						fi.setNearlmarkresiflag(strnearlmarkresiflag);
						fi.setEmptype(stremptypeflag);
						fi.setEmptypeflag(stremptypeflag);
						fi.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
						fi.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);
						fi.setStgoremprecmremarks(strstgoremprecmremark);
						fi.setStgoremprecmflag(strstgoremprecmflag);
						fi.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
						fi.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);
						fi.setStgnoofvehiremarks(strstgnoofvehiremark);
						fi.setStgnoofvehiflag(strstgnoofvehiflag);
						fi.setBikeownerremark(strbikeownerremark);
						fi.setBikeownerflag(strbikeownerflag);
						fi.setNetincomeremarks(strnetincomeremark);
						fi.setNetincomeflag(strnetincomeflag);
						fi.setBikeusearearemarks(strbikeusearearemark);
						fi.setBikeuseareaflag(strbikeuseareaflag);
						fi.setSpousenameremarks(strspousenameremark);
						fi.setSpousenameflag(strspousenameflag);
						fi.setSpousenoremarks(strspousenoremark);
						fi.setSpousenoflag(strspousenoflag);
						fi.setSpouseconfirmremarks(strspouseconfirmremark);
						fi.setSpouseconfirmflag(strspouseconfirmflag);
						fi.setOffcdistanceremarks(stroffcdistanceremark);
						fi.setOffcdistanceflag(stroffcdistanceflag);
						fi.setRelawithapplicantremarks(strrelawithapplicantremark);
						fi.setRelawithapplicantflag(strrelawithapplicantflag);
						fi.setPaymentbyriderremarks(strpaymentbyriderremark);
						fi.setPaymentbyriderflag(strpaymentbyriderflag);
						fi.setYakanumremarks(stryakanumremark);
						fi.setYakanumflag(stryakanumflag);
						fi.setYakanumnameremarks(stryakanumnameremark);
						fi.setYakanumnamflag(stryakanumnamflag);
						fi.setPaymtdetailstovbyremarks(strpaymtdetailstovbyremark);
						fi.setPaymtdetailstovbyflag(strpaymtdetailstovbyflag);
						fi.setCashpaymntworeceiptremarks(strcashpaymntworeceiptremark);
						fi.setCashpaymntworeceiptflag(strcashpaymntworeceiptflag);
						fi.setApplicantknowvbyremarks(strapplicantknowvbyremark);
						fi.setApplicantknowvbyflag(strapplicantknowvbyflag);
						fi.setRelawithguarantorsremarks(strrelawithguarantorsremark);
						fi.setRelawithguarantorsflag(strrelawithguarantorsflag);
						fi.setBikeappliedremarks(strbikeappliedremarks);
						fi.setBikeappliedflag(strbikeappliedflag);
						fi.setDownpaymentremarks(strdownpaymentremarks);
						fi.setDownpaymentflag(strdownpaymentflag);
						fi.setTenureremarks(strtenureremarks);
						fi.setTenureflag(strtenureflag);
						fi.setEwioremiremarks(strewioremiremarks);
						fi.setEwioremiflag(strewioremiflag);
						
						fi.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
						fi.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
						
						fi.setResiadrssremarks(strresiadrssRemarks);
						fi.setResiadrssflag(strresiadrsschkbx);
						fi.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
						fi.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);		
						fi.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						fi.setMbnonotinnameflag(strmbnumnotinnamechkbx);
						
						fi.setFiremarks(strRemarks);
						fi.setFiverdict(blnFiverdict);
						
						fiVerifyRepo.save(fi);
						response.setData("FI Verification: " + strCustid + " (Customer) updated successfully");
					}	
				} else if (strMsg.contains("")) {
					response.setData("General error");
				}
			}
	}
		return response;
	}

	public Response<String> addGuaranFi(FiVerificationDto fiVerificationDto) {
		
		Response<String> response = new Response<>();
		
		String strId, strId2, strRemarks, strFiverdict;
		
		String strMsg = "";
		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnFiverdict = false;
		
		String strFIRequestdatetime = "";
		String strLoginuser = "";
		
		int intUser = 0;
		
		strId =  fiVerificationDto.getGfiid();
		strId2 =  fiVerificationDto.getGfiid2();
		
		strFiverdict = fiVerificationDto.getStrfiverdict();
		strRemarks =  fiVerificationDto.getFiremarks();		
		
		String strSurname = fiVerificationDto.getSurname();
		String strSurnameremark = fiVerificationDto.getSurnameremark();
		String strSurnamechkbx = fiVerificationDto.getSurnameflag(); 
		String strFname = fiVerificationDto.getFirstname(); 
		String strfnameremark = fiVerificationDto.getFirstnameremark(); 
		String strfnamechkbx = fiVerificationDto.getFirstnameflag();
		String stroname =  fiVerificationDto.getOthername();
		String stronameremark = fiVerificationDto.getOthernameremark();
		String stronamechkbx =  fiVerificationDto.getOthernameflag();	
		String strnid =  fiVerificationDto.getNationalid();
		String strnidremark =  fiVerificationDto.getNationalidremark();
		String strnidchkbx =  fiVerificationDto.getNationalidflag();		
		String strmobno =  fiVerificationDto.getMobileno();
		String strmobnoremark = fiVerificationDto.getMobilenoremark();
		String strmobnochkbx =  fiVerificationDto.getMobilenoflag();
		String stradd =  fiVerificationDto.getAddress();
		String straddremark = fiVerificationDto.getAddressremark();
		String straddchkbx =  fiVerificationDto.getAddressflag();
		String strpadd =  fiVerificationDto.getPermanentaddress();
		String strpaddremark =  fiVerificationDto.getPermanentaddressremark();
		String strpaddchkbx =  fiVerificationDto.getPermanentaddressflag();
		String stryradd =  fiVerificationDto.getYearsinaddress();
		String stryraddremark = fiVerificationDto.getYearsinaddressremark();
		String stryraddchkbx =  fiVerificationDto.getYearsinaddressflag();
		String stror =  fiVerificationDto.getOwnhouserented();
		String strorremark = fiVerificationDto.getOwnhouserentedremark();
		String strorchkbx =  fiVerificationDto.getOwnhouserentedflag();
		String strrpm =fiVerificationDto.getRentpm();
		String strrpmremark =  fiVerificationDto.getRentpmremark();
		String strrpmchkbx =  fiVerificationDto.getRentpmflag();
		String strnok =  fiVerificationDto.getNextofkin();
		String strnokremark = fiVerificationDto.getNextofkinremark();
		String strnokchkbx =  fiVerificationDto.getNextofkinflag();
		String strnokm =  fiVerificationDto.getNokmobileno();
		String strnokmremark = fiVerificationDto.getNokmobilenoremark();
		String strnokmchkbx = fiVerificationDto.getNokmobilenoflag();
		String strnokr = fiVerificationDto.getNokrelationship();
		String strnokrremark = fiVerificationDto.getNokrelationshipremark();
		String strnokrchkbx = fiVerificationDto.getNokrelationshipflag();
		String strnoka = fiVerificationDto.getNokagreeing();
		String strnokaremark = fiVerificationDto.getNokagreeingremark();
		String strnokachkbx =  fiVerificationDto.getNokagreeingflag();
		String strbregno = fiVerificationDto.getBikeregno();
		String strbregnoremark = fiVerificationDto.getBikeregnoremark();
		String strbregnochkbx = fiVerificationDto.getBikeregnoflag();
		String strbowner = fiVerificationDto.getBikeowner();
		String strbownerremark = fiVerificationDto.getBikeownerremark();
		String strbownerchkbx = fiVerificationDto.getBikeownerflag();
		String strsal = fiVerificationDto.getSalaried();
		String strsalremark = fiVerificationDto.getSalariedremark();
		String strsalchkbx =  fiVerificationDto.getSalariedflag();
		String strmi =  fiVerificationDto.getMonthlyincome();
		String strmiremark = fiVerificationDto.getMonthlyincomeremark();
		String strmichkbx = fiVerificationDto.getMonthlyincomeflag();
		String strois = fiVerificationDto.getOtherincomesource();
		String stroisremark = fiVerificationDto.getOtherincomesourceremark();
		String stroischkbx = fiVerificationDto.getOtherincomesourceflag();
		String stroi = fiVerificationDto.getOtherincome();
		String stroiremark = fiVerificationDto.getOtherincomeremark();
		String stroichkbx =  fiVerificationDto.getOtherincomeflag();		
		String strstgnm =  fiVerificationDto.getStage();
		String strstgnmremark = fiVerificationDto.getStageremark();
		String strstgnmchkbx = fiVerificationDto.getStageflag();
		String strstgadd = fiVerificationDto.getStageaddress();
		String strstgaddremark = fiVerificationDto.getStageaddressremark();
		String strstgaddchkbx = fiVerificationDto.getStageaddressflag();
		String strstgcc =  fiVerificationDto.getStagechairconfirmation();
		String strstgccremark = fiVerificationDto.getStagechairconfirmationremark();
		String strstgccchkbx = fiVerificationDto.getStagechairconfirmationflag();
		String strlcnm =  fiVerificationDto.getLc();
		String strlcnmremark = fiVerificationDto.getLcremark();
		String strlcnmchkbx =  fiVerificationDto.getLcflag();
		String strrltnshp =  fiVerificationDto.getRelationship();
		String strrltnshpremark = fiVerificationDto.getRelationshipremark();
		String strrltnshpchkbx = fiVerificationDto.getRelationshipflag();
		
		String strDob = fiVerificationDto.getDob();
		String strDobRemarks = fiVerificationDto.getDobremark();
		String strDobchkbx = fiVerificationDto.getDobflag();

		String strLlname = fiVerificationDto.getLandlordname();
		String strLlnameRemarks = fiVerificationDto.getLandlordnameremark();
		String strLlnamechkbx = fiVerificationDto.getLandlordnameflag();

		String strLlmbno = fiVerificationDto.getLandlordmobileno();
		String strLlmbnoRemarks = fiVerificationDto.getLandlordmobilenoremark();
		String strLlmbnochkbx = fiVerificationDto.getLandlordmobilenoflag();

		String strResiadrss = fiVerificationDto.getResiadrss();
		String strResiadrssRemarks = fiVerificationDto.getResiadrssremarks();
		String strResiadrsschkbx = fiVerificationDto.getResiadrssflag();

		String strMaritalsts = fiVerificationDto.getMaritalstatus();
		String strMaritalstsRemarks = fiVerificationDto.getMaritalstatusremark();
		String strMaritalstschkbx = fiVerificationDto.getMaritalstatusflag();

		String strLLFeedAbtRnt = fiVerificationDto.getLlrentfeedback();
		String strLLFeedAbtRntRemarks = fiVerificationDto.getLlrentfeedbackremarks();
		String strLLFeedAbtRntchkbx = fiVerificationDto.getLlrentfeedbackflag();

		String strNoOfYrsInArea = fiVerificationDto.getNoyrsinarea();
		String strNoOfYrsInAreaRemarks = fiVerificationDto.getNoyrsinarearemarks();
		String strNoOfYrsInAreachkbx = fiVerificationDto.getNoyrsinareaflag();

		String strLc1chmnRecFeedbk = fiVerificationDto.getLc1chmnrecfeed();
		String strLc1chmnRecFeedbkRemarks = fiVerificationDto.getLc1chmnrecfeedremarks();
		String strLc1chmnRecFeedbkchkbx = fiVerificationDto.getLc1chmnrecfeedflag();

		String strNearLndmrkorResi = fiVerificationDto.getNearlmarkresi();
		String strNearLndmrkorResiRemarks = fiVerificationDto.getNearlmarkresiremarks();
		String strNearLndmrkorResichkbx = fiVerificationDto.getNearlmarkresiflag();

		String strEmpType = fiVerificationDto.getEmptype();
		String strEmpTypeRemarks = fiVerificationDto.getEmptyperemarks();
		String strEmpTypechkbx = fiVerificationDto.getEmptypeflag();

		String strStgOrWrkadrssWithNearLNMRK = fiVerificationDto.getStgorwrkadrssnearlmark();
		String strStgOrWrkadrssWithNearLNMRKRemarks = fiVerificationDto.getStgorwrkadrssnearlmarkremarks();
		String strStgOrWrkadrssWithNearLNMRKchkbx = fiVerificationDto.getStgorwrkadrssnearlmarkflag();

		String strNoOfYrsInstgorBusi = fiVerificationDto.getNoofyrsinstgorbusi();
		String strNoOfYrsInstgorBusiRemarks = fiVerificationDto.getNoofyrsinstgorbusiremarks();
		String strNoOfYrsInstgorBusichkbx = fiVerificationDto.getNoofyrsinstgorbuisflag();

		String strSpouseName = fiVerificationDto.getSpousename();
		String strSpouseNameRemarks = fiVerificationDto.getSpousenameremarks();
		String strSpouseNamechkbx = fiVerificationDto.getSpousenameflag();

		String strSpouseMbno = fiVerificationDto.getSpouseno();
		String strSpouseMbnoRemarks = fiVerificationDto.getSpousenoremarks();
		String strSpouseMbnochkbx = fiVerificationDto.getSpousenoflag();

		String strYakaNum = fiVerificationDto.getYakanum();
		String strYakaNumRemarks = fiVerificationDto.getYakanumremarks();
		String strYakaNumchkbx = fiVerificationDto.getYakanumflag();

		String strYakaNoName = fiVerificationDto.getYakanumname();
		String strYakaNoNameRemarks = fiVerificationDto.getYakanumnameremarks();
		String strYakaNoNamechkbx = fiVerificationDto.getYakanumnamflag();

		String strlc1number = fiVerificationDto.getLcmobileno();
		String strlc1numberRemarks = fiVerificationDto.getLcmobilenoremark();
		String strlc1numberchkbx = fiVerificationDto.getLcmobilenoflag();

		String strstgorempno = fiVerificationDto.getStagechairmanno();
		String strstgorempnoRemarks = fiVerificationDto.getStagechairmannoremarks();
		String strstgorempnochkbx = fiVerificationDto.getStagechairmannoflag();
		
		String strmbnumnotinname = fiVerificationDto.getMbnonotinname();
		String strmbnumnotinnameRemarks = fiVerificationDto.getMbnonotinnameremarks();
		String strmbnumnotinnamechkbx = fiVerificationDto.getMbnonotinnameflag();

		

		String strSurname2 = fiVerificationDto.getSurname2();
		String strSurname2remark = fiVerificationDto.getSurnameremark2();
		String strSurname2chkbx = fiVerificationDto.getSurnameflag2(); 
		String strFname2 = fiVerificationDto.getFirstname2(); 
		String strfname2remark = fiVerificationDto.getFirstnameremark2(); 
		String strfname2chkbx = fiVerificationDto.getFirstnameflag2();
		String stroname2 =  fiVerificationDto.getOthername2();
		String stroname2remark = fiVerificationDto.getOthernameremark2();
		String stroname2chkbx =  fiVerificationDto.getOthernameflag2();	
		String strnid2 =  fiVerificationDto.getNationalid2();
		String strnid2remark =  fiVerificationDto.getNationalidremark2();
		String strnid2chkbx =  fiVerificationDto.getNationalidflag2();		
		String strmobno2 =  fiVerificationDto.getMobileno2();
		String strmobno2remark = fiVerificationDto.getMobilenoremark2();
		String strmobno2chkbx =  fiVerificationDto.getMobilenoflag2();
		String stradd2 =  fiVerificationDto.getAddress2();
		String stradd2remark = fiVerificationDto.getAddressremark2();
		String stradd2chkbx =  fiVerificationDto.getAddressflag2();
		String strpadd2 =  fiVerificationDto.getPermanentaddress2();
		String strpadd2remark =  fiVerificationDto.getPermanentaddressremark2();
		String strpadd2chkbx =  fiVerificationDto.getPermanentaddressflag2();
		String stryradd2 =  fiVerificationDto.getYearsinaddress2();
		String stryradd2remark = fiVerificationDto.getYearsinaddressremark2();
		String stryradd2chkbx =  fiVerificationDto.getYearsinaddressflag2();
		String stror2 =  fiVerificationDto.getOwnhouserented2();
		String stror2remark = fiVerificationDto.getOwnhouserentedremark2();
		String stror2chkbx =  fiVerificationDto.getOwnhouserentedflag2();
		String strrpm2 =fiVerificationDto.getRentpm2();
		String strrpm2remark =  fiVerificationDto.getRentpmremark2();
		String strrpm2chkbx =  fiVerificationDto.getRentpmflag2();
		String strnok2 =  fiVerificationDto.getNextofkin2();
		String strnok2remark = fiVerificationDto.getNextofkinremark2();
		String strnok2chkbx =  fiVerificationDto.getNextofkinflag2();
		String strnokm2 =  fiVerificationDto.getNokmobileno2();
		String strnokm2remark = fiVerificationDto.getNokmobilenoremark2();
		String strnokm2chkbx = fiVerificationDto.getNokmobilenoflag2();
		String strnokr2 = fiVerificationDto.getNokrelationship2();
		String strnokr2remark = fiVerificationDto.getNokrelationshipremark2();
		String strnokr2chkbx = fiVerificationDto.getNokrelationshipflag2();
		String strnoka2 = fiVerificationDto.getNokagreeing2();
		String strnoka2remark = fiVerificationDto.getNokagreeingremark2();
		String strnoka2chkbx =  fiVerificationDto.getNokagreeingflag2();
		String strbregno2 = fiVerificationDto.getBikeregno2();
		String strbregno2remark = fiVerificationDto.getBikeregnoremark2();
		String strbregno2chkbx = fiVerificationDto.getBikeregnoflag2();
		String strbowner2 = fiVerificationDto.getBikeowner2();
		String strbowner2remark = fiVerificationDto.getBikeownerremark2();
		String strbowner2chkbx = fiVerificationDto.getBikeownerflag2();
		String strsal2 = fiVerificationDto.getSalaried2();
		String strsal2remark = fiVerificationDto.getSalariedremark2();
		String strsal2chkbx =  fiVerificationDto.getSalariedflag2();
		String strmi2 =  fiVerificationDto.getMonthlyincome2();
		String strmi2remark = fiVerificationDto.getMonthlyincomeremark2();
		String strmi2chkbx = fiVerificationDto.getMonthlyincomeflag2();
		String strois2 = fiVerificationDto.getOtherincomesource2();
		String strois2remark = fiVerificationDto.getOtherincomesourceremark2();
		String strois2chkbx = fiVerificationDto.getOtherincomesourceflag2();
		String stroi2 = fiVerificationDto.getOtherincome2();
		String stroi2remark = fiVerificationDto.getOtherincomeremark2();
		String stroi2chkbx =  fiVerificationDto.getOtherincomeflag2();		
		String strstgnm2 =  fiVerificationDto.getStage2();
		String strstgnm2remark = fiVerificationDto.getStageremark2();
		String strstgnm2chkbx = fiVerificationDto.getStageflag2();
		String strstgadd2 = fiVerificationDto.getStageaddress2();
		String strstgadd2remark = fiVerificationDto.getStageaddressremark2();
		String strstgadd2chkbx = fiVerificationDto.getStageaddressflag2();
		String strstgcc2 =  fiVerificationDto.getStagechairconfirmation2();
		String strstgcc2remark = fiVerificationDto.getStagechairconfirmationremark2();
		String strstgcc2chkbx = fiVerificationDto.getStagechairconfirmationflag2();
		String strlcnm2 =  fiVerificationDto.getLc2();
		String strlcnm2remark = fiVerificationDto.getLcremark2();
		String strlcnm2chkbx =  fiVerificationDto.getLcflag2();
		String strrltnshp2 =  fiVerificationDto.getRelationship2();
		String strrltnshp2remark = fiVerificationDto.getRelationshipremark2();
		String strrltnshp2chkbx = fiVerificationDto.getRelationshipflag2();
		
		String strDob2 = fiVerificationDto.getDob2();
		String strDobRemarks2 = fiVerificationDto.getDobremark2();
		String strDobchkbx2 = fiVerificationDto.getDobflag2();

		String strLlname2 = fiVerificationDto.getLandlordname2();
		String strLlnameRemarks2 = fiVerificationDto.getLandlordnameremark2();
		String strLlnamechkbx2 = fiVerificationDto.getLandlordnameflag2();

		String strLlmbno2 = fiVerificationDto.getLandlordmobileno2();
		String strLlmbnoRemarks2 = fiVerificationDto.getLandlordmobilenoremark2();
		String strLlmbnochkbx2 = fiVerificationDto.getLandlordmobilenoflag2();

		String strResiadrss2 = fiVerificationDto.getResiadrss2();
		String strResiadrssRemarks2 = fiVerificationDto.getResiadrssremarks2();
		String strResiadrsschkbx2 = fiVerificationDto.getResiadrssflag2();

		String strMaritalsts2 = fiVerificationDto.getMaritalstatus2();
		String strMaritalstsRemarks2 = fiVerificationDto.getMaritalstatusremark2();
		String strMaritalstschkbx2 = fiVerificationDto.getMaritalstatusflag2();

		String strLLFeedAbtRnt2 = fiVerificationDto.getLlrentfeedback2();
		String strLLFeedAbtRntRemarks2 = fiVerificationDto.getLlrentfeedbackremarks2();
		String strLLFeedAbtRntchkbx2 = fiVerificationDto.getLlrentfeedbackflag2();

		String strNoOfYrsInArea2 = fiVerificationDto.getNoyrsinarea2();
		String strNoOfYrsInAreaRemarks2 = fiVerificationDto.getNoyrsinarearemarks2();
		String strNoOfYrsInAreachkbx2 = fiVerificationDto.getNoyrsinareaflag2();

		String strLc1chmnRecFeedbk2 = fiVerificationDto.getLc1chmnrecfeed2();
		String strLc1chmnRecFeedbkRemarks2 = fiVerificationDto.getLc1chmnrecfeedremarks2();
		String strLc1chmnRecFeedbkchkbx2 = fiVerificationDto.getLc1chmnrecfeedflag2();

		String strNearLndmrkorResi2 = fiVerificationDto.getNearlmarkresi2();
		String strNearLndmrkorResiRemarks2 = fiVerificationDto.getNearlmarkresiremarks2();
		String strNearLndmrkorResichkbx2 = fiVerificationDto.getNearlmarkresiflag2();

		String strEmpType2 = fiVerificationDto.getEmptype2();
		String strEmpTypeRemarks2 = fiVerificationDto.getEmptyperemarks2();
		String strEmpTypechkbx2 = fiVerificationDto.getEmptypeflag2();

		String strStgOrWrkadrssWithNearLNMRK2 = fiVerificationDto.getStgorwrkadrssnearlmark2();
		String strStgOrWrkadrssWithNearLNMRKRemarks2 = fiVerificationDto.getStgorwrkadrssnearlmarkremarks2();
		String strStgOrWrkadrssWithNearLNMRKchkbx2 = fiVerificationDto.getStgorwrkadrssnearlmarkflag2();

		String strNoOfYrsInstgorBusi2 = fiVerificationDto.getNoofyrsinstgorbusi2();
		String strNoOfYrsInstgorBusiRemarks2 = fiVerificationDto.getNoofyrsinstgorbusiremarks2();
		String strNoOfYrsInstgorBusichkbx2 = fiVerificationDto.getNoofyrsinstgorbuisflag2();

		String strSpouseName2 = fiVerificationDto.getSpousename2();
		String strSpouseNameRemarks2 = fiVerificationDto.getSpousenameremarks2();
		String strSpouseNamechkbx2 = fiVerificationDto.getSpousenameflag2();

		String strSpouseMbno2 = fiVerificationDto.getSpouseno2();
		String strSpouseMbnoRemarks2 = fiVerificationDto.getSpousenoremarks2();
		String strSpouseMbnochkbx2 = fiVerificationDto.getSpousenoflag2();

		String strYakaNum2 = fiVerificationDto.getYakanum2();
		String strYakaNumRemarks2 = fiVerificationDto.getYakanumremarks2();
		String strYakaNumchkbx2 = fiVerificationDto.getYakanumflag2();

		String strYakaNoName2 = fiVerificationDto.getYakanumname2();
		String strYakaNoNameRemarks2 = fiVerificationDto.getYakanumnameremarks2();
		String strYakaNoNamechkbx2 = fiVerificationDto.getYakanumnamflag2();

		String strlc1number2 = fiVerificationDto.getLcmobileno2();
		String strlc1numberRemarks2 = fiVerificationDto.getLcmobilenoremark2();
		String strlc1numberchkbx2 = fiVerificationDto.getLcmobilenoflag2();

		String strstgorempno2 = fiVerificationDto.getStagechairmanno2();
		String strstgorempnoRemarks2 = fiVerificationDto.getStagechairmannoremarks2();
		String strstgorempnochkbx2 = fiVerificationDto.getStagechairmannoflag2();
		
		String strmbnumnotinname2 = fiVerificationDto.getMbnonotinname2();
		String strmbnumnotinnameRemarks2 = fiVerificationDto.getMbnonotinnameremarks2();
		String strmbnumnotinnamechkbx2 = fiVerificationDto.getMbnonotinnameflag2();
		
		

	
		strLoginuser =  fiVerificationDto.getUsername();
		
		if (strId == null || strId.length() == 0) {
			
			response.setData("Please select customer id");
		
		} else {
			
			if (strFiverdict.equals(strPosverdict)) {
				blnFiverdict = true;
			} else if (strFiverdict.equals(strNegverdict)) {
				blnFiverdict = false;
			}
			
			TimeStampUtil gts = new TimeStampUtil();		
			strFIRequestdatetime = gts.TimeStamp();
			
			FiVerification fiverify = new FiVerification();
			fiverify.setFiid(strId);
			fiverify.setSurname(strSurname);
			fiverify.setSurnameremark(strSurnameremark);
			fiverify.setSurnameflag(strSurnamechkbx);
			fiverify.setFirstname(strFname);
			fiverify.setFirstnameremark(strfnameremark);
			fiverify.setFirstnameflag(strfnamechkbx);
			fiverify.setOthername(stroname);
			fiverify.setOthernameremark(stronameremark);
			fiverify.setOthernameflag(stronamechkbx);
			fiverify.setNationalid(strnid);
			fiverify.setNationalidremark(strnidremark);
			fiverify.setNationalidflag(strnidchkbx);
			fiverify.setMobileno(strmobno);
			fiverify.setMobilenoremark(strmobnoremark);
			fiverify.setMobilenoflag(strmobnochkbx);
			fiverify.setAddress(stradd);
			fiverify.setAddressremark(straddremark);
			fiverify.setAddressflag(straddchkbx);
			fiverify.setPermanentaddress(strpadd);
			fiverify.setPermanentaddressremark(strpaddremark);
			fiverify.setPermanentaddressflag(strpaddchkbx);
			fiverify.setYearsinaddress(stryradd);
			fiverify.setYearsinaddressremark(stryraddremark);
			fiverify.setYearsinaddressflag(stryraddchkbx);
			fiverify.setOwnhouserented(stror);
			fiverify.setOwnhouserentedremark(strorremark);
			fiverify.setOwnhouserentedflag(strorchkbx);
			fiverify.setRentpm(strrpm);
			fiverify.setRentpmremark(strrpmremark);
			fiverify.setRentpmflag(strrpmchkbx);
			fiverify.setNextofkin(strnok);
			fiverify.setNextofkinremark(strnokremark);
			fiverify.setNextofkinflag(strnokchkbx);
			fiverify.setNokmobileno(strnokm);
			fiverify.setNokmobilenoremark(strnokmremark);
			fiverify.setNokmobilenoflag(strnokmchkbx);
			fiverify.setNokrelationship(strnokr);
			fiverify.setNokrelationshipremark(strnokrremark);
			fiverify.setNokrelationshipflag(strnokrchkbx);
			fiverify.setNokagreeing(strnoka);
			fiverify.setNokagreeingremark(strnokaremark);
			fiverify.setNokagreeingflag(strnokachkbx);
			fiverify.setBikeregno(strbregno);
			fiverify.setBikeregnoremark(strbregnoremark);
			fiverify.setBikeregnoflag(strbregnochkbx);
			fiverify.setBikeowner(strbowner);
			fiverify.setBikeownerremark(strbownerremark);
			fiverify.setBikeownerflag(strbownerchkbx);
			fiverify.setSalaried(strsal);
			fiverify.setSalariedremark(strsalremark);
			fiverify.setSalariedflag(strsalchkbx);
			fiverify.setMonthlyincome(strmi);
			fiverify.setMonthlyincomeremark(strmiremark);
			fiverify.setMonthlyincomeflag(strmichkbx);
			fiverify.setOtherincomesource(strois);
			fiverify.setOtherincomesourceremark(stroisremark);
			fiverify.setOtherincomesourceflag(stroischkbx);
			fiverify.setOtherincome(stroi);
			fiverify.setOtherincomeremark(stroiremark);
			fiverify.setOtherincomeflag(stroichkbx);
			fiverify.setStage(strstgnm);
			fiverify.setStageremark(strstgnmremark);
			fiverify.setStageflag(strstgnmchkbx);
			fiverify.setStageaddress(strstgadd);
			fiverify.setStageaddressremark(strstgaddremark);
			fiverify.setStageaddressflag(strstgaddchkbx);
			fiverify.setStagechairconfirmation(strstgcc);
			fiverify.setStagechairconfirmationremark(strstgccremark);
			fiverify.setStagechairconfirmationflag(strstgccchkbx);
			fiverify.setLc(strlcnm);
			fiverify.setLcremark(strlcnmremark);
			fiverify.setLcflag(strlcnmchkbx);
			fiverify.setRelationship(strrltnshp);
			fiverify.setRelationshipremark(strrltnshpremark);
			fiverify.setRelationshipflag(strrltnshpchkbx);
			
			fiverify.setDob(strDob);
			fiverify.setDobremark(strDobRemarks);
			fiverify.setDobflag(strDobchkbx);

			fiverify.setLandlordname(strLlname);
			fiverify.setLandlordnameremark(strLlnameRemarks);
			fiverify.setLandlordnameflag(strLlnamechkbx);

			fiverify.setLandlordmobileno(strLlmbno);
			fiverify.setLandlordmobilenoremark(strLlmbnoRemarks);
			fiverify.setLandlordmobilenoflag(strLlmbnochkbx);

			fiverify.setResiadrss(strResiadrss);
			fiverify.setResiadrssremarks(strResiadrssRemarks);
			fiverify.setResiadrssflag(strResiadrsschkbx);

			fiverify.setMaritalstatus(strMaritalsts);
			fiverify.setMaritalstatusremark(strMaritalstsRemarks);
			fiverify.setMaritalstatusflag(strMaritalstschkbx);

			fiverify.setLlrentfeedback(strLLFeedAbtRnt);
			fiverify.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks);
			fiverify.setLlrentfeedbackflag(strLLFeedAbtRntchkbx);

			fiverify.setNoyrsinarea(strNoOfYrsInArea);
			fiverify.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks);
			fiverify.setNoyrsinareaflag(strNoOfYrsInAreachkbx);

			fiverify.setLc1chmnrecfeed(strLc1chmnRecFeedbk);
			fiverify.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks);
			fiverify.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx);

			fiverify.setNearlmarkresi(strNearLndmrkorResi);
			fiverify.setNearlmarkresiremarks(strNearLndmrkorResiRemarks);
			fiverify.setNearlmarkresiflag(strNearLndmrkorResichkbx);

			fiverify.setEmptype(strEmpType);
			fiverify.setEmptyperemarks(strEmpTypeRemarks);
			fiverify.setEmptypeflag(strEmpTypechkbx);

			fiverify.setStgorwrkadrssnearlmark(strStgOrWrkadrssWithNearLNMRK);
			fiverify.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks);
			fiverify.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx);

			fiverify.setNoofyrsinstgorbusi(strNoOfYrsInstgorBusi);
			fiverify.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks);
			fiverify.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx);

			fiverify.setSpousename(strSpouseName);
			fiverify.setSpousenameremarks(strSpouseNameRemarks);
			fiverify.setSpousenameflag(strSpouseNamechkbx);

			fiverify.setSpouseno(strSpouseMbno);
			fiverify.setSpousenoremarks(strSpouseMbnoRemarks);
			fiverify.setSpousenoflag(strSpouseMbnochkbx);

			fiverify.setYakanum(strYakaNum);
			fiverify.setYakanumremarks(strYakaNumRemarks);
			fiverify.setYakanumflag(strYakaNumchkbx);

			fiverify.setYakanumname(strYakaNoName);
			fiverify.setYakanumnameremarks(strYakaNoNameRemarks);
			fiverify.setYakanumnamflag(strYakaNoNamechkbx);

			fiverify.setLcmobileno(strlc1number);
			fiverify.setLcmobilenoremark(strlc1numberRemarks);
			fiverify.setLcmobilenoflag(strlc1numberchkbx);

			fiverify.setStagechairmanno(strstgorempno);
			fiverify.setStagechairmannoremarks(strstgorempnoRemarks);
			fiverify.setStagechairmannoflag(strstgorempnochkbx);
			
			fiverify.setMbnonotinname(strmbnumnotinname);
			fiverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			fiverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

			
			fiverify.setFirstguarantor(true);
			fiverify.setSecondguarantor(false);
			fiverify.setFiremarks(strRemarks);
			fiverify.setFiverdict(blnFiverdict);	
			
			try {
				fiVerifyRepo.save(fiverify);
				response.setData("FI Verification for : " + strId + " (Guarantor) saved successfully.");	
				
			} catch (Exception e) {
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		         
				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {
					
					Optional<FiVerification> opfi = fiVerifyRepo.findById(strId);
					
					if(opfi.isPresent()) {
						FiVerification fi = opfi.get();
						fi.setSurnameremark(strSurnameremark); 
						fi.setSurnameflag(strSurnamechkbx);
						fi.setFirstnameremark(strfnameremark);
						fi.setFirstnameflag(strfnamechkbx);
						fi.setOthernameremark(stronameremark);
						fi.setOthernameflag(stronamechkbx);
						fi.setMaritalstatusremark(strmiremark);
						fi.setMobilenoremark(strmobnoremark);	
						fi.setMobilenoflag(strmobnochkbx);
						fi.setStageremark(strstgnmremark);
						fi.setStageflag(strstgnmchkbx);
						fi.setLcremark(strlcnmremark);
						fi.setLcflag(strlcnmchkbx);
						fi.setNationalidremark(strnidremark);
						fi.setNationalidflag(strnidchkbx);
						fi.setBikeregnoremark(strbregnoremark);
						fi.setBikeregnoflag(strbregnochkbx);
						fi.setNextofkinremark(strnokremark);
						fi.setNextofkinflag(strnokchkbx);
						fi.setNokmobilenoremark(strnokmremark);
						fi.setNokmobilenoflag(strnokmchkbx);
						fi.setNokrelationshipremark(strnokrremark);
						fi.setNokrelationshipflag(strnokrchkbx);
						fi.setNokagreeingremark(strnokaremark);
						fi.setNokagreeingflag(strnokachkbx);
						fi.setOwnhouserentedremark(strorremark);
						fi.setOwnhouserentedflag(strorchkbx);
						fi.setRentpmremark(strrpmremark);
						fi.setRentpmflag(strrpmchkbx);
						fi.setOtherincomesourceremark(stroisremark);
						fi.setOtherincomesourceflag(stroischkbx);
						fi.setPermanentaddressremark(strpaddremark);
						fi.setPermanentaddressflag(strpaddchkbx);		
						
						fi.setDobremark(strDobRemarks);
						fi.setDobflag(strDobchkbx);

						fi.setLandlordnameremark(strLlnameRemarks);
						fi.setLandlordnameflag(strLlnamechkbx);

						fi.setLandlordmobilenoremark(strLlmbnoRemarks);
						fi.setLandlordmobilenoflag(strLlmbnochkbx);

						fi.setResiadrssremarks(strResiadrssRemarks);
						fi.setResiadrssflag(strResiadrsschkbx);

						fi.setMaritalstatusremark(strMaritalstsRemarks);
						fi.setMaritalstatusflag(strMaritalstschkbx);

						fi.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks);
						fi.setLlrentfeedbackflag(strLLFeedAbtRntchkbx);

						fi.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks);
						fi.setNoyrsinareaflag(strNoOfYrsInAreachkbx);

						fi.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks);
						fi.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx);

						fi.setNearlmarkresiremarks(strNearLndmrkorResiRemarks);
						fi.setNearlmarkresiflag(strNearLndmrkorResichkbx);

						fi.setEmptyperemarks(strEmpTypeRemarks);
						fi.setEmptypeflag(strEmpTypechkbx);

						fi.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks);
						fi.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx);

						fi.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks);
						fi.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx);

						fi.setSpousenameremarks(strSpouseNameRemarks);
						fi.setSpousenameflag(strSpouseNamechkbx);

						fi.setSpousenoremarks(strSpouseMbnoRemarks);
						fi.setSpousenoflag(strSpouseMbnochkbx);

						fi.setYakanumremarks(strYakaNumRemarks);
						fi.setYakanumflag(strYakaNumchkbx);

						fi.setYakanumnameremarks(strYakaNoNameRemarks);
						fi.setYakanumnamflag(strYakaNoNamechkbx);

						fi.setLcmobilenoremark(strlc1numberRemarks);
						fi.setLcmobilenoflag(strlc1numberchkbx);

						fi.setStagechairmannoremarks(strstgorempnoRemarks);
						fi.setStagechairmannoflag(strstgorempnochkbx);
						
						fi.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						fi.setMbnonotinnameflag(strmbnumnotinnamechkbx);
						
						

						
						fi.setFiremarks(strRemarks);
						fi.setFiverdict(blnFiverdict);
						System.out.println(strRemarks);
						System.out.println(blnFiverdict);
						fiVerifyRepo.save(fi);
						response.setData("FI Verification: " + strId + " (Guarantor) updated successfully");
					}
					
				} else if (strMsg.contains("")) {
					response.setData("General error");
				}
			}
			
			int Id = Integer.parseInt(strId);
			
			Optional<Guarantor> opGua = guarantorRepo.findById(Id);
			if(opGua.isPresent()) {
				Guarantor guarantor = opGua.get();
				System.out.println(strRemarks);
				System.out.println(blnFiverdict);
				guarantor.setFiverified(blnFiverdict);
				guarantor.setFiremarks(strRemarks);
				guarantor.setFiuser(strLoginuser);
				guarantor.setFidatetime(strFIRequestdatetime);	
				guarantorRepo.save(guarantor);
			}
			
			strQuery = "";
			
			FiVerification fiverify2 = new FiVerification();
			fiverify2.setFiid(strId2);
			fiverify2.setSurname(strSurname2);
			fiverify2.setSurnameremark(strSurname2remark);
			fiverify2.setSurnameflag(strSurname2chkbx);
			fiverify2.setFirstname(strFname2);
			fiverify2.setFirstnameremark(strfname2remark);
			fiverify2.setFirstnameflag(strfname2chkbx);
			fiverify2.setOthername(stroname2);
			fiverify2.setOthernameremark(stroname2remark);
			fiverify2.setOthernameflag(stroname2chkbx);
			fiverify2.setNationalid(strnid2);
			fiverify2.setNationalidremark(strnid2remark);
			fiverify2.setNationalidflag(strnid2chkbx);
			fiverify2.setMobileno(strmobno2);
			fiverify2.setMobilenoremark(strmobno2remark);
			fiverify2.setMobilenoflag(strmobno2chkbx);
			fiverify2.setAddress(stradd2);
			fiverify2.setAddressremark(stradd2remark);
			fiverify2.setAddressflag(stradd2chkbx);
			fiverify2.setPermanentaddress(strpadd2);
			fiverify2.setPermanentaddressremark(strpadd2remark);
			fiverify2.setPermanentaddressflag(strpadd2chkbx);
			fiverify2.setYearsinaddress(stryradd2);
			fiverify2.setYearsinaddressremark(stryradd2remark);
			fiverify2.setYearsinaddressflag(stryradd2chkbx);
			fiverify2.setOwnhouserented(stror2);
			fiverify2.setOwnhouserentedremark(stror2remark);
			fiverify2.setOwnhouserentedflag(stror2chkbx);
			fiverify2.setRentpm(strrpm2);
			fiverify2.setRentpmremark(strrpm2remark);
			fiverify2.setRentpmflag(strrpm2chkbx);
			fiverify2.setNextofkin(strnok2);
			fiverify2.setNextofkinremark(strnok2remark);
			fiverify2.setNextofkinflag(strnok2chkbx);
			fiverify2.setNokmobileno(strnokm2);
			fiverify2.setNokmobilenoremark(strnokm2remark);
			fiverify2.setNokmobilenoflag(strnokm2chkbx);
			fiverify2.setNokrelationship(strnokr2);
			fiverify2.setNokrelationshipremark(strnokr2remark);
			fiverify2.setNokrelationshipflag(strnokr2chkbx);
			fiverify2.setNokagreeing(strnoka2);
			fiverify2.setNokagreeingremark(strnoka2remark);
			fiverify2.setNokagreeingflag(strnoka2chkbx);
			fiverify2.setBikeregno(strbregno2);
			fiverify2.setBikeregnoremark(strbregno2remark);
			fiverify2.setBikeregnoflag(strbregno2chkbx);
			fiverify2.setBikeowner(strbowner2);
			fiverify2.setBikeownerremark(strbowner2remark);
			fiverify2.setBikeownerflag(strbowner2chkbx);
			fiverify2.setSalaried(strsal2);
			fiverify2.setSalariedremark(strsal2remark);
			fiverify2.setSalariedflag(strsal2chkbx);
			fiverify2.setMonthlyincome(strmi2);
			fiverify2.setMonthlyincomeremark(strmi2remark);
			fiverify2.setMonthlyincomeflag(strmi2chkbx);
			fiverify2.setOtherincomesource(strois2);
			fiverify2.setOtherincomesourceremark(strois2remark);
			fiverify2.setOtherincomesourceflag(strois2chkbx);
			fiverify2.setOtherincome(stroi2);
			fiverify2.setOtherincomeremark(stroi2remark);
			fiverify2.setOtherincomeflag(stroi2chkbx);
			fiverify2.setStage(strstgnm2);
			fiverify2.setStageremark(strstgnm2remark);
			fiverify2.setStageflag(strstgnm2chkbx);
			fiverify2.setStageaddress(strstgadd2);
			fiverify2.setStageaddressremark(strstgadd2remark);
			fiverify2.setStageaddressflag(strstgadd2chkbx);
			fiverify2.setStagechairconfirmation(strstgcc2);
			fiverify2.setStagechairconfirmationremark(strstgcc2remark);
			fiverify2.setStagechairconfirmationflag(strstgcc2chkbx);
			fiverify2.setLc(strlcnm2);
			fiverify2.setLcremark(strlcnm2remark);
			fiverify2.setLcflag(strlcnm2chkbx);
			fiverify2.setRelationship(strrltnshp2);
			fiverify2.setRelationshipremark(strrltnshp2remark);
			fiverify2.setRelationshipflag(strrltnshp2chkbx);
			
			fiverify2.setDob(strDob2);
			fiverify2.setDobremark(strDobRemarks2);
			fiverify2.setDobflag(strDobchkbx2);

			fiverify2.setLandlordname(strLlname2);
			fiverify2.setLandlordnameremark(strLlnameRemarks2);
			fiverify2.setLandlordnameflag(strLlnamechkbx2);

			fiverify2.setLandlordmobileno(strLlmbno2);
			fiverify2.setLandlordmobilenoremark(strLlmbnoRemarks2);
			fiverify2.setLandlordmobilenoflag(strLlmbnochkbx2);

			fiverify2.setResiadrss(strResiadrss2);
			fiverify2.setResiadrssremarks(strResiadrssRemarks2);
			fiverify2.setResiadrssflag(strResiadrsschkbx2);

			fiverify2.setMaritalstatus(strMaritalsts2);
			fiverify2.setMaritalstatusremark(strMaritalstsRemarks2);
			fiverify2.setMaritalstatusflag(strMaritalstschkbx2);

			fiverify2.setLlrentfeedback(strLLFeedAbtRnt2);
			fiverify2.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks2);
			fiverify2.setLlrentfeedbackflag(strLLFeedAbtRntchkbx2);

			fiverify2.setNoyrsinarea(strNoOfYrsInArea2);
			fiverify2.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks2);
			fiverify2.setNoyrsinareaflag(strNoOfYrsInAreachkbx2);

			fiverify2.setLc1chmnrecfeed(strLc1chmnRecFeedbk2);
			fiverify2.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks2);
			fiverify2.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx2);

			fiverify2.setNearlmarkresi(strNearLndmrkorResi2);
			fiverify2.setNearlmarkresiremarks(strNearLndmrkorResiRemarks2);
			fiverify2.setNearlmarkresiflag(strNearLndmrkorResichkbx2);

			fiverify2.setEmptype(strEmpType2);
			fiverify2.setEmptyperemarks(strEmpTypeRemarks2);
			fiverify2.setEmptypeflag(strEmpTypechkbx2);

			fiverify2.setStgorwrkadrssnearlmark(strStgOrWrkadrssWithNearLNMRK2);
			fiverify2.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks2);
			fiverify2.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx2);

			fiverify2.setNoofyrsinstgorbusi(strNoOfYrsInstgorBusi2);
			fiverify2.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks2);
			fiverify2.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx2);

			fiverify2.setSpousename(strSpouseName2);
			fiverify2.setSpousenameremarks(strSpouseNameRemarks2);
			fiverify2.setSpousenameflag(strSpouseNamechkbx2);

			fiverify2.setSpouseno(strSpouseMbno2);
			fiverify2.setSpousenoremarks(strSpouseMbnoRemarks2);
			fiverify2.setSpousenoflag(strSpouseMbnochkbx2);

			fiverify2.setYakanum(strYakaNum2);
			fiverify2.setYakanumremarks(strYakaNumRemarks2);
			fiverify2.setYakanumflag(strYakaNumchkbx2);

			fiverify2.setYakanumname(strYakaNoName2);
			fiverify2.setYakanumnameremarks(strYakaNoNameRemarks2);
			fiverify2.setYakanumnamflag(strYakaNoNamechkbx2);

			fiverify2.setLcmobileno(strlc1number2);
			fiverify2.setLcmobilenoremark(strlc1numberRemarks2);
			fiverify2.setLcmobilenoflag(strlc1numberchkbx2);

			fiverify2.setStagechairmanno(strstgorempno2);
			fiverify2.setStagechairmannoremarks(strstgorempnoRemarks2);
			fiverify2.setStagechairmannoflag(strstgorempnochkbx2);
			
			fiverify2.setMbnonotinname(strmbnumnotinname2);
			fiverify2.setMbnonotinnameremarks(strmbnumnotinnameRemarks2);
			fiverify2.setMbnonotinnameflag(strmbnumnotinnamechkbx2);

			
			
			fiverify2.setFirstguarantor(false);
			fiverify2.setSecondguarantor(true);
			fiverify2.setFiremarks(strRemarks);
			fiverify2.setFiverdict(blnFiverdict);
							
			try {
				fiVerifyRepo.save(fiverify2);
				response.setData("FI Verification for : " + strId2 + " (Guarantor) saved successfully.");				
				
			} catch (Exception e) {	
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		         
				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {
					
					Optional<FiVerification> opfi = fiVerifyRepo.findById(strId2);
					
					if(opfi.isPresent()) {
						FiVerification fi = opfi.get();
						fi.setSurnameremark(strSurname2remark); 
						fi.setSurnameflag(strSurname2chkbx);
						fi.setFirstnameremark(strfname2remark);
						fi.setFirstnameflag(strfname2chkbx);
						fi.setOthernameremark(stroname2remark);
						fi.setOthernameflag(stroname2chkbx);
						fi.setMaritalstatusremark(strmi2remark);
						fi.setMobilenoremark(strmobno2remark);	
						fi.setMobilenoflag(strmobno2chkbx);
						fi.setStageremark(strstgnm2remark);
						fi.setStageflag(strstgnm2chkbx);
						fi.setLcremark(strlcnm2remark);
						fi.setLcflag(strlcnm2chkbx);
						fi.setNationalidremark(strnid2remark);
						fi.setNationalidflag(strnid2chkbx);
						fi.setBikeregnoremark(strbregno2remark);
						fi.setBikeregnoflag(strbregno2chkbx);
						fi.setNextofkinremark(strnok2remark);
						fi.setNextofkinflag(strnok2chkbx);
						fi.setNokmobilenoremark(strnokm2remark);
						fi.setNokmobilenoflag(strnokm2chkbx);
						fi.setNokrelationshipremark(strnokr2remark);
						fi.setNokrelationshipflag(strnokr2chkbx);
						fi.setNokagreeingremark(strnoka2remark);
						fi.setNokagreeingflag(strnoka2chkbx);
						fi.setOwnhouserentedremark(stror2remark);
						fi.setOwnhouserentedflag(stror2chkbx);
						fi.setRentpmremark(strrpm2remark);
						fi.setRentpmflag(strrpm2chkbx);
						fi.setOtherincomesourceremark(strois2remark);
						fi.setOtherincomesourceflag(strois2chkbx);
						fi.setPermanentaddressremark(strpadd2remark);
						fi.setPermanentaddressflag(strpadd2chkbx);	
						
						fi.setDobremark(strDobRemarks2);
						fi.setDobflag(strDobchkbx2);

						fi.setLandlordnameremark(strLlnameRemarks2);
						fi.setLandlordnameflag(strLlnamechkbx2);

						fi.setLandlordmobilenoremark(strLlmbnoRemarks2);
						fi.setLandlordmobilenoflag(strLlmbnochkbx2);

						fi.setResiadrssremarks(strResiadrssRemarks2);
						fi.setResiadrssflag(strResiadrsschkbx2);

						fi.setMaritalstatusremark(strMaritalstsRemarks2);
						fi.setMaritalstatusflag(strMaritalstschkbx2);

						fi.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks2);
						fi.setLlrentfeedbackflag(strLLFeedAbtRntchkbx2);

						fi.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks2);
						fi.setNoyrsinareaflag(strNoOfYrsInAreachkbx2);

						fi.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks2);
						fi.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx2);

						fi.setNearlmarkresiremarks(strNearLndmrkorResiRemarks2);
						fi.setNearlmarkresiflag(strNearLndmrkorResichkbx2);

						fi.setEmptyperemarks(strEmpTypeRemarks2);
						fi.setEmptypeflag(strEmpTypechkbx2);

						fi.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks2);
						fi.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx2);

						fi.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks2);
						fi.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx2);

						fi.setSpousenameremarks(strSpouseNameRemarks2);
						fi.setSpousenameflag(strSpouseNamechkbx2);

						fi.setSpousenoremarks(strSpouseMbnoRemarks2);
						fi.setSpousenoflag(strSpouseMbnochkbx2);

						fi.setYakanumremarks(strYakaNumRemarks2);
						fi.setYakanumflag(strYakaNumchkbx2);

						fi.setYakanumnameremarks(strYakaNoNameRemarks2);
						fi.setYakanumnamflag(strYakaNoNamechkbx2);

						fi.setLcmobilenoremark(strlc1numberRemarks2);
						fi.setLcmobilenoflag(strlc1numberchkbx2);

						fi.setStagechairmannoremarks(strstgorempnoRemarks2);
						fi.setStagechairmannoflag(strstgorempnochkbx2);
						
						fi.setMbnonotinnameremarks(strmbnumnotinnameRemarks2);
						fi.setMbnonotinnameflag(strmbnumnotinnamechkbx2);

						
						fi.setFiremarks(strRemarks);
						fi.setFiverdict(blnFiverdict);
						System.out.println(strRemarks);
						System.out.println(blnFiverdict);
						fiVerifyRepo.save(fi);
						response.setData("FI Verification: " + strId2 + " (Guarantor) updated successfully");
					}
				} else if (strMsg.contains("")) {
					response.setData("General error");
				}
			}	
			
			int Id2 = Integer.parseInt(strId2);
			
			Optional<Guarantor> opGua2 = guarantorRepo.findById(Id2);
			if(opGua2.isPresent()) {
				Guarantor guarantor = opGua2.get();
				System.out.println(strRemarks);
				System.out.println(blnFiverdict);
				guarantor.setFiverified(blnFiverdict);
				guarantor.setFiremarks(strRemarks);
				guarantor.setFiuser(strLoginuser);
				guarantor.setFidatetime(strFIRequestdatetime);	
				guarantorRepo.save(guarantor);
			}
		
		}	
		return response;
	}
	
	
	public Response<String> addRiderFi(FiVerificationDto fiVerificationDto) {

		Response<String> response = new Response<>();

		String strCustid, strRemarks, strFiverdict;

		String strMsg = "";
//		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnFiverdict = false;

		String strFIRequestdatetime = "";
		String strLoginuser = "";

		strCustid = fiVerificationDto.getFiid();
//		int Custid = Integer.parseInt(strCustid);
		strFiverdict = fiVerificationDto.getStrfiverdict();
//		recommed or not (recommed , notrecommed)
		strRemarks = fiVerificationDto.getFiremarks();
//		last box

		
		String strFname = fiVerificationDto.getFirstnameRdR();
		String strfnameremark = fiVerificationDto.getFirstnameremarkRdR();
		String strfnameflag = fiVerificationDto.getFirstnameflagRdR();
		
		String strms = fiVerificationDto.getMaritalstatusRdR();
		String strmsremark = fiVerificationDto.getMaritalstatusremarkRdR();
		String strmsflag = fiVerificationDto.getMaritalstatusflagRdR();

		String strmobno = fiVerificationDto.getMobilenoRdR();
		String strmobnoremark = fiVerificationDto.getMobilenoremarkRdR();
		String strmobnoflag = fiVerificationDto.getMobilenoflagRdR();

		String strstgnm = fiVerificationDto.getStageRdR();
		String strstgnmremark = fiVerificationDto.getStageremarkRdR();
		String strstgnmflag = fiVerificationDto.getStageflagRdR();

		String strstgchmname = fiVerificationDto.getStagechairmanRdR();
		String strstgchmnnameremark = fiVerificationDto.getStagechairmanremarkRdR();
		String strstgchmnameflag = fiVerificationDto.getStagechairmanflagRdR();

		String strstgchmnno = fiVerificationDto.getStagechairmannoRdR();
		String strstgchmnnoremarks = fiVerificationDto.getStagechairmannoremarkRdR();
		String strstgchmnnoflag = fiVerificationDto.getStagechairmannoflagRdR();

		String strlcnm = fiVerificationDto.getLcRdR();
		String strlcnmremark = fiVerificationDto.getLcremarkRdR();
		String strlcnmflag = fiVerificationDto.getLcflagRdR();

		String strnid = fiVerificationDto.getNationalidRdR();
		String strnidremark = fiVerificationDto.getNationalidremarkRdR();
		String strnidflag = fiVerificationDto.getNationalidflagRdR();

		String strbregno = fiVerificationDto.getBikeregnoRdR();
		String strbregnoremark = fiVerificationDto.getBikeregnoremarkRdR();
		String strbregnoflag = fiVerificationDto.getBikeregnoflagRdR();

		String strbuse = fiVerificationDto.getNewbikeuseRdR();
		String strbuseremark = fiVerificationDto.getNewbikeuseremarkRdR();
		String strbuseflag = fiVerificationDto.getNewbikeuseflagRdR();

		String strdob = fiVerificationDto.getDobRdR();
		String strdobremark = fiVerificationDto.getDobremarkRdR();
		String strdobflag = fiVerificationDto.getDobflagRdR();

		String stror = fiVerificationDto.getOwnhouserentedRdR();
		String strorremark = fiVerificationDto.getOwnhouserentedremarkRdR();
		String strorflag = fiVerificationDto.getOwnhouserentedflagRdR();

		String strll = fiVerificationDto.getLandlordnameRdR();
		String strllremark = fiVerificationDto.getLandlordnameremarkRdR();
		String strllflag = fiVerificationDto.getLandlordnameflagRdR();

		String strllmob = fiVerificationDto.getLandlordmobilenoRdR();
		String strllmobremark = fiVerificationDto.getLandlordmobilenoremarkRdR();
		String strllmobflag = fiVerificationDto.getLandlordmobilenoflagRdR();

		String strrpm = fiVerificationDto.getRentpmRdR();
		String strrpmremark = fiVerificationDto.getRentpmremarkRdR();
		String strrpmflag = fiVerificationDto.getRentpmflagRdR();

		String strois = fiVerificationDto.getOtherincomesourceRdR();
		String stroisremark = fiVerificationDto.getOtherincomesourceremarkRdR();
		String stroisflag = fiVerificationDto.getOtherincomesourceflagRdR();

		String strpadd = fiVerificationDto.getPermanentaddressRdR();
		String strpaddremark = fiVerificationDto.getPermanentaddressremarkRdR();
		String strpaddflag = fiVerificationDto.getPermanentaddressflagRdR();

		String strnps = fiVerificationDto.getNearbypolicestationRdR();
		String strnpsremark = fiVerificationDto.getNearbypolicestationremarkRdR();
		String strnpsflag = fiVerificationDto.getNearbypolicestationflagRdR();

		String strlcnmobno = fiVerificationDto.getLcmobilenoRdR();
		String strlcnmobnoremark = fiVerificationDto.getLcmobilenoremarkRdR();
		String strlcnmobnoflag = fiVerificationDto.getLcmobilenoflagRdR();

		String strllrentfeedback = fiVerificationDto.getLlrentfeedbackRdR();
		String strllrentfeedbackremark = fiVerificationDto.getLlrentfeedbackremarkRdR();
		String strllrentfeedbackflag = fiVerificationDto.getLlrentfeedbackflagRdR();

		String strnoyrsinarea = fiVerificationDto.getNoyrsinareaRdR();
		String strnoyrsinarearemark = fiVerificationDto.getNoyrsinarearemarkRdR();
		String strnoyrsinareaflag = fiVerificationDto.getNoyrsinareaflagRdR();

		String strlc1chmnrecfeed = fiVerificationDto.getLc1chmnrecfeedRdR();
		String strlc1chmnrecfeedremark = fiVerificationDto.getLc1chmnrecfeedremarkRdR();
		String strlc1chmnrecfeedflag = fiVerificationDto.getLc1chmnrecfeedflagRdR();

		String strnearlmarkresi = fiVerificationDto.getNearlmarkresiRdR();
		String strnearlmarkresiremark = fiVerificationDto.getNearlmarkresiremarkRdR();
		String strnearlmarkresiflag = fiVerificationDto.getNearlmarkresiflagRdR();

		String stremptype = fiVerificationDto.getEmptypeRdR();
		String stremptyremark = fiVerificationDto.getEmptyperemarkRdR();
		String stremptypeflag = fiVerificationDto.getEmptypeflagRdR();

		String strstgorwrkadrssnearlmark = fiVerificationDto.getStgorwrkadrssnearlmarkRdR();
		String strstgorwrkadrssnearlmarkremark = fiVerificationDto.getStgorwrkadrssnearlmarkremarkRdR();
		String strstgorwrkadrssnearlmarkflag = fiVerificationDto.getStgorwrkadrssnearlmarkflagRdR();

		String strstgoremprecm = fiVerificationDto.getStgoremprecmRdR();
		String strstgoremprecmremark = fiVerificationDto.getStgoremprecmremarkRdR();
		String strstgoremprecmflag = fiVerificationDto.getStgoremprecmflagRdR();

		String strnoofyrsinstgorbusi = fiVerificationDto.getNoofyrsinstgorbusiRdR();
		String strnoofyrsinstgorbusiremark = fiVerificationDto.getNoofyrsinstgorbusiremarkRdR();
		String strnoofyrsinstgorbuisflag = fiVerificationDto.getNoofyrsinstgorbusinessflagRdR();

		String strstgnoofvehi = fiVerificationDto.getStgnoofvehiRdR();
		String strstgnoofvehiremark = fiVerificationDto.getStgnoofvehiremarkRdR();
		String strstgnoofvehiflag = fiVerificationDto.getStgnoofvehiflagRdR();

		String strbikeowner = fiVerificationDto.getBikeownerRdR();
		String strbikeownerremark = fiVerificationDto.getBikeownerremarkRdR();
		String strbikeownerflag = fiVerificationDto.getBikeownerflagRdR();

		String strnetincome = fiVerificationDto.getNetincomeRdR();
		String strnetincomeremark = fiVerificationDto.getNetincomeremarkRdR();
		String strnetincomeflag = fiVerificationDto.getNetincomeflagRdR();

		String strbikeusearea = fiVerificationDto.getBikeuseareaRdR();
		String strbikeusearearemark = fiVerificationDto.getBikeusearearemarkRdR();
		String strbikeuseareaflag = fiVerificationDto.getBikeuseareaflagRdR();

		String strspousename = fiVerificationDto.getSpousenameRdR();
		String strspousenameremark = fiVerificationDto.getSpousenameremarkRdR();
		String strspousenameflag = fiVerificationDto.getSpousenameflagRdR();

		String strspouseno = fiVerificationDto.getSpousenoRdR();
		String strspousenoremark = fiVerificationDto.getSpousenonoremarkRdR();
		String strspousenoflag = fiVerificationDto.getSpousenonoflagRdR();

		String strspouseconfirm = fiVerificationDto.getSpouseconfirmRdR();
		String strspouseconfirmremark = fiVerificationDto.getSpouseconfirmremarkRdR();
		String strspouseconfirmflag = fiVerificationDto.getSpouseconfirmflagRdR();


		String strrelawithapplicant = fiVerificationDto.getRelawithapplicantRdR();
		String strrelawithapplicantremark = fiVerificationDto.getRelawithapplicantremarkRdR();
		String strrelawithapplicantflag = fiVerificationDto.getRelawithapplicantflagRdR();

		String strpaymentbyrider = fiVerificationDto.getPaymentbyriderRdR();
		String strpaymentbyriderremark = fiVerificationDto.getPaymentbyriderremarkRdR();
		String strpaymentbyriderflag = fiVerificationDto.getPaymentbyriderflagRdR();

//		String strbikeapplied = fiVerificationDto.getBikeapplied();
//		String strbikeappliedremarks = fiVerificationDto.getBikeappliedremarks();
//		String strbikeappliedflag = fiVerificationDto.getBikeappliedflag();

		
		String strarrangebtwnrider = fiVerificationDto.getArrangebtwnriderRdR();
		String strarrangebtwnriderRemarks = fiVerificationDto.getArrangebtwnriderremarkRdR();
		String strarrangebtwnriderchkbx = fiVerificationDto.getArrangebtwnriderflagRdR();

		String strresiadrss = fiVerificationDto.getResiadrssRdR();
		String strresiadrssRemarks = fiVerificationDto.getResiadrssremarkRdR();
		String strresiadrsschkbx = fiVerificationDto.getResiadrssflagRdR();
		
		String strnoofyrsinaddrs = fiVerificationDto.getNoofyrinaddrssRdR();
		String strnoofyrsinaddrsRemarks = fiVerificationDto.getNoofyrinaddrssremarkRdR();
		String strnoofyrsinaddrschkbx = fiVerificationDto.getNoofyrinaddrssflagRdR();
		
		String strmbnumnotinname = fiVerificationDto.getMbnonotinnameRdR();
		String strmbnumnotinnameRemarks = fiVerificationDto.getMbnonotinnameremarkRdR();
		String strmbnumnotinnamechkbx = fiVerificationDto.getMbnonotinnameflagRdR();

		String strfiid = fiVerificationDto.getCfiid();
		String strfiid2 = fiVerificationDto.getCfiid2();

		strLoginuser = fiVerificationDto.getUsername();

		if (strCustid == null || strCustid.length() == 0) {

			strMsg = "Please select customer id";

		} else {

			if (strFiverdict.equals(strPosverdict)) {
				blnFiverdict = true;
			} else if (strFiverdict.equals(strNegverdict)) {
				blnFiverdict = false;
			}

			TimeStampUtil gts = new TimeStampUtil();
			strFIRequestdatetime = gts.TimeStamp();

			if (strfiid.length() > 0 && strfiid2.length() > 0) {
				Optional<RiderDetails> opCd = Optional.of(riderRepo.findBynationalid(strCustid));
				if (opCd.isPresent()) {
					RiderDetails rd = opCd.get();
					rd.setFiremarks(strRemarks);
					rd.setFiverified(blnFiverdict);
					rd.setFiuser(strLoginuser);
					rd.setFidatetime(strFIRequestdatetime);
					riderRepo.save(rd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			} else {
				blnFiverdict = false;
				Optional<RiderDetails> opCd = Optional.of(riderRepo.findBynationalid(strCustid));
				if (opCd.isPresent()) {
					RiderDetails rd = opCd.get();
					rd.setFiremarks(strRemarks);
					rd.setFiverified(blnFiverdict);
					rd.setFiuser(strLoginuser);
					rd.setFidatetime(strFIRequestdatetime);
					riderRepo.save(rd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			}


			FiVerification fiverify = new FiVerification();
			fiverify.setFiid(strCustid);

			fiverify.setFirstname(strFname);
			fiverify.setFirstnameremark(strfnameremark);
			fiverify.setFirstnameflag(strfnameflag);

			fiverify.setMaritalstatus(strms);
			fiverify.setMaritalstatusremark(strmsremark);
			fiverify.setMaritalstatusflag(strmsflag);
			fiverify.setMobileno(strmobno);
			fiverify.setMobilenoremark(strmobnoremark);
			fiverify.setMobilenoflag(strmobnoflag);
			fiverify.setStage(strstgnm);
			fiverify.setStageremark(strstgnmremark);
			fiverify.setStageflag(strstgnmflag);
			fiverify.setStagechairmanname(strstgchmname);
			fiverify.setStagechairmannameremarks(strstgchmnnameremark);
			fiverify.setStagechairmannameflag(strfnameflag);
			fiverify.setStagechairmanno(strstgchmnno);
			fiverify.setStagechairmannoremarks(strstgchmnnoremarks);
			fiverify.setStagechairmannoflag(strstgchmnnoflag);

			fiverify.setLc(strlcnm);
			fiverify.setLcremark(strlcnmremark);
			fiverify.setLcflag(strlcnmflag);

			fiverify.setNationalid(strnid);
			fiverify.setNationalidremark(strnidremark);
			fiverify.setNationalidflag(strnidflag);
			fiverify.setBikeregno(strbregno);
			fiverify.setBikeregnoremark(strbregnoremark);
			fiverify.setBikeregnoflag(strbregnoflag);
			fiverify.setBikeuse(strbuse);
			fiverify.setBikeuseremark(strbuseremark);
			fiverify.setBikeuseflag(strbuseflag);
			fiverify.setDob(strdob);
			fiverify.setDobremark(strdobremark);
			fiverify.setDobflag(strdobflag);

			fiverify.setOwnhouserented(stror);
			fiverify.setOwnhouserentedremark(strorremark);
			fiverify.setOwnhouserentedflag(strorflag);
			fiverify.setLandlordname(strll);
			fiverify.setLandlordnameremark(strllremark);
			fiverify.setLandlordnameflag(strllflag);
			fiverify.setLandlordmobileno(strllmob);
			fiverify.setLandlordmobilenoremark(strllmobremark);
			fiverify.setLandlordmobilenoflag(strllmobflag);
			fiverify.setRentpm(strrpm);
			fiverify.setRentpmremark(strrpmremark);
			fiverify.setRentpmflag(strrpmflag);
			fiverify.setOtherincomesource(strois);
			fiverify.setOtherincomesourceremark(stroisremark);
			fiverify.setOtherincomesourceflag(stroisflag);

			fiverify.setPermanentaddress(strpadd);
			fiverify.setPermanentaddressremark(strpaddremark);
			fiverify.setPermanentaddressflag(strpaddflag);

			fiverify.setNearbypolicestation(strnps);
			fiverify.setNearbypolicestationremark(strnpsremark);
			fiverify.setNearbypolicestationflag(strnpsflag);
			fiverify.setLcmobileno(strlcnmobno);
			fiverify.setLcmobilenoremark(strlcnmobnoremark);
			fiverify.setLcmobilenoflag(strlcnmobnoflag);

			fiverify.setLlrentfeedback(strllrentfeedback);
			fiverify.setLlrentfeedbackremarks(strllrentfeedbackremark);
			fiverify.setLlrentfeedbackflag(strllrentfeedbackflag);

			fiverify.setNoyrsinarea(strnoyrsinarea);
			fiverify.setNoyrsinarearemarks(strnoyrsinarearemark);
			fiverify.setNoyrsinareaflag(strnoyrsinareaflag);

			fiverify.setLc1chmnrecfeed(strlc1chmnrecfeed);
			fiverify.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
			fiverify.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);

			fiverify.setNearlmarkresi(strnearlmarkresi);
			fiverify.setNearlmarkresiremarks(strnearlmarkresiremark);
			fiverify.setNearlmarkresiflag(strnearlmarkresiflag);

			fiverify.setEmptype(stremptype);
			fiverify.setEmptyperemarks(stremptyremark);
			fiverify.setEmptypeflag(stremptypeflag);

			fiverify.setStgorwrkadrssnearlmark(strstgorwrkadrssnearlmark);
			fiverify.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
			fiverify.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);

			fiverify.setStgoremprecm(strstgoremprecm);
			fiverify.setStgoremprecmremarks(strstgoremprecmremark);
			fiverify.setStgoremprecmflag(strstgoremprecmflag);

			fiverify.setNoofyrsinstgorbusi(strnoofyrsinstgorbusi);
			fiverify.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
			fiverify.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);

			fiverify.setStgnoofvehi(strstgnoofvehi);
			fiverify.setStgnoofvehiremarks(strstgnoofvehiremark);
			fiverify.setStgnoofvehiflag(strstgnoofvehiflag);

			fiverify.setOwnerofbike(strbikeowner);
			fiverify.setOwnerofbikeremarks(strbikeownerremark);
			fiverify.setOwnerofbikeflag(strbikeownerflag);

			fiverify.setNetincome(strnetincome);
			fiverify.setNetincomeremarks(strnetincomeremark);
			fiverify.setNetincomeflag(strnetincomeflag);

			fiverify.setBikeusearea(strbikeusearea);
			fiverify.setBikeusearearemarks(strbikeusearearemark);
			fiverify.setBikeuseareaflag(strbikeuseareaflag);

			fiverify.setSpousename(strspousename);
			fiverify.setSpousenameremarks(strspousenameremark);
			fiverify.setSpousenameflag(strspousenameflag);

			fiverify.setSpouseno(strspouseno);
			fiverify.setSpousenoremarks(strspousenoremark);
			fiverify.setSpousenoflag(strspousenoflag);

			fiverify.setSpouseconfirm(strspouseconfirm);
			fiverify.setSpouseconfirmremarks(strspouseconfirmremark);
			fiverify.setSpouseconfirmflag(strspouseconfirmflag);

			fiverify.setRelawithapplicant(strrelawithapplicant);
			fiverify.setRelawithapplicantremarks(strrelawithapplicantremark);
			fiverify.setRelawithapplicantflag(strrelawithapplicantflag);

			fiverify.setPaymentbyrider(strpaymentbyrider);
			fiverify.setPaymentbyriderremarks(strpaymentbyriderremark);
			fiverify.setPaymentbyriderflag(strpaymentbyriderflag);

			// fiverify.setBikeapplied(strbikeapplied);
			// fiverify.setBikeappliedremarks(strbikeappliedremarks);
			// fiverify.setBikeappliedflag(strbikeappliedflag);

			fiverify.setArrangebtwnrider(strarrangebtwnrider);
			fiverify.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
			fiverify.setArrangebtwnriderflag(strarrangebtwnriderchkbx);

			fiverify.setResiadrss(strresiadrss);
			fiverify.setResiadrssremarks(strresiadrssRemarks);
			fiverify.setResiadrssflag(strresiadrsschkbx);

			fiverify.setNoofyrinaddrss(strnoofyrsinaddrs);
			fiverify.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
			fiverify.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);

			fiverify.setMbnonotinname(strmbnumnotinname);
			fiverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			fiverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

//			tvverify.setFirstguarantor(false);
//			tvverify.setSecondguarantor(false);
			fiverify.setFiremarks(strRemarks);
			fiverify.setFiverdict(blnFiverdict);
			
			
			try {
				fiVerifyRepo.save(fiverify);
				response.setData("FI Verification for : " + strCustid + " Rider Details saved successfully.");

			} catch (Exception e) {
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {

					Optional<FiVerification> optv = fiVerifyRepo.findBynationalid(strCustid);

					if (optv.isPresent()) {
						FiVerification fi = optv.get();
					
						fi.setFirstnameremark(strfnameremark);
						fi.setFirstnameflag(strfnameflag);

						fi.setMaritalstatusremark(strmsremark);
						fi.setMaritalstatusflag(strmsflag);

						fi.setMobilenoremark(strmobnoremark);
						fi.setMobilenoflag(strmobnoflag);
						fi.setStageremark(strstgnmremark);
						fi.setStageflag(strstgnmflag);
						fi.setStagechairmannameremarks(strstgchmnnameremark);
						fi.setStagechairmannameflag(strstgchmnameflag);
						fi.setStagechairmannoremarks(strstgchmnnoremarks);
						fi.setStagechairmannoflag(strstgchmnnoflag);

						fi.setLcremark(strlcnmremark);
						fi.setLcflag(strlcnmflag);

						fi.setNationalidremark(strnidremark);
						fi.setNationalidflag(strnidflag);
						fi.setBikeregnoremark(strbregnoremark);
						fi.setBikeregnoflag(strbregnoflag);
						fi.setBikeuseremark(strbuseremark);
						fi.setBikeuseflag(strbuseflag);
						fi.setDobremark(strdobremark);
						fi.setDobflag(strdobflag);

						fi.setOwnhouserentedremark(strorremark);
						fi.setOwnhouserentedflag(strorflag);
						fi.setLandlordnameremark(strllremark);
						fi.setLandlordnameflag(strllflag);
						fi.setLandlordmobilenoremark(strllmobremark);
						fi.setLandlordmobilenoflag(strllmobflag);
						fi.setRentpmremark(strrpmremark);
						fi.setRentpmflag(strrpmflag);
						fi.setOtherincomesourceremark(stroisremark);
						fi.setOtherincomesourceflag(stroisflag);

						fi.setPermanentaddressremark(strpaddremark);
						fi.setPermanentaddressflag(strpaddflag);

						fi.setNearbypolicestationremark(strnpsremark);
						fi.setNearbypolicestationflag(strnpsflag);
						fi.setLcmobilenoremark(strlcnmobnoremark);
						fi.setLcmobilenoflag(strlcnmobnoflag);

						fi.setFiremarks(strRemarks);
						fi.setFiverdict(blnFiverdict);

						fi.setLlrentfeedbackremarks(strllrentfeedbackremark);
						fi.setLlrentfeedbackflag(strllrentfeedbackflag);
						fi.setNoyrsinarearemarks(strnoyrsinarearemark);
						fi.setNoyrsinareaflag(strnoyrsinareaflag);
						fi.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
						fi.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);
						fi.setNearlmarkresiremarks(strnearlmarkresiremark);
						fi.setNearlmarkresiflag(strnearlmarkresiflag);
						fi.setEmptype(stremptypeflag);
						fi.setEmptypeflag(stremptypeflag);
						fi.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
						fi.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);
						fi.setStgoremprecmremarks(strstgoremprecmremark);
						fi.setStgoremprecmflag(strstgoremprecmflag);
						fi.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
						fi.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);
						fi.setStgnoofvehiremarks(strstgnoofvehiremark);
						fi.setStgnoofvehiflag(strstgnoofvehiflag);
						fi.setBikeownerremark(strbikeownerremark);
						fi.setBikeownerflag(strbikeownerflag);
						fi.setNetincomeremarks(strnetincomeremark);
						fi.setNetincomeflag(strnetincomeflag);
						fi.setBikeusearearemarks(strbikeusearearemark);
						fi.setBikeuseareaflag(strbikeuseareaflag);
						fi.setSpousenameremarks(strspousenameremark);
						fi.setSpousenameflag(strspousenameflag);
						fi.setSpousenoremarks(strspousenoremark);
						fi.setSpousenoflag(strspousenoflag);
						fi.setSpouseconfirmremarks(strspouseconfirmremark);
						fi.setSpouseconfirmflag(strspouseconfirmflag);

						fi.setRelawithapplicantremarks(strrelawithapplicantremark);
						fi.setRelawithapplicantflag(strrelawithapplicantflag);
						fi.setPaymentbyriderremarks(strpaymentbyriderremark);
						fi.setPaymentbyriderflag(strpaymentbyriderflag);

						fi.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
						fi.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
						fi.setResiadrssremarks(strresiadrssRemarks);
						fi.setResiadrssflag(strresiadrsschkbx);
						fi.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
						fi.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);
						fi.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						fi.setMbnonotinnameflag(strmbnumnotinnamechkbx);


						fiVerifyRepo.save(fi);
						response.setData("FI Verification: " + strCustid + " Rider Details updated successfully");
					}
				} else if (strMsg.contains("")) {
					strMsg = "General error";
				}
			}
		}
		return response;
	}


	public ResponseEntity<String> printFi(String custId) {
		
//		HttpSession session = request.getSession(true);
//		String strSessionid = (String) session.getAttribute("fiprofid");
		
		String strMsg="";
		String strQuery ="";
			String strDate = "";
			String strPrintDate = "";
			String strPdffilename="";
			
			//Customer details
			String strCustomer = "";	
			String strCustomerName = "";
			String strFlag = "";
			String strMobileno = "";
			String strStage = "";
			String strDistrict = "";
			String strCounty = "";
			String strSubcounty = "";
			String strParish = "";
			String strVillage = "";
			String strNationalid = "";
			String strSurname = "";
			String strFirstname = "";
			String strDob = "";
			String strOthername = "";
			String strRemarks = "";
			String strMaritalstatus = "";
			String strSex = "";
			String strBikeregno = "";
			String strBikeuse = "";
			String strYrsinvillage = "";	
			String strNok = ""; 
			String strNokmobile = "";
			String strNorship = "";
			boolean blnNoka = false;
			String strPermit = "";
			String strNationality = "";
			String strDependants = "";
			String strHouse = "";
			String strLandlord = "";
			String strLandlordmobile = ""; 
			String strRent = "";
			String strOis = "";
			String strDps = "";
			String strPadd = "";
			String strFather = "";
			String strMother = "";
			String strPolice = "";	
			String strLc = "";
			String strLcmobile = "";
			String strCusttype = "";
			
			String strTvremarks = "";
			String strFiremarks = "";
			String strCqcremarks = "";
			String strCoappremarks = "";
			String strRevremarks = "";
			String strTv = "";
			String strFi = "";
			String strCqc = "";
			String StrCoapp = "";
			
			boolean blnTv = false;
			boolean blnFi = false;
			boolean blnCqc = false;
			
			//Customer tvverification
			String strSurnameremark = "";
			String strSurnameflag = "";		 
			String strfnameremark = ""; 
			String strfnameflag = "";		
			String stronameremark = "";
			String stronameflag = "";		
			String strmsremark = "";
			String strmsflag = "";		
			String strsexremark = "";
			String strsexflag = "";		
			String strmobnoremark = "";
			String strmobnoflag = "";		
			String strstgnmremark = "";
			String strstgnmflag = "";		
			String strdistremark = "";
			String strdistflag = "";		
			String strlcnmremark = "";
			String strlcnmflag = "";		
			String strparremark = "";
			String strparflag = "";		
			String strnidremark = "";
			String strnidflag = "";		
			String strbregnoremark = "";
			String strbregnoflag = "";		
			String strbuseremark = "";
			String strbuseflag = "";		
			String strdobremark = "";
			String strdobflag = "";		
			String strcntremark = "";
			String strcntflag = "";		
			String strscntremark = "";
			String strscntflag = "";		
			String strvilremark = "";
			String strvilflag = "";		
			String stryrvilremark = "";
			String stryrvilflag = "";		
			String strnokremark = "";
			String strnokflag = "";		
			String strnokmremark = "";
			String strnokmflag = "";		
			String strnokrremark = "";
			String strnokrflag = "";		
			String strnokaremark = "";
			String strnokaflag = "";		
			String strdlremark = "";
			String strdlflag = "";		
			String strnatremark = "";
			String strnatflag = "";		
			String strnodremark = "";
			String strnodflag = "";		
			String strorremark = "";
			String strorflag = "";		
			String strllremark = "";
			String strllflag = "";		
			String strllmobremark = "";
			String strllmobflag = "";		
			String strrpmremark = "";
			String strrpmflag = "";		
			String stroisremark = "";
			String stroisflag = "";		
			String strdpsremark = "";
			String strdpsflg = "";		
			String strpaddremark = "";
			String strpaddflag = "";		
			String strfathremark = "";
			String strfathflag = "";		
			String strmothremark = "";
			String strmothflag = "";		
			String strnpsremark = "";
			String strnpsflag = "";		
			String strlcnmobnoremark = "";
			String strlcnmobnoflag = "";		
			String strcusttyperemark = "";
			String strcusttypeflag = "";
			
			boolean blnFirstguarantor = false;
			boolean blnSecondguarantor = false;
			
			//Guaran 1
			String strId1 = "";		
			String strSurname1 = "";
			String strFirstname1 = "";
			String strOthername1 = "";
			String strG1Name = "";
			String strNatid1 = "";
			String strMobno1 = "";
			String strAdd1 = "";
			String strPadd1 = "";
			String strYrsinadd1 = "";
			String strOr1 = "";
			String strRent1 = "";
			String strNok1 = "";
			String strNokmobno1 = "";
			String strNokrshp1 = "";
			boolean blnNoka1 = false;
			String strNoka1 = "";
			String strBkregno1 = "";
			String strBkowner1 = "";
			boolean blnSal1 = false;
			String strSal1 = "";
			String strEmpname1 = "";
			String strMnthin1 = "";
			String strOis1 = "";
			String strOi1 = "";
			String strStg1 = "";
			String strStgadd1 = "";
			boolean blnStgchconf1 = false;
			String strStgchconf1 = "";
			String strLc1 = "";
			
			String strTvremarks1 = "";
			String strFiremarks1 = "";
			String strCqcremarks1 = "";					
			
			boolean blnTv1 = false;
			boolean blnFi1 = false;
			boolean blnCqc1 = false;
			
			String strCoremarks1 = "";							
			String strCoapproval1 = "";	
			
			String strTv1 = "";
			String strFi1 = "";
			String strCqc1 = "";
			String StrCoapp1 = "";
			
			//Guaratnor 1 verification
			String strSurnameremark1 = "X";
			String strSurnameflag1 = "X";		 
			String strfnameremark1 = "X"; 
			String strfnameflag1 = "X";		
			String stronameremark1 = "X";
			String stronameflag1 = "X";		
			String strmsremark1 = "X";
			String strmsflag1 = "X";		
			String strsexremark1 = "X";
			String strsexflag1 = "X";		
			String strmobnoremark1 = "X";
			String strmobnoflag1 = "X";		
			String strstgnmremark1 = "X";
			String strstgnmflag1 = "X";		
			String strdistremark1 = "X";
			String strdistflag1 = "X";		
			String strlcnmremark1 = "X";
			String strlcnmflag1 = "X";		
			String strparremark1 = "X";
			String strparflag1 = "X";	
			String straddremark1 = "X";
			String straddflag1 = "X";
			String strnidremark1 = "X";
			String strnidflag1 = "X";		
			String strbregnoremark1 = "X";
			String strbregnoflag1 = "X";		
			String strbuseremark1 = "X";
			String strbuseflag1 = "X";		
			String strdobremark1 = "X";
			String strdobflag1 = "X";		
			String strcntremark1 = "X";
			String strcntflag1 = "X";		
			String strscntremark1 = "X";
			String strscntflag1 = "X";		
			String strvilremark1 = "X";
			String strvilflag1 = "X";		
			String stryrvilremark1 = "X";
			String stryrvilflag1 = "X";		
			String strnokremark1 = "X";
			String strnokflag1 = "X";		
			String strnokmremark1 = "X";
			String strnokmflag1 = "X";		
			String strnokrremark1 = "X";
			String strnokrflag1 = "X";		
			String strnokaremark1 = "X";
			String strnokaflag1 = "X";		
			String strdlremark1 = "X";
			String strdlflag1 = "X";		
			String strnatremark1 = "X";
			String strnatflag1 = "X";		
			String strnodremark1 = "X";
			String strnodflag1 = "X";		
			String strorremark1 = "X";
			String strorflag1 = "X";		
			String strllremark1 = "X";
			String strllflag1 = "X";		
			String strllmobremark1 = "X";
			String strllmobflag1 = "X";		
			String strrpmremark1 = "X";
			String strrpmflag1 = "X";		
			String stroisremark1 = "X";
			String stroisflag1 = "X";		
			String strdpsremark1 = "X";
			String strdpsflg1 = "X";		
			String strpaddremark1 = "X";
			String strpaddflag1 = "X";		
			String strfathremark1 = "X";
			String strfathflag1 = "X";		
			String strmothremark1 = "X";
			String strmothflag1 = "X";		
			String strnpsremark1 = "X";
			String strnpsflag1 = "X";		
			String strlcnmobnoremark1 = "X";
			String strlcnmobnoflag1 = "X";		
			String strcusttyperemark1 = "X";
			String strcusttypeflag1 = "X";		
			String strbownerremark1 = "X";
			String strbownerflag1 = "X";
			String strsalremark1 = "X";
			String strsalflag1 = "X";
			String strmiremark1 = "X";
			String strmiflag1 = "X";
			String stroiremark1 = "X";
			String stroiflag1 = "X";
			String strstgaddremark1 = "X";
			String strstgaddflag1 = "X";
			String strstgccremark1 = "X";
			String strstgccflag1 = "X";
			String strrltnshpremark1 = "X";
			String strrltnshpflag1 = "X";			
			
			//Guarantor 2
			String strId2 = "";		
			String strSurname2 = "";
			String strFirstname2 = "";
			String strOthername2 = "";
			String strG2Name = "";
			String strNatid2 = "";
			String strMobno2 = "";
			String strAdd2 = "";
			String strPadd2 = "";
			String strYrsinadd2 = "";
			String strOr2 = "";
			String strRent2 = "";
			String strNok2 = "";
			String strNokmobno2 = "";
			String strNokrshp2 = "";
			boolean blnNoka2 = false;
			String strNoka2 = "";
			String strBkregno2 = "";
			String strBkowner2 = "";
			boolean blnSal2 = false;
			String strSal2 = "";
			String strEmpname2 = "";
			String strMnthin2 = "";
			String strOis2 = "";
			String strOi2 = "";
			String strStg2 = "";
			String strStgadd2 = "";
			boolean blnStgchconf2 = false;
			String strStgchconf2 = "";
			String strLc2 = "";
			String strRltnshp1 = "";
			String strRltnshp2 = "";
			

			String strTvremarks2 = "";
			String strFiremarks2 = "";
			String strCqcremarks2 = "";					
			
			String strTv2 = "";
			String strFi2 = "";
			String strCqc2 = "";
			String StrCoapp2 = "";
			
			boolean blnTv2 = false;
			boolean blnFi2 = false;
			boolean blnCqc2 = false;
			
			String strCoremarks2 = "";							
			String strCoapproval2 = "";	
			
			//Guaratnor 2 verification
			String strSurnameremark2 = "X";
			String strSurnameflag2 = "X";		 
			String strfnameremark2 = "X"; 
			String strfnameflag2 = "X";		
			String stronameremark2 = "X";
			String stronameflag2 = "X";		
			String strmsremark2 = "X";
			String strmsflag2 = "X";		
			String strsexremark2 = "X";
			String strsexflag2 = "X";		
			String strmobnoremark2 = "X";
			String strmobnoflag2 = "X";		
			String strstgnmremark2 = "X";
			String strstgnmflag2 = "X";		
			String strdistremark2 = "X";
			String strdistflag2 = "X";		
			String strlcnmremark2 = "X";
			String strlcnmflag2 = "X";		
			String strparremark2 = "X";
			String strparflag2 = "X";	
			String straddremark2 = "X";
			String straddflag2 = "X";
			String strnidremark2 = "X";
			String strnidflag2 = "X";		
			String strbregnoremark2 = "X";
			String strbregnoflag2 = "X";		
			String strbuseremark2 = "X";
			String strbuseflag2 = "X";		
			String strdobremark2 = "X";
			String strdobflag2 = "X";		
			String strcntremark2 = "X";
			String strcntflag2 = "X";		
			String strscntremark2 = "X";
			String strscntflag2 = "X";		
			String strvilremark2 = "X";
			String strvilflag2 = "X";		
			String stryrvilremark2 = "X";
			String stryrvilflag2 = "X";		
			String strnokremark2 = "X";
			String strnokflag2 = "X";		
			String strnokmremark2 = "X";
			String strnokmflag2 = "X";		
			String strnokrremark2 = "X";
			String strnokrflag2 = "X";		
			String strnokaremark2 = "X";
			String strnokaflag2 = "X";		
			String strdlremark2 = "X";
			String strdlflag2 = "X";		
			String strnatremark2 = "X";
			String strnatflag2 = "X";		
			String strnodremark2 = "X";
			String strnodflag2 = "X";		
			String strorremark2 = "X";
			String strorflag2 = "X";		
			String strllremark2 = "X";
			String strllflag2 = "X";		
			String strllmobremark2 = "X";
			String strllmobflag2 = "X";		
			String strrpmremark2 = "X";
			String strrpmflag2 = "X";		
			String stroisremark2 = "X";
			String stroisflag2 = "X";		
			String strdpsremark2 = "X";
			String strdpsflg2 = "X";		
			String strpaddremark2 = "X";
			String strpaddflag2 = "X";		
			String strfathremark2 = "X";
			String strfathflag2 = "X";		
			String strmothremark2 = "X";
			String strmothflag2 = "X";		
			String strnpsremark2 = "X";
			String strnpsflag2 = "X";		
			String strlcnmobnoremark2 = "X";
			String strlcnmobnoflag2 = "X";		
			String strcusttyperemark2 = "X";
			String strcusttypeflag2 = "X";		
			String strbownerremark2 = "X";
			String strbownerflag2 = "X";
			String strsalremark2 = "X";
			String strsalflag2 = "X";
			String strmiremark2 = "X";
			String strmiflag2 = "X";
			String stroiremark2 = "X";
			String stroiflag2 = "X";
			String strstgaddremark2 = "X";
			String strstgaddflag2 = "X";
			String strstgccremark2 = "X";
			String strstgccflag2 = "X";
			String strrltnshpremark2 = "X";
			String strrltnshpflag2 = "X";
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    ObjectMetadata metadata = new ObjectMetadata();
		    URL url = null;
		    Document document = new Document();
			HttpHeaders headers = new HttpHeaders();
			InputStreamResource resource1 = null ;
			File newfile = null ;
			
			int intCount = 0;
						
			Response<String> response = new Response<>();
			strCustomer = custId;
			strCustomer = strCustomer.trim();
			
			formatDigits fd = new formatDigits();
								
			if (strCustomer == null || strCustomer.length() == 0) {			
				strMsg = "Please select customer";		
			} else {									
				TimeStampUtil gts = new TimeStampUtil();		
				strPrintDate = gts.TimeStamp();
				strDate = gts.standardDate();					
								
				try {					
//					strQuery = "From CustomerDetails where otherid = '" + strCustomer + "'";
					CustomerDetails cd = customerRepo.findByotherid(strCustomer);
									
						strDob = cd.getDob();
						strFirstname = cd.getFirstname();
						strOthername = cd.getOthername();
						strSurname = cd.getSurname();
						strCustomerName = strSurname + " " + strFirstname; //Rectify for long company names
						strRemarks = cd.getRemarks();
						strMaritalstatus = cd.getMaritalstatus();
						strSex = cd.getSex();	
						strMobileno = cd.getMobileno();
						strStage = cd.getStage();
						strBikeregno = cd.getCurrentbikeregno();
						strBikeuse = cd.getNewbikeuse();
						strDistrict = cd.getDistrict();
						strCounty = cd.getCounty();
						strSubcounty = cd.getSubcounty();
						strParish = cd.getParish();
						strVillage = cd.getVillage();
						strNationalid = cd.getNationalid();
						strYrsinvillage = cd.getYearsinvillage();	
						strNok = cd.getNextofkin(); 
						strNokmobile = cd.getNokmobileno();
						strNorship = cd.getNokrelationship();
						blnNoka = cd.getNokagreeing();
						strPermit = cd.getDrivingpermit();
						strNationality = cd.getNationality();
						strDependants = cd.getNoofdependants();
						strHouse = cd.getOwnhouserented();
						strLandlord = cd.getLandlordname();
						strLandlordmobile = cd.getLandlordmobileno(); 
						strRent = cd.getRentpm();
						strOis = cd.getOtherincomesource();
						strDps = cd.getDownpaymentsource();
						strPadd = cd.getPermanentaddress();
						strFather = cd.getFathersname();
						strMother = cd.getMothersname();
						strPolice = cd.getNearbypolicestation();
						strLc = cd.getLc();
						strLcmobile = cd.getLcmobileno();
						strCusttype = cd.getCusttype();
						
						strTvremarks = cd.getTvremarks();
						strFiremarks = cd.getFiremarks();
						strCqcremarks = cd.getCqcremarks();	
						strCoappremarks = cd.getCoremarks();
						StrCoapp = cd.getCoapproval();
						
						blnTv = cd.getTvverified();
						blnFi = cd.getFiverified();
						blnCqc = cd.getCqc();
						
						strRevremarks = cd.getRevremarks();
						if (strRevremarks==null) {strRevremarks = "";}
					
					if (blnTv == true) {strTv = "Recommended";} else {strTv = "Not Recommended";}
					if (blnFi == true) {strFi = "Recommended";} else {strFi = "Not Recommended";}
					if (blnCqc == true) {strCqc = "Recommended";} else {strCqc = "Not Recommended";}
					
					//load customer tvverifiation details
//					strQuery = "From FiVerification where fiid = '" + strCustomer + "'";
					
					try {
						Optional<FiVerification> opfiverification = fiVerifyRepo.findById(strCustomer);
						
						if(opfiverification.isPresent()) {
							
							FiVerification fi = opfiverification.get();
							
							strSurnameremark = fi.getSurnameremark();
							strSurnameflag = fi.getSurnameflag();
							if (strSurnameflag.equals("false")) {strSurnameflag = "";}
							strfnameremark = fi.getFirstnameremark(); 
							strfnameflag = fi.getFirstnameflag();	
							if (strfnameflag.equals("false")) {strfnameflag = "";}
							stronameremark = fi.getOthernameremark();
							stronameflag = fi.getOthernameflag();	
							if (stronameflag.equals("false")) {stronameflag = "";}
							strmsremark = fi.getMaritalstatusremark();
							strmsflag = fi.getMaritalstatusflag();		
							if (strmsflag.equals("false")) {strmsflag = "";}
							strsexremark = fi.getSexremark();
							strsexflag = fi.getSexflag();	
							if (strsexflag.equals("false")) {strsexflag = "";}
							strmobnoremark = fi.getMobilenoremark();
							strmobnoflag = fi.getMobilenoflag();	
							if (strmobnoflag.equals("false")) {strmobnoflag = "";}
							strstgnmremark = fi.getStageremark();
							strstgnmflag = fi.getStageflag();	
							if (strstgnmflag.equals("false")) {strstgnmflag = "";}
							strdistremark = fi.getDistrictremark();
							strdistflag = fi.getDistrictflag();	
							if (strdistflag.equals("false")) {strdistflag = "";}
							strlcnmremark = fi.getLcremark();
							strlcnmflag = fi.getLcflag();	
							if (strlcnmflag.equals("false")) {strlcnmflag = "";}
							strparremark = fi.getParishremark();
							strparflag = fi.getParishflag();	
							if (strparflag.equals("false")) {strparflag = "";}
							strnidremark = fi.getNationalidremark();
							strnidflag = fi.getNationalidflag();	
							if (strnidflag.equals("false")) {strnidflag = "";}
							strbregnoremark = fi.getBikeregnoremark();
							strbregnoflag = fi.getBikeregnoflag();	
							if (strbregnoflag.equals("false")) {strbregnoflag = "";}
							strbuseremark = fi.getBikeuseremark();
							strbuseflag = fi.getBikeuseflag();	
							if (strbuseflag.equals("false")) {strbuseflag = "";}
							strdobremark = fi.getDobremark();
							strdobflag = fi.getDobflag();	
							if (strdobflag.equals("false")) {strdobflag = "";}
							strcntremark = fi.getCountyremark();
							strcntflag = fi.getCountyflag();	
							if (strcntflag.equals("false")) {strcntflag = "";}
							strscntremark = fi.getSubcountyremark();
							strscntflag = fi.getSubcountyflag();	
							if (strscntflag.equals("false")) {strscntflag = "";}
							strvilremark = fi.getVillageremark();
							strvilflag = fi.getVillageflag();	
							if (strvilflag.equals("false")) {strvilflag = "";}
							stryrvilremark = fi.getYearsinvillageremark();
							stryrvilflag = fi.getYearsinvillageflag();	
							if (stryrvilflag.equals("false")) {stryrvilflag = "";}
							strnokremark = fi.getNextofkinremark();
							strnokflag = fi.getNextofkinflag();	
							if (strnokflag.equals("false")) {strnokflag = "";}
							strnokmremark = fi.getNokmobilenoremark();
							strnokmflag = fi.getNokmobilenoflag();	
							if (strnokmflag.equals("false")) {strnokmflag = "";}
							strnokrremark = fi.getNokrelationshipremark();
							strnokrflag = fi.getNokrelationshipflag();	
							if (strnokrflag.equals("false")) {strnokrflag = "";}
							strnokaremark = fi.getNokagreeingremark();
							strnokaflag = fi.getNokagreeingflag();	
							if (strnokaflag.equals("false")) {strnokaflag = "";}
							strdlremark = fi.getDrivingpermitremark();
							strdlflag = fi.getDrivingpermitflag();		
							if (strdlflag.equals("false")) {strdlflag = "";}
							strnatremark = fi.getNationalityremark();
							strnatflag = fi.getNationalityflag();	
							if (strnatflag.equals("false")) {strnatflag = "";}
							strnodremark = fi.getNoofdependantsremark();
							strnodflag = fi.getNoofdependantsflag();	
							if (strnodflag.equals("false")) {strnodflag = "";}
							strorremark = fi.getOwnhouserentedremark();
							strorflag = fi.getOwnhouserentedflag();		
							if (strorflag.equals("false")) {strorflag = "";}
							strllremark = fi.getLandlordnameremark();
							strllflag = fi.getLandlordnameflag();		
							if (strllflag.equals("false")) {strllflag = "";}
							strllmobremark = fi.getLandlordmobilenoremark();
							strllmobflag = fi.getLandlordmobilenoflag();	
							if (strllmobflag.equals("false")) {strllmobflag = "";}
							strrpmremark = fi.getRentpmremark();
							strrpmflag = fi.getRentpmflag();	
							if (strrpmflag.equals("false")) {strrpmflag = "";}
							stroisremark = fi.getOtherincomesourceremark();
							stroisflag = fi.getOtherincomesourceflag();	
							if (stroisflag.equals("false")) {stroisflag = "";}
							strdpsremark = fi.getDownpaymentsourceremark();
							strdpsflg = fi.getDownpaymentsourceflag();		
							if (strdpsflg.equals("false")) {strdpsflg = "";}
							strpaddremark = fi.getPermanentaddressremark();
							strpaddflag = fi.getPermanentaddressflag();	
							if (strpaddflag.equals("false")) {strpaddflag = "";}
							strfathremark = fi.getFathersnameremark();
							strfathflag = fi.getFathersnameflag();	
							if (strfathflag.equals("false")) {strfathflag = "";}
							strmothremark = fi.getMothersnameremark();
							strmothflag = fi.getMothersnameflag();	
							if (strmothflag.equals("false")) {strmothflag = "";}
							strnpsremark = fi.getNearbypolicestationremark();
							strnpsflag = fi.getNearbypolicestationflag();	
							if (strnpsflag.equals("false")) {strnpsflag = "";}
							strlcnmobnoremark = fi.getLcmobilenoremark();
							strlcnmobnoflag = fi.getLcmobilenoflag();	
							if (strlcnmobnoflag.equals("false")) {strlcnmobnoflag = "";}
							strcusttyperemark = fi.getCusttyperemark();
							strcusttypeflag = fi.getCusttypeflag();
							if (strcusttypeflag.equals("false")) {strcusttypeflag = "";}
						}
									
					} catch (Exception e) {
						System.out.println(e.getLocalizedMessage());
						strMsg = "Error retrieving customer id: " + strCustomer;
					}
					
					//Guarantors					
//					strQuery = "From Guarantor where custid = '" + strCustomer + "'";
					
					try {
						List<Guarantor> guarantor = guarantorRepo.findBycustid(strCustomer);							
						if (guarantor.size() != 0) {
							for (Guarantor gr:guarantor) {
								blnFirstguarantor = gr.getFirstguarantor();
								blnSecondguarantor = gr.getSecondguarantor();
								if (blnFirstguarantor == true) {
									strId1 = Integer.toString(gr.getId());
									strSurname1 = gr.getSurname();
									strFirstname1 = gr.getFirstname();
									strOthername1 = gr.getOthername();
									strG1Name = strSurname1 + " " + strFirstname1;
									strNatid1 = gr.getNationalid();
									strMobno1 = gr.getMobileno();
									strAdd1 = gr.getAddress();
									strPadd1 = gr.getPermanentaddress();
									strYrsinadd1 = gr.getYrsinaddress();
									strOr1 = gr.getOwnhouserented();
									strRent1 = gr.getRentpm();
									strNok1 = gr.getNextofkin();
									strNokmobno1 = gr.getNokmobileno();
									strNokrshp1 = gr.getNokrelationship();
									blnNoka1 = gr.getNokagreeing();
									if(blnNoka1 == true) {strNoka1 = "true";} else {strNoka1 = "false";}
									strBkregno1 = gr.getBikeregno();
									strBkowner1 = gr.getBikeowner();
									blnSal1 = gr.getSalaried();
									if(blnSal1 == true) {strSal1 = "true";} else {strSal1 = "false";}
									strEmpname1 = gr.getEmployername();
									strMnthin1 = gr.getMonthlyincome();
									strOis1 = gr.getOis();
									strOi1 = gr.getOtherincome();
									strStg1 = gr.getStagename();
									strStgadd1 = gr.getStageaddress();
									blnStgchconf1 = gr.getStagechairconf();
									if(blnStgchconf1 == true) {strStgchconf1 = "true";} else {strStgchconf1 = "false";}
									strLc1 = gr.getLcname();
									strRltnshp1 = gr.getRelationship();
									
									strTvremarks1 = gr.getTvremarks();
									strFiremarks1 = gr.getFiremarks();
									strCqcremarks1 = gr.getCqcremarks();					
									
									blnTv1 = gr.getTvverified();
									blnFi1 = gr.getFiverified();
									blnCqc1 = gr.getCqc();
									
									strCoremarks1 = gr.getCoremarks();							
									strCoapproval1 = gr.getCoapproval();					
			
								} else if (blnSecondguarantor == true) {
									strId2 = Integer.toString(gr.getId());
									strSurname2 = gr.getSurname();
									strFirstname2 = gr.getFirstname();
									strOthername2 = gr.getOthername();
									strG2Name = strSurname2 + " " + strFirstname2;
									strNatid2 = gr.getNationalid();
									strMobno2 = gr.getMobileno();
									strAdd2 = gr.getAddress();
									strPadd2 = gr.getPermanentaddress();
									strYrsinadd2 = gr.getYrsinaddress();
									strOr2 = gr.getOwnhouserented();
									strRent2 = gr.getRentpm();
									strNok2 = gr.getNextofkin();
									strNokmobno2 = gr.getNokmobileno();
									strNokrshp2 = gr.getNokrelationship();
									blnNoka2 = gr.getNokagreeing();
									if(blnNoka2 == true) {strNoka2 = "true";} else {strNoka2 = "false";}
									strBkregno2 = gr.getBikeregno();
									strBkowner2 = gr.getBikeowner();
									blnSal2 = gr.getSalaried();
									if(blnSal2 == true) {strSal2 = "true";} else {strSal2 = "false";}
									strEmpname2 = gr.getEmployername();
									strMnthin2 = gr.getMonthlyincome();
									strOis2 = gr.getOis();
									strOi2 = gr.getOtherincome();
									strStg2 = gr.getStagename();
									strStgadd2 = gr.getStageaddress();
									blnStgchconf2 = gr.getStagechairconf();
									if(blnStgchconf2 == true) {strStgchconf2 = "true";} else {strStgchconf2 = "false";}
									strLc2 = gr.getLcname();
									strRltnshp2 = gr.getRelationship();
									
									strTvremarks2 = gr.getTvremarks();
									strFiremarks2 = gr.getFiremarks();
									strCqcremarks2 = gr.getCqcremarks();					
									
									blnTv2 = gr.getTvverified();
									blnFi2 = gr.getFiverified();
									blnCqc2 = gr.getCqc();
									
									strCoremarks2 = gr.getCoremarks();
									strCoapproval2 = gr.getCoapproval();
								}
								blnFirstguarantor = false;
								blnSecondguarantor = false;
							}	
							
							if (blnTv1 == true) {strTv1 = "Recommended";} else {strTv1 = "Not Recommended";}
							if (blnFi1 == true) {strFi1 = "Recommended";} else {strFi1 = "Not Recommended";}
							if (blnCqc1 == true) {strCqc1 = "Recommended";} else {strCqc1 = "Not Recommended";}
							
							if (blnTv2 == true) {strTv2 = "Recommended";} else {strTv2 = "Not Recommended";}
							if (blnFi2 == true) {strFi2 = "Recommended";} else {strFi2 = "Not Recommended";}
							if (blnCqc2 == true) {strCqc2 = "Recommended";} else {strCqc2 = "Not Recommended";}
												
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						strMsg = "Error retrieving guarantors for customer id: " + strCustomer;
					}
					
					//guarantor tvverification
					if (strId1.length() > 0) {
						//*******************************************************************
						
//						strQuery = "From FiVerification where fiid = '" + strId1 + "'";
						
						try {
							Optional<FiVerification> fiverification = fiVerifyRepo.findById(strId1);
							
							if(fiverification.isPresent()) {
								
								FiVerification fi = fiverification.get();
								
								strSurnameremark1 = fi.getSurnameremark();
								strSurnameflag1 = fi.getSurnameflag();
								if (strSurnameflag1.equals("false")) {strSurnameflag1 = "";}
								strfnameremark1 = fi.getFirstnameremark(); 
								strfnameflag1 = fi.getFirstnameflag();	
								if (strfnameflag1.equals("false")) {strfnameflag1 = "";}
								stronameremark1 = fi.getOthernameremark();
								stronameflag1 = fi.getOthernameflag();	
								if (stronameflag1.equals("false")) {stronameflag1 = "";}
								strmobnoremark1 = fi.getMobilenoremark();
								strmobnoflag1 = fi.getMobilenoflag();	
								if (strmobnoflag1.equals("false")) {strmobnoflag1 = "";}
								strstgnmremark1 = fi.getStageremark();
								strstgnmflag1 = fi.getStageflag();	
								if (strstgnmflag1.equals("false")) {strstgnmflag1 = "";}
								straddremark1 = fi.getAddressremark();
								straddflag1 = fi.getAddressflag();
								if (straddflag1.equals("false")) {straddflag1 = "";}
								strlcnmremark1 = fi.getLcremark();
								strlcnmflag1 = fi.getLcflag();	
								if (strlcnmflag1.equals("false")) {strlcnmflag1 = "";}
								strnidremark1 = fi.getNationalidremark();
								strnidflag1 = fi.getNationalidflag();	
								if (strnidflag1.equals("false")) {strnidflag1 = "";}
								strbregnoremark1 = fi.getBikeregnoremark();
								strbregnoflag1 = fi.getBikeregnoflag();	
								if (strbregnoflag1.equals("false")) {strbregnoflag1 = "";}
								stryrvilremark1 = fi.getYearsinaddressremark();
								stryrvilflag1 = fi.getYearsinaddressflag();
								if (stryrvilflag1.equals("false")) {stryrvilflag1 = "";}
								strnokremark1 = fi.getNextofkinremark();
								strnokflag1 = fi.getNextofkinflag();	
								if (strnokflag1.equals("false")) {strnokflag1 = "";}
								strnokmremark1 = fi.getNokmobilenoremark();
								strnokmflag1 = fi.getNokmobilenoflag();	
								if (strnokmflag1.equals("false")) {strnokmflag1 = "";}
								strnokrremark1 = fi.getNokrelationshipremark();
								strnokrflag1 = fi.getNokrelationshipflag();	
								if (strnokrflag1.equals("false")) {strnokrflag1 = "";}
								strnokaremark1 = fi.getNokagreeingremark();
								strnokaflag1 = fi.getNokagreeingflag();	
								if (strnokaflag1.equals("false")) {strnokaflag1 = "";}
								strorremark1 = fi.getOwnhouserentedremark();
								strorflag1 = fi.getOwnhouserentedflag();		
								if (strorflag1.equals("false")) {strorflag1 = "";}
								strrpmremark1 = fi.getRentpmremark();
								strrpmflag1 = fi.getRentpmflag();	
								if (strrpmflag1.equals("false")) {strrpmflag1 = "";}
								stroisremark1 = fi.getOtherincomesourceremark();
								stroisflag1 = fi.getOtherincomesourceflag();	
								if (stroisflag1.equals("false")) {stroisflag1 = "";}
								strpaddremark1 = fi.getPermanentaddressremark();
								strpaddflag1 = fi.getPermanentaddressflag();	
								if (strpaddflag1.equals("false")) {strpaddflag1 = "";}		
								strbownerremark1 = fi.getBikeownerremark();
								strbownerflag1 = fi.getBikeownerflag();
								if (strbownerflag1.equals("false")) {strbownerflag1 = "";}
								strsalremark1 = fi.getSalariedremark();
								strsalflag1 = fi.getSalariedflag();
								if (strsalflag1.equals("false")) {strsalflag1 = "";}
								strmiremark1 = fi.getMonthlyincomeremark();
								strmiflag1 = fi.getMonthlyincomeflag();
								if (strmiflag1.equals("false")) {strmiflag1 = "";}
								stroiremark1 = fi.getOtherincomeremark();
								stroiflag1 = fi.getOtherincomeflag();
								if (stroiflag1.equals("false")) {stroiflag1 = "";}
								strstgaddremark1 = fi.getStageaddressremark();
								strstgaddflag1 = fi.getStageaddressflag();
								if (strstgaddflag1.equals("false")) {strstgaddflag1 = "";}
								strstgccremark1 = fi.getStagechairconfirmationremark();
								strstgccflag1 = fi.getStagechairconfirmationflag();
								if (strstgccflag1.equals("false")) {strstgccflag1 = "";}
								strrltnshpremark1 = fi.getRelationshipremark();
								strrltnshpflag1 = fi.getRelationshipflag();
								if (strrltnshpflag1.equals("false")) {strrltnshpflag1 = "";}
								
							}										
						} catch (Exception e) {
							System.out.println(e.getLocalizedMessage());
							strMsg = "Error retrieving guarantor id: " + strId1;
						}
					}
					
					if (strId2.length() > 0) {
						//*******************************************************************
						
//						strQuery = "From FiVerification where fiid = '" + strId2 + "'";
						
						try {
							Optional<FiVerification> fiverification = fiVerifyRepo.findById(strId2);
							
							if(fiverification.isPresent()) {
								
								FiVerification fi = fiverification.get();
								
								strSurnameremark2 = fi.getSurnameremark();
								strSurnameflag2 = fi.getSurnameflag();
								if (strSurnameflag2.equals("false")) {strSurnameflag2 = "";}
								strfnameremark2 = fi.getFirstnameremark(); 
								strfnameflag2 = fi.getFirstnameflag();	
								if (strfnameflag2.equals("false")) {strfnameflag2 = "";}
								stronameremark2 = fi.getOthernameremark();
								stronameflag2 = fi.getOthernameflag();	
								if (stronameflag2.equals("false")) {stronameflag2 = "";}
								strmobnoremark2 = fi.getMobilenoremark();
								strmobnoflag2 = fi.getMobilenoflag();	
								if (strmobnoflag2.equals("false")) {strmobnoflag2 = "";}
								strstgnmremark2 = fi.getStageremark();
								strstgnmflag2 = fi.getStageflag();	
								if (strstgnmflag2.equals("false")) {strstgnmflag2 = "";}
								straddremark2 = fi.getAddressremark();
								straddflag2 = fi.getAddressflag();
								if (straddflag2.equals("false")) {straddflag2 = "";}
								strlcnmremark2 = fi.getLcremark();
								strlcnmflag2 = fi.getLcflag();	
								if (strlcnmflag2.equals("false")) {strlcnmflag2 = "";}
								strnidremark2 = fi.getNationalidremark();
								strnidflag2 = fi.getNationalidflag();	
								if (strnidflag2.equals("false")) {strnidflag2 = "";}
								strbregnoremark2 = fi.getBikeregnoremark();
								strbregnoflag2 = fi.getBikeregnoflag();	
								if (strbregnoflag2.equals("false")) {strbregnoflag2 = "";}
								stryrvilremark2 = fi.getYearsinaddressremark();
								stryrvilflag2 = fi.getYearsinaddressflag();
								if (stryrvilflag2.equals("false")) {stryrvilflag2 = "";}
								strnokremark2 = fi.getNextofkinremark();
								strnokflag2 = fi.getNextofkinflag();	
								if (strnokflag2.equals("false")) {strnokflag2 = "";}
								strnokmremark2 = fi.getNokmobilenoremark();
								strnokmflag2 = fi.getNokmobilenoflag();	
								if (strnokmflag2.equals("false")) {strnokmflag2 = "";}
								strnokrremark2 = fi.getNokrelationshipremark();
								strnokrflag2 = fi.getNokrelationshipflag();	
								if (strnokrflag2.equals("false")) {strnokrflag2 = "";}
								strnokaremark2 = fi.getNokagreeingremark();
								strnokaflag2 = fi.getNokagreeingflag();	
								if (strnokaflag2.equals("false")) {strnokaflag2 = "";}
								strorremark2 = fi.getOwnhouserentedremark();
								strorflag2 = fi.getOwnhouserentedflag();		
								if (strorflag2.equals("false")) {strorflag2 = "";}
								strrpmremark2 = fi.getRentpmremark();
								strrpmflag2 = fi.getRentpmflag();	
								if (strrpmflag2.equals("false")) {strrpmflag2 = "";}
								stroisremark2 = fi.getOtherincomesourceremark();
								stroisflag2 = fi.getOtherincomesourceflag();	
								if (stroisflag2.equals("false")) {stroisflag2 = "";}
								strpaddremark2 = fi.getPermanentaddressremark();
								strpaddflag2 = fi.getPermanentaddressflag();	
								if (strpaddflag2.equals("false")) {strpaddflag2 = "";}		
								strbownerremark2 = fi.getBikeownerremark();
								strbownerflag2 = fi.getBikeownerflag();
								if (strbownerflag2.equals("false")) {strbownerflag2 = "";}
								strsalremark2 = fi.getSalariedremark();
								strsalflag2 = fi.getSalariedflag();
								if (strsalflag2.equals("false")) {strsalflag2 = "";}
								strmiremark2 = fi.getMonthlyincomeremark();
								strmiflag2 = fi.getMonthlyincomeflag();
								if (strmiflag2.equals("false")) {strmiflag2 = "";}
								stroiremark2 = fi.getOtherincomeremark();
								stroiflag2 = fi.getOtherincomeflag();
								if (stroiflag2.equals("false")) {stroiflag2 = "";}
								strstgaddremark2 = fi.getStageaddressremark();
								strstgaddflag2 = fi.getStageaddressflag();
								if (strstgaddflag2.equals("false")) {strstgaddflag2 = "";}
								strstgccremark2 = fi.getStagechairconfirmationremark();
								strstgccflag2 = fi.getStagechairconfirmationflag();
								if (strstgccflag2.equals("false")) {strstgccflag2 = "";}
								strrltnshpremark2 = fi.getRelationshipremark();
								strrltnshpflag2 = fi.getRelationshipflag();
								if (strrltnshpflag2.equals("false")) {strrltnshpflag2 = "";}
								
							}										
						} catch (Exception e) {
							System.out.println(e.getLocalizedMessage());
							strMsg = "Error retrieving guarantor id: " + strId2;
						}
					}
					
					
				}catch (Exception e) {}
								
				try {
//					strPdffilename = "src/main/resources/Documents/FiVerification" + strCustomer + ".pdf";
//					Document document = new Document();
					PdfWriter.getInstance(document, outputStream);
					document.open();
					Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
					Font bldFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
										
					//section 0					
					PdfPTable hdrtbl = new PdfPTable(2);
					hdrtbl.setWidthPercentage(100);
					hdrtbl.setHorizontalAlignment(Element.ALIGN_LEFT);
					hdrtbl.setWidths(new int[]{50,50});
					hdrtbl.setTotalWidth(100);
					
					PdfPCell lblblnk3 = new PdfPCell(new Phrase("FIELD VERIFICATION:", bldFont));
					lblblnk3.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lblblnk3);
					PdfPCell lbldate = new PdfPCell(new Phrase("Date:  " + strPrintDate, font));
					lbldate.setHorizontalAlignment(Element.ALIGN_RIGHT);
					lbldate.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lbldate);
					
					PdfPCell lblblnk1 = new PdfPCell(new Phrase("\n"));
					lblblnk1.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lblblnk1);
					PdfPCell lblblnk2 = new PdfPCell(new Phrase("\n"));
					lblblnk2.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lblblnk2);
					
					PdfPCell lblvehicle = new PdfPCell(new Phrase("CUSTOMER:  " + strCustomerName, bldFont));
					lblvehicle.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lblvehicle);
					PdfPCell lblblnk4 = new PdfPCell(new Phrase(""));
					lblblnk4.setBorderColor(BaseColor.WHITE);
					hdrtbl.addCell(lblblnk4);
					
					document.add(hdrtbl);					
					document.add(new Paragraph(""));
										
					//section I					
					PdfPTable tbl = new PdfPTable(4);
					tbl.setWidthPercentage(100);
					tbl.setHorizontalAlignment(Element.ALIGN_LEFT);
					tbl.setWidths(new int[]{25,25,25,25});
					tbl.setTotalWidth(100);
					addCustTableHeader(tbl);
					
					//add table rows					
					PdfPCell lblcustid = new PdfPCell(new Phrase("Customer Id: ",font));
					tbl.addCell(lblcustid);
					PdfPCell custid = new PdfPCell(new Phrase(strCustomer,font));
					tbl.addCell(custid);
					PdfPCell custidremark = new PdfPCell(new Phrase("",font));
					tbl.addCell(custidremark);
					PdfPCell custidflag = new PdfPCell(new Phrase("",font));
					tbl.addCell(custidflag);
					
					PdfPCell lblnatid = new PdfPCell(new Phrase("National Id: ",font));
					tbl.addCell(lblnatid);
					PdfPCell natid = new PdfPCell(new Phrase(strNationalid,font));
					tbl.addCell(natid);
					PdfPCell natidremark = new PdfPCell(new Phrase(strnidremark,font));
					tbl.addCell(natidremark);
					PdfPCell natidflag = new PdfPCell(new Phrase(strnidflag,font));
					tbl.addCell(natidflag);
					
					PdfPCell lblsurname = new PdfPCell(new Phrase("Surname: ",font));
					tbl.addCell(lblsurname);
					PdfPCell surname = new PdfPCell(new Phrase(strSurname,font));
					tbl.addCell(surname);
					PdfPCell surnameremark = new PdfPCell(new Phrase(strSurnameremark,font));
					tbl.addCell(surnameremark);
					PdfPCell surnameflag = new PdfPCell(new Phrase(strSurnameflag,font));
					tbl.addCell(surnameflag);
					
					PdfPCell lblfname = new PdfPCell(new Phrase("First name: ",font));
					tbl.addCell(lblfname);
					PdfPCell fname = new PdfPCell(new Phrase(strFirstname,font));
					tbl.addCell(fname);
					PdfPCell fnameremark = new PdfPCell(new Phrase(strfnameremark,font));
					tbl.addCell(fnameremark);
					PdfPCell fnameflag = new PdfPCell(new Phrase(strfnameflag,font));
					tbl.addCell(fnameflag);
					
					PdfPCell lbloname = new PdfPCell(new Phrase("Other name: ",font));
					tbl.addCell(lbloname);
					PdfPCell oname = new PdfPCell(new Phrase(strOthername,font));
					tbl.addCell(oname);
					PdfPCell onameremark = new PdfPCell(new Phrase(stronameremark,font));
					tbl.addCell(onameremark);
					PdfPCell onameflag = new PdfPCell(new Phrase(stronameflag,font));
					tbl.addCell(onameflag);					
					
					PdfPCell lbldob = new PdfPCell(new Phrase("Date of Birth: ",font));
					tbl.addCell(lbldob);					
					PdfPCell dob = new PdfPCell(new Phrase(strDob,font));
					tbl.addCell(dob);
					PdfPCell dobremark = new PdfPCell(new Phrase(strdobremark,font));
					tbl.addCell(dobremark);
					PdfPCell dobflag = new PdfPCell(new Phrase(strdobflag,font));
					tbl.addCell(dobflag);
					
					PdfPCell lblsex = new PdfPCell(new Phrase("Sex: ",font));
					tbl.addCell(lblsex);
					PdfPCell sex = new PdfPCell(new Phrase(strSex,font));
					tbl.addCell(sex);	
					PdfPCell sexremark = new PdfPCell(new Phrase(strsexremark,font));
					tbl.addCell(sexremark);
					PdfPCell sexflag = new PdfPCell(new Phrase(strsexflag,font));
					tbl.addCell(sexflag);
					
					PdfPCell lblyrsinvill = new PdfPCell(new Phrase("Years in village: ",font));
					tbl.addCell(lblyrsinvill);
					PdfPCell yrsinvill = new PdfPCell(new Phrase(strYrsinvillage,font));
					tbl.addCell(yrsinvill);	
					PdfPCell yrsinvillremark = new PdfPCell(new Phrase(stryrvilremark,font));
					tbl.addCell(yrsinvillremark);
					PdfPCell yrsinvillflag = new PdfPCell(new Phrase(stryrvilflag,font));
					tbl.addCell(yrsinvillflag);
					
					PdfPCell lblmstatus = new PdfPCell(new Phrase("Marital Status: ",font));
					tbl.addCell(lblmstatus);
					PdfPCell mstatus = new PdfPCell(new Phrase(strMaritalstatus,font));
					tbl.addCell(mstatus);
					PdfPCell mstatusremark = new PdfPCell(new Phrase(strmsremark,font));
					tbl.addCell(mstatusremark);
					PdfPCell mstatusflag = new PdfPCell(new Phrase(strmsflag,font));
					tbl.addCell(mstatusflag);
					
					PdfPCell lblnok = new PdfPCell(new Phrase("Next of Kin: ",font));
					tbl.addCell(lblnok);
					PdfPCell nok = new PdfPCell(new Phrase(strNok,font));
					tbl.addCell(nok);
					PdfPCell nokremark = new PdfPCell(new Phrase(strnokremark,font));
					tbl.addCell(nokremark);
					PdfPCell nokflag = new PdfPCell(new Phrase(strnokflag,font));
					tbl.addCell(nokflag);
					
					PdfPCell lblmobno = new PdfPCell(new Phrase("Mobile No: ",font));
					tbl.addCell(lblmobno);
					PdfPCell mobno = new PdfPCell(new Phrase(strMobileno,font));
					tbl.addCell(mobno);
					PdfPCell mobnoremark = new PdfPCell(new Phrase(strmobnoremark,font));
					tbl.addCell(mobnoremark);
					PdfPCell mobnoflag = new PdfPCell(new Phrase(strmobnoflag,font));
					tbl.addCell(mobnoflag);
					
					PdfPCell lblnokmobile = new PdfPCell(new Phrase("Next of Kin Mobile: ",font));
					tbl.addCell(lblnokmobile);
					PdfPCell nokmobile = new PdfPCell(new Phrase(strNokmobile,font));
					tbl.addCell(nokmobile);
					PdfPCell nokmobileremark = new PdfPCell(new Phrase(strnokmremark,font));
					tbl.addCell(nokmobileremark);			
					PdfPCell nokmobileflag = new PdfPCell(new Phrase(strnokmflag,font));
					tbl.addCell(nokmobileflag);			
										
					PdfPCell lblstage = new PdfPCell(new Phrase("Stage Name: ",font));
					tbl.addCell(lblstage);
					PdfPCell stage = new PdfPCell(new Phrase(strStage,font));
					tbl.addCell(stage);
					PdfPCell stageremark = new PdfPCell(new Phrase(strstgnmremark,font));
					tbl.addCell(stageremark);
					PdfPCell stageflag = new PdfPCell(new Phrase(strstgnmflag,font));
					tbl.addCell(stageflag);
					
					PdfPCell lblnokr = new PdfPCell(new Phrase("Next of Kin Relationship: ",font));
					tbl.addCell(lblnokr);
					PdfPCell nokr = new PdfPCell(new Phrase(strNorship,font));
					tbl.addCell(nokr);
					PdfPCell nokrremark = new PdfPCell(new Phrase(strnokremark,font));
					tbl.addCell(nokrremark);
					PdfPCell nokrflag = new PdfPCell(new Phrase(strnokflag,font));
					tbl.addCell(nokrflag);
					
					PdfPCell lblcurrbike = new PdfPCell(new Phrase("Current Bike Regno: ",font));
					tbl.addCell(lblcurrbike);
					PdfPCell curbike = new PdfPCell(new Phrase(strBikeregno,font));
					tbl.addCell(curbike);
					PdfPCell curbikeremark = new PdfPCell(new Phrase(strbregnoremark,font));
					tbl.addCell(curbikeremark);
					PdfPCell curbikeflag = new PdfPCell(new Phrase(strbregnoflag,font));
					tbl.addCell(curbikeflag);
					
					PdfPCell lblpermit = new PdfPCell(new Phrase("Driving Permit: ",font));
					tbl.addCell(lblpermit);
					PdfPCell permit = new PdfPCell(new Phrase(strPermit,font));
					tbl.addCell(permit);
					PdfPCell permitremark = new PdfPCell(new Phrase(strdlremark,font));
					tbl.addCell(permitremark);
					PdfPCell permitflag = new PdfPCell(new Phrase(strdlflag,font));
					tbl.addCell(permitflag);
					
					PdfPCell lblbikeuse = new PdfPCell(new Phrase("New Bike Use: ",font));
					tbl.addCell(lblbikeuse);
					PdfPCell bikeuse = new PdfPCell(new Phrase(strBikeuse,font));
					tbl.addCell(bikeuse);
					PdfPCell bikeuseremark = new PdfPCell(new Phrase(strbuseremark,font));
					tbl.addCell(bikeuseremark);
					PdfPCell bikeuseflag = new PdfPCell(new Phrase(strbuseflag,font));
					tbl.addCell(bikeuseflag);
					
					PdfPCell lblnationality = new PdfPCell(new Phrase("Nationality: ",font));
					tbl.addCell(lblnationality);
					PdfPCell nationality = new PdfPCell(new Phrase(strNationality,font));
					tbl.addCell(nationality);	
					PdfPCell nationalityremark = new PdfPCell(new Phrase(strnatremark,font));
					tbl.addCell(nationalityremark);
					PdfPCell nationalityflag = new PdfPCell(new Phrase(strnatflag,font));
					tbl.addCell(nationalityflag);
										
					PdfPCell lblnod = new PdfPCell(new Phrase("No of Dependants: ",font));
					tbl.addCell(lblnod);
					PdfPCell nod = new PdfPCell(new Phrase(strDependants,font));
					tbl.addCell(nod);
					PdfPCell nodremark = new PdfPCell(new Phrase(strnodremark,font));
					tbl.addCell(nodremark);
					PdfPCell nodflag = new PdfPCell(new Phrase(strnodflag,font));
					tbl.addCell(nodflag);
					
					PdfPCell lblor = new PdfPCell(new Phrase("Own House or Rented: ",font));
					tbl.addCell(lblor);
					PdfPCell or = new PdfPCell(new Phrase(strHouse,font));
					tbl.addCell(or);
					PdfPCell orremark = new PdfPCell(new Phrase(strorremark,font));
					tbl.addCell(orremark);
					PdfPCell orflag = new PdfPCell(new Phrase(strorflag,font));
					tbl.addCell(orflag);
										
					PdfPCell lblllname = new PdfPCell(new Phrase("Landlord Name: ",font));
					tbl.addCell(lblllname);
					PdfPCell llname = new PdfPCell(new Phrase(strLandlord,font));
					tbl.addCell(llname);
					PdfPCell llnameremark = new PdfPCell(new Phrase(strllremark,font));
					tbl.addCell(llnameremark);
					PdfPCell llnameflag = new PdfPCell(new Phrase(strllflag,font));
					tbl.addCell(llnameflag);
					
					PdfPCell lblllmobile = new PdfPCell(new Phrase("Landlord Mobile No: ",font));
					tbl.addCell(lblllmobile);
					PdfPCell llmobile = new PdfPCell(new Phrase(strLandlordmobile,font));
					tbl.addCell(llmobile);
					PdfPCell llmobileremark = new PdfPCell(new Phrase(strllmobremark,font));
					tbl.addCell(llmobileremark);
					PdfPCell llmobileflag = new PdfPCell(new Phrase(strllmobflag,font));
					tbl.addCell(llmobileflag);
					
					PdfPCell lblrpm = new PdfPCell(new Phrase("Rent Per Month: ",font));
					tbl.addCell(lblrpm);
					PdfPCell rpm = new PdfPCell(new Phrase(strRent,font));
					tbl.addCell(rpm);
					PdfPCell rpmremark = new PdfPCell(new Phrase(strrpmremark,font));
					tbl.addCell(rpmremark);
					PdfPCell rpmflag = new PdfPCell(new Phrase(strrpmflag,font));
					tbl.addCell(rpmflag);
					
					PdfPCell lblois = new PdfPCell(new Phrase("Other Income Source: ",font));
					tbl.addCell(lblois);
					PdfPCell ois = new PdfPCell(new Phrase(strOis,font));
					tbl.addCell(ois);
					PdfPCell oisremark = new PdfPCell(new Phrase(stroisremark,font));
					tbl.addCell(oisremark);
					PdfPCell oisflag = new PdfPCell(new Phrase(stroisflag,font));
					tbl.addCell(oisflag);
					
					PdfPCell lbldps = new PdfPCell(new Phrase("Down Payment Source: ",font));
					tbl.addCell(lbldps);
					PdfPCell dps = new PdfPCell(new Phrase(strDps,font));
					tbl.addCell(dps);
					PdfPCell dpsremark = new PdfPCell(new Phrase(strdpsremark,font));
					tbl.addCell(dpsremark);
					PdfPCell dpsflag = new PdfPCell(new Phrase(strdpsflg,font));
					tbl.addCell(dpsflag);
					
					PdfPCell lblpadd = new PdfPCell(new Phrase("Permanent Address: ",font));
					tbl.addCell(lblpadd);
					PdfPCell padd = new PdfPCell(new Phrase(strPadd,font));
					tbl.addCell(padd);
					PdfPCell paddremark = new PdfPCell(new Phrase(strpaddremark,font));
					tbl.addCell(paddremark);
					PdfPCell paddflag = new PdfPCell(new Phrase(strpaddflag,font));
					tbl.addCell(paddflag);
					
					PdfPCell lblfather = new PdfPCell(new Phrase("Father's Name: ",font));
					tbl.addCell(lblfather);
					PdfPCell father = new PdfPCell(new Phrase(strFather,font));
					tbl.addCell(father);
					PdfPCell fatherremark = new PdfPCell(new Phrase(strfathremark,font));
					tbl.addCell(fatherremark);
					PdfPCell fatherflag = new PdfPCell(new Phrase(strfathflag,font));
					tbl.addCell(fatherflag);
					
					PdfPCell lblmother = new PdfPCell(new Phrase("Mother's Name: ",font));
					tbl.addCell(lblmother);
					PdfPCell mother = new PdfPCell(new Phrase(strMother,font));
					tbl.addCell(mother);
					PdfPCell motherremark = new PdfPCell(new Phrase(strmothremark,font));
					tbl.addCell(motherremark);
					PdfPCell motherflag = new PdfPCell(new Phrase(strmothflag,font));
					tbl.addCell(motherflag);
					
					PdfPCell lblchair = new PdfPCell(new Phrase("Local Chairman: ",font));
					tbl.addCell(lblchair);
					PdfPCell lc = new PdfPCell(new Phrase(strLc,font));
					tbl.addCell(lc);
					PdfPCell lcremark = new PdfPCell(new Phrase(strlcnmremark,font));
					tbl.addCell(lcremark);
					PdfPCell lcflag = new PdfPCell(new Phrase(strlcnmflag,font));
					tbl.addCell(lcflag);
					
					PdfPCell lbllcmobile = new PdfPCell(new Phrase("LC Mobile no: ",font));
					tbl.addCell(lbllcmobile);
					PdfPCell lcmobile = new PdfPCell(new Phrase(strLcmobile,font));
					tbl.addCell(lcmobile);
					PdfPCell lcmobileremark = new PdfPCell(new Phrase(strlcnmobnoremark,font));
					tbl.addCell(lcmobileremark);
					PdfPCell lcmobileflag = new PdfPCell(new Phrase(strlcnmobnoflag,font));
					tbl.addCell(lcmobileflag);
					
					PdfPCell lblpolice = new PdfPCell(new Phrase("Nearby Police Station: ",font));
					tbl.addCell(lblpolice);
					PdfPCell police = new PdfPCell(new Phrase(strPolice,font));
					tbl.addCell(police);
					PdfPCell policeremark = new PdfPCell(new Phrase(strnpsremark,font));
					tbl.addCell(policeremark);
					PdfPCell policeflag = new PdfPCell(new Phrase(strnpsflag,font));
					tbl.addCell(policeflag);
					
					PdfPCell lbldistrict = new PdfPCell(new Phrase("District: ",font));
					tbl.addCell(lbldistrict);
					PdfPCell district = new PdfPCell(new Phrase(strDistrict,font));
					tbl.addCell(district);
					PdfPCell districtremark = new PdfPCell(new Phrase(strdistremark,font));
					tbl.addCell(districtremark);
					PdfPCell districtflag = new PdfPCell(new Phrase(strdistflag,font));
					tbl.addCell(districtflag);
					
					PdfPCell lblcounty = new PdfPCell(new Phrase("County: ",font));
					tbl.addCell(lblcounty);
					PdfPCell county = new PdfPCell(new Phrase(strCounty,font));
					tbl.addCell(county);
					PdfPCell countyremark = new PdfPCell(new Phrase(strcntremark,font));
					tbl.addCell(countyremark);
					PdfPCell countyflag = new PdfPCell(new Phrase(strcntflag,font));
					tbl.addCell(countyflag);
					
					PdfPCell lblsubcounty = new PdfPCell(new Phrase("Sub County: ",font));
					tbl.addCell(lblsubcounty);
					PdfPCell subcounty = new PdfPCell(new Phrase(strSubcounty,font));
					tbl.addCell(subcounty);
					PdfPCell subcountyremark = new PdfPCell(new Phrase(strscntremark,font));
					tbl.addCell(subcountyremark);
					PdfPCell subcountyflag = new PdfPCell(new Phrase(strscntflag,font));
					tbl.addCell(subcountyflag);
					
					PdfPCell lblparish = new PdfPCell(new Phrase("Parish: ",font));
					tbl.addCell(lblparish);
					PdfPCell parish = new PdfPCell(new Phrase(strParish,font));
					tbl.addCell(parish);
					PdfPCell parishremark = new PdfPCell(new Phrase(strparremark,font));
					tbl.addCell(parishremark);
					PdfPCell parishflag = new PdfPCell(new Phrase(strparflag,font));
					tbl.addCell(parishflag);
					
					PdfPCell lblVillage = new PdfPCell(new Phrase("Village: ",font));
					tbl.addCell(lblVillage);
					PdfPCell village = new PdfPCell(new Phrase(strVillage,font));
					tbl.addCell(village);
					PdfPCell villageremark = new PdfPCell(new Phrase(strvilremark,font));
					tbl.addCell(villageremark);
					PdfPCell villageflag = new PdfPCell(new Phrase(strvilflag,font));
					tbl.addCell(villageflag);
					
					PdfPCell lbltv = new PdfPCell(new Phrase("FI Recommendation: ",font));
					tbl.addCell(lbltv);
					PdfPCell tvremarks = new PdfPCell(new Phrase(strFi,font));
					tbl.addCell(tvremarks);
					PdfPCell tvremarksrm = new PdfPCell(new Phrase(strFiremarks,font));
					tbl.addCell(tvremarksrm);
					PdfPCell tvremarksflg = new PdfPCell(new Phrase("",font));
					tbl.addCell(tvremarksflg);
					
					document.add(tbl);
					
					document.add(new Paragraph("\n"));
					
					document.newPage();
					
					//section II Guarantor 1				
					PdfPTable mdltbl = new PdfPTable(2);
					mdltbl.setWidthPercentage(100);
					mdltbl.setHorizontalAlignment(Element.ALIGN_LEFT);
					mdltbl.setWidths(new int[]{50,50});
					mdltbl.setTotalWidth(100);
											
					PdfPCell lblblnk5 = new PdfPCell(new Phrase("GUARANTOR 1: " + strG1Name, bldFont));
					lblblnk5.setBorderColor(BaseColor.WHITE);
					mdltbl.addCell(lblblnk5);
					PdfPCell lblblnk6 = new PdfPCell(new Phrase(""));
					lblblnk6.setBorderColor(BaseColor.WHITE);
					mdltbl.addCell(lblblnk6);
					
					document.add(mdltbl);
					document.add(new Paragraph(""));
					
					PdfPTable table = new PdfPTable(4);
					table.setWidthPercentage(100);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidths(new int[]{25,25,25,25});
					table.setTotalWidth(100);
					addTableHeader(table);
					//add table rows
					
					PdfPCell lblsurname1 = new PdfPCell(new Phrase("Surname", font));
					table.addCell(lblsurname1);					
					PdfPCell g1surname = new PdfPCell(new Phrase(strSurname1, font));
		            table.addCell(g1surname);					
		            PdfPCell g1surnameremark = new PdfPCell(new Phrase(strSurnameremark1, font));
		            table.addCell(g1surnameremark);
		            PdfPCell g1surnameflag = new PdfPCell(new Phrase(strSurnameflag1, font));
		            table.addCell(g1surnameflag);
		           
		            PdfPCell lblfirstname1 = new PdfPCell(new Phrase("Firstname", font));
					table.addCell(lblfirstname1);					
					PdfPCell g1firstname = new PdfPCell(new Phrase(strFirstname1, font));
		            table.addCell(g1firstname);					
		            PdfPCell g1firstnameremark = new PdfPCell(new Phrase(strfnameremark1, font));
		            table.addCell(g1firstnameremark);
		            PdfPCell g1firstnameflag = new PdfPCell(new Phrase(strfnameflag1, font));
		            table.addCell(g1firstnameflag);
		            
		            PdfPCell lblothername1 = new PdfPCell(new Phrase("Othername", font));
					table.addCell(lblothername1);					
					PdfPCell g1othername = new PdfPCell(new Phrase(strOthername1, font));
		            table.addCell(g1othername);					
		            PdfPCell g1othernamerm = new PdfPCell(new Phrase(stronameremark1, font));
		            table.addCell(g1othernamerm);
		            PdfPCell g1othernamefg = new PdfPCell(new Phrase(stronameflag1, font));
		            table.addCell(g1othernamefg);
		            
		            PdfPCell lblnatid1 = new PdfPCell(new Phrase("National Id", font));
					table.addCell(lblnatid1);					
					PdfPCell g1natid = new PdfPCell(new Phrase(strNatid1, font));
		            table.addCell(g1natid);					
		            PdfPCell g1natidrm = new PdfPCell(new Phrase(strnidremark1, font));
		            table.addCell(g1natidrm);
		            PdfPCell g1natidfg = new PdfPCell(new Phrase(strnidflag1, font));
		            table.addCell(g1natidfg);
		            
		            PdfPCell lblgmobileno1 = new PdfPCell(new Phrase("Mobile No", font));
					table.addCell(lblgmobileno1);					
					PdfPCell g1gmobile = new PdfPCell(new Phrase(strMobno1, font));
		            table.addCell(g1gmobile);					
		            PdfPCell g1gmobilerm = new PdfPCell(new Phrase(strmobnoremark1, font));
		            table.addCell(g1gmobilerm);
		            PdfPCell g1gmobilefg = new PdfPCell(new Phrase(strmobnoflag1, font));
		            table.addCell(g1gmobilefg);
		            
		            PdfPCell lblgadd1 = new PdfPCell(new Phrase("Address", font));
					table.addCell(lblgadd1);					
					PdfPCell g1gadd = new PdfPCell(new Phrase(strAdd1, font));
		            table.addCell(g1gadd);					
		            PdfPCell g1gaddrm = new PdfPCell(new Phrase(straddremark1, font));
		            table.addCell(g1gaddrm);
		            PdfPCell g1gaddfg = new PdfPCell(new Phrase(straddflag1, font));
		            table.addCell(g1gaddfg);
		            
		            PdfPCell lblgpadd1 = new PdfPCell(new Phrase("Permanent Address", font));
					table.addCell(lblgpadd1);					
					PdfPCell g1gpadd = new PdfPCell(new Phrase(strPadd1, font));
		            table.addCell(g1gpadd);					
		            PdfPCell g1gpaddrm = new PdfPCell(new Phrase(strpaddremark1, font));
		            table.addCell(g1gpaddrm);
		            PdfPCell g1gpaddfg = new PdfPCell(new Phrase(strpaddflag1, font));
		            table.addCell(g1gpaddfg);
		            
		            PdfPCell lblgyrsinadd1 = new PdfPCell(new Phrase("Years in Address", font));
					table.addCell(lblgyrsinadd1);					
					PdfPCell g1gyrsinadd = new PdfPCell(new Phrase(strYrsinadd1, font));
		            table.addCell(g1gyrsinadd);					
		            PdfPCell g1gyrsinaddrm = new PdfPCell(new Phrase(stryrvilremark1, font));
		            table.addCell(g1gyrsinaddrm);
		            PdfPCell g1gyrsinaddfg = new PdfPCell(new Phrase(stryrvilflag1, font));
		            table.addCell(g1gyrsinaddfg);
		            
		            PdfPCell lblgor1 = new PdfPCell(new Phrase("Own House or Rented", font));
					table.addCell(lblgor1);					
					PdfPCell g1gor = new PdfPCell(new Phrase(strOr1, font));
		            table.addCell(g1gor);					
		            PdfPCell g1gorrm = new PdfPCell(new Phrase(strorremark1, font));
		            table.addCell(g1gorrm);
		            PdfPCell g1gorfg = new PdfPCell(new Phrase(strorflag1, font));
		            table.addCell(g1gorfg);
		            
		            PdfPCell lblgrpm1 = new PdfPCell(new Phrase("Rent per Month", font));
					table.addCell(lblgrpm1);					
					PdfPCell g1grpm = new PdfPCell(new Phrase(strRent1, font));
		            table.addCell(g1grpm);					
		            PdfPCell g1grpmrm = new PdfPCell(new Phrase(strrpmremark1, font));
		            table.addCell(g1grpmrm);
		            PdfPCell g1grpmfg = new PdfPCell(new Phrase(strrpmflag1, font));
		            table.addCell(g1grpmfg);
		            
		            PdfPCell lblgnok1 = new PdfPCell(new Phrase("Next of Kin", font));
					table.addCell(lblgnok1);					
					PdfPCell g1gnok = new PdfPCell(new Phrase(strNok1, font));
		            table.addCell(g1gnok);					
		            PdfPCell g1gnokrm = new PdfPCell(new Phrase(strnokremark1, font));
		            table.addCell(g1gnokrm);
		            PdfPCell g1gnokfg = new PdfPCell(new Phrase(strnokflag1, font));
		            table.addCell(g1gnokfg);
		            
		            PdfPCell lblgnokmobile1 = new PdfPCell(new Phrase("Nexk of Kin Mobile", font));
					table.addCell(lblgnokmobile1);					
					PdfPCell g1gnokmobile = new PdfPCell(new Phrase(strNokmobno1, font));
		            table.addCell(g1gnokmobile);					
		            PdfPCell g1gnokmobilerm = new PdfPCell(new Phrase(strnokmremark1, font));
		            table.addCell(g1gnokmobilerm);
		            PdfPCell g1gnokmobilefg = new PdfPCell(new Phrase(strnokmflag1, font));
		            table.addCell(g1gnokmobilefg);
		            
		            PdfPCell lblgnokr1 = new PdfPCell(new Phrase("Next of Kin Relationship", font));
					table.addCell(lblgnokr1);					
					PdfPCell g1gnokr = new PdfPCell(new Phrase(strNokrshp1, font));
		            table.addCell(g1gnokr);					
		            PdfPCell g1gnokrrm = new PdfPCell(new Phrase(strnokrremark1, font));
		            table.addCell(g1gnokrrm);
		            PdfPCell g1gnokrfg = new PdfPCell(new Phrase(strnokrflag1, font));
		            table.addCell(g1gnokrfg);
		            
		            PdfPCell lblgnoka1 = new PdfPCell(new Phrase("Next of Kin Agreeing", font));
					table.addCell(lblgnoka1);					
					PdfPCell g1gnoka = new PdfPCell(new Phrase(strNoka1, font));
		            table.addCell(g1gnoka);					
		            PdfPCell g1gnokarm = new PdfPCell(new Phrase(strnokaremark1, font));
		            table.addCell(g1gnokarm);
		            PdfPCell g1gnokafg = new PdfPCell(new Phrase(strnokaflag1, font));
		            table.addCell(g1gnokafg);

		            PdfPCell lblgbikeregno1 = new PdfPCell(new Phrase("Bike Regno", font));
					table.addCell(lblgbikeregno1);					
					PdfPCell g1gbikeregno = new PdfPCell(new Phrase(strBkregno1, font));
		            table.addCell(g1gbikeregno);					
		            PdfPCell g1gbikeregnorm = new PdfPCell(new Phrase(strbregnoremark1, font));
		            table.addCell(g1gbikeregnorm);
		            PdfPCell g1gbikeregnofg = new PdfPCell(new Phrase(strbregnoflag1, font));
		            table.addCell(g1gbikeregnofg);
		            
		            PdfPCell lblgbikeowner1 = new PdfPCell(new Phrase("Bike Owner", font));
					table.addCell(lblgbikeowner1);					
					PdfPCell g1gbikeowner = new PdfPCell(new Phrase(strBkowner1, font));
		            table.addCell(g1gbikeowner);					
		            PdfPCell g1gbikeownerrm = new PdfPCell(new Phrase(strbownerremark1, font));
		            table.addCell(g1gbikeownerrm);
		            PdfPCell g1gbikeownerfg = new PdfPCell(new Phrase(strbownerflag1, font));
		            table.addCell(g1gbikeownerfg);
		            
		            PdfPCell lblgsalaried1 = new PdfPCell(new Phrase("Salaried", font));
					table.addCell(lblgsalaried1);					
					PdfPCell g1gsalaried = new PdfPCell(new Phrase(strSal1, font));
		            table.addCell(g1gsalaried);					
		            PdfPCell g1gsalariedrm = new PdfPCell(new Phrase(strsalremark1, font));
		            table.addCell(g1gsalariedrm);
		            PdfPCell g1gsalariedfg = new PdfPCell(new Phrase(strsalflag1, font));
		            table.addCell(g1gsalariedfg);
		            
		            PdfPCell lblgmincome1 = new PdfPCell(new Phrase("Monthly Income", font));
					table.addCell(lblgmincome1);					
					PdfPCell g1gmincome = new PdfPCell(new Phrase(strMnthin1, font));
		            table.addCell(g1gmincome);					
		            PdfPCell g1gmincomerm = new PdfPCell(new Phrase(strmiremark1, font));
		            table.addCell(g1gmincomerm);
		            PdfPCell g1gmincomefg = new PdfPCell(new Phrase(strmiflag1, font));
		            table.addCell(g1gmincomefg);
		            
		            PdfPCell lblgois1 = new PdfPCell(new Phrase("Other Income Source", font));
					table.addCell(lblgois1);					
					PdfPCell g1gois = new PdfPCell(new Phrase(strOis1, font));
		            table.addCell(g1gois);					
		            PdfPCell g1goisrm = new PdfPCell(new Phrase(stroisremark1, font));
		            table.addCell(g1goisrm);
		            PdfPCell g1goisfg = new PdfPCell(new Phrase(stroisflag1, font));
		            table.addCell(g1goisfg);
		            
		            PdfPCell lblgoi1 = new PdfPCell(new Phrase("Other Income", font));
					table.addCell(lblgoi1);					
					PdfPCell g1goi = new PdfPCell(new Phrase(strOi1, font));
		            table.addCell(g1goi);					
		            PdfPCell g1goirm = new PdfPCell(new Phrase(stroiremark1, font));
		            table.addCell(g1goirm);
		            PdfPCell g1goifg = new PdfPCell(new Phrase(stroiflag1, font));
		            table.addCell(g1goifg);
					
		            PdfPCell lblgstage1 = new PdfPCell(new Phrase("Stage Name", font));
					table.addCell(lblgstage1);					
					PdfPCell g1gstage = new PdfPCell(new Phrase(strStg1, font));
		            table.addCell(g1gstage);					
		            PdfPCell g1gstagerm = new PdfPCell(new Phrase(strstgnmremark1, font));
		            table.addCell(g1gstagerm);
		            PdfPCell g1gstagefg = new PdfPCell(new Phrase(strstgnmflag1, font));
		            table.addCell(g1gstagefg);
		            
		            PdfPCell lblgstageadd1 = new PdfPCell(new Phrase("Stage Address", font));
					table.addCell(lblgstageadd1);					
					PdfPCell g1gstageadd = new PdfPCell(new Phrase(strStgadd1, font));
		            table.addCell(g1gstageadd);					
		            PdfPCell g1gstageaddrm = new PdfPCell(new Phrase(strstgaddremark1, font));
		            table.addCell(g1gstageaddrm);
		            PdfPCell g1gstageaddfg = new PdfPCell(new Phrase(strstgaddflag1, font));
		            table.addCell(g1gstageaddfg);
		            
		            PdfPCell lblgstgchairconf1 = new PdfPCell(new Phrase("Stage Chairman Confirmation", font));
					table.addCell(lblgstgchairconf1);					
					PdfPCell g1gstgchairconf = new PdfPCell(new Phrase(strStgchconf1, font));
		            table.addCell(g1gstgchairconf);					
		            PdfPCell g1gstgchairconfrm = new PdfPCell(new Phrase(strstgccremark1, font));
		            table.addCell(g1gstgchairconfrm);
		            PdfPCell g1gstgchairconffg = new PdfPCell(new Phrase(strstgccflag1, font));
		            table.addCell(g1gstgchairconffg);
		            
		            PdfPCell lblgchair1 = new PdfPCell(new Phrase("Local Chairman", font));
					table.addCell(lblgchair1);					
					PdfPCell g1gchair = new PdfPCell(new Phrase(strLc1, font));
		            table.addCell(g1gchair);					
		            PdfPCell g1gchairrm = new PdfPCell(new Phrase(strlcnmremark1, font));
		            table.addCell(g1gchairrm);
		            PdfPCell g1gchairfg = new PdfPCell(new Phrase(strlcnmflag1, font));
		            table.addCell(g1gchairfg);
		            
		            PdfPCell lblgrelationship1 = new PdfPCell(new Phrase("Relationship", font));
					table.addCell(lblgrelationship1);					
					PdfPCell g1relationship = new PdfPCell(new Phrase(strRltnshp1, font));
		            table.addCell(g1relationship);					
		            PdfPCell g1relationshiprm = new PdfPCell(new Phrase(strrltnshpremark1, font));
		            table.addCell(g1relationshiprm);
		            PdfPCell g1relationshipfg = new PdfPCell(new Phrase(strrltnshpflag1, font));
		            table.addCell(g1relationshipfg);
		            
		            PdfPCell lblgtvremarks1 = new PdfPCell(new Phrase("FI Recommendation", font));
					table.addCell(lblgtvremarks1);					
					PdfPCell g1gtv = new PdfPCell(new Phrase(strFi1, font));
		            table.addCell(g1gtv);					
		            PdfPCell g1gtvremarks = new PdfPCell(new Phrase(strFiremarks1, font));
		            table.addCell(g1gtvremarks);
		            PdfPCell g1gtvflag = new PdfPCell(new Phrase("", font));
		            table.addCell(g1gtvflag);
		           		            		            
		            document.add(table);
		            
		            document.add(new Paragraph("\n"));
		            document.add(new Paragraph("\n"));
		            
		            //section II Guarantor 2				
					PdfPTable mdltbl2 = new PdfPTable(2);
					mdltbl2.setWidthPercentage(100);
					mdltbl2.setHorizontalAlignment(Element.ALIGN_LEFT);
					mdltbl2.setWidths(new int[]{50,50});
					mdltbl2.setTotalWidth(100);
											
					PdfPCell lblblnkg2 = new PdfPCell(new Phrase("GUARANTOR 2: " + strG2Name, bldFont));
					lblblnkg2.setBorderColor(BaseColor.WHITE);
					mdltbl2.addCell(lblblnkg2);
					PdfPCell lblblnkg21 = new PdfPCell(new Phrase(""));
					lblblnkg21.setBorderColor(BaseColor.WHITE);
					mdltbl2.addCell(lblblnkg21);
					
					document.add(mdltbl2);
					document.add(new Paragraph(""));
					
					PdfPTable table2 = new PdfPTable(4);
					table2.setWidthPercentage(100);
					table2.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.setWidths(new int[]{25,25,25,25});
					table2.setTotalWidth(100);
					addTableHeader(table2);
					//add table rows
					
					PdfPCell lblsurname2 = new PdfPCell(new Phrase("Surname", font));
					table2.addCell(lblsurname2);					
					PdfPCell g2surname = new PdfPCell(new Phrase(strSurname2, font));
		            table2.addCell(g2surname);					
		            PdfPCell g2surnameremark = new PdfPCell(new Phrase(strSurnameremark2, font));
		            table2.addCell(g2surnameremark);
		            PdfPCell g2surnameflag = new PdfPCell(new Phrase(strSurnameflag2, font));
		            table2.addCell(g2surnameflag);
		           
		            PdfPCell lblfirstname2 = new PdfPCell(new Phrase("Firstname", font));
					table2.addCell(lblfirstname2);					
					PdfPCell g2firstname = new PdfPCell(new Phrase(strFirstname2, font));
		            table2.addCell(g2firstname);					
		            PdfPCell g2firstnameremark = new PdfPCell(new Phrase(strfnameremark2, font));
		            table2.addCell(g2firstnameremark);
		            PdfPCell g2firstnameflag = new PdfPCell(new Phrase(strfnameflag2, font));
		            table2.addCell(g2firstnameflag);
		            
		            PdfPCell lblothername2 = new PdfPCell(new Phrase("Othername", font));
					table2.addCell(lblothername2);					
					PdfPCell g2othername = new PdfPCell(new Phrase(strOthername2, font));
		            table2.addCell(g2othername);					
		            PdfPCell g2othernamerm = new PdfPCell(new Phrase(stronameremark2, font));
		            table2.addCell(g2othernamerm);
		            PdfPCell g2othernamefg = new PdfPCell(new Phrase(stronameflag2, font));
		            table2.addCell(g2othernamefg);
		            
		            PdfPCell lblnatid2 = new PdfPCell(new Phrase("National Id", font));
					table2.addCell(lblnatid2);					
					PdfPCell g2natid = new PdfPCell(new Phrase(strNatid2, font));
		            table2.addCell(g2natid);					
		            PdfPCell g2natidrm = new PdfPCell(new Phrase(strnidremark2, font));
		            table2.addCell(g2natidrm);
		            PdfPCell g2natidfg = new PdfPCell(new Phrase(strnidflag2, font));
		            table2.addCell(g2natidfg);
		            
		            PdfPCell lblgmobileno2 = new PdfPCell(new Phrase("Mobile No", font));
					table2.addCell(lblgmobileno2);					
					PdfPCell g2gmobile = new PdfPCell(new Phrase(strMobno2, font));
		            table2.addCell(g2gmobile);					
		            PdfPCell g2gmobilerm = new PdfPCell(new Phrase(strmobnoremark2, font));
		            table2.addCell(g2gmobilerm);
		            PdfPCell g2gmobilefg = new PdfPCell(new Phrase(strmobnoflag2, font));
		            table2.addCell(g2gmobilefg);
		            
		            PdfPCell lblgadd2 = new PdfPCell(new Phrase("Address", font));
					table2.addCell(lblgadd2);					
					PdfPCell g2gadd = new PdfPCell(new Phrase(strAdd2, font));
		            table2.addCell(g2gadd);					
		            PdfPCell g2gaddrm = new PdfPCell(new Phrase(straddremark2, font));
		            table2.addCell(g2gaddrm);
		            PdfPCell g2gaddfg = new PdfPCell(new Phrase(straddflag2, font));
		            table2.addCell(g2gaddfg);
		            
		            PdfPCell lblgpadd2 = new PdfPCell(new Phrase("Permanent Address", font));
					table2.addCell(lblgpadd2);					
					PdfPCell g2gpadd = new PdfPCell(new Phrase(strPadd2, font));
		            table2.addCell(g2gpadd);					
		            PdfPCell g2gpaddrm = new PdfPCell(new Phrase(strpaddremark2, font));
		            table2.addCell(g2gpaddrm);
		            PdfPCell g2gpaddfg = new PdfPCell(new Phrase(strpaddflag2, font));
		            table2.addCell(g2gpaddfg);
		            
		            PdfPCell lblgyrsinadd2 = new PdfPCell(new Phrase("Years in Address", font));
					table2.addCell(lblgyrsinadd2);					
					PdfPCell g2gyrsinadd = new PdfPCell(new Phrase(strYrsinadd2, font));
		            table2.addCell(g2gyrsinadd);					
		            PdfPCell g2gyrsinaddrm = new PdfPCell(new Phrase(stryrvilremark2, font));
		            table2.addCell(g2gyrsinaddrm);
		            PdfPCell g2gyrsinaddfg = new PdfPCell(new Phrase(stryrvilflag2, font));
		            table2.addCell(g2gyrsinaddfg);
		            
		            PdfPCell lblgor2 = new PdfPCell(new Phrase("Own House or Rented", font));
					table2.addCell(lblgor2);					
					PdfPCell g2gor = new PdfPCell(new Phrase(strOr2, font));
		            table2.addCell(g2gor);					
		            PdfPCell g2gorrm = new PdfPCell(new Phrase(strorremark2, font));
		            table2.addCell(g2gorrm);
		            PdfPCell g2gorfg = new PdfPCell(new Phrase(strorflag2, font));
		            table2.addCell(g2gorfg);
		            
		            PdfPCell lblgrpm2 = new PdfPCell(new Phrase("Rent per Month", font));
					table2.addCell(lblgrpm2);					
					PdfPCell g2grpm = new PdfPCell(new Phrase(strRent2, font));
		            table2.addCell(g2grpm);					
		            PdfPCell g2grpmrm = new PdfPCell(new Phrase(strrpmremark2, font));
		            table2.addCell(g2grpmrm);
		            PdfPCell g2grpmfg = new PdfPCell(new Phrase(strrpmflag2, font));
		            table2.addCell(g2grpmfg);
		            
		            PdfPCell lblgnok2 = new PdfPCell(new Phrase("Next of Kin", font));
					table2.addCell(lblgnok2);					
					PdfPCell g2gnok = new PdfPCell(new Phrase(strNok2, font));
		            table2.addCell(g2gnok);					
		            PdfPCell g2gnokrm = new PdfPCell(new Phrase(strnokremark2, font));
		            table2.addCell(g2gnokrm);
		            PdfPCell g2gnokfg = new PdfPCell(new Phrase(strnokflag2, font));
		            table2.addCell(g2gnokfg);
		            
		            PdfPCell lblgnokmobile2 = new PdfPCell(new Phrase("Nexk of Kin Mobile", font));
					table2.addCell(lblgnokmobile2);					
					PdfPCell g2gnokmobile = new PdfPCell(new Phrase(strNokmobno2, font));
		            table2.addCell(g2gnokmobile);					
		            PdfPCell g2gnokmobilerm = new PdfPCell(new Phrase(strnokmremark2, font));
		            table2.addCell(g2gnokmobilerm);
		            PdfPCell g2gnokmobilefg = new PdfPCell(new Phrase(strnokmflag2, font));
		            table2.addCell(g2gnokmobilefg);
		            
		            PdfPCell lblgnokr2 = new PdfPCell(new Phrase("Next of Kin Relationship", font));
					table2.addCell(lblgnokr2);					
					PdfPCell g2gnokr = new PdfPCell(new Phrase(strNokrshp2, font));
		            table2.addCell(g2gnokr);					
		            PdfPCell g2gnokrrm = new PdfPCell(new Phrase(strnokrremark2, font));
		            table2.addCell(g2gnokrrm);
		            PdfPCell g2gnokrfg = new PdfPCell(new Phrase(strnokrflag2, font));
		            table2.addCell(g2gnokrfg);
		            
		            PdfPCell lblgnoka2 = new PdfPCell(new Phrase("Next of Kin Agreeing", font));
					table2.addCell(lblgnoka2);					
					PdfPCell g2gnoka = new PdfPCell(new Phrase(strNoka2, font));
		            table2.addCell(g2gnoka);					
		            PdfPCell g2gnokarm = new PdfPCell(new Phrase(strnokaremark2, font));
		            table2.addCell(g2gnokarm);
		            PdfPCell g2gnokafg = new PdfPCell(new Phrase(strnokaflag2, font));
		            table2.addCell(g2gnokafg);

		            PdfPCell lblgbikeregno2 = new PdfPCell(new Phrase("Bike Regno", font));
					table2.addCell(lblgbikeregno2);					
					PdfPCell g2gbikeregno = new PdfPCell(new Phrase(strBkregno2, font));
		            table2.addCell(g2gbikeregno);					
		            PdfPCell g2gbikeregnorm = new PdfPCell(new Phrase(strbregnoremark2, font));
		            table2.addCell(g2gbikeregnorm);
		            PdfPCell g2gbikeregnofg = new PdfPCell(new Phrase(strbregnoflag2, font));
		            table2.addCell(g2gbikeregnofg);
		            
		            PdfPCell lblgbikeowner2 = new PdfPCell(new Phrase("Bike Owner", font));
					table2.addCell(lblgbikeowner2);					
					PdfPCell g2gbikeowner = new PdfPCell(new Phrase(strBkowner2, font));
		            table2.addCell(g2gbikeowner);					
		            PdfPCell g2gbikeownerrm = new PdfPCell(new Phrase(strbownerremark2, font));
		            table2.addCell(g2gbikeownerrm);
		            PdfPCell g2gbikeownerfg = new PdfPCell(new Phrase(strbownerflag2, font));
		            table2.addCell(g2gbikeownerfg);
		            
		            PdfPCell lblgsalaried2 = new PdfPCell(new Phrase("Salaried", font));
					table2.addCell(lblgsalaried2);					
					PdfPCell g2gsalaried = new PdfPCell(new Phrase(strSal2, font));
		            table2.addCell(g2gsalaried);					
		            PdfPCell g2gsalariedrm = new PdfPCell(new Phrase(strsalremark2, font));
		            table2.addCell(g2gsalariedrm);
		            PdfPCell g2gsalariedfg = new PdfPCell(new Phrase(strsalflag2, font));
		            table2.addCell(g2gsalariedfg);
		            
		            PdfPCell lblgmincome2 = new PdfPCell(new Phrase("Monthly Income", font));
					table2.addCell(lblgmincome2);					
					PdfPCell g2gmincome = new PdfPCell(new Phrase(strMnthin2, font));
		            table2.addCell(g2gmincome);					
		            PdfPCell g2gmincomerm = new PdfPCell(new Phrase(strmiremark2, font));
		            table2.addCell(g2gmincomerm);
		            PdfPCell g2gmincomefg = new PdfPCell(new Phrase(strmiflag2, font));
		            table2.addCell(g2gmincomefg);
		            
		            PdfPCell lblgois2 = new PdfPCell(new Phrase("Other Income Source", font));
					table2.addCell(lblgois2);					
					PdfPCell g2gois = new PdfPCell(new Phrase(strOis2, font));
		            table2.addCell(g2gois);					
		            PdfPCell g2goisrm = new PdfPCell(new Phrase(stroisremark2, font));
		            table2.addCell(g2goisrm);
		            PdfPCell g2goisfg = new PdfPCell(new Phrase(stroisflag2, font));
		            table2.addCell(g2goisfg);
		            
		            PdfPCell lblgoi2 = new PdfPCell(new Phrase("Other Income", font));
					table2.addCell(lblgoi2);					
					PdfPCell g2goi = new PdfPCell(new Phrase(strOi2, font));
		            table2.addCell(g2goi);					
		            PdfPCell g2goirm = new PdfPCell(new Phrase(stroiremark2, font));
		            table2.addCell(g2goirm);
		            PdfPCell g2goifg = new PdfPCell(new Phrase(stroiflag2, font));
		            table2.addCell(g2goifg);
					
		            PdfPCell lblgstage2 = new PdfPCell(new Phrase("Stage Name", font));
					table2.addCell(lblgstage2);					
					PdfPCell g2gstage = new PdfPCell(new Phrase(strStg2, font));
		            table2.addCell(g2gstage);					
		            PdfPCell g2gstagerm = new PdfPCell(new Phrase(strstgnmremark2, font));
		            table2.addCell(g2gstagerm);
		            PdfPCell g2gstagefg = new PdfPCell(new Phrase(strstgnmflag2, font));
		            table2.addCell(g2gstagefg);
		            
		            PdfPCell lblgstageadd2 = new PdfPCell(new Phrase("Stage Address", font));
					table2.addCell(lblgstageadd2);					
					PdfPCell g2gstageadd = new PdfPCell(new Phrase(strStgadd2, font));
		            table2.addCell(g2gstageadd);					
		            PdfPCell g2gstageaddrm = new PdfPCell(new Phrase(strstgaddremark2, font));
		            table2.addCell(g2gstageaddrm);
		            PdfPCell g2gstageaddfg = new PdfPCell(new Phrase(strstgaddflag2, font));
		            table2.addCell(g2gstageaddfg);
		            
		            PdfPCell lblgstgchairconf2 = new PdfPCell(new Phrase("Stage Chairman Confirmation", font));
					table2.addCell(lblgstgchairconf2);					
					PdfPCell g2gstgchairconf = new PdfPCell(new Phrase(strStgchconf2, font));
		            table2.addCell(g2gstgchairconf);					
		            PdfPCell g2gstgchairconfrm = new PdfPCell(new Phrase(strstgccremark2, font));
		            table2.addCell(g2gstgchairconfrm);
		            PdfPCell g2gstgchairconffg = new PdfPCell(new Phrase(strstgccflag2, font));
		            table2.addCell(g2gstgchairconffg);
		            
		            PdfPCell lblgchair2 = new PdfPCell(new Phrase("Local Chairman", font));
					table2.addCell(lblgchair2);					
					PdfPCell g2gchair = new PdfPCell(new Phrase(strLc2, font));
		            table2.addCell(g2gchair);					
		            PdfPCell g2gchairrm = new PdfPCell(new Phrase(strlcnmremark2, font));
		            table2.addCell(g2gchairrm);
		            PdfPCell g2gchairfg = new PdfPCell(new Phrase(strlcnmflag2, font));
		            table2.addCell(g2gchairfg);
		            
		            PdfPCell lblgrelationship2 = new PdfPCell(new Phrase("Relationship", font));
					table2.addCell(lblgrelationship2);					
					PdfPCell g2relationship = new PdfPCell(new Phrase(strRltnshp2, font));
		            table2.addCell(g2relationship);					
		            PdfPCell g2relationshiprm = new PdfPCell(new Phrase(strrltnshpremark2, font));
		            table2.addCell(g2relationshiprm);
		            PdfPCell g2relationshipfg = new PdfPCell(new Phrase(strrltnshpflag2, font));
		            table2.addCell(g2relationshipfg);
		            
		            PdfPCell lblgtvremarks2 = new PdfPCell(new Phrase("FI Recommendation", font));
					table2.addCell(lblgtvremarks2);					
					PdfPCell g2gtv = new PdfPCell(new Phrase(strFi2, font));
		            table2.addCell(g2gtv);					
		            PdfPCell g2gtvremarks = new PdfPCell(new Phrase(strFiremarks2, font));
		            table2.addCell(g2gtvremarks);
		            PdfPCell g2gtvflag = new PdfPCell(new Phrase("", font));
		            table2.addCell(g2gtvflag);
		           		            		            
		            document.add(table2);	            
		           	            
		            //***************************************************************************************************
					
		            document.close();
				
					//document.open();
		            
		            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		            // Upload to S3 bucket
		            AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
		            String keyName = custId+"TeleVerify.xls"; 
		            
		      
		            metadata.setContentLength(outputStream.size());
		            metadata.setContentType("application/pdf");

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
					System.out.println("Error printing payment schedule");
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
				headers.add("X-message", strMsg);
			}
			return ResponseEntity.ok(url.toString());
	}
	
	private void addCustTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
		
		Stream.of("Field Name","Values","FI Remarks","Matching (true/false)")
			.forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setPhrase(new Phrase(columnTitle, hdrfont));
				table.addCell(header);
		});
	}
		
	private void addTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
		
		Stream.of("Field Name","Values","FI Remarks","Matching (true/false)")
			.forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setPhrase(new Phrase(columnTitle, hdrfont));
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
