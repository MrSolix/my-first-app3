package eu.senla.dutov.service.user;

import eu.senla.dutov.dto.RequestStudentDto;
import eu.senla.dutov.dto.ResponseStudentDto;
import eu.senla.dutov.mapper.StudentMapper;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.repository.jpa.user.StudentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Lazy
public class StudentService extends AbstractUserService<RequestStudentDto, ResponseStudentDto, Student> {

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        jpaRepository = studentRepository;
        abstractMapper = studentMapper;
        role = Role.ROLE_STUDENT;
    }
}
