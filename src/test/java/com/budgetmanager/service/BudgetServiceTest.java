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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void addBudget_ShouldSaveAndReturnBudget() {
        // Given
        Budget budget = new Budget(null, YearMonth.of(2024, 2), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000));
        when(budgetRepository.save(budget)).thenReturn(budget);

        // When
        Budget savedBudget = budgetService.addBudget(budget);

        // Then
        assertEquals(BigDecimal.valueOf(5000), savedBudget.getLimitAmount());
        verify(budgetRepository, times(1)).save(budget);
    }

    @Test
    void updateBudget() {
        // Given
        Long budgetId = 1L;
        Budget budget = new Budget(budgetId, YearMonth.of(2024, 2), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000));
        YearMonth month = YearMonth.of(2024, 3);
        BigDecimal limitAmount = BigDecimal.valueOf(10000);
        BigDecimal currentExpenses = BigDecimal.valueOf(5000);

        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(budget));
        when(budgetRepository.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Budget updatedBudget = budgetService.updateBudget(1L, month, limitAmount, currentExpenses);

        // Then
        assertEquals(limitAmount, updatedBudget.getLimitAmount());
        assertEquals(currentExpenses, updatedBudget.getCurrentExpenses());
        assertEquals(month, updatedBudget.getMonth());
        verify(budgetRepository, times(1)).save(budget);
    }

    @Test
    void deleteBudget_ShouldReturnTrueIfBudgetExists() {
        // Given
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(new Budget(1L, YearMonth.of(2024, 2), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000))));

        // When
        boolean result = budgetService.deleteBudget(1L);

        // Then
        assertTrue(result);
        verify(budgetRepository, times(1)).findById(1L);
        verify(budgetRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBudget_ShouldReturnFalseIfBudgetDoesNotExist() {
        // Given
        when(budgetRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        boolean result = budgetService.deleteBudget(1L);

        // Then
        assertFalse(result);
        verify(budgetRepository, times(1)).findById(1L);
        verify(budgetRepository, times(0)).deleteById(1L);
    }
}
