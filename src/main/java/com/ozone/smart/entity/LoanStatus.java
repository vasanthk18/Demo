package com.ozone.smart.entity;

import java.math.BigInteger;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect("SELECT loanid," + 
		"    substring(scheduleno, 1, 8) AS agreementno," + 
		"    count(*) AS overdueinst," + 
		"    installment," + 
		"    sum(principal::double precision) AS principalout" + 
		"   FROM tblpaymentschedule" + 
		"  WHERE to_date(paymentdate, 'dd/mm/yyyy') < CURRENT_DATE " + 
		"  AND status <> 'PAID'" + 
		"  GROUP BY loanid, agreementno, installment"		
		)
public class LoanStatus {
	 @Id
	 private String loanid;
	 private String agreementno;
	 private BigInteger overdueinst;
	 private String installment;
	 private double principalout;
	
	 public String getLoanid() {
		 return loanid;
	 }
	 
	 public String getAgreementno() {
		 return agreementno;
	 }
	 
	 public BigInteger getOverdueinst() {
		 return overdueinst;
	 }
	 
	 public String getInstallment() {
		 return installment;
	 }
	 
	 public double getPrincipalout() {
		 return principalout;
	 }
}