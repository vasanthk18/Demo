package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.Response;
import com.ozone.smart.service.DataExtractionService;

@RestController
@RequestMapping("/api/v1/dataExtraction")
public class DataExtractionController {
	
	@Autowired
	private DataExtractionService dataExtractionService;
	
	@GetMapping("/dataDump")
	public ResponseEntity<String> generateDataDump(@RequestParam String datetime){
		return dataExtractionService.generateDataDump(datetime);
	}
	
	@GetMapping("/collectionReport")
	public ResponseEntity<String> generateCollectionReport(@RequestParam String datetime){
		return dataExtractionService.generateCollectionReport(datetime);
	}
	
	@GetMapping("/due")
	public ResponseEntity<String> generateDue(@RequestParam String datetime, @RequestParam String fromDate, @RequestParam String toDate){
		return dataExtractionService.generateDue(datetime,fromDate,toDate);
	}
	
	@GetMapping("/rejCust")
	public ResponseEntity<String> generateRejCust(@RequestParam String datetime){
		return dataExtractionService.generateRejCust(datetime);
	}
	
	@GetMapping("/bikereldisb")
	public ResponseEntity<String> generateBikereldisb(@RequestParam String datetime){
		return dataExtractionService.generateBikereldisb(datetime);
	}
	
	@GetMapping("/tat")
	public ResponseEntity<String> generateTat(@RequestParam String datetime){
		return dataExtractionService.generateTat(datetime);
	}
	
	@GetMapping("/tele")
	public ResponseEntity<String> generateTele(@RequestParam String datetime){
		return dataExtractionService.generateTele(datetime);
	}
	
	@GetMapping("/report")
	public ResponseEntity<String> generateReporpt(@RequestParam String datetime){
		return dataExtractionService.generateReporpt(datetime);
	}
	
	@GetMapping("/dp")
	public ResponseEntity<String> generateDp(@RequestParam String datetime,@RequestParam String fromDate, @RequestParam String toDate){
		return dataExtractionService.generateDp(datetime,fromDate,toDate);
	}
	@GetMapping("/weeklyInst")
	public ResponseEntity<String> generateWeeklyinst(@RequestParam String datetime,@RequestParam String fromDate, @RequestParam String toDate){
		return dataExtractionService.generateWeeklyinst(datetime,fromDate,toDate);
	}
	
}
