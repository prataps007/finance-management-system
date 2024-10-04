package com.ems.finance.service.FinanceService.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.Data;

import java.time.LocalDateTime;

@Document(indexName = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    private String id;
    private String userId;
    private String action;
    private LocalDateTime date;
    private String details;

}

