package com.example.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>🏦 Bank App is running successfully!</h1><p>Use endpoints like /auth/signup, /auth/login, /account/deposit, /account/withdraw</p>";
    }
}

