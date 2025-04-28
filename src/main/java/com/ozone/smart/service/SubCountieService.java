package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CountyDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.StageDto;
import com.ozone.smart.dto.SubCountyDto;
import com.ozone.smart.entity.Counties;
import com.ozone.smart.entity.Districts;
import com.ozone.smart.entity.Stage;
import com.ozone.smart.entity.SubCounties;
import com.ozone.smart.repository.DistrictRepo;
import com.ozone.smart.repository.SubCountiesRepo;
import com.ozone.smart.util.stringPadding;

@Service
public class SubCountieService {
	
	@Autowired
	SubCountiesRepo subCountyRepo;
	
	@Autowired
	DistrictRepo districtRepo;

	public Response<String> saveSubCounty(SubCountyDto subCountyDto) {
		// TODO Auto-generated method stub
		String Subcountyid ="";
		String SubcountyName= subCountyDto.getSubcountyname();
		Response<String> msg = new Response<>();
		String countyId = subCountyDto.getCountyid();
		
		if (countyId.isEmpty() != true) {
			countyId += "%";
		}
		List<SubCounties> subCnty=subCountyRepo.findByCountyId(countyId);
		try {
			if (subCnty.size() != 0) {
				for (SubCounties subcnts:subCnty) {
					Subcountyid = subcnts.getId(); //0010131190073
				}		
				
				String strNwsubcntid = Subcountyid.substring(9, 13);
				int intNwsubcntid = Integer.parseInt(strNwsubcntid);
				intNwsubcntid += 1;
				
				strNwsubcntid = Integer.toString(intNwsubcntid);
				strNwsubcntid = stringPadding.leftPadZeroes(strNwsubcntid, 4);
				
				Subcountyid = Subcountyid.substring(0, 9) + strNwsubcntid;
			} else {
				countyId = countyId.replace("%", "");
				Subcountyid = countyId + "0001";
			}
			
			
			SubCounties subcnty = new SubCounties();
			subcnty.setcountyname(subCountyDto.getCountyname());
			subcnty.setSubcountyname(SubcountyName);
			subcnty.setId(Subcountyid);
			
			
			try {
				subCountyRepo.save(subcnty);
				msg.setData("Sub County " + SubcountyName +  " created successfully.");
				msg.setData("Sub County " + SubcountyName +  " created successfully.");
			}catch(Exception e) {
				e.printStackTrace();
				msg.setErrorMsg("Error creating Sub County : " + SubcountyName);
				msg.setErrorMsg("Error creating Sub County : " + SubcountyName);
			}
		}catch(Exception e) {
			e.printStackTrace();
			msg.setErrorMsg("Error creating Sub County : " + SubcountyName);
			msg.setErrorMsg("Error creating Sub County : " + SubcountyName);
		}
		return msg;
	}

	public Response<String> updateSubCounty(SubCountyDto subCountyDto) {
		Response<String> msg = new Response<>();
		String subCountyId = subCountyDto.getId();
		String countyName = subCountyDto.getCountyname();
		String subCountyName = subCountyDto.getSubcountyname();
		Optional<SubCounties> Opcounty = subCountyRepo.findById(subCountyId);
		if(Opcounty.isPresent()) {
			SubCounties subCounty = Opcounty.get();
			subCounty.setcountyname(countyName);
			subCounty.setSubcountyname(subCountyName);
			subCountyRepo.save(subCounty);
			msg.setData("Sub County" + subCountyName + " update successfully");
			msg.setData("Sub County" + subCountyName + " update successfully");
		}else {
			msg.setErrorMsg("County with id " + subCountyId + " not found");
			msg.setErrorMsg("County with id " + subCountyId + " not found");
		}	
	return msg;
	}

	public Response<String> deleteSubCounty(String id) {
		subCountyRepo.deleteById(id);
		Response<String> msg = new Response<>();
		msg.setData("SubCounty Deleted Successfully");
		msg.setData("SubCounty Deleted Successfully");
		return msg;
	}

	public Response<String> viewSubCounty(String id) {
		String subCntyId="";
		String subCntyName="";
		String cntyName="";
		String districtName="";
		Response<String> msg = new Response<>();
		try {
			Optional<SubCounties> Opsubcnty= subCountyRepo.findById(id);
			
			if(Opsubcnty.isPresent()) {
				SubCounties subcnty = Opsubcnty.get();
				cntyName = subcnty.getSubcountyname();
				subCntyName= subcnty.getcountyname();
				subCntyId = subcnty.getId();
			}
			String districtId = subCntyId.substring(0,6);
			Optional<Districts> Opdistrict= districtRepo.findById(districtId);
			if(Opdistrict.isPresent()) {
				Districts dist = Opdistrict.get();
				districtName = dist.getName();
			}
			msg.setData(subCntyName +"|"+ cntyName +"|"+ districtName );
		}catch(Exception e) {
			e.printStackTrace();
			msg.setErrorMsg("Error retreiving details for county id: " + id);
			msg.setErrorMsg("Error retreiving details for county id: " + id);
		}
		
		return msg;
	}
	
	public Response<String> checkSubCounty(SubCountyDto subCountyDto) {
		
		String msg[];
		String cntyName = subCountyDto.getCountyname().toUpperCase();
		String subCntyName = subCountyDto.getSubcountyname().toUpperCase();
		String subCntyN;
		List<SubCounties> subCounty = subCountyRepo.findByCntyAndSubCntyName(cntyName, subCntyName);
		int i=0;
		if(subCounty.size()>0) {
			msg=new String[subCounty.size()];
			for (SubCounties subcntys:subCounty) {
				subCntyN = subcntys.getSubcountyname();
				msg[i] = subCntyN;
				i++;
			}
		}else {
			msg = new String[1];
			msg[i] = "";
		}
		Response<String> response = new Response<>();
		response.setData(Arrays.toString(msg));
		return response;
	}

	public Response<List<SubCountyDto>> viewSubCountys(String Countyid) {
		
		Response<List<SubCountyDto>> response = new Response<>();
		
		List<SubCounties> subCounties = subCountyRepo.findByCountyId(Countyid);
		
		List<SubCountyDto> stageDtoList = new ArrayList<>();
		for(SubCounties subCounty: subCounties) {
			SubCountyDto subCountyDto = new SubCountyDto();
			subCountyDto.setSubcountyname(subCounty.getSubcountyname());
			subCountyDto.setId(subCounty.getId());
			stageDtoList.add(subCountyDto);
		}
		response.setData(stageDtoList);
		return response;

	}

	public List<SubCountyDto> viewAllCounty() {
		List<SubCounties> county = subCountyRepo.findAll();
		List<SubCountyDto> countyDto = new ArrayList<SubCountyDto>();
		for(SubCounties cnty : county) {
			SubCountyDto cdto = new SubCountyDto();
			cdto.setSubcountyname(cnty.getSubcountyname());
			cdto.setId(cnty.getId());
			countyDto.add(cdto);
		}
		return countyDto;
	}

}

	