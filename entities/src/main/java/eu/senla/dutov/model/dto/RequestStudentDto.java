package eu.senla.dutov.model.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestStudentDto extends RequestUserDto {

    private Set<GroupDto> groups = new HashSet<>();
    private List<GradeDto> grades = new ArrayList<>();
}
