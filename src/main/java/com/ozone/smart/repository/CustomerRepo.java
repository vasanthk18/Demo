package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.CustomerDetails;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerDetails, Integer> {
	
	
	@Query("SELECT cd FROM CustomerDetails cd WHERE cd.id = :customerId AND (:flag IS NULL OR "
            + "(:flag = 'cqc' AND cd.cqc != true) OR "
            + "(:flag = 'fi' AND cd.fiverified != true) OR "
            + "(:flag = 'tv' AND cd.tvverified != true) OR "
            + "(:flag = 'cam' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true) OR "
            + "(:flag = 'cama' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true AND cd.coapproval = 'Recommended') OR "
            + "(:flag = 'all'))")
    CustomerDetails findCustomerByFlag(@Param("customerId") String customerId, @Param("flag") String flag);


	@Modifying
	@Transactional
    @Query("UPDATE CustomerDetails cd SET cd.cqc = :cqc, cd.cqcremarks = :cqcremarks, cd.cqcuser = :cqcuser, cd.cqcdatetime = :cqcdatetime WHERE cd.otherid = :querypart")
    int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks, @Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime, @Param("querypart") String querypart);
	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.dcupload = true WHERE cd.otherid = :otherid")
	int updateCustomerDetailsDcUpload(@Param("otherid") String otherid);
	
	@Query("SELECT cd FROM CustomerDetails cd WHERE cd.otherid IN (SELECT p.customerid FROM Proposal p WHERE p.proposalno = :strCashprop)")
    List<CustomerDetails> findByProposalNo(String strCashprop);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.coapproval = :strCocam , cd.coremarks = :strCocamremarks, cd.camuser =:strLoginuser, cd.camdatetime = :strCAMRequestdatetime WHERE cd.otherid = :strId ")
	int updateCustomerDetails(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	

	
	@Query("SELECT cd FROM CustomerDetails cd " +
	           "WHERE cd.otherid IN " +
	           "(SELECT p.customerid FROM Proposal p " +
	           "WHERE p.proposalno IN " +
	           "(SELECT cv.proposalno FROM CustomerVehicle cv " +
	           "WHERE cv.agreementserial = :stragreementSerial))")
	 List<CustomerDetails> findByAgreementSerial(String stragreementSerial);
	

	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.coapproval = '' , cd.coremarks = '' , cd.tvverified = false, cd.fiverified = false, cd.cqc = false, cd.cqcremarks ='', cd.srremarks = :strCocamremarks WHERE cd.otherid = :strId ")
	int updateCustomerForCQC(String strCocamremarks, String strId);
	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.coapproval = '' , cd.coremarks = '' , cd.tvverified = false, cd.tvremarks = '', cd.srremarks = :strCocamremarks WHERE cd.otherid = :strId ")
	int updateCustomerForTV(String strCocamremarks, String strId);
	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.coapproval = '' , cd.coremarks = '' , cd.fiverified = false, cd.firemarks = '', cd.srremarks = :strCocamremarks WHERE cd.otherid = :strId ")
	int updateCustomerForFI(String strCocamremarks, String strId);
	
	
	@Query("FROM CustomerDetails cd WHERE cd.otherid = :strCustomer " +
		       "AND (:strFlag = 'db' AND cd.tvverified = true AND cd.fiverified = true " +
		       "AND cd.cqc = true AND cd.coapproval = 'Recommended' AND cd.cooapproval = 'Approve') " +
		       "OR (:strFlag = '')")
	List<CustomerDetails> findByCustomerIdAndFlag(String strCustomer, String strFlag);

	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails cd SET cd.cooapproval = :strCocam , cd.cooremarks = :strCocamremarks, cd.camauser =:strLoginuser, cd.camadatetime = :strCAMRequestdatetime WHERE cd.otherid = :strId ")
	int updateCustomerForCoo(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	
	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails c SET c.coapproval = '', c.cooapproval = '', c.revremarks = :strCocamremarks WHERE c.otherid = :strId")
	int updateCustomerForReverse(@Param("strCocamremarks") String strCocamremarks, @Param("strId") String strId);

	
//	 @Query("SELECT cd FROM CustomerDetails cd WHERE (?1 IS NULL OR ?1 = '') OR (?1 IS NOT NULL AND ?1 != '' AND ?1 = cd.arg1) ORDER BY cd.surname")
//	 List<CustomerDetails> findByDynamicQuery(String arg1);

	
	 @Query("SELECT cd FROM CustomerDetails cd ORDER BY cd.capturedatetime DESC")
	 List<CustomerDetails> findAllOrderByCapturedatetimeDesc();
	 
	 @Query("SELECT DISTINCT c FROM CustomerDetails c WHERE c.otherid IN (" +
	           "SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc = true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' " +
	           "AND p.customerid IN (SELECT l.custid FROM Loan l WHERE l.cqc = true AND l.coapproval = 'Recommended' AND l.cooapproval = 'Approve') " +
	           "AND p.proposalno NOT IN (SELECT cv.proposalno FROM CustomerVehicle cv)) " +
	           "ORDER BY c.otherid")
	  List<CustomerDetails> findEligibleCustomers();
	 
	 @Query("SELECT DISTINCT c FROM CustomerDetails c WHERE c.otherid IN( SELECT DISTINCT p.customerid FROM Proposal p WHERE p.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE agreementfilename IS NOT NULL) AND p.proposalno not in (select rl.proposalno from ReleaseLetter rl) order by p.customerid)")
	 List<CustomerDetails> getCustIdForRelease();
	 
	 @Query("SELECT DISTINCT c FROM CustomerDetails c WHERE c.otherid IN (SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc=true AND p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND p.customerid IN (SELECT l.custid FROM Loan l where l.cqc = true and l.coapproval = 'Recommended' and l.cooapproval = 'Approve') AND p.proposalno IN (select cv.proposalno from CustomerVehicle cv WHERE cv.disbursed = false) order by p.customerid)")
	 List<CustomerDetails> getCustForDisburse();
	 
	 
	 
//	 @Query("SELECT cd FROM CustomerDetails cd WHERE cd.dcsubmit = false")
//	 List<CustomerDetails> custNameForDocs();

	
//	 @Query("SELECT cd FROM CustomerDetails cd WHERE "
//	            + "(:flag IS NULL OR "
//	            + "(:flag = 'docs' AND cd.dcsubmit = false) OR "
//	            + "(:flag = 'EDoc' AND cd.dcsubmit = true AND cd.dcupload = false) OR "
//	            + "(:flag = 'AgreementUpload' AND cd.cooapproval = 'Approve' AND "
//	            + "cd.otherid IN (SELECT l.custid FROM Loan l WHERE l.cooapproval = 'Approve' AND "
//	            + "l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.agreementfilename IS NULL OR cv.agreementfilename = ''))) OR "
//	            + "(:flag = 'printSchedule' AND cd.cooapproval = 'Approve' AND "
//	            + "cd.otherid IN (SELECT l.custid FROM Loan l WHERE l.cooapproval = 'Approve' AND "
//	            + "l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true))) OR "
//	            + "(:flag = 'updateCustomer' AND cd.cqc != true) OR "
//	            + "(:flag = 'telephoneVerify' AND cd.tvverified != true AND cd.cqc = true) OR "
//	            + "(:flag = 'fieldVerify' AND cd.fiverified != true AND cd.cqc = true) OR "
//	            + "(:flag = 'CustomerQualityCheck' AND cd.cqc != true OR "
//	            + "cd.otherid IN (SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc != true) OR "
//	            + "cd.otherid IN (SELECT DISTINCT g.custid FROM Guarantor g WHERE g.cqc != true) OR "
//	            + "cd.otherid IN (SELECT DISTINCT l.custid FROM Loan l WHERE l.cqc != true)) OR "
//	            + "(:flag = 'cashReceipt') OR "
//	            + "(:flag = 'micsCashReceipt' AND cd.otherid IN (SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc = true AND "
//	            + "p.coapproval = 'Recommended' AND p.cooapproval = 'Approve' AND "
//	            + "p.customerid IN (SELECT l.custid FROM Loan l WHERE l.cqc = true AND "
//	            + "l.coapproval = 'Recommended' AND l.cooapproval = 'Approve') AND "
//	            + "p.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true) AND "
//	            + "p.proposalno IN (SELECT p.loanid FROM Penalty p WHERE p.status IS NULL OR p.status = '')ORDER BY p.customerid)) OR "
//	            + "(:flag = 'CreditAppraisalMemo' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true AND "
//	            + "(cd.coapproval IS NULL OR cd.coapproval = '') OR "
//	            + "cd.otherid IN (SELECT DISTINCT g.custid FROM Guarantor g WHERE g.cqc = true AND "
//	            + "(g.coapproval IS NULL OR g.coapproval = '')) OR "
//	            + "cd.otherid IN (SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc = true AND "
//	            + "(p.coapproval IS NULL OR p.coapproval = '')) OR "
//	            + "cd.otherid IN (SELECT DISTINCT l.custid FROM Loan l WHERE l.cqc = true AND "
//	            + "(l.coapproval IS NULL OR l.coapproval = ''))) OR "
//	            + "(:flag = 'CAMApproval' AND cd.tvverified = true AND cd.fiverified = true AND cd.cqc = true AND "
//	            + "cd.coapproval = 'Recommended' AND "
//	            + "cd.otherid IN (SELECT p.customerid FROM Proposal p WHERE p.cqc = true AND "
//	            + "p.coapproval = 'Recommended' AND (p.cooapproval IS NULL OR p.cooapproval = '')) AND "
//	            + "cd.otherid IN (SELECT l.custid FROM Loan l WHERE l.cqc = true AND "
//	            + "l.coapproval = 'Recommended' AND (l.cooapproval IS NULL OR l.cooapproval = '')) AND "
//	            + "cd.otherid IN (SELECT g.custid FROM Guarantor g WHERE g.tvverified = true AND "
//	            + "g.fiverified = true AND g.cqc = true AND g.coapproval = 'Recommended')) OR "
//	            + "(:flag = 'PrintCustomerStatement' AND cd.cooapproval = 'Approve' AND "
//	            + "cd.otherid IN (SELECT l.custid FROM Loan l WHERE l.cooapproval = 'Approve' AND "
//	            + "l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true)))) "
//	            + "ORDER BY cd.surname")
//	    List<CustomerDetails> findByFlag(@Param("flag") String flag);
	 
	 @Query("SELECT cd FROM CustomerDetails cd WHERE cd.dcsubmit = false ORDER BY cd.surname")
	 List<CustomerDetails> custNameForDocs();
	   
	 @Query("SELECT cd FROM CustomerDetails cd WHERE cd.dcsubmit = true ORDER BY cd.surname")
	 List<CustomerDetails> custNameForEdocs();
	 
	 @Query("SELECT cd FROM CustomerDetails cd WHERE cd.dcsubmit = true AND cd.dcupload = true ORDER BY cd.surname")
	 List<CustomerDetails> custNameForDwdDocuments();

	 @Query("SELECT cd FROM CustomerDetails cd " +
		       "WHERE cd.cooapproval = 'Approve' " +
		       "AND EXISTS (SELECT 1.custid FROM Loan l " +
		       "WHERE l.cooapproval = 'Approve' " +
		       "AND l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.agreementfilename IS NULL OR cv.agreementfilename = '') " +
		       "AND cd.otherid = l.custid) ORDER BY cd.surname")
		List<CustomerDetails> custNameForAgupload();
	 
	    
	 @Query("SELECT cd FROM CustomerDetails cd " +
		       "WHERE cd.cooapproval = 'Approve' " +
		       "AND EXISTS (SELECT 1 FROM Loan l " +
		                   "WHERE l.cooapproval = 'Approve' " +
		                   "AND l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true) " +
		                   "AND cd.otherid = l.custid) ORDER BY cd.surname")
		List<CustomerDetails> custNameForPrintSchedule();

	    @Query("SELECT cd FROM CustomerDetails cd WHERE cd.cqc != true ORDER BY cd.surname")
	    List<CustomerDetails> custNameForUpCus();
	    
	    
	    @Query("SELECT cd FROM CustomerDetails cd WHERE cd.tvverified != true AND cd.cqc = true ORDER BY cd.surname ")
	    List<CustomerDetails> custNameForTelVerify();

	    
	    @Query("SELECT cd FROM CustomerDetails cd WHERE cd.fiverified != true AND cd.cqc = true ORDER BY cd.surname")
	    List<CustomerDetails> custNameForFieldVerify();
	    
	    @Query("SELECT cd FROM CustomerDetails cd " +
	    	       "WHERE cd.cqc != true " +
	    	       "OR cd.otherid IN (SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc != true) " +
	    	       "OR cd.otherid IN (SELECT DISTINCT g.custid FROM Guarantor g WHERE g.cqc != true) " +
	    	       "OR cd.otherid IN (SELECT DISTINCT l.custid FROM Loan l WHERE l.cqc != true) ORDER BY cd.surname")
	    	List<CustomerDetails> custNameForCusQcheck();
	    
	    @Query("SELECT cd FROM CustomerDetails cd ORDER BY cd.surname")
		 List<CustomerDetails> custNameForCashReci();
	    
		@Query("SELECT cd FROM CustomerDetails cd " +
			       "WHERE cd.otherid IN (" +
			       "   SELECT DISTINCT p.customerid FROM Proposal p " + // Alias 'p' for Proposal entity
			       "   WHERE p.cqc = true " +
			       "   AND p.coapproval = 'Recommended' " +
			       "   AND p.cooapproval = 'Approve' " +
			       "   AND p.customerid IN (" +
			       "       SELECT l.custid FROM Loan l WHERE l.cqc = true AND l.coapproval = 'Recommended' AND l.cooapproval = 'Approve'" +
			       "   ) " +
			       "   AND p.proposalno IN (" +
			       "       SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true" +
			       "   ) " +
			       "   AND p.proposalno IN (" +
			       "       SELECT pl.loanid FROM Penalty pl WHERE pl.status IS NULL OR pl.status = '') ORDER BY p.customerid) ORDER BY cd.surname") // Ensure 'p' is used to reference attributes of the Proposal entity
			  	List<CustomerDetails> custNameForMCashReceipt();
	    
	    
	    @Query("SELECT cd FROM CustomerDetails cd " + 
	    	       "WHERE cd.tvverified = true " + 
	    	       "AND cd.fiverified = true " + 
	    	       "AND cd.cqc = true " +
	    	       "AND (cd.coapproval IS NULL OR cd.coapproval = '') OR cd.otherid IN "
	    	       + "(SELECT DISTINCT g.custid FROM Guarantor g  WHERE g.cqc = true " + 
	    	       "   AND (g.coapproval IS NULL OR g.coapproval = '')) OR cd.otherid IN (" + 
	    	       "   SELECT DISTINCT p.customerid FROM Proposal p WHERE p.cqc = true " + 
	    	       "   AND (p.coapproval IS NULL OR p.coapproval = '')) OR cd.otherid IN (" + 
	    	       "   SELECT DISTINCT l.custid FROM Loan l WHERE l.cqc = true " + 
	    	       "   AND (l.coapproval IS NULL OR l.coapproval = '')" + 
	    	       ") ORDER BY cd.surname")
	    	List<CustomerDetails> custNameForCreAppMemo();
	    
	    @Query("SELECT cd FROM CustomerDetails cd " +
	    	       "WHERE cd.tvverified = true " +
	    	       "AND cd.fiverified = true " +
	    	       "AND cd.cqc = true " +
	    	       "AND cd.coapproval = 'Recommended' " +
	    	       "AND cd.otherid IN (" +
	    	       "   SELECT p.customerid FROM Proposal p " +
	    	       "   WHERE p.cqc = true " +
	    	       "   AND p.coapproval = 'Recommended' " +
	    	       "   AND (p.cooapproval IS NULL OR p.cooapproval = '')" +
	    	       ") " +
	    	       "AND cd.otherid IN (" +
	    	       "   SELECT l.custid FROM Loan l " +
	    	       "   WHERE l.cqc = true " +
	    	       "   AND l.coapproval = 'Recommended' " +
	    	       "   AND (l.cooapproval IS NULL OR l.cooapproval = '')" +
	    	       ") " +
	    	       "AND cd.otherid IN (" +
	    	       "   SELECT g.custid FROM Guarantor g " +
	    	       "   WHERE g.tvverified = true " +
	    	       "   AND g.fiverified = true " +
	    	       "   AND g.cqc = true " +
	    	       "   AND g.coapproval = 'Recommended'" +
	    	       ") ORDER BY cd.surname")
	    	List<CustomerDetails> custNameForCAMApprove();
	    
	    @Query("SELECT cd FROM CustomerDetails cd WHERE cd.cooapproval = 'Approve' AND "
	            + "cd.otherid IN (SELECT l.custid FROM Loan l WHERE l.cooapproval = 'Approve' AND "
	            + "l.proposalno IN (SELECT cv.proposalno FROM CustomerVehicle cv WHERE cv.disbursed = true)) "
	            + "ORDER BY cd.surname")
	    List<CustomerDetails> custNameForPrntCustStat();
	    
	    @Query("SELECT cd FROM CustomerDetails cd ORDER BY cd.surname")
	    List<CustomerDetails> custBySurname();
	
	
//	    @Query("SELECT c FROM CustomerDetails c WHERE c.otherid = :customerId")
//	    CustomerDetails findByOtherId(@Param("customerId") String customerId);
	    
	    CustomerDetails findByotherid(String CustId);

}
