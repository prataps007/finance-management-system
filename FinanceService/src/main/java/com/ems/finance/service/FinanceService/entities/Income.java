package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "incomes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    private String id;

    private String userId; // Reference to the user

    private String source; // E.g., Salary, Freelance, Investment

    private double amount;

    private LocalDateTime date;
}
