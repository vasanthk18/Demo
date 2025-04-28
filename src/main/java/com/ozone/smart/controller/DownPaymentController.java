package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DownPaymentService;

@RestController
@RequestMapping("/api/v1")
public class DownPaymentController {
	
	@Autowired
	private DownPaymentService dpService;
	
	@GetMapping("/loadDp")
	public Response<String> loadDp(@RequestParam String propNo , @RequestParam String flag){
		return dpService.viewDp(propNo,flag);
	}

}
