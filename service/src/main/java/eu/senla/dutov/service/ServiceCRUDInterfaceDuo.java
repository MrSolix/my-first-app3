package eu.senla.dutov.service;

import java.util.List;

public interface ServiceCRUDInterfaceDuo<T, R> {

    R save(T t);

    R findById(Integer id);

    R update(Integer id, T t);

    void remove(int id);

    List<R> findAll();
}
