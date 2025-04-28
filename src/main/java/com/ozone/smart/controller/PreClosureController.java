package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.LoanEntryDto;
import com.ozone.smart.dto.PreclosureDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Loan;
import com.ozone.smart.service.PreClosureService;

@RestController
@RequestMapping("/api/v1/preClosure")
public class PreClosureController {
	
	
	@Autowired
	private PreClosureService preClosureService;
	
	@GetMapping("/details")
	public Response<String> viewPreClosureDetails(@RequestParam String agreeNo, @RequestParam String flag){
		return preClosureService.viewDetails(agreeNo,flag);
	}
	
	@PostMapping("/savePreClosure")
	public Response<String> addPreClosure(@RequestBody PreclosureDto preclosureDto){
		return preClosureService.savePreClosure(preclosureDto);
	}
	
	@GetMapping("/getValue")
	public Response<String> getPreClosureValue(@RequestParam String principal, @RequestParam String value, @RequestParam String percent){
		return preClosureService.getValue(principal,value,percent);
	}
	
	@GetMapping("/getLetter")
	public ResponseEntity<InputStreamResource> getPreClosureLetter(@RequestParam String agreement, @RequestParam String value, @RequestParam String percent){
		return preClosureService.getClosureLetter(agreement,value,percent);
	}
	
	@GetMapping("/getObjletter")
	public ResponseEntity<InputStreamResource> getObjLetter(@RequestParam String agreement){
		return preClosureService.getObjLetter(agreement);
	}
	
	@GetMapping("/getAgree")
	public Response<List<String>> getAgreeNo(){
		return preClosureService.getAgreeNo();
	}
	

}
