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
    private final BankService bankService;
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TransactionRepository transactionRepository;

    public Iterable<Transaction> listSourceById(Account account){return transactionRepository.findSourceById(account);}
    public Iterable<Transaction> listRecipientById(Account account){return transactionRepository.findRecipientById(account);}

    @Transactional
    public void payment(String accountSourceMail, String accountRecipientMail, float amount, String description){
        float taxAmount = amount * 0.05F;
        Account sourceAccount = accountService.getAccountByMail(accountSourceMail)/*.orElseThrow(() -> new IllegalArgumentException("Compte débiteur non trouvé"))*/;
        Account recipientAccount = accountService.getAccountByMail(accountRecipientMail)/*.orElseThrow(() -> new IllegalArgumentException("Compte crediteur non trouvé"))*/;

        if(sourceAccount.getFinances()<(amount+taxAmount)){
            throw new IllegalArgumentException("Your balance is too low for that");
        }

        sourceAccount.setFinances(sourceAccount.getFinances() - taxAmount);
        sourceAccount.setFinances(sourceAccount.getFinances() - amount);
        accountService.save(sourceAccount);

        /*Recuperation de la taxe de transaction*/
        bankService.taxSave(taxAmount);

        recipientAccount.setFinances(recipientAccount.getFinances() + amount);
        accountService.save(recipientAccount);

        /*Sauvegarde de la transaction*/
        Transaction transaction = new Transaction();

        transaction.setAccountSource(sourceAccount);
        transaction.setAccountRecipient(recipientAccount);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);
    }

}
