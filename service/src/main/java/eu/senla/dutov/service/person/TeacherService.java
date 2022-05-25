package eu.senla.dutov.service.person;

import eu.senla.dutov.mapper.TeacherMapper;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.dto.RequestTeacherDto;
import eu.senla.dutov.model.dto.ResponseTeacherDto;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.subclass.person.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherService extends AbstractUserService<RequestTeacherDto, ResponseTeacherDto, Teacher> {

    public TeacherService(TeacherRepository teacherRepository,
                          TeacherMapper teacherMapper) {
        commonRepository = teacherRepository;
        abstractMapper = teacherMapper;
        role = Role.ROLE_TEACHER;
    }
}
