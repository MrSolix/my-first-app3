package eu.senla.dutov.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userName;
    private String password;
}
