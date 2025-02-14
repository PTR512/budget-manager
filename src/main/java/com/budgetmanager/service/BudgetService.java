package com.budgetmanager.service;

import com.budgetmanager.model.Budget;
import com.budgetmanager.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Optional<Budget> getBudgetForMonth(YearMonth month) {
        return budgetRepository.findByMonth(month);
    }

    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    public Budget addBudget(Budget budget) {
        if (getBudgetForMonth(budget.getMonth()).isPresent()) {
            throw new IllegalArgumentException("Budget for month already exists");
        }
        return budgetRepository.save(budget);
    }

    @Transactional
    public Budget updateBudget(Long id, YearMonth month, BigDecimal limitAmount, BigDecimal currentExpenses) {
        Budget existingBudget = getBudgetById(id).orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if(limitAmount != null &&
                limitAmount.compareTo(BigDecimal.ZERO) >= 0 &&
                !limitAmount.equals(existingBudget.getLimitAmount())) {
            existingBudget.setLimitAmount(limitAmount);
        }
        if(currentExpenses != null &&
                currentExpenses.compareTo(BigDecimal.ZERO) >= 0 &&
                !currentExpenses.equals(existingBudget.getCurrentExpenses())) {
            existingBudget.setCurrentExpenses(currentExpenses);
        }
        if(month != null &&
                !month.equals(existingBudget.getMonth())) {
            existingBudget.setMonth(month);
        }
        return budgetRepository.save(existingBudget);
    }

    public boolean deleteBudget(Long id) {
        if (getBudgetById(id).isPresent()) {
            budgetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
