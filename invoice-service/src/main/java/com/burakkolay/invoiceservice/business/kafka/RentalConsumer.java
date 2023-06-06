package com.burakkolay.invoiceservice.business.kafka;

import com.burakkolay.commonpackage.events.rental.InvoiceCreatedEvent;
import com.burakkolay.invoiceservice.business.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final InvoiceService invoiceService;


    @KafkaListener(topics = "invoice",groupId = "created-invoice")
    public void consume(InvoiceCreatedEvent event){
        invoiceService.create(event);
        log.info("Rental created for invoice event consumed {}", event);
    }
}