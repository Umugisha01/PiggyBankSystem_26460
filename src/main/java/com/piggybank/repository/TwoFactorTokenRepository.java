package com.piggybank.repository;

import com.piggybank.model.TwoFactorToken;
import com.piggybank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TwoFactorTokenRepository extends JpaRepository<TwoFactorToken, Long> {
    Optional<TwoFactorToken> findByTokenAndUserAndUsedFalse(String token, User user);
    
    @Modifying
    @Query("DELETE FROM TwoFactorToken tft WHERE tft.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);
    
    void deleteByUser(User user);
}