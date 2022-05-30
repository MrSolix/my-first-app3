package eu.senla.dutov.dto;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDto {

    private Integer id;
    private String themeName;
    private Integer grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeDto)) {
            return false;
        }
        GradeDto gradeDto = (GradeDto) o;
        return Objects.equals(id, gradeDto.id)
                && Objects.equals(themeName, gradeDto.themeName)
                && Objects.equals(grade, gradeDto.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeName, grade);
    }
}
