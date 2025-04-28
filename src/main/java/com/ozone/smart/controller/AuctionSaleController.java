package com.ozone.smart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.AuctionDto;
import com.ozone.smart.dto.ImpStockDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.AuctionSaleService;

@RestController
@RequestMapping("/api/v1/auction")
public class AuctionSaleController {
	
	@Autowired
	private AuctionSaleService auctionSaleService;
	
	@PostMapping("/saveAuction")
	public Response<String> saveAuction(@RequestBody AuctionDto auctionDto){
		return auctionSaleService.saveAuction(auctionDto);
	}
	
	@PostMapping("/auctionCal")
	public Response<String> getAuctionCal(@RequestBody Map<String, String> payload){
		String salecost = payload.get("salecost");
        String salevalue = payload.get("salevalue");
        String pout = payload.get("pout");
		return auctionSaleService.getCal(salecost,salevalue,pout);	
	}
	
	@PostMapping("/printAgree")
	public ResponseEntity<InputStreamResource> printAgree(@RequestBody Map<String, String> payload){
		String agreement = payload.get("agreement");
		return auctionSaleService.printAgree(agreement);
	}
	
	@GetMapping("/getAgree")
	public Response<List<ImpStockDto>> getAgreeNo(){
		return auctionSaleService.getAgreeNo();
	}

}
