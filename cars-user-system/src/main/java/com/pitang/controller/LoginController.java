package com.pitang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.dto.LoginDTO;
import com.pitang.dto.UserDTO;
import com.pitang.service.JwtTokenService;
import com.pitang.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value ="/signin")
    public ResponseEntity<?>  signIn(@RequestBody LoginDTO loginDTO) {
        UserDTO user = loginService.validateLogin(loginDTO);

        Map<String, Object> claims = buildClams( user );

        String token = jwtTokenService.generateJwtToken(loginDTO.getLogin(), claims);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/me")
    public ResponseEntity<?> getInfoUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userName = jwtTokenService.getUsernameFromToken( authorization );
        UserDTO userDTO = loginService.findByUsername( userName );

        return new ResponseEntity<>(userDTO, new HttpHeaders(), HttpStatus.OK);
    }

    private Map<String, Object> buildClams (UserDTO user){
        return Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "birthday", user.getBirthday(),
                "phone", user.getPhone(),
                "createdAt", Date.from( user.getCreatedAt().atZone( ZoneId.systemDefault()).toInstant())  ,
                "lastLogin", Date.from( user.getLastLogin().atZone( ZoneId.systemDefault() ).toInstant())
        );
    }



}
