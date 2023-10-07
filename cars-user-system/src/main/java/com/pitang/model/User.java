package com.pitang.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence")
    @SequenceGenerator(name="user_sequence", sequenceName="SEQ_USER",allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

}
