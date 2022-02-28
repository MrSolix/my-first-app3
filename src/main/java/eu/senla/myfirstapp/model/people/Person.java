package eu.senla.myfirstapp.model.people;

import eu.senla.myfirstapp.model.AbstractEntity;
import eu.senla.myfirstapp.model.auth.Authority;
import eu.senla.myfirstapp.model.auth.Role;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Person extends AbstractEntity implements Printable {
    @Column(name = "user_name")
    private String userName;
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Collection<Authority> authorities;

    public Set<String> getRolesName(Collection<Role> roles) {
        Set<String> result = new HashSet<>();
        if (roles != null) {
            roles.forEach(role -> result.add(role.getName()));
        }
        return result;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        if (role != null) {
            roles.add(role);
        }
    }
}
