package com.budgetmanager.controller;

import com.budgetmanager.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        mockMvc.perform(put("/api/budgets/1")
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
        mockMvc.perform(delete("/api/budgets/1"))
                .andExpect(status().isNoContent());
    }
}
