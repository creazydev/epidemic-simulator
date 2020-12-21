package com.github.epidemicsimulator.controller;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.exception.SimulationNotFoundException;
import com.github.epidemicsimulator.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SimulationController {
    private final SimulationService service;

    @GetMapping("/simulations")
    List<Simulation> all() {
        return service.getAll();
    }

    @PostMapping("/simulations")
    @ResponseStatus(value = HttpStatus.CREATED)
    Simulation newSimulation(@Valid @RequestBody Simulation simulation) {
        return service.generateAndSave(simulation);
    }

    @GetMapping("/simulations/{id}")
    Simulation one(@PathVariable Long id) {
        return service.getOne(id)
                .orElseThrow(() -> new SimulationNotFoundException(id));
    }

    @DeleteMapping("/simulations/{id}")
    void deleteSimulation(@PathVariable Long id) {
        service.deleteSimulation(id);
    }
}
