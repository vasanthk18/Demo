package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblimpoundedstock")
public class Impoundedstock implements Serializable {

	private static final long serialVersionUID = 1L;
	private String agreementno;
	private String proposalno;
	private String vehicleregno;
	private String remarks;
	private String impounddate;
	private String captureuser;
	private String capturedatetime;
	private String vehphotofilename;
	private String status;
	
	public Impoundedstock() {		
	}
	
	@Id
	@Column(name="agreementno")
	public String getAgreementno() {
		return agreementno;
	}
	
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
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
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getImpounddate() {
		return impounddate;
	}
	
	public void setImpounddate(String impounddate) {
		this.impounddate = impounddate;
	}
		
	public String getCaptureuser() {
		return captureuser;
	}
	
	public void setCaptureuser(String captureuser) {
		this.captureuser = captureuser;
	}
	
	public String getCapturedatetime() {
		return capturedatetime;
	}
	
	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}
	
	public String getVehphotofilename() {
		return vehphotofilename;
	}
	
	public void setVehphotofilename(String vehphotofilename) {
		this.vehphotofilename = vehphotofilename;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}