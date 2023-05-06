package com.burakkolay.inventoryservice.business.dto.responses.get;

import com.burakkolay.inventoryservice.entities.enums.State;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetCarResponse {
    private UUID id;
    private UUID modelId;
    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
}
