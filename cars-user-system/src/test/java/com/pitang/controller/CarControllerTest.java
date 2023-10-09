package com.pitang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitang.dto.CarDTO;
import com.pitang.service.CarService;
import com.pitang.service.JwtTokenService;
import com.pitang.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Mock
    private LoginService loginService;

    @Mock
    private JwtTokenService jwtTokenService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testListAll() throws Exception {
        String authorizationHeader = "Bearer sampleToken";

        List<CarDTO> carDTOs = new ArrayList<>();
        carDTOs.add(CarDTO.builder()
                .id(1L)
                .year(2020)
                .licensePlate("ABCD123")
                .model("Toyota Camry")
                .color("Blue")
                .build());

        when(carService.getAll(anyLong())).thenReturn(carDTOs);
        when(jwtTokenService.getUsernameFromToken(authorizationHeader)).thenReturn("testuser");
        when(loginService.findIdUserByLogin("testuser")).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars")
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testFindById() throws Exception {
        Long carId = 1L;
        Long userId = 1L;
        String authorizationHeader = "Bearer sampleToken";

        CarDTO carDTO = CarDTO.builder()
                .id(carId)
                .year(2021)
                .licensePlate("XYZ123")
                .model("Honda Accord")
                .color("Red")
                .build();

        when(carService.findById(carId, userId)).thenReturn(carDTO);
        when(jwtTokenService.getUsernameFromToken(authorizationHeader)).thenReturn("testuser");
        when(loginService.findIdUserByLogin("testuser")).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/{id}", carId)
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        String authorizationHeader = "Bearer sampleToken";
        Long userId = 1L;

        CarDTO carDTO = CarDTO.builder()
                .year(2022)
                .licensePlate("EFGH456")
                .model("Ford Mustang")
                .color("Black")
                .build();

        when(carService.create(carDTO, userId)).thenReturn(carDTO);
        when(jwtTokenService.getUsernameFromToken(authorizationHeader)).thenReturn("testuser");
        when(loginService.findIdUserByLogin("testuser")).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        Long carId = 2L;
        Long userId = 1L;

        String authorizationHeader = "Bearer sampleToken";

        CarDTO carDTO = CarDTO.builder()
                .id(carId)
                .year(2023)
                .licensePlate("IJKL789")
                .model("Tesla Model 3")
                .color("White")
                .build();

        when(carService.update(carDTO, carId, userId)).thenReturn(carDTO);
        when(jwtTokenService.getUsernameFromToken(authorizationHeader)).thenReturn("testuser");
        when(loginService.findIdUserByLogin("testuser")).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cars/{id}", carId)
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Long carId = 3L;
        Long userId = 1L;

        String authorizationHeader = "Bearer sampleToken";

        doNothing().when(carService).delete(carId, userId);
        when(jwtTokenService.getUsernameFromToken(authorizationHeader)).thenReturn("testuser");
        when(loginService.findIdUserByLogin("testuser")).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cars/{id}", carId)
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(carService, times(1)).delete(carId, 1L);
    }
}


