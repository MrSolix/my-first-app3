package eu.senla.dutov.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository<T, R> extends JpaRepository<T, R> {

    void update(String userName, String password,
                String name, Integer age, Integer id);
}
