package com.burakkolay.maintenanceservice.repository;

import com.burakkolay.maintenanceservice.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {
    boolean existsByCarIdAndIsCompletedFalse(UUID carId);
    Maintenance findMaintenanceByCarIdAndIsCompletedFalse(UUID carId);
}
