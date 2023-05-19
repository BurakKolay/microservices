package com.burakkolay.inventoryservice.business.abstracts;

import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateCarRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateCarRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.burakkolay.inventoryservice.entities.enums.State;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponse getById(UUID id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UUID id, UpdateCarRequest request);
    void delete(UUID id);
    ClientResponse checkIfCarAvailable(UUID id);
    void changeStateByCarId(State state,UUID id);
}
