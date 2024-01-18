package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.repositories.AccountRepository;
import com.dnm.paymybuddy.webapp.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class TransactionService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TransactionRepository transactionRepository;

    public Iterable<Transaction> listById(String accountId){return transactionRepository.findAllById(accountId);}

    @Transactional
    public void payment(Integer accountSourceId, Integer accountRecipientId, float amount){

        if(accountSourceId.equals(accountRecipientId)){
            throw new IllegalArgumentException("Vous ne pouvez pas vous envoyer de l'argent");
        }

        Account sourceAccount = accountRepository.findById(accountSourceId).orElseThrow(() -> new IllegalArgumentException("Compte débiteur non trouvé"));
        Account recipientAccount = accountRepository.findById(accountRecipientId).orElseThrow(() -> new IllegalArgumentException("Compte crediteur non trouvé"));

        if(sourceAccount.getFinances()<amount){
            throw new IllegalArgumentException("Vous ne disposez pas de suffisament de fond");
        }

        sourceAccount.setFinances(sourceAccount.getFinances() - amount);
        accountRepository.save(sourceAccount);

        recipientAccount.setFinances(recipientAccount.getFinances() + amount);
        accountRepository.save(recipientAccount);

        Transaction transaction = new Transaction();

        transaction.setAccountSourceId(accountSourceId);
        transaction.setAccountRecipientId(accountRecipientId);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }

}
