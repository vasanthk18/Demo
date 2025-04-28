package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblptp")
public class Ptp implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String agreementno;
	private String customername;
	private String mobile;
	private String installment;
	private String bucket;
	private String ptpdate;
	private String ptpremarks;
	private String captureuser;
	private String capturedatetime;
	private String capturedate;
	
	public Ptp() {		
	}
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAgreementno() {
		return agreementno;
	}
	
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
	}
	
	public String getCustomername() {
		return customername;
	}
	
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getInstallment() {
		return installment;
	}
	
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	
	public String getBucket() {
		return bucket;
	}
	
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	
	public String getPtpdate() {
		return ptpdate;
	}
	
	public void setPtpdate(String ptpdate) {
		this.ptpdate = ptpdate;
	}
	
	public String getPtpremarks() {
		return ptpremarks;
	}
	
	public void setPtpremarks(String ptpremarks) {
		this.ptpremarks = ptpremarks;
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
	
	public String getCapturedate() {
		return capturedate;
	}
	
	public void setCapturedate(String capturedate) {
		this.capturedate = capturedate;
	}

}