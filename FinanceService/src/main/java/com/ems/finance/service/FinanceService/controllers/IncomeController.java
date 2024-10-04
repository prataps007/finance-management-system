package com.ems.finance.service.FinanceService.controllers;

import com.ems.finance.service.FinanceService.entities.Income;
import com.ems.finance.service.FinanceService.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/add")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        return ResponseEntity.ok(incomeService.addIncome(income));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Income>> getUserIncomes(@PathVariable String userId) {
        return ResponseEntity.ok(incomeService.getIncomesByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable String id) {
        Income income = incomeService.getIncomeById(id);
        return ResponseEntity.ok(income);
    }

    @PutMapping("/update")
    public ResponseEntity<Income> updateIncome(@RequestBody Income income) {
        return ResponseEntity.ok(incomeService.updateIncome(income));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
