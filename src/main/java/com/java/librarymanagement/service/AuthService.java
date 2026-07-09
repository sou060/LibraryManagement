package com.java.librarymanagement.service;

import com.java.librarymanagement.DTO.LoginDto;
import com.java.librarymanagement.DTO.LoginResponseDto;
import com.java.librarymanagement.Jwt.JwtService;
import com.java.librarymanagement.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        Users user = (Users) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponseDto(user.getName(), accessToken,refreshToken);


    }
}
