package com.github.epidemicsimulator.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"results"})
@ToString
public class Simulation {
    private Long id;
    private String n;
    private long p;
    private long i;
    private double r;
    private double m;
    private int ti;
    private int tm;
    private int ts;
    private List<SimulationResult> results;
}


