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

import com.ozone.smart.dto.CountyDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.SubCountyDto;
import com.ozone.smart.service.SubCountieService;

@RestController
@RequestMapping("api/v1")
public class SubCountieController {
	
	@Autowired
	private SubCountieService subCountyService;
	
	
	
	@PostMapping("/saveSubCounty")
	public Response<String> addSubCounty(@RequestBody SubCountyDto subCountyDto){
		return subCountyService.saveSubCounty(subCountyDto);
	}
	
	@PutMapping("/editSubCounty")
	public Response<String> editSubCounty(@RequestBody SubCountyDto subCountyDto){
		return subCountyService.updateSubCounty(subCountyDto);
	}
	
	@DeleteMapping("/deleteSubCounty")
	public Response<String> deleteSubCounty(@RequestParam String id){
		return subCountyService.deleteSubCounty(id);
	}
	
	@GetMapping("/subCountyById")
	public Response<String> viewSubCountyByID(@RequestParam String id ){
		return subCountyService.viewSubCounty(id);
	}
	
	@GetMapping("/loadSubCounty")
	public Response<List<SubCountyDto>> viewSubCounty(@RequestParam String countyId){
		return subCountyService.viewSubCountys(countyId);
	}
	
	@GetMapping("/checkSubcnty")
	public Response<String> checkSubCounty(@RequestBody SubCountyDto subCountyDto){
		return subCountyService.checkSubCounty(subCountyDto);
	}
	
	@GetMapping("/subCounties")
	public List<SubCountyDto> viewCounty(){
		return subCountyService.viewAllCounty();
	}
	
}
