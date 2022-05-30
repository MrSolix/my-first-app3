package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.model.people.Admin;
import eu.senla.dutov.repository.user.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AbstractUserServiceTest {

    private static final String ADMIN = "admin";
    private static final String INVALID = "invalid";
    UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Test
    void LoadUserByUsername_WithUsernameIsCorrectData_ShouldReturnUserDetails() {
        Admin admin = new Admin();
        admin.setUserName(ADMIN);
        admin.setPassword("$2a$12$GQX1oFO7dQIUHtDrS2/pNuYRfdfgt.00MWC7YzARUOSVT5Swusg1G");
        admin.setAuthorities(new HashSet<>());
        Mockito.when(userRepository.findByUserName(ADMIN)).thenReturn(Optional.of(admin));
        UserService userService = new UserService(userRepository);
        UserDetails actual = userService.loadUserByUsername(ADMIN);
        UserDetails expected = new UserPrincipal(admin);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void LoadUserByUsername_WithUsernameIsInvalidData_ShouldThrowException() {
        Mockito.when(userRepository.findByUserName(INVALID)).thenReturn(Optional.empty());
        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(INVALID));
    }
}