package com.ozone.smart.dto;

public class WaiverDto {
	
	private int id;
	private String proposalNo;
	private int penaltyAmt;
	private String reason;
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
