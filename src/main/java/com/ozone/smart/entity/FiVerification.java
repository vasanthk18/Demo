package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblfiverification")

public class FiVerification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fiid;
	private String surname;
	private String surnameremark;
	private String surnameflag;
	private String firstname;
	private String firstnameremark;
	private String firstnameflag;
	private String othername;
	private String othernameremark;
	private String othernameflag;
	private String maritalstatus;
	private String maritalstatusremark;
	private String maritalstatusflag;
	private String sex;
	private String sexremark;
	private String sexflag;
	private String mobileno;
	private String mobilenoremark;
	private String mobilenoflag;
	private String stage;
	private String stageremark;
	private String stageflag;
	private String stagechmnname;
	private String stagechmnnameremaks;
	private String stagechmnnameflag;
	private String stagechmnno;
	private String stagechmnnoremarks;
	private String stagechmnnoflag;
	private String district;
	private String districtremark;
	private String districtflag;
	private String lc;
	private String lcremark;
	private String lcflag;
	private String parish;
	private String parishremark;
	private String parishflag;
	private String address;
	private String addressremark;
	private String addressflag;
	private String nationalid;
	private String nationalidremark;
	private String nationalidflag;
	private String bikeregno;
	private String bikeregnoremark;
	private String bikeregnoflag;
	private String bikeuse;
	private String bikeuseremark;
	private String bikeuseflag;
	private boolean firstguarantor;
	private boolean secondguarantor;
	private String guarantorcustid;
	private String relationship;
	private String relationshipremark;
	private String relationshipflag;
	private String firemarks;	
	private String dob;
	private String dobremark;
	private String dobflag;
	private String county;
	private String countyremark;
	private String countyflag;
	private String subcounty;
	private String subcountyremark;
	private String subcountyflag;
	private String village;
	private String villageremark;
	private String villageflag;
	private String yearsinvillage;
	private String yearsinvillageremark;
	private String yearsinvillageflag;
	private String nextofkin;
	private String nextofkinremark;
	private String nextofkinflag;
	private String nokmobileno;
	private String nokmobilenoremark;
	private String nokmobilenoflag;
	private String nokrelationship;
	private String nokrelationshipremark;
	private String nokrelationshipflag;
	private String nokagreeing;
	private String nokagreeingremark;
	private String nokagreeingflag;
	private String drivingpermit;
	private String drivingpermitremark;
	private String drivingpermitflag;
	private String nationality;
	private String nationalityremark;
	private String nationalityflag;
	private String noofdependants;
	private String noofdependantsremark;
	private String noofdependantsflag;
	private String ownhouserented;
	private String ownhouserentedremark;
	private String ownhouserentedflag;
	private String landlordname;
	private String landlordnameremark;
	private String landlordnameflag;
	private String landlordmobileno;
	private String landlordmobilenoremark;
	private String landlordmobilenoflag;
	private String rentpm;
	private String rentpmremark;
	private String rentpmflag;
	private String otherincomesource;
	private String otherincomesourceremark;
	private String otherincomesourceflag;
	private String otherincome;
	private String otherincomeremark;
	private String otherincomeflag;	
	private String downpaymentsource;
	private String downpaymentsourceremark;
	private String downpaymentsourceflag;
	private String permanentaddress;
	private String permanentaddressremark;
	private String permanentaddressflag;
	private String fathersname;
	private String fathersnameremark;
	private String fathersnameflag;
	private String mothersname;
	private String mothersnameremark;
	private String mothersnameflag;
	private String nearbypolicestation;
	private String nearbypolicestationremark;
	private String nearbypolicestationflag;
	private String lcmobileno;
	private String lcmobilenoremark;
	private String lcmobilenoflag;
	private String custtype;
	private String custtyperemark;
	private String custtypeflag;
	private boolean fiverdict;
	private String yearsinaddress;
	private String yearsinaddressremark;
	private String yearsinaddressflag;
	private String bikeowner;
	private String bikeownerremark;
	private String bikeownerflag;
	private String salaried;
	private String salariedremark;
	private String salariedflag;
	private String monthlyincome;
	private String monthlyincomeremark;
	private String monthlyincomeflag;
	private String stageaddress;
	private String stageaddressremark;
	private String stageaddressflag;
	private String stagechairconfirmation;
	private String stagechairconfirmationremark;
	private String stagechairconfirmationflag;
	
	private String stagechairmanname;
	private String stagechairmannameremarks;
	private String stagechairmannameflag;
	
	private String llrentfeedback;
	private String llrentfeedbackremarks;
	private String llrentfeedbackflag;

	private String noyrsinarea;
	private String noyrsinarearemarks;
	private String noyrsinareaflag;

	private String lc1chmnrecfeed;
	private String lc1chmnrecfeedremarks;
	private String lc1chmnrecfeedflag;

	private String nearlmarkresi;
	private String nearlmarkresiremarks;
	private String nearlmarkresiflag;

	private String emptype;
	private String emptyperemarks;
	private String emptypeflag;

	private String stgorwrkadrssnearlmark;
	private String stgorwrkadrssnearlmarkremarks;
	private String stgorwrkadrssnearlmarkflag;

	private String stgoremprecm;
	private String stgoremprecmremarks;
	private String stgoremprecmflag;

	private String noofyrsinstgorbusi;
	private String noofyrsinstgorbusiremarks;
	private String noofyrsinstgorbuisflag;

	private String stgnoofvehi;
	private String stgnoofvehiremarks;
	private String stgnoofvehiflag;

	private String ownerofbike;
	private String ownerofbikeremarks;
	private String ownerofbikeflag;

	private String netincome;
	private String netincomeremarks;
	private String netincomeflag;

	private String bikeusearea;
	private String bikeusearearemarks;
	private String bikeuseareaflag;

	private String spousename;
	private String spousenameremarks;
	private String spousenameflag;

	private String spouseno;
	private String spousenoremarks;
	private String spousenoflag;

	private String spouseconfirm;
	private String spouseconfirmremarks;
	private String spouseconfirmflag;

	private String offcdistance;
	private String offcdistanceremarks;
	private String offcdistanceflag;

	private String relawithapplicant;
	private String relawithapplicantremarks;
	private String relawithapplicantflag;

	private String paymentbyrider;
	private String paymentbyriderremarks;
	private String paymentbyriderflag;

	private String yakanum;
	private String yakanumremarks;
	private String yakanumflag;

	private String yakanumname;
	private String yakanumnameremarks;
	private String yakanumnamflag;

	private String paymtdetailstovby;
	private String paymtdetailstovbyremarks;
	private String paymtdetailstovbyflag;

	private String cashpaymntworeceipt;
	private String cashpaymntworeceiptremarks;
	private String cashpaymntworeceiptflag;

	private String applicantknowvby;
	private String applicantknowvbyremarks;
	private String applicantknowvbyflag;

	private String relawithguarantors;
	private String relawithguarantorsremarks;
	private String relawithguarantorsflag;
	
	private String bikeapplied;
	private String bikeappliedremarks;
	private String bikeappliedflag;
	
	private String downpayment;
	private String downpaymentremarks;
	private String downpaymentflag;
	
	private String tenure;
	private String tenureremarks;
	private String tenureflag;
	
	private String ewioremi;
	private String ewioremiremarks;
	private String ewioremiflag;
	
	private String resiadrss;
	private String resiadrssremarks;
	private String resiadrssflag;
	
	private String stagechairmanno;
	private String stagechairmannoremarks;
	private String stagechairmannoflag;
	
	private String arrangebtwnrider;
	private String arrangebtwnriderremarks;
	private String arrangebtwnriderflag;
	
	private String noofyrinaddrss;
	private String noofyrinaddrssremarks;
	private String noofyrinaddrssflag;
	
	private String mbnonotinname;
	private String mbnonotinnameremarks;
	private String mbnonotinnameflag;
	
	private String mbnonotinname2;
	private String mbnonotinnameremarks2;
	private String mbnonotinnameflag2;

	
	public FiVerification() {}
	
	@Id
	@Column(name="fiid")
	public String getFiid() {
		return fiid;
	}
	
	public void setFiid(String fiid) {
		this.fiid = fiid;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getSurnameremark() {
		return surnameremark;
	}
	
	public void setSurnameremark(String surnameremark) {
		this.surnameremark = surnameremark;
	}
	
	public String getSurnameflag() {
		return surnameflag;
	}
	
	public void setSurnameflag(String surnameflag) {
		this.surnameflag = surnameflag;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getFirstnameremark() {
		return firstnameremark;
	}
	
	public void setFirstnameremark(String firstnameremark) {
		this.firstnameremark = firstnameremark;
	}
	
	public String getFirstnameflag() {
		return firstnameflag;
	}
	
	public void setFirstnameflag(String firstnameflag) {
		this.firstnameflag = firstnameflag;
	}

	public String getOthername() {
		return othername;
	}
	
	public void setOthername(String othername) {
		this.othername = othername;
	}
	
	public String getOthernameremark() {
		return othernameremark;
	}
	
	public void setOthernameremark(String othernameremark) {
		this.othernameremark = othernameremark;
	}
	
	public String getOthernameflag() {
		return othernameflag;
	}
	
	public void setOthernameflag(String othernameflag) {
		this.othernameflag = othernameflag;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}
	
	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}
	
	public String getMaritalstatusremark() {
		return maritalstatusremark;
	}
	
	public void setMaritalstatusremark(String maritalstatusremark) {
		this.maritalstatusremark = maritalstatusremark;
	}
	
	public String getMaritalstatusflag() {
		return maritalstatusflag;
	}
	
	public void setMaritalstatusflag(String maritalstatusflag) {
		this.maritalstatusflag = maritalstatusflag;
	}

	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSexremark() {
		return sexremark;
	}
	
	public void setSexremark(String sexremark) {
		this.sexremark = sexremark;
	}
	
	public String getSexflag() {
		return sexflag;
	}
	
	public void setSexflag(String sexflag) {
		this.sexflag = sexflag;
	}

	public String getMobileno() {
		return mobileno;
	}
	
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	public String getMobilenoremark() {
		return mobilenoremark;
	}
	
	public void setMobilenoremark(String mobilenoremark) {
		this.mobilenoremark = mobilenoremark;
	}
	
	public String getMobilenoflag() {
		return mobilenoflag;
	}
	
	public void setMobilenoflag(String mobilenoflag) {
		this.mobilenoflag = mobilenoflag;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getStageremark() {
		return stageremark;
	}
	
	public void setStageremark(String stageremark) {
		this.stageremark = stageremark;
	}
	
	public String getStageflag() {
		return stageflag;
	}
	
	public void setStageflag(String stageflag) {
		this.stageflag = stageflag;
	}

	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getDistrictremark() {
		return districtremark;
	}
	
	public void setDistrictremark(String districtremark) {
		this.districtremark = districtremark;
	}
	
	public String getDistrictflag() {
		return districtflag;
	}
	
	public void setDistrictflag(String districtflag) {
		this.districtflag = districtflag;
	}
	
	public String getLc() {
		return lc;
	}
	
	public void setLc(String lc) {
		this.lc = lc;
	}
	
	public String getLcremark() {
		return lcremark;
	}
	
	public void setLcremark(String lcremark) {
		this.lcremark = lcremark;
	}
	
	public String getLcflag() {
		return lcflag;
	}
	
	public void setLcflag(String lcflag) {
		this.lcflag = lcflag;
	}
	
	public String getParish() {
		return parish;
	}
	
	public void setParish(String parish) {
		this.parish = parish;
	}
	
	public String getParishremark() {
		return parishremark;
	}
	
	public void setParishremark(String parishremark) {
		this.parishremark = parishremark;
	}
	
	public String getParishflag() {
		return parishflag;
	}
	
	public void setParishflag(String parishflag) {
		this.parishflag = parishflag;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddressremark() {
		return addressremark;
	}
	
	public void setAddressremark(String addressremark) {
		this.addressremark = addressremark;
	}
	
	public String getAddressflag() {
		return addressflag;
	}
	
	public void setAddressflag(String addressflag) {
		this.addressflag = addressflag;
	}
	
	public String getNationalid() {
		return nationalid;
	}
	
	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}
	
	public String getNationalidremark() {
		return nationalidremark;
	}
	
	public void setNationalidremark(String nationalidremark) {
		this.nationalidremark = nationalidremark;
	}
	
	public String getNationalidflag() {
		return nationalidflag;
	}
	
	public void setNationalidflag(String nationalidflag) {
		this.nationalidflag = nationalidflag;
	}
	
	public String getBikeregno() {
		return bikeregno;
	}
	
	public void setBikeregno(String bikeregno) {
		this.bikeregno = bikeregno;
	}
	
	public String getBikeregnoremark() {
		return bikeregnoremark;
	}
	
	public void setBikeregnoremark(String bikeregnoremark) {
		this.bikeregnoremark = bikeregnoremark;
	}
	
	public String getBikeregnoflag() {
		return bikeregnoflag;
	}
	
	public void setBikeregnoflag(String bikeregnoflag) {
		this.bikeregnoflag = bikeregnoflag;
	}
	
	
	public String getBikeuse() {
		return bikeuse;
	}
	
	public void setBikeuse(String bikeuse) {
		this.bikeuse = bikeuse;
	}
	
	public String getBikeuseremark() {
		return bikeuseremark;
	}
	
	public void setBikeuseremark(String bikeuseremark) {
		this.bikeuseremark =bikeuseremark;
	}
	
	public String getBikeuseflag() {
		return bikeuseflag;
	}
	
	public void setBikeuseflag(String bikeuseflag) {
		this.bikeuseflag = bikeuseflag;
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
	
	
	public String getGuarantorcustid() {
		return guarantorcustid;
	}
	
	public void setGuarantorcustid(String guarantorcustid) {
		this.guarantorcustid = guarantorcustid;
	}
	
	
	public String getRelationship() {
		return relationship;
	}
	
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getRelationshipremark() {
		return relationshipremark;
	}
	
	public void setRelationshipremark(String relationshipremark) {
		this.relationshipremark =relationshipremark;
	}
	
	public String getRelationshipflag() {
		return relationshipflag;
	}
	
	public void setRelationshipflag(String relationshipflag) {
		this.relationshipflag = relationshipflag;
	}
	
	public String getFiremarks() {
		return firemarks;
	}
	
	public void setFiremarks(String firemarks) {
		this.firemarks = firemarks;
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob =dob;
	}
	
	public String getDobremark() {
		return dobremark;
	}
	
	public void setDobremark(String dobremark) {
		this.dobremark = dobremark;
	}
	
	public String getDobflag() {
		return dobflag;
	}
	
	public void setDobflag(String dobflag) {
		this.dobflag = dobflag;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county =county;
	}
	
	public String getCountyremark() {
		return countyremark;
	}
	
	public void setCountyremark(String countyremark) {
		this.countyremark = countyremark;
	}
	
	public String getCountyflag() {
		return countyflag;
	}
	
	public void setCountyflag(String countyflag) {
		this.countyflag = countyflag;
	}

	public String getSubcounty() {
		return subcounty;
	}
	
	public void setSubcounty(String subcounty) {
		this.subcounty =subcounty;
	}
	
	public String getSubcountyremark() {
		return subcountyremark;
	}
	
	public void setSubcountyremark(String subcountyremark) {
		this.subcountyremark = subcountyremark;
	}
	
	public String getSubcountyflag() {
		return subcountyflag;
	}
	
	public void setSubcountyflag(String subcountyflag) {
		this.subcountyflag = subcountyflag;
	}
	
	public String getVillage() {
		return village;
	}
	
	public void setVillage(String village) {
		this.village =village;
	}
	
	public String getVillageremark() {
		return villageremark;
	}
	
	public void setVillageremark(String villageremark) {
		this.villageremark = villageremark;
	}
	
	public String getVillageflag() {
		return villageflag;
	}
	
	public void setVillageflag(String villageflag) {
		this.villageflag = villageflag;
	}
	
	public String getYearsinvillage() {
		return yearsinvillage;
	}
	
	public void setYearsinvillage(String yearsinvillage) {
		this.yearsinvillage =yearsinvillage;
	}
	
	public String getYearsinvillageremark() {
		return yearsinvillageremark;
	}
	
	public void setYearsinvillageremark(String yearsinvillageremark) {
		this.yearsinvillageremark = yearsinvillageremark;
	}
	
	public String getYearsinvillageflag() {
		return yearsinvillageflag;
	}
	
	public void setYearsinvillageflag(String yearsinvillageflag) {
		this.yearsinvillageflag = yearsinvillageflag;
	}
	
	public String getNextofkin() {
		return nextofkin;
	}
	
	public void setNextofkin(String nextofkin) {
		this.nextofkin =nextofkin;
	}
	
	public String getNextofkinremark() {
		return nextofkinremark;
	}
	
	public void setNextofkinremark(String nextofkinremark) {
		this.nextofkinremark = nextofkinremark;
	}
	
	public String getNextofkinflag() {
		return nextofkinflag;
	}
	
	public void setNextofkinflag(String nextofkinflag) {
		this.nextofkinflag = nextofkinflag;
	}
	
	public String getNokmobileno() {
		return nokmobileno;
	}
	
	public void setNokmobileno(String nokmobileno) {
		this.nokmobileno =nokmobileno;
	}
	
	public String getNokmobilenoremark() {
		return nokmobilenoremark;
	}
	
	public void setNokmobilenoremark(String nokmobilenoremark) {
		this.nokmobilenoremark = nokmobilenoremark;
	}
	
	public String getNokmobilenoflag() {
		return nokmobilenoflag;
	}
	
	public void setNokmobilenoflag(String nokmobilenoflag) {
		this.nokmobilenoflag = nokmobilenoflag;
	}
	
	public String getNokrelationship() {
		return nokrelationship;
	}
	
	public void setNokrelationship(String nokrelationship) {
		this.nokrelationship =nokrelationship;
	}
	
	public String getNokrelationshipremark() {
		return nokrelationshipremark;
	}
	
	public void setNokrelationshipremark(String nokrelationshipremark) {
		this.nokrelationshipremark = nokrelationshipremark;
	}
	
	public String getNokrelationshipflag() {
		return nokrelationshipflag;
	}
	
	public void setNokrelationshipflag(String nokrelationshipflag) {
		this.nokrelationshipflag = nokrelationshipflag;
	}
	
	public String getNokagreeing() {
		return nokagreeing;
	}
	
	public void setNokagreeing(String nokagreeing) {
		this.nokagreeing =nokagreeing;
	}
	
	public String getNokagreeingremark() {
		return nokagreeingremark;
	}
	
	public void setNokagreeingremark(String nokagreeingremark) {
		this.nokagreeingremark = nokagreeingremark;
	}
	
	public String getNokagreeingflag() {
		return nokagreeingflag;
	}
	
	public void setNokagreeingflag(String nokagreeingflag) {
		this.nokagreeingflag = nokagreeingflag;
	}
	
	public String getDrivingpermit() {
		return drivingpermit;
	}
	
	public void setDrivingpermit(String drivingpermit) {
		this.drivingpermit =drivingpermit;
	}
	
	public String getDrivingpermitremark() {
		return drivingpermitremark;
	}
	
	public void setDrivingpermitremark(String drivingpermitremark) {
		this.drivingpermitremark = drivingpermitremark;
	}
	
	public String getDrivingpermitflag() {
		return drivingpermitflag;
	}
	
	public void setDrivingpermitflag(String drivingpermitflag) {
		this.drivingpermitflag = drivingpermitflag;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality =nationality;
	}
	
	public String getNationalityremark() {
		return nationalityremark;
	}
	
	public void setNationalityremark(String nationalityremark) {
		this.nationalityremark = nationalityremark;
	}
	
	public String getNationalityflag() {
		return nationalityflag;
	}
	
	public void setNationalityflag(String nationalityflag) {
		this.nationalityflag = nationalityflag;
	}
	
	public String getNoofdependants() {
		return noofdependants;
	}
	
	public void setNoofdependants(String noofdependants) {
		this.noofdependants =noofdependants;
	}
	
	public String getNoofdependantsremark() {
		return noofdependantsremark;
	}
	
	public void setNoofdependantsremark(String noofdependantsremark) {
		this.noofdependantsremark = noofdependantsremark;
	}
	
	public String getNoofdependantsflag() {
		return noofdependantsflag;
	}
	
	public void setNoofdependantsflag(String noofdependantsflag) {
		this.noofdependantsflag = noofdependantsflag;
	}
	
	public String getOwnhouserented() {
		return ownhouserented;
	}
	
	public void setOwnhouserented(String ownhouserented) {
		this.ownhouserented =ownhouserented;
	}
	
	public String getOwnhouserentedremark() {
		return ownhouserentedremark;
	}
	
	public void setOwnhouserentedremark(String ownhouserentedremark) {
		this.ownhouserentedremark = ownhouserentedremark;
	}
	
	public String getOwnhouserentedflag() {
		return ownhouserentedflag;
	}
	
	public void setOwnhouserentedflag(String ownhouserentedflag) {
		this.ownhouserentedflag = ownhouserentedflag;
	}
	
	public String getLandlordname() {
		return landlordname;
	}
	
	public void setLandlordname(String landlordname) {
		this.landlordname =landlordname;
	}
	
	public String getLandlordnameremark() {
		return landlordnameremark;
	}
	
	public void setLandlordnameremark(String landlordnameremark) {
		this.landlordnameremark = landlordnameremark;
	}
	
	public String getLandlordnameflag() {
		return landlordnameflag;
	}
	
	public void setLandlordnameflag(String landlordnameflag) {
		this.landlordnameflag = landlordnameflag;
	}
	
	public String getLandlordmobileno() {
		return landlordmobileno;
	}
	
	public void setLandlordmobileno(String landlordmobileno) {
		this.landlordmobileno =landlordmobileno;
	}
	
	public String getLandlordmobilenoremark() {
		return landlordmobilenoremark;
	}
	
	public void setLandlordmobilenoremark(String landlordmobilenoremark) {
		this.landlordmobilenoremark = landlordmobilenoremark;
	}
	
	public String getLandlordmobilenoflag() {
		return landlordmobilenoflag;
	}
	
	public void setLandlordmobilenoflag(String landlordmobilenoflag) {
		this.landlordmobilenoflag = landlordmobilenoflag;
	}
	
	public String getRentpm() {
		return rentpm;
	}
	
	public void setRentpm(String rentpm) {
		this.rentpm =rentpm;
	}
	
	public String getRentpmremark() {
		return rentpmremark;
	}
	
	public void setRentpmremark(String rentpmremark) {
		this.rentpmremark = rentpmremark;
	}
	
	public String getRentpmflag() {
		return rentpmflag;
	}
	
	public void setRentpmflag(String rentpmflag) {
		this.rentpmflag = rentpmflag;
	}
	
	public String getOtherincomesource() {
		return otherincomesource;
	}
	
	public void setOtherincomesource(String otherincomesource) {
		this.otherincomesource =otherincomesource;
	}
	
	public String getOtherincomesourceremark() {
		return otherincomesourceremark;
	}
	
	public void setOtherincomesourceremark(String otherincomesourceremark) {
		this.otherincomesourceremark = otherincomesourceremark;
	}
	
	public String getOtherincomesourceflag() {
		return otherincomesourceflag;
	}
	
	public void setOtherincomesourceflag(String otherincomesourceflag) {
		this.otherincomesourceflag = otherincomesourceflag;
	}
		
	public String getOtherincome() {
		return otherincome;
	}
	
	public void setOtherincome(String otherincome) {
		this.otherincome =otherincome;
	}
	
	public String getOtherincomeremark() {
		return otherincomeremark;
	}
	
	public void setOtherincomeremark(String otherincomeremark) {
		this.otherincomeremark = otherincomeremark;
	}
	
	public String getOtherincomeflag() {
		return otherincomeflag;
	}
	
	public void setOtherincomeflag(String otherincomeflag) {
		this.otherincomeflag = otherincomeflag;
	}
		
	public String getDownpaymentsource() {
		return downpaymentsource;
	}
	
	public void setDownpaymentsource(String downpaymentsource) {
		this.downpaymentsource =downpaymentsource;
	}
	
	public String getDownpaymentsourceremark() {
		return downpaymentsourceremark;
	}
	
	public void setDownpaymentsourceremark(String downpaymentsourceremark) {
		this.downpaymentsourceremark = downpaymentsourceremark;
	}
	
	public String getDownpaymentsourceflag() {
		return downpaymentsourceflag;
	}
	
	public void setDownpaymentsourceflag(String downpaymentsourceflag) {
		this.downpaymentsourceflag = downpaymentsourceflag;
	}
	
	public String getPermanentaddress() {
		return permanentaddress;
	}
	
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress =permanentaddress;
	}
	
	public String getPermanentaddressremark() {
		return permanentaddressremark;
	}
	
	public void setPermanentaddressremark(String permanentaddressremark) {
		this.permanentaddressremark = permanentaddressremark;
	}
	
	public String getPermanentaddressflag() {
		return permanentaddressflag;
	}
	
	public void setPermanentaddressflag(String permanentaddressflag) {
		this.permanentaddressflag = permanentaddressflag;
	}
	
	public String getFathersname() {
		return fathersname;
	}
	
	public void setFathersname(String fathersname) {
		this.fathersname =fathersname;
	}
	
	public String getFathersnameremark() {
		return fathersnameremark;
	}
	
	public void setFathersnameremark(String fathersnameremark) {
		this.fathersnameremark = fathersnameremark;
	}
	
	public String getFathersnameflag() {
		return fathersnameflag;
	}
	
	public void setFathersnameflag(String fathersnameflag) {
		this.fathersnameflag = fathersnameflag;
	}
	
	public String getMothersname() {
		return mothersname;
	}
	
	public void setMothersname(String mothersname) {
		this.mothersname =mothersname;
	}
	
	public String getMothersnameremark() {
		return mothersnameremark;
	}
	
	public void setMothersnameremark(String mothersnameremark) {
		this.mothersnameremark = mothersnameremark;
	}
	
	public String getMothersnameflag() {
		return mothersnameflag;
	}
	
	public void setMothersnameflag(String mothersnameflag) {
		this.mothersnameflag = mothersnameflag;
	}
	
	public String getNearbypolicestation() {
		return nearbypolicestation;
	}
	
	public void setNearbypolicestation(String nearbypolicestation) {
		this.nearbypolicestation =nearbypolicestation;
	}
	
	public String getNearbypolicestationremark() {
		return nearbypolicestationremark;
	}
	
	public void setNearbypolicestationremark(String nearbypolicestationremark) {
		this.nearbypolicestationremark = nearbypolicestationremark;
	}
	
	public String getNearbypolicestationflag() {
		return nearbypolicestationflag;
	}
	
	public void setNearbypolicestationflag(String nearbypolicestationflag) {
		this.nearbypolicestationflag = nearbypolicestationflag;
	}
	
	public String getLcmobileno() {
		return lcmobileno;
	}
	
	public void setLcmobileno(String lcmobileno) {
		this.lcmobileno =lcmobileno;
	}
	
	public String getLcmobilenoremark() {
		return lcmobilenoremark;
	}
	
	public void setLcmobilenoremark(String lcmobilenoremark) {
		this.lcmobilenoremark = lcmobilenoremark;
	}
	
	public String getLcmobilenoflag() {
		return lcmobilenoflag;
	}
	
	public void setLcmobilenoflag(String lcmobilenoflag) {
		this.lcmobilenoflag = lcmobilenoflag;
	}
	
	public String getCusttype() {
		return custtype;
	}
	
	public void setCusttype(String custtype) {
		this.custtype =custtype;
	}
	
	public String getCusttyperemark() {
		return custtyperemark;
	}
	
	public void setCusttyperemark(String custtyperemark) {
		this.custtyperemark = custtyperemark;
	}
	
	public String getCusttypeflag() {
		return custtypeflag;
	}
	
	public void setCusttypeflag(String custtypeflag) {
		this.custtypeflag = custtypeflag;
	}
	
	public boolean getFiverdict() {
		return fiverdict;
	}
	
	public void setFiverdict(boolean fiverdict) {
		this.fiverdict = fiverdict;
	}
	
	public String getYearsinaddress() {
		return yearsinaddress;
	}
	
	public void setYearsinaddress(String yearsinaddress) {
		this.yearsinaddress = yearsinaddress;
	}
	
	public String getYearsinaddressremark() {
		return yearsinaddressremark;
	}
	
	public void setYearsinaddressremark(String yearsinaddressremark) {
		this.yearsinaddressremark = yearsinaddressremark;
	}
	
	public String getYearsinaddressflag() {
		return yearsinaddressflag;
	}
	
	public void setYearsinaddressflag(String yearsinaddressflag) {
		this.yearsinaddressflag = yearsinaddressflag;
	}
		
	public String getBikeowner() {
		return bikeowner;
	}
	
	public void setBikeowner(String bikeowner) {
		this.bikeowner = bikeowner;
	}
	
	public String getBikeownerremark() {
		return bikeownerremark;
	}
	
	public void setBikeownerremark(String bikeownerremark) {
		this.bikeownerremark = bikeownerremark;
	}
	
	public String getBikeownerflag() {
		return bikeownerflag;
	}
	
	public void setBikeownerflag(String bikeownerflag) {
		this.bikeownerflag = bikeownerflag;
	}
		
	public String getSalaried() {
		return salaried;
	}
	
	public void setSalaried(String salaried) {
		this.salaried = salaried;
	}
	
	public String getSalariedremark() {
		return salariedremark;
	}
	
	public void setSalariedremark(String salariedremark) {
		this.salariedremark = salariedremark;
	}
	
	public String getSalariedflag() {
		return salariedflag;
	}
	
	public void setSalariedflag(String salariedflag) {
		this.salariedflag = salariedflag;
	}
		
	public String getMonthlyincome() {
		return monthlyincome;
	}
	
	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
	}
	
	public String getMonthlyincomeremark() {
		return monthlyincomeremark;
	}
	
	public void setMonthlyincomeremark(String monthlyincomeremark) {
		this.monthlyincomeremark = monthlyincomeremark;
	}
	
	public String getMonthlyincomeflag() {
		return monthlyincomeflag;
	}
	
	public void setMonthlyincomeflag(String monthlyincomeflag) {
		this.monthlyincomeflag = monthlyincomeflag;
	}
		
	public String getStageaddress() {
		return stageaddress;
	}
	
	public void setStageaddress(String stageaddress) {
		this.stageaddress = stageaddress;
	}
	
	public String getStageaddressremark() {
		return stageaddressremark;
	}
	
	public void setStageaddressremark(String stageaddressremark) {
		this.stageaddressremark = stageaddressremark;
	}
	
	public String getStageaddressflag() {
		return stageaddressflag;
	}
	
	public void setStageaddressflag(String stageaddressflag) {
		this.stageaddressflag = stageaddressflag;
	}
		
	public String getStagechairconfirmation() {
		return stagechairconfirmation;
	}
	
	public void setStagechairconfirmation(String stagechairconfirmation) {
		this.stagechairconfirmation = stagechairconfirmation;
	}
	
	public String getStagechairconfirmationremark() {
		return stagechairconfirmationremark;
	}
	
	public void setStagechairconfirmationremark(String stagechairconfirmationremark) {
		this.stagechairconfirmationremark = stagechairconfirmationremark;
	}
	
	public String getStagechairconfirmationflag() {
		return stagechairconfirmationflag;
	}
	
	public void setStagechairconfirmationflag(String stagechairconfirmationflag) {
		this.stagechairconfirmationflag = stagechairconfirmationflag;
	}

	public String getStagechmnname() {
		return stagechmnname;
	}

	public void setStagechmnname(String stagechmnname) {
		this.stagechmnname = stagechmnname;
	}

	public String getStagechmnnameremaks() {
		return stagechmnnameremaks;
	}

	public void setStagechmnnameremaks(String stagechmnnameremaks) {
		this.stagechmnnameremaks = stagechmnnameremaks;
	}

	public String getStagechmnnameflag() {
		return stagechmnnameflag;
	}

	public void setStagechmnnameflag(String stagechmnnameflag) {
		this.stagechmnnameflag = stagechmnnameflag;
	}

	public String getStagechmnno() {
		return stagechmnno;
	}

	public void setStagechmnno(String stagechmnno) {
		this.stagechmnno = stagechmnno;
	}

	public String getStagechmnnoremarks() {
		return stagechmnnoremarks;
	}

	public void setStagechmnnoremarks(String stagechmnnoremarks) {
		this.stagechmnnoremarks = stagechmnnoremarks;
	}

	public String getStagechmnnoflag() {
		return stagechmnnoflag;
	}

	public void setStagechmnnoflag(String stagechmnnoflag) {
		this.stagechmnnoflag = stagechmnnoflag;
	}

	public String getLlrentfeedback() {
		return llrentfeedback;
	}

	public void setLlrentfeedback(String llrentfeedback) {
		this.llrentfeedback = llrentfeedback;
	}

	public String getLlrentfeedbackremarks() {
		return llrentfeedbackremarks;
	}

	public void setLlrentfeedbackremarks(String llrentfeedbackremarks) {
		this.llrentfeedbackremarks = llrentfeedbackremarks;
	}

	public String getLlrentfeedbackflag() {
		return llrentfeedbackflag;
	}

	public void setLlrentfeedbackflag(String llrentfeedbackflag) {
		this.llrentfeedbackflag = llrentfeedbackflag;
	}

	public String getNoyrsinarea() {
		return noyrsinarea;
	}

	public void setNoyrsinarea(String noyrsinarea) {
		this.noyrsinarea = noyrsinarea;
	}

	public String getNoyrsinarearemarks() {
		return noyrsinarearemarks;
	}

	public void setNoyrsinarearemarks(String noyrsinarearemarks) {
		this.noyrsinarearemarks = noyrsinarearemarks;
	}

	public String getNoyrsinareaflag() {
		return noyrsinareaflag;
	}

	public void setNoyrsinareaflag(String noyrsinareaflag) {
		this.noyrsinareaflag = noyrsinareaflag;
	}

	public String getLc1chmnrecfeed() {
		return lc1chmnrecfeed;
	}

	public void setLc1chmnrecfeed(String lc1chmnrecfeed) {
		this.lc1chmnrecfeed = lc1chmnrecfeed;
	}

	public String getLc1chmnrecfeedremarks() {
		return lc1chmnrecfeedremarks;
	}

	public void setLc1chmnrecfeedremarks(String lc1chmnrecfeedremarks) {
		this.lc1chmnrecfeedremarks = lc1chmnrecfeedremarks;
	}

	public String getLc1chmnrecfeedflag() {
		return lc1chmnrecfeedflag;
	}

	public void setLc1chmnrecfeedflag(String lc1chmnrecfeedflag) {
		this.lc1chmnrecfeedflag = lc1chmnrecfeedflag;
	}

	public String getNearlmarkresi() {
		return nearlmarkresi;
	}

	public void setNearlmarkresi(String nearlmarkresi) {
		this.nearlmarkresi = nearlmarkresi;
	}

	public String getNearlmarkresiremarks() {
		return nearlmarkresiremarks;
	}

	public void setNearlmarkresiremarks(String nearlmarkresiremarks) {
		this.nearlmarkresiremarks = nearlmarkresiremarks;
	}

	public String getNearlmarkresiflag() {
		return nearlmarkresiflag;
	}

	public void setNearlmarkresiflag(String nearlmarkresiflag) {
		this.nearlmarkresiflag = nearlmarkresiflag;
	}

	public String getEmptype() {
		return emptype;
	}

	public void setEmptype(String emptype) {
		this.emptype = emptype;
	}

	public String getEmptyperemarks() {
		return emptyperemarks;
	}

	public void setEmptyperemarks(String emptyperemarks) {
		this.emptyperemarks = emptyperemarks;
	}

	public String getEmptypeflag() {
		return emptypeflag;
	}

	public void setEmptypeflag(String emptypeflag) {
		this.emptypeflag = emptypeflag;
	}

	public String getStgorwrkadrssnearlmark() {
		return stgorwrkadrssnearlmark;
	}

	public void setStgorwrkadrssnearlmark(String stgorwrkadrssnearlmark) {
		this.stgorwrkadrssnearlmark = stgorwrkadrssnearlmark;
	}

	public String getStgorwrkadrssnearlmarkremarks() {
		return stgorwrkadrssnearlmarkremarks;
	}

	public void setStgorwrkadrssnearlmarkremarks(String stgorwrkadrssnearlmarkremarks) {
		this.stgorwrkadrssnearlmarkremarks = stgorwrkadrssnearlmarkremarks;
	}

	public String getStgorwrkadrssnearlmarkflag() {
		return stgorwrkadrssnearlmarkflag;
	}

	public void setStgorwrkadrssnearlmarkflag(String stgorwrkadrssnearlmarkflag) {
		this.stgorwrkadrssnearlmarkflag = stgorwrkadrssnearlmarkflag;
	}

	public String getStgoremprecm() {
		return stgoremprecm;
	}

	public void setStgoremprecm(String stgoremprecm) {
		this.stgoremprecm = stgoremprecm;
	}

	public String getStgoremprecmremarks() {
		return stgoremprecmremarks;
	}

	public void setStgoremprecmremarks(String stgoremprecmremarks) {
		this.stgoremprecmremarks = stgoremprecmremarks;
	}

	public String getStgoremprecmflag() {
		return stgoremprecmflag;
	}

	public void setStgoremprecmflag(String stgoremprecmflag) {
		this.stgoremprecmflag = stgoremprecmflag;
	}

	public String getNoofyrsinstgorbusi() {
		return noofyrsinstgorbusi;
	}

	public void setNoofyrsinstgorbusi(String noofyrsinstgorbusi) {
		this.noofyrsinstgorbusi = noofyrsinstgorbusi;
	}

	public String getNoofyrsinstgorbusiremarks() {
		return noofyrsinstgorbusiremarks;
	}

	public void setNoofyrsinstgorbusiremarks(String noofyrsinstgorbusiremarks) {
		this.noofyrsinstgorbusiremarks = noofyrsinstgorbusiremarks;
	}

	public String getNoofyrsinstgorbuisflag() {
		return noofyrsinstgorbuisflag;
	}

	public void setNoofyrsinstgorbuisflag(String noofyrsinstgorbuisflag) {
		this.noofyrsinstgorbuisflag = noofyrsinstgorbuisflag;
	}

	public String getStgnoofvehi() {
		return stgnoofvehi;
	}

	public void setStgnoofvehi(String stgnoofvehi) {
		this.stgnoofvehi = stgnoofvehi;
	}

	public String getStgnoofvehiremarks() {
		return stgnoofvehiremarks;
	}

	public void setStgnoofvehiremarks(String stgnoofvehiremarks) {
		this.stgnoofvehiremarks = stgnoofvehiremarks;
	}

	public String getStgnoofvehiflag() {
		return stgnoofvehiflag;
	}

	public void setStgnoofvehiflag(String stgnoofvehiflag) {
		this.stgnoofvehiflag = stgnoofvehiflag;
	}

	public String getOwnerofbike() {
		return ownerofbike;
	}

	public void setOwnerofbike(String ownerofbike) {
		this.ownerofbike = ownerofbike;
	}

	public String getOwnerofbikeremarks() {
		return ownerofbikeremarks;
	}

	public void setOwnerofbikeremarks(String ownerofbikeremarks) {
		this.ownerofbikeremarks = ownerofbikeremarks;
	}

	public String getOwnerofbikeflag() {
		return ownerofbikeflag;
	}

	public void setOwnerofbikeflag(String ownerofbikeflag) {
		this.ownerofbikeflag = ownerofbikeflag;
	}

	public String getNetincome() {
		return netincome;
	}

	public void setNetincome(String netincome) {
		this.netincome = netincome;
	}

	public String getNetincomeremarks() {
		return netincomeremarks;
	}

	public void setNetincomeremarks(String netincomeremarks) {
		this.netincomeremarks = netincomeremarks;
	}

	public String getNetincomeflag() {
		return netincomeflag;
	}

	public void setNetincomeflag(String netincomeflag) {
		this.netincomeflag = netincomeflag;
	}

	public String getBikeusearea() {
		return bikeusearea;
	}

	public void setBikeusearea(String bikeusearea) {
		this.bikeusearea = bikeusearea;
	}

	public String getBikeusearearemarks() {
		return bikeusearearemarks;
	}

	public void setBikeusearearemarks(String bikeusearearemarks) {
		this.bikeusearearemarks = bikeusearearemarks;
	}

	public String getBikeuseareaflag() {
		return bikeuseareaflag;
	}

	public void setBikeuseareaflag(String bikeuseareaflag) {
		this.bikeuseareaflag = bikeuseareaflag;
	}

	public String getSpousename() {
		return spousename;
	}

	public void setSpousename(String spousename) {
		this.spousename = spousename;
	}

	public String getSpousenameremarks() {
		return spousenameremarks;
	}

	public void setSpousenameremarks(String spousenameremarks) {
		this.spousenameremarks = spousenameremarks;
	}

	public String getSpousenameflag() {
		return spousenameflag;
	}

	public void setSpousenameflag(String spousenameflag) {
		this.spousenameflag = spousenameflag;
	}

	public String getSpouseno() {
		return spouseno;
	}

	public void setSpouseno(String spouseno) {
		this.spouseno = spouseno;
	}

	public String getSpousenoremarks() {
		return spousenoremarks;
	}

	public void setSpousenoremarks(String spousenoremarks) {
		this.spousenoremarks = spousenoremarks;
	}

	public String getSpousenoflag() {
		return spousenoflag;
	}

	public void setSpousenoflag(String spousenoflag) {
		this.spousenoflag = spousenoflag;
	}

	public String getSpouseconfirm() {
		return spouseconfirm;
	}

	public void setSpouseconfirm(String spouseconfirm) {
		this.spouseconfirm = spouseconfirm;
	}

	public String getSpouseconfirmremarks() {
		return spouseconfirmremarks;
	}

	public void setSpouseconfirmremarks(String spouseconfirmremarks) {
		this.spouseconfirmremarks = spouseconfirmremarks;
	}

	public String getSpouseconfirmflag() {
		return spouseconfirmflag;
	}

	public void setSpouseconfirmflag(String spouseconfirmflag) {
		this.spouseconfirmflag = spouseconfirmflag;
	}

	public String getOffcdistance() {
		return offcdistance;
	}

	public void setOffcdistance(String offcdistance) {
		this.offcdistance = offcdistance;
	}

	public String getOffcdistanceremarks() {
		return offcdistanceremarks;
	}

	public void setOffcdistanceremarks(String offcdistanceremarks) {
		this.offcdistanceremarks = offcdistanceremarks;
	}

	public String getOffcdistanceflag() {
		return offcdistanceflag;
	}

	public void setOffcdistanceflag(String offcdistanceflag) {
		this.offcdistanceflag = offcdistanceflag;
	}

	public String getRelawithapplicant() {
		return relawithapplicant;
	}

	public void setRelawithapplicant(String relawithapplicant) {
		this.relawithapplicant = relawithapplicant;
	}

	public String getRelawithapplicantremarks() {
		return relawithapplicantremarks;
	}

	public void setRelawithapplicantremarks(String relawithapplicantremarks) {
		this.relawithapplicantremarks = relawithapplicantremarks;
	}

	public String getRelawithapplicantflag() {
		return relawithapplicantflag;
	}

	public void setRelawithapplicantflag(String relawithapplicantflag) {
		this.relawithapplicantflag = relawithapplicantflag;
	}

	public String getPaymentbyrider() {
		return paymentbyrider;
	}

	public void setPaymentbyrider(String paymentbyrider) {
		this.paymentbyrider = paymentbyrider;
	}

	public String getPaymentbyriderremarks() {
		return paymentbyriderremarks;
	}

	public void setPaymentbyriderremarks(String paymentbyriderremarks) {
		this.paymentbyriderremarks = paymentbyriderremarks;
	}

	public String getPaymentbyriderflag() {
		return paymentbyriderflag;
	}

	public void setPaymentbyriderflag(String paymentbyriderflag) {
		this.paymentbyriderflag = paymentbyriderflag;
	}

	public String getYakanum() {
		return yakanum;
	}

	public void setYakanum(String yakanum) {
		this.yakanum = yakanum;
	}

	public String getYakanumremarks() {
		return yakanumremarks;
	}

	public void setYakanumremarks(String yakanumremarks) {
		this.yakanumremarks = yakanumremarks;
	}

	public String getYakanumflag() {
		return yakanumflag;
	}

	public void setYakanumflag(String yakanumflag) {
		this.yakanumflag = yakanumflag;
	}

	public String getYakanumname() {
		return yakanumname;
	}

	public void setYakanumname(String yakanumname) {
		this.yakanumname = yakanumname;
	}

	public String getYakanumnameremarks() {
		return yakanumnameremarks;
	}

	public void setYakanumnameremarks(String yakanumnameremarks) {
		this.yakanumnameremarks = yakanumnameremarks;
	}

	public String getYakanumnamflag() {
		return yakanumnamflag;
	}

	public void setYakanumnamflag(String yakanumnamflag) {
		this.yakanumnamflag = yakanumnamflag;
	}

	public String getPaymtdetailstovby() {
		return paymtdetailstovby;
	}

	public void setPaymtdetailstovby(String paymtdetailstovby) {
		this.paymtdetailstovby = paymtdetailstovby;
	}

	public String getPaymtdetailstovbyremarks() {
		return paymtdetailstovbyremarks;
	}

	public void setPaymtdetailstovbyremarks(String paymtdetailstovbyremarks) {
		this.paymtdetailstovbyremarks = paymtdetailstovbyremarks;
	}

	public String getPaymtdetailstovbyflag() {
		return paymtdetailstovbyflag;
	}

	public void setPaymtdetailstovbyflag(String paymtdetailstovbyflag) {
		this.paymtdetailstovbyflag = paymtdetailstovbyflag;
	}

	public String getCashpaymntworeceipt() {
		return cashpaymntworeceipt;
	}

	public void setCashpaymntworeceipt(String cashpaymntworeceipt) {
		this.cashpaymntworeceipt = cashpaymntworeceipt;
	}

	public String getCashpaymntworeceiptremarks() {
		return cashpaymntworeceiptremarks;
	}

	public void setCashpaymntworeceiptremarks(String cashpaymntworeceiptremarks) {
		this.cashpaymntworeceiptremarks = cashpaymntworeceiptremarks;
	}

	public String getCashpaymntworeceiptflag() {
		return cashpaymntworeceiptflag;
	}

	public void setCashpaymntworeceiptflag(String cashpaymntworeceiptflag) {
		this.cashpaymntworeceiptflag = cashpaymntworeceiptflag;
	}

	public String getApplicantknowvby() {
		return applicantknowvby;
	}

	public void setApplicantknowvby(String applicantknowvby) {
		this.applicantknowvby = applicantknowvby;
	}

	public String getApplicantknowvbyremarks() {
		return applicantknowvbyremarks;
	}

	public void setApplicantknowvbyremarks(String applicantknowvbyremarks) {
		this.applicantknowvbyremarks = applicantknowvbyremarks;
	}

	public String getApplicantknowvbyflag() {
		return applicantknowvbyflag;
	}

	public void setApplicantknowvbyflag(String applicantknowvbyflag) {
		this.applicantknowvbyflag = applicantknowvbyflag;
	}

	public String getRelawithguarantors() {
		return relawithguarantors;
	}

	public void setRelawithguarantors(String relawithguarantors) {
		this.relawithguarantors = relawithguarantors;
	}

	public String getRelawithguarantorsremarks() {
		return relawithguarantorsremarks;
	}

	public void setRelawithguarantorsremarks(String relawithguarantorsremarks) {
		this.relawithguarantorsremarks = relawithguarantorsremarks;
	}

	public String getRelawithguarantorsflag() {
		return relawithguarantorsflag;
	}

	public void setRelawithguarantorsflag(String relawithguarantorsflag) {
		this.relawithguarantorsflag = relawithguarantorsflag;
	}

	public String getBikeapplied() {
		return bikeapplied;
	}

	public void setBikeapplied(String bikeapplied) {
		this.bikeapplied = bikeapplied;
	}

	public String getBikeappliedremarks() {
		return bikeappliedremarks;
	}

	public void setBikeappliedremarks(String bikeappliedremarks) {
		this.bikeappliedremarks = bikeappliedremarks;
	}

	public String getBikeappliedflag() {
		return bikeappliedflag;
	}

	public void setBikeappliedflag(String bikeappliedflag) {
		this.bikeappliedflag = bikeappliedflag;
	}

	public String getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}

	public String getDownpaymentremarks() {
		return downpaymentremarks;
	}

	public void setDownpaymentremarks(String downpaymentremarks) {
		this.downpaymentremarks = downpaymentremarks;
	}

	public String getDownpaymentflag() {
		return downpaymentflag;
	}

	public void setDownpaymentflag(String downpaymentflag) {
		this.downpaymentflag = downpaymentflag;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getTenureremarks() {
		return tenureremarks;
	}

	public void setTenureremarks(String tenureremarks) {
		this.tenureremarks = tenureremarks;
	}

	public String getTenureflag() {
		return tenureflag;
	}

	public void setTenureflag(String tenureflag) {
		this.tenureflag = tenureflag;
	}

	public String getEwioremi() {
		return ewioremi;
	}

	public void setEwioremi(String ewioremi) {
		this.ewioremi = ewioremi;
	}

	public String getEwioremiremarks() {
		return ewioremiremarks;
	}

	public void setEwioremiremarks(String ewioremiremarks) {
		this.ewioremiremarks = ewioremiremarks;
	}

	public String getEwioremiflag() {
		return ewioremiflag;
	}

	public void setEwioremiflag(String ewioremiflag) {
		this.ewioremiflag = ewioremiflag;
	}

	public String getResiadrss() {
		return resiadrss;
	}

	public void setResiadrss(String resiadrss) {
		this.resiadrss = resiadrss;
	}

	public String getResiadrssremarks() {
		return resiadrssremarks;
	}

	public void setResiadrssremarks(String resiadrssremarks) {
		this.resiadrssremarks = resiadrssremarks;
	}

	public String getResiadrssflag() {
		return resiadrssflag;
	}

	public void setResiadrssflag(String resiadrssflag) {
		this.resiadrssflag = resiadrssflag;
	}

	public String getStagechairmanno() {
		return stagechairmanno;
	}

	public void setStagechairmanno(String stagechairmanno) {
		this.stagechairmanno = stagechairmanno;
	}

	public String getStagechairmannoremarks() {
		return stagechairmannoremarks;
	}

	public void setStagechairmannoremarks(String stagechairmannoremarks) {
		this.stagechairmannoremarks = stagechairmannoremarks;
	}

	public String getStagechairmannoflag() {
		return stagechairmannoflag;
	}

	public void setStagechairmannoflag(String stagechairmannoflag) {
		this.stagechairmannoflag = stagechairmannoflag;
	}

	public String getArrangebtwnrider() {
		return arrangebtwnrider;
	}

	public void setArrangebtwnrider(String arrangebtwnrider) {
		this.arrangebtwnrider = arrangebtwnrider;
	}

	public String getArrangebtwnriderremarks() {
		return arrangebtwnriderremarks;
	}

	public void setArrangebtwnriderremarks(String arrangebtwnriderremarks) {
		this.arrangebtwnriderremarks = arrangebtwnriderremarks;
	}

	public String getArrangebtwnriderflag() {
		return arrangebtwnriderflag;
	}

	public void setArrangebtwnriderflag(String arrangebtwnriderflag) {
		this.arrangebtwnriderflag = arrangebtwnriderflag;
	}

	public String getNoofyrinaddrss() {
		return noofyrinaddrss;
	}

	public void setNoofyrinaddrss(String noofyrinaddrss) {
		this.noofyrinaddrss = noofyrinaddrss;
	}

	public String getNoofyrinaddrssremarks() {
		return noofyrinaddrssremarks;
	}

	public void setNoofyrinaddrssremarks(String noofyrinaddrssremarks) {
		this.noofyrinaddrssremarks = noofyrinaddrssremarks;
	}

	public String getNoofyrinaddrssflag() {
		return noofyrinaddrssflag;
	}

	public void setNoofyrinaddrssflag(String noofyrinaddrssflag) {
		this.noofyrinaddrssflag = noofyrinaddrssflag;
	}

	public String getMbnonotinname() {
		return mbnonotinname;
	}

	public void setMbnonotinname(String mbnonotinname) {
		this.mbnonotinname = mbnonotinname;
	}

	public String getMbnonotinnameremarks() {
		return mbnonotinnameremarks;
	}

	public void setMbnonotinnameremarks(String mbnonotinnameremarks) {
		this.mbnonotinnameremarks = mbnonotinnameremarks;
	}

	public String getMbnonotinnameflag() {
		return mbnonotinnameflag;
	}

	public void setMbnonotinnameflag(String mbnonotinnameflag) {
		this.mbnonotinnameflag = mbnonotinnameflag;
	}

	public String getMbnonotinname2() {
		return mbnonotinname2;
	}

	public void setMbnonotinname2(String mbnonotinname2) {
		this.mbnonotinname2 = mbnonotinname2;
	}

	public String getMbnonotinnameremarks2() {
		return mbnonotinnameremarks2;
	}

	public void setMbnonotinnameremarks2(String mbnonotinnameremarks2) {
		this.mbnonotinnameremarks2 = mbnonotinnameremarks2;
	}

	public String getMbnonotinnameflag2() {
		return mbnonotinnameflag2;
	}

	public void setMbnonotinnameflag2(String mbnonotinnameflag2) {
		this.mbnonotinnameflag2 = mbnonotinnameflag2;
	}

	public String getStagechairmanname() {
		return stagechairmanname;
	}

	public void setStagechairmanname(String stagechairmanname) {
		this.stagechairmanname = stagechairmanname;
	}

	public String getStagechairmannameremarks() {
		return stagechairmannameremarks;
	}

	public void setStagechairmannameremarks(String stagechairmannameremarks) {
		this.stagechairmannameremarks = stagechairmannameremarks;
	}

	public String getStagechairmannameflag() {
		return stagechairmannameflag;
	}

	public void setStagechairmannameflag(String stagechairmannameflag) {
		this.stagechairmannameflag = stagechairmannameflag;
	}
	
	
}
