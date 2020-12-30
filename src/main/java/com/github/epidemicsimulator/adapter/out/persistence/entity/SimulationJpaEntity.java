package com.github.epidemicsimulator.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "simulations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimulationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "n")
    private String n;

    @Column(name = "p")
    private long p;

    @Column(name = "i")
    private long i;

    @Column(name = "r")
    private double r;

    @Column(name = "m")
    private double m;

    @Column(name = "ti")
    private int ti;

    @Column(name = "tm")
    private int tm;

    @Column(name = "ts")
    private int ts;

    @OneToMany(mappedBy = "simulation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SimulationResultJpaEntity> results;
}
