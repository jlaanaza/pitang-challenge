package com.pitang.service;

import com.pitang.dto.LoginDTO;
import com.pitang.dto.UserDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.UserMapper;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import com.pitang.utils.PasswordEncoderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private PasswordEncoderUtils passwordEncoderUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateLoginInvalidCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("testuser");
        loginDTO.setPassword("wrongPassword");

        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setPassword("hashedPassword");

        when(userRepository.findByLogin("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoderUtils.validatePassword("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows( UsernameNotFoundException.class, () -> loginService.validateLogin(loginDTO));
    }

    @Test
    public void testFindIdUserByLoginUserFound() {
        String username = "testuser";
        User user = new User();
        user.setId(1L);

        when(userRepository.findByLogin(username)).thenReturn(Optional.of(user));

        Long result = loginService.findIdUserByLogin(username);

        assertEquals(1L, result);
    }

    @Test
    public void testFindIdUserByLoginUserNotFound() {
        String username = "";

        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> loginService.findIdUserByLogin(username));
    }

    @Test
    public void testFindByUsernameUserFound() {
        String username = "testuser";
        User user = new User();
        user.setId(1L);

        when(userRepository.findByLogin(username)).thenReturn(Optional.of(user));
        when(userMapper.toTokenUserDTO(user)).thenReturn(new UserDTO());

        UserDTO result = loginService.findByUsername(username);

        assertNotNull(result);
    }

    @Test
    public void testFindByUsernameUserNotFound() {
        String username = "";

        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> loginService.findByUsername(username));
    }
}
