package com.burakkolay.inventoryservice.repository;

import com.burakkolay.inventoryservice.entities.Car;
import com.burakkolay.inventoryservice.entities.enums.State;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    @Modifying
    @Transactional
    @Query(value = "update Car set state =:state where id =:id")
    void changeStateByCarId(State state,UUID id);
}
