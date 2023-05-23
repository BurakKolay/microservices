package com.burakkolay.filterservice.business.kafka.consumer;

import com.burakkolay.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.burakkolay.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.burakkolay.filterservice.business.abstracts.FilterService;
import com.burakkolay.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceConsumer {
    private final FilterService service;

    @KafkaListener(
            topics = "maintenance-created",
            groupId = "filter-maintenance-create"
    )
    public void consume(MaintenanceCreatedEvent event){
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState("Maintenance");
        service.add(filter);
        log.info("Maintenance created event consumed {}", event);
    }

    @KafkaListener(
            topics = "maintenance-returned",
            groupId = "filter-maintenance-delete"
    )
    public void consume(MaintenanceDeletedEvent event){
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState("Available");
        service.add(filter);
        log.info("Maintenance deleted event consumed {}", event);
    }
}
