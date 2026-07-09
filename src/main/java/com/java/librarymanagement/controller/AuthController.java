package com.java.librarymanagement.controller;

import com.java.librarymanagement.DTO.LoginDto;
import com.java.librarymanagement.DTO.LoginResponseDto;
import com.java.librarymanagement.DTO.SignupDtoRequest;
import com.java.librarymanagement.DTO.SignupDtoResponse;
import com.java.librarymanagement.Jwt.JwtService;
import com.java.librarymanagement.entity.Users;
import com.java.librarymanagement.service.AuthService;
import com.java.librarymanagement.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<SignupDtoResponse> signup(@RequestBody SignupDtoRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.signup(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto request, HttpServletResponse response)
    {
       LoginResponseDto loginResponse = authService.login(request);

        Cookie cookie=new Cookie("refreshToken",loginResponse.refreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

       return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody LoginDto login, HttpServletResponse response, HttpServletRequest request)
    {
        String refreshToken=Arrays.stream(request.getCookies())
                .filter(name -> name.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                        .orElseThrow(()->new RuntimeException("Refresh token not found"));

        String userId=jwtService.getUserNameFromToken(refreshToken);
        Users user=userService.getUserById(userId);
        String accessToken=jwtService.generateAccessToken(user);
        LoginResponseDto loginResponse=new LoginResponseDto(user.getName(),accessToken,refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }



}
