package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String userId;

    private String name;

    private String email;

    private String password;

    private String role; // ROLE_USER or ROLE_ADMIN
}
