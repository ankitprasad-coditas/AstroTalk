package com.assignment.Astrotalk.repository;

import com.assignment.Astrotalk.entity.Astrologer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AstrologerRepo extends JpaRepository<Astrologer,Long> {
    Optional<Astrologer> findByEmailId(String emailId);
}
