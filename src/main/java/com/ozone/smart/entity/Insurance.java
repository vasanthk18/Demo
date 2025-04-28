package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblinsurance")
public class Insurance implements Serializable {

	private static final long serialVersionUID = 1L;
	private String insuranceid;
	private String assetdepreciation;
	private String rate;
	private String firstvalue;
	private String secondvalue;
	private String thirdvalue;

	public Insurance() {
	}
	
	@Id
	@Column(name="insuranceid")
	public String getInsuranceid() {
		return insuranceid;
	}
	
	public void setInsuranceid(String insuranceid) {
		this.insuranceid = insuranceid;
	}
	
	public String getAssetdepreciation() {
		return assetdepreciation;
	}
	
	public void setAssetdepreciation(String assetdepreciation) {
		this.assetdepreciation = assetdepreciation;
	}
	
	public String getRate() {
		return rate;
	}
	
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getFirstvalue() {
		return firstvalue;
	}
	
	public void setFirstvalue(String firstvalue) {
		this.firstvalue = firstvalue;
	}
	
	public String getSecondvalue() {
		return secondvalue;
	}
	
	public void setSecondvalue(String secondvalue) {
		this.secondvalue = secondvalue;
	}
	
	public String getThirdvalue() {
		return thirdvalue;
	}
	
	public void setThirdvalue(String thirdvalue) {
		this.thirdvalue = thirdvalue;
	}

}
