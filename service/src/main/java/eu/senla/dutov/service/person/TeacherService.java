package eu.senla.dutov.service.person;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.person.SpringDataTeacherRepository;
import eu.senla.dutov.service.CRUDInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eu.senla.dutov.model.auth.Role.ROLE_TEACHER;
import static eu.senla.dutov.service.person.PersonService.setPersonFields;
import static eu.senla.dutov.service.util.ServiceConstantClass.PASSED_ID_IS_NOT_EQUAL_TO_USER_ID;
import static eu.senla.dutov.service.util.ServiceConstantClass.USER_IS_NOT_FOUND;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService implements CRUDInterface<Teacher> {

    private final SpringDataTeacherRepository springDataTeacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        return springDataTeacherRepository.save(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teacher> findById(Integer id) {
        return springDataTeacherRepository.findById(id);
    }

    @Override
    public Teacher update(Integer id, Teacher teacher) {
        if (!id.equals(teacher.getId())) {
            throw new IncorrectValueException(format(PASSED_ID_IS_NOT_EQUAL_TO_USER_ID, ROLE_TEACHER));
        }
        Teacher oldTeacher = springDataTeacherRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_TEACHER)));
        setPersonFields(oldTeacher, teacher);
        springDataTeacherRepository.update(oldTeacher.getUserName(), oldTeacher.getPassword(),
                oldTeacher.getName(), oldTeacher.getAge(), oldTeacher.getId());
        return oldTeacher;
    }

    @Override
    public void remove(int id) {
        springDataTeacherRepository
                .delete(springDataTeacherRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_TEACHER))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        return springDataTeacherRepository.findAll();
    }
}
