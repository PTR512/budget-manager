package com.budgetmanager.service;

import com.budgetmanager.model.Budget;
import com.budgetmanager.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
}
