package com.dnm.paymybuddy.webapp.repositories;

import com.dnm.paymybuddy.webapp.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{
}
