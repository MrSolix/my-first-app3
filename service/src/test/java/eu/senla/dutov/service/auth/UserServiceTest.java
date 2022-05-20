package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.model.people.Admin;
import eu.senla.dutov.repository.person.SpringDataPersonRepositoryImpl;
import eu.senla.dutov.service.auth.UserService;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    SpringDataPersonRepositoryImpl springDataPersonRepository = mock(SpringDataPersonRepositoryImpl.class);

    @Test
    void LoadUserByUsername_WithUsernameIsCorrectData_ShouldReturnUserDetails() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin()
                .withUserName("admin")
                .withPassword("$2a$12$GQX1oFO7dQIUHtDrS2/pNuYRfdfgt.00MWC7YzARUOSVT5Swusg1G");
        admin.setAuthorities(new HashSet<>());
        when(springDataPersonRepository.findByName("admin")).thenReturn(Optional.of(admin));
        UserService userService = new UserService(springDataPersonRepository);
        UserDetails actual = userService.loadUserByUsername("admin");
        UserDetails expected = new UserPrincipal(admin);
        assertEquals(expected, actual);
    }

    @Test
    void LoadUserByUsername_WithUsernameIsInvalidData_ShouldThrowException() throws NoSuchFieldException, IllegalAccessException {
        when(springDataPersonRepository.findByName("invalid")).thenReturn(Optional.empty());
        UserService userService = new UserService(springDataPersonRepository);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invalid"));
    }
}