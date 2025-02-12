package com.budgetmanager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal amount;
    private String category;
    private String type;
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(Long id, BigDecimal amount, String category, String type, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    public Transaction(BigDecimal amount, String category, String type, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
