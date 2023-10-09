package com.pitang.service;

import com.pitang.dto.UserDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.UserMapper;
import com.pitang.model.User;
import com.pitang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toReponseUserDTO(any())).thenReturn(new UserDTO());

        List<UserDTO> result = userService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByIdUserFound() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toReponseUserDTO(user)).thenReturn(new UserDTO());

        UserDTO result = userService.findById(userId);

        assertNotNull(result);
    }

    @Test
    public void testFindByIdUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userMapper.toSaveUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toReponseUserDTO(user)).thenReturn(new UserDTO());

        UserDTO result = userService.create(userDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User existingUser = new User();
        User updatedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.toUserUpdate(userDTO, existingUser)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toReponseUserDTO(updatedUser)).thenReturn(new UserDTO());

        UserDTO result = userService.update(userId, userDTO);

        assertNotNull(result);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        assertDoesNotThrow(() -> userService.delete(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, never()).deleteById(userId);
    }
}
