package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.VehicleDto;
import com.ozone.smart.entity.Vehicle;
import com.ozone.smart.repository.VehicleRepo;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepo vehicleRepo;

	public Response<String> addVehicle(VehicleDto vehicleDto) {
		
		String Dealer = vehicleDto.getDealer().trim();
		String RegNo = vehicleDto.getRegno().trim();
		String Engine = vehicleDto.getEngineno().trim();
		String Chassis = vehicleDto.getChassisno().trim();
		String TrackingNo = vehicleDto.getTrackingno().trim();
		String SimcardNo = vehicleDto.getSimcardno().trim();
		RegNo = RegNo.replace(" ", "");
		Response<String> response = new Response<>();
		Vehicle vehicle = new Vehicle();
		vehicle.setChassisno(Chassis);
		vehicle.setDealer(Dealer);
		vehicle.setEngineno(Engine);
		vehicle.setRegno(RegNo);
		vehicle.setSimcardno(SimcardNo);
		vehicle.setTrackingno(TrackingNo);
		try {
			vehicleRepo.save(vehicle);
			response.setData("Vehicle regno: " + RegNo + " saved successfully.");
		}catch(Exception e){
			Throwable th = e.getCause();
	        System.out.println("THROWABLE INFO: " + th.getCause().toString());
	         
			String Msg = th.getCause().toString();
			if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
				response.setData( "Vehicle regno: " + RegNo + " saved successfully.");
			} else if (Msg.contains("The database returned no natively generated identity value")) {
				response.setData("Vehicle regno: " + RegNo + " saved successfully.");
			} else if (Msg.contains("ConstraintViolationException")) {
				response.setData("Vehicle regno: " + RegNo + " saved successfully.");	
			}
		}
		
		return response;
	}

	public Response<String> editVehicle(VehicleDto vehicleDto) {
		String id = vehicleDto.getRegno().trim();
		Response<String> response = new Response<>();
		Optional<Vehicle> opVehicle = vehicleRepo.findById(id);
		
		if(opVehicle.isPresent()) {
			Vehicle v = opVehicle.get();
			v.setChassisno(vehicleDto.getChassisno().trim());
			v.setDealer(vehicleDto.getDealer().trim());
			v.setEngineno(vehicleDto.getEngineno().trim());
			v.setRegno(vehicleDto.getRegno().trim());
			v.setSimcardno(vehicleDto.getSimcardno().trim());
			v.setTrackingno(vehicleDto.getTrackingno().trim());
			vehicleRepo.save(v);
		}
		response.setData("Vehicle " + id + " updated successfully");
		return response;
	}

	public Response<String> removeVehicle(String id) {
		Response<String> response = new Response<>();
		vehicleRepo.deleteById(id);
		response.setData("Vehicle " + id + " has been deleted");
		return response;
	}

	public Response<VehicleDto> viewVehicle(String id) {
		Response<VehicleDto> response = new Response<>();
		VehicleDto vehicleDto = new VehicleDto();
		try {
			Optional<Vehicle> opVehicle = vehicleRepo.findById(id);
			if(opVehicle.isPresent()) {
				Vehicle vehicle = opVehicle.get();
				vehicleDto.setChassisno(vehicle.getChassisno());
				vehicleDto.setDealer(vehicle.getDealer());
				vehicleDto.setEngineno(vehicle.getEngineno());
				vehicleDto.setRegno(vehicle.getRegno());
				vehicleDto.setSimcardno(vehicle.getSimcardno());
				vehicleDto.setTrackingno(vehicle.getTrackingno());
			}
		}catch(Exception e) {
			response.setErrorMsg("Error retreiving details for vehicle: " + id);
		}
		
		response.setData(vehicleDto);
		return response;
	}

	public Response<List<VehicleDto>> viewVehicles() {
		Response<List<VehicleDto>> response = new Response<>();
		List<Vehicle> vehicleList = vehicleRepo.findAll();
		List<VehicleDto> vehicleDtoList = new ArrayList<>();
		
		for(Vehicle vehicle : vehicleList) {
			VehicleDto vehicleDto = new VehicleDto();
			vehicleDto.setRegno(vehicle.getRegno());
			vehicleDtoList.add(vehicleDto);
			response.setData(vehicleDtoList);
		}	
		return response;
	}

	public Response<String> viewVehicleByDealer(String dealer) {
		
		Response<String> response = new Response<>();
		
		String strMsg = "";
		String strDealer = "";
		String strVehicle[];
		String strVehicleno = "";
		
		strDealer = dealer;
		
		if (strDealer == null || strDealer.length() == 0) {			
			strMsg = "Select dealer";
		} else {
					
//			String strQuery = "From Vehicle where dealer = " + "'" + strDealer + "' and allocated = false";
			
			try {
				List<Vehicle> vehicle = vehicleRepo.findByDealerAndAllocation(strDealer);
				strVehicle = new String[vehicle.size()];

				int i = 0;
				for (Vehicle vh:vehicle) {
					strVehicleno = vh.getRegno();
					strVehicle[i] = strVehicleno;
					i++;
				}	
				
				strMsg = Arrays.toString(strVehicle);
				
			} catch (Exception e) {
				e.printStackTrace();
				strMsg = "Error retrieving dealer vehicles for : " + strDealer;
			}
		}
				
		response.setData(strMsg);
		return response;
	}

}
