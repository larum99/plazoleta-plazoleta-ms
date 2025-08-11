package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign;

import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderTraceabilityPort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.TraceabilityFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.TraceabilityLogRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderTraceabilityAdapter implements OrderTraceabilityPort {

    private final TraceabilityFeignClient traceabilityFeignClient;

    public OrderTraceabilityAdapter(TraceabilityFeignClient traceabilityFeignClient) {
        this.traceabilityFeignClient = traceabilityFeignClient;
    }

    @Override
    public void sendTraceabilityLog(TraceabilityLogRequest request) {
        traceabilityFeignClient.saveTraceabilityLog(request);
    }
}

