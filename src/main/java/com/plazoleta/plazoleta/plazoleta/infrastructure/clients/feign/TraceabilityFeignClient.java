package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign;

import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.TraceabilityLogRequest;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.response.TraceabilityLogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "traceability-ms", url = "${microservices.traceability.url}")
public interface TraceabilityFeignClient {

    @PostMapping("/api/v1/traceability/logs")
    void saveTraceabilityLog(TraceabilityLogRequest request);

    @GetMapping("/api/v1/traceability/order/{orderId}")
    List<TraceabilityLogResponse> getTraceabilityByOrderId(@PathVariable("orderId") Long orderId);
}
