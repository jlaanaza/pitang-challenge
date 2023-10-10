package com.pitang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.dto.LoginDTO;
import com.pitang.dto.UserDTO;
import com.pitang.service.JwtTokenService;
import com.pitang.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testSignIn() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("testuser");
        loginDTO.setPassword("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setBirthday(LocalDate.of(1990, 1, 1));
        userDTO.setPhone("1234567890");
        userDTO.setCreatedAt( LocalDateTime.now() );
        userDTO.setLastLogin( LocalDateTime.now() );


        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", "John");
        claims.put("lastName", "Doe");
        claims.put("email", "john@example.com");
        String token = "sampleToken";

        when(loginService.validateLogin(any())).thenReturn(userDTO);
        when(jwtTokenService.generateJwtToken(anyString(), any())).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signin")
                        .content(new ObjectMapper().writeValueAsString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));
    }

    @Test
    public void testGetInfoUser() throws Exception {
        String authorizationHeader = "Bearer sampleToken";

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setBirthday(LocalDate.of(1990, 1, 1));
        userDTO.setPhone("1234567890");

        when(jwtTokenService.getUsernameFromToken("Bearer sampleToken")).thenReturn("testuser");
        when(loginService.findByUsername("testuser")).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/me")
                        .header("Authorization", authorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

