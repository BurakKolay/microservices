package com.burakkolay.filterservice.api.controller;

import com.burakkolay.filterservice.business.abstracts.FilterService;
import com.burakkolay.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.burakkolay.filterservice.business.dto.responses.GetFilterResponse;
import com.burakkolay.filterservice.entities.Filter;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/filters")
public class FiltersController {
    private final FilterService service;

    @PostConstruct
    public void createDb(){
        service.add(new Filter());
    }

    @GetMapping
    public List<GetAllFiltersResponse> getAll(){
        return service.getAlll();
    }

    @GetMapping("/{id}")
    public GetFilterResponse getById(@PathVariable String id){
        return service.getById(id);
    }
}
