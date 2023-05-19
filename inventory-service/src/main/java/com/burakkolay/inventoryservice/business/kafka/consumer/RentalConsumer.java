package com.burakkolay.inventoryservice.business.kafka.consumer;


import com.burakkolay.commonpackage.events.inventory.CarCreatedEvent;
import com.burakkolay.commonpackage.events.rental.RentalCreatedEvent;
import com.burakkolay.commonpackage.events.rental.RentalDeletedEvent;
import com.burakkolay.inventoryservice.business.abstracts.CarService;
import com.burakkolay.inventoryservice.entities.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final CarService service;

    @KafkaListener(
            topics = "rental-created",
            groupId = "inventory-rental-create"
    )
    public void consume(RentalCreatedEvent event){
        service.changeStateByCarId(State.Rented,event.getCarId());
        log.info("Rental created event consumed {}", event);
    }

    @KafkaListener(
            topics = "rental-deleted",
            groupId = "inventory-rental-delete"
    )
    public void consume(RentalDeletedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Rental deleted event consumed {}", event);
    }

}
