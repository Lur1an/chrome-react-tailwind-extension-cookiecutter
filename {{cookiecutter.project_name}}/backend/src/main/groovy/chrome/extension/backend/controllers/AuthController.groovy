package chrome.extension.backend.controllers

import chrome.extension.backend.models.LoginDto
import chrome.extension.backend.models.UserDto
import chrome.extension.backend.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping('/api/auth')
class AuthController {

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    AuthService authService

    @PostMapping('/signup')
    @ResponseStatus(HttpStatus.CREATED)
    void signup(@RequestBody @Valid UserDto userDto) {
        authService.signup(userDto)
    }

    @PostMapping('/login')
    Map login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto)
    }
}

