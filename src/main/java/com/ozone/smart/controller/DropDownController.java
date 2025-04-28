package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.DistrictDto;
import com.ozone.smart.dto.LoanParamDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Districts;
import com.ozone.smart.service.DropDownService;

@RestController
@RequestMapping("/api/v1")
public class DropDownController {
	
	@Autowired
	DropDownService ddService;
	
	@GetMapping("/districts")
	public Response<List<DistrictDto>> getDistricts(){
		return ddService.viewDistrict();
	}
	
	@GetMapping("/loanParam")
	public Response<List<LoanParamDto>> getLoanParam(){
		return ddService.viewLoanParam();
	}


}
