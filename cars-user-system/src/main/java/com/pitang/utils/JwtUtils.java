package com.pitang.utils;

import com.pitang.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtils {

    public static String generateJwtToken(String login, User user) {
        long expirationMs = 86400000; // 1 day
        SecretKey key = Keys.secretKeyFor( SignatureAlgorithm.HS512);
        Map<String, Object> claims = Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "birthday", user.getBirthday(),
                "phone", user.getPhone()
        );

        return Jwts.builder()
                .addClaims( claims )
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith( key,SignatureAlgorithm.HS512)
                .compact();
    }
}
