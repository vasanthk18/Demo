package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblPasswordPolicy")
public class PasswordPolicy implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int pwdchangedays;
	private int pwdchangemonths;
	private int pwdlength;
	private String specialcharacters;
	private boolean uppercasecharacters;
	private boolean numericcharacters;
	
	public PasswordPolicy() {
	}
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPasswordChangeDays() {
		return pwdchangedays;
	}
	
	public void setPasswordChangeDays(int pwdchangedays) {
		this.pwdchangedays = pwdchangedays;
	}
	
	public int getPasswordChangeMonths() {
		return pwdchangemonths;
	}
	
	public void setPasswordChangeMonths(int pwdchangemonths) {
		this.pwdchangemonths = pwdchangemonths;
	}
	
	public int getPasswordLength() {
		return pwdlength;
	}
	
	public void setPasswordLength(int pwdlength) {
		this.pwdlength = pwdlength;
	}
	
	public String getSpecialCharacters() {
		return specialcharacters;
	}
	
	public void setSpecialCharacters(String specialcharacters) {
		this.specialcharacters = specialcharacters;
	}
	
	public boolean getUppercaseCharacters() {
		return uppercasecharacters;
	}
	
	public void setUppercaseCharacters(boolean uppercasecharacters) {
		this.uppercasecharacters = uppercasecharacters;
	}
	
	public boolean getNumericCharacters() {
		return numericcharacters;
	}
	
	public void setNumericCharacters(boolean numericcharacters) {
		this.numericcharacters = numericcharacters;
	}
	
}