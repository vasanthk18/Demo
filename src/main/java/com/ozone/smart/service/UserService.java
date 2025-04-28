package com.ozone.smart.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.AESEncryptComponent;
import com.ozone.smart.dto.Response;
import com.ozone.smart.dto.UserDto;
import com.ozone.smart.entity.User;
import com.ozone.smart.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AESEncryptComponent AESComponent;
	
	private Date Pwdchangedate;
	
	private String strEncryptedPassword;
	
	private void parseDateAndEncryptPassword(UserDto userDto) {
        // Parse date
		
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date passwordchangedatee = inputFormatter.parse(userDto.getPasswordchangedate());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String passwordchangedate = formatter.format(passwordchangedatee);
            Date dtPwdchangedate = formatter.parse(passwordchangedate);
            Pwdchangedate = dtPwdchangedate;
        } catch (ParseException e) {
            // Log or throw custom exception, don't just print stack trace
            throw new IllegalArgumentException("Invalid date format for password change date", e);
        }

        // Encrypt password
        try {
            String secretKey = userDto.getUsername();
            String encryptedPassword = AESComponent.encrypt(userDto.getPassword(), secretKey);
            strEncryptedPassword=encryptedPassword;
        } catch (Exception e) {
            // Log or throw custom exception, don't just print stack trace
            throw new RuntimeException("Error encrypting password", e);
        }
    }
	

	public Response<String> addUser(UserDto userDto) {
		
		Response<String> response = new Response<>();
		
		parseDateAndEncryptPassword(userDto);
		
		String userName = userDto.getUsername();
		
		Optional<User> checkuser = userRepo.findByusername(userName);
		if(checkuser.isPresent()) {
			response.setData("Username " + userName +" Already Exist");
		}else {
		
		
		User user = new User();
		user.setFullname(userDto.getFullname());
		user.setPassword(strEncryptedPassword);
		user.setPasswordchangedate(Pwdchangedate);
		user.setProfile(userDto.getProfile());
		user.setStatus(userDto.getStatus());
		user.setUsername(userDto.getUsername());
		
		
		try {
			userRepo.save(user);	
			response.setData("User " + userName + " saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorMsg("Error saving user: " + userName);
		}
		
		}
		return response;
	}


	public Response<String> editUser(UserDto userDto) {
		Response<String> response = new Response<>();
		String userName = userDto.getUsername();
		Optional<User> Opuser = userRepo.findById(userName);
		parseDateAndEncryptPassword(userDto);
		try {
			if(Opuser.isPresent()) {
				User user = Opuser.get();
				user.setFullname(userDto.getFullname());
				user.setPassword(strEncryptedPassword);
				user.setPasswordchangedate(Pwdchangedate);
				user.setProfile(userDto.getProfile());
				user.setStatus(userDto.getStatus());
				user.setUsername(userDto.getUsername());
				userRepo.save(user);
				response.setData("User " + userName + " updated successfully");
			}
		}catch(Exception e) {
			e.printStackTrace();
			Throwable th = e.getCause();
			response.setErrorMsg(th.getCause().toString());
		}	
		return response;
	}


	public Response<String> removeUser(String userName) {
		Response<String> response = new Response<>();
		try {
			userRepo.deleteById(userName);
			response.setData("User " + userName + " has been deleted");
		}catch(Exception e) {
			Throwable th = e.getCause();
			response.setErrorMsg(th.getCause().toString());
		}	
		return response;
	}


	public Response<UserDto> viewUser(String id) {
		
		Response<UserDto> response = new Response<>();
		UserDto userDto = new UserDto();
		try {
			Optional<User> opUser = userRepo.findById(id);
			if(opUser.isPresent()) {
				User user = opUser.get();
//				String Username = user.getUsername();
				String password = user.getPassword();
				
				String repassword = password.replaceAll("[\\n\\t]", "");
				String DecPassword = AESComponent.decrypt(repassword, id);
				
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String Passwordchgdate = formatter.format(user.getPasswordchangedate());
//				Date finalPasswordchgdate = formatter.parse(Passwordchgdate);
				
				userDto.setFullname(user.getFullname());
				userDto.setPassword(DecPassword);
				userDto.setPasswordchangedate(Passwordchgdate);
				userDto.setProfile(user.getProfile());
				userDto.setStatus(user.getStatus());
				userDto.setUsername(user.getUsername());
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.setErrorMsg("Error retreiving details for vehicle: " + id);
		}
		
		response.setData(userDto);
		return response;
	}


	public Response<List<UserDto>> viewUsers() {
		Response<List<UserDto>> response = new Response<>();
		List<User> userList = userRepo.findAll();
		List<UserDto> UserDtoList = new ArrayList<>();
		
		for(User user : userList) {
			UserDto userDto = new UserDto();
			userDto.setUsername(user.getUsername());
			UserDtoList.add(userDto);
			response.setData(UserDtoList);
		}	
		return response;
	}
	
	public Response<List<UserDto>> listUsers() {
		Response<List<UserDto>> response = new Response<>();
		List<User> userList = userRepo.getListOfUsers();
		List<UserDto> UserDtoList = new ArrayList<>();
		
		for(User user : userList) {
			UserDto userDto = new UserDto();
			userDto.setUsername(user.getUsername());
			userDto.setProfile(user.getProfile());
			userDto.setStatus(user.getStatus());
			UserDtoList.add(userDto);
			response.setData(UserDtoList);
		}	
		return response;
	}

}
