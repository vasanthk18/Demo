package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblloan")
public class Loan implements Serializable {

	private static final long serialVersionUID = 1L;
	private String proposalno;
	private String custid;
	private String firstguarantorid;
	private String secondguarantorid;
	private String loanamount;
	private String interestrate;
	private String paymentmode;
	private String ewi;
	private String noofinstallments;
	private String downpayment;
	private String transferfee;
	private String insurancefee;
	private String trackerfee;
	private int verify;
	private int scheduled;
	private boolean cqc;
	private String cqcremarks;
	private String coapproval;
	private String coremarks;
	private String cooapproval;
	private String cooremarks; 
	private String ceoapproval;
	private String ceoremarks;
	private String srremarks;
	
	private String revremarks;
	private String tvdatetime;
	private String 	tvuser;
	private String fidatetime;
	private String fiuser;
	private String cqcdatetime;
	private String cqcuser;
	private String camdatetime;
	private String camuser;
	private String camadatetime;
	private String camauser;
	private String ceodatetime;
	private String ceouser;
	
	private String captureuser;
	private String capturedatetime;
	
	private boolean impounded;
	private boolean reposessed;
	private boolean auctioned;
	private boolean preclosed;
	private boolean normalclosure;
	
	private String closureuser;
	private String closuredatetime;
	
	public Loan() {		
	}
	
	@Id
	@Column(name="proposalno")
	public String getProposalno() {
		return proposalno;
	}
	
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	
	public String getCustid() {
		return custid;
	}
	
	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	public String getFirstguarantorid() {
		return firstguarantorid;
	}
	
	public void setFirstguarantorid(String firstguarantorid) {
		this.firstguarantorid = firstguarantorid;
	}
	
	public String getSecondguarantorid() {
		return secondguarantorid;
	}
	
	public void setSecondguarantorid(String secondguarantorid) {
		this.secondguarantorid = secondguarantorid;
	}
	
	public String getLoanamount() {
		return loanamount;
	}
	
	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}
	
	public String getInterestrate() {
		return interestrate;
	}
	
	public void setInterestrate(String interestrate) {
		this.interestrate = interestrate;
	}
	
	public String getPaymentmode() {
		return paymentmode;
	}
	
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	
	public String getEwi() {
		return ewi;
	}
	
	public void setEwi(String ewi) {
		this.ewi = ewi;
	}
	
	public String getNoofinstallments() {
		return noofinstallments;
	}
	
	public void setNoofinstallments(String noofinstallments) {
		this.noofinstallments = noofinstallments;
	}
	
	public String getDownpayment() {
		return downpayment;
	}
	
	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}
	
	public String getTransferfee() {
		return transferfee;
	}
	
	public void setTransferfee(String transferfee) {
		this.transferfee = transferfee;
	}
	
	public String getInsurancefee() {
		return insurancefee;
	}
	
	public void setInsurancefee(String insurancefee) {
		this.insurancefee = insurancefee;
	}
	
	public String getTrackerfee() {
		return trackerfee;
	}
	
	public void setTrackerfee(String trackerfee) {
		this.trackerfee = trackerfee;
	}
	
	public int getVerify() {
		return verify;
	}
	
	public void setVerify(int verify) {
		this.verify = verify;
	}

	public int getScheduled() {
		return scheduled;
	}
	
	public void setScheduled(int scheduled) {
		this.scheduled = scheduled;
	}
	
	public boolean getCqc() {
		return cqc;
	}
	
	public void setCqc(boolean cqc) {
		this.cqc = cqc;
	}
	
	public String getCqcremarks() {
		return cqcremarks;
	}
	
	public void setCqcremarks(String cqcremarks) {
		this.cqcremarks = cqcremarks;
	}
	
	public String getCoapproval() {
		return coapproval;
	}
	
	public void setCoapproval(String coapproval) {
		this.coapproval = coapproval;
	}
	
	public String getCoremarks() {
		return coremarks;
	}
	
	public void setCoremarks(String coremarks) {
		this.coremarks = coremarks;
	}
	
	public String getCooapproval() {
		return cooapproval;
	}
	
	public void setCooapproval(String cooapproval) {
		this.cooapproval = cooapproval;
	}
	
	public String getcooremarks() {
		return cooremarks;
	}
	
	public void setcooremarks(String cooremarks) {
		this.cooremarks = cooremarks;
	}
	
	public String getCeoapproval() {
		return ceoapproval;
	}
	
	public void setCeoapproval(String ceoapproval) {
		this.ceoapproval = ceoapproval;
	}
	
	public String getCeoremarks() {
		return ceoremarks;
	}
	
	public void setCeoremarks(String ceoremarks) {
		this.ceoremarks = ceoremarks;
	}
	
	public String getSrremarks() {
		return srremarks;
	}
	
	public void setSrremarks(String srremarks) {
		this.srremarks = srremarks;
	}
	
	public String getRevremarks() {
		return revremarks;
	}
	
	public void setRevremarks(String revremarks) {
		this.revremarks = revremarks;
	}
	
	public String getTvdatetime() {
		return tvdatetime;
	}
	
	public void setTvdatetime(String tvdatetime) {
		this.tvdatetime = tvdatetime;
	}
	
	public String getTvuser() {
		return tvuser;
	}
	
	public void setTvuser(String tvuser) {
		this.tvuser = tvuser;
	}
	
	public String getFidatetime() {
		return fidatetime;
	}
	
	public void setFidatetime(String fidatetime) {
		this.fidatetime = fidatetime;
	}
	
	public String getFiuser() {
		return fiuser;
	}
	
	public void setFiuser(String fiuser) {
		this.fiuser = fiuser;
	}
	
	public String getCqcdatetime() {
		return cqcdatetime;
	}
	
	public void setCqcdatetime(String cqcdatetime) {
		this.cqcdatetime = cqcdatetime;
	}
	
	public String getCqcuser() {
		return cqcuser;
	}
	
	public void setCqcuser(String cqcuser) {
		this.cqcuser = cqcuser;
	}
	
	public String getCamdatetime() {
		return camdatetime;
	}
	
	public void setCamdatetime(String camdatetime) {
		this.camdatetime = camdatetime;
	}
	
	public String getCamuser() {
		return camuser;
	}
	
	public void setCamuser(String camuser) {
		this.camuser = camuser;
	}
	
	public String getCamadatetime() {
		return camadatetime;
	}
	
	public void setCamadatetime(String camadatetime) {
		this.camadatetime = camadatetime;
	}
	
	public String getCamauser() {
		return camauser;
	}
	
	public void setCamauser(String camauser) {
		this.camauser = camauser;
	}
	
	public String getCeodatetime() {
		return ceodatetime;
	}
	
	public void setCeodatetime(String ceodatetime) {
		this.ceodatetime = ceodatetime;
	}
	
	public String getCeouser() {
		return ceouser;
	}
	
	public void setCeouser(String ceouser) {
		this.ceouser = ceouser;
	}	
	
	public String getCaptureuser() {
		return captureuser;
	}
	
	public void setCaptureuser(String captureuser) {
		this.captureuser = captureuser;
	}
	
	public String getCapturedatetime() {
		return capturedatetime;
	}
	
	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}
	
	public boolean getImpounded() {
		return impounded;
	}
	
	public void setImpounded(boolean impounded) {
		this.impounded = impounded;
	}	
	
	public boolean getReposessed() {
		return reposessed;
	}
	
	public void setReposessed(boolean reposessed) {
		this.reposessed = reposessed;
	}
	
	public boolean getAuctioned() {
		return auctioned;
	}
	
	public void setAuctioned(boolean auctioned) {
		this.auctioned = auctioned;
	}
	
	public boolean getPreclosed() {
		return preclosed;
	}
	
	public void setPreclosed(boolean preclosed) {
		this.preclosed = preclosed;
	}	
	
	public boolean getNormalclosure() {
		return normalclosure;
	}
	
	public void setNormalclosure(boolean normalclosure) {
		this.normalclosure = normalclosure;
	}
	
	public String getClosureuser() {
		return closureuser;
	}
	
	public void setClosureuser(String closureuser) {
		this.closureuser = closureuser;
	}
	
	public String getClosuredatetime() {
		return closuredatetime;
	}
	
	public void setClosuredatetime(String closuredatetime) {
		this.closuredatetime = closuredatetime;
	}

}