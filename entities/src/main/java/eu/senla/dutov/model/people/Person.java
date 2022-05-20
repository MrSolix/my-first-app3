package eu.senla.dutov.model.people;

import eu.senla.dutov.model.AbstractEntity;
import eu.senla.dutov.model.auth.Authority;
import eu.senla.dutov.model.auth.Role;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import static eu.senla.dutov.model.people.Person.USERS;
import static eu.senla.dutov.model.people.Person.USER_TYPE;
import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity(name = USERS)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = USER_TYPE,
        discriminatorType = STRING)
public abstract class Person extends AbstractEntity {

    public static final String USERS = "users";
    public static final String USER_TYPE = "user_type";
    public static final String USER_NAME = "user_name";
    public static final String USER_ROLE = "user_role";
    public static final String USER_ID = "user_id";
    public static final String ROLE_ID = "role_id";
    public static final String USER_AUTHORITY = "user_authority";
    public static final String AUTHORITY_ID = "authority_id";

    @Column(name = USER_NAME)
    @Pattern(regexp = "^[a-zA-Z]+[\\w.]+[a-zA-Z]+$")
    @NotNull
    private String userName;
    private String password;
    private String name;
    @Min(1)
    @Max(1000)
    private Integer age;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = USER_ROLE,
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = ROLE_ID))
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = USER_AUTHORITY,
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = AUTHORITY_ID))
    @Fetch(FetchMode.JOIN)
    private Set<Authority> authorities;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        if (role != null) {
            roles.add(role);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Person person = (Person) object;
        return Objects.equals(userName, person.userName) && Objects.equals(password, person.password) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(roles, person.roles) && Objects.equals(authorities, person.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, password, name, age, roles, authorities);
    }
}
