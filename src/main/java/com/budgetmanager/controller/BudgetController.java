package com.budgetmanager.controller;

import com.budgetmanager.model.Budget;
import com.budgetmanager.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.addBudget(budget));
    }

    /**
     * Updates a budget in the database.
     *
     * @param id The ID of the budget to be updated.
     * @param month The month of the budget to be updated.
     * @param limitAmount The limit amount of the budget to be updated.
     * @param currentExpenses The current expenses of the budget to be updated.
     * @return The updated budget.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id,
                                               @RequestParam(required = false) YearMonth month,
                                               @RequestParam(required = false) BigDecimal limitAmount,
                                               @RequestParam(required = false) BigDecimal currentExpenses) {
        return ResponseEntity.ok(budgetService.updateBudget(id, month, limitAmount, currentExpenses));
    }

    /**
     * Deletes a budget from the database.
     *
     * @param id The ID of the budget to be deleted.
     * @return No content if the budget was deleted successfully, or a 404 if the budget was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable Long id) {
        if (budgetService.deleteBudget(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
