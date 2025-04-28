package com.ozone.smart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.dto.CategoryDto;
import com.ozone.smart.dto.ProfileDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.Category;
import com.ozone.smart.entity.Profile;
import com.ozone.smart.repository.CategoryRepo;
import com.ozone.smart.repository.ProfileRepo;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private CategoryRepo catRepo;

	public Response<String> addRole(ProfileDto profileDto) {
		
		String ProfileName = profileDto.getProfile().trim();
		String Roles = profileDto.getCategory().trim();
		String[] Role = Roles.split("::");
		
		Response<String> response = new Response<>();
		
		for (int i = 0; i < Role.length; i++) {
			Profile pf = new Profile();
			pf.setProfile(ProfileName);
			pf.setCategory(Role[i]);
			
			try {
				profileRepo.save(pf);
				response.setData("Profile: " + ProfileName + " saved successfully.");	
			} catch (Exception e) {
				Throwable th = e.getCause();
		        System.out.println("THROWABLE INFO: " + th.getCause().toString());
		         
				String Msg = th.getCause().toString();
				if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
					Msg = "Profile: " + ProfileName + " saved successfully.";
				} else if (Msg.contains("The database returned no natively generated identity value")) {
					Msg = "Profile: " + ProfileName + " saved successfully.";
				} else if (Msg.contains("ConstraintViolationException")) {
					Msg = "Profile: " + ProfileName + " saved successfully.";	
				}
				response.setData(Msg);
				response.setData(Msg);
			}
		}	
		return response;
	}

	public Response<String> editRole(ProfileDto profileDto) {
		
		int i=0;
		int j=0;
		String Arrayorole[];
		String strOldrole = "";
		Response<String> response = new Response<>();
		String ProfileName = profileDto.getProfile().trim();
		String Roles = profileDto.getCategory().trim();
		String[] Role = Roles.split("::");
		
		try {
		List<Profile> profile = profileRepo.findByProfileName(ProfileName);
		Arrayorole = new String[profile.size()];
		int f = 0;
		for (Profile spf:profile) {
			String Orole = spf.getCategory();
			Arrayorole[f] = Orole;
			f++;
		}	
		
		String Nrole="";
		String Norole="";
		boolean blnAvail = false;
		
		//check for availability and insert
		for (i = 0; i < Role.length; i++) {						
			Nrole = Role[i];
			for (j = 0; j < Arrayorole.length; j++ ) {
				Norole = Arrayorole[j];
				if (Nrole.equals(Norole)) {
					blnAvail = true;
				}						
			}		
			if (blnAvail == false) {
				Profile upf = new Profile();
				upf.setProfile(ProfileName);
				upf.setCategory(Nrole);
				
				try {
					profileRepo.save(upf);
					response.setData( "Profile: " + ProfileName + " saved successfully.");
				} catch (Exception e) {
					Throwable th = e.getCause();
			        System.out.println("THROWABLE INFO: " + th.getCause().toString());
			         
					String Msg = th.getCause().toString();
					if (Msg.contains("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
						response.setData("Profile: " + ProfileName + " saved successfully.");
					} else if (Msg.contains("The database returned no natively generated identity value")) {
						response.setData("Profile: " + ProfileName + " saved successfully."); 
					} else if (Msg.contains("ConstraintViolationException")) {
						response.setData( "Profile: " + ProfileName + " saved successfully.");	
					}
				}
			}
			blnAvail = false;
		}
		blnAvail = false;
		Nrole = "";
		for (i = 0; i < Arrayorole.length; i++) {						
			strOldrole = Arrayorole[i];
			
			for (j = 0; j < Role.length; j++ ) {
				Nrole = Role[j];
				if (strOldrole.equals(Nrole)) {
					blnAvail = true;
				}							
			}
			
			if (blnAvail == false) {
//				strQuery = "delete Profile where profile = '" + strPfname + "' and category = '" + strOldrole + "'";
//				intUser = libraryBean.updateProfile(strQuery);
				profileRepo.deleteByProfileAndCategory(ProfileName, strOldrole);
				response.setData(ProfileName + " as updated successfully");
			}
			blnAvail = false;
		}				
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	
	public Response<String> removeRole(ProfileDto profileDto) {
		String ProfileName = profileDto.getProfile().trim();
		String Roles = profileDto.getCategory().trim();
		String[] Role = Roles.split("::");
		List<Profile> profile = profileRepo.findByProfileName(ProfileName);
		String[] Arrayorole = new String[profile.size()];
		Response<String> response = new Response<>();
		
		int f = 0;
		for (Profile spf:profile) {
			String Orole = spf.getCategory();
			Arrayorole[f] = Orole;
			f++;
		}					
		String Nrole="";
		String Oldrole="";
		boolean blnAvail = false;
		//check for availability and insert
		for (int i = 0; i < Arrayorole.length; i++) {						
			Oldrole = Arrayorole[i];
			
			for (int j = 0; j < Role.length; j++ ) {
				Nrole = Role[j];
				if (Oldrole.equals(Nrole)) {
					blnAvail = true;
				}							
			}		
			if (blnAvail == false) {
				try {
					profileRepo.deleteByProfileAndCategory(ProfileName, Oldrole);
					response.setData(ProfileName + " as deleted successfully");
				}catch(Exception e){
					e.printStackTrace();
					response.setData("Error Deleting"+ ProfileName);
				}
				
			}
			blnAvail = false;
		}
		return response;
	}
	
	public Response<String> viewProfile(String profileName) {
		Response<String> response = new Response<>();
		String ProfileName = profileName.trim();
		String Role="";
		String Msg="";
		try {
			List<Profile> profile = profileRepo.findByProfileName(ProfileName);
			
			for (Profile pf:profile) {
				Role = pf.getCategory();
				Role += "|";
				Msg += Role;
			}
			response.setData(Msg);
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			Msg = "Error retreiving details for stage id: " + ProfileName;
			response.setErrorMsg(Msg);
			response.setErrorMsg(Msg);
		}	
		return response;
	}

	public Response<List<ProfileDto>> viewProfiles() {
		Response<List<ProfileDto>> response = new Response<>();
		
		List<String> profileName = profileRepo.findAllByDistintName();
		
		List<ProfileDto> profileDtoList = new ArrayList<>();
		
		for(String p : profileName) {
			ProfileDto pd = new ProfileDto();
			pd.setProfile(p);
			profileDtoList.add(pd);
			
		}
		response.setData(profileDtoList);
		return response;
	}

	public Response<List<CategoryDto>> viewCategory() {
		Response<List<CategoryDto>> response = new Response<>();
		
		List<String> categoryName = catRepo.getParentCategory();
		
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		
		for(String p : categoryName) {
			CategoryDto pd = new CategoryDto();
			pd.setParentcategory(p);
			categoryDtoList.add(pd);
			
		}
		response.setData(categoryDtoList);
		return response;
	}
}
