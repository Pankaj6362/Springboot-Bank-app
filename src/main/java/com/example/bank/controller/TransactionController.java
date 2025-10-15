package com.example.bank.controller;

import org.springframework.web.bind.annotation.*;
import com.example.bank.service.TransactionService;
import com.example.bank.service.AccountService;
import com.example.bank.service.AuthService;
import com.example.bank.model.User;
import com.example.bank.model.Account;
import com.example.bank.model.TransactionRecord;
import com.example.bank.util.SimpleSessionStore;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService txService;
    private final AccountService accountService;
    private final AuthService authService;

    public TransactionController(TransactionService txService, AccountService accountService, AuthService authService) {
        this.txService = txService; this.accountService = accountService; this.authService = authService;
    }

    private User requireUser(String token) {
        if (token == null) throw new RuntimeException("Missing session token (X-Session-Token header)");
        String userId = SimpleSessionStore.get(token);
        if (userId == null) throw new RuntimeException("Invalid session token");
        return authService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/me")
    public List<TransactionRecord> myTransactions(@RequestHeader(name="X-Session-Token", required=false) String token) {
        User u = requireUser(token);
        Account acct = accountService.getByUser(u);
        return txService.history(acct);
    }
}
