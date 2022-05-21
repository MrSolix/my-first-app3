package eu.senla.dutov.service;

import java.util.List;
import java.util.Optional;

public interface CRUDInterface<T> {

    T save(T t);

    Optional<T> findById(Integer id);

    T update(Integer id, T t);

    void remove(int id);

    List<T> findAll();
}
