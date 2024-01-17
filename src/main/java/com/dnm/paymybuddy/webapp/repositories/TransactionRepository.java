package com.dnm.paymybuddy.webapp.repositories;

import com.dnm.paymybuddy.webapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
