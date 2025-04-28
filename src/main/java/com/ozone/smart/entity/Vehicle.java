package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblvehicle")
public class Vehicle implements Serializable{

	private static final long serialVersionUID = 1L;
	private String regno;
	private String engineno;
	private String chassisno;
	private String trackingno;
	private String simcardno;
	private String dealer;
	private boolean allocated;
	
	public Vehicle() {		
	}
	
	@Id
	@Column(name="regno")
	public String getRegno() {
		return regno;
	}
	
	public void setRegno(String regno) {
		this.regno = regno;
	}
	
	public String getEngineno() {
		return engineno;
	}
	
	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}
		
	public String getChassisno() {
		return chassisno;
	}
	
	public void setChassisno(String chassisno) {
		this.chassisno = chassisno;
	}
	
	public String getTrackingno() {
		return trackingno;
	}
	
	public void setTrackingno(String trackingno) {
		this.trackingno = trackingno;
	}

	public String getSimcardno() {
		return simcardno;
	}
	
	public void setSimcardno(String simcardno) {
		this.simcardno = simcardno;
	}

	public String getDealer() {
		return dealer;
	}
	
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}	
	
	public boolean getAllocated() {
		return allocated;
	}
	
	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}	
	
}