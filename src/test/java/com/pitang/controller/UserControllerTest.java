package com.pitang.controller;

import com.pitang.dto.UserDTO;
import com.pitang.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testListAll() throws Exception {
        List<UserDTO> userDTOS = new ArrayList<>();
        when(userService.getAll()).thenReturn(userDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testFindById() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setBirthday(LocalDate.of(1990, 1, 1));
        userDTO.setLogin("johndoe");
        userDTO.setPassword("password");
        userDTO.setPhone("1234567890");
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setLastLogin(LocalDateTime.now());

        when(userService.findById(userId)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"));
    }

    @Test
    public void testCreate() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setBirthday(LocalDate.of(1990, 1, 1));
        userDTO.setLogin("johndoe");
        userDTO.setPassword("password");
        userDTO.setPhone("1234567890");
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setLastLogin(LocalDateTime.now());

        when(userService.create(userDTO)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Doe\",\n" +
                                "    \"email\": \"john@example.com\",\n" +
                                "    \"birthday\": \"1990-01-01\",\n" +
                                "    \"login\": \"johndoe\",\n" +
                                "    \"password\": \"password\",\n" +
                                "    \"phone\": \"1234567890\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("UpdatedJohn");
        userDTO.setLastName("UpdatedDoe");
        userDTO.setEmail("updatedjohn@example.com");
        userDTO.setBirthday(LocalDate.of(1995, 5, 5));
        userDTO.setLogin("updatedjohndoe");
        userDTO.setPassword("updatedpassword");
        userDTO.setPhone("9876543210");
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setLastLogin(LocalDateTime.now());

        when(userService.update(userId, userDTO)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"UpdatedJohn\",\n" +
                                "    \"lastName\": \"UpdatedDoe\",\n" +
                                "    \"email\": \"updatedjohn@example.com\",\n" +
                                "    \"birthday\": \"1995-05-05\",\n" +
                                "    \"login\": \"updatedjohndoe\",\n" +
                                "    \"password\": \"updatedpassword\",\n" +
                                "    \"phone\": \"9876543210\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).delete(userId);
    }
}

