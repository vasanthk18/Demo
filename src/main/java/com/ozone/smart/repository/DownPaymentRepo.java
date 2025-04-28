package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.DownPayment;
import com.ozone.smart.entity.Proposal;

@Repository
public interface DownPaymentRepo extends JpaRepository<DownPayment, Integer> {
	
	@Query("SELECT p FROM Proposal p " +
	        "WHERE p.proposalno = :proposalNo " +
	        "AND (:flag IS NULL OR " +
	        "(:flag = 'cqc' AND p.cqc != true) OR " +
//	        "(:flag = 'fi' AND p.fiverified != true) OR " +
//	        "(:flag = 'tv' AND p.tvverified != true) OR " +
	        "(:flag = 'cam' AND p.cqc = true) OR " +
	        "(:flag = 'cama') OR " +
	        "(:flag = 'all'))")
	List<Proposal> findByProposalNoWithFlag(@Param("proposalNo") String proposalNo, @Param("flag") String flag);

	@Query("SELECT dp FROM DownPayment dp WHERE dp.proposalno = :proposalNo AND dp.revflag = false")
	List<DownPayment> findByProposalNoAndRevFlagFalse(@Param("proposalNo") String proposalNo);
	
	 @Query("SELECT dp FROM DownPayment dp WHERE dp.proposalno = :strCashprop AND dp.transactionref = :strTranref AND dp.amount = :strCashamount")
	 List<DownPayment> findByProposalNoAndTransactionRefAndAmount(String strCashprop, String strTranref, String strCashamount);

	 List<DownPayment> findBydownreceipt(String receipt);
	 
	 @Query("SELECT MAX(d.downreceipt) FROM DownPayment d")
	    String findMaxDownreceipt();
	 
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE DownPayment dp " +
	        "SET dp.revflag = true, dp.revuser = :strLoginuser, dp.revdatetime = :strRequestdatetime " +
	        "WHERE dp.downreceipt = :strReceiptno AND dp.proposalno = :strProposalno " +
	        "AND dp.transactionref = :strTransref " +
	        "AND dp.amount = :strAmount AND dp.paymentmode = :strPaymode")
	 int updateDownPayment(String strLoginuser, 
	                              String strRequestdatetime, 
	                              String strReceiptno, 
	                              String strProposalno, 
	                              String strTransref, 
	                              String strAmount, 
	                              String strPaymode);

	 
//	 @Query(value = "SELECT * FROM tbldownpayment " +
//             "WHERE to_date(substring(paymentdate, 1, 8), 'DDMMYYYY') >= to_date(:fromDate, 'YYYY-MM-DD') " +
//             "AND to_date(substring(paymentdate, 1, 8), 'DDMMYYYY') <= to_date(:toDate, 'YYYY-MM-DD') " +
//             "ORDER BY receiptid", 
//     nativeQuery = true)
//	 List<DownPayment> findByDateRange(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	 
	 
//	 @Query("SELECT d FROM DownPayment d " +
//	           "WHERE FUNCTION('TO_DATE', CONCAT(SUBSTRING(d.paymentdate, 1, 2), '/', SUBSTRING(d.paymentdate, 3, 2), '/', SUBSTRING(d.paymentdate, 5, 4)), 'dd/MM/yyyy') >= FUNCTION('TO_DATE', :strFromdate, 'dd/MM/yyyy') " +
//	           "AND FUNCTION('TO_DATE', CONCAT(SUBSTRING(d.paymentdate, 1, 2), '/', SUBSTRING(d.paymentdate, 3, 2), '/', SUBSTRING(d.paymentdate, 5, 4)), 'dd/MM/yyyy') <= FUNCTION('TO_DATE', :strTodate, 'dd/MM/yyyy') " +
//	           "ORDER BY d.receiptid")
//	  List<DownPayment> findByDateRange(@Param("strFromdate") String strFromdate, @Param("strTodate") String strTodate);
	
	 @Query(value = "SELECT t FROM DownPayment t WHERE SUBSTRING(t.paymentdate, 3, 6) >= :startPeriod "
	            + "AND SUBSTRING(t.paymentdate, 3, 6) <= :endPeriod "
	            + "AND t.paymentdate >= :startDate AND t.paymentdate <= :endDate "
	            + "ORDER BY t.id ASC")
	    List<DownPayment> findByDateRange(
	            @Param("startPeriod") String startPeriod,
	            @Param("endPeriod") String endPeriod,
	            @Param("startDate") String startDate,
	            @Param("endDate") String endDate);
	 
	 
	 
//		List<DownPayment> findByDateRange(@Param("strFromdate") Date strFromdate, @Param("strTodate") Date strTodate);

//	List<DownPayment> findByDateRange(Date sqlFromDate, Date sqlToDate);

	
	
}
