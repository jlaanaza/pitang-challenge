package com.pitang.controller;

import com.pitang.dto.UserDTO;
import com.pitang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {

        List<UserDTO> userDTOS = userService.getAll();

        return new ResponseEntity<>( userDTOS, new HttpHeaders(), HttpStatus.OK );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO response = userService.findById(id);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {

        UserDTO response = userService.create(userDTO);

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable(value = "id") Long id) {
        UserDTO response = userService.update(id, userDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        userService.delete( id );
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }



}
