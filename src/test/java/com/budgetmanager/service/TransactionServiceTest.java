package com.budgetmanager.service;

import com.budgetmanager.model.Transaction;
import com.budgetmanager.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    private AutoCloseable closeable;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    void getAllTransactions_ShouldReturnTransactionsList() {
        // Given
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "Food", "EXPENSE", LocalDate.now());
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
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), "Salary", "INCOME", LocalDate.now());
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // When
        Transaction savedTransaction = transactionService.addTransaction(transaction);

        // Then
        assertEquals(BigDecimal.valueOf(200), savedTransaction.getAmount());
        assertEquals("Salary", savedTransaction.getCategory());
        verify(transactionRepository, times(1)).save(transaction);
    }
}
