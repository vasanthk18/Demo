package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Counties;

@Repository
public interface CountyRepo extends JpaRepository<Counties, String> {
	
	@Query("SELECT c FROM Counties c WHERE c.id LIKE %:districtId% ORDER BY c.id")
    List<Counties> findByDistrictIdLikeOrderByDistrictId(String districtId);
	
	 @Query("SELECT c FROM Counties c WHERE UPPER(c.districtname) = :districtName AND UPPER(c.countyname) LIKE CONCAT('%', UPPER(:countyName), '%') ORDER BY c.countyname")
	 List<Counties> findByDistrictNameAndCountyName(String districtName, String countyName);
	 
	 @Query("SELECT c FROM Counties c ORDER BY c.countyname ASC")
	 List<Counties> findAllByAsc();


}
