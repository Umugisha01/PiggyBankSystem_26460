package com.piggybank.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0.01")
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private SavingGoal savingGoal;

    public LocalDateTime getTransactionDate() {
        return date;
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public Transaction(BigDecimal amount, TransactionType type, String description,
            User user, SavingGoal savingGoal) {
        this();
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.user = user;
        this.savingGoal = savingGoal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SavingGoal getSavingGoal() {
        return savingGoal;
    }

    public void setSavingGoal(SavingGoal savingGoal) {
        this.savingGoal = savingGoal;
    }
}