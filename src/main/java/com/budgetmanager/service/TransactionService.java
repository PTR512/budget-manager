package com.budgetmanager.service;

import com.budgetmanager.model.Budget;
import com.budgetmanager.model.Transaction;
import com.budgetmanager.model.TransactionType;
import com.budgetmanager.repository.BudgetRepository;
import com.budgetmanager.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    public List<Transaction> getTransactionsByBudgetId(Long budgetId) {
        if(!budgetRepository.existsById(budgetId)) {
            throw new IllegalArgumentException("Budget not found");
        }
        return transactionRepository.findByBudgetId(budgetId);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction addTransaction(Transaction transaction) {
        Optional<Budget> budget = Optional.ofNullable(budgetRepository.findById(transaction.getBudget().getId())
                .orElseThrow(() -> new IllegalArgumentException("Budget not found")));

        transaction.setBudget(budget.get());
        return transactionRepository.save(transaction);
    }

    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Transaction updateTransaction(Long id, BigDecimal amount, String category, TransactionType type, LocalDate date) {
        Transaction transaction = getTransactionById(id);
        if(amount != null &&
                amount.compareTo(BigDecimal.ZERO) >= 0 &&
                !amount.equals(transaction.getAmount())) {
            transaction.setAmount(amount);
        }
        if(category != null &&
                !category.equals(transaction.getCategory())) {
            transaction.setCategory(category);
        }
        if(type != null &&
                !type.equals(transaction.getType())) {
            transaction.setType(type);
        }
        if(date != null &&
                !date.equals(transaction.getDate())) {
            transaction.setDate(date);
        }
        return transactionRepository.save(transaction);
    }
}
