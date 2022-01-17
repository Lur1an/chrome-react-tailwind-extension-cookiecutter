package chrome.extension.backend.services

import chrome.extension.backend.models.User
import groovy.util.logging.Slf4j
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Service
@Slf4j
@ConfigurationProperties('jwt-config')
class JwtService {

    @Autowired
    UserService userService

    String jwtSecret
    byte[] secretKey
    int jwtExpirationMs

    JwtParser jwtParser

    String generateJwt(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal()
        Date creationDate = new Date()
        Date expirationDate = new Date(creationDate.getTime() + jwtExpirationMs)
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
        return jwtBuilder.compact()
    }

    String getUsername(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject()
    }

    boolean validate(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken)
            return true;
        } catch (SignatureException e) {
            log.error "Invalid JWT signature: ${e.message}"
        } catch (MalformedJwtException e) {
            log.error "Invalid JWT token: ${e.message}"
        } catch (ExpiredJwtException e) {
            log.error "JWT token is expired: ${e.message}"
        } catch (UnsupportedJwtException e) {
            log.error "JWT token is unsupported: ${e.message}"
        } catch (IllegalArgumentException e) {
            log.error "JWT claims string is empty: ${e.message}"
        }
        return false
    }

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encode(jwtSecret.getBytes())
        jwtParser = Jwts.parser().setSigningKey(secretKey)
    }
}