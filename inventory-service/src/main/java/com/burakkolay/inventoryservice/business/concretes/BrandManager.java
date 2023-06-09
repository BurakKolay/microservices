package com.burakkolay.inventoryservice.business.concretes;

import com.burakkolay.commonpackage.events.inventory.BrandDeletedEvent;
import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.inventoryservice.business.abstracts.BrandService;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.burakkolay.inventoryservice.business.rules.BrandBusinessRules;
import com.burakkolay.inventoryservice.entities.Brand;
import com.burakkolay.inventoryservice.repository.BrandRepository;
import com.burakkolay.commonpackage.kafka.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final BrandBusinessRules rules;
    private final KafkaProducer producer;
    @Override
    public List<GetAllBrandsResponse> getAll() {
        var brands = repository.findAll();
        return brands.stream()
                .map(brand -> mapper.forResponse().map(brand,GetAllBrandsResponse.class))
                .toList();
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);
        var brand = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(brand,GetBrandResponse.class);
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        var brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(null);
        repository.save(brand);
        return mapper.forResponse().map(brand,CreateBrandResponse.class);
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(id);
        var brand = mapper.forRequest().map(request,Brand.class);
        brand.setId(id);
        repository.save(brand);
        return mapper.forResponse().map(brand,UpdateBrandResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);
        repository.deleteById(id);
        sendKafkaBrandDeletedEvent(id);
    }

    private void sendKafkaBrandDeletedEvent(UUID id) {
        producer.sendMessage(new BrandDeletedEvent(id),"brand-deleted");
    }
}
