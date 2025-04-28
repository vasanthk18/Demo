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
import com.ozone.smart.dto.VehicleDto;
import com.ozone.smart.service.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	
	@PostMapping("/saveVehicle")
	public Response<String> addVehicle(@RequestBody VehicleDto vehicleDto){
		return vehicleService.addVehicle(vehicleDto);
	}
	
	@PutMapping("/updateVehicle")
	public Response<String> updateVehicle(@RequestBody VehicleDto vehicleDto){
		return vehicleService.editVehicle(vehicleDto);
	}
	
	@DeleteMapping("/deleteById")
	public Response<String> deleteVehicle(@RequestParam String regno){
		return vehicleService.removeVehicle(regno);
	}
	
	@GetMapping("/getById")
	public Response<VehicleDto> viewVById(@RequestParam String regno){
		return vehicleService.viewVehicle(regno);
	}
	
	@GetMapping("/getAll")
	public Response<List<VehicleDto>> viewV(){
		return vehicleService.viewVehicles();
	}
	
	@GetMapping("/getBydealer")
	public Response<String> viewVByDealer(@RequestParam String dealer){
		return vehicleService.viewVehicleByDealer(dealer);
	}
	
	

}
