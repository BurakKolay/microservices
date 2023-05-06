package com.burakkolay.inventoryservice.business.abstracts;

import com.burakkolay.inventoryservice.business.dto.request.create.CreateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateModelRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateModelRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateModelResponse;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(UUID id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(UUID id, UpdateModelRequest request);
    void delete(UUID id);
}
