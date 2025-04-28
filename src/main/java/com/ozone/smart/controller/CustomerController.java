package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/saveCustomer")
	public Response<String> saveCustomer(@RequestBody CustomerDetailsDto customerDetailsDto) {
		return customerService.saveCustomer(customerDetailsDto) ;
	}
	
//	Flag = empty or All
	@GetMapping("/viewByCustId")
	public Response<CustomerDetailsDto> viewCustomerById(@RequestParam String id, @RequestParam String flag){
		return customerService.viewCustomer(id, flag);
	}
	
	@GetMapping("/viewCustomers")
	public Response<List<CustomerDetailsDto>> viewCustomer(){
		return customerService.viewCustomers();
	}
	
	@GetMapping("/viewCustomerDetails")
	public Response<String> viewCustomerStatus(@RequestParam String custId){
		return customerService.viewCustomerStatus(custId);
	}
	
	@GetMapping("/viewCustomerArgs")
	public Response<List<CustomerDetailsDto>> viewCustomerArgs(@RequestParam String flag){
		return customerService.viewCustomerArgs(flag);
	}
	
	@GetMapping("/viewCustVeh")
	public Response<String> viewCustomerVeh(@RequestParam String propNo){
		return customerService.viewCustomerVeh(propNo);
	}
	
	
	
}
