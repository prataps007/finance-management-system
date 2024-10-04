package com.ems.finance.service.FinanceService.services;

import java.time.LocalDate;
import java.util.Map;

public interface AnalyticsService {

    Double calculateTotalExpenses(String userId, LocalDate startDate, LocalDate endDate);

    Double calculateTotalIncome(String userId, LocalDate startDate, LocalDate endDate);

    Map<String, Object> generateSpendingTrends(String userId, LocalDate startDate, LocalDate endDate);
}
