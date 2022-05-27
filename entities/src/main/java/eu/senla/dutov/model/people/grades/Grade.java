package eu.senla.dutov.model.people.grades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.people.Student;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ModelConstantClass.GRADES)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ModelConstantClass.THEME_NAME)
    @ToString.Include
    private String themeName;

    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ModelConstantClass.STUDENT_ID)
    @ToString.Exclude
    @JsonIgnore
    private Student student;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Grade grade1 = (Grade) object;
        return Objects.equals(id, grade1.id)
                && Objects.equals(themeName, grade1.themeName)
                && Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeName, grade);
    }
}
