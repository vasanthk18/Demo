package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblweeklyinstallment")
public class WeeklyInstallment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String proposalno;
	private String vehicleregno;
	private String transactiontype;
	private String transactionref;
	private String amount;
	private String paymentmode;
	private String paymentdate;
	
//	@Column(name="receiptno")
//	private String receiptno ;
	
	private String captureuser;
	private String revuser;
	private boolean revflag;
	private String revdatetime;
	private String weekreceipt;
	
	@Column(name="receiptid", insertable = false, updatable = false)
	@Access(AccessType.FIELD)
	private int receiptid;
	
	public WeeklyInstallment () {}
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
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
	
	public String getVehicleregno() {
		return vehicleregno;
	}
	
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
	
	public String getTransactiontype() {
		return transactiontype;
	}
	
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	
	public String getTransactionref() {
		return transactionref;
	}
	
	public void setTransactionref(String transactionref) {
		this.transactionref = transactionref;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getPaymentmode() {
		return paymentmode;
	}
	
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	
	public String getPaymentdate() {
		return paymentdate;
	}
	
	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}
	
	
	
	public String getCaptureuser() {
		return captureuser;
	}
	
	public void setCaptureuser(String captureuser) {
		this.captureuser = captureuser;
	}
	
	public String getRevuser() {
		return revuser;
	}
	
	public void setRevuser(String revuser) {
		this.revuser = revuser;
	}
	
	public boolean getRevflag() {
		return revflag;
	}
	
	public void setRevflag(boolean revflag) {
		this.revflag = revflag;
	}
	
	public String getRevdatetime() {
		return revdatetime;
	}
	
	public void setRevdatetime(String revdatetime) {
		this.revdatetime = revdatetime;
	}

	public String getWeekreceipt() {
		return weekreceipt;
	}

	public void setWeekreceipt(String weekReceipt) {
		this.weekreceipt = weekReceipt;
	}

	public int getReceiptid() {
		return receiptid;
	}

//	public void setReceiptid(String receiptid) {
//		this.receiptid = receiptid;
//	}	
	
	
	
	
}