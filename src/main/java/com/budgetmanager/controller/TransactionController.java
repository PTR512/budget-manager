package com.budgetmanager.controller;

import com.budgetmanager.model.Transaction;
import com.budgetmanager.model.TransactionType;
import com.budgetmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * REST API Controller for managing financial transactions.
 */
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieves all transactions.
     *
     * @return List of all transactions.
     */
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    /**
     * Retrieves transactions for a given budget.
     *
     * @param budgetId The ID of the budget to retrieve transactions for.
     * @return List of transactions for the specified budget.
     */
    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<List<Transaction>> getTransactionsByBudgetId(@PathVariable Long budgetId) {
        return ResponseEntity.ok(transactionService.getTransactionsByBudgetId(budgetId));
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return The transaction with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * Adds a new transaction to the database.
     *
     * @param transaction Transaction to be added.
     * @return The created transaction.
     */
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.status(201).body(transactionService.addTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
                                                         @RequestParam(required = false) BigDecimal amount,
                                                         @RequestParam(required = false) String category,
                                                         @RequestParam(required = false) TransactionType type,
                                                         @RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, amount, category, type, date));
    }

    /**
     * Deletes a transaction from the database.
     *
     * @param id The ID of the transaction to be deleted.
     * @return No content if the transaction was deleted successfully, or a 404 if the transaction was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        if (transactionService.deleteTransaction(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
