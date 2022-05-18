package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.repository.person.PersonDAOInterface;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    public static final String USER_S_NOT_FOUND = "User %s not found";
    private final PersonDAOInterface dataPerson;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = dataPerson.findByName(username);
        Person person = optionalPerson.orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format(USER_S_NOT_FOUND, username));
        });
        return new UserPrincipal(person);
    }
}
