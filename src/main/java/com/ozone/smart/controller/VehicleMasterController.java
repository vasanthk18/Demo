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
import com.ozone.smart.dto.VehicleMasterDto;
import com.ozone.smart.service.VehicleMasterService;

@RestController
@RequestMapping("/api/v1")
public class VehicleMasterController {
	
	@Autowired
	private VehicleMasterService vmService;
	
	@PostMapping("/saveVm")
	public Response<String> getVehicleMaster(@RequestBody VehicleMasterDto vmDto){
		return vmService.addVehicleMaster(vmDto);
	}
	
	@PutMapping("/editVm")
	public Response<String> putVehicleMaster(@RequestBody VehicleMasterDto vmDto){
		return vmService.editVehicleMaster(vmDto);
	}
	
	@DeleteMapping("/deleteVm")
	public Response<String> deleteVehicleMaster(@RequestParam int id){
		return vmService.removeVehicleMaster(id);
	}
//	id ->pk id of vehicleMaster
	@GetMapping("/viewVmById")
	public Response<VehicleMasterDto> viewVMById(@RequestParam int id){
		return vmService.viewVehicleMaster(id);
	}
	
	@GetMapping("/viewVM")
	public Response<List<VehicleMasterDto>> viewVM(){
		return vmService.viewVM();
	}

}
