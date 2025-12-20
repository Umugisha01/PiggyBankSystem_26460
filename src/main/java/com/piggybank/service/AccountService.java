package com.piggybank.service;

import com.piggybank.model.SavingsAccount;
import com.piggybank.repository.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Transactional
    public SavingsAccount deposit(Long accountId, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        SavingsAccount account = savingsAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        return savingsAccountRepository.save(account);
    }

    @Transactional
    public SavingsAccount withdraw(Long accountId, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        SavingsAccount account = savingsAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        return savingsAccountRepository.save(account);
    }

    public Double getBalanceByUser(Long userId) {
        return savingsAccountRepository.findByUserId(userId)
                .map(SavingsAccount::getBalance)
                .orElse(0.0);
    }

    public SavingsAccount getAccountByUser(Long userId) {
        return savingsAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user"));
    }

    public List<SavingsAccount> getRichestUsers() {
        return savingsAccountRepository.findRichestUsers();
    }

    public Double getAverageBalance() {
        return savingsAccountRepository.getAverageBalance();
    }

    public Double getTotalBalance() {
        return savingsAccountRepository.getTotalBalance();
    }
}