package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DisbursementService;

@RestController
@RequestMapping("/api/v1/disbursement")
public class DisbursementController {
	
	@Autowired
	private DisbursementService disbursementService;
	
	@GetMapping("/viewDb")
	public Response<String> viewDbParameters(@RequestParam String customer, @RequestParam String proposal, @RequestParam String flag){
		return disbursementService.viewParameters(customer, proposal, flag);
	}
	
	@GetMapping("/schedule")
	public Response<String> scheduleGenServlet(@RequestParam String customer, @RequestParam String proposal, @RequestParam String username){
		return disbursementService.scheduleGen(customer,proposal,username);
		
	}
	
	@GetMapping("/getDisbursement")
	public Response<List<CustomerDetailsDto>> getCustId(){
		return disbursementService.viewCustId();
	}

}
