package chrome.extension.backend.security

import groovy.util.logging.Slf4j
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error "Unauthorized error: ${authException.message}"
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unathorized")
    }
}
