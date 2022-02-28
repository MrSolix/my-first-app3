package eu.senla.myfirstapp.app.repository.person;


import eu.senla.myfirstapp.app.repository.DAOInterface;
import eu.senla.myfirstapp.model.people.Person;

import java.util.Optional;

public interface PersonDAOInterface extends DAOInterface<Person> {

    Optional<Person> find(String name);

}
