package eu.senla.myfirstapp.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.myfirstapp.model.people.Person;
import java.util.Collection;
import java.util.Objects;
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
public class Authority {

    public static final String USER_AUTHORITY = "user_authority";
    public static final String AUTHORITY_ID = "authority_id";
    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name = USER_AUTHORITY,
            joinColumns = @JoinColumn(name = AUTHORITY_ID),
            inverseJoinColumns = @JoinColumn(name = USER_ID))

    @JsonIgnore
    @ToString.Exclude
    private Collection<Person> users;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Authority authority = (Authority) object;
        return Objects.equals(id, authority.id) && Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
