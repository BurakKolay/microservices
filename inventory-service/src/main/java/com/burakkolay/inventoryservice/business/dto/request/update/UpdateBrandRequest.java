package com.burakkolay.inventoryservice.business.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBrandRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
