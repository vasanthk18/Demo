package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.DistrictDto;
import com.ozone.smart.dto.LoanParamDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Districts;
import com.ozone.smart.entity.LoanParam;
import com.ozone.smart.repository.DistrictRepo;
import com.ozone.smart.repository.LoanParamRepo;
import com.ozone.smart.repository.StageRepo;

@Service
public class DropDownService {
	
	@Autowired
	DistrictRepo districtRepo;
	
	@Autowired
	LoanParamRepo loanParamRepo;
	
	public Response<List<DistrictDto>> viewDistrict() {
		Response<List<DistrictDto>> response = new Response<>();
		List<DistrictDto> LdistDto = new ArrayList<>();
		List<Districts> district = districtRepo.findAll() ;
		for(Districts dis : district) {
			DistrictDto distDto = new DistrictDto();
			distDto.setId(dis.getId());
			distDto.setName(dis.getName());
			LdistDto.add(distDto);
		}
		response.setData(LdistDto);
		return response;
	}

	public Response<List<LoanParamDto>> viewLoanParam() {
		Response<List<LoanParamDto>> response = new Response<>();
		List<LoanParamDto> ListLpDto = new ArrayList<>();
		List<LoanParam> lp = loanParamRepo.findAll();
		for(LoanParam loanParam : lp) {
			LoanParamDto lpDto = new LoanParamDto();
			lpDto.setIrr(loanParam.getIrr());
			lpDto.setMarginmoney(loanParam.getMarginmoney());
			lpDto.setPaymentmode(loanParam.getPaymentmode());
			lpDto.setTrackerfee(loanParam.getTrackerfee());
			ListLpDto.add(lpDto);
		}
		response.setData(ListLpDto);
		return response;
	}

}
