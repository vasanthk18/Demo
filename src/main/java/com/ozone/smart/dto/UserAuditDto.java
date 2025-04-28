package com.ozone.smart.dto;

public class UserAuditDto {
	private int id;
	private String userid;
	private String logindatetime;
	private String logoutdatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
