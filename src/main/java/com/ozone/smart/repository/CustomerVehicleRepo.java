package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.CustomerVehicle;

@Repository
public interface CustomerVehicleRepo extends JpaRepository<CustomerVehicle, String> {
	
//	@Query("SELECT cv FROM CustomerVehicle cv WHERE cv.proposalno IN (SELECT l.proposalno FROM Loan l WHERE l.custid = :customerId)" +
//            " AND (:flag is null OR " +
//			"		(:flag = 'all' AND) OR " +
//            "       (:flag = 'penalcshrcpt' AND cv.proposalno IN (SELECT p.loanid FROM Penalty p WHERE p.status IS NULL OR p.status = '')) OR " +
//            "       (:flag = 'agreementupload' AND (cv.agreementfilename IS NULL OR cv.agreementfilename = '')))" +
//            " ORDER BY cv.agreementserial DESC")
//    List<CustomerVehicle> findFilteredCustomerVehicles(String customerId, String flag);
	
	@Query("SELECT cv FROM CustomerVehicle cv WHERE cv.proposalno IN (SELECT l.proposalno FROM Loan l WHERE l.custid = :customerId)" +
	        " AND (:flag is null OR (:flag = 'all' ) OR" +  // Added closing parenthesis
	        "       (:flag = 'penalcshrcpt' AND cv.proposalno IN (SELECT p.loanid FROM Penalty p WHERE p.status IS NULL OR p.status = '')) OR" +
	        "       (:flag = 'agreementupload' AND (cv.agreementfilename IS NULL OR cv.agreementfilename = '')))" +
	        " ORDER BY cv.agreementserial DESC")
	List<CustomerVehicle> findFilteredCustomerVehicles(String customerId, String flag);


	
	CustomerVehicle findByproposalno(String propoNo);
	
	List<CustomerVehicle> findByagreementserial(int agreementserial);
	
	@Modifying
    @Transactional
    @Query("UPDATE CustomerVehicle cv SET cv.agreementfilename = :agreementName, cv.agupuser = :loginUser, cv.agupdatetime = :agupdatetime WHERE cv.agreementserial = :agreementSerial")
    int updateAgreementInfo(@Param("agreementName") String agreementName, @Param("loginUser") String loginUser, @Param("agupdatetime") String agupdatetime, @Param("agreementSerial") int agreementSerial);
	
	
	@Query("SELECT cv FROM CustomerVehicle cv " +
	           "WHERE cv.agreementserial = :agreementSerial " +
	           "AND cv.proposalno IN (SELECT l.proposalno FROM Loan l WHERE l.custid = :custId)")
	List<CustomerVehicle> findCustomerVehiclesByAgreeIdAndCustId(int agreementSerial, String custId);
	
	@Modifying
    @Transactional
	@Query("UPDATE CustomerVehicle cv SET cv.disbursed = true, cv.disbuser = :strLoginuser, cv.disbdatetime = :strDISBRequestdatetime WHERE cv.proposalno = :strProposal")
	int updateCustomerVehicle(String strLoginuser, String strDISBRequestdatetime, String strProposal);
	
	@Query("SELECT cv FROM CustomerVehicle cv WHERE proposalno = :strProposal AND disbursed = false")
	List<CustomerVehicle> findByProposalAndDisb(String strProposal);
	

	@Query("SELECT cv FROM CustomerVehicle cv WHERE cv.agreementserial = :strAgreementserial " +
	           "AND cv.proposalno IN (SELECT l.proposalno FROM Loan l " +
	           "WHERE l.auctioned = true OR l.preclosed = true OR l.normalclosure = true)")
	    List<CustomerVehicle> findByAgreeSerialAndLoanStatus(String strAgreementserial);

	
//	From CustomerVehicle where agreementserial = " + AgreementSerial + " and proposalno in (select proposalno from Loan where custid = '" + strCustid + 
	
	@Query("SELECT cv FROM CustomerVehicle cv " +
	           "WHERE cv.agreementserial = :agreementSerial " +
	           "AND cv.proposalno IN " +
	           "(SELECT l.proposalno FROM Loan l WHERE l.custid = :custid)")
	 List<CustomerVehicle> findByAgreementSerialAndCustid(int agreementSerial, String custid);

	
	
	List<CustomerVehicle> findByvehicleregno(String strVehicle);
	
	 @Query("SELECT MAX(w.agreementno) FROM CustomerVehicle w")
	 String findMaxagreementno();
	
	
	
	
	
}
