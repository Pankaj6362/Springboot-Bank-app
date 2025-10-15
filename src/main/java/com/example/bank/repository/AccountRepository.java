package com.example.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bank.model.Account;
import com.example.bank.model.User;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByOwner(User owner);
}
