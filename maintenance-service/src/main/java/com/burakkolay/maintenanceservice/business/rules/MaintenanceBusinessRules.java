package com.burakkolay.maintenanceservice.business.rules;

import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.maintenanceservice.api.clients.CarClient;
import com.burakkolay.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarClient carClient;

    public void checkIfMaintenanceExists(UUID id){
        if(!repository.existsById(id)){
            throw new BusinessException("MAINTENANCE_NOT_EXISTS");
        }
    }

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
    public void checkIfCarIsNotUnderMaintenance(UUID carId) {
        if (!repository.existsByCarIdAndIsCompletedFalse(carId)) {
            throw new BusinessException("CAR_IS_CURRENTLY_UNDER_MAINTENANCE");
        }
    }
}
