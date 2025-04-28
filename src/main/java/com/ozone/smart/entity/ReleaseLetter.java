package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblreleaseletter")
public class ReleaseLetter implements Serializable {

	private static final long serialVersionUID = 1L;
	private String proposalno;
	private String customerid;
	private String agreementno;
	private String printuser;
	private String printdatetime;
		
	public ReleaseLetter() {		
	}
	
	@Id
	@Column(name="proposalno")
	public String getProposalno() {
		return proposalno;
	}
	
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	
	public String getCustomerid() {
		return customerid;
	}
	
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	public String getAgreementno() {
		return agreementno;
	}
	
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
	}
	
	public String getPrintuser() {
		return printuser;
	}
	
	public void setPrintuser(String printuser) {
		this.printuser = printuser;
	}
	
	public String getPrintdatetime() {
		return printdatetime;
	}
	
	public void setPrintdatetime(String printdatetime) {
		this.printdatetime = printdatetime;
	}

}