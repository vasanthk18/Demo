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
import com.ozone.smart.service.CountyService;

@RestController
@RequestMapping("api/v1")
public class CountyController {
	
	@Autowired
	private CountyService countyService;
	
	@GetMapping("/viewcountyById")
	public Response<CountyDto> viewCountyByCountyId(@RequestParam String id){
		return countyService.viewCounty(id);
	}
	
	@GetMapping("/viewcountyByDist")
	public Response<List<CountyDto>> viewCountyByDistrictId(@RequestParam String districtid){
		return countyService.fetchCounty(districtid);
	}
	
	@GetMapping("/counties")
	public List<CountyDto> viewCounty(){
		return countyService.viewAllCounty();
	}
	
	@GetMapping("/countyByName")
	public Response<String> getCountyName(@RequestBody CountyDto countyDto ){
		
		return countyService.findStageByName(countyDto);
	}
	
	@PostMapping("/saveCounty")
	public Response<String> saveCounty(@RequestBody CountyDto countyDto){
		return countyService.addCounty(countyDto);
	}
	
	@PutMapping("/editCounty")
	public Response<String> editCounty(@RequestBody CountyDto countyDto){
		return countyService.updateCounty(countyDto);
	}
	
	@DeleteMapping("/deleteCounty")
	public Response<String> deleteCounty(@RequestParam String id){
		return countyService.deleteCounty(id);
	}
}
