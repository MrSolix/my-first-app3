package eu.senla.myfirstapp.app.repository.person.orm;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.model.group.Group;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Optional;

import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_ALL_TEACHERS;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_TEACHER_BY_ID;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_TEACHER_BY_NAME;
import static eu.senla.myfirstapp.app.util.ConstantsClass.PERSON_NOT_FOUND;
import static eu.senla.myfirstapp.app.util.ConstantsClass.REMOVE_TEACHER_FROM_GROUP;


@Repository
public class TeacherDaoSpringOrm extends AbstractPersonDaoSpringOrm {

    public TeacherDaoSpringOrm() {
        clazz = Teacher.class;
    }

    @Override
    public Person remove(Person person) {
        Teacher teacher = (Teacher) person;
        Group group = teacher.getGroup();
        if (group != null) {
            Query query = em.createQuery(REMOVE_TEACHER_FROM_GROUP);
            query.setParameter("id", group.getId());
            query.setParameter("teacher_id", teacher.getId());
            query.executeUpdate();
            teacher.removeGroup(group);
        }
        return super.remove(teacher);
    }

    @Override
    public Person update(Integer id, Person person) {
        Optional<Person> oldTeacher = super.find(id);
        if (oldTeacher.isPresent()) {
            Teacher teacher = updateTeacher(((Teacher) oldTeacher.get()), ((Teacher) person));
            em.merge(teacher);
            return teacher;
        }
        throw new DataBaseException(PERSON_NOT_FOUND);
    }

    public Teacher updateTeacher(Teacher oldTeacher, Teacher teacher) {
        setPersonFields(oldTeacher, teacher);
        Group group = teacher.getGroup();
        Double salary = teacher.getSalary();
        if (group != null) {
            saveGroup(oldTeacher, teacher);
        }
        if (salary != null) {
            saveSalary(oldTeacher, teacher);
        }
        return em.merge(oldTeacher);
    }

    private Teacher setPersonFields(Teacher oldTeacher, Teacher teacher) {
        String userName = teacher.getUserName();
        String password = teacher.getPassword();
        String name = teacher.getName();
        Integer age = teacher.getAge();
        if (userName != null) {
            oldTeacher.setUserName(userName);
        }
        if (password != null) {
            oldTeacher.setPassword(password);
        }
        if (name != null) {
            oldTeacher.setName(name);
        }
        if (age != null) {
            oldTeacher.setAge(age);
        }
        return oldTeacher;
    }

    private void saveSalary(Teacher oldTeacher, Teacher teacher) {
        Double oldSalary = oldTeacher.getSalary();
        Double newSalary = teacher.getSalary();
        if (newSalary != null && !oldSalary.equals(newSalary)) {
            oldTeacher.setSalary(newSalary);
        }
    }

    private void saveGroup(Teacher oldTeacher, Teacher teacher) {
        Group newGroup = teacher.getGroup();
        Group oldGroup = oldTeacher.getGroup();
        Optional<Group> group = groupDaoSpringOrm.find(newGroup.getId());
        if (group.isPresent() && group.get().getTeacher() == null) {
            if (oldGroup != null) {
                oldGroup.setTeacher(null);
            }
            oldTeacher.setGroup(newGroup);
        }
    }

    @Override
    protected String findAllJpql() {
        return GET_ALL_TEACHERS;
    }

    @Override
    protected String namedQueryByName() {
        return GET_TEACHER_BY_NAME;
    }

    @Override
    protected String namedQueryById() {
        return GET_TEACHER_BY_ID;
    }
}
