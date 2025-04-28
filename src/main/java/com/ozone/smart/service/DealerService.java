package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.DealerDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Dealer;
import com.ozone.smart.repository.DealerRepo;

@Service
public class DealerService {
	
	@Autowired
	private DealerRepo dealerRepo;

	public Response<String> addDealer(DealerDto dealerDto) {
		String dealerName = dealerDto.getDealer();
		
		Response<String> response = new Response<>();
		try {
			Dealer dler = new Dealer();
			dler.setDealer(dealerName);
			dler.setEmail(dealerDto.getEmail());
			dler.setPhysicaladdress(dealerDto.getPhysicaladdress());
			dler.setPostaladdress(dealerDto.getPostaladdress());
			dler.setTelephone1(dealerDto.getTelephone1());
			dler.setTelephone2(dealerDto.getTelephone2());
			try {
				dealerRepo.save(dler);
				response.setData("Dealer : " + dealerName + " created successfully.");
			}catch(Exception e){
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());			         
				 String Msg = th.getCause().toString();
				
				if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
					response.setData("Dealer : " + dealerName + " saved successfully");
				} else if (Msg.contains("The database returned no natively generated identity value")) {
					response.setData("Dealer : " + dealerName + " saved successfully");
				} else if (Msg.contains("ConstraintViolationException")) {
					response.setData("Dealer : " + dealerName + " already exists");
					response.setData("General error");;
				}
				
			}
			
		}catch(Exception e) {
			response.setErrorMsg("Error creating dealer : " + dealerName);
			response.setErrorMsg("Error creating dealer : " + dealerName);
		}
			
		return response;
	}

	public Response<String> editDealer(DealerDto dealerDto) {
		String dealerName = dealerDto.getDealer();
		Response<String> response = new Response<>();
		Optional<Dealer> opDealer = dealerRepo.findById(dealerName);
		
		if(opDealer.isPresent()) {
			Dealer dealer  = opDealer.get();
			dealer.setDealer(dealerName);
			dealer.setEmail(dealerDto.getEmail());
			dealer.setPhysicaladdress(dealerDto.getPhysicaladdress());
			dealer.setPostaladdress(dealerDto.getPostaladdress());
			dealer.setTelephone1(dealerDto.getTelephone1());
			dealer.setTelephone2(dealerDto.getTelephone2());
			dealerRepo.save(dealer);
		}
		response.setData("Dealer: " + dealerName + " has been updated successfully");
		return response;
	}

	public Response<String> removeDealer(String dealerName) {
		
		dealerRepo.deleteById(dealerName);
		Response<String> response = new Response<>();
		response.setData("Dealer: " + dealerName + " has been deleted");
		return response;
	}

	public Response<List<DealerDto>> viewDealers() {
		Response<List<DealerDto>> response = new Response<>();
		List<Dealer> dealer = dealerRepo.findAll();
		List<DealerDto> dealerDtoList = new ArrayList<>();
		
		for(Dealer dlr : dealer) {
			DealerDto dealerDto = new DealerDto();
			dealerDto.setDealer(dlr.getDealer());;
			dealerDtoList.add(dealerDto);
			response.setData(dealerDtoList);;
		}	
		return response;
	}

	public Response<DealerDto> viewDealer(String dealerName) {
		Response<DealerDto> response = new Response<>();
		DealerDto dealerDto = new DealerDto();
		try {
			Optional<Dealer> opDealer = dealerRepo.findById(dealerName);
			if(opDealer.isPresent()) {
				Dealer dealer = opDealer.get();
				dealerDto.setDealer(dealer.getDealer());
				dealerDto.setEmail(dealer.getEmail());
				dealerDto.setPhysicaladdress(dealer.getPhysicaladdress());
				dealerDto.setPostaladdress(dealer.getPostaladdress());
				dealerDto.setTelephone1(dealer.getTelephone1());
				dealerDto.setTelephone2(dealer.getTelephone2());
				
			}
		}catch(Exception e) {
			response.setErrorMsg("Error retreiving details for dealer: " + dealerName);
		}
		
		response.setData(dealerDto);
		return response;
	}

}
