package com.burakkolay.paymentservice.business.abstracts;


import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.burakkolay.paymentservice.business.dto.requets.CreatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.requets.UpdatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetPaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.UpdatePaymentResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetPaymentResponse getById(UUID id);
    CreatePaymentResponse add(CreatePaymentRequest request);
    UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request);
    void delete(UUID id);
    ClientResponse processPayment(CreateRentalPaymentRequest request);
}
