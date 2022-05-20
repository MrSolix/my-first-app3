package eu.senla.dutov.repository.person;

import eu.senla.dutov.model.people.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface SpringDataStudentRepository extends JpaRepository<Student, Integer> {

    String UPDATE_STUDENT = "update Student s set s.userName = ?1, s.password = ?2," +
            " s.name = ?3, s.age = ?4 where s.id = ?5";

    @Modifying
    @Query(UPDATE_STUDENT)
    void update(String userName, String password,
                String name, Integer age, Integer id);
}
