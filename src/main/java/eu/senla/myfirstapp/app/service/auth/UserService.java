package eu.senla.myfirstapp.app.service.auth;

import eu.senla.myfirstapp.app.service.person.PersonDaoInstance;
import eu.senla.myfirstapp.model.auth.UserPrincipal;
import eu.senla.myfirstapp.model.people.Person;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final PersonDaoInstance personDaoInstance;

    @Autowired
    public UserService(PersonDaoInstance personDaoInstance) {
        this.personDaoInstance = personDaoInstance;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personDaoInstance.getRepository().find(username);
        Person person = optionalPerson.orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        });
        return new UserPrincipal(person);
    }
}
