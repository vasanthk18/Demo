package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Reposession;

@Repository
public interface ReposessionRepo extends JpaRepository<Reposession, String> {

	@Query("SELECT r FROM Reposession r WHERE r.status = 'INITIATED' AND r.customerid = :customerId AND (r.newproposalid IS NULL OR r.newproposalid = '')")
    List<Reposession> findInitiatedReposessionsByCustomerId(String customerId);
	
	
	List<Reposession> findByproposalno(String proposal);
	
//	From Reposession where agreementno = '" + strAgreementno + "'
	
	@Query("SELECT a FROM Reposession a WHERE a.agreementno =:strAgreementno")
	List<Reposession> findByAgree(String strAgreementno);

}
