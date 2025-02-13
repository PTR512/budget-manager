package com.budgetmanager.controller;

import com.budgetmanager.model.Budget;
import com.budgetmanager.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Optional;

/**
 * REST API Controller for managing monthly budgets.
 */
@RestController
@RequestMapping(path = "/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    /**
     * Retrieves the budget for a given month and year.
     *
     * @param year  The year of the budget.
     * @param month The month of the budget.
     * @return The budget details for the specified period.
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<Budget> getBudget(@PathVariable int year, @PathVariable int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        Optional<Budget> budget = budgetService.getBudgetForMonth(yearMonth);
        return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds a new budget to the database.
     *
     * @param budget Budget to be added.
     * @return The created budget.
     */
    @PostMapping()
    public ResponseEntity<Budget> addBudget(@RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.addBudget(budget));
    }
}
