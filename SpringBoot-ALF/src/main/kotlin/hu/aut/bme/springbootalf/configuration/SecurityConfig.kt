package hu.aut.bme.springbootalf.configuration

import hu.aut.bme.springbootalf.components.LogoutSuccessHandler
import hu.aut.bme.springbootalf.components.RestAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Configuration class for defining security settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
class SecurityConfig {

    @Autowired
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint? = null

    /**
     * Configures security filters and rules.
     *
     * This method configures security filters and rules for the application, including CORS, CSRF protection,
     * authorization rules, logout behavior, HTTP Basic authentication, and session management.
     *
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain configured with the specified security settings.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // Configure Cross-Origin Resource Sharing (CORS)
            .cors(Customizer.withDefaults<CorsConfigurer<HttpSecurity>>())
            // Disable Cross-Site Request Forgery (CSRF) protection
            .csrf { obj -> obj.disable() }
            // Configure authorization rules for different HTTP requests
            .authorizeHttpRequests { auth ->
                // Authorization rules for various HTTP requests
                auth.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.POST, "/api/sales/**").hasRole("ADMIN")

                auth.requestMatchers("/api/management/**").hasRole("ADMIN")

                auth.requestMatchers("/api/**").authenticated()

                auth.requestMatchers("/items/**").permitAll()
                auth.requestMatchers(HttpMethod.DELETE, "/items/**").hasRole("ADMIN")

                auth.anyRequest().permitAll()
            }
            // Configure logout behavior
            .logout { logout ->
                logout.logoutSuccessHandler(
                    LogoutSuccessHandler()
                )
            }
            // Configure HTTP Basic authentication
            .httpBasic { auth ->
                auth.authenticationEntryPoint(
                    restAuthenticationEntryPoint
                )
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.NEVER
                )
            }
            // Build and return the SecurityFilterChain
            .build()
    }

    /**
     * Configures CORS settings.
     *
     * This method defines CORS configuration for the application, specifying allowed origins, methods, credentials,
     * and headers.
     *
     * @return A CorsConfigurationSource configured with CORS settings.
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * Provides a PasswordEncoder bean for encoding passwords.
     *
     * This method creates and returns a BCryptPasswordEncoder instance for encoding passwords.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}