package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblpenalty")
public class Penalty implements Serializable {

	private static final long serialVersionUID = 1L;	
	private String loanscheduleno; 
	private String loanid;
	private String loanpaymentdate;
	private String penalty;
	private String loaninstallment;
	private String status;
	private String penaltypaymentdate;
	private String penaltytranref;
	private boolean waiver;
	
	public Penalty() {		
	}
	
	@Id
	@Column(name="loanscheduleno")
	public String getLoanscheduleno() {
		return loanscheduleno;
	}
	
	public void setLoanscheduleno(String loanscheduleno) {
		this.loanscheduleno = loanscheduleno;
	}
	
	public String getLoanid() {
		return loanid;
	}
	
	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}
	
	public String getLoanpaymentdate() {
		return loanpaymentdate;
	}
	
	public void setLoanpaymentdate(String loanpaymentdate) {
		this.loanpaymentdate = loanpaymentdate;
	}
	
	public String getPenalty() {
		return penalty;
	}
	
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	
	public String getLoaninstallment() {
		return loaninstallment;
	}
	
	public void setLoaninstallment(String loaninstallment) {
		this.loaninstallment = loaninstallment;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPenaltypaymentdate() {
		return penaltypaymentdate;
	}
	
	public void setPenaltypaymentdate(String penaltypaymentdate) {
		this.penaltypaymentdate = penaltypaymentdate;
	}
	
	public String getPenaltytranref() {
		return penaltytranref;
	}
	
	public void setPenaltytranref(String penaltytranref) {
		this.penaltytranref = penaltytranref;
	}

	public boolean isWaiver() {
		return waiver;
	}

	public void setWaiver(boolean waiver) {
		this.waiver = waiver;
	}
	
	
}