package eu.senla.dutov.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.people.User;
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
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name = ModelConstantClass.USER_AUTHORITY,
            joinColumns = @JoinColumn(name = ModelConstantClass.AUTHORITY_ID),
            inverseJoinColumns = @JoinColumn(name = ModelConstantClass.USER_ID))
    @JsonIgnore
    @ToString.Exclude
    private Collection<User> users;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Authority authority = (Authority) object;
        return Objects.equals(id, authority.id) && Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
