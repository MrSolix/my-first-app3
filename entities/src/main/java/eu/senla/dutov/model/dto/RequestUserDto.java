package eu.senla.dutov.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public abstract class RequestUserDto {

    private Integer id;

    @Pattern(regexp = "^[a-zA-Z]+[\\w.]+[a-zA-Z]+$")
    @NotNull
    private String userName;
    private String password;
    private String name;

    @Min(1)
    @Max(1000)
    private Integer age;
}
