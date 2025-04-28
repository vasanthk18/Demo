package com.ozone.smart.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblloanpayments")
public class LoanPayments implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String scheduleno;
	private String loanid;
	private Date rundate;
	private String amount;
	private int schedule;
	private String paymenttype;
	
	public LoanPayments() {		
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="scheduleno")
	public String getScheduleno() {
		return scheduleno;
	}

	public void setScheduleno(String scheduleno) {
		this.scheduleno = scheduleno;
	}
	
	public String getLoanid() {
		return loanid;
	}
	
	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}
	
	public Date getRundate() {
		return rundate;
	}
	
	public void setRundate(Date rundate) {
		this.rundate = rundate;
	}
		
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
		
	public int getSchedule() {
		return schedule;
	}
	
	public void setSchedule(int schedule) {
		this.schedule = schedule;
	}
	
	public String getPaymenttype() {
		return paymenttype;
	}
	
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
}