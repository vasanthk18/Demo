package com.ozone.smart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Waiver;

@Repository
public interface WaiverRepo extends JpaRepository<Waiver, Integer> {
	
	List<Waiver> findByproposalNo(String proposalno);

}
