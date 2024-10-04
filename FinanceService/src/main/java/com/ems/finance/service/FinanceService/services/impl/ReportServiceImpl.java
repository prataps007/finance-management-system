package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.entities.Budget;
import com.ems.finance.service.FinanceService.entities.Expense;
import com.ems.finance.service.FinanceService.entities.Income;
import com.ems.finance.service.FinanceService.dto.ReportRequest;
import com.ems.finance.service.FinanceService.repositories.BudgetRepo;
import com.ems.finance.service.FinanceService.repositories.ExpenseRepo;
import com.ems.finance.service.FinanceService.repositories.IncomeRepo;
import com.ems.finance.service.FinanceService.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private IncomeRepo incomeRepository;

    @Autowired
    private BudgetRepo budgetRepository;

    // Fetch all expenses or filter by category and date range if provided
    @Override
    public List<Expense> getFilteredExpenses(String userId, String category, LocalDate startDate, LocalDate endDate) {
        if (category != null && startDate != null && endDate != null) {
            return expenseRepository.findByUserIdAndCategoryAndDateBetween(userId, category, startDate, endDate);
        } else if (startDate != null && endDate != null) {
            return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        } else {
            return expenseRepository.findByUserId(userId); // Return all expenses if no category or date filters
        }
    }

    // Fetch all incomes or filter by date range if provided
    @Override
    public List<Income> getFilteredIncomes(String userId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        } else {
            return incomeRepository.findByUserId(userId); // Return all incomes if no date range
        }
    }

    // Get the budget for a specific category
    public Budget getBudgetForCategory(String userId, String category) {
        return budgetRepository.findByUserIdAndCategory(userId, category);
    }

    // Generate Excel Report (with budget and over-budget check)
    public void generateExcelReport(ReportRequest request, HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Overall Financial Report");

        // Fetch filtered expenses and incomes based on the request
        List<Expense> expenses = getFilteredExpenses(request.getUserId(), request.getCategory(), request.getStartDate(), request.getEndDate());
        List<Income> incomes = getFilteredIncomes(request.getUserId(), request.getStartDate(), request.getEndDate());

        // Track total expenses per category
        Map<String, BigDecimal> expenseCategoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            expenseCategoryTotals.merge(expense.getCategory(), BigDecimal.valueOf(expense.getAmount()), BigDecimal::add);
        }

        // Create Excel headers
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Type");
        header.createCell(1).setCellValue("Category");
        header.createCell(2).setCellValue("Description");
        header.createCell(3).setCellValue("Amount");
        header.createCell(4).setCellValue("Date");
        header.createCell(5).setCellValue("Budget");
        header.createCell(6).setCellValue("Over-Budget Amount");

        // Add expenses to the Excel sheet with budget and over-budget check
        int rowIdx = 1;
        for (Expense expense : expenses) {
            Row row = sheet.createRow(rowIdx++);

            // Check if this category has a budget
            Budget budget = getBudgetForCategory(request.getUserId(), expense.getCategory());
            BigDecimal budgetAmount = (budget != null) ? BigDecimal.valueOf(budget.getAmount()) : BigDecimal.ZERO;
            BigDecimal categoryTotal = expenseCategoryTotals.getOrDefault(expense.getCategory(), BigDecimal.ZERO);

            // Calculate over-budget amount (if any)
            BigDecimal overBudgetAmount = BigDecimal.ZERO;
            if (budgetAmount.compareTo(BigDecimal.ZERO) > 0 && categoryTotal.compareTo(budgetAmount) > 0) {
                overBudgetAmount = categoryTotal.subtract(budgetAmount);
            }

            row.createCell(0).setCellValue("Expense");
            row.createCell(1).setCellValue(expense.getCategory());
            row.createCell(2).setCellValue(expense.getDescription());
            row.createCell(3).setCellValue(expense.getAmount());
            row.createCell(4).setCellValue(expense.getDate().toString());
            row.createCell(5).setCellValue(budgetAmount.toString());
            row.createCell(6).setCellValue(overBudgetAmount.compareTo(BigDecimal.ZERO) > 0 ? overBudgetAmount.toString() : "-");
        }

        // Add incomes to the Excel sheet (incomes don’t have budgets)
        for (Income income : incomes) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue("Income");
            row.createCell(1).setCellValue(income.getSource());
            row.createCell(2).setCellValue("-");
            row.createCell(3).setCellValue(income.getAmount());
            row.createCell(4).setCellValue(income.getDate().toString());
            row.createCell(5).setCellValue("-");
            row.createCell(6).setCellValue("-");
        }

        // Write Excel file to the response output stream
        response.setHeader("Content-Disposition", "attachment; filename=overall_financial_report.xlsx");
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } finally {
            workbook.close();
        }
    }

//    @Override
//    public void generateExcelReport(ReportRequest request, HttpServletResponse response) throws Exception {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Financial Report");
//
//        List<Expense> expenses = getFilteredExpenses(request.getUserId(), request.getCategory(),
//                request.getStartDate(), request.getEndDate());
//
//        List<Income> incomes = getFilteredIncomes(request.getUserId(), request.getStartDate(), request.getEndDate());
//
//        // Create Excel headers
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("Type");
//        header.createCell(1).setCellValue("Category");
//        header.createCell(2).setCellValue("Amount");
//        header.createCell(3).setCellValue("Date");
//
//        // Add data to the Excel sheet
//        int rowIdx = 1;
//        for (Expense expense : expenses) {
//            Row row = sheet.createRow(rowIdx++);
//            row.createCell(0).setCellValue("Expense");
//            row.createCell(1).setCellValue(expense.getCategory());
//            row.createCell(2).setCellValue(expense.getAmount());
//            row.createCell(3).setCellValue(expense.getDate().toString());
//        }
//
//        for (Income income : incomes) {
//            Row row = sheet.createRow(rowIdx++);
//            row.createCell(0).setCellValue("Income");
//            row.createCell(1).setCellValue(income.getSource());
//            row.createCell(2).setCellValue(income.getAmount());
//            row.createCell(3).setCellValue(income.getDate().toString());
//        }
//
//        // Write Excel file to the response output stream
//        response.setHeader("Content-Disposition", "attachment; filename=financial_report.xlsx");
//        try (OutputStream out = response.getOutputStream()) {
//            workbook.write(out);
//        } finally {
//            workbook.close();
//        }
//    }
//
//    // Generate Excel Report (without category filtering)
//    public void generateOverallExcelReport(ReportRequest request, HttpServletResponse response) throws Exception {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Overall Financial Report");
//
//        // Fetch filtered expenses and incomes based on the request
//        List<Expense> expenses = getFilteredExpenses(request.getUserId(), null, request.getStartDate(), request.getEndDate());
//        List<Income> incomes = getFilteredIncomes(request.getUserId(), request.getStartDate(), request.getEndDate());
//
//        // Create Excel headers
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("Type");
//        header.createCell(1).setCellValue("Description");
//        header.createCell(2).setCellValue("Amount");
//        header.createCell(3).setCellValue("Date");
//
//        // Add data to the Excel sheet
//        int rowIdx = 1;
//        for (Expense expense : expenses) {
//            Row row = sheet.createRow(rowIdx++);
//            row.createCell(0).setCellValue("Expense");
//            row.createCell(1).setCellValue(expense.getCategory() + " : " + expense.getDescription());
//            row.createCell(2).setCellValue(expense.getAmount());
//            row.createCell(3).setCellValue(expense.getDate().toString());
//        }
//
//        for (Income income : incomes) {
//            Row row = sheet.createRow(rowIdx++);
//            row.createCell(0).setCellValue("Income");
//            row.createCell(1).setCellValue("Source: " + income.getSource());
//            row.createCell(2).setCellValue(income.getAmount());
//            row.createCell(3).setCellValue(income.getDate().toString());
//        }
//
//        // Write Excel file to the response output stream
//        response.setHeader("Content-Disposition", "attachment; filename=overall_financial_report.xlsx");
//        try (OutputStream out = response.getOutputStream()) {
//            workbook.write(out);
//        } finally {
//            workbook.close();
//        }
//    }

    // Add similar methods for PDF and CSV generation using JasperReports
}
