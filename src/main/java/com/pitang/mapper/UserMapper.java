package com.pitang.mapper;

import com.pitang.dto.UserDTO;
import com.pitang.model.User;
import com.pitang.utils.PasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private PasswordEncoderUtils passwordEncoderUtils;

    public UserDTO toReponseUserDTO(User user) {
        if(Objects.isNull(user)){
            return null;
        }

        return UserDTO
                .builder()
                .id(user.getId())
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .birthday( user.getBirthday() )
                .login( user.getLogin() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .cars( Objects.isNull( user.getCars() ) ? null : user.getCars().stream().map( car -> carMapper.toCarDTO( car ) ).collect( Collectors.toList()) )
                .build();
    }

    public UserDTO toTokenUserDTO(User user) {
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
                .cars( Objects.isNull( user.getCars() ) ? null : user.getCars().stream().map( car -> carMapper.toCarDTO( car ) ).collect( Collectors.toList()) )
                .createdAt( user.getCreatedAt() )
                .lastLogin( user.getLastLogin() )
                .build();
    }

    public User toSaveUser(UserDTO userDTO) {
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
                .password( passwordEncoderUtils.passwordEncoder( userDTO.getPassword() ) )
                .cars( Objects.isNull( userDTO.getCars() ) ? null : userDTO.getCars().stream().map( carDTO -> carMapper.toCar( carDTO ) ).collect( Collectors.toList() ) )
                .createdAt( LocalDateTime.now() )
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
            existingUser.setPassword( passwordEncoderUtils.passwordEncoder(userDTO.getPassword()));
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
        return existingUser;
    }
}
