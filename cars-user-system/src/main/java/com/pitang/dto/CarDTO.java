package com.pitang.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
    private Long id;
    @NotNull
    private Integer year;
    @NotEmpty
    @Size(max = 8)
    private String licensePlate;
    @NotEmpty
    @Size(max = 200)
    private String model;
    @NotEmpty
    @Size(max = 200)
    private String color;

}
