package com.example.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bank.model.TransactionRecord;
import com.example.bank.model.Account;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionRecord, String> {
    List<TransactionRecord> findByAccountOrderByCreatedAtDesc(Account account);
}
