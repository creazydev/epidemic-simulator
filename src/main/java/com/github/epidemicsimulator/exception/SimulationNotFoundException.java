package com.github.epidemicsimulator.exception;

public class SimulationNotFoundException extends RuntimeException {
    private final Long id;

    public SimulationNotFoundException(Long id) {
        super("Simulation could not be found with id: " + id);
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
