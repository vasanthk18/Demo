package com.ozone.smart.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.UserAudit;

import jakarta.transaction.Transactional;

@Repository
public interface LoginRepo extends JpaRepository<UserAudit, Integer> {
	
//			@Modifying
//			@Transactional
//			@Query("UPDATE UserAudit set logoutdatetime = :outTime WHERE userid = :userId AND logindatetime= :inTime")
//			int updateUserAudit(String userId, String inTime, String outTime);
			
			@Modifying
			@Transactional
			@Query("UPDATE UserAudit ua SET ua.logoutdatetime = :outTime WHERE ua.userid = :userId AND ua.logindatetime = :inTime")
			int updateUserAudit(@Param("userId") String userId, @Param("inTime") String inTime, @Param("outTime") String outTime);


}
