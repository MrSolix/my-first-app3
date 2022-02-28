package eu.senla.myfirstapp.app.repository.person.jpa;

import eu.senla.myfirstapp.model.people.Admin;
import eu.senla.myfirstapp.model.people.Person;
import org.springframework.stereotype.Repository;

import static eu.senla.myfirstapp.app.repository.ConstantsClass.GET_ADMIN_BY_ID;
import static eu.senla.myfirstapp.app.repository.ConstantsClass.GET_ADMIN_BY_NAME;
import static eu.senla.myfirstapp.app.repository.ConstantsClass.GET_ALL_ADMINS;


@Repository
public class AdminDaoJpa extends AbstractPersonDaoJpa {

    @Override
    protected Class<? extends Person> getType() {
        return Admin.class;
    }

    @Override
    protected String findAllJpql() {
        return GET_ALL_ADMINS;
    }

    @Override
    protected String namedQueryByName() {
        return GET_ADMIN_BY_NAME;
    }

    @Override
    protected String namedQueryById() {
        return GET_ADMIN_BY_ID;
    }
}
