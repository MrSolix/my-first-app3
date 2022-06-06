package eu.senla.dutov.dto;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RequestUserDto {

    public static final String REGEXP_FOR_USERNAME = "^[a-zA-Z]+[\\w.]+[a-zA-Z]+$";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestUserDto)) {
            return false;
        }
        RequestUserDto that = (RequestUserDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(userName, that.userName)
                && Objects.equals(password, that.password)
                && Objects.equals(name, that.name)
                && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, name, age);
    }
}
