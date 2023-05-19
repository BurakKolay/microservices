package com.burakkolay.inventoryservice.business.concretes;

import com.burakkolay.commonpackage.events.inventory.CarCreatedEvent;
import com.burakkolay.commonpackage.events.inventory.CarDeletedEvent;
import com.burakkolay.commonpackage.utils.dto.ClientResponse;
import com.burakkolay.commonpackage.utils.exceptions.BusinessException;
import com.burakkolay.commonpackage.utils.mappers.ModelMapperService;
import com.burakkolay.inventoryservice.business.abstracts.CarService;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateCarRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateCarRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.burakkolay.inventoryservice.business.rules.CarBusinessRules;
import com.burakkolay.inventoryservice.entities.Car;
import com.burakkolay.inventoryservice.entities.enums.State;
import com.burakkolay.inventoryservice.repository.CarRepository;
import com.burakkolay.commonpackage.kafka.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CarManager implements CarService {
    private final CarRepository repository;
    private final ModelMapperService mapper;
    private final CarBusinessRules rules;
    private final KafkaProducer producer;
    @Override
    public List<GetAllCarsResponse> getAll() {
        var cars=repository.findAll();
        var response = cars
                .stream()
                .map(car -> mapper.forResponse().map(car,GetAllCarsResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetCarResponse getById(UUID id) {
        rules.checkIfCarExists(id);
        var car = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(car,GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID());
        car.setState(State.Available);
        var createdCar = repository.save(car);
        sendKafkaCarCreatedEvent(createdCar);
        var response = mapper.forResponse().map(createdCar, CreateCarResponse.class);

        return response;
    }

    @Override
    public UpdateCarResponse update(UUID id, UpdateCarRequest request) {
        rules.checkIfCarExists(id);
        var car = mapper.forRequest().map(request,Car.class);
        car.setId(id);
        repository.save(car);
        return mapper.forResponse().map(car,UpdateCarResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfCarExists(id);
        repository.deleteById(id);
        sendKafkaCarDeletedEvent(id);
    }

    @Override
    public ClientResponse checkIfCarAvailable(UUID id) {
        var response = new ClientResponse();
        validateCarAvailability(id,response);
        return response;
    }

    @Override
    public void changeStateByCarId(State state, UUID id) {
        repository.changeStateByCarId(state,id);
    }

    private void sendKafkaCarCreatedEvent(Car createdCar) {
        var event = mapper.forResponse().map(createdCar, CarCreatedEvent.class);
        producer.sendMessage(event,"car-created");
    }

    private void sendKafkaCarDeletedEvent(UUID id) {
        producer.sendMessage(new CarDeletedEvent(id),"car-deleted");
    }
    private void validateCarAvailability(UUID id, ClientResponse response) {
        try {
            rules.checkIfCarExists(id);
            rules.checkCarAvailability(id);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
    }
}
