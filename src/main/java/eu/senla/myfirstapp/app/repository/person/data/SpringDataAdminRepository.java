package eu.senla.myfirstapp.app.repository.person.data;

import eu.senla.myfirstapp.model.people.Admin;
import eu.senla.myfirstapp.model.people.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface SpringDataAdminRepository extends JpaRepository<Admin, Integer> {
    String SELECT_ADMIN_BY_NAME = "from Admin s join s.roles r where s.userName = ?1 and r.name = 'ADMIN'";
    String SELECT_ADMIN_BY_ID = "from Admin s join s.roles r where s.id = ?1 and r.name = 'ADMIN'";

    @Query(SELECT_ADMIN_BY_NAME)
    Optional<Person> find(String name);

    @Query(SELECT_ADMIN_BY_ID)
    Optional<Person> find(Integer id);
}
