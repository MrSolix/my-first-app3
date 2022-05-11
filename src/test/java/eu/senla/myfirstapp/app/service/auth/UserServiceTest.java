package eu.senla.myfirstapp.app.service.auth;

import eu.senla.myfirstapp.app.repository.person.PersonDAOInterface;
import eu.senla.myfirstapp.app.repository.person.data.SpringDataPersonRepositoryImpl;
import eu.senla.myfirstapp.app.service.person.PersonDaoInstance;
import eu.senla.myfirstapp.model.auth.UserPrincipal;
import eu.senla.myfirstapp.model.people.Admin;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Test
    void LoadUserByUsername_WithUsernameIsCorrectData_ShouldReturnUserDetails() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin()
                .withUserName("admin")
                .withPassword("$2a$12$GQX1oFO7dQIUHtDrS2/pNuYRfdfgt.00MWC7YzARUOSVT5Swusg1G");
        admin.setAuthorities(new HashSet<>());
        PersonDaoInstance personDaoInstance = new PersonDaoInstance(new HashMap<>());
        Field repository = personDaoInstance.getClass().getDeclaredField("repository");
        repository.setAccessible(true);
        SpringDataPersonRepositoryImpl springDataPersonRepository = mock(SpringDataPersonRepositoryImpl.class);
        when(springDataPersonRepository.find("admin")).thenReturn(Optional.of(admin));
        repository.set(personDaoInstance, springDataPersonRepository);
        UserService userService = new UserService(personDaoInstance);
        UserDetails actual = userService.loadUserByUsername("admin");
        UserDetails expected = new UserPrincipal(admin);
        assertEquals(expected, actual);
    }

    @Test
    void LoadUserByUsername_WithUsernameIsInvalidData_ShouldThrowException() throws NoSuchFieldException, IllegalAccessException {
        PersonDaoInstance personDaoInstance = new PersonDaoInstance(new HashMap<>());
        Field repository = personDaoInstance.getClass().getDeclaredField("repository");
        repository.setAccessible(true);
        SpringDataPersonRepositoryImpl springDataPersonRepository = mock(SpringDataPersonRepositoryImpl.class);
        when(springDataPersonRepository.find("invalid")).thenReturn(Optional.empty());
        repository.set(personDaoInstance, springDataPersonRepository);
        UserService userService = new UserService(personDaoInstance);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invalid"));
    }
}