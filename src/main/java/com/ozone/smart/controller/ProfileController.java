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

import com.ozone.smart.dto.CategoryDto;
import com.ozone.smart.dto.ProfileDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/saveProfile")
	public Response<String> saveRole(@RequestBody ProfileDto profileDto){
		return profileService.addRole(profileDto);
	}
	
	@PutMapping("/updateProfile")
	public Response<String> putProfile(@RequestBody ProfileDto profileDto){
		return profileService.editRole(profileDto);
	}
	
	@DeleteMapping("/deleteProfile")
	public Response<String> deleteProfile(@RequestBody ProfileDto profileDto){
		return profileService.removeRole(profileDto);
	}
	
	
	@GetMapping("/viewProfile")
	public Response<String> viewProfileById(@RequestParam String id){
		return profileService.viewProfile(id);
	}
	
	@GetMapping("/viewProfiles")
	public Response<List<ProfileDto>> viewProfile(){
		return profileService.viewProfiles();
	}
	
	@GetMapping("/viewCategory")
	public Response<List<CategoryDto>> viewCategory(){
		return profileService.viewCategory();
	}
	
	
}
