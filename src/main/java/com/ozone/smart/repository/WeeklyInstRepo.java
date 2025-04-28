package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.WeeklyInstallment;

@Repository
public interface WeeklyInstRepo extends JpaRepository<WeeklyInstallment, Integer> {
	
	 @Query("SELECT wi FROM WeeklyInstallment wi WHERE wi.proposalno = :strCashprop AND wi.transactionref = :strTranref AND wi.amount = :strCashamount AND wi.vehicleregno = :strCashvehicle")
	    List<WeeklyInstallment> findByParameters(String strCashprop, String strTranref, String strCashamount, String strCashvehicle);
	 
	 @Query("SELECT wi FROM WeeklyInstallment wi WHERE wi.revflag = false AND wi.proposalno = :strCashprop")
	    List<WeeklyInstallment> findByRevFlagAndProposalNo(String strCashprop);
	 
	 
	 List<WeeklyInstallment> findByweekreceipt(String receipt);
	 
	 @Query("SELECT MAX(w.weekreceipt) FROM WeeklyInstallment w")
	 String findMaxWeekreceipt();
	 
//	 "From WeeklyInstallment where proposalno = '" + strProposalno + "' order by receiptid"
	 
	 @Query("SELECT wi FROM WeeklyInstallment wi WHERE wi.proposalno = :proposal ORDER BY wi.receiptid")
	 List<WeeklyInstallment> findall(String proposal);
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE WeeklyInstallment wi " +
	        "SET wi.revflag = true, wi.revuser = :strLoginuser, wi.revdatetime = :strRequestdatetime " +
	        "WHERE wi.weekreceipt = :strReceiptno AND wi.proposalno = :strProposalno " +
	        "AND wi.vehicleregno = :strVehicle AND wi.transactionref = :strTransref " +
	        "AND wi.amount = :strAmount AND wi.paymentmode = :strPaymode")
	 int updateWeeklyInstallment(String strLoginuser, 
	                              String strRequestdatetime, 
	                              String strReceiptno, 
	                              String strProposalno, 
	                              String strVehicle, 
	                              String strTransref, 
	                              String strAmount, 
	                              String strPaymode);
	 

	 @Query("SELECT SUM(CAST(wi.amount AS double)) AS totalpaid FROM WeeklyInstallment wi WHERE wi.proposalno = :proposalno AND wi.revflag = false GROUP BY wi.proposalno")
	 Double getTotalPaidAmountByProposal(String proposalno);
	 
	 @Query(value = "SELECT * FROM tblweeklyinstallment " +
             "WHERE to_date(substring(paymentdate, 1, 8), 'DDMMYYYY') >= to_date(:fromDate, 'YYYY-MM-DD') " +
             "AND to_date(substring(paymentdate, 1, 8), 'DDMMYYYY') <= to_date(:toDate, 'YYYY-MM-DD') " +
             "ORDER BY receiptid", nativeQuery = true)
	 List<WeeklyInstallment> findByPaymentDateBetween(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	 
//	 @Query("SELECT wi FROM WeeklyInstallment wi " +
//	           "WHERE wi.proposalno = :proposalno " +
//	           "ORDER BY wi.receiptid")
//	    List<WeeklyInstallment> findByProposalnoOrderByReceiptid(String proposalno);
	 
//	 @Query("SELECT w FROM WeeklyInstallment w " +
//		       "WHERE FUNCTION('TO_DATE', CONCAT(SUBSTRING(w.paymentdate, 1, 2), '/' , SUBSTRING(w.paymentdate, 3, 2), '/' , SUBSTRING(w.paymentdate, 5, 4)), 'dd/MM/yyyy') >= :strFromdate " +
//		       "AND FUNCTION('TO_DATE', CONCAT(SUBSTRING(w.paymentdate, 1, 2), '/' , SUBSTRING(w.paymentdate, 3, 2), '/' , SUBSTRING(w.paymentdate, 5, 4)), 'dd/MM/yyyy') <= :strTodate " +
//		       "ORDER BY w.receiptid")
//		List<WeeklyInstallment> findByDateRange(@Param("strFromdate") String strFromdate, @Param("strTodate") String strTodate);

	 @Query("SELECT wi.amount, wi.paymentdate, wi.transactiontype FROM WeeklyInstallment wi WHERE wi.proposalno = :proposalno ORDER BY wi.receiptid")
	    List<String> findAmountsByProposalNo(@Param("proposalno") String proposalno);



}
