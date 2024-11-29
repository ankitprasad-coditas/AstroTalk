package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.*;
import com.assignment.Astrotalk.jwt.JwtService;
import com.assignment.Astrotalk.service.AuthService;
import com.assignment.Astrotalk.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<HomeScreenDto>> AuthenticateAndGetToken(@RequestBody @Valid AuthRequestDto authRequestDTO) {
        return authService.loginAndHomePage(authRequestDTO);
    }

    @PostMapping("/refreshToken")
    public NewAccessTokenResponseDto refreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshToken){
        return objectMapper.convertValue(refreshTokenService.generateNewToken(refreshToken), NewAccessTokenResponseDto.class);
    }
}

