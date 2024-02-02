package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.service.AccountService;
import com.dnm.paymybuddy.webapp.service.BankService;
import com.dnm.paymybuddy.webapp.service.PersonService;
import com.dnm.paymybuddy.webapp.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private PersonService personService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private BankService bankService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(personService, transactionService, accountService, bankService)).build();
    }

    @Test
    public void testHome() throws Exception {
        Person person = new Person();
        Account account = new Account();
        float balance = 100.0F;
        when(personService.getPersonByMail(anyString())).thenReturn(person);
        when(accountService.getAccountByMail(anyString())).thenReturn(account);
        when(transactionService.listRecipientById(account)).thenReturn(new ArrayList<>());
        when(bankService.getBalance(account)).thenReturn(balance);

        mockMvc.perform(get("/").principal(() -> "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("person", person))
                .andExpect(model().attribute("account", account))
                .andExpect(model().attribute("finances", account.getFinances()))
                .andExpect(model().attribute("balance", 100.0f))
                .andExpect(view().name("home"));

        verify(personService).getPersonByMail("user@example.com");
        verify(accountService).getAccountByMail("user@example.com");
        verify(transactionService).listRecipientById(account);
        verify(bankService).getBalance(account);
        assertEquals(balance, bankService.getBalance(account));
    }

    @Test
    public void testTransfer() {
        Person person = new Person();
        Account account = new Account();
        List<Transaction> transactions = new ArrayList<>();

        when(principal.getName()).thenReturn("testUserMail");
        when(personService.getPersonByMail("testUserMail")).thenReturn(person);
        when(accountService.getAccountByMail("testUserMail")).thenReturn(account);
        when(transactionService.listSourceById(account)).thenReturn(transactions);

        String result = transactionController.transfer(model, principal);

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("transaction", transactions);

        assertEquals("transfer", result);
    }

    @Test
    public void testProcessPayment() {
        Person person = new Person();

        when(principal.getName()).thenReturn("testUserMail");
        when(personService.getPersonByMail("testUserMail")).thenReturn(person);

        String result = transactionController.processPayment(model, principal, "sourceMail", "recipientMail", 100.0f, "Payment description");

        verify(model).addAttribute("person", person);
        verify(model).addAttribute("accountSourceMail", "sourceMail");
        verify(model).addAttribute("accountRecipientMail", "recipientMail");
        verify(model).addAttribute("amount", 100.0f);
        verify(model).addAttribute("description", "Payment description");

        assertEquals("transferconfirm", result);
    }

    @Test
    public void testConfirmPayment() {
        Person person = new Person();
        Account sourceAccount = new Account();
        Account recipientAccount = new Account();

        when(principal.getName()).thenReturn("testUserMail");
        when(personService.getPersonByMail("testUserMail")).thenReturn(person);
        when(accountService.getAccountByMail("sourceMail")).thenReturn(sourceAccount);
        when(accountService.getAccountByMail("recipientMail")).thenReturn(recipientAccount);
        doNothing().when(transactionService).payment("sourceMail", "recipientMail", 100.0f, "Payment description");
        String result = transactionController.confirmPayment(model, principal, "sourceMail", "recipientMail", 100.0f, "Payment description");

        verify(model).addAttribute("person", person);
        verify(transactionService).payment("sourceMail", "recipientMail", 100.0f, "Payment description");
        verify(model).addAttribute("accountSourceMail", "sourceMail");
        verify(model).addAttribute("accountRecipientMail", "recipientMail");
        verify(model).addAttribute("amount", 100.0f);
        verify(model).addAttribute("description", "Payment description");

        assertEquals("transferconfirmed", result);
    }
}