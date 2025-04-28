package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tblstage")
public class Stage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblstage_id_seq")
	@SequenceGenerator(name = "tblstage_id_seq", sequenceName = "tblstage_id_seq",  allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	private String name;
	private String chairman;
	private String mobileno;
	private String location;
	private String landmark;
	private String noofvehicles;
	
	public Stage() {		
	}
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getChairman() {
		return chairman;
	}
	
	public void setChairman(String chairman) {
		this.chairman = chairman;
	}

	public String getMobileno() {
		return mobileno;
	}
	
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLandmark() {
		return landmark;
	}
	
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	public String getNoofvehicles() {
		return noofvehicles;
	}
	
	public void setNoofvehicles(String noofvehicles) {
		this.noofvehicles = noofvehicles;
	}
		
}
