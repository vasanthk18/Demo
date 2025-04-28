package com.ozone.smart.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Profile;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
	
	@Query("SELECT p FROM Profile p WHERE p.profile = :profileName ORDER BY p.category")
	List<Profile> findByProfileName(String profileName);
	
	
	@Query("SELECT DISTINCT p.profile FROM Profile p")
	List<String> findAllByDistintName();
	
	
	 @Modifying
	 @Transactional
	 @Query("DELETE FROM Profile p WHERE p.profile = :profileName AND p.category = :categoryName")
	 int deleteByProfileAndCategory(String profileName, String categoryName);
	 
//	 From Profile where profile = " + "'" + strProfile + "'"
	 
	 
	 
	 
}
