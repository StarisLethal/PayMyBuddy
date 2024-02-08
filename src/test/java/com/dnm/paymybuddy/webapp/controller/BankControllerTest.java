package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.service.AccountService;
import com.dnm.paymybuddy.webapp.service.BankService;
import com.dnm.paymybuddy.webapp.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class BankControllerTest {

    @Mock
    private BankService bankService;

    @Mock
    private PersonService personService;

    @Mock
    private AccountService accountService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    private BankController bankController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bankController = new BankController(bankService, personService, accountService);
    }
    @Test
    public void testTransferToBank() {
        String userMail = "user@example.com";
        Person person = new Person();
        Account account = new Account();
        when(principal.getName()).thenReturn(userMail);
        when(personService.getPersonByMail(userMail)).thenReturn(person);
        when(accountService.getAccountByMail(userMail)).thenReturn(account);

        String viewName = bankController.transferToBank(model, principal, 100.0f);

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("account", account);
        verify(model).addAttribute("transferToBankAmount", 100.0f);
        assertEquals("tranferToBankConfirm", viewName);
    }

    @Test
    public void testTransferToBankConfirm() {
        String userMail = "user@example.com";
        Person person = new Person();
        Account account = new Account();
        float transferToBankAmount = 100.0f;
        when(principal.getName()).thenReturn(userMail);
        when(personService.getPersonByMail(userMail)).thenReturn(person);
        when(accountService.getAccountByMail(userMail)).thenReturn(account);

        String viewName = bankController.transferToBankConfirm(model, principal, transferToBankAmount);

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("account", account);
        verify(model).addAttribute("transferToBankAmount", transferToBankAmount);
        verify(bankService).transferToBank(account, transferToBankAmount);
        assertEquals("transfertobankconfirmed", viewName);
    }

    @Test
    public void testbankToAccount() {
        String userMail = "user@example.com";
        Person person = new Person();
        Account account = new Account();
        when(principal.getName()).thenReturn(userMail);
        when(personService.getPersonByMail(userMail)).thenReturn(person);
        when(accountService.getAccountByMail(userMail)).thenReturn(account);

        String viewName = bankController.bankToAccount(model, principal, 100.0f);

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("account", account);
        verify(model).addAttribute("transferToAccountAmount", 100.0f);
        assertEquals("transfertoaccountconfirm", viewName);
    }

    @Test
    public void testbankToAccountConfirm() {
        String userMail = "user@example.com";
        Person person = new Person();
        Account account = new Account();
        float transferToAccountAmount = 100.0f;
        when(principal.getName()).thenReturn(userMail);
        when(personService.getPersonByMail(userMail)).thenReturn(person);
        when(accountService.getAccountByMail(userMail)).thenReturn(account);

        String viewName = bankController.bankToAccountConfirm(model, principal, transferToAccountAmount);

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("account", account);
        verify(model).addAttribute("transferToAccountAmount", transferToAccountAmount);
        verify(bankService).bankToAccount(account, transferToAccountAmount);
        assertEquals("transfertoaccountconfirmed", viewName);
    }
}