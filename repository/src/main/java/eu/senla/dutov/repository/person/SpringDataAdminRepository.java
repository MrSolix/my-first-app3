package eu.senla.dutov.repository.person;

import eu.senla.dutov.model.people.Admin;
import eu.senla.dutov.model.people.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface SpringDataAdminRepository extends JpaRepository<Admin, Integer> {

    String SELECT_ADMIN_BY_NAME = "from Admin s join fetch s.roles r where s.userName = ?1 and r.name = 'ADMIN'";
    String SELECT_ADMIN_BY_ID = "from Admin s join fetch s.roles r where s.id = ?1 and r.name = 'ADMIN'";

    @Query(SELECT_ADMIN_BY_NAME)
    Optional<Person> find(String name);

    @Query(SELECT_ADMIN_BY_ID)
    Optional<Person> find(Integer id);
}
