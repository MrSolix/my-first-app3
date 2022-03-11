package eu.senla.myfirstapp.app.repository.person.data;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.repository.person.PersonDAOInterface;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static eu.senla.myfirstapp.app.util.ConstantsClass.ERROR_FROM_SAVE;
import static eu.senla.myfirstapp.model.auth.Role.getRolesName;

@Repository("dataPerson")
@RequiredArgsConstructor
public class SpringDataPersonRepositoryImpl implements PersonDAOInterface {

    private final SpringDataStudentRepository springDataStudentRepository;
    private final SpringDataTeacherRepository springDataTeacherRepository;
    private final SpringDataAdminRepository springDataAdminRepository;

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
        if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
            if (person.getId() != null) {
                springDataStudentRepository.update(person.getUserName(), person.getPassword(),
                        person.getName(), person.getAge(), person.getId());
                return person;
            }
            return springDataStudentRepository.saveAndFlush(((Student) person));
        }
        if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
            if (person.getId() != null) {
                springDataTeacherRepository.update(person.getUserName(), person.getPassword(),
                        person.getName(), person.getAge(), person.getId());
                return person;
            }
            return springDataTeacherRepository.saveAndFlush(((Teacher) person));
        }
        throw new DataBaseException(ERROR_FROM_SAVE);
    }

    @Override
    public Person update(Integer id, Person person) {
        person.setId(id);
        if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
            return springDataStudentRepository.save(((Student) person));
        }
        return springDataTeacherRepository.save(((Teacher) person));
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
