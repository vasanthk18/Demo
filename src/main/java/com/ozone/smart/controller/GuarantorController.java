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

import com.ozone.smart.dto.GuarantorDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.GuarantorService;

@RestController
@RequestMapping("/api/v1/guarantor")
public class GuarantorController {
	
	@Autowired
	private GuarantorService guarantorService;
	
	@PostMapping("/saveGuarantor")
	public Response<String> saveGuarantor(@RequestBody GuarantorDto guarantorDto){
		return guarantorService.addGuarantor(guarantorDto);
		
	}
	
	@PutMapping("/updateGuarantor")
	public Response<String> updateGuarantor(@RequestBody GuarantorDto guarantorDto){
		return guarantorService.editGuarantor(guarantorDto);
		
	}
	
	@DeleteMapping("/deleteGuarantor")
	public Response<String> deleteGuarantor(@RequestParam int id){
		return guarantorService.removeGuarantor(id);
		
	}
	
//	loadGuarantor
	@GetMapping("/getByCustId")
	public Response<String> viewGuarantor(@RequestParam String id, @RequestParam String flag ){
		return guarantorService.viewByCustId(id,flag);
	}
	
//	loadGRDetails
	@GetMapping("/getByGuaranId")
	public Response<String> viewGuarantor(@RequestParam String id){	
		return guarantorService.viewByGuaranId(id);
	}
	
	@GetMapping("/viewGuarantorDetails")
	public Response<String> viewGuarantorStatus(@RequestParam String custId, @RequestParam String flag){
		return guarantorService.viewCustomerStatus(custId,flag);
	}
	
	@GetMapping("/getAll")
	public Response<List<GuarantorDto>> viewGuarantors(){
		return guarantorService.viewGuarantors();
	}
	
	

}
