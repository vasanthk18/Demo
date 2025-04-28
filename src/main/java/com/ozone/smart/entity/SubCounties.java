package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblsubcounties")
public class SubCounties implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String subcountyname;
	private String countyname;
	
	public SubCounties() {		
	}
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSubcountyname() {
		return subcountyname;
	}
	
	public void setSubcountyname(String subcountyname) {
		this.subcountyname = subcountyname;
	}
	
	public String getcountyname() {
		return countyname;
	}
	
	public void setcountyname(String countyname) {
		this.countyname = countyname;
	}
	
}
