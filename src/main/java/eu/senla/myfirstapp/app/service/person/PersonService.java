package eu.senla.myfirstapp.app.service.person;

import eu.senla.myfirstapp.model.people.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService implements PersonServiceInterface {

    private final PersonDaoInstance personDaoInstance;

    @Override
    public Person save(Person person) {
        return personDaoInstance.getRepository().save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> find(Integer id) {
        return personDaoInstance.getRepository().find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> find(String name) {
        return personDaoInstance.getRepository().find(name);
    }

    @Override
    public Person update(Integer id, Person person) {
        person.setId(id);
        return personDaoInstance.getRepository().save(person);
    }

    @Override
    public Person remove(Person person) {
        return personDaoInstance.getRepository().remove(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personDaoInstance.getRepository().findAll();
    }
}
