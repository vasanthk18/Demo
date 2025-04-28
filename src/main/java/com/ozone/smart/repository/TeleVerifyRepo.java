package com.ozone.smart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.RiderDetails;
import com.ozone.smart.entity.TvVerification;

@Repository
public interface TeleVerifyRepo extends JpaRepository<TvVerification, String> {
	
	@Query("SELECT tv FROM TvVerification tv WHERE tv.tvid = :customerId ")
//			+ "AND (:flag = 'cam') OR "
////            + "(:flag = 'fi' AND tv.fiverified != true) OR "
////            + "(:flag = 'tv'AND tv.tvverified != true) OR "
//            + "(:flag = 'cama') OR "
//            + "(:flag = 'all')")
    List<TvVerification> findTvVerificationByCust(@Param("customerId") String customerId);
	
	Optional<TvVerification> findBynationalid(String CustId);


	@Query("SELECT tv FROM TvVerification tv WHERE tv.tvid = :customerId ")
    TvVerification findTvVerificationBynationalid(@Param("customerId") String customerId);
}
