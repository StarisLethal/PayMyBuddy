package com.dnm.paymybuddy.webapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Person {

    @Id
    private String email;
    @Column(name ="firstName")
    private String firstName;
    @Column(name ="lastName")
    private String lastName;
    private String password;
    private String role;
    public Person() {

    }
}
