package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.FiVerificationDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.service.FiVerifyService;

@RestController
@RequestMapping("/api/v1/fi")
public class FiVerifyController {
	
	@Autowired
	private FiVerifyService fiverifyService;
	
	@PostMapping("/saveCust")
	public Response<String> saveCustFi(@RequestBody FiVerificationDto fiVerificationDto){
		return fiverifyService.addCustFi(fiVerificationDto);
	}
	
	@PostMapping("/saveGuaran")
	public Response<String> saveGuaranFi(@RequestBody FiVerificationDto fiVerificationDto){
		return fiverifyService.addGuaranFi(fiVerificationDto);
	}
	
	@PostMapping("/saveRider")
	public Response<String> saveRiderFi(@RequestBody FiVerificationDto fiVerificationDto){
		return fiverifyService.addRiderFi(fiVerificationDto);
	}
	
//	customer()check it
	@GetMapping("/printFi")
	public ResponseEntity<String> printFi(@RequestParam String custId){
		return fiverifyService.printFi(custId);
	}

}
