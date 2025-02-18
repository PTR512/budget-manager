package com.budgetmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than or equal to 0.01")
    private BigDecimal amount;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type is required")
    private TransactionType type;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Transaction() {
    }

    public Transaction(Long id, BigDecimal amount, String category, TransactionType type, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    public Transaction(BigDecimal amount, String category, TransactionType type, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    public Transaction(Long id, BigDecimal amount, String category, TransactionType type, LocalDate date, Budget budget) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.budget = budget;
    }

    public Transaction(BigDecimal amount, String category, TransactionType type, LocalDate date, Budget budget) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.budget = budget;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", budget=" + budget +
                '}';
    }
}
