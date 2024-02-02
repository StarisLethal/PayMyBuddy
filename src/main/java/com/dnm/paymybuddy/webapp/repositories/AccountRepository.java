package com.dnm.paymybuddy.webapp.repositories;

import com.dnm.paymybuddy.webapp.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{

    @Query("SELECT a.finances FROM Account a WHERE a.accountId = :accountId")
    float findFinanceById(Integer accountId);

    @Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
    Account getAccountId(Integer accountId);

    @Query("SELECT a FROM Account a WHERE a.person.email = :mail")
    Account getAccountByMail(String mail);

}

