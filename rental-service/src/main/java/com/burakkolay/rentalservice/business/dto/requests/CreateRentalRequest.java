package com.burakkolay.rentalservice.business.dto.requests;


import com.burakkolay.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.burakkolay.commonpackage.utils.dto.GetCardInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
    @NotNull
    private UUID carId;
    @Min(1)
    private double dailyPrice;
    @Min(1)
    private int rentedForDays;
    private GetCardInfo info;
    private CreateRentalPaymentRequest paymentRequest;
}
