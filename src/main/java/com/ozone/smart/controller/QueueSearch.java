package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.GuarantorService;
import com.ozone.smart.service.QueueSearchService;

@RestController
@RequestMapping("/api/v1/qSearch")
public class QueueSearch {
	
	@Autowired
	private QueueSearchService queueSearchService;
	
	@GetMapping("/customer")
	public Response<String> viewCustomer(@RequestParam String custId, @RequestParam String flag ){
		return queueSearchService.viewByCustId(custId,flag);
	}
	
	@GetMapping("/proposal")
	public Response<String> viewProposal(@RequestParam String custId, @RequestParam String flag ){
		return queueSearchService.viewProposalByCustId(custId,flag);
	}

}
