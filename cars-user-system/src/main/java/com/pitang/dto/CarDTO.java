package com.pitang.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
    @NotNull
    private Integer year;
    @NotEmpty
    private String licensePlate;
    @NotEmpty
    private String model;
    @NotEmpty
    private String color;

}
