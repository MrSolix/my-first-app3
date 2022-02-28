package eu.senla.myfirstapp.app.service.facade;

import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.people.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckingService {
    private final PersonService personService;

    public Person checkUser(String userName) {
        Optional<? extends Person> person = personService.find(userName);
        return person.orElse(null);
    }

    public boolean isEmpty(String... string) {
        int i = 0;
        for (String s : string) {
            if ("".equals(s.trim())) {
                i++;
            }
        }
        return i > 0;
    }


}
