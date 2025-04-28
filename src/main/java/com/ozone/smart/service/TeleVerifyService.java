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
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.entity.RiderDetails;
import com.ozone.smart.entity.TvVerification;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.repository.TeleVerifyRepo;
import com.ozone.smart.util.TimeStampUtil;
import com.ozone.smart.util.formatDigits;

import jakarta.servlet.ServletOutputStream;

@Service
public class TeleVerifyService {

	@Autowired
	private TeleVerifyRepo teleVerifyRepo;

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private RiderRepo riderRepo;

	@Autowired
	private GuarantorRepo guarantorRepo;

	@Value("${s3.bucket.name}")
	private String bucketName;

	public Response<String> addCustTv(TeleVerificationDto teleVerificationDto) {

		Response<String> response = new Response<>();

		String strCustid, strRemarks, strTvverdict;

		String strMsg = "";
		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnTvverdict = false;

		String strTVRequestdatetime = "";
		String strLoginuser = "";

		strCustid = teleVerificationDto.getTvid();
//		int Custid = Integer.parseInt(strCustid);
		strTvverdict = teleVerificationDto.getStrtvverdict();
//		recommed or not (recommed , notrecommed)
		strRemarks = teleVerificationDto.getTvremarks();
//		last box

		String strSurname = teleVerificationDto.getSurname();
		String strSurnameremark = teleVerificationDto.getSurnameremark();
		String strSurnameflag = teleVerificationDto.getSurnameflag();
		String strFname = teleVerificationDto.getFirstname();
		String strfnameremark = teleVerificationDto.getFirstnameremark();
		String strfnameflag = teleVerificationDto.getFirstnameflag();
		String stroname = teleVerificationDto.getOthername();
		String stronameremark = teleVerificationDto.getOthernameremark();
		String stronameflag = teleVerificationDto.getOthernameflag();
		String strms = teleVerificationDto.getMaritalstatus();
		String strmsremark = teleVerificationDto.getMaritalstatusremark();
		String strmsflag = teleVerificationDto.getMaritalstatusflag();
		String strsex = teleVerificationDto.getSex();
		String strsexremark = teleVerificationDto.getSexremark();
		String strsexflag = teleVerificationDto.getSexflag();
		String strmobno = teleVerificationDto.getMobileno();
		String strmobnoremark = teleVerificationDto.getMobilenoremark();
		String strmobnoflag = teleVerificationDto.getMobilenoflag();
		String strstgnm = teleVerificationDto.getStage();
		String strstgnmremark = teleVerificationDto.getStageremark();
		String strstgnmflag = teleVerificationDto.getStageflag();
		String strstgchmname = teleVerificationDto.getStagechairmanname();
		String strstgchmnnameremark = teleVerificationDto.getStagechairmannameremarks();
		String strstgchmnameflag = teleVerificationDto.getStagechairmanflag();
		String strstgchmnno = teleVerificationDto.getStagechairmanno();
		String strstgchmnnoremarks = teleVerificationDto.getStagechairmannoremarks();
		String strstgchmnnoflag = teleVerificationDto.getStagechairmannoflag();
		String strdist = teleVerificationDto.getDistrict();
		String strdistremark = teleVerificationDto.getDistrictremark();
		String strdistflag = teleVerificationDto.getDistrictflag();
		String strlcnm = teleVerificationDto.getLc();
		String strlcnmremark = teleVerificationDto.getLcremark();
		String strlcnmflag = teleVerificationDto.getLcflag();
		String strpar = teleVerificationDto.getParish();
		String strparremark = teleVerificationDto.getParishremark();
		String strparflag = teleVerificationDto.getParishflag();
		String strnid = teleVerificationDto.getNationalid();
		String strnidremark = teleVerificationDto.getNationalidremark();
		String strnidflag = teleVerificationDto.getNationalidflag();
		String strbregno = teleVerificationDto.getBikeregno();
		String strbregnoremark = teleVerificationDto.getBikeregnoremark();
		String strbregnoflag = teleVerificationDto.getBikeregnoflag();
		String strbuse = teleVerificationDto.getBikeuse();
		String strbuseremark = teleVerificationDto.getBikeuseremark();
		String strbuseflag = teleVerificationDto.getBikeuseflag();
		String strdob = teleVerificationDto.getDob();
		String strdobremark = teleVerificationDto.getDobremark();
		String strdobflag = teleVerificationDto.getDobflag();
		String strcntd = teleVerificationDto.getCounty();
		String strcntremark = teleVerificationDto.getCountyremark();
		String strcntflag = teleVerificationDto.getCountyflag();
		String strscnt = teleVerificationDto.getSubcounty();
		String strscntremark = teleVerificationDto.getSubcountyremark();
		String strscntflag = teleVerificationDto.getSubcountyflag();
		String strvil = teleVerificationDto.getVillage();
		String strvilremark = teleVerificationDto.getVillageremark();
		String strvilflag = teleVerificationDto.getVillageflag();
		String stryrvil = teleVerificationDto.getYearsinvillage();
		String stryrvilremark = teleVerificationDto.getYearsinvillageremark();
		String stryrvilflag = teleVerificationDto.getYearsinvillageflag();
		String strnok = teleVerificationDto.getNextofkin();
		String strnokremark = teleVerificationDto.getNextofkinremark();
		String strnokflag = teleVerificationDto.getNextofkinflag();
		String strnokm = teleVerificationDto.getNokmobileno();
		String strnokmremark = teleVerificationDto.getNokmobilenoremark();
		String strnokmflag = teleVerificationDto.getNokmobilenoflag();
		String strnokr = teleVerificationDto.getNokrelationship();
		String strnokrremark = teleVerificationDto.getNokrelationshipremark();
		String strnokrflag = teleVerificationDto.getNokrelationshipflag();
		String strnoka = teleVerificationDto.getNokagreeing();
		String strnokaremark = teleVerificationDto.getNokagreeingremark();
		String strnokaflag = teleVerificationDto.getNokagreeingflag();
		String strdl = teleVerificationDto.getDrivingpermit();
		String strdlremark = teleVerificationDto.getDrivingpermitremark();
		String strdlflag = teleVerificationDto.getDrivingpermitflag();
		String strnat = teleVerificationDto.getNationality();
		String strnatremark = teleVerificationDto.getNationalityremark();
		String strnatflag = teleVerificationDto.getNationalityflag();
		String strnod = teleVerificationDto.getNoofdependants();
		String strnodremark = teleVerificationDto.getNoofdependantsremark();
		String strnodflag = teleVerificationDto.getNoofdependantsflag();
		String stror = teleVerificationDto.getOwnhouserented();
		String strorremark = teleVerificationDto.getOwnhouserentedremark();
		String strorflag = teleVerificationDto.getOwnhouserentedflag();
		String strll = teleVerificationDto.getLandlordname();
		String strllremark = teleVerificationDto.getLandlordnameremark();
		String strllflag = teleVerificationDto.getLandlordnameflag();
		String strllmob = teleVerificationDto.getLandlordmobileno();
		String strllmobremark = teleVerificationDto.getLandlordmobilenoremark();
		String strllmobflag = teleVerificationDto.getLandlordmobilenoflag();
		String strrpm = teleVerificationDto.getRentpm();
		String strrpmremark = teleVerificationDto.getRentpmremark();
		String strrpmflag = teleVerificationDto.getRentpmflag();
		String strois = teleVerificationDto.getOtherincomesource();
		String stroisremark = teleVerificationDto.getOtherincomesourceremark();
		String stroisflag = teleVerificationDto.getOtherincomesourceflag();
		String strdps = teleVerificationDto.getDownpaymentsource();
		String strdpsremark = teleVerificationDto.getDownpaymentsourceremark();
		String strdpsflg = teleVerificationDto.getDownpaymentsourceflag();
		String strpadd = teleVerificationDto.getPermanentaddress();
		String strpaddremark = teleVerificationDto.getPermanentaddressremark();
		String strpaddflag = teleVerificationDto.getPermanentaddressflag();
		String strfath = teleVerificationDto.getFathersname();
		String strfathremark = teleVerificationDto.getFathersnameremark();
		String strfathflag = teleVerificationDto.getFathersnameflag();
		String strmoth = teleVerificationDto.getMothersname();
		String strmothremark = teleVerificationDto.getMothersnameremark();
		String strmothflag = teleVerificationDto.getMothersnameflag();
		String strnps = teleVerificationDto.getNearbypolicestation();
		String strnpsremark = teleVerificationDto.getNearbypolicestationremark();
		String strnpsflag = teleVerificationDto.getNearbypolicestationflag();
		String strlcnmobno = teleVerificationDto.getLcmobileno();
		String strlcnmobnoremark = teleVerificationDto.getLcmobilenoremark();
		String strlcnmobnoflag = teleVerificationDto.getLcmobilenoflag();
		String strcusttype = teleVerificationDto.getCusttype();
		String strcusttyperemark = teleVerificationDto.getCusttyperemark();
		String strcusttypeflag = teleVerificationDto.getCusttypeflag();

		String strllrentfeedback = teleVerificationDto.getLlrentfeedback();
		String strllrentfeedbackremark = teleVerificationDto.getLlrentfeedbackremarks();
		String strllrentfeedbackflag = teleVerificationDto.getLlrentfeedbackflag();

		String strnoyrsinarea = teleVerificationDto.getNoyrsinarea();
		String strnoyrsinarearemark = teleVerificationDto.getNoyrsinarearemarks();
		String strnoyrsinareaflag = teleVerificationDto.getNoyrsinareaflag();

		String strlc1chmnrecfeed = teleVerificationDto.getLc1chmnrecfeed();
		String strlc1chmnrecfeedremark = teleVerificationDto.getLc1chmnrecfeedremarks();
		String strlc1chmnrecfeedflag = teleVerificationDto.getLc1chmnrecfeedflag();

		String strnearlmarkresi = teleVerificationDto.getNearlmarkresi();
		String strnearlmarkresiremark = teleVerificationDto.getNearlmarkresiremarks();
		String strnearlmarkresiflag = teleVerificationDto.getNearlmarkresiflag();

		String stremptype = teleVerificationDto.getEmptype();
		String stremptyremark = teleVerificationDto.getEmptyperemarks();
		String stremptypeflag = teleVerificationDto.getEmptypeflag();

		String strstgorwrkadrssnearlmark = teleVerificationDto.getStgorwrkadrssnearlmark();
		String strstgorwrkadrssnearlmarkremark = teleVerificationDto.getStgorwrkadrssnearlmarkremarks();
		String strstgorwrkadrssnearlmarkflag = teleVerificationDto.getStgorwrkadrssnearlmarkflag();

		String strstgoremprecm = teleVerificationDto.getStgoremprecm();
		String strstgoremprecmremark = teleVerificationDto.getStgoremprecmremarks();
		String strstgoremprecmflag = teleVerificationDto.getStgoremprecmflag();

		String strnoofyrsinstgorbusi = teleVerificationDto.getNoofyrsinstgorbusi();
		String strnoofyrsinstgorbusiremark = teleVerificationDto.getNoofyrsinstgorbusiremarks();
		String strnoofyrsinstgorbuisflag = teleVerificationDto.getNoofyrsinstgorbuisflag();

		String strstgnoofvehi = teleVerificationDto.getStgnoofvehi();
		String strstgnoofvehiremark = teleVerificationDto.getStgnoofvehiremarks();
		String strstgnoofvehiflag = teleVerificationDto.getStgnoofvehiflag();

		String strbikeowner = teleVerificationDto.getOwnerofbike();
		String strbikeownerremark = teleVerificationDto.getOwnerofbikeremarks();
		String strbikeownerflag = teleVerificationDto.getOwnerofbikeflag();

		String strnetincome = teleVerificationDto.getNetincome();
		String strnetincomeremark = teleVerificationDto.getNetincomeremarks();
		String strnetincomeflag = teleVerificationDto.getNetincomeflag();

		String strbikeusearea = teleVerificationDto.getBikeusearea();
		String strbikeusearearemark = teleVerificationDto.getBikeusearearemarks();
		String strbikeuseareaflag = teleVerificationDto.getBikeuseareaflag();

		String strspousename = teleVerificationDto.getSpousename();
		String strspousenameremark = teleVerificationDto.getSpousenameremarks();
		String strspousenameflag = teleVerificationDto.getSpousenameflag();

		String strspouseno = teleVerificationDto.getSpouseno();
		String strspousenoremark = teleVerificationDto.getSpousenoremarks();
		String strspousenoflag = teleVerificationDto.getSpousenoflag();

		String strspouseconfirm = teleVerificationDto.getSpouseconfirm();
		String strspouseconfirmremark = teleVerificationDto.getSpouseconfirmremarks();
		String strspouseconfirmflag = teleVerificationDto.getSpouseconfirmflag();

		String stroffcdistance = teleVerificationDto.getOffcdistance();
		String stroffcdistanceremark = teleVerificationDto.getOffcdistanceremarks();
		String stroffcdistanceflag = teleVerificationDto.getOffcdistanceflag();

		String strrelawithapplicant = teleVerificationDto.getRelawithapplicant();
		String strrelawithapplicantremark = teleVerificationDto.getRelawithapplicantremarks();
		String strrelawithapplicantflag = teleVerificationDto.getRelawithapplicantflag();

		String strpaymentbyrider = teleVerificationDto.getPaymentbyrider();
		String strpaymentbyriderremark = teleVerificationDto.getPaymentbyriderremarks();
		String strpaymentbyriderflag = teleVerificationDto.getPaymentbyriderflag();

		String stryakanum = teleVerificationDto.getYakanum();
		String stryakanumremark = teleVerificationDto.getYakanumremarks();
		String stryakanumflag = teleVerificationDto.getYakanumflag();

		String stryakanumname = teleVerificationDto.getYakanumname();
		String stryakanumnameremark = teleVerificationDto.getYakanumnameremarks();
		String stryakanumnamflag = teleVerificationDto.getYakanumnamflag();

		String strpaymtdetailstovby = teleVerificationDto.getPaymtdetailstovby();
		String strpaymtdetailstovbyremark = teleVerificationDto.getPaymtdetailstovbyremarks();
		String strpaymtdetailstovbyflag = teleVerificationDto.getPaymtdetailstovbyflag();

		String strcashpaymntworeceipt = teleVerificationDto.getCashpaymntworeceipt();
		String strcashpaymntworeceiptremark = teleVerificationDto.getCashpaymntworeceiptremarks();
		String strcashpaymntworeceiptflag = teleVerificationDto.getCashpaymntworeceiptflag();

		String strapplicantknowvby = teleVerificationDto.getApplicantknowvby();
		String strapplicantknowvbyremark = teleVerificationDto.getApplicantknowvbyremarks();
		String strapplicantknowvbyflag = teleVerificationDto.getApplicantknowvbyflag();

		String strrelawithguarantors = teleVerificationDto.getRelawithguarantors();
		String strrelawithguarantorsremark = teleVerificationDto.getRelawithguarantorsremarks();
		String strrelawithguarantorsflag = teleVerificationDto.getRelawithguarantorsflag();

		String strbikeapplied = teleVerificationDto.getBikeapplied();
		String strbikeappliedremarks = teleVerificationDto.getBikeappliedremarks();
		String strbikeappliedflag = teleVerificationDto.getBikeappliedflag();

		String strdownpayment = teleVerificationDto.getDownpayment();
		String strdownpaymentremarks = teleVerificationDto.getDownpaymentremarks();
		String strdownpaymentflag = teleVerificationDto.getDownpaymentflag();

		String strtenure = teleVerificationDto.getTenure();
		String strtenureremarks = teleVerificationDto.getTenureremarks();
		String strtenureflag = teleVerificationDto.getTenureflag();

		String strewioremi = teleVerificationDto.getEwioremi();
		String strewioremiremarks = teleVerificationDto.getEwioremiremarks();
		String strewioremiflag = teleVerificationDto.getEwioremiflag();
		
		String strarrangebtwnrider = teleVerificationDto.getArrangebtwnrider();
		String strarrangebtwnriderRemarks = teleVerificationDto.getArrangebtwnriderremarks();
		String strarrangebtwnriderchkbx = teleVerificationDto.getArrangebtwnriderflag();

		String strresiadrss = teleVerificationDto.getResiadrss();
		String strresiadrssRemarks = teleVerificationDto.getResiadrssremarks();
		String strresiadrsschkbx = teleVerificationDto.getResiadrssflag();
		
		String strnoofyrsinaddrs = teleVerificationDto.getNoofyrinaddrss();
		String strnoofyrsinaddrsRemarks = teleVerificationDto.getNoofyrinaddrssremarks();
		String strnoofyrsinaddrschkbx = teleVerificationDto.getNoofyrinaddrssflag();
		
		String strmbnumnotinname = teleVerificationDto.getMbnonotinname();
		String strmbnumnotinnameRemarks = teleVerificationDto.getMbnonotinnameremarks();
		String strmbnumnotinnamechkbx = teleVerificationDto.getMbnonotinnameflag();

		String strtvid = teleVerificationDto.getCtvid();
		String strtvid2 = teleVerificationDto.getCtvid2();

		strLoginuser = teleVerificationDto.getUserName();

		if (strCustid == null || strCustid.length() == 0) {

			strMsg = "Please select customer id";

		} else {

			if (strTvverdict.equals(strPosverdict)) {
				blnTvverdict = true;
			} else if (strTvverdict.equals(strNegverdict)) {
				blnTvverdict = false;
			}

			TimeStampUtil gts = new TimeStampUtil();
			strTVRequestdatetime = gts.TimeStamp();

			if (strtvid.length() > 0 && strtvid2.length() > 0) {
				Optional<CustomerDetails> opCd = Optional.of(customerRepo.findByotherid(strCustid));
				if (opCd.isPresent()) {
					CustomerDetails cd = opCd.get();
					cd.setTvremarks(strRemarks);
					cd.setTvverified(blnTvverdict);
					cd.setTvuser(strLoginuser);
					cd.setTvdatetime(strTVRequestdatetime);
					customerRepo.save(cd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			} else {
				blnTvverdict = false;
				Optional<CustomerDetails> opCd = Optional.of(customerRepo.findByotherid(strCustid));
				if (opCd.isPresent()) {
					CustomerDetails cd = opCd.get();
					cd.setTvremarks(strRemarks);
					cd.setTvverified(blnTvverdict);
					cd.setTvuser(strLoginuser);
					cd.setTvdatetime(strTVRequestdatetime);
					customerRepo.save(cd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			}

//			int intUser = libraryBean.updateCustomerDetails(strQuery);
//			if (intUser == 1) {
//				System.out.println("Customer " + strCustid + " tv verified");
//			}

			TvVerification tvverify = new TvVerification();
			tvverify.setTvid(strCustid);
			tvverify.setSurname(strSurname);
			tvverify.setSurnameremark(strSurnameremark);
			tvverify.setSurnameflag(strSurnameflag);
			tvverify.setFirstname(strFname);
			tvverify.setFirstnameremark(strfnameremark);
			tvverify.setFirstnameflag(strfnameflag);
			tvverify.setOthername(stroname);
			tvverify.setOthernameremark(stronameremark);
			tvverify.setOthernameflag(stronameflag);
			tvverify.setMaritalstatus(strms);
			tvverify.setMaritalstatusremark(strmsremark);
			tvverify.setMaritalstatusflag(strmsflag);
			tvverify.setSex(strsex);
			tvverify.setSexremark(strsexremark);
			tvverify.setSexflag(strsexflag);
			tvverify.setMobileno(strmobno);
			tvverify.setMobilenoremark(strmobnoremark);
			tvverify.setMobilenoflag(strmobnoflag);
			tvverify.setStage(strstgnm);
			tvverify.setStageremark(strstgnmremark);
			tvverify.setStageflag(strstgnmflag);
			tvverify.setStagechairmanname(strstgchmname);
			tvverify.setStagechairmannameremarks(strstgchmnnameremark);
			tvverify.setStagechairmannameflag(strstgchmnameflag);
			tvverify.setStagechairmanno(strstgchmnno);
			tvverify.setStagechairmannoremarks(strstgchmnnoremarks);
			tvverify.setStagechairmannoflag(strstgchmnnoflag);
			tvverify.setDistrict(strdist);
			tvverify.setDistrictremark(strdistremark);
			tvverify.setDistrictflag(strdistflag);
			tvverify.setLc(strlcnm);
			tvverify.setLcremark(strlcnmremark);
			tvverify.setLcflag(strlcnmflag);
			tvverify.setParish(strpar);
			tvverify.setParishremark(strparremark);
			tvverify.setParishflag(strparflag);
			tvverify.setNationalid(strnid);
			tvverify.setNationalidremark(strnidremark);
			tvverify.setNationalidflag(strnidflag);
			tvverify.setBikeregno(strbregno);
			tvverify.setBikeregnoremark(strbregnoremark);
			tvverify.setBikeregnoflag(strbregnoflag);
			tvverify.setBikeuse(strbuse);
			tvverify.setBikeuseremark(strbuseremark);
			tvverify.setBikeuseflag(strbuseflag);
			tvverify.setDob(strdob);
			tvverify.setDobremark(strdobremark);
			tvverify.setDobflag(strdobflag);
			tvverify.setCounty(strcntd);
			tvverify.setCountyremark(strcntremark);
			tvverify.setCountyflag(strcntflag);
			tvverify.setSubcounty(strscnt);
			tvverify.setSubcountyremark(strscntremark);
			tvverify.setSubcountyflag(strscntflag);
			tvverify.setVillage(strvil);
			tvverify.setVillageremark(strvilremark);
			tvverify.setVillageflag(strvilflag);
			tvverify.setYearsinvillage(stryrvil);
			tvverify.setYearsinvillageremark(stryrvilremark);
			tvverify.setYearsinvillageflag(stryrvilflag);
			tvverify.setNextofkin(strnok);
			tvverify.setNextofkinremark(strnokremark);
			tvverify.setNextofkinflag(strnokflag);
			tvverify.setNokmobileno(strnokm);
			tvverify.setNokmobilenoremark(strnokmremark);
			tvverify.setNokmobilenoflag(strnokmflag);
			tvverify.setNokrelationship(strnokr);
			tvverify.setNokrelationshipremark(strnokrremark);
			tvverify.setNokrelationshipflag(strnokrflag);
			tvverify.setNokagreeing(strnoka);
			tvverify.setNokagreeingremark(strnokaremark);
			tvverify.setNokagreeingflag(strnokaflag);
			tvverify.setDrivingpermit(strdl);
			tvverify.setDrivingpermitremark(strdlremark);
			tvverify.setDrivingpermitflag(strdlflag);
			tvverify.setNationality(strnat);
			tvverify.setNationalityremark(strnatremark);
			tvverify.setNationalityflag(strnatflag);
			tvverify.setNoofdependants(strnod);
			tvverify.setNoofdependantsremark(strnodremark);
			tvverify.setNoofdependantsflag(strnodflag);
			tvverify.setOwnhouserented(stror);
			tvverify.setOwnhouserentedremark(strorremark);
			tvverify.setOwnhouserentedflag(strorflag);
			tvverify.setLandlordname(strll);
			tvverify.setLandlordnameremark(strllremark);
			tvverify.setLandlordnameflag(strllflag);
			tvverify.setLandlordmobileno(strllmob);
			tvverify.setLandlordmobilenoremark(strllmobremark);
			tvverify.setLandlordmobilenoflag(strllmobflag);
			tvverify.setRentpm(strrpm);
			tvverify.setRentpmremark(strrpmremark);
			tvverify.setRentpmflag(strrpmflag);
			tvverify.setOtherincomesource(strois);
			tvverify.setOtherincomesourceremark(stroisremark);
			tvverify.setOtherincomesourceflag(stroisflag);
			tvverify.setDownpaymentsource(strdps);
			tvverify.setDownpaymentsourceremark(strdpsremark);
			tvverify.setDownpaymentsourceflag(strdpsflg);
			tvverify.setPermanentaddress(strpadd);
			tvverify.setPermanentaddressremark(strpaddremark);
			tvverify.setPermanentaddressflag(strpaddflag);
			tvverify.setFathersname(strfath);
			tvverify.setFathersnameremark(strfathremark);
			tvverify.setFathersnameflag(strfathflag);
			tvverify.setMothersname(strmoth);
			tvverify.setMothersnameremark(strmothremark);
			tvverify.setMothersnameflag(strmothflag);
			tvverify.setNearbypolicestation(strnps);
			tvverify.setNearbypolicestationremark(strnpsremark);
			tvverify.setNearbypolicestationflag(strnpsflag);
			tvverify.setLcmobileno(strlcnmobno);
			tvverify.setLcmobilenoremark(strlcnmobnoremark);
			tvverify.setLcmobilenoflag(strlcnmobnoflag);
			tvverify.setCusttype(strcusttype);
			tvverify.setCusttyperemark(strcusttyperemark);
			tvverify.setCusttypeflag(strcusttypeflag);

			tvverify.setLlrentfeedback(strllrentfeedback);
			tvverify.setLlrentfeedbackremarks(strllrentfeedbackremark);
			tvverify.setLlrentfeedbackflag(strllrentfeedbackflag);

			tvverify.setNoyrsinarea(strnoyrsinarea);
			tvverify.setNoyrsinarearemarks(strnoyrsinarearemark);
			tvverify.setNoyrsinareaflag(strnoyrsinareaflag);

			tvverify.setLc1chmnrecfeed(strlc1chmnrecfeed);
			tvverify.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
			tvverify.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);

			tvverify.setNearlmarkresi(strnearlmarkresi);
			tvverify.setNearlmarkresiremarks(strnearlmarkresiremark);
			tvverify.setNearlmarkresiflag(strnearlmarkresiflag);

			tvverify.setEmptype(stremptype);
			tvverify.setEmptyperemarks(stremptyremark);
			tvverify.setEmptypeflag(stremptypeflag);

			tvverify.setStgorwrkadrssnearlmark(strstgorwrkadrssnearlmark);
			tvverify.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
			tvverify.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);

			tvverify.setStgoremprecm(strstgoremprecm);
			tvverify.setStgoremprecmremarks(strstgoremprecmremark);
			tvverify.setStgoremprecmflag(strstgoremprecmflag);

			tvverify.setNoofyrsinstgorbusi(strnoofyrsinstgorbusi);
			tvverify.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
			tvverify.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);

			tvverify.setStgnoofvehi(strstgnoofvehi);
			tvverify.setStgnoofvehiremarks(strstgnoofvehiremark);
			tvverify.setStgnoofvehiflag(strstgnoofvehiflag);

			tvverify.setOwnerofbike(strbikeowner);
			tvverify.setOwnerofbikeremarks(strbikeownerremark);
			tvverify.setOwnerofbikeflag(strbikeownerflag);

			tvverify.setNetincome(strnetincome);
			tvverify.setNetincomeremarks(strnetincomeremark);
			tvverify.setNetincomeflag(strnetincomeflag);

			tvverify.setBikeusearea(strbikeusearea);
			tvverify.setBikeusearearemarks(strbikeusearearemark);
			tvverify.setBikeuseareaflag(strbikeuseareaflag);

			tvverify.setSpousename(strspousename);
			tvverify.setSpousenameremarks(strspousenameremark);
			tvverify.setSpousenameflag(strspousenameflag);

			tvverify.setSpouseno(strspouseno);
			tvverify.setSpousenoremarks(strspousenoremark);
			tvverify.setSpousenoflag(strspousenoflag);

			tvverify.setSpouseconfirm(strspouseconfirm);
			tvverify.setSpouseconfirmremarks(strspouseconfirmremark);
			tvverify.setSpouseconfirmflag(strspouseconfirmflag);

			tvverify.setOffcdistance(stroffcdistance);
			tvverify.setOffcdistanceremarks(stroffcdistanceremark);
			tvverify.setOffcdistanceflag(stroffcdistanceflag);

			tvverify.setRelawithapplicant(strrelawithapplicant);
			tvverify.setRelawithapplicantremarks(strrelawithapplicantremark);
			tvverify.setRelawithapplicantflag(strrelawithapplicantflag);

			tvverify.setPaymentbyrider(strpaymentbyrider);
			tvverify.setPaymentbyriderremarks(strpaymentbyriderremark);
			tvverify.setPaymentbyriderflag(strpaymentbyriderflag);

			tvverify.setYakanum(stryakanum);
			tvverify.setYakanumremarks(stryakanumremark);
			tvverify.setYakanumflag(stryakanumflag);

			tvverify.setYakanumname(stryakanumname);
			tvverify.setYakanumnameremarks(stryakanumnameremark);
			tvverify.setYakanumnamflag(stryakanumnamflag);

			tvverify.setPaymtdetailstovby(strpaymtdetailstovby);
			tvverify.setPaymtdetailstovbyremarks(strpaymtdetailstovbyremark);
			tvverify.setPaymtdetailstovbyflag(strpaymtdetailstovbyflag);

			tvverify.setCashpaymntworeceipt(strcashpaymntworeceipt);
			tvverify.setCashpaymntworeceiptremarks(strcashpaymntworeceiptremark);
			tvverify.setCashpaymntworeceiptflag(strcashpaymntworeceiptflag);

			tvverify.setApplicantknowvby(strapplicantknowvby);
			tvverify.setApplicantknowvbyremarks(strapplicantknowvbyremark);
			tvverify.setApplicantknowvbyflag(strapplicantknowvbyflag);

			tvverify.setRelawithguarantors(strrelawithguarantors);
			tvverify.setRelawithguarantorsremarks(strrelawithguarantorsremark);
			tvverify.setRelawithguarantorsflag(strrelawithguarantorsflag);
			
			tvverify.setBikeapplied(strbikeapplied);
			tvverify.setBikeappliedremarks(strbikeappliedremarks);
			tvverify.setBikeappliedflag(strbikeappliedflag);
			
			tvverify.setDownpayment(strdownpayment);
			tvverify.setDownpaymentremarks(strdownpaymentremarks);
			tvverify.setDownpaymentflag(strdownpaymentflag);
			
			tvverify.setTenure(strtenure);
			tvverify.setTenureremarks(strtenureremarks);
			tvverify.setTenureflag(strtenureflag);
			
			tvverify.setEwioremi(strewioremi);
			tvverify.setEwioremiremarks(strewioremiremarks);
			tvverify.setEwioremiflag(strewioremiflag);
			
			tvverify.setArrangebtwnrider(strarrangebtwnrider);
			tvverify.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
			tvverify.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
			
			tvverify.setResiadrss(strresiadrss);
			tvverify.setResiadrssremarks(strresiadrssRemarks);
			tvverify.setResiadrssflag(strresiadrsschkbx);
			
			tvverify.setNoofyrinaddrss(strnoofyrsinaddrs);
			tvverify.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
			tvverify.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);
					
			tvverify.setMbnonotinname(strmbnumnotinname);
			tvverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			tvverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

			tvverify.setFirstguarantor(false);
			tvverify.setSecondguarantor(false);
			tvverify.setTvremarks(strRemarks);
			tvverify.setTvverdict(blnTvverdict);
			
			

			try {
				teleVerifyRepo.save(tvverify);
				response.setData("TV Verification for : " + strCustid + " (Customer) saved successfully.");

			} catch (Exception e) {
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {

					Optional<TvVerification> optv = teleVerifyRepo.findById(strCustid);

					if (optv.isPresent()) {
						TvVerification tv = optv.get();
						tv.setSurnameremark(strSurnameremark);
						tv.setSurnameflag(strSurnameflag);
						tv.setFirstnameremark(strfnameremark);
						tv.setFirstnameflag(strfnameflag);
						tv.setOthernameremark(stronameremark);
						tv.setOthernameflag(stronameflag);
						tv.setMaritalstatusremark(strmsremark);
						tv.setMaritalstatusflag(strmsflag);
						tv.setSexremark(strsexremark);
						tv.setSexflag(strsexflag);
						tv.setMobilenoremark(strmobnoremark);
						tv.setMobilenoflag(strmobnoflag);
						tv.setStageremark(strstgnmremark);
						tv.setStageflag(strstgnmflag);
						tv.setStagechairmannameremarks(strstgchmnnameremark);
						tv.setStagechairmannameflag(strstgchmnameflag);
						tv.setStagechairmannoremarks(strstgchmnnoremarks);
						tv.setStagechairmannoflag(strstgchmnnoflag);
						tv.setDistrictremark(strdistremark);
						tv.setDistrictflag(strdistflag);
						tv.setLcremark(strlcnmremark);
						tv.setLcflag(strlcnmflag);
						tv.setParishremark(strparremark);
						tv.setParishflag(strparflag);
						tv.setNationalidremark(strnidremark);
						tv.setNationalidflag(strnidflag);
						tv.setBikeregnoremark(strbregnoremark);
						tv.setBikeregnoflag(strbregnoflag);
						tv.setBikeuseremark(strbuseremark);
						tv.setBikeuseflag(strbuseflag);
						tv.setDobremark(strdobremark);
						tv.setDobflag(strdobflag);
						tv.setCountyremark(strcntremark);
						tv.setCountyflag(strcntflag);
						tv.setSubcountyremark(strscntremark);
						tv.setSubcountyflag(strscntflag);
						tv.setVillageremark(strvilremark);
						tv.setVillageflag(strvilflag);
						tv.setYearsinvillageremark(stryrvilremark);
						tv.setYearsinvillageflag(stryrvilflag);
						tv.setNextofkinremark(strnokremark);
						tv.setNextofkinflag(strnokflag);
						tv.setNokmobilenoremark(strnokmremark);
						tv.setNokmobilenoflag(strnokmflag);
						tv.setNokrelationshipremark(strnokrremark);
						tv.setNokrelationshipflag(strnokrflag);
						tv.setNokagreeingremark(strnokaremark);
						tv.setNokagreeingflag(strnokaflag);
						tv.setDrivingpermitremark(strdlremark);
						tv.setDrivingpermitflag(strdlflag);
						tv.setNationalityremark(strnatremark);
						tv.setNationalityflag(strnatflag);
						tv.setNoofdependantsremark(strnodremark);
						tv.setNoofdependantsflag(strnodflag);
						tv.setOwnhouserentedremark(strorremark);
						tv.setOwnhouserentedflag(strorflag);
						tv.setLandlordnameremark(strllremark);
						tv.setLandlordnameflag(strllflag);
						tv.setLandlordmobilenoremark(strllmobremark);
						tv.setLandlordmobilenoflag(strllmobflag);
						tv.setRentpmremark(strrpmremark);
						tv.setRentpmflag(strrpmflag);
						tv.setOtherincomesourceremark(stroisremark);
						tv.setOtherincomesourceflag(stroisflag);
						tv.setDownpaymentsourceremark(strdpsremark);
						tv.setDownpaymentsourceflag(strdpsflg);
						tv.setPermanentaddressremark(strpaddremark);
						tv.setPermanentaddressflag(strpaddflag);
						tv.setFathersnameremark(strfathremark);
						tv.setFathersnameflag(strfathflag);
						tv.setMothersnameremark(strmothremark);
						tv.setMothersnameflag(strmothflag);
						tv.setNearbypolicestationremark(strnpsremark);
						tv.setNearbypolicestationflag(strnpsflag);
						tv.setLcmobilenoremark(strlcnmobnoremark);
						tv.setLcmobilenoflag(strlcnmobnoflag);
						tv.setCusttyperemark(strcusttyperemark);
						tv.setCusttypeflag(strcusttypeflag);
						tv.setTvremarks(strRemarks);
						tv.setTvverdict(blnTvverdict);

						tv.setLlrentfeedbackremarks(strllrentfeedbackremark);
						tv.setLlrentfeedbackflag(strllrentfeedbackflag);
						tv.setNoyrsinarearemarks(strnoyrsinarearemark);
						tv.setNoyrsinareaflag(strnoyrsinareaflag);
						tv.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
						tv.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);
						tv.setNearlmarkresiremarks(strnearlmarkresiremark);
						tv.setNearlmarkresiflag(strnearlmarkresiflag);
						tv.setEmptype(stremptypeflag);
						tv.setEmptypeflag(stremptypeflag);
						tv.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
						tv.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);
						tv.setStgoremprecmremarks(strstgoremprecmremark);
						tv.setStgoremprecmflag(strstgoremprecmflag);
						tv.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
						tv.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);
						tv.setStgnoofvehiremarks(strstgnoofvehiremark);
						tv.setStgnoofvehiflag(strstgnoofvehiflag);
						tv.setBikeownerremark(strbikeownerremark);
						tv.setBikeownerflag(strbikeownerflag);
						tv.setNetincomeremarks(strnetincomeremark);
						tv.setNetincomeflag(strnetincomeflag);
						tv.setBikeusearearemarks(strbikeusearearemark);
						tv.setBikeuseareaflag(strbikeuseareaflag);
						tv.setSpousenameremarks(strspousenameremark);
						tv.setSpousenameflag(strspousenameflag);
						tv.setSpousenoremarks(strspousenoremark);
						tv.setSpousenoflag(strspousenoflag);
						tv.setSpouseconfirmremarks(strspouseconfirmremark);
						tv.setSpouseconfirmflag(strspouseconfirmflag);
						tv.setOffcdistanceremarks(stroffcdistanceremark);
						tv.setOffcdistanceflag(stroffcdistanceflag);
						tv.setRelawithapplicantremarks(strrelawithapplicantremark);
						tv.setRelawithapplicantflag(strrelawithapplicantflag);
						tv.setPaymentbyriderremarks(strpaymentbyriderremark);
						tv.setPaymentbyriderflag(strpaymentbyriderflag);
						tv.setYakanumremarks(stryakanumremark);
						tv.setYakanumflag(stryakanumflag);
						tv.setYakanumnameremarks(stryakanumnameremark);
						tv.setYakanumnamflag(stryakanumnamflag);
						tv.setPaymtdetailstovbyremarks(strpaymtdetailstovbyremark);
						tv.setPaymtdetailstovbyflag(strpaymtdetailstovbyflag);
						tv.setCashpaymntworeceiptremarks(strcashpaymntworeceiptremark);
						tv.setCashpaymntworeceiptflag(strcashpaymntworeceiptflag);
						tv.setApplicantknowvbyremarks(strapplicantknowvbyremark);
						tv.setApplicantknowvbyflag(strapplicantknowvbyflag);
						tv.setRelawithguarantorsremarks(strrelawithguarantorsremark);
						tv.setRelawithguarantorsflag(strrelawithguarantorsflag);
						tv.setBikeappliedremarks(strbikeappliedremarks);
						tv.setBikeappliedflag(strbikeappliedflag);
						tv.setDownpaymentremarks(strdownpaymentremarks);
						tv.setDownpaymentflag(strdownpaymentflag);
						tv.setTenureremarks(strtenureremarks);
						tv.setTenureflag(strtenureflag);
						tv.setEwioremiremarks(strewioremiremarks);
						tv.setEwioremiflag(strewioremiflag);
						tv.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
						tv.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
						tv.setResiadrssremarks(strresiadrssRemarks);
						tv.setResiadrssflag(strresiadrsschkbx);
						tv.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
						tv.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);		
						tv.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						tv.setMbnonotinnameflag(strmbnumnotinnamechkbx);

						teleVerifyRepo.save(tv);
						response.setData("TV Verification: " + strCustid + " (Customer) updated successfully");
					}
				} else if (strMsg.contains("")) {
					strMsg = "General error";
				}
			}
		}
		return response;
	}
	
	
	public Response<String> addRiderTv(TeleVerificationDto teleVerificationDto) {

		Response<String> response = new Response<>();

		String strCustid, strRemarks, strTvverdict;

		String strMsg = "";
		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnTvverdict = false;

		String strTVRequestdatetime = "";
		String strLoginuser = "";

		strCustid = teleVerificationDto.getTvid();
//		int Custid = Integer.parseInt(strCustid);
		strTvverdict = teleVerificationDto.getStrtvverdict();
//		recommed or not (recommed , notrecommed)
		strRemarks = teleVerificationDto.getTvremarks();
//		last box

		
		String strFname = teleVerificationDto.getFirstnameRdR();
		String strfnameremark = teleVerificationDto.getFirstnameremarkRdR();
		String strfnameflag = teleVerificationDto.getFirstnameflagRdR();
		
		String strms = teleVerificationDto.getMaritalstatusRdR();
		String strmsremark = teleVerificationDto.getMaritalstatusremarkRdR();
		String strmsflag = teleVerificationDto.getMaritalstatusflagRdR();
		
		String strmobno = teleVerificationDto.getMobilenoRdR();
		String strmobnoremark = teleVerificationDto.getMobilenoremarkRdR();
		String strmobnoflag = teleVerificationDto.getMobilenoflagRdR();
		
		String strstgnm = teleVerificationDto.getStageRdR();
		String strstgnmremark = teleVerificationDto.getStageremarkRdR();
		String strstgnmflag = teleVerificationDto.getStageflagRdR();
		
		String strstgchmname = teleVerificationDto.getStagechairmanRdR();
		String strstgchmnnameremark = teleVerificationDto.getStagechairmanremarkRdR();
		String strstgchmnameflag = teleVerificationDto.getStagechairmanflagRdR();
		
		String strstgchmnno = teleVerificationDto.getStagechairmannoRdR();
		String strstgchmnnoremarks = teleVerificationDto.getStagechairmannoremarkRdR();
		String strstgchmnnoflag = teleVerificationDto.getStagechairmannoflagRdR();
		
		String strlcnm = teleVerificationDto.getLcRdR();
		String strlcnmremark = teleVerificationDto.getLcremarkRdR();
		String strlcnmflag = teleVerificationDto.getLcflagRdR();
		
		String strnid = teleVerificationDto.getNationalidRdR();
		String strnidremark = teleVerificationDto.getNationalidremarkRdR();
		String strnidflag = teleVerificationDto.getNationalidflagRdR();
		
		String strbregno = teleVerificationDto.getBikeregnoRdR();
		String strbregnoremark = teleVerificationDto.getBikeregnoremarkRdR();
		String strbregnoflag = teleVerificationDto.getBikeregnoflagRdR();
		
		String strbuse = teleVerificationDto.getNewbikeuseRdR();
		String strbuseremark = teleVerificationDto.getNewbikeuseremarkRdR();
		String strbuseflag = teleVerificationDto.getNewbikeuseflagRdR();
		
		String strdob = teleVerificationDto.getDobRdR();
		String strdobremark = teleVerificationDto.getDobremarkRdR();
		String strdobflag = teleVerificationDto.getDobflagRdR();
		
		String stror = teleVerificationDto.getOwnhouserentedRdR();
		String strorremark = teleVerificationDto.getOwnhouserentedremarkRdR();
		String strorflag = teleVerificationDto.getOwnhouserentedflagRdR();
		
		String strll = teleVerificationDto.getLandlordnameRdR();
		String strllremark = teleVerificationDto.getLandlordnameremarkRdR();
		String strllflag = teleVerificationDto.getLandlordnameflagRdR();
		
		String strllmob = teleVerificationDto.getLandlordmobilenoRdR();
		String strllmobremark = teleVerificationDto.getLandlordmobilenoremarkRdR();
		String strllmobflag = teleVerificationDto.getLandlordmobilenoflagRdR();
		
		String strrpm = teleVerificationDto.getRentpmRdR();
		String strrpmremark = teleVerificationDto.getRentpmremarkRdR();
		String strrpmflag = teleVerificationDto.getRentpmflagRdR();
		
		String strois = teleVerificationDto.getOtherincomesourceRdR();
		String stroisremark = teleVerificationDto.getOtherincomesourceremarkRdR();
		String stroisflag = teleVerificationDto.getOtherincomesourceflagRdR();
		
		String strpadd = teleVerificationDto.getPermanentaddressRdR();
		String strpaddremark = teleVerificationDto.getPermanentaddressremarkRdR();
		String strpaddflag = teleVerificationDto.getPermanentaddressflagRdR();
		
		String strnps = teleVerificationDto.getNearbypolicestationRdR();
		String strnpsremark = teleVerificationDto.getNearbypolicestationremarkRdR();
		String strnpsflag = teleVerificationDto.getNearbypolicestationflagRdR();
		
		String strlcnmobno = teleVerificationDto.getLcmobilenoRdR();
		String strlcnmobnoremark = teleVerificationDto.getLcmobilenoremarkRdR();
		String strlcnmobnoflag = teleVerificationDto.getLcmobilenoflagRdR();

		String strllrentfeedback = teleVerificationDto.getLlrentfeedbackRdR();
		String strllrentfeedbackremark = teleVerificationDto.getLlrentfeedbackremarkRdR();
		String strllrentfeedbackflag = teleVerificationDto.getLlrentfeedbackflagRdR();

		String strnoyrsinarea = teleVerificationDto.getNoyrsinareaRdR();
		String strnoyrsinarearemark = teleVerificationDto.getNoyrsinarearemarkRdR();
		String strnoyrsinareaflag = teleVerificationDto.getNoyrsinareaflagRdR();

		String strlc1chmnrecfeed = teleVerificationDto.getLc1chmnrecfeedRdR();
		String strlc1chmnrecfeedremark = teleVerificationDto.getLc1chmnrecfeedremarkRdR();
		String strlc1chmnrecfeedflag = teleVerificationDto.getLc1chmnrecfeedflagRdR();

		String strnearlmarkresi = teleVerificationDto.getNearlmarkresiRdR();
		String strnearlmarkresiremark = teleVerificationDto.getNearlmarkresiremarkRdR();
		String strnearlmarkresiflag = teleVerificationDto.getNearlmarkresiflagRdR();

		String stremptype = teleVerificationDto.getEmptypeRdR();
		String stremptyremark = teleVerificationDto.getEmptyperemarkRdR();
		String stremptypeflag = teleVerificationDto.getEmptypeflagRdR();

		String strstgorwrkadrssnearlmark = teleVerificationDto.getStgorwrkadrssnearlmarkRdR();
		String strstgorwrkadrssnearlmarkremark = teleVerificationDto.getStgorwrkadrssnearlmarkremarkRdR();
		String strstgorwrkadrssnearlmarkflag = teleVerificationDto.getStgorwrkadrssnearlmarkflagRdR();

		String strstgoremprecm = teleVerificationDto.getStgoremprecmRdR();
		String strstgoremprecmremark = teleVerificationDto.getStgoremprecmremarkRdR();
		String strstgoremprecmflag = teleVerificationDto.getStgoremprecmflagRdR();

		String strnoofyrsinstgorbusi = teleVerificationDto.getNoofyrsinstgorbusiRdR();
		String strnoofyrsinstgorbusiremark = teleVerificationDto.getNoofyrsinstgorbusiremarkRdR();
		String strnoofyrsinstgorbuisflag = teleVerificationDto.getNoofyrsinstgorbusinessflagRdR();

		String strstgnoofvehi = teleVerificationDto.getStgnoofvehiRdR();
		String strstgnoofvehiremark = teleVerificationDto.getStgnoofvehiremarkRdR();
		String strstgnoofvehiflag = teleVerificationDto.getStgnoofvehiflagRdR();

		String strbikeowner = teleVerificationDto.getBikeownerRdR();
		String strbikeownerremark = teleVerificationDto.getBikeownerremarkRdR();
		String strbikeownerflag = teleVerificationDto.getBikeownerflagRdR();

		String strnetincome = teleVerificationDto.getNetincomeRdR();
		String strnetincomeremark = teleVerificationDto.getNetincomeremarkRdR();
		String strnetincomeflag = teleVerificationDto.getNetincomeflagRdR();

		String strbikeusearea = teleVerificationDto.getBikeuseareaRdR();
		String strbikeusearearemark = teleVerificationDto.getBikeusearearemarkRdR();
		String strbikeuseareaflag = teleVerificationDto.getBikeuseareaflagRdR();

		String strspousename = teleVerificationDto.getSpousenameRdR();
		String strspousenameremark = teleVerificationDto.getSpousenameremarkRdR();
		String strspousenameflag = teleVerificationDto.getSpousenameflagRdR();

		String strspouseno = teleVerificationDto.getSpousenoRdR();
		String strspousenoremark = teleVerificationDto.getSpousenonoremarkRdR();
		String strspousenoflag = teleVerificationDto.getSpousenonoflagRdR();

		String strspouseconfirm = teleVerificationDto.getSpouseconfirmRdR();
		String strspouseconfirmremark = teleVerificationDto.getSpouseconfirmremarkRdR();
		String strspouseconfirmflag = teleVerificationDto.getSpouseconfirmflagRdR();


		String strrelawithapplicant = teleVerificationDto.getRelawithapplicantRdR();
		String strrelawithapplicantremark = teleVerificationDto.getRelawithapplicantremarkRdR();
		String strrelawithapplicantflag = teleVerificationDto.getRelawithapplicantflagRdR();

		String strpaymentbyrider = teleVerificationDto.getPaymentbyriderRdR();
		String strpaymentbyriderremark = teleVerificationDto.getPaymentbyriderremarkRdR();
		String strpaymentbyriderflag = teleVerificationDto.getPaymentbyriderflagRdR();

//		String strbikeapplied = teleVerificationDto.getBikeapplied();
//		String strbikeappliedremarks = teleVerificationDto.getBikeappliedremarks();
//		String strbikeappliedflag = teleVerificationDto.getBikeappliedflag();

		
		String strarrangebtwnrider = teleVerificationDto.getArrangebtwnriderRdR();
		String strarrangebtwnriderRemarks = teleVerificationDto.getArrangebtwnriderremarkRdR();
		String strarrangebtwnriderchkbx = teleVerificationDto.getArrangebtwnriderflagRdR();

		String strresiadrss = teleVerificationDto.getResiadrssRdR();
		String strresiadrssRemarks = teleVerificationDto.getResiadrssremarkRdR();
		String strresiadrsschkbx = teleVerificationDto.getResiadrssflagRdR();
		
		String strnoofyrsinaddrs = teleVerificationDto.getNoofyrinaddrssRdR();
		String strnoofyrsinaddrsRemarks = teleVerificationDto.getNoofyrinaddrssremarkRdR();
		String strnoofyrsinaddrschkbx = teleVerificationDto.getNoofyrinaddrssflagRdR();
		
		String strmbnumnotinname = teleVerificationDto.getMbnonotinnameRdR();
		String strmbnumnotinnameRemarks = teleVerificationDto.getMbnonotinnameremarkRdR();
		String strmbnumnotinnamechkbx = teleVerificationDto.getMbnonotinnameflagRdR();

		String strtvid = teleVerificationDto.getCtvid();
		String strtvid2 = teleVerificationDto.getCtvid2();

		strLoginuser = teleVerificationDto.getUserName();

		if (strCustid == null || strCustid.length() == 0) {

			strMsg = "Please select customer id";

		} else {

			if (strTvverdict.equals(strPosverdict)) {
				blnTvverdict = true;
			} else if (strTvverdict.equals(strNegverdict)) {
				blnTvverdict = false;
			}

			TimeStampUtil gts = new TimeStampUtil();
			strTVRequestdatetime = gts.TimeStamp();

			if (strtvid.length() > 0 && strtvid2.length() > 0) {
				Optional<RiderDetails> opCd = Optional.of(riderRepo.findBynationalid(strCustid));
				if (opCd.isPresent()) {
					RiderDetails rd = opCd.get();
					rd.setTvremarks(strRemarks);
					rd.setTvverified(blnTvverdict);
					rd.setTvuser(strLoginuser);
					rd.setTvdatetime(strTVRequestdatetime);
					riderRepo.save(rd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			} else {
				blnTvverdict = false;
				Optional<RiderDetails> opCd = Optional.of(riderRepo.findBynationalid(strCustid));
				if (opCd.isPresent()) {
					RiderDetails rd = opCd.get();
					rd.setTvremarks(strRemarks);
					rd.setTvverified(blnTvverdict);
					rd.setTvuser(strLoginuser);
					rd.setTvdatetime(strTVRequestdatetime);
					riderRepo.save(rd);
				}
//				strQuery = "UPDATE CustomerDetails set tvverified = " + blnTvverdict + ", tvremarks = " + "'" + strRemarks + "', tvuser = " + "'" + strLoginuser + "', tvdatetime = " + "'" + strTVRequestdatetime + "'  where otherid = " + "'" + strCustid + "'";
			}


			TvVerification tvverify = new TvVerification();
			tvverify.setTvid(strCustid);
		
			tvverify.setFirstname(strFname);
			tvverify.setFirstnameremark(strfnameremark);
			tvverify.setFirstnameflag(strfnameflag);
			
			tvverify.setMaritalstatus(strms);
			tvverify.setMaritalstatusremark(strmsremark);
			tvverify.setMaritalstatusflag(strmsflag);
			tvverify.setMobileno(strmobno);
			tvverify.setMobilenoremark(strmobnoremark);
			tvverify.setMobilenoflag(strmobnoflag);
			tvverify.setStage(strstgnm);
			tvverify.setStageremark(strstgnmremark);
			tvverify.setStageflag(strstgnmflag);
			tvverify.setStagechairmanname(strstgchmname);
			tvverify.setStagechairmannameremarks(strstgchmnnameremark);
			tvverify.setStagechairmannameflag(strstgchmnameflag);
			tvverify.setStagechairmanno(strstgchmnno);
			tvverify.setStagechairmannoremarks(strstgchmnnoremarks);
			tvverify.setStagechairmannoflag(strstgchmnnoflag);
			
			tvverify.setLc(strlcnm);
			tvverify.setLcremark(strlcnmremark);
			tvverify.setLcflag(strlcnmflag);
			
			tvverify.setNationalid(strnid);
			tvverify.setNationalidremark(strnidremark);
			tvverify.setNationalidflag(strnidflag);
			tvverify.setBikeregno(strbregno);
			tvverify.setBikeregnoremark(strbregnoremark);
			tvverify.setBikeregnoflag(strbregnoflag);
			tvverify.setBikeuse(strbuse);
			tvverify.setBikeuseremark(strbuseremark);
			tvverify.setBikeuseflag(strbuseflag);
			tvverify.setDob(strdob);
			tvverify.setDobremark(strdobremark);
			tvverify.setDobflag(strdobflag);
			
			tvverify.setOwnhouserented(stror);
			tvverify.setOwnhouserentedremark(strorremark);
			tvverify.setOwnhouserentedflag(strorflag);
			tvverify.setLandlordname(strll);
			tvverify.setLandlordnameremark(strllremark);
			tvverify.setLandlordnameflag(strllflag);
			tvverify.setLandlordmobileno(strllmob);
			tvverify.setLandlordmobilenoremark(strllmobremark);
			tvverify.setLandlordmobilenoflag(strllmobflag);
			tvverify.setRentpm(strrpm);
			tvverify.setRentpmremark(strrpmremark);
			tvverify.setRentpmflag(strrpmflag);
			tvverify.setOtherincomesource(strois);
			tvverify.setOtherincomesourceremark(stroisremark);
			tvverify.setOtherincomesourceflag(stroisflag);
			
			tvverify.setPermanentaddress(strpadd);
			tvverify.setPermanentaddressremark(strpaddremark);
			tvverify.setPermanentaddressflag(strpaddflag);
			
			tvverify.setNearbypolicestation(strnps);
			tvverify.setNearbypolicestationremark(strnpsremark);
			tvverify.setNearbypolicestationflag(strnpsflag);
			tvverify.setLcmobileno(strlcnmobno);
			tvverify.setLcmobilenoremark(strlcnmobnoremark);
			tvverify.setLcmobilenoflag(strlcnmobnoflag);
			
			tvverify.setLlrentfeedback(strllrentfeedback);
			tvverify.setLlrentfeedbackremarks(strllrentfeedbackremark);
			tvverify.setLlrentfeedbackflag(strllrentfeedbackflag);

			tvverify.setNoyrsinarea(strnoyrsinarea);
			tvverify.setNoyrsinarearemarks(strnoyrsinarearemark);
			tvverify.setNoyrsinareaflag(strnoyrsinareaflag);

			tvverify.setLc1chmnrecfeed(strlc1chmnrecfeed);
			tvverify.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
			tvverify.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);

			tvverify.setNearlmarkresi(strnearlmarkresi);
			tvverify.setNearlmarkresiremarks(strnearlmarkresiremark);
			tvverify.setNearlmarkresiflag(strnearlmarkresiflag);

			tvverify.setEmptype(stremptype);
			tvverify.setEmptyperemarks(stremptyremark);
			tvverify.setEmptypeflag(stremptypeflag);

			tvverify.setStgorwrkadrssnearlmark(strstgorwrkadrssnearlmark);
			tvverify.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
			tvverify.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);

			tvverify.setStgoremprecm(strstgoremprecm);
			tvverify.setStgoremprecmremarks(strstgoremprecmremark);
			tvverify.setStgoremprecmflag(strstgoremprecmflag);

			tvverify.setNoofyrsinstgorbusi(strnoofyrsinstgorbusi);
			tvverify.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
			tvverify.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);

			tvverify.setStgnoofvehi(strstgnoofvehi);
			tvverify.setStgnoofvehiremarks(strstgnoofvehiremark);
			tvverify.setStgnoofvehiflag(strstgnoofvehiflag);

			tvverify.setOwnerofbike(strbikeowner);
			tvverify.setOwnerofbikeremarks(strbikeownerremark);
			tvverify.setOwnerofbikeflag(strbikeownerflag);

			tvverify.setNetincome(strnetincome);
			tvverify.setNetincomeremarks(strnetincomeremark);
			tvverify.setNetincomeflag(strnetincomeflag);

			tvverify.setBikeusearea(strbikeusearea);
			tvverify.setBikeusearearemarks(strbikeusearearemark);
			tvverify.setBikeuseareaflag(strbikeuseareaflag);

			tvverify.setSpousename(strspousename);
			tvverify.setSpousenameremarks(strspousenameremark);
			tvverify.setSpousenameflag(strspousenameflag);

			tvverify.setSpouseno(strspouseno);
			tvverify.setSpousenoremarks(strspousenoremark);
			tvverify.setSpousenoflag(strspousenoflag);

			tvverify.setSpouseconfirm(strspouseconfirm);
			tvverify.setSpouseconfirmremarks(strspouseconfirmremark);
			tvverify.setSpouseconfirmflag(strspouseconfirmflag);

			
			tvverify.setRelawithapplicant(strrelawithapplicant);
			tvverify.setRelawithapplicantremarks(strrelawithapplicantremark);
			tvverify.setRelawithapplicantflag(strrelawithapplicantflag);

			tvverify.setPaymentbyrider(strpaymentbyrider);
			tvverify.setPaymentbyriderremarks(strpaymentbyriderremark);
			tvverify.setPaymentbyriderflag(strpaymentbyriderflag);
			
			
//			tvverify.setBikeapplied(strbikeapplied);
//			tvverify.setBikeappliedremarks(strbikeappliedremarks);
//			tvverify.setBikeappliedflag(strbikeappliedflag);
			
			tvverify.setArrangebtwnrider(strarrangebtwnrider);
			tvverify.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
			tvverify.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
			
			tvverify.setResiadrss(strresiadrss);
			tvverify.setResiadrssremarks(strresiadrssRemarks);
			tvverify.setResiadrssflag(strresiadrsschkbx);
			
			tvverify.setNoofyrinaddrss(strnoofyrsinaddrs);
			tvverify.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
			tvverify.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);
					
			tvverify.setMbnonotinname(strmbnumnotinname);
			tvverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			tvverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

//			tvverify.setFirstguarantor(false);
//			tvverify.setSecondguarantor(false);
			tvverify.setTvremarks(strRemarks);
			tvverify.setTvverdict(blnTvverdict);
			
			

			try {
				teleVerifyRepo.save(tvverify);
				response.setData("TV Verification for : " + strCustid + " (Rider Details) saved successfully.");

			} catch (Exception e) {
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {

					Optional<TvVerification> optv = teleVerifyRepo.findBynationalid(strCustid);

					if (optv.isPresent()) {
						TvVerification tv = optv.get();
					
						tv.setFirstnameremark(strfnameremark);
						tv.setFirstnameflag(strfnameflag);
						
						tv.setMaritalstatusremark(strmsremark);
						tv.setMaritalstatusflag(strmsflag);
						
						tv.setMobilenoremark(strmobnoremark);
						tv.setMobilenoflag(strmobnoflag);
						tv.setStageremark(strstgnmremark);
						tv.setStageflag(strstgnmflag);
						tv.setStagechairmannameremarks(strstgchmnnameremark);
						tv.setStagechairmannameflag(strstgchmnameflag);
						tv.setStagechairmannoremarks(strstgchmnnoremarks);
						tv.setStagechairmannoflag(strstgchmnnoflag);
						
						tv.setLcremark(strlcnmremark);
						tv.setLcflag(strlcnmflag);
						
						tv.setNationalidremark(strnidremark);
						tv.setNationalidflag(strnidflag);
						tv.setBikeregnoremark(strbregnoremark);
						tv.setBikeregnoflag(strbregnoflag);
						tv.setBikeuseremark(strbuseremark);
						tv.setBikeuseflag(strbuseflag);
						tv.setDobremark(strdobremark);
						tv.setDobflag(strdobflag);
						
						tv.setOwnhouserentedremark(strorremark);
						tv.setOwnhouserentedflag(strorflag);
						tv.setLandlordnameremark(strllremark);
						tv.setLandlordnameflag(strllflag);
						tv.setLandlordmobilenoremark(strllmobremark);
						tv.setLandlordmobilenoflag(strllmobflag);
						tv.setRentpmremark(strrpmremark);
						tv.setRentpmflag(strrpmflag);
						tv.setOtherincomesourceremark(stroisremark);
						tv.setOtherincomesourceflag(stroisflag);
						
						tv.setPermanentaddressremark(strpaddremark);
						tv.setPermanentaddressflag(strpaddflag);
						
						tv.setNearbypolicestationremark(strnpsremark);
						tv.setNearbypolicestationflag(strnpsflag);
						tv.setLcmobilenoremark(strlcnmobnoremark);
						tv.setLcmobilenoflag(strlcnmobnoflag);
						
						tv.setTvremarks(strRemarks);
						tv.setTvverdict(blnTvverdict);

						tv.setLlrentfeedbackremarks(strllrentfeedbackremark);
						tv.setLlrentfeedbackflag(strllrentfeedbackflag);
						tv.setNoyrsinarearemarks(strnoyrsinarearemark);
						tv.setNoyrsinareaflag(strnoyrsinareaflag);
						tv.setLc1chmnrecfeedremarks(strlc1chmnrecfeedremark);
						tv.setLc1chmnrecfeedflag(strlc1chmnrecfeedflag);
						tv.setNearlmarkresiremarks(strnearlmarkresiremark);
						tv.setNearlmarkresiflag(strnearlmarkresiflag);
						tv.setEmptype(stremptypeflag);
						tv.setEmptypeflag(stremptypeflag);
						tv.setStgorwrkadrssnearlmarkremarks(strstgorwrkadrssnearlmarkremark);
						tv.setStgorwrkadrssnearlmarkflag(strstgorwrkadrssnearlmarkflag);
						tv.setStgoremprecmremarks(strstgoremprecmremark);
						tv.setStgoremprecmflag(strstgoremprecmflag);
						tv.setNoofyrsinstgorbusiremarks(strnoofyrsinstgorbusiremark);
						tv.setNoofyrsinstgorbuisflag(strnoofyrsinstgorbuisflag);
						tv.setStgnoofvehiremarks(strstgnoofvehiremark);
						tv.setStgnoofvehiflag(strstgnoofvehiflag);
						tv.setBikeownerremark(strbikeownerremark);
						tv.setBikeownerflag(strbikeownerflag);
						tv.setNetincomeremarks(strnetincomeremark);
						tv.setNetincomeflag(strnetincomeflag);
						tv.setBikeusearearemarks(strbikeusearearemark);
						tv.setBikeuseareaflag(strbikeuseareaflag);
						tv.setSpousenameremarks(strspousenameremark);
						tv.setSpousenameflag(strspousenameflag);
						tv.setSpousenoremarks(strspousenoremark);
						tv.setSpousenoflag(strspousenoflag);
						tv.setSpouseconfirmremarks(strspouseconfirmremark);
						tv.setSpouseconfirmflag(strspouseconfirmflag);
					
						tv.setRelawithapplicantremarks(strrelawithapplicantremark);
						tv.setRelawithapplicantflag(strrelawithapplicantflag);
						tv.setPaymentbyriderremarks(strpaymentbyriderremark);
						tv.setPaymentbyriderflag(strpaymentbyriderflag);
						
						tv.setArrangebtwnriderremarks(strarrangebtwnriderRemarks);
						tv.setArrangebtwnriderflag(strarrangebtwnriderchkbx);
						tv.setResiadrssremarks(strresiadrssRemarks);
						tv.setResiadrssflag(strresiadrsschkbx);
						tv.setNoofyrinaddrssremarks(strnoofyrsinaddrsRemarks);
						tv.setNoofyrinaddrssflag(strnoofyrsinaddrschkbx);		
						tv.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						tv.setMbnonotinnameflag(strmbnumnotinnamechkbx);

						teleVerifyRepo.save(tv);
						response.setData("TV Verification: " + strCustid + " (Rider Details) updated successfully");
					}
				} else if (strMsg.contains("")) {
					strMsg = "General error";
				}
			}
		}
		return response;
	}

	public Response<String> addGuaranTv(TeleVerificationDto teleVerificationDto) {

		Response<String> response = new Response<>();

		String strId, strId2, strRemarks, strTvverdict;

		String strMsg = "";
		String strQuery = "";
		String strPosverdict = "Recommended";
		String strNegverdict = "Not Recommended";
		boolean blnTvverdict = false;

		String strTVRequestdatetime = "";
		String strLoginuser = "";

		int intUser = 0;

		strId = teleVerificationDto.getGtvid();
		strId2 = teleVerificationDto.getGtvid2();

		strTvverdict = teleVerificationDto.getStrtvverdict();
		strRemarks = teleVerificationDto.getTvremarks();

		String strSurname = teleVerificationDto.getSurname();
		String strSurnameremark = teleVerificationDto.getSurnameremark();
		String strSurnamechkbx = teleVerificationDto.getSurnameflag();
		String strFname = teleVerificationDto.getFirstname();
		String strfnameremark = teleVerificationDto.getFirstnameremark();
		String strfnamechkbx = teleVerificationDto.getFirstnameflag();
		String stroname = teleVerificationDto.getOthername();
		String stronameremark = teleVerificationDto.getOthernameremark();
		String stronamechkbx = teleVerificationDto.getOthernameflag();
		String strnid = teleVerificationDto.getNationalid();
		String strnidremark = teleVerificationDto.getNationalidremark();
		String strnidchkbx = teleVerificationDto.getNationalidflag();
		String strmobno = teleVerificationDto.getMobileno();
		String strmobnoremark = teleVerificationDto.getMobilenoremark();
		String strmobnochkbx = teleVerificationDto.getMobilenoflag();
		String stradd = teleVerificationDto.getAddress();
		String straddremark = teleVerificationDto.getAddressremark();
		String straddchkbx = teleVerificationDto.getAddressflag();
		String strpadd = teleVerificationDto.getPermanentaddress();
		String strpaddremark = teleVerificationDto.getPermanentaddressremark();
		String strpaddchkbx = teleVerificationDto.getPermanentaddressflag();
		String stryradd = teleVerificationDto.getYearsinaddress();
		String stryraddremark = teleVerificationDto.getYearsinaddressremark();
		String stryraddchkbx = teleVerificationDto.getYearsinaddressflag();
		String stror = teleVerificationDto.getOwnhouserented();
		String strorremark = teleVerificationDto.getOwnhouserentedremark();
		String strorchkbx = teleVerificationDto.getOwnhouserentedflag();
		String strrpm = teleVerificationDto.getRentpm();
		String strrpmremark = teleVerificationDto.getRentpmremark();
		String strrpmchkbx = teleVerificationDto.getRentpmflag();
		String strnok = teleVerificationDto.getNextofkin();
		String strnokremark = teleVerificationDto.getNextofkinremark();
		String strnokchkbx = teleVerificationDto.getNextofkinflag();
		String strnokm = teleVerificationDto.getNokmobileno();
		String strnokmremark = teleVerificationDto.getNokmobilenoremark();
		String strnokmchkbx = teleVerificationDto.getNokmobilenoflag();
		String strnokr = teleVerificationDto.getNokrelationship();
		String strnokrremark = teleVerificationDto.getNokrelationshipremark();
		String strnokrchkbx = teleVerificationDto.getNokrelationshipflag();
		String strnoka = teleVerificationDto.getNokagreeing();
		String strnokaremark = teleVerificationDto.getNokagreeingremark();
		String strnokachkbx = teleVerificationDto.getNokagreeingflag();
		String strbregno = teleVerificationDto.getBikeregno();
		String strbregnoremark = teleVerificationDto.getBikeregnoremark();
		String strbregnochkbx = teleVerificationDto.getBikeregnoflag();
		String strbowner = teleVerificationDto.getBikeowner();
		String strbownerremark = teleVerificationDto.getBikeownerremark();
		String strbownerchkbx = teleVerificationDto.getBikeownerflag();
		String strsal = teleVerificationDto.getSalaried();
		String strsalremark = teleVerificationDto.getSalariedremark();
		String strsalchkbx = teleVerificationDto.getSalariedflag();
		String strmi = teleVerificationDto.getMonthlyincome();
		String strmiremark = teleVerificationDto.getMonthlyincomeremark();
		String strmichkbx = teleVerificationDto.getMonthlyincomeflag();
		String strois = teleVerificationDto.getOtherincomesource();
		String stroisremark = teleVerificationDto.getOtherincomesourceremark();
		String stroischkbx = teleVerificationDto.getOtherincomesourceflag();
		String stroi = teleVerificationDto.getOtherincome();
		String stroiremark = teleVerificationDto.getOtherincomeremark();
		String stroichkbx = teleVerificationDto.getOtherincomeflag();
		String strstgnm = teleVerificationDto.getStage();
		String strstgnmremark = teleVerificationDto.getStageremark();
		String strstgnmchkbx = teleVerificationDto.getStageflag();
		String strstgadd = teleVerificationDto.getStageaddress();
		String strstgaddremark = teleVerificationDto.getStageaddressremark();
		String strstgaddchkbx = teleVerificationDto.getStageaddressflag();
		String strstgcc = teleVerificationDto.getStagechairconfirmation();
		String strstgccremark = teleVerificationDto.getStagechairconfirmationremark();
		String strstgccchkbx = teleVerificationDto.getStagechairconfirmationflag();
		String strlcnm = teleVerificationDto.getLc();
		String strlcnmremark = teleVerificationDto.getLcremark();
		String strlcnmchkbx = teleVerificationDto.getLcflag();
		String strrltnshp = teleVerificationDto.getRelationship();
		String strrltnshpremark = teleVerificationDto.getRelationshipremark();
		String strrltnshpchkbx = teleVerificationDto.getRelationshipflag();
		
		String strDob = teleVerificationDto.getDob();
		String strDobRemarks = teleVerificationDto.getDobremark();
		String strDobchkbx = teleVerificationDto.getDobflag();

		String strLlname = teleVerificationDto.getLandlordname();
		String strLlnameRemarks = teleVerificationDto.getLandlordnameremark();
		String strLlnamechkbx = teleVerificationDto.getLandlordnameflag();

		String strLlmbno = teleVerificationDto.getLandlordmobileno();
		String strLlmbnoRemarks = teleVerificationDto.getLandlordmobilenoremark();
		String strLlmbnochkbx = teleVerificationDto.getLandlordmobilenoflag();

		String strResiadrss = teleVerificationDto.getResiadrss();
		String strResiadrssRemarks = teleVerificationDto.getResiadrssremarks();
		String strResiadrsschkbx = teleVerificationDto.getResiadrssflag();

		String strMaritalsts = teleVerificationDto.getMaritalstatus();
		String strMaritalstsRemarks = teleVerificationDto.getMaritalstatusremark();
		String strMaritalstschkbx = teleVerificationDto.getMaritalstatusflag();

		String strLLFeedAbtRnt = teleVerificationDto.getLlrentfeedback();
		String strLLFeedAbtRntRemarks = teleVerificationDto.getLlrentfeedbackremarks();
		String strLLFeedAbtRntchkbx = teleVerificationDto.getLlrentfeedbackflag();

		String strNoOfYrsInArea = teleVerificationDto.getNoyrsinarea();
		String strNoOfYrsInAreaRemarks = teleVerificationDto.getNoyrsinarearemarks();
		String strNoOfYrsInAreachkbx = teleVerificationDto.getNoyrsinareaflag();

		String strLc1chmnRecFeedbk = teleVerificationDto.getLc1chmnrecfeed();
		String strLc1chmnRecFeedbkRemarks = teleVerificationDto.getLc1chmnrecfeedremarks();
		String strLc1chmnRecFeedbkchkbx = teleVerificationDto.getLc1chmnrecfeedflag();

		String strNearLndmrkorResi = teleVerificationDto.getNearlmarkresi();
		String strNearLndmrkorResiRemarks = teleVerificationDto.getNearlmarkresiremarks();
		String strNearLndmrkorResichkbx = teleVerificationDto.getNearlmarkresiflag();

		String strEmpType = teleVerificationDto.getEmptype();
		String strEmpTypeRemarks = teleVerificationDto.getEmptyperemarks();
		String strEmpTypechkbx = teleVerificationDto.getEmptypeflag();

		String strStgOrWrkadrssWithNearLNMRK = teleVerificationDto.getStgorwrkadrssnearlmark();
		String strStgOrWrkadrssWithNearLNMRKRemarks = teleVerificationDto.getStgorwrkadrssnearlmarkremarks();
		String strStgOrWrkadrssWithNearLNMRKchkbx = teleVerificationDto.getStgorwrkadrssnearlmarkflag();

		String strNoOfYrsInstgorBusi = teleVerificationDto.getNoofyrsinstgorbusi();
		String strNoOfYrsInstgorBusiRemarks = teleVerificationDto.getNoofyrsinstgorbusiremarks();
		String strNoOfYrsInstgorBusichkbx = teleVerificationDto.getNoofyrsinstgorbuisflag();

		String strSpouseName = teleVerificationDto.getSpousename();
		String strSpouseNameRemarks = teleVerificationDto.getSpousenameremarks();
		String strSpouseNamechkbx = teleVerificationDto.getSpousenameflag();

		String strSpouseMbno = teleVerificationDto.getSpouseno();
		String strSpouseMbnoRemarks = teleVerificationDto.getSpousenoremarks();
		String strSpouseMbnochkbx = teleVerificationDto.getSpousenoflag();

		String strYakaNum = teleVerificationDto.getYakanum();
		String strYakaNumRemarks = teleVerificationDto.getYakanumremarks();
		String strYakaNumchkbx = teleVerificationDto.getYakanumflag();

		String strYakaNoName = teleVerificationDto.getYakanumname();
		String strYakaNoNameRemarks = teleVerificationDto.getYakanumnameremarks();
		String strYakaNoNamechkbx = teleVerificationDto.getYakanumnamflag();

		String strlc1number = teleVerificationDto.getLcmobileno();
		String strlc1numberRemarks = teleVerificationDto.getLcmobilenoremark();
		String strlc1numberchkbx = teleVerificationDto.getLcmobilenoflag();

		String strstgorempno = teleVerificationDto.getStagechairmanno();
		String strstgorempnoRemarks = teleVerificationDto.getStagechairmannoremarks();
		String strstgorempnochkbx = teleVerificationDto.getStagechairmannoflag();
		
		String strmbnumnotinname = teleVerificationDto.getMbnonotinname();
		String strmbnumnotinnameRemarks = teleVerificationDto.getMbnonotinnameremarks();
		String strmbnumnotinnamechkbx = teleVerificationDto.getMbnonotinnameflag();
		
		

		String strSurname2 = teleVerificationDto.getSurname2();
		String strSurname2remark = teleVerificationDto.getSurnameremark2();
		String strSurname2chkbx = teleVerificationDto.getSurnameflag2();
		String strFname2 = teleVerificationDto.getFirstname2();
		String strfname2remark = teleVerificationDto.getFirstnameremark2();
		String strfname2chkbx = teleVerificationDto.getFirstnameflag2();
		String stroname2 = teleVerificationDto.getOthername2();
		String stroname2remark = teleVerificationDto.getOthernameremark2();
		String stroname2chkbx = teleVerificationDto.getOthernameflag2();
		String strnid2 = teleVerificationDto.getNationalid2();
		String strnid2remark = teleVerificationDto.getNationalidremark2();
		String strnid2chkbx = teleVerificationDto.getNationalidflag2();
		String strmobno2 = teleVerificationDto.getMobileno2();
		String strmobno2remark = teleVerificationDto.getMobilenoremark2();
		String strmobno2chkbx = teleVerificationDto.getMobilenoflag2();
		String stradd2 = teleVerificationDto.getAddress2();
		String stradd2remark = teleVerificationDto.getAddressremark2();
		String stradd2chkbx = teleVerificationDto.getAddressflag2();
		String strpadd2 = teleVerificationDto.getPermanentaddress2();
		String strpadd2remark = teleVerificationDto.getPermanentaddressremark2();
		String strpadd2chkbx = teleVerificationDto.getPermanentaddressflag2();
		String stryradd2 = teleVerificationDto.getYearsinaddress2();
		String stryradd2remark = teleVerificationDto.getYearsinaddressremark2();
		String stryradd2chkbx = teleVerificationDto.getYearsinaddressflag2();
		String stror2 = teleVerificationDto.getOwnhouserented2();
		String stror2remark = teleVerificationDto.getOwnhouserentedremark2();
		String stror2chkbx = teleVerificationDto.getOwnhouserentedflag2();
		String strrpm2 = teleVerificationDto.getRentpm2();
		String strrpm2remark = teleVerificationDto.getRentpmremark2();
		String strrpm2chkbx = teleVerificationDto.getRentpmflag2();
		String strnok2 = teleVerificationDto.getNextofkin2();
		String strnok2remark = teleVerificationDto.getNextofkinremark2();
		String strnok2chkbx = teleVerificationDto.getNextofkinflag2();
		String strnokm2 = teleVerificationDto.getNokmobileno2();
		String strnokm2remark = teleVerificationDto.getNokmobilenoremark2();
		String strnokm2chkbx = teleVerificationDto.getNokmobilenoflag2();
		String strnokr2 = teleVerificationDto.getNokrelationship2();
		String strnokr2remark = teleVerificationDto.getNokrelationshipremark2();
		String strnokr2chkbx = teleVerificationDto.getNokrelationshipflag2();
		String strnoka2 = teleVerificationDto.getNokagreeing2();
		String strnoka2remark = teleVerificationDto.getNokagreeingremark2();
		String strnoka2chkbx = teleVerificationDto.getNokagreeingflag2();
		String strbregno2 = teleVerificationDto.getBikeregno2();
		String strbregno2remark = teleVerificationDto.getBikeregnoremark2();
		String strbregno2chkbx = teleVerificationDto.getBikeregnoflag2();
		String strbowner2 = teleVerificationDto.getBikeowner2();
		String strbowner2remark = teleVerificationDto.getBikeownerremark2();
		String strbowner2chkbx = teleVerificationDto.getBikeownerflag2();
		String strsal2 = teleVerificationDto.getSalaried2();
		String strsal2remark = teleVerificationDto.getSalariedremark2();
		String strsal2chkbx = teleVerificationDto.getSalariedflag2();
		String strmi2 = teleVerificationDto.getMonthlyincome2();
		String strmi2remark = teleVerificationDto.getMonthlyincomeremark2();
		String strmi2chkbx = teleVerificationDto.getMonthlyincomeflag2();
		String strois2 = teleVerificationDto.getOtherincomesource2();
		String strois2remark = teleVerificationDto.getOtherincomesourceremark2();
		String strois2chkbx = teleVerificationDto.getOtherincomesourceflag2();
		String stroi2 = teleVerificationDto.getOtherincome2();
		String stroi2remark = teleVerificationDto.getOtherincomeremark2();
		String stroi2chkbx = teleVerificationDto.getOtherincomeflag2();
		String strstgnm2 = teleVerificationDto.getStage2();
		String strstgnm2remark = teleVerificationDto.getStageremark2();
		String strstgnm2chkbx = teleVerificationDto.getStageflag2();
		String strstgadd2 = teleVerificationDto.getStageaddress2();
		String strstgadd2remark = teleVerificationDto.getStageaddressremark2();
		String strstgadd2chkbx = teleVerificationDto.getStageaddressflag2();
		String strstgcc2 = teleVerificationDto.getStagechairconfirmation2();
		String strstgcc2remark = teleVerificationDto.getStagechairconfirmationremark2();
		String strstgcc2chkbx = teleVerificationDto.getStagechairconfirmationflag2();
		String strlcnm2 = teleVerificationDto.getLc2();
		String strlcnm2remark = teleVerificationDto.getLcremark2();
		String strlcnm2chkbx = teleVerificationDto.getLcflag2();
		String strrltnshp2 = teleVerificationDto.getRelationship2();
		String strrltnshp2remark = teleVerificationDto.getRelationshipremark2();
		String strrltnshp2chkbx = teleVerificationDto.getRelationshipflag2();

		String strDob2 = teleVerificationDto.getDob2();
		String strDobRemarks2 = teleVerificationDto.getDobremark2();
		String strDobchkbx2 = teleVerificationDto.getDobflag2();

		String strLlname2 = teleVerificationDto.getLandlordname2();
		String strLlnameRemarks2 = teleVerificationDto.getLandlordnameremark2();
		String strLlnamechkbx2 = teleVerificationDto.getLandlordnameflag2();

		String strLlmbno2 = teleVerificationDto.getLandlordmobileno2();
		String strLlmbnoRemarks2 = teleVerificationDto.getLandlordmobilenoremark2();
		String strLlmbnochkbx2 = teleVerificationDto.getLandlordmobilenoflag2();

		String strResiadrss2 = teleVerificationDto.getResiadrss2();
		String strResiadrssRemarks2 = teleVerificationDto.getResiadrssremarks2();
		String strResiadrsschkbx2 = teleVerificationDto.getResiadrssflag2();

		String strMaritalsts2 = teleVerificationDto.getMaritalstatus2();
		String strMaritalstsRemarks2 = teleVerificationDto.getMaritalstatusremark2();
		String strMaritalstschkbx2 = teleVerificationDto.getMaritalstatusflag2();

		String strLLFeedAbtRnt2 = teleVerificationDto.getLlrentfeedback2();
		String strLLFeedAbtRntRemarks2 = teleVerificationDto.getLlrentfeedbackremarks2();
		String strLLFeedAbtRntchkbx2 = teleVerificationDto.getLlrentfeedbackflag2();

		String strNoOfYrsInArea2 = teleVerificationDto.getNoyrsinarea2();
		String strNoOfYrsInAreaRemarks2 = teleVerificationDto.getNoyrsinarearemarks2();
		String strNoOfYrsInAreachkbx2 = teleVerificationDto.getNoyrsinareaflag2();

		String strLc1chmnRecFeedbk2 = teleVerificationDto.getLc1chmnrecfeed2();
		String strLc1chmnRecFeedbkRemarks2 = teleVerificationDto.getLc1chmnrecfeedremarks2();
		String strLc1chmnRecFeedbkchkbx2 = teleVerificationDto.getLc1chmnrecfeedflag2();

		String strNearLndmrkorResi2 = teleVerificationDto.getNearlmarkresi2();
		String strNearLndmrkorResiRemarks2 = teleVerificationDto.getNearlmarkresiremarks2();
		String strNearLndmrkorResichkbx2 = teleVerificationDto.getNearlmarkresiflag2();

		String strEmpType2 = teleVerificationDto.getEmptype2();
		String strEmpTypeRemarks2 = teleVerificationDto.getEmptyperemarks2();
		String strEmpTypechkbx2 = teleVerificationDto.getEmptypeflag2();

		String strStgOrWrkadrssWithNearLNMRK2 = teleVerificationDto.getStgorwrkadrssnearlmark2();
		String strStgOrWrkadrssWithNearLNMRKRemarks2 = teleVerificationDto.getStgorwrkadrssnearlmarkremarks2();
		String strStgOrWrkadrssWithNearLNMRKchkbx2 = teleVerificationDto.getStgorwrkadrssnearlmarkflag2();

		String strNoOfYrsInstgorBusi2 = teleVerificationDto.getNoofyrsinstgorbusi2();
		String strNoOfYrsInstgorBusiRemarks2 = teleVerificationDto.getNoofyrsinstgorbusiremarks2();
		String strNoOfYrsInstgorBusichkbx2 = teleVerificationDto.getNoofyrsinstgorbuisflag2();

		String strSpouseName2 = teleVerificationDto.getSpousename2();
		String strSpouseNameRemarks2 = teleVerificationDto.getSpousenameremarks2();
		String strSpouseNamechkbx2 = teleVerificationDto.getSpousenameflag2();

		String strSpouseMbno2 = teleVerificationDto.getSpouseno2();
		String strSpouseMbnoRemarks2 = teleVerificationDto.getSpousenoremarks2();
		String strSpouseMbnochkbx2 = teleVerificationDto.getSpousenoflag2();

		String strYakaNum2 = teleVerificationDto.getYakanum2();
		String strYakaNumRemarks2 = teleVerificationDto.getYakanumremarks2();
		String strYakaNumchkbx2 = teleVerificationDto.getYakanumflag2();

		String strYakaNoName2 = teleVerificationDto.getYakanumname2();
		String strYakaNoNameRemarks2 = teleVerificationDto.getYakanumnameremarks2();
		String strYakaNoNamechkbx2 = teleVerificationDto.getYakanumnamflag2();

		String strlc1number2 = teleVerificationDto.getLcmobileno2();
		String strlc1numberRemarks2 = teleVerificationDto.getLcmobilenoremark2();
		String strlc1numberchkbx2 = teleVerificationDto.getLcmobilenoflag2();

		String strstgorempno2 = teleVerificationDto.getStagechairmanno2();
		String strstgorempnoRemarks2 = teleVerificationDto.getStagechairmannoremarks2();
		String strstgorempnochkbx2 = teleVerificationDto.getStagechairmannoflag2();
		
		String strmbnumnotinname2 = teleVerificationDto.getMbnonotinname2();
		String strmbnumnotinnameRemarks2 = teleVerificationDto.getMbnonotinnameremarks2();
		String strmbnumnotinnamechkbx2 = teleVerificationDto.getMbnonotinnameflag2();

		
//		strLoginuser =  etParameter("username");

		if (strId == null || strId.length() == 0) {

			response.setData("Please select customer id");

		} else {

			if (strTvverdict.equals(strPosverdict)) {
				blnTvverdict = true;
			} else if (strTvverdict.equals(strNegverdict)) {
				blnTvverdict = false;
			}

			TimeStampUtil gts = new TimeStampUtil();
			strTVRequestdatetime = gts.TimeStamp();

			TvVerification tvverify = new TvVerification();
			tvverify.setTvid(strId);
			tvverify.setSurname(strSurname);
			tvverify.setSurnameremark(strSurnameremark);
			tvverify.setSurnameflag(strSurnamechkbx);
			tvverify.setFirstname(strFname);
			tvverify.setFirstnameremark(strfnameremark);
			tvverify.setFirstnameflag(strfnamechkbx);
			tvverify.setOthername(stroname);
			tvverify.setOthernameremark(stronameremark);
			tvverify.setOthernameflag(stronamechkbx);
			tvverify.setNationalid(strnid);
			tvverify.setNationalidremark(strnidremark);
			tvverify.setNationalidflag(strnidchkbx);
			tvverify.setMobileno(strmobno);
			tvverify.setMobilenoremark(strmobnoremark);
			tvverify.setMobilenoflag(strmobnochkbx);
			tvverify.setAddress(stradd);
			tvverify.setAddressremark(straddremark);
			tvverify.setAddressflag(straddchkbx);
			tvverify.setPermanentaddress(strpadd);
			tvverify.setPermanentaddressremark(strpaddremark);
			tvverify.setPermanentaddressflag(strpaddchkbx);
			tvverify.setYearsinaddress(stryradd);
			tvverify.setYearsinaddressremark(stryraddremark);
			tvverify.setYearsinaddressflag(stryraddchkbx);
			tvverify.setOwnhouserented(stror);
			tvverify.setOwnhouserentedremark(strorremark);
			tvverify.setOwnhouserentedflag(strorchkbx);
			tvverify.setRentpm(strrpm);
			tvverify.setRentpmremark(strrpmremark);
			tvverify.setRentpmflag(strrpmchkbx);
			tvverify.setNextofkin(strnok);
			tvverify.setNextofkinremark(strnokremark);
			tvverify.setNextofkinflag(strnokchkbx);
			tvverify.setNokmobileno(strnokm);
			tvverify.setNokmobilenoremark(strnokmremark);
			tvverify.setNokmobilenoflag(strnokmchkbx);
			tvverify.setNokrelationship(strnokr);
			tvverify.setNokrelationshipremark(strnokrremark);
			tvverify.setNokrelationshipflag(strnokrchkbx);
			tvverify.setNokagreeing(strnoka);
			tvverify.setNokagreeingremark(strnokaremark);
			tvverify.setNokagreeingflag(strnokachkbx);
			tvverify.setBikeregno(strbregno);
			tvverify.setBikeregnoremark(strbregnoremark);
			tvverify.setBikeregnoflag(strbregnochkbx);
			tvverify.setBikeowner(strbowner);
			tvverify.setBikeownerremark(strbownerremark);
			tvverify.setBikeownerflag(strbownerchkbx);
			tvverify.setSalaried(strsal);
			tvverify.setSalariedremark(strsalremark);
			tvverify.setSalariedflag(strsalchkbx);
			tvverify.setMonthlyincome(strmi);
			tvverify.setMonthlyincomeremark(strmiremark);
			tvverify.setMonthlyincomeflag(strmichkbx);
			tvverify.setOtherincomesource(strois);
			tvverify.setOtherincomesourceremark(stroisremark);
			tvverify.setOtherincomesourceflag(stroischkbx);
			tvverify.setOtherincome(stroi);
			tvverify.setOtherincomeremark(stroiremark);
			tvverify.setOtherincomeflag(stroichkbx);
			tvverify.setStage(strstgnm);
			tvverify.setStageremark(strstgnmremark);
			tvverify.setStageflag(strstgnmchkbx);
			tvverify.setStageaddress(strstgadd);
			tvverify.setStageaddressremark(strstgaddremark);
			tvverify.setStageaddressflag(strstgaddchkbx);
			tvverify.setStagechairconfirmation(strstgcc);
			tvverify.setStagechairconfirmationremark(strstgccremark);
			tvverify.setStagechairconfirmationflag(strstgccchkbx);
			tvverify.setLc(strlcnm);
			tvverify.setLcremark(strlcnmremark);
			tvverify.setLcflag(strlcnmchkbx);
			tvverify.setRelationship(strrltnshp);
			tvverify.setRelationshipremark(strrltnshpremark);
			tvverify.setRelationshipflag(strrltnshpchkbx);
			
			tvverify.setDob(strDob);
			tvverify.setDobremark(strDobRemarks);
			tvverify.setDobflag(strDobchkbx);

			tvverify.setLandlordname(strLlname);
			tvverify.setLandlordnameremark(strLlnameRemarks);
			tvverify.setLandlordnameflag(strLlnamechkbx);

			tvverify.setLandlordmobileno(strLlmbno);
			tvverify.setLandlordmobilenoremark(strLlmbnoRemarks);
			tvverify.setLandlordmobilenoflag(strLlmbnochkbx);

			tvverify.setResiadrss(strResiadrss);
			tvverify.setResiadrssremarks(strResiadrssRemarks);
			tvverify.setResiadrssflag(strResiadrsschkbx);

			tvverify.setMaritalstatus(strMaritalsts);
			tvverify.setMaritalstatusremark(strMaritalstsRemarks);
			tvverify.setMaritalstatusflag(strMaritalstschkbx);

			tvverify.setLlrentfeedback(strLLFeedAbtRnt);
			tvverify.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks);
			tvverify.setLlrentfeedbackflag(strLLFeedAbtRntchkbx);

			tvverify.setNoyrsinarea(strNoOfYrsInArea);
			tvverify.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks);
			tvverify.setNoyrsinareaflag(strNoOfYrsInAreachkbx);

			tvverify.setLc1chmnrecfeed(strLc1chmnRecFeedbk);
			tvverify.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks);
			tvverify.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx);

			tvverify.setNearlmarkresi(strNearLndmrkorResi);
			tvverify.setNearlmarkresiremarks(strNearLndmrkorResiRemarks);
			tvverify.setNearlmarkresiflag(strNearLndmrkorResichkbx);

			tvverify.setEmptype(strEmpType);
			tvverify.setEmptyperemarks(strEmpTypeRemarks);
			tvverify.setEmptypeflag(strEmpTypechkbx);

			tvverify.setStgorwrkadrssnearlmark(strStgOrWrkadrssWithNearLNMRK);
			tvverify.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks);
			tvverify.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx);

			tvverify.setNoofyrsinstgorbusi(strNoOfYrsInstgorBusi);
			tvverify.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks);
			tvverify.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx);

			tvverify.setSpousename(strSpouseName);
			tvverify.setSpousenameremarks(strSpouseNameRemarks);
			tvverify.setSpousenameflag(strSpouseNamechkbx);

			tvverify.setSpouseno(strSpouseMbno);
			tvverify.setSpousenoremarks(strSpouseMbnoRemarks);
			tvverify.setSpousenoflag(strSpouseMbnochkbx);

			tvverify.setYakanum(strYakaNum);
			tvverify.setYakanumremarks(strYakaNumRemarks);
			tvverify.setYakanumflag(strYakaNumchkbx);

			tvverify.setYakanumname(strYakaNoName);
			tvverify.setYakanumnameremarks(strYakaNoNameRemarks);
			tvverify.setYakanumnamflag(strYakaNoNamechkbx);

			tvverify.setLcmobileno(strlc1number);
			tvverify.setLcmobilenoremark(strlc1numberRemarks);
			tvverify.setLcmobilenoflag(strlc1numberchkbx);

			tvverify.setStagechairmanno(strstgorempno);
			tvverify.setStagechairmannoremarks(strstgorempnoRemarks);
			tvverify.setStagechairmannoflag(strstgorempnochkbx);
			
			tvverify.setMbnonotinname(strmbnumnotinname);
			tvverify.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
			tvverify.setMbnonotinnameflag(strmbnumnotinnamechkbx);

			
			tvverify.setFirstguarantor(true);
			tvverify.setSecondguarantor(false);
			tvverify.setTvremarks(strRemarks);
			tvverify.setTvverdict(blnTvverdict);

			try {
				teleVerifyRepo.save(tvverify);
				response.setData("TV Verification for : " + strId + " (Guarantor) saved successfully.");

			} catch (Exception e) {
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {

					Optional<TvVerification> optv = teleVerifyRepo.findById(strId);

					if (optv.isPresent()) {
						TvVerification tv = optv.get();
						tv.setSurnameremark(strSurnameremark);
						tv.setSurnameflag(strSurnamechkbx);
						tv.setFirstnameremark(strfnameremark);
						tv.setFirstnameflag(strfnamechkbx);
						tv.setOthernameremark(stronameremark);
						tv.setOthernameflag(stronamechkbx);
						tv.setMaritalstatusremark(strmiremark);
						tv.setMobilenoremark(strmobnoremark);
						tv.setMobilenoflag(strmobnochkbx);
						tv.setStageremark(strstgnmremark);
						tv.setStageflag(strstgnmchkbx);
						tv.setLcremark(strlcnmremark);
						tv.setLcflag(strlcnmchkbx);
						tv.setNationalidremark(strnidremark);
						tv.setNationalidflag(strnidchkbx);
						tv.setBikeregnoremark(strbregnoremark);
						tv.setBikeregnoflag(strbregnochkbx);
						tv.setNextofkinremark(strnokremark);
						tv.setNextofkinflag(strnokchkbx);
						tv.setNokmobilenoremark(strnokmremark);
						tv.setNokmobilenoflag(strnokmchkbx);
						tv.setNokrelationshipremark(strnokrremark);
						tv.setNokrelationshipflag(strnokrchkbx);
						tv.setNokagreeingremark(strnokaremark);
						tv.setNokagreeingflag(strnokachkbx);
						tv.setOwnhouserentedremark(strorremark);
						tv.setOwnhouserentedflag(strorchkbx);
						tv.setRentpmremark(strrpmremark);
						tv.setRentpmflag(strrpmchkbx);
						tv.setOtherincomesourceremark(stroisremark);
						tv.setOtherincomesourceflag(stroischkbx);
						tv.setPermanentaddressremark(strpaddremark);
						tv.setPermanentaddressflag(strpaddchkbx);
						System.out.println(strRemarks);
						System.out.println(blnTvverdict);
						
						tv.setDobremark(strDobRemarks);
						tv.setDobflag(strDobchkbx);

						tv.setLandlordnameremark(strLlnameRemarks);
						tv.setLandlordnameflag(strLlnamechkbx);

						tv.setLandlordmobilenoremark(strLlmbnoRemarks);
						tv.setLandlordmobilenoflag(strLlmbnochkbx);

						tv.setResiadrssremarks(strResiadrssRemarks);
						tv.setResiadrssflag(strResiadrsschkbx);

						tv.setMaritalstatusremark(strMaritalstsRemarks);
						tv.setMaritalstatusflag(strMaritalstschkbx);

						tv.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks);
						tv.setLlrentfeedbackflag(strLLFeedAbtRntchkbx);

						tv.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks);
						tv.setNoyrsinareaflag(strNoOfYrsInAreachkbx);

						tv.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks);
						tv.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx);

						tv.setNearlmarkresiremarks(strNearLndmrkorResiRemarks);
						tv.setNearlmarkresiflag(strNearLndmrkorResichkbx);

						tv.setEmptyperemarks(strEmpTypeRemarks);
						tv.setEmptypeflag(strEmpTypechkbx);

						tv.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks);
						tv.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx);

						tv.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks);
						tv.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx);

						tv.setSpousenameremarks(strSpouseNameRemarks);
						tv.setSpousenameflag(strSpouseNamechkbx);

						tv.setSpousenoremarks(strSpouseMbnoRemarks);
						tv.setSpousenoflag(strSpouseMbnochkbx);

						tv.setYakanumremarks(strYakaNumRemarks);
						tv.setYakanumflag(strYakaNumchkbx);

						tv.setYakanumnameremarks(strYakaNoNameRemarks);
						tv.setYakanumnamflag(strYakaNoNamechkbx);

						tv.setLcmobilenoremark(strlc1numberRemarks);
						tv.setLcmobilenoflag(strlc1numberchkbx);

						tv.setStagechairmannoremarks(strstgorempnoRemarks);
						tv.setStagechairmannoflag(strstgorempnochkbx);
						
						tv.setMbnonotinnameremarks(strmbnumnotinnameRemarks);
						tv.setMbnonotinnameflag(strmbnumnotinnamechkbx);

						
						
						tv.setTvremarks(strRemarks);
						tv.setTvverdict(blnTvverdict);

						teleVerifyRepo.save(tv);
						response.setData("TV Verification: " + strId + " (Guarantor) updated successfully");
					}

				} else if (strMsg.contains("")) {
					response.setData("General error");
				}
			}

			int Id = Integer.parseInt(strId);

			Optional<Guarantor> opGua = guarantorRepo.findById(Id);
			if (opGua.isPresent()) {
				System.out.println(strRemarks);
				System.out.println(blnTvverdict);
				Guarantor guarantor = opGua.get();
				guarantor.setTvverified(blnTvverdict);
				guarantor.setTvremarks(strRemarks);
				guarantor.setTvuser(strLoginuser);
				guarantor.setTvdatetime(strTVRequestdatetime);
				guarantorRepo.save(guarantor);
			}

			strQuery = "";

			TvVerification tvverify2 = new TvVerification();
			tvverify2.setTvid(strId2);
			tvverify2.setSurname(strSurname2);
			tvverify2.setSurnameremark(strSurname2remark);
			tvverify2.setSurnameflag(strSurname2chkbx);
			tvverify2.setFirstname(strFname2);
			tvverify2.setFirstnameremark(strfname2remark);
			tvverify2.setFirstnameflag(strfname2chkbx);
			tvverify2.setOthername(stroname2);
			tvverify2.setOthernameremark(stroname2remark);
			tvverify2.setOthernameflag(stroname2chkbx);
			tvverify2.setNationalid(strnid2);
			tvverify2.setNationalidremark(strnid2remark);
			tvverify2.setNationalidflag(strnid2chkbx);
			tvverify2.setMobileno(strmobno2);
			tvverify2.setMobilenoremark(strmobno2remark);
			tvverify2.setMobilenoflag(strmobno2chkbx);
			tvverify2.setAddress(stradd2);
			tvverify2.setAddressremark(stradd2remark);
			tvverify2.setAddressflag(stradd2chkbx);
			tvverify2.setPermanentaddress(strpadd2);
			tvverify2.setPermanentaddressremark(strpadd2remark);
			tvverify2.setPermanentaddressflag(strpadd2chkbx);
			tvverify2.setYearsinaddress(stryradd2);
			tvverify2.setYearsinaddressremark(stryradd2remark);
			tvverify2.setYearsinaddressflag(stryradd2chkbx);
			tvverify2.setOwnhouserented(stror2);
			tvverify2.setOwnhouserentedremark(stror2remark);
			tvverify2.setOwnhouserentedflag(stror2chkbx);
			tvverify2.setRentpm(strrpm2);
			tvverify2.setRentpmremark(strrpm2remark);
			tvverify2.setRentpmflag(strrpm2chkbx);
			tvverify2.setNextofkin(strnok2);
			tvverify2.setNextofkinremark(strnok2remark);
			tvverify2.setNextofkinflag(strnok2chkbx);
			tvverify2.setNokmobileno(strnokm2);
			tvverify2.setNokmobilenoremark(strnokm2remark);
			tvverify2.setNokmobilenoflag(strnokm2chkbx);
			tvverify2.setNokrelationship(strnokr2);
			tvverify2.setNokrelationshipremark(strnokr2remark);
			tvverify2.setNokrelationshipflag(strnokr2chkbx);
			tvverify2.setNokagreeing(strnoka2);
			tvverify2.setNokagreeingremark(strnoka2remark);
			tvverify2.setNokagreeingflag(strnoka2chkbx);
			tvverify2.setBikeregno(strbregno2);
			tvverify2.setBikeregnoremark(strbregno2remark);
			tvverify2.setBikeregnoflag(strbregno2chkbx);
			tvverify2.setBikeowner(strbowner2);
			tvverify2.setBikeownerremark(strbowner2remark);
			tvverify2.setBikeownerflag(strbowner2chkbx);
			tvverify2.setSalaried(strsal2);
			tvverify2.setSalariedremark(strsal2remark);
			tvverify2.setSalariedflag(strsal2chkbx);
			tvverify2.setMonthlyincome(strmi2);
			tvverify2.setMonthlyincomeremark(strmi2remark);
			tvverify2.setMonthlyincomeflag(strmi2chkbx);
			tvverify2.setOtherincomesource(strois2);
			tvverify2.setOtherincomesourceremark(strois2remark);
			tvverify2.setOtherincomesourceflag(strois2chkbx);
			tvverify2.setOtherincome(stroi2);
			tvverify2.setOtherincomeremark(stroi2remark);
			tvverify2.setOtherincomeflag(stroi2chkbx);
			tvverify2.setStage(strstgnm2);
			tvverify2.setStageremark(strstgnm2remark);
			tvverify2.setStageflag(strstgnm2chkbx);
			tvverify2.setStageaddress(strstgadd2);
			tvverify2.setStageaddressremark(strstgadd2remark);
			tvverify2.setStageaddressflag(strstgadd2chkbx);
			tvverify2.setStagechairconfirmation(strstgcc2);
			tvverify2.setStagechairconfirmationremark(strstgcc2remark);
			tvverify2.setStagechairconfirmationflag(strstgcc2chkbx);
			tvverify2.setLc(strlcnm2);
			tvverify2.setLcremark(strlcnm2remark);
			tvverify2.setLcflag(strlcnm2chkbx);
			tvverify2.setRelationship(strrltnshp2);
			tvverify2.setRelationshipremark(strrltnshp2remark);
			tvverify2.setRelationshipflag(strrltnshp2chkbx);
			
			tvverify2.setDob(strDob2);
			tvverify2.setDobremark(strDobRemarks2);
			tvverify2.setDobflag(strDobchkbx2);

			tvverify2.setLandlordname(strLlname2);
			tvverify2.setLandlordnameremark(strLlnameRemarks2);
			tvverify2.setLandlordnameflag(strLlnamechkbx2);

			tvverify2.setLandlordmobileno(strLlmbno2);
			tvverify2.setLandlordmobilenoremark(strLlmbnoRemarks2);
			tvverify2.setLandlordmobilenoflag(strLlmbnochkbx2);

			tvverify2.setResiadrss(strResiadrss2);
			tvverify2.setResiadrssremarks(strResiadrssRemarks2);
			tvverify2.setResiadrssflag(strResiadrsschkbx2);

			tvverify2.setMaritalstatus(strMaritalsts2);
			tvverify2.setMaritalstatusremark(strMaritalstsRemarks2);
			tvverify2.setMaritalstatusflag(strMaritalstschkbx2);

			tvverify2.setLlrentfeedback(strLLFeedAbtRnt2);
			tvverify2.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks2);
			tvverify2.setLlrentfeedbackflag(strLLFeedAbtRntchkbx2);

			tvverify2.setNoyrsinarea(strNoOfYrsInArea2);
			tvverify2.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks2);
			tvverify2.setNoyrsinareaflag(strNoOfYrsInAreachkbx2);

			tvverify2.setLc1chmnrecfeed(strLc1chmnRecFeedbk2);
			tvverify2.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks2);
			tvverify2.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx2);

			tvverify2.setNearlmarkresi(strNearLndmrkorResi2);
			tvverify2.setNearlmarkresiremarks(strNearLndmrkorResiRemarks2);
			tvverify2.setNearlmarkresiflag(strNearLndmrkorResichkbx2);

			tvverify2.setEmptype(strEmpType2);
			tvverify2.setEmptyperemarks(strEmpTypeRemarks2);
			tvverify2.setEmptypeflag(strEmpTypechkbx2);

			tvverify2.setStgorwrkadrssnearlmark(strStgOrWrkadrssWithNearLNMRK2);
			tvverify2.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks2);
			tvverify2.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx2);

			tvverify2.setNoofyrsinstgorbusi(strNoOfYrsInstgorBusi2);
			tvverify2.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks2);
			tvverify2.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx2);

			tvverify2.setSpousename(strSpouseName2);
			tvverify2.setSpousenameremarks(strSpouseNameRemarks2);
			tvverify2.setSpousenameflag(strSpouseNamechkbx2);

			tvverify2.setSpouseno(strSpouseMbno2);
			tvverify2.setSpousenoremarks(strSpouseMbnoRemarks2);
			tvverify2.setSpousenoflag(strSpouseMbnochkbx2);

			tvverify2.setYakanum(strYakaNum2);
			tvverify2.setYakanumremarks(strYakaNumRemarks2);
			tvverify2.setYakanumflag(strYakaNumchkbx2);

			tvverify2.setYakanumname(strYakaNoName2);
			tvverify2.setYakanumnameremarks(strYakaNoNameRemarks2);
			tvverify2.setYakanumnamflag(strYakaNoNamechkbx2);

			tvverify2.setLcmobileno(strlc1number2);
			tvverify2.setLcmobilenoremark(strlc1numberRemarks2);
			tvverify2.setLcmobilenoflag(strlc1numberchkbx2);

			tvverify2.setStagechairmanno(strstgorempno2);
			tvverify2.setStagechairmannoremarks(strstgorempnoRemarks2);
			tvverify2.setStagechairmannoflag(strstgorempnochkbx2);
			
			tvverify2.setMbnonotinname(strmbnumnotinname2);
			tvverify2.setMbnonotinnameremarks(strmbnumnotinnameRemarks2);
			tvverify2.setMbnonotinnameflag(strmbnumnotinnamechkbx2);


			
			tvverify2.setFirstguarantor(false);
			tvverify2.setSecondguarantor(true);
			tvverify2.setTvremarks(strRemarks);
			tvverify2.setTvverdict(blnTvverdict);

			try {
				teleVerifyRepo.save(tvverify2);
				response.setData("TV Verification for : " + strId2 + " (Guarantor) saved successfully.");

			} catch (Exception e) {
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				strMsg = th.getCause().toString();
				if (strMsg.contains("org.hibernate.exception.ConstraintViolationException")) {

					Optional<TvVerification> optv = teleVerifyRepo.findById(strId2);

					if (optv.isPresent()) {
						TvVerification tv = optv.get();
						tv.setSurnameremark(strSurname2remark);
						tv.setSurnameflag(strSurname2chkbx);
						tv.setFirstnameremark(strfname2remark);
						tv.setFirstnameflag(strfname2chkbx);
						tv.setOthernameremark(stroname2remark);
						tv.setOthernameflag(stroname2chkbx);
						tv.setMaritalstatusremark(strmi2remark);
						tv.setMobilenoremark(strmobno2remark);
						tv.setMobilenoflag(strmobno2chkbx);
						tv.setStageremark(strstgnm2remark);
						tv.setStageflag(strstgnm2chkbx);
						tv.setLcremark(strlcnm2remark);
						tv.setLcflag(strlcnm2chkbx);
						tv.setNationalidremark(strnid2remark);
						tv.setNationalidflag(strnid2chkbx);
						tv.setBikeregnoremark(strbregno2remark);
						tv.setBikeregnoflag(strbregno2chkbx);
						tv.setNextofkinremark(strnok2remark);
						tv.setNextofkinflag(strnok2chkbx);
						tv.setNokmobilenoremark(strnokm2remark);
						tv.setNokmobilenoflag(strnokm2chkbx);
						tv.setNokrelationshipremark(strnokr2remark);
						tv.setNokrelationshipflag(strnokr2chkbx);
						tv.setNokagreeingremark(strnoka2remark);
						tv.setNokagreeingflag(strnoka2chkbx);
						tv.setOwnhouserentedremark(stror2remark);
						tv.setOwnhouserentedflag(stror2chkbx);
						tv.setRentpmremark(strrpm2remark);
						tv.setRentpmflag(strrpm2chkbx);
						tv.setOtherincomesourceremark(strois2remark);
						tv.setOtherincomesourceflag(strois2chkbx);
						tv.setPermanentaddressremark(strpadd2remark);
						tv.setPermanentaddressflag(strpadd2chkbx);
						
						tv.setDobremark(strDobRemarks2);
						tv.setDobflag(strDobchkbx2);

						tv.setLandlordnameremark(strLlnameRemarks2);
						tv.setLandlordnameflag(strLlnamechkbx2);

						tv.setLandlordmobilenoremark(strLlmbnoRemarks2);
						tv.setLandlordmobilenoflag(strLlmbnochkbx2);

						tv.setResiadrssremarks(strResiadrssRemarks2);
						tv.setResiadrssflag(strResiadrsschkbx2);

						tv.setMaritalstatusremark(strMaritalstsRemarks2);
						tv.setMaritalstatusflag(strMaritalstschkbx2);

						tv.setLlrentfeedbackremarks(strLLFeedAbtRntRemarks2);
						tv.setLlrentfeedbackflag(strLLFeedAbtRntchkbx2);

						tv.setNoyrsinarearemarks(strNoOfYrsInAreaRemarks2);
						tv.setNoyrsinareaflag(strNoOfYrsInAreachkbx2);

						tv.setLc1chmnrecfeedremarks(strLc1chmnRecFeedbkRemarks2);
						tv.setLc1chmnrecfeedflag(strLc1chmnRecFeedbkchkbx2);

						tv.setNearlmarkresiremarks(strNearLndmrkorResiRemarks2);
						tv.setNearlmarkresiflag(strNearLndmrkorResichkbx2);

						tv.setEmptyperemarks(strEmpTypeRemarks2);
						tv.setEmptypeflag(strEmpTypechkbx2);

						tv.setStgorwrkadrssnearlmarkremarks(strStgOrWrkadrssWithNearLNMRKRemarks2);
						tv.setStgorwrkadrssnearlmarkflag(strStgOrWrkadrssWithNearLNMRKchkbx2);

						tv.setNoofyrsinstgorbusiremarks(strNoOfYrsInstgorBusiRemarks2);
						tv.setNoofyrsinstgorbuisflag(strNoOfYrsInstgorBusichkbx2);

						tv.setSpousenameremarks(strSpouseNameRemarks2);
						tv.setSpousenameflag(strSpouseNamechkbx2);

						tv.setSpousenoremarks(strSpouseMbnoRemarks2);
						tv.setSpousenoflag(strSpouseMbnochkbx2);

						tv.setYakanumremarks(strYakaNumRemarks2);
						tv.setYakanumflag(strYakaNumchkbx2);

						tv.setYakanumnameremarks(strYakaNoNameRemarks2);
						tv.setYakanumnamflag(strYakaNoNamechkbx2);

						tv.setLcmobilenoremark(strlc1numberRemarks2);
						tv.setLcmobilenoflag(strlc1numberchkbx2);

						tv.setStagechairmannoremarks(strstgorempnoRemarks2);
						tv.setStagechairmannoflag(strstgorempnochkbx2);

						tv.setMbnonotinnameremarks(strmbnumnotinnameRemarks2);
						tv.setMbnonotinnameflag(strmbnumnotinnamechkbx2);
						
						
						tv.setTvremarks(strRemarks);
						tv.setTvverdict(blnTvverdict);
						System.out.println(strRemarks);
						System.out.println(blnTvverdict);
						teleVerifyRepo.save(tv);
						response.setData("TV Verification: " + strId2 + " (Guarantor) updated successfully");
					}
				} else if (strMsg.contains("")) {
					response.setData("General error");
				}
			}

			int Id2 = Integer.parseInt(strId2);

			Optional<Guarantor> opGua2 = guarantorRepo.findById(Id2);
			if (opGua2.isPresent()) {
				Guarantor guarantor = opGua2.get();
				System.out.println(strRemarks);
				System.out.println(blnTvverdict);
				guarantor.setTvverified(blnTvverdict);
				guarantor.setTvremarks(strRemarks);
				guarantor.setTvuser(strLoginuser);
				guarantor.setTvdatetime(strTVRequestdatetime);
				guarantorRepo.save(guarantor);
			}

		}
		return response;
	}

	public ResponseEntity<String> printTv(String custId) {
//		HttpSession session = request.getSession(true);
//		String strSessionid = (String) session.getAttribute("tvprofid");

		String strMsg = "";
		String strQuery = "";

		String strDate = "";
		String strPrintDate = "";
		String strPdffilename = "";

		// Customer details
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

		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource1 = null;
		File file = null;

		// Customer tvverification
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

		// Guaran 1
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

		// Guaratnor 1 verification
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

		// Guarantor 2
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

		// Guaratnor 2 verification
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

		Response<String> response = new Response<>();

		int intCount = 0;

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
				strCustomerName = strSurname + " " + strFirstname; // Rectify for long company names
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
				if (strRevremarks == null) {
					strRevremarks = "";
				}

				if (blnTv == true) {
					strTv = "Recommended";
				} else {
					strTv = "Not Recommended";
				}
				if (blnFi == true) {
					strFi = "Recommended";
				} else {
					strFi = "Not Recommended";
				}
				if (blnCqc == true) {
					strCqc = "Recommended";
				} else {
					strCqc = "Not Recommended";
				}

				// load customer tvverifiation details
//					strQuery = "From TvVerification where tvid = '" + strCustomer + "'";

				try {
					Optional<TvVerification> opteleverification = teleVerifyRepo.findById(strCustomer);

					if (opteleverification.isPresent()) {

						TvVerification tv = opteleverification.get();

						strSurnameremark = tv.getSurnameremark();
						strSurnameflag = tv.getSurnameflag();
//							if (strSurnameflag.equals("false")) {strSurnameflag = "";}
						strfnameremark = tv.getFirstnameremark();
						strfnameflag = tv.getFirstnameflag();
//							if (strfnameflag.equals("false")) {strfnameflag = "";}
						stronameremark = tv.getOthernameremark();
						stronameflag = tv.getOthernameflag();
//							if (stronameflag.equals("false")) {stronameflag = "";}
						strmsremark = tv.getMaritalstatusremark();
						strmsflag = tv.getMaritalstatusflag();
//							if (strmsflag.equals("false")) {strmsflag = "";}
						strsexremark = tv.getSexremark();
						strsexflag = tv.getSexflag();
//							if (strsexflag.equals("false")) {strsexflag = "";}
						strmobnoremark = tv.getMobilenoremark();
						strmobnoflag = tv.getMobilenoflag();
//							if (strmobnoflag.equals("false")) {strmobnoflag = "";}
						strstgnmremark = tv.getStageremark();
						strstgnmflag = tv.getStageflag();
//							if (strstgnmflag.equals("false")) {strstgnmflag = "";}
						strdistremark = tv.getDistrictremark();
						strdistflag = tv.getDistrictflag();
//							if (strdistflag.equals("false")) {strdistflag = "";}
						strlcnmremark = tv.getLcremark();
						strlcnmflag = tv.getLcflag();
//							if (strlcnmflag.equals("false")) {strlcnmflag = "";}
						strparremark = tv.getParishremark();
						strparflag = tv.getParishflag();
//							if (strparflag.equals("false")) {strparflag = "";}
						strnidremark = tv.getNationalidremark();
						strnidflag = tv.getNationalidflag();
//							if (strnidflag.equals("false")) {strnidflag = "";}
						strbregnoremark = tv.getBikeregnoremark();
						strbregnoflag = tv.getBikeregnoflag();
//							if (strbregnoflag.equals("false")) {strbregnoflag = "";}
						strbuseremark = tv.getBikeuseremark();
						strbuseflag = tv.getBikeuseflag();
//							if (strbuseflag.equals("false")) {strbuseflag = "";}
						strdobremark = tv.getDobremark();
						strdobflag = tv.getDobflag();
//							if (strdobflag.equals("false")) {strdobflag = "";}
						strcntremark = tv.getCountyremark();
						strcntflag = tv.getCountyflag();
//							if (strcntflag.equals("false")) {strcntflag = "";}
						strscntremark = tv.getSubcountyremark();
						strscntflag = tv.getSubcountyflag();
//							if (strscntflag.equals("false")) {strscntflag = "";}
						strvilremark = tv.getVillageremark();
						strvilflag = tv.getVillageflag();
//							if (strvilflag.equals("false")) {strvilflag = "";}
						stryrvilremark = tv.getYearsinvillageremark();
						stryrvilflag = tv.getYearsinvillageflag();
//							if (stryrvilflag.equals("false")) {stryrvilflag = "";}
						strnokremark = tv.getNextofkinremark();
						strnokflag = tv.getNextofkinflag();
//							if (strnokflag.equals("false")) {strnokflag = "";}
						strnokmremark = tv.getNokmobilenoremark();
						strnokmflag = tv.getNokmobilenoflag();
//							if (strnokmflag.equals("false")) {strnokmflag = "";}
						strnokrremark = tv.getNokrelationshipremark();
						strnokrflag = tv.getNokrelationshipflag();
//							if (strnokrflag.equals("false")) {strnokrflag = "";}
						strnokaremark = tv.getNokagreeingremark();
						strnokaflag = tv.getNokagreeingflag();
//							if (strnokaflag.equals("false")) {strnokaflag = "";}
						strdlremark = tv.getDrivingpermitremark();
						strdlflag = tv.getDrivingpermitflag();
//							if (strdlflag.equals("false")) {strdlflag = "";}
						strnatremark = tv.getNationalityremark();
						strnatflag = tv.getNationalityflag();
//							if (strnatflag.equals("false")) {strnatflag = "";}
						strnodremark = tv.getNoofdependantsremark();
						strnodflag = tv.getNoofdependantsflag();
//							if (strnodflag.equals("false")) {strnodflag = "";}
						strorremark = tv.getOwnhouserentedremark();
						strorflag = tv.getOwnhouserentedflag();
//							if (strorflag.equals("false")) {strorflag = "";}
						strllremark = tv.getLandlordnameremark();
						strllflag = tv.getLandlordnameflag();
//							if (strllflag.equals("false")) {strllflag = "";}
						strllmobremark = tv.getLandlordmobilenoremark();
						strllmobflag = tv.getLandlordmobilenoflag();
//							if (strllmobflag.equals("false")) {strllmobflag = "";}
						strrpmremark = tv.getRentpmremark();
						strrpmflag = tv.getRentpmflag();
//							if (strrpmflag.equals("false")) {strrpmflag = "";}
						stroisremark = tv.getOtherincomesourceremark();
						stroisflag = tv.getOtherincomesourceflag();
//							if (stroisflag.equals("false")) {stroisflag = "";}
						strdpsremark = tv.getDownpaymentsourceremark();
						strdpsflg = tv.getDownpaymentsourceflag();
//							if (strdpsflg.equals("false")) {strdpsflg = "";}
						strpaddremark = tv.getPermanentaddressremark();
						strpaddflag = tv.getPermanentaddressflag();
//							if (strpaddflag.equals("false")) {strpaddflag = "";}
						strfathremark = tv.getFathersnameremark();
						strfathflag = tv.getFathersnameflag();
//							if (strfathflag.equals("false")) {strfathflag = "";}
						strmothremark = tv.getMothersnameremark();
						strmothflag = tv.getMothersnameflag();
//							if (strmothflag.equals("false")) {strmothflag = "";}
						strnpsremark = tv.getNearbypolicestationremark();
						strnpsflag = tv.getNearbypolicestationflag();
//							if (strnpsflag.equals("false")) {strnpsflag = "";}
						strlcnmobnoremark = tv.getLcmobilenoremark();
						strlcnmobnoflag = tv.getLcmobilenoflag();
//							if (strlcnmobnoflag.equals("false")) {strlcnmobnoflag = "";}
						strcusttyperemark = tv.getCusttyperemark();
						strcusttypeflag = tv.getCusttypeflag();
//							if (strcusttypeflag.equals("false")) {strcusttypeflag = "";}
					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setErrorMsg("Error retrieving customer id: " + strCustomer);
				}

				// Guarantors
//					strQuery = "From Guarantor where custid = '" + strCustomer + "'";

				try {
					List<Guarantor> guarantor = guarantorRepo.findBycustid(strCustomer);
					if (guarantor.size() != 0) {
						for (Guarantor gr : guarantor) {
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
								if (blnNoka1 == true) {
									strNoka1 = "true";
								} else {
									strNoka1 = "false";
								}
								strBkregno1 = gr.getBikeregno();
								strBkowner1 = gr.getBikeowner();
								blnSal1 = gr.getSalaried();
								if (blnSal1 == true) {
									strSal1 = "true";
								} else {
									strSal1 = "false";
								}
								strEmpname1 = gr.getEmployername();
								strMnthin1 = gr.getMonthlyincome();
								strOis1 = gr.getOis();
								strOi1 = gr.getOtherincome();
								strStg1 = gr.getStagename();
								strStgadd1 = gr.getStageaddress();
								blnStgchconf1 = gr.getStagechairconf();
								if (blnStgchconf1 == true) {
									strStgchconf1 = "true";
								} else {
									strStgchconf1 = "false";
								}
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
								if (blnNoka2 == true) {
									strNoka2 = "true";
								} else {
									strNoka2 = "false";
								}
								strBkregno2 = gr.getBikeregno();
								strBkowner2 = gr.getBikeowner();
								blnSal2 = gr.getSalaried();
								if (blnSal2 == true) {
									strSal2 = "true";
								} else {
									strSal2 = "false";
								}
								strEmpname2 = gr.getEmployername();
								strMnthin2 = gr.getMonthlyincome();
								strOis2 = gr.getOis();
								strOi2 = gr.getOtherincome();
								strStg2 = gr.getStagename();
								strStgadd2 = gr.getStageaddress();
								blnStgchconf2 = gr.getStagechairconf();
								if (blnStgchconf2 == true) {
									strStgchconf2 = "true";
								} else {
									strStgchconf2 = "false";
								}
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

						if (blnTv1 == true) {
							strTv1 = "Recommended";
						} else {
							strTv1 = "Not Recommended";
						}
						if (blnFi1 == true) {
							strFi1 = "Recommended";
						} else {
							strFi1 = "Not Recommended";
						}
						if (blnCqc1 == true) {
							strCqc1 = "Recommended";
						} else {
							strCqc1 = "Not Recommended";
						}

						if (blnTv2 == true) {
							strTv2 = "Recommended";
						} else {
							strTv2 = "Not Recommended";
						}
						if (blnFi2 == true) {
							strFi2 = "Recommended";
						} else {
							strFi2 = "Not Recommended";
						}
						if (blnCqc2 == true) {
							strCqc2 = "Recommended";
						} else {
							strCqc2 = "Not Recommended";
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setErrorMsg("Error retrieving guarantors for customer id: " + strCustomer);
				}

				// guarantor tvverification
				if (strId1.length() > 0) {
					// *******************************************************************

//						strQuery = "From TvVerification where tvid = '" + strId1 + "'";

					try {
						Optional<TvVerification> opteleverification = teleVerifyRepo.findById(strId1);

						if (opteleverification.isPresent()) {

							TvVerification tv = opteleverification.get();

							strSurnameremark1 = tv.getSurnameremark();
							strSurnameflag1 = tv.getSurnameflag();
//								if (strSurnameflag1.equals("false")) {strSurnameflag1 = "";}
							strfnameremark1 = tv.getFirstnameremark();
							strfnameflag1 = tv.getFirstnameflag();
//								if (strfnameflag1.equals("false")) {strfnameflag1 = "";}
							stronameremark1 = tv.getOthernameremark();
							stronameflag1 = tv.getOthernameflag();
//								if (stronameflag1.equals("false")) {stronameflag1 = "";}
							strmobnoremark1 = tv.getMobilenoremark();
							strmobnoflag1 = tv.getMobilenoflag();
//								if (strmobnoflag1.equals("false")) {strmobnoflag1 = "";}
							strstgnmremark1 = tv.getStageremark();
							strstgnmflag1 = tv.getStageflag();
//								if (strstgnmflag1.equals("false")) {strstgnmflag1 = "";}
							straddremark1 = tv.getAddressremark();
							straddflag1 = tv.getAddressflag();
//								if (straddflag1.equals("false")) {straddflag1 = "";}
							strlcnmremark1 = tv.getLcremark();
							strlcnmflag1 = tv.getLcflag();
//								if (strlcnmflag1.equals("false")) {strlcnmflag1 = "";}
							strnidremark1 = tv.getNationalidremark();
							strnidflag1 = tv.getNationalidflag();
//								if (strnidflag1.equals("false")) {strnidflag1 = "";}
							strbregnoremark1 = tv.getBikeregnoremark();
							strbregnoflag1 = tv.getBikeregnoflag();
//								if (strbregnoflag1.equals("false")) {strbregnoflag1 = "";}
							stryrvilremark1 = tv.getYearsinaddressremark();
							stryrvilflag1 = tv.getYearsinaddressflag();
//								if (stryrvilflag1.equals("false")) {stryrvilflag1 = "";}
							strnokremark1 = tv.getNextofkinremark();
							strnokflag1 = tv.getNextofkinflag();
//								if (strnokflag1.equals("false")) {strnokflag1 = "";}
							strnokmremark1 = tv.getNokmobilenoremark();
							strnokmflag1 = tv.getNokmobilenoflag();
//								if (strnokmflag1.equals("false")) {strnokmflag1 = "";}
							strnokrremark1 = tv.getNokrelationshipremark();
							strnokrflag1 = tv.getNokrelationshipflag();
//								if (strnokrflag1.equals("false")) {strnokrflag1 = "";}
							strnokaremark1 = tv.getNokagreeingremark();
							strnokaflag1 = tv.getNokagreeingflag();
//								if (strnokaflag1.equals("false")) {strnokaflag1 = "";}
							strorremark1 = tv.getOwnhouserentedremark();
							strorflag1 = tv.getOwnhouserentedflag();
//								if (strorflag1.equals("false")) {strorflag1 = "";}
							strrpmremark1 = tv.getRentpmremark();
							strrpmflag1 = tv.getRentpmflag();
//								if (strrpmflag1.equals("false")) {strrpmflag1 = "";}
							stroisremark1 = tv.getOtherincomesourceremark();
							stroisflag1 = tv.getOtherincomesourceflag();
//								if (stroisflag1.equals("false")) {stroisflag1 = "";}
							strpaddremark1 = tv.getPermanentaddressremark();
							strpaddflag1 = tv.getPermanentaddressflag();
//								if (strpaddflag1.equals("false")) {strpaddflag1 = "";}		
							strbownerremark1 = tv.getBikeownerremark();
							strbownerflag1 = tv.getBikeownerflag();
//								if (strbownerflag1.equals("false")) {strbownerflag1 = "";}
							strsalremark1 = tv.getSalariedremark();
							strsalflag1 = tv.getSalariedflag();
//								if (strsalflag1.equals("false")) {strsalflag1 = "";}
							strmiremark1 = tv.getMonthlyincomeremark();
							strmiflag1 = tv.getMonthlyincomeflag();
//								if (strmiflag1.equals("false")) {strmiflag1 = "";}
							stroiremark1 = tv.getOtherincomeremark();
							stroiflag1 = tv.getOtherincomeflag();
//								if (stroiflag1.equals("false")) {stroiflag1 = "";}
							strstgaddremark1 = tv.getStageaddressremark();
							strstgaddflag1 = tv.getStageaddressflag();
//								if (strstgaddflag1.equals("false")) {strstgaddflag1 = "";}
							strstgccremark1 = tv.getStagechairconfirmationremark();
							strstgccflag1 = tv.getStagechairconfirmationflag();
//								if (strstgccflag1.equals("false")) {strstgccflag1 = "";}
							strrltnshpremark1 = tv.getRelationshipremark();
							strrltnshpflag1 = tv.getRelationshipflag();
//								if (strrltnshpflag1.equals("false")) {strrltnshpflag1 = "";}

						}
					} catch (Exception e) {
//							System.out.println(e.getLocalizedMessage());
						e.printStackTrace();
						response.setErrorMsg("Error retrieving guarantor id: " + strId1);
					}
				}

				if (strId2.length() > 0) {
					// *******************************************************************

//						strQuery = "From TvVerification where tvid = '" + strId2 + "'";

					try {
						Optional<TvVerification> televerification = teleVerifyRepo.findById(strId2);

						if (televerification.isPresent()) {

							TvVerification tv = televerification.get();

							strSurnameremark2 = tv.getSurnameremark();
							strSurnameflag2 = tv.getSurnameflag();
//								if (strSurnameflag2.equals("false")) {strSurnameflag2 = "";}
							strfnameremark2 = tv.getFirstnameremark();
							strfnameflag2 = tv.getFirstnameflag();
//								if (strfnameflag2.equals("false")) {strfnameflag2 = "";}
							stronameremark2 = tv.getOthernameremark();
							stronameflag2 = tv.getOthernameflag();
//								if (stronameflag2.equals("false")) {stronameflag2 = "";}
							strmobnoremark2 = tv.getMobilenoremark();
							strmobnoflag2 = tv.getMobilenoflag();
//								if (strmobnoflag2.equals("false")) {strmobnoflag2 = "";}
							strstgnmremark2 = tv.getStageremark();
							strstgnmflag2 = tv.getStageflag();
//								if (strstgnmflag2.equals("false")) {strstgnmflag2 = "";}
							straddremark2 = tv.getAddressremark();
							straddflag2 = tv.getAddressflag();
//								if (straddflag2.equals("false")) {straddflag2 = "";}
							strlcnmremark2 = tv.getLcremark();
							strlcnmflag2 = tv.getLcflag();
//								if (strlcnmflag2.equals("false")) {strlcnmflag2 = "";}
							strnidremark2 = tv.getNationalidremark();
							strnidflag2 = tv.getNationalidflag();
//								if (strnidflag2.equals("false")) {strnidflag2 = "";}
							strbregnoremark2 = tv.getBikeregnoremark();
							strbregnoflag2 = tv.getBikeregnoflag();
//								if (strbregnoflag2.equals("false")) {strbregnoflag2 = "";}
							stryrvilremark2 = tv.getYearsinaddressremark();
							stryrvilflag2 = tv.getYearsinaddressflag();
//								if (stryrvilflag2.equals("false")) {stryrvilflag2 = "";}
							strnokremark2 = tv.getNextofkinremark();
							strnokflag2 = tv.getNextofkinflag();
//								if (strnokflag2.equals("false")) {strnokflag2 = "";}
							strnokmremark2 = tv.getNokmobilenoremark();
							strnokmflag2 = tv.getNokmobilenoflag();
//								if (strnokmflag2.equals("false")) {strnokmflag2 = "";}
							strnokrremark2 = tv.getNokrelationshipremark();
							strnokrflag2 = tv.getNokrelationshipflag();
//								if (strnokrflag2.equals("false")) {strnokrflag2 = "";}
							strnokaremark2 = tv.getNokagreeingremark();
							strnokaflag2 = tv.getNokagreeingflag();
//								if (strnokaflag2.equals("false")) {strnokaflag2 = "";}
							strorremark2 = tv.getOwnhouserentedremark();
							strorflag2 = tv.getOwnhouserentedflag();
//								if (strorflag2.equals("false")) {strorflag2 = "";}
							strrpmremark2 = tv.getRentpmremark();
							strrpmflag2 = tv.getRentpmflag();
//								if (strrpmflag2.equals("false")) {strrpmflag2 = "";}
							stroisremark2 = tv.getOtherincomesourceremark();
							stroisflag2 = tv.getOtherincomesourceflag();
//								if (stroisflag2.equals("false")) {stroisflag2 = "";}
							strpaddremark2 = tv.getPermanentaddressremark();
							strpaddflag2 = tv.getPermanentaddressflag();
//							if (strpaddflag2.equals("false")) {
//								strpaddflag2 = "";
//							}
							strbownerremark2 = tv.getBikeownerremark();
							strbownerflag2 = tv.getBikeownerflag();
//								if (strbownerflag2.equals("false")) {strbownerflag2 = "";}
							strsalremark2 = tv.getSalariedremark();
							strsalflag2 = tv.getSalariedflag();
//								if (strsalflag2.equals("false")) {strsalflag2 = "";}
							strmiremark2 = tv.getMonthlyincomeremark();
							strmiflag2 = tv.getMonthlyincomeflag();
//								if (strmiflag2.equals("false")) {strmiflag2 = "";}
							stroiremark2 = tv.getOtherincomeremark();
							stroiflag2 = tv.getOtherincomeflag();
//								if (stroiflag2.equals("false")) {stroiflag2 = "";}
							strstgaddremark2 = tv.getStageaddressremark();
							strstgaddflag2 = tv.getStageaddressflag();
//								if (strstgaddflag2.equals("false")) {strstgaddflag2 = "";}
							strstgccremark2 = tv.getStagechairconfirmationremark();
							strstgccflag2 = tv.getStagechairconfirmationflag();
//								if (strstgccflag2.equals("false")) {strstgccflag2 = "";}
							strrltnshpremark2 = tv.getRelationshipremark();
							strrltnshpflag2 = tv.getRelationshipflag();
//								if (strrltnshpflag2.equals("false")) {strrltnshpflag2 = "";}

						}
					} catch (Exception e) {
//							System.out.println(e.getLocalizedMessage());
						e.printStackTrace();
						response.setErrorMsg("Error retrieving guarantor id: " + strId2);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
//					strPdffilename = "src/main/resources/Documents/TvVerification" + strCustomer + ".pdf";

				PdfWriter.getInstance(document, outputStream);
				document.open();
				Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
				Font bldFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);

				// section 0
				PdfPTable hdrtbl = new PdfPTable(2);
				hdrtbl.setWidthPercentage(100);
				hdrtbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				hdrtbl.setWidths(new int[] { 50, 50 });
				hdrtbl.setTotalWidth(100);

				PdfPCell lblblnk3 = new PdfPCell(new Phrase("TV VERIFICATION:", bldFont));
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

				// section I
				PdfPTable tbl = new PdfPTable(4);
				tbl.setWidthPercentage(100);
				tbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbl.setWidths(new int[] { 25, 25, 25, 25 });
				tbl.setTotalWidth(100);
				addCustTableHeader(tbl);

				// add table rows
				PdfPCell lblcustid = new PdfPCell(new Phrase("Customer Id: ", font));
				tbl.addCell(lblcustid);
				PdfPCell custid = new PdfPCell(new Phrase(strCustomer, font));
				tbl.addCell(custid);
				PdfPCell custidremark = new PdfPCell(new Phrase("", font));
				tbl.addCell(custidremark);
				PdfPCell custidflag = new PdfPCell(new Phrase("", font));
				tbl.addCell(custidflag);

				PdfPCell lblnatid = new PdfPCell(new Phrase("National Id: ", font));
				tbl.addCell(lblnatid);
				PdfPCell natid = new PdfPCell(new Phrase(strNationalid, font));
				tbl.addCell(natid);
				PdfPCell natidremark = new PdfPCell(new Phrase(strnidremark, font));
				tbl.addCell(natidremark);
				PdfPCell natidflag = new PdfPCell(new Phrase(strnidflag, font));
				tbl.addCell(natidflag);

				PdfPCell lblsurname = new PdfPCell(new Phrase("Surname: ", font));
				tbl.addCell(lblsurname);
				PdfPCell surname = new PdfPCell(new Phrase(strSurname, font));
				tbl.addCell(surname);
				PdfPCell surnameremark = new PdfPCell(new Phrase(strSurnameremark, font));
				tbl.addCell(surnameremark);
				PdfPCell surnameflag = new PdfPCell(new Phrase(strSurnameflag, font));
				tbl.addCell(surnameflag);

				PdfPCell lblfname = new PdfPCell(new Phrase("First name: ", font));
				tbl.addCell(lblfname);
				PdfPCell fname = new PdfPCell(new Phrase(strFirstname, font));
				tbl.addCell(fname);
				PdfPCell fnameremark = new PdfPCell(new Phrase(strfnameremark, font));
				tbl.addCell(fnameremark);
				PdfPCell fnameflag = new PdfPCell(new Phrase(strfnameflag, font));
				tbl.addCell(fnameflag);

				PdfPCell lbloname = new PdfPCell(new Phrase("Other name: ", font));
				tbl.addCell(lbloname);
				PdfPCell oname = new PdfPCell(new Phrase(strOthername, font));
				tbl.addCell(oname);
				PdfPCell onameremark = new PdfPCell(new Phrase(stronameremark, font));
				tbl.addCell(onameremark);
				PdfPCell onameflag = new PdfPCell(new Phrase(stronameflag, font));
				tbl.addCell(onameflag);

				PdfPCell lbldob = new PdfPCell(new Phrase("Date of Birth: ", font));
				tbl.addCell(lbldob);
				PdfPCell dob = new PdfPCell(new Phrase(strDob, font));
				tbl.addCell(dob);
				PdfPCell dobremark = new PdfPCell(new Phrase(strdobremark, font));
				tbl.addCell(dobremark);
				PdfPCell dobflag = new PdfPCell(new Phrase(strdobflag, font));
				tbl.addCell(dobflag);

				PdfPCell lblsex = new PdfPCell(new Phrase("Sex: ", font));
				tbl.addCell(lblsex);
				PdfPCell sex = new PdfPCell(new Phrase(strSex, font));
				tbl.addCell(sex);
				PdfPCell sexremark = new PdfPCell(new Phrase(strsexremark, font));
				tbl.addCell(sexremark);
				PdfPCell sexflag = new PdfPCell(new Phrase(strsexflag, font));
				tbl.addCell(sexflag);

				PdfPCell lblyrsinvill = new PdfPCell(new Phrase("Years in village: ", font));
				tbl.addCell(lblyrsinvill);
				PdfPCell yrsinvill = new PdfPCell(new Phrase(strYrsinvillage, font));
				tbl.addCell(yrsinvill);
				PdfPCell yrsinvillremark = new PdfPCell(new Phrase(stryrvilremark, font));
				tbl.addCell(yrsinvillremark);
				PdfPCell yrsinvillflag = new PdfPCell(new Phrase(stryrvilflag, font));
				tbl.addCell(yrsinvillflag);

				PdfPCell lblmstatus = new PdfPCell(new Phrase("Marital Status: ", font));
				tbl.addCell(lblmstatus);
				PdfPCell mstatus = new PdfPCell(new Phrase(strMaritalstatus, font));
				tbl.addCell(mstatus);
				PdfPCell mstatusremark = new PdfPCell(new Phrase(strmsremark, font));
				tbl.addCell(mstatusremark);
				PdfPCell mstatusflag = new PdfPCell(new Phrase(strmsflag, font));
				tbl.addCell(mstatusflag);

				PdfPCell lblnok = new PdfPCell(new Phrase("Next of Kin: ", font));
				tbl.addCell(lblnok);
				PdfPCell nok = new PdfPCell(new Phrase(strNok, font));
				tbl.addCell(nok);
				PdfPCell nokremark = new PdfPCell(new Phrase(strnokremark, font));
				tbl.addCell(nokremark);
				PdfPCell nokflag = new PdfPCell(new Phrase(strnokflag, font));
				tbl.addCell(nokflag);

				PdfPCell lblmobno = new PdfPCell(new Phrase("Mobile No: ", font));
				tbl.addCell(lblmobno);
				PdfPCell mobno = new PdfPCell(new Phrase(strMobileno, font));
				tbl.addCell(mobno);
				PdfPCell mobnoremark = new PdfPCell(new Phrase(strmobnoremark, font));
				tbl.addCell(mobnoremark);
				PdfPCell mobnoflag = new PdfPCell(new Phrase(strmobnoflag, font));
				tbl.addCell(mobnoflag);

				PdfPCell lblnokmobile = new PdfPCell(new Phrase("Next of Kin Mobile: ", font));
				tbl.addCell(lblnokmobile);
				PdfPCell nokmobile = new PdfPCell(new Phrase(strNokmobile, font));
				tbl.addCell(nokmobile);
				PdfPCell nokmobileremark = new PdfPCell(new Phrase(strnokmremark, font));
				tbl.addCell(nokmobileremark);
				PdfPCell nokmobileflag = new PdfPCell(new Phrase(strnokmflag, font));
				tbl.addCell(nokmobileflag);

				PdfPCell lblstage = new PdfPCell(new Phrase("Stage Name: ", font));
				tbl.addCell(lblstage);
				PdfPCell stage = new PdfPCell(new Phrase(strStage, font));
				tbl.addCell(stage);
				PdfPCell stageremark = new PdfPCell(new Phrase(strstgnmremark, font));
				tbl.addCell(stageremark);
				PdfPCell stageflag = new PdfPCell(new Phrase(strstgnmflag, font));
				tbl.addCell(stageflag);

				PdfPCell lblnokr = new PdfPCell(new Phrase("Next of Kin Relationship: ", font));
				tbl.addCell(lblnokr);
				PdfPCell nokr = new PdfPCell(new Phrase(strNorship, font));
				tbl.addCell(nokr);
				PdfPCell nokrremark = new PdfPCell(new Phrase(strnokremark, font));
				tbl.addCell(nokrremark);
				PdfPCell nokrflag = new PdfPCell(new Phrase(strnokflag, font));
				tbl.addCell(nokrflag);

				PdfPCell lblcurrbike = new PdfPCell(new Phrase("Current Bike Regno: ", font));
				tbl.addCell(lblcurrbike);
				PdfPCell curbike = new PdfPCell(new Phrase(strBikeregno, font));
				tbl.addCell(curbike);
				PdfPCell curbikeremark = new PdfPCell(new Phrase(strbregnoremark, font));
				tbl.addCell(curbikeremark);
				PdfPCell curbikeflag = new PdfPCell(new Phrase(strbregnoflag, font));
				tbl.addCell(curbikeflag);

				PdfPCell lblpermit = new PdfPCell(new Phrase("Driving Permit: ", font));
				tbl.addCell(lblpermit);
				PdfPCell permit = new PdfPCell(new Phrase(strPermit, font));
				tbl.addCell(permit);
				PdfPCell permitremark = new PdfPCell(new Phrase(strdlremark, font));
				tbl.addCell(permitremark);
				PdfPCell permitflag = new PdfPCell(new Phrase(strdlflag, font));
				tbl.addCell(permitflag);

				PdfPCell lblbikeuse = new PdfPCell(new Phrase("New Bike Use: ", font));
				tbl.addCell(lblbikeuse);
				PdfPCell bikeuse = new PdfPCell(new Phrase(strBikeuse, font));
				tbl.addCell(bikeuse);
				PdfPCell bikeuseremark = new PdfPCell(new Phrase(strbuseremark, font));
				tbl.addCell(bikeuseremark);
				PdfPCell bikeuseflag = new PdfPCell(new Phrase(strbuseflag, font));
				tbl.addCell(bikeuseflag);

				PdfPCell lblnationality = new PdfPCell(new Phrase("Nationality: ", font));
				tbl.addCell(lblnationality);
				PdfPCell nationality = new PdfPCell(new Phrase(strNationality, font));
				tbl.addCell(nationality);
				PdfPCell nationalityremark = new PdfPCell(new Phrase(strnatremark, font));
				tbl.addCell(nationalityremark);
				PdfPCell nationalityflag = new PdfPCell(new Phrase(strnatflag, font));
				tbl.addCell(nationalityflag);

				PdfPCell lblnod = new PdfPCell(new Phrase("No of Dependants: ", font));
				tbl.addCell(lblnod);
				PdfPCell nod = new PdfPCell(new Phrase(strDependants, font));
				tbl.addCell(nod);
				PdfPCell nodremark = new PdfPCell(new Phrase(strnodremark, font));
				tbl.addCell(nodremark);
				PdfPCell nodflag = new PdfPCell(new Phrase(strnodflag, font));
				tbl.addCell(nodflag);

				PdfPCell lblor = new PdfPCell(new Phrase("Own House or Rented: ", font));
				tbl.addCell(lblor);
				PdfPCell or = new PdfPCell(new Phrase(strHouse, font));
				tbl.addCell(or);
				PdfPCell orremark = new PdfPCell(new Phrase(strorremark, font));
				tbl.addCell(orremark);
				PdfPCell orflag = new PdfPCell(new Phrase(strorflag, font));
				tbl.addCell(orflag);

				PdfPCell lblllname = new PdfPCell(new Phrase("Landlord Name: ", font));
				tbl.addCell(lblllname);
				PdfPCell llname = new PdfPCell(new Phrase(strLandlord, font));
				tbl.addCell(llname);
				PdfPCell llnameremark = new PdfPCell(new Phrase(strllremark, font));
				tbl.addCell(llnameremark);
				PdfPCell llnameflag = new PdfPCell(new Phrase(strllflag, font));
				tbl.addCell(llnameflag);

				PdfPCell lblllmobile = new PdfPCell(new Phrase("Landlord Mobile No: ", font));
				tbl.addCell(lblllmobile);
				PdfPCell llmobile = new PdfPCell(new Phrase(strLandlordmobile, font));
				tbl.addCell(llmobile);
				PdfPCell llmobileremark = new PdfPCell(new Phrase(strllmobremark, font));
				tbl.addCell(llmobileremark);
				PdfPCell llmobileflag = new PdfPCell(new Phrase(strllmobflag, font));
				tbl.addCell(llmobileflag);

				PdfPCell lblrpm = new PdfPCell(new Phrase("Rent Per Month: ", font));
				tbl.addCell(lblrpm);
				PdfPCell rpm = new PdfPCell(new Phrase(strRent, font));
				tbl.addCell(rpm);
				PdfPCell rpmremark = new PdfPCell(new Phrase(strrpmremark, font));
				tbl.addCell(rpmremark);
				PdfPCell rpmflag = new PdfPCell(new Phrase(strrpmflag, font));
				tbl.addCell(rpmflag);

				PdfPCell lblois = new PdfPCell(new Phrase("Other Income Source: ", font));
				tbl.addCell(lblois);
				PdfPCell ois = new PdfPCell(new Phrase(strOis, font));
				tbl.addCell(ois);
				PdfPCell oisremark = new PdfPCell(new Phrase(stroisremark, font));
				tbl.addCell(oisremark);
				PdfPCell oisflag = new PdfPCell(new Phrase(stroisflag, font));
				tbl.addCell(oisflag);

				PdfPCell lbldps = new PdfPCell(new Phrase("Down Payment Source: ", font));
				tbl.addCell(lbldps);
				PdfPCell dps = new PdfPCell(new Phrase(strDps, font));
				tbl.addCell(dps);
				PdfPCell dpsremark = new PdfPCell(new Phrase(strdpsremark, font));
				tbl.addCell(dpsremark);
				PdfPCell dpsflag = new PdfPCell(new Phrase(strdpsflg, font));
				tbl.addCell(dpsflag);

				PdfPCell lblpadd = new PdfPCell(new Phrase("Permanent Address: ", font));
				tbl.addCell(lblpadd);
				PdfPCell padd = new PdfPCell(new Phrase(strPadd, font));
				tbl.addCell(padd);
				PdfPCell paddremark = new PdfPCell(new Phrase(strpaddremark, font));
				tbl.addCell(paddremark);
				PdfPCell paddflag = new PdfPCell(new Phrase(strpaddflag, font));
				tbl.addCell(paddflag);

				PdfPCell lblfather = new PdfPCell(new Phrase("Father's Name: ", font));
				tbl.addCell(lblfather);
				PdfPCell father = new PdfPCell(new Phrase(strFather, font));
				tbl.addCell(father);
				PdfPCell fatherremark = new PdfPCell(new Phrase(strfathremark, font));
				tbl.addCell(fatherremark);
				PdfPCell fatherflag = new PdfPCell(new Phrase(strfathflag, font));
				tbl.addCell(fatherflag);

				PdfPCell lblmother = new PdfPCell(new Phrase("Mother's Name: ", font));
				tbl.addCell(lblmother);
				PdfPCell mother = new PdfPCell(new Phrase(strMother, font));
				tbl.addCell(mother);
				PdfPCell motherremark = new PdfPCell(new Phrase(strmothremark, font));
				tbl.addCell(motherremark);
				PdfPCell motherflag = new PdfPCell(new Phrase(strmothflag, font));
				tbl.addCell(motherflag);

				PdfPCell lblchair = new PdfPCell(new Phrase("Local Chairman: ", font));
				tbl.addCell(lblchair);
				PdfPCell lc = new PdfPCell(new Phrase(strLc, font));
				tbl.addCell(lc);
				PdfPCell lcremark = new PdfPCell(new Phrase(strlcnmremark, font));
				tbl.addCell(lcremark);
				PdfPCell lcflag = new PdfPCell(new Phrase(strlcnmflag, font));
				tbl.addCell(lcflag);

				PdfPCell lbllcmobile = new PdfPCell(new Phrase("LC Mobile no: ", font));
				tbl.addCell(lbllcmobile);
				PdfPCell lcmobile = new PdfPCell(new Phrase(strLcmobile, font));
				tbl.addCell(lcmobile);
				PdfPCell lcmobileremark = new PdfPCell(new Phrase(strlcnmobnoremark, font));
				tbl.addCell(lcmobileremark);
				PdfPCell lcmobileflag = new PdfPCell(new Phrase(strlcnmobnoflag, font));
				tbl.addCell(lcmobileflag);

				PdfPCell lblpolice = new PdfPCell(new Phrase("Nearby Police Station: ", font));
				tbl.addCell(lblpolice);
				PdfPCell police = new PdfPCell(new Phrase(strPolice, font));
				tbl.addCell(police);
				PdfPCell policeremark = new PdfPCell(new Phrase(strnpsremark, font));
				tbl.addCell(policeremark);
				PdfPCell policeflag = new PdfPCell(new Phrase(strnpsflag, font));
				tbl.addCell(policeflag);

				PdfPCell lbldistrict = new PdfPCell(new Phrase("District: ", font));
				tbl.addCell(lbldistrict);
				PdfPCell district = new PdfPCell(new Phrase(strDistrict, font));
				tbl.addCell(district);
				PdfPCell districtremark = new PdfPCell(new Phrase(strdistremark, font));
				tbl.addCell(districtremark);
				PdfPCell districtflag = new PdfPCell(new Phrase(strdistflag, font));
				tbl.addCell(districtflag);

				PdfPCell lblcounty = new PdfPCell(new Phrase("County: ", font));
				tbl.addCell(lblcounty);
				PdfPCell county = new PdfPCell(new Phrase(strCounty, font));
				tbl.addCell(county);
				PdfPCell countyremark = new PdfPCell(new Phrase(strcntremark, font));
				tbl.addCell(countyremark);
				PdfPCell countyflag = new PdfPCell(new Phrase(strcntflag, font));
				tbl.addCell(countyflag);

				PdfPCell lblsubcounty = new PdfPCell(new Phrase("Sub County: ", font));
				tbl.addCell(lblsubcounty);
				PdfPCell subcounty = new PdfPCell(new Phrase(strSubcounty, font));
				tbl.addCell(subcounty);
				PdfPCell subcountyremark = new PdfPCell(new Phrase(strscntremark, font));
				tbl.addCell(subcountyremark);
				PdfPCell subcountyflag = new PdfPCell(new Phrase(strscntflag, font));
				tbl.addCell(subcountyflag);

				PdfPCell lblparish = new PdfPCell(new Phrase("Parish: ", font));
				tbl.addCell(lblparish);
				PdfPCell parish = new PdfPCell(new Phrase(strParish, font));
				tbl.addCell(parish);
				PdfPCell parishremark = new PdfPCell(new Phrase(strparremark, font));
				tbl.addCell(parishremark);
				PdfPCell parishflag = new PdfPCell(new Phrase(strparflag, font));
				tbl.addCell(parishflag);

				PdfPCell lblVillage = new PdfPCell(new Phrase("Village: ", font));
				tbl.addCell(lblVillage);
				PdfPCell village = new PdfPCell(new Phrase(strVillage, font));
				tbl.addCell(village);
				PdfPCell villageremark = new PdfPCell(new Phrase(strvilremark, font));
				tbl.addCell(villageremark);
				PdfPCell villageflag = new PdfPCell(new Phrase(strvilflag, font));
				tbl.addCell(villageflag);

				PdfPCell lbltv = new PdfPCell(new Phrase("TV Recommendation: ", font));
				tbl.addCell(lbltv);
				PdfPCell tvremarks = new PdfPCell(new Phrase(strTv, font));
				tbl.addCell(tvremarks);
				PdfPCell tvremarksrm = new PdfPCell(new Phrase(strTvremarks, font));
				tbl.addCell(tvremarksrm);
				PdfPCell tvremarksflg = new PdfPCell(new Phrase("", font));
				tbl.addCell(tvremarksflg);

				document.add(tbl);

				document.add(new Paragraph("\n"));

				document.newPage();

				// section II Guarantor 1
				PdfPTable mdltbl = new PdfPTable(2);
				mdltbl.setWidthPercentage(100);
				mdltbl.setHorizontalAlignment(Element.ALIGN_LEFT);
				mdltbl.setWidths(new int[] { 50, 50 });
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
				table.setWidths(new int[] { 25, 25, 25, 25 });
				table.setTotalWidth(100);
				addTableHeader(table);
				// add table rows

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

				PdfPCell lblgtvremarks1 = new PdfPCell(new Phrase("TV Remarks", font));
				table.addCell(lblgtvremarks1);
				PdfPCell g1gtv = new PdfPCell(new Phrase(strTv1, font));
				table.addCell(g1gtv);
				PdfPCell g1gtvremarks = new PdfPCell(new Phrase(strTvremarks1, font));
				table.addCell(g1gtvremarks);
				PdfPCell g1gtvflag = new PdfPCell(new Phrase("", font));
				table.addCell(g1gtvflag);

				document.add(table);

				document.add(new Paragraph("\n"));
				document.add(new Paragraph("\n"));

				// section II Guarantor 2
				PdfPTable mdltbl2 = new PdfPTable(2);
				mdltbl2.setWidthPercentage(100);
				mdltbl2.setHorizontalAlignment(Element.ALIGN_LEFT);
				mdltbl2.setWidths(new int[] { 50, 50 });
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
				table2.setWidths(new int[] { 25, 25, 25, 25 });
				table2.setTotalWidth(100);
				addTableHeader(table2);
				// add table rows

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

				PdfPCell lblgtvremarks2 = new PdfPCell(new Phrase("TV Remarks", font));
				table2.addCell(lblgtvremarks2);
				PdfPCell g2gtv = new PdfPCell(new Phrase(strTv2, font));
				table2.addCell(g2gtv);
				PdfPCell g2gtvremarks = new PdfPCell(new Phrase(strTvremarks2, font));
				table2.addCell(g2gtvremarks);
				PdfPCell g2gtvflag = new PdfPCell(new Phrase("", font));
				table2.addCell(g2gtvflag);

				document.add(table2);

				// ***************************************************************************************************

				document.close();

				// document.open();
				InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

				// Upload to S3 bucket
				AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
				String keyName = custId + "TeleVerify.xls";

				metadata.setContentLength(outputStream.size());
				metadata.setContentType("application/pdf");

				s3client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, metadata));

				java.util.Date expiration = new java.util.Date();
				long expTimeMillis = expiration.getTime();
				expTimeMillis += 1000 * 60 * 60; // 1 hour expiration time
				expiration.setTime(expTimeMillis);

				GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
						keyName).withMethod(HttpMethod.GET).withExpiration(expiration);

				url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

//				    session.setAttribute("tvprofid", ""); //Clear session tracker only after printing tv

			} catch (Exception e) {
				System.out.println("Error printing payment schedule");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		return ResponseEntity.ok(url.toString());
	}

	private void addCustTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);

		Stream.of("Field Name", "Values", "TV Remarks", "Matching (true/false)").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase(columnTitle, hdrfont));
			table.addCell(header);
		});
	}

	private void addTableHeader(PdfPTable table) {
		Font hdrfont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);

		Stream.of("Field Name", "Values", "TV Remarks", "Matching (true/false)").forEach(columnTitle -> {
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
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}
}
