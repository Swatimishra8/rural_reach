package com.springBoot.rural_reach.controller;

import com.springBoot.rural_reach.dto.AuthRequestDto;
import com.springBoot.rural_reach.dto.JwtResponseDto;
import com.springBoot.rural_reach.dto.RefreshTokenRequestDto;
import com.springBoot.rural_reach.dto.UserDto;
import com.springBoot.rural_reach.entity.RefreshToken;
import com.springBoot.rural_reach.exceptions.UserNotFoundException;
import com.springBoot.rural_reach.jwt.JwtService;
import com.springBoot.rural_reach.service.RefreshTokenService;
import com.springBoot.rural_reach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public JwtResponseDto registerUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto); // save the user

        String accessToken = jwtService.generateAccessToken(userDto.getEmailId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getEmailId());

        return JwtResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @PostMapping("/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmailId(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateAccessToken(authRequestDTO.getEmailId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getEmailId());
            return JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
        } else {
            throw new UserNotFoundException("Invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateAccessToken(user.getEmail());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDto.getToken())
                            .build();
                }).orElseThrow(()-> new RuntimeException(
                        "Invalid Refresh Token....Not In Database !"));
    }
}
