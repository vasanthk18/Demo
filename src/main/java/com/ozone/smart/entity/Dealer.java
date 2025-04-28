package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbldealer")
public class Dealer implements Serializable{

	private static final long serialVersionUID = 1L;
	private String dealer;
	private String physicaladdress;
	private String postaladdress;
	private String telephone1;
	private String telephone2;
	private String email;
	
	public Dealer() {		
	}
	
	@Id
	@Column(name="dealer")
	public String getDealer() {
		return dealer;
	}
	
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	public String getPhysicaladdress() {
		return physicaladdress;
	}
	
	public void setPhysicaladdress(String physicaladdress) {
		this.physicaladdress = physicaladdress;
	}
		
	public String getPostaladdress() {
		return postaladdress;
	}
	
	public void setPostaladdress(String postaladdress) {
		this.postaladdress = postaladdress;
	}
	
	public String getTelephone1() {
		return telephone1;
	}
	
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
		
	public String getTelephone2() {
		return telephone2;
	}
	
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}