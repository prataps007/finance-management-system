package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    private String id;

    private String userId; // Reference to the user

    private String category; // E.g., Food, Travel, Shopping

    private double amount;

    private String description;

    private LocalDateTime date;

    private boolean isRecurring;
}
