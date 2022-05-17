package eu.senla.myfirstapp.app.repository.person;

import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Teacher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public interface SpringDataTeacherRepository extends JpaRepository<Teacher, Integer> {

    String SELECT_TEACHER_BY_NAME = "from Teacher t join fetch t.roles r where t.userName = ?1 and r.name = 'TEACHER'";
    String SELECT_TEACHER_BY_ID = "from Teacher t join fetch t.roles r where t.id = ?1 and r.name = 'TEACHER'";
    String SELECT_ALL_TEACHERS = "from Teacher t join fetch t.roles r where r.name = 'TEACHER'";
    String UPDATE_TEACHER = "update Teacher s set s.userName = ?1, s.password = ?2," +
            " s.name = ?3, s.age = ?4 where s.id = ?5";

    @Query(SELECT_TEACHER_BY_NAME)
    Optional<Person> find(String name);

    @Query(SELECT_TEACHER_BY_ID)
    Optional<Person> find(Integer id);

    @Query(SELECT_ALL_TEACHERS)
    @NonNull
    List<Teacher> findAll();

    @Modifying
    @Query(UPDATE_TEACHER)
    void update(String userName, String password,
                String name, Integer age, Integer id);
}
