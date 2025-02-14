package com.budgetmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Month is required")
    private YearMonth month;

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.0", message = "Limit amount must be greater than or equal to 0")
    private BigDecimal limitAmount;

    @NotNull(message = "Current expenses is required")
    @DecimalMin(value = "0.0", message = "Current expenses must be greater than or equal to 0")
    private BigDecimal currentExpenses;

    public Budget() {
    }

    public Budget(Long id, YearMonth month, BigDecimal limitAmount, BigDecimal currentExpenses) {
        this.id = id;
        this.month = month;
        this.limitAmount = limitAmount;
        this.currentExpenses = currentExpenses;
    }

    public Budget(YearMonth month, BigDecimal limitAmount, BigDecimal currentExpenses) {
        this.month = month;
        this.limitAmount = limitAmount;
        this.currentExpenses = currentExpenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getCurrentExpenses() {
        return currentExpenses;
    }

    public void setCurrentExpenses(BigDecimal currentExpenses) {
        this.currentExpenses = currentExpenses;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", month=" + month +
                ", limitAmount=" + limitAmount +
                ", currentExpenses=" + currentExpenses +
                '}';
    }
}
