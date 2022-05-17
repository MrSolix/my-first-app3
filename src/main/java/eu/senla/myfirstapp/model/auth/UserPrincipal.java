package eu.senla.myfirstapp.model.auth;

import eu.senla.myfirstapp.model.people.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class UserPrincipal implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    private final Person person;
    private final Collection<SimpleGrantedAuthority> authorities;

    public UserPrincipal(Person person) {
        this.person = person;
        List<SimpleGrantedAuthority> rolesList = person.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
                .collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorityList = person.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        this.authorities = new ArrayList<>();
        this.authorities.addAll(rolesList);
        this.authorities.addAll(authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return person.getUserName();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserPrincipal)) return false;
        UserPrincipal that = (UserPrincipal) object;
        return Objects.equals(person, that.person) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, authorities);
    }
}
