package eu.senla.myfirstapp.model.people;

import eu.senla.myfirstapp.model.AbstractEntity;
import eu.senla.myfirstapp.model.auth.Authority;
import eu.senla.myfirstapp.model.auth.Role;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Person extends AbstractEntity {
    @Column(name = "user_name")
    private String userName;
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(userName, person.userName) && Objects.equals(password, person.password) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(roles, person.roles) && Objects.equals(authorities, person.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, password, name, age, roles, authorities);
    }
}
