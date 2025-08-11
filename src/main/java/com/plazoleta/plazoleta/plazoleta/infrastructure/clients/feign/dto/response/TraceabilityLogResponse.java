package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraceabilityLogResponse {
    private Long orderId;
    private String previousStatus;
    private String newStatus;
    private Long userId;
    private LocalDateTime changeDate;
}

