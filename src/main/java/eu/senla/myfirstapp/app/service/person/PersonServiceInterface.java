package eu.senla.myfirstapp.app.service.person;


import eu.senla.myfirstapp.app.repository.DAOInterface;
import eu.senla.myfirstapp.model.people.Person;
import java.util.Optional;

public interface PersonServiceInterface extends DAOInterface<Person> {

    Optional<Person> find(String name);

}
