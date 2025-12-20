package com.piggybank.controller;

import com.piggybank.model.Transaction;
import com.piggybank.model.Transaction.TransactionType;
import com.piggybank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable Long userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    @GetMapping("/user/{userId}/sorted")
    public List<Transaction> getTransactionsByUserSorted(@PathVariable Long userId,
            @RequestParam String sortBy,
            @RequestParam String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return transactionService.getTransactionsByUser(userId, sort);
    }

    @GetMapping("/user/{userId}/paged")
    public Page<Transaction> getTransactionsByUserPaged(@PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return transactionService.getTransactionsByUserPaged(userId, pageable);
    }

    @GetMapping("/type/{type}")
    public List<Transaction> getTransactionsByType(@PathVariable TransactionType type) {
        return transactionService.getTransactionsByType(type);
    }

    @GetMapping("/saving-goal/{savingGoalId}")
    public List<Transaction> getTransactionsBySavingGoal(@PathVariable Long savingGoalId) {
        return transactionService.getTransactionsBySavingGoal(savingGoalId);
    }

    @GetMapping("/user/{userId}/date-range")
    public List<Transaction> getTransactionsByUserAndDateRange(@PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return transactionService.getTransactionsByUserAndDateRange(userId, start, end);
    }

    @GetMapping("/province/{provinceName}")
    public List<Transaction> getTransactionsByUserProvince(@PathVariable String provinceName) {
        return transactionService.getTransactionsByUserProvince(provinceName);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
            @RequestBody Transaction transactionDetails) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/exists/user/{userId}/amount-greater-than/{amount}")
    public boolean existsTransactionWithAmountGreaterThan(@PathVariable Long userId, @PathVariable BigDecimal amount) {
        return transactionService.existsTransactionWithAmountGreaterThan(userId, amount);
    }

    @GetMapping("/count/user/{userId}/type/{type}")
    public long countTransactionsByUserAndType(@PathVariable Long userId, @PathVariable TransactionType type) {
        return transactionService.countTransactionsByUserAndType(userId, type);
    }

    @GetMapping("/total-amount/user/{userId}/type/{type}")
    public BigDecimal getTotalAmountByUserAndType(@PathVariable Long userId, @PathVariable TransactionType type) {
        return transactionService.getTotalAmountByUserAndType(userId, type);
    }
}