package com.pitang.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class LoginController {


    @PostMapping(value ="/signin")
    public ResponseEntity<?> signIn() {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/me")
    public ResponseEntity<?> getInfoUser() {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }



}
