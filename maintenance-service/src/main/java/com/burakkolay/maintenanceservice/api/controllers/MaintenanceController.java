package com.burakkolay.maintenanceservice.api.controllers;

import com.burakkolay.maintenanceservice.business.abstracts.MaintenanceService;
import com.burakkolay.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.burakkolay.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import com.burakkolay.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import com.burakkolay.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenances")
public class MaintenanceController {
    private final MaintenanceService service;

    @GetMapping
    public List<GetAllMaintenancesResponse> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetMaintenanceResponse getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @PostMapping
    public CreateMaintenanceResponse add(@RequestBody CreateMaintenanceRequest request){
        return service.add(request);
    }

    @PutMapping("/return/{id}")
    public GetMaintenanceResponse returnCarFromMaintenance(@PathVariable UUID id){
        return service.returnCarFromMaintenance(id);
    }
}
