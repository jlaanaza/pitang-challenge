package com.pitang.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    @NotEmpty
    @Size(max=200)
    private String firstName;
    @NotEmpty
    @Size(max=200)
    private String lastName;
    @NotEmpty
    @Size(max=200)
    private String email;
    @NotNull
    @Past
    private LocalDate birthday;
    @NotEmpty
    @Size(max=200)
    private String login;
    @NotEmpty
    @Size(min=6, max=200)
    private String password;
    @NotEmpty
    @Size(max=200)
    private String phone;
    @Valid
    private List<CarDTO> cars;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastLogin;

}
