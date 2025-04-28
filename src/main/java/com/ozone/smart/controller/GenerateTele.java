package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.service.TeleService;

@RestController
@RequestMapping("/api/v1/report")
public class GenerateTele {
	
	
	@Autowired
	private TeleService teleService;
	
	@GetMapping("/tele")
	public ResponseEntity<String> generateTele(@RequestParam String datetime){
		return teleService.generateTele(datetime);
	}

}
