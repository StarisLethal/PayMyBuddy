package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    private Integer accountId;
    @Column(nullable = false)
    private Float finances;
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Person person;

    public Account() {

    }
}
