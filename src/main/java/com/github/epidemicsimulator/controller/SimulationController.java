package com.github.epidemicsimulator.controller;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.exception.SimulationNotFoundException;
import com.github.epidemicsimulator.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    Simulation newSimulation(@RequestBody Simulation simulation) {
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
