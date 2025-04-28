package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbluseraudit")
public class UserAudit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userid;
	private String logindatetime;
	private String logoutdatetime;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public UserAudit() {		
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getLogindatetime() {
		return logindatetime;
	}
	
	public void setLogindatetime(String logindatetime) {
		this.logindatetime = logindatetime;
	}
	
	public String getLogoutdatetime() {
		return logoutdatetime;
	}
	
	public void setLogoutdatetime(String logoutdatetime) {
		this.logoutdatetime = logoutdatetime;
	}

}