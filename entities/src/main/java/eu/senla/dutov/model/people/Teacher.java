package eu.senla.dutov.model.people;

import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.group.Group;
import java.util.Objects;
import javax.persistence.CascadeType;
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
@SecondaryTable(name = "salaries", pkJoinColumns = @PrimaryKeyJoinColumn(name = "teacher_id"))
@DiscriminatorValue("teacher")
public class Teacher extends User {

    @ToString.Include
    @EqualsAndHashCode.Include
    @OneToOne(mappedBy = "teacher", fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    private Group group;

    @Column(table = "salaries", name = "salary")
    private Double salary;

    public Teacher() {
        addRole(new Role()
                .withId(2)
                .withName(Role.ROLE_TEACHER)
                .addPerson(this));
    }

    public void setSalary(Double salary) {
        if (salary != null && salary >= 0) {
            this.salary = salary;
        } else {
            this.salary = 0.0;
        }
    }

    @Override
    public Teacher withId(Integer id) {
        setId(id);
        return this;
    }

    public Teacher withSalary(double salary) {
        setSalary(salary);
        return this;
    }

    public void setGroup(Group group) {
        if (group == null) {
            if (this.group != null) {
                this.group.setTeacher(null);
            }
        } else {
            group.setTeacher(this);
        }
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Teacher teacher = (Teacher) o;
        return getId() != null && Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
