package eu.senla.myfirstapp.model.people;

import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.group.Group;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import static eu.senla.myfirstapp.model.auth.Role.ROLE_TEACHER;
import static eu.senla.myfirstapp.model.people.Teacher.SALARIES_TABLE_NAME;
import static eu.senla.myfirstapp.model.people.Teacher.TEACHER;

@Getter
@Setter
@Entity
@SecondaryTable(name = SALARIES_TABLE_NAME, pkJoinColumns = @PrimaryKeyJoinColumn(name = Teacher.TEACHER_ID))
@DiscriminatorValue(TEACHER)
public class Teacher extends Person {

    public static final String SALARIES_TABLE_NAME = "salaries";
    public static final String SALARY_COLUMN_NAME = "salary";
    public static final String TEACHER = "teacher";
    public static final String TEACHER_ID = "teacher_id";

    @ToString.Include
    @EqualsAndHashCode.Include
    @OneToOne(mappedBy = TEACHER, fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    private Group group;

    @Column(table = SALARIES_TABLE_NAME, name = SALARY_COLUMN_NAME)
    private Double salary;

    public Teacher() {
        addRole(new Role()
                .withId(2)
                .withName(ROLE_TEACHER)
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

    public Teacher withUserName(String userName) {
        setUserName(userName);
        return this;
    }

    public Teacher withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Teacher withName(String name) {
        setName(name);
        return this;
    }

    public Teacher withAge(int age) {
        setAge(age);
        return this;
    }

    public Teacher withSalary(double salary) {
        setSalary(salary);
        return this;
    }

    public Teacher withGroup(Group group) {
        setGroup(group);
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
}
