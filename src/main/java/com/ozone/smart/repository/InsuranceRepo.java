package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Insurance;

@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, String>{

}
