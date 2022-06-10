package eu.senla.dutov.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestTeacherDto extends RequestUserDto {

    public static final int MIN_SALARY = 0;
    private GroupDto group;

    @Min(MIN_SALARY)
    private double salary;
}
