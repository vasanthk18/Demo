package com.ozone.smart.dto;

public class ChangePwdDto {
	
	private String userName;
	private String oldPassword;
	private String newPassword;
	private String passwordChangeDate;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getPasswordChangeDate() {
		return passwordChangeDate;
	}
	public void setPasswordChangeDate(String passwordChangeDate) {
		this.passwordChangeDate = passwordChangeDate;
	}
	
	
	
	
	
}
