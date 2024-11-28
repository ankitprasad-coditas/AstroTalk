package com.assignment.Astrotalk.controller;

import com.assignment.Astrotalk.dto.ApiResponseDto;
import com.assignment.Astrotalk.dto.AuthRequestDto;
import com.assignment.Astrotalk.dto.HomeScreenDto;
import com.assignment.Astrotalk.jwt.JwtService;
import com.assignment.Astrotalk.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<HomeScreenDto>> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO) {
        return authService.loginAndHomePage(authRequestDTO);
    }
//
//    @PostMapping("/refreshToken")
//    public NewAccessTokenResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshToken){
//        return objectMapper.convertValue(refreshTokenService.generateNewToken(refreshToken), NewAccessTokenResponseDto.class);
//    }
}

