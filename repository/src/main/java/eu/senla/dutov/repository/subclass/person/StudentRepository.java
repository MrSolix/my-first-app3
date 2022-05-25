package eu.senla.dutov.repository.subclass.person;

import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.repository.base.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CommonRepository<Student, Integer> {

    String UPDATE_STUDENT = "update Student s set s.userName = ?1, s.password = ?2," +
            " s.name = ?3, s.age = ?4 where s.id = ?5";

    @Modifying
    @Query(UPDATE_STUDENT)
    @Override
    void update(String userName, String password,
                String name, Integer age, Integer id);
}
