package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ReleaseLetterService;

@RestController
@RequestMapping("/api/v1")
public class ReleaseLetterController {
	
	@Autowired
	private ReleaseLetterService releaseLetterService;
	
	@GetMapping("/releaseLetter")
	public ResponseEntity<InputStreamResource> generateReleaseLetter(@RequestParam String proposal,@RequestParam String custId  ,@RequestParam String username){
		return releaseLetterService.generateRL(proposal, custId,username);
	}
	
	
	@GetMapping("/getReleaseLetter")
	public Response<List<CustomerDetailsDto>> ReleaseLetter(){
		return releaseLetterService.viewCustId();
	}

}
