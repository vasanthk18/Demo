package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.LoanStatusDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ImpoundService;

@RestController
@RequestMapping("/api/v1/impound")
public class ImpoundController {
	
	@Autowired
	private ImpoundService impoundService;
	
	@GetMapping("/getImpound")
	public Response<String> viewImpoundDetails(@RequestParam String agreeNo){
		return impoundService.viewImpound(agreeNo);
	}
	
	@PostMapping("/save")
	public Response<String> addImpound(@RequestBody ImpStockDto impStockDto){
		return impoundService.saveImpound(impStockDto);
	}
	
	@PostMapping("/saveVehiclePhoto")
	public Response<String> addVehiclePhoto(@RequestParam String username, @RequestParam String agreeNo ,@RequestParam("file") MultipartFile multipartFile){
		return impoundService.savePhoto(agreeNo, agreeNo, multipartFile);
	}
	
	@GetMapping("/getAgree")
	public Response<List<LoanStatusDto>> viewImpound(){
		return impoundService.viewImpounds();
	}
	

}
