package com.ozone.smart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.CustomerDetails;
import com.ozone.smart.entity.RiderDetails;

@Repository
public interface RiderRepo extends JpaRepository<RiderDetails, String> {

	Optional<RiderDetails> deleteBynationalid(String id);

	@Query("SELECT r FROM RiderDetails r ORDER BY r.firstname")
	List<RiderDetails> RiderNameForUpdateRider();

	@Query("SELECT rr FROM RiderDetails rr WHERE rr.proposalid = :proposalId AND " +
		       "((:flag IS NULL OR :flag = 'all') OR " +
		       "(:flag = 'cqc' AND rr.cqc != true) OR " + 
		       "(:flag = 'tv' AND rr.tvverified != true) OR " +
		       "(:flag = 'fi' AND rr.fiverified != true) OR " +
	           "(:flag = 'cam' AND rr.tvverified = true AND rr.fiverified = true AND rr.cqc = true))")
		RiderDetails findRiderDetailsByFlag(@Param("proposalId") String proposalId, @Param("flag") String flag);


	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.cqc = :cqc, rd.cqcremarks = :cqcremarks, rd.cqcuser = :cqcuser, rd.cqcdatetime = :cqcdatetime WHERE rd.proposalid = :querypart")
	int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks,
			@Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime,
			@Param("querypart") String querypart);

	RiderDetails findBynationalid(String CustId);
	
	@Query("SELECT r.nationalid FROM RiderDetails r WHERE r.proposalid = :ProposalId")
	String findRiderNationalId(@Param("ProposalId") String ProposalId);

	
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rr SET rr.coapproval = :strCocam , rr.coremarks = :strCocamremarks, rr.camuser =:strLoginuser, rr.camdatetime = :strCAMRequestdatetime WHERE rr.proposalid = :strId ")
	int updateCamRiderDetails(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	
	
	//for reverting the rider from cam to CQC
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.coapproval = '' , rd.coremarks = '' , rd.tvverified = false, rd.fiverified = false, rd.cqc = false, rd.cqcremarks ='', rd.srremarks = :strCocamremarks WHERE rd.proposalid = :strProposalid ")
	int updateRiderForCQC(String strCocamremarks, String strProposalid);
	
	
	//for reverting the rider from cam  to TV
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.coapproval = '' , rd.coremarks = '' , rd.tvverified = false, rd.tvremarks = '', rd.srremarks = :strCocamremarks WHERE rd.proposalid = :strProposalid ")
	int updateRiderForTV(String strCocamremarks, String strProposalid);
	
	//for reverting the rider from cam  to FI
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.coapproval = '' , rd.coremarks = '' , rd.fiverified = false, rd.firemarks = '', rd.srremarks = :strCocamremarks WHERE rd.proposalid = :strProposalid ")
	int updateRiderForFI(String strCocamremarks, String strProposalid);
	
	//for updating rider from cam to cam approval
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.cooapproval = :strCocam , rd.cooremarks = :strCocamremarks, rd.camauser =:strLoginuser, rd.camadatetime = :strCAMRequestdatetime WHERE rd.proposalid = :strProp ")
	int updateRiderForCoo(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strProp);
	
	//for reverting the rider from cam approval to cam
	@Modifying
	@Transactional
	@Query("UPDATE RiderDetails rd SET rd.coapproval = '', rd.cooapproval = '', rd.revremarks = :strCocamremarks WHERE rd.proposalid = :strProp")
	int updateRiderForReverse(@Param("strCocamremarks") String strCocamremarks, @Param("strProp") String strProp);
}
