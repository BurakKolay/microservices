package com.burakkolay.filterservice.repository;

import com.burakkolay.filterservice.entities.Filter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface FilterRepository extends MongoRepository<Filter, String> {
    void deleteByCarId(UUID carId);
    void deleteAllByBrandId(UUID brandId);
    Filter findByCarId(UUID carId);
}
