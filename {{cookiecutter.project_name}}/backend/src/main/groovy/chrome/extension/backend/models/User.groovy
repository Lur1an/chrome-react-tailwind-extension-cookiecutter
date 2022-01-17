package chrome.extension.backend.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mongodb.lang.NonNull
import groovy.transform.CompileStatic
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import java.time.LocalDateTime

@Setter
@Getter
@CompileStatic
@Document(collection = 'users')
@JsonIgnoreProperties(['password', 'roles', 'id'])
class User implements UserDetails {

    @Id
    String id

    @NonNull
    @Indexed(unique = true)
    String email

    @NonNull
    @Indexed(unique = true)
    String username

    @NonNull
    String password

    @NonNull
    List<String> roles


    LocalDateTime createdOn
    LocalDateTime lastLogin

    // UserDetails fields
    private boolean accountNonExpired = true
    private boolean accountNonLocked = true
    private boolean credentialsNonExpired = true
    private boolean enabled = true

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.collect { String role ->
            new SimpleGrantedAuthority(role)
        }
    }

    @Override
    boolean isAccountNonExpired() {
        return accountNonExpired
    }

    @Override
    boolean isAccountNonLocked() {
        return accountNonLocked
    }

    @Override
    boolean isCredentialsNonExpired() {
        return credentialsNonExpired
    }

    @Override
    boolean isEnabled() {
        return true
    }

}
