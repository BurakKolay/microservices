package com.burakkolay.inventoryservice.business.concretes;

import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.inventoryservice.business.abstracts.ModelService;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateModelRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateModelRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.burakkolay.inventoryservice.business.rules.ModelBusinessRules;
import com.burakkolay.inventoryservice.entities.Model;
import com.burakkolay.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {
    private final ModelRepository repository;
    private final ModelMapperService mapper;
    private final ModelBusinessRules rules;
    @Override
    public List<GetAllModelsResponse> getAll() {
        var models = repository.findAll();
        return models.stream()
                .map(model -> mapper.forResponse().map(model,GetAllModelsResponse.class))
                .toList();
    }

    @Override
    public GetModelResponse getById(UUID id) {
        rules.checkIfModelExists(id);
        var model = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(model, GetModelResponse.class);
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(null);
        repository.save(model);
        return mapper.forResponse().map(model, CreateModelResponse.class);
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request) {
        rules.checkIfModelExists(id);
        var model = mapper.forRequest().map(request,Model.class);
        model.setId(id);
        repository.save(model);
        return mapper.forResponse().map(model, UpdateModelResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfModelExists(id);
        repository.deleteById(id);
    }
}
