package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.*;
import com.assignment.Astrotalk.exception.NotAllowedException;
import com.assignment.Astrotalk.jwt.JwtService;
import com.assignment.Astrotalk.repository.ClientRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepo clientRepo;


    public ResponseEntity<ApiResponseDto<HomeScreenDto>> loginAndHomePage(AuthRequestDto authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            HomeScreenDto homeScreenDto = new HomeScreenDto();

            User astrologer = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(astrologer.getUsername());
            String refreshToken = jwtService.generateRefreshToken(astrologer.getUsername());
            JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            homeScreenDto.setJwtResponseDto(jwtResponseDto);
            homeScreenDto.setClientDtoList(clientService.allClients());
            homeScreenDto.setConsultationDtoList(consultationService.getAllConsultation());

            Double monthlyEarning = clientRepo.findTotalEarningsByMonth(new Date().getMonth(),new Date().getYear());
            homeScreenDto.setMonthlyEarning(monthlyEarning);

            ApiResponseDto<HomeScreenDto> response = new ApiResponseDto<>(homeScreenDto, HttpStatus.CREATED.value(), "Logged In Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new NotAllowedException("Invalid Login Details!");
        }
    }

    public NewAccessTokenResponseDto refreshToken(RefreshTokenRequestDto refreshToken){
        return objectMapper.convertValue(refreshTokenService.generateNewToken(refreshToken), NewAccessTokenResponseDto.class);
    }
}
