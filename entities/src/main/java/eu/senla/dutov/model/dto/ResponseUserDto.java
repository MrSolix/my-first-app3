package eu.senla.dutov.model.dto;

import lombok.Data;

@Data
public abstract class ResponseUserDto {

    private Integer id;
    private String userName;
    private String name;
    private Integer age;
}
