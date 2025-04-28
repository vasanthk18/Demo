package com.ozone.smart.repository;

import java.util.List;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Auctionsale;
import com.ozone.smart.entity.Reposession;
import com.ozone.smart.entity.Reposession;

@Repository
public interface AuctionSaleRepo extends JpaRepository<Auctionsale, String> {
	
//	strQuery3 = "From Auctionsale where agreementno = '" + strAgreementno + "'";
//	strQuery3 = "From Auctionsale where agreementno = '" + strAgreementno + "'";
	
	@Query("SELECT a FROM Auctionsale a WHERE a.agreementno =:strAgreementno")
	List<Auctionsale> findByAgree(String strAgreementno);

}
