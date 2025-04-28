package com.ozone.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.DocSubmitDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.DocSubmission;
import com.ozone.smart.repository.CustomerRepo;
import com.ozone.smart.repository.DocSubmitRepo;
import com.ozone.smart.util.TimeStampUtil;

@Service
public class DocSubmitService {
	
	@Autowired
	private DocSubmitRepo docSubmitRepo;
	@Autowired
	private CustomerRepo customerRepo;

	public Response<String> saveDocSubmission(DocSubmitDto docSubmitDto) {
		
		Response<String> response = new Response<>();
		
		
		TimeStampUtil gts = new TimeStampUtil();		
		String Subdateime = gts.TimeStamp();
		Subdateime = Subdateime.trim();
		
		DocSubmission ds = new DocSubmission();
//		CustId -> otherId
		String CustId = docSubmitDto.getCustid();
		ds.setCustid(docSubmitDto.getCustid());
		ds.setApplicationform(docSubmitDto.getApplicationform());
		ds.setApplicationformremark(docSubmitDto.getApplicationformremark());
		ds.setGuarantoroneid(docSubmitDto.getGuarantoroneid());
		ds.setGuarantoroneidremark(docSubmitDto.getGuarantoroneidremark());
		ds.setGuarantortwoid(docSubmitDto.getGuarantortwoid());
		ds.setGuarantortwoidremark(docSubmitDto.getGuarantortwoidremark());
		ds.setLc1recomletter(docSubmitDto.getLc1recomletter());
		ds.setLc1recomletterremark(docSubmitDto.getLc1recomletterremark());
		ds.setMarriageproof(docSubmitDto.getMarriageproof());
		ds.setMarriageproofremark(docSubmitDto.getMarriageproofremark());
		ds.setNationalid(docSubmitDto.getNationalid());
		ds.setNationalidremark(docSubmitDto.getNationalidremark());
		ds.setRemarks(docSubmitDto.getRemarks());
		ds.setStageid(docSubmitDto.getStageid());
		ds.setStageidremark(docSubmitDto.getStageidremark());
		ds.setStagerecomletter(docSubmitDto.getStagerecomletter());
		ds.setStagerecomletterremark(docSubmitDto.getStagerecomletterremark());
		ds.setSubdatetime(Subdateime);
		ds.setSubuser(docSubmitDto.getSubuser());
		
		try {
			docSubmitRepo.save(ds);
			CustomerDetails cd = customerRepo.findByotherid(CustId);
			cd.setDcsubmit(true);
			CustomerDetails cd1 = customerRepo.save(cd);
			if(cd1 != null) {
				response.setData("DC submission for : " + CustId + " saved successfully.");
			}
		}catch(Exception e) {
			e.getLocalizedMessage();
			response.setErrorMsg("DC submission for : " + CustId + " Failed.");
		}
		return response;
	}
}
