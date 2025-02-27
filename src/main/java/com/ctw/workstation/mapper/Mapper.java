package com.ctw.workstation.mapper;

public interface Mapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
