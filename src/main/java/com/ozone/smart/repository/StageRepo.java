package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Stage;


@Repository
public interface StageRepo extends JpaRepository<Stage, Integer> {
		
	@Query("SELECT s FROM Stage s WHERE UPPER(s.name) LIKE CONCAT('%', UPPER(:name), '%') ORDER BY s.name")
    List<Stage> findByNameContainingIgnoreCase(String name);
}
