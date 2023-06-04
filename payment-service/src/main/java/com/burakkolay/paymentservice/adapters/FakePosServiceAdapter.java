package com.burakkolay.paymentservice.adapters;


import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.paymentservice.business.abstracts.PosService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        boolean isPaymentSuccessful = new Random().nextBoolean();
        if (isPaymentSuccessful) {
            throw new BusinessException("PAYMENT_FAILED");
        }
    }
}
