package com.dnm.paymybuddy.webapp.repositories;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.accountSource = :account")
    Iterable<Transaction> findSourceById(Account account);

    @Query("SELECT t FROM Transaction t WHERE t.accountRecipient = :account")
    Iterable<Transaction> findRecipientById(Account account);


}

