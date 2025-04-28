package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.UploadAgreeService;

@RestController
@RequestMapping("/api/v1/uploadAgreement")
public class UploadAgreeController {
	
	@Autowired
	private UploadAgreeService uploadAgreeService;
	
	
	@PostMapping("/saveAgree")
	public Response<String> uploadAgree(@RequestParam String username, @RequestParam String agreeId, @RequestParam("file") MultipartFile multipartFile){
		
		return uploadAgreeService.addAgree(username,agreeId,multipartFile);
		
	}
	
	@GetMapping("/viewAgreement")
	public Response<String> viewAgreement(@RequestParam String custId , @RequestParam String flag){
		return uploadAgreeService.viewAgreement(custId,flag);
	}
	

}
