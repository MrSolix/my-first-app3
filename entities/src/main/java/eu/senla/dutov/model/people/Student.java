package eu.senla.dutov.model.people;

import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.group.Group;
import eu.senla.dutov.model.people.grades.Grade;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import static eu.senla.dutov.model.people.Student.STUDENT;

@Getter
@Setter
@AllArgsConstructor
@Entity
@DiscriminatorValue(STUDENT)
public class Student extends Person {

    public static final String STUDENT = "student";
    public static final String GROUP_STUDENT_TABLE_NAME = "group_student";
    public static final String STUDENT_ID = "student_id";
    public static final String GROUP_ID = "group_id";

    @ToString.Include
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = GROUP_STUDENT_TABLE_NAME,
            joinColumns = @JoinColumn(name = STUDENT_ID),
            inverseJoinColumns = @JoinColumn(name = GROUP_ID))
    private Set<Group> groups;

    @ToString.Include
    @OneToMany(mappedBy = STUDENT,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Grade> grades;

    public Student() {
        addRole(new Role()
                .withId(1)
                .withName(Role.ROLE_STUDENT)
                .addPerson(this));
    }

    public Student withGroups(Set<Group> groups) {
        setGroups(groups);
        return this;
    }

    @Override
    public Student withId(Integer id) {
        setId(id);
        return this;
    }

    public Student withUserName(String userName) {
        setUserName(userName);
        return this;
    }

    public Student withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Student withName(String name) {
        setName(name);
        return this;
    }

    public Student withAge(int age) {
        setAge(age);
        return this;
    }

    public Student withGrades(List<Grade> grades) {
        setGrades(grades);
        return this;
    }

    public void setGrades(List<Grade> grades) {
        if (grades != null && this.grades != null) {
            this.grades.clear();
            this.grades.addAll(grades);
            grades.forEach(grade -> grade.setStudent(this));
        } else {
            this.grades = grades;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Student student = (Student) object;
        return Objects.equals(groups, student.groups) && Objects.equals(grades, student.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groups, grades);
    }
}
