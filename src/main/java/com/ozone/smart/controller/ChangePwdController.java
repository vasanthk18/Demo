package com.ozone.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozone.smart.dto.ChangePwdDto;
import com.ozone.smart.dto.Response;
import com.ozone.smart.service.ChangePwdService;

@RestController
@RequestMapping("/api/v1")
public class ChangePwdController {
	
	@Autowired
	private ChangePwdService chgPwdService;
	
	@PostMapping("/changepwd")
	public Response<String> changePwd(@RequestBody ChangePwdDto changepwdDto){
		return chgPwdService.updatePwd(changepwdDto);
		
	}

}
