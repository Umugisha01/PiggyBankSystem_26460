package com.piggybank.service;

import com.piggybank.model.SavingGoal;
import com.piggybank.model.SavingGoal.GoalStatus;
import com.piggybank.repository.SavingGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
// import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SavingGoalService {

    @Autowired
    private SavingGoalRepository savingGoalRepository;

    public List<SavingGoal> getAllSavingGoals() {
        return savingGoalRepository.findAll();
    }

    public Optional<SavingGoal> getSavingGoalById(Long id) {
        return savingGoalRepository.findById(id);
    }

    public List<SavingGoal> getSavingGoalsByUser(Long userId) {
        return savingGoalRepository.findByUserId(userId);
    }

    public List<SavingGoal> getSavingGoalsByUser(Long userId, Sort sort) {
        return savingGoalRepository.findByUserId(userId, sort);
    }

    public Page<SavingGoal> getSavingGoalsByUserPaged(Long userId, Pageable pageable) {
        return savingGoalRepository.findByUserId(userId, pageable);
    }

    public List<SavingGoal> getSavingGoalsByStatus(GoalStatus status) {
        return savingGoalRepository.findByStatus(status);
    }

    public List<SavingGoal> getCompletedGoalsByUser(Long userId) {
        return savingGoalRepository.findCompletedGoalsByUser(userId);
    }

    public List<SavingGoal> getActiveGoalsByUser(Long userId) {
        return savingGoalRepository.findActiveGoalsByUser(userId);
    }

    public SavingGoal createSavingGoal(SavingGoal savingGoal) {
        return savingGoalRepository.save(savingGoal);
    }

    public SavingGoal updateSavingGoal(Long id, SavingGoal savingGoalDetails) {
        Optional<SavingGoal> optionalSavingGoal = savingGoalRepository.findById(id);
        if (optionalSavingGoal.isPresent()) {
            SavingGoal savingGoal = optionalSavingGoal.get();
            savingGoal.setName(savingGoalDetails.getName());
            savingGoal.setDescription(savingGoalDetails.getDescription());
            savingGoal.setTargetAmount(savingGoalDetails.getTargetAmount());
            savingGoal.setCurrentAmount(savingGoalDetails.getCurrentAmount());
            savingGoal.setTargetDate(savingGoalDetails.getTargetDate());
            savingGoal.setStatus(savingGoalDetails.getStatus());
            return savingGoalRepository.save(savingGoal);
        }
        return null;
    }

    public boolean deleteSavingGoal(Long id) {
        if (savingGoalRepository.existsById(id)) {
            savingGoalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean savingGoalExistsForUser(Long userId, String name) {
        return savingGoalRepository.existsByUserIdAndName(userId, name);
    }

    public long countSavingGoalsByUserAndStatus(Long userId, GoalStatus status) {
        return savingGoalRepository.countByUserIdAndStatus(userId, status);
    }

    public BigDecimal getTotalSavingsByUser(Long userId) {
        return savingGoalRepository.getTotalSavingsByUser(userId);
    }

    public Page<SavingGoal> searchSavingGoals(String searchTerm, Pageable pageable) {
        return savingGoalRepository.findAll().stream()
                .filter(goal -> goal.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                               (goal.getDescription() != null && goal.getDescription().toLowerCase().contains(searchTerm.toLowerCase())))
                .collect(java.util.stream.Collectors.toList())
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.toList(),
                    list -> new org.springframework.data.domain.PageImpl<>(list, pageable, 
                        savingGoalRepository.findAll().stream()
                            .filter(goal -> goal.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                           (goal.getDescription() != null && goal.getDescription().toLowerCase().contains(searchTerm.toLowerCase())))
                            .count()
                    )
                ));
    }
}