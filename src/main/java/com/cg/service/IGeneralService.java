package com.cg.service;

import java.util.List;

public interface IGeneralService<E, T> {

    List<E> findAll();

    E findById(T id);

    void create(E e);

    void update(T id, E e);

    void removeById(T id);
}
