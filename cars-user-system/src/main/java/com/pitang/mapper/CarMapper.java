package com.pitang.mapper;

import com.pitang.dto.CarDTO;
import com.pitang.model.Car;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CarMapper {

    public CarDTO toCarDTO(Car car) {
        if(Objects.isNull(car)){
            return null;
        }
        return CarDTO
                .builder()
                .color( car.getColor() )
                .licensePlate( car.getLicensePlate() )
                .year( car.getYear() )
                .model( car.getModel() )
                .build();
    }

    public Car toCar(CarDTO carDTO) {
        if(Objects.isNull(carDTO)){
            return null;
        }
        return Car
                .builder()
                .color( carDTO.getColor() )
                .licensePlate( carDTO.getLicensePlate() )
                .year( carDTO.getYear() )
                .model( carDTO.getModel() )
                .build();
    }
}
