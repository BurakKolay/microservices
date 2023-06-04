package com.burakkolay.rentalservice.api.clients;


import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentClientFallback implements PaymentClient {
    @Override
    public ClientResponse processPayment(CreateRentalPaymentRequest request) {
        return null;
    }
}
