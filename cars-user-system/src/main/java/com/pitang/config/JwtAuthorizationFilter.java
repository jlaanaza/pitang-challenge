package com.pitang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.exception.ExceptionError;
import com.pitang.exception.JwtInvalidException;
import com.pitang.service.JwtTokenService;
import org.springframework.http.HttpStatus;
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
            } catch (JwtInvalidException e) {
                // Token inválido ou expirado
                sendUnauthorizedResponse(response, e.getMessage());
                return;
            }
        }
//        else{
//            sendUnauthorizedResponse(response, "Unauthorized");
//            return;
//        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ExceptionError exceptionError = new ExceptionError(message, HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), exceptionError);
    }
}









