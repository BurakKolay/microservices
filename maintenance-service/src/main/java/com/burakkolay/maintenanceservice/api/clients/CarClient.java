package com.burakkolay.maintenanceservice.api.clients;

import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
public interface CarClient {
    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    @Retry(name = "retry-maintenance")
    ClientResponse checkIfCarAvailable(@PathVariable UUID carId);
}
