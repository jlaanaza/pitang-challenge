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

    public Car toSaveCar(CarDTO carDTO, Long userId) {
        if(Objects.isNull(carDTO)){
            return null;
        }
        return Car
                .builder()
                .color( carDTO.getColor() )
                .licensePlate( carDTO.getLicensePlate() )
                .year( carDTO.getYear() )
                .model( carDTO.getModel() )
                .userId( userId )
                .build();
    }

    public Car toUpdateCar(CarDTO carDTO, Car exitingCar) {
        if(carDTO.getLicensePlate() != null){
            exitingCar.setLicensePlate( carDTO.getLicensePlate() );
        }
        if(carDTO.getYear() != null){
            exitingCar.setYear( carDTO.getYear() );
        }
        if(carDTO.getModel() != null){
            exitingCar.setModel( carDTO.getModel() );
        }
        if(carDTO.getColor() != null){
            exitingCar.setColor( carDTO.getColor() );
        }
        return exitingCar;
    }
}