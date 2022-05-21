package eu.senla.dutov.repository.person;

import eu.senla.dutov.model.people.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUserName(String username);
}
