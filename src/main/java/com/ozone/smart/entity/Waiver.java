package com.ozone.smart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "tblWaiver")
public class Waiver {
	
	
	private int id;
	private String proposalNo;
	private int penaltyAmt;
	private String reason;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	public int getPenaltyAmt() {
		return penaltyAmt;
	}
	public void setPenaltyAmt(int penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
