package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.vwGuarantors;

@Repository
public interface VWGuaranRepo extends JpaRepository<vwGuarantors, String> {
	
	List<vwGuarantors> findBycustid(String strCustid);

}
