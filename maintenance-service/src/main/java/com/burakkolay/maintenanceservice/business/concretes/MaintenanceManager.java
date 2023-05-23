package com.burakkolay.maintenanceservice.business.concretes;

import com.burakkolay.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.burakkolay.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.burakkolay.commonpackage.kafka.producer.KafkaProducer;
import com.burakkolay.maintenanceservice.business.abstracts.MaintenanceService;
import com.burakkolay.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.burakkolay.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import com.burakkolay.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import com.burakkolay.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import com.burakkolay.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import com.burakkolay.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;
import com.burakkolay.maintenanceservice.business.rules.MaintenanceBusinessRules;
import com.burakkolay.maintenanceservice.entities.Maintenance;
import com.burakkolay.maintenanceservice.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapper mapper;
    private final MaintenanceBusinessRules rules;
    private final KafkaProducer producer;
    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance> maintenances = repository.findAll();
        List<GetAllMaintenancesResponse> response = maintenances
                .stream()
                .map(maintenance -> mapper.map(maintenance, GetAllMaintenancesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        producer.sendMessage(new MaintenanceDeletedEvent(carId),"maintenance-returned");
        return mapper.map(maintenance,GetMaintenanceResponse.class);
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(UUID.randomUUID());
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        producer.sendMessage(new MaintenanceCreatedEvent(request.getCarId()),"maintenance-created");
        repository.save(maintenance);
        return mapper.map(maintenance,CreateMaintenanceResponse.class);
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
