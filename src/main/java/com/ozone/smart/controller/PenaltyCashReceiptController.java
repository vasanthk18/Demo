package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.CashReceiptDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.WaiverDto;
import com.ozone.smart.service.PenaltyCashReceiptService;

@RestController
@RequestMapping("/api/v1/penaltyCash")
public class PenaltyCashReceiptController {
	
	@Autowired
	private PenaltyCashReceiptService pCashReceiptService;
	
	@PostMapping("/receipt")
	public ResponseEntity<InputStreamResource> PenaltycashReceipt(@RequestBody CashReceiptDto cashReceiptDto){
		return pCashReceiptService.saveCashReceipt(cashReceiptDto);
		
	}
	
	@GetMapping("/outStandingPenalty")
	public Response<String> getOutStandingPenalty(@RequestParam String propNo){
		return pCashReceiptService.fetchOurStandingPenalty(propNo);
	}
	
//	 @PutMapping("/updatePenalty")	
//	    public Response<String> updatePenaltyToZero(@RequestParam String loanid) {
//	        return pCashReceiptService.updatePenaltyToZero(loanid);
//	    }
	
	@PostMapping("/waiver")
	public ResponseEntity<String> addWaiver(@RequestBody WaiverDto waiverDto){
		return pCashReceiptService.saveWaiver(waiverDto);
		
	}

}
