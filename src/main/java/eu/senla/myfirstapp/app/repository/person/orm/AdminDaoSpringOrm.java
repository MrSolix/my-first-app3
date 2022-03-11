package eu.senla.myfirstapp.app.repository.person.orm;

import eu.senla.myfirstapp.model.people.Admin;
import org.springframework.stereotype.Repository;

import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_ADMIN_BY_ID;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_ADMIN_BY_NAME;
import static eu.senla.myfirstapp.app.util.ConstantsClass.GET_ALL_ADMINS;


@Repository
public class AdminDaoSpringOrm extends AbstractPersonDaoSpringOrm {

    public AdminDaoSpringOrm() {
        clazz = Admin.class;
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
