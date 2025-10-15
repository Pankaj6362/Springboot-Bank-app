package com.example.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.bank.repository.UserRepository;
import com.example.bank.repository.AccountRepository;
import com.example.bank.model.User;
import com.example.bank.model.Account;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    public User signup(String email, String password, String fullName) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User u = new User();
        u.setEmail(email);
        u.setFullName(fullName);
        u.setPasswordHash(encoder.encode(password));
        User saved = userRepo.save(u);

        Account acct = new Account();
        acct.setOwner(saved);
        acct.setBalance(java.math.BigDecimal.ZERO);
        accountRepo.save(acct);
        return saved;
    }

    public User authenticate(String email, String password) {
        User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(password, u.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return u;
    }

    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }
}
