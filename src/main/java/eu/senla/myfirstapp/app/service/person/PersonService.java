package eu.senla.myfirstapp.app.service.person;

import eu.senla.myfirstapp.app.repository.person.PersonDAOInterface;
import eu.senla.myfirstapp.model.people.Person;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService implements PersonServiceInterface {

    private final PersonDAOInterface dataPerson;

    @Override
    public Person save(Person person) {
        return dataPerson.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findById(Integer id) {
        return dataPerson.findById(id);
    }

    @Override
    public Person update(Integer id, Person person) {
        person.setId(id);
        return dataPerson.save(person);
    }

    @Override
    public void remove(Person person) {
        dataPerson.remove(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return dataPerson.findAll();
    }
}
