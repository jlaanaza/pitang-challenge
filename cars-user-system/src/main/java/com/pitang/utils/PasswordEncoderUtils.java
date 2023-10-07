package com.pitang.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtils {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String passwordEncoder(String password){
        return passwordEncoder.encode(password);
    }

    public boolean validatePassword(String passwordRequest , String passwordUserDB) {
        return passwordEncoder.matches( passwordRequest, passwordUserDB );
    }
}
