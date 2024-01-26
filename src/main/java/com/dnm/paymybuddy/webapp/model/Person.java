package com.dnm.paymybuddy.webapp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Person {

    @Id
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @ManyToMany
    @JoinTable(
            name = "listoffriend",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "email")
    )
    private List<Person> listOfFriend;
    @Column(nullable = false)
    private String role;
    public Person() {

    }
}
