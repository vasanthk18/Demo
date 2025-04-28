package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.LoanStatus;

@Repository
public interface LoanStatusRepo extends JpaRepository<LoanStatus, String> {
	
	
	List<LoanStatus> findByagreementno(String strAgreementno);
	
	@Query("SELECT ls FROM LoanStatus ls WHERE ls.overdueinst > 3 AND ls.agreementno NOT IN (SELECT i.agreementno FROM Impoundedstock i) ORDER BY ls.agreementno")
	List<LoanStatus> findloanStatus();

}
