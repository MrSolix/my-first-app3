package eu.senla.dutov.service.person;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.repository.person.PersonDAOInterface;
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
        if (!id.equals(person.getId())) {
            throw new IncorrectValueException("Passed id is not equal to person id");
        }
        person.setId(id);
        return dataPerson.save(person);
    }

    @Override
    public void remove(int id) {
        Optional<Person> optionalPerson = dataPerson.findById(id);
        if (optionalPerson.isEmpty()) {
            throw new NotFoundException("Person is not found");
        }
        dataPerson.remove(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return dataPerson.findAll();
    }
}
