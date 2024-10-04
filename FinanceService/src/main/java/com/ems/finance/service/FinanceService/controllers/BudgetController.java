package com.ems.finance.service.FinanceService.controllers;

import com.ems.finance.service.FinanceService.entities.Budget;
import com.ems.finance.service.FinanceService.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/add")
    public ResponseEntity<Budget> addOrUpdateBudget(@RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.addOrUpdateBudget(budget));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getUserBudgets(@PathVariable String userId) {
        return ResponseEntity.ok(budgetService.getBudgetsByUserId(userId));
    }

    @PutMapping("/updateSpent")
    public void updateSpentAmount(@RequestParam String userId, @RequestParam String category, @RequestParam double amount) {
        budgetService.updateSpentAmount(userId, category, amount);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBudget(@PathVariable String id) {
        budgetService.deleteBudget(id);
    }
}
