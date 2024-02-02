package com.dnm.paymybuddy.webapp.service;

import com.dnm.paymybuddy.webapp.model.Account;
import com.dnm.paymybuddy.webapp.model.Bank;
import com.dnm.paymybuddy.webapp.repositories.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class BankServiceTest {

    @InjectMocks
    private BankService bankService;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testSave() {
        Bank bank = new Bank();

        when(bankRepository.save(bank)).thenReturn(bank);

        Bank result = bankService.save(bank);

        verify(bankRepository).save(bank);
        assertEquals(bank, result);
    }

    @Test
    public void testGetBalance() {
        Account account = new Account();
        Bank bank = new Bank();
        bank.setBalance(100.0F);
        account.setBank(bank);

        float result = bankService.getBalance(account);

        assertEquals(bank.getBalance(), result);
    }

    @Test
    public void testTransferToBank() {
        Account account = new Account();
        Bank bank = new Bank();
        bank.setBalance(404.0F);
        account.setBank(bank);
        account.setFinances(96.0F);
        float transferAmount = 42.0F;
        float expectedBankBalance = bank.getBalance() + transferAmount;
        float expectedAccountFinances = account.getFinances() - transferAmount;

        when(bankRepository.save(bank)).thenReturn(bank);

        Bank result = bankService.transferToBank(account, transferAmount);

        verify(bankRepository).save(bank);
        assertEquals(expectedBankBalance, bank.getBalance());
        assertEquals(expectedAccountFinances, account.getFinances());
        assertEquals(bank, result);
    }

    @Test
    public void testTaxSave() {
        float taxAmount = 10.0F;
        float balanceTest = 100.00F;
        Integer accountId = 40000;
        Account payMyBuddyAccount = new Account();
        payMyBuddyAccount.setAccountId(40000);
        Bank payMyBuddyBank = new Bank();
        payMyBuddyBank.setAccount(40000);
        payMyBuddyBank.setBalance(balanceTest);

        when(accountService.getPayMyBuddyAccount(accountId)).thenReturn(payMyBuddyAccount);
        when(bankRepository.getPayMyBuddyBank(payMyBuddyAccount)).thenReturn(payMyBuddyBank);
        when(bankRepository.save(payMyBuddyBank)).thenReturn(payMyBuddyBank);

        Bank result = bankService.taxSave(taxAmount);

        verify(bankRepository).save(payMyBuddyBank);
        assertEquals(balanceTest + taxAmount, result.getBalance());
    }
}