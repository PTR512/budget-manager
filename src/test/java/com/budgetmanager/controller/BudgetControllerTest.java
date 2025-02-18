package com.budgetmanager.controller;

import com.budgetmanager.model.Budget;
import com.budgetmanager.model.Transaction;
import com.budgetmanager.model.TransactionType;
import com.budgetmanager.repository.BudgetRepository;
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
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BudgetRepository budgetRepository;

    private Budget budget;

    @BeforeEach
    void setUp() {
        budgetRepository.deleteAll();
        budget = new Budget(YearMonth.of(2024, 1), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000));
        budget = budgetRepository.save(budget);
    }

    @AfterEach
    void cleanup() {
        budgetRepository.deleteAll();
    }

    @Test
    void shouldAddBudget() throws Exception {
        // Given
        String budgetJson = """
                {
                    "month": "2024-02",
                    "limitAmount": 5000,
                    "currentExpenses": 2000
                }
                """;

        // When & Then
        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(budgetJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.limitAmount").value(5000));
    }

    @Test
    void shouldUpdateBudget() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(put("/api/budgets/" + budget.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("month", "2024-04")
                        .param("limitAmount", "10000")
                        .param("currentExpenses", "5000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limitAmount").value(10000))
                .andExpect(jsonPath("$.currentExpenses").value(5000))
                .andExpect(jsonPath("$.month").value("2024-04"));
    }

    @Test
    void shouldDeleteBudget() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/budgets/" + budget.getId()))
                .andExpect(status().isNoContent());
    }
}
