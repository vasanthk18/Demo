package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CashReceiptDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CashReceiptService;

@RestController
@RequestMapping("/api/v1/cashReceipt")
public class CashReceiptController {
	
	@Autowired
	private CashReceiptService cashReceiptService;
	
	@PostMapping
	public ResponseEntity<InputStreamResource> cashReceipt(@RequestBody CashReceiptDto cashReceiptDto){
		System.out.println(cashReceiptDto.getProposal());
		return cashReceiptService.saveCashReceipt(cashReceiptDto);
		
	}

}
