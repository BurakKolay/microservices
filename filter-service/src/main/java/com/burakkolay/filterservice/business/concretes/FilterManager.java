package com.burakkolay.filterservice.business.concretes;

import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.filterservice.business.abstracts.FilterService;
import com.burakkolay.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.burakkolay.filterservice.business.dto.responses.GetFilterResponse;
import com.burakkolay.filterservice.entities.Filter;
import com.burakkolay.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllFiltersResponse> getAlll() {
        var filters = repository.findAll();
        var response = filters
                .stream()
                .map(filter -> mapper.forResponse().map(filter,GetAllFiltersResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetFilterResponse getById(String id) {
        var filter = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(filter,GetFilterResponse.class);
        return response;
    }

    @Override
    public void add(Filter filter) {
        repository.save(filter);
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void deleteByCarId(UUID carId) {
        repository.deleteByCarId(carId);
    }

    @Override
    public void deleteAllByBrandId(UUID brandId) {
        repository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteAllByModeId(UUID modelId) {

    }

    @Override
    public Filter getByCarId(UUID carId) {
        return repository.findByCarId(carId);
    }
}
