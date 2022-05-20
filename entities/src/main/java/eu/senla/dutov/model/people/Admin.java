package eu.senla.dutov.model.people;

import eu.senla.dutov.model.auth.Role;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import static eu.senla.dutov.model.people.Admin.ADMIN;

@Getter
@Setter
@Entity
@DiscriminatorValue(ADMIN)
public class Admin extends Person {

    public static final String ADMIN = "admin";

    public Admin() {
        addRole(new Role()
                .withId(3)
                .withName(Role.ROLE_ADMIN)
                .addPerson(this));
    }

    @Override
    public Admin withId(Integer id) {
        setId(id);
        return this;
    }

    public Admin withUserName(String userName) {
        setUserName(userName);
        return this;
    }

    public Admin withPassword(String password) {
        setPassword(password);
        return this;
    }
}
