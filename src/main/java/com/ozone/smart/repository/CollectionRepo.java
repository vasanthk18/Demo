package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Collection;


public interface CollectionRepo extends JpaRepository<Collection, Integer> {
	
	Collection findByreceiptid(String receiptid);
	
	@Modifying
	@Transactional
	@Query("UPDATE Collection c SET c.transferstatus = true WHERE c.date BETWEEN :fromDate AND :toDate")
	int updateTransferStatus(@Param("fromDate") String fromDate, @Param("toDate") String toDate);



}
