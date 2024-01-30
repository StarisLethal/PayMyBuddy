package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @Column(name = "account_id")
    private Integer accountId;
    @Column(nullable = false)
    private Float finances;
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Person person;

    public Account() {

    }
}
