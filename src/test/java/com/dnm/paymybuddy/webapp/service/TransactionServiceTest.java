package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Transaction;
import com.dnm.paymybuddy.webapp.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private BankService bankService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListSourceById() {
        Account account = new Account();
        account.setAccountId(1);

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAccountSource(account);
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAccountSource(account);
        transactions.add(transaction2);

        when(transactionRepository.findSourceById(account)).thenReturn(transactions);

        Iterable<Transaction> result = transactionService.listSourceById(account);

        verify(transactionRepository, times(1)).findSourceById(account);
        assertEquals(transactions, result);
    }

    @Test
    public void testListRecipientById() {
        Account account = new Account();
        account.setAccountId(1);

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAccountRecipient(account);
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAccountRecipient(account);
        transactions.add(transaction2);

        when(transactionRepository.findRecipientById(account)).thenReturn(transactions);

        Iterable<Transaction> result = transactionService.listRecipientById(account);

        verify(transactionRepository, times(1)).findRecipientById(account);
        assertEquals(transactions, result);
    }

    @Test
    void testPayment() {

        Account source = new Account();
        source.setFinances(100.00F);

        Account recipient = new Account();
        recipient.setFinances(50.00F);

        when(accountService.getAccountByMail("source@mail.com")).thenReturn(source);
        when(accountService.getAccountByMail("recipient@mail.com")).thenReturn(recipient);

        transactionService.payment("source@mail.com", "recipient@mail.com", 50, "test");

        verify(accountService).save(source);
        verify(accountService).save(recipient);
        verify(bankService).taxSave(2.50F);

        assertEquals(recipient.getFinances(), 100.00F);
        assertNotEquals(source.getFinances(), 100.00F);
    }

}