package com.burakkolay.filterservice.business.abstracts;

import com.burakkolay.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.burakkolay.filterservice.business.dto.responses.GetFilterResponse;
import com.burakkolay.filterservice.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponse> getAlll();
    GetFilterResponse getById(UUID id);
    void add(Filter filter);
    void delete(UUID id);
    void deleteByCarId(UUID carId);
    void deleteAllByBrandId(UUID brandId);
    void deleteAllByModeId(UUID modelId);
}
