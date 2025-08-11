package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceabilityLogRequest {
    private Long orderId;
    private String status;
    private LocalDateTime changedAt;
    private Long changedBy;
    private Long clientId;
    private String description;
}
