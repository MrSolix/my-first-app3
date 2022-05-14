package eu.senla.myfirstapp.app.repository.person.jpa;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.model.group.Group;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.grades.Grade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_ALL_STUDENTS;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_STUDENT_BY_ID;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_STUDENT_BY_NAME;
import static eu.senla.myfirstapp.app.util.ConstantsClass.PERSON_NOT_FOUND;


@Repository
public class StudentDaoJpa extends AbstractPersonDaoJpa {

    @Override
    public Person update(Integer id, Person person) {
        EntityManager em = helper.getObject();
        Optional<Person> oldStudent = super.find(id);
        if (oldStudent.isPresent()) {
            Student student = updateStudent(((Student) oldStudent.get()), ((Student) person));
            em.merge(student);
            return student;
        }
        throw new DataBaseException(PERSON_NOT_FOUND);
    }

    private Student updateStudent(Student oldStudent, Student student) {
        setPersonFields(oldStudent, student);
        Set<Group> groups = student.getGroups();
        List<Grade> grades = student.getGrades();
        if (groups != null && !groups.isEmpty()) {
            saveGroups(oldStudent, groups);
        }
        if (grades != null && !grades.isEmpty()) {
            saveGrades(oldStudent, grades);
        }
        return oldStudent;
    }

    private void setPersonFields(Student oldStudent, Student student) {
        String userName = student.getUserName();
        String password = student.getPassword();
        String name = student.getName();
        Integer age = student.getAge();
        if (userName != null) {
            oldStudent.setUserName(userName);
        }
        if (password != null) {
            oldStudent.setPassword(password);
        }
        if (name != null) {
            oldStudent.setName(name);
        }
        if (age != null) {
            oldStudent.setAge(age);
        }
    }

    private void saveGroups(Student oldStudent, Set<Group> studentGroups) {
        List<Group> oldGroups = new ArrayList<>(oldStudent.getGroups());
        for (Group g : studentGroups) {
            if (!oldGroups.contains(g)) {
                Optional<Group> group = groupDaoJpa.find(g.getId());
                group.ifPresent(oldStudent::addGroup);
            }
        }
        oldGroups.removeAll(studentGroups);
        for (Group group : oldGroups) {
            group.removeStudent(oldStudent);
        }
    }

    private void saveGrades(Student oldStudent, List<Grade> studentGrades) {
        List<Grade> oldGrades = oldStudent.getGrades();
        List<Grade> allGrades = new ArrayList<>(oldGrades);
        allGrades.removeAll(studentGrades);
        allGrades.addAll(studentGrades);
        if (allGrades.isEmpty()) {
            return;
        }
        List<Grade> updateGrades = equalsGradeLists(oldGrades, allGrades);
        List<Grade> newGrades = checkNewGrades(allGrades, updateGrades);
        oldStudent.getGrades().clear();
        oldStudent.setGrades(newGrades);
    }

    private List<Grade> checkNewGrades(List<Grade> allGrades, List<Grade> updateGrades) {
        List<Grade> result = new ArrayList<>();
        allGrades.removeAll(updateGrades);
        for (Grade grade : allGrades) {
            Grade newGrade = new Grade();
            if (grade.getThemeName() != null) {
                newGrade.setThemeName(grade.getThemeName());
            } else {
                newGrade.setThemeName("Math");
            }
            if (grade.getGrade() != null) {
                newGrade.setGrade(grade.getGrade());
            } else {
                newGrade.setGrade(0);
            }
            result.add(newGrade);
        }
        result.addAll(updateGrades);
        return result;
    }

    private List<Grade> equalsGradeLists(List<Grade> oldGrades, List<Grade> allGrades) {
        List<Grade> result = new ArrayList<>();
        Map<Integer, Grade> mapGrades = new HashMap<>();
        oldGrades.forEach(grade -> mapGrades.putIfAbsent(grade.getId(), grade));
        for (Grade grade : allGrades) {
            if (oldGrades.contains(grade)) {
                Grade oldGrade = mapGrades.get(grade.getId());
                Grade newGrade = new Grade();
                newGrade.setId(oldGrade.getId());
                if (grade.getThemeName() != null) {
                    newGrade.setThemeName(grade.getThemeName());
                } else {
                    newGrade.setThemeName(oldGrade.getThemeName());
                }
                if (grade.getGrade() != null) {
                    newGrade.setGrade(grade.getGrade());
                } else {
                    newGrade.setGrade(oldGrade.getGrade());
                }
                result.add(newGrade);
            }
        }
        return result;
    }

    @Override
    public Person remove(Person person) {
        Student student = (Student) person;
        EntityManager em = helper.getObject();
        for (int i = 0; i < student.getGroups().size(); i++) {
            Optional<Group> first = student.getGroups().stream().findFirst();
            first.ifPresent(student::removeGroup);
        }
        for (int i = 0; i < student.getGrades().size(); i++) {
            Grade grade = student.getGrades().get(i);
            em.remove(grade);
        }
        return super.remove(student);
    }

    @Override
    protected Class<? extends Person> getType() {
        return Student.class;
    }

    @Override
    protected String findAllJpql() {
        return GET_ALL_STUDENTS;
    }

    @Override
    protected String namedQueryByName() {
        return GET_STUDENT_BY_NAME;
    }

    @Override
    protected String namedQueryById() {
        return GET_STUDENT_BY_ID;
    }
}
