package com.budgetmanager.service;

import com.budgetmanager.model.Budget;
import com.budgetmanager.repository.BudgetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;
    AutoCloseable closeable;

    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    void getBudgetForMonth_ShouldReturnBudgetIfExists() {
        // Given
        YearMonth month = YearMonth.of(2024, 2);
        Budget budget = new Budget(1L, month, BigDecimal.valueOf(1000), BigDecimal.valueOf(500));
        when(budgetRepository.findByMonth(month)).thenReturn(Optional.of(budget));

        // When
        Optional<Budget> result = budgetService.getBudgetForMonth(month);

        // Then
        assertTrue(result.isPresent());
        verify(budgetRepository, times(1)).findByMonth(month);
    }
}
