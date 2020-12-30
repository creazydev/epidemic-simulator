package com.github.epidemicsimulator.application;

import java.util.List;

public interface Generator<T, V> {
    List<V> generate(T arg);
}
