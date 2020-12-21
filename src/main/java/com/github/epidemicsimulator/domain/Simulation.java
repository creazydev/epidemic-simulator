package com.github.epidemicsimulator.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "simulations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "results"})
@ToString
public class Simulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "population")
    @NonNull
    private long population;

    @Column(name = "infected")
    @NonNull
    private long infected;

    @Column(name = "r")
    private double r;

    @Column(name = "m")
    @NonNull
    private double m;

    @Column(name = "ti")
    @NonNull
    private int ti;

    @Column(name = "tm")
    @NonNull
    private int tm;

    @Column(name = "ts")
    @NonNull
    private int ts;

    @OneToMany(mappedBy = "simulation", fetch = FetchType.LAZY)
    private List<SimulationResult> results;
}


