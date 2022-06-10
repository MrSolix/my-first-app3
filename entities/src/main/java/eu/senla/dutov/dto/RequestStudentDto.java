package eu.senla.dutov.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestStudentDto extends RequestUserDto {

    private Set<GroupDto> groups = new HashSet<>();
    private List<GradeDto> grades = new ArrayList<>();
}
