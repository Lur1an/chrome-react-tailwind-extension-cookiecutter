package chrome.extension.backend.security

import chrome.extension.backend.services.JwtService
import chrome.extension.backend.services.UserService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
@CompileStatic
class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService

    @Autowired
    JwtService jwtService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request)
            if (jwt && jwtService.validate(jwt)) {
                String username = jwtService.getUsername(jwt)
                UserDetails userDetails = userService.loadUserByUsername(username)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        } catch (Exception e) {
            log.error "Cannot set user authentication ${e.message}"
        }
        filterChain.doFilter(request, response)
    }

    private static String parseJwt(HttpServletRequest request) {
        String rawToken = request.getHeader('Authorization')
        if (rawToken && rawToken.startsWith("Bearer ")) {
            return rawToken.substring(7)
        } else {
            return null
        }
    }
}
