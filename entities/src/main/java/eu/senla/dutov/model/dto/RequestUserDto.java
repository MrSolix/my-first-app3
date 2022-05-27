package eu.senla.dutov.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public abstract class RequestUserDto {

    private static final String REGEXP_FOR_USERNAME = "^[a-zA-Z]+[\\w.]+[a-zA-Z]+$";
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 1000;
    private Integer id;

    @Pattern(regexp = REGEXP_FOR_USERNAME)
    @NotNull
    private String userName;
    private String password;
    private String name;

    @Min(MIN_VALUE)
    @Max(MAX_VALUE)
    private Integer age;
}
