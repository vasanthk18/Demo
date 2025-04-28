package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.FiVerificationDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.RiderDetailsDto;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.entity.FiVerification;
import com.ozone.smart.entity.RiderDetails;
import com.ozone.smart.entity.TvVerification;
import com.ozone.smart.repository.FiVerifyRepo;
import com.ozone.smart.repository.RiderRepo;
import com.ozone.smart.repository.TeleVerifyRepo;
import com.ozone.smart.util.TimeStampUtil;

import jakarta.transaction.Transactional;

@Service
public class RiderService {

	@Autowired
	private RiderRepo riderRepo;

	@Autowired
	private TeleVerifyRepo teleVerifyRepo;

	@Autowired
	private FiVerifyRepo fiVerifyRepo;

	public Response<String> saveRider(RiderDetailsDto riderDetailsDto) {

		TimeStampUtil timeStamp = new TimeStampUtil();
		String Requestdatetime = timeStamp.TimeStamp();
		Response<String> msg = new Response<String>();

		RiderDetails riderdet = new RiderDetails();

		String RiderName = riderDetailsDto.getFirstname();
		riderdet.setOtherid(riderDetailsDto.getOtherid());	
		riderdet.setProposalid(riderDetailsDto.getProposalid());
		riderdet.setDob(riderDetailsDto.getDob());
		riderdet.setFirstname(riderDetailsDto.getFirstname());
		riderdet.setCapturedatetime(Requestdatetime);
		riderdet.setMaritalstatus(riderDetailsDto.getMaritalstatus());
		riderdet.setMobileno(riderDetailsDto.getMobileno());
		riderdet.setStage(riderDetailsDto.getStage());
		riderdet.setStagechairman(riderDetailsDto.getStagechairman());
		riderdet.setStagechairmanno(riderDetailsDto.getStagechairmanno());
		riderdet.setCurrentbikeregno(riderDetailsDto.getCurrentbikeregno());
		riderdet.setNewbikeuse(riderDetailsDto.getNewbikeuse());
		riderdet.setNationalid(riderDetailsDto.getNationalid());
		riderdet.setOwnhouserented(riderDetailsDto.getOwnhouserented());
		riderdet.setLandlordname(riderDetailsDto.getLandlordname());
		riderdet.setLandlordmobileno(riderDetailsDto.getLandlordmobileno());
		riderdet.setRentpm(riderDetailsDto.getRentpm());
		riderdet.setOtherincomesource(riderDetailsDto.getOtherincomesource());
		riderdet.setPermanentaddress(riderDetailsDto.getPermanentaddress());
		riderdet.setLlrentfeedback(riderDetailsDto.getLlrentfeedback());
		riderdet.setNoyrsinarea(riderDetailsDto.getNoyrsinarea());
		riderdet.setLc1chmnrecfeed(riderDetailsDto.getLc1chmnrecfeed());
		riderdet.setNearlmarkresi(riderDetailsDto.getNearlmarkresi());
		riderdet.setEmptype(riderDetailsDto.getEmptype());
		riderdet.setStgorwrkadrssnearlmark(riderDetailsDto.getStgorwrkadrssnearlmark());
		riderdet.setStgoremprecm(riderDetailsDto.getStgoremprecm());
		riderdet.setNoofyrsinstgorbusi(riderDetailsDto.getNoofyrsinstgorbusi());
		riderdet.setStgnoofvehi(riderDetailsDto.getStgnoofvehi());
		riderdet.setBikeowner(riderDetailsDto.getBikeowner());
		riderdet.setNetincome(riderDetailsDto.getNetincome());
		riderdet.setBikeusearea(riderDetailsDto.getBikeusearea());
		riderdet.setSpousename(riderDetailsDto.getSpousename());
		riderdet.setSpouseno(riderDetailsDto.getSpouseno());
		riderdet.setSpouseconfirm(riderDetailsDto.getSpouseconfirm());

		riderdet.setRelawithapplicant(riderDetailsDto.getRelawithapplicant());
		riderdet.setResiadrss(riderDetailsDto.getResiadrss());
		riderdet.setNoofyrinaddrss(riderDetailsDto.getNoofyrinaddrss());
		riderdet.setMbnonotinname(riderDetailsDto.getMbnonotinname());
		riderdet.setLc(riderDetailsDto.getLc());
		riderdet.setLcmobileno(riderDetailsDto.getLcmobileno());
		riderdet.setArrangebtwnrider(riderDetailsDto.getArrangebtwnrider());
		riderdet.setNearbypolicestation(riderDetailsDto.getNearbypolicestation());
		riderdet.setPaymentbyrider(riderDetailsDto.getPaymentbyrider());

		
			riderRepo.save(riderdet);
			msg.setData("Rider " + RiderName + " Successfully Added");
		return msg;

	}

	public Response<String> updateRider(RiderDetailsDto riderDetailsDto) {
		Response<String> msg = new Response<String>();

		String otherid = riderDetailsDto.getOtherid();
		String proposalid = riderDetailsDto.getProposalid();
		String dob = riderDetailsDto.getDob();
		String firstname = riderDetailsDto.getFirstname();
		String maritalstatus = riderDetailsDto.getMaritalstatus();
		String mobileno = riderDetailsDto.getMobileno();
		String stage = riderDetailsDto.getStage();
		String stagechairman = riderDetailsDto.getStagechairman();
		String stagechairmanno = riderDetailsDto.getStagechairmanno();
		String currentbikeregno = riderDetailsDto.getCurrentbikeregno();
		String newbikeuse = riderDetailsDto.getNewbikeuse();
		String nationalid = riderDetailsDto.getNationalid();
		String ownhouserented = riderDetailsDto.getOwnhouserented();
		String landlordname = riderDetailsDto.getLandlordname();
		String landlordmobileno = riderDetailsDto.getLandlordmobileno();
		String rentpm = riderDetailsDto.getRentpm();
		String otherincomesource = riderDetailsDto.getOtherincomesource();
		String permanentaddress = riderDetailsDto.getPermanentaddress();
		String llrentfeedback = riderDetailsDto.getLlrentfeedback();
		String noyrsinarea = riderDetailsDto.getNoyrsinarea();
		String lc1chmnrecfeed = riderDetailsDto.getLc1chmnrecfeed();
		String nearlmarkresi = riderDetailsDto.getNearlmarkresi();
		String emptype = riderDetailsDto.getEmptype();
		String stgorwrkadrssnearlmark = riderDetailsDto.getStgorwrkadrssnearlmark();
		String stgoremprecm = riderDetailsDto.getStgoremprecm();
		String noofyrsinstgorbusi = riderDetailsDto.getNoofyrsinstgorbusi();
		String stgnoofvehi = riderDetailsDto.getStgnoofvehi();
		String bikeowner = riderDetailsDto.getBikeowner();
		String netincome = riderDetailsDto.getNetincome();
		String bikeusearea = riderDetailsDto.getBikeusearea();
		String spousename = riderDetailsDto.getSpousename();
		String spouseno = riderDetailsDto.getSpouseno();
		String spouseconfirm = riderDetailsDto.getSpouseconfirm();
		String relawithapplicant = riderDetailsDto.getRelawithapplicant();
		String resiadrss = riderDetailsDto.getResiadrss();
		String noofyrinaddrss = riderDetailsDto.getNoofyrinaddrss();
		String mbnonotinname = riderDetailsDto.getMbnonotinname();
		String lc = riderDetailsDto.getLc();
		String lcmobileno = riderDetailsDto.getLcmobileno();
		String arrangebtwnrider = riderDetailsDto.getArrangebtwnrider();
		String nearbypolicestation = riderDetailsDto.getNearbypolicestation();
		String paymentbyrider = riderDetailsDto.getPaymentbyrider();

		Optional<RiderDetails> opRider = riderRepo.findById(proposalid);

		if (opRider.isPresent()) {
			RiderDetails rider = opRider.get();

			rider.setOtherid(otherid);
			rider.setProposalid(proposalid);
			rider.setDob(dob);
			rider.setFirstname(firstname);
			rider.setMaritalstatus(maritalstatus);
			rider.setMobileno(mobileno);
			rider.setStage(stage);
			rider.setStagechairman(stagechairman);
			rider.setStagechairmanno(stagechairmanno);
			rider.setCurrentbikeregno(currentbikeregno);
			rider.setNewbikeuse(newbikeuse);
			rider.setNationalid(nationalid);
			rider.setOwnhouserented(ownhouserented);
			rider.setLandlordname(landlordname);
			rider.setLandlordmobileno(landlordmobileno);
			rider.setRentpm(rentpm);
			rider.setOtherincomesource(otherincomesource);
			rider.setPermanentaddress(permanentaddress);
			rider.setLlrentfeedback(llrentfeedback);
			rider.setNoyrsinarea(noyrsinarea);
			rider.setLc1chmnrecfeed(lc1chmnrecfeed);
			rider.setNearlmarkresi(nearlmarkresi);
			rider.setEmptype(emptype);
			rider.setStgorwrkadrssnearlmark(stgorwrkadrssnearlmark);
			rider.setStgoremprecm(stgoremprecm);
			rider.setNoofyrsinstgorbusi(noofyrsinstgorbusi);
			rider.setStgnoofvehi(stgnoofvehi);
			rider.setBikeowner(bikeowner);
			rider.setNetincome(netincome);
			rider.setBikeusearea(bikeusearea);
			rider.setSpousename(spousename);
			rider.setSpouseno(spouseno);
			rider.setSpouseconfirm(spouseconfirm);
			rider.setRelawithapplicant(relawithapplicant);
			rider.setResiadrss(resiadrss);
			rider.setNoofyrinaddrss(noofyrinaddrss);
			rider.setMbnonotinname(mbnonotinname);
			rider.setLc(lc);
			rider.setLcmobileno(lcmobileno);
			rider.setArrangebtwnrider(arrangebtwnrider);
			rider.setNearbypolicestation(nearbypolicestation);
			rider.setPaymentbyrider(paymentbyrider);

			try {
				riderRepo.save(rider);
				msg.setData("Rider " + firstname + " Updated Successfully");
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				Throwable th = e.getCause();
				System.out.println("THROWABLE INFO: " + th.getCause().toString());

				msg.setErrorMsg(th.getCause().toString());
			}

		}
		return msg;

	}

	@Transactional
	public Response<String> removeRider(String id, String name) {
		System.out.println("id & name >> " + id + name);
		Response<String> response = new Response<>();
		try {
			riderRepo.deleteBynationalid(id);
			response.setData("Rider " + id + " :: " + name + " has been deleted");
		} catch (Exception e) {
//			System.out.println(e.getLocalizedMessage());
			Throwable th = e.getCause();
//			System.out.println("THROWABLE INFO: " + th.getCause().toString());
			e.printStackTrace();
			response.setErrorMsg(th.getCause().toString());
		}
		return response;
	}

	public Response<List<RiderDetailsDto>> viewRider() {
		Response<List<RiderDetailsDto>> res = new Response<>();

		List<RiderDetails> rideList = riderRepo.RiderNameForUpdateRider();
		List<RiderDetailsDto> riderDdto = new ArrayList<>();
		for (RiderDetails ride : rideList) {
			RiderDetailsDto rideDdto = new RiderDetailsDto();
			rideDdto.setFirstname(ride.getFirstname());
			rideDdto.setOtherid(ride.getOtherid());
			rideDdto.setNationalid(ride.getNationalid());
			rideDdto.setProposalid(ride.getProposalid());
			riderDdto.add(rideDdto);
			res.setData(riderDdto);
		}
		return res;
	}

	public Response<RiderDetailsDto> viewRiderDetails(String id, String flag) {
          id = id.trim();

		Response<RiderDetailsDto> response = new Response<>();
		if (id == null || id.length() == 0) {
			response.setErrorMsg("Select customer id");

		} else {
//			id = id.substring(id.length() - 6);
			try {

				RiderDetails riderDetails = riderRepo.findRiderDetailsByFlag(id, flag);
				if (riderDetails != null) {
					RiderDetailsDto ridedto = new RiderDetailsDto();

					ridedto.setOtherid(riderDetails.getOtherid());
					ridedto.setDob(riderDetails.getDob());
					ridedto.setFirstname(riderDetails.getFirstname());
					ridedto.setMaritalstatus(riderDetails.getMaritalstatus());
					ridedto.setMobileno(riderDetails.getMobileno());
					ridedto.setStage(riderDetails.getStage());
					ridedto.setStagechairman(riderDetails.getStagechairman());
					ridedto.setStagechairmanno(riderDetails.getStagechairmanno());
					ridedto.setCurrentbikeregno(riderDetails.getCurrentbikeregno());
					ridedto.setNewbikeuse(riderDetails.getNewbikeuse());
					ridedto.setNationalid(riderDetails.getNationalid());
					ridedto.setOwnhouserented(riderDetails.getOwnhouserented());
					ridedto.setLandlordname(riderDetails.getLandlordname());
					ridedto.setLandlordmobileno(riderDetails.getLandlordmobileno());
					ridedto.setRentpm(riderDetails.getRentpm());
					ridedto.setOtherincomesource(riderDetails.getOtherincomesource());
					ridedto.setPermanentaddress(riderDetails.getPermanentaddress());
					ridedto.setLlrentfeedback(riderDetails.getLlrentfeedback());
					ridedto.setNoyrsinarea(riderDetails.getNoyrsinarea());
					ridedto.setLc1chmnrecfeed(riderDetails.getLc1chmnrecfeed());
					ridedto.setNearlmarkresi(riderDetails.getNearlmarkresi());
					ridedto.setEmptype(riderDetails.getEmptype());
					ridedto.setStgorwrkadrssnearlmark(riderDetails.getStgorwrkadrssnearlmark());
					ridedto.setStgoremprecm(riderDetails.getStgoremprecm());
					ridedto.setNoofyrsinstgorbusi(riderDetails.getNoofyrsinstgorbusi());
					ridedto.setStgnoofvehi(riderDetails.getStgnoofvehi());
					ridedto.setBikeowner(riderDetails.getBikeowner());
					ridedto.setNetincome(riderDetails.getNetincome());
					ridedto.setBikeusearea(riderDetails.getBikeusearea());
					ridedto.setSpousename(riderDetails.getSpousename());
					ridedto.setSpouseno(riderDetails.getSpouseno());
					ridedto.setSpouseconfirm(riderDetails.getSpouseconfirm());
					ridedto.setRelawithapplicant(riderDetails.getRelawithapplicant());
					ridedto.setResiadrss(riderDetails.getResiadrss());
					ridedto.setNoofyrinaddrss(riderDetails.getNoofyrinaddrss());
					ridedto.setMbnonotinname(riderDetails.getMbnonotinname());
					ridedto.setLc(riderDetails.getLc());
					ridedto.setLcmobileno(riderDetails.getLcmobileno());
					ridedto.setArrangebtwnrider(riderDetails.getArrangebtwnrider());
					ridedto.setNearbypolicestation(riderDetails.getNearbypolicestation());
					ridedto.setPaymentbyrider(riderDetails.getPaymentbyrider());
					
					ridedto.setCqc(riderDetails.isCqc());
					ridedto.setCqcremarks(riderDetails.getCqcremarks());
					ridedto.setTvverified(riderDetails.getTvverified());
					ridedto.setTvremarks(riderDetails.getTvremarks());
					ridedto.setFiverified(riderDetails.getFiverified());
					ridedto.setFiremarks(riderDetails.getFiremarks());
					
					ridedto.setCoremarks(riderDetails.getCoremarks());
					ridedto.setCoapproval(riderDetails.getCoapproval());

					response.setData(ridedto);

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
				response.setErrorMsg("Error retrieving customer id: " + id);
			}
		}
		return response;
	}

	public Response<TeleVerificationDto> viewRiderStatustv(String proposalId) {
		
		System.out.println("inside  view rider status rmakrs");
		System.out.println("proposalId >> "+proposalId);

		String strProposalid = proposalId.trim();
		System.out.println("proposalId >>> "+strProposalid);

		String strMsg = "";
		Response<TeleVerificationDto> response = new Response<>();
		if (strProposalid == null || strProposalid.length() == 0) {
			response.setErrorMsg("Select customer id");
		} else {
			try {
				String riderNatinalid = riderRepo.findRiderNationalId(strProposalid);

				TvVerification tv = teleVerifyRepo.findTvVerificationBynationalid(riderNatinalid);
				if (tv != null) {
					TeleVerificationDto tvverifyDto = new TeleVerificationDto();

					tvverifyDto.setFirstnameremarkRdR(tv.getFirstnameremark());
					tvverifyDto.setFirstnameflagRdR(tv.getFirstnameflag());

					tvverifyDto.setMaritalstatusremarkRdR(tv.getMaritalstatusremark());
					tvverifyDto.setMaritalstatusflagRdR(tv.getMaritalstatusflag());

					tvverifyDto.setMobilenoremarkRdR(tv.getMobilenoremark());
					tvverifyDto.setMobilenoflagRdR(tv.getMobilenoflag());

					tvverifyDto.setStageremarkRdR(tv.getStageremark());
					tvverifyDto.setStageflagRdR(tv.getStageflag());

					tvverifyDto.setStagechairmanremarkRdR(tv.getStagechairmannameremarks());
					tvverifyDto.setStagechairmanflagRdR(tv.getStagechairmannameflag());

					tvverifyDto.setStagechairmannoremarkRdR(tv.getStagechairmannoremarks());
					tvverifyDto.setStagechairmannoflagRdR(tv.getStagechairmannoflag());

					tvverifyDto.setLcremarkRdR(tv.getLcremark());
					tvverifyDto.setLcflagRdR(tv.getLcflag());

					tvverifyDto.setNationalidremarkRdR(tv.getNationalidremark());
					tvverifyDto.setNationalidflagRdR(tv.getNationalidflag());

					tvverifyDto.setBikeregnoremarkRdR(tv.getBikeregnoremark());
					tvverifyDto.setBikeregnoflagRdR(tv.getBikeregnoflag());

					tvverifyDto.setNewbikeuseremarkRdR(tv.getBikeuseremark());
					tvverifyDto.setNewbikeuseflagRdR(tv.getBikeuseflag());

					tvverifyDto.setDobremarkRdR(tv.getDobremark());
					tvverifyDto.setDobflagRdR(tv.getDobflag());

					tvverifyDto.setOwnhouserentedremarkRdR(tv.getOwnhouserentedremark());
					tvverifyDto.setOwnhouserentedflagRdR(tv.getOwnhouserentedflag());

					tvverifyDto.setLandlordnameremarkRdR(tv.getLandlordnameremark());
					tvverifyDto.setLandlordnameflagRdR(tv.getLandlordnameflag());

					tvverifyDto.setLandlordmobilenoremarkRdR(tv.getLandlordmobilenoremark());
					tvverifyDto.setLandlordmobilenoflagRdR(tv.getLandlordmobilenoflag());

					tvverifyDto.setRentpmremarkRdR(tv.getRentpmremark());
					tvverifyDto.setRentpmflagRdR(tv.getRentpmflag());

					tvverifyDto.setOtherincomesourceremarkRdR(tv.getOtherincomesourceremark());
					tvverifyDto.setOtherincomesourceflagRdR(tv.getOtherincomesourceflag());

					tvverifyDto.setPermanentaddressremarkRdR(tv.getPermanentaddressremark());
					tvverifyDto.setPermanentaddressflagRdR(tv.getPermanentaddressflag());

					tvverifyDto.setNearbypolicestationremarkRdR(tv.getNearbypolicestationremark());
					tvverifyDto.setNearbypolicestationflagRdR(tv.getNearbypolicestationflag());

					tvverifyDto.setLcmobilenoremarkRdR(tv.getLcmobilenoremark());
					tvverifyDto.setLcmobilenoflagRdR(tv.getLcmobilenoflag());

					tvverifyDto.setLlrentfeedbackremarkRdR(tv.getLlrentfeedbackremarks());
					tvverifyDto.setLlrentfeedbackflagRdR(tv.getLlrentfeedbackflag());

					tvverifyDto.setNoyrsinarearemarkRdR(tv.getNoyrsinarearemarks());
					tvverifyDto.setNoyrsinareaflagRdR(tv.getNoyrsinareaflag());

					tvverifyDto.setLc1chmnrecfeedremarkRdR(tv.getLc1chmnrecfeedremarks());
					tvverifyDto.setLc1chmnrecfeedflagRdR(tv.getLc1chmnrecfeedflag());

					tvverifyDto.setNearlmarkresiremarkRdR(tv.getNearlmarkresiremarks());
					tvverifyDto.setNearlmarkresiflagRdR(tv.getNearlmarkresiflag());

					tvverifyDto.setEmptyperemarkRdR(tv.getEmptyperemarks());
					tvverifyDto.setEmptypeflagRdR(tv.getEmptypeflag());

					tvverifyDto.setStgorwrkadrssnearlmarkremarkRdR(tv.getStgorwrkadrssnearlmarkremarks());
					tvverifyDto.setStgorwrkadrssnearlmarkflagRdR(tv.getStgorwrkadrssnearlmarkflag());

					tvverifyDto.setStgoremprecmremarkRdR(tv.getStgoremprecmremarks());
					tvverifyDto.setStgoremprecmflagRdR(tv.getStgoremprecmflag());

					tvverifyDto.setNoofyrsinstgorbusiremarkRdR(tv.getNoofyrsinstgorbusiremarks());
					tvverifyDto.setNoofyrsinstgorbusinessflagRdR(tv.getNoofyrsinstgorbuisflag());

					tvverifyDto.setStgnoofvehiremarkRdR(tv.getStgnoofvehiremarks());
					tvverifyDto.setStgnoofvehiflagRdR(tv.getStgnoofvehiflag());

					tvverifyDto.setBikeownerremarkRdR(tv.getOwnerofbikeremarks());
					tvverifyDto.setBikeownerflagRdR(tv.getOwnerofbikeflag());

					tvverifyDto.setNetincomeremarkRdR(tv.getNetincomeremarks());
					tvverifyDto.setNetincomeflagRdR(tv.getNetincomeflag());

					tvverifyDto.setBikeusearearemarkRdR(tv.getBikeusearearemarks());
					tvverifyDto.setBikeuseareaflagRdR(tv.getBikeuseareaflag());

					tvverifyDto.setSpousenameremarkRdR(tv.getSpousenameremarks());
					tvverifyDto.setSpousenameflagRdR(tv.getSpousenameflag());

					tvverifyDto.setSpousenonoremarkRdR(tv.getSpousenoremarks());
					tvverifyDto.setSpousenonoflagRdR(tv.getSpousenoflag());

					tvverifyDto.setSpouseconfirmremarkRdR(tv.getSpouseconfirmremarks());
					tvverifyDto.setSpouseconfirmflagRdR(tv.getSpouseconfirmflag());

					tvverifyDto.setRelawithapplicantremarkRdR(tv.getRelawithapplicantremarks());
					tvverifyDto.setRelawithapplicantflagRdR(tv.getRelawithapplicantflag());

					tvverifyDto.setPaymentbyriderremarkRdR(tv.getPaymentbyriderremarks());
					tvverifyDto.setPaymentbyriderflagRdR(tv.getPaymentbyriderflag());

					tvverifyDto.setArrangebtwnriderremarkRdR(tv.getArrangebtwnriderremarks());
					tvverifyDto.setArrangebtwnriderflagRdR(tv.getArrangebtwnriderflag());

					tvverifyDto.setResiadrssremarkRdR(tv.getResiadrssremarks());
					tvverifyDto.setResiadrssflagRdR(tv.getResiadrssflag());

					tvverifyDto.setNoofyrinaddrssremarkRdR(tv.getNoofyrinaddrssremarks());
					tvverifyDto.setNoofyrinaddrssflagRdR(tv.getNoofyrinaddrssflag());

					tvverifyDto.setMbnonotinnameremarkRdR(tv.getMbnonotinnameremarks());
					tvverifyDto.setMbnonotinnameflagRdR(tv.getMbnonotinnameflag());

					response.setData(tvverifyDto);

				}

			} catch (Exception e) {
//					System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
				strMsg = "Error retrieving customer id: " + strProposalid;
				response.setErrorMsg(strMsg);
			}

		}

		return response;
	}

	public Response<FiVerificationDto> viewRiderStatusfi(String proposalId) {
		
		System.out.println("inside  view fi rider status rmakrs");

		String strProposalid = proposalId.trim();
		String strMsg = "";
		Response<FiVerificationDto> response = new Response<>();
		if (strProposalid == null || strProposalid.length() == 0) {
			response.setErrorMsg("Select customer id");
		} else {
			try {
				String riderNatinalid = riderRepo.findRiderNationalId(strProposalid);

				FiVerification fi = fiVerifyRepo.findFiVerificationBynationalid(riderNatinalid);
				if (fi != null) {
					FiVerificationDto fiverifyDto = new FiVerificationDto();

					fiverifyDto.setFirstnameremarkRdR(fi.getFirstnameremark());
					fiverifyDto.setFirstnameflagRdR(fi.getFirstnameflag());

					fiverifyDto.setMaritalstatusremarkRdR(fi.getMaritalstatusremark());
					fiverifyDto.setMaritalstatusflagRdR(fi.getMaritalstatusflag());

					fiverifyDto.setMobilenoremarkRdR(fi.getMobilenoremark());
					fiverifyDto.setMobilenoflagRdR(fi.getMobilenoflag());

					fiverifyDto.setStageremarkRdR(fi.getStageremark());
					fiverifyDto.setStageflagRdR(fi.getStageflag());

					fiverifyDto.setStagechairmanremarkRdR(fi.getStagechairmannameremarks());
					fiverifyDto.setStagechairmanflagRdR(fi.getStagechairmannameflag());

					fiverifyDto.setStagechairmannoremarkRdR(fi.getStagechairmannoremarks());
					fiverifyDto.setStagechairmannoflagRdR(fi.getStagechairmannoflag());

					fiverifyDto.setLcremarkRdR(fi.getLcremark());
					fiverifyDto.setLcflagRdR(fi.getLcflag());

					fiverifyDto.setNationalidremarkRdR(fi.getNationalidremark());
					fiverifyDto.setNationalidflagRdR(fi.getNationalidflag());

					fiverifyDto.setBikeregnoremarkRdR(fi.getBikeregnoremark());
					fiverifyDto.setBikeregnoflagRdR(fi.getBikeregnoflag());

					fiverifyDto.setNewbikeuseremarkRdR(fi.getBikeuseremark());
					fiverifyDto.setNewbikeuseflagRdR(fi.getBikeuseflag());

					fiverifyDto.setDobremarkRdR(fi.getDobremark());
					fiverifyDto.setDobflagRdR(fi.getDobflag());

					fiverifyDto.setOwnhouserentedremarkRdR(fi.getOwnhouserentedremark());
					fiverifyDto.setOwnhouserentedflagRdR(fi.getOwnhouserentedflag());

					fiverifyDto.setLandlordnameremarkRdR(fi.getLandlordnameremark());
					fiverifyDto.setLandlordnameflagRdR(fi.getLandlordnameflag());

					fiverifyDto.setLandlordmobilenoremarkRdR(fi.getLandlordmobilenoremark());
					fiverifyDto.setLandlordmobilenoflagRdR(fi.getLandlordmobilenoflag());

					fiverifyDto.setRentpmremarkRdR(fi.getRentpmremark());
					fiverifyDto.setRentpmflagRdR(fi.getRentpmflag());

					fiverifyDto.setOtherincomesourceremarkRdR(fi.getOtherincomesourceremark());
					fiverifyDto.setOtherincomesourceflagRdR(fi.getOtherincomesourceflag());

					fiverifyDto.setPermanentaddressremarkRdR(fi.getPermanentaddressremark());
					fiverifyDto.setPermanentaddressflagRdR(fi.getPermanentaddressflag());

					fiverifyDto.setNearbypolicestationremarkRdR(fi.getNearbypolicestationremark());
					fiverifyDto.setNearbypolicestationflagRdR(fi.getNearbypolicestationflag());

					fiverifyDto.setLcmobilenoremarkRdR(fi.getLcmobilenoremark());
					fiverifyDto.setLcmobilenoflagRdR(fi.getLcmobilenoflag());

					fiverifyDto.setLlrentfeedbackremarkRdR(fi.getLlrentfeedbackremarks());
					fiverifyDto.setLlrentfeedbackflagRdR(fi.getLlrentfeedbackflag());

					fiverifyDto.setNoyrsinarearemarkRdR(fi.getNoyrsinarearemarks());
					fiverifyDto.setNoyrsinareaflagRdR(fi.getNoyrsinareaflag());

					fiverifyDto.setLc1chmnrecfeedremarkRdR(fi.getLc1chmnrecfeedremarks());
					fiverifyDto.setLc1chmnrecfeedflagRdR(fi.getLc1chmnrecfeedflag());

					fiverifyDto.setNearlmarkresiremarkRdR(fi.getNearlmarkresiremarks());
					fiverifyDto.setNearlmarkresiflagRdR(fi.getNearlmarkresiflag());

					fiverifyDto.setEmptyperemarkRdR(fi.getEmptyperemarks());
					fiverifyDto.setEmptypeflagRdR(fi.getEmptypeflag());

					fiverifyDto.setStgorwrkadrssnearlmarkremarkRdR(fi.getStgorwrkadrssnearlmarkremarks());
					fiverifyDto.setStgorwrkadrssnearlmarkflagRdR(fi.getStgorwrkadrssnearlmarkflag());

					fiverifyDto.setStgoremprecmremarkRdR(fi.getStgoremprecmremarks());
					fiverifyDto.setStgoremprecmflagRdR(fi.getStgoremprecmflag());

					fiverifyDto.setNoofyrsinstgorbusiremarkRdR(fi.getNoofyrsinstgorbusiremarks());
					fiverifyDto.setNoofyrsinstgorbusinessflagRdR(fi.getNoofyrsinstgorbuisflag());

					fiverifyDto.setStgnoofvehiremarkRdR(fi.getStgnoofvehiremarks());
					fiverifyDto.setStgnoofvehiflagRdR(fi.getStgnoofvehiflag());

					fiverifyDto.setBikeownerremarkRdR(fi.getOwnerofbikeremarks());
					fiverifyDto.setBikeownerflagRdR(fi.getOwnerofbikeflag());

					fiverifyDto.setNetincomeremarkRdR(fi.getNetincomeremarks());
					fiverifyDto.setNetincomeflagRdR(fi.getNetincomeflag());

					fiverifyDto.setBikeusearearemarkRdR(fi.getBikeusearearemarks());
					fiverifyDto.setBikeuseareaflagRdR(fi.getBikeuseareaflag());

					fiverifyDto.setSpousenameremarkRdR(fi.getSpousenameremarks());
					fiverifyDto.setSpousenameflagRdR(fi.getSpousenameflag());

					fiverifyDto.setSpousenonoremarkRdR(fi.getSpousenoremarks());
					fiverifyDto.setSpousenonoflagRdR(fi.getSpousenoflag());

					fiverifyDto.setSpouseconfirmremarkRdR(fi.getSpouseconfirmremarks());
					fiverifyDto.setSpouseconfirmflagRdR(fi.getSpouseconfirmflag());

					fiverifyDto.setRelawithapplicantremarkRdR(fi.getRelawithapplicantremarks());
					fiverifyDto.setRelawithapplicantflagRdR(fi.getRelawithapplicantflag());

					fiverifyDto.setPaymentbyriderremarkRdR(fi.getPaymentbyriderremarks());
					fiverifyDto.setPaymentbyriderflagRdR(fi.getPaymentbyriderflag());

					fiverifyDto.setArrangebtwnriderremarkRdR(fi.getArrangebtwnriderremarks());
					fiverifyDto.setArrangebtwnriderflagRdR(fi.getArrangebtwnriderflag());

					fiverifyDto.setResiadrssremarkRdR(fi.getResiadrssremarks());
					fiverifyDto.setResiadrssflagRdR(fi.getResiadrssflag());

					fiverifyDto.setNoofyrinaddrssremarkRdR(fi.getNoofyrinaddrssremarks());
					fiverifyDto.setNoofyrinaddrssflagRdR(fi.getNoofyrinaddrssflag());

					fiverifyDto.setMbnonotinnameremarkRdR(fi.getMbnonotinnameremarks());
					fiverifyDto.setMbnonotinnameflagRdR(fi.getMbnonotinnameflag());

					response.setData(fiverifyDto);

				}

			} catch (Exception e) {
//					System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
				strMsg = "Error retrieving customer id: " + strProposalid;
				response.setErrorMsg(strMsg);
			}

		}

		return response;
	}

}
