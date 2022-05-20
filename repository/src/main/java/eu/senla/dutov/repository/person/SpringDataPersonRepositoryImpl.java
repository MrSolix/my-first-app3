package eu.senla.dutov.repository.person;

import eu.senla.dutov.exception.DataBaseException;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.model.people.Teacher;
import java.util.List;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import static eu.senla.dutov.model.auth.Role.ROLE_STUDENT;
import static eu.senla.dutov.model.auth.Role.ROLE_TEACHER;
import static eu.senla.dutov.model.auth.Role.getRolesName;
import static eu.senla.dutov.repository.person.SpringDataPersonRepositoryImpl.DATA_PERSON;

@Repository(DATA_PERSON)
public class SpringDataPersonRepositoryImpl implements PersonDAOInterface {

    public static final String STUDENT_NOT_FOUND = ", Student Not Found";
    public static final String DATA_PERSON = "dataPerson";
    public static final String ERROR_FROM_UPDATE = "Error from update";
    public static final String ERROR_FROM_SAVE = "Error from save";

    private final PersonDAOInterface springDataPersonRepositoryImpl;
    private final SpringDataStudentRepository springDataStudentRepository;
    private final SpringDataTeacherRepository springDataTeacherRepository;
    private final PersonRepository personRepository;


    public SpringDataPersonRepositoryImpl(
            @Qualifier(DATA_PERSON) @Lazy PersonDAOInterface springDataPersonRepositoryImpl,
            SpringDataStudentRepository springDataStudentRepository,
            SpringDataTeacherRepository springDataTeacherRepository,
            PersonRepository personRepository) {
        this.springDataPersonRepositoryImpl = springDataPersonRepositoryImpl;
        this.springDataStudentRepository = springDataStudentRepository;
        this.springDataTeacherRepository = springDataTeacherRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findByName(String name) {
        return personRepository.findByUserName(name);
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public Person save(Person person) {
        if (person.getId() != null) {
            return springDataPersonRepositoryImpl.update(person.getId(), person);
        }
        try {
            if (getRolesName(person.getRoles()).contains(ROLE_STUDENT)) {
                return springDataStudentRepository.saveAndFlush(((Student) person));
            }
            if (getRolesName(person.getRoles()).contains(ROLE_TEACHER)) {
                return springDataTeacherRepository.saveAndFlush(((Teacher) person));
            }
        } catch (ConstraintViolationException | DataIntegrityViolationException runtimeException) {
            throw new DataBaseException(ERROR_FROM_SAVE);
        }
        throw new DataBaseException(ERROR_FROM_SAVE);
    }

    @Override
    public Person update(Integer id, Person person) {
        person.setId(id);
        Optional<Person> optionalPerson = springDataPersonRepositoryImpl.findById(id);
        if (optionalPerson.isEmpty()) {
            throw new DataBaseException(ERROR_FROM_UPDATE + STUDENT_NOT_FOUND);
        }
        if (getRolesName(person.getRoles()).contains(ROLE_STUDENT) &&
                getRolesName(optionalPerson.get().getRoles()).contains(ROLE_STUDENT)) {
            Student oldStudent = (Student) optionalPerson.get();
            Student newStudent = (Student) person;
            setPersonFields(oldStudent, newStudent);
            springDataStudentRepository.update(oldStudent.getUserName(), oldStudent.getPassword(),
                    oldStudent.getName(), oldStudent.getAge(), oldStudent.getId());
            return oldStudent;
        }
        if (getRolesName(person.getRoles()).contains(ROLE_TEACHER) &&
                getRolesName(optionalPerson.get().getRoles()).contains(ROLE_TEACHER)) {
            Teacher oldTeacher = (Teacher) optionalPerson.get();
            Teacher newTeacher = (Teacher) person;
            setPersonFields(oldTeacher, newTeacher);
            springDataTeacherRepository.update(oldTeacher.getUserName(), oldTeacher.getPassword(),
                    oldTeacher.getName(), oldTeacher.getAge(), oldTeacher.getId());
            return oldTeacher;
        }
        throw new DataBaseException(ERROR_FROM_UPDATE);
    }

    @Override
    public void remove(int id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    private void setPersonFields(Person oldPerson, Person newPerson) {
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
