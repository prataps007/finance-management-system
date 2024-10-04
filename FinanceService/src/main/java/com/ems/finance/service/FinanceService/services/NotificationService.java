package com.ems.finance.service.FinanceService.services;

import com.ems.finance.service.FinanceService.entities.Notification;

import java.util.List;

public interface NotificationService {

    Notification addNotification(Notification notification);

    List<Notification> getUnreadNotifications(String userId);

    void markAsRead(String notificationId);

    void receiveNotificationMessage(String message);
}
