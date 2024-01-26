package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class TransactionService {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TransactionRepository transactionRepository;

    public Iterable<Transaction> listById(Account account){return transactionRepository.findSourceById(account);}

    @Transactional
    public void payment(String accountSourceMail, String accountRecipientMail, Float amount, String description){

        Account sourceAccount = accountService.getAccountByMail(accountSourceMail)/*.orElseThrow(() -> new IllegalArgumentException("Compte débiteur non trouvé"))*/;
        Account recipientAccount = accountService.getAccountByMail(accountRecipientMail)/*.orElseThrow(() -> new IllegalArgumentException("Compte crediteur non trouvé"))*/;

        Integer accountSourceId = sourceAccount.getAccountId();
        Integer accountRecipientId = recipientAccount.getAccountId();

        if(accountSourceId.equals(accountRecipientId)){
            throw new IllegalArgumentException("Vous ne pouvez pas vous envoyer de l'argent");
        }


        if(sourceAccount.getFinances()<(amount+(amount*0.05))){
            throw new IllegalArgumentException("Vous ne disposez pas de suffisament de fond");
        }

        sourceAccount.setFinances(sourceAccount.getFinances() - amount);
        accountService.save(sourceAccount);

        recipientAccount.setFinances(recipientAccount.getFinances() + amount);
        accountService.save(recipientAccount);

        Transaction transaction = new Transaction();

        transaction.setAccountSource(sourceAccount);
        transaction.setAccountRecipient(recipientAccount);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);
    }

}
