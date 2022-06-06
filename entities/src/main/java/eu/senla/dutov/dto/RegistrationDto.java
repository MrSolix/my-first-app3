package eu.senla.dutov.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class RegistrationDto {

    @Pattern(regexp = RequestUserDto.REGEXP_FOR_USERNAME)
    @NotNull
    private String userName;
    private String password;

    @NotNull
    private String role;
}
