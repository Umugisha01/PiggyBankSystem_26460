package com.piggybank.repository;

import com.piggybank.model.Transaction;
import com.piggybank.model.Transaction.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserId(Long userId, Sort sort);

    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findBySavingGoalId(Long savingGoalId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findByUserIdAndDateRange(@Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type")
    BigDecimal getTotalAmountByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);

    boolean existsByUserIdAndAmountGreaterThan(Long userId, BigDecimal amount);

    long countByUserIdAndType(Long userId, TransactionType type);

    @Query("SELECT t FROM Transaction t WHERE t.user.location.name = :provinceName")
    List<Transaction> findByUserProvince(@Param("provinceName") String provinceName);
}