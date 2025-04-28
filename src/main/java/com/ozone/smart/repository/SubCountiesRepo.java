package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.SubCounties;

@Repository
public interface SubCountiesRepo extends JpaRepository<SubCounties, String>{

//	"From SubCounties where id like '" + strCountyid + "' ORDER BY id"
	@Query("SELECT c FROM SubCounties c WHERE c.id LIKE %:countyId% ORDER BY c.id")
	List<SubCounties> findByCountyId(String countyId);
	
//	@Query("DELETE FROM SubCounties s where trim(s.id) = :Id")
//    void deleteByTrimmedId(@Param("Id") String Id);
	
//	@Query("UPDATE SubCounties s set s.countyname = :countyName, s.subcountyname = :subCountyName where trim(s.id) = :Id")
//    void updateSubCountyById(@Param("countyName") String countyName, @Param("subCountyName") String subCountyName, @Param("Id") String Id);

	@Query("SELECT c FROM SubCounties c WHERE UPPER(c.countyname) = :countyName AND UPPER(c.subcountyname) LIKE CONCAT('%',:subCountyName, '%') ORDER BY c.subcountyname")
	List<SubCounties> findByCntyAndSubCntyName(String countyName, String subCountyName);
	

}
