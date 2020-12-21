package com.github.epidemicsimulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "simulation_results")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = "simulation")
@ToString(exclude = "simulation")
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pi")
    private long pi;

    @Column(name = "pv")
    private long pv;

    @Column(name = "pm")
    private long pm;

    @Column(name = "pr")
    private long pr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Simulation simulation;
}
