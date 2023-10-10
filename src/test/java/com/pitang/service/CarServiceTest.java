package com.pitang.service;

import com.pitang.dto.CarDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.CarMapper;
import com.pitang.model.Car;
import com.pitang.repository.CarRepository;
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

public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        Long userId = 1L;
        List<Car> cars = new ArrayList<>();
        when(carRepository.findByUserId(userId)).thenReturn(Optional.of(cars));
        when(carMapper.toCarDTO(any())).thenReturn(new CarDTO());

        List<CarDTO> result = carService.getAll(userId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByIdCarFound() {
        Long userId = 1L;
        Long carId = 2L;
        Car car = new Car();
        when(carRepository.findByUserIdAndId(userId, carId)).thenReturn(Optional.of(car));
        when(carMapper.toCarDTO(car)).thenReturn(new CarDTO());

        CarDTO result = carService.findById(carId, userId);

        assertNotNull(result);
    }

    @Test
    public void testFindByIdCarNotFound() {
        Long userId = 1L;
        Long carId = 2L;
        when(carRepository.findByUserIdAndId(userId, carId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findById(carId, userId));
    }

    @Test
    public void testCreateCar() {
        Long userId = 1L;
        CarDTO carDTO = new CarDTO();
        Car car = new Car();
        when(carMapper.toSaveCar(carDTO, userId)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toCarDTO(car)).thenReturn(new CarDTO());

        CarDTO result = carService.create(carDTO, userId);

        assertNotNull(result);
    }

    @Test
    public void testUpdateCar() {
        Long userId = 1L;
        Long carId = 2L;
        CarDTO carDTO = new CarDTO();
        Car existingCar = new Car();
        Car updatedCar = new Car();
        when(carRepository.findByUserIdAndId(userId, carId)).thenReturn(Optional.of(existingCar));
        when(carMapper.toUpdateCar(carDTO, existingCar)).thenReturn(updatedCar);
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        when(carMapper.toCarDTO(updatedCar)).thenReturn(new CarDTO());

        CarDTO result = carService.update(carDTO, carId, userId);

        assertNotNull(result);
    }

    @Test
    public void testDeleteCar() {
        Long userId = 1L;
        Long carId = 2L;
        when(carRepository.findByUserIdAndId(userId, carId)).thenReturn(Optional.of(new Car()));

        assertDoesNotThrow(() -> carService.delete(carId, userId));
        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    public void testDeleteCarNotFound() {
        Long userId = 1L;
        Long carId = 2L;
        when(carRepository.findByUserIdAndId(userId, carId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.delete(carId, userId));
        verify(carRepository, never()).deleteById(carId);
    }
}
