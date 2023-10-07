package com.pitang.mapper;

import com.pitang.dto.UserDTO;
import com.pitang.model.User;
import com.pitang.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private LoginService loginService;

    public UserDTO toReponseUserDTO(User user) {
        if(Objects.isNull(user)){
            return null;
        }

        return UserDTO
                .builder()
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .birthday( user.getBirthday() )
                .login( user.getLogin() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .cars( user.getCars().stream().map( car -> carMapper.toCarDTO( car ) ).collect( Collectors.toList()) )
                .build();
    }

    public User toUser(UserDTO userDTO) {
        if(Objects.isNull(userDTO)){
            return null;
        }

        return User
                .builder()
                .email( userDTO.getEmail() )
                .phone( userDTO.getPhone() )
                .birthday( userDTO.getBirthday() )
                .login( userDTO.getLogin() )
                .firstName( userDTO.getFirstName() )
                .lastName( userDTO.getLastName() )
                .password( loginService.passwordEncoder( userDTO.getPassword() ) )
                .cars( userDTO.getCars().stream().map( carDTO -> carMapper.toCar( carDTO ) ).collect( Collectors.toList()) )
                .build();
    }

    public User toUserUpdate(UserDTO userDTO, User existingUser) {
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getBirthday() != null) {
            existingUser.setBirthday(userDTO.getBirthday());
        }
        if (userDTO.getLogin() != null) {
            existingUser.setLogin(userDTO.getLogin());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(loginService.passwordEncoder(userDTO.getPassword()));
        }
        if (userDTO.getPhone() != null) {
            existingUser.setPhone(userDTO.getPhone());
        }
        if (userDTO.getFirstName() != null) {
            existingUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            existingUser.setLastName(userDTO.getLastName());
        }
        //todo:
//        if(userDTO.getCars() != null){
//            existingUser.setCars( userDTO.getCars().stream().map( carDTO -> carMapper.toCar( carDTO ) ).collect( Collectors.toList()) );
//        }
        return existingUser;
    }
}
