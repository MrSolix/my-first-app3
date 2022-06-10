package eu.senla.dutov.service.user;

import eu.senla.dutov.dto.UserTimeStamp;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.exception.UsernameOrPasswordException;
import eu.senla.dutov.model.jwt.JwtRequest;
import eu.senla.dutov.model.jwt.JwtResponse;
import eu.senla.dutov.model.people.User;
import eu.senla.dutov.repository.jpa.user.UserRepository;
import eu.senla.dutov.repository.mongo.UserTimeStampRepository;
import eu.senla.dutov.service.util.JwtTokenUtil;
import eu.senla.dutov.service.util.ServiceConstantClass;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String USERNAME_OR_PASSWORD_IS_WRONG = "Username or password is wrong";
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserTimeStampRepository userTimeStampRepository;

    public JwtResponse authentication(JwtRequest jwtRequest) {
        JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(checkUser(jwtRequest).getUserName()));
        userTimeStampRepository.save(UserTimeStamp.builder()
                .timeStamp(LocalDateTime.now())
                .userName(jwtRequest.getUserName())
                .build());
        return jwtResponse;
    }

    private User checkUser(JwtRequest jwtRequest) {
        User user = userRepository.findByUserName(jwtRequest.getUserName()).orElseThrow(() ->
                new NotFoundException(String.format(ServiceConstantClass.VALUE_IS_NOT_FOUND, jwtRequest.getUserName())));
        if (passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new UsernameOrPasswordException(USERNAME_OR_PASSWORD_IS_WRONG);
        }
    }
}
