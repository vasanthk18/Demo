package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.ReverseTransactionDto;
import com.ozone.smart.service.ReverseTransactionService;

@RestController
@RequestMapping("/api/v1/reverseTnx")
public class ReverseTransactionController {
	
	
	@Autowired
	private ReverseTransactionService reverseTnxService;
	
	@GetMapping("/receipt")
	public Response<String> getReceiptDetails(@RequestParam String receipt){
		return reverseTnxService.fetchReceiptDetails(receipt);
	}
	
	@PutMapping("/reverse")
	public Response<String> ReverseTnxUpdate(@RequestBody ReverseTransactionDto reverseTransDto){
		return reverseTnxService.updateTnx(reverseTransDto);
	}

}
