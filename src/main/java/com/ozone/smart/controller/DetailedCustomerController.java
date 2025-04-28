package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DetailedCustomerService;

@RestController
@RequestMapping("/api/v1")
public class DetailedCustomerController {
	
	@Autowired
	private DetailedCustomerService detailedCustService;
	
	@PutMapping("/detailedcustomer")
	public Response<String> updateCustomer(@RequestBody CustomerDetailsDto customerDto){
		return detailedCustService.updateCust(customerDto);
	}

}
