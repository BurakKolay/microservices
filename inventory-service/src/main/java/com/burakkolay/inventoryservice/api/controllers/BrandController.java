package com.burakkolay.inventoryservice.api.controllers;


import com.burakkolay.inventoryservice.business.abstracts.BrandService;
import com.burakkolay.inventoryservice.business.dto.request.create.CreateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.request.update.UpdateBrandRequest;
import com.burakkolay.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.burakkolay.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.burakkolay.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService service;

    @GetMapping
    public List<GetAllBrandsResponse> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetBrandResponse getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest request){
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateBrandResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateBrandRequest request){
        return service.update(id,request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}
