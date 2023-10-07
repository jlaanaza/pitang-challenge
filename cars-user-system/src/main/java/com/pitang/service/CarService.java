package com.pitang.service;

import com.pitang.dto.CarDTO;
import com.pitang.exception.NotFoundException;
import com.pitang.mapper.CarMapper;
import com.pitang.model.Car;
import com.pitang.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
	
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;
    
    public List<CarDTO> getAll(Long userId) {
        List<Car> listCar = carRepository.findByUserId(userId).orElse( new ArrayList<>() );
        return listCar.stream().map( car -> carMapper.toCarDTO(car)  ).collect( Collectors.toList());
    }
    
    public CarDTO findById(Long id, Long userId) {
        return carMapper.toCarDTO( carRepository.findByUserIdAndId(userId, id).orElseThrow( NotFoundException::new ));
    }

    public CarDTO create(CarDTO carDTO, Long userId) {
        Car car = carMapper.toSaveCar( carDTO, userId );

        return carMapper.toCarDTO( carRepository.save(car) );
    }

    public CarDTO update(CarDTO carDTO, Long id, Long userId) {
        Car existingCar = carRepository.findByUserIdAndId(userId, id).orElseThrow( NotFoundException::new);
        Car updateCar = carMapper.toUpdateCar( carDTO, existingCar );

        return carMapper.toCarDTO(carRepository.save(updateCar));
    }

    public void delete(Long id, Long userId ) {
        carRepository.findByUserIdAndId(userId, id).orElseThrow( NotFoundException::new);
        carRepository.deleteById(id);
    }
    
}
