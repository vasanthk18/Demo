package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Ptp;

@Repository
public interface PtpRepo extends JpaRepository<Ptp, Integer> {
	
	List<Ptp>findByagreementno(String strAgreement);

}
