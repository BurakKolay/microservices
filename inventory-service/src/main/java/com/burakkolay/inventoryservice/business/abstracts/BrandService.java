package com.burakkolay.inventoryservice.business.abstracts;

import com.burakkolay.inventoryservice.business.dto.request.create.CreateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateBrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();
    GetBrandResponse getById(UUID id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(UUID id, UpdateBrandRequest request);
    void delete(UUID id);
}
