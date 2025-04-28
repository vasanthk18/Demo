package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.vwRejcustomers;

@Repository
public interface vwRejCustRepo extends JpaRepository<vwRejcustomers, String> {

}
