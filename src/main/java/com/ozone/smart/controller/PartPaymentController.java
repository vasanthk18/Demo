package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CashReceiptDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.PartPaymentService;

@RestController
@RequestMapping("/api/v1/partPayment")
public class PartPaymentController {
	
	@Autowired
	private PartPaymentService partPaymentService;
	
	@PostMapping("/generateCashReceipt")
	public ResponseEntity<InputStreamResource> getPartPayment(@RequestBody CashReceiptDto cashReceiptDto){
		
		return partPaymentService.getPayment(cashReceiptDto);
//		if(res.getResponseCode() == 200) {
//			return new ResponseEntity<String>((String) res.getData(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>((String) res.getErrorMsg(), HttpStatus.BAD_REQUEST);
//		}
	}
	
	@GetMapping("/totalToPay")
	public Response<String> getRepoTotaltoPay(@RequestParam String propNo){
		
		return partPaymentService.getTotal(propNo);
		
	}
	
	

}
