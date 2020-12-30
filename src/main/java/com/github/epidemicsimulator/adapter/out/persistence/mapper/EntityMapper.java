package com.github.epidemicsimulator.adapter.out.persistence.mapper;

import java.util.List;

public interface EntityMapper<T, V>{
    V toEntity(T domainModel);
    List<V> toEntityList(List<T> domainModels);
    T toDomainModel(V entity);
    List<T> toDomainModelList(List<V> entities);
}
