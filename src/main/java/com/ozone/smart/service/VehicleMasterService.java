package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.VehicleMasterDto;
import com.ozone.smart.entity.VehicleMaster;
import com.ozone.smart.repository.VehicleMasterRepo;
import com.ozone.smart.util.formatDigits;

@Service
public class VehicleMasterService {
	
	@Autowired
	private VehicleMasterRepo vmRepo;

	public Response<String> addVehicleMaster(VehicleMasterDto vmDto) {
		String brand = vmDto.getBrand();
		String model = vmDto.getModel();
		String cc = vmDto.getCc();
		String vat = vmDto.getVat();
		Response<String> response = new Response<>();
		VehicleMaster vm = new VehicleMaster();
		vm.setBrand(brand);
		vm.setCc(cc);
		vm.setColor(vmDto.getColor());
		vm.setDiscount(vmDto.getDiscount());
		vm.setModel(model);
		vm.setPrice(vmDto.getPrice());
		vm.setVat(vat);
		try {
			vmRepo.save(vm);
			response.setData("Vehicle " + brand + "  " + model + " "+ cc + " has been created successfully.");
		}catch(Exception e){
			e.printStackTrace();
			response.setErrorMsg("Error creating vehicle brand : " + brand);
		}
		
		return response;
	}

	public Response<String> editVehicleMaster(VehicleMasterDto vmDto) {
		String brand = vmDto.getBrand();
		String model = vmDto.getModel();
		String cc = vmDto.getCc();
		int id = vmDto.getId();
		Response<String> response = new Response<>();
		Optional<VehicleMaster> vmm = vmRepo.findById(id);
		
		if(vmm.isPresent()) {
			VehicleMaster vm = vmm.get();
			vm.setBrand(brand);
			vm.setCc(cc);
			vm.setModel(model);
			vm.setColor(vmDto.getColor());
			vm.setDiscount(vmDto.getDiscount());
			vm.setPrice(vmDto.getPrice());
			vmRepo.save(vm);
			response.setData("Vehicle: " + brand + " " + model + " " + cc + " has been updated successfully");
		}else {
			response.setErrorMsg("Failed to Update Vehicle");
		}
		
		return response;
	}

	public Response<String> removeVehicleMaster(int vmId) {
		String brand = "";
		String model = "";
		String cc ="";
		Optional<VehicleMaster> vmm =vmRepo.findById(vmId);
		
		if(vmm.isPresent()) {
			VehicleMaster vm = vmm.get();
			brand=vm.getBrand();
			model =vm.getCc();
			cc=vm.getModel();
		}
		vmRepo.deleteById(vmId);
		Response<String> response = new Response<>();
		response.setData("Vehicle: " + brand + " " + model + " " + cc + " has been deleted");
		return response;
	}
// unique ID
	public Response<VehicleMasterDto> viewVehicleMaster(int id) {
		String brand = "";
		String model = "";
		String cc ="";
		String VehiclePrice ="";
		
		Response<VehicleMasterDto> response = new Response<>();
		VehicleMasterDto vmDto = new VehicleMasterDto();
		
		
		try {
			Optional<VehicleMaster> vmm = vmRepo.findById(id);
			if(vmm.isPresent()) {
				VehicleMaster vm = vmm.get();
				brand = vm.getBrand();
				model = vm.getModel();
				VehiclePrice = vm.getPrice();
				
				if (VehiclePrice.contains(",")) 
				{
					VehiclePrice = VehiclePrice.replace(",", "");
				}
				formatDigits fd = new formatDigits();
				String Vehpricefmt = fd.digit(VehiclePrice);
				
				cc = vm.getCc();
				vmDto.setBrand(brand);
				vmDto.setCc(cc);
				vmDto.setColor(vm.getColor());
				vmDto.setDiscount(vm.getDiscount());
				vmDto.setModel(model);
				vmDto.setPrice(Vehpricefmt);
				
				 List<Integer> myList = Arrays.asList(10,15,8,49,25,98,98,32,15);
				 myList.stream().map(n ->{
					 if(n == n+1) {
						 
					 }
					 return null;
				 });
			}
		}catch(Exception e) {
			response.setErrorMsg("Error retreiving details for vehicle: " + brand + " " +  model + " " +  cc);
		}
		
		response.setData(vmDto);
		return response;
	}

	public Response<List<VehicleMasterDto>> viewVM() {
		Response<List<VehicleMasterDto>> response = new Response<>();
		List<VehicleMaster> vmm = vmRepo.findAll();
		List<VehicleMasterDto> vmDtoList = new ArrayList<>();
		
		for(VehicleMaster vm : vmm) {
			VehicleMasterDto vmDto = new VehicleMasterDto();
			vmDto.setId(vm.getId());
			vmDto.setBrand(vm.getBrand());
			vmDto.setCc(vm.getCc());
			vmDto.setColor(vm.getColor());
			vmDto.setDiscount(vm.getDiscount());
			vmDto.setModel(vm.getModel());
			vmDto.setPrice(vm.getPrice());
			vmDtoList.add(vmDto);
			
		}
		response.setData(vmDtoList);
		return response;
	}
}
