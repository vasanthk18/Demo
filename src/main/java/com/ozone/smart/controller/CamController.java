package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CoCamDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CamService;

@RestController
@RequestMapping("/api/v1/cam")
public class CamController {
	
	@Autowired
	private CamService camService;
	
	@PutMapping
	public Response<String> updateCoCam(@RequestBody CoCamDto coCamDto){
		return camService.updateCam(coCamDto);
	}

}
