package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.entities.Expense;
import com.ems.finance.service.FinanceService.entities.Income;
import com.ems.finance.service.FinanceService.repositories.ExpenseRepo;
import com.ems.finance.service.FinanceService.repositories.IncomeRepo;
import com.ems.finance.service.FinanceService.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private IncomeRepo incomeRepository;

    // Calculate total expenses
    public Double calculateTotalExpenses(String userId, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // Calculate total income
    public Double calculateTotalIncome(String userId, LocalDate startDate, LocalDate endDate) {
        List<Income> incomes = incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }

    // Analyze spending trends and frequent expense categories
    public Map<String, Object> generateSpendingTrends(String userId, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.merge(expense.getCategory(), expense.getAmount(), Double::sum);
        }

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalExpenses", calculateTotalExpenses(userId, startDate, endDate));
        analytics.put("categoryTotals", categoryTotals);

        return analytics;
    }

    // Additional methods for AI-based suggestions could be added here
}
