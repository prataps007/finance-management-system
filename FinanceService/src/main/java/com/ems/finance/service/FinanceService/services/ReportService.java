package com.ems.finance.service.FinanceService.services;

import com.ems.finance.service.FinanceService.entities.Expense;
import com.ems.finance.service.FinanceService.entities.Income;
import com.ems.finance.service.FinanceService.dto.ReportRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Expense> getFilteredExpenses(String userId, String category, LocalDate startDate, LocalDate endDate);

    List<Income> getFilteredIncomes(String userId, LocalDate startDate, LocalDate endDate);

    void generateExcelReport(ReportRequest request, HttpServletResponse response) throws Exception;

    //void generateOverallExcelReport(ReportRequest request, HttpServletResponse response) throws Exception;

}
