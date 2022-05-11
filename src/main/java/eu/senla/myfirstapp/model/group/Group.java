package eu.senla.myfirstapp.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.AbstractEntity;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.Teacher;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "\"group\"")
public class Group extends AbstractEntity {
    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Teacher teacher;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "group_student",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonIgnore
    private Set<Student> students;


    @Override
    public Group withId(Integer id) {
        setId(id);
        return this;
    }

    public Group withTeacher(Teacher teacher) {
        setTeacher(teacher);
        if (teacher != null) {
            teacher.setGroup(this);
        }
        return this;
    }

    public Group withStudents(Set<Student> students) {
        setStudents(students);
        for (Student s : students) {
            s.addGroup(this);
        }
        return this;
    }

    public Group addStudent(Student student) {
        if (!students.contains(student) && student != null) {
            students.add(student);
        }
        return this;
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getGroups().remove(this);
    }

    public void removeTeacher(Teacher teacher) {
        this.teacher = null;
        teacher.setGroup(null);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
