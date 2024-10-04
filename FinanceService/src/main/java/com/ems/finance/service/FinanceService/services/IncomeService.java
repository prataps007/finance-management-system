package com.ems.finance.service.FinanceService.services;

import com.ems.finance.service.FinanceService.entities.Income;

import java.util.List;


public interface IncomeService {

    Income addIncome(Income income);

    List<Income> getIncomesByUserId(String userId);

    Income getIncomeById(String id);

    void deleteIncome(String id);

    Income updateIncome(Income income);
}
