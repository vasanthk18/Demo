package com.ozone.smart.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect("select custid, " + 
		"	MAX(CASE WHEN firstguarantor = true THEN surname || ' ' || firstname || '/' || mobileno END) AS firstguaran, " + 
		"	MAX(CASE WHEN secondguarantor = true THEN surname || ' ' || firstname || '/' || mobileno END) AS secondguaran " + 
		"from " + 
		"tblguarantor " + 
		"group by " + 
		"custid"
		)

public class vwGuarantors {
	 @Id
	 private String custid;
	 private String firstguaran;
	 private String secondguaran;
	
	 public String getCustid() {
		 return custid;
	 }
	 
	 public String getFirstguaran() {
		 return firstguaran;
	 }
	 
	 public String getSecondguaran() {
		 return secondguaran;
	 }
	 
}