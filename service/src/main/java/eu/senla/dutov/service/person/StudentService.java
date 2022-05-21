package eu.senla.dutov.service.person;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.repository.person.SpringDataStudentRepository;
import eu.senla.dutov.service.CRUDInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eu.senla.dutov.model.auth.Role.ROLE_STUDENT;
import static eu.senla.dutov.service.person.PersonService.setPersonFields;
import static eu.senla.dutov.service.util.ServiceConstantClass.PASSED_ID_IS_NOT_EQUAL_TO_USER_ID;
import static eu.senla.dutov.service.util.ServiceConstantClass.USER_IS_NOT_FOUND;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService implements CRUDInterface<Student> {

    private final SpringDataStudentRepository springDataStudentRepository;

    @Override
    public Student save(Student student) {
        return springDataStudentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Integer id) {
        return springDataStudentRepository.findById(id);
    }

    @Override
    public Student update(Integer id, Student student) {
        if (!id.equals(student.getId())) {
            throw new IncorrectValueException(format(PASSED_ID_IS_NOT_EQUAL_TO_USER_ID, ROLE_STUDENT));
        }
        Student oldStudent = springDataStudentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_STUDENT)));
        setPersonFields(oldStudent, student);
        springDataStudentRepository.update(oldStudent.getUserName(), oldStudent.getPassword(),
                oldStudent.getName(), oldStudent.getAge(), oldStudent.getId());
        return oldStudent;
    }

    @Override
    public void remove(int id) {
        springDataStudentRepository
                .delete(springDataStudentRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_STUDENT))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return springDataStudentRepository.findAll();
    }
}
