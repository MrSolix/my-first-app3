package eu.senla.dutov.model.people;

import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.group.Group;
import eu.senla.dutov.model.people.grades.Grade;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@DiscriminatorValue(ModelConstantClass.STUDENT_TO_LOWER_CASE)
public class Student extends User {

    @ToString.Include
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = ModelConstantClass.GROUP_STUDENT,
            joinColumns = @JoinColumn(name = ModelConstantClass.STUDENT_ID),
            inverseJoinColumns = @JoinColumn(name = ModelConstantClass.GROUP_ID))
    private Set<Group> groups;

    @ToString.Include
    @OneToMany(mappedBy = ModelConstantClass.STUDENT_TO_LOWER_CASE,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Grade> grades;

    public Student() {
        Role roleStudent = new Role();
        roleStudent.setId(ModelConstantClass.ID_FOR_ROLE_STUDENT);
        roleStudent.setName(Role.ROLE_STUDENT);
        roleStudent.addUser(this);
        addRole(roleStudent);
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
