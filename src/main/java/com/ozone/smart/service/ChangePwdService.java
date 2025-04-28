package com.ozone.smart.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozone.smart.component.AESEncryptComponent;
import com.ozone.smart.dto.ChangePwdDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.User;
import com.ozone.smart.repository.PasswordPolicyRepo;
import com.ozone.smart.repository.UserRepo;

@Service
public class ChangePwdService {
	
	@Autowired
	AESEncryptComponent AESComponent;
	
	@Autowired
	PasswordPolicyRepo passwordPolicyRepo;
	
	@Autowired
	UserRepo userRepo;

	public Response<String> updatePwd(ChangePwdDto changepwdDto) {
		
		
		Response<String> response = new Response<>();
		
		String Username =changepwdDto.getUserName();
		String OldPassword=changepwdDto.getOldPassword();
		String Newpassword=changepwdDto.getNewPassword();
		String Passwordchangedate=changepwdDto.getPasswordChangeDate();
		Date Pwdchangedate= null;
		String EncryptedPassword = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Pwdchangedate = formatter.parse(Passwordchangedate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			String secretKey = Username;
			EncryptedPassword = AESComponent.encrypt(Newpassword, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Optional<User> Opuser = userRepo.findById(Username);
		if(Opuser.isEmpty()) {
			response.setData("Username " + Username + " does not exist");
		}else {			
				User user = Opuser.get();
				String Password = user.getPassword();
				Password = Password.replaceAll("[\\n\\t]", "");
				
				String DecryptedPassword = AESComponent.encrypt(OldPassword, Username);
				
				if (DecryptedPassword.equals(Password)) {
					
					
					user.setPassword(EncryptedPassword);
					user.setPasswordchangedate(Pwdchangedate);
					
					try {
						User savedUser =userRepo.save(user);
						if (savedUser != null) {
							response.setData("User password changed successfully.");
						} else {
							response.setData("Error changing password for user: " + Username);
						}
					} catch (Exception e) {
						e.printStackTrace();
						response.setData("Error changing password for user: " + Username);
					}	
				} else {
					response.setData("Please enter correct old password ");
				}
			}	
		return response;
	}
}
