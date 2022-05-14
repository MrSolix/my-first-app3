package eu.senla.myfirstapp.model.people.grades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.people.Student;
import java.io.Serializable;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grades")
public class Grade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "theme_name")
    @ToString.Include
    @EqualsAndHashCode.Exclude
    private String themeName;

    @Column(name = "grade")
    @EqualsAndHashCode.Exclude
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "student_id")
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

    public String getThemeName() {
        return themeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(id, grade1.id) && Objects.equals(themeName, grade1.themeName) && Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeName, grade);
    }
}
