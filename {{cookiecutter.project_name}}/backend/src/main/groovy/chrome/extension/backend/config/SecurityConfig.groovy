package chrome.extension.backend.config

import chrome.extension.backend.security.AuthEntryPointJwt
import chrome.extension.backend.security.AuthTokenFilter
import chrome.extension.backend.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.web.cors.CorsConfiguration

import javax.servlet.http.HttpServletRequest

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig {

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder()
    }

    /**
     * Configuration for API-Security with API-keys
     */
    @Order(1)
    @Configuration
    static class APISecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Value('${api-key-header}')
        String securityHeader

        @Value('${api-key-value}')
        String securityKey

        protected void configure(HttpSecurity http) throws Exception {
            def apiAuthManager = new AuthenticationManager() {
                @Override
                Authentication authenticate(Authentication authentication) throws AuthenticationException {
                    def principal = authentication.getPrincipal().toString()
                    if (securityKey != principal) {
                        throw new BadCredentialsException('Wrong API key!')
                    }
                    authentication.setAuthenticated(true)
                    return authentication
                }
            }

            def filter = new AbstractPreAuthenticatedProcessingFilter() {
                @Override
                protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
                    return request.getHeader(securityHeader);
                }

                @Override
                protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
                    return "N/A";
                }
            }

            filter.setAuthenticationManager(apiAuthManager)

            // CONFIGURE YOUR OWN API-KEY-PROTECTED ENDPOINTS
            http.antMatcher('/api/secret/**')
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().addFilter(filter).authorizeRequests().anyRequest().authenticated()
        }
    }

    @Order(2)
    @Configuration
    static class ResourceSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        UserService userService

        @Autowired
        AuthEntryPointJwt authEntryPointJwt

        @Autowired
        PasswordEncoder passwordEncoder

        @Autowired
        AuthTokenFilter authTokenFilter

        @Override
        void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
            authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder)
        }

        @Bean
        @Override
        AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()

            // Set auth entry point
            http.exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
            http.authorizeRequests().antMatchers('/api/auth/**').permitAll()
            http.authorizeRequests().anyRequest().authenticated()

            // Session Management
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // Cors Configuration
            CorsConfiguration corsConfiguration = new CorsConfiguration()
            corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"))
            corsConfiguration.setAllowedOrigins(List.of("*"))
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", 'OPTIONS'))
            corsConfiguration.setExposedHeaders(List.of("Authorization"))
            http.cors().configurationSource(request -> corsConfiguration)

            // Add token filter
            http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
        }
    }
}
