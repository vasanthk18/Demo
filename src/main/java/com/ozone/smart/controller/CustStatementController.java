package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CustStatementService;

@RestController
@RequestMapping("/api/v1/custStatement")
public class CustStatementController {
	
	@Autowired
	private CustStatementService custStatementService;

	@GetMapping("/printCustState")
	public ResponseEntity<InputStreamResource> printCustStatement(@RequestParam String customer , @RequestParam String agreement){
		
		return custStatementService.printStatement(customer,agreement);
	}

}
