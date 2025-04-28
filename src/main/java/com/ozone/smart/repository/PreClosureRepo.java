package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Auctionsale;
import com.ozone.smart.entity.Preclosure;

@Repository
public interface PreClosureRepo extends JpaRepository<Preclosure, String>{
		
//	"From Preclosure where agreementno = '" + strAgreementno + "'"
	
	@Query("SELECT a FROM Preclosure a WHERE a.agreementno =:strAgreementno")
	List<Preclosure> findByAgree(String strAgreementno);

}
