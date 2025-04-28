package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblguarantor")
public class Guarantor implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String surname;
	private String firstname;
	private String othername;
	private boolean firstguarantor;
	private boolean secondguarantor;
	private String custid;
	private String nationalid;
	private String mobileno;	
	private String address;
	private String permanentaddress;
	private String yrsinaddress;
	private String ownhouserented;
	private String rentpm;
	private String nextofkin;
	private String nokmobileno;
	private String nokrelationship;
	private boolean nokagreeing;
	private String bikeregno;
	private String bikeowner;
	private boolean salaried;
	private String employername;
	private String monthlyincome;
	private String ois;
	private String otherincome;
	private String stagename;
	private String stageaddress;
	private boolean stagechairconf;
	private String lcname;
	private String capturedatetime;
	private String remarks;
	private String relationship;
	private String lcmobile;
	private String stagechairname;
	private String stagechairmobile;
	private boolean cqc;
	private String cqcremarks;
	private boolean tvverified;
	private String tvremarks;
	private boolean fiverified;
	private String firemarks;
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
	
	private String dob;
	private String llordname;
	private String llordmbno;
	private String llrentfeedbk;
	public String getLlrentfeedbk() {
		return llrentfeedbk;
	}

	public void setLlrentfeedbk(String llrentfeedbk) {
		this.llrentfeedbk = llrentfeedbk;
	}

	private String yakanum;
	private String yakanumname;
	private String resiadrss;
	private String yrsinarea;
	private String lc1chmrecfeed;
	private String nearlmarkresi;
	private String emptype;
	private String stgwrkadrssnearlmark;
	private String yrsinstgorbusi;
	private String maritalsts;
	private String spousename;
	private String spouseno;
	private String mbnonotinname;

	
	public Guarantor() {}
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
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
	
	public boolean getFirstguarantor() {
		return firstguarantor;
	}
	
	public void setFirstguarantor(boolean firstguarantor) {
		this.firstguarantor = firstguarantor;
	}
	
	public boolean getSecondguarantor() {
		return secondguarantor;
	}
	
	public void setSecondguarantor(boolean secondguarantor) {
		this.secondguarantor = secondguarantor;
	}
	
	public String getCustid() {
		return custid;
	}
	
	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	public String getNationalid() {
		return nationalid;
	}
	
	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}
		
	public String getMobileno() {
		return mobileno;
	}
	
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}	
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPermanentaddress() {
		return permanentaddress;
	}
	
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}
	
	public String getYrsinaddress() {
		return yrsinaddress;
	}
	
	public void setYrsinaddress(String yrsinaddress) {
		this.yrsinaddress = yrsinaddress;
	}
	
	public String getOwnhouserented() {
		return ownhouserented;
	}
	
	public void setOwnhouserented(String ownhouserented) {
		this.ownhouserented = ownhouserented;
	}
	
	public String getRentpm() {
		return rentpm;
	}
	
	public void setRentpm(String rentpm) {
		this.rentpm = rentpm;
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
	
	public boolean getNokagreeing() {
		return nokagreeing;
	}
	
	public void setNokagreeing(boolean nokagreeing) {
		this.nokagreeing = nokagreeing;
	}
	
	public String getBikeregno() {
		return bikeregno;
	}
	
	public void setBikeregno(String bikeregno) {
		this.bikeregno = bikeregno;
	}
	
	public String getBikeowner() {
		return bikeowner;
	}
	
	public void setBikeowner(String bikeowner) {
		this.bikeowner = bikeowner;
	}
	
	public boolean getSalaried() {
		return salaried;
	}
	
	public void setSalaried(boolean salaried) {
		this.salaried = salaried;
	}
		
	public String getEmployername() {
		return employername;
	}
	
	public void setEmployername(String employername) {
		this.employername = employername;
	}
	
	public String getMonthlyincome() {
		return monthlyincome;
	}
	
	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
	}
	
	public String getOis() {
		return ois;
	}
	
	public void setOis(String ois) {
		this.ois = ois;
	}
	
	public String getOtherincome() {
		return otherincome;
	}
	
	public void setOtherincome(String otherincome) {
		this.otherincome = otherincome;
	}
		
	public String getStagename() {
		return stagename;
	}
	
	public void setStagename(String stagename) {
		this.stagename = stagename;
	}
	
	public String getStageaddress() {
		return stageaddress;
	}
	
	public void setStageaddress(String stageaddress) {
		this.stageaddress = stageaddress;
	}
	
	public boolean getStagechairconf() {
		return stagechairconf;
	}
	
	public void setStagechairconf(boolean stagechairconf) {
		this.stagechairconf = stagechairconf;
	}
	
	public String getLcname() {
		return lcname;
	}
	
	public void setLcname(String lcname) {
		this.lcname = lcname;
	}
	
	public String getCapturedatetime() {
		return capturedatetime;
	}
	
	public void setCapturedatetime(String capturedatetime) {
		this.capturedatetime = capturedatetime;
	}

	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRelationship() {
		return relationship;
	}
	
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getLcmobile() {
		return lcmobile;
	}
	
	public void setLcmobile(String lcmobile) {
		this.lcmobile = lcmobile;
	}
	
	public String getStagechairname() {
		return stagechairname;
	}
	
	public void setStagechairname(String stagechairname) {
		this.stagechairname = stagechairname;
	}
	
	public String getStagechairmobile() {
		return stagechairmobile;
	}
	
	public void setStagechairmobile(String stagechairmobile) {
		this.stagechairmobile = stagechairmobile;
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
	
	
	public boolean getTvverified() {
		return tvverified;
	}
	
	public void setTvverified(boolean tvverified) {
		this.tvverified = tvverified;
	}
	
	public String getTvremarks() {
		return tvremarks;
	}
	
	public void setTvremarks(String tvremarks) {
		this.tvremarks = tvremarks;
	}
	
	public boolean getFiverified() {
		return fiverified;
	}
	
	public void setFiverified(boolean fiverified) {
		this.fiverified = fiverified;
	}
	
	public String getFiremarks() {
		return firemarks;
	}
	
	public void setFiremarks(String firemarks) {
		this.firemarks = firemarks;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLlordname() {
		return llordname;
	}

	public void setLlordname(String llordname) {
		this.llordname = llordname;
	}

	public String getLlordmbno() {
		return llordmbno;
	}

	public void setLlordmbno(String llordmbno) {
		this.llordmbno = llordmbno;
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

	public String getResiadrss() {
		return resiadrss;
	}

	public void setResiadrss(String resiadrss) {
		this.resiadrss = resiadrss;
	}

	public String getYrsinarea() {
		return yrsinarea;
	}

	public void setYrsinarea(String yrsinarea) {
		this.yrsinarea = yrsinarea;
	}

	public String getLc1chmrecfeed() {
		return lc1chmrecfeed;
	}

	public void setLc1chmrecfeed(String lc1chmrecfeed) {
		this.lc1chmrecfeed = lc1chmrecfeed;
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

	public String getStgwrkadrssnearlmark() {
		return stgwrkadrssnearlmark;
	}

	public void setStgwrkadrssnearlmark(String stgwrkadrssnearlmark) {
		this.stgwrkadrssnearlmark = stgwrkadrssnearlmark;
	}

	public String getYrsinstgorbusi() {
		return yrsinstgorbusi;
	}

	public void setYrsinstgorbusi(String yrsinstgorbusi) {
		this.yrsinstgorbusi = yrsinstgorbusi;
	}

	public String getMaritalsts() {
		return maritalsts;
	}

	public void setMaritalsts(String maritalsts) {
		this.maritalsts = maritalsts;
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

	public String getMbnonotinname() {
		return mbnonotinname;
	}

	public void setMbnonotinname(String mbnonotinname) {
		this.mbnonotinname = mbnonotinname;
	}
	
	
	
}
