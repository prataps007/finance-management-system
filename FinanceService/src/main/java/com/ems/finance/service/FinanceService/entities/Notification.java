package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String userId;        // User to notify

    private String message;       // Notification message

    private LocalDateTime date;   // Timestamp of notification

    private boolean seen;         // Whether the notification has been read
}
