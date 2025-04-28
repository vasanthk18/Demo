package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CamApprovalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CamApprovalService;

@RestController
@RequestMapping("/api/v1/CamApproval")
public class CamApprovalController {
	
	@Autowired
	private CamApprovalService camApprovalService;
	
	@PutMapping("/update")
	public Response<String> updateCoCamApp(@RequestBody CamApprovalDto camApproval){	
		return camApprovalService.updateCoCamApp(camApproval);
		
	}

}
