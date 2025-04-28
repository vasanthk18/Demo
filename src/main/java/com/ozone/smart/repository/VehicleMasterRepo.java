package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.VehicleMaster;

@Repository
public interface VehicleMasterRepo extends JpaRepository<VehicleMaster, Integer>{
	
	@Query("SELECT vm FROM VehicleMaster vm WHERE CONCAT(vm.brand, ' | ', vm.model, ' | ', vm.cc, ' | ', vm.color) = :strVehicle")
	List<VehicleMaster> findByBrandModelCcAndColor(@Param("strVehicle") String strVehicle);


}
