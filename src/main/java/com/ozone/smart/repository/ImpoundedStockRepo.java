package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Impoundedstock;

@Repository
public interface ImpoundedStockRepo extends JpaRepository<Impoundedstock, String> {
	
	List<Impoundedstock>findByproposalno(String strProposalno);

	@Modifying
	@Transactional
	@Query("UPDATE Impoundedstock SET vehphotofilename = :strUploadfilename WHERE agreementno = :strAgreement")
	int updateImpound(String strUploadfilename, String strAgreement);
	
	@Query("SELECT is FROM Impoundedstock is WHERE is.agreementno NOT IN (SELECT r.agreementno FROM Reposession r) ")
	List<Impoundedstock> getAgree();
	
//	From Impoundedstock where proposalno not in " + 
//	"(select proposalno from Loan where auctioned = true or reposessed = true or preclosed = true or normalclosure= true)

	@Query("SELECT is FROM Impoundedstock is WHERE is.proposalno NOT IN (SELECT l.proposalno FROM Loan l WHERE l.auctioned = true OR l.reposessed = true OR l.normalclosure = true)")
	List<Impoundedstock> getAgreeNo();
	
	
	@Query("SELECT imp FROM Impoundedstock imp WHERE imp.agreementno = :strAgreementno")
	List<Impoundedstock> getByAgreeNo(String strAgreementno);
	
	
	
	
	
}
