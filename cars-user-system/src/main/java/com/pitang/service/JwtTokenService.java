package com.pitang.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class JwtTokenService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.token-validity-in-seconds}")
    private long expirationMs;

    public String generateJwtToken(String login, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims( claims )
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
