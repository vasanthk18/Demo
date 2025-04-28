package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblpaymentschedule")
public class PaymentSchedule implements Serializable {

	private static final long serialVersionUID = 1L;
	private String scheduleno;
	private String loanid;
	private String paymentdate;
	private String runningamount;
	private String installment;
	private String interestamount;
	private String principal;
	private String balance;
	private String status;
	private int schedule;
	
	public PaymentSchedule() {		
	}
	
	@Id
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
	
	public String getPaymentdate() {
		return paymentdate;
	}
	
	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}
	
	public String getRunningamount() {
		return runningamount;
	}
	
	public void setRunningamount(String runningamount) {
		this.runningamount = runningamount;
	}
	
	public String getInstallment() {
		return installment;
	}
	
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	
	public String getInterestamount() {
		return interestamount;
	}
	
	public void setInterestamount(String interestamount) {
		this.interestamount = interestamount;
	}
	
	public String getPrincipal() {
		return principal;
	}
	
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getBalance() {
		return balance;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getSchedule() {
		return schedule;
	}
	
	public void setSchedule(int schedule) {
		this.schedule = schedule;
	}
}