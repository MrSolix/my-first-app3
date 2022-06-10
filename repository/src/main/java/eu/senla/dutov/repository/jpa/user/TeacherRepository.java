package eu.senla.dutov.repository.jpa.user;

import eu.senla.dutov.model.people.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}