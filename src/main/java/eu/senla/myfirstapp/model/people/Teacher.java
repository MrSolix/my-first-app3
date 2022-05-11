package eu.senla.myfirstapp.model.people;

import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.group.Group;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Getter
@Setter
@ToString(callSuper = true)
@Entity
@SecondaryTable(name = "salaries", pkJoinColumns = @PrimaryKeyJoinColumn(name = "teacher_id"))
@NamedQuery(name = "findTeacherByName", query = "select u from Teacher u join u.roles r where u.userName = :name and r.name = 'TEACHER'")
@NamedQuery(name = "findTeacherById", query = "select u from Teacher u join u.roles r where u.id = :id and r.name = 'TEACHER'")
@NamedQuery(name = "findAllTeachers", query = "select u from Teacher u join u.roles r where r.name = 'TEACHER'")
@DiscriminatorValue("teacher")
public class Teacher extends Person {
    @ToString.Include
    @EqualsAndHashCode.Include
    @OneToOne(mappedBy = "teacher",fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    private Group group;
    @Column(table = "salaries", name = "salary")
    private Double salary;

    public Teacher() {
        addRole(new Role()
                .withId(2)
                .withName("TEACHER")
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

    public void removeGroup(Group group) {
        this.group = null;
        group.setTeacher(null);
    }

    @Override
    public String infoGet() {
        return "Name: \"" + getName() +
                "\"<br>Age: \"" + getAge() +
                "\"<br>Role: \"" + getRoles() +
                "\"<br>Group №: " + (group != null ? group.getId() : 0) +
                "<br>Salary: " + getSalary();
    }
}
