package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.LoanPayments;

@Repository
public interface LoanPaymentRepo extends JpaRepository<LoanPayments, Integer> {
		

	 @Query("SELECT SUM(CAST(lp.amount AS double)) AS totalcleared FROM LoanPayments lp WHERE lp.loanid = :proposalno")
	 Double getTotalClearedAmountByProposal(String proposalno);
	

}
