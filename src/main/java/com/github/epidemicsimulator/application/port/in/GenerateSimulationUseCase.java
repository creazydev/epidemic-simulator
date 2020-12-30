package com.github.epidemicsimulator.application.port.in;

import com.github.epidemicsimulator.domain.Simulation;

public interface GenerateSimulationUseCase {

    Simulation generateAndStore(GenerateSimulationCommand validatedSimulation);
}
