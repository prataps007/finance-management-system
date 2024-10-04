package com.ems.finance.service.FinanceService.services;

import com.ems.finance.service.FinanceService.entities.Budget;

import java.util.List;

public interface BudgetService {

    Budget addOrUpdateBudget(Budget budget);

    List<Budget> getBudgetsByUserId(String userId);

    Budget getBudgetById(String id);

    Budget getBudgetByCategory(String category);

    void updateSpentAmount(String userId, String category, double amount);

    void deleteBudget(String id);
}
