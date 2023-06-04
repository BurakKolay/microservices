package com.burakkolay.paymentservice.business.concretes;


import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.paymentservice.business.abstracts.PaymentService;
import com.burakkolay.paymentservice.business.abstracts.PosService;
import com.burakkolay.paymentservice.business.dto.requets.CreatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.requets.UpdatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetPaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.UpdatePaymentResponse;
import com.burakkolay.paymentservice.business.rules.PaymentBusinessRules;
import com.burakkolay.paymentservice.entities.Payment;
import com.burakkolay.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapperService mapper;
    private final PaymentBusinessRules rules;
    private final PosService posService;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        var brands = repository.findAll();
        var response = brands
                .stream()
                .map(brand -> mapper.forResponse().map(brand, GetAllPaymentsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        var brand = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(brand, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        rules.checkIfCardNumberExists(request.getCardNumber());
        var brand = mapper.forRequest().map(request, Payment.class);
        var createdPayment = repository.save(brand);
        var response = mapper.forResponse().map(createdPayment, CreatePaymentResponse.class);

        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        var brand = mapper.forRequest().map(request, Payment.class);
        brand.setId(id);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }

    @Override
    public ClientResponse processPayment(CreateRentalPaymentRequest request) {
        var response = new ClientResponse();
        processPaymentTransaction(request, response);

        return response;
    }

    private void processPaymentTransaction(CreateRentalPaymentRequest request, ClientResponse response) {
        try {
            rules.checkIfPaymentValid(request);
            var payment = repository.findByCardNumber(request.getCardNumber());
            double balance = payment.getBalance();
            rules.checkIfBalanceIsEnough(balance, request.getPrice());
            posService.pay();
            payment.setBalance(balance - request.getPrice());
            repository.save(payment);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
    }
}
