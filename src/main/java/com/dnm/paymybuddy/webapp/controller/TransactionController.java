package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.service.AccountService;
import com.dnm.paymybuddy.webapp.service.BankService;
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
    private final BankService bankService;

    public TransactionController(PersonService personService, TransactionService transactionService, AccountService accountService, BankService bankService) {
        this.personService = personService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.bankService = bankService;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        Account account = accountService.getAccountByMail(userMail);
        Iterable<Transaction> transaction = transactionService.listRecipientById(account);
        float finances = account.getFinances();
        float balance = bankService.getBalance(account);

        model.addAttribute("person", person);
        model.addAttribute("account", account);
        model.addAttribute("transaction", transaction);
        model.addAttribute("finances", finances);
        model.addAttribute("balance", balance);

        return "home";

    }

    @GetMapping("/transfer")
    public String transfer(Model model, Principal principal) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        Account account = accountService.getAccountByMail(userMail);
        Iterable<Transaction> transaction = transactionService.listSourceById(account);
        model.addAttribute("person", person);
        model.addAttribute("transaction", transaction);
        return "transfer";
    }

    @PostMapping("/payment")
    public String processPayment(Model model, Principal principal,
                                 @RequestParam("accountSourceMail") String accountSourceMail,
                                 @RequestParam("accountRecipientMail") String accountRecipientMail,
                                 @RequestParam("amount") float amount,
                                 @RequestParam("description") String description){

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);

        model.addAttribute("accountSourceMail", accountSourceMail);
        model.addAttribute("accountRecipientMail", accountRecipientMail);
        model.addAttribute("amount", amount);
        model.addAttribute("description", description);

        return "transferconfirm";
    }

    @PostMapping("/transferConfirm")
    public String confirmPayment(Model model, Principal principal,
                                 @RequestParam("accountSourceMail") String accountSourceMail,
                                 @RequestParam("accountRecipientMail") String accountRecipientMail,
                                 @RequestParam("amount") float amount,
                                 @RequestParam("description") String description) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);

        try {
            transactionService.payment(accountSourceMail, accountRecipientMail, amount, description);

            model.addAttribute("accountSourceMail", accountSourceMail);
            model.addAttribute("accountRecipientMail", accountRecipientMail);
            model.addAttribute("amount", amount);
            model.addAttribute("description", description);

            return "transferconfirmed";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "test";
        }
    }
}