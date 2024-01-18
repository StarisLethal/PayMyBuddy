package com.dnm.paymybuddy.webapp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Person {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "person_friends",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "email")
    )
    private List<Person> listOfFriend;
    private String role;
    public Person() {

    }
}
