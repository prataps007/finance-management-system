package com.ems.user.service.UserService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    private String id;

    private String userId; // Reference to the user

    private String category; // E.g., Food, Travel, Entertainment

    private double amount;   // The budget limit for the category

    // weekly budget / monthly budget

    private double spentAmount; // The current spent amount for this category
}

