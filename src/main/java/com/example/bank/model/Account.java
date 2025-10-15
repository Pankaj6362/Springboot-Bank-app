package com.example.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private String id = UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {}

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
