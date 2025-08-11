package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.TraceabilityLogRequest;

public interface OrderTraceabilityPort {
    void sendTraceabilityLog(TraceabilityLogRequest request);
}

