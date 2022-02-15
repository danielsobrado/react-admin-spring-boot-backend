package demo.reactAdmin.crud.entities;

import demo.reactAdmin.crud.enums.Role;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Where(clause="published=1")
@Table( uniqueConstraints =  {
    @UniqueConstraint(columnNames = {"username"}),
})
public class PlatformUser implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name;
    public String username;
    public String location;
    public String role = getUserRole().getRole().toString().toLowerCase();
    public String password;
    public boolean published = true;

    @Transient
    private List<GrantedAuthority> authorities;

    public PlatformUser(){}

    public PlatformUser(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public static PlatformUser create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new PlatformUser(username, authorities);
    }

    public Set<GrantedAuthority> getAuthorities() {
        UserRole role = this.getUserRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getRole().authority()));
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public UserRole getUserRole() {
        return new UserRole(id, Role.ADMINISTRATOR);

    }

}
