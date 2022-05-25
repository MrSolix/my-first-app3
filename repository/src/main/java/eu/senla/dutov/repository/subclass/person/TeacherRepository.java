package eu.senla.dutov.repository.subclass.person;

import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.base.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CommonRepository<Teacher, Integer> {

    String UPDATE_TEACHER = "update Teacher s set s.userName = ?1, s.password = ?2," +
            " s.name = ?3, s.age = ?4 where s.id = ?5";

    @Modifying
    @Query(UPDATE_TEACHER)
    @Override
    void update(String userName, String password,
                String name, Integer age, Integer id);
}
