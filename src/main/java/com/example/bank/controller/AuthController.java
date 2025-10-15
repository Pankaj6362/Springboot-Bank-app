package com.example.bank.controller;

import org.springframework.web.bind.annotation.*;
import com.example.bank.service.AuthService;
import com.example.bank.dto.*;
import com.example.bank.util.SimpleSessionStore;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest req) {
        var u = authService.signup(req.getEmail(), req.getPassword(), req.getFullName());
        String token = UUID.randomUUID().toString();
        SimpleSessionStore.put(token, u.getId());
        return new AuthResponse(token, u.getEmail(), u.getFullName());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        var u = authService.authenticate(req.getEmail(), req.getPassword());
        String token = UUID.randomUUID().toString();
        SimpleSessionStore.put(token, u.getId());
        return new AuthResponse(token, u.getEmail(), u.getFullName());
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader(name="X-Session-Token", required=false) String token) {
        if (token != null) SimpleSessionStore.remove(token);
        return "ok";
    }
}
