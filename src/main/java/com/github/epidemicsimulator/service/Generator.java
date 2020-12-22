package com.github.epidemicsimulator.service;

import java.util.List;

public interface Generator<T, V> {
    List<V> generate(T arg);
}
