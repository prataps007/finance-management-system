package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.entities.Income;
import com.ems.finance.service.FinanceService.exceptions.ResourceNotFoundException;
import com.ems.finance.service.FinanceService.repositories.IncomeRepo;
import com.ems.finance.service.FinanceService.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepo incomeRepository;


    public Income addIncome(Income income) {
        income.setDate(LocalDateTime.now());
        return incomeRepository.save(income);
    }


    public List<Income> getIncomesByUserId(String userId) {
        return incomeRepository.findByUserId(userId);
    }


    public Income getIncomeById(String id) {
        return incomeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Income by given id not found" + id));
    }


    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }


    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }
}
