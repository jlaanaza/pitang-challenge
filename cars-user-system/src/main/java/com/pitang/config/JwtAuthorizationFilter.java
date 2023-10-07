package com.pitang.config;

import com.pitang.service.JwtTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken;
            if(authorizationHeader.startsWith("Bearer ")){
                jwtToken = authorizationHeader.substring(7);
            }else{
                jwtToken = authorizationHeader;
            }

            try {
                if (jwtTokenService.validateToken(jwtToken)) {
                    String username = jwtTokenService.getUsernameFromToken(jwtToken);
                    Authentication authentication = jwtTokenService.getAuthentication(username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
               throw  e;
            }
        }

        filterChain.doFilter(request, response);
    }
}









