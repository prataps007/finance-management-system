package com.ems.finance.service.FinanceService.controllers;

import com.ems.finance.service.FinanceService.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/spending-trends")
    public Map<String, Object> getSpendingTrends(@RequestParam String userId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return analyticsService.generateSpendingTrends(userId, start, end);
    }

    // Add additional endpoints for other analytics features as needed
}
