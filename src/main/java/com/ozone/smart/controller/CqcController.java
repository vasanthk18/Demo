package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CqcDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CqcService;

@RestController
@RequestMapping("/api/v1/cqc")
public class CqcController {
	
	@Autowired
	private CqcService cqcService;
	
	@PutMapping()
	public Response<String> updateCqc(@RequestBody CqcDto cqcDto){
		return cqcService.updateCqc(cqcDto);
	}

}
