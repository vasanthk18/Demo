package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Districts;

@Repository
public interface DistrictRepo extends JpaRepository<Districts, String>{
	
	

}
