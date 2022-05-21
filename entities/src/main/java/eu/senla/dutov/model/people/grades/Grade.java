package eu.senla.dutov.model.people.grades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.people.Student;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import static eu.senla.dutov.model.people.grades.Grade.GRADES;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = GRADES)
public class Grade implements Serializable {

    public static final String GRADES = "grades";
    public static final String THEME_NAME = "theme_name";
    public static final String STUDENT_ID = "student_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = THEME_NAME)
    @ToString.Include
    @EqualsAndHashCode.Exclude
    private String themeName;

    @EqualsAndHashCode.Exclude
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = STUDENT_ID)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Student student;

    public Grade withId(Integer id) {
        setId(id);
        return this;
    }

    public Grade withName(String themeName) {
        setThemeName(themeName);
        return this;
    }

    public Grade withGrade(Integer grade) {
        setGrade(grade);
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Grade grade1 = (Grade) object;
        return Objects.equals(id, grade1.id) && Objects.equals(themeName, grade1.themeName) && Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeName, grade);
    }
}
