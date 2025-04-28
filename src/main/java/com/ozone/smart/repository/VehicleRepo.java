package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, String> {
//	From Vehicle where dealer = " + "'" + strDealer + "' and allocated = false
	@Query("SELECT v FROM Vehicle v WHERE dealer = :dealer AND allocated = false")
	List<Vehicle> findByDealerAndAllocation(String dealer);
	
	@Modifying
	@Transactional
	@Query("UPDATE Vehicle v SET v.allocated = true WHERE v.regno = :regno")
	int updateAllocatedStatus(String regno);

}
