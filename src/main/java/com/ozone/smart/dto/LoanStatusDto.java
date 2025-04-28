package com.ozone.smart.dto;

import java.math.BigInteger;

public class LoanStatusDto {
	
	private String loanid;
	private String agreementno;
	private BigInteger overdueinst;
	private String installment;
	private double principalout;
	public String getLoanid() {
		return loanid;
	}
	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}
	public String getAgreementno() {
		return agreementno;
	}
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
	}
	public BigInteger getOverdueinst() {
		return overdueinst;
	}
	public void setOverdueinst(BigInteger overdueinst) {
		this.overdueinst = overdueinst;
	}
	public String getInstallment() {
		return installment;
	}
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	public double getPrincipalout() {
		return principalout;
	}
	public void setPrincipalout(double principalout) {
		this.principalout = principalout;
	}
	 
	 

}
