package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.repositories.AccountRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public Optional<Account> get(Integer accountId){return accountRepository.findById(accountId);}

    public Float getFinanceById(Integer accountId){return accountRepository.findFinanceById(accountId);}

    public Account getAccountId(String mail){return accountRepository.getAccountId(mail);}

    public Account getAccountByMail(String mail){return accountRepository.getAccountIdByMail(mail);}

    public Account save(Account account){return accountRepository.save(account);}

}
