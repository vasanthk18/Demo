package com.ozone.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class DetailedCustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;

	public Response<String> updateCust(CustomerDetailsDto customerDto) {
		
//		Response<String> response = new Response<>();
		
		TimeStampUtil timeStamp = new TimeStampUtil();
		String Requestdatetime = timeStamp.TimeStamp();
		boolean Noka = customerDto.isNokagreeing();
		boolean blnNoka;
		if (Noka) {
			blnNoka = true;
		} else {
			blnNoka = false;
		}	
		
		String CustId = customerDto.getOtherid();
		CustomerDetails custdet  = customerRepo.findByotherid(CustId);
		String custSurname = customerDto.getSurname();
		custdet.setOtherid(customerDto.getOtherid());
		custdet.setNationalid(customerDto.getNationalid());
		custdet.setDob(customerDto.getDob());
		custdet.setFirstname(customerDto.getFirstname());
		custdet.setOthername(customerDto.getOthername());
		custdet.setSurname(customerDto.getSurname());
		custdet.setMaritalstatus(customerDto.getMaritalstatus());
		custdet.setSex(customerDto.getSex());
		custdet.setMobileno(customerDto.getMobileno());
		custdet.setStage(customerDto.getStage());
		custdet.setStagechairman(customerDto.getStagechairman());
		custdet.setStagechairmanno(customerDto.getStagechairmanno());
		custdet.setCurrentbikeregno(customerDto.getCurrentbikeregno());
		custdet.setNewbikeuse(customerDto.getNewbikeuse());
		custdet.setDistrict(customerDto.getDistrict());
		custdet.setCounty(customerDto.getCounty());
		custdet.setSubcounty(customerDto.getSubcounty());
		custdet.setParish(customerDto.getParish());
		custdet.setVillage(customerDto.getVillage());
		custdet.setYearsinvillage(customerDto.getYearsinvillage());
		custdet.setNextofkin(customerDto.getNextofkin());
		custdet.setNokmobileno(customerDto.getNokmobileno());
		custdet.setNokrelationship(customerDto.getNokrelationship());
		custdet.setNokagreeing(blnNoka);
		custdet.setDrivingpermit(customerDto.getDrivingpermit());
		custdet.setNationality(customerDto.getNationality());
		custdet.setNoofdependants(customerDto.getNoofdependants());
		custdet.setOwnhouserented(customerDto.getOwnhouserented());
		custdet.setLandlordname(customerDto.getLandlordname());
		custdet.setLandlordmobileno(customerDto.getLandlordmobileno());
		custdet.setRentpm(customerDto.getRentpm());
		custdet.setOtherincomesource(customerDto.getOtherincomesource());
		custdet.setDownpaymentsource(customerDto.getDownpaymentsource());
		custdet.setPermanentaddress(customerDto.getPermanentaddress());
		custdet.setFathersname(customerDto.getFathersname());
		custdet.setMothersname(customerDto.getMothersname());
		custdet.setNearbypolicestation(customerDto.getNearbypolicestation());
		custdet.setLc(customerDto.getLc());
		custdet.setLcmobileno(customerDto.getLcmobileno());
		custdet.setCusttype(customerDto.getCusttype());
		custdet.setLlrentfeedback(customerDto.getLlrentfeedback());
		custdet.setNoyrsinarea(customerDto.getNoyrsinarea());
		custdet.setLc1chmnrecfeed(customerDto.getLc1chmnrecfeed());
		custdet.setNearlmarkresi(customerDto.getNearlmarkresi());
		custdet.setEmptype(customerDto.getEmptype());
		custdet.setStgorwrkadrssnearlmark(customerDto.getStgorwrkadrssnearlmark());
		custdet.setStgoremprecm(customerDto.getStgoremprecm());
		custdet.setNoofyrsinstgorbusi(customerDto.getNoofyrsinstgorbusi());
		custdet.setStgnoofvehi(customerDto.getStgnoofvehi());
		custdet.setBikeowner(customerDto.getBikeowner());
		custdet.setNetincome(customerDto.getNetincome());
		custdet.setBikeusearea(customerDto.getBikeusearea());
		custdet.setSpousename(customerDto.getSpousename());
		custdet.setSpouseno(customerDto.getSpouseno());
		custdet.setSpouseconfirm(customerDto.getSpouseconfirm());
		custdet.setOffcdistance(customerDto.getOffcdistance());
//		custdet.setRelawithapplicant(customerDto.getRelawithapplicant());
//		custdet.setPaymentbyrider(customerDto.getPaymentbyrider());
		custdet.setYakanum(customerDto.getYakanum());
		custdet.setYakanumname(customerDto.getYakanumname());
		custdet.setPaymtdetailstovby(customerDto.getPaymtdetailstovby());
		custdet.setCashpaymntworeceipt(customerDto.getCashpaymntworeceipt());
		custdet.setApplicantknowvby(customerDto.getApplicantknowvby());
		custdet.setRelawithguarantors(customerDto.getRelawithguarantors());
//		custdet.setArrangebtwnrider(customerDto.getArrangebtwnrider());
		custdet.setResiadrss(customerDto.getResiadrss());
		custdet.setNoofyrinaddrss(customerDto.getNoofyrinaddrss());
		custdet.setMbnonotinname(customerDto.getMbnonotinname());

		customerRepo.save(custdet);
		Response<String> msg = new Response<String>();
		msg.setData("Customer "+ custSurname+" Updated Successfully.");

		return msg;
	}

}
