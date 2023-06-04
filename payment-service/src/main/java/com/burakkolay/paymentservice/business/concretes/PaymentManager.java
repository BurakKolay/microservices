package com.burakkolay.paymentservice.business.concretes;


import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.paymentservice.business.abstracts.PaymentService;
import com.burakkolay.paymentservice.business.abstracts.PosService;
import com.burakkolay.paymentservice.business.dto.requets.CreatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.requets.CreateRentalPaymentRequest;
import com.burakkolay.paymentservice.business.dto.requets.UpdatePaymentRequest;
import com.burakkolay.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.burakkolay.paymentservice.business.dto.responses.GetPaymentResponse;
import com.burakkolay.paymentservice.business.dto.responses.UpdatePaymentResponse;
import com.burakkolay.paymentservice.business.rules.PaymentBusinessRules;
import com.burakkolay.paymentservice.entities.Payment;
import com.burakkolay.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentBusinessRules rules;
    private final ModelMapperService mapper;
    private final PosService posService;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        var payments = repository.findAll();
        return payments.stream().map(payment -> mapper.forResponse()
                .map(payment, GetAllPaymentsResponse.class)).toList();
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        return mapper.forResponse().map(repository.findById(id), GetPaymentResponse.class);
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        rules.checkIfCardExists(request.getCardNumber());
        var payment = mapper.forResponse().map(request, Payment.class);
        payment.setId(UUID.randomUUID());
        var createdPayment = repository.save(payment);
        return mapper.forResponse().map(createdPayment, CreatePaymentResponse.class);
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        var payment = mapper.forResponse().map(request, Payment.class);
        payment.setId(id);
        var updatedPayment = repository.save(payment);
        return mapper.forResponse().map(updatedPayment, UpdatePaymentResponse.class);

    }

    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);

    }

    public ClientResponse pay(CreateRentalPaymentRequest request, Payment payment) {
        var response = new ClientResponse();
        try {
            rules.checkIfPaymentIsValid(request);
            rules.checkIfBalanceIdEnough(payment.getBalance(), request.getPrice());
            posService.pay(); // fake pos service
            payment.setBalance(payment.getBalance() - request.getPrice());
            repository.save(payment);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());

        }
        return response;
    }


    @Override
    public ClientResponse processRentalPayment(CreateRentalPaymentRequest request) {
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        return pay(request, payment);

    }
}
