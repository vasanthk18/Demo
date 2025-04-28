package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.LoanEntryDto;
import com.ozone.smart.dto.ProposalDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.LoanEntryService;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanEntryController {
	
	@Autowired
	private LoanEntryService loanEntryService;
	
	@PostMapping("/saveLoan")
	public Response<String> saveLoan(@RequestBody LoanEntryDto loanEntryDto){
		return loanEntryService.addLoan(loanEntryDto);
	}
	
	@PostMapping("/calInsurance")
	public Response<String> calInsurance(@RequestBody LoanEntryDto loanEntryDto){
		return loanEntryService.calInsurance(loanEntryDto);
	}
	
	@GetMapping("/loadLN")
	public Response<String> viewLoan(@RequestParam String propno, @RequestParam String flag ){
		return loanEntryService.viewLoanByPropNo(propno, flag);
	}
	
//	for getting the ewi/emi and installment of latest proposal from Loan 
	@GetMapping("/viewLatestProposalLoan")
	 public Response<LoanEntryDto> getProposalsLoanDetails(@RequestParam String custId) {
			return loanEntryService.getProposalsLoanDetails(custId);
	}

}
