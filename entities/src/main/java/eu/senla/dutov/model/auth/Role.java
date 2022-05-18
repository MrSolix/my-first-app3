package eu.senla.dutov.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.people.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {

    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String USER_ROLE = "user_role";
    public static final String ROLE_ID = "role_id";
    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name = USER_ROLE,
            joinColumns = @JoinColumn(name = ROLE_ID),
            inverseJoinColumns = @JoinColumn(name = USER_ID))
    @JsonIgnore
    @ToString.Exclude
    private Collection<Person> users;


    public Role withId(Integer id) {
        setId(id);
        return this;
    }

    public Role withName(String name) {
        setName(name);
        return this;
    }

    public Role addPerson(Person person) {
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(person)) {
            users.add(person);
        }
        return this;
    }

    public static Set<String> getRolesName(Collection<Role> roles) {
        Set<String> result = new HashSet<>();
        if (roles != null) {
            roles.forEach(role -> result.add(role.getName()));
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Role role = (Role) object;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
