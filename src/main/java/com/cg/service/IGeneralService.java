package com.cg.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<E, T> {

    List<E> findAll();

    E findById(T id);

    void save(E e);

    void delete(E e);

    void deleteById(T t);
}
