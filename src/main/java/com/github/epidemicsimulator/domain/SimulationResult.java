package com.github.epidemicsimulator.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class SimulationResult {
    private Long id;
    private long pi;
    private long pv;
    private long pm;
    private long pr;

    public static SimulationResult copy(SimulationResult simulationResult) {
        return new SimulationResult(
                simulationResult.getId(),
                simulationResult.getPi(),
                simulationResult.getPv(),
                simulationResult.getPm(),
                simulationResult.getPr());
    }
}
