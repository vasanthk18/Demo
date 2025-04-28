package com.ozone.smart.controller;

import java.util.Map;

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
import com.ozone.smart.service.CustProfileService;

@RestController
@RequestMapping("/api/v1/custprofile")
public class CustProfileController {
	
	@Autowired
	private CustProfileService custProfileService;

	
	@PostMapping("/loadCustProfile")
	public Response<String> loadCustProfile(@RequestBody Map<String, String> payload){	
		String vehicle = payload.get("vehicle");
        String flag = payload.get("flag");
		return custProfileService.viewStatement(vehicle,flag);
	}
	
	
	@GetMapping("/printProfile")
	public ResponseEntity<InputStreamResource> printCustProfile(@RequestParam String customer , @RequestParam String vehicle){
		
		return custProfileService.printProfile(customer,vehicle);
	}
}
