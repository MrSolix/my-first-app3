package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static eu.senla.dutov.service.util.ServiceConstantClass.USER_WITH_USERNAME_NOT_FOUND;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(personRepository
                .findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(format(USER_WITH_USERNAME_NOT_FOUND, username))));
    }
}
