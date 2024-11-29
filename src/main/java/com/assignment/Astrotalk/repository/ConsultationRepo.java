package com.assignment.Astrotalk.repository;

import com.assignment.Astrotalk.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationRepo extends JpaRepository<Consultation, Long> {

    @Query(value = "SELECT * FROM Consultations  WHERE next_consultation_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Consultation> findConsultationsWithinDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(c.balanceAmount) FROM Consultation c WHERE c.client.id = :clientId")
    Double findTotalBalanceAmountByClientId(@Param("clientId") Long clientId);
}
