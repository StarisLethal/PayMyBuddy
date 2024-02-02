package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPayMyBuddyAccount() {
        Integer accountId = 123;
        Account account = new Account();
        account.setAccountId(accountId);

        when(accountRepository.getAccountId(accountId)).thenReturn(account);

        Account result = accountService.getPayMyBuddyAccount(accountId);

        verify(accountRepository).getAccountId(accountId);
        assertEquals(account, result);
    }

    @Test
    public void testGetAccountByMail() {
        String mail = "test@example.com";
        Person person = new Person();
        person.setEmail(mail);

        Account account = new Account();
        account.setPerson(person);

        when(accountRepository.getAccountByMail(mail)).thenReturn(account);

        Account result = accountService.getAccountByMail(mail);

        verify(accountRepository).getAccountByMail(mail);
        assertEquals(account, result);
    }

    @Test
    public void testSave() {
        Account account = new Account();

        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.save(account);

        verify(accountRepository).save(account);
        assertEquals(account, result);
    }
}