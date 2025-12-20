package com.piggybank.service;

import com.piggybank.dto.GlobalSearchResponse;
import com.piggybank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlobalSearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SavingGoalRepository savingGoalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<GlobalSearchResponse> globalSearch(String query) {
        List<GlobalSearchResponse> results = new ArrayList<>();
        String searchTerm = "%" + query.toLowerCase() + "%";

        // Search Users
        userRepository.findAll().stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                               user.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                               user.getEmail().toLowerCase().contains(query.toLowerCase()))
                .limit(5)
                .forEach(user -> results.add(new GlobalSearchResponse(
                        "USER",
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getEmail()
                )));

        // Search Categories
        categoryRepository.findAll().stream()
                .filter(category -> category.getName().toLowerCase().contains(query.toLowerCase()))
                .limit(5)
                .forEach(category -> results.add(new GlobalSearchResponse(
                        "CATEGORY",
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                )));

        // Search Saving Goals
        savingGoalRepository.findAll().stream()
                .filter(goal -> goal.getName().toLowerCase().contains(query.toLowerCase()))
                .limit(5)
                .forEach(goal -> results.add(new GlobalSearchResponse(
                        "SAVING_GOAL",
                        goal.getId(),
                        goal.getName(),
                        "Target: $" + goal.getTargetAmount()
                )));

        // Search Locations
        locationRepository.findAll().stream()
                .filter(location -> location.getName().toLowerCase().contains(query.toLowerCase()))
                .limit(5)
                .forEach(location -> results.add(new GlobalSearchResponse(
                        "LOCATION",
                        location.getId(),
                        location.getName(),
                        location.getType().toString()
                )));

        return results;
    }
}