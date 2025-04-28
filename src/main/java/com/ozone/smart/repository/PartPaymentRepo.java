package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.PartPayment;

@Repository
public interface PartPaymentRepo extends JpaRepository<PartPayment, Integer> {
	
	@Query("SELECT pp FROM PartPayment pp WHERE proposalno = :strCashprop AND transactionref = :strTranref AND amount = :strCashamount")
	List<PartPayment> findByParameters(String strCashprop, String strTranref, String strCashamount);
	
    @Query("SELECT MAX(pp.partreceipt) FROM PartPayment pp")
    String findMaxPartReceipt();
	   



}
