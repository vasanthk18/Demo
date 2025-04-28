package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.service.TeleVerifyService;

@RestController
@RequestMapping("/api/v1/tv")
public class TeleverifyController {
	
	@Autowired
	private TeleVerifyService teleVerifyService;
	
	@PostMapping("/saveCust")
	public Response<String> saveCustTv(@RequestBody TeleVerificationDto teleVerificationDto){
		return teleVerifyService.addCustTv(teleVerificationDto);
	}
	
	@PostMapping("/saveGuaran")
	public Response<String> saveGuaranTv1(@RequestBody TeleVerificationDto teleVerificationDto){
		return teleVerifyService.addGuaranTv(teleVerificationDto);
	}
	
	
	@PostMapping("/saveRider")
	public Response<String> saveRiderTv(@RequestBody TeleVerificationDto teleVerificationDto){
		return teleVerifyService.addRiderTv(teleVerificationDto);
	}
//	@PostMapping("/saveGuaranTwo")
//	public Response<String> saveGuaranTv2(@RequestBody TeleVerificationDto teleVerificationDto){
//		return teleVerifyService.addGuaranTvTwo(teleVerificationDto);
//	}
	
//	customer()check it
	@GetMapping("/printTv")
	public ResponseEntity<String> printTv(@RequestParam String custId){
		return teleVerifyService.printTv(custId);
	}
	
	
}
