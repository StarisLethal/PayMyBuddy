package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    private Integer transactionId;
    private Integer accountSourceId;
    private Integer accountRecipientId;
    private float amount;

    public Transaction() {

    }
}
