package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.StageDto;
import com.ozone.smart.entity.Stage;
import com.ozone.smart.repository.StageRepo;

@Service
public class StageService {
	
	@Autowired
	private StageRepo stageRepo;
	
	public Response<String> saveStage(StageDto stageDto) {
		Response<String> msg = new Response<>();
	    try {
	        Stage stg = new Stage();
	        stg.setName(stageDto.getName());
	        stg.setMobileno(stageDto.getMobileno());
	        stg.setChairman(stageDto.getChairman());
	        stg.setLandmark(stageDto.getLandmark());
	        stg.setLocation(stageDto.getLocation());
	        stg.setNoofvehicles(stageDto.getNoofvehicles());
	        stageRepo.save(stg);
	        msg.setData("Stage saved Successfully");
	    } catch (Exception e) {
	        // Log the exception or handle it according to your application's requirements
	        e.printStackTrace();
	        msg.setErrorMsg("An error occurred while saving the stage.");
	    }
	    return msg;
	}

	public Response<String> editStage(StageDto stageDto) {
		Response<String> msg = new Response<>();
		try {
			int id =stageDto.getId();
			Optional<Stage> stageOp =stageRepo.findById(id);
			if(stageOp.isPresent()) {
				Stage stage = stageOp.get();
				stage.setChairman(stageDto.getChairman());
				stage.setLandmark(stageDto.getLandmark());
				stage.setLocation(stageDto.getLocation());
				stage.setMobileno(stageDto.getMobileno());
				stage.setName(stageDto.getName());
				stage.setNoofvehicles(stageDto.getNoofvehicles());				
				stageRepo.save(stage);
			}
			msg.setData("Stage Edited Successfully");	
		}catch(Exception e){
			e.printStackTrace();
			msg.setErrorMsg("An error occurred while saving the stage.");	
		}
		return msg;
		
		
	}
	public Response<String> deleteSatge(int id) {
		 Response<String> msg =new Response<String>();
		 try {
		        stageRepo.deleteById(id);
		        msg.setData("Stage Deleted Successfully for " + id);
		    } catch (Exception e) {
		        // Handle the exception
		        msg.setErrorMsg("Failed to delete stage with ID " + id );
		        // Optionally, you can log the exception for further investigation
		        e.printStackTrace();
		    }
		return msg;
		
	}
	
	
	public List<StageDto> viewStageNames() {
		List<Stage> stageOp = stageRepo.findAll();
		
		List<StageDto> stageDtoList = new ArrayList<>();
		for(Stage stage: stageOp) {
			StageDto stageDto = new StageDto();
			stageDto.setName(stage.getName());
			stageDto.setLocation(stage.getLocation());
			stageDto.setId(stage.getId());
			stageDtoList.add(stageDto);
		}
		return stageDtoList;	
	}
	
	public Response<StageDto> viewStage(int id) {
	    try {
	        Optional<Stage> stageOp = stageRepo.findById(id);
	        StageDto stageDto = new StageDto();
	        if (stageOp.isPresent()) {
	            Stage stage = stageOp.get();
	            stageDto.setId(stage.getId());
	            stageDto.setChairman(stage.getChairman());
	            stageDto.setLandmark(stage.getLandmark());
	            stageDto.setLocation(stage.getLocation());
	            stageDto.setMobileno(stage.getMobileno());
	            stageDto.setName(stage.getName());
	            stageDto.setNoofvehicles(stage.getNoofvehicles());
	        }
	        Response<StageDto> stageDetails = new Response<>();
	        stageDetails.setData(stageDto);
	        return stageDetails;
	    } catch (Exception e) {
	        // Log the exception or handle it according to your application's requirements
	        e.printStackTrace();
	        // Create and return an appropriate response indicating the failure
	        Response<StageDto> errorResponse = new Response<>();
	        errorResponse.setErrorMsg("Error retreiving details for stage id: " + id);
	        return errorResponse;
	    }
	}
	
	
	public Response<String> findStageByName(String name) {
		String customerName=name.toUpperCase();
		String strMsg[]=null;
		String strMsgs="";
		List<Stage> stage=stageRepo.findByNameContainingIgnoreCase(customerName);
		int i=0;
		if(stage.size()>0) {
			strMsg =new String[stage.size()];
			for(Stage stg: stage) {
				String strName=stg.getName();
				String strLocation=stg.getLocation();
				strMsg[i]=strName+"::"+strLocation;
				i++;
			}
		}else {
			strMsg = new String[1];
			strMsg[i] = "";
		}
		strMsgs = Arrays.toString(strMsg);
		Response<String> response = new Response<>();
		response.setData(strMsgs);
		return response;
	}

}
