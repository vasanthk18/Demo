package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.LogoutService;

@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {
	
	@Autowired
	private LogoutService outService;
	
	@PutMapping
	public Response<String> logout(String userId, String inTime ){
		return outService.logout(userId,inTime);
	}

}
