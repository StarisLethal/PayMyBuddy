package com.dnm.paymybuddy.webapp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
@ToString(exclude = "bank")
public class Account {

    @Id
    @Column(name = "account_id")
    private Integer accountId;
    @Column(nullable = false)
    private float finances;
    @OneToOne(mappedBy = "accountData")
    private Bank bank;
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Person person;

}
