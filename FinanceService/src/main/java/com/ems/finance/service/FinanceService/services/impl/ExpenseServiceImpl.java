package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.FinanceServiceApplication;
import com.ems.finance.service.FinanceService.entities.Expense;
import com.ems.finance.service.FinanceService.exceptions.ResourceNotFoundException;
import com.ems.finance.service.FinanceService.external.UserClient;
import com.ems.finance.service.FinanceService.external.sevices.UserServiceClient;
import com.ems.finance.service.FinanceService.repositories.ExpenseRepo;
import com.ems.finance.service.FinanceService.services.BudgetService;
import com.ems.finance.service.FinanceService.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private UserClient userClient;

    private Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);



    public Expense addExpense(Expense expense) {
        // Verify if the user is valid
        if (userServiceClient.isUserValid(expense.getUserId())) {
            logger.info("{} ",expense.getUserId());

            // Proceed to add expense for the user
            expense.setDate(LocalDateTime.now());
            Expense savedExpense = expenseRepository.save(expense);

            // Update the budget spent amount
            budgetService.updateSpentAmount(expense.getUserId(), expense.getCategory(), expense.getAmount());

            String message = expense.getUserId() + ": A new transaction recorded" + "\nDetails- " + "\nCategory- " + expense.getCategory()+ "\nTransaction Amount = " + expense.getAmount();
//                String message = String.format(
//                        "%s: You have exceeded your budget for %s\nDetails\nBudget Amount = %.2f and Spent Amount = %.2f",
//                        userId, budget.getCategory(), budget.getAmount(), budget.getSpentAmount()
//                );
            kafkaTemplate.send("expense_notifications", message);

            return savedExpense;
        }
        else {
            throw new ResourceNotFoundException("User with given id not found: " + expense.getUserId());
        }

    }

    public List<Expense> getExpensesByUserId(String userId) {
        // Verify if the user is valid
        if (userServiceClient.isUserValid(userId)) {
            // Proceed to add expense for the user
            return expenseRepository.findByUserId(userId);
        } else {
            throw new ResourceNotFoundException("User with given id not found: " + userId);
        }
    }

    public Expense getExpenseById(String id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense with given id not found: " + id));
        return expense;
    }


    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }


    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
