package eu.senla.myfirstapp.model.people;

import eu.senla.myfirstapp.model.auth.Role;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static eu.senla.myfirstapp.model.auth.Role.ROLE_ADMIN;
import static eu.senla.myfirstapp.model.people.Admin.ADMIN;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@DiscriminatorValue(ADMIN)
public class Admin extends Person {

    public static final String ADMIN = "admin";

    public Admin() {
        addRole(new Role()
                .withId(3)
                .withName(ROLE_ADMIN)
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
