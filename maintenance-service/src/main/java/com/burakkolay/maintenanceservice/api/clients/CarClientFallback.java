package com.burakkolay.maintenanceservice.api.clients;

import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {
    @Override
    public ClientResponse checkIfCarAvailable(UUID carId) {
        throw new RuntimeException("SERVER IS DOWN");
    }
}
