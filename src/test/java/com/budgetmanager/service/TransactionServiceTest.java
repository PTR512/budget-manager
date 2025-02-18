package com.budgetmanager.service;

import com.budgetmanager.model.Budget;
import com.budgetmanager.model.Transaction;
import com.budgetmanager.model.TransactionType;
import com.budgetmanager.repository.BudgetRepository;
import com.budgetmanager.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    private AutoCloseable closeable;

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private TransactionService transactionService;

    Budget budget;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        budget = new Budget(1L, YearMonth.now(), BigDecimal.valueOf(5000), BigDecimal.valueOf(2000));
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    void getAllTransactions_ShouldReturnTransactionsList() {
        // Given
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "Food", TransactionType.EXPENSE, LocalDate.now(), budget);
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        // When
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Then
        assertEquals(1, transactions.size());
        assertEquals("Food", transactions.getFirst().getCategory());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void addTransaction_ShouldSaveTransaction() {
        // Given
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), "Salary", TransactionType.INCOME, LocalDate.now(), budget);
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // When
        Transaction savedTransaction = transactionService.addTransaction(transaction, budget.getId());

        // Then
        assertEquals(BigDecimal.valueOf(200), savedTransaction.getAmount());
        assertEquals("Salary", savedTransaction.getCategory());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void shouldReturnTransactionsByBudget() {
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "Food", TransactionType.EXPENSE, LocalDate.now(), budget);
        when(budgetRepository.existsById(1L)).thenReturn(true);
        when(transactionRepository.findByBudgetId(1L)).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.getTransactionsByBudgetId(1L);

        assertEquals(1, transactions.size());
        verify(transactionRepository, times(1)).findByBudgetId(1L);
    }

    @Test
    void shouldUpdateTransaction() {
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "Food", TransactionType.EXPENSE, LocalDate.now(), budget);

        String cat = "Shopping";
        BigDecimal amount = BigDecimal.valueOf(200);
        TransactionType type = TransactionType.INCOME;
        LocalDate date = LocalDate.now();

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.updateTransaction(1L, amount, cat, type, date);

        assertEquals(cat, result.getCategory());
        assertEquals(amount, result.getAmount());
        assertEquals(type, result.getType());
        assertEquals(date, result.getDate());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void shouldDeleteTransaction() {
        when(transactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(1L);

        assertDoesNotThrow(() -> transactionService.deleteTransaction(1L));
        verify(transactionRepository, times(1)).deleteById(1L);
    }
}
