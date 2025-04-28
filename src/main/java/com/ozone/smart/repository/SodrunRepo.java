package com.ozone.smart.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.Sodrun;

@Repository
public interface SodrunRepo extends JpaRepository<Sodrun, Date> {
	
//	@Query("Select s From Sodrun s where s.rundate = :strnewToday")
//	List<Sodrun> findByrundate(String strnewToday);
	
	@Query("SELECT COUNT(m) > 0 FROM Sodrun m WHERE m.rundate = :date")
    boolean existsByDateField(Date date);

}
