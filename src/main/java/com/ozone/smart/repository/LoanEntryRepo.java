package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Loan;
import com.ozone.smart.entity.Proposal;

@Repository
public interface LoanEntryRepo extends JpaRepository<Loan, String> {

	@Query("SELECT l FROM Loan l " +
	        "WHERE l.proposalno = :proposalNo " +
	        "AND (:flag IS NULL OR " +
	        "(:flag = 'cqc' AND l.cqc != true) OR " +
//	        "(:flag = 'fi' AND l.fiverified != true) OR " +
//	        "(:flag = 'tv' AND l.tvverified != true) OR " +
			"(:flag = 'cama') OR " +
			"(:flag = 'all') OR " + 
	        "(:flag = 'cam' AND l.cqc = true))")
	List<Loan> findByProposalNoWithFlag(@Param("proposalNo") String proposalNo, @Param("flag") String flag);

	
	List<Loan> findByproposalno(String id);
	
	
	
//	@Modifying
//	@Transactional
//    @Query("UPDATE Loan cd SET cd.cqc = :cqc, cd.cqcremarks = :cqcremarks, cd.cqcuser = :cqcuser, cd.cqcdatetime = :cqcdatetime WHERE cd.proposalno = :querypart")
//    int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks, @Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime, @Param("querypart") String querypart);
//	@Modifying
//	@Transactional
//    @Query("UPDATE Loan cd SET cd.cqc = :cqc, cd.cqcremarks = :cqcremarks, cd.cqcuser = :cqcuser, cd.cqcdatetime = :cqcdatetime WHERE cd.proposalno = :querypart")
//    int updateCqcDetails(@Param("cqc") boolean cqc, @Param("cqcremarks") String cqcremarks, @Param("cqcuser") String cqcuser, @Param("cqcdatetime") String cqcdatetime, @Param("querypart") String querypart);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.loanamount = :strLnamount, l.downpayment = :strDp, l.insurancefee = :insFee, l.ewi = :strEwi, " +
	       "l.cqcuser = :strLoginuser, l.cqcdatetime = :strCQCRequestdatetime WHERE l.proposalno = :strId")
	int updateLoan(@Param("strLnamount") String strLnamount, @Param("strDp") String strDp, 
	                @Param("insFee") String insFee, @Param("strEwi") String strEwi, 
	                @Param("strLoginuser") String strLoginuser, 
	                @Param("strCQCRequestdatetime") String strCQCRequestdatetime, 
	                @Param("strId") String strId);
	
	
//	@Modifying
//	@Transactional 
//	@Query("UPDATE Loan l SET l.loanamount = :strLnamount, l.noofinstallments = :strNoinst, l.downpayment = :strDp, " +
//	       "l.trackerfee = :strTrckFee, l.interestrate = :strIrr, l.paymentmode = :strPaymode, l.insurancefee = :strInsfee, " +
//	       "l.ewi = :strEwi, l.cqcuser = :strLoginuser, l.cqcdatetime = :strCQCRequestdatetime WHERE l.proposalno=:strId")
//	int updateLoanwithinst(@Param("strLnamount") String strLnamount, @Param("strNoinst") String strNoinst, 
//	                @Param("strDp") String strDp, @Param("strTrckFee") String strTrckFee, 
//	                @Param("strIrr") String strIrr, @Param("strPaymode") String strPaymode, 
//	                @Param("strInsfee") String strInsfee, @Param("strEwi") String strEwi, 
//	                @Param("strLoginuser") String strLoginuser, 
//	                @Param("strCQCRequestdatetime") String strCQCRequestdatetime, 
//	                @Param("strId") String steId);
	
	
//	@Modifying
//	@Transactional 
//	@Query("UPDATE Loan l SET l.loanamount = :strLnamount, l.noofinstallments = :strNoinst, l.downpayment = :strDp, " +
//	       "l.trackerfee = :strTrckFee, l.interestrate = :strIrr, l.paymentmode = :strPaymode, l.insurancefee = :strInsfee, " +
//	       "l.ewi = :strEwi, l.cqcuser = :strLoginuser, l.cqcdatetime = :strCQCRequestdatetime WHERE l.proposalno=:strId")
//	int updateLoanwithinst(@Param("strLnamount") String strLnamount, @Param("strNoinst") String strNoinst, 
//	                @Param("strDp") String strDp, @Param("strTrckFee") String strTrckFee, 
//	                @Param("strIrr") String strIrr, @Param("strPaymode") String strPaymode, 
//	                @Param("strInsfee") String strInsfee, @Param("strEwi") String strEwi, 
//	                @Param("strLoginuser") String strLoginuser, 
//	                @Param("strCQCRequestdatetime") String strCQCRequestdatetime, 
//	                @Param("strId") String steId);
	
	

	
	@Query("SELECT l FROM Loan l WHERE custid = :strCustomer AND proposalno = :strProposal AND scheduled =0 AND cqc =true AND coapproval = 'Recommended' AND cooapproval = 'Approve'")
	List<Loan> findByParameters(String strCustomer, String strProposal);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.scheduled = 1 WHERE l.proposalno = :strProposal")
	int updateLoanByProposal(String strProposal);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.coapproval = :strCocam , l.coremarks = :strCocamremarks, l.camuser =:strLoginuser, l.camdatetime = :strCAMRequestdatetime WHERE l.proposalno = :strId ")
	int updateLoanForCam(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strId);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.coapproval = '', l.coremarks = '', l.cqc =false, l.cqcremarks = '',l.srremarks = :strCocamremarks  WHERE l.proposalno = :strId ")
	int updateLoans(String strCocamremarks, String strId);
	
	
	
	
//	"Update Loan set preclosed = true where proposalno = '" + strProposalno + "'"
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.preclosed = true WHERE l.proposalno = :strProposalno")
	int updateLoan(String strProposalno);
	
	
//	strQuery = "Update Loan set normalclosure = true, closureuser = '" + strLoginuser + "', closuredatetime = '" + strRequestdatetime + "' "
//			+ "where proposalno = '" + strProposalno + "'";
//	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.normalclosure= true, l.closureuser = :strLoginuser, l.closuredatetime = :strRequestdatetime WHERE l.proposalno = :strProposalno")
	int updateLoanForNormalClosure(String strLoginuser, String strRequestdatetime ,String strProposalno);
	
//	strQuery = "Update Loan set impounded = true where proposalno = '" + strProposal + "'";
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l set l.impounded = true WHERE l.proposalno = :strProposal")
	int updateLoanForImpound(String strProposal);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l set l.reposessed = true WHERE l.proposalno = :strProposal")
	int updateLoanForRepo(String strProposal);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l set l.auctioned = true WHERE l.proposalno = :strProposal")
	int updateLoanForAuction(String strProposal);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.cooapproval = :strCocam , l.cooremarks = :strCocamremarks, l.camauser =:strLoginuser, l.camadatetime = :strCAMRequestdatetime WHERE l.proposalno = :strProp ")
	int updateLoanForCoo(String strCocam, String strCocamremarks, String strLoginuser, String strCAMRequestdatetime, String strProp);
	
	@Modifying
	@Transactional
	@Query("UPDATE Loan l SET l.coapproval = '', l.cooapproval = '', l.revremarks = :strCocamremarks WHERE l.proposalno = :strProp")
	int updateLoanForReverse(@Param("strCocamremarks") String strCocamremarks, @Param("strProp") String strProp);

	@Query("SELECT 'AN' || agreementserial AS agreement FROM CustomerVehicle WHERE proposalno IN (SELECT proposalno FROM Loan WHERE auctioned = true OR preclosed = true OR normalclosure = true) order by agreementserial")
	List<String> getAgreementNo();


//	int updateLoanDetails(boolean blnCqc, String strCqcremarks, String strLnamount, String strNoinst, String strDp,
//			String strTrckFee, String strIrr, String strPaymode, String strInsfee, String strEwi, String strLoginuser,
//			String strCQCRequestdatetime, String strQuerypart);
	
	@Modifying
    @Transactional
    @Query("UPDATE Loan l SET l.cqc = :blnCqc, l.cqcremarks = :strCqcremarks, " +
           "l.loanamount = :strLnamount, l.noofinstallments = :strNoinst, l.downpayment = :strDp, " +
           "l.trackerfee = :strTrckfee, l.interestrate = :strIrr, l.paymentmode = :strPaymode, l.insurancefee = :strInsfee, " +
           "l.ewi = :strEwi, l.cqcuser = :strLoginuser, l.cqcdatetime = :strCQCRequestdatetime " +
           "WHERE l.proposalno = :strQuerypart")
    int updateLoanDetails(@Param("blnCqc") boolean blnCqc, @Param("strCqcremarks") String strCqcremarks, 
                          @Param("strLnamount") String strLnamount, @Param("strNoinst") String strNoinst, 
                          @Param("strDp") String strDp, @Param("strTrckfee") String strTrckfee, 
                          @Param("strIrr") String strIrr, @Param("strPaymode") String strPaymode, 
                          @Param("strInsfee") String strInsfee, @Param("strEwi") String strEwi, 
                          @Param("strLoginuser") String strLoginuser, 
                          @Param("strCQCRequestdatetime") String strCQCRequestdatetime, 
                          @Param("strQuerypart") String strQuerypart);

	@Query("SELECT COUNT(l) FROM Loan l WHERE l.proposalno = :strProposal AND l.scheduled = 1")
	int countLoansByProposal(@Param("strProposal") String strProposal);

//	@Query("SELECT l FROM Loan l WHERE l.proposalno = " +


	@Query("SELECT l FROM Loan l WHERE l.proposalno = (" +
		       "SELECT MAX(subLoan.proposalno) FROM Loan subLoan WHERE subLoan.custid = :strCustid)")
		Loan getLatestLoanByCustId(@Param("strCustid") String strCustid);

	
}
