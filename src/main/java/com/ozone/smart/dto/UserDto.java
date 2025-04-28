package com.ozone.smart.dto;

import java.util.Date;

public class UserDto {
	
	private String username;
	private String password;
	private String newusername;
	private int status;
	private String passwordchangedate;
	private String profile;
	private String fullname;
	private int loggedin;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewusername() {
		return newusername;
	}
	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPasswordchangedate() {
		return passwordchangedate;
	}
	public void setPasswordchangedate(String passwordchangedate) {
		this.passwordchangedate = passwordchangedate;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getLoggedin() {
		return loggedin;
	}
	public void setLoggedin(int loggedin) {
		this.loggedin = loggedin;
	}
	
	
}
