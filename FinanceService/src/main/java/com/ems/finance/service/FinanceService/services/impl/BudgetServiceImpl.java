package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.entities.Budget;
import com.ems.finance.service.FinanceService.exceptions.ResourceNotFoundException;
import com.ems.finance.service.FinanceService.repositories.BudgetRepo;
import com.ems.finance.service.FinanceService.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepo budgetRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Budget addOrUpdateBudget(Budget budget) {
        Budget existingBudget = budgetRepository.findByUserIdAndCategory(budget.getUserId(), budget.getCategory());
        if (existingBudget != null) {  // update budget
            existingBudget.setAmount(budget.getAmount());
            return budgetRepository.save(existingBudget);
        }

        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getBudgetsByUserId(String userId) {
        return budgetRepository.findByUserId(userId);
    }


    @Override
    public Budget getBudgetById(String id) {
        return budgetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Budget with given id not found " + id));
    }

    @Override
    public Budget getBudgetByCategory(String category) {
        return budgetRepository.findByCategory(category);
    }

    @Override
    public void updateSpentAmount(String userId, String category, double amount) {
        Budget budget = budgetRepository.findByUserIdAndCategory(userId, category);

        if (budget != null) {  // update spent amount
            budget.setSpentAmount(budget.getSpentAmount() + amount);
            budgetRepository.save(budget);

            // Check if the budget limit has been exceeded
            if (budget.getSpentAmount() > budget.getAmount()) {
                String message = userId + ":You have exceeded your budget for " + category + "\nDetails" + "\nBudget Amount = " + budget.getAmount() + " and Spent Amount = " + budget.getSpentAmount();
//                String message = String.format(
//                        "%s: You have exceeded your budget for %s\nDetails\nBudget Amount = %.2f and Spent Amount = %.2f",
//                        userId, budget.getCategory(), budget.getAmount(), budget.getSpentAmount()
//                );
                kafkaTemplate.send("budget_notifications", message);
            }
        }

    }

    @Override
    public void deleteBudget(String id) {
        budgetRepository.deleteById(id);
    }
}
