package eu.senla.myfirstapp.app.repository.person;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import static eu.senla.myfirstapp.app.repository.person.SpringDataPersonRepositoryImpl.DATA_PERSON;
import static eu.senla.myfirstapp.model.auth.Role.getRolesName;

@Repository(DATA_PERSON)
public class SpringDataPersonRepositoryImpl implements PersonDAOInterface {

    public static final String STUDENT_NOT_FOUND = ", Student Not Found";
    public static final String DATA_PERSON = "dataPerson";
    public static final String ERROR_FROM_UPDATE = "Error from update";
    public static final String ERROR_FROM_SAVE = "Error from save";

    private final PersonDAOInterface springDataPersonRepositoryImpl;
    private final SpringDataStudentRepository springDataStudentRepository;
    private final SpringDataTeacherRepository springDataTeacherRepository;
    private final SpringDataAdminRepository springDataAdminRepository;


    public SpringDataPersonRepositoryImpl(
            @Qualifier(DATA_PERSON) @Lazy PersonDAOInterface springDataPersonRepositoryImpl,
            SpringDataStudentRepository springDataStudentRepository,
            SpringDataTeacherRepository springDataTeacherRepository,
            SpringDataAdminRepository springDataAdminRepository) {
        this.springDataPersonRepositoryImpl = springDataPersonRepositoryImpl;
        this.springDataStudentRepository = springDataStudentRepository;
        this.springDataTeacherRepository = springDataTeacherRepository;
        this.springDataAdminRepository = springDataAdminRepository;
    }

    @Override
    public Optional<Person> findByName(String name) {
        Optional<Person> student = springDataStudentRepository.find(name);
        if (student.isPresent()) {
            return student;
        }
        Optional<Person> teacher = springDataTeacherRepository.find(name);
        if (teacher.isPresent()) {
            return teacher;
        }
        return springDataAdminRepository.find(name);
    }

    @Override
    public Optional<Person> findById(Integer id) {
        Optional<Person> student = springDataStudentRepository.find(id);
        if (student.isPresent()) {
            return student;
        }
        Optional<Person> teacher = springDataTeacherRepository.find(id);
        if (teacher.isPresent()) {
            return teacher;
        }
        return springDataAdminRepository.find(id);
    }

    @Override
    public Person save(Person person) {
        if (person.getId() != null) {
            return springDataPersonRepositoryImpl.update(person.getId(), person);
        }
        try {
            if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
                return springDataStudentRepository.saveAndFlush(((Student) person));
            }
            if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
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
        if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT) &&
                getRolesName(optionalPerson.get().getRoles()).contains(Role.ROLE_STUDENT)) {
            Student oldStudent = (Student) optionalPerson.get();
            Student newStudent = (Student) person;
            setPersonFields(oldStudent, newStudent);
            springDataStudentRepository.update(oldStudent.getUserName(), oldStudent.getPassword(),
                    oldStudent.getName(), oldStudent.getAge(), oldStudent.getId());
            return oldStudent;
        }
        if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER) &&
                getRolesName(optionalPerson.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            Teacher oldTeacher = (Teacher) optionalPerson.get();
            Teacher newTeacher = (Teacher) person;
            setPersonFields(oldTeacher, newTeacher);
            springDataTeacherRepository.update(oldTeacher.getUserName(), oldTeacher.getPassword(),
                    oldTeacher.getName(), oldTeacher.getAge(), oldTeacher.getId());
            return oldTeacher;
        }
        throw new DataBaseException(ERROR_FROM_UPDATE);
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

    @Override
    public void remove(Person person) {
        if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
            springDataStudentRepository.deleteById(person.getId());
        }
        if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
            springDataTeacherRepository.deleteById(person.getId());
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        personList.addAll(springDataStudentRepository.findAll());
        personList.addAll(springDataTeacherRepository.findAll());
        return personList;
    }
}
