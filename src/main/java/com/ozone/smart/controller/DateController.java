package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DateService;

@RestController
@RequestMapping("/api/v1")
public class DateController {
	@Autowired
	private DateService dateService;
	
	@GetMapping("/payDate")
	public String ValidateUser(){
		return dateService.getDate();
	}

}
