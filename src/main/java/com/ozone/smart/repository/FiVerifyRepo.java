package com.ozone.smart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.FiVerification;


@Repository
public interface FiVerifyRepo extends JpaRepository<FiVerification, String>{
	
	@Query("SELECT fi FROM FiVerification fi WHERE fi.fiid = :customerId ")
//			+ "AND (:flag = 'cam') OR "
//          + "(:flag = 'fi' AND fi.fiverified != true) OR "
//          + "(:flag = 'fi'AND fi.fiverified != true) OR "
//          + "(:flag = 'cama') OR "
//          + "(:flag = 'all')")
  List<FiVerification> findFiVerificationByCust(@Param("customerId") String customerId);

	Optional<FiVerification> findBynationalid(String CustId);
	
	@Query("SELECT fi FROM FiVerification fi WHERE fi.fiid = :customerId ")
	FiVerification findFiVerificationBynationalid(@Param("customerId") String customerId);
}
