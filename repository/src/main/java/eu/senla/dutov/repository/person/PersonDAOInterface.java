package eu.senla.dutov.repository.person;

import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.repository.DAOInterface;
import java.util.Optional;

public interface PersonDAOInterface extends DAOInterface<Person> {

    Optional<Person> findByName(String name);
}
