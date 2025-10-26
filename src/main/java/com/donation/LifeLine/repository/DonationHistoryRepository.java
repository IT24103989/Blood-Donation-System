package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

    List<DonationHistory> findByDonorNIC(String donorNIC);

    // ✅ Corrected method name to match service call
    @Query("SELECT d.donorNIC, d.donorName, COUNT(d) FROM DonationHistory d " +
            "WHERE (:from IS NULL OR d.donationDate >= :from) " +
            "AND (:to IS NULL OR d.donationDate <= :to) " +
            "GROUP BY d.donorNIC, d.donorName")
    List<Object[]> countDonationsBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}