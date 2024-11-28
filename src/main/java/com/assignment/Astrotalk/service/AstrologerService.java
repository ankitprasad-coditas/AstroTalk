package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.AstrologerDto;
import com.assignment.Astrotalk.entity.Astrologer;
import com.assignment.Astrotalk.exception.NotAllowedException;
import com.assignment.Astrotalk.repository.AstrologerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AstrologerService {

    private final AstrologerRepo astrologerRepo;
    private final ObjectMapper objectMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AstrologerService(AstrologerRepo astrologerRepo, ObjectMapper objectMapper,PasswordEncoder passwordEncoder) {
        this.astrologerRepo = astrologerRepo;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AstrologerDto createNewAstrologer(AstrologerDto astrologerDto) {
        if (astrologerRepo.findByEmailId(astrologerDto.getEmailId()).isPresent()) {
            throw new NotAllowedException("Already Exists ! Please Login");
        } else {
            Astrologer astrologer = new Astrologer();
            astrologer = objectMapper.convertValue(astrologerDto, Astrologer.class);
            astrologer.setPassword(passwordEncoder.encode(astrologer.getPassword()));
            return objectMapper.convertValue(astrologerRepo.save(astrologer), AstrologerDto.class);
        }
    }
}
