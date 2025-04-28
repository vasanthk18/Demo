package com.ozone.smart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.LoginService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
	
	@Autowired
	private LoginService userAuditService;
	

	@PostMapping("/login")
	public Response<String> ValidateUser(@RequestBody Map<String, String> payload){
		String username = payload.get("username");
        String password = payload.get("password");
		return userAuditService.checkUser(username,password);
	}

}
