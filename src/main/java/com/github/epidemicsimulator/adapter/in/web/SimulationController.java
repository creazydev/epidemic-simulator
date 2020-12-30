package com.github.epidemicsimulator.adapter.in.web;

import com.github.epidemicsimulator.adapter.in.web.exception.EntityNotFoundException;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationUseCase;
import com.github.epidemicsimulator.application.port.in.ModifySimulationUseCase;
import com.github.epidemicsimulator.application.port.out.LoadSimulationPort;
import com.github.epidemicsimulator.application.port.out.RemoveSimulationQuery;
import com.github.epidemicsimulator.domain.Simulation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class SimulationController {
    private final LoadSimulationPort loadSimulationPort;
    private final GenerateSimulationUseCase generateSimulationUseCase;
    private final ModifySimulationUseCase modifySimulationUseCase;
    private final RemoveSimulationQuery removeSimulationQuery;

    @GetMapping("/simulations")
    @ResponseStatus(value = HttpStatus.OK)
    List<Simulation> loadSimulations() {
        return loadSimulationPort.loadAll();
    }

    @GetMapping("/simulations/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    Simulation loadSimulation(@PathVariable Long id) {
        return loadSimulationPort.loadById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Simulation.class.getSimpleName()));
    }

    @PostMapping("/simulations")
    @ResponseStatus(value = HttpStatus.CREATED)
    Simulation generateAndStoreSimulation(@RequestBody Simulation simulation) {
        return generateSimulationUseCase.generateAndStore(new GenerateSimulationCommand(simulation));
    }

    @PutMapping("/simulations/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    Simulation modifySimulation(@PathVariable Long id, @RequestBody Simulation simulation) {
        return modifySimulationUseCase.modifyStoredSimulation(id, new GenerateSimulationCommand(simulation))
                .orElseThrow(() -> new EntityNotFoundException(id, Simulation.class.getSimpleName()));
    }

    @DeleteMapping("/simulations/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    void deleteSimulation(@PathVariable Long id) {
        if (!removeSimulationQuery.removeById(id)) {
                throw new EntityNotFoundException(id, Simulation.class.getSimpleName());
        }
    }
}
