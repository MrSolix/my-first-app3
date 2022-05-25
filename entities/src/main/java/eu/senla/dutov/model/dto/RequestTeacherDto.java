package eu.senla.dutov.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestTeacherDto extends RequestUserDto {

    private GroupDto group;
    private Double salary;
}
