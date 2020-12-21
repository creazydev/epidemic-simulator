package com.github.epidemicsimulator.service;

import java.util.List;

public interface SimulationResultFactory<T, V> {
    List<V> create(T arg);
}
