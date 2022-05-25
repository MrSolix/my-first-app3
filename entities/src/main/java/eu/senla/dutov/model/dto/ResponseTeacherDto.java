package eu.senla.dutov.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseTeacherDto extends ResponseUserDto {

    private GroupDto group;
    private Double salary;
}
