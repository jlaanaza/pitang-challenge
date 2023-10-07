package com.pitang.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserDTO {
	
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String login;
    private String password;
    private String phone;
    private List<CarDTO> cars;

}
