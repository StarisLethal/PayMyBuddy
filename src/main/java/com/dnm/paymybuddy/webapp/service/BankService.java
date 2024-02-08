package com.dnm.paymybuddy.webapp.service;


import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Bank;
import com.dnm.paymybuddy.webapp.repositories.BankRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class BankService {

    @Autowired
    private final BankRepository bankRepository;
    @Autowired
    private final AccountService accountService;

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public Float getBalance(Account account){
        Bank bank = account.getBank();
        return bank.getBalance();
    }

    @Transactional
    public Bank bankToAccount(Account account,  float transferToAccountAmount){
        Bank bank = account.getBank();
        if(bank.getBalance()< transferToAccountAmount){
            throw new IllegalArgumentException("Your balance is too low for that");
        }
        bank.setBalance(bank.getBalance() - transferToAccountAmount);
        account.setFinances(account.getFinances() + transferToAccountAmount);
        return save(bank);
    }

    @Transactional
    public Bank transferToBank(Account account,  float transferToBankAmount){
        Bank bank = account.getBank();
        if(account.getFinances()< transferToBankAmount){
            throw new IllegalArgumentException("Your balance is too low for that");
        }
        account.setFinances(account.getFinances() - transferToBankAmount);
        bank.setBalance(bank.getBalance() + transferToBankAmount);
        return save(bank);
    }

    @Transactional
    public Bank taxSave(float taxAmount) {
        Integer accountId = 40000;
        Account account = accountService.getPayMyBuddyAccount(accountId);
        Bank bank = bankRepository.getPayMyBuddyBank(account);
        bank.setBalance(bank.getBalance() + taxAmount);
        return save(bank);
    }
}
