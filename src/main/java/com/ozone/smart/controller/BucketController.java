package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ozone.smart.dto.PtpDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.BucketService;

@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {
	
	@Autowired
	private BucketService bucketService;
	
	@PostMapping("/saveBucket")
	public Response<String> saveBucket(@RequestBody PtpDto ptpDto){
		return bucketService.saveBucket(ptpDto);
	}
	
	@GetMapping("/buckDetails")
	public Response<String> viewBucketDetails(@RequestParam String agreeno){
		return bucketService.viewBucket(agreeno);
	}
	
	@GetMapping("/getBucket")
	public String[] viewBucket(@RequestParam String flag){
		return bucketService.viewBuckets(flag);
	}

}
