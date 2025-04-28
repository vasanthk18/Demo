package com.ozone.smart.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblusers")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int status;
	private Date passwordchangedate;
	private String profile;
	private String fullname;
	private int loggedin;
	
	public User() {		
	}
	
	@Id
	@Column(name="username")
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
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getPasswordchangedate() {
		return passwordchangedate;
	}

	public void setPasswordchangedate(Date passwordchangedate) {
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