package com.example.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bank.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
