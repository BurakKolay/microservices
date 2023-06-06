package com.burakkolay.invoiceservice.repository;



import com.burakkolay.invoiceservice.entities.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface InvoiceRepository extends MongoRepository<Invoice, UUID> {
}