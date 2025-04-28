package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblauctionsale")
public class Auctionsale implements Serializable {


	private static final long serialVersionUID = 1L;
	private String agreementno;
	private String vehicle;
	private String impounddate;
	private String overdue;
	private String penalty;
	private String impoundcharges;
	private String principalout;
	private String assetcost;	
	private String buyername;	
	private String mobileno;
	private String tin;
	private String address;
	private String saleamount;
	private String profit;
	private String vatonsale;	
	private String aucuser;
	private String aucdatetime;
	
	public Auctionsale() {
	}
	
	@Id
	@Column(name="agreementno")
	public String getAgreementno() {
		return agreementno;
	}
	
	public void setAgreementno(String agreementno) {
		this.agreementno = agreementno;
	}
	
	public String getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
	public String getImpounddate() {
		return impounddate;
	}
	
	public void setImpounddate(String impounddate) {
		this.impounddate = impounddate;
	}
	
	public String getOverdue() {
		return overdue;
	}
	
	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	
	public String getPenalty() {
		return penalty;
	}
	
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	
	public String getImpoundcharges() {
		return impoundcharges;
	}
	
	public void setImpoundcharges(String impoundcharges) {
		this.impoundcharges = impoundcharges;
	}
	
	public String getPrincipalout() {
		return principalout;
	}
	
	public void setPrincipalout(String principalout) {
		this.principalout = principalout;
	}
	
	public String getAssetcost() {
		return assetcost;
	}
	
	public void setAssetcost(String assetcost) {
		this.assetcost = assetcost;
	}
		
	public String getBuyername() {
		return buyername;
	}
	
	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}
	
	public String getMobileno() {
		return mobileno;
	}
	
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	public String getTin() {
		return tin;
	}
	
	public void setTin(String tin) {
		this.tin = tin;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSaleamount() {
		return saleamount;
	}
	
	public void setSaleamount(String saleamount) {
		this.saleamount = saleamount;
	}

	public String getProfit() {
		return profit;
	}
	
	public void setProfit(String profit) {
		this.profit = profit;
	}
	
	public String getVatonsale() {
		return vatonsale;
	}
	
	public void setVatonsale(String vatonsale) {
		this.vatonsale = vatonsale;
	}
	
	public String getAucuser() {
		return aucuser;
	}
	
	public void setAucuser(String aucuser) {
		this.aucuser = aucuser;
	}
	
	public String getAucdatetime() {
		return aucdatetime;
	}
	
	public void setAucdatetime(String aucdatetime) {
		this.aucdatetime = aucdatetime;
	}
}
