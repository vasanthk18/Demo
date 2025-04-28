package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbldownpayment")
public class DownPayment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String proposalno;
	private String transactiontype;
	private String transactionref;
	private String amount;
	private String paymentmode;
	private String paymentdate;
//	private String receiptno;
//	private String receiptno;
	private int receiptid;
	private String captureuser;
	private String revuser;
	private boolean revflag;
	private String revdatetime;
	private String downreceipt;
	
	public DownPayment () {}
	
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
	
//	public String getReceiptno() {
//		return receiptno;
//	}
//	
//	public void setReceiptno(String receiptno) {
//		this.receiptno = receiptno;
//	}	
//	public String getReceiptno() {
//		return receiptno;
//	}
//	
//	public void setReceiptno(String receiptno) {
//		this.receiptno = receiptno;
//	}	
	
	public int getReceiptid() {
		return receiptid;
	}
	
	public void setReceiptid(int receiptid) {
		this.receiptid = receiptid;
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

	public String getDownreceipt() {
		return downreceipt;
	}

	public void setDownreceipt(String downreceipt) {
		this.downreceipt = downreceipt;
	}	
	
	}