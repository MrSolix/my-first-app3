package eu.senla.myfirstapp.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.people.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<Person> users;

    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_ADMIN = "ADMIN";

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
}
