package com.java.librarymanagement.Jwt;

import com.java.librarymanagement.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final String secretKey = "fyfwfruycbgubfjbcgbhcbnfnxhhxakzamhg";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(Users user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role",user.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSecretKey())
                .compact();

    }

    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (claims.getSubject());
    }

    public String generateRefreshToken(Users user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();

    }


}
