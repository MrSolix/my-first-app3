package eu.senla.dutov.service.person;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.repository.person.PersonRepository;
import eu.senla.dutov.service.CRUDInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eu.senla.dutov.service.util.ServiceConstantClass.PASSED_ID_IS_NOT_EQUAL_TO_USER_ID;
import static eu.senla.dutov.service.util.ServiceConstantClass.USER_IS_NOT_FOUND;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService implements CRUDInterface<Person> {

    private static final String PERSON = "Person";
    private final PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public Person update(Integer id, Person person) {
        if (!id.equals(person.getId())) {
            throw new IncorrectValueException(format(PASSED_ID_IS_NOT_EQUAL_TO_USER_ID, PERSON));
        }
        person.setId(id);
        return personRepository.save(person);
    }

    @Override
    public void remove(int id) {
        personRepository
                .delete(personRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, PERSON))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    static void setPersonFields(Person oldPerson, Person newPerson) {
        String userName = newPerson.getUserName();
        String password = newPerson.getPassword();
        String name = newPerson.getName();
        Integer age = newPerson.getAge();
        if (userName != null) oldPerson.setUserName(userName);
        if (password != null) oldPerson.setPassword(password);
        if (name != null) oldPerson.setName(name);
        if (age != null) oldPerson.setAge(age);
    }
}
