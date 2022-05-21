package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.model.people.Admin;
import eu.senla.dutov.repository.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    PersonRepository personRepository = mock(PersonRepository.class);

    @Test
    void LoadUserByUsername_WithUsernameIsCorrectData_ShouldReturnUserDetails() {
        Admin admin = new Admin()
                .withUserName("admin")
                .withPassword("$2a$12$GQX1oFO7dQIUHtDrS2/pNuYRfdfgt.00MWC7YzARUOSVT5Swusg1G");
        admin.setAuthorities(new HashSet<>());
        when(personRepository.findByUserName("admin")).thenReturn(Optional.of(admin));
        UserService userService = new UserService(personRepository);
        UserDetails actual = userService.loadUserByUsername("admin");
        UserDetails expected = new UserPrincipal(admin);
        assertEquals(expected, actual);
    }

    @Test
    void LoadUserByUsername_WithUsernameIsInvalidData_ShouldThrowException() {
        when(personRepository.findByUserName("invalid")).thenReturn(Optional.empty());
        UserService userService = new UserService(personRepository);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invalid"));
    }
}