package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Dealer;

@Repository
public interface DealerRepo extends JpaRepository<Dealer, String> {

}
