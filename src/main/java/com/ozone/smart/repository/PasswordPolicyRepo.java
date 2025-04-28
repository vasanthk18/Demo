package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.PasswordPolicy;

@Repository
public interface PasswordPolicyRepo extends JpaRepository<PasswordPolicy, Integer> {

}
