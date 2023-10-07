package com.pitang.service;

import com.pitang.dto.LoginDTO;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User validateLogin(LoginDTO loginDTO) {
        User user = userRepository.findByLogin(loginDTO.getLogin()).orElse( null );
        if (user == null || !this.validatePassword(loginDTO.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid login or password");
        }
        return user;
    }

    public String passwordEncoder(String password){
       return passwordEncoder.encode(password);
    }

    private boolean validatePassword(String passwordRequest , String passwordUserDB) {
        return passwordEncoder.matches( passwordRequest, passwordUserDB );
    }
}
