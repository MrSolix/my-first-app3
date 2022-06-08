package eu.senla.dutov.service.auth;

import eu.senla.dutov.model.auth.UserPrincipal;
import eu.senla.dutov.repository.jpa.user.UserRepository;
import eu.senla.dutov.service.util.ServiceConstantClass;
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

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format(ServiceConstantClass.USER_WITH_USERNAME_NOT_FOUND, username))));
    }
}
