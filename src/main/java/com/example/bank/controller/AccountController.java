package com.example.bank.controller;

import org.springframework.web.bind.annotation.*;
import com.example.bank.service.AccountService;
import com.example.bank.service.TransactionService;
import com.example.bank.service.AuthService;
import com.example.bank.model.Account;
import com.example.bank.model.User;
import com.example.bank.dto.TransactionRequest;
import com.example.bank.util.SimpleSessionStore;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService txService;
    private final AuthService authService;

    public AccountController(AccountService accountService, TransactionService txService, AuthService authService) {
        this.accountService = accountService;
        this.txService = txService;
        this.authService = authService;
    }

    private User requireUser(String token) {
        if (token == null) throw new RuntimeException("Missing session token (X-Session-Token header)");
        String userId = SimpleSessionStore.get(token);
        if (userId == null) throw new RuntimeException("Invalid session token");
        return authService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/me")
    public Account getMyAccount(@RequestHeader(name="X-Session-Token", required=false) String token) {
        User u = requireUser(token);
        return accountService.getByUser(u);
    }

    @PostMapping("/deposit")
    public String deposit(@RequestHeader(name="X-Session-Token", required=false) String token, @RequestBody TransactionRequest req) {
        User u = requireUser(token);
        Account acct = accountService.getByUser(u);
        accountService.deposit(acct, req.getAmount());
        txService.record(acct, req.getAmount(), "DEPOSIT", req.getDescription());
        return "Deposited " + req.getAmount();
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestHeader(name="X-Session-Token", required=false) String token, @RequestBody TransactionRequest req) {
        User u = requireUser(token);
        Account acct = accountService.getByUser(u);
        accountService.withdraw(acct, req.getAmount());
        txService.record(acct, req.getAmount(), "WITHDRAW", req.getDescription());
        return "Withdrawn " + req.getAmount();
    }
}
