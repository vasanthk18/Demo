package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Guarantor;

@Repository
public interface GuarantorRepo extends JpaRepository<Guarantor, Integer> {
	
	List<Guarantor> findBynationalid(String GuarantorId);
	
	@Query("SELECT ss FROM Guarantor ss WHERE UPPER(ss.surname)= :sureName AND UPPER(ss.stagename) = :stageName")
	List<Guarantor> findBySurAndStageName(String sureName, String stageName);
	
	@Query("SELECT g FROM Guarantor g WHERE g.custid = :customerId " +
            "AND (:flag = 'cqc' AND g.cqc != true " +
            "OR :flag = 'fi' AND g.fiverified != true " +
            "OR :flag = 'tv' AND g.tvverified != true " +
            "OR :flag = 'cam' AND g.tvverified = true AND g.fiverified = true AND g.cqc = true " +
            "OR :flag = 'cama' AND g.tvverified = true AND g.fiverified = true AND g.cqc = true AND g.coapproval = 'Recommended' " +
            "OR :flag = 'all')")
    List<Guarantor> findByCustomerIdAndFlag(String customerId, String flag);
	
	List<Guarantor> findBycustid(String custId);

	@Modifying
	@Transactional
    @Query("UPDATE Guarantor cd SET cd.cqc = :cqc, cd.cqcremarks = :cqcremarks, cd.cqcuser = :cqcuser, cd.cqcdatetime = :cqcdatetime WHERE cd.id = :querypart")
    int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks, @Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime, @Param("querypart") String querypart);
	
	
	@Query(nativeQuery=true, value="SELECT * FROM tblguarantor g WHERE exists (SELECT * FROM tbldocsubmission d WHERE d.custid = g.custid AND d.custid = :custid)")
	List<Guarantor> findGuarantorsByDocSubmissionCustId(@Param("custid") String custid);
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.coapproval = :strCocam , g.coremarks = :strCocamremarks, g.camuser =:strLoginuser, g.camdatetime = :strCAMRequestdatetime WHERE g.id = :strId ")
	int updateGuarantor(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);

//	int updateGuarantorForCQC(String strCocamremarks);
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.coapproval = '' , g.coremarks = '' , g.tvverified = false, g.fiverified = false, g.cqc = false, g.cqcremarks ='', g.srremarks = :strCocamremarks WHERE g.custid = :strId ")
	int updateGuarantorForCQC(String strCocamremarks, String strId);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.coapproval = '' , g.coremarks = '' , g.tvverified = false, g.tvremarks = '', g.srremarks = :strCocamremarks WHERE g.custid = :strId ")
	int updateGuarantorForTV(String strCocamremarks, String strId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.coapproval = '' , g.coremarks = '' , g.fiverified = false, g.firemarks = '', g.srremarks = :strCocamremarks WHERE g.custid = :strId ")
	int updateGuarantorForFI(String strCocamremarks, String strId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.cooapproval = :strCocam , g.cooremarks = :strCocamremarks, g.camauser =:strLoginuser, g.camadatetime = :strCAMRequestdatetime WHERE g.id = :strId ")
	int updateGuarantorForCoo(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Guarantor g SET g.coapproval = '', g.cooapproval = '', g.revremarks = :strCocamremarks WHERE g.id = :strId")
	int updateGuaranForReverse(@Param("strCocamremarks") String strCocamremarks, @Param("strId") String strId);

	
	
	@Query("SELECT g FROM Guarantor g WHERE g.custid = :custid ORDER BY g.firstguarantor DESC")
    List<Guarantor> findByCustidOrderByFirstguarantorDesc(@Param("custid") String custid);

	
	
}
