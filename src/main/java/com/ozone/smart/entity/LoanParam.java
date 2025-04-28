package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblloanparam")
public class LoanParam implements Serializable {


	private static final long serialVersionUID = 1L;
	private String paramid;
	private String transferfee;
	private String irr;
	private String trackerfee;
	private String paymentmode;
	private String marginmoney;
	private String latepayment;
	private String impound;
	private String latepaymentmonthly;

	public LoanParam() {
	}
	
	@Id
	@Column(name="paramid")
	public String getParamid() {
		return paramid;
	}
	
	public void setParamid(String paramid) {
		this.paramid = paramid;
	}
	
	public String getTransferfee() {
		return transferfee;
	}
	
	public void setTransferfee(String transferfee) {
		this.transferfee = transferfee;
	}
	
	public String getIrr() {
		return irr;
	}
	
	public void setIrr(String irr) {
		this.irr = irr;
	}
	
	public String getTrackerfee() {
		return trackerfee;
	}
	
	public void setTrackerfee(String trackerfee) {
		this.trackerfee = trackerfee;
	}
	
	public String getPaymentmode() {
		return paymentmode;
	}
	
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	
	public String getMarginmoney() {
		return marginmoney;
	}
	
	public void setMarginmoney(String marginmoney) {
		this.marginmoney = marginmoney;
	}

	public String getLatepayment() {
		return latepayment;
	}
	
	public void setLatepayment(String latepayment) {
		this.latepayment = latepayment;
	}
	
	public String getLatepaymentmonthly() {
		return latepaymentmonthly;
	}
	
	public void setLatepaymentmonthly(String latepaymentmonthly) {
		this.latepaymentmonthly = latepaymentmonthly;
	}
	
	public String getImpound() {
		return impound;
	}
	
	public void setImpound(String impound) {
		this.impound = impound;
	}
}
