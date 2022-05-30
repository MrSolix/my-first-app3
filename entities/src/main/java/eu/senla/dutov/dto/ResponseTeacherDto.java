package eu.senla.dutov.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ResponseTeacherDto extends ResponseUserDto {

    private GroupDto group;
    private Double salary;
}
