package com.ozone.smart.entity;

public class CustDataRecord {
	
	 private String date;
	 private String amount;
	 private String transactionType; // EWI or PEN for Penalty
	 private boolean isPenalty;
	 
	public CustDataRecord(String date, String amount, String transactionType, boolean isPenalty) {
		super();
		this.date = date;
		this.amount = amount;
		this.transactionType = transactionType;
		this.isPenalty = isPenalty;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public boolean isPenalty() {
		return isPenalty;
	}
	public void setPenalty(boolean isPenalty) {
		this.isPenalty = isPenalty;
	}

}
