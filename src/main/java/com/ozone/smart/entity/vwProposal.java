package com.ozone.smart.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect("select a.proposalno, 'AN' || b.agreementserial as agreementno, a.customerid, b.vehicleregno, a.vehicleregno as brand, c.dealer, " + 
		"to_date(b.disbdatetime, 'dd/mm/yyyy') as dateofrelease, to_char(to_date(b.disbdatetime, 'dd/mm/yyyy'), 'Day') as paymentday, " +
		"to_char(to_date(b.disbdatetime, 'dd/mm/yyyy'), 'Mon-yy') AS monthyear, c.simcardno, c.trackingno, " + 
		"d.trackerfee, d.loanamount, d.downpayment, d.interestrate, d.ewi, d.noofinstallments, d.paymentmode, a.discount " + 
		"from tblproposal a " + 
		"LEFT JOIN tblcustomervehicle b ON b.proposalno = a.proposalno " + 
		"LEFT JOIN tblvehicle c ON b.vehicleregno = c.regno " + 
		"LEFT JOIN tblloan d ON b.proposalno = d.proposalno " + 
		"order by a.proposalno"
		)

public class vwProposal {
	 @Id	 
	 private String proposalno;
	 private String agreementno;
	 private String customerid;
	 private String vehicleregno;
	 private String brand;
	 private String dealer;
	 private String dateofrelease;
	 private String paymentday;
	 private String monthyear;
	 private String simcardno;
	 private String trackingno;
	 private String trackerfee;
	 private String loanamount;
	 private String downpayment;
	 private String interestrate;
	 private String ewi;
	 private String noofinstallments;
	 private String paymentmode;
	 private String discount;
	
	 public String getCustomerid() {
		 return customerid;
	 }
	 
	 public String getProposalno() {
		 return proposalno;
	 }
	 
	 public String getAgreementno() {
		 return agreementno;
	 }
	 
	 public String getVehicleregno() {
		 return vehicleregno;
	 }
	 
	 public String getBrand() {
		 return brand;
	 }
	 
	 public String getDealer() {
		 return dealer;
	 }
	 
	 public String getDateofrelease() {
		 return dateofrelease;
	 }
	 
	 public String getPaymentday() {
		 return paymentday;
	 }
	 
	 public String getMonthyear() {
		 return monthyear;
	 }
	 
	 public String getSimcardno() {
		 return simcardno;
	 }
	 
	 public String getTrackingno() {
		 return trackingno;
	 }
	 
	 public String getTrackerfee() {
		 return trackerfee;
	 }
	 
	 public String getLoanamount() {
		 return loanamount;
	 }
	 
	 public String getDownpayment() {
		 return downpayment;
	 }
	 
	 public String getInterestrate() {
		 return interestrate;
	 }
	 
	 public String getEwi() {
		 return ewi;
	 }
	 
	 public String getNoofinstallments() {
		 return noofinstallments;
	 }
	 
	 public String getPaymentmode() {
		 return paymentmode;
	 }
	 
	 public String getDiscount() {
		 return discount;
	 }
	 
}