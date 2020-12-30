package com.github.epidemicsimulator.application.port.out;

import com.github.epidemicsimulator.domain.Simulation;

public interface StoreSimulationPort {
    Simulation save(Simulation simulation);
}
