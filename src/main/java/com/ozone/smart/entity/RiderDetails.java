package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblriderdetails")

public class RiderDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String otherid;
	private String proposalid;
	private String nationalid;
	private String dob;
	private String firstname;
	private String maritalstatus;
	private String mobileno;
	private String stage;
	private String stagechairman;
	private String stagechairmanno;
	private String currentbikeregno;
	private String newbikeuse;
	private String ownhouserented;
	private String landlordname;
	private String landlordmobileno;
	private String rentpm;
	private String otherincomesource;
	private String permanentaddress;
	private String nearbypolicestation;
	private String lc;
	private String lcmobileno;
	private String llrentfeedback;
	private String noyrsinarea;
	private String lc1chmnrecfeed;
	private String nearlmarkresi;
	private String emptype;
	private String stgorwrkadrssnearlmark;
	private String stgoremprecm;
	private String noofyrsinstgorbusi;
	private String stgnoofvehi;
	private String bikeowner;
	private String netincome;
	private String bikeusearea;
	private String spousename;
	private String spouseno;
	private String spouseconfirm;
	private String relawithapplicant;
	private String paymentbyrider;
	private String arrangebtwnrider;
	private String resiadrss;
	private String noofyrinaddrss;
	private String mbnonotinname;

	private String capturedatetime;
    @Column(name = "cqc", nullable = false)
	private Boolean cqc =false;
	private String cqcremarks;
	private String cqcuser;
	private String cqcdatetime;

	private Boolean tvverified = false;
	private Boolean fiverified = false;
	private String tvremarks;
	private String firemarks;
	private String tvdatetime;
	private String tvuser;
	private String fidatetime;
	private String fiuser;
	
	private String coapproval;
	private String cooapproval;
	private String coremarks;
	private String cooremarks;
	private String camuser;
	private String camauser;
	private String camdatetime;
	private String camadatetime;
	private String cocamremarks;
	private String srremarks;
	private String revremarks;

	public RiderDetails() {

	}

	
	public String getOtherid() {
		return otherid;
	}

	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}

	@Id
	@Column(name = "proposalid", nullable = false)
	public String getProposalid() {
		return proposalid;
	}

	public void setProposalid(String proposalid) {
		this.proposalid = proposalid;
	}

	public String getNationalid() {
		return nationalid;
	}

	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStagechairman() {
		return stagechairman;
	}

	public void setStagechairman(String stagechairman) {
		this.stagechairman = stagechairman;
	}

	public String getStagechairmanno() {
		return stagechairmanno;
	}

	public void setStagechairmanno(String stagechairmanno) {
		this.stagechairmanno = stagechairmanno;
	}

	public String getCurrentbikeregno() {
		return currentbikeregno;
	}

	public void setCurrentbikeregno(String currentbikeregno) {
		this.currentbikeregno = currentbikeregno;
	}

	public String getNewbikeuse() {
		return newbikeuse;
	}

	public void setNewbikeuse(String newbikeuse) {
		this.newbikeuse = newbikeuse;
	}

	public String getOwnhouserented() {
		return ownhouserented;
	}

	public void setOwnhouserented(String ownhouserented) {
		this.ownhouserented = ownhouserented;
	}

	public String getLandlordname() {
		return landlordname;
	}

	public void setLandlordname(String landlordname) {
		this.landlordname = landlordname;
	}

	public String getLandlordmobileno() {
		return landlordmobileno;
	}

	public void setLandlordmobileno(String landlordmobileno) {
		this.landlordmobileno = landlordmobileno;
	}

	public String getRentpm() {
		return rentpm;
	}

	public void setRentpm(String rentpm) {
		this.rentpm = rentpm;
	}

	public String getOtherincomesource() {
		return otherincomesource;
	}

	public void setOtherincomesource(String otherincomesource) {
		this.otherincomesource = otherincomesource;
	}

	public String getPermanentaddress() {
		return permanentaddress;
	}

	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}

	public String getNearbypolicestation() {
		return nearbypolicestation;
	}

	public void setNearbypolicestation(String nearbypolicestation) {
		this.nearbypolicestation = nearbypolicestation;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getLcmobileno() {
		return lcmobileno;
	}

	public void setLcmobileno(String lcmobileno) {
		this.lcmobileno = lcmobileno;
	}

	public String getLlrentfeedback() {
		return llrentfeedback;
	}

	public void setLlrentfeedback(String llrentfeedback) {
		this.llrentfeedback = llrentfeedback;
	}

	public String getNoyrsinarea() {
		return noyrsinarea;
	}

	public void setNoyrsinarea(String noyrsinarea) {
		this.noyrsinarea = noyrsinarea;
	}

	public String getLc1chmnrecfeed() {
		return lc1chmnrecfeed;
	}

	public void setLc1chmnrecfeed(String lc1chmnrecfeed) {
		this.lc1chmnrecfeed = lc1chmnrecfeed;
	}

	public String getNearlmarkresi() {
		return nearlmarkresi;
	}

	public void setNearlmarkresi(String nearlmarkresi) {
		this.nearlmarkresi = nearlmarkresi;
	}

	public String getEmptype() {
		return emptype;
	}

	public void setEmptype(String emptype) {
		this.emptype = emptype;
	}

	public String getStgorwrkadrssnearlmark() {
		return stgorwrkadrssnearlmark;
	}

	public void setStgorwrkadrssnearlmark(String stgorwrkadrssnearlmark) {
		this.stgorwrkadrssnearlmark = stgorwrkadrssnearlmark;
	}

	public String getStgoremprecm() {
		return stgoremprecm;
	}

	public void setStgoremprecm(String stgoremprecm) {
		this.stgoremprecm = stgoremprecm;
	}

	public String getNoofyrsinstgorbusi() {
		return noofyrsinstgorbusi;
	}

	public void setNoofyrsinstgorbusi(String noofyrsinstgorbusi) {
		this.noofyrsinstgorbusi = noofyrsinstgorbusi;
	}

	public String getStgnoofvehi() {
		return stgnoofvehi;
	}

	public void setStgnoofvehi(String stgnoofvehi) {
		this.stgnoofvehi = stgnoofvehi;
	}

	public String getBikeowner() {
		return bikeowner;
	}

	public void setBikeowner(String bikeowner) {
		this.bikeowner = bikeowner;
	}

	public String getNetincome() {
		return netincome;
	}

	public void setNetincome(String netincome) {
		this.netincome = netincome;
	}

	public String getBikeusearea() {
		return bikeusearea;
	}

	public void setBikeusearea(String bikeusearea) {
		this.bikeusearea = bikeusearea;
	}

	public String getSpousename() {
		return spousename;
	}

	public void setSpousename(String spousename) {
		this.spousename = spousename;
	}

	public String getSpouseno() {
		return spouseno;
	}

	public void setSpouseno(String spouseno) {
		this.spouseno = spouseno;
	}

	public String getSpouseconfirm() {
		return spouseconfirm;
	}

	public void setSpouseconfirm(String spouseconfirm) {
		this.spouseconfirm = spouseconfirm;
	}

	public String getRelawithapplicant() {
		return relawithapplicant;
	}

	public void setRelawithapplicant(String relawithapplicant) {
		this.relawithapplicant = relawithapplicant;
	}

	public String getPaymentbyrider() {
		return paymentbyrider;
	}

	public void setPaymentbyrider(String paymentbyrider) {
		this.paymentbyrider = paymentbyrider;
	}

	public String getArrangebtwnrider() {
		return arrangebtwnrider;
	}

	public void setArrangebtwnrider(String arrangebtwnrider) {
		this.arrangebtwnrider = arrangebtwnrider;
	}

	public String getResiadrss() {
		return resiadrss;
	}

	public void setResiadrss(String resiadrss) {
		this.resiadrss = resiadrss;
	}

	public String getNoofyrinaddrss() {
		return noofyrinaddrss;
	}

	public void setNoofyrinaddrss(String noofyrinaddrss) {
		this.noofyrinaddrss = noofyrinaddrss;
	}

	public String getMbnonotinname() {
		return mbnonotinname;
	}

	public void setMbnonotinname(String mbnonotinname) {
		this.mbnonotinname = mbnonotinname;
	}

	public String getCapturedatetime() {
		return capturedatetime;
	}

	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}

	public Boolean isCqc() {
		return cqc;
	}

	public void setCqc(Boolean cqc) {
		this.cqc = cqc;
	}

	public String getCqcremarks() {
		return cqcremarks;
	}

	public void setCqcremarks(String cqcremarks) {
		this.cqcremarks = cqcremarks;
	}

	public String getCqcuser() {
		return cqcuser;
	}

	public void setCqcuser(String cqcuser) {
		this.cqcuser = cqcuser;
	}

	public String getCqcdatetime() {
		return cqcdatetime;
	}

	public void setCqcdatetime(String cqcdatetime) {
		this.cqcdatetime = cqcdatetime;
	}

	

	public String getTvremarks() {
		return tvremarks;
	}

	public void setTvremarks(String tvremarks) {
		this.tvremarks = tvremarks;
	}

	public String getFiremarks() {
		return firemarks;
	}

	public void setFiremarks(String firemarks) {
		this.firemarks = firemarks;
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

	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
	public Boolean getTvverified() {
		return tvverified;
	}

	public void setTvverified(Boolean tvverified) {
		this.tvverified = tvverified;
	}

	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
	public Boolean getFiverified() {
		return fiverified;
	}

	public void setFiverified(Boolean fiverified) {
		this.fiverified = fiverified;
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


	public String getCamuser() {
		return camuser;
	}


	public void setCamuser(String camuser) {
		this.camuser = camuser;
	}


	public String getCamdatetime() {
		return camdatetime;
	}


	public void setCamdatetime(String camdatetime) {
		this.camdatetime = camdatetime;
	}


	public String getCocamremarks() {
		return cocamremarks;
	}


	public void setCocamremarks(String cocamremarks) {
		this.cocamremarks = cocamremarks;
	}


	public String getSrremarks() {
		return srremarks;
	}


	public void setSrremarks(String srremarks) {
		this.srremarks = srremarks;
	}


	public String getCooapproval() {
		return cooapproval;
	}


	public void setCooapproval(String cooapproval) {
		this.cooapproval = cooapproval;
	}


	public String getCooremarks() {
		return cooremarks;
	}


	public void setCooremarks(String cooremarks) {
		this.cooremarks = cooremarks;
	}


	public String getCamauser() {
		return camauser;
	}


	public void setCamauser(String camauser) {
		this.camauser = camauser;
	}


	public String getCamadatetime() {
		return camadatetime;
	}


	public void setCamadatetime(String camadatetime) {
		this.camadatetime = camadatetime;
	}


	public String getRevremarks() {
		return revremarks;
	}


	public void setRevremarks(String revremarks) {
		this.revremarks = revremarks;
	}
	
	

}
