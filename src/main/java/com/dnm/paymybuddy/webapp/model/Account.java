package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    private Integer accountId;
    private Float finances;

    public Account() {

    }
}
