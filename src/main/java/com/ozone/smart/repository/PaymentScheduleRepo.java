package com.ozone.smart.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.dto.AgreementCountDto;
import com.ozone.smart.entity.PaymentSchedule;

@Repository
public interface PaymentScheduleRepo extends JpaRepository<PaymentSchedule, String> {
	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE TO_DATE(ps.paymentdate, 'dd/MM/yyyy') <= :strnewToday AND ps.loanid = :strCashprop ORDER BY ps.schedule")
    List<PaymentSchedule> findByPaymentDateAndLoanId(Date strnewToday, String strCashprop);
	
	@Query("SELECT ps FROM PaymentSchedule ps " +
	           "WHERE ps.loanid = :strProposal " +
	           "AND ps.scheduleno LIKE :agreementNo " +
	           "ORDER BY ps.schedule")
	List<PaymentSchedule> findPaymentSchedulesByLoanIdAndScheduleNo(String strProposal, String agreementNo);

	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.scheduleno LIKE :strAgreementqry AND TO_DATE(ps.paymentdate, 'dd/mm/yyyy') <= :strNewimpounddate AND ps.status != 'PAID'")
	List<PaymentSchedule>findByScheduleDateStatus(String strAgreementqry, String strNewimpounddate);
	

	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.loanid = :strProposalno AND ps.schedule = " +
	        "(SELECT MAX(ps2.schedule) FROM PaymentSchedule ps2 WHERE ps2.loanid = :strProposalno)")
	List<PaymentSchedule> findLatestPaymentScheduleByLoanId(String strProposalno);

	@Modifying
	@Transactional
	@Query("UPDATE PaymentSchedule ps SET ps.status = 'PAID' WHERE ps.scheduleno = :strScheduleno AND ps.loanid = :strProposalno AND ps.paymentdate =:strInstallmentpaydate")
	int updatePaymentSchedule(String strScheduleno, String strProposalno, String strInstallmentpaydate);
	
	
	@Query("SELECT ps FROM PaymentSchedule ps " +
		       "WHERE FUNCTION('TO_DATE', ps.paymentdate, 'dd/mm/yyyy') < :strnewToday " +
		       "AND (ps.status = '' OR ps.status IS NULL) " +
		       "AND ps.loanid IN (SELECT l.proposalno FROM Loan l " +
		       "                  WHERE l.impounded = false AND l.reposessed = false AND l.auctioned = false " +
		       "                  AND l.preclosed = false AND l.normalclosure = false) " +
		       "ORDER BY ps.loanid, ps.schedule")
	List<PaymentSchedule> findPaymentSchedules(@Param("strnewToday") Date strnewToday);
	
	
	@Query("SELECT ps FROM PaymentSchedule ps " +
            "WHERE ps.scheduleno LIKE :strAgreementqry " +
            "AND TO_DATE(ps.paymentdate, 'dd/mm/yyyy') <= :strNewimpounddate " +
            "AND ps.status != 'PAID'")
	List<PaymentSchedule> findNonPaidSchedules(String strAgreementqry, Date strNewimpounddate);
	
	
	@Query("SELECT ps FROM PaymentSchedule ps " +
            "WHERE ps.scheduleno LIKE :strAgreementqry " +
            "AND TO_DATE(ps.paymentdate, 'dd/mm/yyyy') > :strNewimpounddate")
	List<PaymentSchedule> findSchedules(String strAgreementqry, Date strNewimpounddate);
	
	
	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.loanid = :strProposalno ORDER BY ps.schedule")
	List<PaymentSchedule> findByLoanId(String strProposalno);
	


	@Query("SELECT ps FROM PaymentSchedule ps " +
            "WHERE ps.loanid = :strProposalno " +
            "AND TO_DATE(ps.paymentdate, 'dd/mm/yyyy') <= :sqlDate " +
            "AND ps.status != 'PAID'")
	List<PaymentSchedule> findPaymentScheduleByLoanId(String strProposalno, java.sql.Date sqlDate);
	
//	"From PaymentSchedule where loanid = '" + strProposalno + "' and scheduleno like '" + strAgreementno + "' " +
//	"and to_date(paymentdate, 'dd/mm/yyyy') <= '" + strNewdate + "' and status = 'PAID' order by schedule"; 

	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.loanid = :strProposalno AND ps.scheduleno LIKE :strAgreementno AND TO_DATE(ps.paymentdate, 'dd/mm/yyyy') <= :strNewdate AND ps.status != 'PAID' ORDER BY ps.schedule")
	List<PaymentSchedule> findByScheduleNoAndLoanId(String strProposalno, String strAgreementno , java.sql.Date strNewdate);
	
	
	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.loanid = :strProposalno AND ps.schedule IN " +
	        "(SELECT MAX(ps2.schedule) FROM PaymentSchedule ps2 WHERE ps2.loanid = :strProposalno)")
	List<PaymentSchedule> findLatestPayScheByLoanId(String strProposalno);

	@Query("SELECT ps FROM PaymentSchedule ps " +
		       "WHERE FUNCTION('to_date', CONCAT(FUNCTION('substring', ps.paymentdate, 1, 2), '/' ," +
		                                          "FUNCTION('substring', ps.paymentdate, 4, 2), '/' ," +
		                                          "FUNCTION('substring', ps.paymentdate, 7, 4)), 'dd/MM/yyyy') >= :fromDate " +
		       "AND " +
		       "FUNCTION('to_date', CONCAT(FUNCTION('substring', ps.paymentdate, 1, 2), '/' ," +
		                                          "FUNCTION('substring', ps.paymentdate, 4, 2), '/' ," +
		                                          "FUNCTION('substring', ps.paymentdate, 7, 4)), 'dd/MM/yyyy') <= :toDate " +
		       "ORDER BY ps.paymentdate, ps.loanid")
		List<PaymentSchedule> findBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	
	 @Query("SELECT SUBSTRING(ps.scheduleno, 1, 8) AS agreementno, ps.loanid, COUNT(*) " +
	           "FROM PaymentSchedule ps " +
	           "WHERE ps.loanid NOT IN (SELECT ps2.loanid FROM PaymentSchedule ps2 WHERE ps2.status <> 'PAID') " +
	           "AND ps.loanid IN (SELECT l.proposalno FROM Loan l WHERE l.normalclosure <> true) " +
	           "GROUP BY SUBSTRING(ps.scheduleno, 1, 8), ps.loanid " +
	           "ORDER BY SUBSTRING(ps.scheduleno, 1, 8)")
	    List<AgreementCountDto> findAgreementCount();
	
}
