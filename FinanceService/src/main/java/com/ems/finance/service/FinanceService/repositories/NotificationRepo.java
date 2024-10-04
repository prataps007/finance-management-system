package com.ems.finance.service.FinanceService.repositories;

import com.ems.finance.service.FinanceService.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends MongoRepository<Notification, String> {

     List<Notification> findByUserIdAndSeen(String userId, boolean read);
}
