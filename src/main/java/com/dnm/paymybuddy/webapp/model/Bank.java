package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_bank")
@ToString(exclude = "accountData")
public class Bank {

    @Id

    @Column(name = "account")
    private Integer account;
    @Column(nullable = false)
    private float balance;
    @OneToOne
    @JoinColumn(name = "account", referencedColumnName = "account_id")
    private Account accountData;

}
