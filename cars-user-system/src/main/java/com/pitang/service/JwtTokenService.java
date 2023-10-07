package com.pitang.service;

import com.pitang.exception.JwtInvalidException;
import com.pitang.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JwtTokenService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.token-validity-in-seconds}")
    private long expirationMs;

    @Autowired
    private UserRepository userRepository;

    public String generateJwtToken(String login, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims( claims )
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        String jwtToken = getToken( token );
        if (jwtToken == null) throw new JwtInvalidException("Unauthorized");

        try {
            getClaimsJws( jwtToken );
            return true;
        } catch (Exception e) {
            throw new JwtInvalidException("Unauthorized");
        }
    }

    public String getUsernameFromToken(String token) {

        String jwtToken = getToken( token );
        if (jwtToken == null) return null;

        Claims claims = getClaimsJws( jwtToken )
                .getBody();

        return claims.getSubject();
    }

    private Jws<Claims> getClaimsJws (String jwtToken){
        return Jwts.parser()
                .setSigningKey( jwtSecret )
                .parseClaimsJws( jwtToken );
    }

    private String getToken (String token){
        if(!StringUtils.hasText( token )){
            return null;
        }

        String jwtToken;

        if(token.startsWith("Bearer ")){
            jwtToken = token.substring(7);
        }else{
            jwtToken = token;
        }
        return jwtToken;
    }

    public Authentication getAuthentication(String username) {
        userRepository.findByLogin( username ).orElseThrow(() -> new JwtInvalidException("Unauthorized"));

        List<GrantedAuthority> authorities = getAuthoritiesForUser(username);

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private List<GrantedAuthority> getAuthoritiesForUser(String username) {
        List<String> userRoles = getUserRoles(username);

        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect( Collectors.toList());
    }

    private List<String> getUserRoles(String username) {
        if ("user".equals(username)) {
            return List.of("USER");
        } else if ("admin".equals(username)) {
            return List.of("USER", "ADMIN");
        } else {
            return List.of();
        }
    }
}
