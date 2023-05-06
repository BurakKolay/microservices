package com.burakkolay.inventoryservice.repository;

import com.burakkolay.inventoryservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
}
