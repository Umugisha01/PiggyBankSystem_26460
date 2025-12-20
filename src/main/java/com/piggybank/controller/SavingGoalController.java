package com.piggybank.controller;

import com.piggybank.model.SavingGoal;
import com.piggybank.model.SavingGoal.GoalStatus;
import com.piggybank.service.SavingGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/saving-goals")
public class SavingGoalController {

    @Autowired
    private SavingGoalService savingGoalService;

    @GetMapping
    public ResponseEntity<?> getAllSavingGoals(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        
        if (search != null && !search.trim().isEmpty()) {
            // Return paginated search results
            Sort sortObj = direction.equalsIgnoreCase("desc") ? 
                Sort.by(sort).descending() : Sort.by(sort).ascending();
            Pageable pageable = PageRequest.of(page, size, sortObj);
            return ResponseEntity.ok(savingGoalService.searchSavingGoals(search, pageable));
        } else {
            // Return all goals (existing functionality)
            return ResponseEntity.ok(savingGoalService.getAllSavingGoals());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingGoal> getSavingGoalById(@PathVariable Long id) {
        Optional<SavingGoal> savingGoal = savingGoalService.getSavingGoalById(id);
        return savingGoal.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<SavingGoal> getSavingGoalsByUser(@PathVariable Long userId) {
        return savingGoalService.getSavingGoalsByUser(userId);
    }

    @GetMapping("/user/{userId}/sorted")
    public List<SavingGoal> getSavingGoalsByUserSorted(@PathVariable Long userId,
            @RequestParam String sortBy,
            @RequestParam String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return savingGoalService.getSavingGoalsByUser(userId, sort);
    }

    @GetMapping("/user/{userId}/paged")
    public Page<SavingGoal> getSavingGoalsByUserPaged(@PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("targetDate"));
        return savingGoalService.getSavingGoalsByUserPaged(userId, pageable);
    }

    @GetMapping("/status/{status}")
    public List<SavingGoal> getSavingGoalsByStatus(@PathVariable GoalStatus status) {
        return savingGoalService.getSavingGoalsByStatus(status);
    }

    @GetMapping("/user/{userId}/completed")
    public List<SavingGoal> getCompletedGoalsByUser(@PathVariable Long userId) {
        return savingGoalService.getCompletedGoalsByUser(userId);
    }

    @GetMapping("/user/{userId}/active")
    public List<SavingGoal> getActiveGoalsByUser(@PathVariable Long userId) {
        return savingGoalService.getActiveGoalsByUser(userId);
    }

    @PostMapping
    public SavingGoal createSavingGoal(@RequestBody SavingGoal savingGoal) {
        return savingGoalService.createSavingGoal(savingGoal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingGoal> updateSavingGoal(@PathVariable Long id,
            @RequestBody SavingGoal savingGoalDetails) {
        SavingGoal updatedSavingGoal = savingGoalService.updateSavingGoal(id, savingGoalDetails);
        return updatedSavingGoal != null ? ResponseEntity.ok(updatedSavingGoal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavingGoal(@PathVariable Long id) {
        boolean deleted = savingGoalService.deleteSavingGoal(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/exists/user/{userId}/name/{name}")
    public boolean savingGoalExistsForUser(@PathVariable Long userId, @PathVariable String name) {
        return savingGoalService.savingGoalExistsForUser(userId, name);
    }

    @GetMapping("/count/user/{userId}/status/{status}")
    public long countSavingGoalsByUserAndStatus(@PathVariable Long userId, @PathVariable GoalStatus status) {
        return savingGoalService.countSavingGoalsByUserAndStatus(userId, status);
    }

    @GetMapping("/total-savings/user/{userId}")
    public BigDecimal getTotalSavingsByUser(@PathVariable Long userId) {
        return savingGoalService.getTotalSavingsByUser(userId);
    }
}