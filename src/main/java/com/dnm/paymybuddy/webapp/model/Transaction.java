package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    private Integer transactionId;
    @ManyToOne
    @JoinColumn(name = "accountSourceId", referencedColumnName = "accountId")
    private Account accountSource;
    @ManyToOne
    @JoinColumn(name = "accountRecipientId", referencedColumnName = "accountId")
    private Account accountRecipient;
    @Column(nullable = false)
    private float amount;
    @Column(nullable = false)
    private String description;

    public Transaction() {

    }
}
