package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.LoanParam;

@Repository
public interface LoanParamRepo extends JpaRepository<LoanParam, String>{

}
