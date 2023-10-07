package com.pitang.service;

import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
    
}
