package eu.senla.dutov.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.AbstractEntity;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.model.people.Teacher;
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

import static eu.senla.dutov.model.group.Group.GROUP;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = GROUP)
public class Group extends AbstractEntity {

    public static final String GROUP = "\"group\"";
    public static final String GROUP_STUDENT = "group_student";
    public static final String GROUP_ID = "group_id";
    public static final String STUDENT_ID = "student_id";

    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Teacher teacher;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = GROUP_STUDENT,
            joinColumns = @JoinColumn(name = GROUP_ID),
            inverseJoinColumns = @JoinColumn(name = STUDENT_ID))
    @JsonIgnore
    private Set<Student> students;


    @Override
    public Group withId(Integer id) {
        setId(id);
        return this;
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
}
