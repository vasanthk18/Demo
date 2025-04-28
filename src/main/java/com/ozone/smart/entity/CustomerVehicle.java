package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblcustomervehicle")
public class CustomerVehicle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String proposalno;
	private String vehicleregno;
	
	@Column(name="agreementserial")
	private int agreementserial;
	
	private String agreementno;
	private String remarks;
	private String capturedatetime;
	private boolean disbursed;
	private String agreementfilename;
	private String cvuser;
	private String disbuser;
	private String disbdatetime;
	private String agupuser;
	private String agupdatetime;
	
	public CustomerVehicle() {		
	}
	
	
	public int getAgreementserial() {
		return agreementserial;
	}
	
//	@PostConstruct
	public void setAgreementserial(int agreementserial) {
		this.agreementserial = agreementserial;
	}
		
	public String getProposalno() {
		return proposalno;
	}
	
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	
	public String getVehicleregno() {
		return vehicleregno;
	}
	
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
		
	public String getAgreementno() {
		return agreementno;
	}
	
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getCapturedatetime() {
		return capturedatetime;
	}
	
	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}
	
	public boolean getDisbursed() {
		return disbursed;
	}
	
	public void setDisbursed(boolean disbursed) {
		this.disbursed = disbursed;
	}
	
	public String getAgreementfilename() {
		return agreementfilename;
	}
	
	public void setAgreementfilename(String agreementfilename) {
		this.agreementfilename = agreementfilename;
	}
	
	public String getCvuser() {
		return cvuser;
	}
	
	public void setCvuser(String cvuser) {
		this.cvuser = cvuser;
	}
	
	public String getDisbuser() {
		return disbuser;
	}
	
	public void setDisbuser(String disbuser) {
		this.disbuser = disbuser;
	}
	
	public String getDisbdatetime() {
		return disbdatetime;
	}
	
	public void setDisbdatetime(String disbdatetime) {
		this.disbdatetime = disbdatetime;
	}
	
	public String getAgupuser() {
		return agupuser;
	}
	
	public void setAgupuser(String agupuser) {
		this.agupuser = agupuser;
	}
	
	public String getAgupdatetime() {
		return agupdatetime;
	}
	
	public void setAgupdatetime(String agupdatetime) {
		this.agupdatetime = agupdatetime;
	}
	
}