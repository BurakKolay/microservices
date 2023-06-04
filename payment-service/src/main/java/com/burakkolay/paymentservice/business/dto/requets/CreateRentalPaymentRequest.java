package com.burakkolay.paymentservice.business.dto.requets;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateRentalPaymentRequest extends PaymentRequest {
    private double price;
}

