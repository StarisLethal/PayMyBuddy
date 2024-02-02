package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.repositories.AccountRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;



    public Account getPayMyBuddyAccount(Integer accountId){return accountRepository.getAccountId(accountId);}

    public Account getAccountByMail(String mail){return accountRepository.getAccountByMail(mail);}

    public Account save(Account account){return accountRepository.save(account);}

}
