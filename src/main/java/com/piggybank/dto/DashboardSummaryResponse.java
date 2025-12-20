package com.piggybank.dto;

import java.util.Map;

public class DashboardSummaryResponse {
    private long totalUsers;
    private long totalSavingGoals;
    private long totalTransactions;
    private double totalSavingsAmount;
    private Map<String, Long> categoryDistribution;
    private Map<String, Double> monthlyActivity;

    public DashboardSummaryResponse() {}

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalSavingGoals() { return totalSavingGoals; }
    public void setTotalSavingGoals(long totalSavingGoals) { this.totalSavingGoals = totalSavingGoals; }

    public long getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(long totalTransactions) { this.totalTransactions = totalTransactions; }

    public double getTotalSavingsAmount() { return totalSavingsAmount; }
    public void setTotalSavingsAmount(double totalSavingsAmount) { this.totalSavingsAmount = totalSavingsAmount; }

    public Map<String, Long> getCategoryDistribution() { return categoryDistribution; }
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; }

    public Map<String, Double> getMonthlyActivity() { return monthlyActivity; }
    public void setMonthlyActivity(Map<String, Double> monthlyActivity) { this.monthlyActivity = monthlyActivity; }
}