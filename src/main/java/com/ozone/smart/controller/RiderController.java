package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CustomerDetailsDto;
import com.ozone.smart.dto.FiVerificationDto;
import com.ozone.smart.dto.GuarantorDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.RiderDetailsDto;
import com.ozone.smart.dto.TeleVerificationDto;
import com.ozone.smart.service.RiderService;

@RestController
@RequestMapping("/api/v1")
public class RiderController {

	@Autowired
	private RiderService riderService;

	@PutMapping("/saveriderdetails")
		public Response<String> saveRider(@RequestBody RiderDetailsDto riderDetailsDto){
			return riderService.saveRider(riderDetailsDto);	
	}
	
	@PutMapping("/updateriderdetails")
	public Response<String> updateRider(@RequestBody RiderDetailsDto riderDetailsDto){
		return riderService.updateRider(riderDetailsDto);
		
	}
	
	@DeleteMapping("/deleteriderdetails")
	public Response<String> deleteRider(@RequestParam String id, @RequestParam String name){
		return riderService.removeRider(id, name);
		
	}
	
	@GetMapping("/viewRider")
	public Response<List<RiderDetailsDto>> viewRider(){
		return riderService.viewRider();
	}
	
	@GetMapping("/viewRiderDetailsbyProposalId")
	public Response<RiderDetailsDto> viewRiderDetails(@RequestParam String id, @RequestParam String flag){
		return riderService.viewRiderDetails(id, flag);
	}
	
	@GetMapping("/viewRiderDetailstv")
	public Response<TeleVerificationDto> viewRiderStatustv(@RequestParam String proposalId){
		return riderService.viewRiderStatustv(proposalId);
	}
	
	@GetMapping("/viewRiderStatusfi")
	public Response<FiVerificationDto> viewRiderStatusfi(@RequestParam String proposalId){
		return riderService.viewRiderStatusfi(proposalId);
	}
	
}
