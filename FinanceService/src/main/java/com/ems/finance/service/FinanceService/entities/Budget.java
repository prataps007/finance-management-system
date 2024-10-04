package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    private String id;

    private String userId; // Reference to the user

    private String category; // E.g., Food, Travel, Entertainment

    private double amount;   // The budget limit for the category

    // weekly budget / monthly budget

    private double spentAmount; // The current spent amount for this category
}
