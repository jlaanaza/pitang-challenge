package com.pitang.service;

import com.pitang.dto.UserDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.UserMapper;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map( user -> userMapper.toReponseUserDTO( user )).collect( Collectors.toList());
    }
    
    public UserDTO findById(Long id) {
        return userMapper.toReponseUserDTO( userRepository.findById(id).orElseThrow( NotFoundException::new ));
    }

    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toSaveUser( userDTO );

        return userMapper.toReponseUserDTO( userRepository.save(user) );
    }

    public UserDTO update(Long id , UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow( NotFoundException::new );

        User updatedUser = userMapper.toUserUpdate( userDTO, existingUser );

        return userMapper.toReponseUserDTO(userRepository.save(updatedUser));
    }

    public void delete(Long id ) {
        userRepository.findById(id).orElseThrow( NotFoundException::new );
        userRepository.deleteById(id);
    }
    
}
