package com.ems.finance.service.FinanceService.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequest {

    private String userId;
    private String category;  // Optional: Filter by category
    private LocalDate startDate;  // Optional: Filter by start date
    private LocalDate endDate;    // Optional: Filter by end date
    private String format;  // "pdf", "excel", or "csv"
}
