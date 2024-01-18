package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    private Integer accountId;
    private Float finances;
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Person person;

    public Account() {

    }
}
