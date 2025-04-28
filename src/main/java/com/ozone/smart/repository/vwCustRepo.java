package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.vwCustomer;

@Repository
public interface vwCustRepo extends JpaRepository<vwCustomer, String> {
	
	List<vwCustomer> findByotherid(String custId);

}
