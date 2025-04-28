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
import com.ozone.smart.dto.UserDto;
import com.ozone.smart.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/saveUser")
	public Response<String> saveUser(@RequestBody UserDto userDto){
		return userService.addUser(userDto);
	}
	
	@PutMapping("/updateUser")
	public Response<String> putUser(@RequestBody UserDto userDto){
		return userService.editUser(userDto);
	}
	
	@DeleteMapping("/deleteUser")
	public Response<String> deleteUser(@RequestParam String id){
		return userService.removeUser(id);
	}
	
	
	@GetMapping("/getUser")
	public Response<UserDto> viewUserById(@RequestParam String id){
		return userService.viewUser(id);
	}
	
	@GetMapping("/viewUser")
	public Response<List<UserDto>> viewUser(){
		return userService.viewUsers();
	}
	
	@GetMapping("/listUser")
	public Response<List<UserDto>> listUser(){
		return userService.listUsers();
	}

}
