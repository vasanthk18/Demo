package com.ozone.smart.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.UploadDocService;

@RestController
@RequestMapping("/api/v1/uploadDoc")
public class UploadDocController {
	
	@Autowired
	private UploadDocService uploadDocService;
	
	@PostMapping("/saveDoc")
	public Response<String> saveDoc(@RequestParam String username, @RequestParam String custId, @RequestParam("file") MultipartFile multipartFile){
		return uploadDocService.addDoc(username,custId,multipartFile);
	}
	
	@GetMapping("/getDoc")
	public ResponseEntity<InputStreamResource> getDoc(@RequestParam String custId, @RequestParam String flag) throws IOException{
		return uploadDocService.fetchDoc(custId,flag);
	}
	

}
