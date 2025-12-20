package com.piggybank.service;

import com.piggybank.dto.DashboardSummaryResponse;
import com.piggybank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavingGoalRepository savingGoalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    public DashboardSummaryResponse getDashboardSummary() {
        DashboardSummaryResponse response = new DashboardSummaryResponse();

        // Basic counts
        response.setTotalUsers(userRepository.count());
        response.setTotalSavingGoals(savingGoalRepository.count());
        response.setTotalTransactions(transactionRepository.count());

        // Total savings amount
        double totalSavings = savingsAccountRepository.findAll().stream()
                .mapToDouble(account -> account.getBalance())
                .sum();
        response.setTotalSavingsAmount(totalSavings);

        // Category distribution
        Map<String, Long> categoryDistribution = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(
                        category -> category.getName(),
                        category -> (long) category.getUserCategories().size()
                ));
        response.setCategoryDistribution(categoryDistribution);

        // Monthly activity (simplified - last 6 months)
        Map<String, Double> monthlyActivity = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        
        for (int i = 5; i >= 0; i--) {
            LocalDateTime monthStart = now.minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime monthEnd = monthStart.plusMonths(1).minusSeconds(1);
            
            double monthlyAmount = transactionRepository.findAll().stream()
                    .filter(transaction -> transaction.getTransactionDate().isAfter(monthStart) && 
                                         transaction.getTransactionDate().isBefore(monthEnd))
                    .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                    .sum();
            
            monthlyActivity.put(monthStart.format(formatter), monthlyAmount);
        }
        response.setMonthlyActivity(monthlyActivity);

        return response;
    }
}