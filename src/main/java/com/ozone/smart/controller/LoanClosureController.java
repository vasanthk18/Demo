package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.AgreementCountDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.service.LoanClosureService;

@RestController
@RequestMapping("/api/v1")
public class LoanClosureController {
	
	@Autowired
	private LoanClosureService lcService;
	
	@GetMapping("/loanClosure")
	public Response<String> getLoanClosure(@RequestParam String agreeNo, @RequestParam String propNo, @RequestParam String userName){
		return lcService.getLoanClosure(agreeNo, propNo, userName);
	}
	
	@GetMapping("/agreement-count")
	public List<AgreementCountDto> getAgreementCount() {
	    return lcService.findAgreementCount();
	}
	

}
