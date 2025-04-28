package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblcounties")
public class Counties implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String countyname;
	private String districtname;
	
	public Counties() {		
	}
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getcountyname() {
		return countyname;
	}
	
	public void setcountyname(String countyname) {
		this.countyname = countyname;
	}
	
	public String getDistrictname() {
		return districtname;
	}
	
	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}
}
