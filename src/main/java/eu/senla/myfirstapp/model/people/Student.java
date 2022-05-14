package eu.senla.myfirstapp.model.people;


import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.group.Group;
import eu.senla.myfirstapp.model.people.grades.Grade;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NamedQuery(name = "findStudentByName", query = "select u from Student u join u.roles r where u.userName = :name and r.name = 'STUDENT'")
@NamedQuery(name = "findStudentById", query = "select u from Student u join u.roles r where u.id = :id and r.name = 'STUDENT'")
@NamedQuery(name = "findAllStudents", query = "select u from Student u join u.roles r where r.name = 'STUDENT'")
@DiscriminatorValue("student")
public class Student extends Person {
    @ToString.Include
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "group_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;
    @ToString.Include
    @OneToMany(mappedBy = "student",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Grade> grades;

    public Student() {
        addRole(new Role()
                .withId(1)
                .withName("STUDENT")
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

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.getStudents().remove(this);
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

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setStudent(this);
    }

    public void removeGrade(Grade grade) {
        grades.remove(grade);
        grade.setStudent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(groups, student.groups) && Objects.equals(grades, student.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groups, grades);
    }
}
