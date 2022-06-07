package eu.senla.dutov.service.user;

import eu.senla.dutov.dto.RegistrationDto;
import eu.senla.dutov.dto.RequestStudentDto;
import eu.senla.dutov.dto.RequestTeacherDto;
import eu.senla.dutov.dto.RequestUserDto;
import eu.senla.dutov.dto.ResponseUserDto;
import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.service.util.ServiceConstantClass;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final Map<String, AbstractUserService> serviceMap = new ConcurrentHashMap<>();
    private final Map<String, RequestUserDto> userDtoMap = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(StudentService studentService, TeacherService teacherService,
                               PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        serviceMap.put(Role.ROLE_STUDENT, studentService);
        serviceMap.put(Role.ROLE_TEACHER, teacherService);
        userDtoMap.put(Role.ROLE_STUDENT, new RequestStudentDto());
        userDtoMap.put(Role.ROLE_TEACHER, new RequestTeacherDto());
    }

    public ResponseUserDto registration(RegistrationDto registrationDto) {
        AbstractUserService abstractUserService = serviceMap.get(registrationDto.getRole().toUpperCase(Locale.ROOT));
        if (abstractUserService == null) {
            throw new NotFoundException(String.format(ServiceConstantClass.VALUE_IS_NOT_FOUND,
                    registrationDto.getRole()));
        }
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        RequestUserDto requestUserDto = userDtoMap.get(registrationDto.getRole().toUpperCase(Locale.ROOT));
        requestUserDto.setUserName(registrationDto.getUserName());
        requestUserDto.setPassword(registrationDto.getPassword());
        return abstractUserService.save(requestUserDto);
    }
}
