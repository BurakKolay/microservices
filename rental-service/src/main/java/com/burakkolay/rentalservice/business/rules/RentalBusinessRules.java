package com.burakkolay.rentalservice.business.rules;


import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.rentalservice.api.clients.CarClient;
import com.burakkolay.rentalservice.api.clients.PaymentClient;
import com.burakkolay.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    private final CarClient carClient;
    private final PaymentClient paymentClient;

    public void checkIfRentalExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("RENTAL_NOT_EXISTS");
        }
    }

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        checkClientResponse(response);
    }

    public void ensurePayment(CreateRentalPaymentRequest request) {
        var response = paymentClient.processPayment(request);
        checkClientResponse(response);
    }

    private void checkClientResponse(ClientResponse response) {
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
}
