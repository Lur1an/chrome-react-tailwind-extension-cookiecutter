package chrome.extension.backend.services

import chrome.extension.backend.models.User
import chrome.extension.backend.repositories.UserRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@CompileStatic
class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository

    User get(String id) {
        return userRepository.findById(id).get()
    }

    User getByEmail(String email) {
        return userRepository.findByEmail(email)
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
        if (user == null) throw new UsernameNotFoundException(username)
        return user
    }

    User createUser(String username, String password, String email, List<String> roles) {
        if (userRepository.existsByEmail(email)) throw new Exception("Email already registered")
        if (userRepository.existsByUsername(username)) throw new Exception("Username already registered")

        User user = new User()
        user.setUsername(username)
        user.setEmail(email)
        user.setRoles(['ROLE_USER'])
        user.setPassword(password)

        return userRepository.insert(user)
    }
}
