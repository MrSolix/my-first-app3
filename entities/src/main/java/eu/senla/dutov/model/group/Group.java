package eu.senla.dutov.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.AbstractEntity;
import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.model.people.Teacher;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ModelConstantClass.GROUP)
public class Group extends AbstractEntity {

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Teacher teacher;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = ModelConstantClass.GROUP_STUDENT,
            joinColumns = @JoinColumn(name = ModelConstantClass.GROUP_ID),
            inverseJoinColumns = @JoinColumn(name = ModelConstantClass.STUDENT_ID))
    @JsonIgnore
    private Set<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return getId() != null && Objects.equals(getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
