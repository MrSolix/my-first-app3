package eu.senla.dutov.model.people;

import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.group.Group;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@SecondaryTable(name = ModelConstantClass.SALARIES,
        pkJoinColumns = @PrimaryKeyJoinColumn(name = ModelConstantClass.TEACHER_ID))
@DiscriminatorValue(ModelConstantClass.TEACHER_TO_LOWER_CASE)
public class Teacher extends User {

    @ToString.Include
    @EqualsAndHashCode.Include
    @OneToOne(mappedBy = ModelConstantClass.TEACHER_TO_LOWER_CASE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Group group;

    @Column(table = ModelConstantClass.SALARIES, name = ModelConstantClass.SALARY)
    private Double salary;

    public Teacher() {
        Role roleTeacher = new Role();
        roleTeacher.setId(ModelConstantClass.ID_FOR_ROLE_TEACHER);
        roleTeacher.setName(Role.ROLE_TEACHER);
        roleTeacher.addUser(this);
        addRole(roleTeacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Teacher teacher = (Teacher) o;
        return getId() != null && Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), group, salary);
    }
}
