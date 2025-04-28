package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.DocSubmitDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DocSubmitService;

@RestController
@RequestMapping("/api/v1")
public class DocSubmitController {
	
	@Autowired
	private DocSubmitService docSubmitService;
	
	@PostMapping("/docsubmission")
	public Response<String> addDocSubmission(@RequestBody DocSubmitDto docSubmitDto){
		return docSubmitService.saveDocSubmission(docSubmitDto);
	}
	

}
