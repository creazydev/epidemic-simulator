package com.github.epidemicsimulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "pi")
    @NonNull
    private long pi;

    @Column(name = "pv")
    @NonNull
    private long pv;

    @Column(name = "pm")
    @NonNull
    private long pm;

    @Column(name = "pr")
    @NonNull
    private long pr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @NonNull
    private Simulation simulation;
}
