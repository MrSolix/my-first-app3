package eu.senla.myfirstapp.app.service.person;

import eu.senla.myfirstapp.app.repository.person.PersonDAOInterface;
import eu.senla.myfirstapp.app.service.AbstractDaoInstance;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDaoInstance extends AbstractDaoInstance {
    private static final String PERSON_DAO_SUFFIX = "Person";
    private PersonDAOInterface repository;
    private final Map<String, PersonDAOInterface> repositoryMap;

    @Autowired
    public PersonDaoInstance(Map<String, PersonDAOInterface> repositoryMap) {
        this.repositoryMap = repositoryMap;
    }

    @PostConstruct
    private void init() {
        repository = repositoryMap.get(repositoryType + PERSON_DAO_SUFFIX);
    }

    public PersonDAOInterface getRepository() {
        return repository;
    }
}
