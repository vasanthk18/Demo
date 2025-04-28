package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.service.PaymentScheduleService;

@RestController
@RequestMapping("/api/v1/printSchedule")
public class PaymentScheduleController {
	
	
	@Autowired
	private PaymentScheduleService paymentScheduleService;
	
	@GetMapping
	ResponseEntity<InputStreamResource> printSchedule(@RequestParam String custId, @RequestParam String agreeId){
		return paymentScheduleService.printSchedule(custId,agreeId);
//		if(res.getResponseCode() == 200) {
//			return new ResponseEntity<String>((String) res.getData(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>((String) res.getErrorMsg(), HttpStatus.BAD_REQUEST);
//		}
	}

}
