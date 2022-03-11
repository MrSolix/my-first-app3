package eu.senla.myfirstapp.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.AbstractEntity;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "\"group\"")
public class Group extends AbstractEntity {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Teacher teacher;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
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
}
