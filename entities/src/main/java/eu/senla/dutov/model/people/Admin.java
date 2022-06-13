package eu.senla.dutov.model.people;

import eu.senla.dutov.model.ModelConstantClass;
import eu.senla.dutov.model.auth.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(ModelConstantClass.ADMIN_TO_LOWER_CASE)
public class Admin extends User {

    public Admin() {
        Role roleAdmin = new Role();
        roleAdmin.setId(ModelConstantClass.ID_FOR_ROLE_ADMIN);
        roleAdmin.setName(Role.ROLE_ADMIN);
        roleAdmin.addUser(this);
        addRole(roleAdmin);
    }
}
