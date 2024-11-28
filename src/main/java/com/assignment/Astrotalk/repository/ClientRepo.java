package com.assignment.Astrotalk.repository;

import com.assignment.Astrotalk.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByNameAndDobAndPlaceOfBirthAndTimeOfBirth(String name, LocalDate dob, String placeOfBirth, String timeOfBirth);

    @Query("SELECT SUM(c.amountPaid) FROM Consultation c WHERE MONTH(c.consultationDate) = :month AND YEAR(c.consultationDate) = :year")
    Double findTotalEarningsByMonth(@Param("month") int month, @Param("year") int year);
}
