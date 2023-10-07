package com.pitang.controller;

import com.pitang.dto.CarDTO;
import com.pitang.model.Car;
import com.pitang.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> listAll() {


        return new ResponseEntity<>( null, new HttpHeaders(), HttpStatus.OK );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CarDTO> findById(@PathVariable Long id) {
        Car response = null;
        try {
            response = carService.findById(id);
        } catch (NoSuchElementException noSuchException) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestBody Car Car) {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CarDTO> update(@RequestBody Car Car, @PathVariable(value = "id") Long id) {
        Car response = null;
        try {
            response = carService.findById(id);
        } catch (NoSuchElementException noSuchException) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CarDTO> delete(@PathVariable Long id) {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }



}
