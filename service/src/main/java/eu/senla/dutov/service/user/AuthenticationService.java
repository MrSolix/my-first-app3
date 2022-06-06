package eu.senla.dutov.service.user;

import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.exception.PasswordException;
import eu.senla.dutov.model.jwt.JwtRequest;
import eu.senla.dutov.model.jwt.JwtResponse;
import eu.senla.dutov.model.people.User;
import eu.senla.dutov.repository.user.UserRepository;
import eu.senla.dutov.service.util.JwtTokenUtil;
import eu.senla.dutov.service.util.ServiceConstantClass;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse authentication(JwtRequest jwtRequest) {
        return new JwtResponse(jwtTokenUtil.generateToken(checkUser(jwtRequest).getUserName()));
    }

    private User checkUser(JwtRequest jwtRequest) {
        User user = userRepository.findByUserName(jwtRequest.getUsername()).orElseThrow(() ->
                new NotFoundException(String.format(ServiceConstantClass.USER_IS_NOT_FOUND, jwtRequest.getUsername())));
        if (passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new PasswordException("Password is wrong");
        }
    }
}
