package com.ems.user.service.UserService.external.service;

import com.ems.user.service.UserService.entities.Budget;
import jdk.jfr.StackTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinanceServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public Budget[] getUserBudget(String userId) {
        String budgetServiceUrl = "http://FinanceService/budgets/user/" + userId;

        try {
            Budget[] budgets = restTemplate.getForObject(budgetServiceUrl, Budget[].class);

            return budgets;  // Return the array of budgets
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return new Budget[0];  // Return an empty array if there's an exception
        }
    }
}
