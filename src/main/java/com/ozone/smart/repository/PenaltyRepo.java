package com.ozone.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozone.smart.entity.Penalty;

@Repository
public interface PenaltyRepo extends JpaRepository<Penalty, String> {
	
	List<Penalty>findByloanid(String cashProp);
	
	
	List<Penalty> findByloanscheduleno(String strScheduleNo);
	
//	From Penalty where loanid = '" + strProposalno + "' and (status is null or status = '')
	
	
	@Query("SELECT p FROM Penalty p WHERE p.loanid = :strProposalno AND (p.status is null OR p.status = '') AND p.waiver = false")
	List<Penalty> findByLoanIdAndStatusIsNull(String strProposalno);
	
	
	
	@Query("SELECT p FROM Penalty p WHERE p.loanscheduleno LIKE :strAgreementqry AND (p.status is null OR p.status = '') AND p.waiver = false")
	List<Penalty> findByLoanSchdlNoIdAndStatusIsNull(String strAgreementqry);
	
	
	@Modifying
	@Transactional
    @Query("UPDATE Penalty p " +
           "SET p.status = :strPenalstatus, " +
           "    p.penaltypaymentdate = :strPenalpaymentdate, " +
           "    p.penaltytranref = :strPenaltranref " +
           "WHERE p.loanscheduleno = :strPenalloanscheduleno " +
           "  AND p.loanid = :strPenalloanid " +
           "  AND p.loanpaymentdate = :strPenalloanpaymentdate")
    int updatePenalty(@Param("strPenalstatus") String strPenalstatus,
                      @Param("strPenalpaymentdate") String strPenalpaymentdate,
                      @Param("strPenaltranref") String strPenaltranref,
                      @Param("strPenalloanscheduleno") String strPenalloanscheduleno,
                      @Param("strPenalloanid") String strPenalloanid,
                      @Param("strPenalloanpaymentdate") String strPenalloanpaymentdate);
	
	
//	"From Penalty where (status = '' or status is null) and loanid = '" + strProposalno + "'" +
//	" order by loanscheduleno"
	
	@Query("SELECT p FROM Penalty p WHERE (p.status ='' or p.status is null) AND p.loanid = :strProposalno AND p.waiver = false ORDER BY loanscheduleno")
	List<Penalty> orderByLoanSchedule(String strProposalno);
	
	@Modifying
	@Transactional
	@Query("UPDATE Penalty p SET p.penalty = '0', p.waiver = true WHERE p.loanid = :loanid AND (p.status = '' OR p.status IS NULL)")
    int updatePenaltyToZeroByLoanId(@Param("loanid") String loanid);
	
//	@Modifying
//	@Transactional
//    @Query("DELETE FROM Penalty p WHERE p.loanid = :loanid")
//    int deletePenaltyToZeroByLoanId(@Param("loanid") String loanid);
	
	@Query("SELECT p.penalty,p.loanpaymentdate FROM Penalty p WHERE p.loanid = :strProposalno ORDER BY p.loanscheduleno")
	List<String> findPenaltybyProposalno(@Param("strProposalno") String strProposalno);

}
