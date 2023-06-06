package com.burakkolay.invoiceservice.business.service;

import com.burakkolay.commonpackage.events.rental.InvoiceCreatedEvent;
import com.burakkolay.invoiceservice.business.dto.GetAllInvoiceResponse;
import com.burakkolay.invoiceservice.business.dto.GetInvoiceResponse;
import com.burakkolay.invoiceservice.entities.Invoice;
import com.burakkolay.invoiceservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private final ModelMapper mapper;
    private final InvoiceRepository invoiceRepository;
    @Override
    public void create(InvoiceCreatedEvent event) {
        var invoce=mapper.map(event, Invoice.class);
        invoiceRepository.save(invoce);

    }
    @Override
    public List<GetAllInvoiceResponse> getAll() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoice -> mapper.map(invoice, GetAllInvoiceResponse.class)).toList();
    }
    @Override
    public GetInvoiceResponse getInvoiceById(UUID id) {
        return mapper.map(invoiceRepository.findById(id).orElseThrow(),GetInvoiceResponse.class);
    }
    @Override
    public void delete(UUID id) {
        invoiceRepository.deleteById(id);
    }
}