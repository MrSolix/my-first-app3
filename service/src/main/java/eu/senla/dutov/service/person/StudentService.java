package eu.senla.dutov.service.person;

import eu.senla.dutov.mapper.StudentMapper;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.dto.RequestStudentDto;
import eu.senla.dutov.model.dto.ResponseStudentDto;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.repository.subclass.person.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService extends AbstractUserService<RequestStudentDto, ResponseStudentDto, Student> {

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper) {
        commonRepository = studentRepository;
        abstractMapper = studentMapper;
        role = Role.ROLE_STUDENT;
    }
}
