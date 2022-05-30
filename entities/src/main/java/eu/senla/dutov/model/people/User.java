package eu.senla.dutov.model.people;

import eu.senla.dutov.model.AbstractEntity;
import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.auth.Authority;
import eu.senla.dutov.model.auth.Role;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity(name = ModelConstantClass.USERS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = ModelConstantClass.USER_TYPE,
        discriminatorType = DiscriminatorType.STRING)
public abstract class User extends AbstractEntity {

    @Column(name = ModelConstantClass.USER_NAME)
    private String userName;
    private String password;
    private String name;
    private Integer age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ModelConstantClass.USER_ROLE,
            joinColumns = @JoinColumn(name = ModelConstantClass.USER_ID),
            inverseJoinColumns = @JoinColumn(name = ModelConstantClass.ROLE_ID))
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ModelConstantClass.USER_AUTHORITY,
            joinColumns = @JoinColumn(name = ModelConstantClass.USER_ID),
            inverseJoinColumns = @JoinColumn(name = ModelConstantClass.AUTHORITY_ID))
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
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(userName, user.userName)
                && Objects.equals(password, user.password)
                && Objects.equals(name, user.name)
                && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, password, name, age);
    }
}
