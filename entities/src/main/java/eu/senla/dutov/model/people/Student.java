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

@Getter
@Setter
@AllArgsConstructor
@Entity
@DiscriminatorValue("student")
public class Student extends User {

    @ToString.Include
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
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
    @Fetch(FetchMode.JOIN)
    private List<Grade> grades;

    public Student() {
        addRole(new Role()
                .withId(1)
                .withName(Role.ROLE_STUDENT)
                .addPerson(this));
    }

    @Override
    public Student withId(Integer id) {
        setId(id);
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
