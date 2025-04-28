package com.ozone.smart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblcollection")
public class Collection {
	
	private int id;
	private String custid;
	private String vehicleregno;
	private String collectionpersonname;
	private String amount;
	private String date;
	private String fiftythousand;
	private String twentythousand;
	private String tenthousand;
	private String fivethousand;
	private String twothousand;
	private String thousand;
	private String fivehundred;
	private String twohundred;
	private String hundred;
	private String coins;
	private Boolean transferstatus;
	private String receiptid;
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getVehicleregno() {
		return vehicleregno;
	}
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
	public String getCollectionpersonname() {
		return collectionpersonname;
	}
	public void setCollectionpersonname(String collectionPersonname) {
		this.collectionpersonname = collectionPersonname;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFiftythousand() {
		return fiftythousand;
	}
	public void setFiftythousand(String fiftythousand) {
		this.fiftythousand = fiftythousand;
	}
	public String getTwentythousand() {
		return twentythousand;
	}
	public void setTwentythousand(String twentythousand) {
		this.twentythousand = twentythousand;
	}
	public String getTenthousand() {
		return tenthousand;
	}
	public void setTenthousand(String tenthousand) {
		this.tenthousand = tenthousand;
	}
	public String getFivethousand() {
		return fivethousand;
	}
	public void setFivethousand(String fivethousand) {
		this.fivethousand = fivethousand;
	}
	public String getTwothousand() {
		return twothousand;
	}
	public void setTwothousand(String twothousand) {
		this.twothousand = twothousand;
	}
	public String getThousand() {
		return thousand;
	}
	public void setThousand(String thousand) {
		this.thousand = thousand;
	}
	public String getFivehundred() {
		return fivehundred;
	}
	public void setFivehundred(String fivehundred) {
		this.fivehundred = fivehundred;
	}
	public String getTwohundred() {
		return twohundred;
	}
	public void setTwohundred(String twohundred) {
		this.twohundred = twohundred;
	}
	public String getHundred() {
		return hundred;
	}
	public void setHundred(String hundred) {
		this.hundred = hundred;
	}
	public String getCoins() {
		return coins;
	}
	public void setCoins(String coins) {
		this.coins = coins;
	}
	public Boolean getTransferstatus() {
		return transferstatus;
	}
	public void setTransferstatus(Boolean transferstatus) {
		this.transferstatus = transferstatus;
	}
	public String getReceiptid() {
		return receiptid;
	}
	public void setReceiptid(String receiptid) {
		this.receiptid = receiptid;
	}
	
	
	

}
