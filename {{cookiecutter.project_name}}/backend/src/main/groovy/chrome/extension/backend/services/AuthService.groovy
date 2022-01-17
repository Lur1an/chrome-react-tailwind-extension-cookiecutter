package chrome.extension.backend.services

import chrome.extension.backend.models.LoginDto
import chrome.extension.backend.models.User
import chrome.extension.backend.models.UserDto
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Slf4j
@Service
class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    UserService userService

    @Autowired
    JwtService jwtService

    User signup(UserDto userDto) {
        if (!userDto.matchingPasswords()) throw new Exception("Passwords are not matching")
        User newUser = userService.createUser(userDto.username, passwordEncoder.encode(userDto.password), userDto.email, ['ROLE_USER'])
        log.info "Signed up new User. Welcome, ${newUser.username}!"
        return newUser
    }

    Map login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(loginDto.getUsernamePasswordAuthToken())
        SecurityContextHolder.getContext().setAuthentication(authentication)
        String jwt = jwtService.generateJwt(authentication)
        User user = (User) authentication.getPrincipal()
        return [
                token: jwt,
                user: user
        ]
    }
}
