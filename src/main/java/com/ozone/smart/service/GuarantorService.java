package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.GuarantorDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.FiVerification;
import com.ozone.smart.entity.Guarantor;
import com.ozone.smart.entity.TvVerification;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.FiVerifyRepo;
import com.ozone.smart.repository.GuarantorRepo;
import com.ozone.smart.repository.TeleVerifyRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class GuarantorService {
	
	@Autowired
	private GuarantorRepo guarantorRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private FiVerifyRepo fiVerifyRepo;
	@Autowired
	private TeleVerifyRepo teleVerifyRepo;

	public Response<String> addGuarantor(GuarantorDto guarantorDto) {
		
		Response<String> response = new Response<>();
		int intId = 0;
		int intUser = 0;
		
		boolean firstGuarantor = false;
		boolean secondGuarantor = false;
//		boolean blnNoka = false;
		boolean blnSal = false;
		boolean blnScc = false;
		
		boolean blnGuarannid = false;
		boolean blnGuaranstage = false;
		String strFirstguarantor = "1st Guarantor";
		String strSecondguarantor = "2nd Guarantor";
		
		String Guarantorid = guarantorDto.getNationalid();
		String Customerid = guarantorDto.getCustid();
		String Surname = guarantorDto.getSurname();
		String Firstname = guarantorDto.getFirstname();
		String Othername = guarantorDto.getOthername();
		String Guarantortype = guarantorDto.getGuarantorType();
		String Mobileno = guarantorDto.getMobileno();
		String Add = guarantorDto.getAddress();
		String Padd = guarantorDto.getPermanentaddress();
		String Yrsadd = guarantorDto.getYrsinaddress();
		String Ownrented = guarantorDto.getOwnhouserented();
		String Rent = guarantorDto.getRentpm();
//		String Nok = guarantorDto.getNextofkin();		
//		String Nokmobile = guarantorDto.getNokmobileno();
//		String Nokrship = guarantorDto.getNokrelationship();
//		boolean Noka = guarantorDto.isNokagreeing();
		String Bkregno = guarantorDto.getBikeregno();
		String Bkowner = guarantorDto.getBikeowner();
		boolean Sal = guarantorDto.isSalaried();
		String Empname = guarantorDto.getEmployername();
		String Mnthincome = guarantorDto.getMonthlyincome();
		String Ois = guarantorDto.getOis();
		String Oincome = guarantorDto.getOtherincome();
		String Stgname = guarantorDto.getStagename();
		String Stgadd = guarantorDto.getStageaddress();
		String Lc = guarantorDto.getLcname();
		boolean Scc = guarantorDto.isStagechairconf();		
		String Stgcname = guarantorDto.getStagechairname();
		String Stgcmobile = guarantorDto.getStagechairmobile();
		String Lcmobile = guarantorDto.getLcmobile();
		int Id = guarantorDto.getId();
		String strId = String.valueOf(Id);
		String Flag = guarantorDto.getFlag();
		String guaranNatid = "";
		String Odflag = guarantorDto.getOldFlag();
		
		String strdob = guarantorDto.getDob();
		String strllordname = guarantorDto.getLlordname();
		String strllordmbno = guarantorDto.getLlordmbno();
		String strllrentfeedbak = guarantorDto.getLlrentfeedbk();
		String stryakanum = guarantorDto.getYakanum();
		String stryakanumname = guarantorDto.getYakanumname();
		String strresiadrss = guarantorDto.getResiadrss();
		String stryrsinarea = guarantorDto.getYrsinarea();
		String strlc1chmrecfeed = guarantorDto.getLc1chmrecfeed();
		String strnearlmarkresi = guarantorDto.getNearlmarkresi();
		String stremptype = guarantorDto.getEmptype();
		String strstgwrkadrssnearlmark = guarantorDto.getStgwrkadrssnearlmark();
		String stryrsinstgorbusi = guarantorDto.getYrsinstgorbusi();
		String strmaritalsts = guarantorDto.getMaritalsts();
		String strspousename = guarantorDto.getSpousename();
		String strspouseno = guarantorDto.getSpouseno();
		String strmbnumnotinname = guarantorDto.getMbnonotinname();


		
		//******************************* Start of dedupe **********************
		if ((Odflag.equals("")) && (Flag.equals("new"))) {
			
			List<Guarantor> guarantor =  guarantorRepo.findBynationalid(Guarantorid);
			if (guarantor.size() > 0) {
				guaranNatid = Guarantorid;
				blnGuarannid = true;
			}
			
			Surname = Surname.toUpperCase();
			Stgname = Stgname.toUpperCase();
			
			
			List<Guarantor> guarantor1 = guarantorRepo.findBySurAndStageName(Surname, Stgname);
			if (guarantor1.size() > 0) {
				for (Guarantor guaran:guarantor1) {
					guaranNatid = guaran.getNationalid();
				}
				blnGuaranstage = true;
			}
		} else if ((Odflag.equals("idoverride")) && (Flag.equals("new"))){
			blnGuarannid = false;
			
			Surname = Surname.toUpperCase();
			Stgname = Stgname.toUpperCase();
			
			
			List<Guarantor> guarantor1 = guarantorRepo.findBySurAndStageName(Surname, Stgname);
			if (guarantor1.size() > 0) {
				for (Guarantor guaran:guarantor1) {
					guaranNatid = guaran.getNationalid();
				}
				blnGuaranstage = true;
			}
		} else if ((Odflag.equals("stgoverride")) && (Flag.equals("new"))){
			blnGuaranstage = false;
							
			List<Guarantor> guarantor = guarantorRepo.findBynationalid(Guarantorid);
			if (guarantor.size() > 0) {
				guaranNatid = Guarantorid;
				blnGuarannid = true;
			}
		}
		
		if (strId == null || strId.length() == 0) {
			intId = 0;
		} else {
			intId = Integer.parseInt(strId.trim());
		}
		
		if (blnGuarannid) { //Dedupe Nationalid
			
			response.setData("Guarantor with National ID : " + guaranNatid + "" + " already exists"); 						
		} else if (blnGuaranstage) { //Dedupe Name + Stage
			
			response.setData("Guarantor with similar Name + Stage having " + "National ID : " + guaranNatid + "" + " already exists");						
		} else {
			
			TimeStampUtil gts = new TimeStampUtil();		
			String Requestdatetime = gts.TimeStamp();
			
//			if (Noka) {
//				blnNoka = true;
//			} else {
//				blnNoka = false;
//			}
			
			if (Sal) {
				blnSal = true;
			} else {
				blnSal = false;
			}
			
			if (Scc) {
				blnScc = true;
			} else {
				blnScc = false;
			}
			
			if (Guarantortype.equals(strFirstguarantor)) {
				firstGuarantor = true;
			} else if (Guarantortype.equals(strSecondguarantor)) {
				secondGuarantor = true;
			}
				
			if (Flag.equals("new")) {
				Guarantor guan = new Guarantor();
				guan.setSurname(Surname);
				guan.setFirstname(Firstname);
				guan.setOthername(Othername);
				guan.setFirstguarantor(firstGuarantor);
				guan.setSecondguarantor(secondGuarantor);
				guan.setCustid(Customerid);
				guan.setNationalid(Guarantorid);
				guan.setMobileno(Mobileno);
				guan.setAddress(Add);
				guan.setPermanentaddress(Padd);
				guan.setYrsinaddress(Yrsadd);
				guan.setOwnhouserented(Ownrented);
				guan.setRentpm(Rent);
//				guan.setNextofkin(Nok);
//				guan.setNokmobileno(Nokmobile);
//				guan.setNokrelationship(Nokrship);
//				guan.setNokagreeing(blnNoka);
				guan.setBikeregno(Bkregno);
				guan.setBikeowner(Bkowner);
				guan.setSalaried(blnSal);
				guan.setEmployername(Empname);
				guan.setMonthlyincome(Mnthincome);
				guan.setOis(Ois);
				guan.setOtherincome(Oincome);
				guan.setStagename(Stgname);
				guan.setStageaddress(Stgadd);
				guan.setStagechairname(Stgcname);
				guan.setStagechairmobile(Stgcmobile);
				guan.setStagechairconf(blnScc);
				guan.setLcname(Lc);
				guan.setLcmobile(Lcmobile);
				guan.setCapturedatetime(Requestdatetime);	
				
				guan.setDob(strdob);
				guan.setLlordname(strllordname);
				guan.setLlordmbno(strllordmbno);
				guan.setYakanum(stryakanum);
				guan.setLlrentfeedbk(strllrentfeedbak);
				guan.setYakanumname(stryakanumname);
				guan.setResiadrss(strresiadrss);
				guan.setYrsinarea(stryrsinarea);
				guan.setLc1chmrecfeed(strlc1chmrecfeed);
				guan.setNearlmarkresi(strnearlmarkresi);
				guan.setEmptype(stremptype);
				guan.setStgwrkadrssnearlmark(strstgwrkadrssnearlmark);
				guan.setYrsinstgorbusi(stryrsinstgorbusi);
				guan.setMaritalsts(strmaritalsts);
				guan.setSpousename(strspousename);
				guan.setSpouseno(strspouseno);
				guan.setMbnonotinname(strmbnumnotinname);

										
				try {
					guarantorRepo.save(guan);
					response.setData("Guarantor : " + Surname + " saved successfully.");
				} catch (Exception e) {
					Throwable th = e.getCause();
					e.printStackTrace();
					System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
				 String Msg = th.getCause().toString();
					
					if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
						response.setData("Guarantor : " + Surname + " saved successfully");
					} else if (Msg.contains("The database returned no natively generated identity value")) {
						response.setData("Guarantor : " + Surname + " saved successfully");
					} else if (Msg.contains("")) {
						response.setData("General error");
					}
				}			
			} 
			}
		return response;
	}

	public Response<String> editGuarantor(GuarantorDto guarantorDto) {
		Response<String> response = new Response<>();
		
		String strFirstguarantor = "1st Guarantor";
		String strSecondguarantor = "2nd Guarantor";
		
		boolean firstGuarantor = false;
		boolean secondGuarantor = false;
		
		String Guarantorid = guarantorDto.getNationalid();
		String Customerid = guarantorDto.getCustid();
		String Surname = guarantorDto.getSurname();
		String Firstname = guarantorDto.getFirstname();
		String Othername = guarantorDto.getOthername();
		String Guarantortype = guarantorDto.getGuarantorType();
		String Mobileno = guarantorDto.getMobileno();
		String Add = guarantorDto.getAddress();
		String Padd = guarantorDto.getPermanentaddress();
		String Yrsadd = guarantorDto.getYrsinaddress();
		String Ownrented = guarantorDto.getOwnhouserented();
		String Rent = guarantorDto.getRentpm();
//		String Nok = guarantorDto.getNextofkin();		
//		String Nokmobile = guarantorDto.getNokmobileno();
//		String Nokrship = guarantorDto.getNokrelationship();
//		boolean Noka = guarantorDto.isNokagreeing();
		String Bkregno = guarantorDto.getBikeregno();
		String Bkowner = guarantorDto.getBikeowner();
		boolean Sal = guarantorDto.isSalaried();
		String Empname = guarantorDto.getEmployername();
		String Mnthincome = guarantorDto.getMonthlyincome();
		String Ois = guarantorDto.getOis();
		String Oincome = guarantorDto.getOtherincome();
		String Stgname = guarantorDto.getStagename();
		String Stgadd = guarantorDto.getStageaddress();
		String Lc = guarantorDto.getLcname();
		boolean Scc = guarantorDto.isStagechairconf();		
		String Stgcname = guarantorDto.getStagechairname();
		String Stgcmobile = guarantorDto.getStagechairmobile();
		String Lcmobile = guarantorDto.getLcmobile();
		int Id = guarantorDto.getId();
		String strId = String.valueOf(Id);
		String Flag = guarantorDto.getFlag();
		String guaranNatid = "";
		String Odflag = guarantorDto.getOldFlag();
		
		String strdob = guarantorDto.getDob();
		String strllordname = guarantorDto.getLlordname();
		String strllordmbno = guarantorDto.getLlordmbno();
		String strllrentfeedbak = guarantorDto.getLlrentfeedbk();
		String stryakanum = guarantorDto.getYakanum();
		String stryakanumname = guarantorDto.getYakanumname();
		String strresiadrss = guarantorDto.getResiadrss();
		String stryrsinarea = guarantorDto.getYrsinarea();
		String strlc1chmrecfeed = guarantorDto.getLc1chmrecfeed();
		String strnearlmarkresi = guarantorDto.getNearlmarkresi();
		String stremptype = guarantorDto.getEmptype();
		String strstgwrkadrssnearlmark = guarantorDto.getStgwrkadrssnearlmark();
		String stryrsinstgorbusi = guarantorDto.getYrsinstgorbusi();
		String strmaritalsts = guarantorDto.getMaritalsts();
		String strspousename = guarantorDto.getSpousename();
		String strspouseno = guarantorDto.getSpouseno();
		String strmbnumnotinname = guarantorDto.getMbnonotinname();

		
		if (Guarantortype.equals(strFirstguarantor)) {
			firstGuarantor = true;
		} else if (Guarantortype.equals(strSecondguarantor)) {
			secondGuarantor = true;
		}
		
		Optional<Guarantor> opGuarantor = guarantorRepo.findById(Id);
		
		
		if(opGuarantor.isPresent()) {
			Guarantor guarantor = opGuarantor.get();
			
			guarantor.setAddress(Add);
			guarantor.setBikeowner(Bkowner);
			guarantor.setBikeregno(Bkregno);
			guarantor.setCustid(Customerid);
			guarantor.setEmployername(Empname);
			guarantor.setFirstguarantor(firstGuarantor);
			guarantor.setFirstname(Firstname);
			guarantor.setLcmobile(Lcmobile);
			guarantor.setLcname(Lc);
			guarantor.setMobileno(Mobileno);
			guarantor.setMonthlyincome(Mnthincome);
			guarantor.setNationalid(guaranNatid);
//			guarantor.setNextofkin(Nok);
//			guarantor.setNokagreeing(Noka);
//			guarantor.setNokmobileno(Nokmobile);
//			guarantor.setNokrelationship(Nokrship);
			guarantor.setOis(Ois);
			guarantor.setOtherincome(Oincome);
			guarantor.setOthername(Othername);
			guarantor.setOwnhouserented(Ownrented);
			guarantor.setPermanentaddress(Padd);
			guarantor.setRentpm(Rent);
			guarantor.setSalaried(Sal);
			guarantor.setSecondguarantor(secondGuarantor);
			guarantor.setStageaddress(Stgadd);
			guarantor.setStagechairconf(Scc);
			guarantor.setStagechairmobile(Stgcmobile);
			guarantor.setStagechairname(Stgcname);
			guarantor.setStagename(Stgname);
			guarantor.setSurname(Surname);
			guarantor.setYrsinaddress(Yrsadd);
			guarantor.setNationalid(Guarantorid);
			
			guarantor.setDob(strdob);
			guarantor.setLlordname(strllordname);
			guarantor.setLlordmbno(strllordmbno);
			guarantor.setYakanum(stryakanum);
			guarantor.setYakanumname(stryakanumname);
			guarantor.setLlrentfeedbk(strllrentfeedbak);
			guarantor.setResiadrss(strresiadrss);
			guarantor.setYrsinarea(stryrsinarea);
			guarantor.setLc1chmrecfeed(strlc1chmrecfeed);
			guarantor.setNearlmarkresi(strnearlmarkresi);
			guarantor.setEmptype(stremptype);
			guarantor.setStgwrkadrssnearlmark(strstgwrkadrssnearlmark);
			guarantor.setYrsinstgorbusi(stryrsinstgorbusi);
			guarantor.setMaritalsts(strmaritalsts);
			guarantor.setSpousename(strspousename);
			guarantor.setSpouseno(strspouseno);
			guarantor.setMbnonotinname(strmbnumnotinname);
			


			
			try {
				guarantorRepo.save(guarantor);
					response.setData("Guarantor " + Id + " updated successfully");
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		         
		        response.setErrorMsg(th.getCause().toString());
			}
		}	
		return response;
	}
	
	
	

	public Response<String> removeGuarantor(int id) {	
		Response<String> response = new Response<>();
		try {
			guarantorRepo.deleteById(id);
			response.setData( "Guarantor " + id + " has been deleted");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			Throwable th = e.getCause();
	        System.out.println("THROWABLE INFO: " + th.getCause().toString());
	         
	        response.setErrorMsg(th.getCause().toString());
		}
		return response;
	}

	public Response<String> viewByCustId(String id, String flag) {
		
		
		Response<String> response = new Response<>();
		String strMsg = "";
		
		boolean blnFirstguarantor = false;
		boolean blnSecondguarantor = false;
		
		int strId = 0 ;		
		String strSurname = "";
		String strFirstname = "";
		String strOthername = "";
		String strNatid = "";
		String strMobno = "";
		String strAdd = "";
		String strPadd = "";
		String strYrsinadd = "";
		String strOr = "";
		String strRent = "";
		String strNok = "";
		String strNokmobno = "";
		String strNokrshp = "";
		boolean blnNoka = false;
		String strBkregno = "";
		String strBkowner = "";
		boolean blnSal = false;
		String strEmpname = "";
		String strMnthin = "";
		String strOis = "";
		String strOi = "";
		String strStg = "";
		String strStgadd = "";
		boolean blnStgchconf = false;
		String strLc = "";
		
		String strDob = "";
		String strLlname = "";
		String strLlmbno = "";
		String strResiadrss = "";
		String strMaritalsts = "";
		String strLLFeedAbtRnt = "";
		String strNoOfYrsInArea = "";
		String strLc1chmnRecFeedbk = "";
		String strNearLndmrkorResi = "";
		String strEmpType = "";
		String strStgOrWrkadrssWithNearLNMRK = "";
		String strNoOfYrsInstgorBusi = "";
		String strSpouseName = "";
		String strSpouseMbno = "";
		String strYakaNum = "";
		String strYakaNoName = "";
		String strlc1number = "";
		String strstgorempno = "";
		String strmbnotiname = "";


		
		int strId2 = 0 ;		
		String strSurname2 = "";
		String strFirstname2 = "";
		String strOthername2 = "";
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
		String strBkregno2 = "";
		String strBkowner2 = "";
		boolean blnSal2 = false;
		String strEmpname2 = "";
		String strMnthin2 = "";
		String strOis2 = "";
		String strOi2 = "";
		String strStg2 = "";
		String strStgadd2 = "";
		boolean blnStgchconf2 = false;
		String strLc2 = "";
		String strRltnshp = "";
		String strRltnshp2 = "";
		
		String strDob2 = "";
		String strLlname2 = "";  
		String strLlmbno2 = "";
		String strResiadrss2 = "";
		String strMaritalsts2 = "";
		String strLLFeedAbtRnt2 = "";
		String strNoOfYrsInArea2 = "";
		String strLc1chmnRecFeedbk2 = "";
		String strNearLndmrkorResi2 = "";
		String strEmpType2 = "";
		String strStgOrWrkadrssWithNearLNMRK2 = "";
		String strNoOfYrsInstgorBusi2 = "";
		String strSpouseName2 = "";
		String strSpouseMbno2 = "";
		String strYakaNum2 = "";
		String strYakaNoName2 = "";
		String strlc1number2 = "";
		String strstgorempno2 = "";
		String strmbnotiname2 = "";
		

 String strcustid="";
 String strcusttype = "";

		
		String strCustomer = "";
		String strFlag = "";
		
		String strTvremarks = "";
		String strFiremarks = "";
		String strCqcremarks = "";					
		
		boolean blnTv = false;
		boolean blnFi = false;
		boolean blnCqc = false;
		
		String strTvremarks2 = "";
		String strFiremarks2 = "";
		String strCqcremarks2 = "";
		String strCoremarks = "";
		String strCoremarks2 = "";	
		String strCoapproval = "";
		String strCoapproval2 = "";	
		
		boolean blnTv2 = false;
		boolean blnFi2 = false;
		boolean blnCqc2 = false;
		
		String strTv = "";	
		String strFi = "";	
		String strCqc = "";	
		String strTv2 = "";	
		String strFi2 = "";	
		String strCqc2 = "";	
		
		int intId = 0;
		int intId2 = 0;
		
		strCustomer = id;
		strFlag = flag;
		
		if (strCustomer == null || strCustomer.length() == 0) {			
			strMsg = "Select customer id";
		} else {
			
			try {
				List<Guarantor> guarantor = guarantorRepo.findByCustomerIdAndFlag(strCustomer, strFlag);	
				
				if (guarantor.size() != 0) {
					for (Guarantor gr:guarantor) {
						blnFirstguarantor = gr.getFirstguarantor();
						blnSecondguarantor = gr.getSecondguarantor();
						if (blnFirstguarantor == true) {
							strId = gr.getId();
							strcustid = gr.getCustid();
							strSurname = gr.getSurname();
							strFirstname = gr.getFirstname();
							strOthername = gr.getOthername();
							strNatid = gr.getNationalid();
							strMobno = gr.getMobileno();
							strAdd = gr.getAddress();
							strPadd = gr.getPermanentaddress();
							strYrsinadd = gr.getYrsinaddress();
							strOr = gr.getOwnhouserented();
							strRent = gr.getRentpm();
//							strNok = gr.getNextofkin();
//							strNokmobno = gr.getNokmobileno();
//							strNokrshp = gr.getNokrelationship();
//							blnNoka = gr.getNokagreeing();
							strBkregno = gr.getBikeregno();
							strBkowner = gr.getBikeowner();
							blnSal = gr.getSalaried();
							strEmpname = gr.getEmployername();
							strMnthin = gr.getMonthlyincome();
							strOis = gr.getOis();
							strOi = gr.getOtherincome();
							strStg = gr.getStagename();
							strStgadd = gr.getStageaddress();
							blnStgchconf = gr.getStagechairconf();
							strLc = gr.getLcname();
							strRltnshp = gr.getRelationship();
							
							strDob = gr.getDob();
							strLlname = gr.getLlordname();
							strLlmbno = gr.getLlordmbno();
							strResiadrss = gr.getResiadrss();
							strMaritalsts = gr.getMaritalsts();
							strLLFeedAbtRnt = gr.getLlrentfeedbk();
							strNoOfYrsInArea = gr.getYrsinarea();
							strLc1chmnRecFeedbk = gr.getLc1chmrecfeed();
							strNearLndmrkorResi = gr.getNearlmarkresi();
							strEmpType = gr.getEmptype();
							strStgOrWrkadrssWithNearLNMRK = gr.getStgwrkadrssnearlmark();
							strNoOfYrsInstgorBusi = gr.getYrsinstgorbusi();
							strSpouseName = gr.getSpousename();
							strSpouseMbno = gr.getSpouseno();
							strYakaNum = gr.getYakanum();
							strYakaNoName = gr.getYakanumname();
							strlc1number = gr.getLcmobile();
							strstgorempno = gr.getStagechairmobile();
							strmbnotiname = gr.getMbnonotinname();

							
							strTvremarks = gr.getTvremarks();
							strFiremarks = gr.getFiremarks();
							strCqcremarks = gr.getCqcremarks();					
							
							blnTv = gr.getTvverified();
							blnFi = gr.getFiverified();
							blnCqc = gr.getCqc();
							
							strCoremarks = gr.getCoremarks();							
							strCoapproval = gr.getCoapproval();		
							
							 CustomerDetails custtype = customerRepo.findByotherid(strcustid);
							 if (custtype !=null) {
								 strcusttype = custtype.getCusttype();
							 }
	
						} else if (blnSecondguarantor == true) {
							strId2 = gr.getId();
							strcustid = gr.getCustid();
							strSurname2 = gr.getSurname();
							strFirstname2 = gr.getFirstname();
							strOthername2 = gr.getOthername();
							strNatid2 = gr.getNationalid();
							strMobno2 = gr.getMobileno();
							strAdd2 = gr.getAddress();
							strPadd2 = gr.getPermanentaddress();
							strYrsinadd2 = gr.getYrsinaddress();
							strOr2 = gr.getOwnhouserented();
							strRent2 = gr.getRentpm();
//							strNok2 = gr.getNextofkin();
//							strNokmobno2 = gr.getNokmobileno();
//							strNokrshp2 = gr.getNokrelationship();
//							blnNoka2 = gr.getNokagreeing();
							strBkregno2 = gr.getBikeregno();
							strBkowner2 = gr.getBikeowner();
							blnSal2 = gr.getSalaried();
							strEmpname2 = gr.getEmployername();
							strMnthin2 = gr.getMonthlyincome();
							strOis2 = gr.getOis();
							strOi2 = gr.getOtherincome();
							strStg2 = gr.getStagename();
							strStgadd2 = gr.getStageaddress();
							blnStgchconf2 = gr.getStagechairconf();
							strLc2 = gr.getLcname();
							strRltnshp2 = gr.getRelationship();
							
							strDob2 = gr.getDob();
							strLlname2 = gr.getLlordname();
							strLlmbno2 = gr.getLlordmbno();
							strResiadrss2 = gr.getResiadrss();
							strMaritalsts2 = gr.getMaritalsts();
							strLLFeedAbtRnt2 = gr.getLlrentfeedbk();
							strNoOfYrsInArea2 = gr.getYrsinarea();
							strLc1chmnRecFeedbk2 = gr.getLc1chmrecfeed();
							strNearLndmrkorResi2 = gr.getNearlmarkresi();
							strEmpType2 = gr.getEmptype();
							strStgOrWrkadrssWithNearLNMRK2 = gr.getStgwrkadrssnearlmark();
							strNoOfYrsInstgorBusi2 = gr.getYrsinstgorbusi();
							strSpouseName2 = gr.getSpousename();
							strSpouseMbno2 = gr.getSpouseno();
							strYakaNum2 = gr.getYakanum();
							strYakaNoName2 = gr.getYakanumname();
							strlc1number2 = gr.getLcmobile();
							strstgorempno2 = gr.getStagechairmobile();
							strmbnotiname2 = gr.getMbnonotinname();
							
							strTvremarks2 = gr.getTvremarks();
							strFiremarks2 = gr.getFiremarks();
							strCqcremarks2 = gr.getCqcremarks();					
							
							blnTv2 = gr.getTvverified();
							blnFi2 = gr.getFiverified();
							blnCqc2 = gr.getCqc();
							
							strCoremarks2 = gr.getCoremarks();
							strCoapproval2 = gr.getCoapproval();
							
							 CustomerDetails custtype = customerRepo.findByotherid(strcustid);
							 if (custtype !=null) {
								 strcusttype = custtype.getCusttype();
							 }
							
						}
						blnFirstguarantor = false;
						blnSecondguarantor = false;
					}	
					
					if (blnTv == true) {strTv = "Recommended";} else {strTv = "Not Recommended";}
					if (blnFi == true) {strFi = "Recommended";} else {strFi = "Not Recommended";}
					if (blnCqc == true) {strCqc = "Recommended";} else {strCqc = "Not Recommended";}
					
					if (blnTv2 == true) {strTv2 = "Recommended";} else {strTv2 = "Not Recommended";}
					if (blnFi2 == true) {strFi2 = "Recommended";} else {strFi2 = "Not Recommended";}
					if (blnCqc2 == true) {strCqc2 = "Recommended";} else {strCqc2 = "Not Recommended";}
										
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setErrorMsg("Error retrieving guarantor for customer id: " + strCustomer);
			}
		}
		
		 response.setData( strId + "|" + strSurname + "|" + strFirstname + "|" + strOthername + "|"
				+ strNatid + "|" + strMobno + "|" + strAdd + "|" + strPadd + "|" + strYrsinadd + "|" + strOr + "|" + strRent + "|"
				+ strNok + "|" + strNokmobno + "|" + strNokrshp + "|" + blnNoka + "|" + strBkregno + "|" + strBkowner + "|" + blnSal + "|"
				+ strEmpname + "|" + strMnthin + "|" + strOis + "|" + strOi + "|" + strStg + "|" + strStgadd + "|" + blnStgchconf + "|" + strLc + "|" + strRltnshp + "|"
				+ strDob + "|" + strLlname + "|" + strLlmbno + "|" + strResiadrss + "|" + strMaritalsts + "|" +strLLFeedAbtRnt + "|" + strNoOfYrsInArea + "|" + strLc1chmnRecFeedbk + "|"
				+ strNearLndmrkorResi + "|" + strEmpType + "|" + strStgOrWrkadrssWithNearLNMRK + "|" + strNoOfYrsInstgorBusi + "|" + strSpouseName + "|" + strSpouseMbno + "|" + strYakaNum + "|" 
				+ strYakaNoName + "|" + strlc1number + "|" + strstgorempno + "|" + strmbnotiname + "|"
				+ strTv + "|" + strFi + "|" + strCqc + "|" + strTvremarks + "|" + strFiremarks + "|" + strCoapproval + "|" +strCoremarks + "|"
				+ strCqcremarks + "|"
				+ strId2 + "|" + strSurname2 + "|" + strFirstname2 + "|" + strOthername2 + "|"
				+ strNatid2 + "|" + strMobno2 + "|" + strAdd2 + "|" + strPadd2 + "|" + strYrsinadd2 + "|" + strOr2 + "|" + strRent2 + "|"
				+ strNok2 + "|" + strNokmobno2 + "|" + strNokrshp2 + "|" + blnNoka2 + "|" + strBkregno2 + "|" + strBkowner2 + "|" + blnSal2 + "|"
				+ strEmpname2 + "|" + strMnthin2 + "|" + strOis2 + "|" + strOi2 + "|" + strStg2 + "|" + strStgadd2 + "|" + blnStgchconf2 + "|" + strLc2 + "|" + strRltnshp2 + "|"	
				+ strDob2 + "|" + strLlname2 + "|" + strLlmbno2 + "|" + strResiadrss2 + "|" + strMaritalsts2 + "|" + strLLFeedAbtRnt2 + "|" + strNoOfYrsInArea2 + "|"
				+ strLc1chmnRecFeedbk2 + "|" + strNearLndmrkorResi2 + "|" + strEmpType2 + "|" + strStgOrWrkadrssWithNearLNMRK2 + "|" + strNoOfYrsInstgorBusi2 + "|" 
				+ strSpouseName2 + "|" + strSpouseMbno2 + "|" + strYakaNum2 + "|" + strYakaNoName2 + "|" + strlc1number2 + "|" + strstgorempno2 + "|" + strmbnotiname2 + "|"
				+ strTv2 + "|" + strFi2 + "|" + strCqc2 + "|" + strTvremarks2 + "|" + strFiremarks2 + "|" + strCoapproval2 + "|" + strCoremarks2 + "|"
				+ strCqcremarks2 + "|"  + strcusttype);
		
		
		
		 
		return response ;
	}

	public Response<String> viewByGuaranId(String id) {
		
		Response<String> response = new Response<>();
		
		String strMsg = "";
		String strQuery = "";
		
		int intGuaranid = 0;
		int intId = 0;
		
		String strCustomerid = "";
		String strGuaranid = "";
		String strSurname = "";
		String strFirstname = "";
		String strOthername = "";
		String strGuarantortype = "";		
		String strMobileno = "";
		String strAdd= "";
		String strPadd = "";
		String strYrsadd = "";
		String strOwnrented = "";
		String strRent = "";
		String strNok = "";
		String strNokmobile = "";
		String strNokrship = "";
		String strNoka = "";
		String strBkregno = "";
		String strBkowner = "";
		String strSal = "";	    	
		String strEmpname = "";
		String strMnthincome = "";
		String strOis = "";
		String strOincome = "";
		String strStgname = "";
		String strStgadd = "";
		String strStgcname = "";
		String strStgcmob = "";
		String strLc = "";
		String strLcmob = "";			
		String strScc= "";
		String strcusttype= "";
		
		String strDob = "";  
		String strLlname = "";  
		String strLlmbno = "";  
		String strResiadrss = "";  
		String strMaritalsts = "";  
		String strLLFeedAbtRnt = "";  
		String strNoOfYrsInArea = "";  
		String strLc1chmnRecFeedbk = "";  
		String strNearLndmrkorResi = "";  
		String strEmpType = "";  
		String strStgOrWrkadrssWithNearLNMRK = "";  
		String strNoOfYrsInstgorBusi = "";  
		String strSpouseName = "";  
		String strSpouseMbno = "";  
		String strYakaNum = "";  
		String strYakaNoName = "";  
		
		String stMbNumNotinName = "";

		
	
		boolean blnFirstguarantor = false;
		boolean blnSecondguarantor = false;
		boolean blnCqc = false;
		boolean blnNoka = false;
		boolean blnSalaried = false;
		boolean blnScc = false;
		
		strGuaranid = id;
		intGuaranid = Integer.parseInt(strGuaranid);
		
		if (strGuaranid == null || strGuaranid.length() == 0) {			
			strMsg = "Please select guarantor";
		} else {		
			try {
				Optional<Guarantor> opGuarantor = guarantorRepo.findById(intGuaranid);
				
				if(opGuarantor.isPresent()) {	
					Guarantor guaran = opGuarantor.get();
					strCustomerid = guaran.getCustid();
					strGuaranid = guaran.getNationalid();
					strSurname = guaran.getSurname();
					strFirstname = guaran.getFirstname();
					strOthername = guaran.getOthername();
					
					blnFirstguarantor = guaran.getFirstguarantor();
					blnSecondguarantor = guaran.getSecondguarantor();
					
					 CustomerDetails custtype = customerRepo.findByotherid(strCustomerid);
					 if (custtype !=null) {
						 strcusttype = custtype.getCusttype();
					 }
					if (blnFirstguarantor == true) {
						strGuarantortype = "1st Guarantor";
					} else if (blnSecondguarantor == true) {
						strGuarantortype = "2nd Guarantor";
					}
					
					strMobileno = guaran.getMobileno();
					strAdd= guaran.getAddress();
					strPadd = guaran.getPermanentaddress();
					strYrsadd = guaran.getYrsinaddress();
					strOwnrented = guaran.getOwnhouserented();
					strRent = guaran.getRentpm();
//					strNok = guaran.getNextofkin();
//					strNokmobile = guaran.getNokmobileno();
//					strNokrship = guaran.getNokrelationship();
//					blnNoka = guaran.getNokagreeing();
					
//					if (blnNoka == true) {
//						strNoka = "Yes";
//					} else {
//						strNoka = "No";
//					}
					
					strBkregno = guaran.getBikeregno();
					strBkowner = guaran.getBikeowner();
					blnSalaried = guaran.getSalaried();
					
					if (blnSalaried == true) {
						strSal = "Yes";
					} else {
						strSal = "No";
					}
					
					strEmpname = guaran.getEmployername();
					strMnthincome = guaran.getMonthlyincome();
					strOis = guaran.getOis();
					strOincome = guaran.getOtherincome();
					strStgname = guaran.getStagename();
					strStgadd = guaran.getStageaddress();
					strStgcname = guaran.getStagechairname();
					strStgcmob = guaran.getStagechairmobile();
					strLc = guaran.getLcname();
					strLcmob = guaran.getLcmobile();
					blnScc= guaran.getStagechairconf();
					
					if (blnScc == true) {
						strScc = "Yes";
					} else {
						strScc = "No";
					}
					
					strDob = guaran.getDob();  
					strLlname = guaran.getLlordname();  
					strLlmbno = guaran.getLlordmbno();  
					strResiadrss = guaran.getResiadrss();  
					strMaritalsts = guaran.getMaritalsts();  
					strLLFeedAbtRnt = guaran.getLlrentfeedbk();  
					strNoOfYrsInArea = guaran.getYrsinarea();  
					strLc1chmnRecFeedbk = guaran.getLc1chmrecfeed();  
					strNearLndmrkorResi = guaran.getNearlmarkresi();  
					strEmpType = guaran.getEmptype();  
					strStgOrWrkadrssWithNearLNMRK = guaran.getStgwrkadrssnearlmark();  
					strNoOfYrsInstgorBusi = guaran.getYrsinstgorbusi();  
					strSpouseName = guaran.getSpousename();  
					strSpouseMbno = guaran.getSpouseno();  
					strYakaNum = guaran.getYakanum();  
					strYakaNoName = guaran.getYakanumname(); 
					stMbNumNotinName =guaran.getMbnonotinname();

				}						
								
				response.setData(strGuaranid + "|" +  strSurname + "|" +  strFirstname + "|" +  strOthername + "|" +  
						strGuarantortype + "|" +  strMobileno + "|" +  strAdd + "|" +  strPadd + "|" +  
						strYrsadd + "|" + strOwnrented + "|" +  strRent + "|" +  strNok + "|" +
						strNokmobile + "|" + strNokrship + "|" +  strNoka + "|" +  strBkregno + "|" +
						strBkowner + "|" + strSal + "|" +  strEmpname + "|" +  strMnthincome + "|" +
						strOis + "|" + strOincome + "|" +  strLc + "|" +  strLcmob + "|" +  strStgname + "|" +  
						strStgadd + "|" + strScc + "|" + strStgcname + "|"+ strStgcmob + "|" + strCustomerid + "|" + strcusttype + "|" +
						strDob + "|" + strLlname + "|" + strLlmbno + "|" + strResiadrss + "|" + strMaritalsts + "|" +
						strLLFeedAbtRnt + "|" + strNoOfYrsInArea + "|" + strLc1chmnRecFeedbk + "|" + strNearLndmrkorResi + "|" + 
						strEmpType + "|" + strStgOrWrkadrssWithNearLNMRK + "|" + strNoOfYrsInstgorBusi + "|" + strSpouseName + "|" + 
						strSpouseMbno + "|" + strYakaNum + "|" + strYakaNoName + "|" + stMbNumNotinName);
				
				
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error retreiving details for guarantor: " + strGuaranid);
			}			
		}
		
		return response;
	}
	

	public Response<String> viewCustomerStatus(String custId, String flag) {
		
		
		String strCustid = ""; 
		String strFlag = "";
		String strMsg = "";
		String strMsg1 = "";
		String strMsg2 = "";
		String strMsg3 = "";
		String strMsg4 = "";
		String strMsg5 = "";
		String strMsg6 = "";
		
		String strSurnameremark = "";
		String strSurnameflag = "false";		 
		String strfnameremark = ""; 
		String strfnameflag = "false";		
		String stronameremark = "";
		String stronameflag = "false";		
		String strmsremark = "";
		String strmsflag = "false";		
		String strsexremark = "";
		String strsexflag = "false";		
		String strmobnoremark = "";
		String strmobnoflag = "false";		
		String strstgnmremark = "";
		String strstgnmflag = "false";		
		String strdistremark = "";
		String strdistflag = "false";		
		String strlcnmremark = "";
		String strlcnmflag = "false";		
		String strparremark = "";
		String strparflag = "false";	
		String straddremark = "";
		String straddflag = "false";
		String strnidremark = "";
		String strnidflag = "false";		
		String strbregnoremark = "";
		String strbregnoflag = "false";		
		String strbuseremark = "";
		String strbuseflag = "false";		
		String strdobremark = "";
		String strdobflag = "false";		
		String strcntremark = "X";
		String strcntflag = "false";		
		String strscntremark = "";
		String strscntflag = "false";		
		String strvilremark = "";
		String strvilflag = "false";		
		String stryrvilremark = "";
		String stryrvilflag = "false";		
		String strnokremark = "";
		String strnokflag = "false";		
		String strnokmremark = "";
		String strnokmflag = "false";		
		String strnokrremark = "";
		String strnokrflag = "false";		
		String strnokaremark = "";
		String strnokaflag = "false";		
		String strdlremark = "";
		String strdlflag = "false";		
		String strnatremark = "";
		String strnatflag = "false";		
		String strnodremark = "";
		String strnodflag = "false";		
		String strorremark = "";
		String strorflag = "false";		
		String strllremark = "";
		String strllflag = "false";		
		String strllmobremark = "";
		String strllmobflag = "false";		
		String strrpmremark = "";
		String strrpmflag = "false";		
		String stroisremark = "";
		String stroisflag = "false";		
		String strdpsremark = "";
		String strdpsflg = "false";		
		String strpaddremark = "";
		String strpaddflag = "false";		
		String strfathremark = "X";
		String strfathflag = "false";		
		String strmothremark = "";
		String strmothflag = "false";		
		String strnpsremark = "";
		String strnpsflag = "false";		
		String strlcnmobnoremark = "";
		String strlcnmobnoflag = "false";		
		String strcusttyperemark = "";
		String strcusttypeflag = "false";		
		String strbownerremark = "";
		String strbownerflag = "false";
		String strsalremark = "";
		String strsalflag = "false";
		String strmiremark = "";
		String strmiflag = "false";
		String stroiremark = "";
		String stroiflag = "false";
		String strstgaddremark = "";
		String strstgaddflag = "false";
		String strstgccremark = "";
		String strstgccflag = "false";
		String strrltnshpremark = "";
		String strrltnshpflag = "false";
		
		String strtvDobRemarks = "";
		String strtvDobFlag = "false";
		String strtvLlnameRemarks = "";
		String strtvLlnameFlag = "false";
		String strtvLlmbnoRemarks = "";
		String strtvLlmbnoFlag = "false";
		String strtvResiadrssRemarks = "";
		String strtvResiadrssFlag = "false";
		String strtvMaritalstsRemarks = "";
		String strtvMaritalstsFlag = "false";
		String strtvLLFeedAbtRntRemarks = "";
		String strtvLLFeedAbtRntFlag = "false";
		String strtvNoOfYrsInAreaRemarks = "";
		String strtvNoOfYrsInAreaFlag = "false";
		String strtvLc1chmnRecFeedbkRemarks = "";
		String strtvLc1chmnRecFeedbkFlag = "false";
		String strtvNearLndmrkorResiRemarks = "";
		String strtvNearLndmrkorResiFlag = "false";
		String strtvEmpTypeRemarks = "";
		String strtvEmpTypeFlag = "false";
		String strtvStgOrWrkadrssWithNearLNMRKRemarks = "";
		String strtvStgOrWrkadrssWithNearLNMRKFlag = "false";
		String strtvNoOfYrsInstgorBusiRemarks = "";
		String strtvNoOfYrsInstgorBusiFlag = "false";
		String strtvSpouseNameRemarks = "";
		String strtvSpouseNameFlag = "false";
		String strtvSpouseMbnoRemarks = "";
		String strtvSpouseMbnoFlag = "false";
		String strtvYakaNumRemarks = "";
		String strtvYakaNumFlag = "false";
		String strtvYakaNoNameRemarks = "";
		String strtvYakaNoNameFlag = "false";
		String strtvLc1numberRemarks = "";
		String strtvLc1numberFlag = "false";
		String strtvStgorempnoRemarks = "";
		String strtvStgorempnoFlag = "false";
		String strtvMbnumnotinnameRemarks = "";
		String strtvMbnumnotinnameFlag = "false";

		
		String strfiSurnameremark = "";
		String strfiSurnameflag = "false";		 
		String strfifnameremark = ""; 
		String strfifnameflag = "false";		
		String strfionameremark = "";
		String strfionameflag = "false";		
		String strfimsremark = "";
		String strfimsflag = "false";		
		String strfisexremark = "";
		String strfisexflag = "false";		
		String strfimobnoremark = "";
		String strfimobnoflag = "false";		
		String strfistgnmremark = "";
		String strfistgnmflag = "false";		
		String strfidistremark = "";
		String strfidistflag = "false";		
		String strfilcnmremark = "";
		String strfilcnmflag = "false";		
		String strfiparremark = "";
		String strfiparflag = "false";	
		String strfiaddremark = "";
		String strfiaddflag = "false";
		String strfinidremark = "";
		String strfinidflag = "false";		
		String strfibregnoremark = "";
		String strfibregnoflag = "false";		
		String strfibuseremark = "";
		String strfibuseflag = "false";		
		String strfidobremark = "";
		String strfidobflag = "false";		
		String strficntremark = "";
		String strficntflag = "false";		
		String strfiscntremark = "";
		String strfiscntflag = "false";		
		String strfivilremark = "";
		String strfivilflag = "false";		
		String strfiyrvilremark = "";
		String strfiyrvilflag = "false";		
		String strfinokremark = "";
		String strfinokflag = "false";		
		String strfinokmremark = "";
		String strfinokmflag = "false";		
		String strfinokrremark = "";
		String strfinokrflag = "false";		
		String strfinokaremark = "";
		String strfinokaflag = "false";		
		String strfidlremark = "";
		String strfidlflag = "false";		
		String strfinatremark = "";
		String strfinatflag = "false";		
		String strfinodremark = "";
		String strfinodflag = "false";		
		String strfiorremark = "";
		String strfiorflag = "false";		
		String strfillremark = "";
		String strfillflag = "false";		
		String strfillmobremark = "";
		String strfillmobflag = "false";		
		String strfirpmremark = "";
		String strfirpmflag = "false";		
		String strfioisremark = "";
		String strfioisflag = "false";		
		String strfidpsremark = "";
		String strfidpsflg = "false";		
		String strfipaddremark = "";
		String strfipaddflag = "false";		
		String strfifathremark = "";
		String strfifathflag = "false";		
		String strfimothremark = "";
		String strfimothflag = "false";		
		String strfinpsremark = "";
		String strfinpsflag = "false";		
		String strfilcnmobnoremark = "";
		String strfilcnmobnoflag = "false";		
		String strficusttyperemark = "";
		String strficusttypeflag = "false";
		String strfibownerremark = "";
		String strfibownerflag = "false";
		String strfisalremark = "";
		String strfisalflag = "false";
		String strfimiremark = "";
		String strfimiflag = "false";
		String strfioiremark = "";
		String strfioiflag = "false";
		String strfistgaddremark = "";
		String strfistgaddflag = "false";
		String strfistgccremark = "";
		String strfistgccflag = "false";
		String strfirltnshpremark = "";
		String strfirltnshpflag = "false";
		
		String strfiDobRemarks = "";
		String strfiDobFlag = "false";
		String strfiLlnameRemarks = "";
		String strfiLlnameFlag = "false";
		String strfiLlmbnoRemarks = "";
		String strfiLlmbnoFlag = "false";
		String strfiResiadrssRemarks = "";
		String strfiResiadrssFlag = "false";
		String strfiMaritalstsRemarks = "";
		String strfiMaritalstsFlag = "false";
		String strfiLLFeedAbtRntRemarks = "";
		String strfiLLFeedAbtRntFlag = "false";
		String strfiNoOfYrsInAreaRemarks = "";
		String strfiNoOfYrsInAreaFlag = "false";
		String strfiLc1chmnRecFeedbkRemarks = "";
		String strfiLc1chmnRecFeedbkFlag = "false";
		String strfiNearLndmrkorResiRemarks = "";
		String strfiNearLndmrkorResiFlag = "false";
		String strfiEmpTypeRemarks = "";
		String strfiEmpTypeFlag = "false";
		String strfiStgOrWrkadrssWithNearLNMRKRemarks = "";
		String strfiStgOrWrkadrssWithNearLNMRKFlag = "false";
		String strfiNoOfYrsInstgorBusiRemarks = "";
		String strfiNoOfYrsInstgorBusiFlag = "false";
		String strfiSpouseNameRemarks = "";
		String strfiSpouseNameFlag = "false";
		String strfiSpouseMbnoRemarks = "";
		String strfiSpouseMbnoFlag = "false";
		String strfiYakaNumRemarks = "";
		String strfiYakaNumFlag = "false";
		String strfiYakaNoNameRemarks = "";
		String strfiYakaNoNameFlag = "false";
		String strfiLc1numberRemarks = "";
		String strfiLc1numberFlag = "false";
		String strfiStgorempnoRemarks = "";
		String strfiStgorempnoFlag = "false";
		String strfiMbnumnotinnameRemarks = "";
		String strfiMbnumnotinnameFlag = "false";


		
		String guatwoStrSurnameremark = "";
		String guatwoStrSurnameflag = "false";		 
		String guatwoStrfnameremark = ""; 
		String guatwoStrfnameflag = "false";		
		String guatwoStronameremark = "";
		String guatwoStronameflag = "false";		
		String guatwoStrmsremark = "";
		String guatwoStrmsflag = "false";		
		String guatwoStrsexremark = "";
		String guatwoStrsexflag = "false";		
		String guatwoStrmobnoremark = "";
		String guatwoStrmobnoflag = "false";		
		String guatwoStrstgnmremark = "";
		String guatwoStrstgnmflag = "false";		
		String guatwoStrdistremark = "";
		String guatwoStrdistflag = "false";		
		String guatwoStrlcnmremark = "";
		String guatwoStrlcnmflag = "false";		
		String guatwoStrparremark = "";
		String guatwoStrparflag = "false";	
		String guatwoStraddremark = "";
		String guatwoStraddflag = "false";
		String guatwoStrnidremark = "";
		String guatwoStrnidflag = "false";		
		String guatwoStrbregnoremark = "";
		String guatwoStrbregnoflag = "false";		
		String guatwoStrbuseremark = "";
		String guatwoStrbuseflag = "false";		
		String guatwoStrdobremark = "";
		String guatwoStrdobflag = "false";		
		String guatwoStrcntremark = "";
		String guatwoStrcntflag = "false";		
		String guatwoStrscntremark = "";
		String guatwoStrscntflag = "false";		
		String guatwoStrvilremark = "";
		String guatwoStrvilflag = "false";		
		String guatwoStryrvilremark = "";
		String guatwoStryrvilflag = "false";		
		String guatwoStrnokremark = "";
		String guatwoStrnokflag = "false";		
		String guatwoStrnokmremark = "";
		String guatwoStrnokmflag = "false";		
		String guatwoStrnokrremark = "";
		String guatwoStrnokrflag = "false";		
		String guatwoStrnokaremark = "";
		String guatwoStrnokaflag = "false";		
		String guatwoStrdlremark = "";
		String guatwoStrdlflag = "false";		
		String guatwoStrnatremark = "";
		String guatwoStrnatflag = "false";		
		String guatwoStrnodremark = "";
		String guatwoStrnodflag = "false";		
		String guatwoStrorremark = "";
		String guatwoStrorflag = "false";		
		String guatwoStrllremark = "";
		String guatwoStrllflag = "false";		
		String guatwoStrllmobremark = "";
		String guatwoStrllmobflag = "false";		
		String guatwoStrrpmremark = "";
		String guatwoStrrpmflag = "false";		
		String guatwoStroisremark = "";
		String guatwoStroisflag = "false";		
		String guatwoStrdpsremark = "";
		String guatwoStrdpsflg = "false";		
		String guatwoStrpaddremark = "";
		String guatwoStrpaddflag = "false";		
		String guatwoStrfathremark = "";
		String guatwoStrfathflag = "false";		
		String guatwoStrmothremark = "";
		String guatwoStrmothflag = "false";		
		String guatwoStrnpsremark = "";
		String guatwoStrnpsflag = "false";		
		String guatwoStrlcnmobnoremark = "";
		String guatwoStrlcnmobnoflag = "false";		
		String guatwoStrcusttyperemark = "X";
		String guatwoStrcusttypeflag = "false";		
		String guatwoStrbownerremark = "";
		String guatwoStrbownerflag = "false";
		String guatwoStrsalremark = "";
		String guatwoStrsalflag = "false";
		String guatwoStrmiremark = "";
		String guatwoStrmiflag = "false";
		String guatwoStroiremark = "";
		String guatwoStroiflag = "false";
		String guatwoStrstgaddremark = "";
		String guatwoStrstgaddflag = "false";
		String guatwoStrstgccremark = "";
		String guatwoStrstgccflag = "false";
		String guatwoStrrltnshpremark = "";
		String guatwoStrrltnshpflag = "false";
		
		String guatwostrtvDobRemarks = "";
		String guatwostrtvDobFlag = "false";
		String guatwostrtvLlnameRemarks = "";
		String guatwostrtvLlnameFlag = "false";
		String guatwostrtvLlmbnoRemarks = "";
		String guatwostrtvLlmbnoFlag = "false";
		String guatwostrtvResiadrssRemarks = "";
		String guatwostrtvResiadrssFlag = "false";
		String guatwostrtvMaritalstsRemarks = "";
		String guatwostrtvMaritalstsFlag = "false";
		String guatwostrtvLLFeedAbtRntRemarks = "";
		String guatwostrtvLLFeedAbtRntFlag = "false";
		String guatwostrtvNoOfYrsInAreaRemarks = "";
		String guatwostrtvNoOfYrsInAreaFlag = "false";
		String guatwostrtvLc1chmnRecFeedbkRemarks = "";
		String guatwostrtvLc1chmnRecFeedbkFlag = "false";
		String guatwostrtvNearLndmrkorResiRemarks = "";
		String guatwostrtvNearLndmrkorResiFlag = "false";
		String guatwostrtvEmpTypeRemarks = "";
		String guatwostrtvEmpTypeFlag = "false";
		String guatwostrtvStgOrWrkadrssWithNearLNMRKRemarks = "";
		String guatwostrtvStgOrWrkadrssWithNearLNMRKFlag = "false";
		String guatwostrtvNoOfYrsInstgorBusiRemarks = "";
		String guatwostrtvNoOfYrsInstgorBusiFlag = "false";
		String guatwostrtvSpouseNameRemarks = "";
		String guatwostrtvSpouseNameFlag = "false";
		String guatwostrtvSpouseMbnoRemarks = "";
		String guatwostrtvSpouseMbnoFlag = "false";
		String guatwostrtvYakaNumRemarks = "";
		String guatwostrtvYakaNumFlag = "false";
		String guatwostrtvYakaNoNameRemarks = "";
		String guatwostrtvYakaNoNameFlag = "false";
		String guatwostrtvLc1numberRemarks = "";
		String guatwostrtvLc1numberFlag = "false";
		String guatwostrtvStgorempnoRemarks = "";
		String guatwostrtvStgorempnoFlag = "false";
		String guatwostrtvMbnumnotinnameRemarks = "";
		String guatwostrtvMbnumnotinnameFlag = "false";



		String guatwoStrfiSurnameremark = "";
		String guatwoStrfiSurnameflag = "false";		 
		String guatwoStrfifnameremark = ""; 
		String guatwoStrfifnameflag = "false";		
		String guatwoStrfionameremark = "";
		String guatwoStrfionameflag = "false";		
		String guatwoStrfimsremark = "";
		String guatwoStrfimsflag = "false";		
		String guatwoStrfisexremark = "";
		String guatwoStrfisexflag = "false";		
		String guatwoStrfimobnoremark = "";
		String guatwoStrfimobnoflag = "false";		
		String guatwoStrfistgnmremark = "";
		String guatwoStrfistgnmflag = "false";		
		String guatwoStrfidistremark = "";
		String guatwoStrfidistflag = "false";		
		String guatwoStrfilcnmremark = "";
		String guatwoStrfilcnmflag = "false";		
		String guatwoStrfiparremark = "";
		String guatwoStrfiparflag = "false";	
		String guatwoStrfiaddremark = "";
		String guatwoStrfiaddflag = "false";
		String guatwoStrfinidremark = "";
		String guatwoStrfinidflag = "false";		
		String guatwoStrfibregnoremark = "";
		String guatwoStrfibregnoflag = "false";		
		String guatwoStrfibuseremark = "";
		String guatwoStrfibuseflag = "false";		
		String guatwoStrfidobremark = "";
		String guatwoStrfidobflag = "false";		
		String guatwoStrficntremark = "";
		String guatwoStrficntflag = "false";		
		String guatwoStrfiscntremark = "";
		String guatwoStrfiscntflag = "false";		
		String guatwoStrfivilremark = "";
		String guatwoStrfivilflag = "false";		
		String guatwoStrfiyrvilremark = "";
		String guatwoStrfiyrvilflag = "false";		
		String guatwoStrfinokremark = "";
		String guatwoStrfinokflag = "false";		
		String guatwoStrfinokmremark = "";
		String guatwoStrfinokmflag = "false";		
		String guatwoStrfinokrremark = "";
		String guatwoStrfinokrflag = "false";		
		String guatwoStrfinokaremark = "";
		String guatwoStrfinokaflag = "false";		
		String guatwoStrfidlremark = "";
		String guatwoStrfidlflag = "false";		
		String guatwoStrfinatremark = "";
		String guatwoStrfinatflag = "false";		
		String guatwoStrfinodremark = "";
		String guatwoStrfinodflag = "false";		
		String guatwoStrfiorremark = "";
		String guatwoStrfiorflag = "false";		
		String guatwoStrfillremark = "";
		String guatwoStrfillflag = "false";		
		String guatwoStrfillmobremark = "";
		String guatwoStrfillmobflag = "false";		
		String guatwoStrfirpmremark = "";
		String guatwoStrfirpmflag = "false";		
		String guatwoStrfioisremark = "";
		String guatwoStrfioisflag = "false";		
		String guatwoStrfidpsremark = "";
		String guatwoStrfidpsflg = "false";		
		String guatwoStrfipaddremark = "";
		String guatwoStrfipaddflag = "false";		
		String guatwoStrfifathremark = "";
		String guatwoStrfifathflag = "false";		
		String guatwoStrfimothremark = "";
		String guatwoStrfimothflag = "false";		
		String guatwoStrfinpsremark = "";
		String guatwoStrfinpsflag = "false";		
		String guatwoStrfilcnmobnoremark = "";
		String guatwoStrfilcnmobnoflag = "false";		
		String guatwoStrficusttyperemark = "";
		String guatwoStrficusttypeflag = "false";		
		String guatwoStrfibownerremark = "";
		String guatwoStrfibownerflag = "false";
		String guatwoStrfisalremark = "";
		String guatwoStrfisalflag = "false";
		String guatwoStrfimiremark = "";
		String guatwoStrfimiflag = "false";
		String guatwoStrfioiremark = "";
		String guatwoStrfioiflag = "false";
		String guatwoStrfistgaddremark = "";
		String guatwoStrfistgaddflag = "false";
		String guatwoStrfistgccremark = "";
		String guatwoStrfistgccflag = "false";
		String guatwoStrfirltnshpremark = "";
		String guatwoStrfirltnshpflag = "false";
		
		String guatwostrfiDobRemarks = "";
		String guatwostrfiDobFlag = "false";
		String guatwostrfiLlnameRemarks = "";
		String guatwostrfiLlnameFlag = "false";
		String guatwostrfiLlmbnoRemarks = "";
		String guatwostrfiLlmbnoFlag = "false";
		String guatwostrfiResiadrssRemarks = "";
		String guatwostrfiResiadrssFlag = "false";
		String guatwostrfiMaritalstsRemarks = "";
		String guatwostrfiMaritalstsFlag = "false";
		String guatwostrfiLLFeedAbtRntRemarks = "";
		String guatwostrfiLLFeedAbtRntFlag = "false";
		String guatwostrfiNoOfYrsInAreaRemarks = "";
		String guatwostrfiNoOfYrsInAreaFlag = "false";
		String guatwostrfiLc1chmnRecFeedbkRemarks = "";
		String guatwostrfiLc1chmnRecFeedbkFlag = "false";
		String guatwostrfiNearLndmrkorResiRemarks = "";
		String guatwostrfiNearLndmrkorResiFlag = "false";
		String guatwostrfiEmpTypeRemarks = "";
		String guatwostrfiEmpTypeFlag = "false";
		String guatwostrfiStgOrWrkadrssWithNearLNMRKRemarks = "";
		String guatwostrfiStgOrWrkadrssWithNearLNMRKFlag = "false";
		String guatwostrfiNoOfYrsInstgorBusiRemarks = "";
		String guatwostrfiNoOfYrsInstgorBusiFlag = "false";
		String guatwostrfiSpouseNameRemarks = "";
		String guatwostrfiSpouseNameFlag = "false";
		String guatwostrfiSpouseMbnoRemarks = "";
		String guatwostrfiSpouseMbnoFlag = "false";
		String guatwostrfiYakaNumRemarks = "";
		String guatwostrfiYakaNumFlag = "false";
		String guatwostrfiYakaNoNameRemarks = "";
		String guatwostrfiYakaNoNameFlag = "false";
		String guatwostrfiLc1numberRemarks = "";
		String guatwostrfiLc1numberFlag = "false";
		String guatwostrfiStgorempnoRemarks = "";
		String guatwostrfiStgorempnoFlag = "false";
		String guatwostrfiMbnumnotinnameRemarks = "";
		String guatwostrfiMbnumnotinnameFlag = "false";

		
		String strQuery = "";
		boolean blnFirstguarantor = false;
		boolean blnSecondguarantor = false;
		String strId = "";
		String strId2 = "";
		
		int intId = 0;
		int intId2 = 0;
		
		Response<String> response = new Response<>();
			
		strCustid = custId;
		strFlag = flag;
		
		if (strCustid == null || strCustid.length() == 0) {			
			strMsg = "Select customer id";
		} else {
			
//			strQuery = "From Guarantor where custid = " + "'" + strCustid + "'" + strFlag +"";			
			List<Guarantor> guarantor = guarantorRepo.findByCustomerIdAndFlag(custId, strFlag);
			
			if (guarantor.size() != 0) {
				for (Guarantor gr:guarantor) {
					blnFirstguarantor = gr.getFirstguarantor();
					blnSecondguarantor = gr.getSecondguarantor();
					if (blnFirstguarantor == true) {
						strId = Integer.toString(gr.getId() );
					} else if (blnSecondguarantor == true) {
						strId2 = Integer.toString(gr.getId() );
					}
					blnFirstguarantor = false;
					blnSecondguarantor = false;
				}										
			}
			
			if (strId.length() > 0) {
				//*******************************************************************
				System.out.println("Inside STRID one");
//				strQuery = "From TvVerification where tvid = '" + strId + "'";
				
				try {
					Optional<TvVerification> opteleverification = teleVerifyRepo.findById(strId);
					
					if(opteleverification.isPresent()) {
						TvVerification tv = opteleverification.get();
						
						strSurnameremark = tv.getSurnameremark();
						strSurnameflag = tv.getSurnameflag();
//						if (strSurnameflag.equals("false")) {strSurnameflag = "";}
						strfnameremark = tv.getFirstnameremark(); 
						strfnameflag = tv.getFirstnameflag();	
//						if (strfnameflag.equals("false")) {strfnameflag = "";}
						stronameremark = tv.getOthernameremark();
						stronameflag = tv.getOthernameflag();	
//						if (stronameflag.equals("false")) {stronameflag = "";}
						/*strmsremark = "";
						strmsflag = "";		
						if (strmsflag.equals("false")) {strmsflag = "";}
						strsexremark = tv.getSexremark();
						strsexflag = tv.getSexflag();	
						if (strsexflag.equals("false")) {strsexflag = "";}*/
						strmobnoremark = tv.getMobilenoremark();
						strmobnoflag = tv.getMobilenoflag();	
//						if (strmobnoflag.equals("false")) {strmobnoflag = "";}
						strstgnmremark = tv.getStageremark();
						strstgnmflag = tv.getStageflag();	
//						if (strstgnmflag.equals("false")) {strstgnmflag = "";}
						/*strdistremark = tv.getDistrictremark();
						strdistflag = tv.getDistrictflag();	
						if (strdistflag.equals("false")) {strdistflag = "";}*/
						///////>>>>>
//						straddremark = tv.getAddressremark();
//						straddflag = tv.getAddressflag();
//						if (straddflag.equals("false")) {straddflag = "";}
						strlcnmremark = tv.getLcremark();
						strlcnmflag = tv.getLcflag();	
//						if (strlcnmflag.equals("false")) {strlcnmflag = "";}
						/*strparremark = tv.getParishremark();
						strparflag = tv.getParishflag();	
						if (strparflag.equals("false")) {strparflag = "";}*/
						strnidremark = tv.getNationalidremark();
						strnidflag = tv.getNationalidflag();	
//						if (strnidflag.equals("false")) {strnidflag = "";}
						strbregnoremark = tv.getBikeregnoremark();
						strbregnoflag = tv.getBikeregnoflag();	
//						if (strbregnoflag.equals("false")) {strbregnoflag = "";}
						/*strbuseremark = tv.getBikeuseremark();
						strbuseflag = tv.getBikeuseflag();	
						if (strbuseflag.equals("false")) {strbuseflag = "";}
						strdobremark = tv.getDobremark();
						strdobflag = tv.getDobremark();	
						if (strdobflag.equals("false")) {strdobflag = "";}
						strcntremark = tv.getCountyremark();
						strcntflag = tv.getCountyflag();	
						if (strcntflag.equals("false")) {strcntflag = "";}
						strscntremark = tv.getSubcountyremark();
						strscntflag = tv.getSubcountyflag();	
						if (strscntflag.equals("false")) {strscntflag = "";}
						strvilremark = tv.getVillageremark();
						strvilflag = tv.getVillageflag();	
						if (strvilflag.equals("false")) {strvilflag = "";}*/
						stryrvilremark = tv.getYearsinaddressremark();
						stryrvilflag = tv.getYearsinaddressflag();
//						if (stryrvilflag.equals("false")) {stryrvilflag = "";}
//						strnokremark = tv.getNextofkinremark();
//						strnokflag = tv.getNextofkinflag();	
//						if (strnokflag.equals("false")) {strnokflag = "";}
//						strnokmremark = tv.getNokmobilenoremark();
//						strnokmflag = tv.getNokmobilenoflag();	
//						if (strnokmflag.equals("false")) {strnokmflag = "";}
//						strnokrremark = tv.getNokrelationshipremark();
//						strnokrflag = tv.getNokrelationshipflag();	
//						if (strnokrflag.equals("false")) {strnokrflag = "";}
//						strnokaremark = tv.getNokagreeingremark();
//						strnokaflag = tv.getNokagreeingflag();	
//						if (strnokaflag.equals("false")) {strnokaflag = "";}
						/*strdlremark = tv.getDrivingpermitremark();
						strdlflag = tv.getDrivingpermitflag();		
						if (strdlflag.equals("false")) {strdlflag = "";}
						strnatremark = tv.getNationalityremark();
						strnatflag = tv.getNationalityflag();	
						if (strnatflag.equals("false")) {strnatflag = "";}
						strnodremark = tv.getNoofdependantsremark();
						strnodflag = tv.getNoofdependantsflag();	
						if (strnodflag.equals("false")) {strnodflag = "";}*/
						strorremark = tv.getOwnhouserentedremark();
						strorflag = tv.getOwnhouserentedflag();		
//						if (strorflag.equals("false")) {strorflag = "";}
						/*strllremark = tv.getLandlordnameremark();
						strllflag = tv.getLandlordnameflag();		
						if (strllflag.equals("false")) {strllflag = "";}
						strllmobremark = tv.getLandlordmobilenoremark();
						strllmobflag = tv.getLandlordmobilenoflag();	
						if (strllmobflag.equals("false")) {strllmobflag = "";}*/
						strrpmremark = tv.getRentpmremark();
						strrpmflag = tv.getRentpmflag();	
//						if (strrpmflag.equals("false")) {strrpmflag = "";}
						stroisremark = tv.getOtherincomesourceremark();
						stroisflag = tv.getOtherincomesourceflag();	
//						if (stroisflag.equals("false")) {stroisflag = "";}
						/*strdpsremark = tv.getDownpaymentsourceremark();
						strdpsflg = tv.getDownpaymentsourceflag();		
						if (strdpsflg.equals("false")) {strdpsflg = "";}*/
						strpaddremark = tv.getPermanentaddressremark();
						strpaddflag = tv.getPermanentaddressflag();	
//						if (strpaddflag.equals("false")) {strpaddflag = "";}
						/*strfathremark = tv.getFathersnameremark();
						strfathflag = tv.getFathersnameflag();	
						if (strfathflag.equals("false")) {strfathflag = "";}
						strmothremark = tv.getMothersnameremark();
						strmothflag = tv.getMothersnameflag();	
						if (strmothflag.equals("false")) {strmothflag = "";}
						strnpsremark = tv.getNearbypolicestationremark();
						strnpsflag = tv.getNearbypolicestationflag();	
						if (strnpsflag.equals("false")) {strnpsflag = "";}
						strlcnmobnoremark = tv.getLcmobilenoremark();
						strlcnmobnoflag = tv.getLcmobilenoflag();	
						if (strlcnmobnoflag.equals("false")) {strlcnmobnoflag = "";}
						strcusttyperemark = tv.getCusttyperemark();
						strcusttypeflag = tv.getCusttypeflag();
						if (strcusttypeflag.equals("false")) {strcusttypeflag = "";}*/						
						strbownerremark = tv.getBikeownerremark();
						strbownerflag = tv.getBikeownerflag();
//						if (strbownerflag.equals("false")) {strbownerflag = "";}
						strsalremark = tv.getSalariedremark();
						strsalflag = tv.getSalariedflag();
//						if (strsalflag.equals("false")) {strsalflag = "";}
						strmiremark = tv.getMonthlyincomeremark();
						strmiflag = tv.getMonthlyincomeflag();
//						if (strmiflag.equals("false")) {strmiflag = "";}
						stroiremark = tv.getOtherincomeremark();
						stroiflag = tv.getOtherincomeflag();
//						if (stroiflag.equals("false")) {stroiflag = "";}
						strstgaddremark = tv.getStageaddressremark();
						strstgaddflag = tv.getStageaddressflag();
//						if (strstgaddflag.equals("false")) {strstgaddflag = "";}
						strstgccremark = tv.getStagechairconfirmationremark();
						strstgccflag = tv.getStagechairconfirmationflag();
//						if (strstgccflag.equals("false")) {strstgccflag = "";}
//						strrltnshpremark = tv.getRelationshipremark();
//						strrltnshpflag = tv.getRelationshipflag();
//						if (strrltnshpflag.equals("false")) {strrltnshpflag = "";}
						
						strtvDobRemarks = tv.getDobremark();
						strtvDobFlag = tv.getDobflag();
//						if (strtvDobFlag.equals("false")) {strtvDobFlag = "";}

						strtvLlnameRemarks = tv.getLandlordnameremark();
						strtvLlnameFlag = tv.getLandlordnameflag();
//						if (strtvLlnameFlag.equals("false")) {strtvLlnameFlag = "";}

						strtvLlmbnoRemarks = tv.getLandlordmobilenoremark();
						strtvLlmbnoFlag = tv.getLandlordmobilenoflag();
//						if (strtvLlmbnoFlag.equals("false")) {strtvLlmbnoFlag = "";}

						strtvResiadrssRemarks = tv.getResiadrssremarks();
						strtvResiadrssFlag = tv.getResiadrssflag();
//						if (strtvResiadrssFlag.equals("false")) {strtvResiadrssFlag = "";}

						strtvMaritalstsRemarks = tv.getMaritalstatusremark();
						strtvMaritalstsFlag = tv.getMaritalstatusflag();
//						if (strtvMaritalstsFlag.equals("false")) {strtvMaritalstsFlag = "";}

						strtvLLFeedAbtRntRemarks = tv.getLlrentfeedbackremarks();
						strtvLLFeedAbtRntFlag = tv.getLlrentfeedbackflag();
//						if (strtvLLFeedAbtRntFlag.equals("false")) {strtvLLFeedAbtRntFlag = "";}

						strtvNoOfYrsInAreaRemarks = tv.getNoyrsinarearemarks();
						strtvNoOfYrsInAreaFlag = tv.getNoyrsinareaflag();
//						if (strtvNoOfYrsInAreaFlag.equals("false")) {strtvNoOfYrsInAreaFlag = "";}

						strtvLc1chmnRecFeedbkRemarks = tv.getLc1chmnrecfeedremarks();
						strtvLc1chmnRecFeedbkFlag = tv.getLc1chmnrecfeedflag();
//						if (strtvLc1chmnRecFeedbkFlag.equals("false")) {strtvLc1chmnRecFeedbkFlag = "";}

						strtvNearLndmrkorResiRemarks = tv.getNearlmarkresiremarks();
						strtvNearLndmrkorResiFlag = tv.getNearlmarkresiflag();
//						if (strtvNearLndmrkorResiFlag.equals("false")) {strtvNearLndmrkorResiFlag = "";}

						strtvEmpTypeRemarks = tv.getEmptyperemarks();
						strtvEmpTypeFlag = tv.getEmptypeflag();
//						if (strtvEmpTypeFlag.equals("false")) {strtvEmpTypeFlag = "";}

						strtvStgOrWrkadrssWithNearLNMRKRemarks = tv.getStgorwrkadrssnearlmarkremarks();
						strtvStgOrWrkadrssWithNearLNMRKFlag = tv.getStgorwrkadrssnearlmarkflag();
//						if (strtvStgOrWrkadrssWithNearLNMRKFlag.equals("false")) {strtvStgOrWrkadrssWithNearLNMRKFlag = "";}

						strtvNoOfYrsInstgorBusiRemarks = tv.getNoofyrsinstgorbusiremarks();
						strtvNoOfYrsInstgorBusiFlag = tv.getNoofyrsinstgorbuisflag();
//						if (strtvNoOfYrsInstgorBusiFlag.equals("false")) {strtvNoOfYrsInstgorBusiFlag = "";}

						strtvSpouseNameRemarks = tv.getSpousenameremarks();
						strtvSpouseNameFlag = tv.getSpousenameflag();
//						if (strtvSpouseNameFlag.equals("false")) {strtvSpouseNameFlag = "";}

						strtvSpouseMbnoRemarks = tv.getSpousenoremarks();
						strtvSpouseMbnoFlag = tv.getSpousenoflag();
//						if (strtvSpouseMbnoFlag.equals("false")) {strtvSpouseMbnoFlag = "";}

						strtvYakaNumRemarks = tv.getYakanumremarks();
						strtvYakaNumFlag = tv.getYakanumflag();
//						if (strtvYakaNumFlag.equals("false")) {strtvYakaNumFlag = "";}

						strtvYakaNoNameRemarks = tv.getYakanumnameremarks();
						strtvYakaNoNameFlag = tv.getYakanumnamflag();
//						if (strtvYakaNoNameFlag.equals("false")) {strtvYakaNoNameFlag = "";}

						strtvLc1numberRemarks = tv.getLcmobilenoremark();
						strtvLc1numberFlag = tv.getLcmobilenoflag();
//						if (strtvLc1numberFlag.equals("false")) {strtvLc1numberFlag = "";}

						strtvStgorempnoRemarks = tv.getStagechairmannoremarks();
						strtvStgorempnoFlag = tv.getStagechairmannoflag();
//						if (strtvStgorempnoFlag.equals("false")) {strtvStgorempnoFlag = "";}
						
						strtvMbnumnotinnameRemarks = tv.getMbnonotinnameremarks();
						strtvMbnumnotinnameFlag = tv.getMbnonotinnameflag();
//						if (strtvMbnumnotinnameFlag.equals("false")) {strtvMbnumnotinnameFlag = "";}

						
					}
								
				} catch (Exception e) {
//					System.out.println(e.getLocalizedMessage());
					e.printStackTrace();
					response.setErrorMsg("Error retrieving customer id: " + strCustid);
				}
				strMsg = strSurnameremark + "|" + strSurnameflag + "|" + strfnameremark + "|" + strfnameflag + "|" + stronameremark + "|" + 
					stronameflag + "|" + strmsremark + "|" + strmsflag + "|" + strsexremark + "|" + strsexflag + "|" + strmobnoremark + "|" + 
					strmobnoflag + "|" + strstgnmremark + "|" + strstgnmflag + "|" + strdistremark + "|" + strdistflag + "|" + strlcnmremark + "|" + 
					strlcnmflag + "|" + strparremark + "|" + strparflag + "|" + strnidremark + "|" + strnidflag + "|" + strbregnoremark + "|" + 
					strbregnoflag + "|" + strbuseremark + "|" + strbuseflag + "|" + strdobremark + "|" + strdobflag + "|" + strcntremark + "|" + 
					strcntflag + "|" + strscntremark + "|" + strscntflag + "|" + strvilremark + "|" + strvilflag + "|" + stryrvilremark + "|" + 
					stryrvilflag + "|" + strnokremark + "|" + strnokflag + "|" + strnokmremark + "|" + strnokmflag + "|" + strnokrremark + "|" + 
					strnokrflag + "|" + strnokaremark + "|" + strnokaflag + "|" + strdlremark + "|" + strdlflag + "|" + strnatremark + "|" + 
					strnatflag + "|" + strnodremark + "|" + strnodflag + "|" + strorremark + "|" + strorflag + "|" + strllremark + "|" + 
					strllflag + "|" + strllmobremark + "|" + strllmobflag + "|" + strrpmremark + "|" + strrpmflag + "|" + stroisremark + "|" + 
					stroisflag + "|" + strdpsremark + "|" + strdpsflg + "|" + strpaddremark + "|" + strpaddflag + "|" + strfathremark + "|" + 
					strfathflag + "|" + strmothremark + "|" + strmothflag + "|" + strnpsremark + "|" + strnpsflag + "|" + strlcnmobnoremark + "|" + 
					strlcnmobnoflag + "|" + strcusttyperemark + "|" + strcusttypeflag + "|" + strbownerremark + "|" + strbownerflag + "|" + 					
					strsalremark + "|" + strsalflag + "|" + strmiremark + "|" + strmiflag + "|" + stroiremark + "|" + stroiflag + "|" + 
					strstgaddremark + "|" + strstgaddflag + "|" + strstgccremark + "|" + strstgccflag + "|" + 
					strrltnshpremark + "|" + strrltnshpflag + "|" + straddremark + "|" + straddflag + "|" + stryrvilremark + "|" + stryrvilflag + "|" +
					strtvDobRemarks + "|" + strtvDobFlag + "|" + strtvLlnameRemarks + "|" + strtvLlnameFlag + "|" + 
                    strtvLlmbnoRemarks + "|" + strtvLlmbnoFlag + "|" + strtvResiadrssRemarks + "|" + strtvResiadrssFlag + "|" + 
                    strtvMaritalstsRemarks + "|" + strtvMaritalstsFlag + "|" + strtvLLFeedAbtRntRemarks + "|" + strtvLLFeedAbtRntFlag + "|" + 
                    strtvNoOfYrsInAreaRemarks + "|" + strtvNoOfYrsInAreaFlag + "|" + strtvLc1chmnRecFeedbkRemarks + "|" + strtvLc1chmnRecFeedbkFlag + "|" + 
                    strtvNearLndmrkorResiRemarks + "|" + strtvNearLndmrkorResiFlag + "|" + strtvEmpTypeRemarks + "|" + strtvEmpTypeFlag + "|" + 
                    strtvStgOrWrkadrssWithNearLNMRKRemarks + "|" + strtvStgOrWrkadrssWithNearLNMRKFlag + "|" + strtvNoOfYrsInstgorBusiRemarks + "|" + 
                    strtvNoOfYrsInstgorBusiFlag + "|" + strtvSpouseNameRemarks + "|" + strtvSpouseNameFlag + "|" + strtvSpouseMbnoRemarks + "|" + 
                    strtvSpouseMbnoFlag + "|" + strtvYakaNumRemarks + "|" + strtvYakaNumFlag + "|" + strtvYakaNoNameRemarks + "|" + 
                    strtvYakaNoNameFlag + "|" + strtvLc1numberRemarks + "|" + strtvLc1numberFlag + "|" + strtvStgorempnoRemarks + "|" + strtvStgorempnoFlag + "|"
                    + strtvMbnumnotinnameRemarks + "|" + strtvMbnumnotinnameFlag;
				
//				strQuery = "From FiVerification where fiid = '" + strId +"'";
				
				try {
					Optional<FiVerification> opFiverification = fiVerifyRepo.findById(strId) ;
					
					if(opFiverification.isPresent()){
						
						FiVerification fi = opFiverification.get();
						
						strfiSurnameremark = fi.getSurnameremark();
						strfiSurnameflag = fi.getSurnameflag();	
//						if (strfiSurnameflag.equals("false")) {strfiSurnameflag = "";}
						strfifnameremark = fi.getFirstnameremark(); 
						strfifnameflag = fi.getFirstnameflag();
//						if (strfifnameflag.equals("false")) {strfifnameflag = "";}
						strfionameremark = fi.getOthernameremark();
						strfionameflag = fi.getOthernameflag();	
//						if (strfionameflag.equals("false")) {strfionameflag = "";}
						/*strfimsremark = fi.getMaritalstatusremark();
						strfimsflag = fi.getMaritalstatusflag();		
						if (strfimsflag.equals("false")) {strfimsflag = "";}
						strfisexremark = fi.getSexremark();
						strfisexflag = fi.getSexflag();*/	
//						if (strfisexflag.equals("false")) {strfisexflag = "";}
						strfimobnoremark = fi.getMobilenoremark();
						strfimobnoflag = fi.getMobilenoflag();	
//						if (strfimobnoflag.equals("false")) {strfimobnoflag = "";}
						strfistgnmremark = fi.getStageremark();
						strfistgnmflag = fi.getStageflag();	
//						if (strfistgnmflag.equals("false")) {strfistgnmflag = "";}
						/*strfidistremark = fi.getDistrictremark();
						strfidistflag = fi.getDistrictflag();	
						if (strfidistflag.equals("false")) {strfidistflag = "";}*/
						strfilcnmremark = fi.getLcremark();
						strfilcnmflag = fi.getLcflag();	
//						if (strfilcnmflag.equals("false")) {strfilcnmflag = "";}
						/*strfiparremark = fi.getParishremark();
						strfiparflag = fi.getParishflag();	
						if (strfiparflag.equals("false")) {strfiparflag = "";}*/
//						strfiaddremark = fi.getAddressremark();
//						strfiaddflag = fi.getAddressflag();
//						if (strfiaddflag.equals("false")) {strfiaddflag = "";}
						strfinidremark = fi.getNationalidremark();
						strfinidflag = fi.getNationalidflag();	
//						if (strfinidflag.equals("false")) {strfinidflag = "";}
						strfibregnoremark = fi.getBikeregnoremark();
						strfibregnoflag = fi.getBikeregnoflag();	
//						if (strfibregnoflag.equals("false")) {strfibregnoflag = "";}
						/*strfibuseremark = fi.getBikeuseremark();
						strfibuseflag = fi.getBikeuseflag();	
						if (strfibuseflag.equals("false")) {strfibuseflag = "";}
						strfidobremark = fi.getDobremark();
						strfidobflag = fi.getDobremark();	
						if (strfidobflag.equals("false")) {strfidobflag = "";}
						strficntremark = fi.getCountyremark();
						strficntflag = fi.getCountyflag();	
						if (strficntflag.equals("false")) {strficntflag = "";}
						strfiscntremark = fi.getSubcountyremark();
						strfiscntflag = fi.getSubcountyflag();	
						if (strfiscntflag.equals("false")) {strfiscntflag = "";}
						strfivilremark = fi.getVillageremark();
						strfivilflag = fi.getVillageflag();	
						if (strfivilflag.equals("false")) {strfivilflag = "";}*/
						strfiyrvilremark = fi.getYearsinaddressremark();
						strfiyrvilflag = fi.getYearsinaddressflag();	
//						if (strfiyrvilflag.equals("false")) {strfiyrvilflag = "";}
//						strfinokremark = fi.getNextofkinremark();
//						strfinokflag = fi.getNextofkinflag();	
//						if (strfinokflag.equals("false")) {strfinokflag = "";}
//						strfinokmremark = fi.getNokmobilenoremark();
//						strfinokmflag = fi.getNokmobilenoflag();	
//						if (strfinokmflag.equals("false")) {strfinokmflag = "";}
//						strfinokrremark = fi.getNokrelationshipremark();
//						strfinokrflag = fi.getNokrelationshipflag();	
//						if (strfinokrflag.equals("false")) {strfinokrflag = "";}
//						strfinokaremark = fi.getNokagreeingremark();
//						strfinokaflag = fi.getNokagreeingflag();	
//						if (strfinokaflag.equals("false")) {strfinokaflag = "";}
						/*strfidlremark = fi.getDrivingpermitremark();
						strfidlflag = fi.getDrivingpermitflag();		
						if (strfidlflag.equals("false")) {strfidlflag = "";}
						strfinatremark = fi.getNationalityremark();
						strfinatflag = fi.getNationalityflag();	
						if (strfinatflag.equals("false")) {strfinatflag = "";}
						strfinodremark = fi.getNoofdependantsremark();
						strfinodflag = fi.getNoofdependantsflag();	
						if (strfinodflag.equals("false")) {strfinodflag = "";}*/
						strfiorremark = fi.getOwnhouserentedremark();
						strfiorflag = fi.getOwnhouserentedflag();		
//						if (strfiorflag.equals("false")) {strfiorflag = "";}
						/*strfillremark = fi.getLandlordnameremark();
						strfillflag = fi.getLandlordnameflag();		
						if (strfillflag.equals("false")) {strfillflag = "";}
						strfillmobremark = fi.getLandlordmobilenoremark();
						strfillmobflag = fi.getLandlordmobilenoflag();	
						if (strfillmobflag.equals("false")) {strfillmobflag = "";}*/
						strfirpmremark = fi.getRentpmremark();
						strfirpmflag = fi.getRentpmflag();	
//						if (strfirpmflag.equals("false")) {strfirpmflag = "";}
						strfioisremark = fi.getOtherincomesourceremark();
						strfioisflag = fi.getOtherincomesourceflag();	
//						if (strfioisflag.equals("false")) {strfioisflag = "";}
						/*strfidpsremark = fi.getDownpaymentsourceremark();
						strfidpsflg = fi.getDownpaymentsourceflag();		
						if (strfidpsflg.equals("false")) {strfidpsflg = "";}*/
						strfipaddremark = fi.getPermanentaddressremark();
						strfipaddflag = fi.getPermanentaddressflag();	
//						if (strfipaddflag.equals("false")) {strfipaddflag = "";}
						/*strfifathremark = fi.getFathersnameremark();
						strfifathflag = fi.getFathersnameflag();	
						if (strfifathflag.equals("false")) {strfifathflag = "";}
						strfimothremark = fi.getMothersnameremark();
						strfimothflag = fi.getMothersnameflag();	
						if (strfimothflag.equals("false")) {strfimothflag = "";}
						strfinpsremark = fi.getNearbypolicestationremark();
						strfinpsflag = fi.getNearbypolicestationflag();	
						if (strfinpsflag.equals("false")) {strfinpsflag = "";}
						strfilcnmobnoremark = fi.getLcmobilenoremark();
						strfilcnmobnoflag = fi.getLcmobilenoflag();	
						if (strfilcnmobnoflag.equals("false")) {strfilcnmobnoflag = "";}
						strficusttyperemark = fi.getCusttyperemark();
						strficusttypeflag = fi.getCusttypeflag();
						if (strficusttypeflag.equals("false")) {strficusttypeflag = "";}*/						
						strfibownerremark = fi.getBikeownerremark();
						strfibownerflag = fi.getBikeownerflag();
//						if (strfibownerflag.equals("false")) {strfibownerflag = "";}
						strfisalremark = fi.getSalariedremark();
						strfisalflag = fi.getSalariedflag();
//						if (strfisalflag.equals("false")) {strfisalflag = "";}
						strfimiremark = fi.getMonthlyincomeremark();
						strfimiflag = fi.getMonthlyincomeflag();
//						if (strfimiflag.equals("false")) {strfimiflag = "";}
						strfioiremark = fi.getOtherincomeremark();
						strfioiflag = fi.getOtherincomeflag();
//						if (strfioiflag.equals("false")) {strfioiflag = "";}
						strfistgaddremark = fi.getStageaddressremark();
						strfistgaddflag = fi.getStageaddressflag();
//						if (strfistgaddflag.equals("false")) {strfistgaddflag = "";}
						strfistgccremark = fi.getStagechairconfirmationremark();
						strfistgccflag = fi.getStagechairconfirmationflag();
//						if (strfistgccflag.equals("false")) {strfistgccflag = "";}
//						strfirltnshpremark = fi.getRelationshipremark();
//						strfirltnshpflag = fi.getRelationshipflag();
//						if (strfirltnshpflag.equals("false")) {strrltnshpflag = "";}
						
						strfiDobRemarks = fi.getDobremark();
						strfiDobFlag = fi.getDobflag();
//						if (strfiDobFlag.equals("false")) { strfiDobFlag = ""; }

						strfiLlnameRemarks = fi.getLandlordnameremark();
						strfiLlnameFlag = fi.getLandlordnameflag();
//						if (strfiLlnameFlag.equals("false")) { strfiLlnameFlag = ""; }

						strfiLlmbnoRemarks = fi.getLandlordmobilenoremark();
						strfiLlmbnoFlag = fi.getLandlordmobilenoflag();
//						if (strfiLlmbnoFlag.equals("false")) { strfiLlmbnoFlag = ""; }

						strfiResiadrssRemarks = fi.getResiadrssremarks();
						strfiResiadrssFlag = fi.getResiadrssflag();
//						if (strfiResiadrssFlag.equals("false")) { strfiResiadrssFlag = ""; }

						strfiMaritalstsRemarks = fi.getMaritalstatusremark();
						strfiMaritalstsFlag = fi.getMaritalstatusflag();
//						if (strfiMaritalstsFlag.equals("false")) { strfiMaritalstsFlag = ""; }

						strfiLLFeedAbtRntRemarks = fi.getLlrentfeedbackremarks();
						strfiLLFeedAbtRntFlag = fi.getLlrentfeedbackflag();
//						if (strfiLLFeedAbtRntFlag.equals("false")) { strfiLLFeedAbtRntFlag = ""; }

						strfiNoOfYrsInAreaRemarks = fi.getNoyrsinarearemarks();
						strfiNoOfYrsInAreaFlag = fi.getNoyrsinareaflag();
//						if (strfiNoOfYrsInAreaFlag.equals("false")) { strfiNoOfYrsInAreaFlag = ""; }

						strfiLc1chmnRecFeedbkRemarks = fi.getLc1chmnrecfeedremarks();
						strfiLc1chmnRecFeedbkFlag = fi.getLc1chmnrecfeedflag();
//						if (strfiLc1chmnRecFeedbkFlag.equals("false")) { strfiLc1chmnRecFeedbkFlag = ""; }

						strfiNearLndmrkorResiRemarks = fi.getNearlmarkresiremarks();
						strfiNearLndmrkorResiFlag = fi.getNearlmarkresiflag();
//						if (strfiNearLndmrkorResiFlag.equals("false")) { strfiNearLndmrkorResiFlag = ""; }

						strfiEmpTypeRemarks = fi.getEmptyperemarks();
						strfiEmpTypeFlag = fi.getEmptypeflag();
//						if (strfiEmpTypeFlag.equals("false")) { strfiEmpTypeFlag = ""; }

						strfiStgOrWrkadrssWithNearLNMRKRemarks = fi.getStgorwrkadrssnearlmarkremarks();
						strfiStgOrWrkadrssWithNearLNMRKFlag = fi.getStgorwrkadrssnearlmarkflag();
//						if (strfiStgOrWrkadrssWithNearLNMRKFlag.equals("false")) { strfiStgOrWrkadrssWithNearLNMRKFlag = ""; }

						strfiNoOfYrsInstgorBusiRemarks = fi.getNoofyrsinstgorbusiremarks();
						strfiNoOfYrsInstgorBusiFlag = fi.getNoofyrsinstgorbuisflag();
//						if (strfiNoOfYrsInstgorBusiFlag.equals("false")) { strfiNoOfYrsInstgorBusiFlag = ""; }

						strfiSpouseNameRemarks = fi.getSpousenameremarks();
						strfiSpouseNameFlag = fi.getSpousenameflag();
//						if (strfiSpouseNameFlag.equals("false")) { strfiSpouseNameFlag = ""; }

						strfiSpouseMbnoRemarks = fi.getSpousenoremarks();
						strfiSpouseMbnoFlag = fi.getSpousenoflag();
//						if (strfiSpouseMbnoFlag.equals("false")) { strfiSpouseMbnoFlag = ""; }

						strfiYakaNumRemarks = fi.getYakanumremarks();
						strfiYakaNumFlag = fi.getYakanumflag();
//						if (strfiYakaNumFlag.equals("false")) { strfiYakaNumFlag = ""; }

						strfiYakaNoNameRemarks = fi.getYakanumnameremarks();
						strfiYakaNoNameFlag = fi.getYakanumnamflag();
//						if (strfiYakaNoNameFlag.equals("false")) { strfiYakaNoNameFlag = ""; }

						strfiLc1numberRemarks = fi.getLcmobilenoremark();
						strfiLc1numberFlag = fi.getLcmobilenoflag();
//						if (strfiLc1numberFlag.equals("false")) { strfiLc1numberFlag = ""; }

						strfiStgorempnoRemarks = fi.getStagechairmannoremarks();
						strfiStgorempnoFlag = fi.getStagechairmannoflag();
//						if (strfiStgorempnoFlag.equals("false")) { strfiStgorempnoFlag = ""; }
						
						strfiMbnumnotinnameRemarks = fi.getMbnonotinnameremarks();
						strfiMbnumnotinnameFlag = fi.getMbnonotinnameflag();
//						if (strfiMbnumnotinnameFlag.equals("false")) {strfiMbnumnotinnameFlag = "";}

					}
								
				} catch (Exception e) {
					e.printStackTrace();
//					System.out.println(e.getLocalizedMessage());
					response.setErrorMsg("Error retrieving customer id: " + strCustid);
				}
				strMsg1 = strfiSurnameremark + "|" + strfiSurnameflag + "|" + strfifnameremark + "|" + strfifnameflag + "|" + strfionameremark + "|" + 
						strfionameflag + "|" + strfimsremark + "|" + strfimsflag + "|" + strfisexremark + "|" + strfisexflag + "|" + strfimobnoremark + "|" + 
						strfimobnoflag + "|" + strfistgnmremark + "|" + strfistgnmflag + "|" + strfidistremark + "|" + strfidistflag + "|" + strfilcnmremark + "|" + 
						strfilcnmflag + "|" + strfiparremark + "|" + strfiparflag + "|" + strfinidremark + "|" + strfinidflag + "|" + strfibregnoremark + "|" + 
						strfibregnoflag + "|" + strfibuseremark + "|" + strfibuseflag + "|" + strfidobremark + "|" + strfidobflag + "|" + strficntremark + "|" + 
						strficntflag + "|" + strfiscntremark + "|" + strfiscntflag + "|" + strfivilremark + "|" + strfivilflag + "|" + strfiyrvilremark + "|" + 
						strfiyrvilflag + "|" + strfinokremark + "|" + strfinokflag + "|" + strfinokmremark + "|" + strfinokmflag + "|" + strfinokrremark + "|" + 
						strfinokrflag + "|" + strfinokaremark + "|" + strfinokaflag + "|" + strfidlremark + "|" + strfidlflag + "|" + strfinatremark + "|" + 
						strfinatflag + "|" + strfinodremark + "|" + strfinodflag + "|" + strfiorremark + "|" + strfiorflag + "|" + strfillremark + "|" + 
						strfillflag + "|" + strfillmobremark + "|" + strfillmobflag + "|" + strfirpmremark + "|" + strfirpmflag + "|" + strfioisremark + "|" + 
						strfioisflag + "|" + strfidpsremark + "|" + strfidpsflg + "|" + strfipaddremark + "|" + strfipaddflag + "|" + strfifathremark + "|" + 
						strfifathflag + "|" + strfimothremark + "|" + strfimothflag + "|" + strfinpsremark + "|" + strfinpsflag + "|" + strfilcnmobnoremark + "|" + 
						strfilcnmobnoflag + "|" + strficusttyperemark + "|" + strficusttypeflag + "|" + strfibownerremark + "|" + strfibownerflag + "|" + 					
						strfisalremark + "|" + strfisalflag + "|" + strfimiremark + "|" + strfimiflag + "|" + strfioiremark + "|" + strfioiflag + "|" + 
						strfistgaddremark + "|" + strfistgaddflag + "|" + strfistgccremark + "|" + strfistgccflag + "|" + 
						strfirltnshpremark + "|" + strfirltnshpflag + "|" + strfiaddremark + "|" + strfiaddflag + "|" + strfiyrvilremark + "|" + strfiyrvilflag + "|" +
						strfiDobRemarks + "|" + strfiDobFlag + "|" + strfiLlnameRemarks + "|" + strfiLlnameFlag + "|" + strfiLlmbnoRemarks + "|" + strfiLlmbnoFlag + "|" +
	                    strfiResiadrssRemarks + "|" + strfiResiadrssFlag + "|" + strfiMaritalstsRemarks + "|" + strfiMaritalstsFlag + "|" + strfiLLFeedAbtRntRemarks + "|" +
						strfiLLFeedAbtRntFlag + "|" + strfiNoOfYrsInAreaRemarks + "|" + strfiNoOfYrsInAreaFlag + "|" + strfiLc1chmnRecFeedbkRemarks + "|" +
	                    strfiLc1chmnRecFeedbkFlag + "|" + strfiNearLndmrkorResiRemarks + "|" + strfiNearLndmrkorResiFlag + "|" + strfiEmpTypeRemarks + "|" + 
						strfiEmpTypeFlag + "|" + strfiStgOrWrkadrssWithNearLNMRKRemarks + "|" + strfiStgOrWrkadrssWithNearLNMRKFlag + "|" +
	                    strfiNoOfYrsInstgorBusiRemarks + "|" + strfiNoOfYrsInstgorBusiFlag + "|" + strfiSpouseNameRemarks + "|" + strfiSpouseNameFlag + "|" +
	                    strfiSpouseMbnoRemarks + "|" + strfiSpouseMbnoFlag + "|" + strfiYakaNumRemarks + "|" + strfiYakaNumFlag + "|" +
	                    strfiYakaNoNameRemarks + "|" + strfiYakaNoNameFlag + "|" + strfiLc1numberRemarks + "|" + strfiLc1numberFlag + "|" + strfiStgorempnoRemarks + "|" +
	                    strfiStgorempnoFlag + "|" + strfiMbnumnotinnameRemarks + "|" + strfiMbnumnotinnameFlag;

				//*******************************************************************
				strMsg2 = strMsg + "|" + strMsg1;
			} else {
				System.out.println("INSIDE ELSE PART");
				strMsg = strSurnameremark + "|" + strSurnameflag + "|" + strfnameremark + "|" + strfnameflag + "|" + stronameremark + "|" + 
						stronameflag + "|" + strmsremark + "|" + strmsflag + "|" + strsexremark + "|" + strsexflag + "|" + strmobnoremark + "|" + 
						strmobnoflag + "|" + strstgnmremark + "|" + strstgnmflag + "|" + strdistremark + "|" + strdistflag + "|" + strlcnmremark + "|" + 
						strlcnmflag + "|" + strparremark + "|" + strparflag + "|" + strnidremark + "|" + strnidflag + "|" + strbregnoremark + "|" + 
						strbregnoflag + "|" + strbuseremark + "|" + strbuseflag + "|" + strdobremark + "|" + strdobflag + "|" + strcntremark + "|" + 
						strcntflag + "|" + strscntremark + "|" + strscntflag + "|" + strvilremark + "|" + strvilflag + "|" + stryrvilremark + "|" + 
						stryrvilflag + "|" + strnokremark + "|" + strnokflag + "|" + strnokmremark + "|" + strnokmflag + "|" + strnokrremark + "|" + 
						strnokrflag + "|" + strnokaremark + "|" + strnokaflag + "|" + strdlremark + "|" + strdlflag + "|" + strnatremark + "|" + 
						strnatflag + "|" + strnodremark + "|" + strnodflag + "|" + strorremark + "|" + strorflag + "|" + strllremark + "|" + 
						strllflag + "|" + strllmobremark + "|" + strllmobflag + "|" + strrpmremark + "|" + strrpmflag + "|" + stroisremark + "|" + 
						stroisflag + "|" + strdpsremark + "|" + strdpsflg + "|" + strpaddremark + "|" + strpaddflag + "|" + strfathremark + "|" + 
						strfathflag + "|" + strmothremark + "|" + strmothflag + "|" + strnpsremark + "|" + strnpsflag + "|" + strlcnmobnoremark + "|" + 
						strlcnmobnoflag + "|" + strcusttyperemark + "|" + strcusttypeflag + "|" + strbownerremark + "|" + strbownerflag + "|" + 					
						strsalremark + "|" + strsalflag + "|" + strmiremark + "|" + strmiflag + "|" + stroiremark + "|" + stroiflag + "|" + 
						strstgaddremark + "|" + strstgaddflag + "|" + strstgccremark + "|" + strstgccflag + "|" + 
						strrltnshpremark + "|" + strrltnshpflag + "|" + straddremark + "|" + straddflag + "|" + stryrvilremark + "|" + stryrvilflag + "|" +
						strtvDobRemarks + "|" + strtvDobFlag + "|" + strtvLlnameRemarks + "|" + strtvLlnameFlag + "|" + 
	                    strtvLlmbnoRemarks + "|" + strtvLlmbnoFlag + "|" + strtvResiadrssRemarks + "|" + strtvResiadrssFlag + "|" + 
	                    strtvMaritalstsRemarks + "|" + strtvMaritalstsFlag + "|" + strtvLLFeedAbtRntRemarks + "|" + strtvLLFeedAbtRntFlag + "|" + 
	                    strtvNoOfYrsInAreaRemarks + "|" + strtvNoOfYrsInAreaFlag + "|" + strtvLc1chmnRecFeedbkRemarks + "|" + strtvLc1chmnRecFeedbkFlag + "|" + 
	                    strtvNearLndmrkorResiRemarks + "|" + strtvNearLndmrkorResiFlag + "|" + strtvEmpTypeRemarks + "|" + strtvEmpTypeFlag + "|" + 
	                    strtvStgOrWrkadrssWithNearLNMRKRemarks + "|" + strtvStgOrWrkadrssWithNearLNMRKFlag + "|" + strtvNoOfYrsInstgorBusiRemarks + "|" + 
	                    strtvNoOfYrsInstgorBusiFlag + "|" + strtvSpouseNameRemarks + "|" + strtvSpouseNameFlag + "|" + strtvSpouseMbnoRemarks + "|" + 
	                    strtvSpouseMbnoFlag + "|" + strtvYakaNumRemarks + "|" + strtvYakaNumFlag + "|" + strtvYakaNoNameRemarks + "|" + 
	                    strtvYakaNoNameFlag + "|" + strtvLc1numberRemarks + "|" + strtvLc1numberFlag + "|" + strtvStgorempnoRemarks + "|" + strtvStgorempnoFlag
	                    + strtvMbnumnotinnameRemarks + "|" + strtvMbnumnotinnameFlag;
				strMsg1 = strfiSurnameremark + "|" + strfiSurnameflag + "|" + strfifnameremark + "|" + strfifnameflag + "|" + strfionameremark + "|" + 
						strfionameflag + "|" + strfimsremark + "|" + strfimsflag + "|" + strfisexremark + "|" + strfisexflag + "|" + strfimobnoremark + "|" + 
						strfimobnoflag + "|" + strfistgnmremark + "|" + strfistgnmflag + "|" + strfidistremark + "|" + strfidistflag + "|" + strfilcnmremark + "|" + 
						strfilcnmflag + "|" + strfiparremark + "|" + strfiparflag + "|" + strfinidremark + "|" + strfinidflag + "|" + strfibregnoremark + "|" + 
						strfibregnoflag + "|" + strfibuseremark + "|" + strfibuseflag + "|" + strfidobremark + "|" + strfidobflag + "|" + strficntremark + "|" + 
						strficntflag + "|" + strfiscntremark + "|" + strfiscntflag + "|" + strfivilremark + "|" + strfivilflag + "|" + strfiyrvilremark + "|" + 
						strfiyrvilflag + "|" + strfinokremark + "|" + strfinokflag + "|" + strfinokmremark + "|" + strfinokmflag + "|" + strfinokrremark + "|" + 
						strfinokrflag + "|" + strfinokaremark + "|" + strfinokaflag + "|" + strfidlremark + "|" + strfidlflag + "|" + strfinatremark + "|" + 
						strfinatflag + "|" + strfinodremark + "|" + strfinodflag + "|" + strfiorremark + "|" + strfiorflag + "|" + strfillremark + "|" + 
						strfillflag + "|" + strfillmobremark + "|" + strfillmobflag + "|" + strfirpmremark + "|" + strfirpmflag + "|" + strfioisremark + "|" + 
						strfioisflag + "|" + strfidpsremark + "|" + strfidpsflg + "|" + strfipaddremark + "|" + strfipaddflag + "|" + strfifathremark + "|" + 
						strfifathflag + "|" + strfimothremark + "|" + strfimothflag + "|" + strfinpsremark + "|" + strfinpsflag + "|" + strfilcnmobnoremark + "|" + 
						strfilcnmobnoflag + "|" + strficusttyperemark + "|" + strficusttypeflag + "|" + strfibownerremark + "|" + strfibownerflag + "|" + 					
						strfisalremark + "|" + strfisalflag + "|" + strfimiremark + "|" + strfimiflag + "|" + strfioiremark + "|" + strfioiflag + "|" + 
						strfistgaddremark + "|" + strfistgaddflag + "|" + strfistgccremark + "|" + strfistgccflag + "|" + 
						strfirltnshpremark + "|" + strfirltnshpflag + "|" + strfiaddremark + "|" + strfiaddflag + "|" + strfiyrvilremark + "|" + strfiyrvilflag + "|" +
						strfiDobRemarks + "|" + strfiDobFlag + "|" + strfiLlnameRemarks + "|" + strfiLlnameFlag + "|" + strfiLlmbnoRemarks + "|" + strfiLlmbnoFlag + "|" +
	                    strfiResiadrssRemarks + "|" + strfiResiadrssFlag + "|" + strfiMaritalstsRemarks + "|" + strfiMaritalstsFlag + "|" + strfiLLFeedAbtRntRemarks + "|" +
						strfiLLFeedAbtRntFlag + "|" + strfiNoOfYrsInAreaRemarks + "|" + strfiNoOfYrsInAreaFlag + "|" + strfiLc1chmnRecFeedbkRemarks + "|" +
	                    strfiLc1chmnRecFeedbkFlag + "|" + strfiNearLndmrkorResiRemarks + "|" + strfiNearLndmrkorResiFlag + "|" + strfiEmpTypeRemarks + "|" + 
						strfiEmpTypeFlag + "|" + strfiStgOrWrkadrssWithNearLNMRKRemarks + "|" + strfiStgOrWrkadrssWithNearLNMRKFlag + "|" +
	                    strfiNoOfYrsInstgorBusiRemarks + "|" + strfiNoOfYrsInstgorBusiFlag + "|" + strfiSpouseNameRemarks + "|" + strfiSpouseNameFlag + "|" +
	                    strfiSpouseMbnoRemarks + "|" + strfiSpouseMbnoFlag + "|" + strfiYakaNumRemarks + "|" + strfiYakaNumFlag + "|" +
	                    strfiYakaNoNameRemarks + "|" + strfiYakaNoNameFlag + "|" + strfiLc1numberRemarks + "|" + strfiLc1numberFlag + "|" + strfiStgorempnoRemarks + "|" +
	                    strfiStgorempnoFlag + "|" + strfiMbnumnotinnameRemarks + "|" + strfiMbnumnotinnameFlag;
				strMsg2 = strMsg + "|" + strMsg1;
			}
			
			if (strId2.length() > 0) {
				//*******************************************************************
				System.out.println("Inside strId2");
//				strQuery = "From TvVerification where tvid = '" + strId2 + "'";
				
				try {
					Optional<TvVerification> opteleverification = teleVerifyRepo.findById(strId2);
					
					if(opteleverification.isPresent()) {

						TvVerification tv = opteleverification.get();
						guatwoStrSurnameremark = tv.getSurnameremark();
						guatwoStrSurnameflag = tv.getSurnameflag();
//						if (guatwoStrSurnameflag.equals("false")) {guatwoStrSurnameflag = "";}
						guatwoStrfnameremark = tv.getFirstnameremark(); 
						guatwoStrfnameflag = tv.getFirstnameflag();	
//						if (guatwoStrfnameflag.equals("false")) {guatwoStrfnameflag = "";}
						guatwoStronameremark = tv.getOthernameremark();
						guatwoStronameflag = tv.getOthernameflag();	
//						if (guatwoStronameflag.equals("false")) {guatwoStronameflag = "";}
						/*strmsremark = tv.getMaritalstatusremark();
						strmsflag = tv.getMaritalstatusflag();		
						if (strmsflag.equals("false")) {strmsflag = "";}
						strsexremark = tv.getSexremark();
						strsexflag = tv.getSexflag();	
						if (strsexflag.equals("false")) {strsexflag = "";}*/
						guatwoStrmobnoremark = tv.getMobilenoremark();
						guatwoStrmobnoflag = tv.getMobilenoflag();	
//						if (guatwoStrmobnoflag.equals("false")) {guatwoStrmobnoflag = "";}
						guatwoStrstgnmremark = tv.getStageremark();
						guatwoStrstgnmflag = tv.getStageflag();	
//						if (guatwoStrstgnmflag.equals("false")) {guatwoStrstgnmflag = "";}
						/*strdistremark = tv.getDistrictremark();
						strdistflag = tv.getDistrictflag();	
						if (strdistflag.equals("false")) {strdistflag = "";}*/
						guatwoStrlcnmremark = tv.getLcremark();
						guatwoStrlcnmflag = tv.getLcflag();	
//						if (guatwoStrlcnmflag.equals("false")) {guatwoStrlcnmflag = "";}
						/*strparremark = tv.getParishremark();
						strparflag = tv.getParishflag();	
						if (strparflag.equals("false")) {strparflag = "";}*/
//						guatwoStraddremark = tv.getAddressremark();
//						guatwoStraddflag = tv.getAddressflag();
//						if (guatwoStraddflag.equals("false")) {guatwoStraddflag = "";}
						guatwoStrnidremark = tv.getNationalidremark();
						guatwoStrnidflag = tv.getNationalidflag();	
//						if (guatwoStrnidflag.equals("false")) {guatwoStrnidflag = "";}
						guatwoStrbregnoremark = tv.getBikeregnoremark();
						guatwoStrbregnoflag = tv.getBikeregnoflag();	
//						if (guatwoStrbregnoflag.equals("false")) {guatwoStrbregnoflag = "";}
						/*strbuseremark = tv.getBikeuseremark();
						strbuseflag = tv.getBikeuseflag();	
						if (strbuseflag.equals("false")) {strbuseflag = "";}
						strdobremark = tv.getDobremark();
						strdobflag = tv.getDobremark();	
						if (strdobflag.equals("false")) {strdobflag = "";}
						strcntremark = tv.getCountyremark();
						strcntflag = tv.getCountyflag();	
						if (strcntflag.equals("false")) {strcntflag = "";}
						strscntremark = tv.getSubcountyremark();
						strscntflag = tv.getSubcountyflag();	
						if (strscntflag.equals("false")) {strscntflag = "";}
						strvilremark = tv.getVillageremark();
						strvilflag = tv.getVillageflag();	
						if (strvilflag.equals("false")) {strvilflag = "";}*/
						guatwoStryrvilremark = tv.getYearsinaddressremark();
						guatwoStryrvilflag = tv.getYearsinaddressflag();
//						if (guatwoStryrvilflag.equals("false")) {guatwoStryrvilflag = "";}
//						strnokremark = tv.getNextofkinremark();
//						strnokflag = tv.getNextofkinflag();	
//						if (strnokflag.equals("false")) {strnokflag = "";}
//						strnokmremark = tv.getNokmobilenoremark();
//						strnokmflag = tv.getNokmobilenoflag();	
//						if (strnokmflag.equals("false")) {strnokmflag = "";}
//						strnokrremark = tv.getNokrelationshipremark();
//						strnokrflag = tv.getNokrelationshipflag();	
//						if (strnokrflag.equals("false")) {strnokrflag = "";}
//						strnokaremark = tv.getNokagreeingremark();
//						strnokaflag = tv.getNokagreeingflag();	
//						if (strnokaflag.equals("false")) {strnokaflag = "";}
						/*strdlremark = tv.getDrivingpermitremark();
						strdlflag = tv.getDrivingpermitflag();		
						if (strdlflag.equals("false")) {strdlflag = "";}
						strnatremark = tv.getNationalityremark();
						strnatflag = tv.getNationalityflag();	
						if (strnatflag.equals("false")) {strnatflag = "";}
						strnodremark = tv.getNoofdependantsremark();
						strnodflag = tv.getNoofdependantsflag();	
						if (strnodflag.equals("false")) {strnodflag = "";}*/
						guatwoStrorremark = tv.getOwnhouserentedremark();
						guatwoStrorflag = tv.getOwnhouserentedflag();		
//						if (guatwoStrorflag.equals("false")) {guatwoStrorflag = "";}
						/*strllremark = tv.getLandlordnameremark();
						strllflag = tv.getLandlordnameflag();		
						if (strllflag.equals("false")) {strllflag = "";}
						strllmobremark = tv.getLandlordmobilenoremark();
						strllmobflag = tv.getLandlordmobilenoflag();	
						if (strllmobflag.equals("false")) {strllmobflag = "";}*/
						guatwoStrrpmremark = tv.getRentpmremark();
						guatwoStrrpmflag = tv.getRentpmflag();	
//						if (guatwoStrrpmflag.equals("false")) {guatwoStrrpmflag = "";}
						guatwoStroisremark = tv.getOtherincomesourceremark();
						guatwoStroisflag = tv.getOtherincomesourceflag();	
//						if (guatwoStroisflag.equals("false")) {guatwoStroisflag = "";}
						/*strdpsremark = tv.getDownpaymentsourceremark();
						strdpsflg = tv.getDownpaymentsourceflag();		
						if (strdpsflg.equals("false")) {strdpsflg = "";}*/
						guatwoStrpaddremark = tv.getPermanentaddressremark();
						guatwoStrpaddflag = tv.getPermanentaddressflag();	
//						if (guatwoStrpaddflag.equals("false")) {guatwoStrpaddflag = "";}
						/*strfathremark = tv.getFathersnameremark();
						strfathflag = tv.getFathersnameflag();	
						if (strfathflag.equals("false")) {strfathflag = "";}
						strmothremark = tv.getMothersnameremark();
						strmothflag = tv.getMothersnameflag();	
						if (strmothflag.equals("false")) {strmothflag = "";}
						strnpsremark = tv.getNearbypolicestationremark();
						strnpsflag = tv.getNearbypolicestationflag();	
						if (strnpsflag.equals("false")) {strnpsflag = "";}
						strlcnmobnoremark = tv.getLcmobilenoremark();
						strlcnmobnoflag = tv.getLcmobilenoflag();	
						if (strlcnmobnoflag.equals("false")) {strlcnmobnoflag = "";}
						strcusttyperemark = tv.getCusttyperemark();
						strcusttypeflag = tv.getCusttypeflag();
						if (strcusttypeflag.equals("false")) {strcusttypeflag = "";}*/						
						guatwoStrbownerremark = tv.getBikeownerremark();
						guatwoStrbownerflag = tv.getBikeownerflag();
//						if (guatwoStrbownerflag.equals("false")) {guatwoStrbownerflag = "";}
						guatwoStrsalremark = tv.getSalariedremark();
						guatwoStrsalflag = tv.getSalariedflag();
//						if (guatwoStrsalflag.equals("false")) {guatwoStrsalflag = "";}
						guatwoStrmiremark = tv.getMonthlyincomeremark();
						guatwoStrmiflag = tv.getMonthlyincomeflag();
//						if (guatwoStrmiflag.equals("false")) {guatwoStrmiflag = "";}
						guatwoStroiremark = tv.getOtherincomeremark();
						guatwoStroiflag = tv.getOtherincomeflag();
//						if (guatwoStroiflag.equals("false")) {guatwoStroiflag = "";}
						guatwoStrstgaddremark = tv.getStageaddressremark();
						guatwoStrstgaddflag = tv.getStageaddressflag();
//						if (guatwoStrstgaddflag.equals("false")) {guatwoStrstgaddflag = "";}
						guatwoStrstgccremark = tv.getStagechairconfirmationremark();
						guatwoStrstgccflag = tv.getStagechairconfirmationflag();
//						if (guatwoStrstgccflag.equals("false")) {guatwoStrstgccflag = "";}
//						guatwoStrrltnshpremark = tv.getRelationshipremark();
//						guatwoStrrltnshpflag = tv.getRelationshipflag();
//						if (guatwoStrrltnshpflag.equals("false")) {guatwoStrrltnshpflag = "";}
						
						guatwostrtvDobRemarks = tv.getDobremark();
						guatwostrtvDobFlag = tv.getDobflag();
//						if (guatwostrtvDobFlag.equals("false")) {
//						    guatwostrtvDobFlag = "";
//						}

						guatwostrtvLlnameRemarks = tv.getLandlordnameremark();
						guatwostrtvLlnameFlag = tv.getLandlordnameflag();
//						if (guatwostrtvLlnameFlag.equals("false")) {
//						    guatwostrtvLlnameFlag = "";
//						}

						guatwostrtvLlmbnoRemarks = tv.getLandlordmobilenoremark();
						guatwostrtvLlmbnoFlag = tv.getLandlordmobilenoflag();
//						if (guatwostrtvLlmbnoFlag.equals("false")) {
//						    guatwostrtvLlmbnoFlag = "";
//						}

						guatwostrtvResiadrssRemarks = tv.getResiadrssremarks();
						guatwostrtvResiadrssFlag = tv.getResiadrssflag();
//						if (guatwostrtvResiadrssFlag.equals("false")) {
//						    guatwostrtvResiadrssFlag = "";
//						}

						guatwostrtvMaritalstsRemarks = tv.getMaritalstatusremark();
						guatwostrtvMaritalstsFlag = tv.getMaritalstatusflag();
//						if (guatwostrtvMaritalstsFlag.equals("false")) {
//						    guatwostrtvMaritalstsFlag = "";
//						}

						guatwostrtvLLFeedAbtRntRemarks = tv.getLlrentfeedbackremarks();
						guatwostrtvLLFeedAbtRntFlag = tv.getLlrentfeedbackflag();
//						if (guatwostrtvLLFeedAbtRntFlag.equals("false")) {
//						    guatwostrtvLLFeedAbtRntFlag = "";
//						}

						guatwostrtvNoOfYrsInAreaRemarks = tv.getNoyrsinarearemarks();
						guatwostrtvNoOfYrsInAreaFlag = tv.getNoyrsinareaflag();
//						if (guatwostrtvNoOfYrsInAreaFlag.equals("false")) {
//						    guatwostrtvNoOfYrsInAreaFlag = "";
//						}

						guatwostrtvLc1chmnRecFeedbkRemarks = tv.getLc1chmnrecfeedremarks();
						guatwostrtvLc1chmnRecFeedbkFlag = tv.getLc1chmnrecfeedflag();
//						if (guatwostrtvLc1chmnRecFeedbkFlag.equals("false")) {
//						    guatwostrtvLc1chmnRecFeedbkFlag = "";
//						}

						guatwostrtvNearLndmrkorResiRemarks = tv.getNearlmarkresiremarks();
						guatwostrtvNearLndmrkorResiFlag = tv.getNearlmarkresiflag();
//						if (guatwostrtvNearLndmrkorResiFlag.equals("false")) {
//						    guatwostrtvNearLndmrkorResiFlag = "";
//						}

						guatwostrtvEmpTypeRemarks = tv.getEmptyperemarks();
						guatwostrtvEmpTypeFlag = tv.getEmptypeflag();
//						if (guatwostrtvEmpTypeFlag.equals("false")) {
//						    guatwostrtvEmpTypeFlag = "";
//						}

						guatwostrtvStgOrWrkadrssWithNearLNMRKRemarks = tv.getStgorwrkadrssnearlmarkremarks();
						guatwostrtvStgOrWrkadrssWithNearLNMRKFlag = tv.getStgorwrkadrssnearlmarkflag();
//						if (guatwostrtvStgOrWrkadrssWithNearLNMRKFlag.equals("false")) {
//						    guatwostrtvStgOrWrkadrssWithNearLNMRKFlag = "";
//						}

						guatwostrtvNoOfYrsInstgorBusiRemarks = tv.getNoofyrsinstgorbusiremarks();
						guatwostrtvNoOfYrsInstgorBusiFlag = tv.getNoofyrsinstgorbuisflag();
//						if (guatwostrtvNoOfYrsInstgorBusiFlag.equals("false")) {
//						    guatwostrtvNoOfYrsInstgorBusiFlag = "";
//						}

						guatwostrtvSpouseNameRemarks = tv.getSpousenameremarks();
						guatwostrtvSpouseNameFlag = tv.getSpousenameflag();
//						if (guatwostrtvSpouseNameFlag.equals("false")) {
//						    guatwostrtvSpouseNameFlag = "";
//						}

						guatwostrtvSpouseMbnoRemarks = tv.getSpousenoremarks();
						guatwostrtvSpouseMbnoFlag = tv.getSpousenoflag();
//						if (guatwostrtvSpouseMbnoFlag.equals("false")) {
//						    guatwostrtvSpouseMbnoFlag = "";
//						}

						guatwostrtvYakaNumRemarks = tv.getYakanumremarks();
						guatwostrtvYakaNumFlag = tv.getYakanumflag();
//						if (guatwostrtvYakaNumFlag.equals("false")) {
//						    guatwostrtvYakaNumFlag = "";
//						}

						guatwostrtvYakaNoNameRemarks = tv.getYakanumnameremarks();
						guatwostrtvYakaNoNameFlag = tv.getYakanumnamflag();
//						if (guatwostrtvYakaNoNameFlag.equals("false")) {
//						    guatwostrtvYakaNoNameFlag = "";
//						}

						guatwostrtvLc1numberRemarks = tv.getLcmobilenoremark();
						guatwostrtvLc1numberFlag = tv.getLcmobilenoflag();
//						if (guatwostrtvLc1numberFlag.equals("false")) {
//						    guatwostrtvLc1numberFlag = "";
//						}

						guatwostrtvStgorempnoRemarks = tv.getStagechairmannoremarks();
						guatwostrtvStgorempnoFlag = tv.getStagechairmannoflag();
//						if (guatwostrtvStgorempnoFlag.equals("false")) {
//						    guatwostrtvStgorempnoFlag = "";
//						}
			
						guatwostrtvMbnumnotinnameRemarks = tv.getMbnonotinnameremarks();
						guatwostrtvMbnumnotinnameFlag = tv.getMbnonotinnameflag();
//						if (guatwostrtvMbnumnotinnameFlag.equals("false")) {
//							guatwostrtvMbnumnotinnameFlag = "";
//						}
						
						
					}
								
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());
					response.setErrorMsg("Error retrieving customer id: " + strCustid);
				}
				strMsg3 = guatwoStrSurnameremark + "|" + guatwoStrSurnameflag + "|" + guatwoStrfnameremark + "|" + guatwoStrfnameflag + "|" + guatwoStronameremark + "|" + 
						guatwoStronameflag + "|" + guatwoStrmsremark + "|" + guatwoStrmsflag + "|" + guatwoStrsexremark + "|" + guatwoStrsexflag + "|" + guatwoStrmobnoremark + "|" + 
						guatwoStrmobnoflag + "|" + guatwoStrstgnmremark + "|" + guatwoStrstgnmflag + "|" + guatwoStrdistremark + "|" + guatwoStrdistflag + "|" + guatwoStrlcnmremark + "|" + 
						guatwoStrlcnmflag + "|" + guatwoStrparremark + "|" + guatwoStrparflag + "|" + guatwoStrnidremark + "|" + guatwoStrnidflag + "|" + guatwoStrbregnoremark + "|" + 
						guatwoStrbregnoflag + "|" + guatwoStrbuseremark + "|" + guatwoStrbuseflag + "|" + guatwoStrdobremark + "|" + guatwoStrdobflag + "|" + guatwoStrcntremark + "|" + 
						guatwoStrcntflag + "|" + guatwoStrscntremark + "|" + guatwoStrscntflag + "|" + guatwoStrvilremark + "|" + guatwoStrvilflag + "|" + guatwoStryrvilremark + "|" + 
						guatwoStryrvilflag + "|" + guatwoStrnokremark + "|" + guatwoStrnokflag + "|" + guatwoStrnokmremark + "|" + guatwoStrnokmflag + "|" + guatwoStrnokrremark + "|" + 
						guatwoStrnokrflag + "|" + guatwoStrnokaremark + "|" + guatwoStrnokaflag + "|" + guatwoStrdlremark + "|" + guatwoStrdlflag + "|" + guatwoStrnatremark + "|" + 
						guatwoStrnatflag + "|" + guatwoStrnodremark + "|" + guatwoStrnodflag + "|" + guatwoStrorremark + "|" + guatwoStrorflag + "|" + guatwoStrllremark + "|" + 
						guatwoStrllflag + "|" + guatwoStrllmobremark + "|" + guatwoStrllmobflag + "|" + guatwoStrrpmremark + "|" + guatwoStrrpmflag + "|" + guatwoStroisremark + "|" + 
						guatwoStroisflag + "|" + guatwoStrdpsremark + "|" + guatwoStrdpsflg + "|" + guatwoStrpaddremark + "|" + guatwoStrpaddflag + "|" + guatwoStrfathremark + "|" + 
						guatwoStrfathflag + "|" + guatwoStrmothremark + "|" + guatwoStrmothflag + "|" + guatwoStrnpsremark + "|" + guatwoStrnpsflag + "|" + guatwoStrlcnmobnoremark + "|" + 
						guatwoStrlcnmobnoflag + "|" + guatwoStrcusttyperemark + "|" + guatwoStrcusttypeflag + "|" + guatwoStrbownerremark + "|" + guatwoStrbownerflag + "|" + 					
						guatwoStrsalremark + "|" + guatwoStrsalflag + "|" + guatwoStrmiremark + "|" + guatwoStrmiflag + "|" + guatwoStroiremark + "|" + guatwoStroiflag + "|" + 
						guatwoStrstgaddremark + "|" + guatwoStrstgaddflag + "|" + guatwoStrstgccremark + "|" + guatwoStrstgccflag + "|" + 
						guatwoStrrltnshpremark + "|" + guatwoStrrltnshpflag + "|" + guatwoStraddremark + "|" + guatwoStraddflag + "|" + guatwoStryrvilremark + "|" + guatwoStryrvilflag + "|" +
						guatwostrtvDobRemarks + "|" + guatwostrtvDobFlag + "|" + guatwostrtvLlnameRemarks + "|" + guatwostrtvLlnameFlag + "|" +  
						guatwostrtvLlmbnoRemarks + "|" + guatwostrtvLlmbnoFlag + "|" + guatwostrtvResiadrssRemarks + "|" + guatwostrtvResiadrssFlag + "|" + 
						guatwostrtvMaritalstsRemarks + "|" + guatwostrtvMaritalstsFlag + "|" + guatwostrtvLLFeedAbtRntRemarks + "|" + guatwostrtvLLFeedAbtRntFlag + "|" + 
						guatwostrtvNoOfYrsInAreaRemarks + "|" + guatwostrtvNoOfYrsInAreaFlag + "|" + guatwostrtvLc1chmnRecFeedbkRemarks + "|" + guatwostrtvLc1chmnRecFeedbkFlag + "|" + 
						guatwostrtvNearLndmrkorResiRemarks + "|" + guatwostrtvNearLndmrkorResiFlag + "|" + guatwostrtvEmpTypeRemarks + "|" + guatwostrtvEmpTypeFlag + "|" + 
						guatwostrtvStgOrWrkadrssWithNearLNMRKRemarks + "|" + guatwostrtvStgOrWrkadrssWithNearLNMRKFlag + "|" + guatwostrtvNoOfYrsInstgorBusiRemarks + "|" + 
						guatwostrtvNoOfYrsInstgorBusiFlag + "|" + guatwostrtvSpouseNameRemarks + "|" + guatwostrtvSpouseNameFlag + "|" + guatwostrtvSpouseMbnoRemarks + "|" + 
						guatwostrtvSpouseMbnoFlag + "|" + guatwostrtvYakaNumRemarks + "|" + guatwostrtvYakaNumFlag + "|" + guatwostrtvYakaNoNameRemarks + "|" + 
						guatwostrtvYakaNoNameFlag + "|" + guatwostrtvLc1numberRemarks + "|" + guatwostrtvLc1numberFlag + "|" + guatwostrtvStgorempnoRemarks + "|" + guatwostrtvStgorempnoFlag + "|" +
						guatwostrtvMbnumnotinnameRemarks + "|" + guatwostrtvMbnumnotinnameFlag;

				
//				strQuery = "From FiVerification where fiid = '" + strId2 +"'";
				
				try {
					Optional<FiVerification> opfiverification = fiVerifyRepo.findById(strId2);
					
					 if(opfiverification.isPresent()){
						
						 FiVerification fi = opfiverification.get();
						 
						 guatwoStrfiSurnameremark = fi.getSurnameremark();
						 guatwoStrfiSurnameflag = fi.getSurnameflag();	
//						if (guatwoStrfiSurnameflag.equals("false")) {guatwoStrfiSurnameflag = "";}
						guatwoStrfifnameremark = fi.getFirstnameremark(); 
						guatwoStrfifnameflag = fi.getFirstnameflag();
//						if (guatwoStrfifnameflag.equals("false")) {guatwoStrfifnameflag = "";}
						guatwoStrfionameremark = fi.getOthernameremark();
						guatwoStrfionameflag = fi.getOthernameflag();	
//						if (guatwoStrfionameflag.equals("false")) {guatwoStrfionameflag = "";}
						/*strfimsremark = fi.getMaritalstatusremark();
						strfimsflag = fi.getMaritalstatusflag();		
						if (strfimsflag.equals("false")) {strfimsflag = "";}
						strfisexremark = fi.getSexremark();
						strfisexflag = fi.getSexflag();	
						if (strfisexflag.equals("false")) {strfisexflag = "";}*/
						guatwoStrfimobnoremark = fi.getMobilenoremark();
						guatwoStrfimobnoflag = fi.getMobilenoflag();	
//						if (guatwoStrfimobnoflag.equals("false")) {guatwoStrfimobnoflag = "";}
						guatwoStrfistgnmremark = fi.getStageremark();
						guatwoStrfistgnmflag = fi.getStageflag();	
//						if (guatwoStrfistgnmflag.equals("false")) {guatwoStrfistgnmflag = "";}
						/*strfidistremark = fi.getDistrictremark();
						strfidistflag = fi.getDistrictflag();	
						if (strfidistflag.equals("false")) {strfidistflag = "";}*/
						guatwoStrfilcnmremark = fi.getLcremark();
						guatwoStrfilcnmflag = fi.getLcflag();	
						if (guatwoStrfilcnmflag.equals("false")) {guatwoStrfilcnmflag = "";}
						/*strfiparremark = fi.getParishremark();
						strfiparflag = fi.getParishflag();	
						if (strfiparflag.equals("false")) {strfiparflag = "";}*/
//						guatwoStrfiaddremark = fi.getAddressremark();
//						guatwoStrfiaddflag = fi.getAddressflag();
//						if (guatwoStrfiaddflag.equals("false")) {guatwoStrfiaddflag = "";}
						guatwoStrfinidremark = fi.getNationalidremark();
						guatwoStrfinidflag = fi.getNationalidflag();	
//						if (guatwoStrfinidflag.equals("false")) {guatwoStrfinidflag = "";}
						guatwoStrfibregnoremark = fi.getBikeregnoremark();
						guatwoStrfibregnoflag = fi.getBikeregnoflag();	
//						if (guatwoStrfibregnoflag.equals("false")) {guatwoStrfibregnoflag = "";}
						/*strfibuseremark = fi.getBikeuseremark();
						strfibuseflag = fi.getBikeuseflag();	
						if (strfibuseflag.equals("false")) {strfibuseflag = "";}
						strfidobremark = fi.getDobremark();
						strfidobflag = fi.getDobremark();	
						if (strfidobflag.equals("false")) {strfidobflag = "";}
						strficntremark = fi.getCountyremark();
						strficntflag = fi.getCountyflag();	
						if (strficntflag.equals("false")) {strficntflag = "";}
						strfiscntremark = fi.getSubcountyremark();
						strfiscntflag = fi.getSubcountyflag();	
						if (strfiscntflag.equals("false")) {strfiscntflag = "";}
						strfivilremark = fi.getVillageremark();
						strfivilflag = fi.getVillageflag();	
						if (strfivilflag.equals("false")) {strfivilflag = "";}*/
						guatwoStrfiyrvilremark = fi.getYearsinaddressremark();
						guatwoStrfiyrvilflag = fi.getYearsinaddressflag();	
//						if (guatwoStrfiyrvilflag.equals("false")) {guatwoStrfiyrvilflag = "";}
//						strfinokremark = fi.getNextofkinremark();
//						strfinokflag = fi.getNextofkinflag();	
//						if (strfinokflag.equals("false")) {strfinokflag = "";}
//						strfinokmremark = fi.getNokmobilenoremark();
//						strfinokmflag = fi.getNokmobilenoflag();	
//						if (strfinokmflag.equals("false")) {strfinokmflag = "";}
//						strfinokrremark = fi.getNokrelationshipremark();
//						strfinokrflag = fi.getNokrelationshipflag();	
//						if (strfinokrflag.equals("false")) {strfinokrflag = "";}
//						strfinokaremark = fi.getNokagreeingremark();
//						strfinokaflag = fi.getNokagreeingflag();	
//						if (strfinokaflag.equals("false")) {strfinokaflag = "";}
						/*strfidlremark = fi.getDrivingpermitremark();
						strfidlflag = fi.getDrivingpermitflag();		
						if (strfidlflag.equals("false")) {strfidlflag = "";}
						strfinatremark = fi.getNationalityremark();
						strfinatflag = fi.getNationalityflag();	
						if (strfinatflag.equals("false")) {strfinatflag = "";}
						strfinodremark = fi.getNoofdependantsremark();
						strfinodflag = fi.getNoofdependantsflag();	
						if (strfinodflag.equals("false")) {strfinodflag = "";}*/
						guatwoStrfiorremark = fi.getOwnhouserentedremark();
						guatwoStrfiorflag = fi.getOwnhouserentedflag();		
//						if (guatwoStrfiorflag.equals("false")) {guatwoStrfiorflag = "";}
						/*strfillremark = fi.getLandlordnameremark();
						strfillflag = fi.getLandlordnameflag();		
						if (strfillflag.equals("false")) {strfillflag = "";}
						strfillmobremark = fi.getLandlordmobilenoremark();
						strfillmobflag = fi.getLandlordmobilenoflag();	
						if (strfillmobflag.equals("false")) {strfillmobflag = "";}*/
						guatwoStrfirpmremark = fi.getRentpmremark();
						guatwoStrfirpmflag = fi.getRentpmflag();	
//						if (guatwoStrfirpmflag.equals("false")) {guatwoStrfirpmflag = "";}
						guatwoStrfioisremark = fi.getOtherincomesourceremark();
						guatwoStrfioisflag = fi.getOtherincomesourceflag();	
//						if (guatwoStrfioisflag.equals("false")) {guatwoStrfioisflag = "";}
						/*strfidpsremark = fi.getDownpaymentsourceremark();
						strfidpsflg = fi.getDownpaymentsourceflag();		
						if (strfidpsflg.equals("false")) {strfidpsflg = "";}*/
						guatwoStrfipaddremark = fi.getPermanentaddressremark();
						guatwoStrfipaddflag = fi.getPermanentaddressflag();	
//						if (guatwoStrfipaddflag.equals("false")) {guatwoStrfipaddflag = "";}
						/*strfifathremark = fi.getFathersnameremark();
						strfifathflag = fi.getFathersnameflag();	
						if (strfifathflag.equals("false")) {strfifathflag = "";}
						strfimothremark = fi.getMothersnameremark();
						strfimothflag = fi.getMothersnameflag();	
						if (strfimothflag.equals("false")) {strfimothflag = "";}
						strfinpsremark = fi.getNearbypolicestationremark();
						strfinpsflag = fi.getNearbypolicestationflag();	
						if (strfinpsflag.equals("false")) {strfinpsflag = "";}
						strfilcnmobnoremark = fi.getLcmobilenoremark();
						strfilcnmobnoflag = fi.getLcmobilenoflag();	
						if (strfilcnmobnoflag.equals("false")) {strfilcnmobnoflag = "";}
						strficusttyperemark = fi.getCusttyperemark();
						strficusttypeflag = fi.getCusttypeflag();
						if (strficusttypeflag.equals("false")) {strficusttypeflag = "";}*/						
						guatwoStrfibownerremark = fi.getBikeownerremark();
						guatwoStrfibownerflag = fi.getBikeownerflag();
//						if (guatwoStrfibownerflag.equals("false")) {guatwoStrfibownerflag = "";}
						guatwoStrfisalremark = fi.getSalariedremark();
						guatwoStrfisalflag = fi.getSalariedflag();
//						if (guatwoStrfisalflag.equals("false")) {guatwoStrfisalflag = "";}
						guatwoStrfimiremark = fi.getMonthlyincomeremark();
						guatwoStrfimiflag = fi.getMonthlyincomeflag();
//						if (guatwoStrfimiflag.equals("false")) {guatwoStrfimiflag = "";}
						guatwoStrfioiremark = fi.getOtherincomeremark();
						guatwoStrfioiflag = fi.getOtherincomeflag();
//						if (guatwoStrfioiflag.equals("false")) {guatwoStrfioiflag = "";}
						guatwoStrfistgaddremark = fi.getStageaddressremark();
						guatwoStrfistgaddflag = fi.getStageaddressflag();
//						if (guatwoStrfistgaddflag.equals("false")) {guatwoStrfistgaddflag = "";}
						guatwoStrfistgccremark = fi.getStagechairconfirmationremark();
						guatwoStrfistgccflag = fi.getStagechairconfirmationflag();
//						if (guatwoStrfistgccflag.equals("false")) {guatwoStrfistgccflag = "";}
//						guatwoStrfirltnshpremark = fi.getRelationshipremark();
//						guatwoStrfirltnshpflag = fi.getRelationshipflag();
//						if (guatwoStrfirltnshpflag.equals("false")) {guatwoStrrltnshpflag = "";}
						
						guatwostrfiDobRemarks = fi.getDobremark();
						guatwostrfiDobFlag = fi.getDobflag();
//						if (guatwostrfiDobFlag.equals("false")) { guatwostrfiDobFlag = ""; }

						guatwostrfiLlnameRemarks = fi.getLandlordnameremark();
						guatwostrfiLlnameFlag = fi.getLandlordnameflag();
//						if (guatwostrfiLlnameFlag.equals("false")) { guatwostrfiLlnameFlag = ""; }

						guatwostrfiLlmbnoRemarks = fi.getLandlordmobilenoremark();
						guatwostrfiLlmbnoFlag = fi.getLandlordmobilenoflag();
//						if (guatwostrfiLlmbnoFlag.equals("false")) { guatwostrfiLlmbnoFlag = ""; }

						guatwostrfiResiadrssRemarks = fi.getResiadrssremarks();
						guatwostrfiResiadrssFlag = fi.getResiadrssflag();
//						if (guatwostrfiResiadrssFlag.equals("false")) { guatwostrfiResiadrssFlag = ""; }

						guatwostrfiMaritalstsRemarks = fi.getMaritalstatusremark();
						guatwostrfiMaritalstsFlag = fi.getMaritalstatusflag();
//						if (guatwostrfiMaritalstsFlag.equals("false")) { guatwostrfiMaritalstsFlag = ""; }

						guatwostrfiLLFeedAbtRntRemarks = fi.getLlrentfeedbackremarks();
						guatwostrfiLLFeedAbtRntFlag = fi.getLlrentfeedbackflag();
//						if (guatwostrfiLLFeedAbtRntFlag.equals("false")) { guatwostrfiLLFeedAbtRntFlag = ""; }

						guatwostrfiNoOfYrsInAreaRemarks = fi.getNoyrsinarearemarks();
						guatwostrfiNoOfYrsInAreaFlag = fi.getNoyrsinareaflag();
//						if (guatwostrfiNoOfYrsInAreaFlag.equals("false")) { guatwostrfiNoOfYrsInAreaFlag = ""; }

						guatwostrfiLc1chmnRecFeedbkRemarks = fi.getLc1chmnrecfeedremarks();
						guatwostrfiLc1chmnRecFeedbkFlag = fi.getLc1chmnrecfeedflag();
//						if (guatwostrfiLc1chmnRecFeedbkFlag.equals("false")) { guatwostrfiLc1chmnRecFeedbkFlag = ""; }

						guatwostrfiNearLndmrkorResiRemarks = fi.getNearlmarkresiremarks();
						guatwostrfiNearLndmrkorResiFlag = fi.getNearlmarkresiflag();
//						if (guatwostrfiNearLndmrkorResiFlag.equals("false")) { guatwostrfiNearLndmrkorResiFlag = ""; }

						guatwostrfiEmpTypeRemarks = fi.getEmptyperemarks();
						guatwostrfiEmpTypeFlag = fi.getEmptypeflag();
//						if (guatwostrfiEmpTypeFlag.equals("false")) { guatwostrfiEmpTypeFlag = ""; }

						guatwostrfiStgOrWrkadrssWithNearLNMRKRemarks = fi.getStgorwrkadrssnearlmarkremarks();
						guatwostrfiStgOrWrkadrssWithNearLNMRKFlag = fi.getStgorwrkadrssnearlmarkflag();
//						if (guatwostrfiStgOrWrkadrssWithNearLNMRKFlag.equals("false")) { guatwostrfiStgOrWrkadrssWithNearLNMRKFlag = ""; }

						guatwostrfiNoOfYrsInstgorBusiRemarks = fi.getNoofyrsinstgorbusiremarks();
						guatwostrfiNoOfYrsInstgorBusiFlag = fi.getNoofyrsinstgorbuisflag();
//						if (guatwostrfiNoOfYrsInstgorBusiFlag.equals("false")) { guatwostrfiNoOfYrsInstgorBusiFlag = ""; }

						guatwostrfiSpouseNameRemarks = fi.getSpousenameremarks();
						guatwostrfiSpouseNameFlag = fi.getSpousenameflag();
//						if (guatwostrfiSpouseNameFlag.equals("false")) { guatwostrfiSpouseNameFlag = ""; }

						guatwostrfiSpouseMbnoRemarks = fi.getSpousenoremarks();
						guatwostrfiSpouseMbnoFlag = fi.getSpousenoflag();
//						if (guatwostrfiSpouseMbnoFlag.equals("false")) { guatwostrfiSpouseMbnoFlag = ""; }

						guatwostrfiYakaNumRemarks = fi.getYakanumremarks();
						guatwostrfiYakaNumFlag = fi.getYakanumflag();
//						if (guatwostrfiYakaNumFlag.equals("false")) { guatwostrfiYakaNumFlag = ""; }

						guatwostrfiYakaNoNameRemarks = fi.getYakanumnameremarks();
						guatwostrfiYakaNoNameFlag = fi.getYakanumnamflag();
//						if (guatwostrfiYakaNoNameFlag.equals("false")) { guatwostrfiYakaNoNameFlag = ""; }

						guatwostrfiLc1numberRemarks = fi.getLcmobilenoremark();
						guatwostrfiLc1numberFlag = fi.getLcmobilenoflag();
//						if (guatwostrfiLc1numberFlag.equals("false")) { guatwostrfiLc1numberFlag = ""; }

						guatwostrfiStgorempnoRemarks = fi.getStagechairmannoremarks();
						guatwostrfiStgorempnoFlag = fi.getStagechairmannoflag();
//						if (guatwostrfiStgorempnoFlag.equals("false")) { guatwostrfiStgorempnoFlag = ""; }
						
						guatwostrfiMbnumnotinnameRemarks = fi.getMbnonotinnameremarks();
						guatwostrfiMbnumnotinnameFlag = fi.getMbnonotinnameflag();
//						if (guatwostrfiMbnumnotinnameFlag.equals("false")) {guatwostrfiMbnumnotinnameFlag = "";}

					}
								
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());
					response.setErrorMsg("Error retrieving customer id: " + strCustid);
				}
				strMsg4 = guatwoStrfiSurnameremark + "|" + guatwoStrfiSurnameflag + "|" + guatwoStrfifnameremark + "|" + guatwoStrfifnameflag + "|" + guatwoStrfionameremark + "|" + 
						guatwoStrfionameflag + "|" + guatwoStrfimsremark + "|" + guatwoStrfimsflag + "|" + guatwoStrfisexremark + "|" + guatwoStrfisexflag + "|" + guatwoStrfimobnoremark + "|" + 
						guatwoStrfimobnoflag + "|" + guatwoStrfistgnmremark + "|" + guatwoStrfistgnmflag + "|" + guatwoStrfidistremark + "|" + guatwoStrfidistflag + "|" + guatwoStrfilcnmremark + "|" + 
						guatwoStrfilcnmflag + "|" + guatwoStrfiparremark + "|" + guatwoStrfiparflag + "|" + guatwoStrfinidremark + "|" + guatwoStrfinidflag + "|" + guatwoStrfibregnoremark + "|" + 
						guatwoStrfibregnoflag + "|" + guatwoStrfibuseremark + "|" + guatwoStrfibuseflag + "|" + guatwoStrfidobremark + "|" + guatwoStrfidobflag + "|" + guatwoStrficntremark + "|" + 
						guatwoStrficntflag + "|" + guatwoStrfiscntremark + "|" + guatwoStrfiscntflag + "|" + guatwoStrfivilremark + "|" + guatwoStrfivilflag + "|" + guatwoStrfiyrvilremark + "|" + 
						guatwoStrfiyrvilflag + "|" + guatwoStrfinokremark + "|" + guatwoStrfinokflag + "|" + guatwoStrfinokmremark + "|" + guatwoStrfinokmflag + "|" + guatwoStrfinokrremark + "|" + 
						guatwoStrfinokrflag + "|" + guatwoStrfinokaremark + "|" + guatwoStrfinokaflag + "|" + guatwoStrfidlremark + "|" + guatwoStrfidlflag + "|" + guatwoStrfinatremark + "|" + 
						guatwoStrfinatflag + "|" + guatwoStrfinodremark + "|" + guatwoStrfinodflag + "|" + guatwoStrfiorremark + "|" + guatwoStrfiorflag + "|" + guatwoStrfillremark + "|" + 
						guatwoStrfillflag + "|" + guatwoStrfillmobremark + "|" + guatwoStrfillmobflag + "|" + guatwoStrfirpmremark + "|" + guatwoStrfirpmflag + "|" + guatwoStrfioisremark + "|" + 
						guatwoStrfioisflag + "|" + guatwoStrfidpsremark + "|" + guatwoStrfidpsflg + "|" + guatwoStrfipaddremark + "|" + guatwoStrfipaddflag + "|" + guatwoStrfifathremark + "|" + 
						guatwoStrfifathflag + "|" + guatwoStrfimothremark + "|" + guatwoStrfimothflag + "|" + guatwoStrfinpsremark + "|" + guatwoStrfinpsflag + "|" + guatwoStrfilcnmobnoremark + "|" + 
						guatwoStrfilcnmobnoflag + "|" + guatwoStrficusttyperemark + "|" + guatwoStrficusttypeflag + "|" + guatwoStrfibownerremark + "|" + guatwoStrfibownerflag + "|" + 					
						guatwoStrfisalremark + "|" + guatwoStrfisalflag + "|" + guatwoStrfimiremark + "|" + guatwoStrfimiflag + "|" + guatwoStrfioiremark + "|" + guatwoStrfioiflag + "|" + 
						guatwoStrfistgaddremark + "|" + guatwoStrfistgaddflag + "|" + guatwoStrfistgccremark + "|" + guatwoStrfistgccflag + "|" + 
						guatwoStrfirltnshpremark + "|" + guatwoStrfirltnshpflag + "|" + guatwoStrfiaddremark + "|" + guatwoStrfiaddflag + "|" + guatwoStrfiyrvilremark + "|" + guatwoStrfiyrvilflag + "|" +
						guatwostrfiDobRemarks + "|" + guatwostrfiDobFlag + "|" + guatwostrfiLlnameRemarks + "|" + guatwostrfiLlnameFlag + "|" + guatwostrfiLlmbnoRemarks + "|" + guatwostrfiLlmbnoFlag + "|" +
	                    guatwostrfiResiadrssRemarks + "|" + guatwostrfiResiadrssFlag + "|" + guatwostrfiMaritalstsRemarks + "|" + guatwostrfiMaritalstsFlag + "|" + guatwostrfiLLFeedAbtRntRemarks + "|" +
	                    guatwostrfiLLFeedAbtRntFlag + "|" + guatwostrfiNoOfYrsInAreaRemarks + "|" + guatwostrfiNoOfYrsInAreaFlag + "|" + guatwostrfiLc1chmnRecFeedbkRemarks + "|" +
	                    guatwostrfiLc1chmnRecFeedbkFlag + "|" + guatwostrfiNearLndmrkorResiRemarks + "|" + guatwostrfiNearLndmrkorResiFlag + "|" + guatwostrfiEmpTypeRemarks + "|" + 
	                    guatwostrfiEmpTypeFlag + "|" + guatwostrfiStgOrWrkadrssWithNearLNMRKRemarks + "|" + guatwostrfiStgOrWrkadrssWithNearLNMRKFlag + "|" +
	                    guatwostrfiNoOfYrsInstgorBusiRemarks + "|" + guatwostrfiNoOfYrsInstgorBusiFlag + "|" + guatwostrfiSpouseNameRemarks + "|" + guatwostrfiSpouseNameFlag + "|" +
	                    guatwostrfiSpouseMbnoRemarks + "|" + guatwostrfiSpouseMbnoFlag + "|" + guatwostrfiYakaNumRemarks + "|" + guatwostrfiYakaNumFlag + "|" +
	                    guatwostrfiYakaNoNameRemarks + "|" + guatwostrfiYakaNoNameFlag + "|" + guatwostrfiLc1numberRemarks + "|" + guatwostrfiLc1numberFlag + "|" + guatwostrfiStgorempnoRemarks + "|" +
	                    guatwostrfiStgorempnoFlag + "|" + guatwostrfiMbnumnotinnameRemarks + "|" + guatwostrfiMbnumnotinnameFlag;

				
				//*******************************************************************
				strMsg5 = strMsg3 + "|" + strMsg4;
			} else {
				strMsg3 = guatwoStrSurnameremark + "|" + guatwoStrSurnameflag + "|" + guatwoStrfnameremark + "|" + guatwoStrfnameflag + "|" + guatwoStronameremark + "|" + 
						guatwoStronameflag + "|" + guatwoStrmsremark + "|" + guatwoStrmsflag + "|" + guatwoStrsexremark + "|" + guatwoStrsexflag + "|" + guatwoStrmobnoremark + "|" + 
						guatwoStrmobnoflag + "|" + guatwoStrstgnmremark + "|" + guatwoStrstgnmflag + "|" + guatwoStrdistremark + "|" + guatwoStrdistflag + "|" + guatwoStrlcnmremark + "|" + 
						guatwoStrlcnmflag + "|" + guatwoStrparremark + "|" + guatwoStrparflag + "|" + guatwoStrnidremark + "|" + guatwoStrnidflag + "|" + guatwoStrbregnoremark + "|" + 
						guatwoStrbregnoflag + "|" + guatwoStrbuseremark + "|" + guatwoStrbuseflag + "|" + guatwoStrdobremark + "|" + guatwoStrdobflag + "|" + guatwoStrcntremark + "|" + 
						guatwoStrcntflag + "|" + guatwoStrscntremark + "|" + guatwoStrscntflag + "|" + guatwoStrvilremark + "|" + guatwoStrvilflag + "|" + guatwoStryrvilremark + "|" + 
						guatwoStryrvilflag + "|" + guatwoStrnokremark + "|" + guatwoStrnokflag + "|" + guatwoStrnokmremark + "|" + guatwoStrnokmflag + "|" + guatwoStrnokrremark + "|" + 
						guatwoStrnokrflag + "|" + guatwoStrnokaremark + "|" + guatwoStrnokaflag + "|" + guatwoStrdlremark + "|" + guatwoStrdlflag + "|" + guatwoStrnatremark + "|" + 
						guatwoStrnatflag + "|" + guatwoStrnodremark + "|" + guatwoStrnodflag + "|" + guatwoStrorremark + "|" + guatwoStrorflag + "|" + guatwoStrllremark + "|" + 
						guatwoStrllflag + "|" + guatwoStrllmobremark + "|" + guatwoStrllmobflag + "|" + guatwoStrrpmremark + "|" + guatwoStrrpmflag + "|" + guatwoStroisremark + "|" + 
						guatwoStroisflag + "|" + guatwoStrdpsremark + "|" + guatwoStrdpsflg + "|" + guatwoStrpaddremark + "|" + guatwoStrpaddflag + "|" + guatwoStrfathremark + "|" + 
						guatwoStrfathflag + "|" + guatwoStrmothremark + "|" + guatwoStrmothflag + "|" + guatwoStrnpsremark + "|" + guatwoStrnpsflag + "|" + guatwoStrlcnmobnoremark + "|" + 
						guatwoStrlcnmobnoflag + "|" + guatwoStrcusttyperemark + "|" + guatwoStrcusttypeflag + "|" + guatwoStrbownerremark + "|" + guatwoStrbownerflag + "|" + 					
						guatwoStrsalremark + "|" + guatwoStrsalflag + "|" + guatwoStrmiremark + "|" + guatwoStrmiflag + "|" + guatwoStroiremark + "|" + guatwoStroiflag + "|" + 
						guatwoStrstgaddremark + "|" + guatwoStrstgaddflag + "|" + guatwoStrstgccremark + "|" + guatwoStrstgccflag + "|" + 
						guatwoStrrltnshpremark + "|" + guatwoStrrltnshpflag + "|" + guatwoStraddremark + "|" + guatwoStraddflag + "|" + guatwoStryrvilremark + "|" + guatwoStryrvilflag + "|" +
						guatwostrtvDobRemarks + "|" + guatwostrtvDobFlag + "|" + guatwostrtvLlnameRemarks + "|" + guatwostrtvLlnameFlag + "|" +  
						guatwostrtvLlmbnoRemarks + "|" + guatwostrtvLlmbnoFlag + "|" + guatwostrtvResiadrssRemarks + "|" + guatwostrtvResiadrssFlag + "|" + 
						guatwostrtvMaritalstsRemarks + "|" + guatwostrtvMaritalstsFlag + "|" + guatwostrtvLLFeedAbtRntRemarks + "|" + guatwostrtvLLFeedAbtRntFlag + "|" + 
						guatwostrtvNoOfYrsInAreaRemarks + "|" + guatwostrtvNoOfYrsInAreaFlag + "|" + guatwostrtvLc1chmnRecFeedbkRemarks + "|" + guatwostrtvLc1chmnRecFeedbkFlag + "|" + 
						guatwostrtvNearLndmrkorResiRemarks + "|" + guatwostrtvNearLndmrkorResiFlag + "|" + guatwostrtvEmpTypeRemarks + "|" + guatwostrtvEmpTypeFlag + "|" + 
						guatwostrtvStgOrWrkadrssWithNearLNMRKRemarks + "|" + guatwostrtvStgOrWrkadrssWithNearLNMRKFlag + "|" + guatwostrtvNoOfYrsInstgorBusiRemarks + "|" + 
						guatwostrtvNoOfYrsInstgorBusiFlag + "|" + guatwostrtvSpouseNameRemarks + "|" + guatwostrtvSpouseNameFlag + "|" + guatwostrtvSpouseMbnoRemarks + "|" + 
						guatwostrtvSpouseMbnoFlag + "|" + guatwostrtvYakaNumRemarks + "|" + guatwostrtvYakaNumFlag + "|" + guatwostrtvYakaNoNameRemarks + "|" + 
						guatwostrtvYakaNoNameFlag + "|" + guatwostrtvLc1numberRemarks + "|" + guatwostrtvLc1numberFlag + "|" + guatwostrtvStgorempnoRemarks + "|" + guatwostrtvStgorempnoFlag + "|" +
						guatwostrtvMbnumnotinnameRemarks + "|" + guatwostrtvMbnumnotinnameFlag;;
				
				strMsg4 = guatwoStrfiSurnameremark + "|" + guatwoStrfiSurnameflag + "|" + guatwoStrfifnameremark + "|" + guatwoStrfifnameflag + "|" + guatwoStrfionameremark + "|" + 
						guatwoStrfionameflag + "|" + guatwoStrfimsremark + "|" + guatwoStrfimsflag + "|" + guatwoStrfisexremark + "|" + guatwoStrfisexflag + "|" + guatwoStrfimobnoremark + "|" + 
						guatwoStrfimobnoflag + "|" + guatwoStrfistgnmremark + "|" + guatwoStrfistgnmflag + "|" + guatwoStrfidistremark + "|" + guatwoStrfidistflag + "|" + guatwoStrfilcnmremark + "|" + 
						guatwoStrfilcnmflag + "|" + guatwoStrfiparremark + "|" + guatwoStrfiparflag + "|" + guatwoStrfinidremark + "|" + guatwoStrfinidflag + "|" + guatwoStrfibregnoremark + "|" + 
						guatwoStrfibregnoflag + "|" + guatwoStrfibuseremark + "|" + guatwoStrfibuseflag + "|" + guatwoStrfidobremark + "|" + guatwoStrfidobflag + "|" + guatwoStrficntremark + "|" + 
						guatwoStrficntflag + "|" + guatwoStrfiscntremark + "|" + guatwoStrfiscntflag + "|" + guatwoStrfivilremark + "|" + guatwoStrfivilflag + "|" + guatwoStrfiyrvilremark + "|" + 
						guatwoStrfiyrvilflag + "|" + guatwoStrfinokremark + "|" + guatwoStrfinokflag + "|" + guatwoStrfinokmremark + "|" + guatwoStrfinokmflag + "|" + guatwoStrfinokrremark + "|" + 
						guatwoStrfinokrflag + "|" + guatwoStrfinokaremark + "|" + guatwoStrfinokaflag + "|" + guatwoStrfidlremark + "|" + guatwoStrfidlflag + "|" + guatwoStrfinatremark + "|" + 
						guatwoStrfinatflag + "|" + guatwoStrfinodremark + "|" + guatwoStrfinodflag + "|" + guatwoStrfiorremark + "|" + guatwoStrfiorflag + "|" + guatwoStrfillremark + "|" + 
						guatwoStrfillflag + "|" + guatwoStrfillmobremark + "|" + guatwoStrfillmobflag + "|" + guatwoStrfirpmremark + "|" + guatwoStrfirpmflag + "|" + guatwoStrfioisremark + "|" + 
						guatwoStrfioisflag + "|" + guatwoStrfidpsremark + "|" + guatwoStrfidpsflg + "|" + guatwoStrfipaddremark + "|" + guatwoStrfipaddflag + "|" + guatwoStrfifathremark + "|" + 
						guatwoStrfifathflag + "|" + guatwoStrfimothremark + "|" + guatwoStrfimothflag + "|" + guatwoStrfinpsremark + "|" + guatwoStrfinpsflag + "|" + guatwoStrfilcnmobnoremark + "|" + 
						guatwoStrfilcnmobnoflag + "|" + guatwoStrficusttyperemark + "|" + guatwoStrficusttypeflag + "|" + guatwoStrfibownerremark + "|" + guatwoStrfibownerflag + "|" + 					
						guatwoStrfisalremark + "|" + guatwoStrfisalflag + "|" + guatwoStrfimiremark + "|" + guatwoStrfimiflag + "|" + guatwoStrfioiremark + "|" + guatwoStrfioiflag + "|" + 
						guatwoStrfistgaddremark + "|" + guatwoStrfistgaddflag + "|" + guatwoStrfistgccremark + "|" + guatwoStrfistgccflag + "|" + 
						guatwoStrfirltnshpremark + "|" + guatwoStrfirltnshpflag + "|" + guatwoStrfiaddremark + "|" + guatwoStrfiaddflag + "|" + guatwoStrfiyrvilremark + "|" + guatwoStrfiyrvilflag + "|" +
						guatwostrfiDobRemarks + "|" + guatwostrfiDobFlag + "|" + guatwostrfiLlnameRemarks + "|" + guatwostrfiLlnameFlag + "|" + guatwostrfiLlmbnoRemarks + "|" + guatwostrfiLlmbnoFlag + "|" +
	                    guatwostrfiResiadrssRemarks + "|" + guatwostrfiResiadrssFlag + "|" + guatwostrfiMaritalstsRemarks + "|" + guatwostrfiMaritalstsFlag + "|" + guatwostrfiLLFeedAbtRntRemarks + "|" +
	                    guatwostrfiLLFeedAbtRntFlag + "|" + guatwostrfiNoOfYrsInAreaRemarks + "|" + guatwostrfiNoOfYrsInAreaFlag + "|" + guatwostrfiLc1chmnRecFeedbkRemarks + "|" +
	                    guatwostrfiLc1chmnRecFeedbkFlag + "|" + guatwostrfiNearLndmrkorResiRemarks + "|" + guatwostrfiNearLndmrkorResiFlag + "|" + guatwostrfiEmpTypeRemarks + "|" + 
	                    guatwostrfiEmpTypeFlag + "|" + guatwostrfiStgOrWrkadrssWithNearLNMRKRemarks + "|" + guatwostrfiStgOrWrkadrssWithNearLNMRKFlag + "|" +
	                    guatwostrfiNoOfYrsInstgorBusiRemarks + "|" + guatwostrfiNoOfYrsInstgorBusiFlag + "|" + guatwostrfiSpouseNameRemarks + "|" + guatwostrfiSpouseNameFlag + "|" +
	                    guatwostrfiSpouseMbnoRemarks + "|" + guatwostrfiSpouseMbnoFlag + "|" + guatwostrfiYakaNumRemarks + "|" + guatwostrfiYakaNumFlag + "|" +
	                    guatwostrfiYakaNoNameRemarks + "|" + guatwostrfiYakaNoNameFlag + "|" + guatwostrfiLc1numberRemarks + "|" + guatwostrfiLc1numberFlag + "|" + guatwostrfiStgorempnoRemarks + "|" +
	                    guatwostrfiStgorempnoFlag + "|" + guatwostrfiMbnumnotinnameRemarks + "|" + guatwostrfiMbnumnotinnameFlag;
				
				//*******************************************************************
				strMsg5 = strMsg3 + "|" + strMsg4;
			}			

		}
	
		response.setData(strMsg2 + ":" + strMsg5);
		return response;
	}
	
	public Response<List<GuarantorDto>> viewGuarantors() {
		Response<List<GuarantorDto>> response = new Response<>();
		List<Guarantor> cdList = guarantorRepo.findAll();
		List<GuarantorDto> cddList = new ArrayList<>();
		for(Guarantor cd :cdList) {
			GuarantorDto cdd = new GuarantorDto();
			cdd.setId(cd.getId());
			cdd.setSurname(cd.getSurname());
			cdd.setFirstname(cd.getFirstname());
			cddList.add(cdd);
		}
		response.setData(cddList);
		return response;
	}

}
