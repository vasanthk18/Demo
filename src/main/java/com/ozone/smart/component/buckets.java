package com.ozone.smart.component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ozone.smart.dto.Response;
import com.ozone.smart.entity.PaymentSchedule;
import com.ozone.smart.entity.Penalty;
import com.ozone.smart.entity.Proposal;
import com.ozone.smart.entity.WeeklyInstallment;
import com.ozone.smart.entity.vwProposal;
import com.ozone.smart.repository.PaymentScheduleRepo;
import com.ozone.smart.repository.PenaltyRepo;
import com.ozone.smart.repository.VmProposalRepo;
import com.ozone.smart.repository.WeeklyInstRepo;

@Component
public class buckets {

    @Autowired
    private VmProposalRepo vmProposalRepo;

    @Autowired
    private PaymentScheduleRepo paySchRepo;

    @Autowired
    private PenaltyRepo penaltyRepo;

    @Autowired
    private WeeklyInstRepo weeklyInstRepo;

    public String[] Bucket(String strBucket) {

        LocalDate today = LocalDate.now();

        List<vwProposal> vwproposal = vmProposalRepo.findByDateRelease();
        StringBuilder strMsg = new StringBuilder();

        vwproposal.parallelStream().forEach(vwprop -> {
            String strCustid = vwprop.getCustomerid();
            String strProposalno = vwprop.getProposalno();
            String strAgreement = vwprop.getAgreementno();
            String strVehicle = vwprop.getVehicleregno();
            String strReleasedate = vwprop.getDateofrelease();
            String strLoanamnt = vwprop.getLoanamount().replace(",", "");
            String strEwi = vwprop.getEwi();
            String strNoofinst = vwprop.getNoofinstallments();
            String strPaymode = vwprop.getPaymentmode();
//            System.out.println(strProposalno);

            if (strReleasedate == null || strReleasedate.isEmpty()) {
                System.out.println("releasedDate null");
                return;
            }

            // Parse necessary values
            int intLoanamnt = Integer.parseInt(strLoanamnt);
            double dblEwi = Double.parseDouble(strEwi);
            int intEwi = (int) dblEwi;
            int intNoofinst = Integer.parseInt(strNoofinst);

            // Fetch payment schedule
            List<PaymentSchedule> paysch = paySchRepo.findLatestPayScheByLoanId(strProposalno);
            LocalDate paymentdate = LocalDate.now(); // Default to today

            if (!paysch.isEmpty()) {
                String strPaymentdate = paysch.get(0).getPaymentdate();
                paymentdate = LocalDate.parse(
                        strPaymentdate.substring(6, 10) + "-" +
                        strPaymentdate.substring(3, 5) + "-" +
                        strPaymentdate.substring(0, 2)
                );
            }

            LocalDate effectiveDate = paymentdate.isBefore(today) ? paymentdate : today;
            LocalDate releaseDate = LocalDate.parse(strReleasedate);

            // Determine first week divisor based on release date
            int firstWeekDivisor = 7; // Default for subsequent weeks
            switch (releaseDate.getDayOfWeek()) {
                case MONDAY: firstWeekDivisor = 9; break;
                case TUESDAY: firstWeekDivisor = 8; break;
                case WEDNESDAY: firstWeekDivisor = 7; break;
                case THURSDAY: firstWeekDivisor = 13; break;
                case FRIDAY: firstWeekDivisor = 12; break;
                case SATURDAY: firstWeekDivisor = 11; break;
                case SUNDAY: firstWeekDivisor = 10; break;
            }

            // Calculate weeks
            long daysBetween = ChronoUnit.DAYS.between(releaseDate, effectiveDate);
            double dblWeeks;
            if (daysBetween <= firstWeekDivisor) {
                dblWeeks = (double) daysBetween / firstWeekDivisor;
            } else {
                dblWeeks = 1 + ((double) (daysBetween - firstWeekDivisor) / 7);
            }

            int intTotalduewks = (int) Math.floor(dblWeeks);
            int intTotaldue = intEwi * intTotalduewks;

            // Fetch penalties
            List<Penalty> penalty = penaltyRepo.findByloanid(strProposalno);
            int intTotalPenalty = penalty.stream()
                .mapToInt(p -> Integer.parseInt(p.getPenalty()))
                .sum();

            intTotaldue += intTotalPenalty;

            // Fetch weekly installments
            List<WeeklyInstallment> weeklyinst = weeklyInstRepo.findByRevFlagAndProposalNo(strProposalno);
            int intTotalrecv = weeklyinst.stream()
                .mapToInt(wi -> Integer.parseInt(wi.getAmount()))
                .sum();

            int intOverdue = intTotaldue - intTotalrecv;
            dblWeeks = Math.round(((double) intOverdue / intEwi) * 100) / 100D;
//            System.out.println("Buckets "+ strBucket + "for proposalNo " + strProposalno + "And dblweeks "+ dblWeeks);
            // Bucket logic
            switch (strBucket) {
                case "1":
                    if (dblWeeks > 0.0 && dblWeeks < 1.01) {
                    	strMsg.append(strAgreement).append("::");
                    	System.out.println(dblWeeks +""+ strAgreement);
                    }
                    break;
                case "2":
                    if (dblWeeks > 1.00 && dblWeeks < 2.01) strMsg.append(strAgreement).append("::");
                    break;
                case "3":
                    if (dblWeeks > 2.00) strMsg.append(strAgreement).append("::");
                    break;
            }
        });

        return strMsg.toString().split("::");
    }
}
