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

    private final JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            try {
                if (jwtTokenService.validateToken(authorizationHeader)) {
                    String username = jwtTokenService.getUsernameFromToken(authorizationHeader);
                    Authentication authentication = jwtTokenService.getAuthentication(username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
                return;
            }
        }else{
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            return;
        }

        filterChain.doFilter(request, response);
    }
}









