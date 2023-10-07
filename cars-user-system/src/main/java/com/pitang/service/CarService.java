package com.pitang.service;

import com.pitang.model.Car;
import com.pitang.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
	
    @Autowired
    private CarRepository carRepository;
    
    public List<Car> getAll() {
        return carRepository.findAll();
    }
    
    public Car findById(Long id) {
        return carRepository.findById(id).get();
    }
    
}
