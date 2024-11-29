package com.assignment.Astrotalk.service;

import com.assignment.Astrotalk.dto.RefreshTokenRequestDto;
import com.assignment.Astrotalk.entity.Astrologer;
import com.assignment.Astrotalk.jwt.JwtService;
import com.assignment.Astrotalk.repository.AstrologerRepo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AstrologerRepo astrologerRepo;

    public String generateNewToken(RefreshTokenRequestDto token) {
        Claims claims = jwtService.extractAllClaims(token.getRefreshToken());
        String email = claims.getSubject();
        Optional<Astrologer> astrologerRepoByEmailId = astrologerRepo.findByEmailId(email);
        if (astrologerRepoByEmailId.isPresent()) {

            String tokenName = claims.get("tokenName").toString();
            if (!tokenName.equalsIgnoreCase("refreshToken")) {
                return "Incorrect Token Passed";
            }

            Date date = claims.getExpiration();
            if (date.before(new Date())) {
                return "Session Expired..Please ReLogin";
            }
            String newToken = jwtService.generateAccessToken((String) claims.get("sub"));
            return newToken;
        }
        return "Token Expired..Please ReLogin";
    }
}
