package com.ozone.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.DocSubmission;

@Repository
public interface DocSubmitRepo extends JpaRepository<DocSubmission, String> {
	
	@Modifying
	@Transactional
	@Query("UPDATE DocSubmission d SET d.filename = :filename, d.updatetime = :updatetime, d.upuser = :upuser WHERE d.custid = :custid")
	int updateDocSubmission(@Param("filename") String filename, @Param("updatetime") String updatetime, @Param("upuser") String upuser, @Param("custid") String custid);

//	FROM docsubmission where custid = '" + strCustid + "'";"
	
	@Query("SELECT d FROM DocSubmission d WHERE d.custid = :strCustid")
	DocSubmission getDocByCustId(String strCustid);
//	FROM docsubmission where custid = '" + strCustid + "'";"
	

}
