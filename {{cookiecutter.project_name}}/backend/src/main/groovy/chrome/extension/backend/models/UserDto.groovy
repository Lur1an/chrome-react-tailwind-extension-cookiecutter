package chrome.extension.backend.models

import groovyjarjarantlr4.v4.runtime.misc.NotNull

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * User data-transfer-object
 * Used for client POST-request for registration
 */
class UserDto {

    @NotNull
    @NotEmpty
    String username

    @NotNull
    @NotEmpty
    String password

    @NotNull
    @NotEmpty
    String matchingPassword

    @Email
    @NotNull
    @NotEmpty
    String email

    boolean matchingPasswords() {
        return this.password == this.matchingPassword
    }

    @Override
    boolean equals(Object o) {
        if (!o instanceof UserDto) return false
        UserDto userDto = (UserDto) o
        return username == userDto.username && password == userDto.password && matchingPassword == userDto.matchingPassword && email == userDto.email
    }
}

