package com.example.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.bank.repository.AccountRepository;
import com.example.bank.model.Account;
import com.example.bank.model.User;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getByUser(User user) {
        return accountRepository.findByOwner(user).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public Account deposit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}
