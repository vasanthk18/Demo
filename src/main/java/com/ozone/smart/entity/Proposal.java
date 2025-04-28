package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblproposal")
public class Proposal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String proposalno;
	private String customerid;
	private String vehicleregno;
	private String amount;
	private String downamount;
	private String discount;
	private String receiptno;
	private String downpaydate;
	private String capturedatetime;
	private boolean cqc;
	private String cqcremarks;
	private String coapproval;
	private String coremarks;
	private String cooapproval;
	private String cooremarks; 
	private String ceoapproval;
	private String ceoremarks; 
	private String srremarks;
	
	private String revremarks;
	private String tvdatetime;
	private String 	tvuser;
	private String fidatetime;
	private String fiuser;
	private String cqcdatetime;
	private String cqcuser;
	private String camdatetime;
	private String camuser;
	private String camadatetime;
	private String camauser;
	private String ceodatetime;
	private String ceouser;
	private String captureuser;
	
	public Proposal(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false, updatable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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
	
	public String getVehicleregno() {
		return vehicleregno;
	}
	
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDownamount() {
		return downamount;
	}
	
	public void setDownamount(String downamount) {
		this.downamount = downamount;
	}
	
	public String getDiscount() {
		return discount;
	}
	
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getReceiptno() {
		return receiptno;
	}
	
	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}
	
	public String getDownpaydate() {
		return downpaydate;
	}
	
	public void setDownpaydate(String downpaydate) {
		this.downpaydate = downpaydate;
	}
	
	public String getCapturedatetime() {
		return capturedatetime;
	}
	
	public void setCapturedatetime (String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}
	
	public boolean getCqc() {
		return cqc;
	}
	
	public void setCqc(boolean cqc) {
		this.cqc = cqc;
	}
	
	public String getCqcremarks() {
		return cqcremarks;
	}
	
	public void setCqcremarks(String cqcremarks) {
		this.cqcremarks = cqcremarks;
	}
	
	public String getCoapproval() {
		return coapproval;
	}
	
	public void setCoapproval(String coapproval) {
		this.coapproval = coapproval;
	}
	
	public String getCoremarks() {
		return coremarks;
	}
	
	public void setCoremarks(String coremarks) {
		this.coremarks = coremarks;
	}
	
	public String getCooapproval() {
		return cooapproval;
	}
	
	public void setCooapproval(String cooapproval) {
		this.cooapproval = cooapproval;
	}
	
	public String getcooremarks() {
		return cooremarks;
	}
	
	public void setcooremarks(String cooremarks) {
		this.cooremarks = cooremarks;
	}
	
	public String getCeoapproval() {
		return ceoapproval;
	}
	
	public void setCeoapproval(String ceoapproval) {
		this.ceoapproval = ceoapproval;
	}
	
	public String getCeoremarks() {
		return ceoremarks;
	}
	
	public void setCeoremarks(String ceoremarks) {
		this.ceoremarks = ceoremarks;
	}
	
	public String getSrremarks() {
		return srremarks;
	}
	
	public void setSrremarks(String srremarks) {
		this.srremarks = srremarks;
	}
	
	public String getRevremarks() {
		return revremarks;
	}
	
	public void setRevremarks(String revremarks) {
		this.revremarks = revremarks;
	}
	
	public String getTvdatetime() {
		return tvdatetime;
	}
	
	public void setTvdatetime(String tvdatetime) {
		this.tvdatetime = tvdatetime;
	}
	
	public String getTvuser() {
		return tvuser;
	}
	
	public void setTvuser(String tvuser) {
		this.tvuser = tvuser;
	}
	
	public String getFidatetime() {
		return fidatetime;
	}
	
	public void setFidatetime(String fidatetime) {
		this.fidatetime = fidatetime;
	}
	
	public String getFiuser() {
		return fiuser;
	}
	
	public void setFiuser(String fiuser) {
		this.fiuser = fiuser;
	}
	
	public String getCqcdatetime() {
		return cqcdatetime;
	}
	
	public void setCqcdatetime(String cqcdatetime) {
		this.cqcdatetime = cqcdatetime;
	}
	
	public String getCqcuser() {
		return cqcuser;
	}
	
	public void setCqcuser(String cqcuser) {
		this.cqcuser = cqcuser;
	}
	
	public String getCamdatetime() {
		return camdatetime;
	}
	
	public void setCamdatetime(String camdatetime) {
		this.camdatetime = camdatetime;
	}
	
	public String getCamuser() {
		return camuser;
	}
	
	public void setCamuser(String camuser) {
		this.camuser = camuser;
	}
	
	public String getCamadatetime() {
		return camadatetime;
	}
	
	public void setCamadatetime(String camadatetime) {
		this.camadatetime = camadatetime;
	}
	
	public String getCamauser() {
		return camauser;
	}
	
	public void setCamauser(String camauser) {
		this.camauser = camauser;
	}
	
	public String getCeodatetime() {
		return ceodatetime;
	}
	
	public void setCeodatetime(String ceodatetime) {
		this.ceodatetime = ceodatetime;
	}
	
	public String getCeouser() {
		return ceouser;
	}
	
	public void setCeouser(String ceouser) {
		this.ceouser = ceouser;
	}

	public String getCaptureuser() {
		return captureuser;
	}
	
	public void setCaptureuser(String captureuser) {
		this.captureuser = captureuser;
	}
}
