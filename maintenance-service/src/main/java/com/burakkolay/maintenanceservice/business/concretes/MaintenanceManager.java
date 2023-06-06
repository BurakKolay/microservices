package com.burakkolay.maintenanceservice.business.concretes;

import com.burakkolay.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.burakkolay.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.burakkolay.commonpackage.kafka.producer.KafkaProducer;
import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
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
    private final MaintenanceBusinessRules rules;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        var maintenances = repository.findAll();
        return maintenances
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
                .toList();
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        Maintenance maintenance = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        var maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        rules.checkIfCarIsNotUnderMaintenance(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        sendKafkaMaintenanceDeletedEvent(carId);
        return mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkIfCarUnderMaintenance(request.getCarId());
        rules.checkCarAvailabilityForMaintenance(request.getCarId());
        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(null);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        repository.save(maintenance);
        sendKafkaMaintenanceCreatedEvent(request.getCarId());
        return mapper.forResponse().map(maintenance, CreateMaintenanceResponse.class);
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);
        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);
        return mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        var carId = repository.findById(id).orElseThrow().getCarId();
        sendKafkaMaintenanceDeletedEvent(carId);
        repository.deleteById(id);
    }

    private void sendKafkaMaintenanceCreatedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCreatedEvent(carId), "maintenance-created");
    }

    private void sendKafkaMaintenanceDeletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceDeletedEvent(carId), "maintenance-returned");
    }
}
