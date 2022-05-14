package eu.senla.myfirstapp.app.repository.person.data;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.repository.person.PersonDAOInterface;
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

import static eu.senla.myfirstapp.app.util.ConstantsClass.ERROR_FROM_SAVE;
import static eu.senla.myfirstapp.app.util.ConstantsClass.ERROR_FROM_UPDATE;
import static eu.senla.myfirstapp.model.auth.Role.getRolesName;

@Repository("dataPerson")
public class SpringDataPersonRepositoryImpl implements PersonDAOInterface {

    private final PersonDAOInterface springDataPersonRepositoryImpl;
    private final SpringDataStudentRepository springDataStudentRepository;
    private final SpringDataTeacherRepository springDataTeacherRepository;
    private final SpringDataAdminRepository springDataAdminRepository;

    public SpringDataPersonRepositoryImpl(@Qualifier("dataPerson") @Lazy PersonDAOInterface springDataPersonRepositoryImpl,
                                          SpringDataStudentRepository springDataStudentRepository,
                                          SpringDataTeacherRepository springDataTeacherRepository,
                                          SpringDataAdminRepository springDataAdminRepository) {
        this.springDataPersonRepositoryImpl = springDataPersonRepositoryImpl;
        this.springDataStudentRepository = springDataStudentRepository;
        this.springDataTeacherRepository = springDataTeacherRepository;
        this.springDataAdminRepository = springDataAdminRepository;
    }

    @Override
    public Optional<Person> find(String name) {
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
    public Optional<Person> find(Integer id) {
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
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DataBaseException(ERROR_FROM_SAVE);
        }
        throw new DataBaseException(ERROR_FROM_SAVE);
    }

    @Override
    public Person update(Integer id, Person person) {
        person.setId(id);
        Optional<Person> optionalPerson = springDataPersonRepositoryImpl.find(id);
        if (optionalPerson.isEmpty()) {
            throw new DataBaseException(ERROR_FROM_UPDATE + ", Student Not Found");
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
    public Person remove(Person person) {
        if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
            springDataStudentRepository.deleteById(person.getId());
        }
        if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
            springDataTeacherRepository.deleteById(person.getId());
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        result.addAll(springDataStudentRepository.findAll());
        result.addAll(springDataTeacherRepository.findAll());
        return result;
    }
}
