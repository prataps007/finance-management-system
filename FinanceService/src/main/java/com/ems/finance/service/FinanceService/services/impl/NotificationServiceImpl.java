package com.ems.finance.service.FinanceService.services.impl;

import com.ems.finance.service.FinanceService.entities.Notification;
import com.ems.finance.service.FinanceService.exceptions.ResourceNotFoundException;
import com.ems.finance.service.FinanceService.repositories.NotificationRepo;
import com.ems.finance.service.FinanceService.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepository;


    // Save a notification in the database
    public Notification addNotification(Notification notification) {
        notification.setDate(LocalDateTime.now());
        notification.setSeen(false);
        return notificationRepository.save(notification);
    }

    // Retrieve unread notifications for a user
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndSeen(userId, false);
    }

    // Mark a notification as read
    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new ResourceNotFoundException("Notification with given id not found: "+notificationId));
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    // Kafka listener to receive messages and create notifications

    @KafkaListener(topics = {"budget_notifications", "expense_notifications"}, groupId = "expense-management")
    public void receiveNotificationMessage(String message) {

        // Process the message and generate a notification
        // Assuming message has userId and message content
        String[] parts = message.split(":", 2);
        String userId = parts[0];
        String notificationMessage = parts[1];

        Notification notification = new Notification(null, userId, notificationMessage, LocalDateTime.now(), false);
        addNotification(notification);
    }
}
