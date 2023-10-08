package com.pitang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.exception.ExceptionError;
import com.pitang.exception.JwtInvalidException;
import com.pitang.service.JwtTokenService;
import com.pitang.utils.RoutesUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenService jwtTokenService;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        String authorizationHeader = request.getHeader("Authorization");
        if(isPublicRoutes(requestURI)){
            chain.doFilter(request, response);
        }else {
            if(!StringUtils.hasText( authorizationHeader )){
                sendUnauthorizedResponse( response, "Unauthorized" );
                return;
            }
            try {
                if (jwtTokenService.validateToken( authorizationHeader )) {
                    String username = jwtTokenService.getUsernameFromToken( authorizationHeader );
                    Authentication authentication = jwtTokenService.getAuthentication( username );
                    SecurityContextHolder.getContext().setAuthentication( authentication );
                    if (username != null) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken( username, null, Collections.emptyList() );

                        SecurityContextHolder.getContext().setAuthentication( authenticationToken );
                    }
                }
            } catch (JwtInvalidException e) {
                sendUnauthorizedResponse( response, e.getMessage() );
                return;
            }

            chain.doFilter( request, response );
        }

    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ExceptionError exceptionError = new ExceptionError(message, HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), exceptionError);
    }

    private boolean isPublicRoutes(String requestURI) {
        return RoutesUtils.publicRoutes.stream().anyMatch( requestURI::startsWith );
    }
}









