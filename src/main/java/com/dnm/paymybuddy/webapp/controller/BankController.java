package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.service.AccountService;
import com.dnm.paymybuddy.webapp.service.BankService;
import com.dnm.paymybuddy.webapp.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class BankController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final BankService bankService;
    private final PersonService personService;
    private final AccountService accountService;

    public BankController(BankService bankService, PersonService personService, AccountService accountService) {
        this.bankService = bankService;
        this.personService = personService;
        this.accountService = accountService;
    }

    @PostMapping("/transferToBank")
    public String transferToBank(Model model, Principal principal,
                                 @RequestParam float transferToBankAmount) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        Account account = accountService.getAccountByMail(userMail);
        model.addAttribute("person", person);

        model.addAttribute("account", account);
        model.addAttribute("transferToBankAmount", transferToBankAmount);

        return "tranferToBankConfirm";

    }

    @PostMapping("/tranferToBankConfirm")
    public String transferToBankConfirm(Model model, Principal principal,

                                        @RequestParam float transferToBankAmount) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        Account account = accountService.getAccountByMail(userMail);
        model.addAttribute("person", person);

        model.addAttribute("account", account);
        model.addAttribute("transferToBankAmount", transferToBankAmount);

        try {
            bankService.transferToBank(account, transferToBankAmount);

            return "transfertobankconfirmed";

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "test";
        }
    }
}