package eu.senla.dutov.model.dto;

import javax.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestTeacherDto extends RequestUserDto {

    public static final int MIN_SALARY = 0;
    private GroupDto group;

    @Min(MIN_SALARY)
    private Double salary;
}
