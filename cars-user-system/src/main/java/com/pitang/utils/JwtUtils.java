package com.pitang.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtils {

    public static String generateJwtToken(String login) {
        long expirationMs = 86400000; // 1 day
        SecretKey key = Keys.secretKeyFor( SignatureAlgorithm.HS512);
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith( key,SignatureAlgorithm.HS512)
                .compact();
    }
}
