package com.ems.finance.service.FinanceService.repositories;

import com.ems.finance.service.FinanceService.entities.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepo extends MongoRepository<Income, String> {

    List<Income> findByUserId(String userId);

    List<Income> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
}
