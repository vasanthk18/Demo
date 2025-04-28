package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.ReposessionDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ReposessionService;

@RestController
@RequestMapping("/api/v1/reposession")
public class ReposessionController {
	
	@Autowired
	private ReposessionService repoService;
	
	
//	cust otherid
	@GetMapping("/getAgreement")
	public Response<List<ReposessionDto>> getRepoAggrementNo(String id){
		return repoService.FetchAggrementNo(id);
	}
	
//	Agreement
	@GetMapping("/viewRepo")
	public Response<ReposessionDto> viewRepoVehicle(String agreement){
		return repoService.viewByAggrementNo(agreement);
	}
	
//	Proposal
	@GetMapping("/viewRepoVehicle")
	public Response<String> viewRepoVehicleByProposal(String proposal){
		return repoService.viewByProposalNo(proposal);
	}
	
	@GetMapping("/viewRepoDetail")
	public Response<String> viewRepoDetails(String agreement){
		return repoService.viewRepo(agreement);
	}
	
	
	@PostMapping("/saveRepo")
	public Response<String> addRepo(@RequestBody ReposessionDto reposessionDto){
		return repoService.saveRepo(reposessionDto);
	}
	
	@GetMapping("/viewAgree")
	public Response<List<ImpStockDto>> viewAgree(){
		return repoService.viewAgree();
	}

}
