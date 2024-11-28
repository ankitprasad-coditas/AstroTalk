package com.assignment.Astrotalk.authentication;

import com.assignment.Astrotalk.entity.Astrologer;
import com.assignment.Astrotalk.exception.AstrologerNotFoundException;
import com.assignment.Astrotalk.repository.AstrologerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AstrologerRepo astrologerRepo;

    @Autowired
    public CustomUserDetailsService(AstrologerRepo astrologerRepo) {
        this.astrologerRepo = astrologerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws AstrologerNotFoundException {
        Astrologer astrologer = astrologerRepo.findByEmailId(emailId)
                .orElseThrow(() -> new AstrologerNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                astrologer.getEmailId(),
                astrologer.getPassword(),
                Collections.emptyList()
        );
    }
}

