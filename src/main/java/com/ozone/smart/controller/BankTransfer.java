package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.service.BankTransferService;

@RestController
@RequestMapping("/api/v1")
public class BankTransfer {
	
	@Autowired
	BankTransferService bts;
	
	@PutMapping("/updateStatus")
	public String updateTransferStatus(@RequestParam String fromDate, @RequestParam String toDate) {
		return bts.transferStatus(fromDate, toDate);
	}

}
