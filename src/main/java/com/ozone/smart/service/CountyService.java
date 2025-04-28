package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ozone.smart.dto.CountyDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.SubCountyDto;
import com.ozone.smart.entity.Counties;
import com.ozone.smart.entity.SubCounties;
import com.ozone.smart.repository.CountyRepo;
import com.ozone.smart.util.stringPadding;

@Service
public class CountyService {
	
	@Autowired
	private CountyRepo countyRepo;
	
	public Response<String> addCounty(CountyDto countyDto) {
		String districtId = countyDto.getDistrictId();
		String countyName = countyDto.getCountyname();
		Response<String> msg = new Response<>();
		
		if (districtId != null && !districtId.isEmpty()) {
			districtId += "%";
		}
		try {
			List<Counties> Listcounty = countyRepo.findByDistrictIdLikeOrderByDistrictId(districtId);
			String Cntid ="";
			String Countyid ="";
			
			if (Listcounty.size() != 0) {
				for (Counties cnts:Listcounty) {
					Cntid = cnts.getId(); //001013119
				}		
		
				String Nwcntid ="";
				Nwcntid = Cntid.substring(6, 9);
				int intNwcntid = Integer.parseInt(Nwcntid);
				intNwcntid += 1;
				
				Nwcntid = Integer.toString(intNwcntid);
				Nwcntid = stringPadding.leftPadZeroes(Nwcntid, 3);
				Countyid = Cntid.substring(0, 6) + Nwcntid;
				
			} else {
				districtId = districtId.replace("%", "");
				Countyid = districtId + "001";
			}
			
			Counties county = new Counties();
			
			county.setcountyname(countyName);
			county.setDistrictname(countyDto.getDistrictname());
			county.setId(Countyid);
			
			try {
				countyRepo.save(county);
				msg.setData("County " + countyName + " saved successfully");
			}
			catch(Exception e){
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		        
		        String strMsg="";
		        
				strMsg = th.getCause().toString();
				if (strMsg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
					strMsg = "County : " + countyName + " saved successfully";
					msg.setData(strMsg);
				} else if (strMsg.contains("The database returned no natively generated identity value")) {
					strMsg = "County : " + countyName + " saved successfully";
					msg.setData(strMsg);
				} else if (strMsg.contains("")) {
					strMsg = "General error";
					msg.setData(strMsg);
				}
				
			}	
		}catch(Exception e) {
			msg.setErrorMsg("Error creating county :" + countyName);
		}
		return msg;
	}

	public Response<String> updateCounty(CountyDto countyDto) {
		Response<String> msg = new Response<>();
			String countyId = countyDto.getId();
			String district = countyDto.getDistrictname();
			String countyName = countyDto.getCountyname();
			Optional<Counties> Opcounty = countyRepo.findById(countyId);
			if(Opcounty.isPresent()) {
				Counties county = Opcounty.get();
				county.setcountyname(countyDto.getCountyname());
				county.setDistrictname(countyDto.getDistrictname());
				countyRepo.save(county);
				msg.setData("County " + countyName + " update successfully");
			}else {
				msg.setErrorMsg("County with id " + countyId + " not found");
			}	
		return msg;
	}

	public Response<String> deleteCounty(String id) {
		countyRepo.deleteById(id);
		Response<String> msg = new Response<>();
		msg.setData("County Deleted Successfully");
		return msg;
	}

//	By County ID fetching countyName and DistrictName
	public Response<CountyDto> viewCounty(String id) {
		
		Response<CountyDto> response = new Response<>();
		
		try {
			Optional<Counties> opcounty = countyRepo.findById(id);
			CountyDto countyDto = new CountyDto();
			if(opcounty.isPresent()) {
				Counties cnty = opcounty.get();	
				countyDto.setCountyname(cnty.getcountyname());
				countyDto.setDistrictname(cnty.getDistrictname());
				response.setData(countyDto);
			}
			else {
				response.setErrorMsg("Please Check the Id");
			}
			
		}catch(Exception e) {
			response.setErrorMsg("Error retreiving details for stage id: " + id);
		}
		return response;
	}
	
	
	public Response<String> findStageByName(CountyDto countyDto) {
		String customerName=countyDto.getCountyname().toUpperCase();
		String districtName= countyDto.getDistrictname().toUpperCase();
		String strMsg[]=null;
		String strMsgs="";
		List<Counties> countys=countyRepo.findByDistrictNameAndCountyName(districtName,customerName );
		int i = 0;	
		if (countys.size() > 0) {
			strMsg = new String[countys.size()];
			for (Counties cntys:countys) {
				String strName = cntys.getcountyname();
				strMsg[i] = strName;
				i++;
			}
		} else {
			strMsg = new String[1];
			strMsg[i] = "";
		}
		strMsgs = Arrays.toString(strMsg);
		Response<String> response = new Response<>();
		response.setData(strMsgs);
		return response;
	}

	public List<CountyDto> viewAllCounty() {
		List<Counties> county = countyRepo.findAllByAsc();
		List<CountyDto> countyDto = new ArrayList<CountyDto>();
		for(Counties cnty : county) {
			CountyDto cdto = new CountyDto();
			cdto.setCountyname(cnty.getcountyname());
			cdto.setId(cnty.getId());
			countyDto.add(cdto);
		}
		return countyDto;
	}

	public Response<List<CountyDto>> fetchCounty(String id) {
		
		Response<List<CountyDto>> response = new Response<>();
		
		List<Counties> county = countyRepo.findByDistrictIdLikeOrderByDistrictId(id);

		
		List<CountyDto> CountyDtoList = county.stream()
		.map(counties -> {
			CountyDto CountyDto = new CountyDto();
			CountyDto.setId(counties.getId());
			CountyDto.setCountyname(counties.getcountyname());
			return CountyDto;
		})
		.collect(Collectors.toList());
		
		response.setData(CountyDtoList);
		return response;
		}
		
}
