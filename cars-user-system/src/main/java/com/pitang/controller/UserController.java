package com.pitang.controller;

import com.pitang.dto.UserDTO;
import com.pitang.model.User;
import com.pitang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {

        return new ResponseEntity<>( null, new HttpHeaders(), HttpStatus.OK );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User response = null;
        try {
            response = userService.findById(id);
        } catch (NoSuchElementException noSuchException) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody User User) {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody User User, @PathVariable(value = "id") Long id) {
        User response = null;
        try {
            response = userService.findById(id);
        } catch (NoSuchElementException noSuchException) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }



}
