package com.piggybank.service;

import com.piggybank.model.Transaction;
import com.piggybank.model.Transaction.TransactionType;
import com.piggybank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionsByUser(Long userId, Sort sort) {
        return transactionRepository.findByUserId(userId, sort);
    }

    public Page<Transaction> getTransactionsByUserPaged(Long userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable);
    }

    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getTransactionsBySavingGoal(Long savingGoalId) {
        return transactionRepository.findBySavingGoalId(savingGoalId);
    }

    public List<Transaction> getTransactionsByUserAndDateRange(Long userId, LocalDateTime startDate,
            LocalDateTime endDate) {
        return transactionRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    public List<Transaction> getTransactionsByUserProvince(String provinceName) {
        return transactionRepository.findByUserProvince(provinceName);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setAmount(transactionDetails.getAmount());
            transaction.setType(transactionDetails.getType());
            transaction.setDescription(transactionDetails.getDescription());
            transaction.setDate(transactionDetails.getDate());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsTransactionWithAmountGreaterThan(Long userId, BigDecimal amount) {
        return transactionRepository.existsByUserIdAndAmountGreaterThan(userId, amount);
    }

    public long countTransactionsByUserAndType(Long userId, TransactionType type) {
        return transactionRepository.countByUserIdAndType(userId, type);
    }

    public BigDecimal getTotalAmountByUserAndType(Long userId, TransactionType type) {
        return transactionRepository.getTotalAmountByUserAndType(userId, type);
    }
}