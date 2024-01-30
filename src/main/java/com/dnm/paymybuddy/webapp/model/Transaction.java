package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    @ManyToOne
    @JoinColumn(name = "accountSourceId", referencedColumnName = "account_id")
    private Account accountSource;
    @ManyToOne
    @JoinColumn(name = "accountRecipientId", referencedColumnName = "account_id")
    private Account accountRecipient;
    @Column(nullable = false)
    private float amount;
    @Column(nullable = false)
    private String description;

    public Transaction() {

    }
}
