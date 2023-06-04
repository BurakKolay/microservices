package com.burakkolay.paymentservice.business.rules;


import com.burakkolay.commonpackage.utils.constants.Messages;
import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("PAYMENT_NOT_FOUND");
        }
    }

    public void checkIfCardNumberExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new BusinessException("CARD_NUMBER_ALREADY_EXISTS");
        }
    }

    public void checkIfPaymentValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new BusinessException("NOT_A_VALID_PAYMENT");
        }
    }

    public void checkIfBalanceIsEnough(double balance, double price) {
        if (balance < price) {
            throw new BusinessException("NOT_ENOUGH_MONEY");
        }
    }
}
