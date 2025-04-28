package com.ozone.smart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.VehicleAllocationService;

@RestController
@RequestMapping("/api/v1/vehicleAllocation")
public class VehicleAllocationController {
	
	@Autowired
	private VehicleAllocationService vaService;
	
	@PostMapping("/addAllocation")
	public Response<String> addAllocation(@RequestBody Map<String, String> payload){
		try {
			String proposal = payload.get("proposal");
	        String regno = payload.get("regno");
	        String username = payload.get("username");
	        String customer = payload.get("selectedCustomer");
	        System.out.println("Inside add allocation");
			return vaService.updateCustVehicle(proposal, regno, username, customer);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@PostMapping("/getAgree")
	public  ResponseEntity<Resource> generateAgree(@RequestBody Map<String, String> payload){
		String proposal = payload.get("proposal");
        String regno = payload.get("regno");
        String chassis = payload.get("chassis");
        String engine = payload.get("engine");
        System.out.println("Inside getAgree");
		return vaService.generateAgree(proposal, regno, chassis, engine);
	}
	
	@GetMapping("/getAllocation")
	public Response<List<CustomerDetailsDto>> getVehAllocation(){
		return vaService.getVehAllocation();
	}
	
//	@GetMapping("/schedule")
//	public Response<String> scheduleGenServlet(@RequestParam String customer, @RequestParam String proposal, @RequestParam String username){
//		return vaService.scheduleGen(customer,proposal,username);
//		
//	}
	
	
}
