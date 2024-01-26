package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.service.AccountService;
import com.dnm.paymybuddy.webapp.service.PersonService;
import com.dnm.paymybuddy.webapp.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class TransactionController {

    private final PersonService personService;
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(PersonService personService, TransactionService transactionService, AccountService accountService) {
        this.personService = personService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/transfer")
    public String home(Model model, Principal principal) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        Account account = accountService.getAccountId(userMail);
        Iterable<Transaction> transaction = transactionService.listById(account);
        model.addAttribute("person", person);
        model.addAttribute("transaction", transaction);
        return "transfer";
    }

    @PostMapping("/payment")
    public String processPayment(Model model,
                                 @RequestParam("accountSourceId") String accountSourceId,
                                 @RequestParam("accountRecipientId") String accountRecipientId,
                                 @RequestParam("amount") float amount,
                                 @RequestParam("description") String description) {

        try {
            transactionService.payment(accountSourceId, accountRecipientId, amount, description);
            return "transfer";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "test"; // Redirection vers une page d'erreur personnalisée
        }
    }
}