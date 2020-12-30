package com.github.epidemicsimulator.application.service;

import com.github.epidemicsimulator.application.Generator;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.application.port.in.ModifySimulationUseCase;
import com.github.epidemicsimulator.application.port.out.LoadSimulationPort;
import com.github.epidemicsimulator.application.port.out.StoreSimulationPort;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ModifySimulationService implements ModifySimulationUseCase {
    private final Generator<GenerateSimulationCommand, SimulationResult> generator;
    private final LoadSimulationPort loadSimulationPort;
    private final StoreSimulationPort storeSimulationPort;

    @Override
    @Transactional
    public Optional<Simulation> modifyStoredSimulation(Long id, GenerateSimulationCommand validatedSimulation) {
        Optional<Simulation> simulation = loadSimulationPort.loadById(id);

        Simulation modifiedSimulation = new Simulation(
                validatedSimulation.getId(),
                validatedSimulation.getN(),
                validatedSimulation.getP(),
                validatedSimulation.getI(),
                validatedSimulation.getR(),
                validatedSimulation.getM(),
                validatedSimulation.getTi(),
                validatedSimulation.getTm(),
                validatedSimulation.getTs(),
                generator.generate(validatedSimulation)
        );

        if (!simulation.isPresent()) {
            modifiedSimulation.setId(null);
        }

        return Optional.of(storeSimulationPort.save(modifiedSimulation));
    }
}
