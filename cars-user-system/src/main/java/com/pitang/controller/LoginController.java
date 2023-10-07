package com.pitang.controller;

import com.pitang.dto.LoginDTO;
import com.pitang.service.UserService;
import com.pitang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @PostMapping(value ="/signin")
    public ResponseEntity<?>  signIn(@RequestBody LoginDTO loginDTO) {
        userService.validateLogin(loginDTO);

        String token = JwtUtils.generateJwtToken(loginDTO.getLogin());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/me")
    public ResponseEntity<?> getInfoUser() {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }




}
