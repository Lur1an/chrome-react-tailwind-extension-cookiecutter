package chrome.extension.backend.models

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class LoginDto {
    String username
    String password

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken() {
        return new UsernamePasswordAuthenticationToken(username, password)
    }

}
