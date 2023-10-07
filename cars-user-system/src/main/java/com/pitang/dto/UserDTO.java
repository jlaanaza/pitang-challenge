package com.pitang.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    //todo: create others validations
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String phone;
    @Valid
    private List<CarDTO> cars;

}
