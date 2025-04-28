package com.ozone.smart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
public class CustomerDetailsDto {
	
	private int id;
	private String surname;
	private String firstname;
	private String othername;
	private String dob;
	private String capturedatetime;
	private String Remarks;
	private String sex;
	private String maritalstatus;
	private String mobileno;
	private String stage;	
	private String stagechairman;
	private String stagechairmanno;
	private String currentbikeregno;
	private String newbikeuse;	
	private String district;
	private String county;
	private String subcounty;
	private String parish;
	private String village;
	private String yearsinvillage;	
	private String nationalid;
	private String otherid;
	private boolean tvverified;
    private boolean fiverified;
	private String tvremarks;
	private String firemarks; 
	private boolean dcverified;
	private String dcremarks;
	private String nextofkin;
	private String nokmobileno;
	private String nokrelationship;	
	private boolean nokagreeing;	
	private String drivingpermit;
	private String nationality;
	private String noofdependants;
	private String ownhouserented;
	private String landlordname;	
	private String landlordmobileno;	
	private String rentpm;
	private String otherincomesource;
	private String downpaymentsource;
	private String permanentaddress;
	private String fathersname;
	private String mothersname;	
	private String nearbypolicestation;
	private boolean dcsubmit;
	private boolean dcupload;
	private String lc;
	private String lcmobileno;
	private boolean cqc;
	private String cqcremarks;
	private String custtype;
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
	private String tv;
	private String fi;
	private String cqcr;
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
	private String offcdistance;
//	private String relawithapplicant;
	private String paymentbyrider;
	private String yakanum;
	private String yakanumname;
	private String paymtdetailstovby;
	private String cashpaymntworeceipt;
	private String applicantknowvby;
	private String relawithguarantors;
	private String arrangebtwnrider;
	private String resiadrss;
	private String noofyrinaddrss;
	private String mbnonotinname;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getOthername() {
		return othername;
	}
	public void setOthername(String othername) {
		this.othername = othername;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCapturedatetime() {
		return capturedatetime;
	}
	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getSubcounty() {
		return subcounty;
	}
	public void setSubcounty(String subcounty) {
		this.subcounty = subcounty;
	}
	public String getParish() {
		return parish;
	}
	public void setParish(String parish) {
		this.parish = parish;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getYearsinvillage() {
		return yearsinvillage;
	}
	public void setYearsinvillage(String yearsinvillage) {
		this.yearsinvillage = yearsinvillage;
	}
	public String getNationalid() {
		return nationalid;
	}
	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}
	public String getOtherid() {
		return otherid;
	}
	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}
	public boolean isTvverified() {
		return tvverified;
	}
	public void setTvverified(boolean tvverified) {
		this.tvverified = tvverified;
	}
	public boolean isFiverified() {
		return fiverified;
	}
	public void setFiverified(boolean fiverified) {
		this.fiverified = fiverified;
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
	public boolean isDcverified() {
		return dcverified;
	}
	public void setDcverified(boolean dcverified) {
		this.dcverified = dcverified;
	}
	public String getDcremarks() {
		return dcremarks;
	}
	public void setDcremarks(String dcremarks) {
		this.dcremarks = dcremarks;
	}
	public String getNextofkin() {
		return nextofkin;
	}
	public void setNextofkin(String nextofkin) {
		this.nextofkin = nextofkin;
	}
	public String getNokmobileno() {
		return nokmobileno;
	}
	public void setNokmobileno(String nokmobileno) {
		this.nokmobileno = nokmobileno;
	}
	public String getNokrelationship() {
		return nokrelationship;
	}
	public void setNokrelationship(String nokrelationship) {
		this.nokrelationship = nokrelationship;
	}
	public boolean isNokagreeing() {
		return nokagreeing;
	}
	public void setNokagreeing(boolean nokagreeing) {
		this.nokagreeing = nokagreeing;
	}
	public String getDrivingpermit() {
		return drivingpermit;
	}
	public void setDrivingpermit(String drivingpermit) {
		this.drivingpermit = drivingpermit;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNoofdependants() {
		return noofdependants;
	}
	public void setNoofdependants(String noofdependants) {
		this.noofdependants = noofdependants;
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
	public String getDownpaymentsource() {
		return downpaymentsource;
	}
	public void setDownpaymentsource(String downpaymentsource) {
		this.downpaymentsource = downpaymentsource;
	}
	public String getPermanentaddress() {
		return permanentaddress;
	}
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}
	public String getFathersname() {
		return fathersname;
	}
	public void setFathersname(String fathersname) {
		this.fathersname = fathersname;
	}
	public String getMothersname() {
		return mothersname;
	}
	public void setMothersname(String mothersname) {
		this.mothersname = mothersname;
	}
	public String getNearbypolicestation() {
		return nearbypolicestation;
	}
	public void setNearbypolicestation(String nearbypolicestation) {
		this.nearbypolicestation = nearbypolicestation;
	}
	public boolean isDcsubmit() {
		return dcsubmit;
	}
	public void setDcsubmit(boolean dcsubmit) {
		this.dcsubmit = dcsubmit;
	}
	public boolean isDcupload() {
		return dcupload;
	}
	public void setDcupload(boolean dcupload) {
		this.dcupload = dcupload;
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
	public boolean isCqc() {
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
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
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
	public String getCooremarks() {
		return cooremarks;
	}
	public void setCooremarks(String cooremarks) {
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
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public String getFi() {
		return fi;
	}
	public void setFi(String fi) {
		this.fi = fi;
	}
	public String getCqcr() {
		return cqcr;
	}
	public void setCqcr(String cqcr) {
		this.cqcr = cqcr;
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
	public String getOffcdistance() {
		return offcdistance;
	}
	public void setOffcdistance(String offcdistance) {
		this.offcdistance = offcdistance;
	}
//	public String getRelawithapplicant() {
//		return relawithapplicant;
//	}
//	public void setRelawithapplicant(String relawithapplicant) {
//		this.relawithapplicant = relawithapplicant;
//	}
	public String getPaymentbyrider() {
		return paymentbyrider;
	}
	public void setPaymentbyrider(String paymentbyrider) {
		this.paymentbyrider = paymentbyrider;
	}
	public String getYakanum() {
		return yakanum;
	}
	public void setYakanum(String yakanum) {
		this.yakanum = yakanum;
	}
	public String getYakanumname() {
		return yakanumname;
	}
	public void setYakanumname(String yakanumname) {
		this.yakanumname = yakanumname;
	}
	public String getPaymtdetailstovby() {
		return paymtdetailstovby;
	}
	public void setPaymtdetailstovby(String paymtdetailstovby) {
		this.paymtdetailstovby = paymtdetailstovby;
	}
	public String getCashpaymntworeceipt() {
		return cashpaymntworeceipt;
	}
	public void setCashpaymntworeceipt(String cashpaymntworeceipt) {
		this.cashpaymntworeceipt = cashpaymntworeceipt;
	}
	public String getApplicantknowvby() {
		return applicantknowvby;
	}
	public void setApplicantknowvby(String applicantknowvby) {
		this.applicantknowvby = applicantknowvby;
	}
	public String getRelawithguarantors() {
		return relawithguarantors;
	}
	public void setRelawithguarantors(String relawithguarantors) {
		this.relawithguarantors = relawithguarantors;
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
	
}
