package com.ems.finance.service.FinanceService.repositories;

import com.ems.finance.service.FinanceService.entities.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BudgetRepo extends MongoRepository<Budget, String> {

    List<Budget> findByUserId(String userId);

    Budget findByUserIdAndCategory(String userId, String category);

    Budget findByCategory(String category);
}
