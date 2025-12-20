package com.piggybank.controller;

import com.piggybank.model.SavingsAccount;
import com.piggybank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<SavingsAccount> deposit(@PathVariable Long accountId, @RequestParam Double amount) {
        try {
            SavingsAccount account = accountService.deposit(accountId, amount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<SavingsAccount> withdraw(@PathVariable Long accountId, @RequestParam Double amount) {
        try {
            SavingsAccount account = accountService.withdraw(accountId, amount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<Double> getBalanceByUser(@PathVariable Long userId) {
        try {
            Double balance = accountService.getBalanceByUser(userId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SavingsAccount> getAccountByUser(@PathVariable Long userId) {
        try {
            SavingsAccount account = accountService.getAccountByUser(userId);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/richest")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SavingsAccount> getRichestUsers() {
        return accountService.getRichestUsers();
    }

    @GetMapping("/analytics/average-balance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getAverageBalance() {
        Double average = accountService.getAverageBalance();
        return ResponseEntity.ok(average != null ? average : 0.0);
    }

    @GetMapping("/analytics/total-balance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTotalBalance() {
        Double total = accountService.getTotalBalance();
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
}