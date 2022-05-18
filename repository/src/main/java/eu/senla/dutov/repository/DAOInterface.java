package eu.senla.dutov.repository;

import java.util.List;
import java.util.Optional;

public interface DAOInterface<T> {

    T save(T t);

    Optional<T> findById(Integer id);

    T update(Integer id, T t);

    void remove(T t);

    List<T> findAll();
}
