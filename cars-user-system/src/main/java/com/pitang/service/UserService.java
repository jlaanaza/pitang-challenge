package com.pitang.service;

import com.pitang.dto.LoginDTO;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }



    public void validateLogin(LoginDTO loginDTO) {
        User user = userRepository.findByLogin(loginDTO.getLogin());
        if (user == null || !this.validatePassword(loginDTO.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid login or password");
        }
    }

    private boolean validatePassword(String passwordRequest , String passwordUserDB) {
        return passwordEncoder.matches( passwordRequest, passwordUserDB );
    }
    
}
