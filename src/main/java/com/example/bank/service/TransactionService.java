package com.example.bank.service;

import org.springframework.stereotype.Service;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.model.TransactionRecord;
import com.example.bank.model.Account;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository txRepo;

    public TransactionService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    public TransactionRecord record(Account account, BigDecimal amount, String type, String desc) {
        TransactionRecord r = new TransactionRecord();
        r.setAccount(account);
        r.setAmount(amount);
        r.setType(type);
        r.setDescription(desc);
        return txRepo.save(r);
    }

    public List<TransactionRecord> history(Account account) {
        return txRepo.findByAccountOrderByCreatedAtDesc(account);
    }
}
