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
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.StageDto;
import com.ozone.smart.service.StageService;

@RestController
@RequestMapping("/api/v1")
public class StageController {
	
	
	@Autowired
	private StageService stageService;
	
	@GetMapping("/stageById")
	public Response<StageDto> viewStage(@RequestParam int id){
		return stageService.viewStage(id);
	}
	
	@GetMapping("/viewStages")
	public List<StageDto> viewStageName(){
		return stageService.viewStageNames();
	}
	
	@GetMapping("/stageByName")
	public Response<String> getStageInfo(@RequestParam String name){
		return stageService.findStageByName(name);
	}
	
	@PostMapping("/saveStage")
	public Response<String> saveStage(@RequestBody StageDto stageDto) {
		return stageService.saveStage(stageDto);
	}
	
	
	@PutMapping("/editStage")
	public Response<String> editStage(@RequestBody StageDto stageDto) {	
		return stageService.editStage(stageDto);
		
	}
	
	@DeleteMapping("/deleteStage")
	public Response<String> deleteStage(@RequestParam int id) {	
		 return stageService.deleteSatge(id);
		
	}
	
	
	

}
