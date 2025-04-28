package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.CustomerVehicle;
import com.ozone.smart.entity.FiVerification;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.TvVerification;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.CustomerVehicleRepo;
import com.ozone.smart.repository.FiVerifyRepo;
import com.ozone.smart.repository.LoanEntryRepo;
import com.ozone.smart.repository.TeleVerifyRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private TeleVerifyRepo teleVerifyRepo;

	@Autowired
	private FiVerifyRepo fiVerifyRepo;

	@Autowired
	private CustomerVehicleRepo custVehRepo;

	@Autowired
	private LoanEntryRepo loanRepo;

	public Response<String> saveCustomer(CustomerDetailsDto customerDetailsDto) {

		TimeStampUtil timeStamp = new TimeStampUtil();
		String Requestdatetime = timeStamp.TimeStamp();
		boolean Noka = customerDetailsDto.isNokagreeing();
		boolean blnNoka;
		if (Noka) {
			blnNoka = true;
		} else {
			blnNoka = false;
		}

		CustomerDetails custdet = new CustomerDetails();
		String custsurname = customerDetailsDto.getSurname();
		custdet.setDob(customerDetailsDto.getDob());
		custdet.setFirstname(customerDetailsDto.getFirstname());
		custdet.setOthername(customerDetailsDto.getOthername());
		custdet.setSurname(customerDetailsDto.getSurname());
		custdet.setCapturedatetime(Requestdatetime);
		custdet.setRemarks("User 1");
		custdet.setMaritalstatus(customerDetailsDto.getMaritalstatus());
		custdet.setSex(customerDetailsDto.getSex());
		custdet.setMobileno(customerDetailsDto.getMobileno());
		custdet.setStage(customerDetailsDto.getStage());
		custdet.setStagechairman(customerDetailsDto.getStagechairman());
		custdet.setStagechairmanno(customerDetailsDto.getStagechairmanno());
		custdet.setCurrentbikeregno(customerDetailsDto.getCurrentbikeregno());
		custdet.setNewbikeuse(customerDetailsDto.getNewbikeuse());
		custdet.setDistrict(customerDetailsDto.getDistrict());
		custdet.setCounty(customerDetailsDto.getCounty());
		custdet.setSubcounty(customerDetailsDto.getSubcounty());
		custdet.setParish(customerDetailsDto.getParish());
		custdet.setVillage(customerDetailsDto.getVillage());
		custdet.setNationalid(customerDetailsDto.getNationalid());
		custdet.setYearsinvillage(customerDetailsDto.getYearsinvillage());
		custdet.setNextofkin(customerDetailsDto.getNextofkin());
		custdet.setNokmobileno(customerDetailsDto.getNokmobileno());
		custdet.setNokrelationship(customerDetailsDto.getNokrelationship());
		custdet.setNokagreeing(blnNoka);
		custdet.setDrivingpermit(customerDetailsDto.getDrivingpermit());
		custdet.setNationality(customerDetailsDto.getNationality());
		custdet.setNoofdependants(customerDetailsDto.getNoofdependants());
		custdet.setOwnhouserented(customerDetailsDto.getOwnhouserented());
		custdet.setLandlordname(customerDetailsDto.getLandlordname());
		custdet.setLandlordmobileno(customerDetailsDto.getLandlordmobileno());
		custdet.setRentpm(customerDetailsDto.getRentpm());
		custdet.setOtherincomesource(customerDetailsDto.getOtherincomesource());
		custdet.setDownpaymentsource(customerDetailsDto.getDownpaymentsource());
		custdet.setPermanentaddress(customerDetailsDto.getPermanentaddress());
		custdet.setFathersname(customerDetailsDto.getFathersname());
		custdet.setMothersname(customerDetailsDto.getMothersname());
		custdet.setNearbypolicestation(customerDetailsDto.getNearbypolicestation());
		custdet.setCusttype(customerDetailsDto.getCusttype());
		custdet.setLlrentfeedback(customerDetailsDto.getLlrentfeedback());
		custdet.setNoyrsinarea(customerDetailsDto.getNoyrsinarea());
		custdet.setLc1chmnrecfeed(customerDetailsDto.getLc1chmnrecfeed());
		custdet.setNearlmarkresi(customerDetailsDto.getNearlmarkresi());
		custdet.setEmptype(customerDetailsDto.getEmptype());
		custdet.setStgorwrkadrssnearlmark(customerDetailsDto.getStgorwrkadrssnearlmark());
		custdet.setStgoremprecm(customerDetailsDto.getStgoremprecm());
		custdet.setNoofyrsinstgorbusi(customerDetailsDto.getNoofyrsinstgorbusi());
		custdet.setStgnoofvehi(customerDetailsDto.getStgnoofvehi());
		custdet.setBikeowner(customerDetailsDto.getBikeowner());
		custdet.setNetincome(customerDetailsDto.getNetincome());
		custdet.setBikeusearea(customerDetailsDto.getBikeusearea());
		custdet.setSpousename(customerDetailsDto.getSpousename());
		custdet.setSpouseno(customerDetailsDto.getSpouseno());
		custdet.setSpouseconfirm(customerDetailsDto.getSpouseconfirm());
		custdet.setOffcdistance(customerDetailsDto.getOffcdistance());

		customerRepo.save(custdet);
		Response<String> msg = new Response<String>();
		msg.setData("Customer" + custsurname + "Successfully Added");
		return msg;
	}

	// otherid not a customerid
	public Response<CustomerDetailsDto> viewCustomer(String id, String flag) {

		Response<CustomerDetailsDto> response = new Response<>();
		if (id == null || id.length() == 0) {
			response.setErrorMsg("Select customer id");
		} else {
			id = id.substring(id.length() - 6);
			System.out.println(id);
			try {
				CustomerDetails customerDetails = customerRepo.findCustomerByFlag(id, flag);
				if (customerDetails != null) {
					System.out.println(customerDetails.getId());
					String strTv;
					String strFi;
					String strCqc;
					boolean blnTv = false;
					boolean blnFi = false;
					boolean blnCqc = false;
					blnTv = customerDetails.getTvverified();
					blnFi = customerDetails.getFiverified();
					blnCqc = customerDetails.getCqc();

					CustomerDetailsDto cdd = new CustomerDetailsDto();
					cdd.setDob(customerDetails.getDob());
					cdd.setFirstname(customerDetails.getFirstname());
					cdd.setOthername(customerDetails.getOthername());
					cdd.setSurname(customerDetails.getSurname());
					cdd.setRemarks(customerDetails.getRemarks());
					cdd.setMaritalstatus(customerDetails.getMaritalstatus());
					cdd.setSex(customerDetails.getSex());
					cdd.setMobileno(customerDetails.getMobileno());
					cdd.setStage(customerDetails.getStage());
					cdd.setStagechairman(customerDetails.getStagechairman());
					cdd.setStagechairmanno(customerDetails.getStagechairmanno());
					cdd.setCurrentbikeregno(customerDetails.getCurrentbikeregno());
					cdd.setNewbikeuse(customerDetails.getNewbikeuse());
					cdd.setDistrict(customerDetails.getDistrict());
					cdd.setCounty(customerDetails.getCounty());
					cdd.setSubcounty(customerDetails.getSubcounty());
					cdd.setParish(customerDetails.getParish());
					cdd.setVillage(customerDetails.getVillage());
					cdd.setNationalid(customerDetails.getNationalid());
					cdd.setYearsinvillage(customerDetails.getYearsinvillage());
					cdd.setNextofkin(customerDetails.getNextofkin());
					cdd.setNokmobileno(customerDetails.getNokmobileno());
					cdd.setNokagreeing(customerDetails.getNokagreeing());
					cdd.setNokrelationship(customerDetails.getNokrelationship());
					cdd.setDrivingpermit(customerDetails.getDrivingpermit());
					cdd.setNationality(customerDetails.getNationality());
					cdd.setNoofdependants(customerDetails.getNoofdependants());
					cdd.setOwnhouserented(customerDetails.getOwnhouserented());
					cdd.setLandlordname(customerDetails.getLandlordname());
					cdd.setLandlordmobileno(customerDetails.getLandlordmobileno());
					cdd.setRentpm(customerDetails.getRentpm());
					cdd.setOtherincomesource(customerDetails.getOtherincomesource());
					cdd.setDownpaymentsource(customerDetails.getDownpaymentsource());
					cdd.setPermanentaddress(customerDetails.getPermanentaddress());
					cdd.setFathersname(customerDetails.getFathersname());
					cdd.setMothersname(customerDetails.getMothersname());
					cdd.setNearbypolicestation(customerDetails.getNearbypolicestation());
					cdd.setLc(customerDetails.getLc());
					cdd.setLcmobileno(customerDetails.getLcmobileno());
					cdd.setCusttype(customerDetails.getCusttype());
					cdd.setTvremarks(customerDetails.getTvremarks());
					cdd.setFiremarks(customerDetails.getFiremarks());
					cdd.setCqcremarks(customerDetails.getCqcremarks());
					cdd.setCoremarks(customerDetails.getCoremarks());
					cdd.setCoapproval(customerDetails.getCoapproval());
					cdd.setTvverified(customerDetails.getTvverified());
					cdd.setFiverified(customerDetails.getFiverified());
					cdd.setCqc(customerDetails.getCqc());
					cdd.setLlrentfeedback(customerDetails.getLlrentfeedback());
					cdd.setNoyrsinarea(customerDetails.getNoyrsinarea());
					cdd.setLc1chmnrecfeed(customerDetails.getLc1chmnrecfeed());
					cdd.setNearlmarkresi(customerDetails.getNearlmarkresi());
					cdd.setEmptype(customerDetails.getEmptype());
					cdd.setStgorwrkadrssnearlmark(customerDetails.getStgorwrkadrssnearlmark());
					cdd.setStgoremprecm(customerDetails.getStgoremprecm());
					cdd.setNoofyrsinstgorbusi(customerDetails.getNoofyrsinstgorbusi());
					cdd.setStgnoofvehi(customerDetails.getStgnoofvehi());
					cdd.setBikeowner(customerDetails.getBikeowner());
					cdd.setNetincome(customerDetails.getNetincome());
					cdd.setBikeusearea(customerDetails.getBikeusearea());
					cdd.setSpousename(customerDetails.getSpousename());
					cdd.setSpouseno(customerDetails.getSpouseno());
					cdd.setSpouseconfirm(customerDetails.getSpouseconfirm());
					cdd.setOffcdistance(customerDetails.getOffcdistance());
//					cdd.setRelawithapplicant(customerDetails.getRelawithapplicant());
					cdd.setPaymentbyrider(customerDetails.getPaymentbyrider());
					cdd.setYakanum(customerDetails.getYakanum());
					cdd.setYakanumname(customerDetails.getYakanumname());
					cdd.setPaymtdetailstovby(customerDetails.getPaymtdetailstovby());
					cdd.setCashpaymntworeceipt(customerDetails.getCashpaymntworeceipt());
					cdd.setApplicantknowvby(customerDetails.getApplicantknowvby());
					cdd.setRelawithguarantors(customerDetails.getRelawithguarantors());
					cdd.setArrangebtwnrider(customerDetails.getArrangebtwnrider());
					cdd.setResiadrss(customerDetails.getResiadrss());
					cdd.setNoofyrinaddrss(customerDetails.getNoofyrinaddrss());
					cdd.setMbnonotinname(customerDetails.getMbnonotinname());

					String Revremarks = customerDetails.getRevremarks();
					if (Revremarks == null) {
						Revremarks = "";
					}
					cdd.setRevremarks(Revremarks);

					System.out.println("I have a revremarks" + Revremarks);

					String srremarks = customerDetails.getSrremarks();
					if (srremarks == null) {
						srremarks = "";
					}
					cdd.setSrremarks(srremarks);

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

					cdd.setTv(strTv);
					cdd.setFi(strFi);
					cdd.setCqcr(strCqc);
					response.setData(cdd);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error retrieving customer id: " + id);
			}
		}
		return response;
	}

	public Response<List<CustomerDetailsDto>> viewCustomers() {
		Response<List<CustomerDetailsDto>> response = new Response<>();
		List<CustomerDetails> cdList = customerRepo.findAll();
		List<CustomerDetailsDto> cddList = new ArrayList<>();
		for (CustomerDetails cd : cdList) {
			CustomerDetailsDto cdd = new CustomerDetailsDto();
			cdd.setFirstname(cd.getFirstname());
			cdd.setSurname(cd.getSurname());
			cdd.setOtherid(cd.getOtherid());
			cddList.add(cdd);
		}
		response.setData(cddList);
		return response;
	}

	public Response<String> viewCustomerStatus(String custId) {

		String strCustid = "";
		String strFlag = "";
		String strMsg = "";
		String strMsg1 = "";
		String strMsg2 = "";

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
		String strstgchmnnameremark = "";
		String strstgchmnnameflag = "";
		String strstgchmnnoremark = "";
		String strstgchmnnoflag = "";
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
		String strstagechairmanremarks = "";
		String strstagechairmanflag = "";
		String strstagechairmanmbnoremarks = "";
		String strstagechairmanmbnoflag = "";

		String strtvllrentfeedbackremark = "";
		String strtvllrentfeedbackflag = "";
		String strtvnoyrsinarearemark = "";
		String strtvnoyrsinareaflag = "";
		String strtvlc1chmnrecfeedremark = "";
		String strtvlc1chmnrecfeedflag = "";
		String strtvnearlmarkresiremark = "";
		String strtvnearlmarkresiflag = "";
		String strtvtvemptyremark = "";
		String strtvemptypeflag = "";
		String strtvstgorwrkadrssnearlmarkremark = "";
		String strtvstgorwrkadrssnearlmarkflag = "";
		String strtvstgoremprecmremark = "";
		String strtvstgoremprecmflag = "";
		String strtvnoofyrsinstgorbusiremark = "";
		String strtvnoofyrsinstgorbuisflag = "";
		String strtvstgnoofvehiremark = "";
		String strtvstgnoofvehiflag = "";
		String strtvbikeownerremark = "";
		String strtvbikeownerflag = "";
		String strtvnetincomeremark = "";
		String strtvnetincomeflag = "";
		String strtvbikeusearearemark = "";
		String strtvbikeuseareaflag = "";
		String strtvspousenameremark = "";
		String strtvspousenameflag = "";
		String strtvspousenoremark = "";
		String strtvspousenoflag = "";
		String strtvspouseconfirmremark = "";
		String strtvspouseconfirmflag = "";
		String strtvoffcdistanceremark = "";
		String strtvoffcdistanceflag = "";
		String strtvrelawithapplicantremark = "";
		String strtvrelawithapplicantflag = "";
		String strtvpaymentbyriderremark = "";
		String strtvpaymentbyriderflag = "";
		String strtvyakanumremark = "";
		String strtvyakanumflag = "";
		String strtvyakanumnameremark = "";
		String strtvyakanumnamflag = "";
		String strtvpaymtdetailstovbyremark = "";
		String strtvpaymtdetailstovbyflag = "";
		String strtvcashpaymntworeceiptremark = "";
		String strtvcashpaymntworeceiptflag = "";
		String strtvapplicantknowvbyremark = "";
		String strtvapplicantknowvbyflag = "";
		String strtvrelawithguarantorsremark = "";
		String strtvrelawithguarantorsflag = "";

		String strtvbikeappliedremark = "";
		String strtvbikeappliedflag = "";

		String strtvdownpaymentremark = "";
		String strtvdownpaymentflag = "";

		String strtvtenureremark = "";
		String strtvtenureflag = "";

		String strtvewioremiremark = "";
		String strtvewioremiflag = "";

		String strtvarrangebtwnriderremark = "";
		String strtvarrangebtwnriderflag = "";

		String strtvresiadrssremarks = "";
		String strtvresiadrssflag = "";

		String strtvnoofyrsinaddrsremarks = "";
		String strtvnoofyrsinaddrsflag = "";

		String strtvmbnumnotinnameremarks = "";
		String strtvmbnumnotinnameflag = "";

		String strfiSurnameremark = "";
		String strfiSurnameflag = "";
		String strfifnameremark = "";
		String strfifnameflag = "";
		String strfionameremark = "";
		String strfionameflag = "";
		String strfimsremark = "";
		String strfimsflag = "";
		String strfisexremark = "";
		String strfisexflag = "";
		String strfimobnoremark = "";
		String strfimobnoflag = "";
		String strfistgnmremark = "";
		String strfistgnmflag = "";
		String strfistgchmnnameremark = "";
		String strfistgchmnnameflag = "";
		String strfistgchmnnoremark = "";
		String strfistgchmnnoflag = "";
		String strfidistremark = "";
		String strfidistflag = "";
		String strfilcnmremark = "";
		String strfilcnmflag = "";
		String strfiparremark = "";
		String strfiparflag = "";
		String strfinidremark = "";
		String strfinidflag = "";
		String strfibregnoremark = "";
		String strfibregnoflag = "";
		String strfibuseremark = "";
		String strfibuseflag = "";
		String strfidobremark = "";
		String strfidobflag = "";
		String strficntremark = "";
		String strficntflag = "";
		String strfiscntremark = "";
		String strfiscntflag = "";
		String strfivilremark = "";
		String strfivilflag = "";
		String strfiyrvilremark = "";
		String strfiyrvilflag = "";
		String strfinokremark = "";
		String strfinokflag = "";
		String strfinokmremark = "";
		String strfinokmflag = "";
		String strfinokrremark = "";
		String strfinokrflag = "";
		String strfinokaremark = "";
		String strfinokaflag = "";
		String strfidlremark = "";
		String strfidlflag = "";
		String strfinatremark = "";
		String strfinatflag = "";
		String strfinodremark = "";
		String strfinodflag = "";
		String strfiorremark = "";
		String strfiorflag = "";
		String strfillremark = "";
		String strfillflag = "";
		String strfillmobremark = "";
		String strfillmobflag = "";
		String strfirpmremark = "";
		String strfirpmflag = "";
		String strfioisremark = "";
		String strfioisflag = "";
		String strfidpsremark = "";
		String strfidpsflg = "";
		String strfipaddremark = "";
		String strfipaddflag = "";
		String strfifathremark = "";
		String strfifathflag = "";
		String strfimothremark = "";
		String strfimothflag = "";
		String strfinpsremark = "";
		String strfinpsflag = "";
		String strfilcnmobnoremark = "";
		String strfilcnmobnoflag = "";
		String strficusttyperemark = "";
		String strficusttypeflag = "";
		String strfistagechairmanremarks = "";
		String strfistagechairmanflag = "";
		String strfistagechairmanmbnoremarks = "";
		String strfistagechairmanmbnoflag = "";

		String strfillrentfeedbackremark = "";
		String strfillrentfeedbackflag = "";
		String strfinoyrsinarearemark = "";
		String strfinoyrsinareaflag = "";
		String strfilc1chmnrecfeedremark = "";
		String strfilc1chmnrecfeedflag = "";
		String strfinearlmarkresiremark = "";
		String strfinearlmarkresiflag = "";
		String strfiemptyremark = "";
		String strfiemptypeflag = "";
		String strfistgorwrkadrssnearlmarkremark = "";
		String strfistgorwrkadrssnearlmarkflag = "";
		String strfistgoremprecmremark = "";
		String strfistgoremprecmflag = "";
		String strfinoofyrsinstgorbusiremark = "";
		String strfinoofyrsinstgorbuisflag = "";
		String strfistgnoofvehiremark = "";
		String strfistgnoofvehiflag = "";
		String strfibikeownerremark = "";
		String strfibikeownerflag = "";
		String strfinetincomeremark = "";
		String strfinetincomeflag = "";
		String strfibikeusearearemark = "";
		String strfibikeuseareaflag = "";
		String strfispousenameremark = "";
		String strfispousenameflag = "";
		String strfispousenoremark = "";
		String strfispousenoflag = "";
		String strfispouseconfirmremark = "";
		String strfispouseconfirmflag = "";
		String strfioffcdistanceremark = "";
		String strfioffcdistanceflag = "";
		String strfirelawithapplicantremark = "";
		String strfirelawithapplicantflag = "";
		String strfipaymentbyriderremark = "";
		String strfipaymentbyriderflag = "";
		String strfiyakanumremark = "";
		String strfiyakanumflag = "";
		String strfiyakanumnameremark = "";
		String strfiyakanumnamflag = "";
		String strfipaymtdetailstovbyremark = "";
		String strfipaymtdetailstovbyflag = "";
		String strficashpaymntworeceiptremark = "";
		String strficashpaymntworeceiptflag = "";
		String strfiapplicantknowvbyremark = "";
		String strfiapplicantknowvbyflag = "";
		String strfirelawithguarantorsremark = "";
		String strfirelawithguarantorsflag = "";

		String strfibikeappliedremark = "";
		String strfibikeappliedflag = "";

		String strfidownpaymentremark = "";
		String strfidownpaymentflag = "";

		String strfitenureremark = "";
		String strfitenureflag = "";

		String strfiewioremiremark = "";
		String strfiewioremiflag = "";

		String strfiarrangebtwnriderremark = "";
		String strfiarrangebtwnriderflag = "";

		String strfiresiadrssremarks = "";
		String strfiresiadrssflag = "";

		String strfinoofyrsinaddrsremarks = "";
		String strfinoofyrsinaddrsflag = "";

		String strfimbnumnotinnameremarks = "";
		String strfimbnumnotinnameflag = "";

		strCustid = custId;
//		strFlag = flag;
		Response<String> response = new Response<>();

		if (strCustid == null || strCustid.length() == 0) {
			strMsg = "Select customer id";
			response.setData(strMsg);
		} else {
//			if (strFlag.equals("cqc")) {
//				strFlag = " and cqc != true";
//			} else if (strFlag.equals("fi")) {
//				strFlag = " and fiverified != true";
//			} else if (strFlag.equals("tv")) {
//				strFlag = " and tvverified != true";
//			} else if (strFlag.equals("cam")) {
//				strFlag = "";
//			} else if (strFlag.equals("cama")) {
//				strFlag = "";
//			} else if (strFlag.equals("all")) {
//				strFlag = "";
//			}
//			@Query("SELECT cd FROM CustomerDetails cd WHERE cd.id = :customerId AND (:flag IS NULL OR "
//		            + "(:flag = 'cqc' AND cd.cqc != true) OR "
//		            + "(:flag = 'fi' AND cd.fiverified != true) OR "
//		            + "(:flag = 'tv' AND cd.tvverified != true) OR "
//		            + "(:flag = 'cam' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true) OR "
//		            + "(:flag = 'cama' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true AND cd.coapproval = 'Recommended') OR "
//		            + "(:flag = 'all'))")
//		    CustomerDetails findCustomerByFlag(@Param("customerId") String customerId, @Param("flag") String flag);

			try {
				List<TvVerification> televerification = teleVerifyRepo.findTvVerificationByCust(strCustid);

				for (TvVerification tv : televerification) {

					strSurnameremark = tv.getSurnameremark();
					strSurnameflag = tv.getSurnameflag();
//					if (strSurnameflag.equals("false")) {
//						strSurnameflag = "";
//					}
					System.out.println("strSurnameremark ==== "+strSurnameremark +" & " + strSurnameflag);
					strfnameremark = tv.getFirstnameremark();
					strfnameflag = tv.getFirstnameflag();
//					if (strfnameflag.equals("false")) {
//						strfnameflag = "";
//					}
					stronameremark = tv.getOthernameremark();
					stronameflag = tv.getOthernameflag();
//					if (stronameflag.equals("false")) {
//						stronameflag = "";
//					}
					strmsremark = tv.getMaritalstatusremark();
					strmsflag = tv.getMaritalstatusflag();
//					if (strmsflag.equals("false")) {
//						strmsflag = "";
//					}
					strsexremark = tv.getSexremark();
					strsexflag = tv.getSexflag();
//					if (strsexflag.equals("false")) {
//						strsexflag = "";
//					}
					strmobnoremark = tv.getMobilenoremark();
					strmobnoflag = tv.getMobilenoflag();
//					if (strmobnoflag.equals("false")) {
//						strmobnoflag = "";
//					}
					strstgnmremark = tv.getStageremark();
					strstgnmflag = tv.getStageflag();
//					if (strstgnmflag.equals("false")) {
//						strstgnmflag = "";
//					}
					strstgchmnnameremark = tv.getStagechairmannameremarks();
					strstgchmnnameflag = tv.getStagechairmannameflag();
//					System.out.println("strstgchmnnameflag >>> " + strstgchmnnameflag);
//					if (strstgchmnnameflag.equals("false") || strstgchmnnameflag.equals(null)
//							|| strstgchmnnameflag == "null") {
//						strstgchmnnameflag = "";
//					}
					strstgchmnnoremark = tv.getStagechairmannoremarks();
					strstgchmnnoflag = tv.getStagechairmannoflag();
//					if (strstgchmnnoflag.equals("false") || strstgchmnnoflag.equals(null) ) {
//						strstgchmnnoflag = "";
//					}
					strdistremark = tv.getDistrictremark();
					strdistflag = tv.getDistrictflag();
//					if (strdistflag.equals("false")) {
//						strdistflag = "";
//					}
					strlcnmremark = tv.getLcremark();
					strlcnmflag = tv.getLcflag();
					if (strlcnmflag.equals("false")) {
						strlcnmflag = "";
					}
					strparremark = tv.getParishremark();
					strparflag = tv.getParishflag();
//					if (strparflag.equals("false")) {
//						strparflag = "";
//					}
					strnidremark = tv.getNationalidremark();
					strnidflag = tv.getNationalidflag();
//					if (strnidflag.equals("false")) {
//						strnidflag = "";
//					}
					strbregnoremark = tv.getBikeregnoremark();
					strbregnoflag = tv.getBikeregnoflag();
//					if (strbregnoflag.equals("false")) {
//						strbregnoflag = "";
//					}
					strbuseremark = tv.getBikeuseremark();
					strbuseflag = tv.getBikeuseflag();
//					if (strbuseflag.equals("false")) {
//						strbuseflag = "";
//					}
					strdobremark = tv.getDobremark();
					strdobflag = tv.getDobflag();
//					if (strdobflag.equals("false")) {
//						strdobflag = "";
//					}
					strcntremark = tv.getCountyremark();
					strcntflag = tv.getCountyflag();
//					if (strcntflag.equals("false")) {
//						strcntflag = "";
//					}
					strscntremark = tv.getSubcountyremark();
					strscntflag = tv.getSubcountyflag();
//					if (strscntflag.equals("false")) {
//						strscntflag = "";
//					}
					strvilremark = tv.getVillageremark();
					strvilflag = tv.getVillageflag();
//					if (strvilflag.equals("false")) {
//						strvilflag = "";
//					}
					stryrvilremark = tv.getYearsinvillageremark();
					stryrvilflag = tv.getYearsinvillageflag();
//					if (stryrvilflag.equals("false")) {
//						stryrvilflag = "";
//					}
					strnokremark = tv.getNextofkinremark();
					strnokflag = tv.getNextofkinflag();
//					if (strnokflag.equals("false")) {
//						strnokflag = "";
//					}
					strnokmremark = tv.getNokmobilenoremark();
					strnokmflag = tv.getNokmobilenoflag();
//					if (strnokmflag.equals("false")) {
//						strnokmflag = "";
//					}
					strnokrremark = tv.getNokrelationshipremark();
					strnokrflag = tv.getNokrelationshipflag();
//					if (strnokrflag.equals("false")) {
//						strnokrflag = "";
//					}
					strnokaremark = tv.getNokagreeingremark();
					strnokaflag = tv.getNokagreeingflag();
//					if (strnokaflag.equals("false")) {
//						strnokaflag = "";
//					}
					strdlremark = tv.getDrivingpermitremark();
					strdlflag = tv.getDrivingpermitflag();
//					if (strdlflag.equals("false")) {
//						strdlflag = "";
//					}
					strnatremark = tv.getNationalityremark();
					strnatflag = tv.getNationalityflag();
//					if (strnatflag.equals("false")) {
//						strnatflag = "";
//					}
					strnodremark = tv.getNoofdependantsremark();
					strnodflag = tv.getNoofdependantsflag();
//					if (strnodflag.equals("false")) {
//						strnodflag = "";
//					}
					strorremark = tv.getOwnhouserentedremark();
					strorflag = tv.getOwnhouserentedflag();
//					if (strorflag.equals("false")) {
//						strorflag = "";
//					}
					strllremark = tv.getLandlordnameremark();
					strllflag = tv.getLandlordnameflag();
//					if (strllflag.equals("false")) {
//						strllflag = "";
//					}
					strllmobremark = tv.getLandlordmobilenoremark();
					strllmobflag = tv.getLandlordmobilenoflag();
//					if (strllmobflag.equals("false")) {
//						strllmobflag = "";
//					}
					strrpmremark = tv.getRentpmremark();
					strrpmflag = tv.getRentpmflag();
//					if (strrpmflag.equals("false")) {
//						strrpmflag = "";
//					}
					stroisremark = tv.getOtherincomesourceremark();
					stroisflag = tv.getOtherincomesourceflag();
//					if (stroisflag.equals("false")) {
//						stroisflag = "";
//					}
					strdpsremark = tv.getDownpaymentsourceremark();
					strdpsflg = tv.getDownpaymentsourceflag();
//					if (strdpsflg.equals("false")) {
//						strdpsflg = "";
//					}
					strpaddremark = tv.getPermanentaddressremark();
					strpaddflag = tv.getPermanentaddressflag();
//					if (strpaddflag.equals("false")) {
//						strpaddflag = "";
//					}
					strfathremark = tv.getFathersnameremark();
					strfathflag = tv.getFathersnameflag();
//					if (strfathflag.equals("false")) {
//						strfathflag = "";
//					}
					strmothremark = tv.getMothersnameremark();
					strmothflag = tv.getMothersnameflag();
//					if (strmothflag.equals("false")) {
//						strmothflag = "";
//					}
					strnpsremark = tv.getNearbypolicestationremark();
					strnpsflag = tv.getNearbypolicestationflag();
//					if (strnpsflag.equals("false")) {
//						strnpsflag = "";
//					}
					strlcnmobnoremark = tv.getLcmobilenoremark();
					strlcnmobnoflag = tv.getLcmobilenoflag();
//					if (strlcnmobnoflag.equals("false")) {
//						strlcnmobnoflag = "";
//					}
					strcusttyperemark = tv.getCusttyperemark();
					strcusttypeflag = tv.getCusttypeflag();
//					if (strcusttypeflag.equals("false")) {
//						strcusttypeflag = "";
//					}
					strstagechairmanremarks = tv.getStagechairmannameremarks();
					strstagechairmanflag = tv.getStagechairmannameflag();
//					if (strstagechairmanflag.equals("false")) {
//						strstagechairmanflag = "";
//					}
					strstagechairmanmbnoremarks = tv.getStagechairmannoremarks();
					strstagechairmanmbnoflag = tv.getStagechairmannoflag();
//					if (strstagechairmanmbnoflag.equals("false")) {
//						strstagechairmanmbnoflag = "";
//					}

					////
					strtvllrentfeedbackremark = tv.getLlrentfeedbackremarks();
					strtvllrentfeedbackflag = tv.getLlrentfeedbackflag();
//					if (strtvllrentfeedbackflag.equals("false")) {
//						strtvllrentfeedbackflag = "";
//					}
					strtvnoyrsinarearemark = tv.getNoyrsinarearemarks();
					strtvnoyrsinareaflag = tv.getNoyrsinareaflag();
//					if (strtvnoyrsinareaflag.equals("false")) {
//						strtvnoyrsinareaflag = "";
//					}
					strtvlc1chmnrecfeedremark = tv.getLc1chmnrecfeedremarks();
					strtvlc1chmnrecfeedflag = tv.getLc1chmnrecfeedflag();
//					if (strtvlc1chmnrecfeedflag.equals("false")) {
//						strtvlc1chmnrecfeedflag = "";
//					}
					strtvnearlmarkresiremark = tv.getNearlmarkresiremarks();
					strtvnearlmarkresiflag = tv.getNearlmarkresiflag();
//					if (strtvnearlmarkresiflag.equals("false")) {
//						strtvnearlmarkresiflag = "";
//					}

					strtvtvemptyremark = tv.getEmptyperemarks();
					strtvemptypeflag = tv.getEmptypeflag();
//					if (strtvemptypeflag.equals("false")) {
//						strtvemptypeflag = "";
//					}

					strtvstgorwrkadrssnearlmarkremark = tv.getStgorwrkadrssnearlmarkremarks();
					strtvstgorwrkadrssnearlmarkflag = tv.getStgorwrkadrssnearlmarkflag();
//					if (strtvstgorwrkadrssnearlmarkflag.equals("false")) {
//						strtvstgorwrkadrssnearlmarkflag = "";
//					}

					strtvstgoremprecmremark = tv.getStgoremprecmremarks();
					strtvstgoremprecmflag = tv.getStgoremprecmflag();
//					if (strtvstgoremprecmflag.equals("false")) {
//						strtvstgoremprecmflag = "";
//					}

					strtvnoofyrsinstgorbusiremark = tv.getNoofyrsinstgorbusiremarks();
					strtvnoofyrsinstgorbuisflag = tv.getNoofyrsinstgorbuisflag();
//					if (strtvnoofyrsinstgorbuisflag.equals("false")) {
//						strtvnoofyrsinstgorbuisflag = "";
//					}

					strtvstgnoofvehiremark = tv.getStgnoofvehiremarks();
					strtvstgnoofvehiflag = tv.getStgnoofvehiflag();
//					if (strtvstgnoofvehiflag.equals("false")) {
//						strtvstgnoofvehiflag = "";
//					}

					strtvbikeownerremark = tv.getOwnerofbikeremarks();
					strtvbikeownerflag = tv.getOwnerofbikeflag();
//					if (strtvbikeownerflag.equals("false")) {
//						strtvbikeownerflag = "";
//					}

					strtvnetincomeremark = tv.getNetincomeremarks();
					strtvnetincomeflag = tv.getNetincomeflag();
//					if (strtvnetincomeflag.equals("false")) {
//						strtvnetincomeflag = "";
//					}
					strtvbikeusearearemark = tv.getBikeusearearemarks();
					strtvbikeuseareaflag = tv.getBikeuseareaflag();
//					if (strtvbikeuseareaflag.equals("false")) {
//						strtvbikeuseareaflag = "";
//					}

					//
					strtvspousenameremark = tv.getSpousenameremarks();
					strtvspousenameflag = tv.getSpousenameflag();
//					if (strtvspousenameflag.equals("false")) {
//						strtvspousenameflag = "";
//					}

					strtvspousenoremark = tv.getSpousenoremarks();
					strtvspousenoflag = tv.getSpousenoflag();
//					if (strtvspousenoflag.equals("false")) {
//						strtvspousenoflag = "";
//					}

					strtvspouseconfirmremark = tv.getSpouseconfirmremarks();
					strtvspouseconfirmflag = tv.getSpouseconfirmflag();
//					if (strtvspouseconfirmflag.equals("false")) {
//						strtvspouseconfirmflag = "";
//					}

					strtvoffcdistanceremark = tv.getOffcdistanceremarks();
					strtvoffcdistanceflag = tv.getOffcdistanceflag();
//					if (strtvoffcdistanceflag.equals("false")) {
//						strtvoffcdistanceflag = "";
//					}

					strtvrelawithapplicantremark = tv.getRelawithapplicantremarks();
					strtvrelawithapplicantflag = tv.getRelawithapplicantflag();
//					if (strtvrelawithapplicantflag.equals("false")) {
//						strtvrelawithapplicantflag = "";
//					}

					strtvpaymentbyriderremark = tv.getPaymentbyriderremarks();
					strtvpaymentbyriderflag = tv.getPaymentbyriderflag();
//					if (strtvpaymentbyriderflag.equals("false")) {
//						strtvpaymentbyriderflag = "";
//					}

					strtvyakanumremark = tv.getYakanumremarks();
					strtvyakanumflag = tv.getYakanumflag();
//					if (strtvyakanumflag.equals("false")) {
//						strtvyakanumflag = "";
//					}

					strtvyakanumnameremark = tv.getYakanumnameremarks();
					strtvyakanumnamflag = tv.getYakanumnamflag();
//					if (strtvyakanumnamflag.equals("false")) {
//						strtvyakanumnamflag = "";
//					}

					strtvpaymtdetailstovbyremark = tv.getPaymtdetailstovbyremarks();
					strtvpaymtdetailstovbyflag = tv.getPaymtdetailstovbyflag();
//					if (strtvpaymtdetailstovbyflag.equals("false")) {
//						strtvpaymtdetailstovbyflag = "";
//					}
					strtvcashpaymntworeceiptremark = tv.getCashpaymntworeceiptremarks();
					strtvcashpaymntworeceiptflag = tv.getCashpaymntworeceiptflag();
//					if (strtvcashpaymntworeceiptflag.equals("false")) {
//						strtvcashpaymntworeceiptflag = "";
//					}

					strtvapplicantknowvbyremark = tv.getApplicantknowvbyremarks();
					strtvapplicantknowvbyflag = tv.getApplicantknowvbyflag();
//					if (strtvapplicantknowvbyflag.equals("false")) {
//						strtvapplicantknowvbyflag = "";
//					}
					strtvrelawithguarantorsremark = tv.getRelawithguarantorsremarks();
					strtvrelawithguarantorsflag = tv.getRelawithguarantorsflag();
//					if (strtvrelawithguarantorsflag.equals("false")) {
//						strtvrelawithguarantorsflag = "";
//					}

					strtvbikeappliedremark = tv.getBikeappliedremarks();
					strtvbikeappliedflag = tv.getBikeappliedflag();
//					if (strtvbikeappliedflag.equals("false") || strstgchmnnameflag.equals(null)) {
//						strtvbikeappliedflag = "";
//					}

					strtvdownpaymentremark = tv.getDownpaymentremarks();
					strtvdownpaymentflag = tv.getDownpaymentflag();
//					if (strtvdownpaymentflag.equals("false")) {
//						strtvdownpaymentflag = "";
//					}

					strtvtenureremark = tv.getTenureremarks();
					strtvtenureflag = tv.getTenureflag();
//					if (strtvtenureflag.equals("false")) {
//						strtvtenureflag = "";
//					}

					strtvewioremiremark = tv.getEwioremiremarks();
					strtvewioremiflag = tv.getEwioremiflag();
//					if (strtvewioremiflag.equals("false")) {
//						strtvewioremiflag = "";
//					}

					strtvarrangebtwnriderremark = tv.getArrangebtwnriderremarks();
					strtvarrangebtwnriderflag = tv.getArrangebtwnriderflag();
//					if (strtvewioremiflag.equals("false")) {
//						strtvewioremiflag = "";
//					}

					strtvresiadrssremarks = tv.getResiadrssremarks();
					strtvresiadrssflag = tv.getResiadrssflag();
//					if (strtvresiadrssflag.equals("false")) {
//						strtvresiadrssflag = "";
//					}

					strtvnoofyrsinaddrsremarks = tv.getNoofyrinaddrssremarks();
					strtvnoofyrsinaddrsflag = tv.getNoofyrinaddrssflag();
//					if (strtvnoofyrsinaddrsflag.equals("false")) {
//						strtvnoofyrsinaddrsflag = "";
//					}

					strtvmbnumnotinnameremarks = tv.getMbnonotinnameremarks();
					strtvmbnumnotinnameflag = tv.getMbnonotinnameflag();
//					if (strtvmbnumnotinnameflag.equals("false")) {
//						strtvmbnumnotinnameflag = "";
//					}

				}

			} catch (Exception e) {
//				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
				strMsg = "Error retrieving customer id: " + strCustid;
				response.setErrorMsg(strMsg);
			}
			strMsg = strSurnameremark + "|" + strSurnameflag + "|" + strfnameremark + "|" + strfnameflag + "|"
					+ stronameremark + "|" + stronameflag + "|" + strmsremark + "|" + strmsflag + "|" + strsexremark
					+ "|" + strsexflag + "|" + strmobnoremark + "|" + strmobnoflag + "|" + strstgnmremark + "|"
					+ strstgnmflag + "|" + strdistremark + "|" + strdistflag + "|" + strlcnmremark + "|" + strlcnmflag
					+ "|" + strparremark + "|" + strparflag + "|" + strnidremark + "|" + strnidflag + "|"
					+ strbregnoremark + "|" + strbregnoflag + "|" + strbuseremark + "|" + strbuseflag + "|"
					+ strdobremark + "|" + strdobflag + "|" + strcntremark + "|" + strcntflag + "|" + strscntremark
					+ "|" + strscntflag + "|" + strvilremark + "|" + strvilflag + "|" + stryrvilremark + "|"
					+ stryrvilflag + "|" + strnokremark + "|" + strnokflag + "|" + strnokmremark + "|" + strnokmflag
					+ "|" + strnokrremark + "|" + strnokrflag + "|" + strnokaremark + "|" + strnokaflag + "|"
					+ strdlremark + "|" + strdlflag + "|" + strnatremark + "|" + strnatflag + "|" + strnodremark + "|"
					+ strnodflag + "|" + strorremark + "|" + strorflag + "|" + strllremark + "|" + strllflag + "|"
					+ strllmobremark + "|" + strllmobflag + "|" + strrpmremark + "|" + strrpmflag + "|" + stroisremark
					+ "|" + stroisflag + "|" + strdpsremark + "|" + strdpsflg + "|" + strpaddremark + "|" + strpaddflag
					+ "|" + strfathremark + "|" + strfathflag + "|" + strmothremark + "|" + strmothflag + "|"
					+ strnpsremark + "|" + strnpsflag + "|" + strlcnmobnoremark + "|" + strlcnmobnoflag + "|"
					+ strcusttyperemark + "|" + strcusttypeflag + "|" + strstagechairmanremarks + "|"
					+ strstagechairmanflag + "|" + strstagechairmanmbnoremarks + "|" + strstagechairmanmbnoflag + "|"
					+ strtvllrentfeedbackremark + "|" + strtvllrentfeedbackflag + "|" + strtvnoyrsinarearemark + "|"
					+ strtvnoyrsinareaflag + "|" + strtvlc1chmnrecfeedremark + "|" + strtvlc1chmnrecfeedflag + "|"
					+ strtvnearlmarkresiremark + "|" + strtvnearlmarkresiflag + "|" + strtvtvemptyremark + "|"
					+ strtvemptypeflag + "|" + strtvstgorwrkadrssnearlmarkremark + "|" + strtvstgorwrkadrssnearlmarkflag
					+ "|" + strtvstgoremprecmremark + "|" + strtvstgoremprecmflag + "|" + strtvnoofyrsinstgorbusiremark
					+ "|" + strtvnoofyrsinstgorbuisflag + "|" + strtvstgnoofvehiremark + "|" + strtvstgnoofvehiflag
					+ "|" + strtvbikeownerremark + "|" + strtvbikeownerflag + "|" + strtvnetincomeremark + "|"
					+ strtvnetincomeflag + "|" + strtvbikeusearearemark + "|" + strtvbikeuseareaflag + "|"
					+ strtvspousenameremark + "|" + strtvspousenameflag + "|" + strtvspousenoremark + "|"
					+ strtvspousenoflag + "|" + strtvspouseconfirmremark + "|" + strtvspouseconfirmflag + "|"
					+ strtvoffcdistanceremark + "|" + strtvoffcdistanceflag + "|" + strtvrelawithapplicantremark + "|"
					+ strtvrelawithapplicantflag + "|" + strtvpaymentbyriderremark + "|" + strtvpaymentbyriderflag + "|"
					+ strtvyakanumremark + "|" + strtvyakanumflag + "|" + strtvyakanumnameremark + "|"
					+ strtvyakanumnamflag + "|" + strtvpaymtdetailstovbyremark + "|" + strtvpaymtdetailstovbyflag + "|"
					+ strtvcashpaymntworeceiptremark + "|" + strtvcashpaymntworeceiptflag + "|"
					+ strtvapplicantknowvbyremark + "|" + strtvapplicantknowvbyflag + "|"
					+ strtvrelawithguarantorsremark + "|" + strtvrelawithguarantorsflag + "|" + strtvbikeappliedremark
					+ "|" + strtvbikeappliedflag + "|" + strtvdownpaymentremark + "|" + strtvdownpaymentflag + "|"
					+ strtvtenureremark + "|" + strtvtenureflag + "|" + strtvewioremiremark + "|" + strtvewioremiflag
					+ "|" + strtvarrangebtwnriderremark + "|" + strtvarrangebtwnriderflag + "|" + strtvresiadrssremarks
					+ "|" + strtvresiadrssflag + "|" + strtvnoofyrsinaddrsremarks + "|" + strtvnoofyrsinaddrsflag + "|"
					+ strtvmbnumnotinnameremarks + "|" + strtvmbnumnotinnameflag;

//			strQuery = "From FiVerification where fiid = '" + strCustid +"'" + strFlag +"";

			try {
				List<FiVerification> fiverification = fiVerifyRepo.findFiVerificationByCust(strCustid);

				for (FiVerification fi : fiverification) {

					strfiSurnameremark = fi.getSurnameremark();
					strfiSurnameflag = fi.getSurnameflag();
//					if (strfiSurnameflag.equals("false")) {
//						strfiSurnameflag = "";
//					}
					strfifnameremark = fi.getFirstnameremark();
					strfifnameflag = fi.getFirstnameflag();
//					if (strfifnameflag.equals("false")) {
//						strfifnameflag = "";
//					}
					strfionameremark = fi.getOthernameremark();
					strfionameflag = fi.getOthernameflag();
//					if (strfionameflag.equals("false")) {
//						strfionameflag = "";
//					}
					strfimsremark = fi.getMaritalstatusremark();
					strfimsflag = fi.getMaritalstatusflag();
//					if (strfimsflag.equals("false")) {
//						strfimsflag = "";
//					}
					strfisexremark = fi.getSexremark();
					strfisexflag = fi.getSexflag();
//					if (strfisexflag.equals("false")) {
//						strfisexflag = "";
//					}
					strfimobnoremark = fi.getMobilenoremark();
					strfimobnoflag = fi.getMobilenoflag();
//					if (strfimobnoflag.equals("false")) {
//						strfimobnoflag = "";
//					}
					strfistgnmremark = fi.getStageremark();
					strfistgnmflag = fi.getStageflag();
//					if (strfistgnmflag.equals("false")) {
//						strfistgnmflag = "";
//					}
					strfistgchmnnameremark = fi.getStagechmnnameremaks();
					strfistgchmnnameflag = fi.getStagechmnnameflag();
//					if (strfistgchmnnameflag.equals("false")) {
//						strfistgchmnnameflag = "";
//					}
					strfistgchmnnoremark = fi.getStagechmnnoremarks();
					strfistgchmnnoflag = fi.getStagechmnnoflag();
//					if (strfistgchmnnoflag.equals("false")) {
//						strfistgchmnnoflag = "";
//					}
					strfidistremark = fi.getDistrictremark();
					strfidistflag = fi.getDistrictflag();
//					if (strfidistflag.equals("false")) {
//						strfidistflag = "";
//					}
					strfilcnmremark = fi.getLcremark();
					strfilcnmflag = fi.getLcflag();
//					if (strfilcnmflag.equals("false")) {
//						strfilcnmflag = "";
//					}
					strfiparremark = fi.getParishremark();
					strfiparflag = fi.getParishflag();
//					if (strfiparflag.equals("false")) {
//						strfiparflag = "";
//					}
					strfinidremark = fi.getNationalidremark();
					strfinidflag = fi.getNationalidflag();
//					if (strfinidflag.equals("false")) {
//						strfinidflag = "";
//					}
					strfibregnoremark = fi.getBikeregnoremark();
					strfibregnoflag = fi.getBikeregnoflag();
//					if (strfibregnoflag.equals("false")) {
//						strfibregnoflag = "";
//					}
					strfibuseremark = fi.getBikeuseremark();
					strfibuseflag = fi.getBikeuseflag();
//					if (strfibuseflag.equals("false")) {
//						strfibuseflag = "";
//					}
					strfidobremark = fi.getDobremark();
					strfidobflag = fi.getDobflag();
//					if (strfidobflag.equals("false")) {
//						strfidobflag = "";
//					}
					strficntremark = fi.getCountyremark();
					strficntflag = fi.getCountyflag();
//					if (strficntflag.equals("false")) {
//						strficntflag = "";
//					}
					strfiscntremark = fi.getSubcountyremark();
					strfiscntflag = fi.getSubcountyflag();
//					if (strfiscntflag.equals("false")) {
//						strfiscntflag = "";
//					}
					strfivilremark = fi.getVillageremark();
					strfivilflag = fi.getVillageflag();
//					if (strfivilflag.equals("false")) {
//						strfivilflag = "";
//					}
					strfiyrvilremark = fi.getYearsinvillageremark();
					strfiyrvilflag = fi.getYearsinvillageflag();
//					if (strfiyrvilflag.equals("false")) {
//						strfiyrvilflag = "";
//					}
					strfinokremark = fi.getNextofkinremark();
					strfinokflag = fi.getNextofkinflag();
//					if (strfinokflag.equals("false")) {
//						strfinokflag = "";
//					}
					strfinokmremark = fi.getNokmobilenoremark();
					strfinokmflag = fi.getNokmobilenoflag();
//					if (strfinokmflag.equals("false")) {
//						strfinokmflag = "";
//					}
					strfinokrremark = fi.getNokrelationshipremark();
					strfinokrflag = fi.getNokrelationshipflag();
//					if (strfinokrflag.equals("false")) {
//						strfinokrflag = "";
//					}
					strfinokaremark = fi.getNokagreeingremark();
					strfinokaflag = fi.getNokagreeingflag();
//					if (strfinokaflag.equals("false")) {
//						strfinokaflag = "";
//					}
					strfidlremark = fi.getDrivingpermitremark();
					strfidlflag = fi.getDrivingpermitflag();
//					if (strfidlflag.equals("false")) {
//						strfidlflag = "";
//					}
					strfinatremark = fi.getNationalityremark();
					strfinatflag = fi.getNationalityflag();
//					if (strfinatflag.equals("false")) {
//						strfinatflag = "";
//					}
					strfinodremark = fi.getNoofdependantsremark();
					strfinodflag = fi.getNoofdependantsflag();
//					if (strfinodflag.equals("false")) {
//						strfinodflag = "";
//					}
					strfiorremark = fi.getOwnhouserentedremark();
					strfiorflag = fi.getOwnhouserentedflag();
//					if (strfiorflag.equals("false")) {
//						strfiorflag = "";
//					}
					strfillremark = fi.getLandlordnameremark();
					strfillflag = fi.getLandlordnameflag();
//					if (strfillflag.equals("false")) {
//						strfillflag = "";
//					}
					strfillmobremark = fi.getLandlordmobilenoremark();
					strfillmobflag = fi.getLandlordmobilenoflag();
//					if (strfillmobflag.equals("false")) {
//						strfillmobflag = "";
//					}
					strfirpmremark = fi.getRentpmremark();
					strfirpmflag = fi.getRentpmflag();
//					if (strfirpmflag.equals("false")) {
//						strfirpmflag = "";
//					}
					strfioisremark = fi.getOtherincomesourceremark();
					strfioisflag = fi.getOtherincomesourceflag();
//					if (strfioisflag.equals("false")) {
//						strfioisflag = "";
//					}
					strfidpsremark = fi.getDownpaymentsourceremark();
					strfidpsflg = fi.getDownpaymentsourceflag();
//					if (strfidpsflg.equals("false")) {
//						strfidpsflg = "";
//					}
					strfipaddremark = fi.getPermanentaddressremark();
					strfipaddflag = fi.getPermanentaddressflag();
//					if (strfipaddflag.equals("false")) {
//						strfipaddflag = "";
//					}
					strfifathremark = fi.getFathersnameremark();
					strfifathflag = fi.getFathersnameflag();
//					if (strfifathflag.equals("false")) {
//						strfifathflag = "";
//					}
					strfimothremark = fi.getMothersnameremark();
					strfimothflag = fi.getMothersnameflag();
//					if (strfimothflag.equals("false")) {
//						strfimothflag = "";
//					}
					strfinpsremark = fi.getNearbypolicestationremark();
					strfinpsflag = fi.getNearbypolicestationflag();
//					if (strfinpsflag.equals("false")) {
//						strfinpsflag = "";
//					}
					strfilcnmobnoremark = fi.getLcmobilenoremark();
					strfilcnmobnoflag = fi.getLcmobilenoflag();
//					if (strfilcnmobnoflag.equals("false")) {
//						strfilcnmobnoflag = "";
//					}
					strficusttyperemark = fi.getCusttyperemark();
					strficusttypeflag = fi.getCusttypeflag();
//					if (strficusttypeflag.equals("false")) {
//						strficusttypeflag = "";
//					}
					strfistagechairmanremarks = fi.getStagechmnnameremaks();
					strfistagechairmanflag = fi.getStagechmnnameflag();
//					if (strfistagechairmanflag.equals("false")) {
//						strfistagechairmanflag = "";
//					}
					strfistagechairmanmbnoremarks = fi.getStagechmnnoremarks();
					strfistagechairmanmbnoflag = fi.getStagechmnnoflag();
//					if (strfistagechairmanmbnoflag.equals("false")) {
//						strfistagechairmanmbnoflag = "";
//					}

					strfillrentfeedbackremark = fi.getLlrentfeedbackremarks();
					strfillrentfeedbackflag = fi.getLlrentfeedbackflag();
//					if (strfillrentfeedbackflag.equals("false")) {
//						strfillrentfeedbackflag = "";
//					}
					strfinoyrsinarearemark = fi.getNoyrsinarearemarks();
					strfinoyrsinareaflag = fi.getNoyrsinareaflag();
//					if (strfinoyrsinareaflag.equals("false")) {
//						strfinoyrsinareaflag = "";
//					}
					strfilc1chmnrecfeedremark = fi.getLc1chmnrecfeedremarks();
					strfilc1chmnrecfeedflag = fi.getLc1chmnrecfeedflag();
//					if (strfilc1chmnrecfeedflag.equals("false")) {
//						strfilc1chmnrecfeedflag = "";
//					}
					strfinearlmarkresiremark = fi.getNearlmarkresiremarks();
					strfinearlmarkresiflag = fi.getNearlmarkresiflag();
//					if (strfinearlmarkresiflag.equals("false")) {
//						strfinearlmarkresiflag = "";
//					}

					strfiemptyremark = fi.getEmptyperemarks();
					strfiemptypeflag = fi.getEmptypeflag();
//					if (strfiemptypeflag.equals("false")) {
//						strfiemptypeflag = "";
//					}

					strfistgorwrkadrssnearlmarkremark = fi.getStgorwrkadrssnearlmarkremarks();
					strfistgorwrkadrssnearlmarkflag = fi.getStgorwrkadrssnearlmarkflag();
//					if (strfistgorwrkadrssnearlmarkflag.equals("false")) {
//						strfistgorwrkadrssnearlmarkflag = "";
//					}

					strfistgoremprecmremark = fi.getStgoremprecmremarks();
					strfistgoremprecmflag = fi.getStgoremprecmflag();
//					if (strfistgoremprecmflag.equals("false")) {
//						strfistgoremprecmflag = "";
//					}

					strfinoofyrsinstgorbusiremark = fi.getNoofyrsinstgorbusiremarks();
					strfinoofyrsinstgorbuisflag = fi.getNoofyrsinstgorbuisflag();
//					if (strfinoofyrsinstgorbuisflag.equals("false")) {
//						strfinoofyrsinstgorbuisflag = "";
//					}

					strfistgnoofvehiremark = fi.getStgnoofvehiremarks();
					strfistgnoofvehiflag = fi.getStgnoofvehiflag();
//					if (strfistgnoofvehiflag.equals("false")) {
//						strfistgnoofvehiflag = "";
//					}

					strfibikeownerremark = fi.getOwnerofbikeremarks();
					strfibikeownerflag = fi.getOwnerofbikeflag();
//					if (strfibikeownerflag.equals("false")) {
//						strfibikeownerflag = "";
//					}

					strfinetincomeremark = fi.getNetincomeremarks();
					strfinetincomeflag = fi.getNetincomeflag();
//					if (strfinetincomeflag.equals("false")) {
//						strfinetincomeflag = "";
//					}

					strfibikeusearearemark = fi.getBikeusearearemarks();
					strfibikeuseareaflag = fi.getBikeuseareaflag();
//					if (strfibikeuseareaflag.equals("false")) {
//						strfibikeuseareaflag = "";
//					}

					strfispousenameremark = fi.getSpousenameremarks();
					strfispousenameflag = fi.getSpousenameflag();
//					if (strfispousenameflag.equals("false")) {
//						strfispousenameflag = "";
//					}

					strfispousenoremark = fi.getSpousenoremarks();
					strfispousenoflag = fi.getSpousenoflag();
//					if (strfispousenoflag.equals("false")) {
//						strfispousenoflag = "";
//					}

					strfispouseconfirmremark = fi.getSpouseconfirmremarks();
					strfispouseconfirmflag = fi.getSpouseconfirmflag();
//					if (strfispouseconfirmflag.equals("false")) {
//						strfispouseconfirmflag = "";
//					}

					strfioffcdistanceremark = fi.getOffcdistanceremarks();
					strfioffcdistanceflag = fi.getOffcdistanceflag();
//					if (strfioffcdistanceflag.equals("false")) {
//						strfioffcdistanceflag = "";
//					}

					strfirelawithapplicantremark = fi.getRelawithapplicantremarks();
					strfirelawithapplicantflag = fi.getRelawithapplicantflag();
//					if (strfirelawithapplicantflag.equals("false")) {
//						strfirelawithapplicantflag = "";
//					}

					strfipaymentbyriderremark = fi.getPaymentbyriderremarks();
					strfipaymentbyriderflag = fi.getPaymentbyriderflag();
//					if (strfipaymentbyriderflag.equals("false")) {
//						strfipaymentbyriderflag = "";
//					}

					strfiyakanumremark = fi.getYakanumremarks();
					strfiyakanumflag = fi.getYakanumflag();
//					if (strfiyakanumflag.equals("false")) {
//						strfiyakanumflag = "";
//					}

					strfiyakanumnameremark = fi.getYakanumnameremarks();
					strfiyakanumnamflag = fi.getYakanumnamflag();
//					if (strfiyakanumnamflag.equals("false")) {
//						strfiyakanumnamflag = "";
//					}

					strfipaymtdetailstovbyremark = fi.getPaymtdetailstovbyremarks();
					strfipaymtdetailstovbyflag = fi.getPaymtdetailstovbyflag();
//					if (strfipaymtdetailstovbyflag.equals("false")) {
//						strfipaymtdetailstovbyflag = "";
//					}

					strficashpaymntworeceiptremark = fi.getCashpaymntworeceiptremarks();
					strficashpaymntworeceiptflag = fi.getCashpaymntworeceiptflag();
//					if (strficashpaymntworeceiptflag.equals("false")) {
//						strficashpaymntworeceiptflag = "";
//					}

					strfiapplicantknowvbyremark = fi.getApplicantknowvbyremarks();
					strfiapplicantknowvbyflag = fi.getApplicantknowvbyflag();
//					if (strfiapplicantknowvbyflag.equals("false")) {
//						strfiapplicantknowvbyflag = "";
//					}

					strfirelawithguarantorsremark = fi.getRelawithguarantorsremarks();
					strfirelawithguarantorsflag = fi.getRelawithguarantorsflag();
//					if (strfirelawithguarantorsflag.equals("false")) {
//						strfirelawithguarantorsflag = "";
//					}

					strfibikeappliedremark = fi.getBikeappliedremarks();
					strfibikeappliedflag = fi.getBikeappliedflag();
//					if (strfibikeappliedflag.equals("false")) {
//						strfibikeappliedflag = "";
//					}

					strfidownpaymentremark = fi.getDownpaymentremarks();
					strfidownpaymentflag = fi.getDownpaymentflag();
//					if (strfidownpaymentflag.equals("false")) {
//						strfidownpaymentflag = "";
//					}

					strfitenureremark = fi.getTenureremarks();
					strfitenureflag = fi.getTenureflag();
//					if (strfitenureflag.equals("false")) {
//						strfitenureflag = "";
//					}

					strfiewioremiremark = fi.getEwioremiremarks();
					strfiewioremiflag = fi.getEwioremiflag();
//					if (strfiewioremiflag.equals("false")) {
//						strfiewioremiflag = "";
//					}

					strfiarrangebtwnriderremark = fi.getArrangebtwnriderremarks();
					strfiarrangebtwnriderflag = fi.getArrangebtwnriderflag();
//					if (strfiewioremiflag.equals("false")) {
//						strfiewioremiflag = "";
//					}

					strfiresiadrssremarks = fi.getResiadrssremarks();
					strfiresiadrssflag = fi.getResiadrssflag();
//					if (strfiresiadrssflag.equals("false") || strfiresiadrssflag.equals(null) || strfiresiadrssflag == "null") {
//						strfiresiadrssflag = "";
//					}

					strfinoofyrsinaddrsremarks = fi.getNoofyrinaddrssremarks();
					strfinoofyrsinaddrsflag = fi.getNoofyrinaddrssflag();
//					if (strfinoofyrsinaddrsflag.equals("false")|| strfinoofyrsinaddrsflag.equals(null) || strfinoofyrsinaddrsflag == "null") {
//						strfinoofyrsinaddrsflag = "";
//					}

					strfimbnumnotinnameremarks = fi.getMbnonotinnameremarks();
					strfimbnumnotinnameflag = fi.getMbnonotinnameflag();
//					if (strfimbnumnotinnameflag.equals("false")|| strfimbnumnotinnameflag.equals(null) || strfimbnumnotinnameflag == "null") {
//						strfimbnumnotinnameflag = "";
//					}

				}

			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println(e.getLocalizedMessage());
				strMsg = "Error retrieving customer id: " + strCustid;
				response.setErrorMsg(strMsg);
			}
			strMsg1 = strfiSurnameremark + "|" + strfiSurnameflag + "|" + strfifnameremark + "|" + strfifnameflag + "|"
					+ strfionameremark + "|" + strfionameflag + "|" + strfimsremark + "|" + strfimsflag + "|"
					+ strfisexremark + "|" + strfisexflag + "|" + strfimobnoremark + "|" + strfimobnoflag + "|"
					+ strfistgnmremark + "|" + strfistgnmflag + "|" + strfidistremark + "|" + strfidistflag + "|"
					+ strfilcnmremark + "|" + strfilcnmflag + "|" + strfiparremark + "|" + strfiparflag + "|"
					+ strfinidremark + "|" + strfinidflag + "|" + strfibregnoremark + "|" + strfibregnoflag + "|"
					+ strfibuseremark + "|" + strfibuseflag + "|" + strfidobremark + "|" + strfidobflag + "|"
					+ strficntremark + "|" + strficntflag + "|" + strfiscntremark + "|" + strfiscntflag + "|"
					+ strfivilremark + "|" + strfivilflag + "|" + strfiyrvilremark + "|" + strfiyrvilflag + "|"
					+ strfinokremark + "|" + strfinokflag + "|" + strfinokmremark + "|" + strfinokmflag + "|"
					+ strfinokrremark + "|" + strfinokrflag + "|" + strfinokaremark + "|" + strfinokaflag + "|"
					+ strfidlremark + "|" + strfidlflag + "|" + strfinatremark + "|" + strfinatflag + "|"
					+ strfinodremark + "|" + strfinodflag + "|" + strfiorremark + "|" + strfiorflag + "|"
					+ strfillremark + "|" + strfillflag + "|" + strfillmobremark + "|" + strfillmobflag + "|"
					+ strfirpmremark + "|" + strfirpmflag + "|" + strfioisremark + "|" + strfioisflag + "|"
					+ strfidpsremark + "|" + strfidpsflg + "|" + strfipaddremark + "|" + strfipaddflag + "|"
					+ strfifathremark + "|" + strfifathflag + "|" + strfimothremark + "|" + strfimothflag + "|"
					+ strfinpsremark + "|" + strfinpsflag + "|" + strfilcnmobnoremark + "|" + strfilcnmobnoflag + "|"
					+ strficusttyperemark + "|" + strficusttypeflag + "|" + strfistagechairmanremarks + "|"
					+ strfistagechairmanflag + "|" + strfistagechairmanmbnoremarks + "|" + strfistagechairmanmbnoflag
					+ "|" + strfillrentfeedbackremark + "|" + strfillrentfeedbackflag + "|" + strfinoyrsinarearemark
					+ "|" + strfinoyrsinareaflag + "|" + strfilc1chmnrecfeedremark + "|" + strfilc1chmnrecfeedflag + "|"
					+ strfinearlmarkresiremark + "|" + strfinearlmarkresiflag + "|" + strfiemptyremark + "|"
					+ strfiemptypeflag + "|" + strfistgorwrkadrssnearlmarkremark + "|" + strfistgorwrkadrssnearlmarkflag
					+ "|" + strfistgoremprecmremark + "|" + strfistgoremprecmflag + "|" + strfinoofyrsinstgorbusiremark
					+ "|" + strfinoofyrsinstgorbuisflag + "|" + strfistgnoofvehiremark + "|" + strfistgnoofvehiflag
					+ "|" + strfibikeownerremark + "|" + strfibikeownerflag + "|" + strfinetincomeremark + "|"
					+ strfinetincomeflag + "|" + strfibikeusearearemark + "|" + strfibikeuseareaflag + "|"
					+ strfispousenameremark + "|" + strfispousenameflag + "|" + strfispousenoremark + "|"
					+ strfispousenoflag + "|" + strfispouseconfirmremark + "|" + strfispouseconfirmflag + "|"
					+ strfioffcdistanceremark + "|" + strfioffcdistanceflag + "|" + strfirelawithapplicantremark + "|"
					+ strfirelawithapplicantflag + "|" + strfipaymentbyriderremark + "|" + strfipaymentbyriderflag + "|"
					+ strfiyakanumremark + "|" + strfiyakanumflag + "|" + strfiyakanumnameremark + "|"
					+ strfiyakanumnamflag + "|" + strfipaymtdetailstovbyremark + "|" + strfipaymtdetailstovbyflag + "|"
					+ strficashpaymntworeceiptremark + "|" + strficashpaymntworeceiptflag + "|"
					+ strfiapplicantknowvbyremark + "|" + strfiapplicantknowvbyflag + "|"
					+ strfirelawithguarantorsremark + "|" + strfirelawithguarantorsflag + "|" + strfibikeappliedremark
					+ "|" + strfibikeappliedflag + "|" + strfidownpaymentremark + "|" + strfidownpaymentflag + "|"
					+ strfitenureremark + "|" + strfitenureflag + "|" + strfiewioremiremark + "|" + strfiewioremiflag
					+ "|" + strfiarrangebtwnriderremark + "|" + strfiarrangebtwnriderflag + "|" + strfiresiadrssremarks + "|"
					+ strfiresiadrssflag + "|" + strfinoofyrsinaddrsremarks + "|" + strfinoofyrsinaddrsflag + "|"
					+ strfimbnumnotinnameremarks + "|" + strfimbnumnotinnameflag;

		}

		strMsg2 = strMsg + "|" + strMsg1;
		response.setData(strMsg2);
		return response;
	}

	public List<CustomerDetailsDto> customerCheck(List<CustomerDetails> cdList) {
		List<CustomerDetailsDto> cDt = new ArrayList<>();
		for (CustomerDetails cusd : cdList) {
			CustomerDetailsDto cusDdto = new CustomerDetailsDto();
			cusDdto.setSurname(cusd.getSurname());
			cusDdto.setOtherid(cusd.getOtherid());
			cDt.add(cusDdto);
		}
		return cDt;
	}

	public Response<List<CustomerDetailsDto>> viewCustomerArgs(String flag) {
		Response<List<CustomerDetailsDto>> res = new Response<>();
		if (flag.equals("docs")) {
			try {
				List<CustomerDetails> cdList = customerRepo.custNameForDocs();
				res.setData(customerCheck(cdList));
				res.setResponseCode(200);
			} catch (Exception e) {
				res.setErrorMsg(e.getLocalizedMessage());
			}

		} else if (flag.equals("DGE")) {
			List<CustomerDetails> cdList = customerRepo.custBySurname();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("EDoc")) {
			List<CustomerDetails> cdList = customerRepo.custNameForEdocs();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("DD")) {
			List<CustomerDetails> cdList = customerRepo.custNameForDwdDocuments();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("Agree")) {
			List<CustomerDetails> cdList = customerRepo.custNameForAgupload();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("printSchedule")) {
			List<CustomerDetails> cdList = customerRepo.custNameForPrintSchedule();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("DCE")) {
			List<CustomerDetails> cdList = customerRepo.custNameForUpCus();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("tv")) {
			List<CustomerDetails> cdList = customerRepo.custNameForTelVerify();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("fv")) {
			List<CustomerDetails> cdList = customerRepo.custNameForFieldVerify();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("cqc")) {
			List<CustomerDetails> cdList = customerRepo.custNameForCusQcheck();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("cashReceipt")) {
			List<CustomerDetails> cdList = customerRepo.custNameForCashReci();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("micsCashReceipt")) {
			List<CustomerDetails> cdList = customerRepo.custNameForMCashReceipt();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("CreditAppraisalMemo")) {
			List<CustomerDetails> cdList = customerRepo.custNameForCreAppMemo();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("CAMApproval")) {
			List<CustomerDetails> cdList = customerRepo.custNameForCAMApprove();
			res.setData(customerCheck(cdList));
		} else if (flag.equals("PrintCustomerStatement")) {
			List<CustomerDetails> cdList = customerRepo.custNameForPrntCustStat();
			res.setData(customerCheck(cdList));
		}
		return res;
	}

	public Response<String> viewCustomerVeh(String propNo) {
		System.out.println(propNo);
		String strMsg = "";
		String strProposalno = propNo;
		String strVehicleRegno = "";
		String strEwi = "";

		Response<String> response = new Response<>();

		if (strProposalno == null || strProposalno.length() == 0) {
			strMsg = "";
		} else {
//			String strQuery = "From CustomerVehicle where proposalno = " + "'" + strProposalno + "'";			
			try {
				CustomerVehicle custveh = custVehRepo.findByproposalno(strProposalno);
				strVehicleRegno = custveh.getVehicleregno();

//					String strQuery1 = "From Loan where proposalno = " + "'" + strProposalno + "'";					
				try {
					List<Loan> loan = loanRepo.findByproposalno(strProposalno);
					if (loan.size() > 0) {
						for (Loan ln : loan) {
							System.out.println(ln.getEwi());
							strEwi = ln.getEwi();
						}
					}
				} catch (Exception e) {
				}
			} catch (Exception e) {
			}
			strMsg = strVehicleRegno + "|" + strEwi;
			System.out.println(strMsg);
		}

		response.setData(strMsg);
		return response;
	}
}
