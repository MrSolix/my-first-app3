package eu.senla.dutov.dto;

import javax.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestTeacherDto extends RequestUserDto {

    public static final int MIN_SALARY = 0;
    private GroupDto group;

    @Min(MIN_SALARY)
    private Double salary;
}
