package com.piggybank.repository;

import com.piggybank.model.SavingGoal;
import com.piggybank.model.SavingGoal.GoalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {

    List<SavingGoal> findByUserId(Long userId);

    List<SavingGoal> findByUserId(Long userId, Sort sort);

    Page<SavingGoal> findByUserId(Long userId, Pageable pageable);

    List<SavingGoal> findByStatus(GoalStatus status);

    List<SavingGoal> findByTargetDateBeforeAndStatus(LocalDate date, GoalStatus status);

    @Query("SELECT sg FROM SavingGoal sg WHERE sg.user.id = :userId AND sg.currentAmount >= sg.targetAmount")
    List<SavingGoal> findCompletedGoalsByUser(@Param("userId") Long userId);

    @Query("SELECT sg FROM SavingGoal sg WHERE sg.user.id = :userId AND sg.currentAmount < sg.targetAmount")
    List<SavingGoal> findActiveGoalsByUser(@Param("userId") Long userId);

    boolean existsByUserIdAndName(Long userId, String name);

    long countByUserIdAndStatus(Long userId, GoalStatus status);

    @Query("SELECT SUM(sg.currentAmount) FROM SavingGoal sg WHERE sg.user.id = :userId")
    BigDecimal getTotalSavingsByUser(@Param("userId") Long userId);
}