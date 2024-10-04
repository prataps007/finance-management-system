package com.ems.finance.service.FinanceService.services;

import com.ems.finance.service.FinanceService.entities.Expense;

import java.util.List;


public interface ExpenseService {

    Expense addExpense(Expense expense);

    List<Expense> getExpensesByUserId(String userId);

    Expense getExpenseById(String id);

    void deleteExpense(String id);

    Expense updateExpense(Expense expense);
}
