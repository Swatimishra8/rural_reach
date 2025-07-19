package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.entity.RefreshToken;
import com.springBoot.rural_reach.jwt.JwtService;
import com.springBoot.rural_reach.repository.RefreshTokenRepo;
import com.springBoot.rural_reach.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    public RefreshToken createRefreshToken(String emailId) {
        var user = userRepo.findByEmail(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<RefreshToken> existingToken = refreshTokenRepo.findByUser_id(user.getId());
        if (existingToken.isPresent()) {
            return existingToken.get();
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(jwtService.generateRefreshToken(emailId))
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
            return refreshTokenRepo.save(refreshToken);
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(" Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}