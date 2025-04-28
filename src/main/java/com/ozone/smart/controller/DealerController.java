package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ozone.smart.dto.DealerDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DealerService;

@RestController
@RequestMapping("/api/v1")
public class DealerController {
	
	@Autowired
	private DealerService dealerService;
	
	
	@PostMapping("/saveDealer")
	public Response<String> getDealer(@RequestBody DealerDto dealerDto){
		return dealerService.addDealer(dealerDto);
	}
	
	@PutMapping("/editDealer")
	public Response<String> putDealer(@RequestBody DealerDto dealerDto){
		return dealerService.editDealer(dealerDto);
	}
	
	@DeleteMapping("/deleteDealer")
	public Response<String> deleteDealer(@RequestParam String id){
		return dealerService.removeDealer(id);
	}
	
	@GetMapping("/viewDealer")
	public Response<DealerDto> viewDealerById(@RequestParam String name){
		return dealerService.viewDealer(name);
	}
	
	@GetMapping("/viewDealers")
	public Response<List<DealerDto>> viewDealer(){
		return dealerService.viewDealers();
		
	}


}
