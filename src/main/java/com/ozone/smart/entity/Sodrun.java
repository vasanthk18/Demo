package com.ozone.smart.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblsodrun")
public class Sodrun implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date rundate;
	private String nofofewi;
	private String noofloans;
	private String status;
	private String runuser;
	private String rundatetime;

	
	public Sodrun() {		
	}
	
	@Id
	@Column(name="rundate")
	public Date getRundate() {
		return rundate;
	}
	
	public void setRundate(Date rundate) {
		this.rundate = rundate;
	}
	
	public String getNofofewi() {
		return nofofewi;
	}
	
	public void setNofofewi(String nofofewi) {
		this.nofofewi = nofofewi;
	}
	
	public String getNoofloans() {
		return noofloans;
	}
	
	public void setNoofloans(String noofloans) {
		this.noofloans = noofloans;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRunuser() {
		return runuser;
	}
	
	public void setRunuser(String runuser) {
		this.runuser = runuser;
	}
	
	public String getRundatetime() {
		return rundatetime;
	}
	
	public void setRundatetime(String rundatetime) {
		this.rundatetime = rundatetime;
	}
}