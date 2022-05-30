package eu.senla.dutov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResponseUserDto {

    private Integer id;
    private String userName;
    private String name;
    private Integer age;
}
