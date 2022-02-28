package eu.senla.myfirstapp.model.people.grades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.people.Student;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode
@Data
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

    @ManyToOne
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

}
