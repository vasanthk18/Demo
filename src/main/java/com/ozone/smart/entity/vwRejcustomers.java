package com.ozone.smart.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect("select otherid, surname, firstname, mobileno, stage, nationalid, capturedatetime, " + 
		"	CASE WHEN (cqc = false and cqcremarks is not null) THEN 'CQC' " + 
		"		WHEN (tvverified = false and tvremarks is not null) THEN 'TV' " + 
		"		WHEN (fiverified = false and firemarks is not null) THEN 'FI' " + 
		"		WHEN (coapproval = 'Not recommended') THEN 'CAM' " + 
		"		WHEN (cooapproval = 'Reverse') THEN 'CAM APPROVAL' " + 
		"	END AS rejectreason, " + 
		"	CASE WHEN (cqc = false and cqcremarks is not null) THEN 'CQC' || '-' || cqc || '-' || cqcremarks " + 
		"		WHEN (tvverified = false and tvremarks is not null) THEN 'TV' || '-' || tvverified || '-' || tvremarks " + 
		"		WHEN (fiverified = false and firemarks is not null) THEN 'FI' || '-' || fiverified || '-' || firemarks " + 
		"		WHEN (coapproval = 'Not recommended') THEN 'CAM' || '-' || coapproval || '-' || coremarks " + 
		"		WHEN (cooapproval = 'Reverse') THEN 'CAM APPROVAL' || '-' || cooapproval || '-' || cooremarks " + 
		"	END AS rejectremarks " + 
		"from tblcustomerdetails where (cqc = false and cqcremarks is not null) or (tvverified = false and tvremarks is not null) " + 
		"or (fiverified = false and firemarks is not null) or coapproval = 'Not recommended' or cooapproval = 'Reverse'"
		)

public class vwRejcustomers {
	 @Id
	 private String otherid;
	 private String surname;
	 private String firstname;
	 private String mobileno;
	 private String stage;
	 private String nationalid;
	 private String capturedatetime;
	 private String rejectreason;
	 private String rejectremarks;
	
	 public String getOtherid() {
		 return otherid;
	 }
	 
	 public String getSurname() {
		 return surname;
	 }
	 
	 public String getFirstname() {
		 return firstname;
	 }
	 
	 public String getMobileno() {
		 return mobileno;
	 }
	 
	 public String getStage() {
		 return stage;
	 }
	 
	 public String getNationalid() {
		 return nationalid;
	 }
	 
	 public String getCapturedatetime() {
		 return capturedatetime;
	 }
	 
	 public String getRejectreason() {
		 return rejectreason;
	 }
	 
	 public String getRejectremarks() {
		 return rejectremarks;
	 }
	 
}