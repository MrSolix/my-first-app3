package eu.senla.dutov.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ResponseStudentDto extends ResponseUserDto {

    private Set<GroupDto> groups = new HashSet<>();
    private List<GradeDto> grades = new ArrayList<>();
}
