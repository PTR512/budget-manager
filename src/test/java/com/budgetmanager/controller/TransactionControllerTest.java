package com.budgetmanager.controller;

import com.budgetmanager.model.Budget;
import com.budgetmanager.model.Transaction;
import com.budgetmanager.model.TransactionType;
import com.budgetmanager.repository.BudgetRepository;
import com.budgetmanager.repository.TransactionRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    private Budget budget;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        budgetRepository.deleteAll();

        budget = new Budget(null, YearMonth.now(), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000));
        budget = budgetRepository.save(budget);

        transaction = new Transaction(null, BigDecimal.valueOf(100), "Food", TransactionType.EXPENSE, LocalDate.now(), budget);
        transaction = transactionRepository.save(transaction);
    }

    @AfterEach
    void cleanup() {
        transactionRepository.deleteAll();
        budgetRepository.deleteAll();
    }

    @Test
    void shouldAddTransaction() throws Exception {
        // Given
        String transactionJson = """
                {
                    "amount": 100.0,
                    "category": "Food",
                    "type": "EXPENSE",
                    "date": "2024-02-12"
                }
                """;

        // When & Then
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("budgetId", String.valueOf(budget.getId()))
                        .content(transactionJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category").value("Food"))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.type").value("EXPENSE"))
                .andExpect(jsonPath("$.date").value("2024-02-12"));
    }

    @Test
    void shouldReturnAllTransactions() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldUpdateTransaction() throws Exception {
        mockMvc.perform(put("/api/transactions/" + transaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("amount", String.valueOf(200))
                .param("category", "Food")
                .param("type", "EXPENSE")
                .param("date", "2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(200))
                .andExpect(jsonPath("$.category").value("Food"))
                .andExpect(jsonPath("$.type").value("EXPENSE"))
                .andExpect(jsonPath("$.date").value("2020-01-01"));
    }

    @Test
    void shouldDeleteTransaction() throws Exception {
        mockMvc.perform(delete("/api/transactions/" + transaction.getId()))
                .andExpect(status().isNoContent());
    }
}
