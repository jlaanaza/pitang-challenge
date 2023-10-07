package com.pitang.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="car_sequence")
    @SequenceGenerator(name="car_sequence", sequenceName="SEQ_CAR",allocationSize = 1)
    private Long id;
    @Column(name = "year")
    private Integer year;
    @Column(name = "license_plate")
    private String licensePlate;
    @Column(name = "model")
    private String model;
    @Column(name = "color")
    private String color;

}
