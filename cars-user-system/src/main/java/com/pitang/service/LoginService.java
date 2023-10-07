package com.pitang.service;

import com.pitang.dto.LoginDTO;
import com.pitang.dto.UserDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.UserMapper;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import com.pitang.utils.PasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private PasswordEncoderUtils passwordEncoderUtils;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO validateLogin(LoginDTO loginDTO) {
        User user = userRepository.findByLogin(loginDTO.getLogin()).orElse( null );
        if (Objects.isNull(user) || Objects.isNull(loginDTO.getPassword()) || !passwordEncoderUtils.validatePassword(loginDTO.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid login or password");
        }
        user.setLastLogin( LocalDateTime.now() );
        return userMapper.toTokenUserDTO( userRepository.save( user ) );
    }

    public Long findIdUserByLogin(String username) {
        return userRepository.findByLogin(username).orElseThrow( NotFoundException::new).getId();
    }

    public UserDTO findByUsername(String username) {
        return userMapper.toTokenUserDTO( userRepository.findByLogin(username).orElseThrow( NotFoundException::new) );
    }

}
