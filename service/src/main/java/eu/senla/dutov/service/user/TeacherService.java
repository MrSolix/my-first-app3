package eu.senla.dutov.service.user;

import eu.senla.dutov.dto.RequestTeacherDto;
import eu.senla.dutov.dto.ResponseTeacherDto;
import eu.senla.dutov.mapper.TeacherMapper;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.user.TeacherRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Lazy
public class TeacherService extends AbstractUserService<RequestTeacherDto, ResponseTeacherDto, Teacher> {

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        jpaRepository = teacherRepository;
        abstractMapper = teacherMapper;
        role = Role.ROLE_TEACHER;
    }
}
