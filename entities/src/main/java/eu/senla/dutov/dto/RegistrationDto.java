package eu.senla.dutov.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


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
