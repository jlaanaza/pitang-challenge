package com.pitang.controller;

import com.pitang.dto.CarDTO;
import com.pitang.service.CarService;
import com.pitang.service.JwtTokenService;
import com.pitang.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> listAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        List<CarDTO> response = carService.getAll( getUserId( authorization ));
        return new ResponseEntity<>( response, new HttpHeaders(), HttpStatus.OK );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CarDTO> findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable Long id) {
        CarDTO response = carService.findById(id, getUserId( authorization ));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CarDTO carDTO) {
        CarDTO response = carService.create(carDTO, getUserId( authorization ));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CarDTO> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CarDTO carDTO, @PathVariable(value = "id") Long id) {
        CarDTO response = carService.update(carDTO, id, getUserId( authorization ));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CarDTO> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable Long id) {
        carService.delete( id , getUserId( authorization ));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    private Long getUserId (String authorization){
        String userName = jwtTokenService.getUsernameFromToken( authorization );
        return loginService.findIdUserByLogin( userName );
    }

}
