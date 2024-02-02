package com.dnm.paymybuddy.webapp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "listOfFriend")
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
    private List<Person> listOfFriend = new ArrayList<>();
    @Column(nullable = false)
    private String role;
}
