package com.ozone.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ProposalService;

@RestController
@RequestMapping("/api/v1/proposal")
public class ProposalController {
	
	@Autowired
	private ProposalService proposalService;
	
	@PostMapping("/saveProposal")
	public Response<String> addProposal(@RequestBody ProposalDto proposalDto){
		return proposalService.saveProposal(proposalDto);
	}
	
	@GetMapping("/viewById")
	public Response<List<ProposalDto>> viewProposalById(){
		return proposalService.viewProposal();
	}
	
	@GetMapping("/viewProposal")
	public Response<String> viewProposal(@RequestParam String custId, @RequestParam String flag){
		return proposalService.viewProposals(custId,flag);
	}
	
	@GetMapping("/viewPropArgs")
	public Response viewProposal(){
		return proposalService.viewPropArgs();
	}
	
	@GetMapping("/viewCustomerArgs")
	public Response<List<ProposalDto>> viewCustomerArgs(@RequestParam String flag){
		return proposalService.viewCustomerArgs(flag);
	}
	
//	for getting the bikename of latest proposal
	@GetMapping("/viewLatestProposalBike")
	 public Response<ProposalDto> getProposalsBikeName(@RequestParam String custId) {
			return proposalService.getProposalsBikeName(custId);
	}
	
}
