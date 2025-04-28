package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.VehicleMaster;

@Repository
public interface ProposalRepo extends JpaRepository<Proposal, Integer> {
	
	@Query("SELECT vm FROM VehicleMaster vm WHERE CONCAT(vm.brand, ' | ', vm.model, ' | ', vm.cc, ' | ', vm.color) = :strVehicleregno")
    List<VehicleMaster> findByBrandModelCcColor(@Param("strVehicleregno") String strVehicleregno);
	
	@Query("SELECT p FROM Proposal p WHERE p.customerid = :strCustomerid AND p.capturedatetime = :strRequestdatetime")
    List<Proposal> findByCustIdAndCaptureDT(@Param("strCustomerid") String strCustomerid, @Param("strRequestdatetime") String strRequestdatetime);

	@Query("SELECT p FROM Proposal p WHERE p.customerid = :customerId AND p.capturedatetime LIKE %:truncReqDateTime%")
	List<Proposal> findByCustIdAndCaptureDTLike(@Param("customerId") String customerId, @Param("truncReqDateTime") String truncReqDateTime);
	
	Proposal findByproposalno(String ProposalNo);
	
	@Query("SELECT p FROM Proposal p " +
	        "WHERE p.customerid = :customerId AND " +
	        "(:flag IS NULL OR " +
	        "(:flag = 'cqc' AND (p.cqc != true OR p.proposalno IN (SELECT l.proposalno FROM Loan l WHERE l.cqc != true AND l.custid = :customerId))) OR "  +
//	        "(:flag = 'fi' AND p.fiverified != true) OR " +
//	        "(:flag = 'tv' AND p.tvverified != true) OR " +
	        "(:flag = 'cam' AND (p.cqc = true AND (p.coapproval IS NULL OR p.coapproval = '') OR p.proposalno IN (SELECT DISTINCT l.proposalno FROM Loan l WHERE l.cqc = true AND (l.coapproval IS NULL OR l.coapproval = '') AND l.custid = :customerId))) OR " +
	        "(:flag = 'cama' AND (p.cqc = true AND p.coapproval = 'Recommended' AND (p.cooapproval IS NULL OR p.cooapproval = '') OR p.proposalno IN (SELECT l.proposalno FROM Loan l WHERE l.cqc = true AND l.coapproval = 'Recommended' AND (l.cooapproval IS NULL OR l.cooapproval = '') AND l.custid = :customerId))) OR "+
	        "(:flag = 'va' AND p.cqc = true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND p.customerid IN (SELECT l.custid FROM Loan l WHERE l.cqc = true AND l.coapproval = 'Recommended' AND l.cooapproval = 'Approve') AND p.proposalno NOT IN (SELECT cv.proposalno FROM CustomerVehicle cv)) OR " +
	        "(:flag = 'db' AND p.cqc = true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND p.customerid IN (SELECT l.custid FROM Loan l WHERE l.cqc = true AND l.coapproval = 'Recommended' AND l.cooapproval = 'Approve') AND p.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = false)) OR " +
	        "(:flag = 'rl' AND p.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.agreementfilename IS NOT NULL)) OR " +
	        "(:flag = 'imp' AND p.proposalno IN (SELECT i.proposalno FROM Impoundedstock i)) OR " +
	        "(:flag = 'all'))" +
	        "ORDER BY p.proposalno DESC")
    List<Proposal> findByCustomerWithFlag(String customerId, String flag);

	@Modifying
	@Transactional
    @Query("UPDATE Proposal cd SET cd.cqc = :cqc, cd.cqcremarks = :cqcremarks, cd.cqcuser = :cqcuser, cd.cqcdatetime = :cqcdatetime WHERE cd.proposalno = :querypart")
    int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks, @Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime, @Param("querypart") String querypart);
	
	@Modifying
	@Transactional
	@Query("UPDATE Proposal p SET p.amount = :strLnamount, p.downamount = :strDp, p.vehicleregno = :strVehicle, " +
	       "p.discount = :strDiscount, p.cqcuser = :strLoginuser, p.cqcdatetime = :strCQCRequestdatetime " +
	       "WHERE p.proposalno = :strId")
	int updateProposal(@Param("strLnamount") String strLnamount, @Param("strDp") String strDp, 
	                    @Param("strVehicle") String strVehicle, @Param("strDiscount") String strDiscount, 
	                    @Param("strLoginuser") String strLoginuser, 
	                    @Param("strCQCRequestdatetime") String strCQCRequestdatetime, 
	                    @Param("strId") String strId);

	@Modifying
	@Transactional
	@Query("UPDATE Proposal p SET p.coapproval = :strCocam , p.coremarks = :strCocamremarks, p.camuser =:strLoginuser, p.camdatetime = :strCAMRequestdatetime WHERE p.proposalno = :strId ")
	int updateProposalForCam(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	
	
	@Query("FROM Proposal WHERE proposalno = :strProposalno" +
		       " AND (:strFlag IS NULL OR " +
		       "(:strFlag = 'db' AND cqc = true AND coapproval = 'Recommended' AND cooapproval = 'Approve'))")
	List<Proposal> findByProposalWithFlag(@Param("strProposalno") String strProposalno, @Param("strFlag") String strFlag);

	
	@Modifying
	@Transactional
	@Query("UPDATE Proposal p SET p.coapproval = '' , p.coremarks = '', p.cqc = false, p.cqcremarks = '', p.srremarks = :strCocamremarks WHERE p.proposalno = :strId ")
	int updateProposals(String strCocamremarks, String strId);
	
	
//	"UPDATE Proposal set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";
	
	@Modifying
	@Transactional
	@Query("UPDATE Proposal p SET p.cooapproval = :strCocam , p.cooremarks = :strCocamremarks, p.camauser =:strLoginuser, p.camadatetime = :strCAMRequestdatetime WHERE p.proposalno = :strProp ")
	int updatePropForCoo(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strProp);

	@Modifying
	@Transactional
	@Query("UPDATE Proposal p SET p.coapproval = '', p.cooapproval = '', p.revremarks = :strCocamremarks WHERE p.proposalno = :strProp")
	int updateProposalForReverse(@Param("strCocamremarks") String strCocamremarks, @Param("strProp") String strProp);

//	"UPDATE Proposal set cooapproval = '" + strCocam + "', cooremarks = '" + strCocamaremarks + "', camauser = '" + strLoginuser + "', camadatetime = '" + strCAMARequestdatetime + "' where " + strQuerypart + "";
	
//	@Modifying
//	@Transactional
//	@Query("UPDATE Proposal p SET p.cooapproval = '', p.cooapproval = '', p.revremarks = :strCocamremarks WHERE p.proposalno = :strProp")
//	int updateProposalForDp(@Param("strCocamremarks") String strCocamremarks, @Param("strProp") String strProp);
	
	List<Proposal> findBycustomerid(String strCustomerid);
//	From Proposal where proposalno not in (select proposalno from Loan)	order by proposalno desc
	
	@Query("SELECT p FROM Proposal p WHERE p.proposalno NOT IN (SELECT l.proposalno FROM Loan l) ORDER BY p.proposalno DESC")
	List<Proposal> findProposalNotInLoan();
	
	
	@Query("SELECT DISTINCT p.customerid FROM Proposal p WHERE p.proposalno IN (SELECT i.proposalno FROM Impoundedstock i) ORDER BY p.customerid")
    List<String> findDistinctCustomerIdWithImpoundedStock();
	
	
	@Query("SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc=true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND p.customerid IN (SELECT l.custid FROM Loan l where l.cqc = true and l.coapproval = 'Recommended' and l.cooapproval = 'Approve') AND p.proposalno not in (select cv.proposalno from CustomerVehicle cv) order by p.customerid")
	List<String> getCustId();
	

//	"select distinct customerid From Proposal where cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve' and " + 
//	"customerid in (select custid from Loan where cqc = true and coapproval = 'Recommended' and cooapproval = 'Approve') and " + 
//	"proposalno in (select proposalno from CustomerVehicle where disbursed = false) order by customerid";

	@Query("SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc=true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND p.customerid IN (SELECT l.custid FROM Loan l where l.cqc = true and l.coapproval = 'Recommended' and l.cooapproval = 'Approve') AND p.proposalno IN (select cv.proposalno from CustomerVehicle cv WHERE cv.disbursed = false) order by p.customerid")
	List<String> getCustForDisburse();
	
	@Query("SELECT DISTINCT p.customerid FROM Proposal p WHERE p.proposalno IN (SELECT imp.proposalno FROM Impoundedstock imp WHERE imp.status != 'PAID') ORDER BY p.customerid")
    List<String> custNameForPartCashReci();
	
//	From Proposal where customerid = '" + strCustid + "' order by proposalno
	
	@Query("SELECT p FROM Proposal p WHERE customerid =:strCustid ORDER BY p.proposalno")
	List<Proposal> getProposalByCustId(String strCustid);
	

	@Query("SELECT p FROM Proposal p WHERE p.proposalno = " +
		       "(SELECT MAX(subProposal.proposalno) FROM Proposal subProposal WHERE subProposal.customerid = :strCustid)")
		Proposal getLatestBikeProposalByCustId(@Param("strCustid") String strCustid);

	

}
