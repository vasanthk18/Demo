package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.vwProposal;

@Repository
public interface VmProposalRepo extends JpaRepository<vwProposal, String> {
	
	List<vwProposal>findByagreementno(String strAgreement);
	
	@Query("SELECT vwp FROM vwProposal vwp WHERE vwp.dateofrelease IS NOT NULL ORDER BY vwp.agreementno")
	List<vwProposal> findByDateRelease();
	
//	@Query("SELECT vwp FROM vwProposal vwp WHERE vwp.dateofrelease IS NOT NULL ORDER BY vwp.agreementno")
//	List<vwProposal> findByDateOfRelease();

}
