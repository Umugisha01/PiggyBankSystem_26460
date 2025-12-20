package com.piggybank.repository;

import com.piggybank.model.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
    
    Optional<SavingsAccount> findByUserId(Long userId);
    
    @Query("SELECT sa FROM SavingsAccount sa ORDER BY sa.balance DESC")
    List<SavingsAccount> findRichestUsers();
    
    @Query("SELECT AVG(sa.balance) FROM SavingsAccount sa")
    Double getAverageBalance();
    
    @Query("SELECT SUM(sa.balance) FROM SavingsAccount sa")
    Double getTotalBalance();
}