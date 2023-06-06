package com.burakkolay.invoiceservice.api.controller;

import com.burakkolay.invoiceservice.business.dto.GetAllInvoiceResponse;
import com.burakkolay.invoiceservice.business.dto.GetInvoiceResponse;
import com.burakkolay.invoiceservice.business.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoicesController {
    private final InvoiceService service;

    @GetMapping
    public ResponseEntity<List<GetAllInvoiceResponse> >getALl(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetInvoiceResponse> getInvoiceById(@PathVariable UUID id){
        return new ResponseEntity<>(service.getInvoiceById(id),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}