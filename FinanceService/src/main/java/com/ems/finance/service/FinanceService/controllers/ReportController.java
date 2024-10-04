package com.ems.finance.service.FinanceService.controllers;

import com.ems.finance.service.FinanceService.dto.ReportRequest;
import com.ems.finance.service.FinanceService.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/generate")
    public void generateReport(@RequestBody ReportRequest request, HttpServletResponse response) throws Exception {

        if ("excel".equalsIgnoreCase(request.getFormat())) {
//            if (request.getCategory() == null || request.getCategory().isEmpty()) {
//                // Generate an overall report if no category is provided
//                reportService.generateOverallExcelReport(request, response);
//            } else {
                // Generate a report filtered by category
                reportService.generateExcelReport(request, response);
            //}
        }
        // Add logic for PDF and CSV report generation
    }
}
