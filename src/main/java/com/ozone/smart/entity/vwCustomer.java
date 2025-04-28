package com.ozone.smart.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect("select a.otherid, to_date(a.capturedatetime, 'dd/mm/yyyy') as capturedate, to_char(to_date(a.capturedatetime, 'dd/mm/yyyy'), 'Mon-yy') as monthYear, " +
		"a.surname || ' ' || a.firstname as customername, a.custtype as profile, a.stage, b.chairman, b.mobileno as chairmanmobileno, " +
		"a.parish || ',' || a.village as address, a.mobileno from tblcustomerdetails a LEFT OUTER JOIN tblstage b ON a.stage = b.name"
		)

public class vwCustomer {
	 @Id
	 private String otherid;
	 private String capturedate;
	 private String monthyear;
	 private String customername;
	 private String profile;
	 private String stage;
	 private String chairman;
	 private String chairmanmobileno;
	 private String address;
	 private String mobileno;
	
	 public String getOtherid() {
		 return otherid;
	 }
	 
	 public String getCapturedate() {
		 return capturedate;
	 }
	 
	 public String getMonthyear() {
		 return monthyear;
	 }
	 
	 public String getCustomername() {
		 return customername;
	 }
	 
	 public String getProfile() {
		 return profile;
	 }
	 
	 public String getStage() {
		 return stage;
	 }
	 
	 public String getChairman() {
		 return chairman;
	 }
	 
	 public String getChairmanmobileno() {
		 return chairmanmobileno;
	 }
	 
	 public String getAddress() {
		 return address;
	 }
	 
	 public String getMobileno() {
		 return mobileno;
	 }
}