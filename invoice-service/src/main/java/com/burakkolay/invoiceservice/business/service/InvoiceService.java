package com.burakkolay.invoiceservice.business.service;


import com.burakkolay.commonpackage.events.rental.InvoiceCreatedEvent;
import com.burakkolay.invoiceservice.business.dto.GetAllInvoiceResponse;
import com.burakkolay.invoiceservice.business.dto.GetInvoiceResponse;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    void create(InvoiceCreatedEvent event);
    List<GetAllInvoiceResponse> getAll();
    GetInvoiceResponse getInvoiceById(UUID id);
    void delete(UUID id);
}