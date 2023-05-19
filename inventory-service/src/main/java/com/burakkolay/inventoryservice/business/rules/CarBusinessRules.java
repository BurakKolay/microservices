package com.burakkolay.inventoryservice.business.rules;

import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.inventoryservice.entities.enums.State;
import com.burakkolay.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;

    public void checkIfCarExists(UUID id) {
        if (!repository.existsById(id)) {
            // TODO: BusinessException
            throw new RuntimeException("CAR_NOT_EXISTS");
        }
    }

    public void checkCarAvailability(UUID id){
        var car = repository.findById(id).orElseThrow();
        if(!car.getState().equals(State.Available)){
            throw new BusinessException("CAR_NOT_AVAILABLE");
        }
    }
}
